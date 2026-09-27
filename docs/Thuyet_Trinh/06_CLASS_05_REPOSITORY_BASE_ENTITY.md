# Class Diagram trang 5 - CSV Repository và BaseEntity

## Mục đích của trang

**Lời thuyết trình:**

Trang năm trình bày hạ tầng dùng chung: cách lưu CSV, lớp cha của entity, data
generator và custom exception. Đây là phần hỗ trợ cho tất cả domain phía trên.

## CsvRepository

**Lời thuyết trình:**

`CsvRepository<T>` là abstract generic class chứa `filePath` và các thao tác
chung như `findAll`, `findById`, `findByCondition`, `save`, `update` và `delete`.
Hai hàm `parseLine` và `formatLine` được class con override vì mỗi entity có cấu
trúc CSV khác nhau.

Cách thiết kế này giúp tái sử dụng phần đọc ghi file nhưng vẫn giữ logic chuyển
đổi dữ liệu tại đúng repository. Model không tự đọc hoặc ghi CSV.

## Repository theo domain

**Lời thuyết trình:**

Các repository thông thường kế thừa `CsvRepository`, ví dụ Stadium, Section,
Seat, Fan, Staff, Match, Booking, Payment, Ticket, Transaction, Refund, Audit và
Simulation. Mỗi repository bổ sung truy vấn đúng domain như tìm Fan theo email,
tìm Section theo stadium, tìm Ticket theo code hoặc tìm Booking theo fan.

`MatchSeatRepository` và `TicketPriceRepository` có contract chuyên biệt vì
khóa nghiệp vụ của chúng liên quan đến hai id. `MatchSeatRepository` còn có bốn
phương thức update tương ứng với bốn cơ chế simulation: không lock,
synchronized, file lock và update khi version khớp.

## BaseEntity

**Lời thuyết trình:**

`BaseEntity` là abstract class chỉ giữ id và getter chung. Các entity như User,
Stadium, Section, Seat, Match, Booking, Payment, Ticket và Simulation kế thừa
lớp này. Những thuộc tính riêng và business method vẫn nằm trong từng entity.

Điểm cần phân biệt là kế thừa `BaseEntity` dùng để tái sử dụng identity, còn quan
hệ Stadium chứa Section hoặc Booking chứa Ticket là composition hoặc association,
không phải kế thừa.

## DataGenerator

**Lời thuyết trình:**

`DataGenerator` tạo stadium, section, seat, fan, staff và match theo thứ tự để
đảm bảo foreign key logic. Mục tiêu là sinh ít nhất mười nghìn dòng dữ liệu,
trong đó seat hoặc match-seat chiếm phần lớn. Data generator chỉ chuẩn bị dữ
liệu, không thay thế repository trong các use case thông thường.

## Exception

**Lời thuyết trình:**

Các lỗi nghiệp vụ dùng chung `AppException` kế thừa `RuntimeException`.
`AppException` giữ một `ErrorCode` để phân loại lỗi, ví dụ `INVALID_INPUT`,
`NOT_FOUND` hoặc `UNKNOWN_ERROR`. Khi có thêm nghiệp vụ, enum có thể bổ sung mã
như seat conflict, payment failed hoặc CSV error mà không cần tạo quá nhiều class
exception nhỏ. Với ứng dụng Console, exception không cần HTTP status.

View hoặc điểm điều phối cao nhất sẽ catch lỗi để in thông báo thân thiện. Service
có thể throw lỗi ngay tại nơi phát hiện thay vì đặt try-catch ở mọi câu lệnh.

## Ý chốt của trang

Trang này tạo nền dùng chung, tránh lặp CRUD và bảo đảm toàn bộ truy cập file đi
qua Repository. Đồng thời nó cung cấp đúng các primitive cần thiết để thử nghiệm
concurrency trên CSV.

## Mối quan hệ UML trên trang 5

### Repository kế thừa CsvRepository

Ký hiệu `--|>` là inheritance. Các class con tái sử dụng CRUD và bắt buộc cung
cấp cách `parseLine` và `formatLine` phù hợp entity.

