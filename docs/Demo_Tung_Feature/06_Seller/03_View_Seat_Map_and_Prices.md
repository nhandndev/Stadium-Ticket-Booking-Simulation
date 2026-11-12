# View Seat Map and Prices

- Dùng để seller xem ghế và giá trước khi tư vấn cho khách.
- Luồng xử lý trong code `MainController → BookingController → BrowseController.getSeatMap → BrowseService → SeatMapView`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-084"></a>

## Flow 084 — View Seat Map and Prices tại quầy

- Bắt đầu ở Main của CORE_DIR sau [Flow 083](02_Create_Customer_Fan_Account.md#flow-083).

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
6. 1
7. 4
8. 0
9. 0
10. 0
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

### Bước 6 — Nhập `1` rồi Enter

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
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | BOOKED
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE

=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
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

- Seller xem ghế và giá trước khi tư vấn cho khách.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
