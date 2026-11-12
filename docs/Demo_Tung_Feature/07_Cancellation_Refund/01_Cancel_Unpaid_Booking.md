# Cancel Unpaid Booking

- Dùng để huỷ booking pending chưa có payment; booking đã trả tiền đi qua yêu cầu hoàn tiền.
- Luồng xử lý trong code `MainController → BookingController.cancelBooking → BookingService.cancelBooking → BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-072"></a>

## Flow 072 — Cancel Unpaid Pending Booking

- Bắt đầu ở Main của CORE_DIR sau [Flow 071](../05_Fan_Booking_Tickets/04_Booking_Details.md#flow-071).

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
1. 2
2. flow.fan@example.com
3. FlowPass123
4. 2
5. 4
6. 2
7. 3
8. y
9. 0
10. 0
11. 0
```

### Bước 1 — Nhập `2` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `flow.fan@example.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `FlowPass123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING
Booking ID: (0 = Back):
```

### Bước 7 — Nhập `3` rồi Enter

Console trả về

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING
Cancel this unpaid booking? (y/n, Enter = No):
```

### Bước 8 — Nhập `y` rồi Enter

Console trả về

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: CANCELLED

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Logged out.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Huỷ booking pending chưa có payment; booking đã trả tiền đi qua yêu cầu hoàn tiền.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
