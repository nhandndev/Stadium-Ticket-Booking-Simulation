# KỊCH BẢN DEMO FULL TÍNH NĂNG (STAGES 01 -> 07)
## HỆ THỐNG MÔ PHỎNG ĐẶT VÉ SÂN VẬN ĐỘNG (STADIUM TICKET BOOKING SIMULATION)

> **Tài liệu tham chiếu chuẩn bị thuyết trình & chạy demo đồ án**  
> **Kiến trúc:** Clean Architecture & Controller-First (3 Layer: View -> Controller -> Service -> Repository -> CSV)  
> **Tỷ lệ hoàn thành hiện tại:** ~65% (Hoàn thiện toàn bộ Nền tảng, Khách vãng lai, Xác thực & Phân quyền đa Role, Quản trị Sân vận động/Khán đài/Ghế, Quản trị Trận đấu/Bảng giá/Đóng mở bán vé, Quản trị Người dùng & Giám sát Hệ thống).

---

## PHẦN 1: TỔNG HỢP DANH SÁCH TOÀN BỘ CÁC TÍNH NĂNG (FEATURES) HIỆN CÓ

Hệ thống đã hiện thực hóa đầy đủ 7 nhóm tính năng cốt lõi (Stages 01 - 07), sẵn sàng chạy kiểm thử thực tế trên dữ liệu tĩnh tại thư mục `data/`:

### 1. Nhóm Khách Vãng Lai (Guest Browsing - Không cần đăng nhập)
*   **Feature 1.1 - Tìm kiếm trận đấu (`searchMatches`)**: Tìm kiếm linh hoạt các trận đấu đang mở bán vé theo từ khóa tên đội bóng (Home/Away team).
*   **Feature 1.2 - Lọc & Liệt kê danh sách trận đấu (`filterMatches`)**: Lọc theo ID sân vận động hoặc theo ngày diễn ra trận đấu; liệt kê toàn bộ lịch thi đấu nếu để trống.
*   **Feature 1.3 - Xem chi tiết thông tin trận đấu (`viewMatchDetails`)**: Xem đầy đủ 2 đội đối đầu, thời gian thi đấu, sân tổ chức và trạng thái mở cổng vé (`OPEN` / `CLOSED`).
*   **Feature 1.4 - Xem sơ đồ ghế trực quan thời gian thực (`getSeatMap`)**: Hiển thị bảng ma trận trạng thái từng ghế (`AVAILABLE`, `HOLD`, `BOOKED`) và số phiên bản lạc quan (`version`).

### 2. Nhóm Đăng Ký & Xác Thực Fan (Fan Registration & Authentication)
*   **Feature 2.1 - Đăng ký tài khoản Cổ động viên (`register`)**: Khởi tạo tài khoản Fan mới với cơ chế kiểm tra tính hợp lệ của Email, Số điện thoại và chống trùng lặp Email trong hệ thống.
*   **Feature 2.2 - Đăng nhập Fan (`login`)**: Xác thực tài khoản hợp lệ, nạp thông tin phiên đăng nhập `currentUser`.
*   **Feature 2.3 - Xem thông tin hồ sơ cá nhân (`View profile`)**: Hiển thị Họ tên, Email, Số điện thoại, Vai trò (`FAN`) và Trạng thái tài khoản (`ACTIVE`).
*   **Feature 2.4 - Bảo vệ an toàn đăng nhập**: 
    *   Tự động phát hiện và chặn đăng nhập đối với tài khoản bị khóa (`INACTIVE` / `SUSPENDED`).
    *   Cơ chế chống đăng nhập nhầm menu: Chặn tài khoản Staff cố tình đăng nhập tại cổng Fan.
*   **Feature 2.5 - Đăng xuất an toàn (`logout`)**: Hủy phiên làm việc của người dùng hiện tại và quay về Menu chính.

