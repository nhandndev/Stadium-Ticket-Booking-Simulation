# Create Customer Fan Account

- Dùng để seller tạo tài khoản Fan cho khách tại quầy.
- Luồng xử lý trong code `MainController → BookingController → SellerView.readRegisterFanRequest → FanController.register → FanService → FanRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-083"></a>

## Flow 083 — Create Fan Account tại quầy

- Bắt đầu ở Main của CORE_DIR sau [Flow 082](../06_Seller/01_Search_and_List_Customers.md#flow-082).

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
2. seller1@stadium.com
3. seller123
4. 2
5. 2
6. Flow Counter Customer
7. flow.counter@example.com
8. Counter123
9. 0902222222
10. 0
11. 0
```

### Bước 1 — Nhập `3` rồi Enter

Console trả về

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Bước 2 — Nhập `seller1@stadium.com` rồi Enter

Console trả về

```text
Password:
```

### Bước 3 — Nhập `seller123` rồi Enter

Console trả về

```text
=== USER PROFILE ===
Name:   Seller Nguyen
Email:  seller1@stadium.com
Phone:  0900000002
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `2` rồi Enter

Console trả về

```text
Full name (Enter = Cancel):
```

### Bước 6 — Nhập `Flow Counter Customer` rồi Enter

Console trả về

```text
Email:
```

### Bước 7 — Nhập `flow.counter@example.com` rồi Enter

Console trả về

```text
Password (at least 8 characters):
```

### Bước 8 — Nhập `Counter123` rồi Enter

Console trả về

```text
Phone:
```

### Bước 9 — Nhập `0902222222` rồi Enter

Console trả về

```text
Created Fan #7 | Flow Counter Customer | flow.counter@example.com

=== SELLER ===
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
Choose:
```

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 11 — Nhập `0` rồi Enter

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

- Seller tạo tài khoản Fan cho khách tại quầy.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
