Thứ Tư 8:52
LAB211_TicketBooking_De_Tai.docx(1).md
Tệp
nó đủ yêu cầu đề bài này chưa , nếu đủ thì gửi tôi file readme

Được. Dựa trên README mới nhất, nếu bạn muốn tự vẽ Use Case Diagram thì tôi khuyên không nhét tất cả use case vào một sơ đồ duy nhất, vì sẽ rất rối. Nên chia thành 4 sơ đồ Use Case: Public/Fan Booking, Staff Operations, Administration, và Concurrency Simulator.

1. Actor hierarchy tổng thể

Bạn vẽ actor trước như sau:

Guest
  ▲
  │ generalization
 Fan


             Staff
          /    |     \
         /     |      \
    Seller  Support  Administrator


Simulator Operator

Payment Service

Ý nghĩa:

Fan là actor chuyên biệt hơn Guest, nên Fan có các quyền xem mà Guest có.
Seller, Support Staff, Administrator đều là các loại Staff.
Payment Service là external actor phục vụ online payment.
Simulator Operator đứng riêng.
2. Use Case Diagram 1 — Guest & Fan Booking

Đây là sơ đồ quan trọng nhất.

Actors
Guest
Fan
Payment Service
Use Cases

Bạn vẽ trong system boundary:

View Match List
Search Match
View Match Detail
View Stadium Information
View Section
View Seat Map
View Seat Availability

Register
Login
Logout

Select Seats
Review Booking
Create Booking
Validate Booking
Check Seat Availability
Lock Seats
Make Online Payment
Confirm Booking
Create Ticket
Record Transaction

View My Tickets
View Ticket Detail
View Booking History
Association
Guest nối với
Guest ── View Match List
Guest ── Search Match
Guest ── View Match Detail
Guest ── View Stadium Information
Guest ── View Section
Guest ── View Seat Map
Guest ── View Seat Availability
Guest ── Register
Guest ── Login

Guest không nối trực tiếp với Create Booking.

Fan nối với

Vì Fan kế thừa Guest nên không cần nối lại toàn bộ chức năng Guest nếu bạn đã dùng actor generalization.

Fan nối với:

Fan ── Logout
Fan ── Select Seats
Fan ── Review Booking
Fan ── Create Booking
Fan ── Make Online Payment
Fan ── View My Tickets
Fan ── View Ticket Detail
Fan ── View Booking History
3. Include relationship trong Fan Booking

Đây là phần rất quan trọng.

Bạn vẽ:

Create Booking
    |
    | <<include>>
    v
Validate Booking

Tiếp tục:

Create Booking
    <<include>> Check Seat Availability
Create Booking
    <<include>> Lock Seats
Create Booking
    <<include>> Make Online Payment
Create Booking
    <<include>> Confirm Booking
Confirm Booking
    <<include>> Create Ticket
Confirm Booking
    <<include>> Record Transaction

Full flow:

                    Create Booking
                   /      |       \
                  /       |        \
       <<include>>   <<include>>   <<include>>
              /           |              \
      Validate Booking   Check Seat      Lock Seats
                           Availability
                               |
                               |
                     <<include>>
                               |
                    Make Online Payment
                               |
                     <<include>>
                               |
                      Confirm Booking
                       /          \
                      /            \
             <<include>>        <<include>>
                /                    \
          Create Ticket         Record Transaction

Nhưng để sơ đồ đỡ rối, bạn có thể làm:

Create Booking
  <<include>> Validate Booking
  <<include>> Check Seat Availability
  <<include>> Lock Seats
  <<include>> Make Online Payment
  <<include>> Confirm Booking

Confirm Booking
  <<include>> Create Ticket
  <<include>> Record Transaction
4. Payment Service relationship

External actor:

Payment Service

nối với:

Make Online Payment

Ví dụ:

Fan ---------------- Make Online Payment ---------------- Payment Service

Payment Service trả trạng thái:

SUCCESS
FAILED
CANCELLED
EXPIRED

Không cần biến các trạng thái này thành use case.

5. Login relationship

Không nên vẽ:

Create Booking
<<include>> Login

vì login không phải lúc nào cũng diễn ra trong booking.

Tốt hơn là coi:

Preconditions của Create Booking: Fan đã authenticated.

Guest khi muốn mua vé:

Guest
→ Login
→ trở thành Fan
→ Create Booking

Use Case Diagram không cần thể hiện từng bước như flowchart.

6. Use Case Diagram 2 — Seller

Actor:

