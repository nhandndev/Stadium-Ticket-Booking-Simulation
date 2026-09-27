# Class Diagram trang 3 - Booking, Payment, Ticket và Refund

## Mục đích của trang

**Lời thuyết trình:**

Trang ba là luồng nghiệp vụ chính của hệ thống. Nó nối các Use Case của Fan,
Seller và Gate Staff từ xem ghế đến booking, payment, ticket, check-in và refund.

## View và Controller

**Lời thuyết trình:**

`SeatMapView` hiển thị ghế của một trận. `BookingView` phục vụ Fan, gồm tạo
booking, xem lịch sử, xem ticket và gửi refund. `SellerView` dùng để tìm hoặc
tạo Fan, sau đó booking cho khách tại quầy. `GateView` dùng để validate và
check-in ticket.

Các View gọi `BrowseController`, `FanController`, `BookingController`,
`TicketController` và `RefundController`. Việc chia Controller theo use case
giúp luồng Fan, Seller và Gate cùng dùng lại Service mà không lặp business logic.

## BookingService

**Lời thuyết trình:**

`BookingService` là class điều phối chính. Nó kiểm tra Fan, giới hạn tối đa bốn
ghế, trạng thái mở bán, trạng thái active của ghế, giá vé và trạng thái
`MatchSeat`. Sau đó Service chọn `BookingStrategy`, tạo booking, xử lý payment,
cập nhật ghế, tạo ticket và ghi `BookingTransaction`.

Fan sử dụng `MockPaymentService` cho online payment. Seller sử dụng phương thức
offline payment. Hai luồng khác cách thanh toán nhưng dùng chung quy tắc booking
và cập nhật `MatchSeat`.

## Model chính

**Lời thuyết trình:**

`Booking` chứa fan, match, danh sách seat, tổng tiền và trạng thái. Model có hành
vi kiểm tra giới hạn vé, confirm hoặc cancel. `Payment` chứa booking, số tiền,
phương thức và trạng thái. `Ticket` gắn với đúng một match-seat, có mã ticket,
trạng thái và thời điểm check-in.

`BookingTransaction` ghi lại cả lần thành công lẫn thất bại, cơ chế đồng bộ, thời
gian chạy và lý do lỗi. Đây là dữ liệu quan trọng để audit và tính kết quả
simulation. `RefundRequest` lưu người gửi, booking, lý do, người review, thời
điểm và ghi chú review.

## Vai trò của MatchSeat

**Lời thuyết trình:**

`MatchSeat` là điểm trung tâm để chống double booking. `Seat` chỉ là ghế vật lý,
còn `MatchSeat` biểu diễn ghế đó trong một trận cụ thể. Trước khi booking, hệ
thống gọi `isAvailable`. Khi xử lý, ghế có thể được lock, mark booked hoặc
release. Trường version hỗ trợ optimistic locking.

## Luồng Fan

**Lời thuyết trình:**

Fan chọn ghế, View tạo `BookingRequestDto`, rồi gọi `BookingController`.
`BookingService` validate và tính tổng giá từ `TicketPriceRepository`. Service
thực hiện strategy để giữ quyền cập nhật ghế, tạo `Booking`, gọi mock payment.
Nếu payment thành công, booking được confirm, ghế chuyển booked và ticket được
tạo. Nếu thất bại, hệ thống đánh dấu payment thất bại và trả ghế về trạng thái
có thể đặt.

## Luồng Seller và Gate Staff

**Lời thuyết trình:**

Seller tìm hoặc tạo Fan rồi gửi booking request thay cho Fan. Sau khi nhận tiền
offline, hệ thống confirm booking và issue ticket. Gate Staff nhập mã ticket;
`TicketController` gọi `TicketService` để validate. Nếu ticket hợp lệ thì
`checkIn` lưu thời gian và đổi trạng thái, vì vậy cùng một ticket không thể qua
cổng hai lần.

## Ý chốt của trang

Trang này cho thấy booking, payment và ticket là ba model riêng nhưng được điều
phối trong một transaction nghiệp vụ. Quy tắc ghế chỉ có một nguồn là
`MatchSeat`, tránh Fan, Seller và Simulator cập nhật theo ba cách không thống nhất.

## Mối quan hệ UML trên trang 3

### View tới Controller

