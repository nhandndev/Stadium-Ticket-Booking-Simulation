# Kịch bản Demo Thực Tế Và Câu Hỏi Phản Biện (Tiến Độ 65%)

## Demo ở trạng thái hiện tại (Chặng 1 đến Chặng 7)

**Lời thuyết trình trước khi chạy:**

Hệ thống hiện đã hoàn thành toàn bộ nền tảng cốt lõi, cơ chế lưu trữ CSV generic, các thực thể Sân vận động, Trận đấu, sơ đồ ghế động Console, phân quyền tài khoản Fan/Staff và toàn bộ phân hệ Quản trị viên (Administrator) kèm bộ sinh dữ liệu mẫu hơn 10.000 dòng. Em sẽ biên dịch sạch toàn bộ mã nguồn và khởi chạy ứng dụng để minh họa các luồng nghiệp vụ đã hoàn thành.

```bash
# Biên dịch toàn bộ 60 file Java
javac -encoding UTF-8 -d out $(find src -name "*.java")

# Khởi chạy hệ thống Console
java -cp out app.Main
```

**Trình tự demo thực tế:**

1. **Biên dịch:** Chạy `javac` chứng minh toàn bộ 60 file mã nguồn trong `src/` biên dịch thành công 100%, không phát sinh lỗi hoặc cảnh báo.
2. **Khởi chạy ứng dụng:** Màn hình chào mừng và Menu chính của `Stadium Ticket Booking Simulation` xuất hiện với các tùy chọn:
   - `1. Browse as Guest` (Duyệt xem lịch thi đấu và sơ đồ ghế)
   - `2. Register Fan Account` (Đăng ký tài khoản người hâm mộ)
   - `3. Login` (Đăng nhập xác thực Fan hoặc Staff)
   - `0. Exit` (Thoát chương trình)
3. **Demo luồng Khách vãng lai (Guest):**
   - Chọn `1`: Hệ thống hiển thị danh sách các trận đấu đang mở bán.
   - Chọn xem chi tiết một trận đấu: Xem thông tin bảng giá vé theo từng khán đài.
   - Chọn xem sơ đồ ghế: `SeatMapView` render trực quan lưới ghế ASCII trên màn hình console với các ký hiệu `[O]` (ghế trống) và `[X]` (ghế đã đặt).
4. **Demo phân hệ Quản trị viên (Admin Management):**
   - Đăng nhập với tài khoản Admin.
   - Vào menu quản trị: Chọn `System Summary` để hiển thị thống kê tổng quan hệ thống thời gian thực (`SystemSummaryDto`).
   - Thực hiện lệnh `Generate Test Dataset`: Kích hoạt `DataGenerator` tự động tạo hơn 10.000 dòng dữ liệu vào các file CSV chuẩn bị cho chặng mô phỏng tải.
   - Xem nhật ký hoạt động qua `AuditLog`.
5. **Đối chiếu kiến trúc:**
   - Mở đối chiếu các class đại diện: `AdminController`, `AdminService`, `AdminManagementView` với trang 02 của Class Diagram `Controller_First_Full (2).drawio` để chứng minh độ khớp 100% về method và quan hệ phụ thuộc.

---

## Câu hỏi: Vì sao Class Diagram có nhiều class hơn source hiện tại?

**Trả lời gợi ý:**

Class Diagram là thiết kế kiến trúc mục tiêu toàn diện được xây dựng từ toàn bộ Use Case của dự án. Hiện tại, mã nguồn đã hoàn thành 7/11 chặng (~65%), bao gồm toàn bộ nền tảng, quản lý sân, trận đấu, tài khoản và quản trị viên. Các class thuộc về Chặng 08 (Booking/Payment/Refund), Chặng 09 (Seller/Gate) và Chặng 10 (Concurrency Simulation) đã được định hình sẵn trên sơ đồ như một hợp đồng thiết kế (contract) để việc triển khai 35% còn lại diễn ra trơn tru mà không làm phá vỡ kiến trúc đã có.

---

## Câu hỏi: Vì sao dự án Console vẫn cần Service và DTO?

**Trả lời gợi ý:**

Console chỉ là hình thức giao tiếp ở tầng hiển thị (Presentation Layer), không quyết định kiến trúc của hệ thống. 
- Việc có tầng **Service** giúp tập trung toàn bộ nghiệp vụ (như kiểm tra trạng thái ghế, quản lý mở bán, xác thực quyền) độc lập hoàn toàn khỏi Console. Nhờ đó, ở Chặng 10, bộ mô phỏng Concurrency (`SimulationService`) có thể tái sử dụng trực tiếp các phương thức nghiệp vụ của `BookingService` mà không bị phụ thuộc vào `Scanner` hay giao diện người dùng.
- **DTO (Data Transfer Object)** giúp đóng gói dữ liệu truyền giữa các tầng, ngăn ngừa việc rò rỉ dữ liệu nhạy cảm (như mật khẩu không bao giờ được trả về View qua `UserResponseDto`).

