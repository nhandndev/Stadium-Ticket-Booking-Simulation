# Update Fan Information

- Update Fan information. An empty password preserves the existing password.
- Code path `MainController → AdminController → AccountAdminController.updateFan → AccountAdminService → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-112"></a>

## Flow 112 — Update Fan Information

- Start at Main in CORE_DIR after [Flow 111](../09_Admin_Accounts_Roles/05_Filter_Fan_Status.md#flow-111).

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
7. 6
8. 8
9. Admin Flow Fan Updated
10. flow.adminfan@example.com
11. Enter
12. 0903333334
13. 0
14. 0
15. 0
16. 0
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

### Step 7 — Enter `6` and press Enter

Console output

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: INACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Step 8 — Enter `8` and press Enter

Console output

```text
Full name (Enter = Cancel):
```

### Step 9 — Enter `Admin Flow Fan Updated` and press Enter

Console output

```text
Email:
```

### Step 10 — Enter `flow.adminfan@example.com` and press Enter

Console output

```text
New password (Enter = Keep current):
```

### Step 11 — Submit a blank line

Console output

```text
Phone:
```

### Step 12 — Enter `0903333334` and press Enter

Console output

```text
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

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

### Step 13 — Enter `0` and press Enter

Console output

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Step 14 — Enter `0` and press Enter

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

### Step 15 — Enter `0` and press Enter

Console output

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 16 — Enter `0` and press Enter

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

- Update Fan information. An empty password preserves the existing password.
- The flow ends at Main. Keep CORE_DIR for the next flow.
