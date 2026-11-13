# View Seat Map and Prices

## Current Seller Menu — 2026-11-14

Seller > 3. Ticket sales contains 1. Create booking for Fan / walk-in customer,
2. Pay for an existing booking and 0. Back. Successful cash checkout automatically
issues/displays tickets. Payment and issue retain separate methods in the code.
The walkthroughs here use an isolated copy of the core fixture with future Match
dates. Inputs are listed in order. A blank line means Enter.

### View seat map and prices

Start at the Seller menu.

Inputs

```text
4
1
```

Result captured from the current code

```text
Match #1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-12-14T01:40:09 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 500000       | BOOKED
2        | A2       | Stand A VIP              | 500000       | BOOKED
3        | A3       | Stand A VIP              | 500000       | AVAILABLE
4        | A4       | Stand A VIP              | 500000       | AVAILABLE
5        | A5       | Stand A VIP              | 500000       | AVAILABLE
6        | A6       | Stand A VIP              | 500000       | AVAILABLE
7        | A7       | Stand A VIP              | 500000       | AVAILABLE
8        | A8       | Stand A VIP              | 500000       | AVAILABLE
9        | A9       | Stand A VIP              | 500000       | AVAILABLE
10       | A10      | Stand A VIP              | 500000       | AVAILABLE
11       | B1       | Stand B                  | 300000       | AVAILABLE
12       | B2       | Stand B                  | 300000       | AVAILABLE
13       | B3       | Stand B                  | 300000       | AVAILABLE
14       | B4       | Stand B                  | 300000       | AVAILABLE
15       | B5       | Stand B                  | 300000       | AVAILABLE
16       | B6       | Stand B                  | 300000       | AVAILABLE
17       | B7       | Stand B                  | 300000       | AVAILABLE
18       | B8       | Stand B                  | 300000       | AVAILABLE
19       | B9       | Stand B                  | 300000       | AVAILABLE
20       | B10      | Stand B                  | 300000       | AVAILABLE

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


- Inspect the Seat IDs, prices and availability before choosing seats.
- Code path `MainController → BookingController → BrowseController.getSeatMap → BrowseService → SeatMapView`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-084"></a>

## Flow 084 — View Seat Map and Prices at the counter

- Start at Main in CORE_DIR after [Flow 083](../06_Seller/02_Create_Customer_Fan_Account.md#flow-083).

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
5. 6
6. 4
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

### Step 5 — Enter `6` and press Enter

Console output

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Step 6 — Enter `4` and press Enter

Console output

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | BOOKED
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE

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

- Inspect the Seat IDs, prices and availability before choosing seats.
- The flow ends at Main. Keep CORE_DIR for the next flow.
