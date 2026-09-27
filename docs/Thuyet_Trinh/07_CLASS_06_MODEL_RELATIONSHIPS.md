# Class Diagram trang 6 - Quan hệ giữa các Model

## Mục đích của trang

**Lời thuyết trình:**

Trang cuối bỏ bớt các layer kỹ thuật và chỉ tập trung vào entity, multiplicity
và quan hệ nghiệp vụ. Đây là trang dùng để giải thích toàn bộ data model từ trái
qua phải và từ cấu trúc sân đến booking rồi simulation.

## User, Fan và Staff

**Lời thuyết trình:**

`Fan` và `Staff` kế thừa `User`. User giữ thông tin chung; Fan bổ sung quy tắc
giới hạn vé, còn Staff được phân quyền bằng `UserRole`. Hệ thống không cần tạo
class Seller, Admin hay Gate Staff riêng khi các vai trò này chủ yếu khác nhau
ở quyền truy cập use case.

## Cấu trúc sân vận động

**Lời thuyết trình:**

Một `Stadium` composition từ không đến nhiều `Section`; mỗi `Section` composition
từ không đến nhiều `Seat`. Section và Seat không có ý nghĩa độc lập nếu sân hoặc
khu vực cha bị xóa trong trường hợp chưa có dữ liệu giao dịch liên quan.

Mỗi `Match` diễn ra tại đúng một Stadium, còn một Stadium có thể tổ chức nhiều
Match. Một Match tạo ra nhiều `MatchSeat`; mỗi MatchSeat trỏ tới đúng một Seat
vật lý. Đây là cách tách vị trí ghế khỏi trạng thái bán theo từng trận.

`TicketPrice` liên kết một Match với một Section. Vì vậy giá được áp dụng theo khu
vực và có thể thay đổi giữa các trận.

## Booking, Payment và Ticket

**Lời thuyết trình:**

Mỗi `Booking` thuộc về một Fan và một Match. Một booking chứa từ một đến bốn
MatchSeat, đúng với giới hạn tối đa bốn vé mỗi giao dịch. Booking có tối đa một
Payment và composition tối đa bốn Ticket.

Mỗi Ticket trỏ tới một MatchSeat, nhờ vậy có thể xác định chính xác vé dành cho
ghế nào ở trận nào. Booking còn liên kết với một hoặc nhiều
`BookingTransaction` để ghi lại quá trình xử lý và kết quả.

## Refund và Audit

**Lời thuyết trình:**

`RefundRequest` luôn thuộc về một Booking và một Fan. Nó có thể chưa có Staff
review khi mới tạo; sau khi Admin xử lý thì liên kết reviewed-by được xác định.
`AuditLog` trỏ tới User đã thực hiện hành động để truy vết các thao tác quản trị.

## Simulation

**Lời thuyết trình:**

Mỗi `Simulation` chạy trên một Match. Một Simulation có nhiều
`SimulationResult`, thường tương ứng với các synchronization mechanism được so
sánh. Simulation cũng có nhiều `BookingTransaction`, là dữ liệu gốc để đếm
success, failure, conflict và double booking.

## Giải thích ký hiệu

**Lời thuyết trình:**

Mũi tên tam giác rỗng là inheritance. Hình thoi đặc là composition, nghĩa là đối
tượng con thuộc vòng đời của đối tượng cha trong mô hình. Mũi tên thường là
association. Các số `1`, `0..1`, `0..*`, `1..4` thể hiện multiplicity. Ví dụ
`1..4` ở quan hệ Booking với MatchSeat chính là business rule tối đa bốn ghế.

## Ý chốt của trang

Trang này xác nhận các foreign key logic cần có trong CSV và cho thấy vì sao
`MatchSeat` là entity trung tâm nối cấu trúc sân, booking, ticket và simulation.

## Giải thích đầy đủ từng relationship

### Inheritance

- `Fan --|> User`: Fan là một loại User. Dùng inheritance vì Fan dùng toàn bộ
  thông tin và hành vi tài khoản, sau đó bổ sung giới hạn ticket.
- `Staff --|> User`: Staff là một loại User. Seller, Gate Staff, Admin và
  Simulator Operator được xác định qua role của Staff thay vì lặp thuộc tính ở
  nhiều class con.

### Stadium, Section, Seat, Match

- `Stadium "1" *-- "0..*" Section`: composition. Một Stadium có thể chưa có
  Section hoặc có nhiều Section; một Section thuộc đúng một Stadium.
