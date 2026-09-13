# Test Plan

## Test Strategy

Test chia thanh:

- Unit test cho model/service/repository helper.
- Integration test voi CSV test data.
- Manual console test cho flow nguoi dung.
- Concurrency test cho simulator.
- Performance test cho CSV read va simulation.

## Unit Test Checklist

### Model

- Seat status transition hop le.
- Booking total amount tinh dung.
- Ticket unique key `matchId + seatId`.
- Enum parse invalid thi fail ro rang.

### Service

- Booking validate limit 1-4 seats.
- Booking reject duplicate seats.
- Booking reject inactive fan.
- Booking reject closed/cancelled match.
- Booking release seats khi payment failed.
- Booking create tickets khi payment success.
- Booking keeps price snapshot.
- Booking idempotency key prevents duplicate ticket.
- Expire pending booking releases held seats.
- Simulator calls booking core only, not payment checkout.

### Repository

- Load CSV thanh cong.
- Save CSV thanh cong.
- Find by id.
- Search/filter.
- Duplicate id detection.
- Missing reference validation.

## Integration Test Cases

| ID | Scenario | Expected |
|---|---|---|
| IT-001 | Fan register -> login -> book 1 seat | Ticket VALID created |
| IT-002 | Fan book 5 seats | Reject |
| IT-003 | Book same seat twice sequentially | Second booking fails |
| IT-004 | Seller creates fan then books seat | Booking source SELLER |
| IT-005 | Payment failed | Seat released |
| IT-006 | Admin generate data | >= 10,000 rows |
| IT-007 | Validate generated data | No broken references |
| IT-008 | Support search ticket | Ticket detail shown |
| IT-009 | Fan submits same idempotency key twice | Only one booking/ticket set |
| IT-010 | Pending booking expires | Booking EXPIRED, seats AVAILABLE |
| IT-011 | Admin opens/closes ticket sales | Fan can/cannot book accordingly |
| IT-012 | Admin changes price after booking | Old booking keeps old price snapshot |
| IT-013 | Gate Staff checks in valid ticket | Ticket marked USED |
| IT-014 | Gate Staff scans used ticket again | Reject |

## Concurrency Test Cases

| ID | Mechanism | Scenario | Expected |
|---|---|---|---|
| CT-001 | NO_LOCK | 1000 threads -> 1 seat | May produce double booking |
| CT-002 | SYNCHRONIZED | 1000 threads -> 1 seat | Double booking = 0 |
| CT-003 | FILE_LOCK | 1000 threads -> 1 seat | Double booking = 0 |
| CT-004 | OPTIMISTIC | 1000 threads -> 1 seat | Double booking = 0, conflicts > 0 |
| CT-005 | All mechanisms | 1000 threads -> 100 seats | Compare throughput |
| CT-006 | All mechanisms | 1000 threads -> many seats | Low conflict |
| CT-007 | Simulator | Any scenario | Does not call online payment flow |

## Performance Test

| ID | Test | Target |
|---|---|---|
| PT-001 | Read >= 10,000 CSV rows | < 500 ms target, record actual |
| PT-002 | Search seat by id | Acceptable for demo |
| PT-003 | Simulation 1000 threads | Completes without crash |
| PT-004 | Export result | File created and readable |

## Manual Demo Script

1. Generate CSV data.
2. Show row counts.
3. Login/register fan.
4. View match and seat map.
5. Book 1-4 seats successfully.
6. Try booking same seat again and show failure.
7. Login seller, search/create fan, book offline.
8. Login support, search ticket/booking.
9. Login admin, show CRUD/search or system summary.
10. Run simulator for each mechanism.
11. Show result table and double booking comparison.

## Acceptance Evidence

Can chup man hinh/log cho:

- Dataset row count.
- Successful booking ticket.
- Failed duplicate booking.
- Simulator result `NO_LOCK`.
- Simulator result safe mechanisms.
- Throughput chart/table.
- Class diagram.
- Flowcharts.

## Bug Severity Guide

| Severity | Definition |
|---|---|
| Critical | Double booking, data corruption, app crash in core flow |
| High | Booking/payment/ticket status wrong |
| Medium | Search/filter/report wrong |
| Low | Text/menu/format issue |
