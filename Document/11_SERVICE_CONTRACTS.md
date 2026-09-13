# Service Contracts

Tai lieu nay khong phai REST API. Vi du an la Java console MVC, "API" o day la contract giua Controller, Service va Repository de code nhat quan.

## DTO / Request Objects

### BookingRequest

| Field | Required | Notes |
|---|---|---|
| fanId | Yes | Fan owner cua booking |
| staffId | No | Co khi seller booking |
| matchId | Yes | Match can dat |
| seatIds | Yes | 1-4 seats |
| source | Yes | FAN, SELLER, SIMULATOR |
| paymentMethod | Yes | ONLINE, OFFLINE, SIMULATED |
| syncMechanism | Yes | NO_LOCK, SYNCHRONIZED, FILE_LOCK, OPTIMISTIC |
| idempotencyKey | No | Tranh duplicate submit |
| expiresAt | No | Can khi hold seats/pending booking |
| coreOnly | No | true cho simulator de bo qua payment checkout |

### BookingResult

| Field | Notes |
|---|---|
| success | true/false |
| bookingId | nullable |
| ticketIds | list |
| transactionId | required if recorded |
| failureReason | nullable |
| conflict | true when concurrency conflict |

### SimulationConfig

| Field | Notes |
|---|---|
| mechanism | Sync mechanism |
| scenario | HIGH, MEDIUM, LOW |
| threadCount | Number of tasks |
| targetMatchId | Match for simulation |
| targetSeatIds | Seat pool |
| seatsPerBooking | 1-4, recommend 1 for clean metric |

## Controller Contracts

### FanController

```text
showFanMenu(fanSession)
viewMatches()
viewSeatMap(matchId, sectionId)
createBooking(fanId, matchId, seatIds, paymentMethod)
viewMyTickets(fanId)
viewBookingHistory(fanId)
```

Rules:

- Validate input format only.
- Call `BookingService.createFanBookingCheckout`.
- Catch business exceptions and show user-friendly messages.

### SellerController

```text
showSellerMenu(staffSession)
searchFan(keyword)
createFan(fanInput)
createBookingForFan(staffId, fanId, matchId, seatIds)
confirmOfflinePayment(bookingId)
lookupTicket(keyword)
```

Rules:

- Must check staff role `SELLER`.
- Must call `BookingService.createSellerBookingCheckout`.
- Seller checkout va Fan checkout phai dung chung `executeBookingCore`.

### SupportController

```text
showSupportMenu(staffSession)
searchFan(keyword)
searchTicket(keyword)
searchBooking(keyword)
searchPayment(keyword)
assistFailedBooking(bookingId)
assistMissingTicket(bookingId)
assistPaymentIssue(paymentId)
```

Rules:

- Must check staff role `SUPPORT` or `ADMIN`.
- Mutating actions should write audit log.

### AdminController

```text
showAdminMenu(staffSession)
manageStadium()
manageSection()
manageSeat()
manageMatch()
manageFan()
manageStaff()
manageRoles()
manageTicketPricing()
openTicketSales(matchId)
closeTicketSales(matchId)
generateCsvData(config)
validateCsvData()
viewAuditLog()
viewSystemSummary()
```

Rules:

- Must check staff role `ADMIN`.
- Prefer soft delete/status update for records with transaction history.

### SimulationController

```text
showSimulationMenu()
configureSimulation()
runSimulation(config)
viewSimulationResult(simulationId)
compareMechanisms(matchId, scenario)
exportSimulationResult(simulationId)
```

Rules:

- Must call `SimulationService`.
- Must not directly create tickets or edit seats.
- Must not call Fan checkout/payment flow.

## Service Contracts

### BookingService

