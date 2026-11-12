# Booking Details

- View the customer, Match, seats, total, status and payment deadline of an accessible booking.
- Code path `MainController → BookingController.getBookingDetails → BookingService → BookingRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-071"></a>

## Flow 071 — Booking Details

- Start at Main in CORE_DIR after [Flow 070](../05_Fan_Booking_Tickets/03_Booking_History.md#flow-070).

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
5. 2
6. 2
7. 3
8. 0
9. 0
10. 0
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

### Step 5 — Enter `2` and press Enter

Console output

```text
=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING
  Payment deadline: 2026-11-13T00:17:29.188470
Booking ID: (0 = Back):
```

### Step 7 — Enter `3` and press Enter

Console output

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING
Seats held until: 2026-11-13T00:17:29.188470
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Step 8 — Enter `0` and press Enter

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

### Step 9 — Enter `0` and press Enter

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

### Step 10 — Enter `0` and press Enter

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

- View the customer, Match, seats, total, status and payment deadline of an accessible booking.
- The flow ends at Main. Keep CORE_DIR for the next flow.
