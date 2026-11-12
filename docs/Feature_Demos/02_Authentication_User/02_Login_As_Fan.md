# Login As Fan

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- Code path `MainController.handleFanLogin → AuthController.login → AuthService.login → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-011"></a>

## Flow 011 — Login As Fan

- Start at Main in CORE_DIR after [Flow 010](../02_Authentication_User/01_Register_Fan_Account.md#flow-010).

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
4. 0
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
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
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

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-015"></a>

## Flow 015 — Reject an incorrect password

- Start at Main in CORE_DIR after [Flow 014](../02_Authentication_User/06_Logout.md#flow-014).

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
3. wrong
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

### Step 3 — Enter `wrong` and press Enter

Console output

```text
Invalid email or password

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-016"></a>

## Flow 016 — Reject an INACTIVE account

- Start at Main in CORE_DIR after [Flow 015](../02_Authentication_User/02_Login_As_Fan.md#flow-015).

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
2. em.do@gmail.com
3. 12345678
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `em.do@gmail.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `12345678` and press Enter

Console output

```text
Account is inactive

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-017"></a>

## Flow 017 — Use a Staff account at Fan login

- Start at Main in CORE_DIR after [Flow 016](../02_Authentication_User/02_Login_As_Fan.md#flow-016).

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
2. admin@stadium.com
3. admin123
```

### Step 1 — Enter `2` and press Enter

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
Use Login As Staff for this account.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-019"></a>

## Flow 019 — Cancel the login form

- Start at Main in CORE_DIR after [Flow 018](../02_Authentication_User/03_Login_As_Staff.md#flow-018).

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
2. Enter
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Submit a blank line

Console output

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Log in as a Fan, reject invalid credentials and INACTIVE accounts, and direct Staff accounts to the Staff login route.
- The flow ends at Main. Keep CORE_DIR for the next flow.
