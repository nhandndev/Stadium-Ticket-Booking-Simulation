# Full Class Diagram Dependencies - Pages 1 to 6

Tai lieu nay tong hop cac duong noi can dung cho 6 trang Class Diagram cua Stadium Ticket Booking Simulation.

## 1. Quy uoc quan he

| Ky hieu | Loai quan he | Cach doc |
|---|---|---|
| `A --> B` | Directed association / uses | `A` giu tham chieu den hoac goi `B`. Thuong dung cho View -> Controller -> Service -> Repository. |
| `A ..> B` | Dependency | `A` su dung tam thoi `B`, nhung khong so huu nhu mot field nghiep vu lau dai. |
| `A --|> B` | Generalization / inheritance | `A extends B`. Mui ten tam giac huong ve class cha `B`. |
| `I <|.. C` | Realization / implements | `C implements I`. Mui ten tam giac huong ve interface `I`. |
| `A *-- B` | Composition | `B` la thanh phan thuoc vong doi cua `A`. Hinh thoi dac dat o phia `A`. |
| `A o-- B` | Aggregation | `A` tap hop `B`, nhung `B` co the ton tai doc lap. Bo so do hien tai khong can dung quan he nay. |
| `"1"`, `"0..1"`, `"0..*"`, `"1..*"` | Multiplicity | So luong object o moi dau quan he. |

Nguyen tac doc dependency layer:

```text
View --> Controller --> Service --> Repository --> Model/CSV
```

DTO chi la boundary object trong parameter/return type. Khong can noi DTO vao tat ca class neu lam so do roi; dependency DTO duoc liet ke rieng trong tung trang.

---

## 2. Page 1 - Main, View and Authentication

Nguon: `01_Main_View_Auth.mmd`.

### 2.1 Navigation va View dependencies

| From | To | Quan he | Ly do |
|---|---|---|---|
| `Main` | `MainView` | `-->` Directed association | Entry point khoi tao va chay giao dien Console chinh. |
| `MainView` | `AuthController` | `-->` Directed association | Login, logout, current user va update profile. |
| `MainView` | `FanController` | `-->` Directed association | Register Fan va cac thao tac Fan co ban. |
| `MainView` | `BrowseController` | `-->` Directed association | Search/filter match va xem seat map tu guest menu. |
| `MainView` | `BookingView` | `-->` Directed association | Dieu huong Fan vao booking/payment/history/ticket. |
| `MainView` | `SellerView` | `-->` Directed association | Dieu huong menu Seller. Quan he nay the hien bang field/constructor va nen noi khi ve full dependency. |
| `MainView` | `GateView` | `-->` Directed association | Dieu huong menu Gate Staff. |
| `MainView` | `AdminManagementView` | `-->` Directed association | Dieu huong menu Administrator. |
| `MainView` | `SimulatorView` | `-->` Directed association | Dieu huong menu Simulator Operator. |
| `BookingView` | `BookingController` | `-->` Directed association | Tao booking, payment va xem booking history. |
| `BookingView` | `RefundController` | `-->` Directed association | Gui refund request. |
| `BookingView` | `SeatMapView` | `-->` Directed association | Hien thi va chon ghe. |
| `BookingView` | `FanController` | `-->` Directed association | Lay danh sach ticket cua Fan. |
| `BookingView` | `TicketController` | `-->` Directed association | Lay chi tiet ticket. |

### 2.2 Controller, Service va Repository

| From | To | Quan he | Ly do |
|---|---|---|---|
| `AuthController` | `AuthService` | `-->` Directed association | Controller delegate authentication/session workflow. |
| `FanController` | `FanService` | `-->` Directed association | Controller delegate register/search/ticket workflow. |
| `BrowseController` | `BrowseService` | `-->` Directed association | Controller delegate browse workflow. |
| `AuthService` | `FanRepository` | `-->` Directed association | Tim Fan theo email va cap nhat profile. |
| `AuthService` | `StaffRepository` | `-->` Directed association | Tim Staff theo email khi login. |
| `AuthService` | `User` | `-->` Association | Giu `currentUser` cho session Console hien tai. |
| `FanService` | `FanRepository` | `-->` Directed association | Tao va tim Fan. |
| `FanService` | `BookingRepository` | `-->` Directed association | Truy van du lieu booking cua Fan khi can. |
| `FanService` | `TicketRepository` | `-->` Directed association | Lay ticket cua Fan. |
| `BrowseService` | `MatchRepository` | `-->` Directed association | Search/filter/view match. |
| `BrowseService` | `MatchSeatRepository` | `-->` Directed association | Lay seat availability theo match. |

