# Prompt sua Class Diagram tren ChatGPT Web

## Vai tro

Hay dong vai Software Architect va Java OOP Lecturer cua mon LAB211. Nhiem vu la review va sua truc tiep bo Mermaid Class Diagram cua project Java Console `Stadium Ticket Booking Simulation`.

Day la project Java thuan, dung file CSV, khong dung Spring, database, REST API hoac kien truc enterprise phuc tap.

## File can doc

Doc tat ca cac file sau truoc khi sua:

- `00_Full_Class_Diagram.mmd`
- `01_User_Account_Domain.mmd`
- `02_Stadium_Match_Domain.mmd`
- `03_Booking_Payment_Ticket_Domain.mmd`
- `04_Simulation_Domain.mmd`
- `05_Console_Navigation_Domain.mmd`
- `README.md`

Mot so class ben duoi co the da duoc them mot phan. Hay audit truoc, khong tao class trung ten va khong them lai feature da co.

## Muc tieu

Hoan thien Class Diagram de du feature, field, method, visibility va multiplicity cho de LAB211, nhung van giu project nho, de code va de thuyet trinh.

Bo so do phai the hien dung luong:

```text
VIEW -> CONTROLLER -> REPOSITORY -> MODEL
```

Controller khong doc/ghi CSV truc tiep. View khong chua business logic. Repository phu trach truy cap CSV. Model chua entity, trang thai va business rule cot loi.

## Nhung noi dung bat buoc can kiem tra va bo sung

### 1. BaseEntity va CSV serialization

Can co abstract `BaseEntity` lam lop cha cua cac entity co ID.

Field va method toi thieu:

```text
BaseEntity
#id: Long
+BaseEntity(id: Long)
+getId(): Long
+toCsvLine(): String
+fromCsvLine(line: String): BaseEntity
```

`Fan`, `Stadium`, `Section`, `Seat`, `Match`, `Booking`, `Payment`, `Ticket` va `BookingTransaction` can ke thua `BaseEntity` neu chung co ID rieng.

Khong khai bao lap lai field `id` trong class con.

### 2. Generic CsvRepository

`CsvRepository` phai la generic abstraction, khong chi la class doc `String[]`.

Contract toi thieu:

```text
CsvRepository<T extends BaseEntity>
#filePath: Path
+findAll(): List<T>
+findById(id: Long): T
+findByCondition(condition: Predicate<T>): List<T>
+save(entity: T): T
+update(entity: T): T
+delete(id: Long): boolean
```

Repository cu the ke thua `CsvRepository<T>`.

### 3. Fan domain

De bai goi dich danh entity `Fan`, file `fans.csv`, `FanRepository` va `FanController`.

Can dam bao co:

```text
User
Fan extends User
FanRepository extends CsvRepository<Fan>
FanController
```

`FanController` toi thieu co:

```text
+register(...): Fan
+login(email: String, password: String): User
+logout(): void
+updateProfile(...): User
+getMyTickets(): List<Ticket>
```

Neu Admin dung chung `User`, chi can `UserRole` gom `FAN` va `ADMIN`. Khong them Seller, Support Staff, Gate Staff hoac he thong role phuc tap.

### 4. Quy tac toi da bon ve

Mot Fan chi duoc dat toi da bon ve trong mot giao dich.

Can the hien ro bang field hoac business method:

```text
Fan.MAX_TICKETS_PER_TRANSACTION: int = 4
Fan.canBook(ticketCount: int): boolean
Booking.validateTicketLimit(): void
BookingController.validateTicketLimit(seatIds: List<Long>): void
```

### 5. Seat va optimistic version

De bai yeu cau `version` de phuc vu Optimistic Locking.

He thong hien co `Seat` la vi tri vat ly va `MatchSeat` la trang thai ghe theo tung tran. Hay giu cach tach nay neu no dang duoc su dung, vi mot ghe vat ly co the xuat hien trong nhieu tran.

Can lam ro:

- `Seat` chua thong tin vi tri nhu section, row va seat number.
- `MatchSeat` chua `status` va `version` dung cho booking theo tung tran.
- `MatchSeat` co `isAvailable`, `lock`, `markBooked` va `release`.
- Repository co atomic update theo `expectedVersion`.

