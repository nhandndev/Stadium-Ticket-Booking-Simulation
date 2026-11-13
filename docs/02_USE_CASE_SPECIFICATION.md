# Use Case Specification

The visible page 00 of ClassDiagram.drawio is the official actor/feature overview.

Reviewed on 2026-11-14 against the current Console menus, Controller routing and
Service rules. The overview contains 75 distinct goals. This specification and
the traceability document describe the same goals and their reachable actor paths.

## Actors and Shared Behavior

- Guest lists/searches/filters Matches, reads details/seat availability, registers and opens login.
- User represents shared profile/update/logout behavior after authentication.
- Fan books and accesses its own bookings, tickets and refund requests.
- Seller serves the counter through the sales workflows below.
- Gate Staff validates and checks in tickets once.
- Administrator manages venues, Matches, prices, sales, accounts/roles,
  refunds, summary and audit.

MockPaymentService supplies the internal mock online payment outcome within Make online payment.

Actor generalization expresses shared capability. Controllers also enforce stored
role and account status. Menu navigation and validation are part of these use-case flows.

## Match Access by Actor

| Actor | Reachable Match operations | Console entry |
| --- | --- | --- |
| Guest | List schedules, search by team keyword, filter by Stadium/date, view Match details and seat availability/prices | Main menu > Browse As Guest |
| Fan | The same browsing operations, plus booking a selected Match | Fan dashboard > Browse matches, or Tickets and bookings |
| Seller | List/select Matches and view Match details, seat availability and prices as part of counter booking or View seat map and prices | Sales counter > Create booking for Fan, or View seat map and prices |
| Administrator | List all Matches, view details, create/update/delete Matches, manage prices and sales status | Manage system operations > Matches, pricing and sales |
| Gate Staff | Enter Ticket Code, inspect the resolved customer/Match/seat and confirm | Check-in ticket |

Administrator connects to the same View Match List and View Match Details nodes
as the other participating actors. Its menu reaches these goals through Match
Management. The current authenticated Admin menu has no
team-keyword search, Stadium/date filter or per-Match availability-map entry.
Seller's menu has no keyword search or Stadium/date filter. Its calls to
filterMatches(null, null) load the selection list without asking for criteria.
Gate ticket-code entry resolves the Match and seat inside check-in.

All four authenticated roles inherit View profile, Update Profile Information
and Logout through User. Login is entered before role-specific navigation.
Displayed Staff role choices belong to account creation/filtering and role
assignment/change. Pending refund lists belong to Review Refund Request.
These existing flows cover their supporting selection and display steps.

## One Node per Use Case

Each distinct goal has one ellipse on page 00. All participating actors connect
to that same node, even when their Console screens or Controller entry points differ.

- View Match List is shared by Guest, Fan, Seller and Administrator.
- View Match Details is shared by Guest, Fan, Seller and Administrator.
- Search Fan is shared by Seller and Administrator.
- View seat map and prices covers availability and pricing for Guest, Fan and Seller.
- View Ticket History covers the Fan ticket list previously labeled View tickets.
- Create Fan is shared by Seller and Administrator.
- Select seats is shared by Fan and Seller within their booking flows.
- View Booking Details is shared by Fan and Seller. Seller displays the selected
  order before completing payment/ticket issue for an existing booking.
- View profile, Update Profile Information and Logout belong to User and are inherited.

The following separate goals have different actors, ownership or outcomes.

- Register is account self-registration by Guest. Create Fan is staff-assisted account creation, including administrative management rules.
- Create booking is Fan self-service. Create booking for Fan is a Seller operation selecting a customer or the walk-in branch.
- View booking history is owner-scoped Fan access. View customer booking history lets Seller select a customer and inspect that customer's orders.
- View Ticket History lists the Fan's tickets. View ticket details inspects one selected ticket.
- View Match List lists Matches. Search Matches uses a keyword and Filter Matches applies Stadium/date criteria.
- View seat map and prices displays per-seat inventory for purchase. View Ticket Prices by Match and View Ticket Price Details support administrative price management.
- Make online payment is Fan online checkout. Pay for an existing booking is Seller cash checkout by Booking ID. Counter creation can also continue directly into cash checkout.
- Check-in ticket validates usability, shows ticket/customer/Match/seat details and asks for confirmation before recording entry.
- Booking history concerns orders. Ticket history concerns issued seats and entry state.
- Select seats is a selection subgoal within Create booking and remains explicitly visible.

