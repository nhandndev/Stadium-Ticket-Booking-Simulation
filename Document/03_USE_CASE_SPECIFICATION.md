# Use Case Specification

## Actor Generalization

```text
Guest
  ^
  |
Fan

Staff
  ^
  |
  +-- Seller
  +-- Support Staff
  +-- Administrator

Payment Service
Notification Service
Simulator Operator
Gate Staff
```

System boundary: **Stadium Ticket Booking & Concurrency Simulation System**

## Use Case Map Tong Quan

| Actor | Use cases chinh |
|---|---|
| Guest | View Match List, Search Match, View Match Detail, View Stadium, View Section, View Seat Map, View Seat Availability, Register, Login |
| Fan | Logout, Manage Profile, Select Seats, Review Booking, Create Booking, Make Online Payment, View My Tickets, View Ticket Detail, View Booking History, Request Cancellation, Request Refund |
| Seller | Staff Login, Logout, Search Fan, Create Fan, Select Fan, Create Booking for Fan, Accept Offline Payment, Confirm Offline Payment, Issue Ticket, Search Ticket |
| Support Staff | Staff Login, Logout, Search Fan/Ticket/Booking/Payment, Check Status, Assist Failed Booking, Assist Missing Ticket, Assist Payment Issue, Escalate Data Inconsistency |
| Administrator | Staff Login, Logout, Manage Stadium/Section/Seat/Match/Fan, Manage Staff, Manage Roles, Manage Ticket Pricing, Open Ticket Sales, Close Ticket Sales, View Audit Log, Generate CSV Data, Validate CSV Data, View System Summary |
| Simulator Operator | Configure Simulation, Select Mechanism, Run Simulation, View Result, Compare Mechanisms, Export Result |
| Payment Service | Process Online Payment Result |
| Notification Service | Send Booking Confirmation, Send Payment Result, Send Ticket Notification. Enterprise extension P2/P3 |
| Gate Staff | Validate Ticket, Check-in Ticket. Enterprise extension P2 |

System/internal use cases:

- Validate Booking.
- Calculate Booking Total.
- Check Seat Availability.
- Hold Seats.
- Release Seats.
- Expire Seat Hold.
- Expire Pending Booking.
- Execute Booking Core.
- Apply Synchronization Strategy.
- Update Seat State.
- Create Pending Booking.
- Confirm Booking.
- Create Ticket.
- Record Transaction.

## UC-01 View Match And Seat Availability

Primary actor: Guest, Fan, Seller

Preconditions:

- Data CSV da load thanh cong.

Main flow:

1. Actor mo danh sach match.
2. He thong hien match dang ban ve.
3. Actor chon match.
4. He thong hien chi tiet stadium/section.
5. Actor chon section.
6. He thong hien seat map va status.

Alternative flows:

- A1: Khong co match phu hop -> hien thong bao rong.
- A2: CSV loi -> hien thong bao va log loi.
- A3: Ghe vua xem available nhung sau do nguoi khac dat -> booking flow phai re-check.

Postconditions:

- Khong thay doi du lieu.

## UC-02 Register

Primary actor: Guest

Main flow:

1. Guest nhap thong tin dang ky.
2. He thong validate required fields.
3. He thong kiem tra trung email/phone.
4. He thong tao fan moi status `ACTIVE`.
5. He thong ghi fan vao CSV.

Alternative flows:

- A1: Thieu field bat buoc -> yeu cau nhap lai.
- A2: Email/phone trung -> tu choi tao moi.
- A3: Ghi CSV fail -> thong bao loi va khong login tu dong.

Postconditions:

- Fan account moi duoc tao neu thanh cong.

## UC-03 Login

Primary actor: Guest, Staff

Main flow:

1. Actor nhap credential.
2. He thong validate credential.
3. He thong tao session theo role.
4. He thong hien menu phu hop role.

Alternative flows:

- A1: Sai credential -> tu choi.
- A2: Account inactive/blocked -> tu choi.

Postconditions:

- Actor duoc authenticated neu thanh cong.

## UC-09 Logout

Primary actor: Fan, Seller, Support Staff, Administrator

Main flow:

1. Actor chon Logout.
2. He thong destroy session hien tai.
3. He thong quay ve Public/Login Menu.

Alternative flows:

- A1: Session da het han -> he thong van quay ve Public/Login Menu.

Postconditions:

- Actor khong con authenticated trong session hien tai.

## UC-10 Manage Fan Profile

Primary actor: Fan

Preconditions:

- Fan da login.

Main flow:

1. Fan mo profile.
2. He thong hien thong tin ca nhan.
3. Fan cap nhat ten, phone, email hoac password.
4. He thong validate du lieu va duplicate email/phone.
5. He thong luu thay doi.

Alternative flows:

- A1: Email/phone trung -> reject.
- A2: Password cu khong dung -> reject change password.

Postconditions:

- Profile duoc cap nhat neu hop le.

## UC-04 Fan Create Booking

Primary actor: Fan

