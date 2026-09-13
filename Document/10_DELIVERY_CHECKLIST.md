# Delivery Checklist

## Code Checklist

- [ ] Java source compiles.
- [ ] Main application runs.
- [ ] MVC respected.
- [ ] View has no business logic.
- [ ] Controller does not read/write CSV directly.
- [ ] Repository handles CSV.
- [ ] BookingService shared by Fan/Seller/Simulator.
- [ ] BookingService separates checkout flow from booking core.
- [ ] At least 5 custom exceptions.
- [ ] DataGenerator works.
- [ ] CSV data >= 10,000 rows.

## Required Functional Checklist

- [ ] Guest can view match list.
- [ ] Guest can view match detail.
- [ ] Guest/Fan can view seat map.
- [ ] Fan can register.
- [ ] Fan can login/logout.
- [ ] Fan can manage profile if implemented.
- [ ] Fan can select 1-4 seats.
- [ ] Fan can book ticket.
- [ ] Fan can view owned tickets.
- [ ] Fan can view booking history.
- [ ] Booked seat cannot be booked again.
- [ ] Seller can book for fan.
- [ ] Seller can confirm offline payment.
- [ ] Seller issue ticket after system creates ticket.
- [ ] Support can search ticket/booking/payment.
- [ ] Admin can CRUD/search core entities.
- [ ] Admin can open/close ticket sales if implemented.
- [ ] Admin can manage pricing/staff/roles if implemented.
- [ ] Pending booking expiry releases held seats.
- [ ] Idempotency prevents duplicate submit if implemented.

## Simulator Checklist

- [ ] Uses `ExecutorService`.
- [ ] Uses `CountDownLatch`.
- [ ] Supports `NO_LOCK`.
- [ ] Supports `SYNCHRONIZED`.
- [ ] Supports `FILE_LOCK`.
- [ ] Supports `OPTIMISTIC`.
- [ ] Can run high contention.
- [ ] Can run medium contention.
- [ ] Can run low contention.
- [ ] Records total attempts.
- [ ] Records successful bookings.
- [ ] Records failed bookings.
- [ ] Records conflict count.
- [ ] Records double booking count.
- [ ] Records execution time.
- [ ] Calculates throughput.
- [ ] Calculates double booking rate.
- [ ] Exports or displays result table.
- [ ] Simulator calls booking core only.
- [ ] Simulator does not run online payment flow.

## Document/Report Checklist

- [ ] SRS completed.
- [ ] Use case diagrams completed.
- [ ] Use case diagrams separate Fan/Seller checkout from Booking Core.
- [ ] Use case specifications completed.
- [ ] Feature backlog completed.
- [ ] Architecture/layering documented.
- [ ] Domain data model documented.
- [ ] CSV schema documented.
- [ ] Booking flowchart completed.
- [ ] Synchronization flowchart completed.
- [ ] Simulator flowchart completed.
- [ ] UML class diagram completed.
- [ ] Test plan completed.
- [ ] Performance result recorded.
- [ ] Throughput chart/table completed.
- [ ] Double booking rate chart/table completed.
- [ ] AI reflection completed.
- [ ] AI logs per member included if required.
- [ ] Prompt audit completed for important AI interactions.
- [ ] AI Reflection >=500 words per member if required by teacher.

## Diagram Checklist

### UML Class Diagram

Must show:

- Attributes.
- Methods.
- Visibility.
- Inheritance.
- Associations.
- Multiplicity.
- `BaseEntity`.
- `CsvRepository<T>`.
- Booking/BookingItem/Ticket relation.
- Synchronization strategy interface and implementations.

### Flowcharts

Required:

- Booking Flow.
- Synchronization / Double Booking Prevention Flow.
- Simulator Flow.

Optional:

- Data Generation Flow.
- Payment Reconciliation Flow.
- Seat Hold Expiry Flow.
- Ticket Check-in Flow.

### Use Case Diagrams

Recommended split:

- Guest & Fan Booking.
- Staff Operations.
- Administration.
- Concurrency Simulator.

If only one diagram is allowed, draw compact overview and put detailed relationship in use case spec.

## Submission Package

Expected:

```text
NHOM_XX_LAB211_TicketBooking.zip
  src/
  data/
  docs/
  ai_logs/
  README.md
```

Before zip:

- [ ] Remove IDE/private files if not needed.
- [ ] Ensure README run commands are correct.
- [ ] Ensure data files are included or generator instructions are clear.
- [ ] Run compile once.
- [ ] Run simulator demo once.
- [ ] Check no absolute path hard-coded.
- [ ] Check `ai_logs/` contains each member AI Log.
- [ ] Check AI Reflection is included in report.
- [ ] Check ZIP contains `src/`, `data/`, `docs/`, `ai_logs/`, `README.md`.

## T10 Checklist - AI Reflection And Nop

- [ ] Tong hop AI Log ca nhan tung thanh vien.
- [ ] Moi AI Log co prompt goc va tom tat output.
- [ ] Moi AI Log ghi phan accepted/rejected/modified.
- [ ] Audit prompt quan trong, nhat la prompt ve SRS, Use Case, architecture, concurrency va code.
- [ ] Ghi loi AI tung mac, vi du nham `Create Booking` checkout voi `Execute Booking Core`.
- [ ] Ghi cach nhom verify output AI bang doc de bai, review logic, compile/test.
- [ ] Viet AI Reflection >=500 tu/nguoi trong report.
- [ ] Review code toan bo.
- [ ] Fix bug con lai.
- [ ] Polish console UI/menu/error message.
- [ ] Dong goi ZIP nop hoan chinh.
