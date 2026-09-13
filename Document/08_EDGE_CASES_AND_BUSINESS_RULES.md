# Edge Cases And Business Rules

## Business Rules

| ID | Rule | Severity |
|---|---|---|
| BR-001 | Fan chi duoc dat 1-4 ghe trong mot transaction | Critical |
| BR-002 | Mot `matchId + seatId` chi co toi da 1 ticket `VALID` | Critical |
| BR-003 | Booking phai re-check availability luc commit | Critical |
| BR-004 | Payment fail/cancel/expire thi release locked seats | Critical |
| BR-005 | Seller booking va Fan booking dung chung BookingService | Critical |
| BR-006 | Simulator khong duoc bypass booking core | Critical |
| BR-007 | Seat `MAINTENANCE` khong duoc ban | High |
| BR-008 | Match `CANCELLED` hoac het sale window khong duoc ban | High |
| BR-009 | Admin khong hard delete data da co transaction | High |
| BR-010 | CSV invalid phai bi reject hoac report ro rang | High |
| BR-011 | Simulator khong duoc goi customer checkout/payment flow | Critical |
| BR-012 | Repeated booking request voi cung idempotency key khong duoc tao duplicate ticket | Critical |
| BR-013 | Pending booking qua han phai `EXPIRED` va release held seats | Critical |
| BR-014 | Fan chi dat duoc ve khi ticket sale dang `ON_SALE` | High |
| BR-015 | Booking item phai luu price snapshot | High |
| BR-016 | Ticket da `USED` khong duoc check-in lan hai | High |

## Booking Edge Cases

| Case | Expected Handling |
|---|---|
| Fan chon 0 ghe | Reject |
| Fan chon > 4 ghe | Reject |
| Duplicate seat trong request | Reject |
| Seat khong ton tai | Throw `SeatNotFoundException` |
| Match khong ton tai | Throw `MatchNotFoundException` |
| Fan khong ton tai | Throw `FanNotFoundException` |
| Fan bi blocked | Reject |
| Seat da booked | Throw `SeatNotAvailableException` |
| Seat available luc xem nhung booked luc confirm | Reject khi re-check |
| Partial lock thanh cong, seat sau fail | Release cac seat da lock |
| Payment failed sau khi lock | Release lock, booking failed |
| Fan thoat app sau khi hold ghe | Expire pending booking va release seats |
| Payment success nhung ticket write fail | Mark for reconciliation/support |
| Ticket tao thanh cong nhung transaction write fail | Data integrity report can bat |
| Submit booking lap lai | Idempotency key tra ve booking cu hoac reject duplicate |
| Gia ve thay doi sau khi booking confirmed | Ticket/booking cu giu price snapshot |

## Concurrency Edge Cases

| Case | Expected Handling |
|---|---|
| 1000 threads dat 1 ghe | Safe strategy chi co 1 success |
| Thread exception | Ghi failed attempt, simulator tiep tuc |
| Optimistic version conflict | Count conflict, khong tao ticket |
| File lock timeout | Fail gracefully, release resource |
| Deadlock/lock khong release | Dung `finally` de release |
| Double booking found | Report ro `matchId`, `seatId`, ticketIds |
| No lock khong tao race trong lan demo | Chay lai/high contention/add artificial delay trong unsafe baseline |
| Simulator task goi payment flow | Sai design, phai doi sang `Execute Booking Core` |

## CSV/Data Edge Cases

| Case | Expected Handling |
|---|---|
| Duplicate id | Validation error |
| Missing required field | Validation error |
| Invalid enum | Validation error |
| Number/date parse fail | Validation error |
| Seat references missing section | Validation error |
| Match references missing stadium | Validation error |
| Ticket references missing booking | Validation error |
| CSV file missing | Tao file mau hoac bao loi than thien |
| File write fail | Throw `FileAccessException` |
| Empty dataset | Hien empty state, khong crash |

## Staff/Admin Edge Cases

| Case | Expected Handling |
|---|---|
| Seller tao fan trung phone/email | Reject duplicate |
| Seller booking cho fan inactive | Reject |
| Support xem ticket fan khac | Allow neu role support, audit lookup neu can |
| Admin xoa match da co booking | Reject hard delete, suggest status cancelled |
| Admin sua stadium cua match da ban ve | Reject hoac canh bao high risk |
| Admin sua seat booked | Reject neu anh huong ticket valid |
| Admin mo ban ve khi match cancelled | Reject |
| Admin dong ban ve khi dang co pending holds | Cho phep close sale nhung pending holds van expire/confirm theo policy |
| Admin doi gia sau khi da co booking | Chi anh huong booking moi |

## Gate/Check-in Edge Cases

| Case | Expected Handling |
|---|---|
| Ticket khong ton tai | Reject |
| Ticket khong dung match | Reject |
| Ticket `CANCELLED` hoac `REFUNDED` | Reject |
| Ticket da `USED` | Reject duplicate scan |
| Ticket `VALID` dung match | Mark `USED` va record audit/log |

## Payment/Ticket Reconciliation Cases

Can co man hinh/report hoac service scan:

| Inconsistency | Suggested Fix |
|---|---|
| Payment SUCCESS, Booking PENDING | Confirm booking neu seats con hop le, hoac refund simulation |
| Payment SUCCESS, Ticket missing | Create missing ticket neu invariant chua vi pham |
| Booking CONFIRMED, Ticket missing | Create ticket hoac mark booking inconsistent |
| Ticket VALID, Seat AVAILABLE | Mark seat BOOKED neu ticket hop le |
| Seat BOOKED, no valid ticket | Release seat hoac flag manual review |
| Duplicate valid tickets | Flag critical, cancel/refund duplicate theo policy |

## User Input Edge Cases

- Nhap chu khi can so.
- Nhap ngay sai format.
- Nhap id khong ton tai.
- Bam back/cancel giua flow.
- Menu option ngoai range.
- Chuoi rong hoac qua dai.

Controller/View can xu ly input loop sach se, khong de crash.
