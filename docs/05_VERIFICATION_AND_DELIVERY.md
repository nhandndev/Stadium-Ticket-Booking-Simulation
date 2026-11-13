# Verification and Delivery

Console execution evidence was recorded on 2026-11-13 for the seven-feature
Seller scope and timed booking holds. The static Use Case/documentation review
below was completed on 2026-11-14.

## Current Evidence

- Compiled all 97 production class/enum contracts with Java 17.
- Replayed 138 core Console flows on an isolated reproducible fixture.
- Executed ten counter/expiry flows on separate copied fixtures.
- Refreshed all 87 feature guides with 148 actual flows and 1546 input/output pairs.
- Matched source fields, explicit public constructors and public methods to all
  192 expanded class boxes and five Mermaid files.
- At that review, mapped the then-current 82 visible Use Case nodes to implemented operations and feature guides.

## Use Case Review Before Counter Integration

- The overview now has 77 unique goals after duplicate-goal consolidation.
- Checked all 77 goals against Console entry points and corresponding code paths.
- Recorded direct actors and inherited User capabilities, plus menu entries,
  source operations and demo links for each goal.
- Shared Match list/details, Fan search/creation, seat maps and ticket history
  have single nodes with their participating actors.
- Seller links cover seat selection and Booking details inside counter flows.
- Checked Guest/Fan browsing, all Staff dashboards and nested Administrator menus.
- Corrected the Staff roles path to Accounts and roles > Staff accounts > 9.
- Preserved 87 feature guides, 148 captured flows and their recorded Console data.
- Checked node/association uniqueness, connected edge endpoints and full traceability.
- Confirmed all five class pages and production source remain unchanged by this review.

The 2026-11-14 review used source and document inspection. The Console execution
evidence above retains its original execution date.

## Accepted Diagram Design Before Counter Integration

On 2026-11-14, the student's redesigned ClassDiagram.drawio was installed as the
canonical docs/ClassDiagram.drawio. The installed file matches the supplied file
byte-for-byte. Its six pages preserve all 77 unique Use Case goals, actor links,
class relationships and multiplicities from the reviewed baseline. All 97 source
class/enum contracts match the 192 expanded class boxes. The 77 actor mappings
also match the traceability document. Acceptance checks compared XML structure,
relationships and source member contracts.

## Current Integrated Counter Checkout — 2026-11-14

- Seller has five top-level groups. Ticket sales has create-new and pay-existing choices.
- Creation can continue through cash receipt and ticket issue or defer payment with the same deadline.
- Pay for an existing booking accepts Fan-created or counter-created orders and issues tickets after successful cash payment.
- Confirmed cash checkout resumes issue using the stored payment and existing ticket codes.
- One public View method was added, SellerView.showTicketSalesMenu(): int. The existing booking/payment/issue methods retain their signatures.
- Page 00 has 75 distinct goals. Cash receipt and ticket issue are steps of the two sales goals.
- Page 03 and its Mermaid counterpart include the added SellerView method.
- Compiled the updated production source with Java 17.
- Captured 12 focused Console scenarios on copied core data with future Match dates.
- Covered Search/Create Fan, maps/prices, customer history, new account-holder sale, deferred walk-in sale, existing cash checkout, retry without another payment, unknown/expired booking rejection, inactive customer rejection, and Fan-created booking paid at the counter.
- Separately removed a ticket only in the copied fixture and verified existing cash checkout fills the missing ticket without another payment.
- Current walkthroughs are at the top of the seven Seller guides and the Fan pending-payment guide. The 148 historical numbered flows retain their original recordings and menu context.

## Balanced Runtime Dataset — 2026-11-14

- Prepared 100 Matches with 50 assigned to each of the two Stadiums.
- Each Stadium has 100 physical Seats across rows A–J and five 20-seat Sections.
- Prepared exactly 10000 MatchSeat rows and 500 prices, covering every Match's own Stadium.
- Included 23 past CLOSED Matches and 77 future Matches, with 52 OPEN/25 CLOSED.
- Preserved existing business-file rows, referenced Seat locations, stored amounts,
  and inventory status/version/hold ownership for retained Match/Seat pairs.
