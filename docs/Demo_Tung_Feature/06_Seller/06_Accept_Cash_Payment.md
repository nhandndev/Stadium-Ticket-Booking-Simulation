# Accept Cash Payment

- Dùng để ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Luồng xử lý trong code `MainController → BookingController.acceptOfflinePayment → BookingService.acceptOfflinePayment → PaymentRepository / MatchSeatRepository / BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-087"></a>

## Flow 087 — Accept Cash Payment - huỷ trước khi thu

- Bắt đầu ở Main của CORE_DIR sau [Flow 086](../06_Seller/05_Customer_Booking_History.md#flow-086).

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
5. 4
6. 7
7. Enter
8. 0
9. 0
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `7` rồi Enter

Console trả về

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Keep Booking ID 7 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:17:35.838320
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Bước 7 — Nhấn Enter để trống

Console trả về

```text
No payment recorded. Keep Booking ID 7 to pay before 2026-11-13T00:17:35.838320.

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

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

- Ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-088"></a>

## Flow 088 — Accept Cash Payment

- Bắt đầu ở Main của CORE_DIR sau [Flow 087](../06_Seller/06_Accept_Cash_Payment.md#flow-087).

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
5. 4
6. 7
7. RECEIVED
8. 0
9. 0
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `7` rồi Enter

Console trả về

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Keep Booking ID 7 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:17:35.838320
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Bước 7 — Nhập `RECEIVED` rồi Enter

Console trả về

```text
Payment #6 | Booking #7 | 500000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

- Ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-144"></a>

## Flow 144 — Accept offline payment for the walk-in booking

- Bắt đầu ở Main của COUNTER_DEMO_DIR. Theo thứ tự được ghi trong file chuẩn bị.

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
5. 4
6. 8
7. RECEIVED
8. 0
9. 0
10. 0
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `8` rồi Enter

Console trả về

```text
Booking #8 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A1, Stand A VIP A2, Stand A VIP A3, Stand A VIP A4] | Total 400000 VND | Status PENDING
Keep Booking ID 8 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:36.617484
Cash amount due: 400000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Bước 7 — Nhập `RECEIVED` rồi Enter

Console trả về

```text
Payment #6 | Booking #8 | 400000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-146"></a>

## Flow 146 — Accept offline payment within the persisted deadline after restart

- Bắt đầu ở Main của COUNTER_DEMO_DIR. Theo thứ tự được ghi trong file chuẩn bị.

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
5. 4
6. 9
7. RECEIVED
8. 0
9. 0
10. 0
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `9` rồi Enter

Console trả về

```text
Booking #9 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A5] | Total 100000 VND | Status PENDING
Keep Booking ID 9 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:37.643472
Cash amount due: 100000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Bước 7 — Nhập `RECEIVED` rồi Enter

Console trả về

```text
Payment #7 | Booking #9 | 100000 VND | Method OFFLINE_CASH | Status SUCCESS
Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.

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

### Bước 8 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 10 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-152"></a>

## Flow 152 — Seller cannot accept offline payment for an expired booking

- Bắt đầu ở Main của EXPIRY_DEMO_DIR. Theo thứ tự được ghi trong file chuẩn bị.

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
5. 4
6. 3
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

### Bước 5 — Nhập `4` rồi Enter

Console trả về

```text
Enter the single Booking ID from the counter receipt, for either customer type.
Booking ID: (0 = Back):
```

### Bước 6 — Nhập `3` rồi Enter

Console trả về

```text
Booking #3 | Fan #2 Tran Thi Binh | Vietnam vs Thailand | Seats [Stand A VIP A2] | Total 500000 VND | Status EXPIRED
Keep Booking ID 3 for payment or ticket issuance.
Booking has expired. Offline payment is no longer allowed. Create a new booking.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Ghi nhận thu tiền offline bằng RECEIVED trong hạn thanh toán. Xuất vé là thao tác Issue ticket riêng.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.
