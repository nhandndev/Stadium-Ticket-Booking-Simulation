# Software Requirements Specification

Baseline review date — 2026-11-14. Java 17 Console stadium ticket booking.

## 1. Product and Architecture

The group implements venue preparation, online and counter booking, payment,
ticket issue, Gate check-in and refund review with CSV persistence.
Main constructs dependencies. Controllers coordinate Views and Services.
Services apply business rules. Repositories parse and persist CSV. Models keep
state and Request/Response DTOs carry boundary data. Enums use enums packages.

## 2. Actors

- Guest browses, registers and opens login.
- User represents shared authenticated profile/update/logout behavior.
- Fan books and manages its own orders, tickets and refund requests.
- Seller provides the counter workflows below.
- Gate Staff validates tickets and records check-in.
- Administrator manages venues, Matches, pricing, sales, accounts/roles,
  refund reviews, summary and audit records.

MockPaymentService supplies the internal mock online payment result within Make online payment.

Page 00 contains 75 unique Use Case goals. A goal shared by multiple actors has
one node. User supplies inherited profile/update/logout behavior to all four
authenticated roles. Supporting selection/detail steps remain part of the
corresponding workflows.

## 3. Functional Requirements

### FR-01 — Match Browsing

- Search by team keyword and filter by Stadium/date, with empty filters supported.
- List schedules, view Match details and view prepared seat availability/prices.
- Selecting a Match preserves its ID for subsequent details/map and Fan booking.
- Book this match reuses the selected ID. General booking asks for an ID once.
- Choose another match clears selection and leaving the screen ends that context.
- Guest and Fan access keyword search and Stadium/date filtering through browsing.
- Seller lists/selects Matches and views details/availability/prices in the counter flows.
- Administrator accesses Match list/details through Match Management. The current
  Admin menu provides no keyword-search or Stadium/date-filter entry.
- Gate Staff enters Ticket Code only. Match and seat are resolved from that ticket.

### FR-02 — Accounts and Authentication

- Register Fan with CSV-safe name, email, password and phone.
- New/changed passwords require at least eight characters.
- Validate email and uniqueness across Fan and Staff repositories.
- Separate Fan/Staff login routes validate credentials, role and ACTIVE status.
- UNASSIGNED Staff receive a role through administration before login.
- View current profile, update name/phone and logout.
- Enforce role and ownership at application entry points.

### FR-03 — Venues and Seats

- Administrator creates, lists/views, updates and safely deletes Stadiums/Sections.
- Sections belong to existing Stadiums.
- Create/list/view/update/delete Seats and activate/deactivate them.
- Seat positions are unique within a Section.
- Inventory/history restrictions protect location changes and deletion.
- Active holds and BOOKED inventory block unsafe deactivation. Expired holds
  are cleared before administrative deactivation checks.

### FR-04 — Matches, Pricing and Sales

- Create/list/view/update/delete Matches with Stadium, teams and kick-off time.
- Protect changes/deletion according to inventory, price and booking history.
- Create/list/view/update/delete positive prices for a Match/Section pair.
- The Section must belong to the Match's Stadium.
- Open sales for eligible future Matches with active seats and required prices.
- Publish missing MatchSeat inventory, close sales and view sales status.

### FR-05 — Booking and Thirty-Minute Holds

- Select one to four distinct positive Seat IDs for one future OPEN Match.
- Validate active physical seats, correct Stadium, published availability and prices.
- Fan self-service requires the active account owner.
- Counter booking supports the existing ACTIVE Fan or walk-in branch.
- Store PENDING with the quoted total, createdAt and expiresAt.
- Set expiresAt to the earlier of createdAt plus 30 minutes and kick-off.
- Atomically hold all selected seats with LOCKED and holdBookingId.
- Another booking cannot acquire held seats. Partial seat groups are rejected.
- Persist the owner and timestamps across application restarts.
- Show the payment deadline. Allow immediate online payment, later online payment
  or Seller offline payment for that booking within the same window.
- At or after expiresAt, reject payment, set EXPIRED and release only owned holds.
- Expiry is evaluated at startup and relevant application operations.
- Cancel an unpaid pending booking and release its holds.
- Failed payment cancels the attempted booking and releases remaining owned holds.
- Booking history/details include PENDING, CONFIRMED, CANCELLED and EXPIRED.

### FR-06 — Seller Features

The Seller counter menu has five groups.

1. Search Fan.
2. Create Fan.
3. Ticket sales.
   1. Create booking for Fan / walk-in customer.
   2. Pay for an existing booking.
   0. Back to Seller menu.
4. View seat map and prices.
5. View customer booking history.
0. Back to dashboard.

Creating a booking continues to the cash-receipt prompt. Confirming RECEIVED
records offline payment and immediately issues/displays tickets. Cancelling the
cash prompt keeps PENDING and displays the Booking ID and persisted deadline.
Ticket sales > Pay for an existing booking accepts that ID, including an order
created by a Fan online, and issues tickets after successful cash payment.
An already-CONFIRMED cash booking can resume ticket issuance through the same
entry without collecting cash again. The existing payment and issue methods remain separate code operations.

