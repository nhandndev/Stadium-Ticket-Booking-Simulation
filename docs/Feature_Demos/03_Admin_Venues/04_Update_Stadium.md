# Update Stadium

- Update the selected Stadium name and address.
- Code path `MainController → AdminController.runAdminMenu → StadiumController.updateStadium → StadiumService → Stadium/Section/SeatRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-024"></a>

## Flow 024 — Update Stadium

- Start at Main in CORE_DIR after [Flow 023](../03_Admin_Venues/03_View_Stadium_Details.md#flow-023).

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
6. 1
7. 4
8. 3
9. Flow Demo Stadium
10. Hanoi Updated
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

### Step 6 — Enter `1` and press Enter

Console output

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Step 7 — Enter `4` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Demo
Stadium ID (0 = Back):
```

### Step 8 — Enter `3` and press Enter

Console output

```text
Name (Enter = Cancel):
```

### Step 9 — Enter `Flow Demo Stadium` and press Enter

Console output

```text
Address:
```

### Step 10 — Enter `Hanoi Updated` and press Enter

Console output

```text
3 | Flow Demo Stadium | Hanoi Updated

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Step 11 — Enter `0` and press Enter

Console output

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Update the selected Stadium name and address.
- The flow ends at Main. Keep CORE_DIR for the next flow.
