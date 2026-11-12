# Logout

- End the authenticated session and return to Main.
- Code path `MainController.routeAuthenticatedUser → AuthController.logout → AuthService.logout`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-014"></a>

## Flow 014 — Log out as Fan

- Start at Main in CORE_DIR after [Flow 013](../02_Authentication_User/05_Update_Profile.md#flow-013).

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
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 0
```

### Step 1 — Enter `2` and press Enter

Console output

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Step 2 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password:
```

### Step 3 — Enter `FlowPass123` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Step 4 — Enter `0` and press Enter

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

- End the authenticated session and return to Main.
- The flow ends at Main. Keep CORE_DIR for the next flow.
