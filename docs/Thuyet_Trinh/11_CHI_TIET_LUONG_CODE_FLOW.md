# Hướng Dẫn Thuyết Trình Chi Tiết Luồng Code Từ MainController Đến Toàn Bộ Dự Án

> **Môn học:** LAB211 - Lập trình hướng đối tượng với Java (OOP)  
> **Dự án:** Stadium Ticket Booking Simulation  
> **Người thuyết trình:** Sinh viên Đoàn Ngọc Nhân - MSSV: QE210282  
> **Mục đích tài liệu:** Cung cấp tài liệu thuyết trình chuyên sâu, truy vết chi tiết từng dòng code theo luồng gọi hàm (Execution Call Stack): Đi từ điểm khởi động `Main.java` qua bộ điều phối `MainController`, rẽ nhánh vào các Controller chuyên trách, lặn sâu vào tầng Service, Repository, File CSV, xử lý kết quả trả về (Return) và tiếp tục hành trình thực thi.

---

## Lời Dẫn Mở Đầu Khi Bắt Đầu Thuyết Trình

> *"Kính thưa Thầy và các bạn!  
> Sau khi đã nắm được bức tranh tổng thể và kiến trúc phân lớp của dự án, bây giờ em xin phép được mở trực tiếp mã nguồn trong IDE và thuyết trình chi tiết **luồng chạy của chương trình từ trên xuống dưới**.  
> Em sẽ bắt đầu từ điểm khởi động `app.Main`, đi vào trái tim điều hướng là `MainController`, và lần lượt truy vết từng dòng lệnh: Khi người dùng bấm phím, hệ thống gọi hàm nào ở Controller, hàm đó chuyển tiếp dữ liệu xuống Service xử lý quy tắc nghiệp vụ ra sao, Repository đọc ghi file CSV thế nào, và khi hàm hoàn tất thì kết quả được trả ngược về điểm gọi như thế nào để hiển thị lên màn hình Console."*

---

## Mục 0: Khởi Động & Tiêm Phụ Thuộc (Dependency Injection) tại `app.Main`

Điểm bắt đầu của toàn bộ chương trình là file [Main.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/Main.java).

```java
// File: src/app/Main.java (Dòng 33 - 76)
public static void main(String[] args) {
    Path dataFolder = args.length > 0 ? Path.of(args[0]) : Path.of("data");
```

### Lời thuyết minh từng dòng:
1. **Dòng 34:** Xác định thư mục lưu trữ dữ liệu CSV. Mặc định hệ thống dùng thư mục `data/`.
2. **Dòng 35 - 46 (Khởi tạo Repository):**
   ```java
   MatchRepository matchRepository = new MatchRepository(dataFolder.resolve("matches.csv"));
   MatchSeatRepository matchSeatRepository = new MatchSeatRepository(dataFolder.resolve("match_seats.csv"));
   FanRepository fanRepository = new FanRepository(dataFolder.resolve("fans.csv"));
   StaffRepository staffRepository = new StaffRepository(dataFolder.resolve("staff.csv"));
   StadiumRepository stadiumRepository = new StadiumRepository(dataFolder.resolve("stadiums.csv"));
   SectionRepository sectionRepository = new SectionRepository(dataFolder.resolve("sections.csv"));
   SeatRepository seatRepository = new SeatRepository(dataFolder.resolve("seats.csv"));
   TicketPriceRepository ticketPriceRepository = new TicketPriceRepository(dataFolder.resolve("ticket_prices.csv"));
   AuditLogRepository auditLogRepository = new AuditLogRepository(dataFolder.resolve("audit_logs.csv"));
   ```
   > *"Ở đây, em áp dụng mẫu thiết kế **Dependency Injection bằng tay (Constructor Injection)**. Thay vì để các class tự tạo mới đối tượng repository bên trong, `Main` chịu trách nhiệm khởi tạo 9 tệp repository gắn liền với 9 file CSV tương ứng."*