### 2.3 Inheritance

| Child | Parent | Quan he | Java |
|---|---|---|---|
| `Fan` | `User` | `--|>` Generalization | `Fan extends User` |
| `Staff` | `User` | `--|>` Generalization | `Staff extends User` |
| `User` | `BaseEntity` | `--|>` Generalization | `User extends BaseEntity` |
| `Match` | `BaseEntity` | `--|>` Generalization | `Match extends BaseEntity` |
| `MatchSeat` | `BaseEntity` | `--|>` Generalization | `MatchSeat extends BaseEntity` |

### 2.4 DTO method dependencies

Khong bat buoc ve mui ten cho nhom nay. Neu can full traceability, dung dependency net dut `..>`.

| Class su dung DTO | DTO |
|---|---|
| `AuthController`, `AuthService` | `LoginRequestDto`, `ProfileUpdateRequestDto`, `UserResponseDto` |
| `FanController`, `FanService` | `RegisterFanRequestDto`, `UserResponseDto`, `TicketResponseDto` |
| `BrowseController`, `BrowseService` | `MatchResponseDto`, `MatchSeatResponseDto` |
| `BookingView` | `BookingRequestDto`, `BookingResponseDto` |

---

## 3. Page 2 - Stadium, Match and Administrator

Nguon: `02_Stadium_Match_Admin.mmd`.

### 3.1 View den Controller

| From | To | Quan he | Ly do |
|---|---|---|---|
| `AdminManagementView` | `StadiumController` | `-->` Directed association | Stadium, Section va Seat management. |
| `AdminManagementView` | `MatchController` | `-->` Directed association | Match, ticket price va ticket sales management. |
| `AdminManagementView` | `AccountAdminController` | `-->` Directed association | Fan, Staff va Role management. |
| `AdminManagementView` | `AdminController` | `-->` Directed association | Dataset, system summary va audit log. |
| `AdminManagementView` | `RefundController` | `-->` Directed association | Review refund request. |

### 3.2 Controller den Service

| From | To | Quan he |
|---|---|---|
| `StadiumController` | `StadiumService` | `-->` Directed association |
| `MatchController` | `MatchService` | `-->` Directed association |
| `AccountAdminController` | `AccountAdminService` | `-->` Directed association |
| `AdminController` | `AdminService` | `-->` Directed association |
| `RefundController` | `RefundService` | `-->` Directed association |

### 3.3 Service den Repository/Helper

| From | To | Quan he | Ly do |
|---|---|---|---|
| `StadiumService` | `StadiumRepository` | `-->` Directed association | CRUD Stadium. |
| `StadiumService` | `SectionRepository` | `-->` Directed association | CRUD Section. |
| `StadiumService` | `SeatRepository` | `-->` Directed association | CRUD va activate/deactivate Seat. |
| `MatchService` | `MatchRepository` | `-->` Directed association | CRUD Match va sale status. |
| `MatchService` | `TicketPriceRepository` | `-->` Directed association | CRUD TicketPrice. |
| `MatchService` | `MatchSeatRepository` | `-->` Directed association | Khoi tao/kiem tra seat inventory cua match. |
| `AccountAdminService` | `FanRepository` | `-->` Directed association | Quan ly Fan account. |
| `AccountAdminService` | `StaffRepository` | `-->` Directed association | Quan ly Staff va role. |
| `AdminService` | `DataGenerator` | `-->` Directed association | Generate CSV dataset. |
| `AdminService` | `FanRepository` | `-->` Directed association | Dem Fan cho system summary. |
| `AdminService` | `StaffRepository` | `-->` Directed association | Dem Staff cho system summary. |
| `AdminService` | `StadiumRepository` | `-->` Directed association | Dem Stadium cho system summary. |
| `AdminService` | `MatchRepository` | `-->` Directed association | Dem Match cho system summary. |
| `AdminService` | `BookingRepository` | `-->` Directed association | Dem Booking cho system summary. |
| `AdminService` | `TicketRepository` | `-->` Directed association | Dem Ticket cho system summary. |
| `AdminService` | `AuditLogRepository` | `-->` Directed association | Doc audit log. |
| `RefundService` | `RefundRepository` | `-->` Directed association | Tao, tim va update RefundRequest. |
| `RefundService` | `BookingRepository` | `-->` Directed association | Kiem tra booking khi request/review refund. |

