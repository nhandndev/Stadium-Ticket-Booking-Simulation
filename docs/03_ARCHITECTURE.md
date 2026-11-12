# Layered Architecture

```text
Main -> MainController
          -> role/domain Controller -> View for input and display
                                    -> Service for business decisions
                                         -> Repository -> CSV
                                         -> Model
                   Request/Response DTOs carry boundary data
```

- Main constructs dependencies and starts MainController.
- Controller owns menu loops, session/role routing and operation orchestration.
- View prints Console output and reads values.
- Service validates domain rules, updates state and maps response DTOs.
- Repository parses/formats CSV and implements data access.
- Model holds domain state. Enums live in separate enums packages.
- Public APIs and explicit constructors appear in UML. Attributes are private.

The booking domain keeps BookingStrategy, StrategyFactory and SynchronizedStrategy
for the existing default checkout. MatchSeatRepository serializes seat commits
in one JVM. See [booking protection](04_BOOKING_CONCURRENCY.md).

The actual package/class inventory appears in the five
[Mermaid files](Class_Diagrams/README.md) and six-page draw.io.