The existing-account branch rejects INACTIVE before Match selection. Walk-ins
use an empty fanId and keep Booking ID/ticket codes. Existing-booking cash checkout
rejects expired or cancelled orders. A previous successful online payment is
rejected as a different payment method when accessed through the cash route.

### FR-07 — Payment and Ticket Issue

- Online payment uses the quoted booking amount and the local mock service.
- Offline payment records cash received by Seller for a booking created by Fan
  or Seller before expiry.
- Recheck the active account when present, OPEN future Match and usable physical seats.
- Commit only seats held by that booking using SYNCHRONIZED.
- Successful payment confirms the booking. Online checkout issues tickets.
- Successful Seller cash checkout automatically issues/displays tickets in the same flow.
- Idempotent completed-payment and issue retries preserve stored results.
- Failed payment/conflict records the outcome and releases owned holds.
- Fan can list ticket history and view ticket details.
- Seller views Booking details before collecting cash or issuing tickets.
- Booking Details lists all tickets for that booking with code and status, including used/cancelled tickets.
- Ticket codes identify individual seats in the booking.
- Booking history covers orders/totals and ticket history covers issued seats/use.

### FR-08 — Gate Validation and Check-in

- Gate Staff validates code, Match and confirmed booking membership.
- Confirmation records CHECKED_IN and timestamp once.
- Resolve Match and seat by Ticket Code. Reject missing, cancelled and already-used tickets.
- Preview customer name/phone and Match/seat before confirmation. Walk-in values are `None`.
- Issued walk-in tickets follow the same Gate rules.
- The current implementation checks ticket/Match/booking validity without an entry-time window.

### FR-09 — Cancellation and Refund

- Unpaid pending cancellation releases holds.
- Eligible paid CONFIRMED bookings use the authenticated Fan refund request.
- Require a reason and prevent duplicate active requests.
- Require pre-kick-off eligibility and reject existing CHECKED_IN/CANCELLED tickets.
- A paid booking can request refund before Seller has issued tickets.
- Fan lists its own requests. Administrator reviews REQUESTED with a note.
- Approval rechecks eligibility, cancels booking/tickets, releases BOOKED seats
  and records APPROVED. Rejection records REJECTED and preserves the sale.
- Review appends an audit record. Mock successful payment remains historical collection data.

### FR-10 — Administrative Accounts, Roles and Monitoring

- Fan account create/list/detail/search/filter/update/activate/deactivate and
  eligible deletion when booking history permits.
- Staff account create/list/detail/search/filter/update/activate/deactivate.
- View available Staff roles and assign/change/revoke role.
- Protect the last ACTIVE Administrator from unsafe role/status removal.
- Account administration may change name/email/phone and optionally password.
- View system summary and stored audit logs. Current audit coverage includes refund review.
- Display the Staff account name and role for each audit row. If the Staff account is missing, display its ID as Unknown staff and role UNKNOWN.

## 4. State and Persistence

### Supplied Runtime Fixture

- Two Stadiums contain 100 physical Seats each across five Sections.
- Rows A–J each contain ten seats. A/B are VIP, C/D Premium, and E–J Standard.
- The 100 Matches are divided equally between the Stadiums.
- Every fixture Match has 100 MatchSeat rows and five Section prices.
- As of 2026-11-14, 23 Matches are past/CLOSED, 52 future Matches are OPEN and
  25 future Matches are CLOSED.
- The fixture retains existing business history and its referenced physical Seat IDs.
- These are initial sample counts. Existing CRUD operations can add further data.

### Stored States and Formats

- BookingStatus uses PENDING, CONFIRMED, CANCELLED, EXPIRED.
- SeatStatus uses AVAILABLE, LOCKED, BOOKED. Seat map displays LOCKED as HELD.
- PaymentStatus uses PENDING, SUCCESS, FAILED.
- TicketStatus uses PENDING, ISSUED, CHECKED_IN, CANCELLED.
- RefundStatus uses REQUESTED, APPROVED, REJECTED.
- UserStatus uses ACTIVE/INACTIVE and SaleStatus uses OPEN/CLOSED.
- Booking CSV writes eight columns with createdAt/expiresAt and reads legacy six.
- Legacy PENDING without timestamps is expired on access, avoiding a renewed window
  on every read. Historical paid CONFIRMED rows retain their state.
- MatchSeat CSV writes six columns with holdBookingId and reads legacy five.
- Walk-in booking/transaction fanId is empty. Transaction bookingId remains required.
- Quoted amounts remain attached to the booking during its payment window.

## 5. Quality and Operating Limits

- Plain Java 17, UTF-8 Console and CSV. Attributes are private, APIs public.
- SYNCHRONIZED coordinates cooperating threads within one JVM.
- Validate before charging and preserve owner-safe hold release.
- Input errors identify the field/Seat ID and actual reason.
- Payment/refund are mocked and demo passwords are plaintext.
- Atomic per-file replacement still permits interrupted multi-file state requiring reconciliation.
- Source has 97 class/enum contracts, six draw.io pages and five Mermaid files.

## 6. Traceability and Verification

[Use Case traceability](06_USE_CASE_TRACEABILITY.md) maps every visible node to
implemented entry points and feature guides. The guide set contains 87 feature
files and 148 current Console flows. [Verification](05_VERIFICATION_AND_DELIVERY.md)
records the hold/deadline, concurrency, persistence and role checks.
