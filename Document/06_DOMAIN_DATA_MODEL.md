# Domain Data Model

## Core Entities

### Stadium

| Field | Type | Notes |
|---|---|---|
| stadiumId | String | Unique |
| name | String | Required |
| address | String | Optional |
| city | String | Optional |
| capacity | int | >= 0 |
| status | StadiumStatus | ACTIVE, INACTIVE |

### Section

| Field | Type | Notes |
|---|---|---|
| sectionId | String | Unique |
| stadiumId | String | FK Stadium |
| name | String | A/B/C/VIP |
| type | SectionType | STANDARD, VIP, AWAY, FAMILY |
| basePrice | double | >= 0 |
| status | SectionStatus | ACTIVE, INACTIVE |

### Seat

| Field | Type | Notes |
|---|---|---|
| seatId | String | Unique physical seat |
| stadiumId | String | FK Stadium |
| sectionId | String | FK Section |
| rowLabel | String | A, B, C |
| seatNumber | int | Required |
| seatType | SeatType | STANDARD, VIP, ACCESSIBLE |
| status | SeatStatus | AVAILABLE, LOCKED, BOOKED, MAINTENANCE |
| lockedBy | String | bookingId/sessionId optional |
| lockedAt | LocalDateTime | optional |
| version | int | Required for optimistic lock |

Important: trong he thong thuc te, status cua ghe nen theo match. Neu CSV don gian chi co `seats.csv`, can dam bao ticket uniqueness bang `matchId + seatId`.

Nen them file `match_seats.csv` neu muon chuan hon.

### Match

| Field | Type | Notes |
|---|---|---|
| matchId | String | Unique |
| stadiumId | String | FK Stadium |
| homeTeam | String | Required |
| awayTeam | String | Required |
| startTime | LocalDateTime | Required |
| saleStartTime | LocalDateTime | Optional |
| saleEndTime | LocalDateTime | Optional |
| status | MatchStatus | SCHEDULED, OPEN_FOR_SALE, SOLD_OUT, CANCELLED, COMPLETED |
| saleStatus | SaleStatus | NOT_OPEN, ON_SALE, CLOSED, SOLD_OUT |
| basePriceMultiplier | double | Optional, default 1.0 |

### Fan

| Field | Type | Notes |
|---|---|---|
| fanId | String | Unique |
| fullName | String | Required |
| email | String | Unique if provided |
| phone | String | Unique if provided |
| password | String | Demo only |
| status | FanStatus | ACTIVE, INACTIVE, BLOCKED |
| createdAt | LocalDateTime | Required |

### Staff

| Field | Type | Notes |
|---|---|---|
| staffId | String | Unique |
| fullName | String | Required |
| username | String | Unique |
| password | String | Demo only |
| role | StaffRole | SELLER, SUPPORT, ADMIN |
| status | StaffStatus | ACTIVE, INACTIVE |

### Booking

| Field | Type | Notes |
|---|---|---|
| bookingId | String | Unique |
| fanId | String | FK Fan |
| staffId | String | optional, seller booking |
| matchId | String | FK Match |
| source | BookingSource | FAN, SELLER, SIMULATOR |
| status | BookingStatus | PENDING, CONFIRMED, FAILED, CANCELLED, EXPIRED |
| totalAmount | double | >= 0 |
| createdAt | LocalDateTime | Required |
| confirmedAt | LocalDateTime | optional |
| expiresAt | LocalDateTime | required for PENDING hold |
| idempotencyKey | String | optional |

### BookingItem

| Field | Type | Notes |
|---|---|---|
| bookingItemId | String | Unique |
| bookingId | String | FK Booking |
| matchId | String | FK Match |
| seatId | String | FK Seat |
| price | double | Snapshot price |
| status | BookingItemStatus | LOCKED, CONFIRMED, RELEASED |

### Ticket

| Field | Type | Notes |
|---|---|---|
| ticketId | String | Unique |
| bookingId | String | FK Booking |
| fanId | String | FK Fan |
| matchId | String | FK Match |
| seatId | String | FK Seat |
| qrCode | String | simulated |
| status | TicketStatus | VALID, USED, CANCELLED, REFUNDED |
| issuedAt | LocalDateTime | Required |
| usedAt | LocalDateTime | optional for check-in |

Unique business constraint:

```text
matchId + seatId + status=VALID must be unique
```

### BookingTransaction

| Field | Type | Notes |
|---|---|---|
| transactionId | String | Unique |
| bookingId | String | optional if failed early |
| fanId | String | Required |
| staffId | String | optional |
| matchId | String | Required |
| seatIds | String | pipe-separated or semicolon-separated |
| status | TransactionStatus | SUCCESS, FAILED, CONFLICT |
| mechanism | SyncMechanism | NO_LOCK, SYNCHRONIZED, FILE_LOCK, OPTIMISTIC |
| failureReason | String | optional |
| createdAt | LocalDateTime | Required |

### PaymentTransaction

