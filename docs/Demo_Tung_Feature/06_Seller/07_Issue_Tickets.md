# Issue Tickets

- Dùng để phát hành vé cho booking CONFIRMED đã trả tiền. Gọi lại giữ nguyên mã và số lượng vé.
- Luồng xử lý trong code `MainController → BookingController.issueTickets → BookingService.issueTickets → TicketRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-089"></a>

## Flow 089 — Issue Tickets for Paid Booking

- Bắt đầu ở Main của CORE_DIR sau [Flow 088](../06_Seller/06_Accept_Cash_Payment.md#flow-088).

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
1. 3
2. seller1@stadium.com
3. seller123
4. 2
5. 5
6. 7
7. 0
8. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `seller1@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `seller123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Seller Nguyen
Email:  seller1@stadium.com
Phone:  0900000002
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `5` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `7` rồi Enter

Console trả về

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status CONFIRMED
Keep Booking ID 7 for payment or ticket issuance.
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 7 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 8 — Nhập `0` rồi Enter

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

- Phát hành vé cho booking CONFIRMED đã trả tiền. Gọi lại giữ nguyên mã và số lượng vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-090"></a>

## Flow 090 — Issue Tickets - gọi lại không tạo trùng

- Bắt đầu ở Main của CORE_DIR sau [Flow 089](../06_Seller/07_Issue_Tickets.md#flow-089).

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
1. 3
2. seller1@stadium.com
3. seller123
4. 2
5. 5
6. 7
7. 0
8. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `seller1@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `seller123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Seller Nguyen
Email:  seller1@stadium.com
Phone:  0900000002
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `5` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `7` rồi Enter

Console trả về

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status CONFIRMED
Keep Booking ID 7 for payment or ticket issuance.
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 7 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 8 — Nhập `0` rồi Enter

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

- Phát hành vé cho booking CONFIRMED đã trả tiền. Gọi lại giữ nguyên mã và số lượng vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-147"></a>

## Flow 147 — Issue paid walk-in tickets as a separate Seller action

- Bắt đầu ở Main của COUNTER_DEMO_DIR. Theo thứ tự được ghi trong file chuẩn bị.

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
1. 3
2. seller1@stadium.com
3. seller123
4. 2
5. 5
6. 8
7. 5
8. 9
9. 0
10. 0
11. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `seller1@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `seller123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Seller Nguyen
Email:  seller1@stadium.com
Phone:  0900000002
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `5` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `8` rồi Enter

Console trả về

```text
Booking #8 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A1, Stand A VIP A2, Stand A VIP A3, Stand A VIP A4] | Total 400000 VND | Status CONFIRMED
Keep Booking ID 8 for payment or ticket issuance.
Ticket #5 | Code T-8-1 | Vietnam vs China | Stand A VIP A1 | Status ISSUED
Ticket #6 | Code T-8-2 | Vietnam vs China | Stand A VIP A2 | Status ISSUED
Ticket #7 | Code T-8-3 | Vietnam vs China | Stand A VIP A3 | Status ISSUED
Ticket #8 | Code T-8-4 | Vietnam vs China | Stand A VIP A4 | Status ISSUED

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 7 — Nhập `5` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 8 — Nhập `9` rồi Enter

Console trả về

```text
Booking #9 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A5] | Total 100000 VND | Status CONFIRMED
Keep Booking ID 9 for payment or ticket issuance.
Ticket #9 | Code T-9-5 | Vietnam vs China | Stand A VIP A5 | Status ISSUED

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
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

- Phát hành vé cho booking CONFIRMED đã trả tiền. Gọi lại giữ nguyên mã và số lượng vé.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.
