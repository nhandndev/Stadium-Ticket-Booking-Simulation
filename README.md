# Stadium Ticket Booking Simulation

## 1. Project Information

| Item | Description |
| --- | --- |
| Course | LAB211 - OOP with Java |
| Project | Stadium Ticket Booking Simulation |
| Member | Doan Ngoc Nhan - QE210282 |
| Application type | Java Console Application |
| Architecture | MVC + Service + Repository |
| Data storage | CSV files |
| Main topic | Preventing double booking in concurrent seat reservation |

This project is a pure Java console application for managing stadium tickets and
simulating multiple users booking seats at the same time. The system applies OOP,
layered design, CSV persistence, DTOs, exception handling and Java concurrency.

The project does not use Spring, a web server, REST API, HTTP status codes or a
database management system.

---

## 2. Project Objectives

The system has two main objectives:

1. Provide the basic operations of a stadium ticket booking system for guests,
   fans, staff and administrators.
2. Compare synchronization mechanisms and show how each mechanism affects
   double booking, execution time and throughput.

The scope is kept suitable for a LAB211 console application. Features from the
previous larger scope, such as Support Staff and Notification Service, are not
part of the current baseline.

---

## 3. Current Scope

### 3.1 Actors

| Actor | Responsibility |
| --- | --- |
| Guest | Browse matches and seat availability, register and log in |
| User | Use common authenticated functions such as logout and profile update |
| Fan | Book seats, pay, view bookings and tickets, and request a refund |
| Seller | Create a fan account, create a booking for a fan, receive offline payment and issue a ticket |
| Gate Staff | Validate a ticket and check it in |
| Administrator | Manage stadium data, matches, prices, accounts, sales and system monitoring |
| Simulator Operator | Configure, run, compare and export concurrency simulations |
| Payment Service | Simulate online payment processing |

In the domain model, `Fan` and `Staff` inherit from `User`. Staff behavior is
identified by `UserRole`, including `SELLER`, `GATE_STAFF`, `ADMIN` and
`SIMULATOR_OPERATOR`.

### 3.2 Guest Features

- Search and filter matches.
- View match details and seat availability.
- Register a Fan account.
- Log in.

### 3.3 Common User Features

- Log out.
- Update profile information.

### 3.4 Fan Features

- Select seats and create a booking.
- Make an online payment.
- View booking history and booking details.
- View tickets, ticket details and ticket history.
- Request a refund.

### 3.5 Seller Features

- Search for or create a Fan.
- Create a booking for a Fan.
- Accept offline payment.
- Issue a ticket.

### 3.6 Gate Staff Features

- Validate a ticket.
- Check in a valid ticket.
- Reject an invalid, cancelled or previously used ticket.

### 3.7 Administrator Features

#### Stadium Management

- Create, list, view, update and delete stadiums.

#### Section Management

- Create sections.
- View sections by stadium.
- View, update and delete a section.

#### Seat Management

- Create seats.
- View seats by section.
- View, update and delete a seat.
- Activate or deactivate a seat.

#### Match Management

- Create, list, view, update and delete matches.

#### Ticket Pricing Management

- Create a ticket price for a match and section.
- View ticket prices by match.
- View, update and delete a ticket price.

#### Ticket Sales Management

- Open or close ticket sales.
- View ticket sales status.

#### Fan Management

- Create, list, view, search and filter Fan accounts.
- Update, activate or deactivate a Fan account.
- Delete a Fan account when business rules allow it.

#### Staff And Role Management

- Create, list, view, search and filter Staff accounts.
- Update, activate or deactivate a Staff account.
- Assign, change or revoke a Staff role.

#### System Monitoring

- View the system summary and audit log.
- Review refund requests.

### 3.8 Simulator Operator Features

- Configure a simulation.
- Select a synchronization mechanism.
- Run a simulation.
- View and compare results.
- Export a result.

---

## 4. Architecture

The class diagrams follow this dependency direction:

```text
View -> Controller -> Service -> Repository -> Model / CSV
                         |
                         +-> DTO
```

### Layer Responsibilities

