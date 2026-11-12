# Pay Pending Booking

- Dùng để thanh toán online trước expiresAt, kiểm tra đúng chủ sở hữu ghế giữ bằng SYNCHRONIZED rồi xác nhận và phát hành vé. Flow lỗi dùng cờ --payment-fails.
- Luồng xử lý trong code `MainController → BookingController.makeOnlinePayment → BookingService → MockPaymentService / StrategyFactory / SynchronizedStrategy → CSV repositories`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-074"></a>

## Flow 074 — Pay Pending Booking

- Bắt đầu ở Main của CORE_DIR sau [Flow 073](../05_Fan_Booking_Tickets/02_Create_Booking.md#flow-073).

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
  Payment deadline: 2026-11-13T00:17:30.823218
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
Seats held until: 2026-11-13T00:17:30.823218
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

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

- Thanh toán online trước expiresAt, kiểm tra đúng chủ sở hữu ghế giữ bằng SYNCHRONIZED rồi xác nhận và phát hành vé. Flow lỗi dùng cờ --payment-fails.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-141"></a>

## Flow 141 — Thanh toán online thất bại

- Bắt đầu ở Main của CORE_DIR sau [Flow 140](../05_Fan_Booking_Tickets/02_Create_Booking.md#flow-140).
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
Seats held until: 2026-11-13T00:17:57.050284
Pay online now or give this Booking ID to a Seller for offline payment before the deadline.

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

- Thanh toán online trước expiresAt, kiểm tra đúng chủ sở hữu ghế giữ bằng SYNCHRONIZED rồi xác nhận và phát hành vé. Flow lỗi dùng cờ --payment-fails.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-151"></a>

## Flow 151 — Expired booking is visible in Fan history and cannot be paid online

- Bắt đầu ở Main của EXPIRY_DEMO_DIR. Theo thứ tự được ghi trong file chuẩn bị.

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
2. binh.tran@gmail.com
3. 12345678
4. 2
5. 2
6. 1
7. 3
8. 0
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

### Bước 2 — Nhập `binh.tran@gmail.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `12345678` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Tran Thi Binh
Email:  binh.tran@gmail.com
Phone:  0912345602
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Booking #3 | Vietnam vs Thailand | [Stand A VIP A2] | 500000 VND | EXPIRED

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Bước 7 — Nhập `3` rồi Enter

Console trả về

```text
No active bookings are waiting for payment. Unpaid bookings expire after at most 30 minutes; check booking history.

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Thanh toán online trước expiresAt, kiểm tra đúng chủ sở hữu ghế giữ bằng SYNCHRONIZED rồi xác nhận và phát hành vé. Flow lỗi dùng cờ --payment-fails.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.
