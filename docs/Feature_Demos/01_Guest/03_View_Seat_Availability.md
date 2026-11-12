# View Seat Availability

- View the seats and their availability for the selected Match.
- Code path `MainController → BrowseController.getSeatMap → BrowseService → MatchSeatRepository / SeatRepository → SeatMapView`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-003"></a>

## Flow 003 — View Seat Availability from search results

- Start at Main in CORE_DIR after [Flow 002](../01_Guest/02_View_Match_Details.md#flow-002).

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
1. 1
2. 1
3. Vietnam
4. 2
5. 1
6. 0
7. 0
```

### Step 1 — Enter `1` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 2 — Enter `1` and press Enter

Console output

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Step 3 — Enter `Vietnam` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Step 4 — Enter `2` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 5 — Enter `1` and press Enter

Console output

```text
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

--- Match #1 ---
1. View match details
2. View seat availability / Seat map
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 6 — Enter `0` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 7 — Enter `0` and press Enter

Console output

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- View the seats and their availability for the selected Match.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-007"></a>

## Flow 007 — View Seat Map directly by ID

- Start at Main in CORE_DIR after [Flow 006](../01_Guest/02_View_Match_Details.md#flow-006).

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
1. 1
2. 4
3. 1
4. 0
5. 0
```

### Step 1 — Enter `1` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 2 — Enter `4` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED
Match ID (0 = Back):
```

### Step 3 — Enter `1` and press Enter

Console output

```text
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

--- Match #1 ---
1. View match details
2. View seat availability / Seat map
4. Choose another match
0. Back to match browsing
Choose action:
```

### Step 4 — Enter `0` and press Enter

Console output

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 5 — Enter `0` and press Enter

Console output

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- View the seats and their availability for the selected Match.
- The flow ends at Main. Keep CORE_DIR for the next flow.