| Layer | Responsibility |
| --- | --- |
| View | Display console menus, read input and print results |
| Controller | Receive View actions and delegate work to Services |
| Service | Validate input and implement business use cases |
| Repository | Read, write, search and update CSV data |
| Model | Store entity state and simple domain behavior |
| DTO | Carry request and response data between application layers |

Important dependency rules:

- View does not access Repository or CSV directly.
- Controller calls Service instead of implementing business rules.
- Service coordinates Repository and Model objects.
- Repository persists Model entities, not DTO objects.
- DTOs contain data only and do not read CSV files.
- Simulator uses the same seat state and synchronization rules as booking.

### Suggested Package Structure

```text
src/
  app/
  common/
    entity/
    exception/
  auth/
  user/
  stadium/
  match/
  booking/
  payment/
  ticket/
  refund/
  admin/
  simulation/
```

Each domain should contain only the folders it currently needs, such as `model`,
`dto`, `repository`, `service`, `controller`, `view` and `enums`.

---

## 5. Main Domain Model

### Account Domain

- `User`: common account information and profile behavior.
- `Fan`: customer account with a maximum ticket limit per transaction.
- `Staff`: employee account whose permissions are determined by `UserRole`.

### Stadium And Match Domain

- `Stadium`: stadium information.
- `Section`: a seating section belonging to a stadium.
- `Seat`: a physical seat belonging to a section.
- `Match`: match information and ticket-sale status.
- `MatchSeat`: the availability of one physical seat for one match.
- `TicketPrice`: price for a match and section.

`Seat` represents the physical seat, while `MatchSeat` represents its booking
state for a particular match. Booking a seat for one match therefore does not
make that physical seat unavailable for every other match.

### Booking Domain

- `Booking`: a Fan's booking and total amount.
- `Payment`: online or offline payment information.
- `Ticket`: ticket issued after a successful booking/payment flow.
- `BookingTransaction`: records a booking attempt for auditing and simulation.
- `RefundRequest`: refund request submitted by a Fan and reviewed by an Admin.

### Simulation Domain

- `Simulation`: simulation configuration and running status.
- `SimulationResult`: measured result of a simulation run.
- `BookingTask`: one concurrent seat-booking attempt.
- `BookingStrategy`: common contract for synchronization strategies.

---

## 6. Main Application Flows

### 6.1 Authentication And Role Navigation

```text
Start application
-> Show Guest menu
-> Register or Login
-> Verify account and password
-> Read UserRole
-> Open the menu belonging to that role
-> Logout
-> Return to Guest menu
```

The application does not ask the user to choose a protected role manually.
After login, the saved `UserRole` determines whether the program opens the Fan,
Seller, Gate Staff, Administrator or Simulator Operator menu.

### 6.2 Fan Booking And Online Payment

```text
Search / Filter Match
-> View Match Details
-> View Seat Availability
-> Select Seats
-> Validate Booking Request
-> Re-check MatchSeat Availability
-> Create Booking
-> Process Mock Online Payment
-> Mark Successful Seats as BOOKED
-> Create Ticket
-> Record Booking Transaction
```

Seat availability must be checked again when the booking is committed. A seat
shown as available may have been booked by another thread before confirmation.

### 6.3 Seller Booking And Offline Payment

```text
Search Fan
-> Create Fan if not found
-> Select Match and Seats
-> Create Booking for Fan
-> Accept Offline Payment
-> Confirm Booking
-> Issue Ticket
```

### 6.4 Ticket Validation And Check-in

```text
Enter Ticket Code
-> Find Ticket
-> Validate Ticket Status
-> Mark Ticket as CHECKED_IN
-> Save Check-in Result
```

A `CHECKED_IN` or `CANCELLED` ticket cannot be checked in again. An approved
refund must also make the related ticket ineligible for check-in.

### 6.5 Refund Review

```text
Fan submits Refund Request
-> Administrator views pending requests
-> Administrator approves or rejects request
-> Update related status when approved
```

---

## 7. Core Business Rules

