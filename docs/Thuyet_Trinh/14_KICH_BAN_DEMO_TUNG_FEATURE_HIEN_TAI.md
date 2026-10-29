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

## 4. Administrator: tạo dữ liệu và demo CRUD

### 4.1. Đăng nhập và phân biệt hai menu Admin

1. Main Menu: chọn `3. Login As Staff`.
2. Nhập tài khoản Admin ở mục 2.2.
3. Tại dashboard Admin: chọn `1. View profile` để cho thấy role `ADMIN`.
4. Chọn `2. Manage system operations` để vào menu nghiệp vụ.

- **Kết quả:** Có các nhóm `1` sân/khán đài/ghế, `2` trận/giá/mở bán, `3` Fan, `4` Staff, `5` audit, `6` summary, `7` refund.
- **Nói với thầy:** “Sau xác thực, hệ thống lấy role trong tài khoản để chọn menu. Người dùng không tự chọn mình là Admin.”

### 4.2. Stadium: tạo, danh sách, chi tiết, cập nhật

Từ menu nghiệp vụ Admin chọn `1. Stadium / Section / Seat`.

| Feature | Thao tác tại menu này | Kết quả và lời giải thích |
| --- | --- | --- |
| Create Stadium | Chọn `1`; name `Demo Presentation Stadium`; address `Hanoi Demo` | Sân được tạo; ghi ID `S`. “ID được sinh khi lưu.” |
| View Stadium List | Chọn `2` | Tìm dòng sân vừa tạo. “Không cần nhớ ID từ trước.” |
| View Stadium Details | Chọn `3`; ID `S` | Hiện tên và địa chỉ. |
| Update Stadium | Chọn `4`; ID `S`; name `Demo Presentation Stadium`; address `Hanoi Presentation Area` | Xem lại bằng `3`, ID `S`. “Cập nhật được lưu xuống CSV.” |

### 4.3. Section: tạo, danh sách, chi tiết, cập nhật

Vẫn tại menu sân/khán đài/ghế:

| Feature | Thao tác | Kết quả và lời giải thích |
| --- | --- | --- |
| Create Section | `6`; Stadium ID `S`; name `Demo Stand` | Ghi Section ID `K`. “Khán đài thuộc một sân cụ thể.” |
| View Sections by Stadium | `7`; Stadium ID `S` | Chỉ ra khán đài thuộc sân demo. |
| View Section Details | `8`; Section ID `K` | Hiện ID sân cha. |
| Update Section | `9`; Section ID `K`; name `Demo Stand Updated` | Dùng tên mới này xuyên suốt phần vé sau đó. |

### 4.4. Seat: tạo tám ghế, xem và cập nhật

1. Chọn `11. Create seat`.
2. Section ID nhập `K`; row label nhập `A`; seat number nhập `1`.
3. Ghi Seat ID `A1` từ kết quả.
4. Lặp lại chức năng `11` cho seat number `2` đến `8`, cùng khán đài `K` và row `A`.
5. Ghi đủ tám Seat ID vào phiếu; đừng nhầm seat number với Seat ID.

| Feature | Thao tác tiếp theo | Kết quả và lời giải thích |
| --- | --- | --- |
| View Seats by Section | `12`; Section ID `K` | Hiện đủ tám ghế A1-A8. |
| View Seat Details | `13`; Seat ID `A8` | Hiện row, số ghế, khán đài, trạng thái hoạt động. |
| Update Seat | `14`; Seat ID `A8`; row `A`; number `80` | Ghế đổi thành A80; làm lại `14` để trả về row `A`, number `8` trước khi bán vé. |
| Deactivate Seat | `17`; Seat ID `A8`; xác nhận `y` | Dùng `13` xem `Active: false`. “Ghế bảo trì không được đưa vào bán như ghế đang hoạt động.” |
| Activate Seat | `16`; Seat ID `A8` | Xem lại `Active: true`. Khôi phục trước khi mở bán. |

- **Nói với thầy:** “Seat là ghế vật lý. Khi mở bán, MatchSeat giữ trạng thái ghế của từng trận. Cùng một ghế vật lý có thể được bán ở hai trận khác nhau.”
- Không đổi vị trí hoặc xoá tám ghế chính sau khi đã có inventory cho trận.

### 4.5. Demo xoá Seat, Section, Stadium bằng dữ liệu phụ

Không xoá sân `S`, khán đài `K` hoặc tám ghế chính.

1. Tạo thêm một sân `Delete Demo Stadium`, address `Temporary`; ghi ID `SX`.
2. Chọn `6`, sân `SX`, name `Delete Demo Section`; ghi ID `KX`.
3. Chọn `11`, khán đài `KX`, row `X`, number `1`; ghi ID `AX`.
4. **Chặn xoá cha còn con:** chọn `5`, Stadium ID `SX`, xác nhận `y`. Kết quả: yêu cầu xoá khán đài trước.
5. Chọn `10`, Section ID `KX`, `y`. Kết quả: yêu cầu xoá ghế trước.
6. **Delete Seat:** chọn `15`, Seat ID `AX`, thử `n` trước; xem lại bằng `13` để chứng minh chưa xoá.
7. Chọn `15`, Seat ID `AX`, lần này `y`: xoá thành công.
8. **Delete Section:** chọn `10`, Section ID `KX`, `y`: xoá thành công.
9. **Delete Stadium:** chọn `5`, Stadium ID `SX`, `y`: xoá thành công.

