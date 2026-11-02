# Kịch bản demo từng feature hiện có

> Dành cho buổi demo Java Console trước giảng viên. Bám theo `src/` và hai bản `ClassDiagram.drawio` hiện tại, sau đợt sửa View.
>
> Chặng 01-09 đã có mã nguồn. Chạy Simulation và so sánh bốn cơ chế là chặng 10, chưa có màn hình thực thi. Không giới thiệu phần đó như feature đã chạy được.
>
> Dùng tài liệu này cho demo hiện tại. File `13_KICH_BAN_DEMO_FULL_FEATURE.md` là kịch bản chặng 01-07 cũ, một số menu, dữ liệu và nhận định trong đó đã lỗi thời.

## 1. Cách dùng kịch bản

- **Thao tác:** Những phím và giá trị cần nhập, theo thứ tự câu hỏi trên console.
- **Kết quả:** Điều cần chỉ cho thầy thấy; ID và thời gian thực tế có thể khác ví dụ.
- **Nói với thầy:** Gợi ý giải thích ngắn, không cần học thuộc.
- `Enter` nghĩa là để trống rồi nhấn Enter, không gõ chữ `Enter`.
- `y` là đồng ý; `n` hoặc Enter là từ chối tại câu hỏi xác nhận.
- `S`, `K`, `M`, `A1`, `B1`... là ký hiệu ghi nhớ ID thực tế. Không gõ các chữ này vào ô nhập ID.
- Khi ghi `A1,A2`, phải thay bằng Seat ID, ví dụ `21,22`, không nhập nhãn ghế `A1,A2`.
- Mỗi bước dưới đây bắt đầu tại menu được ghi rõ. Đọc tiêu đề màn hình trước khi nhập số, tránh nhầm menu cha với menu con.
- Các thao tác tạo/xoá trong kịch bản chỉ làm trên bản sao dữ liệu demo.

## 2. Chuẩn bị trước buổi demo

### 2.1. Chạy bằng bộ CSV riêng

