# Stadium Ticket Booking Simulation — Class Diagrams with Service + DTO

## Controller-first redraw

`ClassDiagram.drawio` is the canonical seven-page design for the console application, mirrored at `../ClassDiagram.drawio`. Controller starts each console workflow, asks View for input/output, and calls Services for business operations. Stages 01-09 are implemented; Simulation remains Stage 10 work. [Stage 08-09 changes](CHANGES_STAGE_08_09.md) list exact visible member changes.

Page 01 gives `MainController` a `SeatMapView` dependency because Guest can view seat availability before logging in. Page 03 also allows `BookingController` to use the same View type for Fan/Seller booking. The current Java constructor contains only dependencies already implemented; later-stage controllers will be added as those modules are built.

Page 05 includes the implemented `AppException` class and `ErrorCode` enum in a Common/Error group. `AppException` extends the JDK `RuntimeException`; the JDK class is not expanded as a project class in the diagram.

The [Console View Review](../15_CONSOLE_VIEW_REVIEW.md) documents later usability fixes,
50 passing CLI smoke scenarios and exact Page/Class member changes. Both draw.io copies
include those changes, while retaining the original colors, columns and relationship style.

Page 02 shows the no-argument constructors and setters already present in the implemented Stadium request/response DTOs. These are Java Console DTO conveniences, not new business features.

The `.mmd` files below remain copies of the earlier View-first design. Use the new draw.io file for the controller-first presentation, not those copies.

> The `.mmd` files are older View-first working copies. Use the visible canvas of `ClassDiagram.drawio` for implementation and presentation. Hidden Mermaid metadata must not override the visible tables.

## Architecture

The revised diagrams use this flow:

Controller-first target: `CONTROLLER -> VIEW` for console I/O and `CONTROLLER -> SERVICE -> REPOSITORY -> MODEL` for business operations.

DTO objects are used at the View/Controller/Service boundary. Repositories still read/write MODEL entities only.

## Class format

- Attribute: `-fieldName: Type`
- Attributes on the final visible diagrams are private (`-`); `+` is reserved for constructors and public methods.
- Constructor: `+ClassName(parameter: Type)` with **no return type**
- Method: `+method(parameter: Type): ReturnType`

## Design rules

- Controllers coordinate Services and console Views. The visible design also gives TicketController a BookingRepository for ownership checks and AdminController an AuditLogRepository for refund-review records.
- Service contains validation/orchestration/business use-case logic and calls Repository.
- Repository owns CSV access and atomic update operations.
- Model contains entities, enums and core state rules.
- DTO has only data needed to receive input or return output; no CSV I/O and no business logic.
- No mapper layer is introduced; Service can create DTO objects directly with simple Java constructors.
- The six-file split is preserved here as a working copy; the final draw.io has one Use Case page and six Class Diagram pages. DTOs stay on their relevant domain pages, with no separate catalog.

## Files

1. `01_Main_View_Auth` — navigation, login/register/browse + Auth/Fan/Browse services and DTOs.
2. `02_Stadium_Match_Admin` — Admin management + Stadium/Match/Account/Admin/Refund services and DTOs.
3. `03_User_Booking_Payment_Ticket` — booking, payment, seller, gate, refund + services and request/response DTOs.
4. `04_Simulation` — simulator + SimulationService + simulation DTOs + strategies.
5. `05_CSV_Repository_BaseEntity` — generic CSV repository, BaseEntity, DataGenerator, exceptions.
6. `06_Model_Relationships` — business entity relationships and multiplicity only; DTOs are intentionally omitted.

## Important consistency choice

`MatchSeat` is the per-match seat inventory entity and now consistently receives an `id` in its constructor when it is treated as a `BaseEntity`. Its `version` is used for optimistic locking.


## Contracts retained

The controller-first file retains these approved domain contracts:

- DTOs expose getters for every private field that must be read by Controller/Service/View.
- Model entities expose getters needed by Service to map entity data into response DTOs.
- `MainController` coordinates role controllers and console Views. Domain controllers own their Views; View classes do not hold controllers. Ticket detail retrieval remains explicit.
- Admin contracts now explicitly cover Section details, Seat details, Ticket Price details, Ticket Sales status, Fan details/filtering, and Staff details/filtering.
- `DataGenerator` remains a planned dataset utility; it is not currently implemented or exposed by Admin menus.
- Repeated `MatchSeatRepository`, `TicketRepository`, and `RefundRepository` definitions are normalized to the file-05 canonical definitions.

The older `.mmd` copies still show `View -> Controller -> Service -> Repository -> Model`. The controller-first draw.io file above supersedes that direction for the target design.

In page 04, `SimulationService.runSimulation()` creates and closes its executor locally. The executor is not a class field, so neither the service nor controller exposes `shutdown()`.

## Implementation details

- `MainController` routes the Fan menu to `BookingController`, which owns `BookingView` for booking/payment/history. The current source adds these dependencies only when their stages are implemented.
- CSV serialization is centralized in Repository classes: `CsvRepository<T>` defines both `+parseLine(line: String): T` and `+formatLine(entity: T): String`. Concrete repositories override both as public methods. `BaseEntity` no longer owns CSV formatting methods, preventing two competing serialization mechanisms.
- `MatchSeatRepository` and `TicketPriceRepository` also define `parseLine/formatLine` because they persist CSV with specialized update rules.
- `SimulationController.configureSimulation()` and `SimulationService.configureSimulation()` now return `SimulationResponseDto`, which represents a configured simulation before execution. `SimulationResultDto` is only returned after a run or comparison.
- Class formatting uses only private (`-`) and public (`+`) members; constructors have no return type. The diagram does not define a CSV `header` field.

The implemented contracts were compared with 196 visible class boxes using Java
syntax-tree inspection. The four `MatchSeatRepository.update*` strategy methods
remain planned for Stage 10; book/release group methods are implemented now.
The original class colors, multiple columns and relationship style are retained,
with longer rows wrapped and boxes expanded as needed.
