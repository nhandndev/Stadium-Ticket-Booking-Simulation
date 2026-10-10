# Danh sách cần chỉnh trong Use Case và Class Diagram

Đối chiếu **các bảng đang hiển thị** trên 7 trang của `hihi.drawio` với code hiện tại và kịch bản các chặng đã chốt. File này chỉ liệt kê thao tác sửa sơ đồ; **chưa sửa `hihi.drawio`**. Ký hiệu `-` là thuộc tính private, `+` là phương thức public.

**Thời điểm:** `Sửa ngay` = code đã có. `Khi làm chặng N` = mới có trong kịch bản, chỉ cập nhật sơ đồ nộp khi code tương ứng đã chốt. Một class xuất hiện ở nhiều trang thì sửa **tất cả bản sao được nêu**.

## Trang 00 — `00_System_Overview_UseCase`

**Khi làm chặng 07**

- **Use Case / Administrator / Fan Management:** hiện **thiếu** `Create Fan Account` → **thêm** oval `Create Fan Account`, nối với `Administrator`.
- **Use Case / Administrator / Fan Management:** hiện **thiếu** `Search Fans` → **thêm** oval `Search Fans`, nối với `Administrator`.
- **Use Case / Administrator / Fan Management:** hiện **thiếu** `Filter Fans` → **thêm** oval `Filter Fans`, nối với `Administrator`.
- **Use Case / Seller:** `Search Fan` và `Create Fan` → **giữ nguyên**, không thay bằng ba oval của Administrator.

## Trang 01 — `01_Account_Public_Browsing`

**Sửa ngay**

- **Class `MainView`:** thiếu `-scanner: Scanner` → thêm `-scanner: Scanner`.
- **Class `MainView`:** constructor hiện tại không có `scanner: Scanner` → thêm tham số đó **đầu constructor**. Chữ ký cuối cùng đầy đủ nằm ở mục chặng 10 bên dưới.

**Khi làm chặng 06**

- **Class `FanService`:** thiếu `-staffRepository: StaffRepository` → thêm field này.
- **Class `FanService`:** `FanService(fanRepository: FanRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)` → `FanService(fanRepository: FanRepository, staffRepository: StaffRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)`.
- **Class `FanController`:** thiếu `-authController: AuthController` → thêm field này.
- **Class `FanController`:** `FanController(fanService: FanService)` → `FanController(fanService: FanService, authController: AuthController)`.

**Khi làm chặng 08–10**

- **Class `BookingView`:** thiếu `-scanner: Scanner`, `-authController: AuthController` → thêm hai field.
- **Class `BookingView`:** `BookingView(bookingController: BookingController, refundController: RefundController, seatMapView: SeatMapView, fanController: FanController, ticketController: TicketController)` → `BookingView(scanner: Scanner, authController: AuthController, bookingController: BookingController, refundController: RefundController, seatMapView: SeatMapView, fanController: FanController, ticketController: TicketController)`.
- **Class `TicketRepository`:** thiếu `-bookingRepository: BookingRepository` → thêm field này.
- **Class `TicketRepository`:** `TicketRepository(filePath: Path)` → `TicketRepository(filePath: Path, bookingRepository: BookingRepository)`.
- **Class `MainView`:** thiếu `-reportView: ReportView`, `-simulationController: SimulationController` → thêm hai field.
- **Class `MainView`:**

```text
TỪ:  +MainView(authController: AuthController, browseController: BrowseController, fanController: FanController, bookingView: BookingView, sellerView: SellerView, gateView: GateView, adminManagementView: AdminManagementView, simulatorView: SimulatorView)
THÀNH: +MainView(scanner: Scanner, authController: AuthController, browseController: BrowseController, fanController: FanController, bookingView: BookingView, sellerView: SellerView, gateView: GateView, adminManagementView: AdminManagementView, simulatorView: SimulatorView, reportView: ReportView, simulationController: SimulationController)
```

## Trang 02 — `02_Stadium_Match_Admin`

**Sửa ngay**

- **Class `StadiumRequestDto`:** thiếu `+StadiumRequestDto()` → thêm constructor rỗng.
- **Class `StadiumRequestDto`:** thiếu `+setName(name: String): void`, `+setAddress(address: String): void` → thêm hai setter.
- **Class `StadiumResponseDto`:** thiếu `+StadiumResponseDto()` → thêm constructor rỗng.
- **Class `StadiumResponseDto`:** thiếu `+setId(id: Long): void`, `+setName(name: String): void`, `+setAddress(address: String): void` → thêm ba setter.

**Khi làm chặng 07–08**

