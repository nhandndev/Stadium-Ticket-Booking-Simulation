# Update Ticket Price

- Update the amount of the selected ticket price.
- Code path `MainController → AdminController → MatchController.updateTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-060"></a>

## Flow 060 — Update Ticket Price

- Start at Main in CORE_DIR after [Flow 059](../04_Admin_Matches_Prices/08_View_Ticket_Price_Details.md#flow-059).

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
5. 2
6. 2
7. 4
8. 4
9. 6
10. 260000
11. 0
12. 0
13. 0
14. 0
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

### Step 5 — Enter `2` and press Enter

Console output

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Step 6 — Enter `2` and press Enter

Console output

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Step 7 — Enter `4` and press Enter

Console output

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Step 8 — Enter `4` and press Enter

Console output

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 9 — Enter `6` and press Enter

Console output

```text
Price in VND (0 = Cancel):
```

### Step 10 — Enter `260000` and press Enter

Console output

```text
Match #4 | Section #6 | Price: 260000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Step 12 — Enter `0` and press Enter

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

### Step 13 — Enter `0` and press Enter

Console output

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Step 14 — Enter `0` and press Enter

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

- Update the amount of the selected ticket price.
- The flow ends at Main. Keep CORE_DIR for the next flow.
