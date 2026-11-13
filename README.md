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

Customers may reserve seats and pay later, pay online immediately, or bring their
Booking ID to a sales counter. Counter staff also serve walk-in customers who
buy without creating an account. These purchase paths share the same inventory,
so the system must keep track of which booking owns a held seat, when that hold
expires, and whether payment and ticket issue have already succeeded.

The booking lifecycle also includes unsuccessful actions. Customers can abandon
payment, enter invalid Seat IDs, request a refund, or present a ticket for the
wrong Match or for a second entry. Administrative changes must respect existing
booking history, inventory and account permissions. The group's problem is to
coordinate these cases throughout one consistent ticket-selling workflow.

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
Seller for offline payment and immediate ticket issue in the same counter flow.

The solution applies object-oriented design through domain models, Request and
Response DTOs, Controllers, Views, Services and CSV Repositories. Ordinary online
and cash checkout use the existing SYNCHRONIZED mechanism to coordinate seat
availability checks and commits within one JVM. The project focuses on completing
and explaining these business workflows, their validation rules and persistence.

## Feature Highlights

- **Availability per Match.** Seat stores the physical location; MatchSeat stores
  its availability for one Match. The supplied 200 physical Seats support
  100 Matches through 10000 separate inventory records.
- **Persisted seat holds.** A PENDING booking owns its selected seats for up to
  30 minutes, capped at kick-off. The stored deadline survives application
  restarts, and relevant operations expire unpaid orders and release owned holds.
- **Online and counter payment for the same order.** Fan can pay immediately or
  later online, or provide the Booking ID to Seller for cash payment within the
  same deadline. Payment uses the booking's quoted amount.
- **Integrated counter checkout.** Seller serves an ACTIVE Fan or a walk-in
  customer, creates the booking, confirms cash receipt and immediately issues
  tickets. Deferring cash collection keeps the booking and displays its deadline.
- **Repeatable paid operations.** Completed payment checks reuse stored results.
  Reopening a confirmed cash booking can return its existing tickets or finish
  missing ticket issue while preserving the original payment.
- **Coordinated seat updates.** A shared SYNCHRONIZED lock protects the seat group
  check and update within one JVM. Payment commits only the holds owned by the
  booking, and expiry/cancellation releases matching holds.
- **Refund review and one-time entry.** Eligible Fan orders can request refund
  before kick-off. Administrator approval cancels the booking/tickets and releases
  seats. Gate Staff validates the ticket and records one-time check-in.
- **Ownership and history-aware administration.** Fan accesses its own orders,
  tickets and requests. Staff operations follow the stored role. Administrative
  checks protect referenced data and the last ACTIVE Administrator.

## Project Scope

### Business Coverage

The delivered system covers venue and seating preparation, Match scheduling,
Section pricing, sales opening/closure, account management, self-service and
counter booking, payment, ticket issue, entry validation, cancellation and refund
review. Each role reaches its operations through a Console dashboard.

Booking history records orders, totals and payment deadlines. Ticket history
records individual issued seats and entry state. Refund requests retain their
review outcome, and refund review creates an audit record.

### Technical and Data Scope

- Java 17 Console with controller-first MVC, Services, Models, DTOs and CSV Repositories.
- Main creates and connects dependencies through constructors. Controllers
  coordinate Console Views and business operations.
- Headerless UTF-8 CSV stores accounts, venues, Matches, prices, inventory and sales history.
- Local mock online payment and refund processing support demonstration of the workflows.
- SYNCHRONIZED protects cooperating threads in one JVM. Deadline processing runs
  on startup and relevant operations using persisted timestamps.
- The initial runtime dataset has 2 Stadiums, 100 physical Seats per Stadium,
  100 Matches split equally between the Stadiums, 500 prices and 10000 MatchSeat rows.
- Past and future Matches, OPEN/CLOSED sales, VIP/Premium/Standard Sections and
  existing business history provide the supplied demonstration cases.

### Users and Delivered Features

| Actor | Responsibilities |
| --- | --- |
| Guest | Search/filter matches, list schedules, view Match details and seat availability, register and open login. |
| User | Login through Fan/Staff routes, view/update profile and logout. Stored role and account status determine access. |
| Fan | Create a booking of up to four seats, pay online immediately or later, use Seller offline payment within the same deadline, view booking/ticket history and details, cancel unpaid bookings and request/track refunds. |
| Seller | Search/create Fan, create booking with cash checkout, pay an existing booking with ticket issue, view seat map/prices and customer booking history. |
| Gate Staff | Enter Ticket Code, review customer/Match/seat details and confirm one-time check-in. |
| Administrator | Venue/seating, Match/pricing/sales and account/role management, refund review, system summary and audit records. |

MockPaymentService returns the local mock result inside the online-payment flow.

Match access follows the current Console menus. Guest and Fan can list/search/filter
Matches and view details/availability. Seller lists/selects Matches and views their
details, seat maps and prices within counter operations. Administrator lists and
views Match details in Match Management. Authenticated Admin and Seller menus
currently provide no team-keyword search or Stadium/date-filter entry. Gate Staff
enters Ticket Code, reviews the resolved customer/Match/seat and confirms check-in. All authenticated roles share
profile, profile update and logout through User.

### Seller Scope

The Seller counter menu has five groups.

```text
1. Search Fan.
2. Create Fan.
3. Ticket sales.
   1. Create booking for Fan / walk-in customer.
   2. Pay for an existing booking.
   0. Back to Seller menu.
4. View seat map and prices.
5. View customer booking history.
0. Back to dashboard.
```

