# Validate Ticket

- Validate the ticket code and Match, rejecting a mismatched Match.
- Code path `MainController → TicketController.validateTicket → TicketService.validateTicket → TicketRepository / BookingRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-100"></a>

## Flow 100 — Validate Ticket

- Start at Main in CORE_DIR after [Flow 099](../02_Authentication_User/03_Login_As_Staff.md#flow-099).

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
5. 1
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

### Step 5 — Enter `1` and press Enter

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

- Validate the ticket code and Match, rejecting a mismatched Match.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-104"></a>

## Flow 104 — Reject a ticket for the wrong Match

- Start at Main in CORE_DIR after [Flow 103](../08_Gate/02_Check_In_Ticket.md#flow-103).

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
5. 1
6. T-4-22
7. 1
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

### Step 5 — Enter `1` and press Enter

Console output

```text
Ticket code (Enter or 0 = Back):
```

### Step 6 — Enter `T-4-22` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 7 — Enter `1` and press Enter

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

- Validate the ticket code and Match, rejecting a mismatched Match.
- The flow ends at Main. Keep CORE_DIR for the next flow.
