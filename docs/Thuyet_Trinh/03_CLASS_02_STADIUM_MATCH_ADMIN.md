# Class Diagram trang 2 - Stadium, Match và Administrator

## Mục đích của trang

**Lời thuyết trình:**

Trang hai hiện thực nhóm Use Case của Administrator. Do Admin có nhiều chức
năng, em không tạo một class khổng lồ mà chia theo trách nhiệm: quản lý sân,
quản lý trận, quản lý tài khoản, quản trị hệ thống và xét duyệt refund.

## View và các Controller

**Lời thuyết trình:**

`AdminManagementView` hiển thị các menu quản lý. Từ đây, thao tác sân, section
và seat đi vào `StadiumController`; thao tác match, giá vé và trạng thái bán vé
đi vào `MatchController`; tài khoản đi vào `AccountAdminController`; thống kê,
audit và tạo dữ liệu đi vào `AdminController`; refund đi vào `RefundController`.

Cách tách này làm cho mỗi Controller bám theo một nhóm Use Case và dễ code hơn
so với gom tất cả vào `AdminController`.

## StadiumService

**Lời thuyết trình:**

`StadiumService` phối hợp ba repository là `StadiumRepository`,
`SectionRepository` và `SeatRepository`. Service hỗ trợ CRUD sân, CRUD section,
CRUD seat, dựng seat map và active hoặc deactivate ghế.

Ba model tạo thành cấu trúc vật lý: một `Stadium` có nhiều `Section`, một
`Section` có nhiều `Seat`. `Seat.active` cho biết ghế vật lý có được sử dụng
hay không; nó khác với trạng thái đặt của ghế trong từng trận.

## MatchService

**Lời thuyết trình:**

`MatchService` quản lý trận đấu, giá vé và việc mở bán. `TicketPrice` được xác
định bằng cặp match và section, vì cùng một khu vực có thể có giá khác nhau ở
từng trận. Khi tạo trận, hệ thống có thể tạo các `MatchSeat` tương ứng với những
ghế đang active. Việc open hoặc close sales thay đổi `SaleStatus` của Match.

## AccountAdminService

**Lời thuyết trình:**

`AccountAdminService` quản lý Fan và Staff. Các hàm list, detail, search, filter,
update, activate và deactivate phản ánh trực tiếp Use Case. Riêng Staff có thêm
assign, change và revoke role. Request dùng `AccountRequestDto`, còn dữ liệu trả
ra dùng `AccountResponseDto` và không chứa password.

## AdminService và RefundService

**Lời thuyết trình:**

`AdminService` sử dụng các repository để tổng hợp số lượng Fan, Staff, Stadium,
Match, Booking và Ticket thành `SystemSummaryDto`. Nó cũng đọc audit log và gọi
`DataGenerator` để tạo dữ liệu CSV phục vụ yêu cầu ít nhất mười nghìn dòng.

`RefundService` cho phép Fan tạo yêu cầu và Admin review. Khi review, hệ thống
ghi admin xử lý, ghi chú và kết quả approve hoặc reject. Ở bước code thực tế,
approve refund phải kiểm tra booking hợp lệ trước khi cập nhật các trạng thái
liên quan.

## Luồng minh họa

**Lời thuyết trình:**

Ví dụ Admin tạo sân: View nhận tên và địa chỉ, tạo `StadiumRequestDto`, gọi
`StadiumController`, Controller gọi `StadiumService`. Service validate dữ liệu,
tạo `Stadium`, lưu qua `StadiumRepository`, sau đó trả
`StadiumResponseDto` về View.

## Ý chốt của trang

Trang này bao phủ toàn bộ Use Case Administrator nhưng vẫn giữ các class nhỏ,
đúng trách nhiệm và có thể triển khai lần lượt theo từng module.

## Mối quan hệ UML trên trang 2

### Nhóm View tới Controller