1. A Fan may select at most four seats in one booking transaction.
2. Ticket sales must be open before a seat can be booked.
3. A deactivated physical seat cannot be sold.
4. A `MatchSeat` must be `AVAILABLE` before a booking can commit it.
5. One seat can produce at most one valid ticket for the same match.
6. A successful payment is required for the Fan online booking flow.
7. Seller booking uses offline payment and does not call the online Payment Service.
8. Ticket check-in succeeds only once.
9. Data with related transaction history may be deleted only when business rules permit it.
10. Concurrent booking attempts must be recorded for simulation metrics.

The main consistency rule is:

```text
matchId + seatId -> at most one successful booking / valid ticket
```

---

## 8. Concurrency Simulation

After a Simulator Operator logs in, `MainView.showSimulatorMenu()` provides
configuration, run, result, comparison, and export options. On diagram page 04,
`SimulatorView.showConfigurationMenu()` reads the match, target seats, thread
count, and mechanism into a `SimulationRequestDto`. It calls
`SimulationController.configureSimulation(request)` and displays the saved
`SimulationResponseDto` with status `CONFIGURED`; this step does not run any
booking tasks. When the operator later selects Run,
`SimulatorView.startSimulation(simulationId)` calls
`SimulationController.runSimulation(simulationId)` for that saved configuration.
`ReportView` displays results, comparisons, and export feedback.

The simulator creates multiple `BookingTask` objects that attempt to reserve the
same or overlapping seats. `SimulationService.runSimulation()` creates a local
`ExecutorService` to run the tasks, collects their results, and shuts it down
before returning. `CountDownLatch` can synchronize the tasks' starting time.
The executor is not a field of `SimulationService`, and neither
`SimulationService` nor `SimulationController` has a separate `shutdown()` method.

Supported synchronization mechanisms:

- `NO_LOCK`: unsafe baseline used to demonstrate race conditions.
- `SYNCHRONIZED`: JVM-level synchronized booking operation.
- `FILE_LOCK`: file-level locking for CSV updates.
- `OPTIMISTIC`: version-based update that detects write conflicts.

Suggested scenarios:

```text
High contention:   many threads -> one seat
Medium contention: many threads -> a small seat group
Low contention:    many threads -> many different seats
```

Simulation results include mechanism, thread count, attempts, successes,
failures, conflicts, double bookings, execution time and throughput.

The simulator tests the booking core only. It does not create hundreds of online
payment screens or require interactive console input from worker threads.

---

## 9. CSV Persistence

Repositories convert Model objects to and from CSV rows. The expected data set
includes files for these domains:

```text
data/
  stadiums.csv
  sections.csv
  seats.csv
  matches.csv
  match_seats.csv
  ticket_prices.csv
  fans.csv
  staff.csv
  bookings.csv
  payments.csv
  tickets.csv
  booking_transactions.csv
  refund_requests.csv
  simulations.csv
  simulation_results.csv
  audit_logs.csv
```

The final generated data set should satisfy the LAB requirement for at least
10,000 rows, with seats or match-seat inventory forming the largest portion.

---

## 10. Use Case And Class Diagrams

### Use Case Diagram

The latest Use Case source is the [current draw.io diagram](<Biểu đồ không có tiêu đề (11).drawio>).

The `System Overview - UseCase` page is the current baseline for actors and
user-visible features.

### Class Diagrams

The class design is divided into six Mermaid files:

| File | Content |
| --- | --- |
| `01_Main_View_Auth.mmd` | Navigation, authentication, profile, Guest browsing and DTOs |
| `02_Stadium_Match_Admin.mmd` | Stadium, section, seat, match, pricing, accounts and Admin operations |
| `03_User_Booking_Payment_Ticket.mmd` | Fan/Seller booking, payment, ticket, refund and Gate Staff flows |
| `04_Simulation.mmd` | Simulation configuration, booking tasks, local executor lifecycle, strategies and reporting |
| `05_CSV_Repository_BaseEntity.mmd` | CSV repositories, base entity, data generator and exceptions |
| `06_Model_Relationships.mmd` | Entity relationships, inheritance and multiplicities |

See the [Class Diagram Index](docs/Class_Diagrams/README.md) for conventions and
detailed design notes.

---

## 11. Project Structure

