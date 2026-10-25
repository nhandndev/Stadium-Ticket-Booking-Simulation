# Kịch Bản Báo Cáo Tiến Độ Dự Án (Hoàn Thành 65% - Chặng 1 Đến Chặng 7)

> **Môn học:** LAB211 - Lập trình hướng đối tượng với Java (OOP)  
> **Đề tài:** Stadium Ticket Booking Simulation  
> **Người trình bày:** Sinh viên Đoàn Ngọc Nhân - MSSV: QE210282  
> **Mục tiêu:** Báo cáo cột mốc hoàn thành mã nguồn chặng 1 - 7 (~65%), chứng minh tính đúng đắn kiến trúc phân lớp Controller-First, trình diễn demo thực tế và trình bày kế hoạch hoàn thiện 35% còn lại.

---

## Phần 1: Chào hỏi, Giới thiệu & Định vị dự án (Khoảng 1 - 2 phút)

**Lời thuyết trình:**

> "Kính chào Thầy và các bạn! Em là Đoàn Ngọc Nhân, sinh viên mã số QE210282.  
> Hôm nay, em xin phép đại diện nhóm báo cáo tiến độ triển khai mã nguồn của dự án **Stadium Ticket Booking Simulation** trong học phần LAB211.
> 
> Về bản chất, đây là một ứng dụng Java thuần chạy trên giao diện Console. Dự án không chỉ giải quyết bài toán nghiệp vụ quản lý sân bóng, lịch thi đấu và bán vé cho người hâm mộ, mà trọng tâm kỹ thuật cốt lõi là **thực nghiệm và đo lường xung đột tài nguyên đồng thời (Concurrency Simulation)** — cụ thể là ngăn chặn triệt để hiện tượng đặt trùng vé (**Double Booking**) khi hàng nghìn người cùng lúc tranh chấp một số lượng ghế giới hạn trên nền tảng lưu trữ file CSV.
> 
> Hôm nay, em rất vui mừng báo cáo rằng toàn bộ cấu trúc nền móng, hệ thống lưu trữ dữ liệu CSV, toàn bộ nghiệp vụ quản lý Sân vận động, Trận đấu, Sơ đồ ghế động, Phân quyền người dùng và Phân hệ Quản trị viên (Administrator) thuộc **Chặng 1 đến Chặng 7 đã được hoàn thiện 100%**, tương đương khoảng **65% khối lượng toàn bộ dự án**.  
> 
> Toàn bộ 60 file mã nguồn trong thư mục `src/` hiện đã biên dịch hoàn toàn sạch sẽ bằng `javac`, không hề có lỗi hay warning, và tuân thủ nghiêm ngặt theo đúng Class Diagram chuẩn `ClassDiagram.drawio` mà Thầy đã duyệt."

---

## Phần 2: Báo cáo Kiến trúc Tổng thể & Phương pháp triển khai (Khoảng 2 phút)

**Lời thuyết trình:**

> "Trước khi đi vào từng module cụ thể, em xin điểm qua tư duy kiến trúc mà nhóm áp dụng.  
> Để bảo đảm tính mở rộng và dễ bảo trì, hệ thống tuân thủ chặt chẽ mô hình **Controller-First Phân lớp (Layered Architecture)** với luồng phụ thuộc một chiều:
> 
> $$\text{Main} \longrightarrow \text{MainController} \longrightarrow \text{Controller} \longrightarrow \text{Service} \longrightarrow \text{Repository} \longrightarrow \text{CSV Files}$$
> 
> Trong mô hình này:
> 1. **Tầng View (`*View`):** Hoàn toàn là giao diện thuần túy. View chỉ có nhiệm vụ in thông tin ra Console và nhận input từ `Scanner`. View **tuyệt đối không chứa logic nghiệp vụ** và không gọi trực tiếp xuống Repository hay đọc file.
> 2. **Tầng Controller (`*Controller`):** Tiếp nhận yêu cầu từ View, đóng gói dữ liệu vào DTO, gọi xuống tầng Service và điều phối màn hình tiếp theo.
> 3. **Tầng Service (`*Service`):** Trái tim của hệ thống. Toàn bộ quy tắc nghiệp vụ (Business Rules), kiểm tra tính hợp lệ của trạng thái ghế, quản lý mở bán vé đều tập trung tại đây. Điều này bảo đảm khi bước sang chặng mô phỏng Concurrency sau này, bộ Simulator có thể tái sử dụng trực tiếp Service mà không cần thông qua giao diện Console.
> 4. **Tầng Repository (`CsvRepository<T>`):** Chịu trách nhiệm trừu tượng hóa toàn bộ việc đọc, ghi, parse và quản lý ID tự tăng trên các tệp tin CSV. Controller và Service hoàn toàn làm việc với đối tượng Java, không phải thao tác trực tiếp với File I/O.
> 
> Phương pháp nhóm áp dụng là **Stage-by-Stage Verification**: Làm đến đâu, kiểm thử độc lập đến đó. Mỗi chặng đều có thể compile và chạy độc lập, ngăn chặn việc phình to lỗi khi ghép nối."

