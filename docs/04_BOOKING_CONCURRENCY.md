# Default Booking Concurrency Protection

## Checkout path

```text
MainController -> BookingController -> BookingService
  -> StrategyFactory -> SynchronizedStrategy
     -> MatchSeatRepository.bookSeatsSynchronized(matchId, seatIds)
  -> PaymentRepository / BookingRepository / TransactionRepository
  -> BookingService.issueTickets -> TicketRepository

TicketController -> TicketService -> TicketRepository    history and Gate checks
```

- Booking creation stores a PENDING quote with up to four distinct seats.
- Online and Seller cash checkout recheck current availability before committing.
- StrategyFactory returns the existing SynchronizedStrategy. SyncMechanism has
  the single value SYNCHRONIZED.
- MatchSeatRepository uses the shared MatchSeatRepository.class lock. All requested seats must be
  AVAILABLE before their state is changed. Competing callers using the same
  repository file in one JVM enter this critical section in turn.
- The first eligible checkout marks the seats BOOKED. The next contender sees
  those seats occupied and records CONFLICT.
- BookingService confirms the successful payment and issues eligible tickets.
  Its retry checks return completed results without creating duplicate tickets.
- TransactionStatus retains SUCCESS, CONFLICT and FAILED for booking outcomes.
- MatchSeat.version increments when seat state changes.

## Transaction CSV

```text
id,bookingId,fanId,matchId,seatIds,mechanism,status,createdAt,completedAt,durationMillis,failureReason
```

The file is headerless. Seat IDs are separated by semicolons and the reason is
URL-encoded. Every saved transaction belongs to a booking. The parser also
accepts older 12-column booking records whose extra second column is empty.

## Verification and operating limits

BookingConcurrencySmoke starts two ordinary online checkouts against one seat.
It asserts one confirmed booking, one cancelled booking, one ticket, two recorded
attempts and SYNCHRONIZED on all transactions. A retry preserves the ticket and
transaction counts. Temporary fixtures isolate the check from source data.

The lock coordinates threads in a single JVM. Separate running applications can
still race. CSV file replacement is per file. A process failure between booking,
payment and ticket writes can require reconciliation. PENDING does not reserve
seats for a time window. Payment and refund services are mocked.