```text
Stadium-Ticket-Booking-Simulation/
  src/                    Java source code
  data/                   CSV data files (created when persistence is implemented)
  docs/                   Requirements and technical documents
    Class_Diagrams/       Six Mermaid class diagrams
  ai_logs/                AI usage evidence and reflection material
  README.md               Project overview
```

The repository is being implemented incrementally. The current source contains
the common foundation and the first Stadium domain classes. The remaining
features in this README describe the approved target scope and will be added in
later implementation stages.

---

## 12. Implementation Progress by Stage

The stages below show the **implementation order**, not the six class diagram
pages. `[x]` means the work exists in `src` and has been checked; `[ ]` means it
remains to be done. The class diagrams describe the complete design, while this
checklist tracks the current code progress.

| Stage | Main scope | Current status | Demo outcome |
| --- | --- | --- | --- |
| 01 | Console foundation and shared classes | Complete | Run `app.Main`, open the menu, and exit |
| 02 | Stadium structure models and DTOs | In progress | Create and print a Stadium, Section, and Seat |
| 03 | CSV and stadium repositories | Not started | Save a stadium and read it after restarting |
| 04 | Match, MatchSeat, and ticket pricing | Not started | Create a match, seat inventory, and prices |
| 05 | Guest match and seat browsing | Not started | Search/filter matches and view available seats |
| 06 | User, Fan, Staff, and authentication | Not started | Register, log in, and navigate by role |
| 07 | System administration | Not started | Manage data and open ticket sales |
| 08 | Fan booking, payment, tickets, and refunds | Not started | Buy and view tickets, then request a refund |
| 09 | Seller and Gate Staff | Not started | Sell at the counter and check in a ticket |
| 10 | Concurrency simulation | Not started | Run competing threads and compare results |
| 11 | Integration, testing, and delivery | Not started | Demonstrate the complete flow |

### Stage 01 - Console and Common Foundation

- [x] `app.Main` starts the Java console application.
- [x] `app.MainView` shows Guest, Fan Login, Staff Login, Register, and Exit options.
- [x] `BaseEntity` has a private `id` and a `getId()` method.
- [x] `AppException` and `ErrorCode` represent internal errors without HTTP status codes.
- [x] The current source compiles and the menu runs; business options still show
  not-yet-implemented messages.

### Stage 02 - Stadium, Section, Seat, and DTOs

- [x] `Stadium`, `Section`, and `Seat` inherit from `BaseEntity`.
- [x] Domain methods include `updateDetails`, `rename`, `updateLocation`,
  `activate`, and `deactivate`.
- [x] `Seat.active` represents the state of a physical seat.
- [x] `StadiumRequestDto` and `StadiumResponseDto` exist.
- [x] `MatchSeat` and `SeatStatus` have not been added prematurely.
- [ ] Add a small demo that creates and prints a Stadium -> Section -> Seat;
  `MainView` does not yet use these models.

### Stage 03 - CSV Foundation and Stadium Repositories

- [ ] Create `CsvRepository<T>` and repositories for Stadium, Section, and Seat.
- [ ] Implement `findAll`, `findById`, `save`, `update`, `delete`, and CSV conversion.
- [ ] Verify that saved data can be read after restarting the application.

### Stage 04 - Match, MatchSeat, and TicketPrice

- [ ] Create `Match`, `MatchSeat`, `TicketPrice`, `SaleStatus`, `SeatStatus`, and DTOs.
- [ ] Create repositories for matches, per-match seat inventory, and section prices.
- [ ] Verify that `Seat.active` differs from `MatchSeat.status`; retain `version`
  for optimistic locking.

### Stage 05 - Guest Browsing

- [ ] Connect `BrowseController -> BrowseService -> Repository` to the console View.
- [ ] Search/filter matches, view details, and inspect available `MatchSeat` records.
- [ ] Handle an empty match list and an unknown match ID.

### Stage 06 - User, Fan, Staff, and Authentication

**Account entities are introduced at this stage:** `User`, `Fan`, and `Staff`.
Staff types are identified by `UserRole`; Fan and Staff are not recreated during
the Administrator stage.

