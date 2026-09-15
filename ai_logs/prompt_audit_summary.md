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
