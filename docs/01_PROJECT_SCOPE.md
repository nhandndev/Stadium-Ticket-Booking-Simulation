# Project Scope

## Vai tro phan tich

Tai lieu nay duoc viet theo goc nhin ket hop:

- Business Analyst: chot actor, workflow, rule, edge case.
- Solution Architect: chia layer, service, data flow.
- QA Lead: dinh nghia test, metric, acceptance criteria.
- Product Owner: sap xep feature theo uu tien de lam duoc trong pham vi LAB211.

## Ten he thong

**Stadium Ticket Booking & Concurrency Simulation System**

## Muc tieu du an

He thong mo phong dat ve san van dong voi CSV persistence va Java MVC, dong thoi chung minh cach ngan double booking khi nhieu nguoi cung dat mot ghe.

Muc tieu nghiep vu:

- Cho phep khach xem tran dau, so do ghe va tinh trang ghe.
- Cho phep fan dat toi da 4 ghe moi giao dich.
- Cho phep nhan vien ban ve ho tro dat ve tai quay.
- Cho phep admin quan ly du lieu can thiet.
- Cho phep support tra cuu su co booking/payment/ticket.

Muc tieu ky thuat:

- MVC ro rang.
- CSV repository co kha nang doc dataset lon.
- Booking core dung chung cho online booking, seller booking va simulator.
- Fan/Seller checkout co payment; simulator chi goi booking core de do concurrency.
- Simulator tao nhieu thread de kiem tra tinh dung dan cua synchronization.
- Co metric de so sanh consistency, conflict va throughput.

## Actor

| Actor | Mo ta | Quyen chinh |
|---|---|---|
| Guest | Khach chua dang nhap | Xem match, stadium, section, seat map, register, login |
| Fan | Khach da dang nhap | Dat ve, thanh toan online, xem ticket/history |
| Staff | Actor cha cho nhan vien | Dang nhap staff |
| Seller | Nhan vien ban ve | Dat ve ho fan, nhan offline payment, issue ticket |
| Support Staff | Nhan vien ho tro | Tra cuu fan, booking, payment, ticket, xu ly su co |
| Administrator | Quan tri vien | CRUD stadium/section/seat/match/fan, generate/validate CSV |
| Payment Service | Cong thanh toan ngoai | Xu ly online payment result |
| Notification Service | Dich vu thong bao mo phong | Gui booking/payment/ticket notification |
| Gate Staff | Nhan vien soat ve | Validate ticket, check-in ticket |
| Simulator Operator | Nguoi chay mo phong | Cau hinh va chay concurrency simulation |

## Pham vi bat buoc

- Java OOP.
- MVC.
- CSV persistence.
- CRUD co ban cho entity quan trong.
- Booking voi limit 1-4 ghe/giao dich.
- Seat status lifecycle.
- Custom exception toi thieu 5 loai.
- Data generator tao >= 10,000 rows.
- Simulator dung `ExecutorService` va `CountDownLatch`.
- It nhat 3 synchronization strategies; khuyen nghi du 4 strategy trong README.

## Pham vi mo rong nen co

Nhung feature nay giup du an gan he thong doanh nghiep hon nhung van co the lam trong Java console/MVC:

- Role-based staff access.
- Booking hold/seat lock timeout.
- Idempotency key cho booking request de tranh bam submit nhieu lan.
- Open/close ticket sales theo match.
- Ticket pricing theo section/match va price snapshot.
- Payment status va reconciliation.
- Ticket status: `VALID`, `USED`, `CANCELLED`, `REFUNDED`.
- Ticket validation/check-in.
- Notification simulation.
- Audit log cho thao tac staff/admin.
- Search/filter/pagination don gian cho dataset lon.
- Refund/cancel booking co rule ro rang.
- Report/export simulator result.

## Ngoai pham vi

- Real payment gateway.
- Real database server.
- Web/mobile UI.
- Email/SMS thật.
- QR scanner thật.
- Distributed lock tren nhieu server.

Co the mo phong cac thanh phan tren bang interface/service va CSV.

## Uu tien trien khai

| Priority | Nhom cong viec |
|---|---|
| P0 | MVC, entity, CSV repository, data generator, fan booking, no double booking rule |
| P1 | Simulator, synchronization strategies, metrics, export result, sales control, pricing |
| P2 | Admin CRUD, seller-assisted booking, support lookup, hold expiry, idempotency, ticket check-in |
| P3 | Payment reconciliation, refund/cancel, notification, audit log, advanced search |
