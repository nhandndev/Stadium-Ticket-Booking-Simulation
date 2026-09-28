# Script gạch đầu dòng từ Use Case đến Class Diagram

## 1. Cách giới thiệu Use Case

- **Use Case Diagram dùng để làm gì?**
  - Xác định hệ thống phục vụ những actor nào.
  - Xác định mỗi actor được sử dụng feature nào.
  - Xác định phạm vi của phần mềm trước khi thiết kế class.
  - Không mô tả chi tiết code hoặc thứ tự gọi method.
- **System boundary là gì?**
  - Là khung chữ nhật bao quanh các use case.
  - Feature bên trong khung là trách nhiệm của phần mềm.
  - Actor nằm ngoài khung vì actor chỉ gửi yêu cầu và nhận kết quả.
- **Ví dụ:**
  - `Make Online Payment` nằm trong boundary vì hệ thống phải xử lý yêu cầu.
  - `Payment Service` nằm ngoài boundary vì đây là dịch vụ mà hệ thống gọi tới.
- **Câu nói gợi ý:**
  - “Em bắt đầu bằng Use Case để trả lời hai câu hỏi: ai sử dụng hệ thống và họ
    cần hệ thống giải quyết việc gì. Sau đó em mới chuyển các feature này thành
    class trong Class Diagram.”

## 2. Guest

- **Actor Guest dùng để làm gì?**
  - Đại diện cho người chưa đăng nhập.
  - Cho phép khách xem thông tin trước khi quyết định tạo tài khoản.
  - Không được booking vì hệ thống chưa có Fan để sở hữu Booking và Ticket.

- **Feature: Search Matches**
  - **Dùng để:** tìm trận bằng từ khóa tự do.
  - **Cách hoạt động:** hệ thống so khớp từ khóa với tên đội hoặc thông tin trận.
  - **Ví dụ:** nhập `Vietnam` để tìm các trận có đội tuyển Việt Nam.
  - **Class liên quan:** `MainView -> BrowseController -> BrowseService -> MatchRepository`.

- **Feature: Filter Matches**
  - **Dùng để:** lọc trận bằng điều kiện có cấu trúc.
  - **Cách hoạt động:** nhận stadium và ngày thi đấu rồi lọc danh sách Match.
  - **Ví dụ:** chỉ xem trận tại sân Mỹ Đình trong ngày 20 tháng 10.
  - **Class liên quan:** `BrowseController`, `BrowseService`, `MatchRepository`.

- **Feature: View Match Details**
  - **Dùng để:** xem đầy đủ thông tin của một trận đã chọn.
  - **Thông tin hiển thị:** đội nhà, đội khách, sân, thời gian và sale status.
  - **Ví dụ:** xem trận M01 đang `ON_SALE` và bắt đầu lúc 19 giờ.
  - **Class liên quan:** `Match`, `MatchResponseDto`, `BrowseService`.

- **Feature: View Seat Availability**
  - **Dùng để:** xem ghế nào còn có thể đặt trong một trận.
  - **Cách hoạt động:** đọc trạng thái từ `MatchSeat`, không đọc trạng thái đặt từ `Seat`.
  - **Ví dụ:** Seat A12 available ở M01 nhưng booked ở M02.
  - **Class liên quan:** `MatchSeat`, `MatchSeatRepository`, `MatchSeatResponseDto`.
  - **Điểm cần nhấn mạnh:** xem ghế available không đồng nghĩa ghế đã được giữ.

- **Feature: Register**
  - **Dùng để:** chuyển Guest thành Fan có tài khoản.
  - **Cách hoạt động:** nhận họ tên, email, password và phone; kiểm tra email trùng.
  - **Ví dụ:** email đã tồn tại thì từ chối tạo account mới.
  - **Class liên quan:** `RegisterFanRequestDto`, `FanController`, `FanService`, `FanRepository`.

- **Feature: Login**
  - **Dùng để:** xác thực tài khoản và xác định role.
  - **Cách hoạt động:** kiểm tra email, password, status rồi tạo current user.
  - **Ví dụ:** account có role `SELLER` được mở Seller menu.
  - **Class liên quan:** `LoginRequestDto`, `AuthController`, `AuthService`, `UserResponseDto`.

## 3. User

- **Actor User dùng để làm gì?**
  - Đại diện chung cho tài khoản đã xác thực.
  - Gom feature dùng chung để không nối lặp cho từng role.
  - Fan và Staff kế thừa thông tin chung từ User trong domain model.

- **Feature: Login**
  - **Dùng để:** tạo phiên người dùng hiện tại.
  - **Ví dụ:** đúng password nhưng account inactive vẫn không được đăng nhập.

- **Feature: Logout**
  - **Dùng để:** xóa current user và quay về Guest menu.
  - **Ví dụ:** Seller logout thì không còn truy cập Seller menu.
  - **Class liên quan:** `AuthController.logout`, `AuthService.logout`.

