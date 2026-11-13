# Architecture

```text
Main -> MainController
          -> domain Controller -> View for Console input/display
                               -> Service for business operations
                                    -> Repository -> CSV
                                    -> Model
                    Request/Response DTOs carry boundary data
```

- Main wires shared repository/service instances.
- Controllers own navigation, authentication/role routing, boundary ownership checks and operation orchestration.
- Views read/print Console values.
- Services apply account-state, pricing, hold, payment, refund and ticket rules, including ownership where the operation requires it.
- Repositories parse/format CSV and perform data access.
- Models hold state and enforce transitions. Enums use separate enums packages.

## Booking Protection

Booking stores createdAt/expiresAt. MatchSeat stores LOCKED and holdBookingId
while reserved. BookingService creates the quoted booking and acquires the
whole group of holds under MatchSeatRepository.class synchronization.

Payment passes bookingId to the existing strategy so it can commit only its own
held seats. Expiry/cancellation/failure releases only holds matching that owner.
Expired/cancelled cleanup is safe to repeat after an interrupted release.

BrowseService references BookingService to expire holds before returning seat
availability. StadiumService uses it before administrative deactivation checks.
Main supplies these dependencies. The complete used BookingService class is
expanded in the corresponding diagram domains.

## Seller Scope

SellerView presents Search Fan, Create Fan, Ticket sales, View seat map and prices,
and View customer booking history. The new public showTicketSalesMenu method
presents the create-new and pay-existing branches.

BookingController.runTicketSalesMenu is private navigation logic. Creation calls
createBookingForCustomer, then collectOfflinePayment. Successful cash checkout
calls the existing acceptOfflinePayment and issueTickets methods. Cancelling
cash entry retains PENDING and its deadline. Existing bookings use the same flow
by Booking ID. Confirmed cash checkout can safely resume ticket issue.

The existing domain classes, public booking/payment/issue methods and relationships
remain. SellerView adds showTicketSalesMenu(): int on Class Diagram page 03 and
its Mermaid counterpart. Walk-in fanId stays empty.

## Contracts

The canonical draw.io and five Mermaid files expand declared fields, explicit
public constructors and public methods. Private helpers remain implementation
details. Relationships express structure. Runtime flow is described above.

## Ticket Preview and Audit Presentation

- `BookingController.getTicketsForBooking` checks the same ownership/role rules as
  Booking Details, then calls `BookingService.getTicketsForBooking`. Existing
  BookingView and SellerView ticket-list methods render the result.
- `TicketController.validateTicket(ticketCode)` obtains a preview. Confirmation calls
  `checkInTicket(ticketCode)`, which revalidates while holding the ticket lock.
- TicketService receives FanRepository through its constructor to resolve customer
  name and phone into TicketResponseDto. BookingService and FanService populate
  the same DTO fields. Walk-in values are `None`.
- AdminController obtains audit rows and Staff account DTOs through its existing
  collaborators. AdminManagementView matches the stored Staff ID to a name/role
  for display. CSV schemas remain compatible. The displayed identity reflects the
  current account record.
