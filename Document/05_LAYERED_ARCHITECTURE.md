# Layered Architecture

## Kien truc tong the

He thong dung MVC nhung nen chia ro hon theo cac layer:

```text
View
  -> Controller
    -> Service / Use Case
      -> Domain Model
      -> Repository Interface
        -> CSV Repository Implementation
      -> Infrastructure Utilities
```

Simulator khong nam rieng mot booking logic moi. Simulator tao concurrent tasks va goi vao booking core trong `BookingService`, khong goi full customer checkout/payment flow.

## Package goi y

```text
src/
  Main.java
  model/
    BaseEntity.java
    Stadium.java
    Section.java
    Seat.java
    Match.java
    Fan.java
    Staff.java
    Booking.java
    BookingItem.java
    Ticket.java
    BookingTransaction.java
    PaymentTransaction.java
    SimulationResult.java
    AuditLog.java
    Notification.java
  repository/
    CsvRepository.java
    StadiumRepository.java
    SectionRepository.java
    SeatRepository.java
    MatchRepository.java
    FanRepository.java
    StaffRepository.java
    TicketRepository.java
    TransactionRepository.java
    SimulationResultRepository.java
  service/
    AuthService.java
    BookingService.java
    PaymentService.java
    TicketService.java
    NotificationService.java
    CheckInService.java
    AdminService.java
    SupportService.java
    DataGenerationService.java
    CsvValidationService.java
    SimulationService.java
  service/sync/
    BookingSynchronizationStrategy.java
    NoLockBookingStrategy.java
    SynchronizedBookingStrategy.java
    FileLockBookingStrategy.java
    OptimisticBookingStrategy.java
  controller/
    GuestController.java
    FanController.java
    StaffController.java
    AdminController.java
    SellerController.java
    SupportController.java
    SimulationController.java
  view/
    MainMenuView.java
    GuestView.java
    FanView.java
    StaffView.java
    AdminView.java
    SeatMapView.java
    SimulationView.java
  exception/
    SeatNotFoundException.java
    SeatNotAvailableException.java
    BookingLimitExceededException.java
    MatchNotFoundException.java
    FanNotFoundException.java
    OptimisticLockException.java
    CsvDataException.java
    FileAccessException.java
  util/
    CsvUtil.java
    IdGenerator.java
    DateTimeUtil.java
    MoneyUtil.java
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
- `SupportService`: tra cuu va reconciliation.
- `AdminService`: CRUD va data validation.

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

Nen co:

- Generic `CsvRepository<T>`.
- Atomic write strategy: ghi temp file roi replace neu co the.
- Cache in-memory cho read performance neu can.

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
View -> Controller -> Service -> Repository -> CSV
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