- **Class `StadiumService`:** thiếu `-matchRepository: MatchRepository` → thêm field.
- **Class `StadiumService`:** `StadiumService(stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository)` → `StadiumService(stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository, matchRepository: MatchRepository)`.
- **Class `StadiumController`:** thiếu `-authController: AuthController`, `-auditLogRepository: AuditLogRepository` → thêm hai field; `StadiumController(stadiumService: StadiumService)` → `StadiumController(stadiumService: StadiumService, authController: AuthController, auditLogRepository: AuditLogRepository)`.
- **Class `MatchService`:** thiếu `-stadiumRepository: StadiumRepository`, `-sectionRepository: SectionRepository`, `-seatRepository: SeatRepository`, `-bookingRepository: BookingRepository` → thêm bốn field.
- **Class `MatchService`:** `MatchService(matchRepository: MatchRepository, ticketPriceRepository: TicketPriceRepository, matchSeatRepository: MatchSeatRepository)` → `MatchService(matchRepository: MatchRepository, ticketPriceRepository: TicketPriceRepository, matchSeatRepository: MatchSeatRepository, stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository, bookingRepository: BookingRepository)`.
- **Class `MatchController`:** thiếu `-authController: AuthController`, `-auditLogRepository: AuditLogRepository` → thêm hai field; `MatchController(matchService: MatchService)` → `MatchController(matchService: MatchService, authController: AuthController, auditLogRepository: AuditLogRepository)`.
- **Class `AccountAdminService`:** thiếu `-bookingRepository: BookingRepository`, `-authController: AuthController` → thêm hai field; `AccountAdminService(fanRepository: FanRepository, staffRepository: StaffRepository)` → `AccountAdminService(fanRepository: FanRepository, staffRepository: StaffRepository, bookingRepository: BookingRepository, authController: AuthController)`.
- **Class `AccountAdminController`:** thiếu `-authController: AuthController`, `-auditLogRepository: AuditLogRepository` → thêm hai field; `AccountAdminController(accountAdminService: AccountAdminService)` → `AccountAdminController(accountAdminService: AccountAdminService, authController: AuthController, auditLogRepository: AuditLogRepository)`.
- **Class `AdminService`:** thiếu `-authController: AuthController` → thêm field; constructor hiện tại → thêm `authController: AuthController` **cuối danh sách tham số**.
- **Class `AdminController`:** thiếu `-authController: AuthController` → thêm field; `AdminController(adminService: AdminService)` → `AdminController(adminService: AdminService, authController: AuthController)`.
- **Class `AdminService` và `AdminController`:** đang có `+generateCsvDataset(recordCount: int): void` nhưng thiếu thao tác dọn dữ liệu mẫu → thêm `+clearGeneratedData(): void` vào **cả hai** bảng.
- **Class `AdminManagementView`:** thiếu `-scanner: Scanner`, `-authController: AuthController` → thêm hai field.
- **Class `AdminManagementView`:** `AdminManagementView(stadiumController: StadiumController, matchController: MatchController, accountAdminController: AccountAdminController, adminController: AdminController, refundController: RefundController)` → `AdminManagementView(scanner: Scanner, stadiumController: StadiumController, matchController: MatchController, accountAdminController: AccountAdminController, adminController: AdminController, refundController: RefundController, authController: AuthController)`.
- **Class `RefundService`:** thiếu `-paymentRepository: PaymentRepository`, `-ticketRepository: TicketRepository`, `-matchRepository: MatchRepository`, `-matchSeatRepository: MatchSeatRepository` → thêm bốn field; `RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository)` → `RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository, paymentRepository: PaymentRepository, ticketRepository: TicketRepository, matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository)`; thêm `+getPendingRefunds(): List<RefundRequest>`.
- **Class `RefundController`:** thiếu `-authController: AuthController` → thêm field; `RefundController(refundService: RefundService)` → `RefundController(refundService: RefundService, authController: AuthController)`; thêm `+getPendingRefunds(): List<RefundRequest>`.
- **Class `TicketRepository`:** thiếu `-bookingRepository: BookingRepository` → thêm field; `TicketRepository(filePath: Path)` → `TicketRepository(filePath: Path, bookingRepository: BookingRepository)`.

## Trang 03 — `03_Booking_Payment_Ticket`

**Khi làm chặng 06–09**