- **Feature: Update Profile Information**
  - **Dùng để:** thay đổi họ tên và số điện thoại.
  - **Không dùng để:** tự đổi id, role hoặc status.
  - **Ví dụ:** Fan đổi phone nhưng không thể đổi role thành Admin.
  - **Class liên quan:** `ProfileUpdateRequestDto`, `AuthService`, `User.updateProfile`.

## 4. Fan

- **Actor Fan dùng để làm gì?**
  - Đại diện cho khách hàng mua vé cho chính mình.
  - Là chủ sở hữu của Booking, Payment history và Ticket.
  - Chỉ được xem dữ liệu thuộc tài khoản hiện tại.

- **Feature: Select Seats**
  - **Dùng để:** chọn ghế cho một Match trước khi booking.
  - **Business rule:** từ một đến tối đa bốn ghế.
  - **Điều kiện:** Seat active và MatchSeat available.
  - **Ví dụ:** chọn A10 và A11 hợp lệ; chọn năm ghế bị từ chối.
  - **Class liên quan:** `SeatMapView`, `MatchSeat`, `BookingRequestDto`.

- **Feature: Create Booking**
  - **Dùng để:** tạo yêu cầu đặt các ghế đã chọn.
  - **Cách hoạt động:** kiểm tra sale status, Fan, số ghế, giá và MatchSeat lần cuối.
  - **Ví dụ:** A10 vừa bị thread khác đặt thì booking hiện tại thất bại.
  - **Class liên quan:** `BookingController`, `BookingService`, `Booking`, `MatchSeat`.

- **Feature: Make Online Payment**
  - **Dùng để:** thanh toán booking của Fan.
  - **Cách hoạt động:** `BookingService` gọi `MockPaymentService` rồi lưu Payment.
  - **Ví dụ:** payment 500.000 đồng thành công thì mới confirm và tạo ticket.
  - **Class liên quan:** `PaymentRequestDto`, `Payment`, `PaymentRepository`.

- **Feature: View Booking History**
  - **Dùng để:** xem danh sách booking của Fan hiện tại.
  - **Ví dụ:** Fan id 12 chỉ thấy booking có `fanId = 12`.
  - **Class liên quan:** `BookingRepository.findByFanId`, `BookingResponseDto`.

- **Feature: View Booking Details**
  - **Dùng để:** xem một booking cụ thể.
  - **Thông tin:** match, ghế, total amount và booking status.
  - **Ví dụ:** B105 gồm M01, A10-A11 và tổng tiền 500.000 đồng.

- **Feature: View Tickets**
  - **Dùng để:** xem các ticket đã phát hành cho Fan.
  - **Ví dụ:** booking hai ghế thành công tạo hai ticket.
  - **Class liên quan:** `FanService`, `TicketRepository`, `TicketResponseDto`.

- **Feature: View Ticket Details**
  - **Dùng để:** xem code, match, seat, status và checked-in time.
  - **Ví dụ:** ticket T-A10-001 thuộc ghế A10 của M01.

- **Feature: View Tickets History**
  - **Dùng để:** giữ lịch sử cả ticket cũ và ticket không còn hiệu lực.
  - **Ví dụ:** ticket của trận trước vẫn hiển thị trạng thái `CHECKED_IN`.

- **Feature: Request Refund**
  - **Dùng để:** gửi yêu cầu hoàn tiền để Admin xét duyệt.
  - **Cách hoạt động:** tạo RefundRequest ở trạng thái `REQUESTED`.
  - **Ví dụ:** Fan gửi refund cho B105 với lý do không thể tham dự.
  - **Điểm cần nhấn mạnh:** gửi request không đồng nghĩa refund đã được approve.
  - **Class liên quan:** `RefundController`, `RefundService`, `RefundRequest`.

## 5. Seller

- **Actor Seller dùng để làm gì?**
  - Đại diện cho nhân viên bán vé tại quầy.
  - Thao tác thay khách nhưng Booking cuối cùng vẫn thuộc về Fan.
  - Sử dụng offline payment thay cho online payment.

- **Feature: Search Fan**
  - **Dùng để:** tìm khách trước khi booking và tránh tạo account trùng.
  - **Ví dụ:** tìm bằng email hoặc số điện thoại của khách tại quầy.
  - **Class liên quan:** `SellerView -> FanController -> FanService`.

- **Feature: Create Fan**
  - **Dùng để:** tạo Fan nếu khách chưa có tài khoản.
  - **Ví dụ:** Seller nhập name, email và phone trước khi bán vé.

- **Feature: Create Booking for Fan**
  - **Dùng để:** Seller đặt ghế thay cho Fan.
  - **Cách hoạt động:** dùng chung `BookingController` và `BookingService` với Fan.
  - **Ví dụ:** Seller đặt B05 cho Fan id 12.
  - **Lý do dùng chung Service:** tránh Fan và Seller có hai quy tắc cập nhật ghế khác nhau.

