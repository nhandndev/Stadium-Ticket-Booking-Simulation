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
| status | PhysicalSeatStatus | ACTIVE, MAINTENANCE, INACTIVE |

`Seat` chi mo ta ghe vat ly. Trang thai ban ve khong duoc dat truc tiep tren `Seat`, vi cung mot ghe co the ban cho nhieu tran khac nhau.

### MatchSeat

| Field | Type | Notes |
|---|---|---|
| matchSeatId | String | Unique |
| matchId | String | FK Match |
| seatId | String | FK Seat |
| status | MatchSeatStatus | AVAILABLE, LOCKED, BOOKED |
| lockedByBookingId | String | optional |
| lockedAt | LocalDateTime | optional |
| lockExpiresAt | LocalDateTime | optional |
| version | int | Required for optimistic lock |

Unique business constraint: `matchId + seatId` phai unique. Moi lifecycle `AVAILABLE -> LOCKED -> BOOKED` dien ra tren `MatchSeat`.

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

### User

`User` la abstract parent cua `Fan` va `Staff`. Khi luu CSV, cac inherited fields co the duoc flatten vao `fans.csv` va `staff.csv` de mapper don gian.

| Field | Type | Notes |
|---|---|---|
| userId | String | Unique |
| fullName | String | Required |
| email | String | Unique if provided |
| phone | String | Unique if provided |
| passwordHash | String | Demo only, khong luu raw password |
| status | UserStatus | ACTIVE, INACTIVE, BLOCKED |
| createdAt | LocalDateTime | Required |
| updatedAt | LocalDateTime | Required |

### Fan

`Fan extends User`.

| Field | Type | Notes |
|---|---|---|
| fanCode | String | Unique customer code |

### Staff

`Staff extends User`.

Trong UML, `Staff` la abstract parent cua `Seller`, `SupportStaff`, `Administrator` va `GateStaff`. Khi luu CSV, tat ca van dung chung `staff.csv`; cot `role` cho biet loai Staff de khoi phuc dung subclass khi doc file.

| Field | Type | Notes |
|---|---|---|
| staffCode | String | Unique employee code |
| username | String | Unique |
| role | StaffRole | SELLER, SUPPORT, ADMINISTRATOR, GATE_STAFF |

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
| matchSeatId | String | FK MatchSeat |
| price | double | Snapshot price |
| status | BookingItemStatus | LOCKED, CONFIRMED, RELEASED |

### Ticket

| Field | Type | Notes |
|---|---|---|
| ticketId | String | Unique |
| bookingId | String | FK Booking |
| fanId | String | FK Fan |
| matchId | String | FK Match |
| matchSeatId | String | FK MatchSeat |
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
- `match_seats.csv`
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
- MatchSeat.matchId ton tai trong Match.
- MatchSeat.seatId ton tai trong Seat va phai thuoc stadium cua Match.
- MatchSeat.matchId + seatId phai unique.
- Booking.fanId ton tai trong Fan.
- Booking.matchId ton tai trong Match.
- Ticket.bookingId ton tai trong Booking.
- Ticket.matchId + matchSeatId khong duplicate voi ticket valid khac.

## Enum Goi Y

```text
PhysicalSeatStatus = ACTIVE, MAINTENANCE, INACTIVE
MatchSeatStatus = AVAILABLE, LOCKED, BOOKED
MatchStatus = SCHEDULED, OPEN_FOR_SALE, SOLD_OUT, CANCELLED, COMPLETED
SaleStatus = NOT_OPEN, ON_SALE, CLOSED, SOLD_OUT
BookingStatus = PENDING, CONFIRMED, FAILED, CANCELLED, EXPIRED
PaymentStatus = PENDING, SUCCESS, FAILED, CANCELLED, EXPIRED, REFUNDED
TicketStatus = VALID, USED, CANCELLED, REFUNDED
StaffRole = SELLER, SUPPORT, ADMINISTRATOR, GATE_STAFF
UserStatus = ACTIVE, INACTIVE, BLOCKED
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
