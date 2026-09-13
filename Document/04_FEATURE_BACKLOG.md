# Feature Backlog

## Feature Priority Legend

| Priority | Y nghia |
|---|---|
| P0 | Bat buoc de dat de bai va demo core |
| P1 | Rat nen co de du an tron ven |
| P2 | Mo rong gan doanh nghiep, lam neu con thoi gian |
| P3 | Nang cao, co the dua vao report/roadmap |

## Module 1 - Public Discovery

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-001 | View match list | P0 | Hien match dang ban ve |
| F-002 | Search/filter match | P1 | Theo ten, ngay, stadium |
| F-003 | View match detail | P0 | Doi bong, thoi gian, stadium |
| F-004 | View stadium/section | P0 | Lam nen cho seat map |
| F-005 | View seat availability | P0 | Chi la snapshot, booking phai re-check |

## Module 2 - Account & Access

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-101 | Fan register | P0 | Kiem tra trung email/phone |
| F-102 | Fan login/logout | P0 | Session don gian |
| F-103 | Staff login | P1 | Phan role Seller/Support/Admin |
| F-104 | Role-based menu | P1 | An menu khong dung quyen |
| F-105 | Account status | P2 | ACTIVE, INACTIVE, BLOCKED |
| F-106 | Manage fan profile | P2 | View/update name, phone, email, password |

## Module 3 - Fan Booking

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-201 | Select 1-4 seats | P0 | Business rule bat buoc |
| F-202 | Review booking | P0 | Gia, ghe, match |
| F-203 | Validate booking | P0 | Fan/match/seat/status/limit |
| F-204 | Seat hold | P0 | AVAILABLE -> LOCKED voi expiry |
| F-205 | Confirm booking | P0 | LOCKED -> BOOKED |
| F-206 | Create ticket | P0 | 1 ticket moi ghe |
| F-207 | Record transaction | P0 | SUCCESS/FAILED |
| F-208 | Idempotency key | P2 | Tranh submit lap |
| F-209 | Seat hold timeout | P2 | LOCKED qua han -> AVAILABLE |
| F-210 | Execute booking core | P0 | Dung chung cho Fan/Seller/Simulator, khong gom payment |
| F-211 | Calculate booking total | P1 | Snapshot gia tai thoi diem booking |
| F-212 | Expire pending booking | P1 | PENDING qua han -> EXPIRED va release seats |

## Module 4 - Payment Simulation

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-301 | Online payment simulation | P1 | SUCCESS/FAILED/CANCELLED/EXPIRED |
| F-302 | Offline payment confirmation | P1 | Cho seller |
| F-303 | Payment transaction CSV | P1 | Luu status va amount |
| F-304 | Payment reconciliation | P2 | Phat hien payment success nhung ticket missing |
| F-305 | Refund simulation | P3 | Sau khi co cancel/refund rule |
| F-306 | Payment result notification | P3 | Mo phong bang log/CSV |

## Module 5 - Ticket

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-401 | View my tickets | P0 | Fan chi xem ticket cua minh |
| F-402 | Ticket detail | P0 | Match, seat, status |
| F-403 | Ticket lookup by staff | P1 | Theo ticketId/fanId/bookingId |
| F-404 | Ticket status lifecycle | P2 | VALID, USED, CANCELLED, REFUNDED |
| F-405 | QR code string simulation | P3 | Khong can generate image |
| F-406 | Ticket validation/check-in | P2 | Gate Staff scan, mark USED |

## Module 6 - Seller

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-501 | Search fan | P1 | Theo phone/email/name |
| F-502 | Create fan at counter | P1 | Extend Search Fan |
| F-503 | Booking for fan | P1 | Dung chung BookingService |
| F-504 | Offline payment | P1 | Xac nhan thanh toan tai quay |
| F-505 | Seller transaction history | P2 | Theo staffId |

## Module 7 - Support

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-601 | Search ticket | P1 | Ho tro khach |
| F-602 | Search booking transaction | P1 | Check luong booking |
| F-603 | Search payment transaction | P2 | Can cho payment issue |
| F-604 | Assist failed booking | P2 | Release lock neu can |
| F-605 | Assist missing ticket | P2 | Reconciliation |
| F-606 | Audit support note | P2 | Log thao tac |

## Module 8 - Administration

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-701 | Manage stadium | P1 | CRUD + search |
| F-702 | Manage section | P1 | CRUD + search |
| F-703 | Manage seat | P1 | CRUD + search |
| F-704 | Manage match | P1 | CRUD + search |
| F-705 | Manage fan | P1 | CRUD + search |
| F-706 | Generate CSV data | P0 | >= 10,000 rows |
| F-707 | Validate CSV data | P1 | Duplicate/reference/status |
| F-708 | View system summary | P2 | Counts/status |
| F-709 | Manage staff | P1 | Create/disable seller/support/admin |
| F-710 | Manage roles | P1 | Assign SELLER/SUPPORT/ADMIN |
| F-711 | Open ticket sales | P1 | Cho match bat dau ban ve |
| F-712 | Close ticket sales | P1 | Ngung ban ve theo match |
| F-713 | Manage ticket pricing | P1 | Section/match price |
| F-714 | View audit log | P2 | Theo actor/action/entity |

## Module 9 - Concurrency Simulator

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-801 | Configure thread count | P0 | 100, 500, 1000 |
| F-802 | Select target seats | P0 | High/medium/low contention |
| F-803 | Select mechanism | P0 | NO_LOCK, SYNCHRONIZED, FILE_LOCK, OPTIMISTIC |
| F-804 | Execute concurrent booking | P0 | Dung BookingService core, khong goi payment checkout |
| F-805 | Detect double booking | P0 | Scan matchId+seatId valid tickets |
| F-806 | Calculate metrics | P0 | Throughput, conflict, rate |
| F-807 | Export result | P1 | CSV/Markdown |
| F-808 | Compare mechanisms | P1 | Bang tong hop |
| F-809 | Execute booking core only | P0 | Simulator khong goi payment checkout |

## Module 10 - Observability & Audit

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-901 | Application log | P2 | Error/debug don gian |
| F-902 | Audit log | P2 | Staff/admin thay doi du lieu |
| F-903 | Error report | P2 | Tong hop exception count |
| F-904 | Data integrity report | P2 | Duplicate/missing reference |
| F-905 | Notification simulation | P3 | Booking confirmation, ticket, reminder |

## Module 11 - Cancellation, Refund And Gate

| ID | Feature | Priority | Notes |
|---|---|---|---|
| F-1001 | Request cancellation | P2 | Fan yeu cau huy booking neu policy cho phep |
| F-1002 | Review cancellation | P2 | Support/Admin duyet |
| F-1003 | Process refund | P3 | Payment refund simulation |
| F-1004 | Cancel ticket | P2 | VALID -> CANCELLED/REFUNDED |
| F-1005 | Gate staff check-in | P2 | Validate ticket va mark USED |

## Enterprise-like Features Nen Them Nhung Lam Gon

Nen them:

- Seat hold timeout.
- Idempotency key.
- Ticket/payment reconciliation.
- Sales open/close control.
- Pricing snapshot.
- Ticket check-in/validation.
- Notification simulation.
- Soft delete/status thay vi xoa du lieu da co transaction.
- Audit log cho staff/admin.
- Validation CSV truoc khi load vao memory.

Khong nen them qua nang:

- Real online payment.
- Distributed microservice.
- Real-time web socket seat map.
- Complex promotion engine.
- Dynamic pricing nang cao.