3. **Dòng 48 - 64 (Khởi tạo Service và Controller chuyên trách):**
   - `BrowseService` nhận `matchRepository` và `matchSeatRepository` $\rightarrow$ truyền vào `BrowseController`.
   - `FanService` và `AuthService` nhận `fanRepository` và `staffRepository` $\rightarrow$ truyền vào `FanController` và `AuthController`.
   - `StadiumService` nhận 3 repo (sân, khán đài, ghế) $\rightarrow$ truyền vào `StadiumController`.
   - `MatchService` nhận repo trận, giá vé, tồn kho ghế $\rightarrow$ truyền vào `MatchController`.
   - `AccountAdminService` nhận repo fan và staff $\rightarrow$ truyền vào `AccountAdminController`.
   - `AdminService` nhận `DataGenerator` cùng 5 repository để phục vụ việc thống kê và sinh dữ liệu lớn.
4. **Dòng 66 - 74 (Ráp nối View và Kích hoạt MainController):**
   ```java
   try (Scanner scanner = new Scanner(System.in)) {
       MainView mainView = new MainView(scanner);
       SeatMapView seatMapView = new SeatMapView();
       AdminController adminController = new AdminController(adminService,
               new AdminManagementView(scanner), stadiumController, matchController,
               accountAdminController);
       MainController mainController = new MainController(mainView, seatMapView,
               browseController, authController, fanController, adminController);
       mainController.start(); // <-- ĐIỂM CHUYỂN LUỒNG VÀO MAINCONTROLLER
   }
   ```
   > *"Tại dòng 74, lệnh `mainController.start()` được kích hoạt, chính thức chuyển giao quyền điều khiển hệ thống cho `MainController`."*

---

## Mục 1: Trái Tim Điều Phối - `MainController.start()`

Bây giờ luồng thực thi nhảy vào [MainController.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/MainController.java), method `start()`:

```java
// File: src/app/MainController.java (Dòng 35 - 67)
public void start() {
    boolean running = true;
    while (running) {
        try {
            switch (mainView.showMainMenu()) {
                case 1:
                    runGuestMenu();
                    break;
                case 2:
                    handleFanLogin();
                    break;
                case 3:
                    handleStaffLogin();
                    break;
                case 4:
                    handleFanRegistration();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    mainView.displayMessage("Invalid choice.");
            }
        } catch (AppException e) {
            mainView.displayMessage(e.getMessage());
        } catch (NumberFormatException e) {
            mainView.displayMessage("Invalid number.");
        } catch (NoSuchElementException e) {
            running = false;
        }
    }
    mainView.displayMessage("Goodbye!");
}
```

### Lời thuyết minh cơ chế:
> *"Tại method `start()`, em duy trì một vòng lặp `while (running)`.  
> Dòng 39 gọi `mainView.showMainMenu()`. Hàm này in ra màn hình Console 5 sự lựa chọn:
> - `1. Browse as Guest` (Khách xem lịch và sơ đồ ghế)
> - `2. Login as Fan` (Người hâm mộ đăng nhập)
> - `3. Login as Staff` (Nhân viên / Quản trị viên đăng nhập)
> - `4. Register Fan Account` (Đăng ký tài khoản mới)
> - `0. Exit` (Thoát)
> 
> Toàn bộ khối lệnh được bọc trong `try-catch` để bắt `AppException`. Bất kỳ lỗi nghiệp vụ nào bắn ra từ tầng dưới (như không tìm thấy dữ liệu, sai mật khẩu) đều được `MainController` đón lấy tại dòng 58 và chuyển cho View in thông báo một cách an toàn mà không làm sập ứng dụng."*

Bây giờ chúng ta sẽ lần lượt đi vào từng nhánh `case`.

---

## Mục 2: Nhánh 1 - Luồng Khách Vãng Lai (`case 1: runGuestMenu()`)

Khi người dùng nhập `1`, dòng 41 gọi `runGuestMenu()`. Luồng thực thi nhảy xuống dòng 69:

