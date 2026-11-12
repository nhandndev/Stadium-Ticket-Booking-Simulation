# Search Matches

- Dùng để tìm trận theo tên đội; keyword không có kết quả trả danh sách rỗng.
- Luồng xử lý trong code `MainController.runGuestMenu → BrowseController.searchMatches → BrowseService → MatchRepository`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-001"></a>

## Flow 001 — Search Matches - tìm trận theo tên đội

- Bắt đầu ở Main của CORE_DIR vừa tạo.

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
4. 0
5. 0
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

### Bước 4 — Nhập `0` rồi Enter

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

### Bước 5 — Nhập `0` rồi Enter

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

- Tìm trận theo tên đội; keyword không có kết quả trả danh sách rỗng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.

<a id="flow-008"></a>

## Flow 008 — Search không có kết quả

- Bắt đầu ở Main của CORE_DIR sau [Flow 007](03_View_Seat_Availability.md#flow-007).

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
3. NO_SUCH_TEAM_DEMO
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

### Bước 2 — Nhập `1` rồi Enter

Console trả về

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Bước 3 — Nhập `NO_SUCH_TEAM_DEMO` rồi Enter

Console trả về

```text
[!] No matches found matching your criteria.

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

- Tìm trận theo tên đội; keyword không có kết quả trả danh sách rỗng.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
