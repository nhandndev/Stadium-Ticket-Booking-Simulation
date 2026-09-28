# Giai thich field cua Model va Entity

Tai lieu nay chi noi ve **Model/Entity va enum dung boi Model** trong sau trang class diagram o `docs/Class_Diagrams/`. Moi class duoc giai thich mot lan, du no xuat hien lai o nhieu trang. Khong bao gom DTO, View, Controller, Service, Repository hay `AppException`.

## Cach doc chung

- `-field: Type` la thuoc tinh private; `+field: Type` va `+method()` la public. Bo class diagram hien tai chi dung `-` va `+`, khong dung `#` (protected). Field luu **trang thai cua doi tuong**, khong phai mot feature/menu.
- `Long` thuong la ID. Field nhu `stadiumId` la ID tham chieu den doi tuong khac, khong phai ban sao cua ca doi tuong.
- `LocalDateTime` luu ngay gio; `BigDecimal` luu tien de tranh sai so so thuc; `List<Long>` luu nhieu ID.
- `BaseEntity.id` duoc ke thua boi cac entity co ID. Vi vay `Stadium`, `Booking`, `Ticket`... khong can khai bao lai `id` trong o class rieng.
- Gia tri `null` o cac field nhu `reviewedAt` hay `checkedInAt` co the hieu la su kien do chua xay ra. Khi code, can xu ly truong hop nay ro rang.

## 1. Tai khoan va quyen

### BaseEntity (entity cha truu tuong)

Class cha dung chung cho entity co ID; khong tao `BaseEntity` truc tiep.

- `id: Long`: ma dinh danh duy nhat cua mot ban ghi trong **loai entity do**. Vi du `Stadium.id = 5` la san so 5; `Seat.id = 5` la ghe so 5, hai ID nay khong can trung nhau ve y nghia. Trong code hien tai, field nay la `private` va co `getId()`.

### User (entity cha cua Fan va Staff)

Luu thong tin dang nhap va ho so chung. `User` ke thua `id` tu `BaseEntity`.

- `fullName: String`: ho ten hien thi cua nguoi dung; duoc sua qua cap nhat ho so.
- `email: String`: dia chi dung de tim tai khoan va dang nhap. Can tranh hai tai khoan co cung email theo quy tac cua he thong.
- `passwordHash: String`: gia tri dai dien cho mat khau da bam; **khong phai** mat khau goc de hien thi hoac luu thang vao CSV.
- `phone: String`: so dien thoai lien lac; dung `String` de khong mat so 0 dau va cho phep dau `+` neu can.
- `role: UserRole`: quyen cua tai khoan, quyet dinh menu va nghiep vu duoc phep goi. Fan co role `FAN`; Staff co mot trong cac role nhan vien.
- `status: UserStatus`: trang thai tai khoan, dung de cho phep hoac tu choi dang nhap/su dung. Khac voi `role`: role tra loi **duoc lam gi**, status tra loi **tai khoan con hoat dong khong**.

### Fan (entity ke thua User)

Nguoi mua va so huu booking/ve. Fan dung chung `id`, `fullName`, `email`, `passwordHash`, `phone`, `role`, `status` cua `User`; class diagram khong khai bao lai chung.

- `MAX_TICKETS_PER_TRANSACTION: int = 4`: gioi han so ve/ghe trong mot lan dat. `canBook(ticketCount)` dung gioi han nay de kiem tra so luong. Day la quy tac co dinh, khong phai so ve Fan da mua.

### Staff (entity ke thua User)

Tai khoan nhan vien. Khong co field rieng tren class diagram; ke thua toan bo field tu `User`, dac biet `role`. `assignRole(role)` doi role cua Staff giua Seller, Gate Staff, Administrator va Simulator Operator theo quyen quan tri.

## 2. San, ghe, tran va gia

### Stadium (entity)

Mot san van dong. Ke thua `id` tu `BaseEntity`.

- `name: String`: ten san de hien thi va tim kiem.
- `address: String`: dia chi/vi tri cua san de nguoi xem biet tran dien ra o dau.

### Section (entity)

