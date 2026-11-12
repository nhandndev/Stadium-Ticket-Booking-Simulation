# Project Presentation Guide

## The Group's Problem Statement

- Our group develops a Java Console stadium ticket booking system with CSV persistence.
- Administrator prepares Stadiums, Sections, Seats, Matches, prices and sales status.
- Guest searches Matches, reads information and registers an account.
- Fan selects up to four seats and creates a booking.
- Seller serves counter customers, accepts offline payment and issues tickets.
- Gate Staff validates tickets and checks them in. Administrator reviews eligible refunds.

## Explaining the Booking Workflow

- Creating PENDING stores the total, createdAt and expiresAt.
- Each held Seat has LOCKED status and holdBookingId identifying its owning booking.
- The maximum window is 30 minutes, ending earlier if the Match starts sooner.
- Fan can pay online immediately or later, or give the Booking ID to Seller for
  offline payment within the same deadline.
- At expiry, the booking becomes EXPIRED, payment is rejected and owned holds are released.
- Cancelling an unpaid booking or failing payment also releases that booking's holds.
- SYNCHRONIZED protects validation and state changes within one JVM.

## The Seven Seller Features

1. Search Fan finds accounts by name/email or lists them.
2. Create Fan registers an account when the customer wants one.
3. Create booking for Fan selects an ACTIVE customer, Match and seats. The walk-in
   branch buys directly at the counter and keeps booking/ticket codes.
4. Accept offline payment collects cash for an unexpired booking. For example,
   Fan chooses later payment and brings the Booking ID to Seller.
5. Issue ticket issues tickets after the booking is CONFIRMED and payment succeeds.
6. View seat map and prices shows Seat IDs, prices and availability.
7. View customer booking history lists the selected Fan account's orders.

Booking creation, offline payment and ticket issue are separate operations.
INACTIVE accounts are rejected before Match selection. Seat IDs 1,2,3,4 select
four numeric IDs from the SEAT ID column. Errors identify the failed Seat ID and reason.

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