---

## Phần 3: Trình bày chi tiết từng Module đã hoàn thành (Khoảng 4 - 5 phút)

**Lời thuyết trình:**

> "Thưa Thầy, sau đây em xin trình bày cụ thể vai trò của 6 module chính đã hoàn thành trong 65% này:

### 1. Module Common & CSV Foundation (`src/common`)
> Đây là xương sống của toàn dự án:
> - `BaseEntity`: Cung cấp định danh ID duy nhất cho toàn bộ các đối tượng dữ liệu.
> - `AppException` & `ErrorCode`: Cơ chế kiểm soát ngoại lệ tập trung. Chúng em sử dụng Runtime Exception với các mã lỗi nghiệp vụ rõ ràng (`NOT_FOUND`, `INVALID_INPUT`, `CONFLICT`...) thay vì dùng HTTP status không phù hợp với Console.
> - `CsvRepository<T>`: Bộ khung Generic Repository có khả năng tự động ánh xạ dữ liệu hai chiều giữa dòng CSV và Object Java, tự động cấp phát ID mới an toàn.

### 2. Module Quản lý Sân vận động (`src/stadium`)
> Quản lý phân cấp không gian vật lý theo quan hệ 1-N chuẩn OOP:  
> **Một Sân vận động (`Stadium`) có nhiều Khán đài (`Section`), một Khán đài có nhiều Ghế vật lý (`Seat`)**.  
> Ghế vật lý có trạng thái `active` biểu thị ghế còn sử dụng được hay đang hỏng. Chúng em tuân thủ chặt chẽ nguyên lý bao gói (Encapsulation): Trạng thái chỉ được thay đổi qua domain methods như `activate()`, `deactivate()`, `updateLocation()` chứ không dùng setter tùy tiện.

### 3. Module Trận đấu & Tồn kho ghế theo trận (`src/match`)
> Điểm sáng kỹ thuật ở module này là sự phân biệt rạch ròi giữa **Ghế vật lý (`Seat`)** và **Tồn kho ghế theo trận đấu (`MatchSeat`)**:
> - Ghế vật lý chỉ phản ánh cơ sở hạ tầng của sân.
> - Nhưng `MatchSeat` phản ánh trạng thái kinh doanh của ghế đó trong một trận bóng cụ thể (`AVAILABLE`, `LOCKED`, `BOOKED`). Một ghế có thể đã bán ở trận chung kết, nhưng hoàn toàn khả dụng ở trận đấu khác.
> - Đi kèm với đó là `TicketPrice` cho phép định cấu hình giá vé linh hoạt theo từng khán đài của từng trận đấu.

### 4. Module Khách vãng lai & Sơ đồ ghế trực quan (`src/match/view/SeatMapView.java`)
> Chúng em đã hiện thực xong tính năng duyệt xem trận đấu cho khách vãng lai (`BrowseController`, `BrowseService`).  
> Đặc biệt, class `SeatMapView` có khả năng dựng ma trận ghế ngồi trực quan ngay trên Console dạng ASCII:
> - Ký hiệu `[O]` thể hiện ghế trống còn mua được.
> - Ký hiệu `[X]` thể hiện ghế đã có người đặt.
> - Ký hiệu `[-]` thể hiện ghế đang bảo trì hoặc tạm khóa.  
> Tính năng này giúp người dùng hình dung toàn bộ khán đài một cách trực quan trước khi chọn vị trí.

### 5. Module Xác thực & Phân quyền Tài khoản (`src/user`, `src/auth`)
> Áp dụng triệt để kế thừa hướng đối tượng:
> - Lớp cha `User` chứa các thuộc tính căn bản và trạng thái tài khoản.
> - `Fan` kế thừa `User`, bổ sung mã người hâm mộ và điểm tích lũy.
> - `Staff` kế thừa `User`, mở rộng với mã nhân viên và các Role chuyên biệt (`ADMIN`, `SELLER`, `GATE_STAFF`, `SUPPORT`).
> - Tầng Service (`AuthService`, `FanService`) bảo vệ dữ liệu bằng cách sử dụng các DTO (`LoginRequestDto`, `UserResponseDto`), bảo đảm mật khẩu không bao giờ bị trả ngược ra tầng View.

