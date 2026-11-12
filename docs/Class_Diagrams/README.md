# Class Diagram Guide

The canonical [ClassDiagram.drawio](../ClassDiagram.drawio) contains six pages.

| Page | Domain | Mermaid counterpart |
| --- | --- | --- |
| 00 | Use Cases and actor generalization | Read the Use Case page in draw.io. |
| 01 | Main, accounts, authentication and Guest browsing | [Main and authentication](01_Main_View_Auth.mmd) |
| 02 | Administration, venues, matches, pricing and accounts | [Stadium, Match and Admin](02_Stadium_Match_Admin.mmd) |
| 03 | Booking, payment, tickets, refunds, Gate and booking protection | [User, Booking, Payment and Ticket](03_User_Booking_Payment_Ticket.mmd) |
| 04 | CSV repositories, BaseEntity and common errors | [CSV Repository and BaseEntity](04_CSV_Repository_BaseEntity.mmd) |
| 05 | Domain models, inheritance and multiplicities | [Model relationships](05_Model_Relationships.mmd) |

The last inspection matched 97 production class/enum contracts to 192 expanded
class boxes and these five Mermaid files. Repeated classes contain the same
declared fields, explicit constructors and public methods as source.

## Reading the Notation

- Private attributes use `-field: Type`.
- Public constructors and methods use `+`, one member per line.
- Constructors show their class name and parameters. Methods show return types.
- Enums list their values. Inheritance and interface realization are explicit.
- DTOs are fully expanded within their domains.
- Arrows describe structural relationships between classes.
- Controllers coordinate runtime navigation through Views and Services.

Default booking protection appears on page 03. SynchronizedStrategy implements
BookingStrategy. StrategyFactory creates it and MatchSeatRepository serializes
seat commits in one JVM.

See [verification](../05_VERIFICATION_AND_DELIVERY.md) for recorded structural
checks. Full rendered wire routing requires visual review before export.

## Timed booking holds

Booking includes createdAt/expiresAt and the EXPIRED state. MatchSeat includes
holdBookingId and a structural hold-owner relationship. Default checkout commits
only its owned LOCKED seats. Expiry releases those holds. BookingService is fully
expanded on pages 01 and 02 where browsing/seat administration use its expiry
operation. The Seller menu exposes the seven features shown on page 00.
