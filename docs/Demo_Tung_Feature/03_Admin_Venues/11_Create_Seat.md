# Create Seat

- Dùng để tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Luồng xử lý trong code `MainController → AdminController.runAdminMenu → StadiumController.createSeat → StadiumService → Stadium/Section/SeatRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-029"></a>

## Flow 029 — Create Seat A1

- Bắt đầu ở Main của CORE_DIR sau [Flow 028](09_Update_Section.md#flow-028).

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
7. 1
8. 6
9. A
10. 1
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `1` rồi Enter

Console trả về

```text
21 | Row A, Seat 1 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-030"></a>

## Flow 030 — Create Seat A2

- Bắt đầu ở Main của CORE_DIR sau [Flow 029](11_Create_Seat.md#flow-029).

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
7. 1
8. 6
9. A
10. 2
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `2` rồi Enter

Console trả về

```text
22 | Row A, Seat 2 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-031"></a>

## Flow 031 — Create Seat A3

- Bắt đầu ở Main của CORE_DIR sau [Flow 030](11_Create_Seat.md#flow-030).

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
7. 1
8. 6
9. A
10. 3
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `3` rồi Enter

Console trả về

```text
23 | Row A, Seat 3 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-032"></a>

## Flow 032 — Create Seat A4

- Bắt đầu ở Main của CORE_DIR sau [Flow 031](11_Create_Seat.md#flow-031).

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
7. 1
8. 6
9. A
10. 4
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `4` rồi Enter

Console trả về

```text
24 | Row A, Seat 4 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-033"></a>

## Flow 033 — Create Seat A5

- Bắt đầu ở Main của CORE_DIR sau [Flow 032](11_Create_Seat.md#flow-032).

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
7. 1
8. 6
9. A
10. 5
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `5` rồi Enter

Console trả về

```text
25 | Row A, Seat 5 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-034"></a>

## Flow 034 — Create Seat A6

- Bắt đầu ở Main của CORE_DIR sau [Flow 033](11_Create_Seat.md#flow-033).

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
7. 1
8. 6
9. A
10. 6
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `6` rồi Enter

Console trả về

```text
26 | Row A, Seat 6 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-035"></a>

## Flow 035 — Create Seat A7

- Bắt đầu ở Main của CORE_DIR sau [Flow 034](11_Create_Seat.md#flow-034).

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
7. 1
8. 6
9. A
10. 7
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `7` rồi Enter

Console trả về

```text
27 | Row A, Seat 7 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-036"></a>

## Flow 036 — Create Seat A8

- Bắt đầu ở Main của CORE_DIR sau [Flow 035](11_Create_Seat.md#flow-035).

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
7. 1
8. 6
9. A
10. 8
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `A` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `8` rồi Enter

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-043"></a>

## Flow 043 — Delete Seat - tạo ghế phụ để xoá

- Bắt đầu ở Main của CORE_DIR sau [Flow 042](15_Activate_Seat.md#flow-042).

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
7. 1
8. 6
9. X
10. 1
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

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Bước 8 — Nhập `6` rồi Enter

Console trả về

```text
Row label (Enter = Cancel):
```

### Bước 9 — Nhập `X` rồi Enter

Console trả về

```text
Seat number:
```

### Bước 10 — Nhập `1` rồi Enter

Console trả về

```text
29 | Row X, Seat 1 | Section #6 | Active: true

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
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

- Tạo ghế theo Section, nhãn hàng và số ghế. Các lượt A1–A8 là dữ liệu chuẩn bị cho bán vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
