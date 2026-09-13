# Stadium Ticket Booking Simulation

## 1. Project Overview

**Course:** LAB211 - OOP with Java  
**Project:** Stadium Ticket Booking Simulation  
**Member:** Doan Ngoc Nhan - QE210282  
**Architecture:** MVC with Service and Repository layers  
**Persistence:** CSV files  
**Core research problem:** Preventing **Double Booking** when many fan threads attempt to book the same seat concurrently.

This project simulates a stadium ticket-booking system and compares synchronization strategies to evaluate the trade-off between data consistency, concurrency and throughput.

The project has two scopes:

1. **LAB211 core scope:** Java OOP, MVC, CSV persistence, CRUD, booking flow, custom exceptions, data generation and concurrency simulator.
2. **Enterprise-like extension scope:** seller-assisted booking, support lookup, admin sales control, seat hold expiry, idempotency, ticket validation, notification simulation, audit log and AI audit/reflection.

---

## 2. Core Design Decision

The most important design rule is:

```text
Fan/Seller checkout flow != Simulator booking attempt
```

The system separates:

- `Create Booking`: customer checkout flow for Fan/Seller, including payment.
- `Execute Booking Core`: shared booking core for checking availability, applying synchronization and reserving/committing seat state.
- `Execute Concurrent Booking`: simulator flow that calls `Execute Booking Core`, not the full checkout/payment flow.

This prevents the simulator from incorrectly creating 1000 online payment flows when the real goal is to test double-booking behavior.

```text
Fan Create Booking
  -> Validate Booking
  -> Calculate Booking Total
  -> Execute Booking Core
  -> Hold Seats
  -> Make Online Payment
  -> Confirm Booking

Seller Create Booking for Fan
  -> Select Fan
  -> Select Seats for Customer
  -> Validate Booking
  -> Calculate Booking Total
  -> Execute Booking Core
  -> Hold Seats
  -> Accept Offline Payment
  -> Confirm Booking
  -> Issue Ticket

Simulator
  -> Create Fan Threads
  -> Synchronize Thread Start
  -> Execute Concurrent Booking
  -> Execute Booking Core
  -> Detect Double Booking
  -> Calculate Metrics
```

`Confirm Booking` includes system actions:

```text
Confirm Booking
  -> Create Ticket
  -> Record Transaction
```

`Issue Ticket` is a Seller operation after the system has already created the ticket.

---

## 3. Main Features

### Guest / Fan

- View match list.
- Search match.
- View match details.
- View stadium and sections.
- View seat map.
- View seat availability.
- Register / login / logout.
- Manage fan profile.
- Select 1-4 seats per transaction.
- Create booking.
- Make online payment simulation.
- View owned tickets.
- View booking history.
- Request cancellation/refund as an extension.

### Seller

- Staff login/logout.
- Search fan.
- Create fan if not found.
- Select fan.
- Select seats for customer.
- Create booking for fan through the shared booking core.
- Accept offline payment.
- Confirm offline payment.
- Issue ticket after ticket creation.
- View seller transactions.

### Support Staff

- Search fan.
- Search ticket.
- Search booking transaction.
- Search payment transaction.
- Check booking/payment/seat status.
- Assist failed booking.
- Assist missing ticket.
- Assist payment issue.
- Escalate data inconsistency.

### Administrator

- Manage stadium.
- Manage section.
- Manage seat.
- Manage match.
- Manage fan.
- Manage staff and roles.
- Manage ticket pricing.
- Open ticket sales.
- Close ticket sales.
- Generate CSV data.
- Validate CSV data.
- View system summary.
- View audit log.

### Enterprise-like Extensions

These are useful for a realistic booking system but should not delay LAB core requirements:

- Seat hold expiry.
- Idempotency key for duplicate booking requests.
- Payment/ticket reconciliation.
- Cancellation/refund simulation.
- Ticket validation/check-in by Gate Staff.
- Notification simulation.
- Audit log.
- Soft delete/status instead of unsafe hard delete.

### Concurrency Simulator

- Configure number of fan threads.
- Configure target match and target seats.
- Select contention scenario.
- Select synchronization mechanism.
- Run concurrent booking simulation.
- Detect double booking.
- Measure throughput.
- Measure success/failure/conflict counts.
- Export or display simulation results.

Supported synchronization strategies:

- `NO_LOCK`
- `SYNCHRONIZED`
- `FILE_LOCK`
- `OPTIMISTIC`

---

## 4. Core Business Rules

1. A fan may book a maximum of **4 seats per transaction**.
2. A seat already booked for a match cannot be sold again for the same match.
3. A successful booking must satisfy:

```text
matchId + seatId -> maximum 1 VALID ticket
```

4. Seat lifecycle:

```text
AVAILABLE -> LOCKED -> BOOKED
LOCKED -> AVAILABLE
```

5. `LOCKED -> AVAILABLE` happens when:

- Payment fails.
- Payment is cancelled.
- Payment expires.
- Booking expires.
- A partial booking fails and held seats must be released.

