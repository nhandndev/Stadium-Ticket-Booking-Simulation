# Deactivate Staff

- Deactivate an eligible Staff account.
- Code path `MainController → AdminController → AccountAdminController.deactivateStaff → AccountAdminService → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-122"></a>

## Flow 122 — Deactivate Staff Account

- Start at Main in CORE_DIR after [Flow 121](../09_Admin_Accounts_Roles/15_Update_Staff_Information.md#flow-121).

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
4. 2
5. 3
6. 2
7. 8
8. 6
9. y
10. 0
11. 0
12. 0
13. 0
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

### Step 4 — Enter `2` and press Enter

Console output

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Step 5 — Enter `3` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Step 7 — Enter `8` and press Enter

Console output

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Unassigned Staff | sim@stadium.com | Role: UNASSIGNED | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Deactivate account #6? (y/n, Enter = No):
```

### Step 9 — Enter `y` and press Enter

Console output

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: INACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Step 12 — Enter `0` and press Enter

Console output

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 13 — Enter `0` and press Enter

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

- Deactivate an eligible Staff account.
- The flow ends at Main. Keep CORE_DIR for the next flow.
