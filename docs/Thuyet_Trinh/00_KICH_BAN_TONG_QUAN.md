# Kịch bản thuyết trình tổng quan

## 1. Chào hỏi và giới thiệu đề tài

**Lời thuyết trình:**

Xin chào thầy và các bạn. Em là Đoàn Ngọc Nhân, mã sinh viên QE210282. Hôm nay
em xin trình bày đề tài Stadium Ticket Booking Simulation thuộc môn LAB211.

Đây là chương trình Java thuần chạy trên Console, áp dụng lập trình hướng đối
tượng, kiến trúc phân lớp, lưu trữ dữ liệu bằng file CSV và Java concurrency.
Đề tài vừa mô phỏng một hệ thống đặt vé sân vận động, vừa thực hiện thí nghiệm
để so sánh các giải pháp ngăn double booking khi nhiều người cùng đặt một ghế.

## 2. Phát biểu lại đề bài

**Lời thuyết trình:**

Theo yêu cầu của đề bài, hệ thống cần quản lý dữ liệu sân vận động và thực hiện
quy trình đặt vé. Dữ liệu không lưu bằng database mà được đọc và ghi bằng các
file CSV. Chương trình cần được tổ chức theo hướng đối tượng và phân tách các
thành phần giao diện Console, xử lý nghiệp vụ, truy cập dữ liệu và model.

Ngoài chức năng đặt vé thông thường, hệ thống phải mô phỏng trường hợp nhiều Fan
cùng đặt vé trong một thời điểm. Dự án cần tạo nhiều thread, đồng bộ thời điểm
bắt đầu và ghi lại kết quả để kiểm tra xem cùng một ghế trong cùng một trận có
bị đặt thành công nhiều lần hay không.

Đề bài cũng yêu cầu có dữ liệu đủ lớn để thử nghiệm. Dự án đặt mục tiêu sinh tối
thiểu mười nghìn dòng CSV, trong đó ghế hoặc tồn kho ghế theo trận chiếm phần
lớn. Phần concurrency sử dụng `ExecutorService` để quản lý các task và
`CountDownLatch` để cho các task bắt đầu gần cùng lúc.

Kết quả cuối cùng không chỉ cho biết booking thành công hay thất bại, mà còn phải
đo được số attempt, số success, số failure, conflict, double booking, thời gian
chạy và throughput. Từ các số liệu đó, dự án so sánh ưu và nhược điểm của từng
cơ chế đồng bộ.

## 3. Vấn đề thực tế cần giải quyết

**Lời thuyết trình:**

Vấn đề chính của bài toán xuất hiện khi hai hoặc nhiều người cùng nhìn thấy một
ghế đang available. Nếu các thread đều đọc trạng thái trước khi bất kỳ thread
nào kịp ghi lại kết quả, tất cả có thể cùng cho rằng mình được quyền đặt ghế.
Khi đó hệ thống có thể tạo nhiều booking hoặc nhiều ticket cho cùng một ghế của
cùng một trận. Hiện tượng này được gọi là double booking.

Quy tắc nhất quán quan trọng nhất của dự án là:

```text
matchId + seatId -> tối đa một booking thành công hoặc một ticket hợp lệ
```

Vì vậy, việc chỉ kiểm tra ghế ở lúc hiển thị seat map là chưa đủ. Hệ thống phải
kiểm tra lại và bảo vệ thao tác cập nhật `MatchSeat` tại thời điểm commit booking.

## 4. Cách em phân tích và xử lý bài toán

**Lời thuyết trình:**

Đầu tiên, em xác định actor và feature bằng Use Case Diagram. Việc này trả lời hai
câu hỏi: ai sử dụng hệ thống và mỗi người được làm gì. Scope sau khi tinh gọn gồm
Guest, Fan, Seller, Gate Staff, Administrator, Simulator Operator, User dùng
chung và Payment Service giả lập.

Tiếp theo, em phân chia hệ thống theo domain thay vì đặt toàn bộ class vào một
khối. Các domain chính gồm tài khoản, sân vận động, trận đấu, booking, payment,
ticket, refund, quản trị và simulation. Cách chia này giúp mỗi module có trách
nhiệm rõ ràng và có thể code lần lượt.

Sau đó, trong mỗi domain em áp dụng luồng dependency:

```text
View -> Controller -> Service -> Repository -> Model / CSV
                         |
                         +-> DTO
```

View chỉ hiển thị menu và nhận input. Controller nhận hành động từ View. Service
chứa validation và business rule. Repository chịu trách nhiệm đọc ghi CSV.
Model biểu diễn dữ liệu và hành vi cốt lõi. DTO mang dữ liệu request và response
giữa các layer.