## Seller Use Cases

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

The six Seller business goals are Search Fan, Create Fan, Create booking for Fan,
Pay for an existing booking, View seat map and prices, and View customer booking
history. Cash receipt and ticket issue are steps within the two sales workflows.
Their methods remain in the Class Diagram.

## Common Execution Rules

- Actor associations follow reachable Console workflows, including displayed
  selection/detail steps inside a larger operation.
- Fan, Seller, Gate Staff and Administrator inherit the three User goals.
- Choosing Login As Staff uses the account's stored role to select the dashboard.
- Authentication checks credentials, ACTIVE status and an assigned Staff role.
- Each dashboard and privileged operation rechecks the relevant session/role.
- IDs must refer to eligible records. An invalid ID or value produces a message.
- Enter or 0 cancels only where the displayed prompt offers that choice.
- Read operations return a list or details. Empty lists receive an empty-result message.
- Write operations validate before saving. Delete and selected state-changing
  Console operations ask for confirmation.
- A private helper or public method used internally can support a visible goal.
  The menu entry and resulting user action determine the actor association.
- Back, Exit, retries, expiry cleanup and field validation are documented workflow
  behavior. The 87 feature guides contain multiple successful and rejected flows
  for the 75 goals.

## Authentication and Profile Flows

### Register and Create Fan

- Guest enters name, email, password and phone through Register Fan Account.
- Seller uses Create Fan. Administrator uses Accounts and roles > Fan accounts > Create.
- The system validates required fields, CSV-safe values, password length and email uniqueness.
- Success stores an ACTIVE Fan account and displays its details.
- Guest registration returns to the main menu with an instruction to log in.
- Account creation is optional for a walk-in sale. The counter booking branch
  can retain an empty fanId and identify the order by Booking ID.

### Login, Profile and Logout

- Login As Fan / Login As Staff reads credentials and calls the shared AuthController.
- Wrong credentials, INACTIVE status or UNASSIGNED Staff role are rejected.
- A valid account used on the other login route is logged out and shown the appropriate route.
- Success displays the profile and opens the dashboard for the stored role.
- View profile reads the current authenticated account. Update Profile Information
  collects a new name/phone and saves validated values.
- Logout clears currentUser and returns to the main menu.
- Controllers that enforce authenticated access use the shared AuthController instance wired by Main.

## Browsing and Match Selection Flows

- View Match List loads the schedule for Guest/Fan browsing, Seller selection,
  or Administrator management. The single node has four actor associations.
- Search Matches takes a required team keyword and matches either team's name.
- Filter Matches accepts optional Stadium ID and an exact calendar date. Empty
  filters list schedules. These criteria are available in Guest/Fan browsing.
- View Match Details takes a Match ID and displays teams, Stadium, kick-off and sales status.
- View seat map and prices takes a Match ID and displays published per-Match
  inventory, physical-seat labels, availability and Section prices.
- A closed or past Match can appear in browsing results. Booking independently
  requires an eligible future OPEN Match.
- Browse actions retain selectedMatchId for subsequent details/map/Book this match.
- Seller selects a Match within Create booking for Fan or View seat map and prices.
- Administrator uses Match list/details inside its management menu. Physical-seat
  administration is available separately under Venues and seating.

## Booking and Payment Scenarios

### Fan

1. Select a future OPEN Match and one to four available seats.
2. Validate the selection and create PENDING with the quoted amount and persisted payment deadline.
3. The system holds those seats exclusively for the booking.
4. Pay online immediately, return to Pay pending booking later or give the
   Booking ID to Seller for offline payment before the same deadline.
5. Successful payment confirms the order. Online checkout issues tickets.
6. View booking/ticket history and present the ticket at the Gate.

### Seller