- **Nói với thầy:** “Xoá đi từ ghế lên khán đài rồi tới sân. Hệ thống không cho xoá cha làm dữ liệu con mồ côi, và có xác nhận trước thao tác xoá.”
- Chọn `0` để về menu nghiệp vụ Admin.

### 4.6. Match: tạo, danh sách, chi tiết, cập nhật

Từ menu nghiệp vụ Admin chọn `2. Match / Pricing / Sales`.

| Feature | Thao tác | Kết quả |
| --- | --- | --- |
| Create Match | `1`; Stadium ID `S`; home `Demo Red`; away `Demo Blue`; kick-off `2030-11-15T19:00` | Ghi Match ID `M`; trận mới `CLOSED`. Nếu ngày này đã qua, thay bằng một ngày tương lai. |
| View Match List | `2` | Có trận demo trong danh sách. |
| View Match Details | `3`; Match ID `M` | Kiểm tra sân, hai đội, giờ và trạng thái. |
| Update Match | `4`; ID `M`; Stadium ID `S`; home `Demo Red`; away `Demo Blue`; kick-off `2030-11-15T19:30` | Cập nhật lúc trận chưa có giá/inventory/booking. |

- **Nói với thầy:** “Nhóm giới hạn chỉnh lịch khi trận đã có dữ liệu liên quan để tránh thay đổi thông tin của vé đã bán.”

### 4.7. Pricing: đủ tạo, xem, cập nhật và xoá

Vẫn tại menu trận/giá/mở bán, khi `M` còn `CLOSED`:

1. **Create Ticket Price:** `6`; Match ID `M`; Section ID `K`; price `250000`.
2. **View Prices by Match:** `7`; Match ID `M`.
3. **View Price Details:** `8`; Match ID `M`; Section ID `K`.
4. **Update Ticket Price:** `9`; Match ID `M`; Section ID `K`; new price `260000`.
5. **Delete Ticket Price:** `10`; Match ID `M`; Section ID `K`; xác nhận `y`.
6. Xem lại bằng `7`, `M`: bảng giá trận demo rỗng.
7. **Tạo lại giá bắt buộc cho phần mua vé:** `6`; `M`; `K`; `250000`.

- **Kết quả:** Giá cuối cùng là `250000 VND` cho khán đài `K` của trận `M`.
- **Nói với thầy:** “Giá theo cặp trận và khán đài, không phải một giá cố định cho tất cả trận.”

### 4.8. Sales: mở bán, đóng bán, xem trạng thái

1. **View Sales Status:** `13`; Match ID `M`: ban đầu `CLOSED`.
2. **Open Ticket Sales:** `11`; Match ID `M`: kết quả `OPEN` và có inventory cho tám ghế đang hoạt động.
3. **Close Ticket Sales:** `12`; Match ID `M`; xác nhận `y`: kết quả `CLOSED`.
4. Chọn `13`, `M`: chứng minh đã đóng.
5. Chọn `11`, `M`: **mở lại để Fan/Seller mua ở phần sau**.
6. Thử `9`, `M`, `K`, giá `270000` khi đang mở bán: bị từ chối đổi giá.

- **Nói với thầy:** “Phải có giá cho khán đài có ghế hoạt động trước khi mở bán. Đang mở bán thì không được tự thay giá.”

### 4.9. Delete Match bằng trận phụ

1. `1`; sân `S`; home `Delete Red`; away `Delete Blue`; kick-off `2030-12-01T19:00`; ghi ID `MX`.
2. `5`; Match ID `MX`; thử `n` để huỷ.
3. `5`; Match ID `MX`; xác nhận `y`: xoá trận phụ thành công.
4. Không xoá trận `M` dùng để bán vé. Trận có booking history sẽ không được xoá.
5. `0` về menu nghiệp vụ, `0` về dashboard, `0` đăng xuất về Main Menu.

## 5. Guest: từng chức năng tra cứu

Tại Main Menu chọn `1. Browse As Guest`.

### 5.1. Search Matches

- **Thao tác:** chọn `1`; keyword `Demo Red`.
- **Kết quả:** Có trận `M`, giờ đấu, sân và trạng thái `OPEN`.
- **Nói với thầy:** “Guest tìm theo tên đội mà chưa cần đăng nhập.”

### 5.2. View Match Details từ kết quả

- **Thao tác:** ngay dưới kết quả, chọn `1`; Match ID `M`.
- **Kết quả:** Hiện chi tiết trận; vẫn ở menu thao tác của kết quả.
- **Nói với thầy:** “Tìm thấy trận xong có thể mở chi tiết ngay, không phải tìm lại.”

