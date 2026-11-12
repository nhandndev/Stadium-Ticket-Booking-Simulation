# View Match Details

- Dùng để xem thông tin một trận.
- Luồng xử lý trong code `MainController → AdminController → MatchController.getMatchDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-053"></a>

## Flow 053 — View Match Details

- Bắt đầu ở Main của CORE_DIR sau [Flow 052](../04_Admin_Matches_Prices/02_View_Match_List.md#flow-052).

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
2. admin@stadium.com
3. admin123
4. 2
5. 2
6. 1
7. 3
8. 4
9. 0
10. 0
11. 0
12. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `admin@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `admin123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Bước 7 — Nhập `3` rồi Enter

Console trả về

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED
Match ID (0 = Back):
```

### Bước 8 — Nhập `4` rồi Enter

Console trả về

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
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

- Xem thông tin một trận.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
