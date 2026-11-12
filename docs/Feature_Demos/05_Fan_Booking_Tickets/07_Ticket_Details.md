# Ticket Details

- View a ticket code, Match, Seat, status and check-in timestamp.
- Code path `MainController → BookingController → TicketController.getTicketDetails → TicketService → TicketRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-076"></a>

## Flow 076 — Ticket Details

- Start at Main in CORE_DIR after [Flow 075](../05_Fan_Booking_Tickets/06_Ticket_History.md#flow-075).

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
5. 3
6. 2
7. 2
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

### Step 5 — Enter `3` and press Enter

Console output

```text
=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED
Ticket ID: (0 = Back):
```

### Step 7 — Enter `2` and press Enter

Console output

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1
Code: T-4-21 | Status: ISSUED
Checked in at: Not checked in

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
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

- View a ticket code, Match, Seat, status and check-in timestamp.
- The flow ends at Main. Keep CORE_DIR for the next flow.
