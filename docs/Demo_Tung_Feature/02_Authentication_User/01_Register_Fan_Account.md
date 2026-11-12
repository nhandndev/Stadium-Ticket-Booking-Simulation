# Register Fan Account

- Dùng để tạo tài khoản Fan và kiểm tra email đã tồn tại; tài khoản mới có role FAN và trạng thái ACTIVE.
- Luồng xử lý trong code `MainController.handleFanRegistration → FanController.register → FanService.register → FanRepository / StaffRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-009"></a>

## Flow 009 — Register Fan Account

- Bắt đầu ở Main của CORE_DIR sau [Flow 008](../01_Guest/01_Search_Matches.md#flow-008).

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
1. 4
2. Flow Demo Fan
3. flow.fan@example.com
4. FlowPass123
5. 0901234567
```

### Bước 1 — Nhập `4` rồi Enter

Console trả về

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Bước 2 — Nhập `Flow Demo Fan` rồi Enter

Console trả về

```text
Email:
```

### Bước 3 — Nhập `flow.fan@example.com` rồi Enter

Console trả về

```text
Password (at least 8 characters):
```

### Bước 4 — Nhập `FlowPass123` rồi Enter

Console trả về

```text
Phone:
```

### Bước 5 — Nhập `0901234567` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE
Account created. Select Login As Fan to continue.

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Tạo tài khoản Fan và kiểm tra email đã tồn tại; tài khoản mới có role FAN và trạng thái ACTIVE.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-010"></a>

## Flow 010 — Register - chặn email trùng

- Bắt đầu ở Main của CORE_DIR sau [Flow 009](01_Register_Fan_Account.md#flow-009).

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
1. 4
2. Duplicate Fan
3. flow.fan@example.com
4. FlowPass123
5. 0901234567
```

### Bước 1 — Nhập `4` rồi Enter

Console trả về

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Bước 2 — Nhập `Duplicate Fan` rồi Enter

Console trả về

```text
Email:
```

### Bước 3 — Nhập `flow.fan@example.com` rồi Enter

Console trả về

```text
Password (at least 8 characters):
```

### Bước 4 — Nhập `FlowPass123` rồi Enter

Console trả về

```text
Phone:
```

### Bước 5 — Nhập `0901234567` rồi Enter

Console trả về

```text
Email already exists

STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Tạo tài khoản Fan và kiểm tra email đã tồn tại; tài khoản mới có role FAN và trạng thái ACTIVE.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
