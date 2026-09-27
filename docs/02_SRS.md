# Software Requirements Specification

## 1. Introduction

### 1.1 Purpose

Tai lieu SRS dinh nghia yeu cau cho he thong Stadium Ticket Booking & Concurrency Simulation System. SRS nay dung lam co so de thiet ke class, package, use case, test case, report va demo.

### 1.2 Product Perspective

He thong la ung dung Java console theo MVC, luu du lieu bang CSV. Trong thuc te, day la mot he thong dat ve san van dong; trong pham vi du an, no la simulation tap trung vao concurrency va double booking prevention.

### 1.3 Product Functions

- Quan ly stadium, section, seat, match, fan.
- Cho guest xem lich tran va ghe.
- Cho fan dat ve.
- Cho seller dat ve ho khach.
- Cho support tra cuu va ho tro su co.
- Cho admin quan ly du lieu.
- Chay concurrency simulation voi nhieu co che dong bo.

## 2. Overall Description

### 2.1 User Classes

- Guest: chi xem va dang ky/dang nhap.
- Fan: dat ve va xem ve cua minh.
- Seller: ban ve tai quay.
- Support Staff: xu ly su co.
- Administrator: quan ly du lieu he thong.
- Simulator Operator: thuc hien test concurrency.

### 2.2 Operating Environment

- Java console application.
- CSV files trong folder `data/`.
- Khong yeu cau database.
- Co the chay tren IDE hoac command line.

### 2.3 Design Constraints

- Phai theo MVC.
- View khong chua business logic.
- Controller khong doc/ghi CSV truc tiep.
- Repository phu trach CSV persistence.
- Booking logic nam trong Service/Domain, dung chung cho Fan, Seller va Simulator.
- Tach ro customer checkout flow va booking core:
  - Fan/Seller checkout co payment.
  - Simulator chi goi booking core/seat booking attempt, khong chay online payment flow.
- Simulator phai dung `ExecutorService` va `CountDownLatch`.

## 3. Functional Requirements

### FR-01 Guest Browsing

Guest co the:

- Xem danh sach match.
- Search match theo keyword, date, stadium.
- Xem chi tiet match.
- Xem stadium, section, seat map.
- Xem tinh trang ghe.

Acceptance criteria:

- Neu CSV rong hoac loi, he thong hien thong bao an toan.
- Seat availability chi la thong tin tai thoi diem xem, khong dam bao ghe van con khi dat.

### FR-02 Account Registration, Login, Logout And Profile

Guest co the dang ky fan account va login. Fan/Staff co the logout. Fan co the quan ly profile co ban.

Thong tin toi thieu:

- fanId
- fullName
- phone/email
- passwordHash hoac password demo
- status

Acceptance criteria:

- Khong cho trung email/phone neu dung lam username.
- Fan bi inactive/blocked khong duoc dat ve.
- Logout phai destroy session va quay ve public/login menu.
- Fan co the view/update name, phone, email va change password.

### FR-03 Fan Booking

Fan co the tao booking voi 1-4 ghe cho mot match.

Luon booking:

1. Login.
2. Chon match.
3. Chon section.
4. Xem seat map.
5. Chon 1-4 ghe.
6. Validate request.
7. Re-check availability.
8. Calculate booking total.
9. Execute booking core de ap dung synchronization va re-check seat.
10. Hold seats voi expiry window.
11. Make online payment.
12. Confirm booking.
13. Mark booked.
14. Create ticket.
15. Record transaction.

Acceptance criteria:

- Vuot 4 ghe thi fail.
- Ghe khong ton tai thi fail.
- Ghe khong thuoc match/stadium hop le thi fail.
- Ghe da booked/locked het han khong hop le thi fail.
- Thanh cong thi moi ghe tao mot ticket valid.
- Pending booking qua han phai expire va release seats.
- Duplicate request phai duoc chan bang `idempotencyKey` neu feature nay duoc implement.

### FR-04 Seller-Assisted Booking

Seller co the dat ve ho fan tai quay.

Acceptance criteria:

- Seller phai login staff.
- Seller search fan truoc.
- Neu fan chua ton tai, seller co the create fan.
- Seller booking phai dung chung `Execute Booking Core` voi fan booking va simulator.
- Offline payment thanh cong moi confirm booking.
- `Create Ticket` la system action; `Issue Ticket` la Seller operation sau khi ticket da tao.

### FR-05 Support Operations

Support Staff co the:

- Search fan.
- Search ticket.
- Search booking transaction.
- Search payment transaction.
- Check booking/payment/seat status.
- Assist failed booking, missing ticket, payment issue, seat issue.

Acceptance criteria:

- Support khong duoc sua du lieu quan trong neu khong co quyen.
- Moi thao tac can co audit log neu anh huong du lieu.

### FR-06 Administration

Administrator co the:

- Manage stadium.
- Manage section.
- Manage seat.
- Manage match.
- Manage fan.
- Manage staff va roles.
- Manage ticket pricing.
- Open ticket sales.
- Close ticket sales.
- View audit log.
- Generate CSV data.
- Validate CSV data.
- View system summary.

Manage = create, view, update, delete, search.

Acceptance criteria:

