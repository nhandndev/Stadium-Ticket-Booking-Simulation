# Create Booking

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- Code path `MainController → BookingController.createBooking → BookingService.createBooking → BookingRepository`. Pay Now continues through `makeOnlinePayment`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-069"></a>

## Flow 069 — Create Booking and defer payment

- Start at Main in CORE_DIR after [Flow 068](../05_Fan_Booking_Tickets/01_View_Seat_Map_and_Prices.md#flow-068).

### Initial Console

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Complete input sequence

```text
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 1
6. 2
7. 4
8. 21
9. n
10. 0
11. 0
12. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Step 8 — Enter `21` and press Enter

Console output

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING
Seats held until: 2026-11-13T00:17:29.188470
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== MOCK ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Step 9 — Enter `n` and press Enter

Console output

```text
Not paid. Pay before 2026-11-13T00:17:29.188470 online or give the Booking ID to a Seller for offline payment.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 12 — Enter `0` and press Enter

Console output

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-073"></a>

## Flow 073 — Create a two-seat Booking for later payment

- Start at Main in CORE_DIR after [Flow 072](../07_Cancellation_Refund/01_Cancel_Unpaid_Booking.md#flow-072).

### Initial Console

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Complete input sequence

```text
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 1
6. 2
7. 4
8. 21,22
9. n
10. 0
11. 0
12. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Step 8 — Enter `21,22` and press Enter

Console output

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: PENDING
Seats held until: 2026-11-13T00:17:30.823218
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== MOCK ONLINE PAYMENT ===
Amount due: 500000 VND
Pay now? (y/n, Enter = No):
```

### Step 9 — Enter `n` and press Enter

Console output

```text
Not paid. Pay before 2026-11-13T00:17:30.823218 online or give the Booking ID to a Seller for offline payment.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 12 — Enter `0` and press Enter

Console output

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-078"></a>

## Flow 078 — Create Booking and pay immediately

- Start at Main in CORE_DIR after [Flow 077](../05_Fan_Booking_Tickets/08_Book_This_Match_from_Browsing.md#flow-077).

### Initial Console

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Complete input sequence

```text
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 1
6. 2
7. 4
8. 25
9. y
10. 0
11. 0
12. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Step 8 — Enter `25` and press Enter

Console output

```text
=== BOOKING #5 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A5] | IDs: [25]
Total: 250000 VND | Status: PENDING
Seats held until: 2026-11-13T00:17:32.992088
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== MOCK ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Step 9 — Enter `y` and press Enter

Console output

```text
Payment #4 | Booking #5 | Amount 250000 VND | Method ONLINE | Status SUCCESS
Ticket #4 | Booking #5 | Flow Red vs Flow Blue | Flow Stand Updated A5 | Code T-5-25 | Status ISSUED

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 12 — Enter `0` and press Enter

Console output

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-079"></a>

## Flow 079 — Create and pay a second Booking immediately

- Start at Main in CORE_DIR after [Flow 078](../05_Fan_Booking_Tickets/02_Create_Booking.md#flow-078).

### Initial Console

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Complete input sequence

```text
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 1
6. 2
7. 4
8. 26
9. y
10. 0
11. 0
12. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | BOOKED
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Step 8 — Enter `26` and press Enter

Console output

```text
=== BOOKING #6 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A6] | IDs: [26]
Total: 250000 VND | Status: PENDING
Seats held until: 2026-11-13T00:17:33.436845
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== MOCK ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Step 9 — Enter `y` and press Enter

Console output

```text
Payment #5 | Booking #6 | Amount 250000 VND | Method ONLINE | Status SUCCESS
Ticket #5 | Booking #6 | Flow Red vs Flow Blue | Flow Stand Updated A6 | Code T-6-26 | Status ISSUED

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 12 — Enter `0` and press Enter

Console output

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-140"></a>

## Flow 140 — Reject more than four selected seats

- Start at Main in CORE_DIR after [Flow 139](../11_System/01_Invalid_Menu_Input.md#flow-139).

### Initial Console

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Complete input sequence

```text
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 1
6. 2
7. 4
8. 21,22,23,24,25
9. 0
10. 0
11. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | BOOKED
24       | A4       | Flow Stand Updated       | 250000       | BOOKED
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Step 8 — Enter `21,22,23,24,25` and press Enter

Console output

```text
Choose 1 to 4 seats

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Step 9 — Enter `0` and press Enter

Console output

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Select one to four seats and create PENDING. The quote and owned seat holds last until expiresAt, up to 30 minutes or kick-off.
- The flow ends at Main. Keep CORE_DIR for the next flow.
