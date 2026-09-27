# Layered Architecture

## Kien truc tong the

He thong dung MVC nhung nen chia ro hon theo cac layer:

```text
View
  -> Controller
    -> Request / Result Object
    -> Service / Use Case
      -> Domain Model
      -> Repository
        -> CSV File
      -> Infrastructure Utilities
```

Simulator khong nam rieng mot booking logic moi. Simulator tao concurrent tasks va goi vao booking core trong `BookingService`, khong goi full customer checkout/payment flow.

## Package goi y

```text
src/
  Main.java
  model/
    User.java
    Stadium.java
    Section.java
    Seat.java
    MatchSeat.java
    Match.java
    Fan.java
    Staff.java
    Booking.java
    Ticket.java
    Payment.java
    Simulation.java
    SimulationConfig.java
    SimulationResult.java
    TicketPrice.java
    BookingItem.java
    SeatHold.java
    Transaction.java
    RefundRequest.java
    CancellationRequest.java
    Notification.java
    AuditLog.java
    InconsistencyReport.java
  dto/
    BookingRequest.java
    BookingResult.java
    PaymentRequest.java
    PaymentResult.java
    SimulationConfig.java
  repository/
    StadiumRepository.java
    SectionRepository.java
    SeatRepository.java
    MatchSeatRepository.java
    MatchRepository.java
    FanRepository.java
    StaffRepository.java
    BookingRepository.java
    PaymentRepository.java
    TicketRepository.java
    SimulationResultRepository.java
    TicketPriceRepository.java
    TransactionRepository.java
    RefundRequestRepository.java
    CancellationRequestRepository.java
    NotificationRepository.java
    AuditLogRepository.java
    InconsistencyReportRepository.java
  service/
    AuthService.java
    BookingService.java
    PaymentService.java
    TicketService.java
    MatchService.java
    UserService.java
    RefundService.java
    NotificationService.java
    SellerService.java
    SupportService.java
    AdminService.java
    DataGenerationService.java
    CsvValidationService.java
    SimulationService.java
  service/sync/
    BookingSynchronizationStrategy.java
    NoLockBookingStrategy.java
    SynchronizedBookingStrategy.java
    FileLockBookingStrategy.java
    OptimisticBookingStrategy.java
    FileLockManager.java
  controller/
    GuestController.java
    UserController.java
    FanController.java
    SellerController.java
    SupportController.java
    AdminController.java
    GateController.java
    SimulationController.java
  view/
    MainMenuView.java
    LoginView.java
    RegisterView.java
    FanView.java
    SellerView.java
    SupportView.java
    GateView.java
    AdminView.java
    SimulationView.java
  exception/
    AppException.java
    ValidationException.java
    NotFoundException.java
    SeatNotAvailableException.java
    BookingLimitException.java
    BookingConflictException.java
    AccessDeniedException.java
    PaymentException.java
    InvalidTicketException.java
    SimulationException.java
    CsvException.java
  util/
    CsvUtil.java
    IdGenerator.java
    DateTimeUtil.java
    DataGenerator.java
```

## Trach nhiem tung layer

### View

Lam:

- Hien menu.
- Nhan input.
- Hien seat map/result/error message.

Khong lam:

- Khong validate business rule phuc tap.
- Khong doc/ghi CSV.
- Khong tinh double booking.
- Khong quyet dinh lock/booking.

### Controller

Lam:

- Dieu phoi request tu View.
- Goi Service phu hop.
- Chuyen DTO/input sang command object neu can.
- Bat exception va tra message ve View.

### Request / Result Object

- Mang du lieu giua Console View, Controller va Service.
- Chi tao khi object giup dien ta nghiep vu nhu `BookingRequest`, `PaymentResult`, `SimulationConfig`.
- Khong tao `ApiResponse`, HTTP DTO hoac global HTTP exception handler.

Khong lam:

- Khong doc/ghi CSV truc tiep.
- Khong viet booking algorithm.

### Service / Use Case

Lam:

- Chua business workflow.
- Validate rule.
- Goi repository.
- Goi synchronization strategy.
- Quan ly transaction logic o muc ung dung.

Service quan trong:

- `BookingService`: customer checkout va booking core, nhung phai tach method ro.
- `SimulationService`: tao thread, chay scenario, collect metric.
- `PaymentService`: payment simulation.
- `AdminService`: mot service don cho toan bo feature Administrator; khong tach service con theo tung menu trong project nho.

### Domain Model

Lam:

- Chua entity va enum.
- Chua method nho gan voi du lieu, vi du `Seat.isAvailable()`, `Booking.canConfirm()`.

Khong lam:

- Khong tu doc CSV.
- Khong tu hien menu.

### Repository

Lam:

- Load/save CSV.
- CRUD.
- Search/filter.
- Cap nhat seat/ticket/transaction.

Moi entity/nhom du lieu dung mot repository concrete don gian, vi du `BookingRepository`, `TicketRepository`. Repository tu doc/ghi file CSV cua no; khong bat buoc generic interface, mapper, cache hoac repository implementation thu hai.

### Infrastructure / Util

Lam:

- CSV parsing.
- ID generation.
- Date/time helper.
- File lock helper.
- Formatting.

## Dependency Rule

Huong phu thuoc nen la:

```text
View -> Controller -> Request/Result -> Service -> Repository -> CSV
Service -> Domain
Repository -> Domain
```

Khong nen co:

```text
View -> Repository
Controller -> CSV file
Model -> View
Repository -> View
```

## Booking Core Shared By All Channels

Tat ca kenh dat ve phai dung chung booking core:

```text
BookingService.executeBookingCore(BookingRequest request)
BookingService.attemptSeatBooking(BookingRequest request)
```

Fan/Seller co the co checkout method rieng de xu ly payment:

```text
BookingService.createFanBookingCheckout(BookingRequest request)
BookingService.createSellerBookingCheckout(BookingRequest request)
```

Trong `BookingRequest` co:

- requesterType: `FAN`, `SELLER`, `SIMULATOR`.
- fanId.
- staffId optional.
- matchId.
- seatIds.
- paymentMethod.
- synchronizationMechanism.
- idempotencyKey optional.
- expiresAt optional.

Loi thuong gap can tranh:

- Fan co booking core rieng.
- Seller co booking core rieng.
- Simulator goi full `Create Booking` co online payment.
- Simulator sua truc tiep ticket CSV.

Neu lam vay thi simulator khong con chung minh duoc booking engine that.

## Synchronization Strategy Pattern

Interface goi y:

```text
BookingSynchronizationStrategy
  - execute(BookingRequest request, BookingOperation operation)
```

Implementations:

- `NoLockBookingStrategy`
- `SynchronizedBookingStrategy`
- `FileLockBookingStrategy`
- `OptimisticBookingStrategy`

`BookingService` chon strategy dua tren config tu UI/simulator.

## Error Handling

Nhom exception:

- Business exception: seat unavailable, booking limit, invalid payment state.
- Data exception: CSV parse, missing reference, duplicate id.
- Infrastructure exception: file access, lock timeout.
- Concurrency exception: optimistic lock conflict.

Controller bat exception va hien message than thien, service/repository ghi log can thiet.
