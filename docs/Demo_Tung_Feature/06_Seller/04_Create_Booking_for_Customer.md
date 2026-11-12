# Create Booking for Customer

- Dùng để chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Luồng xử lý trong code `MainController → BookingController.createBookingForCustomer → BookingService.createBookingForCustomer → BookingRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-085"></a>

## Flow 085 — Create Booking for Customer

- Bắt đầu ở Main của CORE_DIR sau [Flow 084](../06_Seller/03_View_Seat_Map_and_Prices.md#flow-084).

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
5. 3
6. 1
7. flow.counter
8. 7
9. 4
10. 23,24
11. 0
12. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Bước 7 — Nhập `flow.counter` rồi Enter

Console trả về

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Bước 8 — Nhập `7` rồi Enter

Console trả về

```text
Customer: Flow Counter Customer | flow.counter@example.com

=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Bước 9 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | BOOKED
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Use numbers from the SEAT ID column, not row labels such as A1.
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 10 — Nhập `23,24` rồi Enter

Console trả về

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Keep Booking ID 7 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:17:35.838320
Use 4. Accept offline payment before the deadline, then 5. Issue ticket.

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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-143"></a>

## Flow 143 — Create a walk-in booking with a 30-minute seat hold

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
5. 3
6. 2
7. 4
8. 1,2,3,4
9. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN
5 | Công An Hà Nội vs Nam Định | Hang Day Stadium | 2026-01-01T20:30 | Sales: CLOSED
6 | Morocco vs Italy | Hang Day Stadium | 2026-01-21T20:30 | Sales: CLOSED
7 | Belgium vs France | Hang Day Stadium | 2026-02-10T16:00 | Sales: CLOSED
8 | Huế FC vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-03-02T17:30 | Sales: CLOSED
9 | Napoli vs Benfica | Hang Day Stadium | 2026-03-22T17:00 | Sales: CLOSED
10 | Napoli vs Atletico Madrid | Hang Day Stadium | 2026-04-11T18:00 | Sales: CLOSED
11 | Khánh Hòa vs Thể Công Viettel | Hang Day Stadium | 2026-05-01T18:30 | Sales: CLOSED
12 | Tottenham Hotspur vs Chelsea | My Dinh National Stadium | 2026-05-21T18:00 | Sales: CLOSED
13 | England vs South Korea | Hang Day Stadium | 2026-06-10T19:00 | Sales: CLOSED
14 | Singapore vs Vietnam | Hang Day Stadium | 2026-06-30T18:00 | Sales: CLOSED
15 | Đà Nẵng vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-11-13T18:30 | Sales: CLOSED
16 | Argentina vs Spain | My Dinh National Stadium | 2026-12-03T20:00 | Sales: CLOSED
17 | PVF-CAND vs Sông Lam Nghệ An | My Dinh National Stadium | 2026-12-23T16:00 | Sales: CLOSED
18 | Real Madrid vs Inter Milan | My Dinh National Stadium | 2027-01-12T18:30 | Sales: CLOSED
19 | Valencia vs AC Milan | Hang Day Stadium | 2027-02-01T15:00 | Sales: CLOSED
20 | Manchester City vs Manchester United | Hang Day Stadium | 2027-02-21T17:30 | Sales: CLOSED
21 | Qatar vs Vietnam | My Dinh National Stadium | 2027-03-13T18:00 | Sales: CLOSED
22 | Barcelona vs RB Leipzig | My Dinh National Stadium | 2027-04-02T19:00 | Sales: CLOSED
23 | Juventus vs Roma | Hang Day Stadium | 2027-04-22T20:00 | Sales: CLOSED
24 | Benfica vs Ajax | Hang Day Stadium | 2027-05-12T20:00 | Sales: CLOSED
25 | Real Madrid vs Borussia Dortmund | My Dinh National Stadium | 2027-06-01T18:30 | Sales: CLOSED
26 | Malaysia vs Philippines | My Dinh National Stadium | 2027-06-21T19:30 | Sales: CLOSED
27 | China vs Philippines | My Dinh National Stadium | 2027-07-11T18:30 | Sales: CLOSED
28 | PVF-CAND vs Hoàng Anh Gia Lai | Hang Day Stadium | 2027-07-31T19:00 | Sales: CLOSED
29 | Qatar vs Philippines | My Dinh National Stadium | 2027-08-20T17:30 | Sales: CLOSED
30 | Barcelona vs Bayer Leverkusen | Hang Day Stadium | 2027-09-09T18:00 | Sales: CLOSED
31 | France vs Vietnam | My Dinh National Stadium | 2027-09-29T19:30 | Sales: CLOSED
32 | Napoli vs Bayer Leverkusen | Hang Day Stadium | 2027-10-19T16:00 | Sales: CLOSED
33 | South Korea vs Belgium | My Dinh National Stadium | 2027-11-08T20:30 | Sales: CLOSED
34 | Quảng Nam vs Becamex Bình Dương | Hang Day Stadium | 2027-11-28T16:00 | Sales: CLOSED
35 | Hoàng Anh Gia Lai vs Thể Công Viettel | My Dinh National Stadium | 2027-12-18T20:30 | Sales: CLOSED
36 | Thailand vs Indonesia | Hang Day Stadium | 2028-01-07T20:00 | Sales: CLOSED
37 | TP. Hồ Chí Minh vs Hoàng Anh Gia Lai | Hang Day Stadium | 2028-01-27T18:00 | Sales: CLOSED
38 | Philippines vs Qatar | Hang Day Stadium | 2028-02-16T20:30 | Sales: CLOSED
39 | Borussia Dortmund vs Brighton | Hang Day Stadium | 2028-03-07T20:30 | Sales: CLOSED
40 | Đà Nẵng vs PVF-CAND | Hang Day Stadium | 2028-03-27T17:30 | Sales: CLOSED
41 | Malaysia vs Vietnam | Hang Day Stadium | 2028-04-16T20:30 | Sales: CLOSED
42 | Newcastle United vs Roma | Hang Day Stadium | 2028-05-06T17:00 | Sales: CLOSED
43 | Bayern Munich vs PSV Eindhoven | Hang Day Stadium | 2028-05-26T18:00 | Sales: CLOSED
44 | Roma vs Villarreal | Hang Day Stadium | 2028-06-15T19:00 | Sales: CLOSED
45 | Đà Nẵng vs Bình Định | My Dinh National Stadium | 2028-07-05T20:00 | Sales: CLOSED
46 | Philippines vs Morocco | My Dinh National Stadium | 2028-07-25T18:30 | Sales: CLOSED
47 | Hải Phòng FC vs Sông Lam Nghệ An | My Dinh National Stadium | 2028-08-14T17:30 | Sales: CLOSED
48 | Portugal vs Singapore | Hang Day Stadium | 2028-09-03T16:00 | Sales: CLOSED
49 | Valencia vs West Ham United | Hang Day Stadium | 2028-09-23T17:00 | Sales: CLOSED
50 | Lazio vs Villarreal | My Dinh National Stadium | 2028-10-13T18:00 | Sales: CLOSED
51 | Becamex Bình Dương vs Hồng Lĩnh Hà Tĩnh | Hang Day Stadium | 2028-11-02T16:00 | Sales: CLOSED
52 | England vs Belgium | My Dinh National Stadium | 2028-11-22T20:00 | Sales: CLOSED
53 | Villarreal vs Real Madrid | Hang Day Stadium | 2028-12-12T20:30 | Sales: CLOSED
54 | Liverpool vs Marseille | Hang Day Stadium | 2029-01-01T20:30 | Sales: CLOSED
Match ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 100000       | AVAILABLE
2        | A2       | Stand A VIP              | 100000       | AVAILABLE
3        | A3       | Stand A VIP              | 100000       | AVAILABLE
4        | A4       | Stand A VIP              | 100000       | AVAILABLE
5        | A5       | Stand A VIP              | 100000       | AVAILABLE
6        | A6       | Stand A VIP              | 100000       | AVAILABLE
7        | A7       | Stand A VIP              | 100000       | AVAILABLE
8        | A8       | Stand A VIP              | 100000       | AVAILABLE
9        | A9       | Stand A VIP              | 100000       | AVAILABLE
10       | A10      | Stand A VIP              | 100000       | AVAILABLE
11       | B1       | Stand B                  | 50000        | AVAILABLE
12       | B2       | Stand B                  | 50000        | AVAILABLE
13       | B3       | Stand B                  | 50000        | AVAILABLE
14       | B4       | Stand B                  | 50000        | AVAILABLE
15       | B5       | Stand B                  | 50000        | AVAILABLE
16       | B6       | Stand B                  | 50000        | AVAILABLE
17       | B7       | Stand B                  | 50000        | AVAILABLE
18       | B8       | Stand B                  | 50000        | AVAILABLE
19       | B9       | Stand B                  | 50000        | AVAILABLE
20       | B10      | Stand B                  | 50000        | AVAILABLE
Use numbers from the SEAT ID column, not row labels such as A1.
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 8 — Nhập `1,2,3,4` rồi Enter

