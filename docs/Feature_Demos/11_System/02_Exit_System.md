# Exit System

- Exit the application from Main and close its Scanner.
- Code path `MainController.start → MainView.displayMessage → Main.main`. Main closes its Scanner on exit.
- Press Enter after each input. A blank Enter submits an empty line.

[Data setup](../00_SETUP.md) · [Complete demo order](../01_DEMO_ORDER.md) · [Feature index](../README.md)

## Demo prerequisites

- Use CORE_DIR, COUNTER_DEMO_DIR or EXPIRY_DEMO_DIR as specified for each flow. The setup guide creates these copies. Follow the order so IDs and state match.
- Earlier venue, seat, Match, purchase and refund flows prepare records reused by later flows.
- Open a feature file for reference. Flow numbers preserve the global execution order.

<a id="flow-142"></a>

## Flow 142 — Exit System

- Start at Main in CORE_DIR after [Flow 141](../05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md#flow-141).

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
1. 0
```

### Step 1 — Enter `0` and press Enter

Console output

```text
Goodbye!
```

### Points to explain

- Exit the application from Main and close its Scanner.