Mot khu hoac khan dai trong san. Ke thua `id`.

- `stadiumId: Long`: ID cua san chua khu nay; vi du khu A thuoc san 5 thi `stadiumId = 5`.
- `name: String`: ten khu, vi du `A`, `VIP` hoac `Khan dai B`.

### Seat (entity)

Mot ghe vat ly trong mot khu; ke thua `id`. Ghe nay ton tai doc lap voi tung tran dau.

- `sectionId: Long`: ID khu chua ghe.
- `rowLabel: String`: nhan hang ghe, vi du `A`.
- `seatNumber: int`: so ghe trong hang, vi du `12`; ket hop voi khu va hang de nguoi dung nhan biet cho ngoi.
- `active: boolean`: ghe vat ly co duoc su dung/ban ve hay khong. `false` co the la ghe hong hoac tam ngung ban. **Khong co nghia ghe da duoc dat.**

### Match (entity)

Mot tran dau dien ra tai mot san; ke thua `id`.

- `stadiumId: Long`: ID san to chuc tran. Khong phai ID khu hoac ghe.
- `homeTeam: String`: ten doi chu nha.
- `awayTeam: String`: ten doi khach.
- `startTime: LocalDateTime`: thoi diem tran bat dau; dung de hien thi, loc tran sap dien ra va kiem tra quy tac ban ve.
- `saleStatus: SaleStatus`: tinh trang mo/dong ban ve cua **ca tran**. `OPEN` khong dong nghia moi ghe deu con trong.

### MatchSeat (entity)

Trang thai cua **mot ghe trong mot tran cu the**; ke thua `id`. Cung mot `Seat` co the trong o tran nay nhung da duoc dat o tran khac.

- `matchId: Long`: ID tran dau ma trang thai nay thuoc ve.
- `seatId: Long`: ID ghe vat ly duoc tham chieu.
- `status: SeatStatus`: tinh trang dat ghe cho **tran do**: `AVAILABLE`, `LOCKED` hoac `BOOKED`. Day khac `Seat.active` (tinh trang su dung ghe vat ly).
- `version: int`: so phien ban cua ban ghi, dung cho optimistic locking; moi cap nhat hop le can doi chieu/tang version de phat hien cap nhat canh tranh.

### TicketPrice (model gia ve)

Gia ve cua mot khu trong mot tran. Class diagram **khong cho TicketPrice ke thua BaseEntity**, nen no khong co `id` rieng tren so do.

- `matchId: Long`: tran ap dung gia.
- `sectionId: Long`: khu ap dung gia.
- `amount: BigDecimal`: gia cua **mot ghe** tai khu do cho tran do. Cap `matchId + sectionId` xac dinh muc gia; khong phai tong tien cua booking.

## 3. Dat ve, thanh toan va vao cong

### Booking (entity)

Mot lan dat ve cua Fan cho mot tran; ke thua `id`.

- `fanId: Long`: ID Fan so huu booking. Khi Seller dat ho, day van la ID cua Fan, khong phai ID Seller.
- `matchId: Long`: ID tran duoc dat; mot booking trong so do chi cho mot tran.
- `seatIds: List<Long>`: cac ID ghe duoc chon trong tran do. Khi xu ly can ket hop `matchId` voi tung `seatId` de tim `MatchSeat` va kiem tra trang thai; gioi han theo so do la 1-4 ghe.
- `totalAmount: BigDecimal`: tong tien booking, tinh tu gia ve cua cac ghe/khu. Khac `TicketPrice.amount` la gia cua mot ghe.
- `status: BookingStatus`: trang thai booking, vi du dang cho, da xac nhan, hoac da huy. Khong dung field nay thay cho ket qua thanh toan hay check-in.

### Payment (entity)

Mot ban ghi thanh toan gan voi booking; ke thua `id`.

- `bookingId: Long`: ID booking duoc thanh toan.
- `amount: BigDecimal`: so tien giao dich; can doi chieu voi `Booking.totalAmount` theo quy tac nghiep vu.
- `method: PaymentMethod`: cach thanh toan: online hoac tien mat offline qua Seller.
- `status: PaymentStatus`: ket qua thanh toan, doc lap voi `Booking.status`. Thanh toan `SUCCESS` la dieu kien de xac nhan luong online/phat ve theo quy tac hien tai.