- [ ] Create `User`, `Fan`, `Staff`, `UserRole`, and `UserStatus`.
- [ ] Create `FanRepository` and `StaffRepository` for CSV account storage.
- [ ] Create login, registration, profile, and user-response DTOs.
- [ ] Create Auth/Fan Services and Controllers; registration creates only Fans.
- [ ] Verify credentials and open the correct role menu; logout returns to Guest.

### Stage 07 - Administrator

- [ ] Implement Stadium, Section, Seat, Match, and TicketPrice CRUD through
  Services and Controllers.
- [ ] Open/close ticket sales and view the sales status.
- [ ] Manage existing Fan/Staff accounts, including status and staff roles.
- [ ] Generate CSV data and view the system summary and audit log.
- [ ] Prevent deletion of referenced data and removal of the last Administrator.

### Stage 08 - Fan Booking, Payment, Tickets, and Refunds

- [ ] Create `Booking`, `Payment`, `Ticket`, `BookingTransaction`, and `RefundRequest`.
- [ ] Let a Fan book at most four `MatchSeat` records; recheck availability on confirmation.
- [ ] Simulate online payment, confirm the booking, and create tickets after success.
- [ ] Show Fan bookings/tickets, accept refund requests, and let an Administrator
  review those requests.
- [ ] Ensure failed payment does not leave seats incorrectly marked as booked.

### Stage 09 - Seller and Gate Staff

- [ ] Let a Seller find/create a Fan, book on their behalf, and record offline payment.
- [ ] Let the Seller issue tickets after a valid booking.
- [ ] Let Gate Staff validate a ticket and check it in only once.
- [ ] Reuse the previous stage's Booking/Ticket Services rather than duplicating seat rules.

### Stage 10 - Concurrency Simulation

- [ ] Create `Simulation`, `SimulationResult`, `BookingTask`, and DTOs.
- [ ] Implement `NO_LOCK`, `SYNCHRONIZED`, `FILE_LOCK`, and `OPTIMISTIC` through
  `BookingStrategy`.
- [ ] Use `ExecutorService` and `CountDownLatch` for concurrent booking attempts.
- [ ] Measure successes, failures, conflicts, double bookings, time, and throughput.
- [ ] View, compare, and export results; simulation does not perform online payment.

### Stage 11 - Integration, Testing, and Delivery

- [ ] Connect role-specific menus and enforce authorization in Services.
- [ ] Test CSV persistence after restart, large data sets, and edge cases.
- [ ] Demonstrate the Admin -> Guest -> Fan -> Seller -> Gate -> Simulator flow.
- [ ] Compare source code with the Use Cases and Class Diagrams, complete the
  report, and prepare the submission package.

The dependency order is **Stadium/CSV -> MatchSeat -> Guest/Auth -> Admin ->
Booking -> Seller/Gate -> Simulation -> Integration**. The `User`, `Fan`, and
`Staff` entities are introduced in **Stage 06**. Each stage creates only the
modules it needs.

---

## 13. Compile And Run

Requirements:

- JDK 17 or a compatible Java version.
- A terminal or Java IDE.

Compile all Java files:

```bash
javac -d out $(find src -name "*.java")
```

Run the current entry point:

```bash
java -cp out app.Main
```

The available menu functions depend on the current implementation stage.

---

## 14. Documentation

Detailed documents are stored in [`docs/`](docs/README.md), including project
scope, SRS, Use Cases, architecture, data model, simulation design, edge cases,
test plan, implementation roadmap, delivery checklist and AI reflection.

When documents conflict, use this priority order:

1. Current LAB211 assignment requirements.
2. Latest Use Case diagram.
3. Latest six Class Diagrams.
4. This README.
5. Older detailed documents in `docs/`.

---

## 15. Definition Of Done

The project is complete when:

- Required use cases can be executed from the console according to role.
- Controllers, Services and Repositories follow the dependency rules.
- CSV data can be loaded, updated and saved reliably.
- Fan and Seller booking flows prevent invalid seat sales.
- Ticket validation prevents duplicate check-in.
- Administrator functions cover the approved management scope.
- All four synchronization mechanisms can be demonstrated and compared.
- The simulator reports double-booking and performance metrics.
- The project compiles and includes test/demo evidence.
- Documentation, diagrams, report and submission package are synchronized.