### 3. Nhóm Xác Thực & Phân Quyền Đa Nhân Viên (Staff Multi-Role Authentication)
*   **Feature 3.1 - Đăng nhập Nhân viên Bán vé (`SELLER`)**: Đăng nhập và điều hướng vào Menu nghiệp vụ của nhân viên bán vé trực tiếp.
*   **Feature 3.2 - Đăng nhập Nhân viên Soát vé cổng (`GATE_STAFF`)**: Đăng nhập và điều hướng vào Menu nghiệp vụ soát vé tại cửa quay.
*   **Feature 3.3 - Đăng nhập Kỹ thuật viên mô phỏng (`SIMULATOR_OPERATOR`)**: Đăng nhập và điều hướng vào Menu điều hành bot mô phỏng tải cao.
*   **Feature 3.4 - Đăng nhập Quản trị viên hệ thống (`ADMIN`)**: Xác thực quyền Admin tối cao và mở ra Menu Quản trị Hệ thống tổng thể.
*   **Feature 3.5 - Chống đăng nhập chéo**: Chặn tài khoản Fan cố tình đăng nhập tại cổng Staff.

### 4. Nhóm Quản Trị Sân Vận Động, Khán Đài & Ghế Ngồi (Stadium Infrastructure CRUD)
*   **Feature 4.1 - Quản lý Sân vận động (`StadiumController`)**: Liệt kê danh sách sân, xem chi tiết thông tin địa chỉ từng sân vận động (`getStadiums`, `getStadiumDetails`).
*   **Feature 4.2 - Quản lý Khán đài (`Section`)**: Xem danh sách các khán đài (Stand A, B, C, D) theo từng sân vận động, xem thông tin chi tiết khán đài (`getSections`, `getSectionDetails`).
*   **Feature 4.3 - Quản lý Ghế ngồi (`Seat`)**: Xem danh sách ghế theo từng khán đài (`buildSeatMap`), xem thông tin chi tiết từng hàng ghế (`getSeatDetails`).
*   **Feature 4.4 - Điều phối bảo trì ghế (`activateSeat`, `deactivateSeat`)**: Tắt hoạt động (vô hiệu hóa) một ghế khi bị hư hỏng và kích hoạt lại sau khi bảo trì xong.

### 5. Nhóm Quản Trị Trận Đấu, Bảng Giá & Mở/Đóng Cổng Vé (Match, Pricing & Ticket Sales)
*   **Feature 5.1 - Quản lý Lịch thi đấu (`MatchController`)**: Xem danh sách tất cả các trận đấu, tra cứu chi tiết trận đấu (`getMatches`, `getMatchDetails`).
*   **Feature 5.2 - Quản lý Bảng giá vé theo Khán đài (`TicketPrice`)**: Thiết lập và tra cứu giá vé theo từng khán đài của trận đấu (`getTicketPrices`, `getTicketPriceDetails`).
*   **Feature 5.3 - Điều phối Mở/Đóng cổng bán vé (`openTicketSales`, `closeTicketSales`)**: 
    *   Đóng cổng bán vé khi trận đấu chuẩn bị diễn ra hoặc quá tải.
    *   Mở cổng bán vé cho cổ động viên vào đặt chỗ.
    *   Kiểm tra nhanh trạng thái bán vé (`getTicketSalesStatus`).

### 6. Nhóm Quản Trị Tài Khoản Người Dùng (Account Administration)
*   **Feature 6.1 - Quản trị Fan (`AccountAdminController`)**: 
    *   Liệt kê danh sách toàn bộ Fan, tra cứu chi tiết Fan theo ID.
    *   Tìm kiếm Fan nhanh theo tên hoặc địa chỉ email.
    *   Lọc Fan theo trạng thái tài khoản (`ACTIVE`, `INACTIVE`).
    *   Khóa tài khoản Fan vi phạm (`deactivateFan`) và Mở khóa tài khoản (`activateFan`).
