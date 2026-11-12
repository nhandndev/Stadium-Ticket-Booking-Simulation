# Use Case Specification

Current baseline follows the visible page 00 in ClassDiagram.drawio.

## Actors and responsibilities

- Guest searches/filters matches, reads match details and seat availability,
  registers a Fan and opens login.
- User represents shared authenticated profile/update/logout behavior.
- Fan creates and pays its own bookings, reads booking/ticket history, cancels
  unpaid bookings and submits refund requests.
- Seller serves customers, creates customer accounts/bookings, records cash
  and issues tickets.
- Gate Staff validates ticket codes and checks eligible tickets in once.
- Administrator manages venues, matches, prices, sales, accounts, roles, refunds,
  summary and audit records.
- Payment Service processes the local mock online payment outcome.

Fan and Staff-role actors share User capabilities through the diagram's
generalization relationships. Controller routing also checks stored account role.

## Ordinary booking scenario

1. An authenticated Fan selects an OPEN future Match and one to four seats.
2. BookingService validates Fan, Match, seats and pricing and stores PENDING.
3. Fan confirms payment for the quoted amount.
4. Default synchronized checkout rechecks availability and commits all seats.
5. A successful payment confirms the booking and creates eligible tickets.
6. A competing checkout receives the seat conflict and its booking is cancelled.
7. Fan reads its booking and ticket histories.

Seller follows the customer-booking path with cash confirmation and ticket issue.
Gate validates/checks in issued tickets. Administrator reviews eligible refunds
and releases seats when approval cancels the sale.

## Detailed Feature Inventory

All 82 visible Use Case nodes are mapped to their feature walkthroughs in
[Use Case traceability](06_USE_CASE_TRACEABILITY.md). Each linked guide describes
the purpose, preconditions, Console inputs, outcomes and exercised rejection
or cancellation cases. The [feature index](Demo_Tung_Feature/README.md) groups
these guides by actor and domain.
