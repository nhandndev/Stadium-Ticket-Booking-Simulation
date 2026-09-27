# Stadium Ticket Booking Simulation — 6 Class Diagrams with Service + DTO

## Architecture

The revised diagrams use this flow:

`VIEW -> CONTROLLER -> SERVICE -> REPOSITORY -> MODEL`

DTO objects are used at the View/Controller/Service boundary. Repositories still read/write MODEL entities only.

## Class format

- Attribute: `-fieldName: Type`
- Protected attribute: `#fieldName: Type`
- Constructor: `+ClassName(parameter: Type)` with **no return type**
- Method: `+method(parameter: Type): ReturnType`

## Design rules

- Controller holds Service, not Repository.
- Service contains validation/orchestration/business use-case logic and calls Repository.
- Repository owns CSV access and atomic update operations.
- Model contains entities, enums and core state rules.
- DTO has only data needed to receive input or return output; no CSV I/O and no business logic.
- No mapper layer is introduced; Service can create DTO objects directly with simple Java constructors.
- The six-file split is preserved so each diagram stays readable.

## Files

1. `01_Main_View_Auth` — navigation, login/register/browse + Auth/Fan/Browse services and DTOs.
2. `02_Stadium_Match_Admin` — Admin management + Stadium/Match/Account/Admin/Refund services and DTOs.
3. `03_User_Booking_Payment_Ticket` — booking, payment, seller, gate, refund + services and request/response DTOs.
4. `04_Simulation` — simulator + SimulationService + simulation DTOs + strategies.
5. `05_CSV_Repository_BaseEntity` — generic CSV repository, BaseEntity, DataGenerator, exceptions.
6. `06_Model_Relationships` — business entity relationships and multiplicity only; DTOs are intentionally omitted.

## Important consistency choice

`MatchSeat` is the per-match seat inventory entity and now consistently receives an `id` in its constructor when it is treated as a `BaseEntity`. Its `version` is used for optimistic locking.


## Audit fixes before full implementation

This revision fixes the four pre-code audit groups:

- DTOs expose getters for every private field that must be read by Controller/Service/View.
- Model entities expose getters needed by Service to map entity data into response DTOs.
- `MainView` now holds Seller/Gate/Admin/Simulator views; `BookingView` holds `FanController` and `TicketController`; ticket detail retrieval is explicit.
- Admin contracts now explicitly cover Section details, Seat details, Ticket Price details, Ticket Sales status, Fan details/filtering, and Staff details/filtering.
- `DataGenerator` uses the same `outputDirectory: Path` constructor contract in diagrams 02 and 05.
- Repeated `MatchSeatRepository`, `TicketRepository`, and `RefundRepository` definitions are normalized to the file-05 canonical definitions.

The architecture remains `View -> Controller -> Service -> Repository -> Model`, with DTOs used only as boundary objects.

## Final code-ready fixes

- `MainView` now owns a `BookingView` and receives it through the constructor so the Fan menu has an explicit navigation path to booking/payment/history.
- CSV serialization is centralized in Repository classes: `CsvRepository<T>` defines both `#parseLine(line: String): T` and `#formatLine(entity: T): String`. Concrete repositories override both. `BaseEntity` no longer owns CSV formatting methods, preventing two competing serialization mechanisms.
- `MatchSeatRepository` and `TicketPriceRepository` also define `parseLine/formatLine` because they persist CSV with specialized update rules.
- `SimulationController.configureSimulation()` and `SimulationService.configureSimulation()` now return `SimulationResponseDto`, which represents a configured simulation before execution. `SimulationResultDto` is only returned after a run or comparison.
- Class formatting remains: private fields with `-`, public constructors/methods with `+`, protected reusable members with `#`; constructors have no return type.
