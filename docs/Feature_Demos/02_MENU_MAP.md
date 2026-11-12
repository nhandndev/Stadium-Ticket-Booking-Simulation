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

From the dashboard, choose 2. Sales counter to open the seven features.

```text
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
```

- Option 3 selects an ACTIVE account or walk-in, followed by Match/Seat IDs, to create PENDING.
- Option 4 records offline cash receipt for a booking within its deadline.
- Option 5 issues tickets for a CONFIRMED booking with successful payment.
- Options 4 and 5 identify the order by Booking ID.
- HELD indicates a reserved seat. BOOKED indicates a successfully purchased seat.

## Gate Staff

Choose dashboard option 2, then Validate ticket or Check in ticket.
Enter the ticket code and Match ID. Check-in requires confirmation, records its
timestamp and rejects reuse.

## Administrator

```text
1. Venues and seating
   1. Stadiums
   2. Sections
   3. Seats
2. Matches, pricing and sales
   1. Matches
   2. Ticket pricing
   3. Ticket sales
3. Accounts and roles
   1. Fans
   2. Staff
   3. Staff roles
4. Refund reviews
5. Reports and audit logs
   1. System summary
   2. Audit logs
0. Back to dashboard
```

Submenus contain the CRUD and status-management operations defined in the SRS.
Use each feature guide for the exact input sequence and Console response.
