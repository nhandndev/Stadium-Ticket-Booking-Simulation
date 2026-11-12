# Ticket Details

- Dùng để tra mã vé, trận, ghế, trạng thái và thời điểm check-in của một vé.
- Luồng xử lý trong code `MainController → BookingController → TicketController.getTicketDetails → TicketService → TicketRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-076"></a>

## Flow 076 — Ticket Details

- Bắt đầu ở Main của CORE_DIR sau [Flow 075](06_Ticket_History.md#flow-075).

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
5. 3
6. 2
7. 2
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED
Ticket ID: (0 = Back):
```

### Bước 7 — Nhập `2` rồi Enter

Console trả về

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1
Code: T-4-21 | Status: ISSUED
Checked in at: Not checked in

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
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

- Tra mã vé, trận, ghế, trạng thái và thời điểm check-in của một vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
