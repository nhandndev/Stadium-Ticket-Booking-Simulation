# Check In Ticket

- Confirm one-time entry, record its timestamp and handle cancellation, reuse and missing ticket codes.
- Code path `MainController → TicketController.checkInTicket → TicketService.checkInTicket → TicketRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Current Flow — 2026-11-14

1. Main menu — enter `3`, then the Gate Staff email and password.
2. Gate Staff dashboard — enter `2`.
3. Gate menu — enter `1. Check in ticket`.
4. Enter an ISSUED Ticket Code from Booking Details or Ticket History.
5. Review customer name/phone, ticket code, Match name/ID, seat and status.
   Walk-in name and phone are `None`.
6. Enter `n` or a blank line to cancel without consuming the ticket, or `y` to check in.
7. Successful confirmation shows `CHECKED_IN` and its timestamp. Reusing that code
   is rejected. An unknown or cancelled ticket is rejected before confirmation.

Example preview for an issued walk-in ticket

```text
Customer: None | Phone: None
Ticket #13 | Code T-12-4 | Hoàng Anh Gia Lai vs Bình Định (Match #8) | Stand A VIP A4 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

The IDs above illustrate the output shape. Choose a real issued code from the
active dataset. The captured flows below are historical recordings of the earlier
separate validation menu with Match ID entry.


## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-101"></a>

## Flow 101 — Cancel check-in confirmation

- Start at Main in CORE_DIR after [Flow 100](../08_Gate/01_Validate_Ticket.md#flow-100).

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
1. 3
2. gate1@stadium.com
3. gate123
4. 2
5. 2
6. T-4-21
7. 4
8. n
9. 0
10. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `gate1@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `gate123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 5 — Enter `2` and press Enter

Console output

```text
Ticket code (Enter or 0 = Back):
```

### Step 6 — Enter `T-4-21` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Step 8 — Enter `n` and press Enter

Console output

```text
Check-in cancelled.

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 9 — Enter `0` and press Enter

Console output

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
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

- Confirm one-time entry, record its timestamp and handle cancellation, reuse and missing ticket codes.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-102"></a>

## Flow 102 — Check-in Ticket

- Start at Main in CORE_DIR after [Flow 101](../08_Gate/02_Check_In_Ticket.md#flow-101).

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
1. 3
2. gate1@stadium.com
3. gate123
4. 2
5. 2
6. T-4-21
7. 4
8. y
9. 0
10. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `gate1@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `gate123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 5 — Enter `2` and press Enter

Console output

```text
Ticket code (Enter or 0 = Back):
```

### Step 6 — Enter `T-4-21` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Step 8 — Enter `y` and press Enter

Console output

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status CHECKED_IN | Checked in at 2026-11-12T23:47:42.306200

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 9 — Enter `0` and press Enter

Console output

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
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

- Confirm one-time entry, record its timestamp and handle cancellation, reuse and missing ticket codes.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-103"></a>

## Flow 103 — Reject repeated ticket use

- Start at Main in CORE_DIR after [Flow 102](../08_Gate/02_Check_In_Ticket.md#flow-102).

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
1. 3
2. gate1@stadium.com
3. gate123
4. 2
5. 2
6. T-4-21
7. 4
8. 0
9. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `gate1@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `gate123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 5 — Enter `2` and press Enter

Console output

```text
Ticket code (Enter or 0 = Back):
```

### Step 6 — Enter `T-4-21` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Ticket is not issued for this match or was already used

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 8 — Enter `0` and press Enter

Console output

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
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

- Confirm one-time entry, record its timestamp and handle cancellation, reuse and missing ticket codes.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-105"></a>

## Flow 105 — Reject an unknown ticket code

- Start at Main in CORE_DIR after [Flow 104](../08_Gate/01_Validate_Ticket.md#flow-104).

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
1. 3
2. gate1@stadium.com
3. gate123
4. 2
5. 2
6. UNKNOWN_TICKET_FLOW
7. 4
8. 0
9. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `gate1@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `gate123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 5 — Enter `2` and press Enter

Console output

```text
Ticket code (Enter or 0 = Back):
```

### Step 6 — Enter `UNKNOWN_TICKET_FLOW` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 7 — Enter `4` and press Enter

Console output

```text
Ticket not found

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Step 8 — Enter `0` and press Enter

Console output

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
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

- Confirm one-time entry, record its timestamp and handle cancellation, reuse and missing ticket codes.
- The flow ends at Main. Keep CORE_DIR for the next flow.
