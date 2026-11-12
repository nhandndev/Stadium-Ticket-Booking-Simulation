# Reject Refund

- Reject the request with a review note while preserving the booking and tickets.
- Code path `MainController → AdminController → RefundController.reviewRefund → RefundService → RefundRepository; AuditLogRepository.append`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-096"></a>

## Flow 096 — Reject Refund

- Start at Main in CORE_DIR after [Flow 095](../07_Cancellation_Refund/04_Approve_Refund.md#flow-095).

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
5. 4
6. 1
7. 3
8. n
9. Rejected after review
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Step 6 — Enter `1` and press Enter

Console output

```text
Refund request ID (0 = Back):
```

### Step 7 — Enter `3` and press Enter

Console output

```text
Approve refund? (y/n):
```

### Step 8 — Enter `n` and press Enter

Console output

```text
Review note (required, 0 = Cancel):
```

### Step 9 — Enter `Rejected after review` and press Enter

Console output

```text
Request #3 | Booking #6 | REJECTED
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
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

- Reject the request with a review note while preserving the booking and tickets.
- The flow ends at Main. Keep CORE_DIR for the next flow.