Mở Terminal ở thư mục gốc dự án. Chạy từng lệnh:

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
```

```bash
DEMO_DIR=$(mktemp -d /tmp/stadium-presentation.XXXXXX)
```

```bash
cp data/*.csv "$DEMO_DIR/"
```

```bash
printf 'Demo CSV folder: %s\n' "$DEMO_DIR"
```

```bash
java -cp out app.Main "$DEMO_DIR"
```

- Giữ Terminal này để biến `DEMO_DIR` còn tồn tại khi khởi động lại.
- Mọi thay đổi được ghi vào thư mục vừa in ra, không phải `data/`.
- Bản sao vẫn chứa dữ liệu đang có của dự án, không phải reset dữ liệu trắng.
- Muốn tập lại từ đầu: thoát chương trình, tạo một thư mục tạm mới và sao chép lại dữ liệu. Không cần xoá CSV gốc.
- Không dùng hai chương trình cùng ghi vào một thư mục CSV để tuyên bố đã kiểm chứng concurrency đa tiến trình.

### 2.2. Tài khoản mẫu đang có trong dữ liệu dự án

| Vai trò | Email | Mật khẩu mẫu |
| --- | --- | --- |
| Administrator | `admin@stadium.com` | `admin123` |
| Seller | `seller1@stadium.com` | `seller123` |
| Gate Staff | `gate1@stadium.com` | `gate123` |
| Simulator Operator | `sim@stadium.com` | `sim123` |
| Fan đang hoạt động | `an.nguyen@gmail.com` | `123456` |
| Fan thứ hai | `binh.tran@gmail.com` | `123456` |
| Fan không hoạt động | `em.do@gmail.com` | `123456` |

- Đây là tài khoản mẫu đọc từ CSV hiện tại. Nếu đã sửa dữ liệu, dùng giá trị thực tế của bản sao.
- Mật khẩu ngắn ở tài khoản cũ vẫn đăng nhập được; **tài khoản tạo mới yêu cầu ít nhất 8 ký tự**.
- Chưa có mã hoá mật khẩu đúng chuẩn triển khai thật. Không nói rằng cơ chế này đủ an toàn cho production.

### 2.3. Phiếu ghi ID trong buổi demo

| Ký hiệu | Ý nghĩa | Ghi ID/code thực tế |
| --- | --- | --- |
| `S` | Sân riêng cho buổi demo | ... |
| `K` | Khán đài của sân demo | ... |
| `M` | Trận `Demo Red vs Demo Blue` | ... |
| `A1` đến `A8` | Seat ID của tám ghế A1 đến A8 | ... |
| `F` | Fan vừa đăng ký: `demo.fan@example.com` | ... |
| `FC` | Khách tại quầy: `demo.counter@example.com` | ... |
| `ST` | Nhân viên tạo riêng để đổi role | ... |
| `B1` | Booking chưa trả tiền, sẽ huỷ | ... |
| `B2` | Booking online hai ghế A1, A2 | ... |
| `B3` | Booking tại quầy hai ghế A3, A4 | ... |
| `B4` | Booking ghế A5, sẽ được duyệt hoàn tiền | ... |
| `B5` | Booking ghế A6, sẽ bị từ chối hoàn tiền | ... |
| `R1`, `R2` | Hai refund request của B4, B5 | ... |
| `T1`, `C1` | Ticket ID và ticket code của ghế A1 trong B2 | ... |

Với dữ liệu hiện tại chưa thay đổi, sân mới thường có ID `3`, khán đài `6`, tám ghế `21` đến `28`, trận mới `4`. Đây chỉ là gợi ý: **luôn lấy ID từ kết quả console**, không đoán ID booking hay ticket code.

### 2.4. Thứ tự demo tránh tự làm hỏng dữ liệu

1. Admin tạo sân, khán đài, ghế, trận và giá vé; cập nhật thông tin trước khi mở bán.
2. Guest tra cứu; đăng ký Fan và demo xác thực.
3. Fan đặt/huỷ booking chưa trả tiền, sau đó mua vé online.
4. Seller tạo khách, đặt vé, thu tiền và phát hành vé.
5. Fan gửi hai yêu cầu hoàn tiền; Admin duyệt một, từ chối một.
6. Gate xác thực/check-in vé B2; demo chặn check-in lặp.
7. Admin quản lý tài khoản, xem summary/audit; demo lưu CSV sau khởi động lại.
8. Các tình huống lỗi và thanh toán thất bại là phần bổ sung, có thể chạy sau luồng chính.

## 3. Mở đầu khoảng 45 giây

- **Nói với thầy:** “Nhóm em xây dựng hệ thống đặt vé sân vận động bằng Java thuần, giao diện Console và lưu bằng CSV. Luồng chính là Admin chuẩn bị sân, ghế, lịch đấu và giá; Fan mua online hoặc Seller bán tại quầy; Gate Staff kiểm tra và check-in vé.”
- **Nói thêm:** “Controller điều phối màn hình và gọi Service. View chỉ nhập/xuất; Service xử lý nghiệp vụ; Repository đọc/ghi CSV. Hôm nay em demo phần đã chạy được, kể cả các tình huống nhập sai và huỷ thao tác.”
- **Giới hạn:** “Thanh toán và hoàn tiền là mô phỏng, không có chuyển tiền thật. Phần benchmark bốn cơ chế concurrency còn ở chặng 10.”

## 4. Quy tắc menu mới

- Cập nhật ngày 01/11/2026: mỗi nhóm nghiệp vụ có submenu riêng; không dùng số menu của bản cũ.
- `0` quay về đúng một menu cha. Từ màn hình nghiệp vụ phải về dashboard trước khi chọn Logout.
- Thao tác xong hoặc nhập sai vẫn ở submenu hiện tại; đọc tiêu đề để biết vị trí.
- Các đường dẫn dưới đây tính từ **menu gốc được ghi trong từng mục**. Giữa hai dòng trong bảng, bấm `0` đủ số cấp để quay lại gốc đó.
- Ví dụ: `1 > 3 > 7` tại Admin Operations nghĩa là Venues -> Seats -> Deactivate, không phải nhập liên tiếp ở ô ID.
- Form, trường nhập và nghiệp vụ không đổi; chỉ cách nhóm và số menu thay đổi.

## 5. Admin: chuẩn bị sân, ghế và trận demo

### 5.1. Vào đúng menu gốc

- Main `3` -> login Admin -> dashboard `2. Manage system operations`.
- Gốc Admin Operations có: `1 Venues`, `2 Matches/Pricing/Sales`, `3 Accounts/Roles`, `4 Refund reviews`, `5 Reports/Audit`.
- **Nói:** “Em chia quản trị theo đối tượng quản lý, thay vì bắt người dùng đọc một danh sách 17 thao tác.”
- Mọi đường dẫn trong mục 5 tính từ **Admin Operations**.

### 5.2. Stadium: đủ CRUD

| Feature | Đường dẫn | Nhập và điều cần chỉ |
| --- | --- | --- |
| Create | `1 > 1 > 1` | Name `Demo Presentation Stadium`, address `Hanoi Demo`; ghi ID `S`. |
| List | `1 > 1 > 2` | Tìm sân vừa tạo trong danh sách. |
| Details | `1 > 1 > 3` | Nhập `S`; xem tên và địa chỉ. |
| Update | `1 > 1 > 4` | Nhập `S`, tên giữ nguyên, address `Hanoi Presentation Area`. |
| Delete | `1 > 1 > 5` | Chỉ xoá sân phụ chưa có khán đài/trận, xác nhận `y`; không xoá S. |

- **Nói:** “Danh sách và chi tiết giúp Admin chọn đúng đối tượng. Xoá phải xác nhận, và không được xoá sân còn dữ liệu phụ thuộc.”

### 5.3. Section: đủ CRUD

| Feature | Đường dẫn | Nhập và điều cần chỉ |
| --- | --- | --- |
| Create | `1 > 2 > 1` | Sân `S`, name `Demo Stand`; ghi `K`. |
| List by stadium | `1 > 2 > 2` | Sân `S`; chỉ ra khán đài thuộc sân này. |
| Details | `1 > 2 > 3` | Section ID `K`; có Stadium ID. |
| Update | `1 > 2 > 4` | `K`, name `Demo Stand Updated`. |
| Delete | `1 > 2 > 5` | Dùng khán đài phụ không có ghế/giá; xác nhận `y`. |

- **Nói:** “Section thuộc Stadium, nhưng giá vé lại phụ thuộc cả Section và Match.”

### 5.4. Seat: tạo tám ghế và demo quản lý

1. Từ Admin Operations đi `1 > 3 > 1`.
2. Section ID `K`, row `A`, seat number `1`; ghi Seat ID A1.
3. Vẫn trong submenu Seats, chọn `1` tiếp cho số ghế `2` đến `8`.
4. Ghi đủ Seat ID A1-A8. Đó là ID số, không phải nhãn ghế.
5. Về Admin Operations trước khi dùng đường dẫn của dòng khác.

| Feature | Đường dẫn | Nhập và kết quả |
| --- | --- | --- |
| List seats | `1 > 3 > 2` | `K`; đủ tám ghế A1-A8. |
| Seat details | `1 > 3 > 3` | Seat ID A8; thấy row, số ghế, khán đài, active. |
| Update | `1 > 3 > 4` | A8, row `A`, number `80`; làm lại để trả number `8` trước khi bán. |
| Deactivate | `1 > 3 > 7` | A8, `y`; xem Active false. |
| Activate | `1 > 3 > 6` | A8; trả về Active true trước mở bán. |
| Delete | `1 > 3 > 5` | Tạo một ghế phụ row X trước khi có inventory; xoá bằng ID ghế phụ, `y`. |

- **Nói:** “Seat là ghế vật lý, MatchSeat là tình trạng ghế ở từng trận. Bảo trì ghế khác với bán ghế cho một trận.”
- Không sửa vị trí/xoá tám ghế chính sau khi đã mở bán.

### 5.5. Thử chặn xoá cha trước con

- Tạo sân phụ SX qua `1 > 1 > 1`, tên `Delete Demo Stadium`, address `Temporary`.
- Tạo khán đài KX trong SX qua `1 > 2 > 1`; tạo ghế AX trong KX qua `1 > 3 > 1`.
- Thử `1 > 1 > 5`, SX, `y`: bị chặn vì còn khán đài.
- Thử `1 > 2 > 5`, KX, `y`: bị chặn vì còn ghế.
- Xoá đúng thứ tự: `1 > 3 > 5` AX -> `1 > 2 > 5` KX -> `1 > 1 > 5` SX.
- Mỗi lần quay lại Admin Operations trước đường dẫn tiếp theo.
- **Nói:** “Hệ thống không xoá cha rồi để dữ liệu con mồ côi.”

### 5.6. Match: đủ CRUD trước khi có giá và inventory

| Feature | Đường dẫn | Nhập và kết quả |
| --- | --- | --- |
| Create | `2 > 1 > 1` | Sân S; home `Demo Red`; away `Demo Blue`; time `2030-11-15T19:00`; ghi M. |
| List | `2 > 1 > 2` | Có M, ban đầu CLOSED. |
| Details | `2 > 1 > 3` | M; kiểm tra sân và hai đội. |
| Update | `2 > 1 > 4` | M; S; hai đội giữ nguyên; time `2030-11-15T19:30`. |
| Delete | `2 > 1 > 5` | Tạo trận phụ MX bằng Create, chưa mở bán/chưa booking; nhập MX và `y`. Không xoá M. |

- Nếu ngày mẫu đã qua, chọn ngày tương lai.
- **Nói:** “Chỉnh lịch trước khi có giá, inventory và lịch sử booking; không tuỳ tiện đổi thông tin trận đã có vé liên quan.”

### 5.7. Ticket pricing: đủ CRUD

| Feature | Đường dẫn | Nhập và kết quả |
| --- | --- | --- |
| Create price | `2 > 2 > 1` | M, K, `250000`. |
| List prices | `2 > 2 > 2` | M; thấy giá theo khán đài. |
| Price details | `2 > 2 > 3` | M, K. |
| Update price | `2 > 2 > 4` | M, K, `260000`. |
| Delete price | `2 > 2 > 5` | M, K, `y`; xem danh sách để chứng minh đã xoá. |

- **Bắt buộc:** tạo lại giá `250000` cho M/K trước phần mở bán.
- **Nói:** “Giá theo cặp trận và khán đài, không phải giá cố định cho mọi trận.”

### 5.8. Ticket sales: mở, đóng và xem trạng thái

- `2 > 3 > 3`, M: xem CLOSED.
- `2 > 3 > 1`, M: mở bán OPEN, tạo inventory cho tám ghế hoạt động.
- `2 > 3 > 2`, M, `y`: đóng bán.
- `2 > 3 > 3`, M: kiểm tra CLOSED.
- `2 > 3 > 1`, M: **mở lại cho phần mua vé**.
- Thử `2 > 2 > 4`, M, K, `270000` khi OPEN: bị chặn đổi giá.
- **Nói:** “Muốn mở bán cần giá cho các khán đài có ghế hoạt động. Muốn đổi giá phải đóng bán.”
- Về Admin Operations rồi `0` về dashboard, `0` logout.

## 6. Guest và xác thực tài khoản

### 6.1. Guest browsing

Từ Main chọn `1`. Mỗi dòng bắt đầu tại Guest menu:

| Feature | Thao tác | Điều cần chỉ |
| --- | --- | --- |
| Search | `1`, keyword `Demo Red` | Có M, tên sân, giờ, sales OPEN. |
| Details từ kết quả | Sau search chọn `1`, M | Xem chi tiết; vẫn ở menu kết quả. |
| Seat map từ kết quả | Sau search chọn `2`, M | Nhãn ghế, khu, giá 250000 và AVAILABLE; `0` về Guest. |
| Filter | `2`, sân S, date `2030-11-15` | Lọc ra trận; `0` khỏi kết quả. |
| List all | `2`, Enter sân, Enter ngày | Lịch toàn bộ; `0` khỏi kết quả. |
| Details bằng ID | `3`, M | Danh sách được hiện trước khi chọn. |
| Seat map bằng ID | `4`, M | Xem lại trạng thái ghế. |

- **Nói:** “Chưa cần đăng nhập vẫn tra cứu được trận và giá. Guest không có chức năng Book this match.”
- `0` về Main.

### 6.2. Register, login, profile, update và logout

1. Main `4`: name `Demo Fan`, email `demo.fan@example.com`, password `DemoPass123`, phone `0901234567`; ghi F.
2. Tạo lại cùng email để thấy chống trùng; tài khoản đã có từ lần tập trước thì dùng email mới.
3. Main `2`: login tài khoản vừa tạo; dashboard `1` xem profile.
4. Dashboard `3`: name `Demo Fan Updated`, phone `0901234568`.
5. Dashboard `1`: thấy thông tin mới; thử `3` rồi Enter tại tên để huỷ.
6. Dashboard `0`: logout về Main.

- **Nói:** “Register chỉ tạo Fan. Role lấy từ dữ liệu tài khoản đã xác thực, không do người dùng tự chọn.”
- Demo thêm login sai password; tài khoản INACTIVE `em.do@gmail.com`; Admin login ở cổng Fan và Fan login ở cổng Staff. Phải bị từ chối hoặc hướng dẫn dùng đúng cổng.

## 7. Fan: menu mới cho mua vé, booking và vé

- Login Fan demo; dashboard `2. Tickets and bookings`.
- Đây là **gốc Fan Tickets**: `1 Book tickets`, `2 My bookings and payments`, `3 My tickets`, `4 Cancellations and refunds`.
- Các đường dẫn trong mục 7 tính từ gốc này; quay về gốc giữa các dòng.

### 7.1. Xem ghế, tạo PENDING và huỷ

1. `1 > 1`, M: xem giá và seat map.
2. Về gốc rồi `1 > 2`, M, Seat ID A1; Pay now chọn `n`; ghi B1.
3. `2 > 1`: xem B1 PENDING. `2 > 2`, B1: xem biên nhận.
4. `1 > 1`, M: A1 vẫn AVAILABLE.
5. `4 > 2`, B1, Enter tại xác nhận: chưa huỷ.
6. Vẫn trong Cancellations, chọn `2`, B1, lần này `y`: CANCELLED.
7. `0` về gốc Fan Tickets.

- **Nói:** “PENDING chưa giữ ghế. Huỷ booking chưa trả tiền không cần quy trình hoàn tiền.”

### 7.2. Hai ghế và thanh toán booking để sau

1. Từ gốc `1 > 2`, M, hai Seat ID A1/A2 bằng số; ghi B2; Pay now `n`.
2. `0` về gốc; chọn `2 > 3. Pay pending booking`.
3. Chọn B2; hệ thống hiện tổng `500000 VND`; xác nhận `y`.
4. Payment SUCCESS, booking CONFIRMED, hai vé online được phát hành.
5. `0` về gốc.

- **Nói:** “Đây là thanh toán mô phỏng. Checkout kiểm tra lại và commit nhóm ghế, không chỉ tin vào lần xem ghế trước đó.”

### 7.3. Lịch sử và chi tiết

| Feature | Đường dẫn từ gốc Fan Tickets | Kết quả |
| --- | --- | --- |
| Booking history | `2 > 1` | B1 CANCELLED, B2 CONFIRMED. |
| Booking details | `2 > 2`, B2 | Hai ghế, đúng Fan, tổng 500000 VND. |
| Ticket history | `3 > 1` | Hai vé ISSUED; ghi Ticket ID T1 và code C1 của A1. |
| Ticket details | `3 > 2`, T1 | Code, ghế, trận và Not checked in. |
| Seat map sau trả tiền | `1 > 1`, M | A1/A2 BOOKED. |

- **Nói:** “Ticket ID để tra cứu nội bộ; ticket code để đưa cho cổng. Ticket history hiện trạng thái hiện tại, không phải nhật ký mọi lần đổi trạng thái.”

### 7.4. Đặt từ kết quả browsing

- Từ gốc Fan Tickets `0` về dashboard; `4` Browse; search `1`, keyword `Demo Red`.
- Trong kết quả chọn `3. Book this match`, M; nhập `0` ở Seat IDs để demo huỷ.
- Không tạo thêm booking. `0` về browsing; `0` về dashboard; `0` logout.

## 8. Seller: khách hàng, booking, thu tiền và phát vé

- Login Seller; dashboard `2. Sales counter`.
- Đây là **gốc Sales counter**: `1 Customers`, `2 Customer bookings`, `3 Payments and ticket issue`.
- Các đường dẫn dưới đây tính từ gốc này.

### 8.1. Customers

- `1 > 1`: keyword `demo.fan`; tìm thấy Fan online.
- Ở submenu Customers chọn `1` rồi Enter: danh sách toàn bộ.
- Chọn `2`: name `Demo Counter Customer`, email `demo.counter@example.com`, password `Counter123`, phone `0902222222`; ghi FC.
- `0` về Sales counter.
- **Nói:** “Tìm khách trước, chưa có mới tạo Fan; Seller không tự tạo Admin.”

### 8.2. Customer bookings

- `2 > 1`, M: xem seat map.
- Về gốc rồi `2 > 2`: keyword `demo.counter`, chọn FC, M, Seat ID A3/A4 bằng số.
- Ghi B3 PENDING, tổng `500000 VND`.
- Vẫn trong Customer bookings chọn `3`, keyword `demo.counter`, FC: xem history B3.
- `0` về Sales counter.
- **Nói:** “Booking đứng tên Fan; Seller là nhân viên thao tác hộ.”

### 8.3. Cash payment

1. `3 > 1`: keyword `demo.counter`, FC, B3.
2. Enter thay vì RECEIVED: không ghi đã thu tiền.
3. Vẫn trong Payments, chọn `1`, chọn lại khách/booking; lần này nhập `RECEIVED`.
4. Payment SUCCESS, method OFFLINE_CASH.
5. Tại Issue tickets now chọn `n` để demo phát vé riêng.

- **Nói:** “Offline chỉ ghi nhận tiền mặt; không đi qua online payment. Nhân viên phải xác nhận đã nhận tiền.”

### 8.4. Issue tickets và gọi lại không trùng

- Vẫn trong Payments chọn `2`, keyword `demo.counter`, FC, B3.
- Có hai vé A3/A4; lặp lại cùng thao tác, code không đổi và không sinh thêm vé.
- Ở giao dịch khác có thể chọn `y` ngay sau cash payment để phát hành liền.
- `0` về Sales counter; `0` dashboard; `0` logout.
- **Nói:** “Chia menu không thay đổi kiểm tra thanh toán trước khi phát vé hay tính idempotent.”

## 9. Refund: Fan gửi, Admin xử lý

### 9.1. Tạo B4, B5 và gửi request

- Login Fan demo; dashboard `2` vào gốc Fan Tickets.
- `1 > 2`, M, A5, Pay now `y`; ghi B4.
- Vẫn trong Book tickets chọn `2`, M, A6, Pay now `y`; ghi B5.
- `0` về gốc, chọn `4` Cancellations and refunds.
- `1`, B4, reason `Cannot attend the match`; ghi R1.
- `1`, B5, reason `Changed travel plan`; ghi R2.
- `3`: hai request REQUESTED. Thử gửi lại B4 để thấy bị chặn request trùng.
- `0` về gốc; `0` dashboard; `0` logout.

- **Nói:** “Gửi request chưa giải phóng ghế và chưa huỷ vé. Cần Admin review.”

### 9.2. Approve và reject

- Login Admin; dashboard `2`; Admin Operations `4. Refund reviews`.
- Chọn `1`, R1, approve `y`, note `Approved before kick-off`: APPROVED.
- Vẫn ở review chọn `1`, R2, approve `n`, note `Rejected after policy review`: REJECTED.
- Hết request chờ thì tự về Admin Operations. Nếu còn request khác, chọn `0` để quay lại.
- Về dashboard và logout.

- **Nói:** “Duyệt thì kiểm tra lại điều kiện, huỷ vé/booking và trả ghế. Từ chối chỉ đổi trạng thái request. Không chuyển tiền ngân hàng thật.”

### 9.3. Fan kiểm tra kết quả

Từ gốc Fan Tickets sau login:

| Kiểm tra | Đường dẫn | Kết quả |
| --- | --- | --- |
| Refund status | `4 > 3` | R1 APPROVED, R2 REJECTED. |
| Booking history | `2 > 1` | B4 CANCELLED, B5 CONFIRMED. |
| Tickets | `3 > 1` | Vé B4 CANCELLED, vé B5 ISSUED; ghi code B4 để thử chặn tại cổng. |
| Seat map | `1 > 1`, M | A5 AVAILABLE, A6 BOOKED. |

- Về gốc, dashboard rồi logout.

## 10. Gate: validate và check-in

- Login Gate, dashboard `2`; menu này vẫn có `1 Validate`, `2 Check in`, `0 Back`.
- `1`, code C1, Match ID M: hiện ISSUED; chưa đánh dấu vào cổng.
- `2`, C1, M, xác nhận `n`: Check-in cancelled.
- `2`, C1, M, xác nhận `y`: CHECKED_IN, có thời gian.
- Lặp lại `2`, C1, M: bị chặn, không hỏi xác nhận lại.
- Thử code của vé A2 chưa dùng nhưng nhập Match ID khác M: chặn sai trận.
- Thử `2`, code UNKNOWN_TICKET, M: không tìm thấy và không hỏi xác nhận.
- Thử code vé B4 đã hoàn tiền: bị chặn vì CANCELLED.
- `0` dashboard, `0` logout.
- **Nói:** “Kiểm tra trước khi hỏi xác nhận; validate không thay đổi trạng thái, check-in mới ghi nhận sử dụng.”

## 11. Admin: account management và staff roles

Login Admin, dashboard `2`. Mỗi đường dẫn tính từ **Admin Operations**.

### 11.1. Fan accounts

| Feature | Đường dẫn | Dữ liệu / kết quả |
| --- | --- | --- |
| Create | `3 > 1 > 1` | Name Admin Created Fan, email demo.adminfan@example.com, password AdminFan123, phone 0903333333; ghi FX. |
| List | `3 > 1 > 2` | Có FX. |
| Details | `3 > 1 > 3`, FX | Đúng tài khoản. |
| Search | `3 > 1 > 4`, demo.adminfan | Tìm được FX. |
| Filter | `3 > 1 > 5`, status 1 | ACTIVE; status 2 INACTIVE, 0 All. |
| Update | `3 > 1 > 6`, FX | Name Admin Fan Updated, email cũ, password Enter, phone 0903333334. |
| Deactivate | `3 > 1 > 8`, FX, y | INACTIVE. |
| Activate | `3 > 1 > 7`, FX | ACTIVE. |
| Delete | `3 > 1 > 9`, FX, y | Được xoá vì chưa booking. |
| Chặn xoá có lịch sử | `3 > 1 > 9`, F, y | Không cho xoá Fan đang có booking history. |

- **Nói:** “Deactivate không xoá lịch sử. Hiện chỉ có ACTIVE/INACTIVE, không có LOCKED riêng.”

### 11.2. Staff accounts

| Feature | Đường dẫn | Dữ liệu / kết quả |
| --- | --- | --- |
| Create | `3 > 2 > 1` | Name Demo Staff, email demo.staff@example.com, password StaffPass123, phone 0904444444, role 1; ghi ST. |
| List / Details | `3 > 2 > 2`; hoặc `3 > 2 > 3`, ST | SELLER, ACTIVE. |
| Search | `3 > 2 > 4`, demo.staff | Đúng nhân viên phụ. |
| Filter | `3 > 2 > 5`, role 1, status 1 | SELLER ACTIVE; 0 là All. |
| Update | `3 > 2 > 6`, ST | Name Demo Staff Updated, email cũ, password Enter, phone 0904444445; role không đổi. |
| Deactivate / Activate | `3 > 2 > 8`, ST, y; rồi `3 > 2 > 7`, ST | INACTIVE rồi ACTIVE. |

### 11.3. Staff roles có menu riêng

| Feature | Đường dẫn | Dữ liệu / kết quả |
| --- | --- | --- |
| Change role | `3 > 2 > 9 > 2`, ST, role 2 | SELLER -> GATE_STAFF. |
| Revoke role | `3 > 2 > 9 > 3`, ST, y | UNASSIGNED. |
| Assign role | `3 > 2 > 9 > 1`, ST, role 1 | Gán lại SELLER. |

- Role picker: 1 Seller, 2 Gate, 3 Admin, 4 Simulator, 5 Unassigned; 0 Back/All theo prompt.
- Không dùng role 5 khi Assign/Change: thu hồi bằng Revoke.
- **Nói:** “Assign cho nhân viên chưa có role, Change đổi role, Revoke thu hồi role. Service vẫn chặn mất Admin ACTIVE cuối cùng.”
- Chưa có Delete Staff trong menu. Không demo quyền bằng cách thay đổi Admin đang đăng nhập.

## 12. Profile dùng chung và Simulator

- Dashboard Fan/Seller/Gate/Admin vẫn giữ `1 View profile`, `3 Update profile`, `0 Logout`.
- Demo update trên Seller phụ ST: name Demo Staff Profile, phone 0904444446; xem lại bằng 1.
- Login Simulator: dashboard `1 Profile`, `2 Update profile`, `0 Logout`.
- **Nói:** “Simulator hiện mới có xác thực và profile. Chạy/so sánh/xuất benchmark bốn cơ chế chưa có trong mã nguồn.”

## 13. Reports, audit và CSV persistence

### 13.1. System summary

- Login Admin, dashboard 2 -> Admin Operations `5 > 1`.
- Có tổng Fan, Staff, Stadium, Match, Booking và Ticket; số thực tế phụ thuộc CSV.
- **Nói:** “Đây là đếm bản ghi, không phải số vé còn hiệu lực hay báo cáo doanh thu.”

### 13.2. Audit log

- Vẫn trong Reports chọn `2`.
- Chỉ dòng REVIEW_REFUND cho R1/R2: actor, target, kết quả, thời gian.
- **Nói:** “Review refund có ghi audit; chưa phải mọi CRUD hay lỗi đều có audit. Không nhầm audit nghiệp vụ với AI Audit.”

### 13.3. Khởi động lại vẫn còn dữ liệu

- Từ Reports `0` về Admin Operations; `0` dashboard; `0` logout; `0` Main để thoát.
- Chạy lại đúng bản sao cũ:

```bash
java -cp out app.Main "$DEMO_DIR"
```

- Login Fan, dashboard 1 xem tên mới; dashboard 2 -> 2 -> 1 xem booking history.
- **Nói:** “Dữ liệu lưu trong CSV, không chỉ trong RAM. Nhiều CSV vẫn không phải transaction ACID xuyên file.”

## 14. Các lỗi nên demo thêm

| Tình huống | Đường dẫn / thao tác | Kết quả |
| --- | --- | --- |
| Menu nhập chữ / ngoài phạm vi | Nhập abc hoặc 99 tại submenu | Báo lỗi, vẫn ở nhóm hiện tại. |
| ID âm / Back | -1 hoặc 0 tại ô ID | Chặn ID âm / quay lại, không ghi dữ liệu. |
| Huỷ form | Enter tại tên tài khoản hoặc tên sân | Không tạo đối tượng rỗng. |
| Ghế trùng | Fan Tickets `1 > 2`, M, hai lần cùng Seat ID | Từ chối. |
| Quá bốn ghế | Fan Tickets `1 > 2`, M, năm ID | Choose 1 to 4 seats. |
| Lỗi danh sách ghế | Nhập 21,abc hoặc 21, | Không tạo booking một phần. |
| Ghế đã bán | Fan khác đặt A1 sau B2 | Từ chối, không tạo vé trùng. |
| Refund vé đã dùng | Fan Tickets `4 > 1`, B2 sau khi C1 check-in | Used or cancelled ticket cannot be refunded. |
| Đóng bán rồi đặt | Admin Operations `2 > 3 > 2`, M, y; Fan đặt M | Không hỏi chọn ghế; nhớ mở lại bằng `2 > 3 > 1`. |
| Booking của Fan khác | Fan thứ hai cần có ít nhất một booking để dùng Details; Fan Tickets `2 > 2`, nhập B2 | Chặn xem booking không sở hữu. |
| Không có booking chờ | Fan Tickets `2 > 3` khi không còn PENDING | Không hỏi Booking ID. |
| Seller không tìm thấy khách | Sales counter `2 > 2`, keyword không tồn tại | Không bắt nhập Fan ID, gợi ý tạo khách. |
| Xác nhận huỷ | Chọn n/Enter ở xác nhận xoá | Không xoá. |
| Không có trận/khách/vé | Chọn chức năng tương ứng trên dữ liệu rỗng | Báo rỗng, không hỏi ID vô nghĩa. |

- Kiểm tra lần lượt hai người mua cùng ghế không đủ chứng minh chống race condition nhiều thread.

## 15. Thanh toán thất bại trên bản sao thứ hai

Thoát ứng dụng, giữ biến DEMO_DIR. Chạy từng lệnh:

```bash
FAIL_DIR=$(mktemp -d /tmp/stadium-payment-fail.XXXXXX)
```

```bash
cp "$DEMO_DIR"/*.csv "$FAIL_DIR/"
```

```bash
java -cp out app.Main "$FAIL_DIR" --payment-fails
```

- Login Fan -> dashboard 2 -> Fan Tickets `1 > 2`, M, A7 nếu AVAILABLE, Pay now y.
- Payment FAILED, booking CANCELLED; không có vé mới của booking lỗi.
- Xem seat map qua Fan Tickets `1 > 1`: A7 không bị bán.
- Nếu A7 đã dùng, chọn A8 hoặc ghế AVAILABLE khác.
- Thoát, chạy lại bản chính không có cờ lỗi: `java -cp out app.Main "$DEMO_DIR"`.
- **Nói:** “Cờ cấu hình MockPaymentService giả lập lỗi online, không phải Fan tự chọn kết quả thanh toán.”

## 16. Ghi nhớ nhanh trước buổi demo

- Không đoán ID hay code; ghi chúng vào phiếu ở mục 2.3.
- Mỗi bảng có menu gốc riêng. Quay về gốc trước khi dùng đường dẫn dòng tiếp theo.
- Không đưa dữ liệu demo vào CSV gốc.
- Menu mới được smoke riêng bằng `python3 scripts/smoke_console_menus.py`.
- Script smoke dùng CSV tạm tự tạo, không chạy trên data/ thật.

## 17. Gợi ý chia thời lượng

### Bản ngắn 12-15 phút

- Trước buổi demo làm sẵn phần 4 trên một bản sao CSV; ghi ID vào phiếu.
- 1 phút: bài toán, kiến trúc và giới hạn hiện tại.
- 2 phút: Guest search, xem chi tiết, seat map; Fan login.
- 3 phút: booking PENDING, huỷ; mua online hai ghế; xem vé.
- 3 phút: Seller chọn khách, cash payment và issue tickets.
- 2 phút: Gate validate, check-in, dùng lại bị chặn.
- 2 phút: một refund được Admin duyệt, xem ghế được trả lại.
- 1-2 phút: summary, audit và khởi động lại vẫn còn dữ liệu.
- **Nói rõ:** “Em chuẩn bị trước dữ liệu bằng các chức năng Admin để dành thời gian cho luồng nghiệp vụ.” Không nói đã demo trực tiếp toàn bộ CRUD nếu đã bỏ qua.

### Bản đầy đủ khoảng 35-50 phút

- Chạy lần lượt phần 4 đến 16, gồm cả CRUD dữ liệu phụ, account management và lỗi.
- Nếu thầy chỉ định feature, mở mục đúng tên; quay về đúng menu trước khi thao tác.
- Chọn dữ liệu demo chưa có quan hệ cho bài test xoá; dữ liệu đã bán vé dùng để chứng minh chặn xoá.

## 18. Kết thúc và trả lời ngắn các câu hỏi thường gặp

- **Kết luận:** “Nhóm đã nối được luồng từ quản trị dữ liệu tới đặt vé, thanh toán mô phỏng, vé, kiểm soát cổng và yêu cầu hoàn tiền. Có kiểm tra nhập liệu và ràng buộc trạng thái, không chỉ CRUD riêng lẻ.”
- **Vì sao có Controller và Service?** Controller điều phối View và luồng menu; Service xử lý quy tắc nghiệp vụ và dùng Repository. Không để View trực tiếp gọi CSV.
- **Tạo booking là giữ ghế chưa?** Chưa. PENDING không hold; checkout kiểm tra và commit nhóm ghế khi thanh toán.
- **Vì sao không xoá Fan hoặc Match đã có booking?** Để giữ lịch sử và tránh mất đối tượng mà vé/giao dịch đang tham chiếu.
- **Có thanh toán thật không?** Không, dùng MockPaymentService; offline chỉ ghi nhận tiền mặt mô phỏng. Refund cũng không chuyển tiền thật.
- **CSV có transaction như database không?** Không. Ghi file đơn lẻ không đồng nghĩa transaction nguyên tử xuyên nhiều file hoặc xử lý mọi tình huống crash.
- **Bốn cơ chế concurrency đã demo chưa?** Chưa. Checkout hiện có core SYNCHRONIZED dùng chung; màn hình benchmark bốn cơ chế còn ở chặng 10.
- **Đã kiểm thử gì?** Có báo cáo 50 kịch bản CLI smoke trong `docs/15_CONSOLE_VIEW_REVIEW.md`; đó không phải bằng chứng đã hoàn thành kiểm thử tải hay security cho production.
- **Checklist kết thúc:** thoát bằng menu `0` theo từng cấp; thấy `Goodbye!`; giữ lại thư mục demo nếu muốn thầy xem CSV; không thay dữ liệu gốc bằng bản demo.
