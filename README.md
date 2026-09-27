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
-> Mark Ticket as USED
-> Save Check-in Result
```

A `USED`, `CANCELLED` or `REFUNDED` ticket cannot be checked in again.

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

The simulator creates multiple `BookingTask` objects that attempt to reserve the
same or overlapping seats. `ExecutorService` runs the tasks and
`CountDownLatch` can synchronize their starting time.

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

The latest Use Case source is:

```text
Biểu đồ không có tiêu đề (11).drawio
```

The `System Overview - UseCase` page is the current baseline for actors and
user-visible features.

### Class Diagrams

The class design is divided into six Mermaid files:

| File | Content |
| --- | --- |
| `01_Main_View_Auth.mmd` | Navigation, authentication, profile, Guest browsing and DTOs |
| `02_Stadium_Match_Admin.mmd` | Stadium, section, seat, match, pricing, accounts and Admin operations |
| `03_User_Booking_Payment_Ticket.mmd` | Fan/Seller booking, payment, ticket, refund and Gate Staff flows |
| `04_Simulation.mmd` | Simulation configuration, booking tasks, strategies and reporting |
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

## 12. Compile And Run

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

## 13. Documentation

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

## 14. Definition Of Done

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
