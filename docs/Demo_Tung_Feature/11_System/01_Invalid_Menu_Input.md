# Invalid Menu Input

- Dùng để thông báo lỗi khi nhập chữ vào menu số rồi cho nhập lại.
- Luồng xử lý trong code `MainController / domain Controller bắt NumberFormatException và quay lại menu`.
- Mỗi lần nhập bên dưới phải nhấn Enter. Dòng Enter trống là không gõ ký tự.

[Chuẩn bị dữ liệu](../00_CHUAN_BI.md) · [Thứ tự chạy đầy đủ](../01_THU_TU_DEMO.md) · [Danh mục tính năng](../README.md)

## Điều kiện demo

- Dùng CORE_DIR từ file chuẩn bị. Chạy các flow theo thứ tự toàn cục để có đúng ID và trạng thái.
- Những flow tạo sân/ghế/trận, mua vé hoặc gửi refund là tiền đề cho các flow dùng lại bản ghi đó.
- Có thể mở file theo từng tính năng để tra cứu; số Flow giữ thứ tự thực thi của buổi demo.

<a id="flow-139"></a>

## Flow 139 — Menu nhập chữ rồi nhập lại đúng

- Bắt đầu ở Main của CORE_DIR sau [Flow 138](../02_Authentication_User/07_CSV_Profile_Persistence.md#flow-138).

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
2. abc
3. 1
4. Flow Red
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

### Bước 2 — Nhập `abc` rồi Enter

Console trả về

```text
Invalid number.

=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Bước 3 — Nhập `1` rồi Enter

Console trả về

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Bước 4 — Nhập `Flow Red` rồi Enter

Console trả về

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

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

- Thông báo lỗi khi nhập chữ vào menu số rồi cho nhập lại.
- Cuối flow đã trở lại Main. Giữ nguyên CORE_DIR để tiếp tục theo thứ tự.
