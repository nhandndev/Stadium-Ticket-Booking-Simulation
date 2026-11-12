# Demo từng feature: bạn bấm gì, console trả gì

> Bản replay theo menu hiện tại, ngày 01/11/2026. Mỗi flow có tên feature riêng và bắt đầu từ Main Menu.
> Các block console bên dưới được lấy từ lần chạy ứng dụng thật trên bản sao CSV; không phải output tự đoán.
> Chưa có demo chạy Simulation vì phần thực thi đó chưa triển khai.

> Cập nhật 02/11/2026: năm Fan seed hiện dùng mật khẩu `12345678`. Lượt nhập của flow INACTIVE bên dưới đã được cập nhật và kiểm tra lại; các output snapshot khác vẫn giữ nguyên từ lần replay. Bản sao CSV demo cũ không tự được cập nhật.

## 0. Chuẩn bị và cách đọc

- Chạy từ đầu đến cuối nếu muốn dữ liệu và điều kiện giống bản replay. Các feature tạo dữ liệu là tiền đề cho những feature phía sau.
- Mỗi feature quay lại Main bằng những lần nhập 0 được ghi đầy đủ; số 0 chỉ lùi một cấp, không tự nhảy nhiều menu.
- Chỉ gõ giá trị ở dòng **Bạn nhập**. Block Console là phản hồi, không phải nội dung cần nhập.
- **Enter (để trống)** nghĩa là chỉ nhấn Enter. Mỗi giá trị là một lần nhập rồi Enter.
- Các danh sách dài có dòng đánh dấu rút gọn. Tên menu, prompt và kết quả quan trọng vẫn giữ lại.
- ID, số lượng bản ghi, mã vé và timestamp trong output là ví dụ của bản sao lúc replay. Khi tập lại hãy dùng ID/code console vừa sinh, không dùng cứng mã vé trong tài liệu.
- Đây là bản demo feature, không phải bằng chứng học viên đã tự viết code hay thao tác thủ công toàn bộ.

### Khởi động bằng dữ liệu riêng

