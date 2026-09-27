# Stadium Ticket Booking Simulation - Domain Class Diagrams

## Cach chia so do

Bo Class Diagram bam theo Use Case moi trong `Bieu do khong co tieu de (6).drawio` va van giu kien truc LAB:

```text
VIEW -> CONTROLLER -> SERVICE -> REPOSITORY -> MODEL / CSV
```

So do chi tiet duoc chia theo domain thay vi tach tung layer. Moi domain gom cac class Model, Repository, Service va Controller can cho luong do. Khi mot domain can class cua domain khac, class can thiet duoc ve lai trong cung file.

Khong co class rong, khong co nhan tro sang file khac va khong co bang doi chieu.

## Thu tu doc

1. `00_Full_Class_Diagram.mmd`: toan bo he thong va multiplicity tong the.
2. `01_User_Account_Domain.mmd`: register, login, logout, profile va Admin account management.
3. `02_Stadium_Match_Domain.mmd`: Stadium, Section, Seat, Match, MatchSeat, pricing va ticket sales.
4. `03_Booking_Payment_Ticket_Domain.mmd`: select seat, booking core, payment, booking history va ticket.
5. `04_Simulation_Domain.mmd`: dataset, configure/run/compare simulation va booking core dung chung.
6. `05_Console_Navigation_Domain.mmd`: Main, ConsoleUI, menu theo actor va cac Controller ma menu goi.

## User and Account Domain

Luong chinh:

```text
Guest/User/Admin
-> FanController
-> UserService
-> FanRepository
-> User
```

`FanController` chi nhan lenh tu Console va goi `UserService`. `UserService` xu ly authentication, session, profile va Fan account rules; `FanRepository` chi doc/ghi CSV.

## Stadium and Match Domain

Luong chinh:

```text
Guest/User xem tran va ghe
Admin CRUD san/tran/gia ve
-> StadiumController
-> StadiumService
-> StadiumRepository + MatchRepository
-> Stadium/Section/Seat/Match/MatchSeat/TicketPrice
```

Multiplicity quan trong:

- Mot Stadium co `0..*` Section va Match.
- Mot Section co `0..*` Seat.
- Mot Match co `0..*` MatchSeat va TicketPrice.
- Mot Seat co the xuat hien trong nhieu MatchSeat cua cac tran khac nhau.

## Booking, Payment and Ticket Domain

Luong chinh:

```text
Select Seats
-> Create Booking
-> BookingService.executeBookingCore
-> Make Online Payment
-> Confirm Booking
-> Issue Tickets
```

Domain nay ve day du `User`, `Match`, `MatchSeat` va `TicketPrice` vi booking can truc tiep cac class do. `BookingController` goi `BookingService`, `PaymentService` va `TicketService`; chi cac Service moi truy cap Repository.

Multiplicity quan trong:

- Mot User co `0..*` Booking.
- Mot Booking dat `1..*` MatchSeat.
- Mot Booking co toi da mot Payment.
- Mot Booking co `0..*` Ticket.
- Mot MatchSeat co toi da mot Ticket.

## Simulation Domain

Luong chinh:

```text
Generate CSV Dataset
-> Configure Simulation
-> Run concurrent attempts
-> SimulationService
-> BookingService.executeBookingCore
-> Save SimulationResult
-> View/Compare Results
```

Simulation ve lai cac class booking can thiet de thay boundary voi booking domain. `SimulationController` goi `SimulationService`; cac strategy trong Simulator chi goi `BookingService.executeBookingCore`, khong goi payment hay ticket issuance.

Bon synchronization mechanism:

- `NO_LOCK`
- `SYNCHRONIZED`
- `FILE_LOCK`
- `OPTIMISTIC`

## Console Navigation Domain

`Main` tao `ConsoleUI`. ConsoleUI dieu huong den:

- `GuestMenu`: browsing, register, login.
- `UserMenu`: profile, booking, payment, ticket.
- `AdminMenu`: account, stadium, match, dataset va simulation.

File nay ve day du bon Controller va cac Service ma menu can, khong dung class box rong.

## Quy tac UML

- `+`: public.
- `-`: private.
- `#`: protected.
- `--|>`: inheritance.
- `-->`: directed association/dependency.
- `*--`: composition.
- `1`, `0..1`, `0..*`, `1..*`: multiplicity.

Constructor co ten trung class va khong co return type. Method co parameter type va return type. Money dung `BigDecimal`, ngay gio dung `LocalDateTime`, ID dung `Long`.

## Mau sac

- Xanh cyan: View.
- Tim: Controller.
- Xanh la: Service.
- Vang: Repository.
- Xanh lam: Model.
- Xanh ngoc: Simulation Model.
- Xam: Enum.

Khi thuyet trinh, mo so do tong de gioi thieu nam tang, sau do chuyen sang tung domain de noi theo luong Use Case. Khong nen doc tung class trong so do tong.