- **Feature: Accept Offline Payment**
  - **Dùng để:** ghi nhận khách đã trả tiền tại quầy.
  - **Ví dụ:** Seller nhận 250.000 đồng tiền mặt.
  - **Điểm khác Fan:** không gọi Payment Service online.

- **Feature: Issue Ticket**
  - **Dùng để:** phát hành vé sau khi booking và offline payment hợp lệ.
  - **Ví dụ:** tạo ticket cho ghế B05 và giao code cho khách.

## 6. Gate Staff

- **Actor Gate Staff dùng để làm gì?**
  - Đại diện cho nhân viên kiểm soát vé tại cổng.
  - Bảo đảm vé đúng trận, còn hiệu lực và chỉ được sử dụng một lần.

- **Feature: Validate Ticket**
  - **Dùng để:** kiểm tra ticket mà chưa thay đổi trạng thái.
  - **Điều kiện:** ticket tồn tại, đúng match và chưa cancelled/refunded/checked-in.
  - **Ví dụ:** T-A10-001 hợp lệ cho M01 nhưng bị từ chối ở M02.
  - **Class liên quan:** `GateView`, `TicketController`, `TicketService`.

- **Feature: Check-in Ticket**
  - **Dùng để:** xác nhận ticket đã được sử dụng.
  - **Cách hoạt động:** chuyển status sang `CHECKED_IN` và lưu thời điểm.
  - **Ví dụ:** quét lần đầu lúc 18:45 thành công; quét lại lúc 18:50 thất bại.
  - **Class liên quan:** `Ticket.checkIn`, `TicketRepository`.

## 7. Administrator

- **Actor Administrator dùng để làm gì?**
  - Chuẩn bị dữ liệu nền để hệ thống có thể bán vé.
  - Quản lý tài khoản và quyền của Staff.
  - Điều khiển trạng thái bán vé.
  - Theo dõi hệ thống và xét duyệt refund.

### Stadium Management

- **Create Stadium**
  - **Dùng để:** tạo sân từ name và address.
  - **Ví dụ:** tạo sân Mỹ Đình tại Hà Nội.
- **View Stadium List**
  - **Dùng để:** xem tất cả sân trước khi chọn quản lý.
  - **Ví dụ:** hiển thị năm sân theo id.
- **View Stadium Details**
  - **Dùng để:** xem thông tin của một sân.
  - **Ví dụ:** chọn stadium id 1 để xem name và address.
- **Update Stadium**
  - **Dùng để:** sửa name hoặc address.
  - **Ví dụ:** sửa địa chỉ bị nhập sai.
- **Delete Stadium**
  - **Dùng để:** loại bỏ sân khi không còn dữ liệu phụ thuộc.
  - **Ví dụ:** từ chối xóa sân đã có Match.
- **Class xử lý nhóm này:** `StadiumController`, `StadiumService`, `StadiumRepository`.

### Section Management

- **Create Section**
  - **Dùng để:** tạo khu vực thuộc một Stadium.
  - **Ví dụ:** tạo khán đài A cho stadium 1.
- **View Sections by Stadium**
  - **Dùng để:** xem các khu vực của đúng sân đã chọn.
  - **Ví dụ:** chọn Mỹ Đình để xem khu A, B và C.
- **View Section Details**
  - **Dùng để:** xem id, stadium cha và tên của một Section.
  - **Ví dụ:** section 10 thuộc stadium 1 và có tên khán đài A.
- **Update Section**
  - **Dùng để:** đổi thông tin của khu vực.
  - **Ví dụ:** đổi tên `A` thành `A Premium`.
- **Delete Section**
  - **Dùng để:** xóa khu vực khi quan hệ dữ liệu cho phép.
  - **Ví dụ:** từ chối xóa section còn Seat.
- **Class xử lý nhóm này:** `StadiumController`, `StadiumService`, `SectionRepository`, `Section`.

### Seat Management

- **Create Seat**
  - **Dùng để:** tạo một ghế vật lý thuộc Section.
  - **Ví dụ:** tạo ghế A12 trong section 10.
- **View Seats by Section**
  - **Dùng để:** xem danh sách ghế theo khu vực.
  - **Ví dụ:** xem toàn bộ ghế hàng A của section 10.
- **View Seat Details**
  - **Dùng để:** xem hàng, số ghế và trạng thái active.
  - **Ví dụ:** mở Seat A12 để biết ghế đang inactive.
- **Update Seat**
  - **Dùng để:** sửa vị trí của ghế vật lý.
  - **Ví dụ:** sửa nhãn ghế nhập nhầm từ A21 thành A12.
- **Delete Seat**
  - **Dùng để:** xóa ghế khi không làm hỏng dữ liệu trận và vé.
  - **Ví dụ:** từ chối xóa Seat đã được MatchSeat tham chiếu.
- **Activate Seat**
  - **Dùng để:** cho phép sử dụng lại ghế vật lý.
  - **Ví dụ:** A12 được sửa xong nên chuyển sang active.
