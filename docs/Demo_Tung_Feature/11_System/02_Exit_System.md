# Exit System

- Dùng để kết thúc vòng lặp Main và đóng Scanner.
- Luồng xử lý trong code `MainController.start → MainView.displayMessage → Main.main đóng Scanner`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-142"></a>

## Flow 142 — Exit System

- Bắt đầu ở Main của CORE_DIR sau [Flow 141](../05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md#flow-141).

### Console ban đầu

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Toàn bộ lượt nhập theo thứ tự

```text
1. 0
```

### Bước 1 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Kết thúc vòng lặp Main và đóng Scanner.
