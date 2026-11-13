# Menu Map

## Main and Shared Capabilities

```text
1. Browse As Guest
2. Login As Fan
3. Login As Staff
4. Register Fan Account
0. Exit System
```

Authenticated accounts can view/update their profile and log out.
Controllers check the stored role before routing to operations.

All authenticated dashboards use 1. View profile, 2. Role operations,
3. Update profile and 0. Logout. Fan additionally has 4. Browse matches.
The role-operations label is Tickets and bookings, Sales counter,
Check in ticket or Manage system operations respectively.

## Guest and Fan Browsing

```text
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details
4. View seat availability / Seat map
0. Back
```

Before selecting a Match, an action asks for its ID once. After selection, the
menu reuses that ID for details, seat availability and Fan Book this match.
Choose another match clears selection. Leaving the screen ends the context.

## Fan

```text
1. View profile
2. Tickets and bookings
   1. Book tickets
      1. View seat map and prices
      2. Create booking
   2. My bookings and payments
      1. Booking history
      2. Booking details
      3. Pay pending booking
   3. My tickets
      1. Ticket history
      2. Ticket details
   4. Cancellations and refunds
      1. Request refund
      2. Cancel unpaid pending booking
      3. My refund requests
3. Update profile
4. Browse matches
0. Logout
```

Create booking holds up to four seats until the persisted payment deadline.
Fan can pay online immediately or later. Seller can accept offline payment by
Booking ID within the same window. EXPIRED remains in history and its holds are released.

## Seller

From the dashboard choose 2. Sales counter.

```text
1. Search Fan
2. Create Fan
3. Ticket sales
   1. Create booking for Fan / walk-in customer
   2. Pay for an existing booking
   0. Back to Seller menu
4. View seat map and prices
5. View customer booking history
0. Back to dashboard
```

- Ticket sales > 1 creates PENDING, displays its ID/deadline and asks for cash receipt.
- Enter at the cash prompt defers payment. RECEIVED pays and automatically issues tickets.
- Ticket sales > 2 asks for Booking ID, then completes the same cash/issue flow.
- A confirmed cash booking can resume ticket issue without another cash prompt.
- The account-holder branch requires an ACTIVE Fan. Walk-ins use Booking ID.
- HELD indicates a reservation. BOOKED indicates a successfully purchased seat.


## Gate Staff

Choose dashboard option 2, then 1. Check in ticket.
Enter Ticket Code, review customer/Match/seat details and confirm with y.
Enter n or a blank line to cancel. Successful check-in records its timestamp and rejects reuse.

## Administrator

From the Administrator dashboard, choose 2. Manage system operations.

```text
1. Venues and seating
   1. Stadiums
      1. Create stadium
      2. List stadiums
      3. Stadium details
      4. Update stadium
      5. Delete stadium
   2. Sections
      1. Create section
      2. List sections by stadium
      3. Section details
      4. Update section
      5. Delete section
   3. Seats
      1. Create seat
      2. List seats by section
      3. Seat details
      4. Update seat
      5. Delete seat
      6. Activate seat
      7. Deactivate seat
2. Matches, pricing and sales
   1. Matches
      1. Create match
      2. List matches
      3. Match details
      4. Update match
      5. Delete match
   2. Ticket pricing
      1. Create price
      2. List prices by match
      3. Price details
      4. Update price
      5. Delete price
   3. Ticket sales
      1. Open ticket sales
      2. Close ticket sales
      3. View sales status
3. Accounts and roles
   1. Fan accounts
      1. Create
      2. List
      3. Details
      4. Search
      5. Filter status
      6. Update
      7. Activate
      8. Deactivate
      9. Delete
   2. Staff accounts
      1. Create
      2. List
      3. Details
      4. Search
      5. Filter role/status
      6. Update
      7. Activate
      8. Deactivate
      9. Staff roles
         1. Assign role
         2. Change role
         3. Revoke role
4. Refund reviews
   1. Review a request
5. Reports and audit logs
   1. System summary
   2. Audit logs
0. Back to dashboard
```

Nested menus have 0. Back. Refund review displays pending requests, then asks
for request ID, a y/n decision and a required note.

Match Management's list/details operations connect to the same Use Case nodes
as the other actors' corresponding operations. Keyword search and Stadium/date
filter prompts are available in Guest/Fan browsing. Seller lists/selects Matches
within its counter workflows. Gate Staff enters Ticket Code during check-in.

The Use Case overview has 75 goals. A shared goal can have several menu entry
paths. Use each feature guide for its exact captured input and Console response.