- **Deactivate Seat**
  - **Dùng để:** tạm ngừng sử dụng ghế vật lý.
  - **Ví dụ:** A12 bị hỏng nên chuyển sang inactive.
- **Class xử lý nhóm này:** `StadiumService`, `SeatRepository`, `Seat`.
- **Điểm cần nhấn mạnh:** `Seat.active` khác `MatchSeat.status`.

### Match Management

- **Create Match**
  - **Dùng để:** lập trận tại một Stadium với hai đội và thời gian cụ thể.
  - **Ví dụ:** Việt Nam gặp Thái Lan tại Mỹ Đình lúc 19 giờ.
- **View Match List**
  - **Dùng để:** xem và chọn trận cần quản lý.
  - **Ví dụ:** liệt kê các trận sắp diễn ra.
- **View Match Details**
  - **Dùng để:** xem thông tin và trạng thái bán vé của trận.
  - **Ví dụ:** M01 đang `ON_SALE`.
- **Update Match**
  - **Dùng để:** sửa thông tin trận khi lịch thay đổi.
  - **Ví dụ:** dời trận từ 19 giờ sang 20 giờ.
- **Delete Match**
  - **Dùng để:** xóa trận khi không có dữ liệu phụ thuộc cản trở.
  - **Ví dụ:** từ chối xóa trận đã bán vé.
- **Class xử lý nhóm này:** `MatchController`, `MatchService`, `MatchRepository`, `Match`.

### Ticket Pricing Management

- **Create Ticket Price for a Match and Section**
  - **Dùng để:** đặt giá cho đúng cặp Match và Section.
  - **Ví dụ:** M01 + Section A có giá 500.000 đồng.
- **View Ticket Prices by Match**
  - **Dùng để:** xem bảng giá tất cả Section trong một trận.
  - **Ví dụ:** M01 có khu A giá 500.000 đồng và khu B giá 300.000 đồng.
- **View Ticket Price Details**
  - **Dùng để:** xem một mức giá cụ thể.
  - **Ví dụ:** tra giá của M01 + Section A.
- **Update Ticket Price**
  - **Dùng để:** thay đổi mức giá khi nghiệp vụ cho phép.
  - **Ví dụ:** đổi khu B từ 300.000 thành 350.000 đồng.
- **Delete Ticket Price**
  - **Dùng để:** bỏ cấu hình giá chưa ảnh hưởng booking.
  - **Ví dụ:** xóa giá nhập nhầm trước khi mở bán.
- **Class xử lý nhóm này:** `MatchService`, `TicketPriceRepository`, `TicketPrice`.

### Ticket Sales Management

- **Open Ticket Sales**
  - **Dùng để:** cho phép Fan và Seller tạo Booking cho trận.
  - **Ví dụ:** Admin mở bán M01.
- **Close Ticket Sales**
  - **Dùng để:** chặn Booking mới nhưng giữ Booking đã tạo.
  - **Ví dụ:** đóng bán M01 trước giờ trận bắt đầu.
- **View Ticket Sales Status**
  - **Dùng để:** kiểm tra trận đang mở hay đóng bán.
  - **Ví dụ:** xem M01 đang `ON_SALE` hay `CLOSED`.
- **Class xử lý nhóm này:** `MatchController`, `MatchService`, `Match.changeSaleStatus`.

### Fan Management

- **Create Fan Account**
  - **Dùng để:** Admin tạo tài khoản khách hàng.
  - **Ví dụ:** nhập account cho khách đăng ký trực tiếp.
- **View Fans**
  - **Dùng để:** xem danh sách Fan trong hệ thống.
  - **Ví dụ:** liệt kê các Fan theo id.
- **View Fan Details**
  - **Dùng để:** xem profile và status của một Fan.
  - **Ví dụ:** mở Fan id 12 để kiểm tra số điện thoại.
- **Search Fan**
  - **Dùng để:** tìm Fan bằng từ khóa.
  - **Ví dụ:** tìm `nhan@gmail.com`.
- **Filter Fan**
  - **Dùng để:** lọc Fan theo trạng thái tài khoản.
  - **Ví dụ:** chỉ xem các Fan inactive.
- **Update Fan Information**
  - **Dùng để:** sửa thông tin Fan đã xác minh.
  - **Ví dụ:** cập nhật số điện thoại mới.
- **Activate Fan Account**
  - **Dùng để:** cho tài khoản hoạt động trở lại.
  - **Ví dụ:** mở lại account sau khi xác minh.
- **Deactivate Fan Account**
  - **Dùng để:** chặn thao tác mới và giữ lịch sử Booking, Ticket.
  - **Ví dụ:** vô hiệu hóa account vi phạm.
- **Delete Fan Account**
  - **Dùng để:** xóa Fan khi không vi phạm dữ liệu giao dịch.
  - **Ví dụ:** xóa account thử nghiệm chưa có Booking.
- **Class xử lý nhóm này:** `AccountAdminController`, `AccountAdminService`, `FanRepository`.

