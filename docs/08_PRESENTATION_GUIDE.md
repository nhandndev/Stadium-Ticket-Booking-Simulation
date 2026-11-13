# Project Presentation Guide

## The Group's Problem Statement

- Our group develops a Java Console stadium ticket booking system with CSV persistence.
- Administrator prepares Stadiums, Sections, Seats, Matches, prices and sales status.
- Guest searches Matches, reads information and registers an account.
- Fan selects up to four seats and creates a booking.
- Seller serves counter customers, accepts offline payment and issues tickets.
- Gate Staff validates tickets and checks them in. Administrator reviews eligible refunds.

## Explaining Shared Match Access

- Guest and Fan list/search/filter Matches and view details and seat availability.
- Seller lists/selects Matches and views details, seat maps and prices inside its counter workflows.
- Administrator reaches the shared View Match List and View Match Details through Match Management.
- The current Admin menu has no team-keyword search or Stadium/date-filter entry.
- Seller calls filterMatches(null, null) to load the selection list. Its menu has no search/filter criteria form.
- Gate Staff enters Ticket Code, reviews customer/Match/seat details and confirms check-in.
- All authenticated actors inherit profile, profile update and logout through User.

## Explaining Shared Use Cases

- A shared business goal has one ellipse with links from every participating actor.
- Guest, Fan, Seller and Administrator share View Match List and View Match Details.
- Seller and Administrator share Search Fan and Create Fan.
- Guest, Fan and Seller share View seat map and prices.
- Fan ticket listing is represented once as View Ticket History.
- Seller shares Select seats and View Booking Details through the counter workflows.
- Owner booking history and Seller customer booking history remain separate because their ownership and selection rules differ.
- Self-registration and staff-assisted account creation remain separate because their initiating actors and account-management rules differ.

There are 75 goals on the current overview. The traceability document records
actors, Console entry paths, implementation calls and demo links for every goal.
Separate successful/rejected demo scenarios can belong to the same goal.

## Explaining the Booking Workflow

- Creating PENDING stores the total, createdAt and expiresAt.
- Each held Seat has LOCKED status and holdBookingId identifying its owning booking.
- The maximum window is 30 minutes, ending earlier if the Match starts sooner.
- Fan can pay online immediately or later, or give the Booking ID to Seller for
  offline payment within the same deadline.
- At expiry, the booking becomes EXPIRED, payment is rejected and owned holds are released.
- Cancelling an unpaid booking or failing payment also releases that booking's holds.
- SYNCHRONIZED protects validation and state changes within one JVM.

## Seller Counter Workflow

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

Explain the distinction between menu navigation and class responsibilities.
Cash payment and ticket issue are sequential steps in the user's sales workflow.
The code still uses separate acceptOfflinePayment and issueTickets methods.
SellerView adds showTicketSalesMenu(): int. The Controller's new menu helper is private.

INACTIVE accounts are rejected before Match selection. Seat IDs use numbers from
the SEAT ID column. Error messages identify the invalid ID and reason.

## Explaining Classes and Relationships

- Seat is the physical layout. MatchSeat is its inventory for a particular Match.
- Booking history represents orders, totals and status. Ticket history represents
  individual issued tickets and their use state.
- Booking-to-Fan multiplicity is 0..1 because walk-in bookings leave fanId empty.
- MatchSeat.holdBookingId refers to the booking currently holding that seat.
- Controller coordinates View and Service. Service uses Repository for CSV access.
- Request/Response DTOs carry data between layers.
- Class arrows express structural relationships. Explain runtime operation separately.

## Diagram Reading Order

- Page 00 introduces actors and features. The [full traceability review](06_USE_CASE_TRACEABILITY.md)
  links each Use Case to code and a demo.
- Page 01 introduces Main, authentication, browsing and booking-expiry cleanup.
- Page 02 covers administration, including elapsed-hold cleanup before Seat deactivation.
- Page 03 covers booking, payment, tickets, refunds, deadlines and hold ownership.
- Page 04 explains CSV repositories, old/new data layouts and shared entities.
- Page 05 explains model relationships and multiplicities.

## Demonstration

Follow the [setup guide](Feature_Demos/00_SETUP.md) and
[execution order](Feature_Demos/01_DEMO_ORDER.md). Use the copied dataset
specified by each flow so Console IDs and state match the captures.
