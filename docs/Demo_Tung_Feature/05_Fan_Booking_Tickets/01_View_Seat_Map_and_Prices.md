# View Seat Map and Prices

- Dùng để xem ghế và giá trước khi chọn mua.
- Luồng xử lý trong code `MainController → BookingController → BrowseController.getSeatMap → BrowseService → SeatMapView`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-068"></a>

## Flow 068 — View Seat Map and Prices

- Bắt đầu ở Main của CORE_DIR sau [Flow 067](../04_Admin_Matches_Prices/11_Open_Ticket_Sales.md#flow-067).

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
6. 1
7. 4
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
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
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
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

- Xem ghế và giá trước khi chọn mua.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
