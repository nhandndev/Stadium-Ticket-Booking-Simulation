# Agent Project Context

Read the root README first for the group's problem statement, delivered scope,
startup commands and sample credentials. The recorded contributor is
Doan Ngoc Nhan — QE210282.

## Current Baseline

- Java 17, Console input/output and headerless UTF-8 CSV persistence.
- Controller-first MVC with Services, domain Models and Request/Response DTOs.
- Main constructs dependencies. MainController owns entry navigation.
- Domain Controllers coordinate Views and Services. Services use Repositories.
- Authenticated roles are FAN, SELLER, GATE_STAFF and ADMIN.
  UNASSIGNED Staff receive a role through account administration before login.
- Booking, online/cash checkout, tickets, Gate check-in, refunds, venue/match
  administration and account/role management are implemented.
- Checkout uses SYNCHRONIZED by default. The simulation module was removed.

## Where to Work

| Concern | Source location |
| --- | --- |
| Startup and menus | src/app/Main.java, MainController.java and MainView.java |
| Authentication | src/auth/ |
| Accounts and roles | src/user/ and src/admin/ |
| Venues and physical seats | src/stadium/ |
| Match browsing, pricing and inventory | src/match/ |
| Checkout and booking transactions | src/booking/ |
| Payments, tickets and refunds | src/payment/, src/ticket/ and src/refund/ |
| Persistent fixtures | data/*.csv |
| Canonical UML and Use Cases | docs/ClassDiagram.drawio |
| Expanded Mermaid contracts | docs/Class_Diagrams/*.mmd |
| Recorded Console acceptance flows | docs/Demo_Tung_Feature/ |

The canonical draw.io has six pages. Page 00 contains Use Cases, 01 accounts and
browsing, 02 administration, 03 booking/payment/tickets, 04 repositories and
05 domain models. Five Mermaid files correspond to class pages 01–05.
The last source inspection matched 97 contracts and 190 expanded class boxes.

## Business Rules to Preserve

- Booking creation stores a PENDING quote for one to four distinct seats.
- Seats are checked again and committed when online/cash checkout executes.
- BookingService checkout and MatchSeatRepository seat commits share the
  MatchSeatRepository.class monitor. Synchronization applies within one JVM.
- Successful online checkout issues tickets through BookingService.issueTickets.
  TicketService handles ticket querying/validation/check-in operations.
- Completed-payment and ticket-issue retries preserve existing results.
- Cancellation, refund review and account actions enforce their ownership,
  status and history restrictions.
- Seat is physical layout. MatchSeat is inventory for one Match.
- Transaction CSV uses 11 columns and requires bookingId. The parser accepts
  legacy 12-column booking records when the extra second column is empty.

Read the SRS and booking-protection document before changing these rules.

## Coding and Alignment Conventions

- Keep implementation straightforward for a plain-Java Console project.
- Follow the existing package-by-domain structure and enums subpackages.
- Attributes are private. UML expands explicit constructors and public methods.
  Private implementation helpers remain internal to the class.
- Preserve established class/method names and DTO contracts. When a requested
  behavior requires a contract change, update code, diagram members and docs together.
- Use the canonical diagram and current source when comparing contracts.
  secret/ and private/ hold ignored local practice/planning material.
- Keep the supplied unique Fan names, physical-seat layout and business history
  when adjusting fixtures. Use a data copy for demos that write records.
- Maintain the personal AI audit for substantive agent contributions.
- Keep root README and this file in English. The presentation and feature demos
  use Vietnamese.

## Run and Verify

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out app.Main
```

The first application argument selects the CSV directory. The optional second
argument --payment-fails makes the online payment mock return failure.

Retained checks and recorded verification are documented in
[verification and delivery](docs/05_VERIFICATION_AND_DELIVERY.md).
The Java source inspector can be run with the following command.

```bash
java scripts/InspectDiagram.java
```

Its output describes source contracts for comparison with the canonical UML.
The feature guides document 139 captured flows across 88 feature files.
Their setup and order files define the fixture and dependent operation sequence.

## Operating Limits

Payment/refund behavior is mocked and demo passwords are plaintext.
Audit coverage currently includes refund review. CSV replacement is per-file,
so interrupted multi-file operations may require reconciliation. Full rendered
UML wire routing requires visual review before submission.

## Essential Reading

- [Documentation index](docs/README.md).
- [Requirements](docs/01_SRS.md).
- [Architecture](docs/03_ARCHITECTURE.md).
- [Booking protection](docs/04_BOOKING_CONCURRENCY.md).
- [Use Case traceability](docs/06_USE_CASE_TRACEABILITY.md).
- [AI audit](ai_logs/doan_ngoc_nhan_QE210282_ai_log.md).
