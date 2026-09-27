# Thuyết trình Use Case hệ thống

## Cách đọc sơ đồ

**Lời thuyết trình:**

Đây là Use Case tổng quan của Stadium Ticket Booking and Concurrency Simulation
System. Khung chữ nhật là ranh giới hệ thống. Actor nằm ngoài khung, còn các
hình oval bên trong là feature mà hệ thống cung cấp cho actor đó.

Đường liền giữa actor và use case là association, nghĩa là actor trực tiếp khởi
tạo hoặc tham gia feature. Quan hệ kế thừa actor cho biết actor con được sử dụng
các feature chung của actor cha. Sơ đồ hiện tại tập trung vào feature nhìn thấy
từ người dùng nên không vẽ dày đặc `include` và `extend`; thứ tự xử lý nội bộ sẽ
được giải thích trong Class Diagram và luồng nghiệp vụ.

Trong thiết kế class, `Fan` và `Staff` kế thừa `User`. Seller, Gate Staff,
Administrator và Simulator Operator là các vai trò của Staff được định danh bằng
`UserRole`. Nhờ đó hệ thống dùng chung thông tin tài khoản nhưng vẫn mở đúng menu
và đúng quyền sau khi đăng nhập.

## Guest

### Actor này để làm gì?

**Lời thuyết trình:**

Guest đại diện cho người chưa đăng nhập. Mục đích của actor này là cho phép khách
tìm hiểu trận đấu và chỗ ngồi trước khi quyết định tạo tài khoản. Guest không
được đặt vé vì hệ thống chưa có danh tính để gắn với Booking và Ticket.

### Từng feature của Guest

- **Search Matches:** tìm trận theo từ khóa như tên đội. Feature này giúp Guest
  thu hẹp danh sách thay vì xem toàn bộ dữ liệu. Ví dụ: nhập `Vietnam` để tìm
  các trận có đội tuyển Việt Nam.
- **Filter Matches:** lọc trận theo sân hoặc ngày. Search xử lý từ khóa, còn
  filter xử lý điều kiện có cấu trúc. Ví dụ: chỉ xem các trận tại sân Mỹ Đình
  trong ngày 20 tháng 10.
- **View Match Details:** xem đội nhà, đội khách, sân, thời gian và trạng thái mở
  bán của một trận cụ thể. Ví dụ: chọn mã trận `M01` để xem Việt Nam gặp Thái
  Lan lúc 19 giờ và vé đang mở bán.
- **View Seat Availability:** xem sơ đồ ghế và trạng thái còn trống của ghế theo
  trận. Dữ liệu phải lấy từ `MatchSeat`, không lấy trực tiếp từ `Seat`. Ví dụ:
  ghế A12 available ở trận `M01` nhưng đã booked ở trận `M02`.
- **Register:** tạo tài khoản Fan từ họ tên, email, mật khẩu và số điện thoại.
  Email phải không trùng tài khoản đã tồn tại. Ví dụ: đăng ký bằng
  `nhan@gmail.com`; nếu email đã có thì hệ thống yêu cầu dùng email khác.
- **Login:** xác thực tài khoản. Nếu thành công, hệ thống đọc role và điều hướng
  đến menu Fan hoặc menu Staff tương ứng. Ví dụ: tài khoản có role `SELLER` sẽ
  được đưa vào Seller menu, không phải Fan menu.

## User

### Actor này để làm gì?

**Lời thuyết trình:**

User là actor tổng quát cho tài khoản đã đăng nhập. Actor này gom các feature mà
mọi tài khoản đều có để sơ đồ không phải nối lặp lại vào Fan, Seller, Gate Staff,
Administrator và Simulator Operator.

### Từng feature của User

- **Login:** tạo phiên người dùng hiện tại sau khi kiểm tra email, mật khẩu và
  trạng thái tài khoản. Ví dụ: tài khoản đúng mật khẩu nhưng đang inactive vẫn
  bị từ chối đăng nhập.
- **Logout:** xóa current user và quay lại menu Guest. Ví dụ: Seller chọn Logout,
  hệ thống xóa phiên Seller rồi hiển thị lại menu công khai.
- **Update Profile Information:** cập nhật họ tên và số điện thoại. Role, status
  và id không được người dùng tự sửa qua feature này. Ví dụ: Fan đổi số điện
  thoại nhưng không thể tự đổi role thành Administrator.

## Fan

### Actor này để làm gì?

**Lời thuyết trình:**