Console trả về

```text
Booking #8 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A1, Stand A VIP A2, Stand A VIP A3, Stand A VIP A4] | Total 400000 VND | Status PENDING
Keep Booking ID 8 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:36.617484
Use 4. Accept offline payment before the deadline, then 5. Issue ticket.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-145"></a>

## Flow 145 — Create another walk-in booking and defer offline payment

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
5. 3
6. 2
7. 4
8. 5
9. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN
5 | Công An Hà Nội vs Nam Định | Hang Day Stadium | 2026-01-01T20:30 | Sales: CLOSED
6 | Morocco vs Italy | Hang Day Stadium | 2026-01-21T20:30 | Sales: CLOSED
7 | Belgium vs France | Hang Day Stadium | 2026-02-10T16:00 | Sales: CLOSED
8 | Huế FC vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-03-02T17:30 | Sales: CLOSED
9 | Napoli vs Benfica | Hang Day Stadium | 2026-03-22T17:00 | Sales: CLOSED
10 | Napoli vs Atletico Madrid | Hang Day Stadium | 2026-04-11T18:00 | Sales: CLOSED
11 | Khánh Hòa vs Thể Công Viettel | Hang Day Stadium | 2026-05-01T18:30 | Sales: CLOSED
12 | Tottenham Hotspur vs Chelsea | My Dinh National Stadium | 2026-05-21T18:00 | Sales: CLOSED
13 | England vs South Korea | Hang Day Stadium | 2026-06-10T19:00 | Sales: CLOSED
14 | Singapore vs Vietnam | Hang Day Stadium | 2026-06-30T18:00 | Sales: CLOSED
15 | Đà Nẵng vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-11-13T18:30 | Sales: CLOSED
16 | Argentina vs Spain | My Dinh National Stadium | 2026-12-03T20:00 | Sales: CLOSED
17 | PVF-CAND vs Sông Lam Nghệ An | My Dinh National Stadium | 2026-12-23T16:00 | Sales: CLOSED
18 | Real Madrid vs Inter Milan | My Dinh National Stadium | 2027-01-12T18:30 | Sales: CLOSED
19 | Valencia vs AC Milan | Hang Day Stadium | 2027-02-01T15:00 | Sales: CLOSED
20 | Manchester City vs Manchester United | Hang Day Stadium | 2027-02-21T17:30 | Sales: CLOSED
21 | Qatar vs Vietnam | My Dinh National Stadium | 2027-03-13T18:00 | Sales: CLOSED
22 | Barcelona vs RB Leipzig | My Dinh National Stadium | 2027-04-02T19:00 | Sales: CLOSED
23 | Juventus vs Roma | Hang Day Stadium | 2027-04-22T20:00 | Sales: CLOSED
24 | Benfica vs Ajax | Hang Day Stadium | 2027-05-12T20:00 | Sales: CLOSED
25 | Real Madrid vs Borussia Dortmund | My Dinh National Stadium | 2027-06-01T18:30 | Sales: CLOSED
26 | Malaysia vs Philippines | My Dinh National Stadium | 2027-06-21T19:30 | Sales: CLOSED
27 | China vs Philippines | My Dinh National Stadium | 2027-07-11T18:30 | Sales: CLOSED
28 | PVF-CAND vs Hoàng Anh Gia Lai | Hang Day Stadium | 2027-07-31T19:00 | Sales: CLOSED
29 | Qatar vs Philippines | My Dinh National Stadium | 2027-08-20T17:30 | Sales: CLOSED
30 | Barcelona vs Bayer Leverkusen | Hang Day Stadium | 2027-09-09T18:00 | Sales: CLOSED
31 | France vs Vietnam | My Dinh National Stadium | 2027-09-29T19:30 | Sales: CLOSED
32 | Napoli vs Bayer Leverkusen | Hang Day Stadium | 2027-10-19T16:00 | Sales: CLOSED
33 | South Korea vs Belgium | My Dinh National Stadium | 2027-11-08T20:30 | Sales: CLOSED
34 | Quảng Nam vs Becamex Bình Dương | Hang Day Stadium | 2027-11-28T16:00 | Sales: CLOSED
35 | Hoàng Anh Gia Lai vs Thể Công Viettel | My Dinh National Stadium | 2027-12-18T20:30 | Sales: CLOSED
36 | Thailand vs Indonesia | Hang Day Stadium | 2028-01-07T20:00 | Sales: CLOSED
37 | TP. Hồ Chí Minh vs Hoàng Anh Gia Lai | Hang Day Stadium | 2028-01-27T18:00 | Sales: CLOSED
38 | Philippines vs Qatar | Hang Day Stadium | 2028-02-16T20:30 | Sales: CLOSED
39 | Borussia Dortmund vs Brighton | Hang Day Stadium | 2028-03-07T20:30 | Sales: CLOSED
40 | Đà Nẵng vs PVF-CAND | Hang Day Stadium | 2028-03-27T17:30 | Sales: CLOSED
41 | Malaysia vs Vietnam | Hang Day Stadium | 2028-04-16T20:30 | Sales: CLOSED
42 | Newcastle United vs Roma | Hang Day Stadium | 2028-05-06T17:00 | Sales: CLOSED
43 | Bayern Munich vs PSV Eindhoven | Hang Day Stadium | 2028-05-26T18:00 | Sales: CLOSED
44 | Roma vs Villarreal | Hang Day Stadium | 2028-06-15T19:00 | Sales: CLOSED
45 | Đà Nẵng vs Bình Định | My Dinh National Stadium | 2028-07-05T20:00 | Sales: CLOSED
46 | Philippines vs Morocco | My Dinh National Stadium | 2028-07-25T18:30 | Sales: CLOSED
47 | Hải Phòng FC vs Sông Lam Nghệ An | My Dinh National Stadium | 2028-08-14T17:30 | Sales: CLOSED
48 | Portugal vs Singapore | Hang Day Stadium | 2028-09-03T16:00 | Sales: CLOSED
49 | Valencia vs West Ham United | Hang Day Stadium | 2028-09-23T17:00 | Sales: CLOSED
50 | Lazio vs Villarreal | My Dinh National Stadium | 2028-10-13T18:00 | Sales: CLOSED
51 | Becamex Bình Dương vs Hồng Lĩnh Hà Tĩnh | Hang Day Stadium | 2028-11-02T16:00 | Sales: CLOSED
52 | England vs Belgium | My Dinh National Stadium | 2028-11-22T20:00 | Sales: CLOSED
53 | Villarreal vs Real Madrid | Hang Day Stadium | 2028-12-12T20:30 | Sales: CLOSED
54 | Liverpool vs Marseille | Hang Day Stadium | 2029-01-01T20:30 | Sales: CLOSED
Match ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 100000       | BOOKED
2        | A2       | Stand A VIP              | 100000       | BOOKED
3        | A3       | Stand A VIP              | 100000       | BOOKED
4        | A4       | Stand A VIP              | 100000       | BOOKED
5        | A5       | Stand A VIP              | 100000       | AVAILABLE
6        | A6       | Stand A VIP              | 100000       | AVAILABLE
7        | A7       | Stand A VIP              | 100000       | AVAILABLE
8        | A8       | Stand A VIP              | 100000       | AVAILABLE
9        | A9       | Stand A VIP              | 100000       | AVAILABLE
10       | A10      | Stand A VIP              | 100000       | AVAILABLE
11       | B1       | Stand B                  | 50000        | AVAILABLE
12       | B2       | Stand B                  | 50000        | AVAILABLE
13       | B3       | Stand B                  | 50000        | AVAILABLE
14       | B4       | Stand B                  | 50000        | AVAILABLE
15       | B5       | Stand B                  | 50000        | AVAILABLE
16       | B6       | Stand B                  | 50000        | AVAILABLE
17       | B7       | Stand B                  | 50000        | AVAILABLE
18       | B8       | Stand B                  | 50000        | AVAILABLE
19       | B9       | Stand B                  | 50000        | AVAILABLE
20       | B10      | Stand B                  | 50000        | AVAILABLE
Use numbers from the SEAT ID column, not row labels such as A1.
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 8 — Nhập `5` rồi Enter

Console trả về

```text
Booking #9 | Walk-in customer Walk-in customer | Vietnam vs China | Seats [Stand A VIP A5] | Total 100000 VND | Status PENDING
Keep Booking ID 9 for payment or ticket issuance.
Collect cash before recording offline payment.
Seats held until: 2026-11-13T00:21:37.643472
Use 4. Accept offline payment before the deadline, then 5. Issue ticket.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-148"></a>