### 6. Module Quản trị viên & Bộ sinh dữ liệu thử nghiệm (`src/admin`)
> Đây là module trọng tâm của chặng 7:
> - `AdminController` & `AdminService`: Cho phép Admin theo dõi tổng quan hệ thống thông qua `SystemSummaryDto` (thống kê tổng số tài khoản, số sân, số trận đấu, số ghế), truy xuất nhật ký thao tác `AuditLog`, quản lý phân quyền nhân viên và mở/đóng bán vé.
> - `AdminManagementView`: Được tái cấu trúc sạch sẽ, tuân thủ 100% Class Diagram, loại bỏ toàn bộ các hàm helper thừa.
> - Đặc biệt, `DataGenerator`: Đã hoàn thiện thuật toán tự động sinh ra **hơn 10.000 dòng dữ liệu CSV** (gồm sân, khán đài, hàng chục nghìn ghế, lịch thi đấu, bảng giá và tài khoản mẫu). Bộ dữ liệu lớn này đáp ứng trực tiếp yêu cầu đề tài và là nguyên liệu sẵn sàng cho thí nghiệm Concurrency sắp tới."

---

## Phần 4: Kịch bản Thao diễn Demo Thực tế (Live Demo - Khoảng 2 - 3 phút)

**Lời thuyết trình và Thao tác:**

> "Để chứng minh tính xác thực, em xin phép mở Terminal và thực hiện demo trực tiếp:
> 
> **Bước 1: Biên dịch sạch mã nguồn**  
> Em chạy lệnh:
> ```bash
> javac -encoding UTF-8 -d out $(find src -name "*.java")
> ```
> *Quan sát:* Lệnh biên dịch kết thúc sau chưa đầy 1 giây, tạo thành công toàn bộ file `.class` trong thư mục `out/`, 0 lỗi, 0 cảnh báo.
> 
> **Bước 2: Khởi chạy ứng dụng**  
> Em chạy lệnh:
> ```bash
> java -cp out app.Main
> ```
> *Quan sát:* Màn hình khởi động của hệ thống `=== STADIUM TICKET BOOKING SIMULATION ===` xuất hiện với menu phân cấp rõ ràng.
> 
> **Bước 3: Trình diễn luồng Khách duyệt trận & Sơ đồ ghế**  
> Em chọn option `1. Browse as Guest`:
> - Hệ thống liệt kê danh sách các trận đấu đang có.
> - Em chọn xem chi tiết trận đấu: Bảng giá theo khán đài được hiển thị chính xác.
> - Em chọn xem sơ đồ ghế: Console vẽ trực tiếp lưới ghế trực quan với các trạng thái `[O]`, `[X]`.
> 
> **Bước 4: Trình diễn Phân hệ Quản trị Admin & Sinh dữ liệu 10.000 dòng**  
> Em quay lại menu chính, đăng nhập với tài khoản Administrator:
> - Hệ thống nhận diện đúng vai trò và mở `Admin Management Menu`.
> - Em chọn chức năng `System Summary`: Dữ liệu thống kê tổng hợp lập tức được tính toán từ các file CSV.
> - Em thực hiện lệnh `Generate Test Dataset`: Hệ thống tự động tạo bộ dữ liệu lớn hơn 10.000 dòng CSV một cách nhất quán về khóa ngoại và logic."

---

## Phần 5: Báo cáo Phần Trăm Còn Lại (35%) & Lộ trình Hoàn thiện (Khoảng 2 phút)

**Lời thuyết trình:**

> "Thưa Thầy, với 65% nền tảng vững chắc đã hoàn thành, khối lượng công việc còn lại là **35%**, chia làm 4 chặng cụ thể:
> 
> 1. **Chặng 08: Fan Booking, Thanh toán & Vé điện tử (~20%)**  
>    - Xây dựng quy trình Fan đặt vé (giới hạn tối đa 4 vé/lần).
>    - Thuật toán giữ chỗ tạm thời (`holdSeats`) với cơ chế giải phóng ghế quá hạn (`timeout`).
>    - Giả lập thanh toán trực tuyến (`PaymentService`), phát hành mã vé điện tử và nghiệp vụ hoàn vé (`RefundService`).
> 
> 2. **Chặng 09: Quầy vé Seller & Soát vé Gate Staff (~8%)**  
>    - Nhân viên Seller đặt vé trực tiếp tại quầy và xác nhận thu tiền mặt offline.
>    - Nhân viên soát vé tại cửa (Gate Staff) quét mã kiểm tra vé hợp lệ, ngăn chặn vé giả mạo hoặc vé tái sử dụng.
> 
> 3. **Chặng 10: Mô phỏng Đa luồng & Đo lường Concurrency (~5%)**  
>    - Đây là điểm nhấn học thuật của đề tài: Sử dụng `ExecutorService` và `CountDownLatch` để kích hoạt hàng trăm luồng khách hàng cùng tranh chấp một tập ghế hẹp.
>    - So sánh đối chứng 4 chiến lược đồng bộ:  
>      1. `NO_LOCK` (chứng minh race condition gây Double Booking).  
>      2. `SYNCHRONIZED` (khóa màn hình JVM).  
>      3. `FILE_LOCK` (khóa file CSV vật lý).  
>      4. `OPTIMISTIC` (khóa lạc quan dựa trên version field trong `MatchSeat`).  
>    - Xuất bảng biểu so sánh: Throughput (giao dịch/giây), Thời gian thực thi và Tỷ lệ đặt trùng ghế.
> 
> 4. **Chặng 11: Tích hợp tổng thể & Báo cáo cuối kỳ (~2%)**  
>    - Kiểm thử Restart Persistence (đóng ứng dụng, mở lại kiểm tra dữ liệu CSV vẫn nguyên vẹn).
>    - Hoàn thiện báo cáo, nghiệm thu và đóng gói sản phẩm."