### Ticket (entity)

Mot ve cho mot ghe/tran; ke thua `id`. Mot booking nhieu ghe co the tao nhieu ticket.

- `bookingId: Long`: ID booking sinh ra ve.
- `matchId: Long`: tran ma ve co gia tri; dung khi Gate Staff xac thuc.
- `seatId: Long`: ghe duoc ghi tren ve.
- `ticketCode: String`: ma ve duy nhat de tim/xac thuc ve tai cong; khac ID noi bo cua entity.
- `status: TicketStatus`: trang thai ve: cho phat hanh, da phat hanh, da check-in, hoac da huy.
- `checkedInAt: LocalDateTime`: thoi diem vao cong thanh cong; chua check-in thi chua co gia tri. Dung de phat hien ve da su dung va tra cuu lich su.

### BookingTransaction (entity)

Ban ghi **mot lan thu thuc hien dat ghe**; ke thua `id`. Co the gan voi simulation de tinh metric. Khac `Payment`: transaction nay ghi ket qua tranh chap/dong bo khi dat ghe, khong phai giao dich tien.

- `simulationId: Long`: ID lan simulation lien quan; co the chua co neu la booking thong thuong.
- `bookingId: Long`: ID booking duoc tao thanh cong; co the chua co neu lan thu that bai.
- `fanId: Long`: ID Fan thay mat thuc hien yeu cau dat.
- `matchId: Long`: ID tran dang dat.
- `seatIds: List<Long>`: danh sach ghe ma lan thu nay nham toi.
- `mechanism: SyncMechanism`: cach dong bo da su dung, de so sanh cac lan chay. Voi booking thong thuong, can quy uoc ro co gan mechanism hay khong khi code.
- `status: TransactionStatus`: ket qua `SUCCESS`, `FAILED` hoac `CONFLICT`; khac `Booking.status`.
- `createdAt: LocalDateTime`: thoi diem bat dau/tao lan thu.
- `completedAt: LocalDateTime`: thoi diem lan thu ket thuc; truoc khi ket thuc co the chua co gia tri.
- `durationMillis: long`: thoi gian xu ly lan thu tinh bang mili giay, thuong dua tren `createdAt` va `completedAt`.
- `failureReason: String`: ly do that bai/xung dot, giup giai thich ket qua; thanh cong thi co the de trong.

### RefundRequest (entity)

Yeu cau hoan tien cua Fan; ke thua `id`. Yeu cau duoc duyet khong tu dong chung minh tien da chuyen lai neu he thong khong co buoc thanh toan hoan tien rieng.

- `bookingId: Long`: booking bi yeu cau hoan.
- `fanId: Long`: Fan gui yeu cau; can doi chieu voi chu so huu booking.
- `reason: String`: ly do Fan de nghi hoan.
- `status: RefundStatus`: `REQUESTED`, `APPROVED` hoac `REJECTED`.
- `requestedAt: LocalDateTime`: thoi diem Fan gui yeu cau.
- `reviewedBy: Long`: ID Administrator da duyet/tu choi; chua review thi chua co gia tri.
- `reviewedAt: LocalDateTime`: thoi diem xu ly yeu cau; chua review thi chua co gia tri.
- `reviewNote: String`: ghi chu/quyet dinh cua Administrator.

### AuditLog (entity)

Ban ghi theo doi hanh dong quan trong; ke thua `id`. Day la audit nghiep vu he thong, **khong phai AI usage log**.

- `actorId: Long`: ID User/Staff thuc hien hanh dong.
- `action: String`: ten hanh dong, vi du `OPEN_SALES` hoac `CHECK_IN_TICKET`.
- `targetType: String`: loai doi tuong bi tac dong, vi du `MATCH` hoac `TICKET`.
- `targetId: Long`: ID doi tuong bi tac dong.
- `result: String`: ket qua hanh dong, vi du thanh cong hay that bai; so do de String, chua quy dinh enum.
- `createdAt: LocalDateTime`: thoi diem ghi log.

