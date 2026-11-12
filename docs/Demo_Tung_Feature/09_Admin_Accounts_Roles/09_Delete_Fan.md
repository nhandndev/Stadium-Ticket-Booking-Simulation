# Delete Fan

- Dùng để xoá Fan phụ đủ điều kiện; Service chặn tài khoản có lịch sử booking.
- Luồng xử lý trong code `MainController → AdminController → AccountAdminController.deleteFan → AccountAdminService → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-115"></a>

## Flow 115 — Delete Fan Account

- Bắt đầu ở Main của CORE_DIR sau [Flow 114](08_Activate_Fan.md#flow-114).

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
6. 1
7. 9
8. 8
9. y
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Bước 7 — Nhập `9` rồi Enter

Console trả về

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: INACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Bước 8 — Nhập `8` rồi Enter

Console trả về

```text
Delete fan #8? (y/n, Enter = No):
```

### Bước 9 — Nhập `y` rồi Enter

Console trả về

```text
Operation completed.

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
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

- Xoá Fan phụ đủ điều kiện; Service chặn tài khoản có lịch sử booking.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