1. Search/select an ACTIVE Fan or choose the walk-in branch when creating booking.
2. Select Match and seats, then create PENDING with its deadline.
3. Continue to the cash-receipt prompt for the newly created booking.
4. Confirm RECEIVED to record payment and automatically issue/display tickets.
5. To defer payment, cancel the cash prompt and keep the Booking ID/deadline.
6. Ticket sales > Pay for an existing booking resumes by ID, collects cash and
   automatically issues tickets. An existing successful cash payment can resume
   issuance through this path without collecting another payment.

### Selection, Details and Payment Alternatives

- Select seats accepts one to four positive, distinct numeric Seat IDs. The
  seat-map SEAT ID column provides those identifiers.
- Reject malformed, duplicate, missing, inactive, wrong-Stadium, unpublished,
  held or already-booked selections with the applicable reason.
- Fan's own Booking History includes PENDING, CONFIRMED, CANCELLED and EXPIRED orders.
- View Booking Details displays the selected order and persisted deadline.
  Fan access is owner-scoped. Seller uses Booking ID in Pay for an existing booking.
- Seller View customer booking history searches/selects an ACTIVE Fan and lists
  that customer's orders. Walk-in orders use Booking ID through payment/issue.
- Online Pay Now and Pay pending booking are entry paths to Make online payment.
- The View supplies the quoted booking amount. Checkout checks the stored amount,
  ownership where relevant, active account, Match eligibility and held-seat owner.
- Mock online failure records FAILED, cancels the booking and releases owned holds.
- Completed payment retries preserve the existing valid result. A method change
  or inconsistent partial checkout can be rejected.
- Seller confirms cash receipt by typing RECEIVED. Cancelling that prompt preserves
  the unpaid booking until its deadline.
- Automatic ticket issue creates missing per-seat tickets for a CONFIRMED paid
  booking and returns existing valid tickets on a repeated operation. A confirmed
  cash booking can resume this step through Pay for an existing booking.

### Expiry and Cancellation

The deadline is the earlier of creation plus 30 minutes and kick-off. Payment
at or after that timestamp is rejected. The next relevant expiry check changes
PENDING to EXPIRED and releases its owned holds.
Cancellation or failed payment also releases the owning booking's holds.
Stored timestamps prevent a restart from granting another window.

### History, Gate and Refunds

Booking Details also lists its tickets with Ticket ID, Booking ID, Match, seat, code and status.
Booking history represents orders and status. Ticket history represents issued
seats and check-in. Gate validates Match and ticket identity before one-time entry.
Eligible paid Fan bookings may request refund and Administrator reviews them.
Approval cancels the sale/tickets and releases BOOKED inventory.

### Ticket and Gate Flows

- View Ticket History lists the authenticated Fan's tickets. View ticket details
  selects one owned ticket and displays its code, Match, seat and use state.
- Gate Staff selects Check in ticket and enters Ticket Code only.
- The code resolves its booking, Match and seat. Validation checks an ISSUED ticket,
  an existing Match and a CONFIRMED booking containing that Match/seat.
- The preview shows customer name and phone, ticket code, Match, seat and status.
  Walk-in name and phone are both `None`.
- Staff confirms with `y` or cancels with `n` or Enter. Cancelling leaves the ticket unchanged.
- After confirmation, the Service re-reads and revalidates under the ticket lock,
  then stores CHECKED_IN and checkedInAt. Missing, cancelled and used tickets are rejected.
- The current Gate implementation has no entry-time window.

### Refund Request and Review Flows

- Fan requests a refund for an owned CONFIRMED booking with successful payment,
  before kick-off, and supplies a reason.
- Existing CHECKED_IN/CANCELLED tickets block eligibility. A paid counter booking
  can be eligible before tickets have been issued.
- Another REQUESTED or APPROVED request for that booking is rejected. A rejected
  request permits a fresh request while eligibility still holds.
- View my refund requests lists only the current Fan's requests.
- Administrator opens Refund reviews, sees pending requests and enters request ID,
  approve/reject decision and a required note.
- Approval rechecks eligibility and BOOKED inventory, cancels existing tickets and
  booking, releases seats and records APPROVED. Rejection records REJECTED.
- Both review outcomes append an audit record through AdminController.
- Payment SUCCESS remains historical payment evidence. Refund review uses local
  simulated business state without an external bank-transfer operation.