Mở Terminal ở gốc dự án, chạy từng lệnh:

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name '*.java')
DEMO_DIR=$(mktemp -d /tmp/stadium-feature-demo.XXXXXX)
cp data/*.csv "$DEMO_DIR/"
java -cp out app.Main "$DEMO_DIR"
```

Giữ nguyên Terminal và DEMO_DIR cho toàn buổi. Không chạy lên data/ thật. Email flow.* trong ví dụ cần chưa tồn tại; nếu đã tập trước, tạo bản sao mới từ dữ liệu gốc hoặc đổi email nhất quán.

Mục lục bên dưới có liên kết tới từng flow. Không cần đọc tuần tự 142 flow nếu chỉ muốn tra thao tác của một feature, nhưng phải có sẵn dữ liệu tiền đề.

### ID dùng trong bản replay

| Đối tượng | ID/code ví dụ |
| --- | --- |
| Fan F | `6` |
| Stadium S | `3` |
| Section K | `6` |
| Seat A1 | `21` |
| Seat A2 | `22` |
| Seat A3 | `23` |
| Seat A4 | `24` |
| Seat A5 | `25` |
| Seat A6 | `26` |
| Seat A7 | `27` |
| Seat A8 | `28` |
| Match M | `4` |
| Booking B1 pending/cancel | `3` |
| Booking B2 online | `4` |
| Ticket T1 | `2` |
| Ticket code C1 - thay bằng code lần chạy của bạn | `T-4-21` |
| Booking B4 refund approve | `5` |
| Booking B5 refund reject | `6` |
| Fan FC tại quầy | `7` |
| Booking B3 cash | `7` |
| Refund R1 approve | `2` |
| Refund R2 reject | `3` |
| Fan FX phụ để xoá | `8` |
| Staff ST phụ | `6` |

Guest dùng các trận có sẵn trước khi Admin tạo trận Flow Red/Flow Blue. Trạng thái ghế cũ phụ thuộc CSV gốc; phần mua vé dùng tám ghế mới riêng.

**Tổng cộng: 142 flow đã được replay.** Các flow lặp tạo ghế A1-A8 là tám lượt chuẩn bị, không phải tám loại feature khác nhau.

## Danh mục flow

- [Flow 001: Search Matches - tìm trận theo tên đội (Guest).](#flow-001)
- [Flow 002: View Match Details từ kết quả tìm kiếm (Guest).](#flow-002)
- [Flow 003: View Seat Availability từ kết quả tìm kiếm (Guest).](#flow-003)
- [Flow 004: Filter Matches theo sân và ngày (Guest).](#flow-004)
- [Flow 005: List All Matches - bỏ trống bộ lọc (Guest).](#flow-005)
- [Flow 006: View Match Details trực tiếp bằng ID (Guest).](#flow-006)
- [Flow 007: View Seat Map trực tiếp bằng ID (Guest).](#flow-007)
- [Flow 008: Search không có kết quả (Guest).](#flow-008)
- [Flow 009: Register Fan Account (User và Authentication).](#flow-009)
- [Flow 010: Register - chặn email trùng (User và Authentication).](#flow-010)
- [Flow 011: Login As Fan (User và Authentication).](#flow-011)
- [Flow 012: View Profile của Fan (User và Authentication).](#flow-012)
- [Flow 013: Update Profile của Fan (User và Authentication).](#flow-013)
- [Flow 014: Logout Fan (User và Authentication).](#flow-014)
- [Flow 015: Login - sai mật khẩu (User và Authentication).](#flow-015)
- [Flow 016: Login - tài khoản INACTIVE (User và Authentication).](#flow-016)
- [Flow 017: Login Fan bằng tài khoản Staff (User và Authentication).](#flow-017)
- [Flow 018: Login Staff bằng tài khoản Fan (User và Authentication).](#flow-018)
- [Flow 019: Huỷ form Login (User và Authentication).](#flow-019)
- [Flow 020: Login As Staff với quyền Administrator (Admin - Stadium).](#flow-020)
- [Flow 021: Create Stadium (Admin - Stadium).](#flow-021)
- [Flow 022: View Stadium List (Admin - Stadium).](#flow-022)
- [Flow 023: View Stadium Details (Admin - Stadium).](#flow-023)
- [Flow 024: Update Stadium (Admin - Stadium).](#flow-024)
- [Flow 025: Create Section (Admin - Section).](#flow-025)
- [Flow 026: View Sections by Stadium (Admin - Section).](#flow-026)
- [Flow 027: View Section Details (Admin - Section).](#flow-027)
- [Flow 028: Update Section (Admin - Section).](#flow-028)
- [Flow 029: Create Seat A1 (Admin - Seat).](#flow-029)
- [Flow 030: Create Seat A2 (Admin - Seat).](#flow-030)
- [Flow 031: Create Seat A3 (Admin - Seat).](#flow-031)
- [Flow 032: Create Seat A4 (Admin - Seat).](#flow-032)
- [Flow 033: Create Seat A5 (Admin - Seat).](#flow-033)
- [Flow 034: Create Seat A6 (Admin - Seat).](#flow-034)
- [Flow 035: Create Seat A7 (Admin - Seat).](#flow-035)
- [Flow 036: Create Seat A8 (Admin - Seat).](#flow-036)
- [Flow 037: View Seats by Section (Admin - Seat).](#flow-037)
- [Flow 038: View Seat Details (Admin - Seat).](#flow-038)
- [Flow 039: Update Seat (Admin - Seat).](#flow-039)
- [Flow 040: Update Seat - trả lại A8 trước bán vé (Admin - Seat).](#flow-040)
- [Flow 041: Deactivate Seat (Admin - Seat).](#flow-041)
- [Flow 042: Activate Seat (Admin - Seat).](#flow-042)
- [Flow 043: Delete Seat - tạo ghế phụ để xoá (Admin - Seat).](#flow-043)
- [Flow 044: Delete Seat - huỷ xác nhận (Admin - Seat).](#flow-044)
- [Flow 045: Delete Seat - xác nhận xoá (Admin - Seat).](#flow-045)
- [Flow 046: Delete Section - tạo khán đài phụ (Admin - Section).](#flow-046)
- [Flow 047: Delete Section (Admin - Section).](#flow-047)
- [Flow 048: Delete Stadium - tạo sân phụ (Admin - Stadium).](#flow-048)
- [Flow 049: Delete Stadium (Admin - Stadium).](#flow-049)
- [Flow 050: Delete Stadium - chặn xoá sân còn khán đài (Admin - Stadium).](#flow-050)
- [Flow 051: Create Match (Admin - Match).](#flow-051)
- [Flow 052: View Match List (Admin - Match).](#flow-052)
- [Flow 053: View Match Details (Admin - Match).](#flow-053)
- [Flow 054: Update Match (Admin - Match).](#flow-054)
- [Flow 055: Delete Match - tạo trận phụ (Admin - Match).](#flow-055)
- [Flow 056: Delete Match (Admin - Match).](#flow-056)
- [Flow 057: Create Ticket Price (Admin - Ticket Pricing).](#flow-057)
- [Flow 058: View Ticket Prices by Match (Admin - Ticket Pricing).](#flow-058)
- [Flow 059: View Ticket Price Details (Admin - Ticket Pricing).](#flow-059)
- [Flow 060: Update Ticket Price (Admin - Ticket Pricing).](#flow-060)
- [Flow 061: Delete Ticket Price (Admin - Ticket Pricing).](#flow-061)
- [Flow 062: Create Ticket Price - khôi phục giá để mở bán (Admin - Ticket Pricing).](#flow-062)
- [Flow 063: View Ticket Sales Status khi chưa mở (Admin - Ticket Sales).](#flow-063)
- [Flow 064: Open Ticket Sales (Admin - Ticket Sales).](#flow-064)
- [Flow 065: Close Ticket Sales (Admin - Ticket Sales).](#flow-065)
- [Flow 066: View Ticket Sales Status sau đóng (Admin - Ticket Sales).](#flow-066)
- [Flow 067: Open Ticket Sales - mở lại cho phần bán vé (Admin - Ticket Sales).](#flow-067)
- [Flow 068: View Seat Map and Prices (Fan - Booking và Payment).](#flow-068)
- [Flow 069: Create Booking - chưa thanh toán (Fan - Booking và Payment).](#flow-069)
- [Flow 070: Booking History (Fan - Booking và Payment).](#flow-070)
- [Flow 071: Booking Details (Fan - Booking và Payment).](#flow-071)
- [Flow 072: Cancel Unpaid Pending Booking (Fan - Cancellation và Refund).](#flow-072)
- [Flow 073: Create Booking - hai ghế để trả tiền sau (Fan - Booking và Payment).](#flow-073)
- [Flow 074: Pay Pending Booking (Fan - Booking và Payment).](#flow-074)
- [Flow 075: My Tickets / Ticket History (Fan - Ticket).](#flow-075)
- [Flow 076: Ticket Details (Fan - Ticket).](#flow-076)
- [Flow 077: Book This Match từ browsing - huỷ chọn ghế (Fan - Booking và Payment).](#flow-077)
- [Flow 078: Create Booking và Pay Now ngay (Fan - Booking và Payment).](#flow-078)
- [Flow 079: Create Booking và Pay Now cho request thứ hai (Fan - Booking và Payment).](#flow-079)
- [Flow 080: Login As Staff với quyền Seller (Seller).](#flow-080)
- [Flow 081: Search Customer (Seller).](#flow-081)
- [Flow 082: List Customers - Enter tại keyword (Seller).](#flow-082)
- [Flow 083: Create Fan Account tại quầy (Seller).](#flow-083)
- [Flow 084: View Seat Map and Prices tại quầy (Seller).](#flow-084)
- [Flow 085: Create Booking for Customer (Seller).](#flow-085)
- [Flow 086: Customer Booking History (Seller).](#flow-086)
- [Flow 087: Accept Cash Payment - huỷ trước khi thu (Seller).](#flow-087)
- [Flow 088: Accept Cash Payment (Seller).](#flow-088)
- [Flow 089: Issue Tickets for Paid Booking (Seller).](#flow-089)
- [Flow 090: Issue Tickets - gọi lại không tạo trùng (Seller).](#flow-090)
- [Flow 091: Browse Matches từ dashboard Seller (Seller).](#flow-091)
- [Flow 092: Request Refund cho B4 (Fan - Cancellation và Refund).](#flow-092)
- [Flow 093: Request Refund cho B5 (Fan - Cancellation và Refund).](#flow-093)
- [Flow 094: My Refund Requests trước review (Fan - Cancellation và Refund).](#flow-094)
- [Flow 095: Approve Refund (Admin - Refund Reviews).](#flow-095)
- [Flow 096: Reject Refund (Admin - Refund Reviews).](#flow-096)
- [Flow 097: My Refund Requests sau review (Fan - Cancellation và Refund).](#flow-097)
- [Flow 098: Ticket History sau hoàn tiền (Fan - Ticket).](#flow-098)
- [Flow 099: Login As Staff với quyền Gate (Gate Staff).](#flow-099)
- [Flow 100: Validate Ticket (Gate Staff).](#flow-100)
- [Flow 101: Check-in Ticket - huỷ xác nhận (Gate Staff).](#flow-101)
- [Flow 102: Check-in Ticket (Gate Staff).](#flow-102)
- [Flow 103: Check-in Ticket - chặn dùng vé lại (Gate Staff).](#flow-103)
- [Flow 104: Validate Ticket - sai trận (Gate Staff).](#flow-104)
- [Flow 105: Check-in Ticket - code không tồn tại (Gate Staff).](#flow-105)
- [Flow 106: Request Refund - chặn vé đã check-in (Fan - Cancellation và Refund).](#flow-106)
- [Flow 107: Create Fan Account bởi Admin (Admin - Fan Accounts).](#flow-107)
- [Flow 108: View Fan List (Admin - Fan Accounts).](#flow-108)
- [Flow 109: View Fan Details (Admin - Fan Accounts).](#flow-109)
- [Flow 110: Search Fan (Admin - Fan Accounts).](#flow-110)
- [Flow 111: Filter Fan Status (Admin - Fan Accounts).](#flow-111)
- [Flow 112: Update Fan Information (Admin - Fan Accounts).](#flow-112)
- [Flow 113: Deactivate Fan Account (Admin - Fan Accounts).](#flow-113)
- [Flow 114: Activate Fan Account (Admin - Fan Accounts).](#flow-114)
- [Flow 115: Delete Fan Account (Admin - Fan Accounts).](#flow-115)
- [Flow 116: Create Staff Account (Admin - Staff Accounts và Roles).](#flow-116)
- [Flow 117: View Staff List (Admin - Staff Accounts và Roles).](#flow-117)
- [Flow 118: View Staff Details (Admin - Staff Accounts và Roles).](#flow-118)
- [Flow 119: Search Staff (Admin - Staff Accounts và Roles).](#flow-119)
- [Flow 120: Filter Staff Role and Status (Admin - Staff Accounts và Roles).](#flow-120)
- [Flow 121: Update Staff Information (Admin - Staff Accounts và Roles).](#flow-121)
- [Flow 122: Deactivate Staff Account (Admin - Staff Accounts và Roles).](#flow-122)
- [Flow 123: Activate Staff Account (Admin - Staff Accounts và Roles).](#flow-123)
- [Flow 124: Change Staff Role (Admin - Staff Accounts và Roles).](#flow-124)
- [Flow 125: Revoke Staff Role (Admin - Staff Accounts và Roles).](#flow-125)
- [Flow 126: Assign Staff Role (Admin - Staff Accounts và Roles).](#flow-126)
- [Flow 127: View Profile của Seller (Profile dùng chung và Simulator).](#flow-127)
- [Flow 128: Update Profile của Seller (Profile dùng chung và Simulator).](#flow-128)
- [Flow 129: View Profile của Gate Staff (Profile dùng chung và Simulator).](#flow-129)
- [Flow 130: Update Profile của Gate Staff (Profile dùng chung và Simulator).](#flow-130)
- [Flow 131: View Profile của Admin (Profile dùng chung và Simulator).](#flow-131)
- [Flow 132: Update Profile của Admin (Profile dùng chung và Simulator).](#flow-132)
- [Flow 133: View Profile của Simulator Operator (Profile dùng chung và Simulator).](#flow-133)
- [Flow 134: Update Profile của Simulator Operator (Profile dùng chung và Simulator).](#flow-134)
- [Flow 135: Logout Simulator Operator (Profile dùng chung và Simulator).](#flow-135)
- [Flow 136: View System Summary (Admin - Reports).](#flow-136)
- [Flow 137: View Audit Log (Admin - Reports).](#flow-137)
- [Flow 138: CSV Persistence - login lại thấy profile đã lưu (User và Authentication).](#flow-138)
- [Flow 139: Menu nhập chữ rồi nhập lại đúng (Bổ sung - lỗi và thoát).](#flow-139)
- [Flow 140: Chọn quá bốn ghế (Bổ sung - lỗi và thoát).](#flow-140)
- [Flow 141: Thanh toán online thất bại (Bổ sung - lỗi và thoát).](#flow-141)
- [Flow 142: Exit System (Bổ sung - lỗi và thoát).](#flow-142)

<a id="flow-001"></a>

## Flow 001 - Search Matches - tìm trận theo tên đội

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 3 - Bạn nhập: `Vietnam` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Guest tìm trận trước khi quyết định đăng ký mua.


<a id="flow-002"></a>

## Flow 002 - View Match Details từ kết quả tìm kiếm

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 3 - Bạn nhập: `Vietnam` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH DETAILS #1 ===
Match:      Vietnam vs Thailand
Stadium:    My Dinh National Stadium [ID: 1]
Kick-off:   15/11/2026 19:00
Sales Gate: [ OPEN ]

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
0. Back to match browsing
Choose action:
```

### Lượt 6 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Không cần quay lại để tìm lại trận.


<a id="flow-003"></a>

## Flow 003 - View Seat Availability từ kết quả tìm kiếm

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 3 - Bạn nhập: `Vietnam` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 500000       | BOOKED
2        | A2       | Stand A VIP              | 500000       | AVAILABLE
3        | A3       | Stand A VIP              | 500000       | AVAILABLE
4        | A4       | Stand A VIP              | 500000       | AVAILABLE
5        | A5       | Stand A VIP              | 500000       | AVAILABLE
6        | A6       | Stand A VIP              | 500000       | AVAILABLE
7        | A7       | Stand A VIP              | 500000       | AVAILABLE
8        | A8       | Stand A VIP              | 500000       | AVAILABLE
[... rút gọn các dòng ở giữa ...]
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

### Lượt 6 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Có Seat ID, khu, giá và trạng thái của dữ liệu hiện tại.


<a id="flow-004"></a>

## Flow 004 - Filter Matches theo sân và ngày

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Lượt 3 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Lượt 4 - Bạn nhập: `2026-11-15` rồi Enter

**Console trả về:**

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

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 6 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Lọc theo hai điều kiện người dùng chọn.


<a id="flow-005"></a>

## Flow 005 - List All Matches - bỏ trống bộ lọc

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== STADIUM FILTER OPTIONS ===
1 | My Dinh National Stadium
2 | Hang Day Stadium
Stadium ID (or press Enter for all):
```

### Lượt 3 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all):
```

### Lượt 4 - Bạn nhập: Enter (để trống)

**Console trả về:**

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

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 6 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Enter tại bộ lọc nghĩa là không áp điều kiện đó.


<a id="flow-006"></a>

## Flow 006 - View Match Details trực tiếp bằng ID

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED
Match ID (0 = Back):
```

### Lượt 3 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH DETAILS #1 ===
Match:      Vietnam vs Thailand
Stadium:    My Dinh National Stadium [ID: 1]
Kick-off:   15/11/2026 19:00
Sales Gate: [ OPEN ]

=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Match Details trực tiếp bằng ID được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-007"></a>

## Flow 007 - View Seat Map trực tiếp bằng ID

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
1    | Vietnam vs Thailand          | My Dinh National Stadium (1) | 15/11/2026 19:00 | OPEN
2    | Vietnam vs Indonesia         | My Dinh National Stadium (1) | 01/12/2026 19:30 | OPEN
3    | Hanoi FC vs Hai Phong FC     | Hang Day Stadium (2)     | 20/11/2026 18:00 | CLOSED
Match ID (0 = Back):
```

### Lượt 3 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
1        | A1       | Stand A VIP              | 500000       | BOOKED
2        | A2       | Stand A VIP              | 500000       | AVAILABLE
3        | A3       | Stand A VIP              | 500000       | AVAILABLE
4        | A4       | Stand A VIP              | 500000       | AVAILABLE
5        | A5       | Stand A VIP              | 500000       | AVAILABLE
6        | A6       | Stand A VIP              | 500000       | AVAILABLE
7        | A7       | Stand A VIP              | 500000       | AVAILABLE
8        | A8       | Stand A VIP              | 500000       | AVAILABLE
[... rút gọn các dòng ở giữa ...]
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

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Seat Map trực tiếp bằng ID được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-008"></a>

## Flow 008 - Search không có kết quả

**Nhóm:** Guest.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 3 - Bạn nhập: `NO_SUCH_TEAM_DEMO` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Không tìm thấy thì không bắt chọn Match ID.


<a id="flow-009"></a>

## Flow 009 - Register Fan Account

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `Flow Demo Fan` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 3 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password (at least 8 characters):
```

### Lượt 4 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
Phone:
```

### Lượt 5 - Bạn nhập: `0901234567` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE
Account created. Select Login As Fan to continue.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Register chỉ tạo Fan, không tự cấp quyền nhân viên.


<a id="flow-010"></a>

## Flow 010 - Register - chặn email trùng

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== REGISTER FAN ACCOUNT ===
Full name (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `Duplicate Fan` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 3 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password (at least 8 characters):
```

### Lượt 4 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
Phone:
```

### Lượt 5 - Bạn nhập: `0901234567` rồi Enter

**Console trả về:**

```text
Email already exists

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Register - chặn email trùng được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-011"></a>

## Flow 011 - Login As Fan

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login As Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-012"></a>

## Flow 012 - View Profile của Fan

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Profile của Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-013"></a>

## Flow 013 - Update Profile của Fan

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Demo Fan
Email:  flow.fan@example.com
Phone:  0901234567
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
New full name (Enter = Cancel):
```

### Lượt 5 - Bạn nhập: `Flow Fan Updated` rồi Enter

**Console trả về:**

```text
New phone:
```

### Lượt 6 - Bạn nhập: `0901234568` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Profile của Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-014"></a>

## Flow 014 - Logout Fan

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Logout Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-015"></a>

## Flow 015 - Login - sai mật khẩu

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `wrong` rồi Enter

**Console trả về:**

```text
Invalid email or password

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login - sai mật khẩu được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-016"></a>

## Flow 016 - Login - tài khoản INACTIVE

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `em.do@gmail.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `12345678` rồi Enter

**Console trả về:**

```text
Account is inactive

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login - tài khoản INACTIVE được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-017"></a>

## Flow 017 - Login Fan bằng tài khoản Staff

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
Use Login As Staff for this account.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login Fan bằng tài khoản Staff được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-018"></a>

## Flow 018 - Login Staff bằng tài khoản Fan

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
Use Login As Fan for this account.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login Staff bằng tài khoản Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-019"></a>

## Flow 019 - Huỷ form Login

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Huỷ form Login được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-020"></a>

## Flow 020 - Login As Staff với quyền Administrator

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login As Staff với quyền Administrator được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-021"></a>

## Flow 021 - Create Stadium

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Name (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Flow Demo Stadium` rồi Enter

**Console trả về:**

```text
Address:
```

### Lượt 9 - Bạn nhập: `Hanoi Demo` rồi Enter

**Console trả về:**

```text
3 | Flow Demo Stadium | Hanoi Demo

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Stadium được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-022"></a>

## Flow 022 - View Stadium List

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Demo

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Stadium List được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-023"></a>

## Flow 023 - View Stadium Details

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Demo
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
3 | Flow Demo Stadium | Hanoi Demo

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Stadium Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-024"></a>

## Flow 024 - Update Stadium

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Demo
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Flow Demo Stadium` rồi Enter

**Console trả về:**

```text
Address:
```

### Lượt 10 - Bạn nhập: `Hanoi Updated` rồi Enter

**Console trả về:**

```text
3 | Flow Demo Stadium | Hanoi Updated

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Stadium được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-025"></a>

## Flow 025 - Create Section

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Section name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Flow Stand` rồi Enter

**Console trả về:**

```text
6 | Flow Stand | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Section được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-026"></a>

## Flow 026 - View Sections by Stadium

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
6 | Flow Stand | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Sections by Stadium được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-027"></a>

## Flow 027 - View Section Details

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
6 | Flow Stand | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Section Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-028"></a>

## Flow 028 - Update Section

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Section name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Flow Stand Updated` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Section được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-029"></a>

## Flow 029 - Create Seat A1

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
21 | Row A, Seat 1 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A1 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-030"></a>

## Flow 030 - Create Seat A2

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
22 | Row A, Seat 2 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A2 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-031"></a>

## Flow 031 - Create Seat A3

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
23 | Row A, Seat 3 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A3 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-032"></a>

## Flow 032 - Create Seat A4

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
24 | Row A, Seat 4 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A4 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-033"></a>

## Flow 033 - Create Seat A5

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
25 | Row A, Seat 5 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A5 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-034"></a>

## Flow 034 - Create Seat A6

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
26 | Row A, Seat 6 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A6 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-035"></a>

## Flow 035 - Create Seat A7

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
27 | Row A, Seat 7 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A7 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-036"></a>

## Flow 036 - Create Seat A8

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Tạo đủ tám ghế riêng để các flow bán vé sau không đụng ghế cũ.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Seat A8 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-037"></a>

## Flow 037 - View Seats by Section

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Seats by Section được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-038"></a>

## Flow 038 - View Seat Details

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `28` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Seat Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-039"></a>

## Flow 039 - Update Seat

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `28` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `80` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 80 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Seat được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-040"></a>

## Flow 040 - Update Seat - trả lại A8 trước bán vé

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 80 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `28` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `A` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Seat - trả lại A8 trước bán vé được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-041"></a>

## Flow 041 - Deactivate Seat

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `28` rồi Enter

**Console trả về:**

```text
Deactivate seat #28? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 8 | Section #6 | Active: false

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Deactivate Seat được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-042"></a>

## Flow 042 - Activate Seat

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: false
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `28` rồi Enter

**Console trả về:**

```text
28 | Row A, Seat 8 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Activate Seat được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-043"></a>

## Flow 043 - Delete Seat - tạo ghế phụ để xoá

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Row label (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `X` rồi Enter

**Console trả về:**

```text
Seat number:
```

### Lượt 10 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
29 | Row X, Seat 1 | Section #6 | Active: true

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Seat - tạo ghế phụ để xoá được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-044"></a>

## Flow 044 - Delete Seat - huỷ xác nhận

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
29 | Row X, Seat 1 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `29` rồi Enter

**Console trả về:**

```text
Delete seat #29? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Seat - huỷ xác nhận được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-045"></a>

## Flow 045 - Delete Seat - xác nhận xoá

**Nhóm:** Admin - Seat.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
1 | Row A, Seat 1 | Section #1 | Active: true
2 | Row A, Seat 2 | Section #1 | Active: true
3 | Row A, Seat 3 | Section #1 | Active: true
4 | Row A, Seat 4 | Section #1 | Active: true
5 | Row A, Seat 5 | Section #1 | Active: true
6 | Row A, Seat 6 | Section #1 | Active: true
7 | Row A, Seat 7 | Section #1 | Active: true
8 | Row A, Seat 8 | Section #1 | Active: true
[... rút gọn các dòng ở giữa ...]
5 | Main Stand | Stadium #2
List is empty.
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
21 | Row A, Seat 1 | Section #6 | Active: true
22 | Row A, Seat 2 | Section #6 | Active: true
23 | Row A, Seat 3 | Section #6 | Active: true
24 | Row A, Seat 4 | Section #6 | Active: true
25 | Row A, Seat 5 | Section #6 | Active: true
26 | Row A, Seat 6 | Section #6 | Active: true
27 | Row A, Seat 7 | Section #6 | Active: true
28 | Row A, Seat 8 | Section #6 | Active: true
29 | Row X, Seat 1 | Section #6 | Active: true
Seat ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `29` rồi Enter

**Console trả về:**

```text
Delete seat #29? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > VENUES > SEATS ===
1. Create seat
2. List seats by section
3. Seat details
4. Update seat
5. Delete seat
6. Activate seat
7. Deactivate seat
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Seat - xác nhận xoá được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-046"></a>

## Flow 046 - Delete Section - tạo khán đài phụ

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Section name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Delete Flow Section` rồi Enter

**Console trả về:**

```text
7 | Delete Flow Section | Stadium #3

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Section - tạo khán đài phụ được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-047"></a>

## Flow 047 - Delete Section

**Nhóm:** Admin - Section.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
1 | Stand A VIP | Stadium #1
2 | Stand B | Stadium #1
3 | Stand C | Stadium #1
4 | Stand D | Stadium #1
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
5 | Main Stand | Stadium #2
3 | Flow Demo Stadium | Hanoi Updated
6 | Flow Stand Updated | Stadium #3
7 | Delete Flow Section | Stadium #3
Section ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Delete section #7? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > VENUES > SECTIONS ===
1. Create section
2. List sections by stadium
3. Section details
4. Update section
5. Delete section
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Section được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-048"></a>

## Flow 048 - Delete Stadium - tạo sân phụ

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Name (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Delete Flow Stadium` rồi Enter

**Console trả về:**

```text
Address:
```

### Lượt 9 - Bạn nhập: `Temporary` rồi Enter

**Console trả về:**

```text
4 | Delete Flow Stadium | Temporary

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Stadium - tạo sân phụ được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-049"></a>

## Flow 049 - Delete Stadium

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
4 | Delete Flow Stadium | Temporary
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Delete stadium #4? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Stadium được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-050"></a>

## Flow 050 - Delete Stadium - chặn xoá sân còn khán đài

**Nhóm:** Admin - Stadium.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Delete stadium #3? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Delete the stadium's sections first

=== ADMIN > VENUES > STADIUMS ===
1. Create stadium
2. List stadiums
3. Stadium details
4. Update stadium
5. Delete stadium
0. Back to venues and seating
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > VENUES AND SEATING ===
1. Stadiums
2. Sections
3. Seats
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Stadium - chặn xoá sân còn khán đài được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-051"></a>

## Flow 051 - Create Match

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Home team:
```

### Lượt 9 - Bạn nhập: `Flow Red` rồi Enter

**Console trả về:**

```text
Away team:
```

### Lượt 10 - Bạn nhập: `Flow Blue` rồi Enter

**Console trả về:**

```text
Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00):
```

### Lượt 11 - Bạn nhập: `2030-11-15T19:00` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Match được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-052"></a>

## Flow 052 - View Match List

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Match List được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-053"></a>

## Flow 053 - View Match Details

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Match Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-054"></a>

## Flow 054 - Update Match

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Lưu ý trước khi bấm:** Trận M chưa có giá, inventory hay booking.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:00 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Stadium ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Home team:
```

### Lượt 10 - Bạn nhập: `Flow Red` rồi Enter

**Console trả về:**

```text
Away team:
```

### Lượt 11 - Bạn nhập: `Flow Blue` rồi Enter

**Console trả về:**

```text
Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00):
```

### Lượt 12 - Bạn nhập: `2030-11-15T19:30` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 16 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Match được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-055"></a>

## Flow 055 - Delete Match - tạo trận phụ

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | My Dinh National Stadium | Le Duc Tho - Hanoi
2 | Hang Day Stadium | Trinh Hoai Duc - Hanoi
3 | Flow Demo Stadium | Hanoi Updated
Stadium ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Home team:
```

### Lượt 9 - Bạn nhập: `Delete Red` rồi Enter

**Console trả về:**

```text
Away team:
```

### Lượt 10 - Bạn nhập: `Delete Blue` rồi Enter

**Console trả về:**

```text
Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00):
```

### Lượt 11 - Bạn nhập: `2030-12-01T19:00` rồi Enter

**Console trả về:**

```text
5 | Delete Red vs Delete Blue | Stadium #3 | 2030-12-01T19:00 | Sales: CLOSED

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Match - tạo trận phụ được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-056"></a>

## Flow 056 - Delete Match

**Nhóm:** Admin - Match.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
5 | Delete Red vs Delete Blue | Stadium #3 | 2030-12-01T19:00 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
Delete match #5? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > MATCHES > MATCH MANAGEMENT ===
1. Create match
2. List matches
3. Match details
4. Update match
5. Delete match
0. Back to matches, pricing and sales
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Match được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-057"></a>

## Flow 057 - Create Ticket Price

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Price in VND (0 = Cancel):
```

### Lượt 10 - Bạn nhập: `250000` rồi Enter

**Console trả về:**

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Ticket Price được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-058"></a>

## Flow 058 - View Ticket Prices by Match

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Ticket Prices by Match được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-059"></a>

## Flow 059 - View Ticket Price Details

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Ticket Price Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-060"></a>

## Flow 060 - Update Ticket Price

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Price in VND (0 = Cancel):
```

### Lượt 10 - Bạn nhập: `260000` rồi Enter

**Console trả về:**

```text
Match #4 | Section #6 | Price: 260000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Ticket Price được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-061"></a>

## Flow 061 - Delete Ticket Price

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Delete price for match #4, section #6? (y/n, Enter = No):
```

### Lượt 10 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Ticket Price được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-062"></a>

## Flow 062 - Create Ticket Price - khôi phục giá để mở bán

**Nhóm:** Admin - Ticket Pricing.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
6 | Flow Stand Updated | Stadium #3
Section ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Price in VND (0 = Cancel):
```

### Lượt 10 - Bạn nhập: `250000` rồi Enter

**Console trả về:**

```text
Match #4 | Section #6 | Price: 250000 VND

=== ADMIN > MATCHES > TICKET PRICING ===
1. Create price
2. List prices by match
3. Price details
4. Update price
5. Delete price
0. Back to matches, pricing and sales
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Ticket Price - khôi phục giá để mở bán được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-063"></a>

## Flow 063 - View Ticket Sales Status khi chưa mở

**Nhóm:** Admin - Ticket Sales.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
CLOSED

=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Ticket Sales Status khi chưa mở được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-064"></a>

## Flow 064 - Open Ticket Sales

**Nhóm:** Admin - Ticket Sales.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: OPEN

=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Open Ticket Sales được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-065"></a>

## Flow 065 - Close Ticket Sales

**Nhóm:** Admin - Ticket Sales.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: OPEN
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Close sales for match #4? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED

=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Close Ticket Sales được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-066"></a>

## Flow 066 - View Ticket Sales Status sau đóng

**Nhóm:** Admin - Ticket Sales.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
CLOSED

=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Ticket Sales Status sau đóng được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-067"></a>

## Flow 067 - Open Ticket Sales - mở lại cho phần bán vé

**Nhóm:** Admin - Ticket Sales.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Vietnam vs Thailand | Stadium #1 | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | Stadium #1 | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Stadium #2 | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: CLOSED
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
4 | Flow Red vs Flow Blue | Stadium #3 | 2030-11-15T19:30 | Sales: OPEN

=== ADMIN > MATCHES > TICKET SALES ===
1. Open ticket sales
2. Close ticket sales
3. View sales status
0. Back to matches, pricing and sales
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > MATCHES, PRICING AND SALES ===
1. Matches
2. Ticket pricing
3. Ticket sales
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Open Ticket Sales - mở lại cho phần bán vé được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-068"></a>

## Flow 068 - View Seat Map and Prices

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Seat Map and Prices được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-069"></a>

## Flow 069 - Create Booking - chưa thanh toán

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `21` rồi Enter

**Console trả về:**

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== SIMULATED ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
Not paid. This booking remains pending; open My bookings and payments > Pay pending booking later.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** PENDING chưa giữ ghế; không nói rằng khách đã chắc chắn mua được ghế.


<a id="flow-070"></a>

## Flow 070 - Booking History

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Booking History được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-071"></a>

## Flow 071 - Booking Details

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Booking Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-072"></a>

## Flow 072 - Cancel Unpaid Pending Booking

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Booking #3 | Flow Red vs Flow Blue | [Flow Stand Updated A1] | 250000 VND | PENDING
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: PENDING
Cancel this unpaid booking? (y/n, Enter = No):
```

### Lượt 8 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
=== BOOKING #3 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1] | IDs: [21]
Total: 250000 VND | Status: CANCELLED

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Cancel Unpaid Pending Booking được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-073"></a>

## Flow 073 - Create Booking - hai ghế để trả tiền sau

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | AVAILABLE
22       | A2       | Flow Stand Updated       | 250000       | AVAILABLE
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `21,22` rồi Enter

**Console trả về:**

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== SIMULATED ONLINE PAYMENT ===
Amount due: 500000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
Not paid. This booking remains pending; open My bookings and payments > Pay pending booking later.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Booking - hai ghế để trả tiền sau được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-074"></a>

## Flow 074 - Pay Pending Booking

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | PENDING
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: PENDING

=== SIMULATED ONLINE PAYMENT ===
Amount due: 500000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 8 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Payment #2 | Booking #4 | Amount 500000 VND | Method ONLINE | Status SUCCESS
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED

=== FAN > MY BOOKINGS AND PAYMENTS ===
1. Booking history
2. Booking details
3. Pay pending booking
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Số tiền lấy từ booking. Thanh toán mô phỏng thành công thì online tự phát vé.


<a id="flow-075"></a>

## Flow 075 - My Tickets / Ticket History

**Nhóm:** Fan - Ticket.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** My Tickets / Ticket History được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-076"></a>

## Flow 076 - Ticket Details

**Nhóm:** Fan - Ticket.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED
Ticket ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1
Code: T-4-21 | Status: ISSUED
Checked in at: Not checked in

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Ticket Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-077"></a>

## Flow 077 - Book This Match từ browsing - huỷ chọn ghế

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 6 - Bạn nhập: `Flow Red` rồi Enter

**Console trả về:**

```text
=== MATCH SCHEDULE ===
ID   | MATCH                        | STADIUM (ID)             | KICK-OFF         | SALES
4    | Flow Red vs Flow Blue        | Flow Demo Stadium (3)    | 15/11/2030 19:30 | OPEN

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book this match
0. Back to match browsing
Choose action:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Booking cancelled.

--- Choose action for matches ---
1. View match details (enter Match ID)
2. View seat availability / Seat map (enter Match ID)
3. Book this match
0. Back to match browsing
Choose action:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Guest không có lựa chọn Book this match; Fan đã login thì có.


<a id="flow-078"></a>

## Flow 078 - Create Booking và Pay Now ngay

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `25` rồi Enter

**Console trả về:**

```text
=== BOOKING #5 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A5] | IDs: [25]
Total: 250000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== SIMULATED ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Payment #3 | Booking #5 | Amount 250000 VND | Method ONLINE | Status SUCCESS
Ticket #4 | Booking #5 | Flow Red vs Flow Blue | Flow Stand Updated A5 | Code T-5-25 | Status ISSUED

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Booking và Pay Now ngay được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-079"></a>

## Flow 079 - Create Booking và Pay Now cho request thứ hai

**Nhóm:** Fan - Booking và Payment.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | AVAILABLE
24       | A4       | Flow Stand Updated       | 250000       | AVAILABLE
25       | A5       | Flow Stand Updated       | 250000       | BOOKED
26       | A6       | Flow Stand Updated       | 250000       | AVAILABLE
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `26` rồi Enter

**Console trả về:**

```text
=== BOOKING #6 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A6] | IDs: [26]
Total: 250000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== SIMULATED ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Payment #4 | Booking #6 | Amount 250000 VND | Method ONLINE | Status SUCCESS
Ticket #5 | Booking #6 | Flow Red vs Flow Blue | Flow Stand Updated A6 | Code T-6-26 | Status ISSUED

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Booking và Pay Now cho request thứ hai được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-080"></a>

## Flow 080 - Login As Staff với quyền Seller

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login As Staff với quyền Seller được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-081"></a>

## Flow 081 - Search Customer

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.fan` rồi Enter

**Console trả về:**

```text
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Search Customer được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-082"></a>

## Flow 082 - List Customers - Enter tại keyword

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
Fan #1 | Nguyen Van An | an.nguyen@gmail.com | ACTIVE
Fan #2 | Tran Thi Binh | binh.tran@gmail.com | ACTIVE
Fan #3 | Le Hoang Cuong | cuong.le@gmail.com | ACTIVE
Fan #4 | Pham Minh Dung | dung.pham@gmail.com | ACTIVE
Fan #5 | Do Thi Em | em.do@gmail.com | INACTIVE
Fan #6 | Flow Fan Updated | flow.fan@example.com | ACTIVE

=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** List Customers - Enter tại keyword được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-083"></a>

## Flow 083 - Create Fan Account tại quầy

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Full name (Enter = Cancel):
```

### Lượt 7 - Bạn nhập: `Flow Counter Customer` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 8 - Bạn nhập: `flow.counter@example.com` rồi Enter

**Console trả về:**

```text
Password (at least 8 characters):
```

### Lượt 9 - Bạn nhập: `Counter123` rồi Enter

**Console trả về:**

```text
Phone:
```

### Lượt 10 - Bạn nhập: `0902222222` rồi Enter

**Console trả về:**

```text
Created Fan #7 | Flow Counter Customer | flow.counter@example.com

=== SELLER > CUSTOMERS ===
1. Search / List customers
2. Create Fan account
0. Back to sales counter
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Fan Account tại quầy được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-084"></a>

## Flow 084 - View Seat Map and Prices tại quầy

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

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

=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Seat Map and Prices tại quầy được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-085"></a>

## Flow 085 - Create Booking for Customer

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com

=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 9 - Bạn nhập: `4` rồi Enter

**Console trả về:**

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
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 10 - Bạn nhập: `23,24` rồi Enter

**Console trả về:**

```text
Booking #7 | Fan #7 Flow Counter Customer | Flow Red vs Flow Blue | Seats [Flow Stand Updated A3, Flow Stand Updated A4] | Total 500000 VND | Status PENDING
Collect cash before recording offline payment.
PENDING bookings do not hold seats.

=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Booking for Customer được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-086"></a>

## Flow 086 - Customer Booking History

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | PENDING

=== SELLER > CUSTOMER BOOKINGS ===
1. View seat map and prices
2. Create booking for customer
3. Customer booking history
0. Back to sales counter
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Customer Booking History được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-087"></a>

## Flow 087 - Accept Cash Payment - huỷ trước khi thu

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | PENDING
Booking ID: (0 = Back):
```

### Lượt 9 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
=== BOOKING #7 ===
Customer: Flow Counter Customer (Fan #7)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A3, Flow Stand Updated A4] | IDs: [23, 24]
Total: 500000 VND | Status: PENDING
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Lượt 10 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
No payment recorded.

=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Accept Cash Payment - huỷ trước khi thu được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-088"></a>

## Flow 088 - Accept Cash Payment

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | PENDING
Booking ID: (0 = Back):
```

### Lượt 9 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
=== BOOKING #7 ===
Customer: Flow Counter Customer (Fan #7)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A3, Flow Stand Updated A4] | IDs: [23, 24]
Total: 500000 VND | Status: PENDING
Cash amount due: 500000 VND
Type RECEIVED after collecting cash (Enter = Cancel):
```

### Lượt 10 - Bạn nhập: `RECEIVED` rồi Enter

**Console trả về:**

```text
Payment #5 | Booking #7 | 500000 VND | Method OFFLINE_CASH | Status SUCCESS
Issue tickets now? (y/n, Enter = No):
```

### Lượt 11 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Chọn n ở Issue tickets now để còn demo phát hành vé riêng.


<a id="flow-089"></a>

## Flow 089 - Issue Tickets for Paid Booking

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Lượt 9 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
=== BOOKING #7 ===
Customer: Flow Counter Customer (Fan #7)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A3, Flow Stand Updated A4] | IDs: [23, 24]
Total: 500000 VND | Status: CONFIRMED
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Issue Tickets for Paid Booking được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-090"></a>

## Flow 090 - Issue Tickets - gọi lại không tạo trùng

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Customer name/email (Enter = List all; 0 = Back):
```

### Lượt 7 - Bạn nhập: `flow.counter` rồi Enter

**Console trả về:**

```text
Fan #7 | Flow Counter Customer | flow.counter@example.com | ACTIVE
Fan ID: (0 = Back):
```

### Lượt 8 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
Customer: Flow Counter Customer | flow.counter@example.com
Booking #7 | Flow Red vs Flow Blue | [Flow Stand Updated A3, Flow Stand Updated A4] | 500000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Lượt 9 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
=== BOOKING #7 ===
Customer: Flow Counter Customer (Fan #7)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A3, Flow Stand Updated A4] | IDs: [23, 24]
Total: 500000 VND | Status: CONFIRMED
Ticket #6 | Code T-7-23 | Flow Red vs Flow Blue | Flow Stand Updated A3 | Status ISSUED
Ticket #7 | Code T-7-24 | Flow Red vs Flow Blue | Flow Stand Updated A4 | Status ISSUED

=== SELLER > PAYMENTS AND TICKET ISSUE ===
1. Accept cash payment
2. Issue tickets for paid booking
0. Back to sales counter
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER > SALES COUNTER ===
1. Customers
2. Customer bookings
3. Payments and ticket issue
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Issue Tickets - gọi lại không tạo trùng được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-091"></a>

## Flow 091 - Browse Matches từ dashboard Seller

**Nhóm:** Seller.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 6 - Bạn nhập: `Flow Red` rồi Enter

**Console trả về:**

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

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Browse Matches từ dashboard Seller được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-092"></a>

## Flow 092 - Request Refund cho B4

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
=== BOOKING #5 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A5] | IDs: [25]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Cannot attend` rồi Enter

**Console trả về:**

```text
Refund request #2 | Booking #5 | Status REQUESTED
Admin review is required. Refunds are simulated; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Request Refund cho B4 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-093"></a>

## Flow 093 - Request Refund cho B5

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #5 | Flow Red vs Flow Blue | [Flow Stand Updated A5] | 250000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
=== BOOKING #6 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A6] | IDs: [26]
Total: 250000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Changed travel plan` rồi Enter

**Console trả về:**

```text
Refund request #3 | Booking #6 | Status REQUESTED
Admin review is required. Refunds are simulated; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Request Refund cho B5 được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-094"></a>

## Flow 094 - My Refund Requests trước review

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Refund request #2 | Booking #5 | Status REQUESTED
Admin review is required. Refunds are simulated; no real money is transferred.
Refund request #3 | Booking #6 | Status REQUESTED
Admin review is required. Refunds are simulated; no real money is transferred.

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** My Refund Requests trước review được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-095"></a>

## Flow 095 - Approve Refund

**Nhóm:** Admin - Refund Reviews.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #2 | Booking #5 | Fan #6 | Cannot attend | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Refund request ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Approve refund? (y/n):
```

### Lượt 8 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Review note (required, 0 = Cancel):
```

### Lượt 9 - Bạn nhập: `Approved before kick-off` rồi Enter

**Console trả về:**

```text
Request #2 | Booking #5 | APPROVED
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Approve Refund được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-096"></a>

## Flow 096 - Reject Refund

**Nhóm:** Admin - Refund Reviews.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED
Request #3 | Booking #6 | Fan #6 | Changed travel plan | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Refund request ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Approve refund? (y/n):
```

### Lượt 8 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
Review note (required, 0 = Cancel):
```

### Lượt 9 - Bạn nhập: `Rejected after review` rồi Enter

**Console trả về:**

```text
Request #3 | Booking #6 | REJECTED
Request #1 | Booking #2 | Fan #1 | hong biebiet | REQUESTED

=== ADMIN > REFUND REVIEWS ===
1. Review a request
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Reject Refund được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-097"></a>

## Flow 097 - My Refund Requests sau review

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
Refund request #2 | Booking #5 | Status APPROVED
Refund request #3 | Booking #6 | Status REJECTED

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** My Refund Requests sau review được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-098"></a>

## Flow 098 - Ticket History sau hoàn tiền

**Nhóm:** Fan - Ticket.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Ticket #2 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A1 | Code T-4-21 | Status ISSUED
Ticket #3 | Booking #4 | Flow Red vs Flow Blue | Flow Stand Updated A2 | Code T-4-22 | Status ISSUED
Ticket #4 | Booking #5 | Flow Red vs Flow Blue | Flow Stand Updated A5 | Code T-5-25 | Status CANCELLED
Ticket #5 | Booking #6 | Flow Red vs Flow Blue | Flow Stand Updated A6 | Code T-6-26 | Status ISSUED

=== FAN > MY TICKETS ===
1. My tickets / Ticket history
2. Ticket details
0. Back to tickets and bookings
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Ticket History sau hoàn tiền được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-099"></a>

## Flow 099 - Login As Staff với quyền Gate

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Login As Staff với quyền Gate được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-100"></a>

## Flow 100 - Validate Ticket

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `T-4-21` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Validate Ticket được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-101"></a>

## Flow 101 - Check-in Ticket - huỷ xác nhận

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `T-4-21` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Lượt 8 - Bạn nhập: `n` rồi Enter

**Console trả về:**

```text
Check-in cancelled.

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Check-in Ticket - huỷ xác nhận được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-102"></a>

## Flow 102 - Check-in Ticket

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `T-4-21` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status ISSUED
Confirm check-in (y/n, Enter = No):
```

### Lượt 8 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Ticket #2 | Code T-4-21 | Flow Red vs Flow Blue (Match #4) | Flow Stand Updated A1 | Status CHECKED_IN | Checked in at 2026-11-01T11:52:39.265056

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Check-in Ticket được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-103"></a>

## Flow 103 - Check-in Ticket - chặn dùng vé lại

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `T-4-21` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Ticket is not issued for this match or was already used

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Check-in Ticket - chặn dùng vé lại được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-104"></a>

## Flow 104 - Validate Ticket - sai trận

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `T-4-22` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Ticket is not issued for this match or was already used

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Validate Ticket - sai trận được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-105"></a>

## Flow 105 - Check-in Ticket - code không tồn tại

**Nhóm:** Gate Staff.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 5 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
Ticket code (Enter or 0 = Back):
```

### Lượt 6 - Bạn nhập: `UNKNOWN_TICKET_FLOW` rồi Enter

**Console trả về:**

```text
Match ID (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Ticket not found

=== GATE STAFF ===
1. Validate ticket
2. Check in ticket
0. Back
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Check-in Ticket - code không tồn tại được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-106"></a>

## Flow 106 - Request Refund - chặn vé đã check-in

**Nhóm:** Fan - Cancellation và Refund.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Booking #4 | Flow Red vs Flow Blue | [Flow Stand Updated A1, Flow Stand Updated A2] | 500000 VND | CONFIRMED
Booking #6 | Flow Red vs Flow Blue | [Flow Stand Updated A6] | 250000 VND | CONFIRMED
Booking ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
=== BOOKING #4 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A1, Flow Stand Updated A2] | IDs: [21, 22]
Total: 500000 VND | Status: CONFIRMED
Reason (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Cannot attend anymore` rồi Enter

**Console trả về:**

```text
Used or cancelled ticket cannot be refunded

=== FAN > CANCELLATIONS AND REFUNDS ===
1. Request refund for paid booking
2. Cancel unpaid pending booking
3. My refund requests
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Request Refund - chặn vé đã check-in được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-107"></a>

## Flow 107 - Create Fan Account bởi Admin

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Full name (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Admin Flow Fan` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 9 - Bạn nhập: `flow.adminfan@example.com` rồi Enter

**Console trả về:**

```text
Password (at least 8 characters):
```

### Lượt 10 - Bạn nhập: `AdminFan123` rồi Enter

**Console trả về:**

```text
Phone:
```

### Lượt 11 - Bạn nhập: `0903333333` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Fan Account bởi Admin được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-108"></a>

## Flow 108 - View Fan List

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Fan List được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-109"></a>

## Flow 109 - View Fan Details

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Fan Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-110"></a>

## Flow 110 - Search Fan

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Keyword (Enter = All):
```

### Lượt 8 - Bạn nhập: `flow.adminfan` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Search Fan được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-111"></a>

## Flow 111 - Filter Fan Status

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 Active | 2 Inactive | 0 All
Status:
```

### Lượt 8 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Filter Fan Status được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-112"></a>

## Flow 112 - Update Fan Information

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
Full name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Admin Flow Fan Updated` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 10 - Bạn nhập: `flow.adminfan@example.com` rồi Enter

**Console trả về:**

```text
New password (Enter = Keep current):
```

### Lượt 11 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
Phone:
```

### Lượt 12 - Bạn nhập: `0903333334` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 16 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Fan Information được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-113"></a>

## Flow 113 - Deactivate Fan Account

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
Deactivate account #8? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: INACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Deactivate Fan Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-114"></a>

## Flow 114 - Activate Fan Account

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: INACTIVE
Fan ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Activate Fan Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-115"></a>

## Flow 115 - Delete Fan Account

**Nhóm:** Admin - Fan Accounts.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `9` rồi Enter

**Console trả về:**

```text
1 | Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE
2 | Tran Thi Binh | binh.tran@gmail.com | Role: FAN | Status: ACTIVE
3 | Le Hoang Cuong | cuong.le@gmail.com | Role: FAN | Status: ACTIVE
4 | Pham Minh Dung | dung.pham@gmail.com | Role: FAN | Status: ACTIVE
5 | Do Thi Em | em.do@gmail.com | Role: FAN | Status: INACTIVE
6 | Flow Fan Updated | flow.fan@example.com | Role: FAN | Status: ACTIVE
7 | Flow Counter Customer | flow.counter@example.com | Role: FAN | Status: ACTIVE
8 | Admin Flow Fan Updated | flow.adminfan@example.com | Role: FAN | Status: ACTIVE
Fan ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
Delete fan #8? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Operation completed.

=== ADMIN > ACCOUNTS > FANS ===
1. Create
2. List
3. Details
4. Search
5. Filter status
6. Update
7. Activate
8. Deactivate
9. Delete
0. Back to accounts and roles
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Delete Fan Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-116"></a>

## Flow 116 - Create Staff Account

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Full name (Enter = Cancel):
```

### Lượt 8 - Bạn nhập: `Flow Staff` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 9 - Bạn nhập: `flow.staff@example.com` rồi Enter

**Console trả về:**

```text
Password (at least 8 characters):
```

### Lượt 10 - Bạn nhập: `StaffPass123` rồi Enter

**Console trả về:**

```text
Phone:
```

### Lượt 11 - Bạn nhập: `0904444444` rồi Enter

**Console trả về:**

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Simulator Operator | 5 Unassigned
Role (0 = Back):
```

### Lượt 12 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 16 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Create Staff Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-117"></a>

## Flow 117 - View Staff List

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Staff List được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-118"></a>

## Flow 118 - View Staff Details

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Staff Details được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-119"></a>

## Flow 119 - Search Staff

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Keyword (Enter = All):
```

### Lượt 8 - Bạn nhập: `flow.staff` rồi Enter

**Console trả về:**

```text
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Search Staff được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-120"></a>

## Flow 120 - Filter Staff Role and Status

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Simulator Operator | 5 Unassigned
Role (0 = All):
```

### Lượt 8 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 Active | 2 Inactive | 0 All
Status:
```

### Lượt 9 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Filter Staff Role and Status được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-121"></a>

## Flow 121 - Update Staff Information

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Full name (Enter = Cancel):
```

### Lượt 9 - Bạn nhập: `Flow Staff Updated` rồi Enter

**Console trả về:**

```text
Email:
```

### Lượt 10 - Bạn nhập: `flow.staff@example.com` rồi Enter

**Console trả về:**

```text
New password (Enter = Keep current):
```

### Lượt 11 - Bạn nhập: Enter (để trống)

**Console trả về:**

```text
Phone:
```

### Lượt 12 - Bạn nhập: `0904444445` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 16 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Staff Information được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-122"></a>

## Flow 122 - Deactivate Staff Account

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `8` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Deactivate account #6? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: INACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Deactivate Staff Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-123"></a>

## Flow 123 - Activate Staff Account

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `7` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: INACTIVE
Staff ID (0 = Back):
```

### Lượt 8 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Activate Staff Account được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-124"></a>

## Flow 124 - Change Staff Role

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `9` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 8 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Simulator Operator | 5 Unassigned
Role (0 = Back):
```

### Lượt 10 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: GATE_STAFF | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Change Staff Role được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-125"></a>

## Flow 125 - Revoke Staff Role

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `9` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 8 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: GATE_STAFF | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
Revoke role from staff #6? (y/n, Enter = No):
```

### Lượt 10 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: UNASSIGNED | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Revoke Staff Role được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-126"></a>

## Flow 126 - Assign Staff Role

**Nhóm:** Admin - Staff Accounts và Roles.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 7 - Bạn nhập: `9` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 8 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
1 | Administrator | admin@stadium.com | Role: ADMIN | Status: ACTIVE
2 | Seller Nguyen | seller1@stadium.com | Role: SELLER | Status: ACTIVE
3 | Seller Tran | seller2@stadium.com | Role: SELLER | Status: ACTIVE
4 | Gate Staff Le | gate1@stadium.com | Role: GATE_STAFF | Status: ACTIVE
5 | Simulator Operator | sim@stadium.com | Role: SIMULATOR_OPERATOR | Status: ACTIVE
6 | Flow Staff Updated | flow.staff@example.com | Role: UNASSIGNED | Status: ACTIVE
Staff ID (0 = Back):
```

### Lượt 9 - Bạn nhập: `6` rồi Enter

**Console trả về:**

```text
1 Seller | 2 Gate Staff | 3 Administrator | 4 Simulator Operator | 5 Unassigned
Role (0 = Back):
```

### Lượt 10 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
6 | Flow Staff Updated | flow.staff@example.com | Role: SELLER | Status: ACTIVE

=== ADMIN > ACCOUNTS > STAFF > ROLES ===
1. Assign role
2. Change role
3. Revoke role
0. Back to staff accounts
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS > STAFF ===
1. Create
2. List
3. Details
4. Search
5. Filter role/status
6. Update
7. Activate
8. Deactivate
9. Staff roles
0. Back to accounts and roles
Choose:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > ACCOUNTS AND ROLES ===
1. Fan accounts
2. Staff accounts
0. Back to system operations
Choose:
```

### Lượt 13 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 14 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 15 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Assign Staff Role được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-127"></a>

## Flow 127 - View Profile của Seller

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

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

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Profile của Seller được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-128"></a>

## Flow 128 - Update Profile của Seller

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `seller1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `seller123` rồi Enter

**Console trả về:**

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

### Lượt 4 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
New full name (Enter = Cancel):
```

### Lượt 5 - Bạn nhập: `Seller Flow Profile` rồi Enter

**Console trả về:**

```text
New phone:
```

### Lượt 6 - Bạn nhập: `0905555555` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Seller Flow Profile
Email:  seller1@stadium.com
Phone:  0905555555
Role:   SELLER | Status: ACTIVE

=== SELLER OPERATIONS ===
1. View profile
2. Sales counter
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Profile của Seller được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-129"></a>

## Flow 129 - View Profile của Gate Staff

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Profile của Gate Staff được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-130"></a>

## Flow 130 - Update Profile của Gate Staff

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `gate1@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `gate123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Le
Email:  gate1@stadium.com
Phone:  0900000004
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
New full name (Enter = Cancel):
```

### Lượt 5 - Bạn nhập: `Gate Staff Flow Profile` rồi Enter

**Console trả về:**

```text
New phone:
```

### Lượt 6 - Bạn nhập: `0905555555` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Gate Staff Flow Profile
Email:  gate1@stadium.com
Phone:  0905555555
Role:   GATE_STAFF | Status: ACTIVE

=== GATE STAFF OPERATIONS ===
1. View profile
2. Validate / Check in ticket
3. Update profile
0. Logout
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Profile của Gate Staff được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-131"></a>

## Flow 131 - View Profile của Admin

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Profile của Admin được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-132"></a>

## Flow 132 - Update Profile của Admin

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Administrator
Email:  admin@stadium.com
Phone:  0900000001
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
New full name (Enter = Cancel):
```

### Lượt 5 - Bạn nhập: `Admin Flow Profile` rồi Enter

**Console trả về:**

```text
New phone:
```

### Lượt 6 - Bạn nhập: `0905555555` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Admin Flow Profile
Email:  admin@stadium.com
Phone:  0905555555
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Profile của Admin được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-133"></a>

## Flow 133 - View Profile của Simulator Operator

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `sim@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `sim123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Simulator Operator
Email:  sim@stadium.com
Phone:  0900000005
Role:   SIMULATOR_OPERATOR | Status: ACTIVE

=== SIMULATOR OPERATOR ===
1. View profile
2. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Simulator Operator
Email:  sim@stadium.com
Phone:  0900000005
Role:   SIMULATOR_OPERATOR | Status: ACTIVE

=== SIMULATOR OPERATOR ===
1. View profile
2. Update profile
0. Logout
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Profile của Simulator Operator được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-134"></a>

## Flow 134 - Update Profile của Simulator Operator

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `sim@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `sim123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Simulator Operator
Email:  sim@stadium.com
Phone:  0900000005
Role:   SIMULATOR_OPERATOR | Status: ACTIVE

=== SIMULATOR OPERATOR ===
1. View profile
2. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
New full name (Enter = Cancel):
```

### Lượt 5 - Bạn nhập: `Simulator Operator Flow Profile` rồi Enter

**Console trả về:**

```text
New phone:
```

### Lượt 6 - Bạn nhập: `0905555555` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Simulator Operator Flow Profile
Email:  sim@stadium.com
Phone:  0905555555
Role:   SIMULATOR_OPERATOR | Status: ACTIVE

=== SIMULATOR OPERATOR ===
1. View profile
2. Update profile
0. Logout
Choose option:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Update Profile của Simulator Operator được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-135"></a>

## Flow 135 - Logout Simulator Operator

**Nhóm:** Profile dùng chung và Simulator.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `sim@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `sim123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Simulator Operator Flow Profile
Email:  sim@stadium.com
Phone:  0905555555
Role:   SIMULATOR_OPERATOR | Status: ACTIVE

=== SIMULATOR OPERATOR ===
1. View profile
2. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Logout Simulator Operator được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-136"></a>

## Flow 136 - View System Summary

**Nhóm:** Admin - Reports.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Admin Flow Profile
Email:  admin@stadium.com
Phone:  0905555555
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== SYSTEM SUMMARY ===
Total fans: 7
Total staff: 6
Total stadiums: 3
Total matches: 4
Total bookings: 7
Total tickets: 7

=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View System Summary được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-137"></a>

## Flow 137 - View Audit Log

**Nhóm:** Admin - Reports.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `3` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `admin@stadium.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `admin123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Admin Flow Profile
Email:  admin@stadium.com
Phone:  0905555555
Role:   ADMIN | Status: ACTIVE

=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `5` rồi Enter

**Console trả về:**

```text
=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== AUDIT LOGS ===
5 | Actor #1 | REVIEW_REFUND | Target: REFUND #3 | Result: REJECTED | 2026-11-01T11:52:38.721182
4 | Actor #1 | REVIEW_REFUND | Target: REFUND #2 | Result: APPROVED | 2026-11-01T11:52:38.615610
3 | Actor #1 | ASSIGN_ROLE | Target: STAFF #3 | Result: SUCCESS | 2026-10-26T00:10
2 | Actor #1 | OPEN_TICKET_SALES | Target: MATCH #1 | Result: SUCCESS | 2026-10-26T00:05
1 | Actor #1 | INITIALIZE_DATASET | Target: SYSTEM #0 | Result: SUCCESS | 2026-10-26T00:00

=== ADMIN > REPORTS AND AUDIT LOGS ===
1. System summary
2. Audit logs
0. Back to system operations
Choose:
```

### Lượt 7 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMIN > SYSTEM OPERATIONS ===
1. Venues and seating
2. Matches, pricing and sales
3. Accounts and roles
4. Refund reviews
5. Reports and audit logs
0. Back to dashboard
Choose:
```

### Lượt 8 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== ADMINISTRATOR ===
1. View profile
2. Manage system operations
3. Update profile
0. Logout
Choose option:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** View Audit Log được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-138"></a>

## Flow 138 - CSV Persistence - login lại thấy profile đã lưu

**Nhóm:** User và Authentication.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Mỗi flow được replay bằng tiến trình mới cùng bản sao CSV; profile vẫn còn.


<a id="flow-139"></a>

## Flow 139 - Menu nhập chữ rồi nhập lại đúng

**Nhóm:** Bổ sung - lỗi và thoát.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 2 - Bạn nhập: `abc` rồi Enter

**Console trả về:**

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

### Lượt 3 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
Enter Team keyword (e.g. Vietnam, Thailand):
```

### Lượt 4 - Bạn nhập: `Flow Red` rồi Enter

**Console trả về:**

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

### Lượt 5 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== MATCH BROWSING ===
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details (by Match ID)
4. View seat availability / Seat map (by Match ID)
0. Back
Choose option:
```

### Lượt 6 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Menu nhập chữ rồi nhập lại đúng được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-140"></a>

## Flow 140 - Chọn quá bốn ghế

**Nhóm:** Bổ sung - lỗi và thoát.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | BOOKED
24       | A4       | Flow Stand Updated       | 250000       | BOOKED
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `21,22,23,24,25` rồi Enter

**Console trả về:**

```text
Choose 1 to 4 seats

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 9 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Chọn quá bốn ghế được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.


<a id="flow-141"></a>

## Flow 141 - Thanh toán online thất bại

**Nhóm:** Bổ sung - lỗi và thoát.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

**Khởi động riêng trước flow này:** thoát bằng Main 0 rồi chạy lại cùng bản sao với:

```bash
java -cp out app.Main "$DEMO_DIR" --payment-fails
```

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== LOGIN ===
Email (Enter = Cancel):
```

### Lượt 2 - Bạn nhập: `flow.fan@example.com` rồi Enter

**Console trả về:**

```text
Password:
```

### Lượt 3 - Bạn nhập: `FlowPass123` rồi Enter

**Console trả về:**

```text
=== USER PROFILE ===
Name:   Flow Fan Updated
Email:  flow.fan@example.com
Phone:  0901234568
Role:   FAN | Status: ACTIVE

=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 4 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 5 - Bạn nhập: `1` rồi Enter

**Console trả về:**

```text
=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 6 - Bạn nhập: `2` rồi Enter

**Console trả về:**

```text
=== MATCHES ===
1 | Vietnam vs Thailand | My Dinh National Stadium | 2026-11-15T19:00 | Sales: OPEN
2 | Vietnam vs Indonesia | My Dinh National Stadium | 2026-12-01T19:30 | Sales: OPEN
3 | Hanoi FC vs Hai Phong FC | Hang Day Stadium | 2026-11-20T18:00 | Sales: CLOSED
4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN
Match ID: (0 = Back):
```

### Lượt 7 - Bạn nhập: `4` rồi Enter

**Console trả về:**

```text
Match #4 | Flow Red vs Flow Blue | Flow Demo Stadium | 2030-11-15T19:30 | Sales: OPEN

=== SEAT AVAILABILITY ===
SEAT ID  | LABEL    | SECTION                  | PRICE (VND)  | STATUS
21       | A1       | Flow Stand Updated       | 250000       | BOOKED
22       | A2       | Flow Stand Updated       | 250000       | BOOKED
23       | A3       | Flow Stand Updated       | 250000       | BOOKED
24       | A4       | Flow Stand Updated       | 250000       | BOOKED
25       | A5       | Flow Stand Updated       | 250000       | AVAILABLE
26       | A6       | Flow Stand Updated       | 250000       | BOOKED
27       | A7       | Flow Stand Updated       | 250000       | AVAILABLE
28       | A8       | Flow Stand Updated       | 250000       | AVAILABLE
Seat IDs (comma-separated, 1 to 4; 0 = Back):
```

### Lượt 8 - Bạn nhập: `27` rồi Enter

**Console trả về:**

```text
=== BOOKING #8 ===
Customer: Flow Fan Updated (Fan #6)
Match: Flow Red vs Flow Blue (#4)
Seats: [Flow Stand Updated A7] | IDs: [27]
Total: 250000 VND | Status: PENDING
Seats are not held until payment succeeds.

=== SIMULATED ONLINE PAYMENT ===
Amount due: 250000 VND
Pay now? (y/n, Enter = No):
```

### Lượt 9 - Bạn nhập: `y` rồi Enter

**Console trả về:**

```text
Payment #6 | Booking #8 | Amount 250000 VND | Method ONLINE | Status FAILED
Payment failed. This booking is cancelled; check availability and create a new booking.

=== FAN > BOOK TICKETS ===
1. View seat map and prices
2. Create booking
0. Back to tickets and bookings
Choose:
```

### Lượt 10 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN > TICKETS AND BOOKINGS ===
1. Book tickets
2. My bookings and payments
3. My tickets
4. Cancellations and refunds
0. Back to dashboard
Choose:
```

### Lượt 11 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
=== FAN DASHBOARD ===
1. View profile
2. Tickets and bookings
3. Update profile
4. Browse matches
0. Logout
Choose option:
```

### Lượt 12 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Logged out.

STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

**Chốt feature với thầy:** Chạy với --payment-fails để cấu hình MockPaymentService, không phải Fan tự chọn kết quả.


<a id="flow-142"></a>

## Flow 142 - Exit System

**Nhóm:** Bổ sung - lỗi và thoát.

**Bắt đầu:** Main Menu, chưa đăng nhập. **Kết thúc:** Main Menu, trừ flow Exit System.

**Điều kiện:** Đã làm các flow trước nếu chúng tạo dữ liệu được dùng ở đây.

Console đầu flow đang ở Main Menu:

```text
STADIUM TICKET BOOKING SIMULATION
1. Browse As Guest (Search matches & View seat availability)
2. Login As Fan
3. Login As Staff (Admin, Seller, Gate, Sim)
4. Register Fan Account
0. Exit System
Choose option:
```

### Lượt 1 - Bạn nhập: `0` rồi Enter

**Console trả về:**

```text
Goodbye!
```

**Chốt feature với thầy:** Exit System được thực hiện bằng đúng menu trên; chỉ ra kết quả vừa hiển thị thay vì chỉ đọc tên hàm.

## Giới hạn cần nói đúng

- Thanh toán/hoàn tiền là mô phỏng, không chuyển tiền thật.
- PENDING chưa giữ ghế; checkout mới kiểm tra và commit nhóm ghế.
- Audit hiện có REVIEW_REFUND; không khẳng định mọi CRUD đều tự ghi audit.
- Role Simulator có login/profile nhưng chưa có chạy benchmark bốn cơ chế.
- Một số list/count chứa bản ghi có sẵn của bản sao, không phải tất cả đều do các flow này tạo.
- Replay được thực hiện bằng agent trên tiến trình CLI; người trình bày vẫn cần tập lại và hiểu nghiệp vụ.
