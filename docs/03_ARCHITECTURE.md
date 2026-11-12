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
- Controllers own navigation, authentication/role routing and operation orchestration.
- Views read/print Console values.
- Services validate ownership, account state, pricing, holds, payment and ticket rules.
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

SellerView presents the seven Use Case features. BookingController separately
orchestrates creation, Accept offline payment and Issue ticket. Internal Booking ID
lookup supports those operations. The account-holder and agreed walk-in branches
share booking/payment logic. Walk-in fanId is empty and the receipt identifies the order.

## Contracts

The canonical draw.io and five Mermaid files expand declared fields, explicit
public constructors and public methods. Private helpers remain implementation
details. Relationships express structure. Runtime flow is described above.