Seller

Seller là con của:

Staff
Seller Use Cases
Staff Login
Search Match
View Match Detail
View Section
View Seat Map
View Seat Availability

Search Fan
Create Fan
Select Fan

Select Seats for Customer
Review Customer Booking
Create Booking for Fan

Accept Offline Payment
Confirm Offline Payment
Issue Ticket

Search Ticket
View Seller Transaction
7. Seller relationships

Association:

Seller ── Staff Login

Seller ── Search Match
Seller ── View Match Detail
Seller ── View Seat Map

Seller ── Search Fan
Seller ── Create Fan
Seller ── Select Fan

Seller ── Create Booking for Fan
Seller ── Accept Offline Payment
Seller ── Issue Ticket

Seller ── Search Ticket
Seller ── View Seller Transaction
8. Seller Create Booking relationship

Quan trọng nhất:

Create Booking for Fan
    <<include>> Select Fan
Create Booking for Fan
    <<include>> Select Seats for Customer
Create Booking for Fan
    <<include>> Validate Booking
Create Booking for Fan
    <<include>> Check Seat Availability
Create Booking for Fan
    <<include>> Lock Seats
Create Booking for Fan
    <<include>> Accept Offline Payment
Accept Offline Payment
    <<include>> Confirm Offline Payment
Create Booking for Fan
    <<include>> Issue Ticket

Thực tế logic bên dưới phải là:

Fan Online Booking ──────┐
                         │
                         ├─ Shared Booking Engine
                         │
Seller Counter Booking ──┘

Trong Use Case Diagram, bạn có thể thể hiện bằng cách cho cả:

Create Booking
Create Booking for Fan

đều <<include>>

Validate Booking
Check Seat Availability
Lock Seats
Confirm Booking
Create Ticket
Record Transaction
9. Create Fan của Seller

Bạn có thể dùng <<extend>>.

Create Fan
   <<extend>>
Search Fan

Ý nghĩa:

Seller search Fan trước.

Nếu:

Fan not found

thì mới:

Create Fan

Vậy đây là optional flow.

Nên vẽ:

Create Fan
     --------<<extend>>-------> Search Fan

Condition:

[Fan not found]
10. Use Case Diagram 3 — Support Staff

Actor:

Support Staff

Use Cases:

Staff Login

Search Fan
View Fan Information

Search Ticket
View Ticket Detail

Search Booking Transaction
View Booking Transaction

Search Payment Transaction
View Payment Transaction

View Fan Booking History

Check Seat Status
Check Booking Status
Check Payment Status

Assist Failed Booking
Assist Missing Ticket
Assist Payment Issue
Assist Seat Issue
11. Support relationships

Support Staff nối trực tiếp với các use case chính:

Support Staff ── Search Fan
Support Staff ── Search Ticket
Support Staff ── Search Booking Transaction
Support Staff ── Search Payment Transaction

Support Staff ── Assist Failed Booking
Support Staff ── Assist Missing Ticket
Support Staff ── Assist Payment Issue
12. Support include relationships
Assist Failed Booking
Assist Failed Booking
    <<include>> Search Fan
    <<include>> Search Booking Transaction
    <<include>> Check Booking Status
    <<include>> Check Seat Status
Assist Missing Ticket
Assist Missing Ticket
    <<include>> Search Fan
    <<include>> Search Booking Transaction
    <<include>> Search Ticket
    <<include>> Check Booking Status
Assist Payment Issue
Assist Payment Issue
    <<include>> Search Fan
    <<include>> Search Booking Transaction
    <<include>> Search Payment Transaction
    <<include>> Check Payment Status
    <<include>> Search Ticket
13. Edge case của Support không cần thành use case riêng hết

Ví dụ:

Payment SUCCESS but Ticket missing

Không cần vẽ:

Handle Payment Success But Ticket Missing

thành oval riêng.

Nó là:

Alternative Flow / Edge Case

của:

Assist Payment Issue

Tương tự:

Seat BOOKED but Ticket missing
Ticket exists but Seat AVAILABLE
Duplicate Ticket

nên để trong specification, không nên nhét vào diagram.

14. Use Case Diagram 4 — Administrator

Actor:

Administrator

Admin kế thừa:

Staff
Use Cases
Staff Login

Manage Stadium
Manage Section
Manage Seat
Manage Match
Manage Fan

Search Ticket
View Ticket Detail

Search Booking Transaction
View Booking Transaction

Search Payment Transaction
View Payment Transaction

