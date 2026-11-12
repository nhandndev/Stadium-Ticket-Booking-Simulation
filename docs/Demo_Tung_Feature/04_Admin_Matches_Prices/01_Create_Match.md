# Create Match

- Dùng để tạo trận với sân, hai đội và thời điểm bắt đầu.
- Luồng xử lý trong code `MainController → AdminController → MatchController.createMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-051"></a>

## Flow 051 — Create Match

- Bắt đầu ở Main của CORE_DIR sau [Flow 050](../03_Admin_Venues/05_Delete_Stadium.md#flow-050).

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
7. 1
8. 3
9. Flow Red
10. Flow Blue
11. 2030-11-15T19:00
12. 0
13. 0
14. 0
15. 0
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Bước 8 — Nhập `3` rồi Enter

Console trả về

```text
Home team:
```

### Bước 9 — Nhập `Flow Red` rồi Enter

Console trả về

```text
Away team:
```

### Bước 10 — Nhập `Flow Blue` rồi Enter

Console trả về

```text
Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00):
```

### Bước 11 — Nhập `2030-11-15T19:00` rồi Enter

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

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 13 — Nhập `0` rồi Enter

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

### Bước 14 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 15 — Nhập `0` rồi Enter

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

- Tạo trận với sân, hai đội và thời điểm bắt đầu.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-055"></a>

## Flow 055 — Delete Match - tạo trận phụ

- Bắt đầu ở Main của CORE_DIR sau [Flow 054](04_Update_Match.md#flow-054).

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
7. 1
8. 3
9. Delete Red
10. Delete Blue
11. 2030-12-01T19:00
12. 0
13. 0
14. 0
15. 0
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Bước 8 — Nhập `3` rồi Enter

Console trả về

```text
Home team:
```

### Bước 9 — Nhập `Delete Red` rồi Enter

Console trả về

```text
Away team:
```

### Bước 10 — Nhập `Delete Blue` rồi Enter

Console trả về

```text
Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00):
```

### Bước 11 — Nhập `2030-12-01T19:00` rồi Enter

Console trả về

```text
5 | Delete Red vs Delete Blue | Stadium #3 | 2030-12-01T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 13 — Nhập `0` rồi Enter

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

### Bước 14 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 15 — Nhập `0` rồi Enter

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

- Tạo trận với sân, hai đội và thời điểm bắt đầu.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
