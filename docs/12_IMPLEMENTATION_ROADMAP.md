# Implementation Roadmap

The canonical Use Case and class design is
[ClassDiagram.drawio](Class_Diagrams/ClassDiagram.drawio),
pages 00-06. This is one plain-Java console application with CSV persistence.
The completed code lives in `src/`; diagram classes for later stages are a
target, not proof that those features already run.

The console dependency direction is:

```text
Main -> MainController -> View (read input / display output)
                       -> domain Controller -> Service -> Repository -> CSV
```

Models hold state and simple domain behavior. DTOs carry request/response
values. No View reads CSV or owns a Controller. `Main` wires dependencies and
does not implement a business use case.

| Stage | Scope | UML page | Status in `src/` |
| --- | --- | --- | --- |
| 01 | Console/Common foundation | 01, 05 | Implemented & Verified |
| 02 | Stadium/Section/Seat model and DTO | 02, 06 | Implemented & Verified |
| 03 | CSV base and stadium repositories | 05 | Implemented & Verified |
| 04 | Match, MatchSeat, pricing and repositories | 02, 05, 06 | Implemented & Verified |
| 05 | Guest browse and controller-first menu | 01, 03 | Implemented & Verified |
| 06 | User/Fan/Staff authentication & profile | 01, 06 | Implemented & Verified |
| 07 | Administrator CRUD, sales, roles, DataGenerator | 02 | Implemented & Verified (Strict UML) |
| 08 | Fan booking, online payment, tickets, refund | 03 | Planned (~20%) |
| 09 | Seller counter flow and Gate check-in | 03 | Planned (~8%) |
| 10 | Isolated concurrency simulation and reporting | 04 | Planned (~5%) |
| 11 | Cross-role integration, CSV restart, delivery | 00-06 | Planned (~2%) |

## Work sequence

1. Finish Stage 05 verification with seeded `matches.csv` and
   `match_seats.csv`; check search, filter, details and empty/invalid input.
2. Finish Stage 06 vertically: DTO -> Auth/Fan Service -> Controller ->
   MainView input/output -> MainController role routing. Do not create empty
   Admin/Booking/Simulation classes just to match the final constructor.
3. Build Stage 07 as four small features: Stadium/Section/Seat, Match/Pricing/
   Sales, Fan/Staff/Role, Summary/Audit. `AdminController` owns the admin View.
4. Build Stage 08 as booking inventory -> online checkout -> tickets/history
   -> refunds -> Fan menu. Keep payment separate from the simulator.
5. Reuse booking and ticket services for Stage 09 Seller and Gate flows.
6. Add Stage 10 simulation on a copy of seat data. Configure and Run are
   separate menu actions; compare four mechanisms on equivalent starting data.
7. In Stage 11, execute one full Admin -> Guest -> Fan -> Seller -> Gate ->
   Simulator demonstration, reopen CSV and reconcile code with the diagrams.

Each feature should end with a clean Java compilation and a focused console
check. Commit one working feature at a time. Stage 11 also includes the report,
AI usage evidence where required, and a ZIP without local-only planning files.
