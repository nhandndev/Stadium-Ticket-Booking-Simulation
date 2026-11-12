# Create Ticket Price

- Dùng để đặt giá cho cặp Match/Section.
- Luồng xử lý trong code `MainController → AdminController → MatchController.createTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-057"></a>

## Flow 057 — Create Ticket Price

- Bắt đầu ở Main của CORE_DIR sau [Flow 056](05_Delete_Match.md#flow-056).

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
6. 2
7. 1
8. 4
9. 6
10. 250000
11. 0
12. 0
13. 0
14. 0
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

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Bước 8 — Nhập `4` rồi Enter

Console trả về

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 9 — Nhập `6` rồi Enter

Console trả về

```text
Price in VND (0 = Cancel):
```

### Bước 10 — Nhập `250000` rồi Enter

Console trả về

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 12 — Nhập `0` rồi Enter

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

### Bước 13 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 14 — Nhập `0` rồi Enter

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

- Đặt giá cho cặp Match/Section.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-062"></a>

## Flow 062 — Create Ticket Price - khôi phục giá để mở bán

- Bắt đầu ở Main của CORE_DIR sau [Flow 061](10_Delete_Ticket_Price.md#flow-061).

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
6. 2
7. 1
8. 4
9. 6
10. 250000
11. 0
12. 0
13. 0
14. 0
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

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Bước 8 — Nhập `4` rồi Enter

Console trả về

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 9 — Nhập `6` rồi Enter

Console trả về

```text
Price in VND (0 = Cancel):
```

### Bước 10 — Nhập `250000` rồi Enter

Console trả về

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Bước 12 — Nhập `0` rồi Enter

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

### Bước 13 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 14 — Nhập `0` rồi Enter

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

- Đặt giá cho cặp Match/Section.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
