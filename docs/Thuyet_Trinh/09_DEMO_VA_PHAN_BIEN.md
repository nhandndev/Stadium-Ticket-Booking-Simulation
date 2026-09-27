# Kịch bản demo và câu hỏi phản biện

## Demo ở trạng thái hiện tại

**Lời thuyết trình trước khi chạy:**

Hiện tại em demo phần foundation, chưa demo nghiệp vụ hoàn chỉnh. Em sẽ compile
toàn bộ source, chạy `app.Main`, sau đó cho thấy menu khung và cách chương trình
xử lý lựa chọn hợp lệ hoặc không hợp lệ.

```bash
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

**Trình tự demo:**

1. Chạy chương trình và chỉ ra title của hệ thống.
2. Chọn Browse as Guest để cho thấy vị trí module sẽ được gắn vào.
3. Chạy lại và nhập lựa chọn không hợp lệ để minh họa menu foundation.
4. Thoát bằng lựa chọn 0.
5. Mở `BaseEntity`, `Stadium`, `Section`, `Seat` và hai Stadium DTO để đối chiếu
   với Class Diagram.

Không demo giả các chức năng chưa code bằng cách mô tả chúng như đã chạy được.

## Câu hỏi: Vì sao Class Diagram nhiều class hơn source?

**Trả lời gợi ý:**

Class Diagram là thiết kế mục tiêu được lập từ toàn bộ Use Case trước khi code.
Source được triển khai theo từng chặng và hiện mới hoàn thành foundation cùng
phần đầu Stadium domain. Em dùng diagram làm contract để những chặng sau không
bị thiếu class hoặc sai dependency.

## Câu hỏi: Vì sao dự án Console vẫn cần Service và DTO?

**Trả lời gợi ý:**

Console chỉ là cách giao tiếp ở View, không quyết định toàn bộ kiến trúc. Service
giữ business rule để Controller và View không xử lý nghiệp vụ. DTO giới hạn dữ
liệu đi vào và đi ra, ví dụ response đăng nhập không trả password. Các lớp này
cũng giúp simulation tái sử dụng nghiệp vụ mà không phụ thuộc menu Console.

## Câu hỏi: Vì sao không gọi Repository trực tiếp từ Controller?

**Trả lời gợi ý:**

Repository chỉ chịu trách nhiệm lưu và truy vấn CSV. Các quy tắc như tối đa bốn
ghế, phải mở bán, ghế phải active, payment thành công và tạo ticket thuộc về
Service. Nếu Controller gọi thẳng Repository thì business logic dễ bị lặp giữa
Fan, Seller và Simulator.

## Câu hỏi: Seat và MatchSeat khác nhau thế nào?

**Trả lời gợi ý:**

Seat là ghế vật lý thuộc một Section và có trạng thái active. MatchSeat là trạng
thái của ghế đó trong một Match cụ thể, ví dụ available hoặc booked. Một ghế có
thể đã booked ở trận A nhưng vẫn available ở trận B, nên không thể dùng một
status duy nhất trong Seat.

## Câu hỏi: Vì sao Seller không dùng Payment Service?

**Trả lời gợi ý:**

Seller nhận thanh toán offline tại quầy nên không cần gọi mock online payment.
Tuy nhiên Seller vẫn dùng chung BookingService và MatchSeat để bảo đảm cùng một
quy tắc chống bán trùng ghế.

## Câu hỏi: Simulator có tạo Booking và Ticket thật không?

**Trả lời gợi ý:**

Simulator tập trung vào booking attempt và trạng thái MatchSeat. Nó không chạy
giao diện checkout hoặc online payment cho từng thread. Mỗi task gọi cùng
booking strategy và ghi BookingTransaction. Tùy mức hiện thực, booking thành
công có thể được gắn id, nhưng simulation không cần tạo luồng tương tác payment
và ticket như người dùng thật.

## Câu hỏi: `synchronized`, file lock và optimistic lock khác gì nhau?

**Trả lời gợi ý:**

`synchronized` khóa trong một JVM nên đơn giản nhưng không đại diện cho nhiều
process. File lock khóa tài nguyên file, phù hợp CSV hơn nhưng chậm vì I/O.
Optimistic lock không giữ khóa lâu; nó so sánh version khi update và báo conflict
nếu dữ liệu đã đổi. `NO_LOCK` được giữ làm baseline để chứng minh race condition.

## Câu hỏi: Phát hiện double booking bằng cách nào?

**Trả lời gợi ý:**

Hệ thống nhóm các transaction thành công theo cặp `matchId` và `seatId`. Nếu một
cặp có nhiều hơn một lần thành công thì có double booking. Khi kiểm tra ticket,
quy tắc tương đương là một match-seat không được có nhiều ticket hợp lệ.

## Câu hỏi: Tại sao dùng CSV thay vì database?

**Trả lời gợi ý:**

CSV là yêu cầu và giới hạn của bài LAB. Repository được tách riêng để quản lý
việc parse, format và cập nhật file. File lock và optimistic version cũng giúp
đề tài minh họa rõ vấn đề concurrency ngay trên lưu trữ file.

## Câu hỏi: Custom exception có cần HTTP status không?

**Trả lời gợi ý:**

Không. Đây là Java Console nên exception chỉ cần mã lỗi nội bộ và message.
HTTP status chỉ phù hợp khi xây Web API. `AppException` kế thừa
`RuntimeException`; điểm điều phối cao nhất catch và in lỗi cho người dùng.

## Câu hỏi: Phần nào đang cần sửa trong code hiện tại?

**Trả lời gợi ý:**

Phần foundation hiện đã đồng bộ với Class Diagram: `BaseEntity.id` là private,
Stadium, Section và Seat có domain method, còn SeatStatus chưa được tạo sớm vì nó
thuộc MatchSeat. Việc tiếp theo là triển khai CSV Repository cho Stadium domain,
sau đó mới nối Service, Controller và View.

## Câu kết

**Lời thuyết trình:**

Thiết kế đã bao phủ Use Case mới và chia domain đủ nhỏ để triển khai bằng Java
thuần. Phần code hiện tại mới là nền móng nhưng đã compile và có cấu trúc đúng
hướng. Các bước tiếp theo sẽ bám theo Class Diagram, ưu tiên hoàn thiện dữ liệu
và repository trước khi nối toàn bộ flow booking và concurrency simulation.
