# Báo Cáo Chi Tiết Mã Nguồn Đã Hoàn Thành (Chặng 1 - Chặng 7)

> **Dự án:** Stadium Ticket Booking Simulation (LAB211 - OOP Java)  
> **Sinh viên thực hiện:** Đoàn Ngọc Nhân - MSSV: QE210282  
> **Tiến độ mã nguồn hiện tại:** Đã hoàn thành 7/11 chặng (~65% toàn bộ hệ thống).  
> **Trạng thái biên dịch:** 60/60 file Java biên dịch thành công (`javac PASS 100%`), không cảnh báo, không lỗi runtime.  
> **Độ khớp thiết kế:** Khớp 100% với Class Diagram chuẩn `Controller_First_Full (2).drawio` (Trang 01, 02, 05, 06).

---

## 1. Tổng quan tiến độ triển khai

Hệ thống được phát triển theo mô hình phân lớp chuẩn **Controller-First MVC + Service + CSV Repository**, áp dụng chặt chẽ các nguyên lý lập trình hướng đối tượng (OOP) và kiểm thử độc lập theo từng chặng (Stage-by-Stage Verification).

| Nhóm Module / Chặng | Các package liên quan | Số lượng Class / Interface | Trạng thái kỹ thuật | Tỷ trọng |
|---|---|:---:|:---:|:---:|
| **Stage 01:** Common & CSV Foundation | `common.entity`, `common.exception`, `common.csv` | 4 file | Hoàn thành, độc lập kiểm thử | 10% |
| **Stage 02:** Stadium Physical Topology | `stadium.model`, `stadium.dto` | 5 file | Hoàn thành, đúng quan hệ OOP | 10% |
| **Stage 03:** Generic CSV Repositories | `stadium.repository`, `match.repository` | 6 file | Hoàn thành, đọc/ghi/parse CSV chuẩn | 10% |
| **Stage 04:** Match & Pricing Domain | `match.model`, `match.dto`, `match.service`, `match.controller` | 10 file | Hoàn thành, quản lý mở/đóng bán vé | 10% |
| **Stage 05:** Guest Browse & Seat Map View | `match.service`, `match.controller`, `match.view` | 3 file | Hoàn thành, render lưới ghế động Console | 10% |
| **Stage 06:** Authentication & User / Staff | `user.model`, `user.enums`, `user.dto`, `user.repository`, `user.service`, `user.controller`, `auth.*` | 13 file | Hoàn thành, phân quyền Role chặt chẽ | 10% |
| **Stage 07:** Administrator & Data Generator | `admin.model`, `admin.dto`, `admin.repository`, `admin.service`, `admin.controller`, `admin.view`, `admin.data` | 7 file | Hoàn thành, sinh >10.000 dòng CSV master | 5% |
| **Tầng điều phối ứng dụng (App Entry)** | `app.Main`, `app.MainController`, `app.MainView` | 3 file | Hoàn thành, Dependency Injection | - |
| **TỔNG CỘNG ĐÃ HOÀN THÀNH** | **src/ (toàn bộ 60 file mã nguồn)** | **60 file Java** | **javac PASS 100%** | **~65%** |

---

## 2. Chi tiết các module mã nguồn đã hoàn thành

### Module 1: Common Foundation & CSV Persistence Engine (`src/common`)
- **`BaseEntity`**: Abstract class định nghĩa định danh duy nhất `Long id`, cung cấp getter/setter, bảo đảm tính tái sử dụng và đóng gói cho toàn bộ Model trong hệ thống.
- **`AppException` & `ErrorCode`**: Hệ thống xử lý ngoại lệ nghiệp vụ tập trung. `AppException` kế thừa `RuntimeException`, mang theo mã lỗi nghiệp vụ enum (`ErrorCode`: `INVALID_INPUT`, `NOT_FOUND`, `UNAUTHORIZED`, `CONFLICT`, v.v.). Loại bỏ hoàn toàn việc in lỗi rải rác hay dùng HTTP status không phù hợp với Console.
- **`CsvRepository<T extends BaseEntity>`**: Generic Abstract Repository hiện thực toàn bộ các thao tác cơ bản: Đọc file CSV, phân tách cột, tự động tăng ID (`nextId`), tìm kiếm theo ID, lưu mới, cập nhật dòng và xóa theo ID.

### Module 2: Stadium Domain & Physical Topology (`src/stadium`)
- **Mô hình thực thể vật lý:**
  - `Stadium`: Quản lý thông tin sân vận động (tên sân, địa chỉ).
  - `Section`: Đại diện khán đài vật lý trực thuộc một sân vận động (`stadiumId`, tên khán đài).
  - `Seat`: Biểu diễn ghế vật lý trên khán đài (`sectionId`, hàng `rowLabel`, số ghế `seatNumber`, và cờ `active`).
