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
Sellers assist customers at the counter, record cash payments and issue tickets.
Gate Staff validate tickets and record check-in. Eligible paid bookings can enter
an Administrator-reviewed refund process.

The solution applies object-oriented design through domain models, Request and
Response DTOs, Controllers, Views, Services and CSV Repositories. Ordinary online
and cash checkout use the existing SYNCHRONIZED mechanism to coordinate seat
availability checks and commits within one JVM. The project focuses on completing
and explaining these business workflows, their validation rules and persistence.

## Project Scope

### Users and Delivered Features

| Actor | Responsibilities |
| --- | --- |
| Guest | Search and filter matches, list schedules, view match details and seat availability, register a Fan account. |
| User | Shared login, profile viewing/updating and logout capabilities. Stored account role determines the authenticated menu. |
| Fan | Select seats, create bookings, make mock online payments, view booking/ticket history and details, cancel unpaid bookings, submit and track refund requests. |
| Seller | Search/list/create customer accounts, view seats and prices, create customer bookings, view customer booking history, confirm cash receipt and issue tickets. |
| Gate Staff | Validate ticket codes for a match and confirm check-in. |
| Administrator | Stadium/Section/Seat/Match CRUD, seat activation, match-section pricing, sales opening/closing, account management, staff role assignment/change/revocation, refund review, summary and audit viewing. |
| Payment Service | Return the mock online payment result used by checkout. |

### Booking and Inventory Rules

- A Seat belongs to a Section within a Stadium. MatchSeat tracks that seat's
  availability for an individual Match.
- One booking contains one to four distinct seats for one Match.
- Creation validates the active Fan, future OPEN Match, eligible seats and prices,
  then stores a PENDING booking with its quoted total.
- The PENDING stage stores the quote. Seat allocation happens during checkout.
- Both online and cash checkout recheck availability and commit the selected
  seats under the shared synchronization lock.
- Successful checkout confirms the booking. Online checkout issues tickets.
  Seller cash checkout supports issuing or retrieving tickets afterward.
- A seat conflict or failed mock payment cancels the attempted booking.
- Completed-payment and ticket-issue retries preserve the existing result.
- Gate validation checks the ticket, its Match and booking. Check-in records
  CHECKED_IN and rejects subsequent use.
- Refund review checks ownership, payment, kick-off time and ticket eligibility.
  Approval cancels the booking/tickets and releases seats. Rejection preserves
  the sale.
- Account roles and ownership checks apply to the corresponding application
  entry points. Historical links restrict unsafe data deletion.

### Implementation Boundaries

- Runtime is a local Java Console application with headerless UTF-8 CSV files.
- Checkout synchronization coordinates cooperating threads within one JVM.
- Payment and refund operations use mock behavior with local records.
- PENDING bookings retain their quote until checkout or cancellation.
- MatchSeat.version increments with state changes. SYNCHRONIZED is the active
  checkout mechanism.
- File replacement is atomic per file where supported. Interruption during a
  multi-file checkout can require reconciliation.
- Demo passwords are stored as plaintext despite the legacy passwordHash field.
- Account status uses ACTIVE and INACTIVE. UNASSIGNED Staff require a role before
  login. Administrator Fan deletion is restricted by booking history.
- Audit records currently capture refund review decisions.
- The large sample dataset is supplied as static CSV fixtures.

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

1. Browse a Match or log in through Login As Fan.
2. Select an eligible Match and seats, then create the PENDING booking.
3. Confirm the quoted payment amount.
4. Checkout rechecks/commits seats using SYNCHRONIZED and records the result.
5. Successful payment confirms the booking and issues its tickets.
6. View booking/ticket history and present a ticket for Gate validation/check-in.

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
| Fan | an.nguyen@gmail.com | 12345678 |

New or changed passwords require at least eight characters. The sample CSV
contains legacy Staff credentials. Use the actual CSV if credentials have changed.

## Supplied Data

The current fixture contains 2 Stadiums, 5 Sections, 4 Matches, 10 Prices,
10100 physical Seats and 1007 Fans, including 1005 ACTIVE accounts.
The 14 CSV files contain 11191 nonblank rows in total.

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
- [Per-feature Console demos](docs/Demo_Tung_Feature/README.md) contain 88 feature
  files and 139 recorded flows. Start with their preparation guide.
- [Agent project context](READMEFORAGENT.md) explains source locations,
  conventions and how to keep changes aligned.
- [Personal AI audit](ai_logs/doan_ngoc_nhan_QE210282_ai_log.md) records contributions
  and their verification.

The previous verification matched 97 source class/enum contracts to 190 expanded
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
tests/             retained Java booking/seed checks
scripts/           Java source-contract inspection
```
