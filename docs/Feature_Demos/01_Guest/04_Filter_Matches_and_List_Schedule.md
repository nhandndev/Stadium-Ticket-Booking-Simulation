# Filter Matches and List Schedule

- Filter the schedule by Stadium and date. Leave both filters empty to list the full schedule.
- Code path `MainController → BrowseController.filterMatches → BrowseService → MatchRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-004"></a>

## Flow 004 — Filter Matches by Stadium and date

- Start at Main in CORE_DIR after [Flow 003](../01_Guest/03_View_Seat_Availability.md#flow-003).

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
2. 2
3. 1
4. 2026-11-15
5. 0
6. 0
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

### Step 2 — Enter `2` and press Enter

Console output

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Step 3 — Enter `1` and press Enter

Console output

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Step 4 — Enter `2026-11-15` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Step 5 — Enter `0` and press Enter

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

### Step 6 — Enter `0` and press Enter

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

- Filter the schedule by Stadium and date. Leave both filters empty to list the full schedule.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-005"></a>

## Flow 005 — List All Matches with empty filters

- Start at Main in CORE_DIR after [Flow 004](../01_Guest/04_Filter_Matches_and_List_Schedule.md#flow-004).

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
2. 2
3. Enter
4. Enter
5. 0
6. 0
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

### Step 2 — Enter `2` and press Enter

Console output

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Step 3 — Submit a blank line

Console output

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Step 4 — Submit a blank line

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Step 5 — Enter `0` and press Enter

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

### Step 6 — Enter `0` and press Enter

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

- Filter the schedule by Stadium and date. Leave both filters empty to list the full schedule.
- The flow ends at Main. Keep CORE_DIR for the next flow.