```text
BookingResult createFanBookingCheckout(BookingRequest request)
BookingResult createSellerBookingCheckout(BookingRequest request)
SeatBookingAttemptResult executeBookingCore(BookingRequest request)
SeatBookingAttemptResult attemptSeatBooking(BookingRequest request)
void validateBookingRequest(BookingRequest request)
Money calculateBookingTotal(String matchId, List<String> seatIds)
void holdSeats(String bookingId, List<String> seatIds, LocalDateTime expiresAt)
void releaseHeldSeats(String bookingId)
void expirePendingBookings(LocalDateTime now)
boolean hasValidTicket(String matchId, String seatId)
```

Responsibilities:

- Validate all business rules.
- Choose synchronization strategy inside booking core.
- Hold/release/confirm seats.
- Create booking, payment, ticket, transaction for checkout flows.
- Maintain invariant `matchId + seatId + VALID ticket` unique.
- Keep simulator on booking core only, without online payment.

### PaymentService

```text
PaymentTransaction createPendingPayment(String bookingId, PaymentMethod method, double amount)
PaymentTransaction processPayment(String paymentId)
PaymentTransaction confirmOfflinePayment(String paymentId, String staffId)
PaymentTransaction expirePayment(String paymentId)
PaymentTransaction refundPayment(String paymentId)
```

Responsibilities:

- Simulate payment result.
- Keep payment status lifecycle valid.

### TicketService

```text
List<Ticket> issueTickets(String bookingId)
Ticket findTicketById(String ticketId)
List<Ticket> findTicketsByFanId(String fanId)
List<Ticket> findTicketsByBookingId(String bookingId)
List<Ticket> findValidTicketsByMatchAndSeat(String matchId, String seatId)
Ticket validateTicketForCheckIn(String ticketCode, String matchId)
Ticket markTicketUsed(String ticketId)
```

Responsibilities:

- Create tickets only after confirmed booking/payment.
- Prevent duplicate valid ticket.
- Reject cancelled/refunded/used tickets during check-in.

### SimulationService

```text
SimulationResult runSimulation(SimulationConfig config)
List<BookingRequest> buildBookingTasks(SimulationConfig config)
int detectDoubleBooking(String matchId)
SimulationComparison compareResults(List<SimulationResult> results)
```

Responsibilities:

- Use `ExecutorService`.
- Use `CountDownLatch`.
- Collect metrics.
- Call `BookingService.executeBookingCore` or `BookingService.attemptSeatBooking`.
- Do not call `createFanBookingCheckout`, because that includes payment.

### NotificationService

```text
void sendBookingConfirmation(String bookingId)
void sendPaymentResult(String paymentId)
void sendTicketNotification(String ticketId)
void sendMatchReminder(String matchId)
```

Responsibilities:

- Simulate notifications through console/log/CSV.
- Keep notification optional so it does not block booking.

### CsvValidationService

```text
CsvValidationReport validateAll()
CsvValidationReport validateFile(String fileName)
```

Responsibilities:

- Detect duplicate ids.
- Detect missing references.
- Detect invalid enum/date/number.

## Repository Contracts

Generic:

```text
interface CsvRepository<T> {
  List<T> findAll();
  Optional<T> findById(String id);
  void save(T entity);
  void update(T entity);
  boolean deleteById(String id);
  void saveAll(List<T> entities);
}
```

Specialized examples:

```text
SeatRepository
  List<Seat> findBySectionId(String sectionId)
  Optional<Seat> findByMatchAndSeat(String matchId, String seatId)
  boolean updateSeatStatusWithVersion(String seatId, SeatStatus status, int expectedVersion)

TicketRepository
  List<Ticket> findByFanId(String fanId)
  List<Ticket> findValidByMatchAndSeat(String matchId, String seatId)

TransactionRepository
  List<BookingTransaction> findByFanId(String fanId)
  List<BookingTransaction> findByBookingId(String bookingId)
```

## Exception Contract

Controller should catch:

- `BookingLimitExceededException`
- `SeatNotFoundException`
- `SeatNotAvailableException`
- `MatchNotFoundException`
- `FanNotFoundException`
- `OptimisticLockException`
- `CsvDataException`
- `FileAccessException`

Service should throw meaningful exceptions. View should only show clean messages, not stack trace.
