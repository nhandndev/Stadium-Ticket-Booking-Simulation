# Booking Details

- Dùng để xem khách, trận, danh sách ghế, tổng tiền và trạng thái của booking thuộc quyền truy cập.
- Luồng xử lý trong code `MainController → BookingController.getBookingDetails → BookingService → BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-071"></a>

## Flow 071 — Booking Details

- Bắt đầu ở Main của CORE_DIR sau [Flow 070](../05_Fan_Booking_Tickets/03_Booking_History.md#flow-070).

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
5. 2
6. 2
7. 3
8. 0
9. 0
10. 0
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

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING
  Payment deadline: 2026-11-13T00:17:29.188470
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
Seats held until: 2026-11-13T00:17:29.188470
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Bước 8 — Nhập `0` rồi Enter

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

### Bước 9 — Nhập `0` rồi Enter

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

### Bước 10 — Nhập `0` rồi Enter

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

- Xem khách, trận, danh sách ghế, tổng tiền và trạng thái của booking thuộc quyền truy cập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
