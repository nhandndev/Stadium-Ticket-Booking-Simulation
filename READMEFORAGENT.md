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
| Recorded Console acceptance flows | docs/Feature_Demos/ |

The canonical draw.io has six pages. Page 00 contains Use Cases, 01 accounts and
browsing, 02 administration, 03 booking/payment/tickets, 04 repositories and
05 domain models. Five Mermaid files correspond to class pages 01–05.
The last source inspection matched 97 contracts and 192 expanded class boxes.

The current Use Case overview contains 75 unique goals. Use one node per shared
goal and connect every participating actor. The traceability document preserves
established IDs, direct actors and Console entry paths. Check reachable menus
before adding an actor association. Seller has five counter menu groups. Ticket sales contains Create booking for Fan /
walk-in customer and Pay for an existing booking. Match listing/details, seat
selection and Booking details remain supporting steps.

## Runtime Dataset Baseline

The main data directory starts with 2 Stadiums, 10 Sections, 200 physical Seats,
100 Matches, 500 prices and 10000 MatchSeat rows. Each Stadium has 100 Seats
(rows A–J, ten seats per row) and 50 Matches. Every Match references exactly the
100 physical Seats of its Stadium. Rows A/B are VIP, C/D Premium and E–J Standard.
All fixture Matches have inventory and prices, with mixed sales status and past/
future dates. Runtime activity can change counts and states. The historical demo
core fixture stays a smaller dataset with its own recorded IDs.

## Business Rules to Preserve

- Creating PENDING holds one to four selected seats and persists createdAt/expiresAt.
- expiresAt is creation plus 30 minutes, capped at kick-off when earlier.
- LOCKED seats carry holdBookingId. Only that booking can commit its holds.
- Fan can pay online now/later, or Seller can accept offline payment by Booking ID
  within the same persisted deadline.
- Expiry sets EXPIRED and releases owned holds. Cancellation/failure also releases
  only that booking's holds. Cleanup is safe to repeat after release interruption.
- Expiry is checked on startup and relevant operations, including seat maps and
  administrative deactivation. BrowseService and StadiumService reference BookingService.
- Seller Ticket sales has create-new and pay-existing paths. Creation can defer
  payment or continue through cash collection and automatic ticket issue.
  Account-holder/walk-in selection is inside counter creation.
- Online checkout and successful Seller cash checkout call issueTickets. A confirmed
  cash booking can resume issue through Pay for an existing booking without a new charge.
  Existing payment/issue methods retain their public names and signatures.
- Fan booking/ticket/refund access is owner-scoped. Gate validates booking/Match/seat.
- Seat is physical layout. MatchSeat is inventory for one Match.
- Booking CSV writes eight columns with createdAt/expiresAt and reads legacy six.
  Legacy PENDING without timestamps expires on access, while paid history stays.
- MatchSeat CSV writes six columns with holdBookingId and reads legacy five.
- Transaction uses eleven columns and a required bookingId, with empty fanId for
  walk-ins. Its old booking-only twelve-column layout remains readable.

Read the SRS, traceability and booking-protection documents before changing rules.

## Coding and Alignment Conventions

- Keep implementation straightforward for a plain-Java Console project.
- Use `this.` for instance field access and calls to the current object's instance methods.
  Keep parameters, local variables and static member access appropriately qualified.
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
- Write all project documentation in English. Captured Console data retains its
  original proper names, input values and runtime output.

## Run and Verify

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out app.Main
```

The first application argument selects the CSV directory. The optional second
argument --payment-fails makes the online payment mock return failure.

Recorded checks and Console acceptance are documented in
[verification and delivery](docs/05_VERIFICATION_AND_DELIVERY.md).
The Java source inspector can be run with the following command.

```bash
java scripts/InspectDiagram.java
```

Its output describes source contracts for comparison with the canonical UML.
The 87 feature files retain 148 historical numbered flows and add current integrated-counter walkthroughs.
The checked-in small fixture makes the core walkthrough reproducible.
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
