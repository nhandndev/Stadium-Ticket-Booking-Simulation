# Accept Cash Payment

## Current Seller Menu — 2026-11-14

Seller > 3. Ticket sales contains 1. Create booking for Fan / walk-in customer,
2. Pay for an existing booking and 0. Back. Successful cash checkout automatically
issues/displays tickets. Payment and issue retain separate methods in the code.
The walkthroughs here use an isolated copy of the core fixture with future Match
dates. Inputs are listed in order. A blank line means Enter.

### Pay for an existing walk-in booking and issue tickets

Start at the Ticket sales menu.

Inputs

```text
2
4
RECEIVED
```

Result captured from the current code

```text
Payment #4 | Booking #4 | 500000 VND | Method OFFLINE_CASH | Status SUCCESS
Ticket #3 | Code T-4-4 | Vietnam vs Thailand | Stand A VIP A4 | Status ISSUED

=== SELLER > TICKET SALES ===
1. Create booking for Fan / walk-in customer
2. Pay for an existing booking
0. Back to Seller menu
Choose:
```

### Reject an unknown booking

Start at the Ticket sales menu.

Inputs

```text
2
999999
```

Result captured from the current code

```text
Booking not found

=== SELLER > TICKET SALES ===
1. Create booking for Fan / walk-in customer
2. Pay for an existing booking
0. Back to Seller menu
Choose:
```

### Reject an expired booking before cash collection

Start at the Ticket sales menu.

Inputs

```text
2
5
```

Result captured from the current code

```text
Booking #5 | Walk-in customer Walk-in customer | Vietnam vs Thailand | Seats [Stand A VIP A5] | Total 500000 VND | Status EXPIRED
Keep Booking ID 5 for payment or ticket issuance.
Booking has expired. Offline payment is no longer allowed. Create a new booking.

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


- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- Code path `MainController → BookingController.acceptOfflinePayment → BookingService.acceptOfflinePayment → PaymentRepository / MatchSeatRepository / BookingRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-087"></a>

## Flow 087 — Cancel offline cash receipt confirmation

- Start at Main in CORE_DIR after [Flow 086](../06_Seller/05_Customer_Booking_History.md#flow-086).

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
5. 4
6. 7
7. Enter
8. 0
9. 0
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `7` and press Enter

Console output

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Keep Booking ID 7 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:17:35.838320
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Step 7 — Submit a blank line

Console output

```text
No payment recorded. Keep Booking ID 7 to pay before 2026-11-13T00:17:35.838320.

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

### Step 8 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 9 — Enter `0` and press Enter

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

- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-088"></a>

## Flow 088 — Accept Cash Payment

- Start at Main in CORE_DIR after [Flow 087](../06_Seller/06_Accept_Cash_Payment.md#flow-087).

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
5. 4
6. 7
7. RECEIVED
8. 0
9. 0
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `7` and press Enter

Console output

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Keep Booking ID 7 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:17:35.838320
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Step 7 — Enter `RECEIVED` and press Enter

Console output

```text
Payment #6 | Booking #7 | 500000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Step 8 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 9 — Enter `0` and press Enter

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

- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- The flow ends at Main. Keep CORE_DIR for the next flow.

<a id="flow-144"></a>

## Flow 144 — Accept offline payment for the walk-in booking

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
5. 4
6. 8
7. RECEIVED
8. 0
9. 0
10. 0
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `8` and press Enter

Console output

```text
Booking #8 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A1, Stand A VIP A2, Stand A VIP A3, Stand A VIP A4] | Total 400000 VND | Status PENDING
Keep Booking ID 8 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:36.617484
Cash amount due: 400000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Step 7 — Enter `RECEIVED` and press Enter

Console output

```text
Payment #6 | Booking #8 | 400000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Step 8 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 9 — Enter `0` and press Enter

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

### Step 10 — Enter `0` and press Enter

Console output

```text
Goodbye!
```

### Points to explain

- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- Writes occur in the demo copy. Keep the designated directory for subsequent flows.

<a id="flow-146"></a>

## Flow 146 — Accept offline payment within the persisted deadline after restart

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
5. 4
6. 9
7. RECEIVED
8. 0
9. 0
10. 0
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `9` and press Enter

Console output

```text
Booking #9 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A5] | Total 100000 VND | Status PENDING
Keep Booking ID 9 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:37.643472
Cash amount due: 100000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Step 7 — Enter `RECEIVED` and press Enter

Console output

```text
Payment #7 | Booking #9 | 100000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Step 8 — Enter `0` and press Enter

Console output

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Step 9 — Enter `0` and press Enter

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

### Step 10 — Enter `0` and press Enter

Console output

```text
Goodbye!
```

### Points to explain

- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- Writes occur in the demo copy. Keep the designated directory for subsequent flows.

<a id="flow-152"></a>

## Flow 152 — Seller cannot accept offline payment for an expired booking

- Start at Main in EXPIRY_DEMO_DIR. Follow the setup guide order.

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
5. 4
6. 3
7. 0
8. 0
9. 0
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

### Step 5 — Enter `4` and press Enter

Console output

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Step 6 — Enter `3` and press Enter

Console output

```text
Booking #3 | Fan #2 Tran Thi Binh | Vietnam vs Thailand | Seats [Stand A VIP A2] | Total 500000 VND | Status EXPIRED
Keep Booking ID 3 for payment or ticket issuance.
Booking has expired. Offline payment is no longer allowed. Create a new booking.

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

### Step 9 — Enter `0` and press Enter

Console output

```text
Goodbye!
```

### Points to explain

- Confirm cash receipt with RECEIVED before the deadline. Successful cash checkout automatically issues tickets.
- Writes occur in the demo copy. Keep the designated directory for subsequent flows.