Supporting actor: Payment Service

Preconditions:

- Fan da login.
- Match con mo ban ve.
- Fan chon 1-4 ghe.

Included use cases:

- Validate Booking.
- Calculate Booking Total.
- Execute Booking Core.
- Hold Seats.
- Make Online Payment.
- Confirm Booking.

Main flow:

1. Fan chon match, section va 1-4 seats.
2. Fan review booking.
3. He thong validate booking request.
4. He thong tinh tong tien booking.
5. He thong goi `Execute Booking Core` de re-check availability va ap dung synchronization.
6. He thong hold seats voi expiry window, vi du 5 phut.
7. He thong tao pending booking va payment transaction status `PENDING`.
8. Fan thuc hien online payment.
9. Payment Service tra `SUCCESS`.
10. He thong confirm booking.
11. He thong mark seats `BOOKED`.
12. He thong tao tickets status `VALID`.
13. He thong record transaction success.
14. He thong co the gui booking/ticket notification.
15. He thong hien booking confirmation.

Alternative flows:

- A1: Chon 0 ghe hoac > 4 ghe -> fail `BookingLimitExceededException`.
- A2: Co ghe khong ton tai -> fail `SeatNotFoundException`.
- A3: Co ghe khong available khi re-check -> fail `SeatNotAvailableException`.
- A4: Payment failed/cancelled/expired -> release lock va record failed transaction.
- A5: Optimistic version conflict -> fail `OptimisticLockException`, release lock neu can.
- A6: Ghi ticket thanh cong nhung transaction fail -> reconciliation can phat hien va sua.
- A7: Fan thoat app/qua payment window -> `Expire Pending Booking`, release seats.
- A8: Fan submit request lap lai -> xu ly bang `idempotencyKey`, khong tao duplicate booking/ticket.

Postconditions:

- Thanh cong: moi ghe co mot ticket valid.
- That bai truoc confirm: ghe tro ve available.
- Pending booking qua han: booking `EXPIRED`, seat hold duoc release.
- Khong duoc tao duplicate valid ticket cho cung match/seat.

## UC-05 Seller Create Booking For Fan

Primary actor: Seller

Preconditions:

- Seller da staff login.
- Fan duoc select hoac create.

Included use cases:

- Search Fan.
- Select Fan.
- Select Seats for Customer.
- Validate Booking.
- Calculate Booking Total.
- Execute Booking Core.
- Hold Seats.
- Accept Offline Payment.
- Confirm Offline Payment.
- Confirm Booking.
- Issue Ticket.

Extend:

- Create Fan extends Search Fan with condition `[Fan not found]`.

Main flow:

1. Seller search fan.
2. Seller select fan.
3. Seller chon match/section/seat theo yeu cau khach.
4. He thong validate, calculate total va execute booking core.
5. Seller xac nhan da nhan offline payment.
6. He thong confirm booking.
7. He thong create ticket va record transaction.
8. Seller issue ticket cho khach bang cach hien/in/thong bao ma ve.
9. He thong record seller audit log.

Alternative flows:

- A1: Fan not found -> Seller tao fan moi.
- A2: Offline payment khong thanh cong -> release seats.
- A3: Ghe vua bi nguoi khac dat -> fail va yeu cau chon ghe khac.

Postconditions:

- Booking cua fan duoc tao voi source `SELLER`.
- `Create Ticket` la system action; `Issue Ticket` la operation cua Seller sau khi ticket da duoc tao.

## UC-06 Support Assist Issue

Primary actor: Support Staff

Use cases:

- Assist Failed Booking.
- Assist Missing Ticket.
- Assist Payment Issue.
- Assist Seat Issue.

Main flow:

1. Support search fan/ticket/booking/payment.
2. He thong hien thong tin lien quan.
3. Support check booking/payment/seat status.
4. Support xac dinh loai su co.
5. Support ghi note/audit va dua huong xu ly.

Edge cases can xu ly trong spec, khong can ve oval rieng:

- Payment `SUCCESS` nhung ticket missing.
- Ticket exists nhung seat van `AVAILABLE`.
- Seat `BOOKED` nhung khong co ticket.
- Duplicate ticket.
- Booking failed nhung ghe van `LOCKED`.

## UC-07 Administrator Manage Data

Primary actor: Administrator

Main flow:

1. Admin chon entity can quan ly.
2. He thong hien menu create/view/update/delete/search.
3. Admin nhap thao tac.
4. He thong validate rule va reference.
5. He thong ghi CSV.
6. He thong record audit log.

Business notes:

- Khong hard delete entity da co transaction.
- Match da co booking khong nen sua stadium/seat map.
- Seat da booked khong duoc xoa vat ly.
- Neu he thong co Seller/Support/Admin thi nen co `Manage Staff` va `Manage Roles`.
- Nen co `Open Ticket Sales` va `Close Ticket Sales` de dieu khien khi nao fan duoc booking.
- Nen co `Manage Ticket Pricing` de set section/match price, phuc vu `Calculate Booking Total`.

