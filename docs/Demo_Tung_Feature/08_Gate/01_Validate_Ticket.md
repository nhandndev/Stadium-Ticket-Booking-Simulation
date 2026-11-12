# Validate Ticket

- Dùng để kiểm tra mã vé và đúng trận trước khi cho phép check-in; trận không khớp bị từ chối.
- Luồng xử lý trong code `MainController → TicketController.validateTicket → TicketService.validateTicket → TicketRepository / BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-100"></a>

## Flow 100 — Validate Ticket

- Bắt đầu ở Main của CORE_DIR sau [Flow 099](../02_Authentication_User/03_Login_As_Staff.md#flow-099).

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
5. 1
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

### Bước 5 — Nhập `1` rồi Enter

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

- Kiểm tra mã vé và đúng trận trước khi cho phép check-in; trận không khớp bị từ chối.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-104"></a>

## Flow 104 — Validate Ticket - sai trận

- Bắt đầu ở Main của CORE_DIR sau [Flow 103](02_Check_In_Ticket.md#flow-103).

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
5. 1
6. T-4-22
7. 1
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
Ticket code (Enter or 0 = Back):
```

### Bước 6 — Nhập `T-4-22` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 7 — Nhập `1` rồi Enter

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

- Kiểm tra mã vé và đúng trận trước khi cho phép check-in; trận không khớp bị từ chối.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