| Field | Type | Notes |
|---|---|---|
| paymentId | String | Unique |
| bookingId | String | FK Booking |
| method | PaymentMethod | ONLINE, OFFLINE, SIMULATED |
| amount | double | Required |
| status | PaymentStatus | PENDING, SUCCESS, FAILED, CANCELLED, EXPIRED, REFUNDED |
| providerRef | String | simulated |
| createdAt | LocalDateTime | Required |
| updatedAt | LocalDateTime | Required |

### SimulationResult

| Field | Type | Notes |
|---|---|---|
| simulationId | String | Unique |
| mechanism | SyncMechanism | Required |
| scenario | ContentionScenario | HIGH, MEDIUM, LOW |
| threadCount | int | Required |
| targetMatchId | String | Required |
| targetSeatCount | int | Required |
| totalAttempts | int | Required |
| successfulBookings | int | Required |
| failedBookings | int | Required |
| conflictCount | int | Required |
| doubleBookingCount | int | Required |
| executionTimeMs | long | Required |
| throughput | double | success per second |
| doubleBookingRate | double | percentage |
| createdAt | LocalDateTime | Required |

### AuditLog

| Field | Type | Notes |
|---|---|---|
| auditId | String | Unique |
| actorType | String | FAN, STAFF, SYSTEM |
| actorId | String | Required |
| action | String | Required |
| entityType | String | Required |
| entityId | String | Required |
| beforeValue | String | optional |
| afterValue | String | optional |
| createdAt | LocalDateTime | Required |

### TicketPricing

| Field | Type | Notes |
|---|---|---|
| pricingId | String | Unique |
| matchId | String | FK Match |
| sectionId | String | FK Section |
| seatType | SeatType | Optional |
| price | double | Required |
| status | PricingStatus | ACTIVE, INACTIVE |
| effectiveFrom | LocalDateTime | Optional |
| effectiveTo | LocalDateTime | Optional |

Booking item phai luu price snapshot de sau nay admin doi gia khong lam doi lich su booking cu.

### Notification

| Field | Type | Notes |
|---|---|---|
| notificationId | String | Unique |
| recipientType | String | FAN, STAFF |
| recipientId | String | Required |
| channel | NotificationChannel | CONSOLE, EMAIL_SIMULATED, SMS_SIMULATED |
| type | NotificationType | BOOKING_CONFIRMATION, PAYMENT_RESULT, TICKET_ISSUED, MATCH_REMINDER |
| status | NotificationStatus | PENDING, SENT, FAILED |
| createdAt | LocalDateTime | Required |
| sentAt | LocalDateTime | Optional |

## CSV Files De Xuat

Bat buoc/nen co:

- `stadiums.csv`
- `sections.csv`
- `seats.csv`
- `fans.csv`
- `staff.csv`
- `matches.csv`
- `bookings.csv`
- `booking_items.csv`
- `tickets.csv`
- `transactions.csv`
- `payment_transactions.csv`
- `simulation_results.csv`
- `audit_logs.csv`
- `ticket_pricing.csv`
- `notifications.csv`

Neu muon giu sat README toi thieu:

- `stadiums.csv`
- `sections.csv`
- `seats.csv`
- `fans.csv`
- `matches.csv`
- `tickets.csv`
- `transactions.csv`

## Referential Integrity

Can validate:

- Section.stadiumId ton tai trong Stadium.
- Seat.sectionId ton tai trong Section.
- Seat.stadiumId khop voi Section.stadiumId.
- Match.stadiumId ton tai trong Stadium.
- Booking.fanId ton tai trong Fan.
- Booking.matchId ton tai trong Match.
- Ticket.bookingId ton tai trong Booking.
- Ticket.matchId + seatId khong duplicate voi ticket valid khac.

## Enum Goi Y

```text
SeatStatus = AVAILABLE, LOCKED, BOOKED, MAINTENANCE
MatchStatus = SCHEDULED, OPEN_FOR_SALE, SOLD_OUT, CANCELLED, COMPLETED
SaleStatus = NOT_OPEN, ON_SALE, CLOSED, SOLD_OUT
BookingStatus = PENDING, CONFIRMED, FAILED, CANCELLED, EXPIRED
PaymentStatus = PENDING, SUCCESS, FAILED, CANCELLED, EXPIRED, REFUNDED
TicketStatus = VALID, USED, CANCELLED, REFUNDED
StaffRole = SELLER, SUPPORT, ADMIN
SyncMechanism = NO_LOCK, SYNCHRONIZED, FILE_LOCK, OPTIMISTIC
ContentionScenario = HIGH, MEDIUM, LOW
PricingStatus = ACTIVE, INACTIVE
NotificationStatus = PENDING, SENT, FAILED
```

## Data Generation Target

De dat >= 10,000 rows:

| File | So dong goi y |
|---|---:|
| stadiums.csv | 3 |
| sections.csv | 60 |
| seats.csv | 10,000+ |
| fans.csv | 1,000 |
| staff.csv | 20 |
| matches.csv | 50 |
| tickets.csv | generated during booking |
| transactions.csv | generated during booking/simulation |
