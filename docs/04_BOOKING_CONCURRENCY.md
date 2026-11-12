# Booking Holds, Payment and Concurrency

## Lifecycle

```text
Create booking
  -> validate account / walk-in, Match, Seat IDs and prices
  -> save PENDING with createdAt / expiresAt
  -> hold all seats with LOCKED + holdBookingId

Pay before expiresAt
  -> Fan online OR Seller offline
  -> default SYNCHRONIZED strategy commits only the booking's own holds
  -> seats BOOKED, payment SUCCESS, booking CONFIRMED
  -> online issues tickets; Seller offline uses separate Issue ticket

Expiry / cancellation / failed payment
  -> EXPIRED or CANCELLED booking
  -> release only LOCKED seats owned by that booking
```

The deadline is 30 minutes after creation, capped at kick-off when earlier.
The exact persisted timestamp is shown in the booking. At or after that timestamp,
an unpaid booking cannot accept online or offline payment.

## Synchronization and Ownership

BookingService and MatchSeatRepository coordinate through the shared
MatchSeatRepository.class monitor within one JVM. Validate and hold the complete
one-to-four-seat selection before another booking can acquire it.

MatchSeat.holdBookingId identifies the owner of a LOCKED seat. Payment's
booking-aware strategy overload verifies both state and owner before marking
BOOKED. Cancellation/expiry release matches the same owner, so an old cleanup
cannot release a newer booking's hold. Successful booking clears the hold owner.

The default remains SYNCHRONIZED. The compatibility strategy API for AVAILABLE
seats remains internal to the existing contracts. Normal checkout uses the
booking-aware API.

## Expiry Processing

Expiry is checked at startup, booking creation/history/details, payment,
cancellation, seat-map access and administrative seat deactivation. The next
relevant request clears elapsed holds. Payment checks the stored deadline again
before accepting a new transaction. Restarting the application reads the same deadline.

An interrupted release can be retried for an EXPIRED/CANCELLED booking. Owner
checks protect other bookings. An incomplete paid multi-file checkout requires
reconciliation and retains its payment evidence.

## CSV Contracts

Booking writes eight columns and reads legacy six-column rows.

```text
id,fanId,matchId,seatIds,totalAmount,status,createdAt,expiresAt
```

Legacy PENDING without timestamps is treated as expired. Legacy CONFIRMED rows
retain their historical paid state. Walk-in fanId is empty.

MatchSeat writes six columns and reads legacy five-column rows.

```text
id,matchId,seatId,status,version,holdBookingId
```

Transaction retains eleven columns and also reads the older booking-only layout
with its empty extra owner column.

```text
id,bookingId,fanId,matchId,seatIds,mechanism,status,createdAt,completedAt,durationMillis,failureReason
```

Every saved transaction belongs to a booking. Walk-in transaction fanId is empty.
Seat lists use semicolons, timestamp fields use ISO local date-time and transaction
failure reasons are URL-encoded. Demo files have no header.

## Verification and Limits

Checks cover exclusive concurrent holds, all-or-nothing groups, owner-safe release,
exact deadline rejection, restart persistence, failure/cancellation release,
offline payment separate from issue and online retry idempotence.

Synchronization coordinates one JVM. CSV replacement is per file, so interrupted
multi-file changes can need reconciliation. Payment/refund are mocked.