---

## Câu hỏi: Vì sao không gọi Repository trực tiếp từ Controller?

**Trả lời gợi ý:**

Đây là nguyên lý phân tách trách nhiệm (Separation of Concerns). Repository chỉ chịu trách nhiệm duy nhất là thao tác File I/O (đọc, ghi, parse CSV, tìm kiếm theo ID). Các quy tắc nghiệp vụ như giới hạn số ghế đặt, kiểm tra ghế có đang active hay không, tính toán tổng tiền, và ghi audit log thuộc về tầng Service. Nếu Controller gọi thẳng Repository thì logic nghiệp vụ sẽ bị phân tán, lặp code và vi phạm nghiêm trọng kiến trúc phân lớp.

---

## Câu hỏi: `Seat` và `MatchSeat` khác nhau thế nào?

**Trả lời gợi ý:**

- `Seat` là **ghế vật lý** gắn liền với cơ sở hạ tầng của một khán đài (`Section`) trong một sân vận động cụ thể. Thuộc tính `active` của nó chỉ cho biết ghế còn lành lặn sử dụng được hay đang hỏng hóc cần sửa chữa.
- `MatchSeat` là **tồn kho ghế theo trận đấu**. Một chiếc ghế vật lý có thể đã được đặt (`BOOKED`) trong trận đấu ngày thứ Bảy, nhưng vẫn hoàn toàn sẵn sàng (`AVAILABLE`) trong trận đấu ngày Chủ Nhật. Việc tách riêng hai thực thể này là nguyên lý cốt lõi để hệ thống quản lý nhiều sự kiện thể thao độc lập trên cùng một địa điểm.

---

## Câu hỏi: Tại sao không mã hóa mật khẩu (password hash) trong dự án?

**Trả lời gợi ý:**

Ban đầu khi hỗ trợ viết code, AI từng tự ý sinh hàm mã hóa SHA-256 (`randomHash()`), nhưng em đã chủ động bác bỏ (reject) và yêu cầu xóa bỏ. Lý do là vì đây là đồ án Java Console trong môn LAB211, tập trung vào lập trình hướng đối tượng (OOP) và xử lý Concurrency đa luồng. Việc mã hóa mật khẩu là over-engineering ngoài phạm vi yêu cầu môn học, gây phức tạp không cần thiết khi kiểm tra và debug dữ liệu trong các file CSV. Em chủ trương giữ mã nguồn trong sáng, minh bạch và đúng với chuẩn mực học phần.

---

## Câu hỏi: Vì sao dùng CSV thay vì database như MySQL?

**Trả lời gợi ý:**

CSV là yêu cầu kỹ thuật và giới hạn của đề tài môn học nhằm rèn luyện kỹ năng xử lý cấu trúc dữ liệu, Generic programming, và thao tác File I/O thuần túy trong Java. Hơn nữa, việc sử dụng tệp tin phẳng CSV đặt ra thách thức thực tế rất lớn về tranh chấp dữ liệu khi nhiều luồng cùng ghi đè một file. Đây chính là tiền đề hoàn hảo để triển khai các kỹ thuật đồng bộ hóa như `FileLock` và Khóa lạc quan (`OPTIMISTIC` lock) ở Chặng 10 mà nếu dùng hệ quản trị CSDL có sẵn ACID thì bài toán sẽ bị ẩn đi.

---

## Câu hỏi: Định hướng hoàn thiện 35% còn lại như thế nào?

**Trả lời gợi ý:**

Với 65% nền tảng đã vững chắc, 35% còn lại sẽ được triển khai theo thứ tự:
1. **Chặng 08:** Hiện thực quy trình Fan đặt vé, cơ chế giữ chỗ tạm thời (`holdSeats`), thanh toán giả lập và tạo vé điện tử.
2. **Chặng 09:** Triển khai quầy bán vé trực tiếp cho Seller và tính năng soát vé cho Gate Staff.
3. **Chặng 10:** Hiện thực bộ Concurrency Simulator với 4 chiến lược (`NO_LOCK`, `SYNCHRONIZED`, `FILE_LOCK`, `OPTIMISTIC`), đo lường Throughput và tỷ lệ Double Booking.
4. **Chặng 11:** Kiểm thử toàn diện và kiểm tra tính bền vững của dữ liệu CSV sau khi khởi động lại ứng dụng.