- Checked exact counts, uniqueness, foreign keys, row layout and historical quoted totals.
- Read the supplied files through the production Java Repository classes.
- On a copied runtime dataset, verified the Console lists 100 Matches and shows
  exactly the correct 100 physical Seats for each Stadium, including VIP/Premium/Standard prices.

## Hold and Payment Checks

- Creating PENDING exclusively holds the requested one-to-four-seat group.
- A competing Fan/walk-in booking cannot acquire those seats.
- Invalid groups acquire no partial hold.
- Hold ownership and timestamps survive restart.
- Online and offline payment reject expired bookings before a new charge.
- At the exact expiry timestamp, the booking is expired.
- Expiry, cancellation and failed payment release only owned holds.
- Repeated cleanup preserves another booking's newer hold.
- Seat-map and administrative deactivation requests clean elapsed holds.
- Kick-off caps a shorter payment window.
- The original 2026-11-13 cash flow used a separate ticket-issue action. The integrated counter section above supersedes that navigation.
- Completed online/offline payment and issue retries preserve existing results.
- Legacy booking/MatchSeat CSV remains readable. Legacy unpaid rows without
  timestamps expire and paid historical records retain their state.

## Scope and Presentation Checks

- Current Seller sales navigation is create-new or pay-existing, with automatic issue after successful cash checkout.
- Account selection rejects INACTIVE immediately.
- Seat input errors identify the field/Seat ID and reason.
- Fan role/ownership checks, cancellation/refunds and ticket histories remain covered.
- Gate rejects wrong-Match/cancelled/used tickets and records one-time check-in.
- Administrator CRUD, roles, refund review, summary and audit remain covered.
- Booking-to-Fan multiplicity is optional for the retained walk-in subflow.
- The expiry-service dependencies are fully expanded in the affected diagram pages.

## Reproduce

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
java scripts/InspectDiagram.java
```

Follow [demo preparation](Feature_Demos/00_SETUP.md) and the
[ordered flows](Feature_Demos/01_DEMO_ORDER.md). CORE_DIR uses the checked-in
small seed to keep IDs stable. COUNTER_DEMO_DIR copies current source data.
EXPIRY_DEMO_DIR adds a deliberately elapsed hold to the small seed.

## Delivery Checklist

- [x] Integrated Seller checkout and shared User capabilities match the declared scope.
- [x] Thirty-minute hold, both payment channels and EXPIRED lifecycle implemented.
- [x] Source/Class Diagram/Mermaid member contracts aligned.
- [x] Every visible Use Case mapped to code and a feature guide.
- [x] All Console walkthroughs regenerated for the current menu and lifecycle.
- [x] README, SRS, architecture, persistence and agent context updated.
- [ ] Student rehearses on the documented fixture and current dates.
- [ ] Student visually checks rendered diagram routing before export.
- [ ] Student prepares the instructor's final report/video/submission package.

## Operating Limits

Full rendered wire routing remains a visual review step. Structural checks verify
members, IDs/endpoints and member bounds. Synchronization coordinates one JVM.
Payments/refunds are mocked, demo credentials are plaintext and multi-file CSV
operations can require reconciliation after interruption.

## Ticket Detail, Gate and Audit Update — 2026-11-14

- Compiled all production sources with Java 17 compatibility.
- Captured 60 Console interactions using isolated copied fixtures.
- Checked multi-ticket Booking Details, Seller existing-booking ticket preview,
  code-only Gate preview, cancellation, successful check-in, repeated use rejection,
  unknown ticket rejection, named Fan details and walk-in `None` details.
- Checked Audit Log names/roles and the missing Staff account fallback.
- TicketResponseDto adds customerName/customerPhone. TicketService receives
  FanRepository. validateTicket/checkInTicket now take Ticket Code only.
- BookingController and BookingService add getTicketsForBooking. GateView removes
  readMatchId. AdminManagementView.showAuditLogs receives Staff account DTOs.
- Updated all affected visible class members in draw.io and Mermaid. Source
  remains 97 class/enum contracts across 192 expanded boxes.
- Page 00 has 75 goals. UC-16 validation is included in UC-17 Check-in ticket.
- XML checks cover member contracts, IDs and relationship endpoints. Rendered
  routing and text fit remain a visual review step.