### Staff And Role Management

- **Create Staff Account**
  - **Dùng để:** tạo tài khoản cho nhân viên mới.
  - **Ví dụ:** tạo account cho nhân viên quầy vé.
- **View Staffs**
  - **Dùng để:** xem danh sách nhân viên.
  - **Ví dụ:** liệt kê Staff đang active.
- **View Staff Details**
  - **Dùng để:** xem profile, role và status của một Staff.
  - **Ví dụ:** Staff id 5 đang có role `SELLER`.
- **Search Staff**
  - **Dùng để:** tìm nhân viên theo thông tin nhận diện.
  - **Ví dụ:** tìm bằng email công ty.
- **Filter Staff**
  - **Dùng để:** lọc theo role hoặc status.
  - **Ví dụ:** chỉ xem các `GATE_STAFF` đang active.
- **Update Staff Information**
  - **Dùng để:** sửa profile của Staff.
  - **Ví dụ:** cập nhật số điện thoại.
- **Activate Staff Account**
  - **Dùng để:** cho phép account hoạt động trở lại.
  - **Ví dụ:** mở lại account khi nhân viên quay lại làm việc.
- **Deactivate Staff Account**
  - **Dùng để:** chặn login và giữ audit history.
  - **Ví dụ:** vô hiệu hóa account khi nhân viên nghỉ việc.
- **Assign Staff Role**
  - **Dùng để:** gán quyền nghiệp vụ lần đầu.
  - **Ví dụ:** gán `SELLER` cho Staff mới.
- **Change Staff Role**
  - **Dùng để:** chuyển nhân viên sang quyền khác.
  - **Ví dụ:** chuyển `SELLER` thành `GATE_STAFF`.
- **Revoke Staff Role**
  - **Dùng để:** thu hồi quyền nghiệp vụ hiện tại.
  - **Ví dụ:** Staff không còn truy cập Seller menu sau khi bị thu hồi role.
- **Class xử lý nhóm này:** `AccountAdminController`, `AccountAdminService`, `StaffRepository`.

### System Monitoring và Refund

- **View System Summary**
  - **Dùng để:** xem tổng số Fan, Staff, Stadium, Match, Booking và Ticket.
  - **Ví dụ:** hệ thống có 500 Fan và 1.200 Booking.
  - **Class liên quan:** `AdminService`, `SystemSummaryDto`.
- **View Audit Log**
  - **Dùng để:** truy vết ai thực hiện hành động gì và khi nào.
  - **Ví dụ:** Admin id 1 đóng bán M01 lúc 18 giờ.
  - **Class liên quan:** `AuditLog`, `AuditLogRepository`.
- **Review Refund Request**
  - **Dùng để:** approve hoặc reject yêu cầu refund.
  - **Ví dụ:** approve R10 với ghi chú `Trận đấu bị hủy`.
  - **Class liên quan:** `RefundController`, `RefundService`, `RefundRequest`.

## 8. Simulator Operator

- **Actor Simulator Operator dùng để làm gì?**
  - Vận hành thí nghiệm concurrency.
  - Không đại diện cho Fan mua vé thật.
  - Tạo nhiều booking attempt có cùng điều kiện để so sánh mechanism.

- **Feature: Configure Simulation**
  - **Dùng để:** chọn Match, target seats, thread count và mechanism.
  - **Ví dụ:** M01, A12, 1.000 thread và `NO_LOCK`.
- **Feature: Select Synchronization Mechanism**
  - **Dùng để:** chọn cách bảo vệ cập nhật MatchSeat.
  - **Giá trị:** `NO_LOCK`, `SYNCHRONIZED`, `FILE_LOCK`, `OPTIMISTIC`.
  - **Ví dụ:** chạy cùng kịch bản với `NO_LOCK`, sau đó đổi sang `FILE_LOCK`.
- **Feature: Run Simulation**
  - **Dùng để:** chạy các BookingTask gần cùng thời điểm.
  - **Ví dụ:** 1.000 task cùng chờ latch rồi tranh A12.
- **Feature: View / Compare Results**
  - **Dùng để:** so sánh success, failure, conflict, double booking và throughput.
  - **Ví dụ:** `NO_LOCK` có hai success cho A12, `SYNCHRONIZED` chỉ có một.
- **Feature: Export Result**
  - **Dùng để:** lưu kết quả làm báo cáo.
  - **Ví dụ:** xuất bốn mechanism thành CSV.
- **Class liên quan:** `SimulatorView`, `SimulationController`, `SimulationService`, `SimulationResult`.

## 9. Payment Service

- **Actor Payment Service dùng để làm gì?**
  - Là hệ thống ngoài, không phải người dùng Console.
  - Xử lý online payment của Fan.
  - Trong project được thay bằng `MockPaymentService`.
