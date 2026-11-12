# Stadium Ticket Booking

Java 17 Console application with CSV persistence and controller-first MVC.
Prepared by Doan Ngoc Nhan — QE210282.

## Our Group's Problem Statement

A stadium ticket-selling process needs to coordinate match schedules, seating,
prices, customer orders, payment records and entry validation. Each physical
seat can be sold for different matches, so availability must be tracked for each
match separately. Online customers and counter staff also need a consistent view
of the seats available for purchase. Two customers attempting to purchase the
same seat must receive outcomes consistent with the inventory.

Our group develops a Console-based system covering the ticket lifecycle from
venue preparation to entry at the stadium. An Administrator manages stadiums,
sections, seats, matches, prices and sales status. Guests browse matches and
register. Fans create bookings and pay through a mock online payment service.
Sellers serve account holders and walk-in customers at the counter, record cash
payments and issue tickets. Walk-in purchases are identified by Booking ID and ticket codes.
Gate Staff validate tickets and record check-in. Eligible paid bookings can enter
an Administrator-reviewed refund process.

Unpaid bookings reserve their selected seats for up to 30 minutes, ending by
kick-off. Fans can pay online within the deadline or bring the Booking ID to a
Seller for offline payment, followed by the separate ticket-issue operation.

The solution applies object-oriented design through domain models, Request and
Response DTOs, Controllers, Views, Services and CSV Repositories. Ordinary online
and cash checkout use the existing SYNCHRONIZED mechanism to coordinate seat
availability checks and commits within one JVM. The project focuses on completing
and explaining these business workflows, their validation rules and persistence.

## Project Scope

### Users and Delivered Features

| Actor | Responsibilities |
| --- | --- |
| Guest | Search/filter matches, list schedules, view Match details and seat availability, register and open login. |
| User | Login through Fan/Staff routes, view/update profile and logout. Stored role and account status determine access. |
| Fan | Create a booking of up to four seats, pay online immediately or later, use Seller offline payment within the same deadline, view booking/ticket history and details, cancel unpaid bookings and request/track refunds. |
| Seller | Search Fan, Create Fan, Create booking for Fan, Accept offline payment, Issue ticket, View seat map and prices, View customer booking history. |
| Gate Staff | Validate a ticket for a Match and confirm one-time check-in. |
| Administrator | Venue/seating, Match/pricing/sales and account/role management, refund review, system summary and audit records. |
| Payment Service | Return the local mock online payment result. |

### Seller Scope

The sales-counter menu contains the seven features shown in the Use Case.

1. Search Fan by name/email or list accounts with status.
2. Create Fan when the customer wants an account.
3. Create booking for an ACTIVE Fan, with the previously agreed walk-in branch
   inside this operation. Walk-ins use Booking ID and ticket codes.
4. Accept offline payment for a PENDING booking before its payment deadline.
5. Issue ticket for a CONFIRMED booking with successful payment.
6. View the seat map, prices and availability for a Match.
7. View the selected Fan account's booking history.

Booking creation, offline payment and ticket issue are separate operations.
Seller can take payment for a booking created by a Fan online. The Booking ID
identifies the order for payment or issue. Repeated issue is idempotent.

### Administrator Scope

- Stadium and Section creation, lists/details, update and eligible deletion.
- Seat creation, lists/details, update, eligible deletion and activation/deactivation.
- Match creation, lists/details, update and eligible deletion.
- Match/Section ticket-price creation, lists/details, update and deletion.
- Open/close ticket sales and view sales status.
- Fan account creation, lists/details, search/filter, update,
  activation/deactivation and history-safe deletion.
- Staff account creation, lists/details, search/filter, update and activation/deactivation.
- View available Staff roles and assign/change/revoke a role.
- Approve/reject eligible refund requests, view summary and stored audit records.

### Booking and Payment Rules

- Seat represents physical layout. MatchSeat tracks inventory for an individual Match.
- A booking selects one to four distinct eligible seats in one future OPEN Match.
- Creating PENDING stores the quoted total, createdAt and expiresAt, and holds
  every selected seat for that booking under the shared synchronization lock.
