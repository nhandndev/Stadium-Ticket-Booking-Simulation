# Prompt Audit Summary

Project: Stadium Ticket Booking Simulation

Member: Doan Ngoc Nhan - QE210282

Purpose: Tong hop audit prompt/output quan trong trong qua trinh dung AI.

## Audit 01 - Generate Project Documents From Repository

Date: 2026-09-13

Prompt quality:

- Clear task: yes, yeu cau quet folder va tao document.
- Context included: partial, co noi stadium ticket booking va simulation.
- Output format: yes, yeu cau nhieu file `.md`.
- Constraints: yes, can phan tang ro rang, gan doanh nghiep, giu simulation.

AI output risk:

- Co the them qua nhieu feature vuot scope LAB.
- Co the lam tai lieu chung chung neu khong doc repo.

Human/AI correction:

- AI da doc `README.md` va `contextchatbotcu.md` truoc khi viet.
- Feature mo rong duoc chia priority de khong pha scope.

Final decision:

- Accepted. Bo document duoc tao trong `Document/`.

## Audit 02 - Use Case Correction For Simulator

Date: 2026-09-13

Prompt quality:

- Clear problem: yes, chi ro Simulation khong nen include Create Booking vi co online payment.
- Context included: yes, neu ro Fan/Seller/Simulator can dung chung core.
- Expected correction: yes, de xuat `Attempt Seat Booking` / `Execute Booking Core`.

AI output risk:

- Neu khong sua, Use Case Diagram se sai logic.
- Simulator metric bi lech vi payment flow khong phai trong tam concurrency.

Human/AI correction:

- Sua relationship thanh `Execute Concurrent Booking <<include>> Execute Booking Core`.
- Ghi ro `Create Booking` la checkout flow, `Execute Booking Core` la shared core.

Final decision:

- Accepted. Day la thay doi thiet ke quan trong nhat.

## Audit 03 - Enterprise-like Feature Additions

Date: 2026-09-13

Prompt quality:

- Clear task: yes, them feature he thong dat ve gan doanh nghiep.
- Scope risk: medium, vi co the them qua nhieu.

AI output risk:

- Refund/check-in/notification/staff role co the lam scope lon.

Human/AI correction:

- Dua cac feature nay vao P1/P2/P3.
- Core LAB van la MVC, CSV, booking, double booking prevention, simulator.

Final decision:

- Accepted with priority control.

## Audit 04 - T10 AI Reflection And Submission

Date: 2026-09-13

Prompt quality:

- Clear task: yes, them T10 AI Reflection & Nop.
- Deliverable clear: AI Log file, AI Reflection >=500 tu/nguoi, ZIP hoan chinh.
- Extra requirement: audit prompt sau moi lan dung AI.

AI output risk:

- Neu chi tao template trong Document thi chua co log nop that.

Human/AI correction:

- Tao them file thuc te trong `ai_logs/`.
- Ghi lai cac prompt/output da dung.

Final decision:

- Accepted. AI audit process da duoc thiet lap.

## Continuing Rule

Tu cac lan dung AI tiep theo trong project nay, moi interaction quan trong nen duoc append vao:

- `ai_logs/doan_ngoc_nhan_QE210282_ai_log.md`
- `ai_logs/prompt_audit_summary.md`

Moi entry nen co:

- Date.
- Prompt summary hoac prompt goc.
- AI output summary.
- Accepted/rejected parts.
- Verification method.
- Impact on project.

## Audit 05 - Cleanup Duplicate Include And Core Boundary

Date: 2026-09-13

Prompt quality:

- Clear issue: yes, chi ro UC-04/UC-05 include lap `Create Ticket` va `Record Transaction`.
- Clear expected fix: yes, giu `Confirm Booking <<include>> Create Ticket/Record Transaction`, giu `Issue Ticket` cho Seller.
- Clear boundary: yes, `Execute Booking Core` khong gom payment/notification/issue ticket.

AI output risk:

