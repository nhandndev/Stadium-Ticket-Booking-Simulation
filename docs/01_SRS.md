# Software Requirements Specification

Baseline date — 2026-11-12. Status — implemented submission scope.
This SRS supersedes the earlier extended research plan.

## 1. Purpose and Product

The application supports stadium ticket selling through a Java 17 Console.
Administrators prepare venue, match, price, account and sales data. Fans create
bookings and make mock online payments. Sellers record cash sales and issue
tickets. Gate Staff validate and check in tickets. Administrator-reviewed mock
refunds cancel eligible sales and release seats.

The root README, current source, six-page draw.io, five Mermaid files,
the booking-protection guide and the per-feature walkthroughs define this baseline.

## 2. Environment and Architecture

- Plain Java 17 or later with UTF-8 source/data.
- Local Console input/output and 14 business CSV files in data/.
- Main wires dependencies and starts MainController.
- Controller orchestrates View input/display and Service operations.
- Service applies business rules and uses Repository for source persistence.
- Model/entity classes hold domain state. DTOs carry input/output. Enums live
  in enums packages.
- Attributes are private. Public constructors/methods are expanded in UML.
  Private implementation helpers remain internal.
- Source contains 97 project class/enum contracts and no protected declarations.
- Physical Seat layout and MatchSeat inventory are separate.

## 3. Actors

- Guest browses public match information, registers and opens login.
- User represents shared authenticated profile/update/logout capabilities.
- Fan creates and accesses its own bookings, tickets and refund requests.
- Seller creates Fan accounts, books for customers, records cash and issues tickets.
- Gate Staff validates ticket codes and confirms check-in.
- Administrator manages venues, seats, matches, prices, sales, accounts, roles,
  refund review, summary and available audit logs.
- Staff roles include SELLER, GATE_STAFF, ADMIN and UNASSIGNED.
  UNASSIGNED Staff cannot log in.

## 4. Functional Requirements

### FR-01 — Public Browsing

- Search matches by team keyword.
- Filter matches by Stadium/date, including empty filters.
- View match details and prepared per-match seat availability.
- Search/filter results provide detail and seat-map actions.
- Authenticated Fan can enter booking from browsing results.
- Seller can browse from its dashboard.

### FR-02 — Authentication and Profile

- Register Fan with nonempty CSV-safe name, email, password and phone.
- New Fan registration passwords require at least eight characters.
- Email must pass the application's basic format validation and be unique
  across Fan and Staff repositories.
- Main provides Login As Fan and Login As Staff.
- Validate credentials and reject INACTIVE/UNASSIGNED accounts.
- Reject an account through the wrong Fan/Staff login route.
- Route authenticated users according to stored role.
- View current name, email, phone, role and status.
- Self-service profile updates change full name and phone.
- Logout clears the current session.
- Role and ownership checks protect application menu/business entry points.
  Administrator domain Controllers are reached through the AdminController gate.

### FR-03 — Venue and Seating Management

Administrator can create/list/view/update/delete Stadium and Section.
Sections belong to existing Stadiums.

Administrator can create/list/view/update/delete Seat and activate/deactivate it.
Seat positions must be unique within a Section. Referential/history restrictions
protect deletions and location changes. Non-AVAILABLE inventory blocks unsafe
seat deactivation.

### FR-04 — Match, Pricing and Sales

- Create/list/view/update/delete Match with Stadium, teams and start time.
- Restrict modification/deletion when inventory, prices or booking history make
  the requested change unsafe, as enforced by MatchService.
- Create/list/view/update/delete price for a Match/Section pair.
- Price must be positive and the Section must belong to the Match's Stadium.
- Open sales only with an eligible future Match, active seats and required prices.
- Prepare missing MatchSeat rows through the sales-opening workflow.
- Close sales with confirmation and view sales status.
- New physical capacity is published per Match through its inventory workflow.

### FR-05 — Fan Booking

- Select one to four unique positive Seat IDs.
- Require an ACTIVE Fan, future OPEN Match, active seats in its Stadium,
  prepared availability and valid prices.
- Calculate and store the quoted total when creating PENDING.
- PENDING stores a quote without a timed seat hold.
- List/view only the Fan's own bookings through Fan entry points.
- Cancel an unpaid PENDING booking directly.
- Paid bookings use the refund-request workflow.

### FR-06 — Payment and Ticket Issue

