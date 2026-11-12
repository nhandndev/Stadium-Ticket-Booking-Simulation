# Filter Fan Status

- Filter Fan accounts by ACTIVE or INACTIVE status.
- Code path `MainController → AdminController → AccountAdminController.filterFans → AccountAdminService → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-111"></a>

## Flow 111 — Filter Fan Status

- Start at Main in CORE_DIR after [Flow 110](../09_Admin_Accounts_Roles/04_Search_Fan.md#flow-110).

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
6. 1
7. 5
8. 1
9. 0
10. 0
11. 0
12. 0
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

### Step 6 — Enter `1` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Step 7 — Enter `5` and press Enter

Console output

```text
1 Active | 2 Inactive | 0 All
Status:
```

### Step 8 — Enter `1` and press Enter

Console output

```text
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Step 9 — Enter `0` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Step 10 — Enter `0` and press Enter

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

### Step 11 — Enter `0` and press Enter

Console output

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 12 — Enter `0` and press Enter

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

- Filter Fan accounts by ACTIVE or INACTIVE status.
- The flow ends at Main. Keep CORE_DIR for the next flow.