## Administrator Venue Flows

### Stadiums

- Create Stadium collects name/address and stores the Stadium.
- View Stadium List and View Stadium Details display existing records.
- Update Stadium validates and saves the new name/address.
- Delete Stadium requires confirmation and rejects a Stadium referenced by a
  Section or Match.

### Sections

- Create Section chooses an existing Stadium and supplies a name.
- View Sections by Stadium lists that Stadium's Sections. View Section Details
  reads the selected Section.
- Update Section changes the validated name.
- Delete Section requires confirmation and rejects Sections with Seats or prices.

### Seats

- Create Seat chooses a Section and supplies row label and positive seat number.
- A physical position must be unique within its Section.
- View Seats by Section and View Seat Details display physical layout records.
- Update Seat changes its row/number. Update/delete reject Seats already referenced
  by Match inventory.
- Activate Seat enables the physical seat. Deactivate Seat first processes elapsed
  holds and rejects a seat with remaining LOCKED/BOOKED Match inventory.
- View Seats by Section concerns physical layout. View seat map and prices uses
  a Match ID and its published inventory.

## Administrator Match, Pricing and Sales Flows

- Create Match requires an existing Stadium, distinct teams and future kick-off.
  The new Match starts CLOSED.
- View Match List and View Match Details use the shared nodes on page 00.
- Update Match requires a CLOSED Match with no booking history, inventory or prices.
- Delete Match requires confirmation, CLOSED sales and no booking history.
  It removes that Match's inventory and prices before deleting the Match.
- Create Ticket Price for a Match and Section requires a positive price and a
  Section belonging to the Match's Stadium.
- View Ticket Prices by Match lists Section prices. View Ticket Price Details
  takes Match ID and Section ID.
- Create/update/delete price operations require CLOSED sales.
- Open Ticket Sales requires a future Match, active physical seats and prices for
  their Sections. It publishes missing MatchSeat inventory and sets OPEN.
- Close Ticket Sales sets CLOSED. Checkout rechecks sales status, so pending
  customers can be blocked from payment after closure.
- View Ticket Sales Status reads OPEN/CLOSED for the selected Match.

## Administrator Account, Role and Monitoring Flows

- Fan management provides Create Fan, View Fans, View Fan Details, Search Fan,
  Filter Fans by status, Update Fan Information, activation/deactivation and deletion.
- Staff management provides creation, list/details, keyword search, role/status
  filtering, update and activation/deactivation.
- Account update accepts name/email/phone and an optional new password. Blank
  password preserves the stored value. Email remains unique across account files.
- Fan deletion is allowed when booking history permits. Otherwise the UI receives
  a rejection and the account can be deactivated.
- Staff roles are reached through Staff accounts > 9. Staff roles. Assign Staff Role
  requires an UNASSIGNED account. Change Staff Role selects an assigned role.
- Revoke Staff Role sets UNASSIGNED. Such a Staff account cannot log in until assigned.
- Role/status changes protect the last ACTIVE Administrator.
- Available role choices are displayed during role selection. They are supporting
  steps of creation, filtering, assignment and change.
- View System Summary reports counts of Fans, Staff, Stadiums, Matches, Bookings and Tickets.
- View Audit Log displays up to the 30 most recent records. Current audit creation
  covers refund review.

## User-Facing Boundaries

- The application is one Java Console session with CSV persistence.
- Synchronization coordinates cooperating threads within one JVM.
- Expiry is checked on operations and uses persisted timestamps.
- Online payment and refund behavior use local mocks. Demo passwords are plaintext.
- CSV changes replace individual files. Interrupted multi-file changes can require reconciliation.
- Use Case actor links describe the current Console entry paths. Internal methods
  can allow broader reuse without introducing a new menu action.

## Full Feature Coverage

[Use Case traceability](06_USE_CASE_TRACEABILITY.md) maps every visible feature
node to code and a captured feature guide. The [feature index](Feature_Demos/README.md)
groups all delivered operations by actor/domain. Hold expiry is a business rule of
booking, payment and seat availability, and is explained in the diagram note.
