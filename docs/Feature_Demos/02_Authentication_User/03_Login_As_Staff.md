# Login As Staff

Gate menu update — 2026-11-14. Gate dashboard option 2 is now `Check in ticket`.
Its submenu has `1. Check in ticket` and `0. Back`. Ticket Code is the only ticket
identifier requested. Captures below retain their original menu context.


- Log in through the Staff route and open the role-specific menu. Fan accounts use the Fan login route.
- Code path `MainController.handleStaffLogin → AuthController.login → AuthService.login → MainController.routeAuthenticatedUser`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-018"></a>

## Flow 018 — Use a Fan account at Staff login

- Start at Main in CORE_DIR after [Flow 017](../02_Authentication_User/02_Login_As_Fan.md#flow-017).

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
2. flow.fan@example.com
3. FlowPass123
```

### Step 1 — Enter `3` and press Enter

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
Use Login As Fan for this account.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Log in through the Staff route and open the role-specific menu. Fan accounts use the Fan login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-020"></a>

## Flow 020 — Log in as Administrator

- Start at Main in CORE_DIR after [Flow 019](../02_Authentication_User/02_Login_As_Fan.md#flow-019).

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
2. admin@stadium.com
3. admin123
4. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `admin@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `admin123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `0` and press Enter

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

- Log in through the Staff route and open the role-specific menu. Fan accounts use the Fan login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-080"></a>

## Flow 080 — Log in as Seller

- Start at Main in CORE_DIR after [Flow 079](../05_Fan_Booking_Tickets/02_Create_Booking.md#flow-079).

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
2. seller1@stadium.com
3. seller123
4. 0
```

### Step 1 — Enter `3` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `seller1@stadium.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `seller123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Seller Nguyen
Email:  seller1@stadium.com
Phone:  0900000002
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 4 — Enter `0` and press Enter

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

- Log in through the Staff route and open the role-specific menu. Fan accounts use the Fan login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-099"></a>

## Flow 099 — Log in as Gate Staff

- Start at Main in CORE_DIR after [Flow 098](../05_Fan_Booking_Tickets/06_Ticket_History.md#flow-098).

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
4. 0
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

### Step 4 — Enter `0` and press Enter

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

- Log in through the Staff route and open the role-specific menu. Fan accounts use the Fan login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.
