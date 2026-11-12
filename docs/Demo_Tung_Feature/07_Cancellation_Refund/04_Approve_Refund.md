# Approve Refund

- Dùng để admin duyệt yêu cầu hợp lệ, huỷ vé/booking, trả ghế về AVAILABLE và ghi Audit Log. Payment mock SUCCESS vẫn là bản ghi lịch sử thu tiền.
- Luồng xử lý trong code `MainController → AdminController → RefundController.reviewRefund → RefundService → Ticket/Booking/MatchSeat/RefundRepository; AuditLogRepository.append`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-095"></a>

## Flow 095 — Approve Refund

- Bắt đầu ở Main của CORE_DIR sau [Flow 094](03_My_Refund_Requests.md#flow-094).

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
5. 4
6. 1
7. 2
8. y
9. Approved before kick-off
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #2 | Booking #5 | Fan #6 | Cannot attend | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Refund request ID (0 = Back):
```

### Bước 7 — Nhập `2` rồi Enter

Console trả về

```text
Approve refund? (y/n):
```

### Bước 8 — Nhập `y` rồi Enter

Console trả về

```text
Review note (required, 0 = Cancel):
```

### Bước 9 — Nhập `Approved before kick-off` rồi Enter

Console trả về

```text
Request #2 | Booking #5 | APPROVED
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
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

- Admin duyệt yêu cầu hợp lệ, huỷ vé/booking, trả ghế về AVAILABLE và ghi Audit Log. Payment mock SUCCESS vẫn là bản ghi lịch sử thu tiền.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
