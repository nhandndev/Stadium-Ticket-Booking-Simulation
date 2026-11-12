# Search and List Customers

- Dùng để tìm khách theo tên/email hoặc để trống để liệt kê khách.
- Luồng xử lý trong code `MainController → BookingController.runSellerMenu → FanController.searchFans → FanService → FanRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-081"></a>

## Flow 081 — Search Customer

- Bắt đầu ở Main của CORE_DIR sau [Flow 080](../02_Authentication_User/03_Login_As_Staff.md#flow-080).

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
6. flow.fan
7. 0
8. 0
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Bước 6 — Nhập `flow.fan` rồi Enter

Console trả về

```text
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

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

### Bước 7 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 8 — Nhập `0` rồi Enter

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

- Tìm khách theo tên/email hoặc để trống để liệt kê khách.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-082"></a>

## Flow 082 — List Customers - Enter tại keyword

- Bắt đầu ở Main của CORE_DIR sau [Flow 081](../06_Seller/01_Search_and_List_Customers.md#flow-081).

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
6. Enter
7. 0
8. 0
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

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Bước 6 — Nhấn Enter để trống

Console trả về

```text
Fan #1 | Nguyen Van An | an.nguyen@gmail.com | INACTIVE
Fan #2 | Tran Thi Binh | binh.tran@gmail.com | ACTIVE
Fan #3 | Le Hoang Cuong | cuong.le@gmail.com | ACTIVE
Fan #4 | Pham Minh Dung | dung.pham@gmail.com | ACTIVE
Fan #5 | Do Thi Em | em.do@gmail.com | INACTIVE
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

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

### Bước 7 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
0. Logout
Choose option:
```

### Bước 8 — Nhập `0` rồi Enter

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

- Tìm khách theo tên/email hoặc để trống để liệt kê khách.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
