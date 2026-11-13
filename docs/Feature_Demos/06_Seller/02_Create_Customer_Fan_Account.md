# Create Customer Fan Account

## Current Seller Menu — 2026-11-14

Seller > 3. Ticket sales contains 1. Create booking for Fan / walk-in customer,
2. Pay for an existing booking and 0. Back. Successful cash checkout automatically
issues/displays tickets. Payment and issue retain separate methods in the code.
The walkthroughs here use an isolated copy of the core fixture with future Match
dates. Inputs are listed in order. A blank line means Enter.

### Create Fan

Start at the Seller menu.

Inputs

```text
2
Counter Flow Customer
counter.flow@example.com
CounterPass123
0901234567
```

Result captured from the current code

```text
Created Fan #6 | Counter Flow Customer | counter.flow@example.com

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


- Register a Fan account for a customer at the counter.
- Code path `MainController → BookingController → SellerView.readRegisterFanRequest → FanController.register → FanService → FanRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-083"></a>

## Flow 083 — Create a Fan Account at the counter

- Start at Main in CORE_DIR after [Flow 082](../06_Seller/01_Search_and_List_Customers.md#flow-082).

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
5. 2
6. Flow Counter Customer
7. flow.counter@example.com
8. Counter123
9. 0902222222
10. 0
11. 0
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

### Step 5 — Enter `2` and press Enter

Console output

```text
Full name (Enter = Cancel):
```

### Step 6 — Enter `Flow Counter Customer` and press Enter

Console output

```text
Email:
```

### Step 7 — Enter `flow.counter@example.com` and press Enter

Console output

```text
Password (at least 8 characters):
```

### Step 8 — Enter `Counter123` and press Enter

Console output

```text
Phone:
```

### Step 9 — Enter `0902222222` and press Enter

Console output

```text
Created Fan #7 | Flow Counter Customer | flow.counter@example.com

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

### Step 10 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 11 — Enter `0` and press Enter

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

- Register a Fan account for a customer at the counter.
- The flow ends at Main. Keep CORE_DIR for the next flow.
