# Thuyết trình phần code đã hoàn thành

## Cách giới thiệu trung thực

**Lời thuyết trình:**

Hiện tại source code mới ở giai đoạn foundation và phần đầu của Stadium domain.
Class Diagram trình bày thiết kế mục tiêu của toàn hệ thống, không có nghĩa tất
cả class trên sơ đồ đã được code xong. Em triển khai theo từng chặng để mỗi chặng
có thể compile và kiểm tra độc lập.

## Entry point và menu khung

**Lời thuyết trình:**

`app.Main` là entry point. Class tạo một `Scanner`, truyền vào `MainMenuView`, gọi
`start` và đóng Scanner khi chương trình kết thúc.

`MainMenuView` hiện đã có menu khung gồm Browse as Guest, Login as Fan, Login as
Staff, Register Fan Account và Exit. Các lựa chọn nghiệp vụ hiện in thông báo
chưa triển khai. Mục đích của chặng này là xác nhận chương trình chạy được và
định hình luồng điều hướng trước khi gắn Controller và Service thật.

## Common foundation

**Lời thuyết trình:**

`BaseEntity` là abstract class có thuộc tính id, constructor và `getId`. Các model
có identity kế thừa class này để không lặp code.

`ErrorCode` hiện định nghĩa ba mã lỗi nền: invalid input, not found và unknown
error. `AppException` kế thừa `RuntimeException`, mang theo `ErrorCode` và cho
phép dùng message mặc định hoặc message cụ thể. Vì đây là Console application,
exception không chứa HTTP status.

Khi code Service, ví dụ không tìm thấy stadium, có thể throw
`new AppException(ErrorCode.NOT_FOUND, "Stadium not found")`. Điểm điều phối ở
View sẽ catch và in mã cùng thông báo. Không cần đặt try-catch trong mọi hàm nhỏ.

## Stadium model

**Lời thuyết trình:**

Ba model đã có là `Stadium`, `Section` và `Seat`. `Stadium` giữ name và address.
`Section` giữ stadiumId và name. `Seat` giữ sectionId, rowLabel, seatNumber và
active. Cả ba đều kế thừa `BaseEntity`.

Các model dùng domain method khớp Class Diagram: `Stadium.updateDetails`,
`Section.rename`, `Seat.updateLocation`, `activate` và `deactivate`. Getter được
dùng để đọc dữ liệu; việc cập nhật đi qua method mang ý nghĩa nghiệp vụ thay vì
setter rời rạc. Thuộc tính `active` của Seat biểu diễn ghế vật lý có được phép sử
dụng hay không, không phải trạng thái ghế đã được đặt trong một Match. Trạng thái
booking theo trận sẽ thuộc về `MatchSeat` ở chặng sau.

## Stadium DTO

**Lời thuyết trình:**

`StadiumRequestDto` có name và address, dùng cho dữ liệu nhập khi tạo hoặc cập
nhật Stadium. `StadiumResponseDto` có thêm id, dùng để trả kết quả ra View. Hai
DTO đều có constructor rỗng, constructor đầy đủ, getter và setter để thuận tiện
cho việc tạo object trong Java thuần.

Request và Response được tách vì id do hệ thống quản lý, người dùng không cần
nhập id khi tạo Stadium.

## Những phần chưa hoàn thành

**Lời thuyết trình:**

Repository CSV, Service, Controller, authentication thật, match, booking,
payment, ticket, refund và simulation hiện chưa được triển khai trong source
chính. Đây là các chặng tiếp theo theo Class Diagram đã chốt.

`SeatStatus` chưa được tạo trong source hiện tại vì chặng Stadium chỉ cần
`Seat.active`. Khi triển khai Match domain, enum sẽ được tạo cùng `MatchSeat` với
các giá trị `AVAILABLE`, `LOCKED` và `BOOKED`. Cách này tuân thủ nguyên tắc làm
module nào thì mới tạo class của module đó.

## Trạng thái chạy

**Lời thuyết trình:**

Source hiện tại compile được bằng `javac` và chạy từ `app.Main`. Chương trình đã
hiển thị menu khung, nhưng chưa có dữ liệu CSV và chưa chạy được các use case
nghiệp vụ. Em phân biệt rõ "compile được" với "đã hoàn thành feature" để việc
báo cáo đúng với trạng thái source.

## Câu chuyển sang kế hoạch tiếp theo

**Lời thuyết trình:**

Từ phần foundation này, thứ tự triển khai tiếp theo là hoàn thiện Stadium model
và DTO, xây CSV repository, thêm Match và MatchSeat, sau đó mới làm browse,
authentication, Admin, booking, ticket và cuối cùng là simulation. Thứ tự đó hạn
chế việc module phía trên phải sửa nhiều lần vì thiếu dependency phía dưới.