### 3.4 Domain relationships

| From | To | Quan he | Multiplicity va y nghia |
|---|---|---|---|
| `Stadium` | `Section` | `*--` Composition | Mot Stadium co `0..*` Section; moi Section thuoc `1` Stadium. |
| `Section` | `Seat` | `*--` Composition | Mot Section co `0..*` Seat; moi Seat thuoc `1` Section. |
| `Match` | `Stadium` | `-->` Association | Nhieu Match (`0..*`) dien ra tai mot Stadium (`1`). |
| `Match` | `MatchSeat` | `*--` Composition | Mot Match co `0..*` trang thai ghe rieng theo tran. |
| `MatchSeat` | `Seat` | `-->` Association | Nhieu MatchSeat tham chieu mot Seat vat ly. |
| `TicketPrice` | `Match` | `-->` Association | Nhieu TicketPrice thuoc mot Match. |
| `TicketPrice` | `Section` | `-->` Association | Nhieu TicketPrice ap dung cho mot Section. Quan he day du nam o Page 6. |

### 3.5 DTO method dependencies

| Class su dung DTO | DTO |
|---|---|
| `StadiumController`, `StadiumService` | `StadiumRequestDto`, `StadiumResponseDto` |
| `MatchController`, `MatchService` | `MatchRequestDto`, `MatchResponseDto`, `TicketPriceRequestDto` |
| `AccountAdminController`, `AccountAdminService` | `AccountRequestDto`, `AccountResponseDto` |
| `AdminController`, `AdminService` | `SystemSummaryDto` |
| `RefundController`, `RefundService` | `RefundRequestDto`, `RefundReviewRequestDto`, `RefundResponseDto` |

---

## 4. Page 3 - User, Booking, Payment and Ticket

Nguon: `03_User_Booking_Payment_Ticket.mmd`.

### 4.1 View den Controller/View

| From | To | Quan he | Ly do |
|---|---|---|---|
| `SeatMapView` | `BrowseController` | `-->` Directed association | Lay seat map/availability. |
| `BookingView` | `BookingController` | `-->` Directed association | Booking, payment va booking history. |
| `BookingView` | `RefundController` | `-->` Directed association | Gui refund request. |
| `BookingView` | `SeatMapView` | `-->` Directed association | Mo luong chon ghe. |
| `BookingView` | `FanController` | `-->` Directed association | Xem ticket list cua Fan. |
| `BookingView` | `TicketController` | `-->` Directed association | Xem ticket detail. |
| `SellerView` | `BookingController` | `-->` Directed association | Booking ho Fan, offline payment va issue ticket. |
| `SellerView` | `FanController` | `-->` Directed association | Search/create Fan. |
| `GateView` | `TicketController` | `-->` Directed association | Validate va check-in ticket. |

### 4.2 Controller den Service

| From | To | Quan he |
|---|---|---|
| `BrowseController` | `BrowseService` | `-->` Directed association |
| `FanController` | `FanService` | `-->` Directed association |
| `BookingController` | `BookingService` | `-->` Directed association |
| `TicketController` | `TicketService` | `-->` Directed association |
| `RefundController` | `RefundService` | `-->` Directed association |

### 4.3 Service den Repository/Helper