Fan là khách hàng mua vé cho chính mình. Booking, Payment và Ticket đều được gắn
với Fan để hệ thống có thể hiển thị đúng lịch sử và kiểm soát quyền xem dữ liệu.

### Từng feature của Fan

- **Select Seats:** chọn từ một đến tối đa bốn ghế của một trận. Ghế vật lý phải
  active và `MatchSeat` phải đang available. Ví dụ: Fan chọn A10 và A11; nếu
  chọn năm ghế thì hệ thống báo vượt giới hạn.
- **Create Booking:** gửi yêu cầu đặt các ghế đã chọn. Hệ thống kiểm tra lại trận,
  trạng thái mở bán, giá và ghế ngay tại thời điểm xử lý. Ví dụ: A10 vừa được
  người khác đặt trước lúc xác nhận thì booking bị từ chối hoặc yêu cầu chọn lại.
- **Make Online Payment:** thanh toán booking qua Payment Service giả lập. Payment
  thành công mới cho phép hoàn tất luồng online. Ví dụ: booking có tổng tiền
  500.000 đồng được mock payment trả thành công rồi mới phát hành vé.
- **View Booking History:** xem các booking thuộc Fan hiện tại, không được xem
  booking của Fan khác. Ví dụ: Fan id 12 chỉ thấy các booking có `fanId = 12`.
- **View Booking Details:** xem trận, danh sách ghế, tổng tiền và trạng thái của
  một booking cụ thể. Ví dụ: mở booking `B105` để xem trận `M01`, ghế A10-A11,
  tổng tiền và trạng thái confirmed.
- **View Tickets:** xem danh sách vé được phát hành từ các booking của Fan.
  Ví dụ: booking hai ghế thành công tạo hai ticket trong danh sách của Fan.
- **View Ticket Details:** xem mã vé, trận, ghế, trạng thái và thời điểm check-in.
  Ví dụ: mở ticket `T-A10-001` để kiểm tra ghế A10 còn hiệu lực.
- **View Tickets History:** xem toàn bộ lịch sử vé, bao gồm vé đã check-in, bị hủy
  hoặc các trạng thái khác theo enum. Ví dụ: Fan vẫn thấy ticket trận cũ ở trạng
  thái `CHECKED_IN`.
- **Request Refund:** gửi booking, lý do và danh tính Fan để Administrator xét
  duyệt. Gửi yêu cầu không đồng nghĩa refund đã được chấp nhận. Ví dụ: Fan gửi
  refund cho `B105` với lý do không thể tham dự và trạng thái ban đầu là requested.

### Luồng chính của Fan

**Lời thuyết trình:**

Fan tìm trận, xem ghế, chọn ghế và tạo booking. Service kiểm tra lại `MatchSeat`,
tính tiền rồi gọi mock online payment. Nếu thành công, booking được xác nhận,
ghế được đánh dấu booked, ticket và transaction được tạo. Nếu thất bại, hệ thống
không được để ghế ở trạng thái booked sai.

## Seller

### Actor này để làm gì?

**Lời thuyết trình:**

Seller là nhân viên bán vé tại quầy. Actor này hỗ trợ khách không tự thao tác
trên tài khoản Fan nhưng vẫn bảo đảm booking và ticket cuối cùng thuộc về Fan.

### Từng feature của Seller

- **Search Fan:** tìm khách bằng tên, email hoặc từ khóa phù hợp trước khi tạo
  booking, tránh tạo trùng tài khoản. Ví dụ: Seller tìm bằng số điện thoại hoặc
  email của khách tại quầy.
- **Create Fan:** tạo nhanh tài khoản Fan nếu khách chưa tồn tại. Ví dụ: Seller
  nhập họ tên, email và điện thoại để tạo account trước khi bán vé.
- **Create Booking for Fan:** chọn Fan, trận và ghế rồi tạo booking thay cho
  khách. Luồng này dùng chung quy tắc kiểm tra ghế với Fan booking. Ví dụ: Seller
  chọn Fan id 12 và đặt ghế B05 của trận `M01`.
- **Accept Offline Payment:** ghi nhận tiền mặt hoặc hình thức thanh toán tại
  quầy; không gọi Payment Service online. Ví dụ: khách trả 250.000 đồng tiền mặt,
  Seller xác nhận đã nhận đủ tiền.