## 4. Mo phong dong thoi

### Simulation (entity)

Mot cau hinh/lan chay mo phong nhieu thread tranh ghe; ke thua `id`.

- `matchId: Long`: tran duoc chon de mo phong.
- `targetSeatIds: List<Long>`: tap ID ghe ma cac thread nham toi; dung cung tap ghe moi the hien tranh chap.
- `threadCount: int`: so thread/nguoi dat gia lap se chay.
- `mechanism: SyncMechanism`: chien luoc dong bo se duoc thu trong lan chay.
- `status: SimulationStatus`: vong doi cua simulation: da cau hinh, dang chay, da hoan thanh.

### SimulationResult (entity)

So lieu tong ket cua mot lan simulation; ke thua `id`.

- `simulationId: Long`: ID simulation tao ra ket qua.
- `mechanism: SyncMechanism`: chien luoc ma bo ket qua nay dai dien; cho phep so sanh giua cac chien luoc.
- `threadCount: int`: so thread duoc cau hinh cho ket qua nay.
- `attemptCount: int`: tong so lan thu dat ghe da ghi nhan; khong mac dinh bang `threadCount` neu moi thread co the thu nhieu lan.
- `successCount: int`: so lan thu thanh cong.
- `failedCount: int`: so lan thu khong thanh cong.
- `conflictCount: int`: so lan gap xung dot khi tranh cung ghe/ghi du lieu; khong dong nghia da double-booking.
- `doubleBookingCount: int`: so truong hop ghe bi dat trung duoc phat hien; day la metric chat luong dong bo quan trong nhat.
- `durationMillis: long`: tong thoi gian chay de tao bo ket qua, tinh bang mili giay; dung cung so lan thu de tinh throughput.

## 5. Enum la kieu du lieu cua Model

Enum khong phai entity/doc lap luu CSV; chung gioi han gia tri hop le cua cac field o tren.

- `UserRole`: `FAN`, `SELLER`, `GATE_STAFF`, `ADMIN`, `SIMULATOR_OPERATOR` - quyen cua `User.role`.
- `UserStatus`: `ACTIVE`, `INACTIVE` - trang thai tai khoan cua `User.status`.
- `SaleStatus`: `CLOSED`, `OPEN` - trang thai mo ban cua `Match.saleStatus`.
- `SeatStatus`: `AVAILABLE`, `LOCKED`, `BOOKED` - trang thai ghe theo tran cua `MatchSeat.status`.
- `BookingStatus`: `PENDING`, `CONFIRMED`, `CANCELLED` - trang thai cua `Booking.status`.
- `PaymentMethod`: `ONLINE`, `OFFLINE_CASH` - cach tra tien cua `Payment.method`.
- `PaymentStatus`: `PENDING`, `SUCCESS`, `FAILED` - ket qua cua `Payment.status`.
- `TicketStatus`: `PENDING`, `ISSUED`, `CHECKED_IN`, `CANCELLED` - trang thai cua `Ticket.status`.
- `RefundStatus`: `REQUESTED`, `APPROVED`, `REJECTED` - trang thai cua `RefundRequest.status`.
- `SyncMechanism`: `NO_LOCK`, `SYNCHRONIZED`, `FILE_LOCK`, `OPTIMISTIC` - cach xu ly tranh chap cua Simulation/Transaction/Result.
- `SimulationStatus`: `CONFIGURED`, `RUNNING`, `COMPLETED` - vong doi cua `Simulation.status`.
- `TransactionStatus`: `SUCCESS`, `FAILED`, `CONFLICT` - ket qua cua `BookingTransaction.status`.

## 6. Bon co che xu ly tranh chap ghe trong Simulation

Vi du chung: hai thread A va B cung muon dat `matchId = 1, seatId = 12`. Ban dau `MatchSeat.status = AVAILABLE`. Pham vi can bao ve la **doc trang thai -> kiem tra -> cap nhat trang thai/ghi CSV**, khong chi rieng lenh ghi cuoi. Day la bon gia tri cua `SyncMechanism` va bon `BookingStrategy` trong class diagram trang 04.

