# Chuẩn bị demo

Bản ghi ngày 13/11/2026. Có 87 file tính năng, 148 flow và 1546 lượt nhập/phản hồi
được chạy lại từ code hiện tại.

## Biên dịch

Chạy từ thư mục gốc với JDK 17 trở lên.

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
```

## CORE_DIR cho Flow 001–142

Dùng fixture nhỏ đi kèm để các ID và trạng thái ban đầu khớp bản ghi.
Mỗi flow bắt đầu tại Main. Các flow tạo dữ liệu là tiền đề cho flow tiếp theo.
Các mã 091 và 133–135 đã rút khỏi scope.

```bash
CORE_DIR=$(mktemp -d /tmp/stadium-core-demo.XXXXXX)
cp docs/Demo_Tung_Feature/fixtures/core/*.csv "$CORE_DIR/"
java -cp out app.Main "$CORE_DIR"
```

Giữ nguyên CORE_DIR trong cả chuỗi. Sau mỗi flow, các số 0 đã ghi đưa về Main.
Khi chạy lại ứng dụng, dùng cùng thư mục để giữ dữ liệu đã tạo.

- Fan Flow có ID 6, email flow.fan@example.com, mật khẩu FlowPass123.
- Sân Flow có ID 3, Section Flow ID 6, ghế 21–28 và Match Flow ID 4.
- Khách quầy tạo ở Flow 083 có Fan ID 7. Booking tạo ở Flow 085 có ID 7.
- Match 4 trong CORE_DIR là Flow Red vs Flow Blue.
- Fixture có một số tài khoản INACTIVE để demo từ chối truy cập.
- Khi trình bày ngày khác, kiểm tra các ngày lọc/trận trong fixture trước buổi demo.

Booking mới giữ ghế tối đa 30 phút, kết thúc sớm khi trận bắt đầu. Khi demo bị
gián đoạn lâu, tạo lại bản sao và chạy lại các tiền đề thay vì dùng booking đã hết hạn.
Main hiển thị hạn thanh toán trong booking. Fan có thể trả online ngay hoặc sau,
Seller có thể nhận thanh toán offline trong cùng hạn đó.

### Flow 141 giả lập thanh toán online thất bại

Thoát ứng dụng ở Main rồi chạy cùng CORE_DIR với cờ sau.

```bash
java -cp out app.Main "$CORE_DIR" --payment-fails
```

## COUNTER_DEMO_DIR cho Flow 143–150

Dùng bản sao dữ liệu đầy đủ hiện tại để demo khách vãng lai.
Chạy các flow theo thứ tự, giữ nguyên thư mục này trong cả nhóm.

```bash
COUNTER_DEMO_DIR=$(mktemp -d /tmp/stadium-counter-demo.XXXXXX)
cp data/*.csv "$COUNTER_DEMO_DIR/"
java -cp out app.Main "$COUNTER_DEMO_DIR"
```

Match 4 của bản sao này là Vietnam vs China. Các SEAT ID trong bản ghi là
các ghế còn AVAILABLE lúc ghi nhận. Nếu nguồn đã bán các ghế đó, chọn ghế còn
trống trên sơ đồ. Dùng Booking ID vừa hiển thị cho những flow thanh toán/xuất vé
tiếp theo. Khách vãng lai mua trực tiếp bằng mã booking và mã vé.

- Flow 143 tạo booking giữ bốn ghế.
- Flow 144 nhận tiền offline cho booking đó.
- Flow 145 tạo booking vãng lai khác để trả sau.
- Flow 146 nhận tiền offline cho booking thứ hai sau khi chạy lại ứng dụng.
- Flow 147 xuất vé bằng thao tác Issue ticket riêng.
- Flow 148–150 kiểm tra tài khoản INACTIVE và nguyên nhân Seat IDs không hợp lệ.

## EXPIRY_DEMO_DIR cho Flow 151–152

Bản sao kiểm thử dưới đây có một booking ID 3 đã quá hạn, giữ ghế 2 của Match 1.
Timestamp cố định giúp kiểm tra hết hạn ngay khi mở ứng dụng.

```bash
EXPIRY_DEMO_DIR=$(mktemp -d /tmp/stadium-expiry-demo.XXXXXX)
cp docs/Demo_Tung_Feature/fixtures/core/*.csv "$EXPIRY_DEMO_DIR/"
printf '%s\n' '3,2,1,2,500000,PENDING,2020-01-01T10:00,2020-01-01T10:30' >> "$EXPIRY_DEMO_DIR/bookings.csv"
awk -F, 'BEGIN {OFS=","} {if ($2==1 && $3==2) {$4="LOCKED"; $5=$5+1; $6=3}; print}' "$EXPIRY_DEMO_DIR/match_seats.csv" > "$EXPIRY_DEMO_DIR/match_seats.tmp"
mv "$EXPIRY_DEMO_DIR/match_seats.tmp" "$EXPIRY_DEMO_DIR/match_seats.csv"
java -cp out app.Main "$EXPIRY_DEMO_DIR"
```

- Flow 151 đăng nhập Fan binh.tran@gmail.com / 12345678, xem EXPIRED trong lịch sử
  và thử Pay pending booking.
- Flow 152 đăng nhập Seller và nhập Booking ID 3 tại Accept offline payment.
- Booking quá hạn bị từ chối trước bước ghi nhận tiền, ghế được giải phóng.

## Bảy feature Seller

1. Search Fan.
2. Create Fan.
3. Create booking for Fan, gồm nhánh tài khoản ACTIVE hoặc khách vãng lai.
4. Accept offline payment.
5. Issue ticket.
6. View seat map and prices.
7. View customer booking history.

Tạo booking, nhận tiền offline và xuất vé là ba thao tác riêng.
Các chức năng profile/login/logout dùng chung ở dashboard tài khoản.

## Dữ liệu nguồn và bản sao

Nguồn hiện có 54 trận, hai sân, năm section và 10100 ghế vật lý, cùng các tài khoản
và lịch sử nghiệp vụ. Các kịch bản demo ghi vào đúng thư mục bản sao tương ứng.
Booking cũ sáu cột thiếu timestamp được coi là quá hạn nếu còn PENDING. Booking
CONFIRMED và lịch sử thanh toán/vé vẫn được đọc theo trạng thái đã lưu.