*   **Feature 6.2 - Quản trị Nhân viên (`Staff`)**: 
    *   Liệt kê danh sách nhân viên nội bộ, tra cứu chi tiết nhân viên.
    *   Tìm kiếm nhân viên theo từ khóa tên/email.
    *   Lọc nhân viên theo chức vụ (`Role`) và trạng thái (`Status`).
    *   Luân chuyển chức vụ: Đổi role nhân viên (`changeRole`), gán quyền (`assignRole`), thu hồi quyền (`revokeRole`).

### 7. Nhóm Giám Sát Hệ Thống & Nhật Ký Kiểm Toán (System Dashboard & Audit Logs)
*   **Feature 7.1 - Bảng điều khiển tổng quan hệ thống (`getSystemSummary`)**: Thống kê số lượng theo thời gian thực: Tổng số Fan, Nhân viên, Sân vận động, Trận đấu.
*   **Feature 7.2 - Nhật ký kiểm toán an ninh (`getAuditLogs`)**: Truy vết toàn bộ lịch sử can thiệp hệ thống: Ai (Actor ID) thực hiện hành động gì (Action) lên thực thể nào (Target Type & ID), kết quả (Result) vào thời điểm nào (Timestamp).

---

## PHẦN 2: DỮ LIỆU MẪU CÓ SẴN TRONG HỆ THỐNG (DATASET CHEAT SHEET)

Để tiện cho người thuyết trình thao tác mượt mà trước Giảng viên, dưới đây là bảng tra cứu dữ liệu đã nạp sẵn trong thư mục `data/*.csv`:

| Đối tượng | Dữ liệu kiểm thử có sẵn | Ghi chú cho Demo |
| :--- | :--- | :--- |
| **Admin** | `admin@stadium.com` / `admin123` | Quản trị viên tối cao, truy cập toàn bộ menu quản trị |
| **Seller** | `seller1@stadium.com` / `seller123` | Nhân viên bán vé |
| **Gate Staff** | `gate1@stadium.com` / `gate123` | Nhân viên soát vé cổng |
| **Simulator** | `sim@stadium.com` / `sim123` | Kỹ thuật viên mô phỏng bot |
| **Fan (Active)** | `an.nguyen@gmail.com` / `123456` | Cổ động viên hoạt động bình thường |
| **Fan (Inactive)** | `em.do@gmail.com` / `123456` | Tài khoản bị khóa, dùng để test chặn login |
| **Stadium** | ID: `1` (Mỹ Đình), ID: `2` (Hàng Đẫy) | Có danh sách khán đài và sơ đồ ghế |
| **Match** | ID: `1` (Vietnam vs Thailand - OPEN)<br>ID: `2` (Vietnam vs Indonesia - OPEN)<br>ID: `3` (Hanoi FC vs Hai Phong FC - CLOSED) | Đã cấu hình giá vé và sơ đồ ghế cho Match 1 |

---

## PHẦN 3: KỊCH BẢN THAO TÁC DEMO TỪNG BƯỚC (BẢN THỰC CHIẾN)

### Chuẩn bị khởi chạy:
Mở Terminal tại thư mục gốc của dự án và chạy lệnh:
```bash
# Biên dịch toàn bộ mã nguồn
javac -encoding UTF-8 -d out $(find src -name "*.java")

# Khởi chạy ứng dụng
java -cp out app.Main
```

---

### ACT 1: DEMO TÍNH NĂNG KHÁCH VÃNG LAI (GUEST BROWSING & SEAT MAP)
> **Mục tiêu:** Chứng minh người dùng chưa có tài khoản vẫn có thể tìm kiếm trận đấu, lọc lịch thi đấu, và ngay tại kết quả tìm kiếm có thể bấm xem chi tiết trận đấu hoặc xem trực tiếp sơ đồ ghế trống theo thời gian thực mà không cần gõ lại từ đầu.