### NO_LOCK - baseline co chu y khong an toan

- A va B cung doc `AVAILABLE`, sau do ca hai cung thu ghi `BOOKED`. Ca hai co the bao thanh cong cho cung mot ghe: double booking.
- Muc dich: lam moc so sanh de thay race condition; **khong phai co che chong dat trung** va khong dung cho luong dat ve binh thuong.
- Ket qua khong bao dam lan nao cung phat sinh double booking, vi no phu thuoc thu tu thread. Can chay nhieu thread cung tranh mot tap ghe de quan sat ro.

### SYNCHRONIZED - khoa cac thread trong mot JVM

- A giu mot lock **dung chung** trong suot buoc doc-kiem-tra-ghi. B cho A xong, doc lai CSV va thay ghe da `BOOKED`, nen B khong dat duoc.
- Phu hop voi chuong trinh Java console chay mot process. Neu moi thread dung mot repository/lock khac nhau thi khoa khong con tac dung; neu chay hai JVM rieng thi `synchronized` khong khoa qua process.
- Tren class diagram, `SynchronizedStrategy` goi co che cap nhat duoc dong bo cua `MatchSeatRepository`.

### FILE_LOCK - khoa file CSV

- A giu khoa file khi doc-kiem-tra-ghi `MatchSeat`; B phai cho khoa duoc tha, sau do doc lai du lieu moi. Co the bao ve viec ghi khi nhieu process Java cung dung mot file, neu tat ca deu tuan thu cung co che khoa.
- Cham hon vi phai mo/khoa/ghi file. Khong khoa chi luc `write`: neu kiem tra ghe xong moi khoa file, ca A va B van co the doc duoc `AVAILABLE` va dat trung.
- Tren class diagram, `FileLockStrategy` su dung `MatchSeatRepository.updateWithFileLock(...)`.

### OPTIMISTIC - so sanh version truoc khi ghi

- A va B cung doc `version = 3`. A ghi hop le thanh `BOOKED, version = 4`. B chi duoc ghi neu version hien tai van bang `3`; luc nay khong khop nen B nhan `CONFLICT`, co the doc lai va thu lai toi `maxRetries`.
- Phu hop khi xung dot it, vi khong can giu khoa trong suot thoi gian xu ly. Neu tranh chap cao, so lan retry co the tang.
- **Kiem tra version va ghi phai nguyen tu.** Neu tach thanh hai thao tac doc/ghi doc lap tren CSV thi ca A va B van co the cung vuot qua kiem tra; chi co field `version` khong tu dong ngan double booking.
- Tren class diagram, `OptimisticLockStrategy` su dung `MatchSeatRepository.updateIfVersionMatches(...)`.

Ket qua mong muon: `NO_LOCK` co the tao double booking; ba co che con lai nham dam bao moi cap `matchId + seatId` chi co mot lan dat thanh cong. `conflictCount` dem lan bi tranh chap; `doubleBookingCount` dem ghe thuc su bi ghi nhan dat trung. Hai so nay khong dong nghia nhau. Simulation chi tranh ghe, khong goi online payment.

## 7. Ba dieu de nham khi thuyet trinh

1. `Seat.active` mo ta **ghe vat ly co duoc su dung khong**; `MatchSeat.status` mo ta **ghe da duoc dat cho tran nay chua**.
2. `Booking.status`, `Payment.status`, `Ticket.status` la **ba vong doi khac nhau**: dat cho, tra tien, phat hanh/su dung ve.
3. `BookingTransaction` la lan thu dat ghe va ket qua dong bo; `Payment` la ban ghi tien. Khong gop chung hai class nay.

**Nguon doi chieu:** `06_Model_Relationships.mmd` cho danh sach model; `05_CSV_Repository_BaseEntity.mmd` cho `BaseEntity`; cac enum bo sung nam o `01`-`04`. Tai lieu nay giai thich y nghia field dang co, khong tu them field vao class diagram.