### 5.3. View Seat Availability từ kết quả

- **Thao tác:** trong menu kết quả chọn `2`; Match ID `M`.
- **Kết quả:** Có Seat ID, nhãn A1-A8, tên khán đài, giá `250000`, trạng thái `AVAILABLE`.
- **Nói với thầy:** “Khách biết khu vực và giá trước khi quyết định đăng ký mua. Bảng hiện tại không hiển thị version và không có bước HOLD trong checkout.”
- Chọn `0` về menu Guest.

### 5.4. Filter Matches và List All

1. Chọn `2`; Stadium ID `S`; date `2030-11-15`: lọc ra trận demo, rồi `0` khỏi menu kết quả.
2. Chọn `2`; Stadium ID để trống; date để trống: xem toàn bộ lịch, rồi `0` khỏi kết quả.
3. Chọn `2`; Stadium ID `S`; date `abc`: thông báo đúng định dạng ngày, không thoát chương trình.

- **Nói với thầy:** “Có thể lọc theo sân và ngày; bỏ trống là không áp điều kiện đó.”

### 5.5. Hai lối tra cứu trực tiếp bằng Match ID

- **View Match Details:** tại Guest chọn `3`, rồi `M`.
- **View Seat Map:** tại Guest chọn `4`, rồi `M`.
- Danh sách trận được hiện trước khi hỏi ID; nhập `0` tại ô Match ID để quay lại.
- Chọn `0` tại Guest để về Main Menu.

## 6. Đăng ký, đăng nhập, profile và logout

### 6.1. Register Fan

1. Main Menu chọn `4. Register Fan Account`.
2. Full name `Demo Fan`; email `demo.fan@example.com`; password `DemoPass123`; phone `0901234567`.
3. Ghi Fan ID `F` từ kết quả. Chưa có phiên đăng nhập tự động.
4. Thử đăng ký lại cùng email: bị từ chối trùng email. Nếu đã tạo tài khoản này khi tập trước, dùng email mới và ghi lại.

- **Nói với thầy:** “Đăng ký chỉ tạo Fan, không thể tự đăng ký thành Admin. Sau đăng ký người dùng đăng nhập riêng.”

### 6.2. Login Fan và View Profile

- Main Menu `2`; email `demo.fan@example.com`; password `DemoPass123`.
- Tại Fan dashboard chọn `1`: hiện tên, email, số điện thoại, role `FAN`, status `ACTIVE`.
- **Nói với thầy:** “Thông tin menu lấy từ tài khoản đã xác thực, không lấy role do người dùng nhập.”

### 6.3. Update Profile

- Tại Fan dashboard chọn `3`; new full name `Demo Fan Updated`; new phone `0901234568`.
- Chọn `1` để xem tên và số điện thoại mới.
- Chọn `3` rồi để trống tên: huỷ cập nhật, không ghi đè hồ sơ.
- **Nói với thầy:** “Đổi tên/số điện thoại không làm đổi định danh tài khoản hay quyền.”

### 6.4. Logout và các lỗi xác thực

1. Tại Fan dashboard chọn `0`: có `Logged out.` và quay về Main Menu.
2. Login Fan bằng email đúng nhưng password `wrong`: `Invalid email or password`.
3. Login Fan bằng `em.do@gmail.com` / `123456`: `Account is inactive`.
4. Login Fan bằng tài khoản Admin: hướng dẫn `Use Login As Staff for this account.`
5. Main Menu `3`, nhập tài khoản Fan: hướng dẫn `Use Login As Fan for this account.`
6. Main Menu `2`, tại Email nhấn Enter: huỷ đăng nhập mà không hỏi password.

- **Nói với thầy:** “Sai mật khẩu, tài khoản không hoạt động và dùng sai cổng đăng nhập đều được xử lý, không tạo phiên truy cập sai quyền.”

## 7. Fan: booking, thanh toán, lịch sử và chi tiết vé

Đăng nhập lại `demo.fan@example.com` / `DemoPass123`. Từ dashboard chọn `2. Booking / Payment / Tickets / Refund`.

### 7.1. View Seat Map trước khi đặt

- Chọn `1`; Match ID `M`.
- Chỉ ra tám ghế, giá `250000 VND` và tên `Demo Stand Updated`.
- **Nói với thầy:** “Fan chọn từ Seat ID có trên bảng, không phải đoán ID.”

### 7.2. Create Booking nhưng chưa trả tiền

1. Chọn `2`; Match ID `M`; Seat IDs nhập ID `A1`.
2. Ghi Booking ID `B1` từ biên nhận.
3. Tại `Pay now?` nhập `n`.
4. Chọn `4. My bookings`: `B1` là `PENDING`.
5. Chọn `8. Booking details`; nhập `B1`: có tên Fan, tên trận, ghế và tổng tiền `250000 VND`.
6. Chọn `1`, trận `M`: ghế A1 vẫn `AVAILABLE`.

- **Nói với thầy:** “Booking PENDING ghi nhận yêu cầu và giá, chưa giữ ghế. Hệ thống kiểm tra lại và commit ghế lúc thanh toán, nên không nói rằng tạo booking đã chắc chắn có ghế.”