#### Các bước bấm phím:
1. Tại Main Menu: Nhập **`1`** (Browse As Guest).
2. Tại Guest Menu: Nhập **`1`** (Search matches by team keyword) -> Nhập từ khóa: **`Viet`**.
   * *Kết quả:* Hiển thị bảng lịch thi đấu đẹp mắt với ngày giờ định dạng chuẩn (`15/11/2026 19:00`):
     - Match 1: Vietnam vs Thailand (OPEN)
     - Match 2: Vietnam vs Indonesia (OPEN)
   * *Ngay dưới kết quả, hệ thống hiển thị Sub-Action Menu để thao tác trực tiếp:*
     - Nhập **`1`** (View match details) -> Nhập Match ID: **`1`** -> Xem chi tiết trận đấu với tên sân rõ ràng: `My Dinh National Stadium (Le Duc Tho - Hanoi) [ID: 1]`.
     - Tiếp tục nhập **`2`** (View seat availability) -> Nhập Match ID: **`1`** -> Hiển thị ma trận 20 ghế kèm tên khán đài trực quan: `Stand A VIP (Sec 1)`, `Stand B (Sec 2)` với trạng thái `AVAILABLE`.
     - Nhập **`0`** để quay lại Guest Menu.
3. Tại Guest Menu: Nhập **`2`** (Filter matches / List all schedule):
   * Hệ thống gợi ý danh sách sân: `Available Stadiums: [1] My Dinh National Stadium | [2] Hang Day Stadium`.
   * Nhấn **`Enter`** (bỏ trống Stadium ID để chọn tất cả sân).
   * Nhấn **`Enter`** (bỏ trống Ngày để chọn tất cả ngày).
   * *Kết quả:* Hiển thị trọn bộ 3 trận đấu trong hệ thống kèm Sub-Action Menu cho phép tra cứu nhanh bất kỳ trận nào.
   * Nhập **`0`** để quay lại Guest Menu.
4. Nhập **`0`** để quay lại Main Menu.

🗣️ **Lời thoại thuyết minh:**
> *"Kính thưa Thầy/Cô, đầu tiên em xin phép demo phân hệ Khách vãng lai (Guest Browsing). Giao diện Console đã được thiết kế lại theo dạng bảng và thẻ thông tin trực quan, ngày giờ được format chuẩn dd/MM/yyyy. Đặc biệt, khi khán giả tìm kiếm trận đấu (ví dụ từ khóa 'Viet') hoặc lọc lịch thi đấu, hệ thống không bắt người dùng phải quay lại menu chính mà cung cấp ngay Action Menu ngữ cảnh: cho phép nhập Match ID để xem chi tiết sân vận động đầy đủ tên địa chỉ, hoặc xem ngay ma trận ghế trống hiển thị rõ từng khán đài như 'Stand A VIP' hay 'Stand B'. Điều này giúp trải nghiệm tra cứu cực kỳ mượt mà và thân thiện."*

---

### ACT 2: DEMO ĐĂNG KÝ & XÁC THỰC TÀI KHOẢN CỔ ĐỘNG VIÊN (FAN AUTH)
> **Mục tiêu:** Chứng minh luồng đăng ký tài khoản tự động sinh ID và lưu vào CSV, đăng nhập thành công, xem profile, và kiểm thử cơ chế an ninh chặn tài khoản Inactive cũng như chống đăng nhập nhầm menu.

#### Các bước bấm phím:
1. Tại Main Menu: Nhập **`4`** (Register Fan Account).
   * Full name: **`Le Van Demo`**
   * Email: **`demo.fan@gmail.com`**
   * Password: **`pass123`**
   * Phone: **`0987654321`**
   * *Kết quả:* Hệ thống tạo thành công tài khoản Fan với Role `FAN`, Status `ACTIVE` và ghi trực tiếp vào `data/fans.csv`.
2. Tại Main Menu: Nhập **`2`** (Login As Fan).
   * Email: **`an.nguyen@gmail.com`**
   * Password: **`123456`**
   * *Kết quả:* Đăng nhập thành công! Hiển thị menu dành riêng cho Fan.