- Make mock online payment for the booking's quoted amount.
- Seller records cash payment after RECEIVED confirmation.
- Recheck/commit seat availability using SYNCHRONIZED during payment.
- Successful payment confirms booking. Online checkout issues tickets.
- Seller can issue/retrieve tickets after successful paid confirmation.
- Retry of completed payment/ticket issue must preserve the existing completed
  result and avoid duplicate tickets.
- Mock payment failure is recorded and handled. --payment-fails supports its demo.
- Record booking transactions and their actual success/conflict/failure outcomes.
- Ticket codes identify individual seats in a booking.

### FR-07 — History and Gate

- Fan can list ticket history and view ticket details.
- Booking history represents orders/totals. Ticket history represents individual
  issued/cancelled/checked-in tickets.
- Gate Staff validates code, Match and confirmed booking membership.
- Check-in requires confirmation and records CHECKED_IN with a timestamp.
- Wrong-Match, missing, cancelled or already-used tickets are rejected.
- A ticket can be checked in once.

### FR-08 — Cancellation and Refund

- Request refund for an eligible paid CONFIRMED booking owned by the Fan.
- Require a reason and prevent duplicate active requests.
- Match must not have started and used/cancelled tickets are ineligible.
- Fan can view its own requests.
- Administrator can approve/reject REQUESTED with a required review note.
- Approval rechecks eligibility, cancels tickets/booking, releases seats and
  records APPROVED.
- Rejection records REJECTED and preserves the sale.
- Review writes an audit record. Payment SUCCESS remains historical collection
  data in this mock refund model.

### FR-09 — Accounts and Roles

- Administrator can create/list/view/search/filter/update Fan and Staff accounts.
- Activate/deactivate accounts subject to the Service's safety restrictions.
- Delete an eligible Fan without booking history.
- Staff role menu supports assign/change/revoke, including UNASSIGNED.
- Protect the last ACTIVE Administrator from unsafe role/status removal.
- Admin account updates may change name/email/phone and optionally password.
  Empty update password preserves the existing password.
- New or changed account passwords require at least eight characters. Login
  compares stored credentials, including the existing legacy Staff demo values.
- Self-service profile changes use FR-02.

### FR-10 — Monitoring

- Display counts of Fans, Staff, Stadiums, Matches, Bookings and Tickets.
- Display stored audit records.
- Current audit coverage includes refund review. The project does not claim
  that every CRUD action is audited.

## 5. States and Persistence

- UserStatus — ACTIVE, INACTIVE. SaleStatus — OPEN, CLOSED.
- BookingStatus — PENDING, CONFIRMED, CANCELLED.
- PaymentStatus — PENDING, SUCCESS, FAILED.
- TicketStatus — PENDING, ISSUED, CHECKED_IN, CANCELLED.
- RefundStatus — REQUESTED, APPROVED, REJECTED.
- SeatStatus — AVAILABLE, LOCKED, BOOKED.
- LOCKED is an internal commit transition. Version increments on seat transitions.
- CSV enforces row/schema validation and identity checks in its repositories.
- Individual file replacement uses temporary files/atomic move where supported.
- Multi-file business operations do not provide ACID transactions or full
  crash recovery.

## 6. Nonfunctional Requirements and Limits

- The application must run from its documented Console entry point.
- UTF-8 names and data must remain readable.
- Normal booking prevents duplicate successful seat purchase in one JVM through
  the synchronized core.
- Input/domain/CSV errors return readable messages on the exercised menu paths.
- Role-based menus and ownership checks apply to application entry points.
- Password storage is demo plaintext despite the legacy passwordHash field name.
- Payment/refund is mock. No external gateway or HTTP API is required.
- Performance claims are limited to observed runs. Large CSV listings and
  sales preparation may require extra time.
- The physical seat layout is supplied static data.

## 7. Verification and Traceability

- 82 Use Case nodes map to 88 feature files in the Use Case traceability guide.
- 139 executed Console flows cover ordinary roles and business workflows.
- BookingConcurrencySmoke verifies synchronized checkout and idempotent retry.
- SeedDataSmoke validates the supplied CSV identities and relationships.
- 97 source contracts match 190 expanded boxes and five Mermaid files.
- Transaction CSV stores 11 columns. Earlier 12-column booking records remain
  readable when their extra owner column is empty.
- See [feature guides](Demo_Tung_Feature/README.md),
  [booking protection](04_BOOKING_CONCURRENCY.md) and
  [submission checks](06_USE_CASE_TRACEABILITY.md).
