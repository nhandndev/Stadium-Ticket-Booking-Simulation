# AI Log - Doan Ngoc Nhan - QE210282

Project: Stadium Ticket Booking Simulation

Course: LAB211 - OOP with Java

AI tool: Codex / ChatGPT

Log purpose: Luu vet qua trinh su dung AI de ho tro phan tich yeu cau, thiet ke tai lieu, audit prompt, va chuan bi AI Reflection.

## Entry 01 - Initial Repository Scan And Document Planning

Date: 2026-09-13

Project phase: Requirement analysis and documentation planning

Original prompt summary:

```text
Quet cac file trong folder stadium nay, rut ket lai toan bo, tao cac file document can thiet de lam du an. Can cac file nhu SRS, Use Case, Feature va cac file can thiet khac. Co the them feature phu hop voi he thong dat ve gan muc doanh nghiep, phan tang ro rang, van giu simulation.
```

AI output summary:

- Quet repo va phat hien file noi dung chinh: `README.md`, `contextchatbotcu.md`.
- Tao folder/file tai lieu trong `Document/`.
- Rut ket du an thanh bo document Markdown gom SRS, scope, use case, feature backlog, architecture, data model, booking/simulation design, edge cases, test plan, checklist, service contracts va roadmap.

Accepted parts:

- Tach tai lieu thanh nhieu file Markdown.
- Giu trong tam Java MVC, CSV persistence, concurrency simulation.
- Them feature gan doanh nghiep: seller, support, admin, payment simulation, audit log, reconciliation, idempotency, hold timeout.

Rejected/modified parts:

- Khong bien du an thanh web/app lon.
- Khong dua real payment, real database, distributed system vao core scope.

Verification method:

- AI doc truc tiep `README.md` va `contextchatbotcu.md`.
- Kiem tra file duoc tao bang `find Document`.
- Kiem tra so dong tai lieu bang `wc -l Document/*.md`.

Impact on project:

- Tao nen bo tai lieu nen tang de code va viet report.
- Giam rui ro thieu edge case khi implement booking/concurrency.

## Entry 02 - Use Case Logic Audit: Booking Core vs Checkout Flow

Date: 2026-09-13

Project phase: Use case correction and architecture refinement

Original prompt summary:

```text
Nguoi dung paste gop y rang Use Case hien tai co diem sai: Simulation khong nen include Create Booking cua Fan vi Create Booking include Make Online Payment. Can tach Attempt Seat Booking/Execute Booking Core de Fan, Seller va Simulator dung chung.
```

AI output summary:

- Doc pasted text tu attachment.
- Cap nhat `Document/03_USE_CASE_SPECIFICATION.md`.
- Sua quan he sai:
  - Bo logic `Execute Concurrent Booking -> Create Booking`.
  - Doi thanh `Execute Concurrent Booking -> Execute Booking Core`.
- Them use case noi bo `Execute Booking Core`.
- Them `Hold Seats`, `Expire Seat Hold`, `Expire Pending Booking`.
- Them `Apply Synchronization Strategy`.

Accepted parts:

- Tach `Create Booking` la customer checkout flow.
- Tach `Execute Booking Core` la loi dat ghe dung chung.
- Simulator chi do concurrency/double booking, khong chay 1000 online payment flows.

Rejected/modified parts:

- Bo cach dien dat cu co the gay hieu nham rang Simulator goi full payment checkout.

Verification method:

- Search trong `Document/` de dam bao khong con pattern sai `Execute Concurrent Booking <<include>> Create Booking`.
- Kiem tra cac file SRS, architecture, service contracts, booking design da dong bo.

Impact on project:

- Sua loi logic quan trong trong Use Case Diagram.
- Giup report va diagram thuyet phuc hon khi giai thich voi giang vien.

## Entry 03 - Enterprise-like Feature Expansion

Date: 2026-09-13

Project phase: Product hardening and edge case analysis

Original prompt summary:

```text
Bo sung cac feature thuc te cua he thong dat ve doanh nghiep: Logout, Manage Profile, Seat Hold/Expiry, Idempotency, Cancellation/Refund, Ticket Validation/Check-in, Notification, Manage Staff/Role, Sales Control, Pricing.
```

AI output summary:

- Cap nhat SRS voi cac FR moi.
- Cap nhat Feature Backlog voi priority P0/P1/P2/P3.
- Cap nhat Domain Data Model them `expiresAt`, `saleStatus`, pricing, notification, `usedAt`.
- Cap nhat Edge Cases and Business Rules.
- Cap nhat Test Plan va Delivery Checklist.

Accepted parts:

- Dua feature enterprise-like vao scope mo rong, khong ep het vao MVP.
- Danh dau cancellation/refund, check-in, notification la P2/P3.
- Giu simulation va double booking la core.

Rejected/modified parts:

- Khong dua dynamic pricing phuc tap, real notification, real QR scanner vao core implementation.

Verification method:

- Kiem tra lai `Document/` bang `rg` va `wc -l`.
- Dam bao cac feature moi co mat trong SRS/backlog/data model/test/checklist.

Impact on project:

- Tai lieu trong giong project gan doanh nghiep hon.
- Co business rule va edge case ro rang de tranh code qua don gian.

## Entry 04 - T10 AI Reflection And Prompt Audit Setup

Date: 2026-09-13

Project phase: Submission preparation

Original prompt summary:

```text
Them task T10: AI Reflection & Nop. Tong hop AI Log ca nhan tung thanh vien. Viet AI Reflection >=500 tu/người. Review code, fix bug, polish UI, dong goi ZIP. Vi toi hay dung AI nen sau khi xai thi audit prompt/audit gi do va them vao prompt truoc do.
```

AI output summary:

- Tao `Document/13_AI_USAGE_AUDIT_AND_REFLECTION.md`.
- Them template AI Log.
- Them Prompt Audit Template.
- Them checklist prompt audit.
- Them danh sach cac prompt truoc do nen dua vao audit.
- Cap nhat roadmap voi Phase 8 - T10 AI Reflection And Submission.
- Cap nhat delivery checklist voi T10 checklist.

Accepted parts:

- Can co AI Log ca nhan tung thanh vien.
- Can co prompt audit sau moi lan dung AI.
- Can viet AI Reflection >=500 tu/nguoi trong report.
- Can ghi ro output AI nao sai/thieu va nhom da sua nhu the nao.

Rejected/modified parts:

- Khong chi ghi template trong document, can co file log thuc te trong `ai_logs/`.

Verification method:

- Kiem tra `Document/13_AI_USAGE_AUDIT_AND_REFLECTION.md`.
- Search cac tu khoa `T10`, `AI Reflection`, `Prompt Audit`, `500`.

Impact on project:

- Co co so de viet phan AI Reflection trong bao cao.
- Minh bach qua trinh dung AI.

## Entry 05 - Personal AI Log Creation

Date: 2026-09-13

Project phase: AI audit logging

Original prompt summary:

```text
Y la khi toi dung thi no se luu vet ai_logs. Member la Doan Ngoc Nhan - QE210282. Hay tu dong luu vet cua toi.
```

AI output summary:

- Tao folder `ai_logs/`.
- Tao file `ai_logs/doan_ngoc_nhan_QE210282_ai_log.md`.
- Ghi lai cac interaction AI da xay ra trong qua trinh tao document.
- Tao prompt audit summary rieng.

Accepted parts:

- Luu vet theo member cu the.
- Ghi ro prompt summary, output summary, accepted/rejected, verification method, impact.

Rejected/modified parts:

- Khong chi de audit summary trong `Document/`; can co file submit-friendly trong `ai_logs/`.

Verification method:

- Kiem tra file ton tai trong `ai_logs/`.
- Kiem tra noi dung co du entry tu cac prompt truoc.

Impact on project:

- Bat dau co AI Log that de dua vao submission package.

## Entry 06 - Cleanup Use Case Include Duplication And Booking Core Boundary

Date: 2026-09-13

Project phase: Use case audit and documentation cleanup

Original prompt summary:

```text
Nguoi dung review ban document moi va danh gia da tot hon, nhung can don sach mot so diem: bo include lap Create Ticket/Record Transaction trong UC-04 va UC-05; chot boundary cua Execute Booking Core voi Hold Seats; giu View Match List/Detail/Seat Map/Availability rieng tren diagram; ghi ro Notification Service va Gate Staff la enterprise extension P1/P2.
```

AI output summary:

- Cap nhat `Document/03_USE_CASE_SPECIFICATION.md`.
- Bo `Create Ticket` va `Record Transaction` khoi included use cases cua `UC-04 Fan Create Booking`.
- Bo `Create Ticket` va `Record Transaction` khoi included use cases cua `UC-05 Seller Create Booking For Fan`, van giu `Issue Ticket`.
- Chot business note: `Execute Booking Core` = check availability + synchronize + reserve/commit seat state; khong xu ly payment, notification hoac issue ticket.
- Cap nhat `Document/07_BOOKING_AND_SIMULATION_DESIGN.md` de `Hold Seats` la buoc rieng cua checkout flow.
- Ghi ro `Notification Service` la enterprise extension P2/P3 va `Gate Staff` la enterprise extension P2.

Accepted parts:

- Nhan xet cua nguoi dung dung: include truc tiep `Create Ticket`/`Record Transaction` o UC-04/UC-05 bi lap voi `Confirm Booking`.
- Can lam ro boundary de thiet ke khong nhap nhang.

Rejected/modified parts:

- Khong xoa `Issue Ticket` cua Seller, vi day la operation cua Seller sau khi system da tao ticket.
- Khong xoa Notification/Gate Staff, chi danh dau la extension de khong lam scope LAB bi nang.

Verification method:

- Doc lai `Document/03_USE_CASE_SPECIFICATION.md` quanh UC-04, UC-05, UC-11 va relationship block.
- Doc lai `Document/07_BOOKING_AND_SIMULATION_DESIGN.md` quanh booking flow va execute booking core.
- Dam bao Simulation van la `Execute Concurrent Booking <<include>> Execute Booking Core`.

Impact on project:

- Use Case Diagram sach hon, it bi bat be ve include lap.
- Boundary giua checkout, hold seats va booking core ro hon.
- Tai lieu phu hop hon de lam baseline chinh thuc cho du an.

## Entry 07 - Update Root README With AI Audit

Date: 2026-09-13

Project phase: Documentation polish and submission readiness

Original prompt summary:

```text
Tu baseline da chinh, update file README tong cua du an va nho viet AI Audit.
```

AI output summary:

- Doc lai README root hien tai.
- Thay README cu bang ban tong hop moi.
- Them core design decision: tach `Create Booking`, `Execute Booking Core`, `Execute Concurrent Booking`.
- Them feature theo actor: Guest/Fan, Seller, Support Staff, Administrator, Simulator.
- Them business rules, architecture, CSV files, data generation, run commands, testing checklist.
- Them section `AI Audit` trong README root.
- Ghi ro AI log cua member `Doan Ngoc Nhan - QE210282`.

Accepted parts:

- README root can phan anh baseline chinh thuc moi.
- AI Audit can xuat hien trong README tong, khong chi trong `Document/`.
- Can link logic T10 voi `ai_logs/`.

Rejected/modified parts:

- Khong dua qua nhieu noi dung chi tiet vao README root; chi giu tong quan va tro ve folder `Document/` cho chi tiet.
- Khong coi enterprise-like extension la bat buoc LAB.

Verification method:

- Kiem tra README co section `AI Audit`.
- Kiem tra README co nhac `Execute Booking Core`.
- Kiem tra README co nhac simulator khong di qua online payment flow.
- Kiem tra README co duong dan `ai_logs/doan_ngoc_nhan_QE210282_ai_log.md`.

Impact on project:

- README root san sang hon cho nguoi doc dau tien.
- Submission package co dau vet AI Audit ro rang.

## Entry 08 - Sync README With Draw.io Use Case HTML And Remove HTML

Date: 2026-09-16

Project phase: README synchronization and repository cleanup

Original prompt summary:

```text
Trong file LABSE20D.drawio.html co lam lai use case. Hay update them may phan con thieu trong README. Cai nao trong HTML khong co thi la HTML thieu chu khong phai du an sai. Lam xong update README va xoa file HTML.
```

AI output summary:

- Doc file `LABSE20D.drawio.html`.
- Trich cac label actor/use case tu draw.io HTML.
- Cap nhat `README.md` voi cac use case thieu/nen noi ro hon:
  - Review booking.
  - Create pending booking.
  - View ticket detail.
  - Assist ticket issues.
  - Manage CSV data.
  - Configure simulation scenario.
  - View / compare simulation results.
  - Payment Service, Notification Service, Gate Staff extension.
- Them section `Use Case Diagram Coverage` vao README.
- Ghi ro `README + Document/` la official project scope; neu HTML thieu thi la diagram incomplete, khong phai project sai.
- Xoa file `LABSE20D.drawio.html` theo yeu cau.

Accepted parts:

- Dung HTML nhu source de dong bo README voi use case diagram hien tai.
- Giu README/Document la baseline chinh thuc vi diagram co the thieu.
- Xoa HTML sau khi da trich noi dung can thiet.

Rejected/modified parts:

- Khong thu hep scope du an theo nhung gi HTML co.
- Khong bo cac requirement da co trong README/Document chi vi HTML khong ve.

Verification method:

- Trich `value` labels tu HTML bang command-line.
- Kiem tra README co section `Use Case Diagram Coverage`.
- Kiem tra `LABSE20D.drawio.html` da bi xoa.
- Kiem tra git status sau thay doi.

Impact on project:

- README dong bo hon voi use case diagram moi.
- Repository gon hon vi khong giu file HTML draw.io tam.
- AI audit tiep tuc ghi lai ly do va tac dong cua thay doi.