- `Section "1" *-- "0..*" Seat`: composition. Một Section có nhiều Seat; Seat là
  thành phần cấu trúc bên trong Section.
- `Match "0..*" --> "1" Stadium`: association. Mỗi Match cần đúng một Stadium,
  còn Stadium có thể tổ chức nhiều Match. Stadium tồn tại độc lập nên không dùng
  composition.
- `Match "1" *-- "0..*" MatchSeat`: composition. Mỗi Match sở hữu tồn kho ghế
  riêng; trước khi khởi tạo có thể là 0, sau đó có nhiều MatchSeat.
- `MatchSeat "0..*" --> "1" Seat`: association. Mỗi MatchSeat tham chiếu một Seat
  vật lý, còn cùng Seat có thể xuất hiện trong nhiều Match khác nhau.
- `TicketPrice "0..*" --> "1" Match`: association. Mỗi TicketPrice thuộc một
  Match; một Match có nhiều giá theo các khu vực.
- `TicketPrice "0..*" --> "1" Section`: association. Mỗi TicketPrice thuộc một
  Section; một Section có thể có giá khác nhau ở nhiều Match. Hai association
  này tạo khóa nghiệp vụ `matchId + sectionId`.

### Booking, Payment, Ticket, Transaction

- `Booking "0..*" --> "1" Fan`: association. Mỗi Booking thuộc một Fan; một Fan
  có thể có không hoặc nhiều Booking.
- `Booking "0..*" --> "1" Match`: association. Mỗi Booking đặt vé cho đúng một
  Match; một Match có nhiều Booking.
- `Booking "0..1" --> "1..4" MatchSeat`: association có multiplicity nghiệp vụ.
  Một Booking chọn từ một đến bốn MatchSeat; một MatchSeat nằm trong tối đa một
  Booking thành công. `0..1` bảo vệ quy tắc không bán trùng ghế.
- `Booking "1" --> "0..1" Payment`: association. Một Booking có thể chưa có
  Payment hoặc có tối đa một Payment chính trong scope; mỗi Payment thuộc một
  Booking.
- `Booking "1" *-- "0..4" Ticket`: composition. Một Booking có thể chưa phát
  hành Ticket hoặc có tối đa bốn Ticket; Ticket là kết quả thuộc Booking.
- `Booking "0..1" --> "1..*" BookingTransaction`: association. Một Booking khi
  hình thành có ít nhất một transaction, trong khi attempt thất bại có thể có
  transaction nhưng chưa tạo được Booking. Vì cần giữ attempt thất bại nên không
  dùng composition.
- `Ticket "0..1" --> "1" MatchSeat`: association. Mỗi Ticket dành cho đúng một
  MatchSeat; một MatchSeat có tối đa một Ticket hiện hành, hỗ trợ chống vé trùng.

### Refund và Audit

- `RefundRequest "0..*" --> "1" Booking`: mỗi request phải gắn với một Booking;
  Booking có thể có lịch sử request và Service kiểm soát request hợp lệ.
- `RefundRequest "0..*" --> "1" Fan`: mỗi request thuộc một Fan; Fan có thể gửi
  nhiều request cho các Booking khác nhau.
- `RefundRequest "0..*" --> "0..1" Staff`: request mới có thể chưa được ai xử
  lý; sau review nó tham chiếu tối đa một Staff/Admin.
- `AuditLog "0..*" --> "1" User`: mỗi log ghi đúng User thực hiện hành động; một
  User có thể tạo nhiều log. Không dùng composition để log vẫn được giữ phục vụ
  audit dù account đổi trạng thái.

### Simulation

- `Simulation "0..*" --> "1" Match`: mỗi Simulation chạy trên đúng một Match;
  một Match có thể được dùng trong nhiều lần thử nghiệm.
- `Simulation "1" --> "0..*" SimulationResult`: một Simulation có thể chưa chạy
  hoặc có nhiều result khi so sánh mechanism.
- `Simulation "1" --> "0..*" BookingTransaction`: một Simulation tạo nhiều
  attempt; mỗi attempt được lưu thành BookingTransaction để tính metric.

### Vì sao trang này không nối DTO, Controller và Repository?

Trang 6 cố ý chỉ thể hiện domain relationship và multiplicity. DTO và các layer
kỹ thuật đã được giải thích ở trang 1 đến 5. Nếu đưa chúng vào đây, các dependency
line sẽ che mất quan hệ nghiệp vụ quan trọng giữa entity.