- **Domain Methods:** Tuân thủ triệt để nguyên lý đóng gói (Encapsulation), cập nhật trạng thái thông qua các method có ngữ nghĩa nghiệp vụ (`updateDetails`, `rename`, `updateLocation`, `activate`, `deactivate`) thay vì các hàm setter tùy tiện.
- **DTOs:** `StadiumRequestDto`, `StadiumResponseDto` tách biệt dữ liệu nhập từ View và dữ liệu trả về cho người dùng.
- **Tầng Service & Controller:** `StadiumService` và `StadiumController` điều phối toàn bộ các nghiệp vụ tạo sân, quản lý khán đài, cấu hình ghế ngồi.

### Module 3: Match & MatchSeat Inventory (`src/match`)
- **`Match` & `SaleStatus`**: Quản lý lịch thi đấu bóng đá, trạng thái mở bán vé (`OPEN_FOR_SALE`, `STOPPED`, `CANCELLED`).
- **`MatchSeat` & `SeatStatus`**: Phân tách rành mạch giữa **ghế vật lý** (`Seat.active`) và **tồn kho ghế theo trận đấu** (`MatchSeat`: `AVAILABLE`, `LOCKED`, `BOOKED`). Một ghế vật lý có thể đã đặt ở trận A nhưng hoàn toàn sẵn sàng ở trận B.
- **`TicketPrice`**: Thiết lập chính sách giá vé linh hoạt theo từng cặp `(matchId, sectionId)`.
- **`MatchService` & `MatchController`**: Cung cấp nghiệp vụ tạo trận, cấu hình giá vé từng khán đài, tự động khởi tạo tồn kho `MatchSeat` khi mở bán vé.

### Module 4: Guest Browse & Interactive Seat Map View (`src/match`)
- **`BrowseService` & `BrowseController`**: Cho phép khách vãng lai và người hâm mộ tìm kiếm trận đấu, xem thông tin chi tiết trận, bảng giá vé của từng khán đài mà không bắt buộc đăng nhập.
- **`SeatMapView`**: Thành phần hiển thị sơ đồ ghế ngồi trực quan ngay trên giao diện Console:
  - Render ma trận ghế theo hàng và số ghế thực tế.
  - Hiển thị ký hiệu phân biệt rõ ràng: `[O]` Ghế còn trống, `[X]` Ghế đã được đặt, `[-]` Ghế bị khóa/bảo trì.
  - Tự động thống kê số ghế khả dụng theo từng khán đài theo thời gian thực.

### Module 5: Authentication & User/Staff Hierarchy (`src/user`, `src/auth`)
- **Mô hình kế thừa hướng đối tượng:**
  - `User`: Abstract class kế thừa `BaseEntity`, chứa thông tin căn bản (`username`, `password`, `fullName`, `email`, `phone`, `UserRole`, `UserStatus`).
  - `Fan`: Kế thừa `User`, bổ sung thông tin `fanCode`, điểm tích lũy `loyaltyPoints`.
  - `Staff`: Kế thừa `User`, bổ sung mã nhân viên `staffCode`, vai trò chuyên trách (`ADMIN`, `SELLER`, `GATE_STAFF`, `SUPPORT`).
- **Bảo mật & DTO:** Sử dụng `LoginRequestDto`, `RegisterFanRequestDto`, `UserResponseDto` để che giấu mật khẩu, ngăn rò rỉ dữ liệu nhạy cảm ra ngoài tầng View.
- **`AuthService` & `AuthController`**: Quản lý phiên làm việc, đăng nhập xác thực thông tin, cấp quyền truy cập theo Role.
- **`FanService` & `FanController`**: Hỗ trợ đăng ký tài khoản Fan mới và cập nhật hồ sơ cá nhân.

### Module 6: Administrator Management & Master Data Seed Generator (`src/admin`)
- **`AdminService` & `AdminController`**:
  - Quản trị toàn diện: Cấu hình hệ thống, quản lý tài khoản người dùng, phân quyền Staff (`AccountAdminController`).
  - Thống kê hệ thống: Tổng hợp số liệu tức thời thành `SystemSummaryDto` (số lượng tài khoản, sân vận động, trận đấu, tổng lượt đặt vé, v.v.).
  - Nhật ký quản trị: Ghi vết hành động qua `AuditLog` và `AuditLogRepository`.
- **`DataGenerator`**:
  - Bộ sinh dữ liệu thử nghiệm chuẩn tự động tạo hơn **10.000 dòng dữ liệu CSV** (Sân vận động, Khán đài, Ghế vật lý, Trận đấu, Bảng giá, Tài khoản mẫu Fan & Staff).
  - Phục vụ trực tiếp cho yêu cầu thử nghiệm tải và chuẩn bị dữ liệu cho chặng mô phỏng Concurrency Simulation.
  - Sử dụng mật khẩu khởi tạo chuẩn hóa, loại bỏ các kỹ thuật mã hóa phức tạp ngoài phạm vi đồ án console.