Creating a booking continues to the cash-receipt prompt. Confirming RECEIVED
records offline payment and immediately issues/displays tickets. Cancelling the
cash prompt keeps PENDING and displays the Booking ID and persisted deadline.
Ticket sales > Pay for an existing booking accepts that ID, including an order
created by a Fan online, and issues tickets after successful cash payment.
An already-CONFIRMED cash booking can resume ticket issuance through the same
entry without collecting cash again. The existing payment and issue methods remain separate code operations.

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
  Seller cash checkout records payment and immediately calls ticket issue.
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
5. Online checkout issues tickets. Seller cash checkout issues tickets in the same flow.
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

The current runtime dataset contains 2 Stadiums, 10 Sections, 200 physical Seats,
100 Matches, 500 Section prices and 10000 MatchSeat records across 14 CSV files.
Each Stadium has exactly 100 physical Seats and hosts 50 Matches. Every Match has
100 inventory records referring only to the physical Seats of its own Stadium.

### Physical Layout

- My Dinh uses Seat IDs 1–100. Hang Day uses Seat IDs 101–200.
- Each Stadium has rows A–J, with seats 1–10 in every row.
- Rows A/B belong to VIP, C/D to Premium, and E–J to Standard Sections.
- Each of the five Sections in a Stadium contains 20 physical Seats.
- VIP/Premium labels belong to Section names and prices. Seat row labels remain A–J.
- Every physical Seat in this fixture starts active.

The headerless Seat CSV format remains id,sectionId,rowLabel,seatNumber,active.

```text
1,1,A,1,true
2,1,A,2,true
3,1,A,3,true
```

### Matches, Prices and Inventory

As of 2026-11-14, the fixture has 23 past Matches and 77 future Matches.
All past Matches are CLOSED. Future Matches include 52 OPEN and 25 CLOSED.
My Dinh has 27 OPEN/23 CLOSED Matches. Hang Day has 25 OPEN/25 CLOSED.
These fictional fixtures retain the original Match IDs, teams, Stadiums and
kick-off times for the first 54 Matches and extend the total to 100. The schedule
covers January 2026 through November 2030 with varied afternoon/evening times.

Each Match has five valid Section prices. Existing valid prices are retained,
missing prices are supplied, and the anomalous 50 VND fixture price is corrected.
Stored Booking/Payment totals remain consistent with the historical Seat selections.
MatchSeat rows are reindexed from 1 to 10000. Existing status/version/hold ownership
is retained by the pair (matchId,seatId), which is how business records identify seats.

All 100 fixture Matches have inventory and prices prepared, including CLOSED
Matches. Administrator can open eligible future Matches or create a fresh Match
for the normal preparation workflow. CRUD operations can grow these initial counts.

### Existing Accounts and History

The 1008 Fan accounts include the 1000 distinct randomly named demo Fans.
Their fixture emails range from simfan0001@example.invalid to
simfan1000@example.invalid, with password FanDemo123. Staff ID 5 is UNASSIGNED
and can receive an eligible role through Administrator account management.

Existing Booking, Payment, Ticket, Refund, transaction and audit rows retain their
IDs and values. Physical Seat IDs referenced by this history keep their original
Section, row and number. Persisted PENDING holds continue to use the stored deadline.

The feature-demo core fixture is a smaller recorded dataset for reproducible
historical IDs. Use a copy of data for demonstrations of the 100-Match layout.

## Essential Documentation

Read the documents in this order.

1. [SRS](docs/01_SRS.md) for detailed requirements and business rules.
2. [Use Case specification](docs/02_USE_CASE_SPECIFICATION.md) for actors and features.
3. [Architecture](docs/03_ARCHITECTURE.md) for layer responsibilities.
4. [Booking protection](docs/04_BOOKING_CONCURRENCY.md) for checkout locking and transactions.
5. [Verification and delivery](docs/05_VERIFICATION_AND_DELIVERY.md) for checks and submission preparation.
6. [Use Case traceability](docs/06_USE_CASE_TRACEABILITY.md) for feature-demo links.
7. [AI reflection guide](docs/07_AI_REFLECTION_GUIDE.md) for T10 and usage records.
8. [Presentation guide](docs/08_PRESENTATION_GUIDE.md).

- [Canonical Use Case and Class Diagram](docs/ClassDiagram.drawio) contains
  pages 00–05. The [diagram guide and five Mermaid files](docs/Class_Diagrams/README.md)
  describe the class contracts.
- [Per-feature Console demos](docs/Feature_Demos/README.md) contain 87 feature
  files and 148 recorded flows. Start with their preparation guide.
- [Agent project context](READMEFORAGENT.md) explains source locations,
  conventions and how to keep changes aligned.
- [Personal AI audit](ai_logs/doan_ngoc_nhan_QE210282_ai_log.md) records contributions
  and their verification.

The current verification matched 97 source class/enum contracts to 192 expanded
class boxes and five Mermaid files, and mapped 75 unique Use Case nodes to the
feature guides. Full rendered diagram routing remains a visual review step.

The 2026-11-14 static review records actor access and the reachable Console entry
for every Use Case. Shared goals have one node. The documentation retains the
original execution dates of captured Console demos.

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

## Booking Details, Gate Entry and Audit Display

- Booking Details lists the booking's tickets with Ticket ID, Booking ID, Match,
  seat, Ticket Code and status. A booking without tickets displays `No tickets yet.`
- Ticket Code is `T-<Booking ID>-<Seat ID>`. Booking IDs are unique within this
  dataset, so separate bookings for the same physical seat have different codes.
  The ticket resolves its Match and the Match resolves its Stadium.
- Gate Staff enters Ticket Code only, reviews customer name/phone and Match/seat,
  then confirms check-in. Walk-in customer name and phone display `None`.
- Audit logs show the responsible Staff account's current name and role. They retain
  the stored actor ID internally. If the account is missing, display `Unknown staff
  #<id>` and role `UNKNOWN`. Current audit creation covers refund reviews.
