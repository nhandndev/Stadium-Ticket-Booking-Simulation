# Book This Match from Browsing

- Continue from the selected Match to seat selection and demonstrate cancelling seat input.
- Code path `MainController.handleMatchListActions → BookingController.bookMatch → BookingView`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-077"></a>

## Flow 077 — Book the selected Match from browsing and cancel seat selection

- Start at Main in CORE_DIR after [Flow 076](../05_Fan_Booking_Tickets/07_Ticket_Details.md#flow-076).

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
4. 4
5. 1
6. Flow Red
7. 1
8. 4
9. 2
10. 3
11. 0
12. 4
13. 3
14. 4
15. 0
16. 0
17. 0
18. 0
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

### Step 4 — Enter `4` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Step 6 — Enter `Flow Red` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book match (enter Match ID)
0. Back to match browsing
Choose action:
```

### Step 7 — Enter `1` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 8 — Enter `4` and press Enter

Console output

```text
=== MATCH DETAILS #4 ===
Match:      Flow Red vs Flow Blue
Stadium:    Flow Demo Stadium [ID: 3]
Kick-off:   15/11/2030 19:30
Sales Gate: [ OPEN ]

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 9 — Enter `2` and press Enter

Console output

```text
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

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 10 — Enter `3` and press Enter

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

### Step 11 — Enter `0` and press Enter

Console output

```text
Booking cancelled.

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 12 — Enter `4` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book match (enter Match ID)
0. Back to match browsing
Choose action:
```

### Step 13 — Enter `3` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 14 — Enter `4` and press Enter

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

### Step 15 — Enter `0` and press Enter

Console output

```text
Booking cancelled.

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 16 — Enter `0` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 17 — Enter `0` and press Enter

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

### Step 18 — Enter `0` and press Enter

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

- Continue from the selected Match to seat selection and demonstrate cancelling seat input.
- The flow ends at Main. Keep CORE_DIR for the next flow.
