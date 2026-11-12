# Filter Matches and List Schedule

- Dùng để lọc lịch theo sân/ngày; để trống hai bộ lọc để xem toàn bộ lịch thuộc kết quả browsing.
- Luồng xử lý trong code `MainController → BrowseController.filterMatches → BrowseService → MatchRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng đúng CORE_DIR, COUNTER_DEMO_DIR hoặc EXPIRY_DEMO_DIR theo từng flow. File chuẩn bị có lệnh tạo các bản sao. Chạy theo thứ tự để ID và trạng thái khớp.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-004"></a>

## Flow 004 — Filter Matches theo sân và ngày

- Bắt đầu ở Main của CORE_DIR sau [Flow 003](../01_Guest/03_View_Seat_Availability.md#flow-003).

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
2. 2
3. 1
4. 2026-11-15
5. 0
6. 0
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

### Bước 2 — Nhập `2` rồi Enter

Console trả về

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Bước 3 — Nhập `1` rồi Enter

Console trả về

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Bước 4 — Nhập `2026-11-15` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 5 — Nhập `0` rồi Enter

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

### Bước 6 — Nhập `0` rồi Enter

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

- Lọc lịch theo sân/ngày; để trống hai bộ lọc để xem toàn bộ lịch thuộc kết quả browsing.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-005"></a>

## Flow 005 — List All Matches - bỏ trống bộ lọc

- Bắt đầu ở Main của CORE_DIR sau [Flow 004](../01_Guest/04_Filter_Matches_and_List_Schedule.md#flow-004).

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
2. 2
3. Enter
4. Enter
5. 0
6. 0
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

### Bước 2 — Nhập `2` rồi Enter

Console trả về

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Bước 3 — Nhấn Enter để trống

Console trả về

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Bước 4 — Nhấn Enter để trống

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Bước 5 — Nhập `0` rồi Enter

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

### Bước 6 — Nhập `0` rồi Enter

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

- Lọc lịch theo sân/ngày; để trống hai bộ lọc để xem toàn bộ lịch thuộc kết quả browsing.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
