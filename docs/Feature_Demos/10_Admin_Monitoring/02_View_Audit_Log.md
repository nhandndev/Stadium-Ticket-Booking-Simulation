# View Audit Log

- View stored audit records, including the refund decisions recorded in earlier flows.
- Code path `MainController → AdminController.getAuditLogs → AdminService → AuditLogRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Current Flow — 2026-11-14

- Administrator dashboard > `2. Manage system operations` > `5. Reports and audit
  logs` > `2. Audit logs`.
- Each row shows the Staff account's current name and role. A missing account is
  shown as `Unknown staff #<id>` with role `UNKNOWN`.
- Current audit creation covers refund review. The following isolated check
  deliberately seeded one known Staff ID and one missing Staff ID.

```text
=== AUDIT LOGS ===
2 | Name: Unknown staff #99999 | Role: UNKNOWN | REVIEW_REFUND | Target: REFUND #2 | Result: REJECTED | 2026-11-14T03:56:39.925082
1 | Name: Administrator | Role: ADMIN | REVIEW_REFUND | Target: REFUND #1 | Result: APPROVED | 2026-11-14T03:56:39.924959

=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

The original numbered recordings below retain their earlier output.

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-137"></a>

## Flow 137 — View Audit Log

- Start at Main in CORE_DIR after [Flow 136](../10_Admin_Monitoring/01_View_System_Summary.md#flow-136).

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
5. 5
6. 2
7. 0
8. 0
9. 0
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

### Step 5 — Enter `5` and press Enter

Console output

```text
=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== AUDIT LOGS ===
5 | Actor #1 | REVIEW_REFUND | Target: REFUND #3 | Result: REJECTED | 2026-11-12T23:47:39.953627
4 | Actor #1 | REVIEW_REFUND | Target: REFUND #2 | Result: APPROVED | 2026-11-12T23:47:39.545422
3 | Actor #1 | ASSIGN_ROLE | Target: STAFF #3 | Result: SUCCESS | 2026-10-26T00:10
2 | Actor #1 | OPEN_TICKET_SALES | Target: MATCH #1 | Result: SUCCESS | 2026-10-26T00:05
1 | Actor #1 | INITIALIZE_DATASET | Target: SYSTEM #0 | Result: SUCCESS | 2026-10-26T00:00

=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Step 7 — Enter `0` and press Enter

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

### Step 8 — Enter `0` and press Enter

Console output

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
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

- View stored audit records, including the refund decisions recorded in earlier flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.