Generate CSV Data
Validate CSV Data
Load CSV Data

View System Summary
15. CRUD của Admin nên vẽ như nào?

Có 2 cách.

Cách 1 — Diagram tổng quát

Tôi khuyên dùng cách này để trình bày.

Administrator ── Manage Stadium
Administrator ── Manage Section
Administrator ── Manage Seat
Administrator ── Manage Match
Administrator ── Manage Fan

Không bung CRUD trên Use Case Diagram.

Trong SRS mới ghi:

Manage Match
├── Create Match
├── Read Match
├── Update Match
├── Delete Match
└── Search Match
Cách 2 — Diagram chi tiết

Nếu thầy muốn chi tiết thì:

Manage Match
    <<include>> Create Match
    <<include>> View Match
    <<include>> Update Match
    <<include>> Delete Match
    <<include>> Search Match

Tương tự Seat/Fan.

Nhưng theo UML thuần thì CRUD không nhất thiết phải dùng <<include>>; đôi khi có thể vẽ riêng từng use case kết nối Admin.

Với bài thuyết trình tôi khuyên vẽ Manage X, rồi viết note:

Manage = CRUD + Search
16. Use Case Diagram 5 — Simulator

Đây là sơ đồ quan trọng nhất về technical.

Actor:

Simulator Operator

Use cases:

Configure Simulation
Configure Thread Count
Select Target Match
Select Target Seats
Select Contention Scenario

Select Synchronization Mechanism

Run Simulation

Create Fan Threads
Synchronize Thread Start
Execute Concurrent Booking

Apply Synchronization Strategy
Detect Conflict
Detect Double Booking

Collect Simulation Results
Calculate Metrics

View Simulation Result
Compare Synchronization Mechanisms
Export Simulation Result
View Simulation History
17. Simulation relationship

Bạn vẽ:

Run Simulation
    <<include>> Configure Simulation

Hoặc nếu configure luôn xảy ra trước đó nhưng không nhất thiết mỗi run:

Không bắt buộc include.

Tôi khuyên:

Configure Simulation
    <<include>> Configure Thread Count
    <<include>> Select Target Match
    <<include>> Select Target Seats
    <<include>> Select Synchronization Mechanism

Sau đó:

Run Simulation
    <<include>> Create Fan Threads
Run Simulation
    <<include>> Synchronize Thread Start
Run Simulation
    <<include>> Execute Concurrent Booking
Run Simulation
    <<include>> Collect Simulation Results
Run Simulation
    <<include>> Detect Double Booking
Run Simulation
    <<include>> Calculate Metrics
18. Execute Concurrent Booking

Phần này liên kết với booking core:

Execute Concurrent Booking
    <<include>> Apply Synchronization Strategy

và:

Apply Synchronization Strategy
    <<include>> Check Seat Availability

hoặc bạn có thể reuse use case:

Create Booking

Tức là:

Execute Concurrent Booking
    <<include>> Create Booking

Cách này tôi đánh giá đẹp nhất vì nó thể hiện:

Simulator không có booking logic riêng.

Nó vẫn gọi cùng booking core.

19. Compare mechanism
Compare Synchronization Mechanisms
    <<include>> View Simulation Result

Có thể thêm:

Compare Synchronization Mechanisms
    <<include>> Calculate Metrics

Nhưng Calculate Metrics đã nằm trong Run Simulation, nên không bắt buộc.

20. Export Result
Export Simulation Result
    <<extend>> View Simulation Result

Vì export là optional.

Không phải lần nào xem result cũng export.

Nên:

Export Simulation Result
      <<extend>>
View Simulation Result
21. Full actor-to-use-case mapping

Bạn có thể dùng bảng này để tự vẽ.

Actor	Use Cases
Guest	View Match List, Search Match, View Match Detail, View Stadium, View Section, View Seat Map, View Seat Availability, Register, Login
Fan	Guest capabilities + Select Seats, Review Booking, Create Booking, Online Payment, View Tickets, View Booking History
Seller	Search Match, View Seats, Search/Create Fan, Select Fan, Select Seat, Create Booking for Fan, Offline Payment, Issue Ticket, Search Ticket
Support Staff	Search Fan, Ticket, Booking, Payment; Check Booking/Payment/Seat; Assist Failed Booking, Missing Ticket, Payment Issue
Administrator	Manage Stadium, Section, Seat, Match, Fan; Search Ticket/Transaction; Generate/Validate CSV; View System Summary
Simulator Operator	Configure Simulation, Select Mechanism, Configure Threads, Run Simulation, View Result, Compare Mechanisms, Export Result
Payment Service	Process/Return Online Payment Result
22. Actor generalization nên vẽ