- **Feature: Process Online Payment**
  - **Dùng để:** trả về success hoặc failure cho một Booking và amount.
  - **Ví dụ:** B105 thanh toán 500.000 đồng thành công thì được tạo Ticket.
  - **Nếu thất bại:** không issue Ticket và không để MatchSeat booked sai.

## 10. Chuyển từ Use Case sang Class Diagram

- **Use Case trả lời:** actor nào cần feature nào.
- **Class Diagram trả lời:** class nào chịu trách nhiệm hiện thực feature đó.
- **Quy tắc ánh xạ:**
  - Danh từ nghiệp vụ → Model.
  - Dữ liệu nhập/xuất → DTO.
  - Hành động từ View → Controller.
  - Validation và workflow → Service.
  - Đọc/ghi CSV → Repository.
- **Ví dụ Create Booking:**
  - Input Console nằm ở `BookingView`.
  - Request đi qua `BookingController`.
  - Workflow nằm trong `BookingService`.
  - Dữ liệu được lưu bởi `BookingRepository`.
  - Trạng thái nằm trong `Booking`, `MatchSeat`, `Payment`, `Ticket`.

## 11. Ký hiệu Class Diagram

- **`-field`:** private attribute.
- **`+method()`:** public method.
- **`A --> B`:** A biết và sử dụng B.
- **`A ..> B`:** A phụ thuộc B trong một thao tác nhưng không nhất thiết giữ B
  làm thuộc tính.
- **`A --|> B`:** A kế thừa B.
- **`Interface <|.. Class`:** Class hiện thực Interface.
- **`A *-- B`:** composition; B là thành phần thuộc A.
- **`1`:** đúng một.
- **`0..1`:** có thể không có hoặc có tối đa một.
- **`0..*`:** có thể không có hoặc có nhiều.
- **`1..4`:** ít nhất một và tối đa bốn.

## 12. Class Diagram trang 1 - Main, View và Authentication

- **Trang này dùng để làm gì?**
  - Mô tả entry point, menu, login, register, profile và Guest browsing.
- **Luồng đọc:** `Main -> MainView -> Controller -> Service -> Repository -> Model`.

- **Relationship: `Main --> MainView`**
  - **Loại:** association có hướng theo ký hiệu `-->` trên sơ đồ.
  - **Ý nghĩa:** Main khởi tạo ứng dụng và gọi `start`.
  - **Lý do:** Main không chứa menu hoặc business logic.
- **Relationship: `MainView --> AuthController/FanController/BrowseController`**
  - **Loại:** association một chiều.
  - **Ý nghĩa:** View gọi Controller; Controller không phụ thuộc Console View.
  - **Lý do:** có thể thay View mà không sửa nghiệp vụ.
- **Relationship: `Controller --> Service`**
  - **Loại:** association một chiều.
  - **Ý nghĩa:** Controller delegate validation và workflow.
- **Relationship: `Service --> Repository`**
  - **Loại:** association sử dụng.
  - **Ý nghĩa:** Service không tự đọc CSV.
- **Relationship: `Fan --|> User`, `Staff --|> User`**
  - **Loại:** inheritance.
  - **Lý do:** Fan và Staff đều là User và dùng chung profile, email, role, status.
- **Ví dụ login:**
  - MainView tạo `LoginRequestDto`.
  - AuthController gọi AuthService.
  - AuthService tìm FanRepository hoặc StaffRepository.
  - UserResponseDto trả role về MainView.
  - MainView mở đúng menu theo role.

## 13. Class Diagram trang 2 - Stadium, Match và Administrator

- **Trang này dùng để làm gì?**
  - Hiện thực toàn bộ feature của Administrator.
- **Vì sao có nhiều Controller?**
  - `StadiumController`: Stadium, Section, Seat.
  - `MatchController`: Match, Pricing, Ticket Sales.
  - `AccountAdminController`: Fan, Staff, Role.
  - `AdminController`: Summary, Audit, Data Generator.
  - `RefundController`: Refund review.
  - Tránh tạo một AdminController quá lớn.

- **Relationship: `Stadium "1" *-- "0..*" Section`**
  - **Loại:** composition.
  - **Ý nghĩa:** Stadium chứa nhiều Section; Section thuộc một Stadium.
  - **Lý do:** Section là thành phần của Stadium, không phải một loại Stadium.
- **Relationship: `Section "1" *-- "0..*" Seat`**
  - **Loại:** composition.
  - **Ý nghĩa:** Section chứa nhiều Seat.
- **Relationship: `Match "0..*" --> "1" Stadium`**
  - **Loại:** association.
  - **Ý nghĩa:** mỗi Match diễn ra tại một Stadium; Stadium có nhiều Match.
  - **Lý do không composition:** xóa Match không được xóa Stadium.
- **Relationship: `Match "1" *-- "0..*" MatchSeat`**
  - **Loại:** composition.
  - **Ý nghĩa:** mỗi Match sở hữu tồn kho ghế riêng.