- The deadline is 30 minutes after creation, capped at kick-off when earlier.
  The Console shows the persisted deadline.
- Fan can pay online immediately or through Pay pending booking later. Seller
  can accept offline payment for the same booking within that window.
- Held seats store LOCKED and holdBookingId. Only the owning booking can commit
  its holds to BOOKED. The seat map displays LOCKED as HELD.
- At the deadline, online and offline payment are rejected. Expiry sets EXPIRED
  and releases only that booking's holds. Cancellation and failed payment also release holds.
- Expiry is checked at startup, seat-map access, booking creation/history/details,
  payment/cancellation and administrative seat deactivation. Stored timestamps
  preserve the deadline across restarts.
- Payment rechecks the active account when present, OPEN future Match and usable seats.
- Successful payment confirms the booking. Online checkout issues tickets.
  Seller offline payment records payment, followed by the separate Issue ticket action.
- Completed-payment and ticket-issue retries preserve the existing result.
- Gate validates ticket/Match/booking membership and records one-time check-in.
- Paid bookings use the eligible Fan refund-request/review workflow. Approval
  cancels the booking/tickets and releases BOOKED seats. Rejection preserves the sale.
- Errors identify malformed, duplicate, missing, inactive, held, booked,
  wrong-Stadium and unpublished Seat IDs.

### Implementation Boundaries

- Runtime is Java 17 Console with headerless UTF-8 CSV files.
- SYNCHRONIZED coordinates cooperating threads within one JVM.
- Expiry is evaluated on application operations using stored timestamps.
- Payment/refund use mock behavior. Demo credentials are plaintext.
- CSV replacement is per file. Interrupted multi-file operations can require reconciliation.
- Account status uses ACTIVE/INACTIVE. UNASSIGNED Staff require a role before login.
- Walk-in purchases use counter cash and ticket issue. Refund self-service is
  an authenticated Fan workflow.
- Audit coverage currently includes refund review. The large dataset is static CSV.
- Booking CSV writes eight columns, adding createdAt/expiresAt. Legacy six-column
  records remain readable. Legacy unpaid PENDING rows without timestamps expire
  on access. Historical CONFIRMED records retain their paid state.
- MatchSeat CSV writes six columns with holdBookingId and reads legacy five-column rows.

## Architecture and Main Flow

```text
Main
  -> MainController
     -> domain Controller
        -> View                         Console input and display
        -> Service                      validation and business operations
           -> Repository -> CSV         persistence
           -> Model                     domain state

Request/Response DTOs carry data across application boundaries.
```

Controllers own menu navigation and coordinate Views and Services. Services
apply business rules and map response DTOs. Repositories handle CSV access.

A typical Fan purchase follows this sequence.

1. Log in and browse/select a Match.
2. Select seats and create PENDING with the displayed payment deadline.
3. Pay online now, return to Pay pending booking later, or give the Booking ID
   to Seller for offline payment before that deadline.
4. Payment commits the owning booking's held seats to BOOKED and confirms the order.
5. Online checkout issues tickets. Seller uses Issue ticket after offline payment.
6. View history and present a valid ticket for Gate check-in.

## Build and Run

