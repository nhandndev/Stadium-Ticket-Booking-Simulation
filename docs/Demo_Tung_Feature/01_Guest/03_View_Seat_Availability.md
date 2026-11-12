# View Seat Availability

- Dùng để xem từng ghế và trạng thái trong trận để biết còn khả năng mua vé.
- Luồng xử lý trong code `MainController → BrowseController.getSeatMap → BrowseService → MatchSeatRepository / SeatRepository → SeatMapView`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-003"></a>

## Flow 003 — View Seat Availability từ kết quả tìm kiếm

- Bắt đầu ở Main của CORE_DIR sau [Flow 002](02_View_Match_Details.md#flow-002).

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
1. 1
2. 1
3. Vietnam
4. 2
5. 1
6. 0
7. 0
```

### Bước 1 — Nhập `1` rồi Enter

Console trả về

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 2 — Nhập `1` rồi Enter

Console trả về

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Bước 3 — Nhập `Vietnam` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 4 — Nhập `2` rồi Enter

Console trả về

```text
Match ID (0 = Back):
```

### Bước 5 — Nhập `1` rồi Enter

Console trả về

```text
=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 500000       | BOOKED
2        | A2       | Stand A VIP              | 500000       | BOOKED
3        | A3       | Stand A VIP              | 500000       | AVAILABLE
4        | A4       | Stand A VIP              | 500000       | AVAILABLE
5        | A5       | Stand A VIP              | 500000       | AVAILABLE
6        | A6       | Stand A VIP              | 500000       | AVAILABLE
7        | A7       | Stand A VIP              | 500000       | AVAILABLE
8        | A8       | Stand A VIP              | 500000       | AVAILABLE
9        | A9       | Stand A VIP              | 500000       | AVAILABLE
10       | A10      | Stand A VIP              | 500000       | AVAILABLE
11       | B1       | Stand B                  | 300000       | AVAILABLE
12       | B2       | Stand B                  | 300000       | AVAILABLE
13       | B3       | Stand B                  | 300000       | AVAILABLE
14       | B4       | Stand B                  | 300000       | AVAILABLE
15       | B5       | Stand B                  | 300000       | AVAILABLE
16       | B6       | Stand B                  | 300000       | AVAILABLE
17       | B7       | Stand B                  | 300000       | AVAILABLE
18       | B8       | Stand B                  | 300000       | AVAILABLE
19       | B9       | Stand B                  | 300000       | AVAILABLE
20       | B10      | Stand B                  | 300000       | AVAILABLE

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 6 — Nhập `0` rồi Enter

Console trả về

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 7 — Nhập `0` rồi Enter

Console trả về

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Xem từng ghế và trạng thái trong trận để biết còn khả năng mua vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-007"></a>

## Flow 007 — View Seat Map trực tiếp bằng ID

- Bắt đầu ở Main của CORE_DIR sau [Flow 006](02_View_Match_Details.md#flow-006).

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
1. 1
2. 4
3. 1
4. 0
```

### Bước 1 — Nhập `1` rồi Enter

Console trả về

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 2 — Nhập `4` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED
Match ID (0 = Back):
```

### Bước 3 — Nhập `1` rồi Enter

Console trả về

```text
=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 500000       | BOOKED
2        | A2       | Stand A VIP              | 500000       | BOOKED
3        | A3       | Stand A VIP              | 500000       | AVAILABLE
4        | A4       | Stand A VIP              | 500000       | AVAILABLE
5        | A5       | Stand A VIP              | 500000       | AVAILABLE
6        | A6       | Stand A VIP              | 500000       | AVAILABLE
7        | A7       | Stand A VIP              | 500000       | AVAILABLE
8        | A8       | Stand A VIP              | 500000       | AVAILABLE
9        | A9       | Stand A VIP              | 500000       | AVAILABLE
10       | A10      | Stand A VIP              | 500000       | AVAILABLE
11       | B1       | Stand B                  | 300000       | AVAILABLE
12       | B2       | Stand B                  | 300000       | AVAILABLE
13       | B3       | Stand B                  | 300000       | AVAILABLE
14       | B4       | Stand B                  | 300000       | AVAILABLE
15       | B5       | Stand B                  | 300000       | AVAILABLE
16       | B6       | Stand B                  | 300000       | AVAILABLE
17       | B7       | Stand B                  | 300000       | AVAILABLE
18       | B8       | Stand B                  | 300000       | AVAILABLE
19       | B9       | Stand B                  | 300000       | AVAILABLE
20       | B10      | Stand B                  | 300000       | AVAILABLE

=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 4 — Nhập `0` rồi Enter

Console trả về

```text
STADIUM TICKET BOOKING
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate)
4. Register Fan Account
0. Exit System
Choose option:
```

### Điểm cần chỉ khi demo

- Xem từng ghế và trạng thái trong trận để biết còn khả năng mua vé.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
