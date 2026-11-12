# Create Section

- Dùng để tạo Section thuộc một Stadium đã chọn.
- Luồng xử lý trong code `MainController → AdminController.runAdminMenu → StadiumController.createSection → StadiumService → Stadium/Section/SeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-025"></a>

## Flow 025 — Create Section

- Bắt đầu ở Main của CORE_DIR sau [Flow 024](04_Update_Stadium.md#flow-024).

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
5. 1
6. 2
7. 1
8. 3
9. Flow Stand
10. 0
11. 0
12. 0
13. 0
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
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
Section name (Enter = Cancel):
```

### Bước 9 — Nhập `Flow Stand` rồi Enter

Console trả về

```text
6 | Flow Stand | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

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

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 13 — Nhập `0` rồi Enter

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

- Tạo Section thuộc một Stadium đã chọn.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-046"></a>

## Flow 046 — Delete Section - tạo khán đài phụ

- Bắt đầu ở Main của CORE_DIR sau [Flow 045](17_Delete_Seat.md#flow-045).

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
5. 1
6. 2
7. 1
8. 3
9. Delete Flow Section
10. 0
11. 0
12. 0
13. 0
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
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
Section name (Enter = Cancel):
```

### Bước 9 — Nhập `Delete Flow Section` rồi Enter

Console trả về

```text
7 | Delete Flow Section | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

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

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 13 — Nhập `0` rồi Enter

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

- Tạo Section thuộc một Stadium đã chọn.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