3. Tại Fan Menu: Nhập **`1`** (View profile).
   * *Kết quả:* Hiển thị đúng thông tin: `Nguyen Van An | an.nguyen@gmail.com | Role: FAN | Status: ACTIVE`.
4. Nhập **`0`** (Logout) để đăng xuất.
5. **Kiểm thử An ninh 1 (Chặn tài khoản Inactive):**
   * Tại Main Menu: Nhập **`2`** (Login As Fan).
   * Email: **`em.do@gmail.com`**
   * Password: **`123456`**
   * *Kết quả:* Hệ thống bắt AppException và thông báo: **`Account is inactive`**.
6. **Kiểm thử An ninh 2 (Chống đăng nhập nhầm menu):**
   * Tại Main Menu: Nhập **`2`** (Login As Fan).
   * Email: **`admin@stadium.com`** (Tài khoản của Admin)
   * Password: **`admin123`**
   * *Kết quả:* Hệ thống tự động đẩy ra và báo: **`This account belongs to the other login menu.`**

🗣️ **Lời thoại thuyết minh:**
> *"Tiếp theo là phân hệ Quản lý định danh Fan. Em vừa thực hiện đăng ký một tài khoản mới 'Le Van Demo', dữ liệu được parse tự động và ghi thẳng xuống file CSV. Khi đăng nhập bằng tài khoản hợp lệ, người dùng xem được Profile cá nhân. Đặc biệt, hệ thống xử lý ngoại lệ nghiệp vụ chặt chẽ: nếu tài khoản đang ở trạng thái INACTIVE như bạn Đỗ Thị Em, hệ thống từ chối đăng nhập. Đồng thời, nếu tài khoản Staff như Admin cố tình đăng nhập vào cổng Fan, hệ thống sẽ phát hiện sai lệch Role và tự động từ chối truy cập."*

---

### ACT 3: DEMO XÁC THỰC ĐA NHÂN VIÊN (STAFF MULTI-ROLE AUTHENTICATION)
> **Mục tiêu:** Chứng minh hệ thống hỗ trợ đa vai trò (SELLER, GATE_STAFF, SIMULATOR_OPERATOR) và điều hướng đúng menu phân quyền tương ứng.

#### Các bước bấm phím:
1. Tại Main Menu: Nhập **`3`** (Login As Staff).
   * Email: **`seller1@stadium.com`** / Password: **`seller123`**
   * *Kết quả:* Vào đúng **`=== SELLER ===`** Menu. Nhập `1` xem Profile -> Nhập `0` Logout.
2. Tại Main Menu: Nhập **`3`** (Login As Staff).
   * Email: **`gate1@stadium.com`** / Password: **`gate123`**
   * *Kết quả:* Vào đúng **`=== GATE STAFF ===`** Menu. Nhập `1` xem Profile -> Nhập `0` Logout.
3. Tại Main Menu: Nhập **`3`** (Login As Staff).
   * Email: **`sim@stadium.com`** / Password: **`sim123`**
   * *Kết quả:* Vào đúng **`=== SIMULATOR OPERATOR ===`** Menu. Nhập `1` xem Profile -> Nhập `0` Logout.
4. **Kiểm thử chống đăng nhập chéo:**
   * Tại Main Menu: Nhập **`3`** (Login As Staff).
   * Email: **`an.nguyen@gmail.com`** (Tài khoản Fan) / Password: **`123456`**
   * *Kết quả:* Báo lỗi: **`This account belongs to the other login menu.`**

🗣️ **Lời thoại thuyết minh:**
> *"Hệ thống xây dựng theo mô hình phân quyền dựa trên Role (RBAC). Cùng một cổng đăng nhập Staff, hệ thống tự động nhận diện Role của người dùng để điều hướng vào đúng không gian làm việc của Seller, Gate Staff hay Simulator Operator. Nếu một Fan cố tình đăng nhập vào cổng nội bộ Staff, hệ thống lập tức ngăn chặn."*