- **`AdminManagementView`**:
  - Giao diện quản trị Console hoàn toàn tuân thủ thiết kế Class Diagram trang 02 (`02_Stadium_Match_Admin`).
  - Loại bỏ hoàn toàn các hàm helper dư thừa, chỉ giữ đúng các hàm nhập liệu nghiệp vụ chuẩn (`readStadiumRequest`, `readMatchRequest`, `readAccountRequest`, `showSystemSummary`, `showAuditLogs`, v.v.).

### Module 7: Application Entry Point & Dependency Injection Wiring (`src/app`)
- **`app.Main`**: Điểm khởi động duy nhất của ứng dụng. Khởi tạo `Scanner`, khởi tạo tầng Repository, kết nối Service, truyền vào Controller và kích hoạt `MainController`.
- **`MainController` & `MainView`**: Điều phối luồng màn hình chính:
  - Khách vãng lai (Guest): Xem lịch đấu, tra cứu sơ đồ ghế.
  - Đăng ký tài khoản Fan mới.
  - Đăng nhập xác thực và phân luồng menu theo vai trò (Fan Dashboard, Administrator Management Menu).

---

## 3. Các phần còn lại của dự án (~35%)

Dự án hiện tuân thủ nguyên tắc triển khai theo từng chặng độc lập. Các module dưới đây đã có thiết kế UML hoàn chỉnh và sẽ được lần lượt cài đặt ở giai đoạn tiếp theo:

| Chặng | Tên Chặng | Trách nhiệm chính | Tỷ trọng |
|---|---|---|:---:|
| **Stage 08** | Booking Core, Payment & Ticket Issuance | - Thuật toán giữ chỗ tạm thời (`holdSeats`) và giải phóng ghế hết hạn (`timeout`).<br>- Quy trình đặt vé của Fan (tối đa 4 vé/lần), thanh toán giả lập (`PaymentService`).<br>- Khởi tạo vé điện tử (`Ticket`) và quy trình xử lý hoàn tiền (`RefundService`). | 20% |
| **Stage 09** | Counter Seller & Gate Validation Flow | - Giao diện quầy bán vé trực tiếp cho nhân viên Seller (thanh toán tiền mặt/offline).<br>- Giao diện soát vé tại cổng cho Gate Staff (quét mã vé, xác thực hợp lệ, ngăn chặn vé giả/vé đã dùng). | 8% |
| **Stage 10** | Concurrency Simulation & Stress Benchmark | - Thử nghiệm tranh chấp ghế đồng thời bằng đa luồng (`ExecutorService`, `CountDownLatch`).<br>- So sánh thực nghiệm 4 chiến lược đồng bộ hóa:<br>&nbsp;&nbsp;1. `NO_LOCK` (chứng minh race condition & double booking)<br>&nbsp;&nbsp;2. `SYNCHRONIZED` (đồng bộ mức JVM)<br>&nbsp;&nbsp;3. `FILE_LOCK` (khóa file CSV vật lý)<br>&nbsp;&nbsp;4. `OPTIMISTIC` (khóa lạc quan dựa trên version field).<br>- Đo lường thông số: Throughput, execution time, tỷ lệ double booking. | 5% |
| **Stage 11** | End-to-End Acceptance & Delivery | - Kiểm thử tính bền vững dữ liệu khi khởi động lại ứng dụng từ file CSV (Persistence Restart Test).<br>- Rà soát báo cáo, tổng hợp AI log, đóng gói sản phẩm hoàn chỉnh theo quy định. | 2% |

---

## 4. Cam kết chất lượng và chuẩn mực kỹ thuật

1. **Tuân thủ thiết kế (Strict UML Compliance):** Mọi class, method, tham số và quan hệ trong `src/` đều khớp 100% với Class Diagram chuẩn `Controller_First_Full (2).drawio`. Tuyệt đối không tự ý thêm các method tiện ích ngoài sơ đồ.
2. **Không Over-engineering:** Giữ mã nguồn trong sáng, dễ bảo trì, đúng chuẩn mực đồ án Java Console của học phần LAB211. Tránh xa các framework cồng kềnh, database phức tạp hoặc cơ chế hash mật khẩu không cần thiết.
3. **Trung thực học thuật (Academic Integrity):** Báo cáo đúng thực trạng 65% đã hoàn thành và chứng minh được bằng lệnh chạy `javac` thực tế. Không giả lập hoặc nói quá về các module chưa code (Stage 08 - 11).
