# Register Fan Account

- Register a Fan and reject duplicate email addresses. New accounts have the FAN role and ACTIVE status.
- Code path `MainController.handleFanRegistration → FanController.register → FanService.register → FanRepository / StaffRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-009"></a>

## Flow 009 — Register Fan Account

- Start at Main in CORE_DIR after [Flow 008](../01_Guest/01_Search_Matches.md#flow-008).

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
1. 4
2. Flow Demo Fan
3. flow.fan@example.com
4. FlowPass123
5. 0901234567
```

### Step 1 — Enter `4` and press Enter

Console output

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Step 2 — Enter `Flow Demo Fan` and press Enter

Console output

```text
Email:
```

### Step 3 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password (at least 8 characters):
```

### Step 4 — Enter `FlowPass123` and press Enter

Console output

```text
Phone:
```

### Step 5 — Enter `0901234567` and press Enter

Console output

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE
Account created. Select Login As Fan to continue.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Register a Fan and reject duplicate email addresses. New accounts have the FAN role and ACTIVE status.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-010"></a>

## Flow 010 — Reject duplicate registration email

- Start at Main in CORE_DIR after [Flow 009](../02_Authentication_User/01_Register_Fan_Account.md#flow-009).

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
1. 4
2. Duplicate Fan
3. flow.fan@example.com
4. FlowPass123
5. 0901234567
```

### Step 1 — Enter `4` and press Enter

Console output

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Step 2 — Enter `Duplicate Fan` and press Enter

Console output

```text
Email:
```

### Step 3 — Enter `flow.fan@example.com` and press Enter

Console output

```text
Password (at least 8 characters):
```

### Step 4 — Enter `FlowPass123` and press Enter

Console output

```text
Phone:
```

### Step 5 — Enter `0901234567` and press Enter

Console output

```text
Email already exists

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Points to explain

- Register a Fan and reject duplicate email addresses. New accounts have the FAN role and ACTIVE status.
- The flow ends at Main. Keep CORE_DIR for the next flow.
