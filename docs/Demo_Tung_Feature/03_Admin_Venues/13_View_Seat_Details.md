# View Seat Details

- Dùng để tra vị trí và trạng thái active của ghế.
- Luồng xử lý trong code `MainController → AdminController.runAdminMenu → StadiumController.getSeatDetails → StadiumService → Stadium/Section/SeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-038"></a>

## Flow 038 — View Seat Details

- Bắt đầu ở Main của CORE_DIR sau [Flow 037](../03_Admin_Venues/12_View_Seats_by_Section.md#flow-037).

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
6. 3
7. 3
8. 28
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

### Bước 6 — Nhập `3` rồi Enter

Console trả về

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Bước 7 — Nhập `3` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
9 | Row A, Seat 9 | Section #1 | Active: true
10 | Row A, Seat 10 | Section #1 | Active: true
2 | Stand B | Stadium #1
11 | Row B, Seat 1 | Section #2 | Active: true
12 | Row B, Seat 2 | Section #2 | Active: true
13 | Row B, Seat 3 | Section #2 | Active: true
14 | Row B, Seat 4 | Section #2 | Active: true
15 | Row B, Seat 5 | Section #2 | Active: true
16 | Row B, Seat 6 | Section #2 | Active: true
17 | Row B, Seat 7 | Section #2 | Active: true
18 | Row B, Seat 8 | Section #2 | Active: true
19 | Row B, Seat 9 | Section #2 | Active: true
20 | Row B, Seat 10 | Section #2 | Active: true
3 | Stand C | Stadium #1
List is empty.
4 | Stand D | Stadium #1
List is empty.
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Bước 8 — Nhập `28` rồi Enter

Console trả về

```text
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tra vị trí và trạng thái active của ghế.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