## Flow 148 — Reject an inactive Fan before selecting the match

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
5. 3
6. 1
7. an.nguyen@gmail.com
8. 1
9. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `1` rồi Enter

Console trả về

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Bước 7 — Nhập `an.nguyen@gmail.com` rồi Enter

Console trả về

```text
Fan #1 | Nguyen Van An | an.nguyen@gmail.com | INACTIVE
Fan ID: (0 = Back):
```

### Bước 8 — Nhập `1` rồi Enter

Console trả về

```text
Fan account #1 is INACTIVE. Choose an ACTIVE customer account.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-149"></a>

## Flow 149 — Explain malformed Seat IDs

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
5. 3
6. 2
7. 4
8. A1,2
9. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN
5 | Công An Hà Nội vs Nam Định | Hang Day Stadium | 2026-01-01T20:30 | Sales: CLOSED
6 | Morocco vs Italy | Hang Day Stadium | 2026-01-21T20:30 | Sales: CLOSED
7 | Belgium vs France | Hang Day Stadium | 2026-02-10T16:00 | Sales: CLOSED
8 | Huế FC vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-03-02T17:30 | Sales: CLOSED
9 | Napoli vs Benfica | Hang Day Stadium | 2026-03-22T17:00 | Sales: CLOSED
10 | Napoli vs Atletico Madrid | Hang Day Stadium | 2026-04-11T18:00 | Sales: CLOSED
11 | Khánh Hòa vs Thể Công Viettel | Hang Day Stadium | 2026-05-01T18:30 | Sales: CLOSED
12 | Tottenham Hotspur vs Chelsea | My Dinh National Stadium | 2026-05-21T18:00 | Sales: CLOSED
13 | England vs South Korea | Hang Day Stadium | 2026-06-10T19:00 | Sales: CLOSED
14 | Singapore vs Vietnam | Hang Day Stadium | 2026-06-30T18:00 | Sales: CLOSED
15 | Đà Nẵng vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-11-13T18:30 | Sales: CLOSED
16 | Argentina vs Spain | My Dinh National Stadium | 2026-12-03T20:00 | Sales: CLOSED
17 | PVF-CAND vs Sông Lam Nghệ An | My Dinh National Stadium | 2026-12-23T16:00 | Sales: CLOSED
18 | Real Madrid vs Inter Milan | My Dinh National Stadium | 2027-01-12T18:30 | Sales: CLOSED
19 | Valencia vs AC Milan | Hang Day Stadium | 2027-02-01T15:00 | Sales: CLOSED
20 | Manchester City vs Manchester United | Hang Day Stadium | 2027-02-21T17:30 | Sales: CLOSED
21 | Qatar vs Vietnam | My Dinh National Stadium | 2027-03-13T18:00 | Sales: CLOSED
22 | Barcelona vs RB Leipzig | My Dinh National Stadium | 2027-04-02T19:00 | Sales: CLOSED
23 | Juventus vs Roma | Hang Day Stadium | 2027-04-22T20:00 | Sales: CLOSED
24 | Benfica vs Ajax | Hang Day Stadium | 2027-05-12T20:00 | Sales: CLOSED
25 | Real Madrid vs Borussia Dortmund | My Dinh National Stadium | 2027-06-01T18:30 | Sales: CLOSED
26 | Malaysia vs Philippines | My Dinh National Stadium | 2027-06-21T19:30 | Sales: CLOSED
27 | China vs Philippines | My Dinh National Stadium | 2027-07-11T18:30 | Sales: CLOSED
28 | PVF-CAND vs Hoàng Anh Gia Lai | Hang Day Stadium | 2027-07-31T19:00 | Sales: CLOSED
29 | Qatar vs Philippines | My Dinh National Stadium | 2027-08-20T17:30 | Sales: CLOSED
30 | Barcelona vs Bayer Leverkusen | Hang Day Stadium | 2027-09-09T18:00 | Sales: CLOSED
31 | France vs Vietnam | My Dinh National Stadium | 2027-09-29T19:30 | Sales: CLOSED
32 | Napoli vs Bayer Leverkusen | Hang Day Stadium | 2027-10-19T16:00 | Sales: CLOSED
33 | South Korea vs Belgium | My Dinh National Stadium | 2027-11-08T20:30 | Sales: CLOSED
34 | Quảng Nam vs Becamex Bình Dương | Hang Day Stadium | 2027-11-28T16:00 | Sales: CLOSED
35 | Hoàng Anh Gia Lai vs Thể Công Viettel | My Dinh National Stadium | 2027-12-18T20:30 | Sales: CLOSED
36 | Thailand vs Indonesia | Hang Day Stadium | 2028-01-07T20:00 | Sales: CLOSED
37 | TP. Hồ Chí Minh vs Hoàng Anh Gia Lai | Hang Day Stadium | 2028-01-27T18:00 | Sales: CLOSED
38 | Philippines vs Qatar | Hang Day Stadium | 2028-02-16T20:30 | Sales: CLOSED
39 | Borussia Dortmund vs Brighton | Hang Day Stadium | 2028-03-07T20:30 | Sales: CLOSED
40 | Đà Nẵng vs PVF-CAND | Hang Day Stadium | 2028-03-27T17:30 | Sales: CLOSED
41 | Malaysia vs Vietnam | Hang Day Stadium | 2028-04-16T20:30 | Sales: CLOSED
42 | Newcastle United vs Roma | Hang Day Stadium | 2028-05-06T17:00 | Sales: CLOSED
43 | Bayern Munich vs PSV Eindhoven | Hang Day Stadium | 2028-05-26T18:00 | Sales: CLOSED
44 | Roma vs Villarreal | Hang Day Stadium | 2028-06-15T19:00 | Sales: CLOSED
45 | Đà Nẵng vs Bình Định | My Dinh National Stadium | 2028-07-05T20:00 | Sales: CLOSED
46 | Philippines vs Morocco | My Dinh National Stadium | 2028-07-25T18:30 | Sales: CLOSED
47 | Hải Phòng FC vs Sông Lam Nghệ An | My Dinh National Stadium | 2028-08-14T17:30 | Sales: CLOSED
48 | Portugal vs Singapore | Hang Day Stadium | 2028-09-03T16:00 | Sales: CLOSED
49 | Valencia vs West Ham United | Hang Day Stadium | 2028-09-23T17:00 | Sales: CLOSED
50 | Lazio vs Villarreal | My Dinh National Stadium | 2028-10-13T18:00 | Sales: CLOSED
51 | Becamex Bình Dương vs Hồng Lĩnh Hà Tĩnh | Hang Day Stadium | 2028-11-02T16:00 | Sales: CLOSED
52 | England vs Belgium | My Dinh National Stadium | 2028-11-22T20:00 | Sales: CLOSED
53 | Villarreal vs Real Madrid | Hang Day Stadium | 2028-12-12T20:30 | Sales: CLOSED
54 | Liverpool vs Marseille | Hang Day Stadium | 2029-01-01T20:30 | Sales: CLOSED
Match ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 100000       | BOOKED
2        | A2       | Stand A VIP              | 100000       | BOOKED
3        | A3       | Stand A VIP              | 100000       | BOOKED
4        | A4       | Stand A VIP              | 100000       | BOOKED
5        | A5       | Stand A VIP              | 100000       | BOOKED
6        | A6       | Stand A VIP              | 100000       | AVAILABLE
7        | A7       | Stand A VIP              | 100000       | AVAILABLE
8        | A8       | Stand A VIP              | 100000       | AVAILABLE
9        | A9       | Stand A VIP              | 100000       | AVAILABLE
10       | A10      | Stand A VIP              | 100000       | AVAILABLE
11       | B1       | Stand B                  | 50000        | AVAILABLE
12       | B2       | Stand B                  | 50000        | AVAILABLE
13       | B3       | Stand B                  | 50000        | AVAILABLE
14       | B4       | Stand B                  | 50000        | AVAILABLE
15       | B5       | Stand B                  | 50000        | AVAILABLE
16       | B6       | Stand B                  | 50000        | AVAILABLE
17       | B7       | Stand B                  | 50000        | AVAILABLE
18       | B8       | Stand B                  | 50000        | AVAILABLE
19       | B9       | Stand B                  | 50000        | AVAILABLE
20       | B10      | Stand B                  | 50000        | AVAILABLE
Use numbers from the SEAT ID column, not row labels such as A1.
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 8 — Nhập `A1,2` rồi Enter

Console trả về

```text
Invalid SEAT ID 'A1'. Use whole numbers separated by commas, for example 1,2,3,4.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.