- Neu khong sua, Use Case Diagram co include lap va de bi bat be.
- Neu mo ta core va hold khong ro, implementation co the dat hold logic sai tang.

Human/AI correction:

- Bo include lap trong UC-04/UC-05.
- Chot boundary core trong use case spec va booking design.
- Danh dau Notification/Gate Staff la enterprise extension.

Final decision:

- Accepted. Tai lieu use case sach hon va phu hop lam baseline chinh thuc.

## Audit 06 - Root README And AI Audit

Date: 2026-09-13

Prompt quality:

- Clear task: yes, update README tong cua du an.
- Explicit AI audit requirement: yes.
- Context included: yes, dua tren baseline da chinh truoc do.

AI output risk:

- README co the qua dai hoac lap lai Document.
- AI Audit co the chi noi chung chung neu khong tro den file log that.

Human/AI correction:

- README duoc viet lai de gom overview, core design decision, features, architecture, CSV, simulator, testing, AI Audit va submission.
- AI Audit tro den file log that cua member `Doan Ngoc Nhan - QE210282`.

Final decision:

- Accepted. README tong da phan anh baseline moi va co AI Audit.

## Audit 07 - Sync README With Draw.io HTML

Date: 2026-09-16

Prompt quality:

- Clear task: yes, update README from `LABSE20D.drawio.html`.
- Clear scope guard: yes, if HTML misses something, HTML is incomplete, not the project.
- Clear cleanup requirement: yes, delete HTML after update.

AI output risk:

- Could incorrectly remove valid requirements because they are missing from the diagram.
- Could keep stale draw.io HTML after extracting useful content.

Human/AI correction:

- README was updated by adding diagram coverage without reducing official project scope.
- HTML was treated as a synchronization source, not as the authority over requirements.
- `LABSE20D.drawio.html` was deleted after README update.

Final decision:

- Accepted. README now includes use case coverage from the diagram and the temporary HTML file is removed.

## Audit 08 - User Actor Inheritance

Date: 2026-09-16

Prompt quality:

- Clear task: yes, update README from the latest Use Case image.
- Clear design change: yes, User is the parent actor and Fan/Staff inherit from User.
- Scope guard: yes, update README without deleting requirements absent from the diagram.

AI output risk:

- Could duplicate Login/Logout/Profile for every actor.
- Could mistakenly remove internal booking/concurrency requirements because the diagram does not show them.

Human/AI correction:

- Added a dedicated actor hierarchy section.
- Made Login, Logout and Manage Profile shared User-level use cases.
- Kept `Execute Booking Core`, synchronization and expiry in Document scope.

Final decision:

- Accepted. README now matches the latest actor inheritance model.

## Audit 09 - Latest Use Case Label Synchronization

Date: 2026-09-21

Prompt quality:

- Clear task: yes, update README from the newest Use Case image.
- Clear source: yes, user provided the updated diagram image.
- Scope guard: implicit, previous rule still applies that README/Document remain official scope.

AI output risk:

- Could remove internal requirements because the diagram is simplified.
- Could keep old labels that no longer match the current diagram.

Human/AI correction:

- Updated README labels to match the latest diagram.
- Kept internal rules and booking core documentation intact.
- Recorded the change in AI log.

Final decision:

- Accepted. README is synchronized with the latest Use Case labels.

## Audit 10 - Use Case To Mermaid Class Diagram

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- Clear deliverable: yes, Markdown documentation plus Mermaid `.mmd` class diagrams.
- Clear notation: yes, `-` attributes, `+` methods, typed parameters and named UML relationships.
- Clear constraint: yes, no relationship multiplicity values.
- Clear platform: yes, pure Java console LAB project.
- Clear readability requirement: yes, split by domain.
- Clear quality gate: yes, verify correctness before delivery.

AI output risks:

- Directly translating actors/use cases could create empty or anemic classes.
- One giant diagram could be syntactically valid but unreadable.
- Duplicating seat availability on physical `Seat` could break bookings across different matches.
- Simulator could accidentally depend on online checkout/payment.
- CSV repository arrows could be modeled as inheritance instead of interface implementation.
- Mermaid generic syntax or nested collections could cause parser errors.

Human/AI correction:

- Actor behavior was mapped through Controller and Service layers; only real domain concepts became entities.
- Detailed diagrams were split into eight domains plus one overview.
- `Seat` and `MatchSeat` were separated to preserve per-match availability.
- Seller/Support/Admin/Gate were represented as `StaffRole` plus role-specific controllers/services.
- Simulator uses `BookingService.executeBookingCore` through synchronization strategies.
- Nested Mermaid generic types were replaced by named types such as `TableRow`, `EntityCache` and `StrategyRegistry`.
- Repository interfaces and concrete CSV implementations use `..|>`.

Output audit:

- Coverage: latest Guest, Fan, Staff roles, external services and Simulator Use Cases are mapped.
- Layering: View -> Controller -> Service -> Repository -> CSV is preserved.
- Signature quality: constructors/methods have typed parameters; methods have explicit return types.
- Relationship quality: all requested Mermaid arrow types are used and no multiplicity is present.
- Syntax: all 9 Mermaid files passed Mermaid parser `11.12.0` on 2026-09-22.
- Consistency: README, domain model, architecture and service contracts were updated for `MatchSeat`.

Final decision:

- Accepted. The class-diagram package is detailed enough to serve as the Java implementation baseline and remains appropriate for a console LAB project.

## Audit 11 - System Overview Semantic Review

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- Clear target: yes, review `00_SYSTEM_OVERVIEW.mmd`.
- Clear expected response: yes, verify first and explain flow only from verified evidence.

Output audit:

- Mermaid syntax: PASS with parser 11.12.0.
- Domain correctness: mostly correct.
- UML semantic completeness: needs small corrections around strategy factory, CSV repository hierarchy, service dependency type and missing key associations.
- No diagram mutation was made because this was a review/explanation request.

Final decision:

- Partially accepted. The overview communicates the intended architecture but should be corrected before being treated as the final UML source.

## Audit 12 - Reduce UML Overfitting

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- Problem is clear: yes, the previous UML was too large and difficult for the assignment.
- Desired persistence style is clear: yes, one simple repository per data type.
- Scope direction is clear: simplify while keeping the LAB requirements.

Risk review:

- Simplifying too far could remove concurrency strategies or the shared booking core.
- Removing `MatchSeat` could reintroduce cross-match seat-status errors.
- Leaving old detailed files would continue to confuse implementation scope.

Correction applied:

- Reduced 9 diagrams to 5 and removed superseded Mermaid files.
- Replaced generic/abstract repository architecture with concrete repositories.
- Kept only abstractions required to demonstrate polymorphism and concurrency.
- Preserved MVC, CSV, booking, payment simulation, ticket, custom exceptions and simulator.
- Rewrote the class-diagram guide so optional enterprise components are explicitly outside the baseline.

Output audit:

- 5/5 Mermaid sources parse successfully.
- No relationship multiplicity is present.
- No generic repository framework remains in Mermaid sources.
- All public methods have typed parameters and explicit return types.

Final decision:

- Accepted. The new diagrams fit the LAB scope and remain sufficient to implement and demonstrate the required booking/concurrency behavior.

## Audit 13 - Staff Role Presentation Views

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- Missing content identified clearly: yes, each Staff role must appear in the class diagram.
- Presentation constraint stated clearly: yes, easy top-down and left-to-right reading with notes and grouping boxes.
- Deadline context provided: yes, the diagrams are for tomorrow's presentation.

Risk review:

- A single complete Staff diagram would become too wide and unreadable.
- Treating all Staff as one controller would conflict with the Use Case actor boundaries.
- Adding role-specific repositories would duplicate the same CSV storage logic.

Correction applied:

- Added four concrete Staff subclasses and four role-specific controllers.
- Kept one shared `StaffRepository` with a role column.
- Created one hierarchy slide and four compact workflow slides.
- Added namespace boxes and reading-order notes.
- Kept Controller -> Service -> Repository dependency direction.

Output audit:

- All 10 Mermaid files parse successfully.
- Five Staff presentation diagrams render successfully through Mermaid CLI.
- Visual inspection confirms no overlapping class text and clear left-to-right role flows.

Final decision:

- Accepted. Staff coverage now matches the Use Case diagram and is suitable for slide presentation.

## Audit 14 - Full Use Case to MVC Class Diagram

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- Scope is explicit: full Java Console class diagram, not Spring or REST.
- Feature checklist is detailed across Guest/Fan, all Staff roles, Simulator, payment/refund, notification and Console UI.
- UML constraints are testable: typed signatures, declared enums, correct relationships and no multiplicity.
- Presentation intent is clear: easy to compare with the Use Case diagram.

Risk review:

- One giant full diagram would technically satisfy coverage but be unreadable in a presentation.
- Blindly following example mechanisms could replace the project's existing FILE_LOCK experiment.
- Empty repository interfaces or HTTP-style DTOs would not match the Java Console implementation.
- Combining cancellation and refund into one persistent entity would hide their different lifecycles.

Correction applied:

- Used a full source model plus actor-specific MVC diagrams and compact presentation views.
- Kept concrete CSV repositories and request/result objects that support console workflows.
- Preserved existing synchronization modes and shared booking core boundaries.
- Split cancellation, refund, payment and transaction history into explicit domain classes.
- Added Use Case coverage documentation and a recommended presentation sequence.

Output audit:

- All Mermaid sources parse and render successfully.
- Actor inheritance includes Fan and five Staff subtypes.
- All enum-like domain values used by the design are declared in the full domain model.
- Core Use Case groups have matching Controller/Service operations.
- Simulator calls booking core only and does not call checkout/payment.
- No UML relationship multiplicity is present.

Final decision:

- Accepted. The package now matches the requested Use Case-oriented structure, remains implementable in plain Java Console, and includes readable slides for presentation.

## Audit 15 - Small-Project Class Inventory Correction

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The correction is clear: complete Use Case coverage does not mean one class per feature.
- Required scope is explicit: plain Java Console, small project, with Model and DTO.

Risk review:

- Splitting every Admin entity into a dedicated Controller and Service would increase code without adding value for this project.
- Removing methods while simplifying would lose Use Case coverage.
- Removing all DTOs would mix account passwords and aggregate console results into Domain Models.

Correction applied:

- Consolidated Administrator behavior into one View, one Controller and one Service.
- Preserved every requested feature as a typed method in `AdminService`.
- Retained entity repositories, domain Models and only useful DTOs.
- Removed the temporary Admin split diagrams and grouping classes.

Output audit:

- Administrator A-I method coverage: PASS.
- Obsolete split Admin classes/files: none.
- Mermaid parse/render: PASS.
- Relationship multiplicity: none added.

Final decision:

- Accepted. The corrected Admin design is complete enough to implement but no longer over-engineered for a small Java Console project.

## Audit 16 - Latest Draw.io Class Diagram Reset

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The required file structure is exact: one full diagram, eight workflow diagrams and one README.
- The scope is corrected clearly to a small plain-Java Console project.
- The newest Draw.io file is identified as the authority for Use Case coverage.
- The intended audience and reading direction are clear: implementation reference plus presentation-friendly flows.

Risk review:

- Reusing the previous 15/16-file package would violate the requested reset and remain difficult to present.
- Creating a class for every feature would over-engineer the lab and confuse features with object responsibilities.
- Removing DTOs, statuses or synchronization strategies would make important flows impossible to type or explain.
- Treating ambiguous labels literally could create duplicate seat logic or delete the wrong account type.

Correction applied:

- Rebuilt the package as exactly 9 Mermaid diagrams under `Document/Stadium_Class_Diagrams`.
- Consolidated related features into practical Console UI, Controller and Service classes.
- Retained concrete CSV repositories, core Models, focused DTOs, enums and synchronization strategies only where used.
- Documented source ambiguities and their implementation interpretation in the package README.
- Added complete Use Case-to-class traceability and kept the full diagram synchronized with all focused diagrams.
- Updated project indexes and packaged the result as a ZIP.

Output audit:

- File structure and count: PASS.
- Latest Draw.io Use Case coverage: PASS.
- Mermaid parse and render for all 9 diagrams: PASS.
- Explicit constructors and typed feature methods: PASS.
- Full diagram contains the union of focused-diagram classes: PASS.
- Simulator isolation from payment and ticket issuance: PASS.
- ZIP archive contents: PASS.

Final decision:

- Accepted. The new package is complete enough to implement, compact enough to present and aligned with the latest Use Case diagram without adding enterprise-only complexity.

## Audit 17 - Domain Consolidation and Diagram Presentation Quality

Date: 2026-09-22

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The correction identifies three measurable problems: document order, duplicated domain responsibility and rough visual presentation.
- The prohibition on reference tables/placeholders is explicit.
- The expected result remains compatible with the small Java Console project scope.

Risk review:

- Keeping the old traceability tables would make the README longer than the diagrams and harder to present.
- Placeholder classes marked as external definitions would make each module look incomplete.
- Repeating Admin CRUD in AdminService and domain services would create two possible implementation owners.
- Mermaid class styling inside namespaces requires direct class styles; class labels alone do not change rendered colors.

Correction applied:

- Replaced table-based documentation with an ordered narrative by domain and workflow.
- Removed all cross-file placeholder classes.
- Assigned each feature to one owning domain service while retaining staff orchestration services.
- Applied a consistent multi-color layer system using styles verified in rendered output.
- Connected internal repository/model and service/DTO relationships to improve left-to-right reading.

Output audit:

- Document reading order: PASS.
- Same-domain feature ownership: PASS.
- Markdown reference tables: none.
- Reference placeholder classes: none.
- All 9 Mermaid files render successfully.
- Booking and Staff/Admin visual samples are colored, separated by namespace and free of overlapping class text.

Final decision:

- Accepted. The diagrams are now domain-focused, presentation-ready and still implementable as a small plain-Java Console project.

## Audit 18 - Reduced Scope Layered Class Diagram

Date: 2026-09-23

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The newest Use Case is explicitly identified as smaller than the previous version.
- The LAB requires exactly four architectural layers and complete UML details.
- The user explicitly requires multiplicity and prohibits reference tables/placeholders.

Risk review:

- Reusing the old diagrams would preserve removed actors and violate the reduced scope.
- Keeping Service/DTO layers would add complexity not requested by the LAB.
- Putting all Admin CRUD methods beside smaller controllers would make the focused diagram unreadable.
- Omitting multiplicity would fail the stated report rubric even if method coverage were correct.

Correction applied:

- Replaced the old package instead of editing it incrementally.
- Modeled only Guest, User, Admin and the features visible in the newest Draw.io.
- Connected View to Controller, Controller to grouped CSV Repository, and Repository to Model in the full diagram.
- Split Model and Controller details across focused pages while retaining one complete system diagram.
- Added direct styles and top-down composition to keep role menus and layer flow visually clear.

Output audit:

- Use Case scope reduction: PASS.
- MODEL, REPOSITORY, CONTROLLER and VIEW layers: PASS.
- Attributes, constructors, typed methods and visibility: PASS.
- UML multiplicity: PASS.
- Mermaid render: 7/7 PASS.
- Reference placeholders/tables: none.

Final decision:

- Accepted. The diagrams satisfy the reduced LAB scope and UML rubric without restoring enterprise-only classes.

## Audit 19 - Domain-Oriented Diagram Correction