- Khong xoa entity da co transaction neu se lam hong lich su; nen dung status inactive/cancelled.
- Validate CSV phai bat duoc duplicate id, field thieu, status sai enum, reference khong ton tai.
- Fan chi booking duoc khi match sale status dang open.
- Gia ve phai tinh tu pricing snapshot tai thoi diem booking.

### FR-07 Payment Simulation

He thong mo phong payment voi cac status:

- `PENDING`
- `SUCCESS`
- `FAILED`
- `CANCELLED`
- `EXPIRED`

Acceptance criteria:

- Payment failed/cancelled/expired thi ghe bi unlock ve available neu chua confirm.
- Payment success nhung ticket missing phai duoc support/reconciliation phat hien.

### FR-08 Ticket Management

He thong tao ticket sau booking thanh cong.

Ticket status:

- `VALID`
- `USED`
- `CANCELLED`
- `REFUNDED`

Acceptance criteria:

- Mot `matchId + seatId` chi co toi da mot ticket `VALID`.
- Ticket phai tra cuu duoc theo ticketId, fanId, bookingId.

### FR-09 Concurrency Simulator

Simulator co the:

- Chon synchronization mechanism.
- Nhap thread count.
- Chon match va target seat(s).
- Chon contention scenario.
- Tao fan threads.
- Dung `CountDownLatch` de start gan nhu cung luc.
- Dung `ExecutorService` de quan ly thread pool.
- Ghi result va metric.

Acceptance criteria:

- `NO_LOCK` la unsafe baseline va co the tao race condition.
- Safe mechanisms phai ngan double booking.
- Co metric: attempts, success, failure, conflict, double booking, time, throughput, double booking rate.
- Simulator khong include `Create Booking` checkout flow cua Fan vi flow do co `Make Online Payment`.
- Simulator phai goi `Execute Booking Core`/`Attempt Seat Booking` dung chung de do tranh chap ghe.

### FR-10 Data Generation

He thong phai tao du lieu CSV >= 10,000 rows.

Acceptance criteria:

- Seat la dataset lon nhat.
- Du lieu co referential integrity: seat -> section -> stadium, match -> stadium.
- Co data mau du de test fan/seller/admin/simulator.

### FR-11 Seat Hold And Booking Expiry

He thong phai ho tro seat hold tam thoi khi booking dang pending.

Acceptance criteria:

- Khi hold thanh cong: seat `AVAILABLE -> LOCKED`.
- Booking pending co `expiresAt`.
- Neu `expiresAt < now`: booking `EXPIRED`, seat `LOCKED -> AVAILABLE`.
- Expiry co the duoc kich hoat khi user vao menu, truoc booking moi, hoac bang job simulation don gian.

### FR-12 Cancellation And Refund

Feature mo rong P2/P3:

- Fan co the request cancellation/refund neu policy cho phep.
- Support/Admin review cancellation.
- Payment Service mo phong refund.
- Ticket sau refund/cancel co status `REFUNDED` hoac `CANCELLED`.

### FR-13 Ticket Validation / Check-in

Feature mo rong P2:

- Gate Staff validate ticket.
- He thong reject ticket khong ton tai, cancelled, refunded hoac already used.
- Ticket hop le duoc mark `USED`.

### FR-14 Notification Simulation

Feature mo rong P2/P3:

- Send booking confirmation.
- Send payment result.
- Send ticket notification.
- Send match reminder.

Co the mo phong bang console log hoac `notifications.csv`.

## 4. Non-Functional Requirements

### NFR-01 Performance

- Doc >= 10,000 rows trong < 500 ms neu moi truong cho phep.
- Search/filter can chap nhan duoc voi dataset lon.
- Simulator 1000 threads phai chay duoc cho demo, neu may yeu co the demo 100-500 va report 1000 neu chay duoc.

### NFR-02 Consistency

- Khong duoc co double booking trong strategy safe.
- CSV write can atomic o muc hop ly: ghi temp file roi replace neu co the.
- Re-check availability tai thoi diem commit.
- Idempotency rule: repeated execution cua cung booking request khong duoc tao duplicate booking/ticket.

### NFR-03 Reliability

- Loi CSV, loi file access, invalid input khong lam crash chuong trinh.
- Lock bi treo phai co timeout/release path.
- Pending booking expiry phai chay duoc de tranh seat `LOCKED` mai.

### NFR-04 Maintainability

- Package ro rang.
- Exception rieng cho nghiep vu va ha tang.
- Interface cho repository va synchronization strategy.
- Khong duplicate booking logic giua Fan/Seller/Simulator.

### NFR-05 Security Simulation

- Password khong nen in ra console.
- Role-based menu cho staff.
- Khong cho fan xem ticket cua fan khac.
- Audit action cua staff/admin.

## 5. Assumptions

- Payment la simulation, khong tich hop gateway that.
- CSV la storage duy nhat.
- Ung dung chay mot process local, tru `FILE_LOCK` co the mo phong file-level lock.
- Seat availability duoc xet theo `matchId + seatId`, khong chi theo `seatId`.

## 6. Acceptance Summary

Du an duoc xem la dat yeu cau khi:

- Compile/run duoc.
- Co data >= 10,000 rows.
- Booking thanh cong tao ticket/transaction dung.
- Ghe booked khong dat lai duoc.
- Simulator chay duoc nhieu mechanism va co metric.
- Co bang chung strategy safe ngan double booking.
- Tai lieu/report/diagram/test checklist hoan thien.