<a id="flow-150"></a>

## Flow 150 — Explain a seat already sold for the chosen match

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
5. 3
6. 2
7. 4
8. 1
9. 0
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

### Bước 5 — Nhập `3` rồi Enter

Console trả về

```text
=== WHO IS BUYING AT THE COUNTER? ===
1. Existing Fan account - search and select an ACTIVE account
2. Walk-in customer - buy directly, without an account
0. Back to Seller menu
Choose customer type:
```

### Bước 6 — Nhập `2` rồi Enter

Console trả về

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN
5 | Công An Hà Nội vs Nam Định | Hang Day Stadium | 2026-01-01T20:30 | Sales: CLOSED
6 | Morocco vs Italy | Hang Day Stadium | 2026-01-21T20:30 | Sales: CLOSED
7 | Belgium vs France | Hang Day Stadium | 2026-02-10T16:00 | Sales: CLOSED
8 | Huế FC vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-03-02T17:30 | Sales: CLOSED
9 | Napoli vs Benfica | Hang Day Stadium | 2026-03-22T17:00 | Sales: CLOSED
10 | Napoli vs Atletico Madrid | Hang Day Stadium | 2026-04-11T18:00 | Sales: CLOSED
11 | Khánh Hòa vs Thể Công Viettel | Hang Day Stadium | 2026-05-01T18:30 | Sales: CLOSED
12 | Tottenham Hotspur vs Chelsea | My Dinh National Stadium | 2026-05-21T18:00 | Sales: CLOSED
13 | England vs South Korea | Hang Day Stadium | 2026-06-10T19:00 | Sales: CLOSED
14 | Singapore vs Vietnam | Hang Day Stadium | 2026-06-30T18:00 | Sales: CLOSED
15 | Đà Nẵng vs Hoàng Anh Gia Lai | My Dinh National Stadium | 2026-11-13T18:30 | Sales: CLOSED
16 | Argentina vs Spain | My Dinh National Stadium | 2026-12-03T20:00 | Sales: CLOSED
17 | PVF-CAND vs Sông Lam Nghệ An | My Dinh National Stadium | 2026-12-23T16:00 | Sales: CLOSED
18 | Real Madrid vs Inter Milan | My Dinh National Stadium | 2027-01-12T18:30 | Sales: CLOSED
19 | Valencia vs AC Milan | Hang Day Stadium | 2027-02-01T15:00 | Sales: CLOSED
20 | Manchester City vs Manchester United | Hang Day Stadium | 2027-02-21T17:30 | Sales: CLOSED
21 | Qatar vs Vietnam | My Dinh National Stadium | 2027-03-13T18:00 | Sales: CLOSED
22 | Barcelona vs RB Leipzig | My Dinh National Stadium | 2027-04-02T19:00 | Sales: CLOSED
23 | Juventus vs Roma | Hang Day Stadium | 2027-04-22T20:00 | Sales: CLOSED
24 | Benfica vs Ajax | Hang Day Stadium | 2027-05-12T20:00 | Sales: CLOSED
25 | Real Madrid vs Borussia Dortmund | My Dinh National Stadium | 2027-06-01T18:30 | Sales: CLOSED
26 | Malaysia vs Philippines | My Dinh National Stadium | 2027-06-21T19:30 | Sales: CLOSED
27 | China vs Philippines | My Dinh National Stadium | 2027-07-11T18:30 | Sales: CLOSED
28 | PVF-CAND vs Hoàng Anh Gia Lai | Hang Day Stadium | 2027-07-31T19:00 | Sales: CLOSED
29 | Qatar vs Philippines | My Dinh National Stadium | 2027-08-20T17:30 | Sales: CLOSED
30 | Barcelona vs Bayer Leverkusen | Hang Day Stadium | 2027-09-09T18:00 | Sales: CLOSED
31 | France vs Vietnam | My Dinh National Stadium | 2027-09-29T19:30 | Sales: CLOSED
32 | Napoli vs Bayer Leverkusen | Hang Day Stadium | 2027-10-19T16:00 | Sales: CLOSED
33 | South Korea vs Belgium | My Dinh National Stadium | 2027-11-08T20:30 | Sales: CLOSED
34 | Quảng Nam vs Becamex Bình Dương | Hang Day Stadium | 2027-11-28T16:00 | Sales: CLOSED
35 | Hoàng Anh Gia Lai vs Thể Công Viettel | My Dinh National Stadium | 2027-12-18T20:30 | Sales: CLOSED
36 | Thailand vs Indonesia | Hang Day Stadium | 2028-01-07T20:00 | Sales: CLOSED
37 | TP. Hồ Chí Minh vs Hoàng Anh Gia Lai | Hang Day Stadium | 2028-01-27T18:00 | Sales: CLOSED
38 | Philippines vs Qatar | Hang Day Stadium | 2028-02-16T20:30 | Sales: CLOSED
39 | Borussia Dortmund vs Brighton | Hang Day Stadium | 2028-03-07T20:30 | Sales: CLOSED
40 | Đà Nẵng vs PVF-CAND | Hang Day Stadium | 2028-03-27T17:30 | Sales: CLOSED
41 | Malaysia vs Vietnam | Hang Day Stadium | 2028-04-16T20:30 | Sales: CLOSED
42 | Newcastle United vs Roma | Hang Day Stadium | 2028-05-06T17:00 | Sales: CLOSED
43 | Bayern Munich vs PSV Eindhoven | Hang Day Stadium | 2028-05-26T18:00 | Sales: CLOSED
44 | Roma vs Villarreal | Hang Day Stadium | 2028-06-15T19:00 | Sales: CLOSED
45 | Đà Nẵng vs Bình Định | My Dinh National Stadium | 2028-07-05T20:00 | Sales: CLOSED
46 | Philippines vs Morocco | My Dinh National Stadium | 2028-07-25T18:30 | Sales: CLOSED
47 | Hải Phòng FC vs Sông Lam Nghệ An | My Dinh National Stadium | 2028-08-14T17:30 | Sales: CLOSED
48 | Portugal vs Singapore | Hang Day Stadium | 2028-09-03T16:00 | Sales: CLOSED
49 | Valencia vs West Ham United | Hang Day Stadium | 2028-09-23T17:00 | Sales: CLOSED
50 | Lazio vs Villarreal | My Dinh National Stadium | 2028-10-13T18:00 | Sales: CLOSED
51 | Becamex Bình Dương vs Hồng Lĩnh Hà Tĩnh | Hang Day Stadium | 2028-11-02T16:00 | Sales: CLOSED
52 | England vs Belgium | My Dinh National Stadium | 2028-11-22T20:00 | Sales: CLOSED
53 | Villarreal vs Real Madrid | Hang Day Stadium | 2028-12-12T20:30 | Sales: CLOSED
54 | Liverpool vs Marseille | Hang Day Stadium | 2029-01-01T20:30 | Sales: CLOSED
Match ID: (0 = Back):
```

### Bước 7 — Nhập `4` rồi Enter

Console trả về

```text
Match #4 | Vietnam vs China | My Dinh National Stadium | 2030-11-15T19:00 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 100000       | BOOKED
2        | A2       | Stand A VIP              | 100000       | BOOKED
3        | A3       | Stand A VIP              | 100000       | BOOKED
4        | A4       | Stand A VIP              | 100000       | BOOKED
5        | A5       | Stand A VIP              | 100000       | BOOKED
6        | A6       | Stand A VIP              | 100000       | AVAILABLE
7        | A7       | Stand A VIP              | 100000       | AVAILABLE
8        | A8       | Stand A VIP              | 100000       | AVAILABLE
9        | A9       | Stand A VIP              | 100000       | AVAILABLE
10       | A10      | Stand A VIP              | 100000       | AVAILABLE
11       | B1       | Stand B                  | 50000        | AVAILABLE
12       | B2       | Stand B                  | 50000        | AVAILABLE
13       | B3       | Stand B                  | 50000        | AVAILABLE
14       | B4       | Stand B                  | 50000        | AVAILABLE
15       | B5       | Stand B                  | 50000        | AVAILABLE
16       | B6       | Stand B                  | 50000        | AVAILABLE
17       | B7       | Stand B                  | 50000        | AVAILABLE
18       | B8       | Stand B                  | 50000        | AVAILABLE
19       | B9       | Stand B                  | 50000        | AVAILABLE
20       | B10      | Stand B                  | 50000        | AVAILABLE
Use numbers from the SEAT ID column, not row labels such as A1.
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Bước 8 — Nhập `1` rồi Enter

Console trả về

```text
Seat ID 1 is BOOKED for Match #4. Choose an AVAILABLE seat.

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

### Bước 9 — Nhập `0` rồi Enter

Console trả về

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
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

### Bước 11 — Nhập `0` rồi Enter

Console trả về

```text
Goodbye!
```

### Điểm cần chỉ khi demo

- Chọn Fan ACTIVE hoặc khách vãng lai, chọn trận và ghế rồi giữ booking trong hạn thanh toán.
- Thao tác ghi dữ liệu diễn ra trong bản sao demo. Giữ đúng thư mục cho các flow tiếp theo.