### 7.3. Cancel Unpaid Booking

1. Tại Booking menu chọn `7`, rồi `2. Cancel unpaid pending booking`.
2. Nhập `B1`, tại xác nhận nhấn Enter: chưa huỷ.
3. Vẫn trong menu Cancellation/Refund, chọn `2`, nhập `B1`, lần này `y`.
4. Kết quả `CANCELLED`. Chọn `0` về Booking menu.

- **Nói với thầy:** “Chưa thanh toán thì huỷ trực tiếp, không cần yêu cầu hoàn tiền và không phát sinh vé.”

### 7.4. Create Booking hai ghế và Pay Pending Booking

1. Chọn `2`; Match ID `M`; Seat IDs là `A1,A2` bằng số, ví dụ `21,22`.
2. Ghi `B2`; tổng tiền `500000 VND`; tại Pay now chọn `n`.
3. Chọn `3. Pay pending booking`: danh sách chỉ gồm booking đang chờ trả tiền.
4. Nhập `B2`; tại Pay now chọn `y`.
5. Kết quả Payment `SUCCESS`; vé tự động xuất hiện cùng ticket code.

- **Nói với thầy:** “Em đang demo thanh toán mô phỏng. Khi thành công booking CONFIRMED, cả hai ghế được bán cùng nhóm và vé online được phát hành tự động.”
- Tạo booking giữ giá tại lúc tạo; không cho người dùng tự nhập một số tiền khác để thanh toán.

### 7.5. My Bookings, Booking Details, My Tickets và Ticket Details

| Feature | Thao tác tại Booking menu | Điều cần chỉ |
| --- | --- | --- |
| My Bookings | `4` | Có `B1 CANCELLED`, `B2 CONFIRMED`; kèm tên trận/ghế. |
| Booking Details | `8`; ID `B2` | Hai ghế, tổng `500000 VND`, đúng Fan sở hữu. |
| My Tickets / Ticket History | `5` | Hai ticket `ISSUED` và mã riêng; ghi Ticket ID `T1`, ticket code `C1` của ghế A1. |
| Ticket Details | `6`; Ticket ID `T1` | Có code, trạng thái, tên ghế/trận và `Not checked in`. |
| Kiểm tra ghế sau mua | `1`; Match ID `M` | A1 và A2 chuyển `BOOKED`. |

- **Nói với thầy:** “Ticket ID dùng tra cứu nội bộ; ticket code là mã đưa cho nhân viên cổng. Lịch sử vé là danh sách vé của chính Fan, gồm trạng thái hiện tại, không phải nhật ký mọi lần đổi trạng thái.”

### 7.6. Đường tắt Book This Match khi đang browse

- Từ Booking chọn `0` về dashboard; chọn `4. Browse matches`.
- Chọn search `1`, keyword `Demo Red`.
- Menu kết quả có thêm `3. Book this match` vì đang đăng nhập Fan.
- Chọn `3`, ID `M`; ở câu hỏi Seat IDs nhập `0` để huỷ, không tạo thêm booking.
- Chọn `0` về browsing; `0` về dashboard; `0` logout.
- **Nói với thầy:** “Cùng là xem trận, nhưng Guest không có nút đặt trực tiếp. Fan đã xác thực có thể đặt từ kết quả tìm kiếm.”

## 8. Seller: tất cả chức năng bán tại quầy

Main Menu `3`, đăng nhập Seller. Tại dashboard chọn `2. Sales counter`.

### 8.1. Search Fan và Create Fan

1. **Search Fan:** chọn `1`; keyword `demo.fan` để tìm Fan online vừa tạo.
2. Chọn `1`; để trống keyword để xem toàn bộ Fan.
3. **Create Fan:** chọn `2`; name `Demo Counter Customer`; email `demo.counter@example.com`; password `Counter123`; phone `0902222222`.
4. Ghi Fan ID khách tại quầy `FC`.

- **Nói với thầy:** “Seller tìm khách đã có trước, chỉ tạo tài khoản Fan nếu khách chưa có.”

### 8.2. View Seat Map và Create Booking For Fan

1. **View seat map:** chọn `3`; Match ID `M`.
2. **Create booking for Fan:** chọn `4`; keyword `demo.counter`; chọn Fan ID `FC` từ kết quả.
3. Match ID `M`; Seat IDs `A3,A4` bằng số.
4. Ghi Booking ID `B3`, trạng thái `PENDING`, tổng `500000 VND`.
5. Booking tại quầy không tự hỏi thanh toán ngay trong bước tạo; tiếp tục chức năng `5` ở dưới.

- **Nói với thầy:** “Booking đứng tên Fan khách hàng. Seller là người thao tác tại quầy, không phải người sở hữu vé.”

### 8.3. Customer Bookings

- Chọn `7`; keyword `demo.counter`; chọn `FC`.
- Kết quả có `B3 PENDING` cùng hai ghế A3/A4.
- **Nói với thầy:** “Seller chọn khách trước rồi mới nhìn các booking của khách, tránh thu tiền nhầm người.”