- **Relationship: `MatchSeat "0..*" --> "1" Seat`**
  - **Loại:** association.
  - **Ý nghĩa:** MatchSeat tham chiếu Seat vật lý.
  - **Ví dụ:** cùng Seat A12 tạo MatchSeat khác nhau cho M01 và M02.
- **Ví dụ Create Stadium:**
  - View tạo `StadiumRequestDto`.
  - StadiumController gọi StadiumService.
  - StadiumService validate và tạo Stadium.
  - StadiumRepository ghi CSV.
  - StadiumResponseDto trả kết quả về View.

## 14. Class Diagram trang 3 - Booking, Payment, Ticket và Refund

- **Trang này dùng để làm gì?**
  - Hiện thực feature của Fan, Seller và Gate Staff.
- **Class trung tâm:** `BookingService`.
  - Kiểm tra Fan và giới hạn ghế.
  - Kiểm tra Seat và MatchSeat.
  - Tính giá từ TicketPrice.
  - Xử lý online/offline payment.
  - Tạo Booking, Ticket và BookingTransaction.

- **Relationship: `Booking "0..*" --> "1" Fan`**
  - **Loại:** association.
  - **Ý nghĩa:** mỗi Booking thuộc một Fan; Fan có nhiều Booking.
- **Relationship: `Booking --> "1..4" MatchSeat`**
  - **Loại:** association có multiplicity.
  - **Ý nghĩa:** một Booking chọn từ một đến bốn ghế.
- **Relationship: `Booking "1" --> "0..1" Payment`**
  - **Loại:** association tùy chọn.
  - **Ý nghĩa:** Booking mới có thể chưa payment; scope hiện tại giữ tối đa một Payment chính.
- **Relationship: `Booking "1" *-- "0..4" Ticket`**
  - **Loại:** composition.
  - **Ý nghĩa:** Ticket được tạo như kết quả thuộc Booking.
- **Relationship: `RefundRequest --> Booking`**
  - **Loại:** association.
  - **Ý nghĩa:** refund phải chỉ ra giao dịch gốc cần xét duyệt.
- **Ví dụ Fan booking A10-A11:**
  - BookingView tạo request.
  - BookingService kiểm tra hai MatchSeat.
  - TicketPriceRepository trả giá.
  - MockPaymentService trả success.
  - Booking confirmed, A10-A11 booked và hai Ticket được tạo.
- **Ví dụ Seller:**
  - SellerView gọi cùng BookingController.
  - Chỉ thay online payment bằng offline payment.
- **Ví dụ Gate:**
  - GateView gọi TicketController.
  - TicketService validate rồi gọi `Ticket.checkIn`.

## 15. Class Diagram trang 4 - Concurrency Simulation

- **Trang này dùng để làm gì?**
  - Chạy booking attempt đồng thời và so sánh synchronization mechanism.
- **Luồng chính:**
  - SimulatorView tạo SimulationRequestDto.
  - SimulationController gọi SimulationService.
  - SimulationService tạo ExecutorService và BookingTask.
  - CountDownLatch mở điểm bắt đầu chung.
  - Transaction được ghi và Result được tổng hợp.

- **Relationship: `BookingTask --> BookingStrategy`**
  - **Loại:** association.
  - **Ý nghĩa:** Task chỉ biết contract `execute`.
- **Relationship: `BookingStrategy <|.. NoLockStrategy/...`**
  - **Loại:** interface realization.
  - **Ý nghĩa:** bốn class dùng cùng method nhưng cách đồng bộ khác nhau.
  - **Lý do:** thay mechanism mà không sửa BookingTask.
- **Relationship: `Simulation --> SimulationResult`**
  - **Loại:** association một-nhiều.
  - **Ý nghĩa:** một cấu hình có nhiều result khi so sánh mechanism.
- **Relationship: `Simulation --> BookingTransaction`**
  - **Loại:** association một-nhiều.
  - **Ý nghĩa:** mỗi task tạo một transaction để tính metric.
- **Ví dụ:**
  - 1.000 task tranh A12.
  - NO_LOCK có thể ghi nhận nhiều success.
  - SYNCHRONIZED phải chỉ có một success.

## 16. Class Diagram trang 5 - CSV Repository và nền dùng chung

- **Trang này dùng để làm gì?**
  - Mô tả persistence, identity và exception.
- **`CsvRepository<T>` dùng để:**
  - Cung cấp `findAll`, `findById`, `save`, `update`, `delete`.
  - Tránh lặp code đọc ghi file.
- **Concrete Repository dùng để:**
  - Override `parseLine` và `formatLine` theo từng entity.
  - Bổ sung truy vấn nghiệp vụ như `findByEmail`, `findByCode`.
- **Relationship: Concrete Repository `--|>` CsvRepository**
  - **Loại:** inheritance.
  - **Lý do:** tái sử dụng CRUD và tùy chỉnh serialization.
- **Relationship: Entity `--|>` BaseEntity**
  - **Loại:** inheritance kỹ thuật.
  - **Lý do:** dùng chung id.
