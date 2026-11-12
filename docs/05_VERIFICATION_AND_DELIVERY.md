# Verification and Delivery

Recorded 2026-11-13 for the seven-feature Seller scope and timed booking holds.

## Current Evidence

- Compiled all 97 production class/enum contracts with Java 17.
- Replayed 138 core Console flows on an isolated reproducible fixture.
- Executed ten counter/expiry flows on separate copied fixtures.
- Refreshed all 87 feature guides with 148 actual flows and 1546 input/output pairs.
- Matched source fields, explicit public constructors and public methods to all
  192 expanded class boxes and five Mermaid files.
- Mapped all 82 visible Use Case nodes to implemented operations and feature guides.

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
- Offline payment leaves ticket issue to Seller's separate Issue ticket action.
- Completed online/offline payment and issue retries preserve existing results.
- Legacy booking/MatchSeat CSV remains readable. Legacy unpaid rows without
  timestamps expire and paid historical records retain their state.

## Scope and Presentation Checks

- Seller has the seven visible features in the Use Case.
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

- [x] Seven Seller features and shared User capabilities match the declared scope.
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
