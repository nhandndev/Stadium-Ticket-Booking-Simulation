# Stadium Ticket Booking Simulation

## 1. Project Overview

**Course:** LAB211 — OOP with Java  
**Project:** Stadium Ticket Booking Simulation  
**Member:** Doan Ngoc Nhan.  
**Architecture:** MVC (Model - View - Controller)  
**Persistence:** CSV files  
**Core Research Problem:** Preventing **Double Booking** when many Fan Threads attempt to book the same seat concurrently.

The project simulates a stadium ticket-booking system and compares different synchronization mechanisms to determine which approach can preserve data consistency while maintaining acceptable throughput.

---

## 2. Main Features

### Fan / Guest
- View match list
- View match details
- View stadium sections
- View seat map
- View seat availability
- Register / Login
- Select up to 4 seats per transaction
- Book tickets
- View owned tickets
- View booking history

### Staff Extension
- Seller-assisted booking
- Offline payment confirmation
- Ticket lookup
- Support for booking/payment/ticket issues
- Administrator CRUD for Stadium, Section, Seat, Match and Fan

> Note: Seller, Support Staff and payment features are business extensions beyond the minimum LAB requirements.

### Concurrency Simulator
- Configure number of Fan Threads
- Configure target seats
- Select synchronization mechanism
- Run concurrent booking simulation
- Detect Double Booking
- Measure throughput
- Measure conflict/failure counts
- Export simulation results

Supported synchronization strategies:

- `NO_LOCK`
- `SYNCHRONIZED`
- `FILE_LOCK`
- `OPTIMISTIC`

---

## 3. Core Business Rules

1. A seat already marked `BOOKED` cannot be sold again for the same match.
2. A Fan may book a maximum of **4 tickets per transaction**.
3. Seat lifecycle:

```text
AVAILABLE -> LOCKED -> BOOKED
```

4. If payment/booking fails before confirmation:

```text
LOCKED -> AVAILABLE
```

5. For a successful booking:

```text
Match + Seat -> maximum 1 valid Ticket
```

6. Fan booking and Seller booking must use the same booking engine and synchronization logic.

---

## 4. Project Structure

Expected submission structure:

```text
NHOM_XX_LAB211_TicketBooking/
├── src/
│   ├── model/
│   ├── repository/
│   ├── controller/
│   ├── view/
│   ├── service/
│   ├── exception/
│   ├── util/
│   └── Main.java
│
├── data/
│   ├── stadiums.csv
│   ├── sections.csv
│   ├── seats.csv
│   ├── fans.csv
│   ├── matches.csv
│   ├── tickets.csv
│   └── transactions.csv
│
├── docs/
│   ├── report.docx
│   ├── slide.pptx
│   ├── class_diagram.png
│   └── flowcharts/
│       ├── booking_flow.png
│       ├── synchronization_flow.png
│       └── simulator_flow.png
│
├── ai_logs/
│   ├── member1_ai_log.md
│   ├── member2_ai_log.md
│   └── ...
│
└── README.md
```

---

## 5. MVC Architecture

The application must follow MVC strictly.

### Model
Responsible for:
- Entity data
- Business rules
- CSV parsing/serialization
- Seat state management

### Repository
Responsible for:
- Reading CSV files
- Writing CSV files
- CRUD operations
- Searching/filtering data

### Controller
Responsible for:
- Receiving requests from View
- Calling Model/Repository/Service
- Coordinating application flow

### View
Responsible for:
- Displaying menus
- Displaying seat maps
- Reading user input
- Displaying simulator reports

### Important Restrictions

- Business logic must **not** be placed in View.
- Controller must **not** access CSV files directly.
- CSV access must go through Model/Repository layers.

---

## 6. Data Generation

The LAB requires a total dataset of at least **10,000 rows**, with the seat dataset expected to be the largest.

Run the data generator before running the main program.

### If using IDE

Run:

```text
DataGenerator.java
```

Then verify that files are created under:

```text
data/
```

### If using command line

