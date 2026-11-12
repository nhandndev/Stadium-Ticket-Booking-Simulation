# Create Seat

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- Code path `MainController → AdminController.runAdminMenu → StadiumController.createSeat → StadiumService → Stadium/Section/SeatRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-029"></a>

## Flow 029 — Create Seat A1

- Start at Main in CORE_DIR after [Flow 028](../03_Admin_Venues/09_Update_Section.md#flow-028).

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
7. 1
8. 6
9. A
10. 1
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `1` and press Enter

Console output

```text
21 | Row A, Seat 1 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-030"></a>

## Flow 030 — Create Seat A2

- Start at Main in CORE_DIR after [Flow 029](../03_Admin_Venues/11_Create_Seat.md#flow-029).

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
7. 1
8. 6
9. A
10. 2
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `2` and press Enter

Console output

```text
22 | Row A, Seat 2 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-031"></a>

## Flow 031 — Create Seat A3

- Start at Main in CORE_DIR after [Flow 030](../03_Admin_Venues/11_Create_Seat.md#flow-030).

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
7. 1
8. 6
9. A
10. 3
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `3` and press Enter

Console output

```text
23 | Row A, Seat 3 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-032"></a>

## Flow 032 — Create Seat A4

- Start at Main in CORE_DIR after [Flow 031](../03_Admin_Venues/11_Create_Seat.md#flow-031).

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
7. 1
8. 6
9. A
10. 4
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `4` and press Enter

Console output

```text
24 | Row A, Seat 4 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-033"></a>

## Flow 033 — Create Seat A5

- Start at Main in CORE_DIR after [Flow 032](../03_Admin_Venues/11_Create_Seat.md#flow-032).

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
7. 1
8. 6
9. A
10. 5
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `5` and press Enter

Console output

```text
25 | Row A, Seat 5 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-034"></a>

## Flow 034 — Create Seat A6

- Start at Main in CORE_DIR after [Flow 033](../03_Admin_Venues/11_Create_Seat.md#flow-033).

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
7. 1
8. 6
9. A
10. 6
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `6` and press Enter

Console output

```text
26 | Row A, Seat 6 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-035"></a>

## Flow 035 — Create Seat A7

- Start at Main in CORE_DIR after [Flow 034](../03_Admin_Venues/11_Create_Seat.md#flow-034).

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
7. 1
8. 6
9. A
10. 7
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `7` and press Enter

Console output

```text
27 | Row A, Seat 7 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-036"></a>

## Flow 036 — Create Seat A8

- Start at Main in CORE_DIR after [Flow 035](../03_Admin_Venues/11_Create_Seat.md#flow-035).

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
7. 1
8. 6
9. A
10. 8
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `A` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `8` and press Enter

Console output

```text
28 | Row A, Seat 8 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-043"></a>

## Flow 043 — Create an auxiliary Seat for deletion

- Start at Main in CORE_DIR after [Flow 042](../03_Admin_Venues/15_Activate_Seat.md#flow-042).

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
7. 1
8. 6
9. X
10. 1
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

### Step 7 — Enter `1` and press Enter

Console output

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Step 8 — Enter `6` and press Enter

Console output

```text
Row label (Enter = Cancel):
```

### Step 9 — Enter `X` and press Enter

Console output

```text
Seat number:
```

### Step 10 — Enter `1` and press Enter

Console output

```text
29 | Row X, Seat 1 | Section #6 | Active: true

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

- Create a Seat with its Section, row label and seat number. Seats A1–A8 prepare the later ticket-sale flows.
- The flow ends at Main. Keep CORE_DIR for the next flow.
