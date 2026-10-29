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
| 07 | Administrator CRUD, sales, roles, summary/audit | 02 | Implemented; booking history guards added in 08 |
| 08 | Fan booking, online payment, tickets, cancellation/refund | 03 | Implemented; console and CSV restart verified |
| 09 | Seller counter flow and Gate check-in | 03 | Implemented; console and duplicate check-in verified |
| 10 | Isolated concurrency simulation, datasets and reporting | 04 | Deferred |
| 11 | Final cross-role integration, report and delivery | 00-06 | Not complete |

## Work sequence

1. Stages 01-07 provide the Console, account, stadium, match and repository foundation.
2. Stage 08 adds Model/enum -> Repository -> DTO -> synchronized seat core ->
   Booking/Ticket/Refund Service -> Controller -> View -> Main wiring.
3. Stage 09 reuses those services for Seller and Gate menus. Seller cash
   collection and ticket issuance are separate actions.
4. Next: add Stage 10 simulation on a copy of seat data. Configure and Run are
   separate menu actions; compare four mechanisms on equivalent starting data.
5. In Stage 11, execute one full Admin -> Guest -> Fan -> Seller -> Gate ->
   Simulator demonstration, reopen CSV and reconcile code with the diagrams.

## Stage 08-09 Checklist

- [x] Retain a Details/Seat Availability submenu after Guest search and filter.
- [x] Show stadium/section names and physical seat labels beside selectable IDs.
- [x] Create a pending booking for 1-4 unique seats and capture its price total.
- [x] Recheck and commit all seats during payment; no seat hold/expiry is implemented.
- [x] Record online/cash payments and booking transactions, and issue tickets without duplicates.
- [x] Show own booking details, booking history, ticket details and ticket history.
- [x] Cancel an unpaid pending booking; submit/view own paid refund requests.
- [x] Let Admin approve/reject refunds; approval cancels tickets and releases seats.
- [x] Let Seller find/create a Fan, book for that Fan, confirm received cash and issue tickets.
- [x] Let Gate Staff validate the match/ticket and persist a one-time check-in.
- [x] Check ownership/roles and protect referenced account, match and seat data.
- [x] Compile 97 source files and compare their public contracts with 196 visible UML boxes.

See [the exact UML changes](Class_Diagrams/CHANGES_STAGE_08_09.md).
`SimulationController`, Simulator Views and three remaining strategies have not
been implemented. Only the shared synchronized strategy is currently used.

Each feature should end with a clean Java compilation and a focused console
check. Commit one working feature at a time. Stage 11 also includes the report,
AI usage evidence where required, and a ZIP without local-only planning files.