### 8.4. Accept Offline Payment

1. Chọn `5`; keyword `demo.counter`; Fan ID `FC`; Booking ID `B3`.
2. Hệ thống hiện số tiền phải thu.
3. Nhấn Enter tại câu `Type RECEIVED...`: không ghi nhận thanh toán.
4. Chọn lại `5`, tìm/chọn cùng khách và booking.
5. Gõ `RECEIVED` sau khi giả lập đã thu tiền.
6. Kết quả Payment `SUCCESS`, phương thức `OFFLINE_CASH`.
7. Tại `Issue tickets now?` chọn `n` để còn demo chức năng phát vé riêng.

- **Nói với thầy:** “Offline ở đây là ghi nhận đã nhận tiền mặt, không gọi cổng thanh toán online. Bỏ qua xác nhận thì hệ thống không ghi đã thu tiền.”

### 8.5. Issue Tickets và gọi lại không sinh vé trùng

1. Chọn `6. Issue tickets`; keyword `demo.counter`; chọn `FC`; Booking ID `B3`.
2. Hai vé A3/A4 được phát hành; chỉ ra mã vé.
3. Lặp lại đúng chức năng `6` với cùng khách/booking.
4. Kết quả vẫn trả các vé đã phát hành, không tạo thêm hai vé mới.

- **Nói với thầy:** “Thao tác phát vé gọi lại không làm tăng số vé cho cùng booking và ghế.”
- Ở giao dịch khác có thể chọn `y` ngay sau thanh toán thành công để phát hành mà không phải mở chức năng `6` lại.
- Từ dashboard Seller còn có `4. Browse matches`; chức năng tra cứu dùng chung với mục Guest. Seller không có lựa chọn `Book this match` dành cho Fan; bán hộ khách qua Sales counter.
- Chọn `0` về dashboard; `0` logout.

## 9. Refund: Fan gửi, Admin duyệt hoặc từ chối

### 9.1. Fan tạo hai booking có thể hoàn tiền

Đăng nhập Fan `demo.fan@example.com`, dashboard `2` vào Booking.

1. Chọn `2`, trận `M`, ghế `A5`, tại Pay now chọn `y`; ghi booking `B4`.
2. Chọn `2`, trận `M`, ghế `A6`, tại Pay now chọn `y`; ghi booking `B5`.
3. Hai booking phải `CONFIRMED`, vé chưa check-in; tổng mỗi booking `250000 VND`.

### 9.2. Request Refund và View Refund Status

1. Chọn `7. Cancellation / Refund`.
2. Chọn `1. Request refund for paid booking`; ID `B4`; reason `Cannot attend the match`; ghi Request ID `R1`.
3. Chọn `1`; ID `B5`; reason `Changed travel plan`; ghi `R2`.
4. Chọn `3. My refund requests`: cả hai `REQUESTED`.
5. Có thể thử gửi lại cho `B4`: bị chặn do đã có yêu cầu đang hoạt động.
6. `0` về Booking; `0` về dashboard; `0` logout.

- **Nói với thầy:** “Gửi yêu cầu không đồng nghĩa đã hoàn tiền. Vé và ghế chưa bị giải phóng chỉ vì vừa gửi request.”

### 9.3. Admin Approve Refund

1. Đăng nhập Admin; dashboard `2`; nghiệp vụ `7. Review refund requests`.
2. Danh sách chờ có `R1`, `R2`; chọn `1. Review a request`.
3. Refund request ID `R1`; Approve nhập `y`; note `Approved before kick-off`.
4. Kết quả `APPROVED`.

- **Nói với thầy:** “Service kiểm tra lại điều kiện, huỷ booking và vé liên quan, rồi trả ghế về AVAILABLE. Đây là hoàn tiền mô phỏng; không có tiền thật chuyển tới ngân hàng.”

### 9.4. Admin Reject Refund

1. Vẫn trong màn hình review, request `R2` còn đang chờ.
2. Chọn `1`; ID `R2`; Approve nhập `n`; note `Rejected after policy review`.
3. Kết quả `REJECTED`.
4. Hết yêu cầu chờ thì chương trình tự quay lại menu nghiệp vụ; không nhập thêm số như thể còn ở màn hình review.
5. Nếu vẫn còn request khác trong CSV, chọn `0` để ra khỏi review.

- **Nói với thầy:** “Từ chối request không huỷ vé hợp lệ và không giải phóng ghế của booking.”
- Màn hình yêu cầu có note, nhưng chương trình chưa có engine tự chấm điều kiện chính sách hoàn tiền phức tạp; đây là quyết định của Admin.

### 9.5. Fan kiểm tra kết quả sau review

- Logout Admin, login lại Fan, dashboard `2` vào Booking.
- `7` rồi `3`: `R1 APPROVED`, `R2 REJECTED`; `0` về Booking.
- `4`: `B4 CANCELLED`, `B5 CONFIRMED`.
- `5`: vé của `B4 CANCELLED`; vé `B5` vẫn `ISSUED`.
- `1`, Match ID `M`: A5 `AVAILABLE`, A6 `BOOKED`.
- `0` về dashboard, `0` logout.

