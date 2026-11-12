# Invalid Menu Input

- Report nonnumeric menu input and allow the user to enter a valid choice.
- `MainController` and domain Controllers handle `NumberFormatException` and return to the menu.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-139"></a>

## Flow 139 — Recover from nonnumeric menu input

- Start at Main in CORE_DIR after [Flow 138](../02_Authentication_User/07_CSV_Profile_Persistence.md#flow-138).

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
2. abc
3. 1
4. Flow Red
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

### Step 2 — Enter `abc` and press Enter

Console output

```text
Invalid number.

=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Step 3 — Enter `1` and press Enter

Console output

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Step 4 — Enter `Flow Red` and press Enter

Console output

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

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

- Report nonnumeric menu input and allow the user to enter a valid choice.
- The flow ends at Main. Keep CORE_DIR for the next flow.
