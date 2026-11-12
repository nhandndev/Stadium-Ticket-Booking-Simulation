# Chuẩn bị trước khi demo

Bản ghi ngày 12/11/2026. Có 88 file tính năng, 139 flow và 1469 lượt nhập/phản hồi được ghi từ ứng dụng hiện tại.

## Dữ liệu sau khi sửa CSV

- Có 2 sân, 5 section và 4 trận hiện có.
- Có 1.007 Fan, trong đó 1.005 ACTIVE; 1.000 tài khoản simfan có tên ngẫu nhiên khác nhau.
- Có 10.100 ghế. Section 1 và 2 có 10 ghế mỗi section; Section 3 có 100 ghế; Section 4 và 5 có 4.990 ghế mỗi section.
- Các ghế trước đây thuộc Section 6/7/8 đã chuyển sang Section 4/5/3. Số ghế, ID, hàng, số chỗ và trạng thái active được giữ lại.
- Sân Seed Capacity Stadium và ba dòng giá của Match 5 đã xoá. Bộ dữ liệu dùng các sân/section hiện có.
- Tổng dữ liệu có 11.191 dòng không trống. Các liên kết CSV đã được kiểm tra lại.

## Cách đọc

- Mỗi bước ghi rõ giá trị cần gõ và Console trả về sau khi Enter.
- Nhấn Enter để trống nghĩa là không gõ chữ Enter.
- Trong danh sách lượt nhập, số đứng trước dấu chấm là số thứ tự bước.
- 139 flow còn lại trong dải 001–142 dùng chung CORE_DIR. Chạy theo [thứ tự đầy đủ](01_THU_TU_DEMO.md) để các ID và trạng thái khớp.
- Guest dùng lịch hiện có ngày ghi nhận. Khi trình bày vào ngày khác, chọn trận còn xuất hiện trong lịch hoặc điều chỉnh lịch trên bản sao demo.

## Biên dịch

Mở Terminal ở thư mục gốc dự án. Chạy lệnh này một lần.

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
```

## Bản demo nghiệp vụ CORE_DIR

Bản này giữ tập bản ghi nhỏ từ CSV hiện tại để các ID tạo mới thống nhất với transcript. Những tài khoản và bản ghi tạo trong buổi demo đều nằm trong CORE_DIR.

```bash
CORE_DIR=$(mktemp -d /tmp/stadium-core-demo.XXXXXX)
cp data/*.csv "$CORE_DIR/"

awk -F, 'NF && $1 <= 5' data/fans.csv > "$CORE_DIR/fans.csv"
awk -F, 'NF && $1 <= 2' data/stadiums.csv > "$CORE_DIR/stadiums.csv"
awk -F, 'NF && $1 <= 5' data/sections.csv > "$CORE_DIR/sections.csv"
awk -F, 'NF && $1 <= 20' data/seats.csv > "$CORE_DIR/seats.csv"
awk -F, 'NF && $1 <= 3' data/matches.csv > "$CORE_DIR/matches.csv"
awk -F, 'NF && $1 <= 2' data/bookings.csv > "$CORE_DIR/bookings.csv"
awk -F, 'NF && $1 <= 1' data/tickets.csv > "$CORE_DIR/tickets.csv"
awk -F, 'NF && $1 <= 2' data/payments.csv > "$CORE_DIR/payments.csv"
awk -F, 'NF && $1 <= 1' data/refunds.csv > "$CORE_DIR/refunds.csv"
awk -F, 'NF && $1 <= 3 && $2 <= 5' data/ticket_prices.csv > "$CORE_DIR/ticket_prices.csv"
awk -F, 'NF && $2 <= 3' data/match_seats.csv > "$CORE_DIR/match_seats.csv"
awk -F, 'NF && $2 != "" && $2 <= 2' data/transactions.csv > "$CORE_DIR/transactions.csv"

java -cp out app.Main "$CORE_DIR"
```

Giữ nguyên biến CORE_DIR và thư mục này trong cả buổi. Khi thoát ứng dụng rồi chạy lại, dùng cùng đường dẫn để thấy dữ liệu đã lưu.

### ID chính sau khi làm các flow tạo dữ liệu

- Fan Flow có ID 6, email flow.fan@example.com, mật khẩu FlowPass123.
- Sân Flow có ID 3, Section Flow ID 6, tám ghế A1–A8 có ID 21–28.
- Match Flow có ID 4.
- Booking 3 là lượt pending rồi huỷ. Booking 4 là lượt thanh toán hai ghế online.
- Mã vé được dùng tại Gate là T-4-21.
- Booking 5 được yêu cầu rồi duyệt hoàn tiền. Booking 6 được yêu cầu rồi từ chối.
- Fan khách quầy có ID 7. Booking cash của khách có ID 7.
- Fan phụ ID 8 và Staff phụ ID 6 dùng cho quản lý tài khoản.

Đây là các ID trong CORE_DIR theo thứ tự demo. Mỗi màn hình vẫn hiển thị ID thực tế để kiểm tra trước khi nhập.

### Flow thanh toán thất bại

Tới Flow 141, từ Main nhập 0 để thoát rồi chạy lại.

```bash
java -cp out app.Main "$CORE_DIR" --payment-fails
```

Cờ này làm MockPaymentService trả thất bại cho lần chạy ứng dụng đó.


## Tài khoản demo

- Administrator dùng admin@stadium.com / admin123.
- Seller dùng seller1@stadium.com / seller123.
- Gate Staff dùng gate1@stadium.com / gate123.
- Fan mới trong flow dùng flow.fan@example.com / FlowPass123.
- Tài khoản Staff ID 5 hiện UNASSIGNED, cần Administrator cấp role trước khi đăng nhập.

## Kết quả ghi nhận

- Đã replay 139 flow nghiệp vụ bằng ứng dụng thật trên CSV bản sao.
- Các mã flow cũ 133–135 đã rút khỏi danh mục. Sau 132 tiếp tục 136.
- Booking dùng cơ chế SYNCHRONIZED mặc định khi thanh toán.
- PENDING lưu báo giá. Ghế được kiểm tra lại và commit khi checkout.