Date: 2026-09-23

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The user clearly identifies why the layer-only presentation is difficult to understand.
- The dependency rule is explicit: include the complete required class box instead of a reference placeholder.
- Existing UML completeness requirements still apply.

Risk review:

- Keeping separate Model/Repository/Controller pages forces the presenter to jump between files for one use case.
- Empty external class boxes would hide the exact dependency contract.
- A single AdminController would mix account, venue and simulation responsibilities across domains.
- Copying every transitive dependency would make each domain nearly as large as the full system diagram.

Correction applied:

- Assigned each feature to a domain controller: UserController, StadiumController, BookingController or SimulationController.
- Grouped each domain's Model, Repository and Controller classes on one page.
- Repeated direct cross-domain classes with full UML content where needed.
- Kept transitive persistence internals out of unrelated domain pages while retaining complete direct class boxes.
- Kept Console Navigation as the cross-domain View page with complete Controller definitions.

Output audit:

- Domain organization: PASS.
- Complete direct dependency class boxes: PASS.
- Empty references: none.
- Attributes, constructors, methods and visibility: PASS.
- Multiplicity and full-system relationships: PASS.
- Mermaid render: 6/6 PASS.

Final decision:

- Accepted. The detailed diagrams now follow business domains and are easier to present without losing UML completeness.

## Audit 20 - LAB211 Completeness Review

Date: 2026-09-23

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The user provides the authoritative LAB211 assignment and asks for a focused completeness review.
- The requested constraint is clear: retain the simple domain-oriented diagram style.
- The review scope explicitly includes features, fields and methods.

Risk review:

- A diagram may look complete at use-case level while still miss classes explicitly scored by the rubric.
- An enum listing synchronization modes does not demonstrate actual synchronization implementations.
- Renaming required entities without documenting the mapping may lose marks during rubric-based review.
- Adding every possible enterprise class would obscure the small Java Console LAB architecture.

Audit result:

- MVC direction and the main business domains are present.
- The current baseline is not yet sufficient for strict rubric compliance.
- High-priority gaps are `BaseEntity`, `CsvRepository<T>`, `Fan`, `BookingTransaction`, synchronization implementations, `BookingTask`, `CountDownLatch`, `ExecutorService`, `DataGenerator` and custom exceptions.
- Medium-priority gaps include rubric-named View classes, predicate search, the four-ticket invariant and more complete simulation configuration/result fields.

Final decision:

- Revision required. Add only rubric-critical classes and contracts, keeping them grouped inside the existing domain diagrams.

## Audit 21 - ChatGPT Web Handoff Prompt

Date: 2026-09-23

Member: Doan Ngoc Nhan - QE210282

Prompt quality:

- The user changed the requested output from direct diagram editing to a Markdown handoff document.
- The target consumer is ChatGPT Web, so the document must be executable as a complete prompt without relying on chat history.

Risk review:

- A short missing-feature list would not preserve the user's diagram style and scope constraints.
- Asking another model to add every possible class could restore the oversized enterprise design.
- Inconsistent class signatures across the full and domain diagrams would make the report unreliable.

Correction applied:

- Produced one self-contained prompt containing required LAB classes, minimum fields and methods, multiplicity, MVC constraints and visual rules.
- Required the next model to audit before adding so partially completed classes are not duplicated.
- Required direct edits to the existing six diagrams and a Mermaid render check.

Final decision:

- Accepted. The handoff file is ready to provide to ChatGPT Web together with the existing diagram folder.

## Audit 22 - Official LAB and Page-1 Use Case Verification

Date: 2026-09-23

Member: Doan Ngoc Nhan - QE210282

Source boundary:

- Requirements: newly attached official LAB211 assignment.
- Use Case scope: page 1 `System Overview` of the Draw.io file only.
- Page 2 legacy class content was explicitly excluded.

Audit result:

- Syntax/render baseline: PASS for all six Mermaid files.
- Reduced Guest/User/Admin scope: PASS.
- LAB rubric coverage: mostly complete but not yet final.
- Blocking corrections: missing `buildSeatMap`, unresolved `getMyTickets` dependency, duplicated seat version source, repository-level synchronization mismatch and cross-file signature drift.
- Documentation cleanup: class-diagram README uses stale controller/repository names.

Final decision:

- Revision required before treating the diagrams as the final implementation contract.

## Audit 23 - Correct Folder and Expanded Use Case Review

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Source boundary:

- Folder: `/Users/lilnhan/Downloads/Stadium_LAB211_Six_Class_Diagrams_Revised 2`.
- Requirements: local `debai.md`.
- Use Case scope: page 1 `System Overview - UseCase` of the local Draw.io file only.

Audit result:

- Original Mermaid syntax: FAIL in all six files because grouped style assignment uses comma-separated class names.
- Syntax-corrected temporary copies: PASS in all six files.
- LAB core coverage: mostly present.
- Page-1 Use Case coverage: incomplete because the diagrams omit multiple staff actors, external services and their business domains.
- No source file in the reviewed Downloads folder was changed.

Final decision:

- Not ready as the final class-diagram package. Fix syntax first, then decide whether page-1 enterprise actors are mandatory implementation scope or should be removed from the Use Case.

## Audit 24 - Draw.io Version 10 Feature Coverage

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Scope:

- Source: page 1 of Draw.io version 10.
- Review dimension: feature completeness only.
- Unsnapped connector metadata was excluded from the decision at the user's request.

Audit result:

- Support Staff removal: PASS.
- Notification Service removal: PASS.
- Remaining Java Console actor scope: coherent.
- Required correction: add Generate CSV Dataset.
- Required correction: add Delete Staff Account.
- Clarification required: approve/reject refund outcome and shared authentication inheritance.

Final decision:

- Nearly complete. A small feature cleanup is required before freezing the Use Case baseline.

## Audit 25 - Six-Diagram Handoff and DTO Decision

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Audit scope:

- Six Mermaid diagrams in the Revised 2 folder.
- LAB requirements from local `debai.md`.
- Feature scope from page 1 of Draw.io version 10.
- Existing relationship style must remain sparse and presentation-friendly.

Audit result:

- LAB core architecture: substantially covered.
- Full Use Case coverage: incomplete.
- Mermaid source syntax: requires grouped-style correction in all six files.
- DTO layer: rejected as unnecessary for a Java Console and CSV boundary.
- A complete revision handoff was created for ChatGPT Web.

Final decision:

- Use the handoff prompt to revise the existing six files without changing their visual language or adding architectural layers.

## Audit 26 - Java Console Complexity Correction

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Issue:

- The previous handoff could be interpreted as encouraging enterprise-style implementation despite the project being plain Java Console.

Correction:

- Set a 3-15 line target for ordinary controller and CRUD methods.
- Restricted implementation to `Scanner`, `switch`, `if`, enum, repository CRUD and CSV append/update patterns.
- Simplified payment, refund, audit, monitoring and role handling.
- Explicitly banned unnecessary architectural patterns and DTOs.

Final decision:

- The handoff now preserves feature coverage while keeping non-concurrency code appropriate for a small LAB211 Java Console project.

## Audit 27 - Use-Case-Aligned Mermaid Review

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Audit scope:

- Six Mermaid diagrams in `Stadium_LAB211_Six_Class_Diagrams_UseCase_Aligned`.
- LAB requirements from local `debai.md`.
- Feature scope from page 1 of Draw.io version 10.

Audit result:

- Feature coverage is close but not yet complete: simulation export, simulator role routing and shared profile update need correction.
- Mermaid syntax is valid in five files; diagram 05 has one generic style parsing error.
- Repository inheritance, entity inheritance and simulation-result multiplicity need consistency fixes.
- Current Admin rendering is too wide for a report or presentation slide.
- DTOs and additional framework layers remain unnecessary.

Final decision:

- Do not freeze this class-diagram baseline yet. Apply the small alignment and layout corrections, then render all six files again.