## 10. Gate Staff: validate, check-in và chặn dùng vé lại

Đăng nhập `gate1@stadium.com` / `gate123`; dashboard `2. Validate / Check in ticket`.

### 10.1. Validate Ticket

- Chọn `1`; ticket code `C1` của ghế A1; Match ID `M`.
- Kết quả hiện tên trận, ghế và trạng thái `ISSUED`.
- **Nói với thầy:** “Validate là kiểm tra, chưa đánh dấu khách đã vào cổng.”

### 10.2. Check-in nhưng huỷ xác nhận

- Chọn `2`; code `C1`; Match ID `M`.
- Hệ thống kiểm tra và hiện vé hợp lệ trước; tại xác nhận nhập `n`.
- Kết quả `Check-in cancelled.`; vé chưa chuyển `CHECKED_IN`.
- **Nói với thầy:** “Nhân viên có cơ hội đối chiếu trận và ghế trước khi xác nhận.”

### 10.3. Check-in thành công

- Chọn `2`; code `C1`; Match ID `M`; xác nhận `y`.
- Kết quả `CHECKED_IN` và có thời gian check-in.
- **Nói với thầy:** “Chỉ lúc xác nhận thành công trạng thái vé mới đổi.”

### 10.4. Check-in lặp, sai trận, mã không tồn tại

| Tình huống | Thao tác tại Gate menu | Kết quả |
| --- | --- | --- |
| Dùng lại vé | `2`; code `C1`; Match ID `M` | Từ chối vì đã check-in; không hỏi xác nhận lại. |
| Sai trận | `1`; dùng code của vé B2 ghế A2 chưa check-in; Match ID của trận khác, ví dụ `1` nếu `M` không phải `1` | Từ chối do không đúng trận. |
| Mã không tồn tại | `2`; code `UNKNOWN_TICKET`; Match ID `M` | `Ticket not found`, không hỏi xác nhận. |
| Vé đã hoàn tiền | `1`; code vé cũ của B4; Match ID `M` | Từ chối vì vé đã huỷ. Lấy code từ Ticket History của Fan nếu cần. |

- Chọn `0` về dashboard; `0` logout.
- **Nói với thầy:** “Hệ thống không chỉ kiểm tra có mã vé, mà còn kiểm tra trận và trạng thái sử dụng.”

## 11. Administrator: toàn bộ quản lý Fan và Staff

Đăng nhập Admin, dashboard `2` vào menu nghiệp vụ.

### 11.1. Fan Management

Chọn `3. Fan accounts`. Dùng một tài khoản phụ, không xoá `F` đã có booking.

| Feature | Thao tác tại Fan accounts | Kết quả / lời giải thích |
| --- | --- | --- |
| Create Fan Account | `1`; name `Admin Created Fan`; email `demo.adminfan@example.com`; password `AdminFan123`; phone `0903333333` | Ghi ID `FX`; không có câu hỏi role vì đây là Fan. |
| View Fan List | `2` | Có `FX` trong danh sách. |
| View Fan Details | `3`; `FX` | Đúng thông tin vừa tạo. |
| Search Fan | `4`; keyword `demo.adminfan` | Tìm được tài khoản phụ. |
| Filter Fan Status | `5`; status `1` | Lọc ACTIVE; `2` là INACTIVE, `0` là tất cả. |
| Update Fan | `6`; `FX`; name `Admin Fan Updated`; email cũ; password Enter; phone `0903333334` | Đổi thông tin, giữ mật khẩu cũ; không đổi role. |
| Deactivate Fan | `8`; `FX`; `y` | `INACTIVE`; lọc `5`, status `2` để nhìn thấy. |
| Activate Fan | `7`; `FX` | Trở lại `ACTIVE`. |
| Delete Fan Account | `9`; `FX`; `y` | Xoá được vì tài khoản phụ chưa có booking. |
| Chặn xoá Fan có lịch sử | `9`; Fan ID `F`; `y` | Bị từ chối vì Fan đã có booking history. |

- **Nói với thầy:** “Deactivate là ngừng hoạt động tài khoản, không xoá lịch sử. Code hiện tại chưa có hai trạng thái LOCKED và INACTIVE riêng biệt.”
- Chọn `0` về menu nghiệp vụ.

### 11.2. Staff Management

Chọn `4. Staff accounts`. Không đổi role hay ngừng hoạt động của Admin đang dùng.

