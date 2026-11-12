# Create Customer Fan Account

- Dùng để seller tạo tài khoản Fan cho khách tại quầy.
- Luồng xử lý trong code `MainController → BookingController → SellerView.readRegisterFanRequest → FanController.register → FanService → FanRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-083"></a>

## Flow 083 — Create Fan Account tại quầy

- Bắt đầu ở Main của CORE_DIR sau [Flow 082](01_Search_and_List_Customers.md#flow-082).

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
5. 1
6. 2
7. Flow Counter Customer
8. flow.counter@example.com
9. Counter123
10. 0902222222
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
4. Browse matches
0. Logout
Choose option:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
Full name (Enter = Cancel):
```

### Bước 7 — Nhập `Flow Counter Customer` rồi Enter

Console trả về

```text
Email:
```

### Bước 8 — Nhập `flow.counter@example.com` rồi Enter

Console trả về

```text
Password (at least 8 characters):
```

### Bước 9 — Nhập `Counter123` rồi Enter

Console trả về

```text
Phone:
```

### Bước 10 — Nhập `0902222222` rồi Enter

Console trả về

```text
Created Fan #7 | Flow Counter Customer | flow.counter@example.com

=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Bước 12 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
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

- Seller tạo tài khoản Fan cho khách tại quầy.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