6. Pending bookings must have an expiry time.
7. Repeated booking requests should be protected by `idempotencyKey`.
8. Fan booking, Seller booking and Simulator must use the same booking core.
9. Simulator must not call the full Fan checkout/payment flow.
10. Admin should not hard delete records that already have transaction history.

---

## 5. Architecture

The application follows MVC with additional Service and Repository layers.

```text
View
  -> Controller
    -> Service / Use Case
      -> Domain Model
      -> Repository Interface
        -> CSV Repository Implementation
```

### Layer Rules

- View displays menus, reads input and displays results.
- Controller coordinates user actions and calls services.
- Service contains business workflows and booking logic.
- Repository reads/writes CSV and performs CRUD/search.
- Controller must not access CSV files directly.
- View must not contain business logic.
- Simulator must call booking core through service methods, not edit CSV/tickets directly.

Suggested packages:

```text
src/
  model/
  repository/
  service/
  service/sync/
  controller/
  view/
  exception/
  util/
  Main.java
```

---

## 6. Data And CSV Files

Minimum CSV files:

```text
data/
  stadiums.csv
  sections.csv
  seats.csv
  fans.csv
  matches.csv
  tickets.csv
  transactions.csv
```

Recommended extended CSV files:

```text
data/
  staff.csv
  bookings.csv
  booking_items.csv
  payment_transactions.csv
  simulation_results.csv
  audit_logs.csv
  ticket_pricing.csv
  notifications.csv
```

Important fields:

- `Seat.version`: required for optimistic locking.
- `Booking.expiresAt`: required for pending booking expiry.
- `Booking.idempotencyKey`: used to prevent duplicate submit.
- `Ticket.status`: `VALID`, `USED`, `CANCELLED`, `REFUNDED`.
- `Match.saleStatus`: `NOT_OPEN`, `ON_SALE`, `CLOSED`, `SOLD_OUT`.

The generated dataset should contain at least **10,000 rows**, with seats as the largest dataset.

---

## 7. Data Generation

Run the data generator before running the main program.

If using an IDE, run:

```text
DataGenerator.java
```

If using command line:

```bash
javac -d out $(find src -name "*.java")
java -cp out util.DataGenerator
```

Verify row count:

```bash
wc -l data/*.csv
```

---

## 8. Compile And Run

Compile:

```bash
javac -d out $(find src -name "*.java")
```

Run:

```bash
java -cp out Main
```

If `Main.java` belongs to a package, update the command, for example:

```bash
java -cp out app.Main
```

---

## 9. Suggested Main Menu

```text
===== STADIUM TICKET BOOKING =====

1. Guest / Fan Booking
2. Staff Operations
3. Data Management
4. Concurrency Simulator
5. Exit
```

Optional staff menu:

```text
===== STAFF OPERATIONS =====

1. Seller Operations
2. Support Operations
3. Administrator Operations
4. Back
```

---

## 10. Booking Flow

Fan checkout flow:

```text
Login
-> Select Match
-> Select Section
-> View Seat Map
-> Select 1-4 Seats
-> Review Booking
-> Validate Booking
-> Calculate Booking Total
-> Execute Booking Core
-> Hold Seats
-> Create Pending Booking
-> Make Online Payment
-> Confirm Booking
-> Mark Seats BOOKED
-> Create Ticket
-> Record Transaction
```

The application must re-check seat availability during the booking transaction.

Viewing a seat as `AVAILABLE` does not guarantee that the seat is still available later.

---

## 11. Concurrency Simulator

Required Java concurrency utilities:

```text
ExecutorService
CountDownLatch
```

Typical scenarios:

```text
High contention:   1000 fan threads -> 1 seat
Medium contention: 1000 fan threads -> 100 seats
Low contention:    1000 fan threads -> many different seats
```

Run simulator:

```text
Main Menu
-> Concurrency Simulator
-> Select Mechanism
-> Enter Thread Count
-> Select Match / Target Seats
-> Run
```

Recommended experiment:

```text
1000 threads x 4 mechanisms
```

Simulator metrics:

- Mechanism.
- Thread count.
- Total attempts.
- Successful bookings.
- Failed bookings.
- Conflict count.
- Double booking count.
- Execution time.
- Throughput.
- Double booking rate.

Critical validation:

```text
same matchId + same seatId + multiple VALID tickets
=> Double Booking
```

Expected research conclusion:

- `NO_LOCK` is the unsafe baseline and may produce race conditions.
- Safe strategies are expected to prevent double booking.
- `SYNCHRONIZED` is simple but JVM-local.
- `FILE_LOCK` is closer to CSV/file persistence but slower.
- `OPTIMISTIC` supports concurrency but may produce conflicts under high contention.

---

## 12. Documentation

Detailed project documents are stored in:

```text
Document/
```

Current documentation set:

- `Document/README.md`
- `Document/00_REPO_SCAN_SUMMARY.md`
- `Document/01_PROJECT_SCOPE.md`
- `Document/02_SRS.md`
- `Document/03_USE_CASE_SPECIFICATION.md`
- `Document/04_FEATURE_BACKLOG.md`
- `Document/05_LAYERED_ARCHITECTURE.md`
- `Document/06_DOMAIN_DATA_MODEL.md`
- `Document/07_BOOKING_AND_SIMULATION_DESIGN.md`
- `Document/08_EDGE_CASES_AND_BUSINESS_RULES.md`
- `Document/09_TEST_PLAN.md`
- `Document/10_DELIVERY_CHECKLIST.md`
- `Document/11_SERVICE_CONTRACTS.md`
- `Document/12_IMPLEMENTATION_ROADMAP.md`
- `Document/13_AI_USAGE_AUDIT_AND_REFLECTION.md`

Use these files as the official baseline for implementation, report writing and diagram drawing.

Required diagrams:

- UML Class Diagram.
- Use Case Diagram, preferably split into Guest/Fan, Staff, Admin and Simulator.
- Booking Flowchart.
- Synchronization / Double Booking Prevention Flowchart.
- Simulator Flowchart.

---

## 13. Testing Checklist

### Single-thread Booking

- [ ] Fan can register.
- [ ] Fan can login/logout.
- [ ] Match list loads correctly.
- [ ] Seat map displays correctly.
- [ ] Fan can select 1-4 seats.
- [ ] Booking total is calculated.
- [ ] Seat is held before payment confirmation.
- [ ] Ticket is created after successful booking.
- [ ] Booked seat cannot be booked again.
- [ ] Pending booking expiry releases held seats.
- [ ] Duplicate submit is prevented if idempotency is implemented.

### CRUD / Admin

- [ ] Stadium CRUD/search works.
- [ ] Section CRUD/search works.
- [ ] Seat CRUD/search works.
- [ ] Fan CRUD/search works.
- [ ] Match CRUD/search works.
- [ ] Sales open/close works if implemented.
- [ ] Pricing snapshot works if implemented.

### CSV

- [ ] CSV parse works.
- [ ] CSV serialization works.
- [ ] >= 10,000 rows generated.
- [ ] Invalid CSV data is handled safely.
- [ ] Duplicate IDs are detected.
- [ ] Broken references are detected.

### Concurrency

- [ ] `NO_LOCK` demonstrates possible race condition.
- [ ] `SYNCHRONIZED` prevents double booking.
- [ ] `FILE_LOCK` prevents double booking.
- [ ] `OPTIMISTIC` detects version conflict.
- [ ] `CountDownLatch` is used.
- [ ] `ExecutorService` is used.
- [ ] Simulator calls booking core only.
- [ ] Simulator does not run online payment flow.
- [ ] 100-500 thread demo works.
- [ ] 1000-thread experiment works if the machine can handle it.

---

## 14. AI Audit

This project keeps an AI audit trail because AI was used to support requirements analysis, use case correction, architecture design, documentation and prompt review.

Current AI log files:

```text
ai_logs/
  doan_ngoc_nhan_QE210282_ai_log.md
  prompt_audit_summary.md
```

AI audit records include:

- Original prompt summary.
- AI output summary.
- Accepted parts.
- Rejected or modified parts.
- Verification method.
- Impact on project.

Important AI-audited decisions:

1. Create documentation from repository scan.
2. Split `Create Booking` checkout flow from `Execute Booking Core`.
3. Prevent Simulator from including Fan online payment flow.
4. Add seat hold expiry and idempotency.
5. Add enterprise-like extensions with priority control.
6. Clean duplicate include relationships in use case specs.

AI Reflection in the report should explain:

- What AI helped with.
- Which AI outputs were incorrect or incomplete.
- How the team verified/corrected AI output.
- How prompts improved over time.
- Risks of depending too much on AI.
- Lessons learned.

Requirement for T10:

```text
AI Reflection >= 500 words per member
AI Log file per member
Prompt audit for important AI interactions
```

For this member:

```text
Member: Doan Ngoc Nhan - QE210282
AI log: ai_logs/doan_ngoc_nhan_QE210282_ai_log.md
Prompt audit: ai_logs/prompt_audit_summary.md
```

---

## 15. Submission Package

Final ZIP naming format:

```text
NHOM_XX_LAB211_TicketBooking.zip
```

Expected contents:

```text
src/
data/
docs/
ai_logs/
README.md
```

Before submission:

- [ ] Source code compiles successfully.
- [ ] Main program runs.
- [ ] MVC architecture is respected.
- [ ] Controller does not access CSV directly.
- [ ] Business logic is not placed in View.
- [ ] CSV data has >= 10,000 rows.
- [ ] DataGenerator works.
- [ ] At least 5 custom exceptions exist.
- [ ] At least 3 synchronization mechanisms are implemented.
- [ ] Simulator uses `ExecutorService`.
- [ ] Simulator uses `CountDownLatch`.
- [ ] Throughput chart/table is completed.
- [ ] Double booking rate chart/table is completed.
- [ ] UML and flowcharts are completed.
- [ ] Report and slides are completed.
- [ ] AI logs are included.
- [ ] AI Reflection is included.
- [ ] ZIP follows the required structure.

---

## 16. Notes

The LAB requirements remain the top priority. Enterprise-like features should improve the design and report quality, but they should not break MVC, CSV persistence or the concurrency simulator deliverables.