- **Class `FanService`:** thiếu `-staffRepository: StaffRepository` → thêm field; `FanService(fanRepository: FanRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)` → `FanService(fanRepository: FanRepository, staffRepository: StaffRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)`.
- **Class `FanController`:** thiếu `-authController: AuthController` → thêm field; `FanController(fanService: FanService)` → `FanController(fanService: FanService, authController: AuthController)`.
- **Class `BookingView`:** thiếu `-scanner: Scanner`, `-authController: AuthController` → thêm hai field; `BookingView(bookingController: BookingController, refundController: RefundController, seatMapView: SeatMapView, fanController: FanController, ticketController: TicketController)` → `BookingView(scanner: Scanner, authController: AuthController, bookingController: BookingController, refundController: RefundController, seatMapView: SeatMapView, fanController: FanController, ticketController: TicketController)`.
- **Class `TicketRepository`:** thiếu `-bookingRepository: BookingRepository` → thêm field; `TicketRepository(filePath: Path)` → `TicketRepository(filePath: Path, bookingRepository: BookingRepository)`.
- **Class `BookingService`:** thiếu `-fanRepository: FanRepository`, `-matchRepository: MatchRepository`, `-matchSeatRepository: MatchSeatRepository`, `-sectionRepository: SectionRepository` → thêm bốn field; constructor hiện tại có 8 tham số → thêm bốn tham số này **cuối constructor theo đúng thứ tự vừa liệt kê**.
- **Class `BookingController`:** thiếu `-authController: AuthController` → thêm field; `BookingController(bookingService: BookingService)` → `BookingController(bookingService: BookingService, authController: AuthController)`.
- **Class `TicketService`:** thiếu `-bookingRepository: BookingRepository` → thêm field; `TicketService(ticketRepository: TicketRepository)` → `TicketService(ticketRepository: TicketRepository, bookingRepository: BookingRepository)`.
- **Class `TicketController`:** thiếu `-authController: AuthController`, `-bookingRepository: BookingRepository` → thêm hai field; `TicketController(ticketService: TicketService)` → `TicketController(ticketService: TicketService, authController: AuthController, bookingRepository: BookingRepository)`.
- **Class `RefundService`:** thiếu `-paymentRepository: PaymentRepository`, `-ticketRepository: TicketRepository`, `-matchRepository: MatchRepository`, `-matchSeatRepository: MatchSeatRepository` → thêm bốn field.
- **Class `RefundService`:** `RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository)` → `RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository, paymentRepository: PaymentRepository, ticketRepository: TicketRepository, matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository)`.
- **Class `RefundService`:** thiếu `+getPendingRefunds(): List<RefundRequest>` → thêm method.
- **Class `RefundController`:** thiếu `-authController: AuthController` → thêm field; `RefundController(refundService: RefundService)` → `RefundController(refundService: RefundService, authController: AuthController)`.
- **Class `RefundController`:** thiếu `+getPendingRefunds(): List<RefundRequest>` → thêm method.
- **Class `SellerView`:** thiếu `-scanner: Scanner`, `-authController: AuthController`, `-seatMapView: SeatMapView` → thêm ba field; `SellerView(fanController: FanController, bookingController: BookingController)` → `SellerView(scanner: Scanner, authController: AuthController, seatMapView: SeatMapView, fanController: FanController, bookingController: BookingController)`.
- **Class `GateView`:** thiếu `-scanner: Scanner`, `-authController: AuthController` → thêm hai field; `GateView(ticketController: TicketController)` → `GateView(scanner: Scanner, authController: AuthController, ticketController: TicketController)`.
- **Class `StrategyFactory`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `StrategyFactory(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository)` → `StrategyFactory(matchSeatRepository: MatchSeatRepository)`.

## Trang 04 — `04_Simulation`

**Khi làm chặng 10**

- **Class `StrategyFactory`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `StrategyFactory(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository)` → `StrategyFactory(matchSeatRepository: MatchSeatRepository)`.
- **Class `SynchronizedStrategy`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `SynchronizedStrategy(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository)` → `SynchronizedStrategy(matchSeatRepository: MatchSeatRepository)`.
- **Class `NoLockStrategy`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `NoLockStrategy(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository)` → `NoLockStrategy(matchSeatRepository: MatchSeatRepository)`.
- **Class `FileLockStrategy`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `FileLockStrategy(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository)` → `FileLockStrategy(matchSeatRepository: MatchSeatRepository)`.
- **Class `OptimisticLockStrategy`:** `-transactionRepository: TransactionRepository` → **bỏ field**; `OptimisticLockStrategy(matchSeatRepository: MatchSeatRepository, transactionRepository: TransactionRepository, maxRetries: int)` → `OptimisticLockStrategy(matchSeatRepository: MatchSeatRepository, maxRetries: int)`.
- **Class `BookingTask`:** đang có `+call(): BookingTransaction` nhưng chưa thể hiện interface → thêm quan hệ `BookingTask ..|> Callable<BookingTransaction>`.
- **Các dây từ `StrategyFactory`/bốn Strategy tới `TransactionRepository`:** nếu đang có dây nào → **bỏ đúng dây đó**.

## Trang 05 — `05_Repository_Persistence`

**Sửa ngay nếu sơ đồ nộp phải thể hiện toàn bộ class Java**

- **Class `AppException`:** chưa có bảng → thêm:

```text
AppException extends RuntimeException
-errorCode: ErrorCode
+AppException(errorCode: ErrorCode)
+AppException(errorCode: ErrorCode, message: String)
+getErrorCode(): ErrorCode
```

- **Enum `ErrorCode`:** chưa có bảng → thêm:

```text
<<enum>> ErrorCode
INVALID_INPUT, NOT_FOUND, UNKNOWN_ERROR, CSV_ERROR
-code: String
-message: String
+getCode(): String
+getMessage(): String
```


**Khi làm chặng 08**

- **Class `TicketRepository`:** thiếu `-bookingRepository: BookingRepository` → thêm field; `TicketRepository(filePath: Path)` → `TicketRepository(filePath: Path, bookingRepository: BookingRepository)`.

## Trang 06 — `06_Domain_Model`

- **Các class Model/enum:** không có dòng cần sửa sau lần đối chiếu này.
