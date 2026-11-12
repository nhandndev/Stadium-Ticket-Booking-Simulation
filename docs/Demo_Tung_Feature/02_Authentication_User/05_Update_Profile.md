# Update Profile

- Dùng để đổi tên hiển thị và điện thoại của chính tài khoản, rồi lưu lại CSV tương ứng.
- Luồng xử lý trong code `MainController → MainView.readProfileUpdateRequest → AuthController.updateCurrentUserProfile → AuthService → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-013"></a>

## Flow 013 — Update Profile của Fan

- Bắt đầu ở Main của CORE_DIR sau [Flow 012](04_View_Profile.md#flow-012).

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
4. 3
5. Flow Fan Updated
6. 0901234568
7. 0
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
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Bước 4 — Nhập `3` rồi Enter

Console trả về

```text
New full name (Enter = Cancel):
```

### Bước 5 — Nhập `Flow Fan Updated` rồi Enter

Console trả về

```text
New phone:
```

### Bước 6 — Nhập `0901234568` rồi Enter

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

### Bước 7 — Nhập `0` rồi Enter

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

- Đổi tên hiển thị và điện thoại của chính tài khoản, rồi lưu lại CSV tương ứng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-128"></a>

## Flow 128 — Update Profile của Seller

- Bắt đầu ở Main của CORE_DIR sau [Flow 127](04_View_Profile.md#flow-127).

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
4. 3
5. Seller Flow Profile
6. 0905555555
7. 0
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

### Bước 4 — Nhập `3` rồi Enter

Console trả về

```text
New full name (Enter = Cancel):
```

### Bước 5 — Nhập `Seller Flow Profile` rồi Enter

Console trả về

```text
New phone:
```

### Bước 6 — Nhập `0905555555` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Seller Flow Profile
Email:  seller1@stadium.com
Phone:  0905555555
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Bước 7 — Nhập `0` rồi Enter

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

- Đổi tên hiển thị và điện thoại của chính tài khoản, rồi lưu lại CSV tương ứng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-130"></a>

## Flow 130 — Update Profile của Gate Staff

- Bắt đầu ở Main của CORE_DIR sau [Flow 129](04_View_Profile.md#flow-129).

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
2. gate1@stadium.com
3. gate123
4. 3
5. Gate Staff Flow Profile
6. 0905555555
7. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `gate1@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `gate123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `3` rồi Enter

Console trả về

```text
New full name (Enter = Cancel):
```

### Bước 5 — Nhập `Gate Staff Flow Profile` rồi Enter

Console trả về

```text
New phone:
```

### Bước 6 — Nhập `0905555555` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Gate Staff Flow Profile
Email:  gate1@stadium.com
Phone:  0905555555
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Bước 7 — Nhập `0` rồi Enter

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

- Đổi tên hiển thị và điện thoại của chính tài khoản, rồi lưu lại CSV tương ứng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-132"></a>

## Flow 132 — Update Profile của Admin

- Bắt đầu ở Main của CORE_DIR sau [Flow 131](04_View_Profile.md#flow-131).

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
4. 3
5. Admin Flow Profile
6. 0905555555
7. 0
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

### Bước 4 — Nhập `3` rồi Enter

Console trả về

```text
New full name (Enter = Cancel):
```

### Bước 5 — Nhập `Admin Flow Profile` rồi Enter

Console trả về

```text
New phone:
```

### Bước 6 — Nhập `0905555555` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Admin Flow Profile
Email:  admin@stadium.com
Phone:  0905555555
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 7 — Nhập `0` rồi Enter

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

- Đổi tên hiển thị và điện thoại của chính tài khoản, rồi lưu lại CSV tương ứng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
