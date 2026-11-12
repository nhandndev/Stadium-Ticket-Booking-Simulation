# Change Staff Role

- Dùng để đổi role đang có sang role khác.
- Luồng xử lý trong code `MainController → AdminController → AccountAdminController.changeRole → AccountAdminService → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-124"></a>

## Flow 124 — Change Staff Role

- Bắt đầu ở Main của CORE_DIR sau [Flow 123](17_Activate_Staff.md#flow-123).

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
7. 9
8. 2
9. 6
10. 2
11. 0
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

### Bước 7 — Nhập `9` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Bước 8 — Nhập `2` rồi Enter

Console trả về

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Unassigned Staff | sim@stadium.com | Role: UNASSIGNED | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Bước 9 — Nhập `6` rồi Enter

Console trả về

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Unassigned
Role (0 = Back):
```

### Bước 10 — Nhập `2` rồi Enter

Console trả về

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: GATE_STAFF | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

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

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
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

- Đổi role đang có sang role khác.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
