# My Refund Requests

- Track REQUESTED, APPROVED and REJECTED refund requests and their review notes.
- Code path `MainController → BookingController → RefundController.getMyRefunds → RefundService → RefundRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-094"></a>

## Flow 094 — View refund requests before review

- Start at Main in CORE_DIR after [Flow 093](../07_Cancellation_Refund/02_Request_Refund.md#flow-093).

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
6. 3
7. 0
8. 0
9. 0
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

### Step 6 — Enter `3` and press Enter

Console output

```text
Refund request #2 | Booking #5 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.
Refund request #3 | Booking #6 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Step 7 — Enter `0` and press Enter

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

### Step 8 — Enter `0` and press Enter

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

### Step 9 — Enter `0` and press Enter

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

- Track REQUESTED, APPROVED and REJECTED refund requests and their review notes.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-097"></a>

## Flow 097 — View refund requests after review

- Start at Main in CORE_DIR after [Flow 096](../07_Cancellation_Refund/05_Reject_Refund.md#flow-096).

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
6. 3
7. 0
8. 0
9. 0
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

### Step 6 — Enter `3` and press Enter

Console output

```text
Refund request #2 | Booking #5 | Status APPROVED
Refund request #3 | Booking #6 | Status REJECTED

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Step 7 — Enter `0` and press Enter

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

### Step 8 — Enter `0` and press Enter

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

### Step 9 — Enter `0` and press Enter

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

- Track REQUESTED, APPROVED and REJECTED refund requests and their review notes.
- The flow ends at Main. Keep CORE_DIR for the next flow.
