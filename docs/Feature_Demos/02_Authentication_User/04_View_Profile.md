# View Profile

- View the signed-in account name, email, phone, role and status for Fan and Staff roles.
- Code path `MainController → AuthController.getCurrentUser → AuthService.getCurrentUser → MainView.displayUser`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-012"></a>

## Flow 012 — View Fan Profile

- Start at Main in CORE_DIR after [Flow 011](../02_Authentication_User/02_Login_As_Fan.md#flow-011).

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
4. 1
5. 0
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

### Step 4 — Enter `1` and press Enter

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

### Step 5 — Enter `0` and press Enter

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

- View the signed-in account name, email, phone, role and status for Fan and Staff roles.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-127"></a>

## Flow 127 — View Seller Profile

- Start at Main in CORE_DIR after [Flow 126](../09_Admin_Accounts_Roles/20_Assign_Staff_Role.md#flow-126).

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
4. 1
5. 0
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

### Step 4 — Enter `1` and press Enter

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

### Step 5 — Enter `0` and press Enter

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

- View the signed-in account name, email, phone, role and status for Fan and Staff roles.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-129"></a>

## Flow 129 — View Gate Staff Profile

- Start at Main in CORE_DIR after [Flow 128](../02_Authentication_User/05_Update_Profile.md#flow-128).

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
4. 1
5. 0
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

### Step 4 — Enter `1` and press Enter

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

### Step 5 — Enter `0` and press Enter

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

- View the signed-in account name, email, phone, role and status for Fan and Staff roles.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-131"></a>

## Flow 131 — View Administrator Profile

- Start at Main in CORE_DIR after [Flow 130](../02_Authentication_User/05_Update_Profile.md#flow-130).

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
4. 1
5. 0
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

### Step 4 — Enter `1` and press Enter

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

### Step 5 — Enter `0` and press Enter

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

- View the signed-in account name, email, phone, role and status for Fan and Staff roles.
- The flow ends at Main. Keep CORE_DIR for the next flow.
