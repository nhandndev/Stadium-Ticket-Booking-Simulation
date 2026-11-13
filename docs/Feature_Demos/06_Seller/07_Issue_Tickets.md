# Issue Tickets

## Current Seller Menu — 2026-11-14

Seller > 3. Ticket sales contains 1. Create booking for Fan / walk-in customer,
2. Pay for an existing booking and 0. Back. Successful cash checkout automatically
issues/displays tickets. Payment and issue retain separate methods in the code.
The walkthroughs here use an isolated copy of the core fixture with future Match
dates. Inputs are listed in order. A blank line means Enter.

Ticket issue is now an automatic step. Use Pay for an existing booking to resume
issue after a successful cash payment, including a repeated request.

### Retry an already-paid cash booking without collecting again

Start at the Ticket sales menu.

Inputs

```text
2
4
```

Result captured from the current code

```text
Booking #4 | Walk-in customer Walk-in customer | Vietnam vs Thailand | Seats [Stand A VIP A4] | Total 500000 VND | Status CONFIRMED
Keep Booking ID 4 for payment or ticket issuance.
Booking is already confirmed. Checking its existing cash payment.
Payment #4 | Booking #4 | 500000 VND | Method OFFLINE_CASH | Status SUCCESS
Ticket #3 | Code T-4-4 | Vietnam vs Thailand | Stand A VIP A4 | Status ISSUED

=== SELLER > TICKET SALES ===
1. Create booking for Fan / walk-in customer
2. Pay for an existing booking
0. Back to Seller menu
Choose:
```

## Historical Numbered Flows

The numbered flows below were captured on 2026-11-13 before the menu merge.
Their Console blocks retain that historical menu. Use the current walkthroughs
above and the [current menu map](../02_MENU_MAP.md) for the new navigation.


- Issue tickets for a CONFIRMED paid booking. Repeated issue preserves the existing ticket codes and count.
- Code path `MainController → BookingController.issueTickets → BookingService.issueTickets → TicketRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-089"></a>

## Flow 089 — Issue Tickets for Paid Booking

- Start at Main in CORE_DIR after [Flow 088](../06_Seller/06_Accept_Cash_Payment.md#flow-088).

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
5. 5
6. 7
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

### Step 5 — Enter `5` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `7` and press Enter

Console output

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status CONFIRMED
Keep Booking ID 7 for payment or ticket issuance.
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

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

- Issue tickets for a CONFIRMED paid booking. Repeated issue preserves the existing ticket codes and count.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-090"></a>

## Flow 090 — Repeat ticket issue without duplicates

- Start at Main in CORE_DIR after [Flow 089](../06_Seller/07_Issue_Tickets.md#flow-089).

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
5. 5
6. 7
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

### Step 5 — Enter `5` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `7` and press Enter

Console output

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status CONFIRMED
Keep Booking ID 7 for payment or ticket issuance.
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

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

- Issue tickets for a CONFIRMED paid booking. Repeated issue preserves the existing ticket codes and count.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-147"></a>

## Flow 147 — Issue paid walk-in tickets as a separate Seller action

- Start at Main in COUNTER_DEMO_DIR. Follow the setup guide order.

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
5. 5
6. 8
7. 5
8. 9
9. 0
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

### Step 5 — Enter `5` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `8` and press Enter

Console output

```text
Booking #8 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A1, Stand A VIP A2, Stand A VIP A3, Stand A VIP A4] | Total 400000 VND | Status CONFIRMED
Keep Booking ID 8 for payment or ticket issuance.
Ticket #5 | Code T-8-1 | Vietnam vs China | Stand A VIP A1 | Status ISSUED
Ticket #6 | Code T-8-2 | Vietnam vs China | Stand A VIP A2 | Status ISSUED
Ticket #7 | Code T-8-3 | Vietnam vs China | Stand A VIP A3 | Status ISSUED
Ticket #8 | Code T-8-4 | Vietnam vs China | Stand A VIP A4 | Status ISSUED

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

### Step 7 — Enter `5` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 8 — Enter `9` and press Enter

Console output

```text
Booking #9 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A5] | Total 100000 VND | Status CONFIRMED
Keep Booking ID 9 for payment or ticket issuance.
Ticket #9 | Code T-9-5 | Vietnam vs China | Stand A VIP A5 | Status ISSUED

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

### Step 9 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 10 — Enter `0` and press Enter

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

### Step 11 — Enter `0` and press Enter

Console output

```text
Goodbye!
```

### Points to explain

- Issue tickets for a CONFIRMED paid booking. Repeated issue preserves the existing ticket codes and count.
- Writes occur in the demo copy. Keep the designated directory for subsequent flows.