Cuối cùng, em tách việc booking thông thường và việc simulation ở phần giao diện,
nhưng cả hai dùng chung `MatchSeat` và contract cập nhật ghế. Nhờ vậy simulation
đang kiểm tra đúng cùng một quy tắc dữ liệu mà hệ thống booking thật sử dụng.

## 5. Giải pháp cho double booking

**Lời thuyết trình:**

Để nghiên cứu double booking, dự án sử dụng Strategy Pattern. Tất cả cơ chế đồng
bộ cùng hiện thực `BookingStrategy` và cùng nhận một đầu vào gồm Fan, Match và
danh sách ghế. `StrategyFactory` chọn strategy dựa trên cấu hình simulation.

Dự án so sánh bốn cơ chế:

- `NO_LOCK` không bảo vệ critical section, được dùng làm baseline để quan sát
  race condition.
- `SYNCHRONIZED` khóa thao tác trong một JVM. Cách này đơn giản nhưng chỉ bảo vệ
  giữa các thread trong cùng process.
- `FILE_LOCK` khóa file khi cập nhật CSV. Cách này gần với cơ chế lưu trữ của bài
  nhưng có chi phí I/O và lock lớn hơn.
- `OPTIMISTIC` sử dụng trường `version`. Thread chỉ update khi version vẫn giống
  lúc đã đọc; nếu version thay đổi thì ghi nhận conflict hoặc retry giới hạn.

Mỗi `BookingTask` chờ trên cùng một `CountDownLatch`. Khi latch được mở, các task
gần như cùng lúc gọi strategy. Sau đó hệ thống lưu `BookingTransaction`, tổng hợp
`SimulationResult` và kiểm tra trùng theo cặp match-seat.

## 6. Dự án gồm những phần nào?

### 6.1 Account và Authentication

**Lời thuyết trình:**

Phần Account quản lý User, Fan, Staff, đăng ký, đăng nhập, đăng xuất, cập nhật
profile, trạng thái tài khoản và role. Sau khi login, hệ thống không hỏi người
dùng muốn vào role nào mà đọc `UserRole` đã lưu để mở đúng menu.

### 6.2 Stadium và Match

**Lời thuyết trình:**

Phần Stadium quản lý cấu trúc `Stadium -> Section -> Seat`. Phần Match quản lý
trận đấu, trạng thái mở bán, `TicketPrice` và `MatchSeat`.

`Seat` là ghế vật lý và có trạng thái active. `MatchSeat` là trạng thái của ghế
trong một trận cụ thể. Việc tách hai class này cho phép cùng một ghế được booked
ở trận A nhưng vẫn available ở trận B.

### 6.3 Guest và Fan Booking

**Lời thuyết trình:**

Guest có thể tìm, lọc, xem trận và xem ghế trước khi đăng ký. Fan có thể chọn từ
một đến bốn ghế, tạo booking, thanh toán online, xem lịch sử booking, xem ticket
và gửi yêu cầu refund.

Khi Fan xác nhận, `BookingService` phải kiểm tra lại trạng thái bán vé, ghế vật
lý, giá và `MatchSeat`. Thanh toán online được mô phỏng bằng
`MockPaymentService`, không tích hợp cổng thanh toán thật.

### 6.4 Staff Operations

**Lời thuyết trình:**

Seller hỗ trợ khách tại quầy bằng cách tìm hoặc tạo Fan, tạo booking cho Fan,
nhận offline payment và issue ticket. Gate Staff validate ticket và check-in;
mỗi ticket chỉ được check-in thành công một lần.

Administrator quản lý stadium, section, seat, match, giá vé, trạng thái mở bán,
tài khoản Fan, tài khoản Staff và role. Admin cũng xem system summary, audit log
và review refund request.

### 6.5 CSV Repository và Data Generator

**Lời thuyết trình:**

Mỗi domain có Repository chịu trách nhiệm parse và format entity thành dòng CSV.
`CsvRepository<T>` cung cấp CRUD chung, còn repository cụ thể bổ sung truy vấn
theo nghiệp vụ. `DataGenerator` tạo dữ liệu theo đúng thứ tự quan hệ để đạt quy
mô tối thiểu mười nghìn dòng.

### 6.6 Concurrency Simulation

**Lời thuyết trình:**

Simulator Operator chọn trận, ghế mục tiêu, số thread và mechanism. Hệ thống chạy
các task, đo kết quả, cho phép xem, so sánh và export. Ba kịch bản chính là nhiều
thread tranh một ghế, nhiều thread tranh một nhóm ghế nhỏ và nhiều thread đặt
các ghế khác nhau.

## 7. Tại sao chọn kiến trúc này?

**Lời thuyết trình:**

Đây là dự án Console nhỏ nên em không sử dụng framework hoặc tạo kiến trúc quá
nặng. Tuy nhiên, nếu để toàn bộ code trong `Main` thì menu, validation, business
rule, CSV và concurrency sẽ bị trộn lẫn, rất khó kiểm tra.