| From | To | Quan he | Ly do |
|---|---|---|---|
| `BrowseService` | `MatchRepository` | `-->` Directed association | Search/filter/view match. |
| `BrowseService` | `MatchSeatRepository` | `-->` Directed association | Xem seat availability. |
| `FanService` | `FanRepository` | `-->` Directed association | Register va search Fan. |
| `FanService` | `BookingRepository` | `-->` Directed association | Lay booking data lien quan Fan. |
| `FanService` | `TicketRepository` | `-->` Directed association | Lay ticket history. |
| `BookingService` | `BookingRepository` | `-->` Directed association | Tao/update/query Booking. |
| `BookingService` | `SeatRepository` | `-->` Directed association | Xac dinh Section cua Seat de tinh gia. |
| `BookingService` | `TicketPriceRepository` | `-->` Directed association | Lay gia theo Match + Section. |
| `BookingService` | `PaymentRepository` | `-->` Directed association | Luu online/offline payment. |
| `BookingService` | `TicketRepository` | `-->` Directed association | Phat hanh Ticket sau payment hop le. |
| `BookingService` | `TransactionRepository` | `-->` Directed association | Ghi ket qua booking attempt/concurrency transaction. |
| `BookingService` | `StrategyFactory` | `-->` Directed association | Chon synchronization mechanism. |
| `BookingService` | `MockPaymentService` | `-->` Directed association | Gia lap external online payment service. |
| `TicketService` | `TicketRepository` | `-->` Directed association | Tim, validate va update check-in Ticket. |
| `RefundService` | `RefundRepository` | `-->` Directed association | Tao va review refund request. |
| `RefundService` | `BookingRepository` | `-->` Directed association | Xac minh booking co du dieu kien refund. |
| `StrategyFactory` | `MatchSeatRepository` | `-->` Directed association | Inject repository vao strategy duoc tao. |
| `StrategyFactory` | `TransactionRepository` | `-->` Directed association | Inject transaction persistence vao strategy. |

### 4.4 Domain relationships

| From | To | Quan he | Multiplicity va y nghia |
|---|---|---|---|
| `Booking` | `Fan` | `-->` Association | Mot Fan co `0..*` Booking; moi Booking thuoc `1` Fan. |
| `Booking` | `MatchSeat` | `-->` Association | Mot Booking chon `1..4` MatchSeat; mot MatchSeat thuoc toi da `0..1` Booking. |
| `Booking` | `Payment` | `-->` Association | Mot Booking co `0..1` Payment. |
| `Booking` | `Ticket` | `*--` Composition | Mot Booking so huu `0..4` Ticket. |
| `RefundRequest` | `Booking` | `-->` Association | Nhieu RefundRequest tham chieu mot Booking. Business rule nen gioi han request active. |

### 4.5 DTO method dependencies

| Class su dung DTO | DTO |
|---|---|
| `BrowseController`, `BrowseService` | `MatchResponseDto`, `MatchSeatResponseDto` |
| `FanController`, `FanService` | `RegisterFanRequestDto`, `UserResponseDto`, `TicketResponseDto` |
| `BookingController`, `BookingService` | `BookingRequestDto`, `BookingResponseDto`, `PaymentRequestDto`, `PaymentResponseDto`, `TicketResponseDto` |
| `TicketController`, `TicketService` | `TicketResponseDto` |
| `RefundController`, `RefundService` | `RefundRequestDto`, `RefundReviewRequestDto`, `RefundResponseDto` |

---

## 5. Page 4 - Concurrency Simulation

Nguon: `04_Simulation.mmd`.

### 5.1 View, Controller va Service

| From | To | Quan he | Ly do |
|---|---|---|---|
| `SimulatorView` | `SimulationController` | `-->` Directed association | Configure va run simulation. |
| `ReportView` | `SimulationController` | `-->` Directed association | View, compare va export result. |
| `SimulationController` | `SimulationService` | `-->` Directed association | Delegate toan bo simulation workflow. |
| `SimulationService` | `SimulationRepository` | `-->` Directed association | Luu/tim simulation configuration. |
| `SimulationService` | `SimulationResultRepository` | `-->` Directed association | Luu, doc va export result. |
| `SimulationService` | `TransactionRepository` | `-->` Directed association | Thu thap cac booking attempt. |
| `SimulationService` | `StrategyFactory` | `-->` Directed association | Tao strategy theo mechanism. |
| `SimulationService` | `BookingTask` | `..>` Dependency | Tao va submit concurrent tasks vao `ExecutorService`. |
| `SimulationService.runSimulation()` | `ExecutorService` | Local implementation detail | Tao pool khi chay simulation va dong pool truoc khi ham ket thuc; khong giu thanh field hay noi association tren class diagram. |

### 5.2 Strategy dependencies