> Replace package/class names below if the project uses different names.

Compile:

```bash
javac -d out $(find src -name "*.java")
```

Run DataGenerator:

```bash
java -cp out util.DataGenerator
```

Example verification:

```bash
wc -l data/*.csv
```

The generated dataset must include at least:

```text
stadiums.csv
sections.csv
seats.csv
fans.csv
matches.csv
tickets.csv
transactions.csv
```

---

## 7. Compile and Run Main Application

### Compile

```bash
javac -d out $(find src -name "*.java")
```

### Run

If `Main.java` has no package:

```bash
java -cp out Main
```

If Main belongs to a package, for example `app.Main`:

```bash
java -cp out app.Main
```

> Update the command above to match the final package structure.

---

## 8. Suggested Main Menu

```text
===== STADIUM TICKET BOOKING =====

1. Guest / Fan Booking
2. Staff Operations
3. Data Management
4. Concurrency Simulator
5. Exit
```

---

## 9. Booking Flow

Basic booking flow:

```text
Login
-> Select Match
-> Select Section
-> View Seat Map
-> Select 1-4 Seats
-> Validate Booking
-> Apply Synchronization
-> Lock Seat
-> Confirm Booking / Payment
-> Mark Seat BOOKED
-> Create Ticket
-> Record Transaction
```

The application must re-check seat availability during the booking transaction.

Viewing a seat as `AVAILABLE` does not guarantee that the seat is still available later.

---

## 10. Concurrency Simulator

The Simulator must create multiple booking tasks and start them as concurrently as possible.

Required Java concurrency utilities:

```text
ExecutorService
CountDownLatch
```

Typical test scenarios:

### High Contention

```text
1000 Fan Threads -> 1 Seat
```

### Medium Contention

```text
1000 Fan Threads -> 100 Seats
```

### Low Contention

```text
1000 Fan Threads -> many different Seats
```

---

## 11. Run Simulator