MVC kết hợp Service và Repository là mức phân tầng phù hợp. View có thể thay đổi
mà không làm thay đổi business rule. Service có thể được Fan, Seller và Simulator
dùng lại. Repository cô lập toàn bộ thao tác CSV. DTO giúp phân biệt dữ liệu
người dùng được nhập với entity được lưu, đồng thời tránh trả password ra View.

## 8. Những giới hạn em chủ động đặt ra

**Lời thuyết trình:**

Dự án là Java Console nên không có Spring, Web UI, REST API, HTTP status hay
database server. Payment Service chỉ là mock service. Hệ thống không gửi email
hoặc notification thật.

Sau khi giảm scope, Support Staff và Notification Service đã được loại bỏ. Em giữ
Seller, Gate Staff, Administrator và Simulator Operator vì các actor này trực
tiếp hỗ trợ luồng bán vé, kiểm tra vé, quản trị dữ liệu và mục tiêu concurrency
của đề bài.

Việc giới hạn scope giúp dự án vẫn có đầy đủ nghiệp vụ cần thiết nhưng có thể
hoàn thành và demo ổn định bằng Java thuần trong thời gian LAB211.

## 9. Các sản phẩm của dự án

**Lời thuyết trình:**

Dự án gồm source code Java, dữ liệu CSV, Use Case Diagram, sáu trang Class
Diagram Mermaid, tài liệu yêu cầu, business rule, thiết kế booking và simulation,
test plan, lộ trình triển khai và tài liệu thuyết trình.

Sáu trang Class Diagram lần lượt mô tả:

1. Main, View, authentication và Guest browsing.
2. Stadium, Match và các chức năng Administrator.
3. Booking, Payment, Ticket, Seller, Gate Staff và Refund.
4. Concurrency Simulation và Strategy Pattern.
5. CSV Repository, BaseEntity, DataGenerator và Exception.
6. Toàn bộ relationship và multiplicity giữa các Model.

Việc chia thành sáu trang giúp giải thích dễ hơn, nhưng các trang vẫn là những
góc nhìn của cùng một hệ thống và dùng chung model, enum cùng repository contract.

## 10. Trạng thái triển khai hiện tại

**Lời thuyết trình:**

Hiện tại phần thiết kế Use Case và Class Diagram đã xác định toàn bộ scope mục
tiêu. Source code đã có entry point, menu Console khung, `BaseEntity`,
`AppException`, `ErrorCode`, ba model `Stadium`, `Section`, `Seat` và hai DTO của
Stadium.

Source hiện compile và chạy được menu khung. Các chức năng nghiệp vụ, Repository,
Service, Controller, authentication, booking và simulation chưa được tuyên bố là
đã hoàn thành; chúng sẽ được code theo từng chặng dựa trên dependency của Class
Diagram.

Thứ tự triển khai là hoàn thiện Model và DTO, sau đó Repository CSV, Service,
Controller và cuối cùng nối View. Riêng simulation được làm sau khi `MatchSeat`
và booking core đã ổn định để tránh mô phỏng trên một nghiệp vụ chưa đúng.

## 11. Mục tiêu kết quả cuối cùng

**Lời thuyết trình:**

Khi hoàn thành, chương trình phải chạy được các use case theo đúng role, đọc ghi
CSV ổn định, không cho bán ghế không hợp lệ, không cho check-in ticket hai lần
và tạo được dữ liệu đủ lớn.

Phần simulation phải chạy được cùng một kịch bản với bốn mechanism, phát hiện
double booking và xuất các metric để so sánh. Kết quả mong đợi là `NO_LOCK` có
thể bộc lộ race condition, trong khi các cơ chế an toàn phải giữ double booking
bằng không; đổi lại mỗi cơ chế sẽ có chi phí thời gian và conflict khác nhau.

## 12. Dẫn sang Use Case

**Lời thuyết trình:**

Vừa rồi là phát biểu đề bài, vấn đề cần giải quyết và hướng thiết kế tổng thể của
dự án. Tiếp theo em sẽ trình bày Use Case Diagram để giải thích cụ thể từng actor
được tạo ra nhằm mục đích gì và từng feature của actor đó hoạt động như thế nào.

## 13. Kết luận ngắn dùng ở cuối bài

**Lời thuyết trình:**

Tóm lại, giải pháp của em không chỉ tạo một menu bán vé, mà xây dựng một mô hình
dữ liệu đủ để chứng minh vấn đề concurrency. `MatchSeat` là điểm chung giữa
booking thật và simulation; Strategy Pattern cho phép so sánh các cơ chế đồng bộ
trên cùng một contract; còn kiến trúc phân lớp giúp code Java Console vẫn rõ
trách nhiệm và có thể triển khai theo từng chặng.