- **Issue Ticket:** phát hành hoặc hiển thị vé sau khi booking và offline payment
  đã hợp lệ. Seller không được issue ticket trước khi xác nhận booking. Ví dụ:
  hệ thống tạo mã ticket cho ghế B05 để Seller giao cho khách.

## Gate Staff

### Actor này để làm gì?

**Lời thuyết trình:**

Gate Staff là nhân viên tại cổng sân. Mục tiêu của actor này là bảo đảm chỉ vé
hợp lệ được sử dụng và một vé không thể check-in nhiều lần.

### Từng feature của Gate Staff

- **Validate Ticket:** tìm ticket theo code, kiểm tra đúng trận và kiểm tra trạng
  thái chưa bị hủy, refund hoặc check-in. Ví dụ: mã `T-A10-001` hợp lệ cho trận
  `M01`, nhưng bị từ chối nếu dùng ở trận `M02`.
- **Check-in Ticket:** sau khi validate thành công, đổi trạng thái ticket thành
  `CHECKED_IN` và lưu thời điểm. Lần check-in tiếp theo phải bị từ chối. Ví dụ:
  ticket check-in lúc 18:45 sẽ báo đã sử dụng nếu quét lại lúc 18:50.

## Administrator

### Actor này để làm gì?

**Lời thuyết trình:**

Administrator quản lý dữ liệu nền và vận hành hệ thống. Actor này chuẩn bị sân,
ghế, trận và giá để Fan có thể booking; đồng thời quản lý tài khoản, trạng thái
bán vé, refund và theo dõi hệ thống.

### Stadium Management

- **Create Stadium:** tạo sân mới từ tên và địa chỉ. Ví dụ: tạo sân Mỹ Đình tại
  Nam Từ Liêm, Hà Nội.
- **View Stadium List:** xem toàn bộ sân để chọn đối tượng quản lý. Ví dụ: Admin
  xem danh sách năm sân trước khi tạo trận.
- **View Stadium Details:** xem thông tin chi tiết của một sân. Ví dụ: chọn id 1
  để xem tên, địa chỉ và các thông tin liên quan.
- **Update Stadium:** sửa tên hoặc địa chỉ sân. Ví dụ: cập nhật địa chỉ sau khi
  dữ liệu ban đầu bị nhập sai.
- **Delete Stadium:** xóa sân khi không có dữ liệu phụ thuộc cản trở. Ví dụ: sân
  đã có Match sẽ bị từ chối xóa để không làm hỏng lịch sử.

### Section Management

- **Create Section:** tạo khu vực thuộc một stadium. Ví dụ: tạo khu vực khán đài A
  cho sân id 1.
- **View Sections by Stadium:** xem đúng các khu vực của sân đã chọn. Ví dụ: chọn
  sân Mỹ Đình để xem các section A, B, C và D.
- **View Section Details:** xem id, stadium cha và tên section. Ví dụ: section 10
  hiển thị thuộc stadium 1 và có tên khán đài A.
- **Update Section:** đổi thông tin section. Ví dụ: đổi tên `A` thành `A Premium`.
- **Delete Section:** xóa section khi không vi phạm quan hệ với seat và dữ liệu
  giao dịch. Ví dụ: section còn Seat sẽ không bị xóa trực tiếp.

### Seat Management

- **Create Seat:** tạo ghế với section, hàng và số ghế. Ví dụ: tạo ghế A12 trong
  section 10.
- **View Seats by Section:** dựng danh sách hoặc seat map theo khu vực. Ví dụ: xem
  toàn bộ ghế hàng A của khán đài A.
- **View Seat Details:** xem vị trí và trạng thái active của ghế. Ví dụ: ghế A12
  đang inactive vì bảo trì.
- **Update Seat:** sửa hàng hoặc số ghế. Ví dụ: sửa ghế nhập nhầm từ A21 thành A12.
- **Delete Seat:** xóa ghế nếu business rule cho phép. Ví dụ: ghế chưa xuất hiện
  trong MatchSeat hoặc Ticket mới được phép xóa.
- **Activate Seat:** đưa ghế vật lý trở lại sử dụng. Ví dụ: ghế A12 được sửa xong
  và chuyển từ inactive sang active.
- **Deactivate Seat:** ngừng sử dụng ghế hỏng hoặc đang bảo trì. Deactivate khác
  với booked vì nó không đại diện cho một trận cụ thể. Ví dụ: khóa ghế A12 ở mọi
  trận mới do ghế bị hỏng.

### Match Management