---

### ACT 4: DEMO QUẢN TRỊ SÂN VẬN ĐỘNG & BẢO TRÌ GHẾ (STADIUM CRUD)
> **Mục tiêu:** Chứng minh Admin quản lý toàn diện cấu trúc cây Sân -> Khán đài -> Ghế, và thực hiện nghiệp vụ vô hiệu hóa ghế hỏng.

#### Các bước bấm phím:
1. Tại Main Menu: Nhập **`3`** (Login As Staff).
   * Email: **`admin@stadium.com`** / Password: **`admin123`**
   * *Kết quả:* Đăng nhập thành công với vai trò **`ADMIN`**.
2. Tại Admin Menu: Nhập **`2`** (Manage system) -> Chọn **`1`** (Stadium/Section/Seat).
3. **Xem danh sách sân:** Nhập **`2`** (List stadiums).
   * *Kết quả:* Hiển thị Sân 1 (Mỹ Đình) và Sân 2 (Hàng Đẫy).
4. **Xem khán đài của Sân 1:** Nhập **`7`** (List sections) -> Stadium ID: **`1`**.
   * *Kết quả:* Hiển thị 4 khán đài: Stand A VIP, Stand B, Stand C, Stand D.
5. **Xem sơ đồ ghế của Khán đài 1:** Nhập **`12`** (List seats) -> Section ID: **`1`**.
   * *Kết quả:* Hiển thị danh sách 10 ghế từ A1 đến A10 với `Active: true`.
6. **Bảo trì ghế (Vô hiệu hóa ghế hỏng):** Nhập **`17`** (Deactivate seat) -> Seat ID: **`1`**.
   * *Kết quả:* Ghế số 1 chuyển sang `Active: false`.
7. **Xem lại chi tiết ghế 1:** Nhập **`13`** (Seat details) -> Seat ID: **`1`**.
   * *Kết quả:* `1 | Row: A, Seat: 1 | Section: 1 | Active: false`.
8. **Kích hoạt lại ghế sau khi sửa:** Nhập **`16`** (Activate seat) -> Seat ID: **`1`**.
   * *Kết quả:* Ghế số 1 trở lại `Active: true`.
9. Nhập **`0`** để quay lại Admin Menu.

🗣️ **Lời thoại thuyết minh:**
> *"Bây giờ em tiến hành đăng nhập với quyền Administrator. Trong Menu quản lý Sân vận động, hệ thống tổ chức quan hệ phân cấp chặt chẽ: Sân vận động chứa nhiều Khán đài, Khán đài chứa nhiều Ghế ngồi. Admin có thể tra cứu chi tiết từng ghế. Khi phát hiện một ghế bị hư hỏng cơ học, Admin có thể thực hiện chức năng Deactivate ghế đó để ngăn không cho khách đặt vé, sau khi sửa chữa xong thì kích hoạt Activate trở lại."*

---

### ACT 5: DEMO QUẢN TRỊ TRẬN ĐẤU, BẢNG GIÁ & ĐIỀU PHỐI CỔNG VÉ
> **Mục tiêu:** Chứng minh Admin quản lý lịch đấu, xem bảng giá vé từng khán đài, và đóng/mở cổng bán vé linh hoạt.

#### Các bước bấm phím:
1. Tại Admin Menu: Nhập **`2`** (Match/Pricing/Sales).
2. **Xem danh sách trận đấu:** Nhập **`2`** (List matches).
   * *Kết quả:* Hiển thị 3 trận đấu kèm trạng thái bán vé (`OPEN` / `CLOSED`).
3. **Xem chi tiết trận 1:** Nhập **`3`** (Match details) -> Match ID: **`1`**.
   * *Kết quả:* `1 | Vietnam vs Thailand | Stadium: 1 | Time: 2026-11-15T19:00 | Sales: OPEN`.