```java
// File: src/app/MainController.java (Dòng 69 - 103)
public void runGuestMenu() {
    boolean browsing = true;
    while (browsing) {
        try {
            switch (mainView.showGuestMenu()) {
                case 1:
                    mainView.displayMatches(browseController.searchMatches(mainView.readKeyword()));
                    break;
                case 2:
                    mainView.displayMatches(browseController.filterMatches(
                            mainView.readStadiumId(), mainView.readStartDate()));
                    break;
                case 3:
                    mainView.displayMatchDetails(browseController.viewMatchDetails(
                            mainView.readMatchId()));
                    break;
                case 4:
                    seatMapView.displaySeatMap(browseController.getSeatMap(
                            mainView.readMatchId()));
                    break;
                case 0:
                    browsing = false;
                    break;
                default:
                    mainView.displayMessage("Invalid choice.");
            }
        } catch (AppException e) { ... }
    }
}
```

### Truy vết chi tiết từng thao tác của Khách:

#### 2.1. Thao tác Tìm kiếm Trận đấu (`case 1`):
1. **Dòng 75:** `mainView.readKeyword()` yêu cầu người dùng nhập từ khóa tìm kiếm (ví dụ: `"Hà Nội"`).
2. Dữ liệu String được truyền làm tham số vào `browseController.searchMatches(keyword)`.
3. **Luồng nhảy vào `BrowseController` $\rightarrow$ nhảy tiếp vào `BrowseService.searchMatches(keyword)` ([BrowseService.java: dòng 25-41](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/service/BrowseService.java#L25-L41)):**
   - Dòng 26: Validate từ khóa không được rỗng, nếu rỗng ném `new AppException(ErrorCode.INVALID_INPUT, "Keyword is required")`.
   - Dòng 31: Gọi `matchRepository.findAll()` để nạp toàn bộ danh sách trận từ file `matches.csv`.
   - Dòng 32-34: So sánh xem `homeTeam` hoặc `awayTeam` có chứa từ khóa hay không (không phân biệt hoa thường).
   - Dòng 35-38: Nếu khớp, đóng gói thành đối tượng `MatchResponseDto` và thêm vào danh sách `result`.
   - Dòng 40: `return result;` trả về danh sách DTO.
4. **Quay lại `MainController` dòng 75:** Danh sách `List<MatchResponseDto>` được nạp thẳng vào `mainView.displayMatches(...)` để in bảng kết quả lên console.
5. Vòng lặp `while (browsing)` tiếp tục.

#### 2.2. Thao tác Xem Sơ đồ ghế động (`case 4` - Điểm nhấn kỹ thuật):
1. **Dòng 86:** `mainView.readMatchId()` nhận ID trận đấu từ người dùng.
2. Gọi `browseController.getSeatMap(matchId)`.
3. **Nhảy vào `BrowseService.getSeatMap(matchId)` ([BrowseService.java: dòng 76-90](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/service/BrowseService.java#L76-L90)):**
   - Kiểm tra trận đấu có tồn tại bằng `matchRepository.findById(matchId)`. Nếu không có, ném `ErrorCode.NOT_FOUND`.
   - Gọi `matchSeatRepository.findByMatchId(matchId)` để nạp toàn bộ ghế của trận này từ file `match_seats.csv`.
   - Chuyển đổi thành `List<MatchSeatResponseDto>` và `return`.
4. **Quay lại `MainController` dòng 86:** Truyền danh sách ghế vào `seatMapView.displaySeatMap(...)`.
5. **Nhảy vào `SeatMapView.displaySeatMap()` ([SeatMapView.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/view/SeatMapView.java)):**
   - Thuật toán sắp xếp ghế theo thứ tự hàng (`rowLabel`) và số thứ tự ghế (`seatNumber`).
   - Duyệt từng phần tử và render ra màn hình lưới ASCII 2D:
     - Ghế `AVAILABLE` in ký hiệu xanh `[O]`.
     - Ghế `BOOKED` in ký hiệu đỏ `[X]`.
     - Ghế `LOCKED` in ký hiệu xám `[-]`.
   - In bảng tổng kết số ghế còn trống của từng khán đài.
6. **Kết thúc `displaySeatMap()`:** Quay lại dòng 88 của `MainController`.
7. **Thoát menu khách (`case 0`):** Đặt `browsing = false`, thoát khỏi vòng lặp `runGuestMenu()`, quay về dòng 42 của `MainController.start()`.

---

## Mục 3: Nhánh 4 - Luồng Đăng Ký Tài Khoản Fan (`case 4: handleFanRegistration()`)

Khi ở menu chính người dùng bấm `4`, luồng nhảy đến dòng 125 của [MainController.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/MainController.java):

```java
// File: src/app/MainController.java (Dòng 125 - 127)
public void handleFanRegistration() {
    mainView.displayUser(fanController.register(mainView.readRegisterFanRequest()));
}
```

### Truy vết từng dòng:
1. `mainView.readRegisterFanRequest()` hiển thị form nhập liệu console: Họ tên, Email, Mật khẩu, Số điện thoại. Đóng gói thành `RegisterFanRequestDto`.
2. Gọi `fanController.register(requestDto)`.
3. **Nhảy vào `FanService.register(requestDto)` ([FanService.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/user/service/FanService.java)):**
   - Kiểm tra định dạng email và mật khẩu hợp lệ.
   - Kiểm tra tính duy nhất: Gọi `fanRepository.findByEmail(email)` và `staffRepository.findByEmail(email)`. Nếu email đã tồn tại, ném `AppException(ErrorCode.CONFLICT, "Email already exists")`.
   - Khởi tạo đối tượng `Fan` mới (kế thừa `User`), sinh mã `fanCode`, cấp điểm tích lũy ban đầu là `0`, trạng thái `ACTIVE`, vai trò `UserRole.FAN`.
   - Gọi `fanRepository.save(fan)`:
     - **Nhảy vào `CsvRepository.save(entity)` ([CsvRepository.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/common/csv/CsvRepository.java)):**
       - Tự động sinh ID mới (`nextId`).
       - Chuyển đổi đối tượng `Fan` thành chuỗi định dạng CSV (`id,username,password,fullName,email,phone,role,status,fanCode,loyaltyPoints`).
       - Mở file `fans.csv` ở chế độ ghi tiếp (append) và ghi dòng mới xuống đĩa.
     - Kết thúc ghi file, trả về đối tượng `Fan` đã có ID.
   - Chuyển đổi sang `UserResponseDto` (loại bỏ trường mật khẩu để bảo mật).
   - `return` về `FanController`.
4. **Quay lại `MainController`:** `mainView.displayUser(...)` nhận `UserResponseDto` và in thông báo chúc mừng đăng ký thành công lên màn hình Console.
5. Luồng chạy xong, quay lại vòng lặp menu chính `start()`.

---

## Mục 4: Nhánh 2 & 3 - Đăng Nhập & Phân Quyền (`handleFanLogin()` & `handleStaffLogin()`)

Tại menu chính:
- Người dùng bấm `2`: gọi `handleFanLogin()` $\rightarrow$ gọi `loginForRole(true)`.
- Người dùng bấm `3`: gọi `handleStaffLogin()` $\rightarrow$ gọi `loginForRole(false)`.

Luồng nhảy vào method `handleFanLogin()` hoặc `handleStaffLogin()` của [MainController.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/MainController.java):

```java
// File: src/app/MainController.java
public void handleFanLogin() {
    UserResponseDto user = authController.login(mainView.readLoginRequest());
    if (user.getRole() != UserRole.FAN) {
        authController.logout();
        mainView.displayMessage("This account belongs to the other login menu.");
        return;
    }
    mainView.displayUser(user);

    while (authController.getCurrentUser() != null) {
        int choice = mainView.showFanMenu();
        if (choice == 1) {
            mainView.displayUser(authController.getCurrentUser());
        } else if (choice == 0) {
            authController.logout();
            mainView.displayMessage("Logged out.");
            break;
        } else {
            mainView.displayMessage("Invalid choice.");
        }
    }
}
```

### Truy vết quá trình xác thực:
1. `mainView.readLoginRequest()` đọc Email và Mật khẩu $\rightarrow$ `LoginRequestDto`.
2. Gọi `authController.login(...)` $\rightarrow$ gọi tiếp `AuthService.login(request)`.
3. **Nhảy vào `AuthService.login(request)` ([AuthService.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/auth/service/AuthService.java)):**
   - Tìm kiếm tài khoản trong `fanRepository.findByEmail(email)` hoặc `staffRepository.findByEmail(email)`.
   - Nếu không tìm thấy hoặc mật khẩu không khớp, ném ngay `AppException(ErrorCode.INVALID_INPUT, "Invalid email or password")`.
   - Kiểm tra tài khoản có bị khóa không: `found.getStatus() != UserStatus.ACTIVE`.
   - **Lưu phiên đăng nhập:** `this.currentUser = found;`.
   - Đóng gói thông tin trả về `UserResponseDto` (không chứa mật khẩu).
4. **Quay lại `MainController`:**
   - Kiểm tra tính phân tách quyền: Nếu là Fan mà lại đăng nhập ở menu Staff (hoặc ngược lại) thì lập tức gọi `authController.logout()`, in cảnh báo và kết thúc hàm.
   - Nếu đúng vai trò: In thông tin người dùng và mở menu tương ứng trực tiếp.

---

## Mục 5: Phiên Làm Việc Trực Tiếp Của Người Dùng Sau Đăng Nhập

Sau khi xác thực thành công, luồng xử lý chạy vòng lặp menu ngay trong hàm tương ứng:

```java
// File: src/app/MainController.java
public void handleStaffLogin() {
    UserResponseDto user = authController.login(mainView.readLoginRequest());
    if (user.getRole() == UserRole.FAN) {
        authController.logout();
        mainView.displayMessage("This account belongs to the other login menu.");
        return;
    }
    mainView.displayUser(user);

    switch (user.getRole()) {
        case ADMIN:
            while (authController.getCurrentUser() != null) {
                try {
                    switch (mainView.showAdminMenu()) {
                        case 1:
                            mainView.displayUser(authController.getCurrentUser());
                            break;
                        case 2:
                            adminController.runAdminMenu(); // <-- ĐIỂM CHUYỂN LUỒNG VÀO ADMIN
                            break;
                        case 0:
                            authController.logout();
                            mainView.displayMessage("Logged out.");
                            break;
                        default:
                            mainView.displayMessage("Invalid choice.");
                    }
                } catch (AppException e) {
                    mainView.displayMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    mainView.displayMessage("Invalid number.");
                }
            }
            break;

        case SELLER:
            // Vòng lặp cho Seller...
            break;
        case GATE_STAFF:
            // Vòng lặp cho Gate Staff...
            break;
        case SIMULATOR_OPERATOR:
            // Vòng lặp cho Simulator...
            break;
        default:
            authController.logout();
            mainView.displayMessage("Access denied for role: " + user.getRole());
            break;
    }
}
```

### Lời thuyết minh:
> *"Tại đây, chúng ta dùng một `switch (user.getRole())` tổng quan để phân nhánh riêng cho từng Role. Nhờ vậy, mỗi Role sở hữu vòng lặp menu độc lập, không bị chồng chéo điều kiện `if/else`:
> - Với `case ADMIN`: Hiển thị menu `mainView.showAdminMenu()` gồm các lựa chọn:
>   - `1`: Xem thông tin cá nhân.
>   - `2`: **Vào Bảng Điều Khiển Quản Trị Hệ Thống (`adminController.runAdminMenu()`)**.
>   - `0`: Đăng xuất (Logout).
> - Với các vai trò `SELLER`, `GATE_STAFF`, `SIMULATOR_OPERATOR`: Mỗi vai trò có riêng menu tương ứng, sẵn sàng mở rộng nghiệp vụ bán vé, soát vé và mô phỏng ở các chặng sau.
> 
> Khi Admin bấm `2`, chương trình kích hoạt `adminController.runAdminMenu()`. Toàn bộ luồng điều khiển bây giờ được trao cho `AdminController`!"*

---

## Mục 6: Phân Hệ Quản Trị - `AdminController.runAdminMenu()`

Bây giờ luồng thực thi chuyển sang file [AdminController.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/controller/AdminController.java), method `runAdminMenu()`:

```java
// File: src/admin/controller/AdminController.java (Dòng 46 - 84)
public void runAdminMenu() {
    while (true) {
        try {
            switch (this.adminManagementView.showAdminMenu()) {
                case 1:
                    runStadiumMenu();
                    break;
                case 2:
                    runMatchMenu();
                    break;
                case 3:
                    runFanManagementMenu();
                    break;
                case 4:
                    runStaffManagementMenu();
                    break;
                case 5:
                    this.adminManagementView.showAuditLogs(this.getAuditLogs());
                    break;
                case 6:
                    int count = this.adminManagementView.readInt("Seat count (1..100000): ");
                    this.generateCsvDataset(count);
                    this.adminManagementView.displayResult("Sample dataset generated.");
                    break;
                case 7:
                    this.adminManagementView.showSystemSummary(this.getSystemSummary());
                    break;
                case 0:
                    return; // <-- THOÁT VỀ MAINCONTROLLER
                default:
                    this.adminManagementView.displayResult("Invalid choice.");
            }
        } catch (AppException e) { ... }
    }
}
```

### Truy vết 4 tính năng quản trị cốt lõi:

#### 6.1. Sinh bộ dữ liệu mẫu >10.000 dòng (`case 6`):
1. **Dòng 66:** `adminManagementView.readInt(...)` nhận số lượng ghế cần sinh (ví dụ: `10000`).
2. **Dòng 67:** Gọi `this.generateCsvDataset(count)`.
3. **Nhảy vào `AdminService.generateCsvDataset(recordCount)` ([AdminService.java: dòng 32-34](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/service/AdminService.java#L32-L34)):**
   - Chuyển tiếp lời gọi sang `this.dataGenerator.generateAll(recordCount)`.
4. **Nhảy vào `DataGenerator.generateAll(recordCount)` ([DataGenerator.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/data/DataGenerator.java)):**
   - Sinh danh sách Sân vận động mẫu (Mỹ Đình, Hàng Đẫy, Thống Nhất...).
   - Sinh các Khán đài (Khán đài A, B, C, D) và tự động tính toán sinh hàng chục nghìn chiếc ghế `Seat` vật lý.
   - Khởi tạo lịch thi đấu các trận bóng đá và bảng giá vé tương ứng theo khán đài.
   - Sinh danh sách tài khoản mẫu Fan và Staff với mật khẩu chuẩn hóa `"password" + id`.
   - Ghi đồng loạt toàn bộ dữ liệu xuống các tệp CSV trong thư mục `data/`.
5. **Kết thúc `generateAll`:** Trả quyền điều khiển về `AdminService` $\rightarrow$ về `AdminController` dòng 68.
6. `adminManagementView.displayResult("Sample dataset generated.")` in thông báo thành công ra màn hình.

#### 6.2. Thống kê hệ thống thời gian thực (`case 7`):
1. **Dòng 71:** Gọi `this.getSystemSummary()`.
2. **Nhảy vào `AdminService.getSystemSummary()` ([AdminService.java: dòng 36-45](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/service/AdminService.java#L36-L45)):**
   ```java
   return new SystemSummaryDto(
           this.fanRepository.findAll().size(),
           this.staffRepository.findAll().size(),
           this.stadiumRepository.findAll().size(),
           this.matchRepository.findAll().size(),
           0, // Tạm thời 0 bookings (chờ Chặng 8)
           0  // Tạm thời 0 tickets (chờ Chặng 8)
   );
   ```
   - Đọc dữ liệu thực tế từ 4 file CSV tương ứng và đếm tổng số dòng.
   - Đóng gói dữ liệu vào đối tượng `SystemSummaryDto`.
3. **Quay lại dòng 71:** `adminManagementView.showSystemSummary(dto)` in bảng dashboard tổng quan hệ thống ra màn hình Console.

#### 6.3. Quản lý Sân vận động, Trận đấu và Tài khoản (`case 1, 2, 3, 4`):
- Khi chọn `1: runStadiumMenu()`: Mở menu con quản lý sân. Admin chọn thêm sân mới $\rightarrow$ `AdminController` nhận `StadiumRequestDto` từ View $\rightarrow$ chuyển cho `StadiumController.createStadium()` $\rightarrow$ `StadiumService` validate và lưu qua `StadiumRepository` $\rightarrow$ kết quả hiển thị qua View.
- Khi chọn `2: runMatchMenu()`: Cho phép tạo trận, định giá vé khán đài qua `MatchController.createTicketPrice()`, và mở bán vé qua `MatchController.openTicketSales(matchId)`.
- Khi chọn `4: runStaffManagementMenu()`: Cho phép Admin phân quyền nhân viên (`assignRole`), đổi quyền (`changeRole`) hoặc thu hồi quyền (`revokeRole`) thông qua `AccountAdminController`.

#### 6.4. Thoát Menu Quản Trị (`case 0`):
- Khi Admin nhập `0`: Lệnh `return;` được thực thi.
- **Vòng lặp quản trị kết thúc:** Luồng thực thi quay trở lại menu chính của Staff trong `MainController.handleStaffLogin()`!

---

## Mục 7: Đăng Xuất & Thoát Ứng Dụng

1. Sau khi thoát khỏi menu Admin, màn hình quay lại menu Staff trong `handleStaffLogin()` tại [MainController.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/MainController.java).
2. Người dùng nhập `0: Logout`:
   - Gọi `authController.logout()`.
   - `AuthService.logout()` gán `this.currentUser = null`.
   - Điều kiện vòng lặp `while (authController.getCurrentUser() != null)` trở thành `false`!
   - Thoát khỏi vòng lặp đăng nhập, quay về vòng lặp chính của `start()`.
3. Tại Menu chính của ứng dụng, người dùng nhập `0: Exit`:
   - Dòng 53: Gán cờ `running = false`.
   - Vòng lặp `while (running)` kết thúc.
   - Dòng 66: In lời chào `mainView.displayMessage("Goodbye!")`.
   - Luồng thoát khỏi method `start()`, quay về dòng 75 của [Main.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/app/Main.java).
   - Khối `try-with-resources` tự động đóng `Scanner` (`scanner.close()`).
   - Hàm `main` kết thúc mã trả về `0`, ứng dụng dừng an toàn.

---

## Lời Kết Thuyết Trình

> *"Thưa Thầy! Như vậy qua việc truy vết chi tiết từng dòng code từ `Main.java` qua `MainController` và các tầng chức năng, chúng ta có thể thấy rõ 3 ưu điểm vượt trội trong kiến trúc của nhóm:  
> 1. **Tính đơn hướng và phân tách trách nhiệm hoàn hảo:** Không hề có hiện tượng View gọi ngược Controller, hay Controller chọc thẳng vào file CSV. Mọi luồng đi qua đúng trình tự `View -> Controller -> Service -> Repository`.  
> 2. **Cơ chế Call-Stack sạch sẽ:** Khi gọi một chức năng nghiệp vụ, luồng đi sâu xuống các tầng dưới xử lý xong rồi `return` trả kết quả ngược lên tuần tự, giúp tài nguyên bộ nhớ được giải phóng ngay lập tức.  
> 3. **Khả năng mở rộng tối đa:** Với kiến trúc này, khi bước sang Chặng 08 (Booking) và Chặng 10 (Concurrency Simulation), chúng em chỉ việc gắn thêm `BookingController` và `SimulationController` vào `MainController` mà không cần phải đập đi xây lại bất kỳ một dòng code nền tảng nào đã có.
> 
> Em xin chân thành cảm ơn Thầy đã lắng nghe phần phân tích luồng code của em!"*
