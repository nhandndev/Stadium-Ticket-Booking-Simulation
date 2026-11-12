# Login As Fan

- Dùng để đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Luồng xử lý trong code `MainController.handleFanLogin → AuthController.login → AuthService.login → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-011"></a>

## Flow 011 — Login As Fan

- Bắt đầu ở Main của CORE_DIR sau [Flow 010](../02_Authentication_User/01_Register_Fan_Account.md#flow-010).

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
4. 0
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

### Bước 4 — Nhập `0` rồi Enter

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

- Đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-015"></a>

## Flow 015 — Login - sai mật khẩu

- Bắt đầu ở Main của CORE_DIR sau [Flow 014](../02_Authentication_User/06_Logout.md#flow-014).

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
3. wrong
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

### Bước 3 — Nhập `wrong` rồi Enter

Console trả về

```text
Invalid email or password

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-016"></a>

## Flow 016 — Login - tài khoản INACTIVE

- Bắt đầu ở Main của CORE_DIR sau [Flow 015](../02_Authentication_User/02_Login_As_Fan.md#flow-015).

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
2. em.do@gmail.com
3. 12345678
```

### Bước 1 — Nhập `2` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `em.do@gmail.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `12345678` rồi Enter

Console trả về

```text
Account is inactive

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-017"></a>

## Flow 017 — Login Fan bằng tài khoản Staff

- Bắt đầu ở Main của CORE_DIR sau [Flow 016](../02_Authentication_User/02_Login_As_Fan.md#flow-016).

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
2. admin@stadium.com
3. admin123
```

### Bước 1 — Nhập `2` rồi Enter

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
Use Login As Staff for this account.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-019"></a>

## Flow 019 — Huỷ form Login

- Bắt đầu ở Main của CORE_DIR sau [Flow 018](../02_Authentication_User/03_Login_As_Staff.md#flow-018).

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
2. Enter
```

### Bước 1 — Nhập `2` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhấn Enter để trống

Console trả về

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Đăng nhập Fan, báo sai mật khẩu, chặn tài khoản INACTIVE và hướng tài khoản Staff về đúng cổng đăng nhập.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