| From | To | Quan he | Ly do |
|---|---|---|---|
| `BookingTask` | `BookingStrategy` | `-->` Directed association | Moi task giu mot strategy va goi `execute`. |
| `NoLockStrategy` | `BookingStrategy` | `..|>` Realization | `NoLockStrategy implements BookingStrategy`. |
| `SynchronizedStrategy` | `BookingStrategy` | `..|>` Realization | `SynchronizedStrategy implements BookingStrategy`. |
| `FileLockStrategy` | `BookingStrategy` | `..|>` Realization | `FileLockStrategy implements BookingStrategy`. |
| `OptimisticLockStrategy` | `BookingStrategy` | `..|>` Realization | `OptimisticLockStrategy implements BookingStrategy`. |
| `NoLockStrategy` | `MatchSeatRepository` | `-->` Directed association | Update seat khong lock. |
| `NoLockStrategy` | `TransactionRepository` | `-->` Directed association | Ghi attempt result. |
| `SynchronizedStrategy` | `MatchSeatRepository` | `-->` Directed association | Update trong synchronized critical section. |
| `SynchronizedStrategy` | `TransactionRepository` | `-->` Directed association | Ghi attempt result. |
| `FileLockStrategy` | `MatchSeatRepository` | `-->` Directed association | Update bang file lock. |
| `FileLockStrategy` | `TransactionRepository` | `-->` Directed association | Ghi attempt result. |
| `OptimisticLockStrategy` | `MatchSeatRepository` | `-->` Directed association | Compare version va retry. |
| `OptimisticLockStrategy` | `TransactionRepository` | `-->` Directed association | Ghi success/conflict/failure. |
| `StrategyFactory` | `MatchSeatRepository` | `-->` Directed association | Inject dependency vao strategy. |
| `StrategyFactory` | `TransactionRepository` | `-->` Directed association | Inject dependency vao strategy. |
| `BookingTask` | `CountDownLatch` | `-->` Association | Dong bo thoi diem bat dau cua cac thread. Java library class. |

Trong Mermaid hien tai, realization duoc viet theo huong interface truoc:

```text
BookingStrategy <|.. NoLockStrategy
BookingStrategy <|.. SynchronizedStrategy
BookingStrategy <|.. FileLockStrategy
BookingStrategy <|.. OptimisticLockStrategy
```

### 5.3 Simulation Model relationships

| From | To | Quan he | Multiplicity va y nghia |
|---|---|---|---|
| `Simulation` | `SimulationResult` | `-->` Association | Mot Simulation co `0..*` result, thuong mot result cho moi mechanism. |
| `Simulation` | `BookingTransaction` | `-->` Association | Mot Simulation tao `0..*` concurrent booking attempts. |

### 5.4 DTO method dependencies

| Class su dung DTO | DTO |
|---|---|
| `SimulatorView`, `SimulationController`, `SimulationService` | `SimulationRequestDto`, `SimulationResponseDto`, `SimulationResultDto` |
| `ReportView` | `SimulationResultDto` |

---

## 6. Page 5 - CSV Repository, BaseEntity and Exceptions

Nguon: `05_CSV_Repository_BaseEntity.mmd`.

### 6.1 Repository inheritance

Moi concrete repository sau phai ke thua `CsvRepository<EntityType>`. Khi code Java, khong dung literal `CsvRepository<T>` trong concrete class.

| Child | Parent khi code | Quan he |
|---|---|---|
| `FanRepository` | `CsvRepository<Fan>` | `--|>` Generalization |
| `StaffRepository` | `CsvRepository<Staff>` | `--|>` Generalization |
| `StadiumRepository` | `CsvRepository<Stadium>` | `--|>` Generalization |
| `SectionRepository` | `CsvRepository<Section>` | `--|>` Generalization |
| `SeatRepository` | `CsvRepository<Seat>` | `--|>` Generalization |
| `MatchRepository` | `CsvRepository<Match>` | `--|>` Generalization |
| `BookingRepository` | `CsvRepository<Booking>` | `--|>` Generalization |
| `PaymentRepository` | `CsvRepository<Payment>` | `--|>` Generalization |
| `TicketRepository` | `CsvRepository<Ticket>` | `--|>` Generalization |
| `TransactionRepository` | `CsvRepository<BookingTransaction>` | `--|>` Generalization |
| `SimulationRepository` | `CsvRepository<Simulation>` | `--|>` Generalization |
| `SimulationResultRepository` | `CsvRepository<SimulationResult>` | `--|>` Generalization |
| `RefundRepository` | `CsvRepository<RefundRequest>` | `--|>` Generalization |
| `AuditLogRepository` | `CsvRepository<AuditLog>` | `--|>` Generalization |

