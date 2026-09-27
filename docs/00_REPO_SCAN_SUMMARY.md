# Repo Scan Summary

## File da quet

Repo hien tai co cac file noi dung chinh:

- `README.md`
- `contextchatbotcu.md`

Ngoai ra co:

- `Document/` dang ton tai va truoc khi ghi tai lieu thi chua co file con.
- `.idea/` va `.DS_Store` la file moi truong/IDE, khong phai tai lieu nghiep vu.

## Noi dung cot loi rut ra tu README

Du an la **Stadium Ticket Booking Simulation** cho LAB211 - OOP with Java.

Trong tam ky thuat:

- Kien truc MVC.
- Persistence bang CSV.
- Dataset toi thieu 10,000 rows.
- Van de nghien cuu: ngan **Double Booking** khi nhieu Fan Threads dat cung mot ghe cung luc.
- Simulator so sanh cac synchronization mechanism:
  - `NO_LOCK`
  - `SYNCHRONIZED`
  - `FILE_LOCK`
  - `OPTIMISTIC`

Feature san co trong dinh huong:

- Guest/Fan xem match, stadium, section, seat map, availability.
- Fan dang ky/dang nhap, chon toi da 4 ghe/giao dich, dat ve, xem ticket, xem booking history.
- Staff extension gom Seller, Support Staff, Administrator.
- Seller dat ve ho khach va xac nhan thanh toan offline.
- Support tra cuu va ho tro su co booking/payment/ticket.
- Administrator CRUD stadium, section, seat, match, fan.

Business rules quan trong:

- Mot ghe da `BOOKED` khong duoc ban lai cho cung mot match.
- Fan chi duoc dat toi da 4 ve/giao dich.
- Seat lifecycle: `AVAILABLE -> LOCKED -> BOOKED`.
- Neu booking/payment fail truoc confirm: `LOCKED -> AVAILABLE`.
- `Match + Seat` chi co toi da 1 valid Ticket.
- Fan booking va Seller booking phai dung chung booking engine/synchronization logic.

## Noi dung cot loi rut ra tu contextchatbotcu

File cu tap trung vao Use Case Diagram, gom:

- Actor hierarchy:
  - `Fan` ke thua `Guest`.
  - `Seller`, `Support Staff`, `Administrator` ke thua `Staff`.
  - `Payment Service` la external actor.
  - `Simulator Operator` dung rieng.

Use case nen chia thanh nhieu diagram:

- Guest & Fan Booking.
- Staff Operations.
- Administration.
- Concurrency Simulator.

Quan he include/extend can chot theo ban production-like:

- `Create Booking` cua Fan la customer checkout flow, co payment.
- `Create Booking for Fan` cua Seller la counter checkout flow, co offline payment.
- `Execute Booking Core` la loi dung chung cho Fan, Seller va Simulator.
- Simulator phai include `Execute Booking Core`, khong include truc tiep `Create Booking`, vi simulator khong nen tao 1000 online payment flows.
- `Create Booking` include `Validate Booking`, `Calculate Booking Total`, `Execute Booking Core`, `Hold Seats`, `Make Online Payment`, `Confirm Booking`.
- `Execute Booking Core` include `Check Seat Availability`, `Apply Synchronization Strategy`, `Update Seat State`.
- `Confirm Booking` include `Create Ticket`, `Record Transaction`.
- `Create Fan` extend `Search Fan` voi condition `[Fan not found]`.
- `Run Simulation` include `Create Fan Threads`, `Synchronize Thread Start`, `Execute Concurrent Booking`, `Detect Double Booking`, `Calculate Metrics`.
- `Execute Concurrent Booking` include `Execute Booking Core`.

## Ket luan phan tich

Tai lieu hien co da du de xac dinh huong lam, nhung can tach thanh bo document ro rang hon:

- SRS de chot functional/non-functional requirements.
- Use case spec chi tiet de ve diagram va code theo actor.
- Feature backlog de biet cai nao bat buoc, cai nao mo rong.
- Architecture document de tranh dat business logic trong View.
- Data model/CSV schema de code repository on dinh.
- Booking/concurrency design de tranh double booking.
- Seat hold/expiry va idempotency de tranh lock treo va duplicate request.
- Edge case document de du an khong bi "project hoc" qua don gian.
- Test plan va checklist de demo/nop bai co bang chung.