## Audit 28 - Final Fixed Mermaid Review

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Audit result:

- All six diagrams now pass Mermaid rendering.
- Use Case coverage is functionally complete for the reduced Java Console scope.
- One blocking multiplicity inconsistency remains in diagram 03: a MatchSeat must belong to at most one Booking, not many Bookings.
- Request/response helper classes are optional and add avoidable mapping for this project.
- Diagram dimensions remain too wide for normal report and slide placement.

Final decision:

- Correct the Booking-to-MatchSeat multiplicity and improve export layout before freezing the final baseline.

## Audit 29 - Full System Mermaid Assembly

Date: 2026-09-24

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Added one full-system class diagram while retaining the six smaller domain diagrams.
- The full diagram includes all layers, attributes, methods, inheritance, multiplicities and primary execution flows.
- The known Booking-to-MatchSeat multiplicity regression was corrected in the source domain diagram and the aggregate diagram.
- Mermaid rendering and paired Markdown synchronization passed.

Final decision:

- The Final Fixed folder now contains a valid full-system overview plus six detailed diagrams for presentation and implementation reference.

## Audit 30 - Standalone DTO Diagram

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Produced one valid Draw.io page containing all 12 selected DTO classes.
- Each DTO has complete attributes, constructors, getters and setters.
- DTOs remain independent from Model, Repository and CSV persistence.

Final decision:

- The DTO page is ready to open and copy into the main class-diagram file.

## Audit 31 - DTO Mermaid Export

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Exported the complete 12-class DTO page to valid Mermaid.
- No DTO relationships or persistence inheritance were introduced.
- Mermaid rendering and source synchronization passed.

## Audit 32 - Full DTO Coverage Revision

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- The previous 12-class DTO set did not explicitly cover public registration or Admin CRUD forms.
- The revised set contains 18 DTOs and distinguishes public registration from Admin-created Fan accounts.
- Controller signature changes and DTO mapping rules are documented without introducing ApiResponse, HTTP concepts or framework dependencies.
- Mermaid rendering and structural counts passed.

## Audit 33 - Service Layer Diagram Alignment

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- The diagrams previously showed Controller classes accessing Repository classes directly even though the project architecture includes Service classes.
- Six focused services were added without introducing Spring, HTTP or enterprise framework concepts.
- Controller classes now handle Console commands and delegation; Service classes own workflows and business rules; Repository classes retain CSV access.
- Simulation and synchronization strategies now reuse `BookingService.executeBookingCore` without entering the online payment or ticket flow.
- The existing domain-oriented layout and relationship style were retained.

Final decision:

- The class-diagram baseline now represents an MVC Java Console application with explicit Service and Repository layers.

## Audit 34 - Six-Page Dependency Reference

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Produced a complete page-by-page dependency guide for the six revised class diagrams.
- Each connector identifies its source, destination, UML relationship type and project meaning.
- Field and constructor dependencies omitted from the visual Mermaid connectors are explicitly identified.
- DTO dependencies are separated from core connectors to preserve diagram readability.

Final decision:

- The dependency guide can be used to redraw connectors or implement constructor injection without changing the approved class-diagram sources.

## Audit 35 - Final Diagram Baseline Organization

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Consolidated the approved six-page class-diagram set under the Document hierarchy.
- Isolated the new baseline in a `Final` folder so it cannot be confused with earlier diagrams.
- Updated project documentation links and removed loose diagram files from the repository root.

Final decision:

- `Document/Stadium_Class_Diagrams/Final/` is the authoritative class-diagram baseline for implementation.

## Audit 36 - Document Folder Cleanup

Date: 2026-09-27

Member: Doan Ngoc Nhan - QE210282

Audit result:

- Removed unnecessary nesting and duplicate generated diagram documents from the active Document tree.
- Preserved all superseded artifacts under `archive/class_diagrams_legacy/`.
- The authoritative implementation baseline is now `Document/Class_Diagrams/`.