`MatchSeatRepository` va `TicketPriceRepository` la repository custom voi composite lookup/update behavior, nen co the khong ke thua generic repository neu nhom muon giu code don gian.

### 6.2 Repository den Model

Moi generic parameter tao mot dependency compile-time tu Repository den Model tuong ung:

| From | To | Quan he |
|---|---|---|
| `FanRepository` | `Fan` | `..>` Dependency |
| `StaffRepository` | `Staff` | `..>` Dependency |
| `StadiumRepository` | `Stadium` | `..>` Dependency |
| `SectionRepository` | `Section` | `..>` Dependency |
| `SeatRepository` | `Seat` | `..>` Dependency |
| `MatchRepository` | `Match` | `..>` Dependency |
| `BookingRepository` | `Booking` | `..>` Dependency |
| `PaymentRepository` | `Payment` | `..>` Dependency |
| `TicketRepository` | `Ticket` | `..>` Dependency |
| `TransactionRepository` | `BookingTransaction` | `..>` Dependency |
| `SimulationRepository` | `Simulation` | `..>` Dependency |
| `SimulationResultRepository` | `SimulationResult` | `..>` Dependency |
| `RefundRepository` | `RefundRequest` | `..>` Dependency |
| `AuditLogRepository` | `AuditLog` | `..>` Dependency |
| `MatchSeatRepository` | `MatchSeat` | `..>` Dependency |
| `TicketPriceRepository` | `TicketPrice` | `..>` Dependency |
| `DataGenerator` | `CsvRepository<T>` | `..>` Dependency | Generator tao du lieu thong qua CSV repositories. |

### 6.3 Model inheritance

| Child | Parent | Quan he |
|---|---|---|
| `User` | `BaseEntity` | `--|>` Generalization |
| `Fan` | `User` | `--|>` Generalization |
| `Staff` | `User` | `--|>` Generalization |
| `Stadium` | `BaseEntity` | `--|>` Generalization |
| `Section` | `BaseEntity` | `--|>` Generalization |
| `Seat` | `BaseEntity` | `--|>` Generalization |
| `Match` | `BaseEntity` | `--|>` Generalization |
| `MatchSeat` | `BaseEntity` | `--|>` Generalization |
| `Booking` | `BaseEntity` | `--|>` Generalization |
| `Payment` | `BaseEntity` | `--|>` Generalization |
| `Ticket` | `BaseEntity` | `--|>` Generalization |
| `BookingTransaction` | `BaseEntity` | `--|>` Generalization |
| `Simulation` | `BaseEntity` | `--|>` Generalization |
| `SimulationResult` | `BaseEntity` | `--|>` Generalization |
| `RefundRequest` | `BaseEntity` | `--|>` Generalization |
| `AuditLog` | `BaseEntity` | `--|>` Generalization |

`TicketPrice` dung cap khoa `(matchId, sectionId)` nen khong bat buoc ke thua `BaseEntity`.

### 6.4 Exception inheritance

| Child | Parent | Quan he |
|---|---|---|
| `AppException` | `RuntimeException` | `--|>` Generalization |

`AppException` co association mot chieu toi `ErrorCode`. Moi loi duoc phan loai
bang ma noi bo nhu `INVALID_INPUT`, `NOT_FOUND` hoac `UNKNOWN_ERROR`; du an
Console khong su dung HTTP status.

### 6.5 CSV parsing/formatting contract

| From | To | Quan he | Ghi chu |
|---|---|---|---|
| Concrete Repository | `CsvRepository<T>` | Override contract | `parseLine(String)` chuyen CSV -> Entity. |
| Concrete Repository | `CsvRepository<T>` | Override contract | `formatLine(T)` chuyen Entity -> CSV. |

---

## 7. Page 6 - Complete Model Relationships

Nguon: `06_Model_Relationships.mmd`.

Day la trang chuan de trinh bay association, composition va multiplicity cua domain.