- `BookingView --> BookingController`: association một chiều để Fan gửi booking
  và xem kết quả mà View không tự xử lý nghiệp vụ.
- `BookingView --> RefundController`: association để cùng màn hình Fan có thể gửi
  và xem refund.
- `SeatMapView --> BrowseController`: association để lấy seat map từ application
  layer thay vì đọc CSV.
- `SellerView --> BookingController`: association vì Seller dùng lại booking use
  case thay vì có một booking engine riêng.
- `SellerView --> FanController`: association vì Seller cần tìm hoặc tạo Fan trước
  khi booking cho khách.
- `GateView --> TicketController`: association vì Gate chỉ thao tác qua các use
  case validate và check-in Ticket.

### Controller tới Service

- `BrowseController --> BrowseService`: delegate truy vấn Match và MatchSeat.
- `FanController --> FanService`: delegate đăng ký, tìm Fan và truy vấn dữ liệu Fan.
- `BookingController --> BookingService`: delegate toàn bộ workflow booking và
  payment.
- `TicketController --> TicketService`: delegate tìm, validate và check-in vé.
- `RefundController --> RefundService`: delegate gửi hoặc review refund.

Đây là association một chiều vì Controller giữ Service. Service không biết View
hay Controller nào đã gọi nó, nên cùng Service có thể được tái sử dụng.

### Service tới Repository và Helper

- `BrowseService --> MatchRepository`: đọc thông tin trận.
- `BrowseService --> MatchSeatRepository`: đọc trạng thái ghế theo trận.
- `FanService --> FanRepository`: tìm, kiểm tra trùng và lưu Fan.
- `FanService --> TicketRepository`: lấy ticket thuộc Fan.
- `BookingService --> BookingRepository`: lưu Booking và truy vấn lịch sử.
- `BookingService --> SeatRepository`: kiểm tra ghế vật lý có active hay không.
- `BookingService --> TicketPriceRepository`: lấy giá theo match-section để tính
  total amount.
- `BookingService --> PaymentRepository`: lưu kết quả online hoặc offline payment.
- `BookingService --> TicketRepository`: tạo Ticket sau khi booking hợp lệ.
- `BookingService --> StrategyFactory`: chọn cách cập nhật MatchSeat theo
  `SyncMechanism`, giúp Service không phụ thuộc class strategy cụ thể.
- `BookingService --> MockPaymentService`: dependency tới cổng thanh toán giả lập
  cho Fan online flow.
- `TicketService --> TicketRepository`: tìm Ticket theo code và lưu trạng thái
  check-in.
- `RefundService --> RefundRepository`: lưu request và tìm các request cần review.

Các Repository không gọi ngược Service. `StrategyFactory` và
`MockPaymentService` là helper dependency, không phải entity được Service sở hữu.

### Quan hệ giữa Model

- `Booking "0..*" --> "1" Fan`: association. Mỗi Booking thuộc đúng một Fan;
  một Fan có thể chưa có hoặc có nhiều Booking. Không dùng composition vì tài
  khoản Fan vẫn tồn tại độc lập với một Booking.
- `Booking "0..*" --> "1..4" MatchSeat`: association thể hiện một Booking chọn
  từ một đến bốn MatchSeat. Ở sơ đồ model tổng, đầu ngược lại được chốt là một
  MatchSeat thuộc tối đa một Booking thành công để chống double booking.
- `Booking "1" --> "0..1" Payment`: association một-một tùy chọn. Booking mới
  tạo có thể chưa thanh toán; khi payment được ghi nhận thì tối đa một Payment
  chính gắn với Booking trong scope đơn giản này.
- `Booking "1" *-- "0..4" Ticket`: composition. Ticket được sinh từ Booking và
  thuộc vòng đời nghiệp vụ của Booking; số ticket tối đa bằng giới hạn bốn ghế.
- `RefundRequest "0..*" --> "1" Booking`: association. Mỗi RefundRequest phải
  chỉ rõ một Booking; một Booking có thể được tham chiếu bởi request theo lịch sử
  và Service sẽ kiểm soát request nào còn hợp lệ.

### Vì sao Payment không composition trong sơ đồ này?

Payment là bản ghi tài chính cần được giữ để audit ngay cả khi Booking đổi trạng
thái, nên sơ đồ dùng association. Ticket được xem là kết quả trực tiếp của
Booking nên dùng composition.