Các đường `-->` ở nhóm này là association một chiều vì
`AdminManagementView` giữ Controller để gọi chức năng, nhưng Controller không
phụ thuộc Console View.

- `AdminManagementView --> StadiumController`: mở các feature Stadium, Section
  và Seat.
- `AdminManagementView --> MatchController`: mở CRUD Match, Pricing và Ticket
  Sales.
- `AdminManagementView --> AccountAdminController`: quản lý Fan, Staff và role.
- `AdminManagementView --> AdminController`: xem summary, audit và sinh CSV.
- `AdminManagementView --> RefundController`: xem và xử lý refund request.

### Nhóm Controller tới Service

Các đường này là association một chiều. Mỗi Controller có đúng Service cùng
domain để Controller không chứa business rule.

- `StadiumController --> StadiumService`: delegate quản lý sân, khu vực và ghế.
- `MatchController --> MatchService`: delegate match, giá và mở/đóng bán vé.
- `AccountAdminController --> AccountAdminService`: delegate tài khoản và role.
- `AdminController --> AdminService`: delegate thống kê, audit và data generator.
- `RefundController --> RefundService`: delegate tạo hoặc review refund.

### Nhóm Service tới Repository

- `StadiumService --> StadiumRepository`: lưu và truy vấn Stadium.
- `StadiumService --> SectionRepository`: kiểm tra quan hệ sân-khu vực và CRUD
  Section.
- `StadiumService --> SeatRepository`: dựng seat map và quản lý Seat.
- `MatchService --> MatchRepository`: CRUD Match và đổi `SaleStatus`.
- `MatchService --> TicketPriceRepository`: quản lý giá theo match-section.
- `MatchService --> MatchSeatRepository`: tạo hoặc truy vấn tồn kho ghế của trận.
- `AccountAdminService --> FanRepository`: quản lý tài khoản Fan.
- `AccountAdminService --> StaffRepository`: quản lý Staff và cập nhật role.
- `AdminService --> AuditLogRepository`: đọc các hành động quản trị gần nhất.
- `RefundService --> RefundRepository`: lưu request và tìm request đang chờ.

Tất cả là association sử dụng một chiều vì Service cần Repository; Repository
không gọi ngược Service và không biết use case nào đang sử dụng nó.

### Nhóm quan hệ Model

- `Stadium "1" *-- "0..*" Section`: composition. Một Stadium sở hữu từ không
  đến nhiều Section, mỗi Section thuộc đúng một Stadium. Composition được chọn
  vì Section là thành phần cấu trúc của Stadium, không phải một loại Stadium.
- `Section "1" *-- "0..*" Seat`: composition. Một Section sở hữu nhiều Seat và
  mỗi Seat thuộc đúng một Section. Seat không phải class con của Section.
- `Match "0..*" --> "1" Stadium`: directed association. Mỗi Match diễn ra tại
  đúng một Stadium; một Stadium có thể được nhiều Match tham chiếu. Không dùng
  composition vì xóa một Match không được làm mất Stadium.
- `Match "1" *-- "0..*" MatchSeat`: composition. Match sở hữu tập tồn kho ghế
  riêng; MatchSeat chỉ có ý nghĩa trong một Match cụ thể.
- `MatchSeat "0..*" --> "1" Seat`: association. Nhiều MatchSeat ở các trận khác
  nhau có thể tham chiếu cùng một Seat vật lý; xóa một MatchSeat không xóa Seat.
- `TicketPrice "0..*" --> "1" Match`: association. Mỗi giá vé trên trang này
  thuộc một Match; một Match có nhiều mức giá. Quan hệ đầy đủ với Section được
  thể hiện ở trang Model Relationships.

### Vì sao không dùng inheritance ở nhóm Stadium?

Stadium, Section và Seat là quan hệ whole-part chứ không phải quan hệ “is-a”. Một
Section không phải là một Stadium và Seat không phải là một Section, nên dùng
composition thay vì inheritance.