Bạn vẽ kiểu:

                Guest
                  ▲
                  │
                 Fan

Mũi tên tam giác rỗng hướng về actor cha:

Fan ─────▷ Guest

Và:

Seller ---------▷ Staff
Support Staff ---▷ Staff
Administrator ---▷ Staff
23. System boundary

Tên system boundary:

Stadium Ticket Booking & Concurrency Simulation System

Bạn vẽ hình chữ nhật lớn.

Actor ở ngoài.

Use case oval ở trong.

Ví dụ:

 Guest

   O
  /|\
  / \

        +-----------------------------------------------+
        | Stadium Ticket Booking System                |
        |                                               |
        |       (View Match List)                       |
        |       (View Seat Map)                         |
        |       (Register)                              |
        |       (Login)                                 |
        |                                               |
        |                     (Create Booking)          |
        |                        |                      |
        |                  <<include>>                  |
        |                        |                      |
        |                (Validate Booking)             |
        |                                               |
        +-----------------------------------------------+
24. Sơ đồ tôi khuyên bạn vẽ để nộp/thuyết trình
Diagram 1 — Customer Booking
Guest
Fan
Payment Service

Khoảng 15–18 use cases.

Diagram 2 — Staff Operations
Staff
Seller
Support Staff
Administrator

Khoảng 15 use cases.

Diagram 3 — Concurrency Simulator
Simulator Operator

Khoảng 10–12 use cases.

Nếu thầy chỉ cho 1 Use Case Diagram, thì vẽ version rút gọn:

Guest
├─ Browse Match
├─ View Seat
├─ Register
└─ Login

Fan
├─ Select Seat
├─ Book Ticket
├─ Online Payment
└─ View Ticket

Seller
├─ Search Fan
├─ Book for Fan
├─ Offline Payment
└─ Issue Ticket

Support
├─ Search Ticket
├─ Check Booking
├─ Check Payment
└─ Assist Issue

Administrator
├─ Manage Stadium
├─ Manage Section
├─ Manage Seat
├─ Manage Match
└─ Manage Fan

Simulator Operator
├─ Configure Simulation
├─ Run Simulation
├─ View Result
└─ Compare Mechanisms

Rồi use case chi tiết để ở các diagram con.

Quan hệ quan trọng nhất cần nhớ khi vẽ
Fan ─────────▷ Guest

Seller ──────▷ Staff
Support ─────▷ Staff
Admin ───────▷ Staff
Create Booking
   <<include>> Validate Booking
   <<include>> Check Seat Availability
   <<include>> Lock Seats
   <<include>> Payment
   <<include>> Confirm Booking
Confirm Booking
   <<include>> Create Ticket
   <<include>> Record Transaction
Create Fan
   <<extend>> Search Fan
   [Fan not found]
Assist Payment Issue
   <<include>> Search Fan
   <<include>> Search Booking Transaction
   <<include>> Search Payment Transaction
   <<include>> Check Payment Status
Run Simulation
   <<include>> Create Fan Threads
   <<include>> Synchronize Thread Start
   <<include>> Execute Concurrent Booking
   <<include>> Detect Double Booking
   <<include>> Calculate Metrics
Execute Concurrent Booking
   <<include>> Create Booking
Export Simulation Result
   <<extend>> View Simulation Result

Nếu bạn vẽ đúng bộ quan hệ trên thì Use Case Diagram đã thể hiện được gần như toàn bộ tư duy của README + SRS, đồng thời giải thích được với thầy vì sao Fan/Seller/Simulator đều dùng chung một booking core.

Chỉnh sửa
vẽ riêng cái này chi tiết đi 2. Use Case Diagram 1 — Guest & Fan Booking
chi tiết k sai cái nào chứ
Chỉnh sửa
Hôm nay 14:05
LAB211_TicketBooking_De_Tai.docx(3).md
Tệp
rồi rút kết lại toàn bộ, cho tôi các file document cần thiết để làm dự án này
à mà có thể thêm feature sao cho phù hợp của hệ thống bình thường của đặt vé nha , và phải có phân tầng rõ ràng , các feature hay có trong project dự án mà tiềm cận doanh nghiệp chứ k phải là project học ( do dự án tự học nó k xửl ý edge case á ) và simuilation thì vẫn vậy
vidIQ