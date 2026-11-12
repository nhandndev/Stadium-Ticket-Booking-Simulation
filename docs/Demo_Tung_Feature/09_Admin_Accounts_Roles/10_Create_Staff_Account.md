# Create Staff Account

- Dùng để tạo Staff và chọn role.
- Luồng xử lý trong code `MainController → AdminController → AccountAdminController.createStaffAccount → AccountAdminService → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-116"></a>

## Flow 116 — Create Staff Account

- Bắt đầu ở Main của CORE_DIR sau [Flow 115](../09_Admin_Accounts_Roles/09_Delete_Fan.md#flow-115).

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
5. 3
6. 2
7. 1
8. Flow Staff
9. flow.staff@example.com
10. StaffPass123
11. 0904444444
12. 1
13. 0
14. 0
15. 0
16. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Bước 7 — Nhập `1` rồi Enter

Console trả về

```text
Full name (Enter = Cancel):
```

### Bước 8 — Nhập `Flow Staff` rồi Enter

Console trả về

```text
Email:
```

### Bước 9 — Nhập `flow.staff@example.com` rồi Enter

Console trả về

```text
Password (at least 8 characters):
```

### Bước 10 — Nhập `StaffPass123` rồi Enter

Console trả về

```text
Phone:
```

### Bước 11 — Nhập `0904444444` rồi Enter

Console trả về

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Unassigned
Role (0 = Back):
```

### Bước 12 — Nhập `1` rồi Enter

Console trả về

```text
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Bước 13 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Bước 14 — Nhập `0` rồi Enter

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

### Bước 15 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 16 — Nhập `0` rồi Enter

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

- Tạo Staff và chọn role.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