| Feature | Thao tác tại Staff accounts | Kết quả / lời giải thích |
| --- | --- | --- |
| Create Staff Account | `1`; name `Demo Staff`; email `demo.staff@example.com`; password `StaffPass123`; phone `0904444444`; role `1` | Tạo Seller phụ, ghi ID `ST`. |
| View Staff List | `2` | Có `ST`, role `SELLER`. |
| View Staff Details | `3`; `ST` | Hiện role/status hiện tại. |
| Search Staff | `4`; keyword `demo.staff` | Tìm đúng nhân viên phụ. |
| Filter Role/Status | `5`; role `1`; status `1` | Lọc Seller ACTIVE. Role `0` là tất cả; status `0` là tất cả. |
| Update Staff | `6`; `ST`; name `Demo Staff Updated`; email cũ; password Enter; phone `0904444445` | Cập nhật thông tin, không hỏi đổi role. |
| Deactivate Staff | `8`; `ST`; `y` | INACTIVE. |
| Activate Staff | `7`; `ST` | ACTIVE. |
| Change Staff Role | `10`; `ST`; new role `2` | Đổi từ Seller sang Gate Staff. |
| Revoke Staff Role | `11`; `ST`; `y` | Role `UNASSIGNED`; chưa được vào menu nghiệp vụ. |
| Assign Staff Role | `9`; `ST`; role `1` | Gán lại Seller cho nhân viên chưa có role. |
| Xem role có thể chọn | Nhìn menu role khi tạo/gán/đổi hoặc lọc | `1 Seller`, `2 Gate`, `3 Admin`, `4 Simulator`, `5 Unassigned`. |

- Trong thao tác gán/đổi role, không chọn `5 Unassigned`: Service sẽ yêu cầu dùng chức năng Revoke để thu hồi role.
- **Nói với thầy:** “Assign dành cho nhân viên chưa có role; Change dành cho đổi role đã có; Revoke thu hồi role. Chưa có chức năng Delete Staff trong menu hiện tại.”
- **Bảo vệ Admin cuối cùng, nếu thầy hỏi:** hệ thống chặn deactivate/đổi role/thu hồi role làm mất Admin ACTIVE cuối cùng. Chỉ thử trên dữ liệu demo sau khi chắc chắn hiểu số tài khoản Admin hiện có.
- Chọn `0` về nghiệp vụ; có thể logout rồi login bằng `demo.staff@example.com` / `StaffPass123` để thấy menu Seller; logout và quay lại Admin nếu demo tiếp summary.

## 12. Profile dùng chung của Staff và Simulator

### 12.1. Staff thông thường

- Tại dashboard Seller/Gate/Admin: `1` xem profile; `3` update profile; `0` logout.
- Demo trên Seller phụ `ST` để không đổi tài khoản mẫu: tên `Demo Staff Profile`, phone `0904444446`.
- Sau cập nhật chọn `1` để chứng minh đã đổi, sau đó logout.

### 12.2. Simulator Operator hiện tại

1. Main Menu `3`; login `sim@stadium.com` / `sim123`.
2. Dashboard `1`: xem profile.
3. Dashboard `2`: update profile, hoặc Enter tại tên để huỷ.
4. Dashboard `0`: logout.

- **Nói với thầy:** “Role Simulator đã có xác thực và hồ sơ. Cấu hình, chạy, so sánh và xuất kết quả concurrency chưa được triển khai. Em không coi phần thiết kế trên sơ đồ là feature đã chạy.”

## 13. System Summary và Audit Log

Admin dashboard `2` vào nghiệp vụ.

### 13.1. View System Summary

- Chọn `6`.
- Chỉ ra `Total fans`, `Total staff`, `Total stadiums`, `Total matches`, `Total bookings`, `Total tickets`.
- Số liệu phụ thuộc bản sao CSV và những thao tác vừa demo, không cam kết một con số cố định.
- **Nói với thầy:** “Đây là đếm bản ghi hiện có, không phải dashboard doanh thu, cũng không phải số vé còn hiệu lực. Vé CANCELLED vẫn có trong tổng bản ghi vé.”

### 13.2. View Audit Log

- Chọn `5`.
- Tìm dòng mới `REVIEW_REFUND` của `R1` hoặc `R2`: actor ID, target REFUND, kết quả và thời gian.
- **Nói với thầy:** “Trong code hiện tại, việc review refund có ghi audit. Những dòng cũ có thể là seed; chưa phải mọi thao tác CRUD hay mọi lỗi đều tự ghi audit.”
- Không trình bày Audit Log nghiệp vụ này như file AI Audit; hai loại nhật ký có mục đích khác nhau.

## 14. CSV Persistence: thoát và mở lại vẫn còn dữ liệu

1. Từ nghiệp vụ Admin chọn `0` về dashboard, `0` logout, `0` tại Main để thoát.
2. Terminal chạy lại **đúng thư mục demo cũ**:

```bash
java -cp out app.Main "$DEMO_DIR"
```

3. Đăng nhập Fan `demo.fan@example.com` / `DemoPass123`.
4. Dashboard `1`: tên đã cập nhật vẫn còn.
5. Dashboard `2`, Booking `4`/`5`: lịch sử booking và trạng thái vé vẫn còn.
6. Đưa thầy xem đường dẫn `DEMO_DIR` nếu cần minh hoạ file lưu trữ; chỉ đọc CSV, không sửa bằng tay giữa lúc demo.