| From | To | Quan he | Multiplicity va y nghia |
|---|---|---|---|
| `Fan` | `User` | `--|>` Generalization | `Fan extends User`. |
| `Staff` | `User` | `--|>` Generalization | `Staff extends User`. |
| `Stadium` | `Section` | `*--` Composition | `Stadium 1` so huu `Section 0..*`. |
| `Section` | `Seat` | `*--` Composition | `Section 1` so huu `Seat 0..*`. |
| `Match` | `Stadium` | `-->` Association | Moi Match o `1` Stadium; Stadium co `0..*` Match. |
| `Match` | `MatchSeat` | `*--` Composition | Match co `0..*` MatchSeat inventory records. |
| `MatchSeat` | `Seat` | `-->` Association | Moi MatchSeat tham chieu `1` Seat; mot Seat xuat hien o `0..*` MatchSeat. |
| `TicketPrice` | `Match` | `-->` Association | Moi price thuoc `1` Match; Match co `0..*` price. |
| `TicketPrice` | `Section` | `-->` Association | Moi price thuoc `1` Section; Section co `0..*` price. |
| `Booking` | `Fan` | `-->` Association | Moi Booking thuoc `1` Fan; Fan co `0..*` Booking. |
| `Booking` | `Match` | `-->` Association | Moi Booking thuoc `1` Match; Match co `0..*` Booking. |
| `Booking` | `MatchSeat` | `-->` Association | Booking co `1..4` MatchSeat; moi MatchSeat thuoc toi da `0..1` Booking. |
| `Booking` | `Payment` | `-->` Association | Booking co `0..1` Payment; Payment thuoc `1` Booking. |
| `Booking` | `Ticket` | `*--` Composition | Booking so huu `0..4` Ticket. |
| `Booking` | `BookingTransaction` | `-->` Association | Booking co `1..*` transaction records; simulation attempt co the chua co Booking. |
| `RefundRequest` | `Booking` | `-->` Association | Moi RefundRequest tham chieu `1` Booking. |
| `RefundRequest` | `Fan` | `-->` Association | Moi RefundRequest duoc tao boi `1` Fan. |
| `RefundRequest` | `Staff` | `-->` Association | RefundRequest co `0..1` reviewer Staff. |
| `Ticket` | `MatchSeat` | `-->` Association | Moi Ticket gan `1` MatchSeat; moi MatchSeat co toi da `0..1` Ticket. |
| `AuditLog` | `User` | `-->` Association | Moi AuditLog co `1` actor User; User co `0..*` log. |
| `Simulation` | `Match` | `-->` Association | Moi Simulation chay tren `1` Match; Match co `0..*` Simulation. |
| `Simulation` | `SimulationResult` | `-->` Association | Simulation co `0..*` result. |
| `Simulation` | `BookingTransaction` | `-->` Association | Simulation co `0..*` attempt transaction. |

---

## 8. Thu tu noi day khi ve lai

De so do de doc va it day cat nhau, noi theo thu tu:

1. Noi inheritance/realization truoc: Model -> BaseEntity, Fan/Staff -> User, Strategy -> interface, Exception -> RuntimeException.
2. Noi duong layer theo chieu tren xuong: View -> Controller -> Service -> Repository.
3. Noi composition domain: Stadium-Section-Seat, Match-MatchSeat, Booking-Ticket.
4. Noi association co multiplicity con lai.
5. Them dependency net dut den Helper/Java concurrency classes.
6. Khong noi tung DTO bang mui ten trong so do chinh; signature method da the hien dependency DTO.

## 9. Luu y de code dung voi dependency

- Constructor injection theo dung field trong diagram; khong `new Repository()` ben trong Controller.
- Controller khong doc/ghi CSV.
- Service khong thao tac `Path` truc tiep, tru luong export simulation neu da thiet ke nhu vay.
- Concrete repository code theo `CsvRepository<Entity>`, khong phai `CsvRepository<T>`.
- `MatchSeatRepository` la noi duy nhat thuc hien atomic seat update cho cac synchronization strategy.
- `SimulationService` va booking thuong phai dung cung `StrategyFactory`/seat update rules de ket qua simulation phan anh he thong that.
- Online payment phai xac minh amount tu `Booking.totalAmount`, khong tin amount do View nhap.
