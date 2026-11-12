# Request Refund

- Dùng để fan gửi lý do hoàn tiền cho booking đã trả tiền; vé đã check-in bị chặn.
- Luồng xử lý trong code `MainController → BookingController → RefundController.requestRefund → RefundService → RefundRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-092"></a>

## Flow 092 — Request Refund cho B4

- Bắt đầu ở Main của CORE_DIR sau [Flow 090](../06_Seller/07_Issue_Tickets.md#flow-090).

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
6. 1
7. 5
8. Cannot attend
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Bước 7 — Nhập `5` rồi Enter

Console trả về

```text
=== BOOKING #5 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A5] | IDs: [25]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Bước 8 — Nhập `Cannot attend` rồi Enter

Console trả về

```text
Refund request #2 | Booking #5 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.

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

- Fan gửi lý do hoàn tiền cho booking đã trả tiền; vé đã check-in bị chặn.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-093"></a>

## Flow 093 — Request Refund cho B5

- Bắt đầu ở Main của CORE_DIR sau [Flow 092](../07_Cancellation_Refund/02_Request_Refund.md#flow-092).

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
6. 1
7. 6
8. Changed travel plan
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Bước 7 — Nhập `6` rồi Enter

Console trả về

```text
=== BOOKING #6 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A6] | IDs: [26]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Bước 8 — Nhập `Changed travel plan` rồi Enter

Console trả về

```text
Refund request #3 | Booking #6 | Status REQUESTED
Admin review is required. Refunds are mocked; no real money is transferred.

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

- Fan gửi lý do hoàn tiền cho booking đã trả tiền; vé đã check-in bị chặn.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-106"></a>

## Flow 106 — Request Refund - chặn vé đã check-in

- Bắt đầu ở Main của CORE_DIR sau [Flow 105](../08_Gate/02_Check_In_Ticket.md#flow-105).

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
6. 1
7. 4
8. Cannot attend anymore
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

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Bước 8 — Nhập `Cannot attend anymore` rồi Enter

Console trả về

```text
Used or cancelled ticket cannot be refunded

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

- Fan gửi lý do hoàn tiền cho booking đã trả tiền; vé đã check-in bị chặn.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