- `FanRepository --|> CsvRepository<T>`: thêm tìm Fan theo email và keyword.
- `StaffRepository --|> CsvRepository<T>`: thêm tìm Staff theo email và keyword.
- `SeatRepository --|> CsvRepository<T>`: thêm tìm Seat theo Section.
- `MatchRepository --|> CsvRepository<T>`: thêm truy vấn trận sắp diễn ra.
- `TicketRepository --|> CsvRepository<T>`: thêm tìm theo booking, code và Fan.
- `StadiumRepository --|> CsvRepository<T>`: thêm tìm Stadium theo tên.
- `SectionRepository --|> CsvRepository<T>`: thêm tìm Section theo Stadium.
- `BookingRepository --|> CsvRepository<T>`: thêm tìm Booking theo Fan.
- `PaymentRepository --|> CsvRepository<T>`: thêm tìm Payment theo Booking.
- `SimulationRepository --|> CsvRepository<T>`: thêm tìm Simulation theo Match.
- `SimulationResultRepository --|> CsvRepository<T>`: thêm tìm và export result.
- `TransactionRepository --|> CsvRepository<T>`: thêm tìm transaction theo
  Simulation.
- `RefundRepository --|> CsvRepository<T>`: thêm tìm theo Fan và trạng thái chờ.
- `AuditLogRepository --|> CsvRepository<T>`: thêm append và lấy log gần nhất.

`MatchSeatRepository` và `TicketPriceRepository` không nối kế thừa trong sơ đồ
vì hai class có khóa ghép và thao tác update chuyên biệt. Chúng vẫn tuân thủ quy
tắc Repository nhưng không buộc phải dùng CRUD theo một id giống class generic.

### Entity kế thừa BaseEntity

Các đường `--|>` dưới đây là inheritance để tái sử dụng `id`, không phải quan hệ
nghiệp vụ giữa các entity.

- `User --|> BaseEntity`: tài khoản có id.
- `Fan --|> User`: Fan là một loại User và có thêm rule mua vé.
- `Staff --|> User`: Staff là một loại User và có role vận hành.
- `Stadium --|> BaseEntity`: Stadium có id để CRUD.
- `Section --|> BaseEntity`: Section có id riêng ngoài `stadiumId`.
- `Seat --|> BaseEntity`: Seat có id riêng ngoài vị trí hàng-số.
- `Match --|> BaseEntity`: Match có identity riêng.
- `MatchSeat --|> BaseEntity`: trạng thái ghế theo trận có record id riêng.
- `Payment --|> BaseEntity`: mỗi payment record có id.
- `Ticket --|> BaseEntity`: mỗi ticket có id bên cạnh ticket code.
- `BookingTransaction --|> BaseEntity`: mỗi attempt cần id để audit.
- `Simulation --|> BaseEntity`: mỗi cấu hình simulation có id.
- `SimulationResult --|> BaseEntity`: mỗi kết quả có id để export và so sánh.
- `Booking --|> BaseEntity`: mỗi booking có id.
- `RefundRequest --|> BaseEntity`: mỗi request có id để Admin review.
- `AuditLog --|> BaseEntity`: mỗi log entry có id.

### DataGenerator dependency

- `DataGenerator ..> CsvRepository<T>`: dependency nét đứt. DataGenerator chỉ sử
  dụng Repository khi sinh dữ liệu, không sở hữu và không phải một Repository.
  Vì vậy không dùng association mạnh hoặc inheritance.

### Exception inheritance và association

- `RuntimeException <|-- AppException`: inheritance. `AppException` là một loại
  unchecked exception, nên Service có thể throw tại nơi phát hiện lỗi mà không
  phải khai báo `throws` ở mọi method.
- `AppException --> ErrorCode`: association một chiều. Mỗi exception giữ một mã
  lỗi để View biết loại lỗi và in thông báo; `ErrorCode` không phụ thuộc ngược
  vào exception.

Cách này được chọn để dự án Console nhỏ có một cơ chế lỗi thống nhất. Ví dụ,
không tìm thấy sân có thể dùng
`new AppException(ErrorCode.NOT_FOUND, "Stadium not found")`.
