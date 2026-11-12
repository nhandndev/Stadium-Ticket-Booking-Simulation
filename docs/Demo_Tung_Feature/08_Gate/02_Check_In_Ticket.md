# Check In Ticket

- Dùng để xác nhận sử dụng vé một lần, ghi thời gian check-in, xử lý huỷ xác nhận, vé đã dùng và code không có.
- Luồng xử lý trong code `MainController → TicketController.checkInTicket → TicketService.checkInTicket → TicketRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-101"></a>

## Flow 101 — Check-in Ticket - huỷ xác nhận

- Bắt đầu ở Main của CORE_DIR sau [Flow 100](01_Validate_Ticket.md#flow-100).

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
4. 2
5. 2
6. T-4-21
7. 4
8. n
9. 0
10. 0
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

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
Ticket code (Enter or 0 = Back):
```

### Bước 6 — Nhập `T-4-21` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Bước 8 — Nhập `n` rồi Enter

Console trả về

```text
Check-in cancelled.

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Bước 10 — Nhập `0` rồi Enter

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

- Xác nhận sử dụng vé một lần, ghi thời gian check-in, xử lý huỷ xác nhận, vé đã dùng và code không có.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-102"></a>

## Flow 102 — Check-in Ticket

- Bắt đầu ở Main của CORE_DIR sau [Flow 101](02_Check_In_Ticket.md#flow-101).

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
4. 2
5. 2
6. T-4-21
7. 4
8. y
9. 0
10. 0
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

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
Ticket code (Enter or 0 = Back):
```

### Bước 6 — Nhập `T-4-21` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Bước 8 — Nhập `y` rồi Enter

Console trả về

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status CHECKED_IN | Checked in at 2026-11-12T19:45:31.044271

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Bước 10 — Nhập `0` rồi Enter

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

- Xác nhận sử dụng vé một lần, ghi thời gian check-in, xử lý huỷ xác nhận, vé đã dùng và code không có.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-103"></a>

## Flow 103 — Check-in Ticket - chặn dùng vé lại

- Bắt đầu ở Main của CORE_DIR sau [Flow 102](02_Check_In_Ticket.md#flow-102).

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
4. 2
5. 2
6. T-4-21
7. 4
8. 0
9. 0
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

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
Ticket code (Enter or 0 = Back):
```

### Bước 6 — Nhập `T-4-21` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Ticket is not issued for this match or was already used

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
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

- Xác nhận sử dụng vé một lần, ghi thời gian check-in, xử lý huỷ xác nhận, vé đã dùng và code không có.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-105"></a>

## Flow 105 — Check-in Ticket - code không tồn tại

- Bắt đầu ở Main của CORE_DIR sau [Flow 104](01_Validate_Ticket.md#flow-104).

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
4. 2
5. 2
6. UNKNOWN_TICKET_FLOW
7. 4
8. 0
9. 0
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

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
Ticket code (Enter or 0 = Back):
```

### Bước 6 — Nhập `UNKNOWN_TICKET_FLOW` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Ticket not found

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
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

- Xác nhận sử dụng vé một lần, ghi thời gian check-in, xử lý huỷ xác nhận, vé đã dùng và code không có.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
