# View Audit Log

- Dùng để xem nhật ký đã được ghi, gồm các quyết định review refund trong các flow trước.
- Luồng xử lý trong code `MainController → AdminController.getAuditLogs → AdminService → AuditLogRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-137"></a>

## Flow 137 — View Audit Log

- Bắt đầu ở Main của CORE_DIR sau [Flow 136](../10_Admin_Monitoring/01_View_System_Summary.md#flow-136).

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
6. 2
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

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== AUDIT LOGS ===
5 | Actor #1 | REVIEW_REFUND | Target: REFUND #3 | Result: REJECTED | 2026-11-12T23:47:39.953627
4 | Actor #1 | REVIEW_REFUND | Target: REFUND #2 | Result: APPROVED | 2026-11-12T23:47:39.545422
3 | Actor #1 | ASSIGN_ROLE | Target: STAFF #3 | Result: SUCCESS | 2026-10-26T00:10
2 | Actor #1 | OPEN_TICKET_SALES | Target: MATCH #1 | Result: SUCCESS | 2026-10-26T00:05
1 | Actor #1 | INITIALIZE_DATASET | Target: SYSTEM #0 | Result: SUCCESS | 2026-10-26T00:00

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

- Xem nhật ký đã được ghi, gồm các quyết định review refund trong các flow trước.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
