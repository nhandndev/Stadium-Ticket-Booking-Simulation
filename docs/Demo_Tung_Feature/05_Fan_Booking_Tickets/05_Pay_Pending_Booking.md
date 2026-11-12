# Pay Pending Booking

- Dùng để thanh toán mock cho booking pending, kiểm tra lại ghế bằng SYNCHRONIZED rồi xác nhận và phát hành vé online. Flow lỗi dùng cờ --payment-fails.
- Luồng xử lý trong code `MainController → BookingController.makeOnlinePayment → BookingService → MockPaymentService / StrategyFactory / SynchronizedStrategy → CSV repositories`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-074"></a>

## Flow 074 — Pay Pending Booking

- Bắt đầu ở Main của CORE_DIR sau [Flow 073](02_Create_Booking.md#flow-073).

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
6. 3
7. 4
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

### Bước 6 — Nhập `3` rồi Enter

Console trả về

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | PENDING
Booking ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: PENDING

=== MOCK ONLINE PAYMENT ===
Amount due: 500000 VND
Pay now? (y/n, Enter = No):
```

### Bước 8 — Nhập `y` rồi Enter

Console trả về

```text
Payment #3 | Booking #4 | Amount 500000 VND | Method ONLINE | Status SUCCESS
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
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

- Thanh toán mock cho booking pending, kiểm tra lại ghế bằng SYNCHRONIZED rồi xác nhận và phát hành vé online. Flow lỗi dùng cờ --payment-fails.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-141"></a>

## Flow 141 — Thanh toán online thất bại

- Bắt đầu ở Main của CORE_DIR sau [Flow 140](02_Create_Booking.md#flow-140).
- Đóng ứng dụng ở Main bằng 0 rồi chạy lại cùng CORE_DIR với cờ lỗi mock.

```bash
java -cp out app.Main "$CORE_DIR" --payment-fails
```


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
5. 1
6. 2
7. 4
8. 27
9. y
10. 0
11. 0
12. 0
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | BOOKED
24       | A4       | Flow Stand Updated       | 250000       | BOOKED
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 8 — Nhập `27` rồi Enter

Console trả về

```text
=== BOOKING #8 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A7] | IDs: [27]
Total: 250000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== MOCK ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Bước 9 — Nhập `y` rồi Enter

Console trả về

```text
Payment #7 | Booking #8 | Amount 250000 VND | Method ONLINE | Status FAILED
Payment failed. This booking is cancelled; check availability and create a new booking.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

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

### Bước 11 — Nhập `0` rồi Enter

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

### Bước 12 — Nhập `0` rồi Enter

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

- Thanh toán mock cho booking pending, kiểm tra lại ghế bằng SYNCHRONIZED rồi xác nhận và phát hành vé online. Flow lỗi dùng cờ --payment-fails.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