Run from the project root with JDK 17 or later.

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out app.Main
```

For a demo, create a separate data copy.

```bash
DEMO_DIR=$(mktemp -d /tmp/stadium-console.XXXXXX)
cp data/*.csv "$DEMO_DIR/"
java -cp out app.Main "$DEMO_DIR"
```

To exercise mock online payment failure, launch the copy with the flag below.

```bash
java -cp out app.Main "$DEMO_DIR" --payment-fails
```

| Role | Sample email | Sample password |
| --- | --- | --- |
| Administrator | admin@stadium.com | admin123 |
| Seller | seller1@stadium.com | seller123 |
| Gate Staff | gate1@stadium.com | gate123 |
| Fan | binh.tran@gmail.com | 12345678 |

New or changed passwords require at least eight characters. The sample CSV
contains legacy Staff credentials. Use the actual CSV if credentials have changed.

## Supplied Data

The current fixture contains 2 Stadiums, 5 Sections, 54 Matches, 10 Prices,
10100 physical Seats and 1008 Fans, including 1006 ACTIVE accounts at review.
The fixture uses 14 CSV files. This match expansion adds 50 rows. Booking,
payment, ticket and transaction totals grow as the application is used.

Match IDs 5–54 add 50 fictional fixtures across 59 distinct team names,
covering Vietnamese clubs, international clubs and national teams. Kick-off
times vary across ten afternoon/evening slots from January 2026 to January 2029.
As of 2026-11-12, the additions include 10 past and 40 future matches.
They use the two existing Stadiums and start CLOSED, following the normal
create-match workflow. An Administrator can configure prices and open sales for
eligible future matches. Existing match records and their sales data remain.

The 1000 added Fan accounts have distinct random Vietnamese full names.
Their fixture emails range from simfan0001@example.invalid to
simfan1000@example.invalid, with password FanDemo123. Staff ID 5 is UNASSIGNED
and can receive an eligible role through Administrator account management.

Physical capacity and published inventory are separate. The supplied
match_seats.csv has 20 seats for Match 1 and 20 for Match 4. Administrator sales
preparation publishes eligible inventory for a Match with configured prices.
Use the feature-demo preparation guide for a smaller fixture with reproducible IDs.

## Essential Documentation

Read the documents in this order.

1. [SRS](docs/01_SRS.md) for detailed requirements and business rules.
2. [Use Case specification](docs/02_USE_CASE_SPECIFICATION.md) for actors and features.
3. [Architecture](docs/03_ARCHITECTURE.md) for layer responsibilities.
4. [Booking protection](docs/04_BOOKING_CONCURRENCY.md) for checkout locking and transactions.
5. [Verification and delivery](docs/05_VERIFICATION_AND_DELIVERY.md) for checks and submission preparation.
6. [Use Case traceability](docs/06_USE_CASE_TRACEABILITY.md) for feature-demo links.
7. [AI reflection guide](docs/07_AI_REFLECTION_GUIDE.md) for T10 and usage records.
8. [Vietnamese presentation guide](docs/08_PRESENTATION_GUIDE.md).

- [Canonical Use Case and Class Diagram](docs/ClassDiagram.drawio) contains
  pages 00–05. The [diagram guide and five Mermaid files](docs/Class_Diagrams/README.md)
  describe the class contracts.
- [Per-feature Console demos](docs/Demo_Tung_Feature/README.md) contain 87 feature
  files and 148 recorded flows. Start with their preparation guide.
- [Agent project context](READMEFORAGENT.md) explains source locations,
  conventions and how to keep changes aligned.
- [Personal AI audit](ai_logs/doan_ngoc_nhan_QE210282_ai_log.md) records contributions
  and their verification.

The current verification matched 97 source class/enum contracts to 192 expanded
class boxes and five Mermaid files, and mapped 82 visible Use Case nodes to the
feature guides. Full rendered diagram routing remains a visual review step.

## Project Layout

```text
src/app/           entry point, dependency wiring and navigation
src/common/        BaseEntity, CSV foundation and application errors
src/auth/, user/   authentication, profiles, accounts and roles
src/stadium/       stadiums, sections and physical seats
src/match/         browsing, matches, pricing and per-match inventory
src/booking/       bookings, checkout, transactions and default strategy
src/payment/       mock payment and payment records
src/ticket/        ticket issue records, history, validation and check-in
src/refund/        refund requests and review
src/admin/         management, summary and audit
src/seller/, gate/ role-specific Console Views
data/              supplied business CSV fixtures
docs/              essential specifications, diagrams and feature demos
ai_logs/           personal AI usage and prompt audit records
scripts/           Java source-contract inspection
```
