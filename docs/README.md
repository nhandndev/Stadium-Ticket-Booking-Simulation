# Essential Project Documentation

The root [README](../README.md) contains the group's problem statement and scope.
Read the documents below in order.

| Order | Document | Purpose |
| --- | --- | --- |
| 01 | [SRS](01_SRS.md) | Requirements, roles, validation and persistence rules. |
| 02 | [Use Case specification](02_USE_CASE_SPECIFICATION.md) | Actors and visible feature inventory. |
| 03 | [Architecture](03_ARCHITECTURE.md) | Controller, View, Service, Model, DTO and Repository responsibilities. |
| 04 | [Booking protection](04_BOOKING_CONCURRENCY.md) | Default synchronized checkout and transaction CSV. |
| 05 | [Verification and delivery](05_VERIFICATION_AND_DELIVERY.md) | Recorded checks, rerun commands, limits and submission checklist. |
| 06 | [Use Case traceability](06_USE_CASE_TRACEABILITY.md) | Mapping from 82 visible Use Case nodes to feature walkthroughs. |
| 07 | [AI reflection guide](07_AI_REFLECTION_GUIDE.md) | T10 reflection and personal AI records. |
| 08 | [Presentation guide](08_PRESENTATION_GUIDE.md) | Vietnamese introduction, project explanation and diagram reading order. |

## Diagrams

[ClassDiagram.drawio](ClassDiagram.drawio) is the canonical six-page document.
Pages 00–05 cover Use Cases, accounts/browsing, administration, booking/payment/
tickets, repositories and domain models. The [diagram guide](Class_Diagrams/README.md)
links the five expanded Mermaid files.

## Feature Demos

The [feature-demo index](Demo_Tung_Feature/README.md) contains 87 feature files,
148 captured Console flows and 1546 input/output steps. Follow its preparation,
menu map and operation order for reproducible IDs and state.

## Agent and Audit

- [Agent context](../READMEFORAGENT.md).
- [Personal AI audit](../ai_logs/doan_ngoc_nhan_QE210282_ai_log.md).
- [Prompt audit summary](../ai_logs/prompt_audit_summary.md).