From the application:

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
1000 Threads x 4 Mechanisms
```

Test:

```text
NO_LOCK
SYNCHRONIZED
FILE_LOCK
OPTIMISTIC
```

---

## 12. Expected Simulator Metrics

Each simulation should record:

- Mechanism
- Thread count
- Total attempts
- Successful bookings
- Failed bookings
- Conflict count
- Double Booking count
- Execution time
- Throughput
- Double Booking rate

Example:

```text
Throughput = Successful Bookings / Execution Time
```

Critical validation:

```text
same Match + same Seat + multiple valid Tickets
=> Double Booking
```

---

## 13. Expected Research Result

`NO_LOCK` is used as the unsafe baseline and may produce race conditions.

Safe synchronization strategies are expected to prevent Double Booking.

The research comparison should discuss the trade-off between:

```text
Data Consistency
vs.
Concurrency
vs.
Throughput
```

The final report should include a chart comparing:

```text
Throughput (tickets/second)
vs.
Double Booking Rate (%)
```

for each synchronization mechanism.

---

## 14. CSV Files

### stadiums.csv
Stores stadium information.

### sections.csv
Stores stadium section information.

### seats.csv
Stores seat information and seat status.

Important field for optimistic locking:

```text
version
```

The version must increase whenever the seat state is updated.

### fans.csv
Stores Fan information.

### matches.csv
Stores match information.

### tickets.csv
Stores successfully issued tickets.

### transactions.csv
Stores booking/simulation transaction results.

---

## 15. Important Entities

Core LAB entities:

```text
Stadium
Section
Seat
Match
Fan
Ticket
BookingTransaction
```

Extended product entities may include:

```text
Booking
BookingItem
PaymentTransaction
SimulationResult
Staff
```

---

## 16. Custom Exceptions

The project should contain at least 5 custom exceptions.

Suggested exceptions:

```text
SeatNotFoundException
SeatNotAvailableException
BookingLimitExceededException
MatchNotFoundException
FanNotFoundException
OptimisticLockException
CsvDataException
FileAccessException
```

---

## 17. Required Diagrams

The documentation must include:

### UML Class Diagram
Must show:
- Attributes
- Methods
- Visibility
- Relationships
- Multiplicity
- BaseEntity hierarchy
- Generic CsvRepository<T>

### Required Flowcharts

1. Booking Flow
2. Synchronization / Double Booking Prevention Flow
3. Simulator Flow

A Data Generation flow may also be included.

---

## 18. Performance Target

Repository implementation should be tested with the large CSV dataset.

LAB target:

```text
Read >= 10,000 rows in < 500 ms
```

Record actual results in the report instead of assuming the target is met.

---

## 19. Testing Checklist

### Single-thread Booking
- [ ] Fan can register
- [ ] Fan can login
- [ ] Match list loads correctly
- [ ] Seat map displays correctly
- [ ] Fan can select 1-4 seats
- [ ] Ticket is created after successful booking
- [ ] BOOKED seat cannot be booked again

### CRUD
- [ ] Seat CRUD
- [ ] Fan CRUD
- [ ] Match CRUD
- [ ] Search by condition works

### CSV
- [ ] CSV parse works
- [ ] CSV serialization works
- [ ] >= 10,000 rows generated
- [ ] Invalid CSV data handled safely

### Concurrency
- [ ] NO_LOCK demonstrates possible race condition
- [ ] SYNCHRONIZED prevents Double Booking
- [ ] FILE_LOCK prevents Double Booking
- [ ] OPTIMISTIC detects version conflict
- [ ] CountDownLatch is used
- [ ] ExecutorService is used
- [ ] 100-500 thread demo works
- [ ] 1000-thread experiment works

### Simulator Metrics
- [ ] Success count
- [ ] Failure count
- [ ] Conflict count
- [ ] Double Booking count
- [ ] Execution time
- [ ] Throughput
- [ ] Double Booking rate

---

## 20. Submission Checklist

Before submission:

- [ ] Source code compiles successfully
- [ ] MVC architecture is respected
- [ ] No business logic in View
- [ ] Controller does not access CSV directly
- [ ] CSV data >= 10,000 rows
- [ ] DataGenerator works
- [ ] Seat/Fan/Match CRUD works
- [ ] At least 5 custom exceptions
- [ ] At least 3 synchronization mechanisms
- [ ] CountDownLatch used in Simulator
- [ ] ExecutorService used in Simulator
- [ ] 1000-thread experiment completed
- [ ] Throughput chart completed
- [ ] Double Booking Rate chart completed
- [ ] UML Class Diagram completed
- [ ] 3 required flowcharts completed
- [ ] Report completed
- [ ] Slide deck completed
- [ ] Each member has an individual AI Log
- [ ] AI Reflection completed

---

## 21. Documentation

Required documentation:

```text
docs/report.docx
docs/slide.pptx
docs/class_diagram.png
docs/flowcharts/
```

The report should explain:

1. Problem background
2. Requirements
3. System architecture
4. Data model
5. Booking algorithm
6. Synchronization mechanisms
7. Simulator design
8. Experimental setup
9. Results
10. Throughput vs. Double Booking comparison
11. Research conclusion
12. AI Reflection

---

## 22. AI Log

Each team member must maintain their own raw AI interaction log.

Example:

```text
ai_logs/member1_ai_log.md
ai_logs/member2_ai_log.md
```

AI Reflection should explain:

- What AI helped with
- Which AI outputs were incorrect or incomplete
- How the team verified/corrected AI output
- How prompts improved over time
- Risks of depending too much on AI
- Lessons learned

---

## 23. Submission Package

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

---

## 24. Notes

This README covers both:

1. **Minimum LAB211 requirements**, especially MVC, CSV persistence, CRUD, concurrency synchronization and Simulator.
2. **Extended real-world ticket-booking requirements**, including Guest, Seller, Support Staff, Administrator and payment flows.

When implementing the project, the LAB requirements should remain the priority. Extended features should not break the required MVC structure or delay the concurrency/simulator deliverables.
