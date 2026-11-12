# Ticket History

- Dùng để xem từng vé và trạng thái sau mua, sau hoàn tiền hoặc sau sử dụng; một booking có thể có nhiều vé.
- Luồng xử lý trong code `MainController → BookingController → FanController.getMyTickets → FanService.getMyTickets → TicketRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-075"></a>

## Flow 075 — My Tickets / Ticket History

- Bắt đầu ở Main của CORE_DIR sau [Flow 074](../05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md#flow-074).

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
6. 1
7. 0
8. 0
9. 0
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Bước 7 — Nhập `0` rồi Enter

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

### Bước 8 — Nhập `0` rồi Enter

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

### Bước 9 — Nhập `0` rồi Enter

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

- Xem từng vé và trạng thái sau mua, sau hoàn tiền hoặc sau sử dụng; một booking có thể có nhiều vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-098"></a>

## Flow 098 — Ticket History sau hoàn tiền

- Bắt đầu ở Main của CORE_DIR sau [Flow 097](../07_Cancellation_Refund/03_My_Refund_Requests.md#flow-097).

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
6. 1
7. 0
8. 0
9. 0
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED
Ticket #4 | Booking #5 | Flow Red vs Flow Blue | Flow Stand Updated A5 | Code T-5-25 | Status CANCELLED
Ticket #5 | Booking #6 | Flow Red vs Flow Blue | Flow Stand Updated A6 | Code T-6-26 | Status ISSUED

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Bước 7 — Nhập `0` rồi Enter

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

### Bước 8 — Nhập `0` rồi Enter

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

### Bước 9 — Nhập `0` rồi Enter

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

- Xem từng vé và trạng thái sau mua, sau hoàn tiền hoặc sau sử dụng; một booking có thể có nhiều vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