- **Create Match:** tạo trận với stadium, đội nhà, đội khách và thời gian. Ví dụ:
  tạo trận Việt Nam gặp Thái Lan tại sân Mỹ Đình lúc 19 giờ.
- **View Match List:** xem danh sách trận. Ví dụ: Admin xem các trận sắp diễn ra
  theo thời gian tăng dần.
- **View Match Details:** xem đầy đủ thông tin và trạng thái mở bán. Ví dụ: trận
  `M01` đang có trạng thái `ON_SALE`.
- **Update Match:** cập nhật thông tin hoặc thời gian trận. Ví dụ: dời trận từ 19
  giờ sang 20 giờ.
- **Delete Match:** xóa khi chưa có booking hoặc dữ liệu phụ thuộc cho phép.
  Ví dụ: trận đã bán vé sẽ không được xóa tùy tiện.

### Ticket Pricing Management

- **Create Ticket Price for a Match and Section:** đặt giá cho một khu vực trong
  một trận cụ thể. Ví dụ: section A của trận `M01` có giá 500.000 đồng.
- **View Ticket Prices by Match:** xem bảng giá của toàn bộ khu vực trong trận.
  Ví dụ: trận `M01` có khu A giá 500.000 và khu B giá 300.000 đồng.
- **View Ticket Price Details:** xem giá của đúng cặp match-section. Ví dụ: tra
  giá của `M01 + Section A`.
- **Update Ticket Price:** thay đổi số tiền. Ví dụ: đổi khu B từ 300.000 thành
  350.000 đồng trước khi bán.
- **Delete Ticket Price:** xóa cấu hình giá khi chưa ảnh hưởng booking đã có.
  Ví dụ: giá nhập nhầm được xóa trước lúc mở bán.

### Ticket Sales Management

- **Open Ticket Sales:** chuyển match sang trạng thái cho phép booking. Ví dụ:
  Admin mở bán `M01`, từ lúc đó Fan mới tạo booking được.
- **Close Ticket Sales:** ngăn booking mới nhưng không xóa booking cũ. Ví dụ: đóng
  bán 30 phút trước giờ trận bắt đầu.
- **View Ticket Sales Status:** kiểm tra trạng thái hiện tại trước khi vận hành.
  Ví dụ: Admin xem `M01` đang `ON_SALE` hay `CLOSED`.

### Fan Management

- **Create Fan Account:** Admin tạo tài khoản Fan. Ví dụ: tạo account cho khách
  được nhập từ danh sách đăng ký trực tiếp.
- **View Fans:** xem danh sách Fan. Ví dụ: hiển thị tất cả Fan theo id.
- **View Fan Details:** xem một Fan cụ thể. Ví dụ: mở Fan id 12 để xem profile và
  trạng thái.
- **Search Fan:** tìm theo từ khóa. Ví dụ: tìm `nhan@gmail.com`.
- **Filter Fan:** lọc theo trạng thái tài khoản. Ví dụ: chỉ xem Fan inactive.
- **Update Fan Information:** chỉnh thông tin Fan. Ví dụ: sửa số điện thoại theo
  yêu cầu xác minh của khách.
- **Activate Fan Account:** cho phép tài khoản hoạt động trở lại. Ví dụ: mở lại
  tài khoản sau khi Admin xác minh.
- **Deactivate Fan Account:** chặn đăng nhập hoặc thao tác mới nhưng giữ lịch sử.
  Ví dụ: vô hiệu hóa account vi phạm nhưng vẫn giữ Booking và Ticket.
- **Delete Fan Account:** chỉ xóa khi quy tắc dữ liệu cho phép. Ví dụ: tài khoản
  thử nghiệm chưa có booking có thể được xóa.

### Staff And Role Management

- **Create Staff Account:** tạo tài khoản nhân viên. Ví dụ: tạo account mới cho
  nhân viên quầy vé.
- **View Staffs:** xem danh sách Staff. Ví dụ: xem toàn bộ nhân viên đang active.
- **View Staff Details:** xem thông tin và role của một Staff. Ví dụ: Staff id 5
  đang có role `SELLER`.
- **Search Staff:** tìm nhân viên theo từ khóa. Ví dụ: tìm bằng email công ty.
- **Filter Staff:** lọc theo role hoặc status. Ví dụ: chỉ xem các `GATE_STAFF`
  đang active.
- **Update Staff Information:** sửa thông tin nhân viên. Ví dụ: đổi số điện thoại.
- **Activate Staff Account:** cho phép tài khoản hoạt động. Ví dụ: mở lại account
  khi nhân viên quay lại làm việc.
