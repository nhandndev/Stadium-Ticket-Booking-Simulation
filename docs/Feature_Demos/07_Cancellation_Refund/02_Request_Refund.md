# Request Refund

- Submit a refund reason for an eligible paid booking and reject tickets that have already been checked in.
- Code path `MainController → BookingController → RefundController.requestRefund → RefundService → RefundRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-092"></a>

## Flow 092 — Request a refund for Booking B4

- Start at Main in CORE_DIR after [Flow 090](../06_Seller/07_Issue_Tickets.md#flow-090).

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
5. 4
6. 1
7. 5
8. Cannot attend
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

### Step 5 — Enter `4` and press Enter

Console output

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `1` and press Enter

Console output

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Step 7 — Enter `5` and press Enter

Console output

```text
=== BOOKING #5 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A5] | IDs: [25]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Step 8 — Enter `Cannot attend` and press Enter

Console output

```text
Refund request #2 | Booking #5 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
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

- Submit a refund reason for an eligible paid booking and reject tickets that have already been checked in.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-093"></a>

## Flow 093 — Request a refund for Booking B5

- Start at Main in CORE_DIR after [Flow 092](../07_Cancellation_Refund/02_Request_Refund.md#flow-092).

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
5. 4
6. 1
7. 6
8. Changed travel plan
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

### Step 5 — Enter `4` and press Enter

Console output

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `1` and press Enter

Console output

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Step 7 — Enter `6` and press Enter

Console output

```text
=== BOOKING #6 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A6] | IDs: [26]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Step 8 — Enter `Changed travel plan` and press Enter

Console output

```text
Refund request #3 | Booking #6 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
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

- Submit a refund reason for an eligible paid booking and reject tickets that have already been checked in.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-106"></a>

## Flow 106 — Reject a refund for a checked-in ticket

- Start at Main in CORE_DIR after [Flow 105](../08_Gate/02_Check_In_Ticket.md#flow-105).

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
5. 4
6. 1
7. 4
8. Cannot attend anymore
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

### Step 5 — Enter `4` and press Enter

Console output

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `1` and press Enter

Console output

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Step 8 — Enter `Cannot attend anymore` and press Enter

Console output

```text
Used or cancelled ticket cannot be refunded

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
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

- Submit a refund reason for an eligible paid booking and reject tickets that have already been checked in.
- The flow ends at Main. Keep CORE_DIR for the next flow.