- **Relationship: `DataGenerator ..> CsvRepository`**
  - **Loại:** dependency.
  - **Lý do:** generator chỉ dùng Repository để tạo dữ liệu, không phải Repository.
- **Relationship: `RuntimeException <|-- AppException`**
  - **Loại:** inheritance.
  - **Lý do:** dùng unchecked exception thống nhất.
- **Relationship: `AppException --> ErrorCode`**
  - **Loại:** association.
  - **Lý do:** mỗi lỗi giữ code và message; Console không cần HTTP status.
- **Ví dụ:**
  - Không tìm thấy sân → `AppException(ErrorCode.NOT_FOUND, "Stadium not found")`.

## 17. Class Diagram trang 6 - Model Relationships

- **Trang này dùng để làm gì?**
  - Chỉ tập trung vào entity và multiplicity.
  - Không nối DTO, Controller hoặc Repository để tránh che domain relationship.

- **Nhóm tài khoản:**
  - `Fan --|> User`: Fan là một loại User.
  - `Staff --|> User`: Staff là một loại User.
- **Nhóm sân:**
  - `Stadium *-- Section`: Stadium chứa Section.
  - `Section *-- Seat`: Section chứa Seat.
  - `Match --> Stadium`: Match diễn ra tại Stadium.
  - `Match *-- MatchSeat`: Match sở hữu trạng thái ghế theo trận.
  - `MatchSeat --> Seat`: MatchSeat tham chiếu ghế vật lý.
- **Nhóm giá:**
  - `TicketPrice --> Match` và `TicketPrice --> Section`.
  - Cặp `matchId + sectionId` xác định giá.
- **Nhóm booking:**
  - Booking thuộc một Fan và một Match.
  - Booking chứa từ một đến bốn MatchSeat.
  - MatchSeat thuộc tối đa một Booking thành công.
  - Booking có tối đa một Payment.
  - Booking tạo tối đa bốn Ticket.
- **Nhóm refund/audit:**
  - RefundRequest thuộc Booking và Fan.
  - Staff review là `0..1` vì request mới có thể chưa được xử lý.
  - AuditLog thuộc User thực hiện hành động.
- **Nhóm simulation:**
  - Simulation thuộc một Match.
  - Simulation có nhiều SimulationResult.
  - Simulation có nhiều BookingTransaction.

- **Ví dụ giải thích multiplicity `Booking "0..1" --> "1..4" MatchSeat`:**
  - Một Booking phải chọn ít nhất một và tối đa bốn MatchSeat.
  - Một MatchSeat nằm trong tối đa một Booking thành công.
  - Multiplicity này biểu diễn trực tiếp rule chống double booking.
- **Muốn giải thích từng đường nối của mỗi trang:**
  - Xem file thuyết trình riêng của trang 1 đến trang 6 trong cùng thư mục.
  - Script này dùng để dẫn dắt người nghe; các file riêng liệt kê chi tiết từng
    relationship và lý do chọn quan hệ đó.

## 18. Nối sáu trang thành một luồng

- **Tình huống:** Fan id 12 đặt A10 và A11 của M01.
- **Trang 1:**
  - Login Fan.
  - Tìm M01 và xem seat map.
- **Trang 2:**
  - Admin đã tạo Stadium, Section, Seat, Match, MatchSeat và TicketPrice.
- **Trang 3:**
  - BookingService kiểm tra A10-A11, payment và tạo Ticket.
- **Trang 5:**
  - Repository ghi Booking, Payment, Ticket và MatchSeat vào CSV.
- **Trang 6:**
  - Relationship bảo đảm Booking thuộc Fan/Match và Ticket thuộc MatchSeat.
- **Trang 4:**
  - Simulator tạo nhiều task gọi cùng quy tắc cập nhật MatchSeat.
- **Ý nghĩa:**
  - Sáu trang là sáu góc nhìn của cùng hệ thống.
  - Simulation không phải chương trình riêng.
  - `MatchSeat` là điểm nối giữa xem ghế, booking, ticket và concurrency.

## 19. Câu kết phần Use Case và Class Diagram

- **Ý 1:** Use Case xác định actor và feature.
- **Ý 2:** Class Diagram phân công feature cho class và layer.
- **Ý 3:** Relationship giữa layer kiểm soát dependency.
- **Ý 4:** Relationship giữa Model kiểm soát cấu trúc dữ liệu và business rule.
- **Ý 5:** Fan, Seller và Simulator dùng chung `MatchSeat` để tránh ba quy tắc booking khác nhau.
- **Câu nói gợi ý:**
  - “Tóm lại, Use Case cho biết hệ thống phải làm gì, còn Class Diagram cho biết
    trách nhiệm đó được chia cho class nào. Điểm trung tâm là MatchSeat vì Guest
    dùng nó để xem ghế, Fan và Seller dùng nó để booking, Ticket dùng nó để xác
    định chỗ ngồi và Simulator dùng nó để kiểm tra double booking.”