## UC-08 Run Concurrency Simulation

Primary actor: Simulator Operator

Preconditions:

- Data du fan/match/seat.
- Booking engine san sang.

Included use cases:

- Configure Simulation.
- Configure Thread Count.
- Select Target Match.
- Select Target Seats.
- Select Synchronization Mechanism.
- Create Fan Threads.
- Synchronize Thread Start.
- Execute Concurrent Booking.
- Detect Double Booking.
- Calculate Metrics.
- Collect Simulation Results.

Main flow:

1. Operator chon mechanism: `NO_LOCK`, `SYNCHRONIZED`, `FILE_LOCK`, `OPTIMISTIC`.
2. Operator nhap thread count.
3. Operator chon contention scenario.
4. He thong tao booking tasks.
5. He thong dung `CountDownLatch` de start dong thoi.
6. Moi task goi `Execute Booking Core` thong qua `BookingService.attemptSeatBooking` hoac method core tuong duong.
7. He thong thu thap success/failure/conflict.
8. He thong scan tickets de detect double booking.
9. He thong tinh throughput va double booking rate.
10. He thong hien/export result.

Alternative flows:

- A1: Thread count khong hop le -> yeu cau nhap lai.
- A2: Khong du seat target -> yeu cau chon scenario khac.
- A3: Task gap exception -> ghi failed attempt, khong crash toan simulator.

Postconditions:

- Simulation result duoc luu vao CSV/log.
- Co bang chung so sanh mechanism.
- Simulator khong chay online payment flow; no chi mo phong attempt dat ghe de do double booking/concurrency.

## UC-11 Execute Booking Core

Primary actor: System/Internal

Used by:

- Fan Create Booking.
- Seller Create Booking For Fan.
- Execute Concurrent Booking trong Simulator.

Included use cases:

- Check Seat Availability.
- Apply Synchronization Strategy.
- Update Seat State.
- Reserve/commit seat state.

Main flow:

1. System nhan request da validate.
2. System ap dung synchronization strategy duoc chon.
3. System re-check `matchId + seatId`.
4. System kiem tra chua co valid ticket.
5. System update seat state an toan.
6. System tra ket qua success/conflict/failure.

Business note:

- `Execute Booking Core` khong tu goi online payment.
- `Hold Seats` la buoc rieng cua checkout flow, dung cho Fan/Seller truoc payment confirmation.
- Simulator co the bo qua hold/payment va chi chay contention core.
- Simulator chi can core attempt de do tranh chap ghe.
- Boundary chot: `Execute Booking Core` = check availability + synchronize + reserve/commit seat state; khong xu ly payment, notification hoac issue ticket.

## UC-12 Expire Pending Booking

Primary actor: System/Internal

Main flow:

1. System scan booking `PENDING`.
2. System tim booking co `expiresAt < now`.
3. System mark booking `EXPIRED`.
4. System release seats tu `LOCKED` ve `AVAILABLE`.
5. System record transaction/audit neu can.

Postconditions:

- Seat khong bi locked mai khi user thoat app hoac payment timeout.

## UC-13 Validate Ticket / Check-in Ticket

Primary actor: Gate Staff

Priority: P2 enterprise-like extension.

Main flow:

1. Gate Staff nhap/scan ticket code.
2. He thong validate ticket ton tai.
3. He thong check match, ticket status va ticket chua used.
4. He thong mark ticket `USED`.

Alternative flows:

- A1: Ticket khong ton tai -> reject.
- A2: Ticket da used -> reject lan scan thu hai.
- A3: Ticket cancelled/refunded -> reject.

## Include/Extend Can Ve Tren Diagram

```text
Create Booking
  <<include>> Validate Booking
  <<include>> Calculate Booking Total
  <<include>> Execute Booking Core
  <<include>> Hold Seats
  <<include>> Make Online Payment
  <<include>> Confirm Booking

Execute Booking Core
  <<include>> Check Seat Availability
  <<include>> Apply Synchronization Strategy
  <<include>> Update Seat State

Confirm Booking
  <<include>> Create Ticket
  <<include>> Record Transaction

Create Booking for Fan
  <<include>> Select Fan
  <<include>> Select Seats for Customer
  <<include>> Validate Booking
  <<include>> Calculate Booking Total
  <<include>> Execute Booking Core
  <<include>> Hold Seats
  <<include>> Accept Offline Payment
  <<include>> Confirm Booking

Issue Ticket
  happens after Create Ticket

Create Fan
  <<extend>> Search Fan
  condition: [Fan not found]

Run Simulation
  <<include>> Create Fan Threads
  <<include>> Synchronize Thread Start
  <<include>> Execute Concurrent Booking
  <<include>> Detect Double Booking
  <<include>> Calculate Metrics
  <<include>> Collect Simulation Results

Execute Concurrent Booking
  <<include>> Execute Booking Core

Export Simulation Result
  <<extend>> View Simulation Result
```
