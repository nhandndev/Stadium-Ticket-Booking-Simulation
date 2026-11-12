# View System Summary

- Dùng để xem tổng Fan, Staff, sân, trận, booking và vé trong bản demo.
- Luồng xử lý trong code `MainController → AdminController.getSystemSummary → AdminService → domain repositories`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-136"></a>

## Flow 136 — View System Summary

- Bắt đầu ở Main của CORE_DIR sau [Flow 132](../02_Authentication_User/05_Update_Profile.md#flow-132).

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
5. 5
6. 1
7. 0
8. 0
9. 0
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

### Bước 5 — Nhập `5` rồi Enter

Console trả về

```text
=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
=== SYSTEM SUMMARY ===
Total fans: 7
Total staff: 6
Total stadiums: 3
Total matches: 4
Total bookings: 7
Total tickets: 7

=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Bước 7 — Nhập `0` rồi Enter

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

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Bước 9 — Nhập `0` rồi Enter

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

- Xem tổng Fan, Staff, sân, trận, booking và vé trong bản demo.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