- **Deactivate Staff Account:** ngăn nhân viên tiếp tục đăng nhập. Ví dụ: khóa
  account khi nhân viên nghỉ việc nhưng giữ audit log.
- **Assign Staff Role:** gán role lần đầu. Ví dụ: gán `SELLER` cho account mới.
- **Change Staff Role:** chuyển sang role khác. Ví dụ: chuyển nhân viên từ
  `SELLER` sang `GATE_STAFF`.
- **Revoke Staff Role:** thu hồi quyền nghiệp vụ hiện tại. Ví dụ: thu hồi role để
  account không còn truy cập menu Staff tương ứng.

### System Monitoring và Refund

- **View System Summary:** tổng hợp số Fan, Staff, Stadium, Match, Booking và
  Ticket để Admin nắm trạng thái dữ liệu. Ví dụ: màn hình báo 500 Fan, 20 Staff,
  3 Stadium và 1.200 Booking.
- **View Audit Log:** xem ai đã thực hiện hành động quản trị nào và vào lúc nào.
  Ví dụ: log ghi Admin id 1 đã đóng bán trận `M01` lúc 18 giờ.
- **Review Refund Request:** xem yêu cầu đang chờ, kiểm tra booking rồi approve
  hoặc reject kèm ghi chú. Ví dụ: Admin approve request `R10` và ghi chú
  `Trận đấu bị hủy`.

## Simulator Operator

### Actor này để làm gì?

**Lời thuyết trình:**

Simulator Operator vận hành thí nghiệm concurrency. Actor này không đại diện cho
khách mua vé thật mà tạo nhiều booking attempt có kiểm soát để so sánh cơ chế
đồng bộ.

### Từng feature của Simulator Operator

- **Configure Simulation:** chọn match, ghế mục tiêu, số thread và mechanism.
  Ví dụ: chọn trận `M01`, ghế A12 và 1.000 thread.
- **Select Synchronization Mechanism:** chọn `NO_LOCK`, `SYNCHRONIZED`,
  `FILE_LOCK` hoặc `OPTIMISTIC`. Ví dụ: chạy lần đầu bằng `NO_LOCK`, sau đó giữ
  nguyên cấu hình và đổi sang `SYNCHRONIZED`.
- **Run Simulation:** tạo thread pool, đồng bộ thời điểm bắt đầu và chạy các
  `BookingTask`. Ví dụ: 1.000 task cùng chờ latch rồi tranh ghế A12.
- **View / Compare Results:** xem attempt, success, failure, conflict, double
  booking, thời gian và throughput; có thể so sánh cùng cấu hình giữa các cơ chế.
  Ví dụ: `NO_LOCK` có hai success cho A12, còn `SYNCHRONIZED` chỉ có một success.
- **Export Result:** ghi kết quả ra file để làm báo cáo và minh chứng. Ví dụ: xuất
  kết quả bốn mechanism thành file CSV để đưa vào bảng so sánh.

## Payment Service

### Actor này để làm gì?

**Lời thuyết trình:**

Payment Service là actor hệ thống bên ngoài, không phải người dùng. Nó nhận yêu
cầu xử lý online payment từ hệ thống và trả kết quả thành công hoặc thất bại.
Trong Java Console, actor này được giả lập bằng `MockPaymentService`, không tích
hợp cổng thanh toán thật.

### Feature của Payment Service

- **Process Online Payment:** nhận booking và amount, trả kết quả để hệ thống
  quyết định confirm hay không confirm booking. Ví dụ: booking `B105` trị giá
  500.000 đồng nhận kết quả success; nếu failed thì hệ thống không issue ticket.

## Quan hệ tổng quát giữa các actor

**Lời thuyết trình:**

Guest chưa có phiên đăng nhập. User là actor tổng quát của tài khoản đã xác thực.
Fan là khách hàng; Staff là nhóm tài khoản vận hành. Seller, Gate Staff,
Administrator và Simulator Operator dùng feature chung của User nhưng chỉ truy
cập menu nghiệp vụ đúng với role. Payment Service đứng ngoài hệ thống và chỉ
tham gia payment online.

## Câu chuyển sang Class Diagram

**Lời thuyết trình:**

Use Case vừa xác định actor và toàn bộ feature cần có. Phần tiếp theo giải thích
các class hiện thực feature đó, loại relationship giữa chúng và lý do vì sao mỗi
relationship được chọn.
