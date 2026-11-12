# Search Matches

- Search matches by team name and show an empty result when no match is found.
- Code path `MainController.runGuestMenu → BrowseController.searchMatches → BrowseService → MatchRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-001"></a>

## Flow 001 — Search Matches by team name

- Start at Main in the newly prepared CORE_DIR.

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

- Search matches by team name and show an empty result when no match is found.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-008"></a>

## Flow 008 — Search with no matching result

- Start at Main in CORE_DIR after [Flow 007](../01_Guest/03_View_Seat_Availability.md#flow-007).

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
3. NO_SUCH_TEAM_DEMO
4. 0
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

### Step 3 — Enter `NO_SUCH_TEAM_DEMO` and press Enter

Console output

```text
[!] No matches found matching your criteria.

=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 4 — Enter `0` and press Enter

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

- Search matches by team name and show an empty result when no match is found.
- The flow ends at Main. Keep CORE_DIR for the next flow.
