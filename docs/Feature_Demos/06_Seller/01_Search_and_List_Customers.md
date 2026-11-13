# Search and List Customers

## Current Seller Menu — 2026-11-14

Seller > 3. Ticket sales contains 1. Create booking for Fan / walk-in customer,
2. Pay for an existing booking and 0. Back. Successful cash checkout automatically
issues/displays tickets. Payment and issue retain separate methods in the code.
The walkthroughs here use an isolated copy of the core fixture with future Match
dates. Inputs are listed in order. A blank line means Enter.

### Search Fan

Start at the Seller menu.

Inputs

```text
1
binh
```

Result captured from the current code

```text
Fan #2 | Tran Thi Binh | binh.tran@gmail.com | ACTIVE

=== SELLER ===
1. Search Fan
2. Create Fan
3. Ticket sales
4. View seat map and prices
5. View customer booking history
0. Back to dashboard
Choose:
```

## Historical Numbered Flows

The numbered flows below were captured on 2026-11-13 before the menu merge.
Their Console blocks retain that historical menu. Use the current walkthroughs
above and the [current menu map](../02_MENU_MAP.md) for the new navigation.


- Search Fan accounts by name or email, or leave the keyword empty to list accounts.
- Code path `MainController → BookingController.runSellerMenu → FanController.searchFans → FanService → FanRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-081"></a>

## Flow 081 — Search Customer

- Start at Main in CORE_DIR after [Flow 080](../02_Authentication_User/03_Login_As_Staff.md#flow-080).

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
4. 2
5. 1
6. flow.fan
7. 0
8. 0
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

### Step 4 — Enter `2` and press Enter

Console output

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Step 6 — Enter `flow.fan` and press Enter

Console output

```text
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Step 7 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 8 — Enter `0` and press Enter

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

- Search Fan accounts by name or email, or leave the keyword empty to list accounts.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-082"></a>

## Flow 082 — List Customers with an empty keyword

- Start at Main in CORE_DIR after [Flow 081](../06_Seller/01_Search_and_List_Customers.md#flow-081).

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
4. 2
5. 1
6. Enter
7. 0
8. 0
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

### Step 4 — Enter `2` and press Enter

Console output

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Step 5 — Enter `1` and press Enter

Console output

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Step 6 — Submit a blank line

Console output

```text
Fan #1 | Nguyen Van An | an.nguyen@gmail.com | INACTIVE
Fan #2 | Tran Thi Binh | binh.tran@gmail.com | ACTIVE
Fan #3 | Le Hoang Cuong | cuong.le@gmail.com | ACTIVE
Fan #4 | Pham Minh Dung | dung.pham@gmail.com | ACTIVE
Fan #5 | Do Thi Em | em.do@gmail.com | INACTIVE
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Step 7 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 8 — Enter `0` and press Enter

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

- Search Fan accounts by name or email, or leave the keyword empty to list accounts.
- The flow ends at Main. Keep CORE_DIR for the next flow.