Them note trong so do hoac README giai thich `MatchSeat.version` chinh la implementation cua yeu cau optimistic version tren ghe ban theo tran. Khong dat `status` o ca `Seat` va `MatchSeat` neu dieu do tao hai nguon trang thai mau thuan.

### 6. Repository bat buoc

Can co va phan cong dung trach nhiem:

- `FanRepository`
- `SeatRepository`
- `MatchRepository`
- `TicketRepository`
- `TransactionRepository`

CRUD day du can the hien ro cho `Fan`, `Seat` va `Match`.

`TransactionRepository` doc/ghi `BookingTransaction` trong `transactions.csv`.

### 7. BookingTransaction

Can co entity `BookingTransaction` de ghi lai tung booking attempt va ket qua simulation.

Field toi thieu:

```text
-id: Long
-bookingId: Long
-fanId: Long
-matchId: Long
-seatIds: List<Long>
-mechanism: SyncMechanism
-status: TransactionStatus
-createdAt: LocalDateTime
-completedAt: LocalDateTime
-durationMillis: long
-failureReason: String
```

Method toi thieu:

```text
+markSuccessful(completedAt: LocalDateTime): void
+markFailed(reason: String, completedAt: LocalDateTime): void
+calculateDuration(): long
```

Khong can xoa `Booking` va `Payment` neu chung dang phuc vu luong dat ve va thanh toan. `BookingTransaction` chi can la log giao dich va ket qua attempt.

### 8. Ba co che dong bo va NO_LOCK baseline

Chi co enum `SyncMechanism` la chua du. Can co implementation thuc te nhung giu nho gon theo Strategy Pattern:

```text
BookingStrategy
NoLockStrategy
SynchronizedStrategy
FileLockStrategy
OptimisticLockStrategy
StrategyFactory
```

`BookingStrategy` co mot operation chung:

```text
+execute(fanId: Long, matchId: Long, seatIds: List<Long>): BookingTransaction
```

Y nghia tung implementation:

- `NoLockStrategy`: baseline khong khoa de thay double booking.
- `SynchronizedStrategy`: dung Java `synchronized` tren critical section.
- `FileLockStrategy`: dung Java NIO `FileLock` khi doc/ghi CSV.
- `OptimisticLockStrategy`: kiem tra version va retry khi conflict.

Khong them distributed lock, Redis, database transaction hoac message queue.

### 9. Simulator da luong

`SimulationController.runSimulation()` mot minh chua chung minh duoc concurrency.

Can the hien:

```text
BookingTask implements Callable<BookingTransaction>
-startLatch: CountDownLatch
-strategy: BookingStrategy
+call(): BookingTransaction
```

`SimulationController` can co:

```text
-executor: ExecutorService
-strategyFactory: StrategyFactory
+configureSimulation(...): Simulation
+runSimulation(simulationId: Long): SimulationResult
+compareSyncMechanisms(...): List<SimulationResult>
+shutdown(): void
```

Tat ca task phai cho cung mot `CountDownLatch` de bat dau gan nhu dong thoi.

### 10. Simulation va SimulationResult

`Simulation` can biet tran, danh sach ghe muc tieu, so thread va co che dong bo.

`SimulationResult` toi thieu can co:

```text
-mechanism: SyncMechanism
-threadCount: int
-attemptCount: int
-successCount: int
-failedCount: int
-conflictCount: int
-doubleBookingCount: int
-durationMillis: long
+calculateThroughput(): double
+calculateDoubleBookingRate(): double
```

### 11. DataGenerator

Can co class `DataGenerator` sinh tong du lieu CSV toi thieu 10.000 dong.

Method co the gom:

```text
+generateAll(recordCount: int): void
+generateStadiums(): void
+generateSections(): void
+generateSeats(recordCount: int): void
+generateFans(): void
+generateMatches(): void
+clearGeneratedData(): void
```

Khong xem `DataGenerator` la Controller. Day la thanh phan ho tro Model/CSV data.

### 12. Custom exceptions

Rubric yeu cau it nhat nam custom exceptions. Chi can cac exception sat nghiep vu:

