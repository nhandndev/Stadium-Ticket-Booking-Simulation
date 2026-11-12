# Use Case Specification

The visible page 00 of ClassDiagram.drawio is the official actor/feature overview.

## Actors and Shared Behavior

- Guest searches/filters Matches, reads details/seat availability, registers and opens login.
- User represents shared profile/update/logout behavior after authentication.
- Fan books and accesses its own bookings, tickets and refund requests.
- Seller serves the counter through the seven features below.
- Gate Staff validates and checks in tickets once.
- Administrator manages venues, Matches, prices, sales, accounts/roles,
  refunds, summary and audit.
- Payment Service supplies mock online payment outcomes.

Actor generalization expresses shared capability. Controllers also enforce stored
role and account status. Menu navigation and validation are part of these use-case flows.

## Seller Use Cases

1. Search Fan.
2. Create Fan.
3. Create booking for Fan.
4. Accept offline payment.
5. Issue ticket.
6. View seat map and prices.
7. View customer booking history.

Create booking preserves the agreed account-holder/walk-in branches inside the
same use case. Existing accounts must be ACTIVE immediately after selection.
Create Fan remains its own explicit feature.

## Booking and Payment Scenarios

### Fan

1. Select a future OPEN Match and one to four available seats.
2. Create PENDING with the quoted amount and persisted payment deadline.
3. The system holds those seats exclusively for the booking.
4. Pay online immediately, return to Pay pending booking later or give the
   Booking ID to Seller for offline payment before the same deadline.
5. Successful payment confirms the order. Online checkout issues tickets.
6. View booking/ticket history and present the ticket at the Gate.

### Seller

1. Search/select an ACTIVE Fan or choose the walk-in branch when creating booking.
2. Select Match and seats, then create PENDING with its deadline.
3. At Accept offline payment, enter Booking ID and confirm cash received.
4. Successful offline payment records CONFIRMED.
5. At Issue ticket, enter the paid Booking ID to issue its tickets.

### Expiry and Cancellation

The deadline is the earlier of creation plus 30 minutes and kick-off. At that
instant, payment is rejected, PENDING becomes EXPIRED and owned holds are released.
Cancellation or failed payment also releases the owning booking's holds.
Stored timestamps prevent a restart from granting another window.

### History, Gate and Refunds

Booking history represents orders and status. Ticket history represents issued
seats and check-in. Gate validates Match and ticket identity before one-time entry.
Eligible paid Fan bookings may request refund and Administrator reviews them.
Approval cancels the sale/tickets and releases BOOKED inventory.

## Full Feature Coverage

[Use Case traceability](06_USE_CASE_TRACEABILITY.md) maps every visible feature
node to code and a captured feature guide. The [feature index](Feature_Demos/README.md)
groups all delivered operations by actor/domain. Hold expiry is a business rule of
booking, payment and seat availability, and is explained in the diagram note.