4. **Xem bảng giá vé trận 1:** Nhập **`7`** (List prices) -> Match ID: **`1`**.
   * *Kết quả:*
     * Section 1 (Stand A VIP): 500.000 VND
     * Section 2 (Stand B): 300.000 VND
     * Section 3 (Stand C): 200.000 VND
     * Section 4 (Stand D): 150.000 VND
5. **Kiểm tra trạng thái cổng vé:** Nhập **`13`** (Sales status) -> Match ID: **`1`** -> Kết quả: **`OPEN`**.
6. **Đóng cổng vé:** Nhập **`12`** (Close sales) -> Match ID: **`1`**.
   * *Kết quả:* Trận đấu cập nhật sang trạng thái **`CLOSED`**.
7. **Kiểm tra lại trạng thái cổng vé:** Nhập **`13`** -> Match ID: **`1`** -> Kết quả: **`CLOSED`**.
8. **Mở lại cổng vé:** Nhập **`11`** (Open sales) -> Match ID: **`1`** -> Kết quả: Trở lại **`OPEN`**.
9. Nhập **`0`** để quay lại Admin Menu.

🗣️ **Lời thoại thuyết minh:**
> *"Tại phân hệ Trận đấu và Bảng giá, mỗi trận đấu được liên kết với một ma trận giá vé linh hoạt theo từng khán đài. Ví dụ Khán đài A VIP là 500.000đ, các khán đài B, C, D có mức giá giảm dần. Admin có toàn quyền kiểm soát vòng đời mở bán: khi chuẩn bị trước trận đấu có thể mở Open Sales, khi cần bảo trì hoặc hết giờ có thể Close Sales chỉ bằng một thao tác."*

---

### ACT 6: DEMO QUẢN TRỊ TÀI KHOẢN & ĐIỀU CHUYỂN CHỨC VỤ (USER ADMIN)
> **Mục tiêu:** Chứng minh năng lực quản trị người dùng: Tìm kiếm, lọc theo trạng thái, khóa tài khoản Fan và đổi vai trò nhân viên.

#### Các bước bấm phím:
1. Tại Admin Menu: Nhập **`3`** (Fan accounts).
   * Nhập **`2`** (List): Xem toàn bộ danh sách Fan hiện có.
   * Nhập **`4`** (Search) -> Nhập Keyword: **`Binh`** -> Tìm thấy `Tran Thi Binh`.
   * Nhập **`5`** (Filter status) -> Nhập: **`INACTIVE`** -> Lọc ra các Fan bị khóa (`Do Thi Em`).
   * Nhập **`8`** (Deactivate) -> Fan ID: **`2`** -> Khóa tài khoản Fan 2.
   * Nhập **`7`** (Activate) -> Fan ID: **`2`** -> Mở khóa lại Fan 2.
   * Nhập **`0`** để quay lại.
2. Tại Admin Menu: Nhập **`4`** (Staff accounts).
   * Nhập **`2`** (List): Xem danh sách toàn bộ 5 nhân viên nội bộ.
   * Nhập **`4`** (Search) -> Keyword: **`Seller`** -> Tìm thấy 2 nhân viên bán vé.
   * Nhập **`5`** (Filter role/status) -> Role: **`SELLER`** / Status: **`ACTIVE`** -> Lọc chính xác.
   * **Điều chuyển chức vụ:** Nhập **`10`** (Change role) -> Staff ID: **`3`** -> New role: **`GATE_STAFF`**.
   * *Kết quả:* Nhân viên Seller Tran được điều chuyển sang làm `GATE_STAFF`.
   * Chuyển lại: Nhập **`10`** -> Staff ID: **`3`** -> New role: **`SELLER`**.
   * Nhập **`0`** để quay lại.

🗣️ **Lời thoại thuyết minh:**
> *"Tính năng quản trị người dùng cho phép Admin kiểm soát cả Cổ động viên và Nhân viên nội bộ. Admin có thể tìm kiếm nhanh theo từ khóa, lọc theo trạng thái, tạm ngưng hoặc kích hoạt tài khoản Fan. Đối với nhân viên, hệ thống cho phép luân chuyển chức vụ linh hoạt giữa các bộ phận bán vé, soát vé hay điều hành kỹ thuật thông qua phương thức changeRole."*

