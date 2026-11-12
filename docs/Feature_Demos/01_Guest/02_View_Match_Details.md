# View Match Details

- View the teams, Stadium, kick-off time and sales status from search results or a Match ID.
- Code path `MainController → BrowseController.viewMatchDetails → BrowseService → MatchRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-002"></a>

## Flow 002 — View Match Details from search results

- Start at Main in CORE_DIR after [Flow 001](../01_Guest/01_Search_Matches.md#flow-001).

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
4. 1
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

### Step 4 — Enter `1` and press Enter

Console output

```text
Match ID (0 = Back):
```

### Step 5 — Enter `1` and press Enter

Console output

```text
=== MATCH DETAILS #1 ===
Match:      Vietnam vs Thailand
Stadium:    My Dinh National Stadium [ID: 1]
Kick-off:   15/11/2026 19:00
Sales Gate: [ OPEN ]

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

- View the teams, Stadium, kick-off time and sales status from search results or a Match ID.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-006"></a>

## Flow 006 — View Match Details directly by ID

- Start at Main in CORE_DIR after [Flow 005](../01_Guest/04_Filter_Matches_and_List_Schedule.md#flow-005).

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
2. 3
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

### Step 2 — Enter `3` and press Enter

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
=== MATCH DETAILS #1 ===
Match:      Vietnam vs Thailand
Stadium:    My Dinh National Stadium [ID: 1]
Kick-off:   15/11/2026 19:00
Sales Gate: [ OPEN ]

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

- View the teams, Stadium, kick-off time and sales status from search results or a Match ID.
- The flow ends at Main. Keep CORE_DIR for the next flow.
