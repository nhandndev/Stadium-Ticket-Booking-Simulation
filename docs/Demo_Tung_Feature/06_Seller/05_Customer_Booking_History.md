# Customer Booking History

- Dùng để tra booking theo khách đã chọn để tiếp tục thu tiền hoặc xuất vé.
- Luồng xử lý trong code `MainController → BookingController.getBookingsForFan → BookingService.getMyBookings → BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-086"></a>

## Flow 086 — Customer Booking History

- Bắt đầu ở Main của CORE_DIR sau [Flow 085](04_Create_Booking_for_Customer.md#flow-085).

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
5. 2
6. 3
7. flow.counter
8. 7
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
4. Browse matches
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Bước 6 — Nhập `3` rồi Enter

Console trả về

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Bước 7 — Nhập `flow.counter` rồi Enter

Console trả về

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Bước 8 — Nhập `7` rồi Enter

Console trả về

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | PENDING

=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

- Tra booking theo khách đã chọn để tiếp tục thu tiền hoặc xuất vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
