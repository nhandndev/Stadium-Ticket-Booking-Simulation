# Customer Booking History

- View the selected Fan account booking history, including status and payment deadlines.
- Code path `MainController → BookingController.getBookingsForFan → BookingService.getMyBookings → BookingRepository`.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-086"></a>

## Flow 086 — Customer Booking History

- Start at Main in CORE_DIR after [Flow 085](../06_Seller/04_Create_Booking_for_Customer.md#flow-085).

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
5. 7
6. flow.counter
7. 7
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

### Step 5 — Enter `7` and press Enter

Console output

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Step 6 — Enter `flow.counter` and press Enter

Console output

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Step 7 — Enter `7` and press Enter

Console output

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | PENDING
  Payment deadline: 2026-11-13T00:17:35.838320

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

- View the selected Fan account booking history, including status and payment deadlines.
- The flow ends at Main. Keep CORE_DIR for the next flow.