---

## Phần 6: Lời kết & Sẵn sàng Phản biện (Q&A)

**Lời thuyết trình:**

> "Tóm lại, dự án hiện đang đi đúng tiến độ đề ra, bám sát 100% thiết kế hướng đối tượng và Class Diagram. Mã nguồn được tổ chức sạch sẽ, không over-engineering, không lạm dụng framework ngoài phạm vi bài học. Nhóm em tự tin sẽ hoàn thành toàn bộ 35% còn lại đúng thời hạn với chất lượng cao nhất.
> 
> Em xin chân thành cảm ơn Thầy và các bạn đã chú ý lắng nghe! Sau đây em xin phép được lắng nghe ý kiến đóng góp và trả lời các câu hỏi phản biện của Thầy."

---

## Phụ lục: Các câu hỏi phản biện tiềm năng & Hướng trả lời tự tin

### 1. "Tại sao không dùng Database như MySQL hay MongoDB mà phải dùng CSV?"
> **Trả lời:**  
> "Thưa Thầy, việc sử dụng CSV là yêu cầu bắt buộc và là giới hạn thiết kế của học phần LAB211 nhằm đánh giá kỹ năng xử lý cấu trúc dữ liệu thuần trong Java (File I/O, String parsing, Generic collections).  
> Hơn nữa, việc sử dụng file CSV giúp chúng em dễ dàng minh họa bài toán tranh chấp tài nguyên vật lý trong chặng Concurrency mô phỏng sau này, đặc biệt là cơ chế `FileLock` trên hệ điều hành và cơ chế khóa lạc quan `OPTIMISTIC` mà nếu dùng Database có sẵn ACID transactions thì sẽ bị ẩn đi."

### 2. "Tại sao không băm mật khẩu (hash password) mà lưu mật khẩu thường?"
> **Trả lời:**  
> "Thưa Thầy, ban đầu trong quá trình nghiên cứu, AI có đề xuất sinh hàm mã hóa SHA-256 (`randomHash()`), nhưng em đã chủ động bác bỏ (reject) đề xuất đó.  
> Lý do là vì đây là đồ án Java Console mô phỏng phục vụ nghiên cứu OOP và Concurrency trong phạm vi LAB211, không phải hệ thống Web phân tán có module an ninh mạng. Việc hash mật khẩu sẽ gây phức tạp hóa không cần thiết (over-engineering), khiến việc kiểm tra dữ liệu bằng mắt trong file CSV và debug dữ liệu sinh tự động (`DataGenerator`) trở nên khó khăn. Em ưu tiên giữ cho mã nguồn trong sáng, dễ hiểu và tập trung đúng vào trọng tâm bài học."

### 3. "Vì sao lại tách `Seat` và `MatchSeat` thành hai class riêng?"
> **Trả lời:**  
> "Thưa Thầy, đây là nguyên lý phân tách trách nhiệm trong thiết kế hướng đối tượng:
> - `Seat` là ghế vật lý thuộc về cơ sở hạ tầng của sân vận động (`Stadium`), chỉ có trạng thái `active` (ghế lành lặn hay bị gãy/bảo trì).
> - `MatchSeat` là tồn kho ghế gắn liền với một trận đấu cụ thể (`Match`). Một chiếc ghế vật lý số A-01 có thể đã được đặt (`BOOKED`) trong trận Bán kết, nhưng vẫn hoàn toàn khả dụng (`AVAILABLE`) trong trận Chung kết. Nếu gộp chung trạng thái vào `Seat` thì hệ thống sẽ không thể tổ chức nhiều trận đấu trên cùng một sân."