- `EntityNotFoundException`
- `InvalidBookingException`
- `SeatAlreadyBookedException`
- `PaymentFailedException`
- `CsvDataException`
- `OptimisticLockException`

Moi exception can co constructor, khong de class box rong.

### 13. Console View

Ngoai menu dieu huong, can co cac View hien thi dung output LAB:

- `MainView`
- `SeatMapView`
- `BookingView`
- `SimulatorView`
- `ReportView`

View chi doc input, in ASCII va goi Controller. View khong duoc tinh gia, khoa ghe, ghi CSV hoac tinh metric.

## Quy tac thiet ke phai giu nguyen

- Giu nguyen cach chia mot file tong va nam file domain hien tai.
- Khong tao them hang loat file `.mmd`.
- Giu nguyen theme, bang mau va phong cach class box hien tai.
- So do tong doc tu tren xuong: `VIEW -> CONTROLLER -> REPOSITORY -> MODEL`.
- So do domain co the dung `TB` hoac `LR` theo file hien tai, nhung phai de nhin.
- Khong tao reference table, mapping table hoac class box rong de tro sang file khac.
- Neu mot domain can class cua domain khac de giai thich luong, ve lai day du class box do trong cung file.
- Khong noi tat ca class voi nhau. Chi giu quan he can de hieu ownership, dependency, inheritance va multiplicity.
- Khong them Service layer, DTO layer, Spring component hoac design pattern khong can thiet.
- Khong them feature ngoai reduced Use Case hien tai.
- Khong dung mot class `AdminController` khong lo. Admin tiep tuc goi cac domain controller tuong ung.
- Tat ca class co field va method phai dung visibility `+`, `-`, `#`.
- Constructor khong co return type.
- Dung `Long` cho ID, `BigDecimal` cho tien va `LocalDateTime` cho thoi gian.

## Multiplicity can giu

- Mot `Stadium` co `0..*` `Section`.
- Mot `Section` co `0..*` `Seat`.
- Mot `Stadium` co `0..*` `Match`.
- Mot `Match` co `0..*` `MatchSeat`.
- Mot `Seat` co the xuat hien trong nhieu `MatchSeat` cua cac tran khac nhau.
- Mot `Fan` co `0..*` `Booking`.
- Mot `Booking` dat `1..*` `MatchSeat`.
- Mot `Booking` co toi da mot `Payment`.
- Mot `Booking` co `0..*` `Ticket`.
- Mot `Booking` co it nhat mot `BookingTransaction`.
- Mot `Simulation` co toi da mot `SimulationResult`.

## Yeu cau ve trinh bay

- Uu tien de thuyet trinh: nhin tu tren xuong, trai sang phai.
- Dat cac class cung vai tro gan nhau trong namespace.
- Han che day noi cat qua class box.
- Dung note ngan de giai thich booking core, MatchSeat version va simulation khong thanh toan.
- Khong dua mo ta dai vao trong diagram.
- Full diagram duoc dung de gioi thieu tong quan; domain diagram moi la trang de thuyet trinh chi tiet.

## Kiem tra truoc khi hoan tat

1. Render thu tat ca sau file `.mmd` bang Mermaid CLI.
2. Dam bao khong co class trung ten trong cung file.
3. Dam bao cung mot class co field va method nhat quan giua file tong va file domain.
4. Dam bao khong con ten cu `UserRepository`, `UserController` hoac `CsvDatasetRepository` neu da chuyen sang `FanRepository`, `FanController` va `DataGenerator`.
5. Dam bao khong co business logic trong View va khong co file I/O trong Controller.
6. Dam bao generic `CsvRepository<T>`, `Predicate<T>`, `CountDownLatch`, `ExecutorService` va bon synchronization strategy deu hien ro.
7. Cap nhat `README.md` cua bo Class Diagram sau khi sua.

## Output mong muon

- Sua truc tiep sau file `.mmd` hien tai.
- Cap nhat `README.md` hien tai.
- Khong tao reference table.
- Khong tao them domain diagram neu khong that su can.
- Cuoi cung tom tat file nao da sua, class nao da them va ket qua render Mermaid.
