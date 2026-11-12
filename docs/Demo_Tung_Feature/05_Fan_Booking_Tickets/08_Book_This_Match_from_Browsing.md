# Book This Match from Browsing

- Dùng để chuyển từ kết quả browsing của Fan sang chọn ghế; có thể huỷ tại bước chọn ghế.
- Luồng xử lý trong code `MainController.handleMatchListActions → BookingController.bookMatch → BookingView`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-077"></a>

## Flow 077 — Book This Match từ browsing - huỷ chọn ghế

- Bắt đầu ở Main của CORE_DIR sau [Flow 076](../05_Fan_Booking_Tickets/07_Ticket_Details.md#flow-076).

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
4. 4
5. 1
6. Flow Red
7. 1
8. 4
9. 2
10. 3
11. 0
12. 4
13. 3
14. 4
15. 0
16. 0
17. 0
18. 0
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

### Bước 4 — Nhập `4` rồi Enter

Console trả về

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Bước 6 — Nhập `Flow Red` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book match (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 8 — Nhập `4` rồi Enter

Console trả về

```text
=== MATCH DETAILS #4 ===
Match:      Flow Red vs Flow Blue
Stadium:    Flow Demo Stadium [ID: 3]
Kick-off:   15/11/2030 19:30
Sales Gate: [ OPEN ]

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Bước 9 — Nhập `2` rồi Enter

Console trả về

```text
=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Bước 10 — Nhập `3` rồi Enter

Console trả về

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Booking cancelled.

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Bước 12 — Nhập `4` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book match (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 13 — Nhập `3` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 14 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 15 — Nhập `0` rồi Enter

Console trả về

```text
Booking cancelled.

--- Match #4 ---
1. View match details
2. View seat availability / Seat map
3. Book this match
4. Choose another match
0. Back to match browsing
Choose action:
```

### Bước 16 — Nhập `0` rồi Enter

Console trả về

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 17 — Nhập `0` rồi Enter

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

### Bước 18 — Nhập `0` rồi Enter

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

- Chuyển từ kết quả browsing của Fan sang chọn ghế; có thể huỷ tại bước chọn ghế.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
