# Deactivate Seat

- Deactivate the Seat after confirmation and the inventory checks.
- Code path `MainController → AdminController.runAdminMenu → StadiumController.deactivateSeat → StadiumService → Stadium/Section/SeatRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-041"></a>

## Flow 041 — Deactivate Seat

- Start at Main in CORE_DIR after [Flow 040](../03_Admin_Venues/14_Update_Seat.md#flow-040).

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
5. 1
6. 3
7. 7
8. 28
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

### Step 5 — Enter `1` and press Enter

Console output

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Step 6 — Enter `3` and press Enter

Console output

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Step 7 — Enter `7` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
9 | Row A, Seat 9 | Section #1 | Active: true
10 | Row A, Seat 10 | Section #1 | Active: true
2 | Stand B | Stadium #1
11 | Row B, Seat 1 | Section #2 | Active: true
12 | Row B, Seat 2 | Section #2 | Active: true
13 | Row B, Seat 3 | Section #2 | Active: true
14 | Row B, Seat 4 | Section #2 | Active: true
15 | Row B, Seat 5 | Section #2 | Active: true
16 | Row B, Seat 6 | Section #2 | Active: true
17 | Row B, Seat 7 | Section #2 | Active: true
18 | Row B, Seat 8 | Section #2 | Active: true
19 | Row B, Seat 9 | Section #2 | Active: true
20 | Row B, Seat 10 | Section #2 | Active: true
3 | Stand C | Stadium #1
List is empty.
4 | Stand D | Stadium #1
List is empty.
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Step 8 — Enter `28` and press Enter

Console output

```text
Deactivate seat #28? (y/n, Enter = No):
```

### Step 9 — Enter `y` and press Enter

Console output

```text
28 | Row A, Seat 8 | Section #6 | Active: false

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Step 10 — Enter `0` and press Enter

Console output

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Deactivate the Seat after confirmation and the inventory checks.
- The flow ends at Main. Keep CORE_DIR for the next flow.
