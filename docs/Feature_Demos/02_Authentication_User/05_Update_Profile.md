# Update Profile

- Update the signed-in account full name and phone, then save the corresponding CSV record.
- Code path `MainController → MainView.readProfileUpdateRequest → AuthController.updateCurrentUserProfile → AuthService → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-013"></a>

## Flow 013 — Update Fan Profile

- Start at Main in CORE_DIR after [Flow 012](../02_Authentication_User/04_View_Profile.md#flow-012).

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
4. 3
5. Flow Fan Updated
6. 0901234568
7. 0
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

### Step 4 — Enter `3` and press Enter

Console output

```text
New full name (Enter = Cancel):
```

### Step 5 — Enter `Flow Fan Updated` and press Enter

Console output

```text
New phone:
```

### Step 6 — Enter `0901234568` and press Enter

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

### Step 7 — Enter `0` and press Enter

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

- Update the signed-in account full name and phone, then save the corresponding CSV record.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-128"></a>

## Flow 128 — Update Seller Profile

- Start at Main in CORE_DIR after [Flow 127](../02_Authentication_User/04_View_Profile.md#flow-127).

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
4. 3
5. Seller Flow Profile
6. 0905555555
7. 0
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

### Step 4 — Enter `3` and press Enter

Console output

```text
New full name (Enter = Cancel):
```

### Step 5 — Enter `Seller Flow Profile` and press Enter

Console output

```text
New phone:
```

### Step 6 — Enter `0905555555` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Seller Flow Profile
Email:  seller1@stadium.com
Phone:  0905555555
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 7 — Enter `0` and press Enter

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

- Update the signed-in account full name and phone, then save the corresponding CSV record.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-130"></a>

## Flow 130 — Update Gate Staff Profile

- Start at Main in CORE_DIR after [Flow 129](../02_Authentication_User/04_View_Profile.md#flow-129).

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
4. 3
5. Gate Staff Flow Profile
6. 0905555555
7. 0
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

### Step 4 — Enter `3` and press Enter

Console output

```text
New full name (Enter = Cancel):
```

### Step 5 — Enter `Gate Staff Flow Profile` and press Enter

Console output

```text
New phone:
```

### Step 6 — Enter `0905555555` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Gate Staff Flow Profile
Email:  gate1@stadium.com
Phone:  0905555555
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Step 7 — Enter `0` and press Enter

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

- Update the signed-in account full name and phone, then save the corresponding CSV record.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-132"></a>

## Flow 132 — Update Administrator Profile

- Start at Main in CORE_DIR after [Flow 131](../02_Authentication_User/04_View_Profile.md#flow-131).

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
4. 3
5. Admin Flow Profile
6. 0905555555
7. 0
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

### Step 4 — Enter `3` and press Enter

Console output

```text
New full name (Enter = Cancel):
```

### Step 5 — Enter `Admin Flow Profile` and press Enter

Console output

```text
New phone:
```

### Step 6 — Enter `0905555555` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Admin Flow Profile
Email:  admin@stadium.com
Phone:  0905555555
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 7 — Enter `0` and press Enter

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

- Update the signed-in account full name and phone, then save the corresponding CSV record.
- The flow ends at Main. Keep CORE_DIR for the next flow.