- **Nói với thầy:** “Dữ liệu không chỉ tồn tại trong RAM của phiên console. Repository ghi CSV, lần chạy tiếp theo nạp lại. Tuy nhiên nhiều file CSV không phải một transaction ACID như cơ sở dữ liệu.”

## 15. Các tình huống lỗi nên chọn demo thêm

Không cần làm hết nếu thời lượng ngắn. Chọn 3-5 trường hợp thầy quan tâm.

| Tình huống | Cách demo | Mong đợi |
| --- | --- | --- |
| Menu nhập chữ | Ở một menu gõ `abc` | Thông báo số không hợp lệ, chương trình còn chạy. |
| ID âm | Ở ô Match ID nhập `-1` | Báo ID phải dương. |
| Quay lại khi nhập ID | Nhập `0` hoặc để trống tại ô ID có hướng dẫn Back | Không thực hiện thao tác. |
| Huỷ đăng ký/tạo tài khoản | Enter tại Full name | Không hỏi tiếp mọi trường hoặc tạo tài khoản rỗng. |
| Seat ID trùng | Fan Booking `2`, `M`, nhập cùng ID hai lần, ví dụ `21,21` | Chặn ghế trùng trước tạo booking. |
| Chọn quá bốn ghế | Fan Booking `2`, `M`, nhập năm ID khác nhau | `Choose 1 to 4 seats`. |
| Danh sách Seat ID lỗi | Nhập `21,abc` hoặc `21,` | Thông báo nhập số hợp lệ, không tạo booking một phần. |
| Ghế đã bán | Fan khác thử chọn A1 đang BOOKED | Từ chối đặt ghế đó; không tạo vé trùng. |
| Thu tiền nhưng chưa xác nhận | Seller Pay, Enter thay vì RECEIVED | Không ghi nhận đã nhận tiền mặt. |
| Request refund trùng | Gửi lần hai cho booking có request REQUESTED | Từ chối tạo request đang hoạt động thứ hai. |
| Refund vé đã dùng | Fan request refund B2 sau khi C1 CHECKED_IN | `Used or cancelled ticket cannot be refunded`. |
| Đóng bán rồi đặt | Admin đóng M bằng `12`, `M`, `y`; Fan Booking `2`, chọn M | Báo đóng bán, không hỏi chọn ghế; sau demo nhớ mở lại. |
| Truy cập booking Fan khác | Login Fan Binh, tạo một booking PENDING A7 rồi chọn Booking Details `8`, nhập B2 | Từ chối đọc booking của Fan khác. Booking phụ không trả tiền thì huỷ sau demo. |
| Sai khách tại quầy | Seller chọn khách có một booking PENDING, nhưng nhập ID booking của khách khác ở chức năng `5` | Báo booking thuộc khách khác. Phải có booking PENDING để màn hình có bước nhập ID. |
| Không có booking chờ trả tiền | Fan có tất cả booking đã CONFIRMED/CANCELLED, chọn `3` | Báo không có booking chờ; không hỏi Booking ID. |
| Không có khách phù hợp | Seller `4`, keyword `khongcotentaikhoannay` | Báo không tìm thấy và gợi ý tạo Fan; không bắt nhập Fan ID. |
| Tên chứa dấu phẩy | Nhập tên tài khoản `Demo,Fan` với các trường khác hợp lệ | Chặn dữ liệu không phù hợp định dạng CSV hiện tại. |

- **Nói với thầy:** “Hệ thống xử lý cả thao tác người dùng làm sai, không chỉ luồng nhập đúng.”
- Thử hai người mua ghế đã bán lần lượt chỉ chứng minh kiểm tra nghiệp vụ; **không đủ chứng minh chống race condition dưới nhiều thread**.

## 16. Demo thanh toán thất bại riêng, không làm lệch luồng chính

1. Thoát ứng dụng về Terminal, giữ biến `DEMO_DIR`.
2. Tạo bản sao thứ hai từ trạng thái demo đang có:

```bash
FAIL_DIR=$(mktemp -d /tmp/stadium-payment-fail.XXXXXX)
```

```bash
cp "$DEMO_DIR"/*.csv "$FAIL_DIR/"
```

```bash
java -cp out app.Main "$FAIL_DIR" --payment-fails
```

3. Login Fan demo; dashboard `2`; Booking `2`; trận `M`; chọn A7 nếu còn AVAILABLE; Pay now `y`.
4. Kết quả Payment `FAILED`; booking `CANCELLED`; không có vé mới của booking này.
5. Xem seat map để chứng minh A7 không bị bán do payment lỗi.
6. Nếu A7 đã dùng ở bài test phụ, chọn A8 hoặc một ghế AVAILABLE khác.
7. Thoát; muốn quay lại bản demo chính thì chạy `java -cp out app.Main "$DEMO_DIR"` không có cờ lỗi.

- **Nói với thầy:** “Cờ này cấu hình MockPaymentService để giả lập thất bại cho online payment. Không phải cổng thanh toán thật hay tính năng tự chọn kết quả thanh toán của Fan.”

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
