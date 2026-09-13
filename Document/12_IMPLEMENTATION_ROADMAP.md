# Implementation Roadmap

## Phase 0 - Project Skeleton

Goal: tao nen tang code chay duoc.

Tasks:

- Tao package `model`, `repository`, `service`, `controller`, `view`, `exception`, `util`.
- Tao `Main.java` va menu chinh.
- Tao enum co ban.
- Tao custom exceptions.
- Tao `CsvRepository<T>` va utility parse/write CSV.

Done when:

- App compile.
- Main menu hien duoc.

## Phase 1 - Data Model And CSV

Goal: co du lieu va repository dung.

Tasks:

- Implement entity: Stadium, Section, Seat, Match, Fan, Ticket, BookingTransaction.
- Implement repository cho core entities.
- Implement `DataGenerator`.
- Implement `CsvValidationService`.
- Tao data >= 10,000 rows.

Done when:

- `wc -l data/*.csv` dat target.
- Load all CSV khong loi.
- Duplicate/missing reference duoc report.

## Phase 2 - Guest/Fan Booking MVP

Goal: dat ve single-thread dung.

Tasks:

- Guest view match/section/seat map.
- Fan register/login.
- BookingService validate 1-4 seats.
- Implement booking core: re-check seat, apply sync strategy, hold seat.
- Implement fan checkout: payment simulation, confirm booking.
- Create ticket.
- Record transaction.
- View ticket/history.

Done when:

- Fan book thanh cong.
- Book same seat lan 2 bi reject.
- Payment fail release seat.
- Pending booking expiry release seat.

## Phase 3 - Synchronization Strategy

Goal: tach booking core va co mechanism de simulator goi.

Tasks:

- Tao `BookingSynchronizationStrategy`.
- Implement `NO_LOCK`.
- Implement `SYNCHRONIZED`.
- Implement `FILE_LOCK`.
- Implement `OPTIMISTIC` voi `version`.
- Dam bao `executeBookingCore` chon strategy tu request.

Done when:

- Moi strategy chay duoc voi booking request.
- Safe strategy khong tao duplicate sequential/concurrent basic test.

## Phase 4 - Concurrency Simulator

Goal: chung minh double booking prevention.

Tasks:

- Tao `SimulationConfig`.
- Tao booking tasks.
- Dung `ExecutorService`.
- Dung `CountDownLatch`.
- Implement high/medium/low contention.
- Moi task goi booking core/attempt seat booking, khong goi fan checkout/payment.
- Collect success/failure/conflict.
- Detect double booking by ticket scan.
- Save `simulation_results.csv`.

Done when:

- Chay duoc 100/500/1000 threads tuy may.
- Co result cho 4 mechanism.
- Co bang throughput/double booking.

## Phase 5 - Staff Extension

Goal: he thong gan thuc te hon.

Tasks:

- Staff login va role menu.
- Seller search/create fan.
- Seller booking for fan bang BookingService checkout va booking core chung.
- Offline payment confirmation.
- Support search fan/ticket/booking/payment.
- Admin CRUD stadium/section/seat/match/fan.
- Admin manage staff/roles neu lam RBAC.
- Admin open/close ticket sales va manage pricing.

Done when:

- Seller co the dat ve ho khach.
- Support tra cuu duoc su co.
- Admin CRUD khong pha data integrity.

## Phase 6 - Enterprise-like Hardening

Goal: xu ly edge case khong bi "project hoc" qua don gian.

Tasks:

- Seat lock timeout.
- Idempotency key.
- Audit log.
- Payment/ticket reconciliation.
- Cancellation/refund simulation.
- Ticket check-in/validation.
- Notification simulation.
- Soft delete/status thay vi hard delete.
- Search/filter/pagination don gian.
- Better error messages.

Done when:

- Edge case trong `08_EDGE_CASES_AND_BUSINESS_RULES.md` co huong xu ly.
- Demo co the giai thich vi sao he thong tranh duplicate va data inconsistency.

## Phase 7 - Documentation And Demo

Goal: san sang nop bai/thuyet trinh.

Tasks:

- Cap nhat README run command.
- Ve UML class diagram.
- Ve Booking Flow.
- Ve Synchronization Flow.
- Ve Simulator Flow.
- Ve Use Case Diagram.
- Viet report.
- Lam slide.
- Ghi AI logs/reflection neu de bai yeu cau.

Done when:

- Zip package dung cau truc.
- Co screenshot/log result.
- Co bang so sanh mechanism.

## Phase 8 - T10 AI Reflection And Submission

Goal: hoan tat yeu cau AI Reflection & Nop.

Tasks:

- Tong hop AI Log ca nhan tung thanh vien.
- Audit cac prompt quan trong da dung trong qua trinh lam.
- Ghi ro output AI nao dung, output nao sai/thieu va nhom da sua ra sao.
- Viet AI Reflection trong report, toi thieu 500 tu moi thanh vien.
- Review code toan bo.
- Fix bug con lai.
- Polish console UI/menu/error message.
- Kiem tra lai diagram, report, slide, README.
- Dong goi ZIP theo cau truc yeu cau.

Done when:

- Co file AI Log cho tung thanh vien.
- Co prompt audit summary hoac audit section trong tung AI Log.
- Report co AI Reflection >=500 tu/nguoi.
- ZIP nop co `src/`, `data/`, `docs/`, `ai_logs/`, `README.md`.
- Project compile/run va demo flow chinh truoc khi zip.

## Suggested Implementation Order

1. Entity + enum.
2. CSV repository + data generator.
3. Fan booking single-thread.
4. Ticket/transaction persistence.
5. Synchronization strategies.
6. Simulator.
7. Staff/admin/support.
8. Sales control, pricing, profile/logout hardening.
9. Edge cases and reporting.
10. AI log/reflection audit va dong goi nop.

## Risk Register

| Risk | Impact | Mitigation |
|---|---|---|
| Booking logic bi duplicate giua Fan/Seller/Simulator | Ket qua simulation khong co y nghia | Bat buoc dung chung BookingService |
| Simulator goi Fan checkout co payment | Metric sai trong tam concurrency | Simulator chi goi executeBookingCore/attemptSeatBooking |
| CSV write bi corrupt khi concurrent | Sai data | File lock/atomic write/critical section |
| NO_LOCK khong tao double booking luc demo | Kho giai thich race | High contention + artificial delay trong unsafe strategy |
| Dataset lon lam search cham | Demo cham | Cache in memory hoac index Map by id |
| Admin hard delete lam hong history | Data inconsistency | Soft delete/status |
| Optimistic locking sai version | Van double booking | Test high contention rieng |
