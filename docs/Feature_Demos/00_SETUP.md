# Demo Setup

## Current Counter Menu — Updated 2026-11-14

The current menu is in [Menu map](02_MENU_MAP.md). Seller > Ticket sales contains
1. Create booking for Fan / walk-in customer and 2. Pay for an existing booking.
Cash confirmation automatically issues tickets. The 001–152 flow captures below
or in linked guides record the earlier menu. Use the current Seller walkthroughs
at the top of each Seller guide for the updated input sequence.


Recorded on 2026-11-13. The 87 feature guides contain 148 flows and 1546
input/output pairs captured from that code version. Current counter walkthroughs appear separately.

## Runtime Dataset for the 100-Match Layout

The main data directory now contains 100 Matches, split 50/50 between the two
Stadiums, with 100 physical Seats per Stadium and 100 MatchSeat rows per Match.
Rows A–J contain ten seats each, grouped into VIP/Premium/Standard Sections.
All Matches have prices and prepared inventory, including CLOSED Matches.
Copy data to a temporary directory to use this layout in a demo.

The CORE_DIR instructions below use the smaller historical fixture so the
numbered captures retain their documented IDs. Current runtime data and those
recorded fixtures have their own preparation instructions.

## Compile

Run from the project root with JDK 17 or later.

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
```

## CORE_DIR for Flows 001–142

Use the supplied small fixture so initial IDs and state match the captures.
Each flow starts at Main. Creation flows prepare records required by later flows.
Retired identifiers are 091 and 133–135.

```bash
CORE_DIR=$(mktemp -d /tmp/stadium-core-demo.XXXXXX)
cp docs/Feature_Demos/fixtures/core/*.csv "$CORE_DIR/"
java -cp out app.Main "$CORE_DIR"
```

Keep CORE_DIR throughout the sequence. The recorded zero inputs return to Main.
When restarting the application, reuse the directory to retain created records.

- Flow Fan has ID 6, email flow.fan@example.com and password FlowPass123.
- Flow Stadium has ID 3, Flow Section ID 6, Seats 21–28 and Flow Match ID 4.
- The counter customer created in Flow 083 has Fan ID 7. Flow 085 creates Booking 7.
- Match 4 in CORE_DIR is Flow Red vs Flow Blue.
- Some fixture accounts are INACTIVE to demonstrate access rejection.
- Before presenting on another date, check the fixture Match dates and date filters.

New bookings hold seats for at most 30 minutes, ending earlier at kick-off.
After a long demo interruption, prepare a fresh copy and repeat the prerequisites
instead of using an elapsed booking. The booking displays its payment deadline.
Fan can pay online immediately or later, and Seller can accept offline payment
within the same window.

### Flow 141 with mock online payment failure

Exit from Main and restart the same CORE_DIR with the following flag.

```bash
java -cp out app.Main "$CORE_DIR" --payment-fails
```

## COUNTER_DEMO_DIR for Flows 143–150

Copy the current full dataset for the walk-in purchase demonstration.
Run the flows in order and retain this directory for the whole group.

```bash
COUNTER_DEMO_DIR=$(mktemp -d /tmp/stadium-counter-demo.XXXXXX)
cp data/*.csv "$COUNTER_DEMO_DIR/"
java -cp out app.Main "$COUNTER_DEMO_DIR"
```

Match 4 in this copy is Vietnam vs China. The recorded SEAT IDs were AVAILABLE
when captured. If the source has since sold those seats, choose available seats
from the displayed map. Use the Booking ID printed by the application in the
subsequent payment and issue flows. Walk-ins keep their booking and ticket codes.

- Flow 143 creates a booking holding four seats.
- Flow 144 accepts offline payment for that booking.
- Flow 145 creates another walk-in booking for later payment.
- Flow 146 accepts offline payment for the second booking after a restart.
- Historical Flow 147 used the former separate Issue ticket operation. Current cash checkout issues tickets automatically.
- Flows 148–150 check INACTIVE rejection and specific invalid Seat ID reasons.

## EXPIRY_DEMO_DIR for Flows 151–152

This test copy contains expired Booking 3 holding Seat 2 of Match 1.
Fixed timestamps allow immediate expiry verification when the application starts.

```bash
EXPIRY_DEMO_DIR=$(mktemp -d /tmp/stadium-expiry-demo.XXXXXX)
cp docs/Feature_Demos/fixtures/core/*.csv "$EXPIRY_DEMO_DIR/"
printf '%s\n' '3,2,1,2,500000,PENDING,2020-01-01T10:00,2020-01-01T10:30' >> "$EXPIRY_DEMO_DIR/bookings.csv"
awk -F, 'BEGIN {OFS=","} {if ($2==1 && $3==2) {$4="LOCKED"; $5=$5+1; $6=3}; print}' "$EXPIRY_DEMO_DIR/match_seats.csv" > "$EXPIRY_DEMO_DIR/match_seats.tmp"
mv "$EXPIRY_DEMO_DIR/match_seats.tmp" "$EXPIRY_DEMO_DIR/match_seats.csv"
java -cp out app.Main "$EXPIRY_DEMO_DIR"
```

- Flow 151 logs in as Fan binh.tran@gmail.com / 12345678, views EXPIRED in the
  booking history and attempts Pay pending booking.
- Flow 152 logs in as Seller and enters Booking ID 3 at Accept offline payment.
- The elapsed booking is rejected before payment is recorded, and its held seats are released.

## Current Seller Workflow

The Seller counter menu has five groups.

1. Search Fan.
2. Create Fan.
3. Ticket sales.
   1. Create booking for Fan / walk-in customer.
   2. Pay for an existing booking.
   0. Back to Seller menu.
4. View seat map and prices.
5. View customer booking history.
0. Back to dashboard.

Creating a booking continues to the cash-receipt prompt. Confirming RECEIVED
records offline payment and immediately issues/displays tickets. Cancelling the
cash prompt keeps PENDING and displays the Booking ID and persisted deadline.
Ticket sales > Pay for an existing booking accepts that ID, including an order
created by a Fan online, and issues tickets after successful cash payment.
An already-CONFIRMED cash booking can resume ticket issuance through the same
entry without collecting cash again. The existing payment and issue methods remain separate code operations.