---

### ACT 7: DEMO GIÁM SÁT HỆ THỐNG & NHẬT KÝ KIỂM TOÁN (AUDIT & SUMMARY)
> **Mục tiêu:** Chứng minh hệ thống có Dashboard tổng quan thời gian thực và Audit Log truy vết an ninh chuẩn doanh nghiệp.

#### Các bước bấm phím:
1. Tại Admin Menu: Nhập **`5`** (Audit logs).
   * *Kết quả:* Hiển thị danh sách các bản ghi nhật ký kiểm toán:
     ```text
     === AUDIT LOGS ===
     3 | Actor #1 | ASSIGN_ROLE | Target: STAFF #3 | Result: SUCCESS | 2026-10-26T00:10
     2 | Actor #1 | OPEN_TICKET_SALES | Target: MATCH #1 | Result: SUCCESS | 2026-10-26T00:05
     1 | Actor #1 | INITIALIZE_DATASET | Target: SYSTEM #0 | Result: SUCCESS | 2026-10-26T00:00
     ```
2. Tại Admin Menu: Nhập **`6`** (System summary).
   * *Kết quả:* Hiển thị Dashboard thời gian thực:
     ```text
     === SYSTEM SUMMARY ===
     Total fans: 5
     Total staff: 5
     Total stadiums: 2
     Total matches: 3
     ```
3. Nhập **`0`** (Back) -> Nhập **`0`** (Logout) -> Nhập **`0`** (Exit) để kết thúc chương trình.
   * *Kết quả:* `Goodbye!`

🗣️ **Lời thoại thuyết minh:**
> *"Cuối cùng là hai tính năng quản trị cấp cao: Nhật ký kiểm toán Audit Log lưu vết minh bạch mọi hành động trọng yếu của quản trị viên (ai làm, can thiệp vào đâu, thành công hay thất bại lúc mấy giờ). Và Bảng tổng quan System Summary cho phép lãnh đạo nắm bắt ngay quy mô hiện tại của hệ thống bao gồm tổng số lượng Fan, Nhân sự, Sân vận động và Trận đấu. Tất cả đều vận hành trơn tru và tuân thủ 100% Class Diagram và chuẩn Clean Architecture."*

---

## PHẦN 4: TỔNG KẾT & ĐÁNH GIÁ ĐẠT ĐƯỢC

1. **Về mặt Kỹ thuật:**
   * Hoàn thành 100% các mục tiêu từ Stage 01 đến Stage 07.
   * Không sử dụng bất kỳ thư viện ngoài phức tạp nào, ứng dụng thuần Java Core (OOP, Collection, Streams, Exception Handling).
   * Toàn bộ 60 file mã nguồn biên dịch với **0 lỗi và 0 cảnh báo**.
   * Hệ số khớp UML Class Diagram đạt mức tuyệt đối: mọi class, method, field đều khớp chính xác với bản vẽ thiết kế `docs/Class_Diagrams/ClassDiagram.drawio`.
2. **Về mặt Lưu trữ (Persistence):**
   * Sử dụng kiến trúc `CsvRepository<T>` tổng quát kế thừa `BaseEntity`.
   * Toàn bộ dữ liệu được lưu trữ dạng file phẳng `.csv` độc lập, dễ dàng kiểm tra, sao lưu và minh chứng dữ liệu khi chấm điểm.
3. **Sẵn sàng cho giai đoạn tiếp theo (Stage 08+):**
   * Nền tảng tài khoản, sân bãi, trận đấu và bảng giá đã sẵn sàng làm đầu vào cho luồng: Đặt vé (Booking) -> Thanh toán (Payment) -> Giữ ghế lạc quan (Optimistic Locking) -> Mô phỏng bot (Simulation).
