# Class Diagram trang 1 - Main, View và Authentication

## Mục đích của trang

**Lời thuyết trình:**

Trang đầu mô tả điểm khởi động chương trình, điều hướng menu, đăng ký, đăng nhập,
cập nhật profile và các chức năng browse dành cho Guest. Em đọc sơ đồ từ trên
xuống theo từng layer.

## View và Controller

**Chỉ trên sơ đồ:** `Main`, `MainView`, `BookingView`, sau đó xuống các Controller.

**Lời thuyết trình:**

`Main` chỉ có nhiệm vụ khởi tạo các dependency cần thiết và gọi `MainView.start`.
`MainView` là menu điều hướng trung tâm. Nó có các menu Guest, Fan, Seller, Gate
Staff, Admin và Simulator. `BookingView` phụ trách menu booking, lịch sử booking,
ticket và refund của Fan.

View không tự đăng nhập hoặc truy cập CSV. Nó gọi `AuthController`,
`FanController` và `BrowseController`. `AuthController` tiếp nhận
`LoginRequestDto`, quản lý logout, current user và cập nhật profile.
`FanController` xử lý đăng ký Fan và truy vấn ticket của Fan.
`BrowseController` cung cấp tìm kiếm, lọc trận, xem chi tiết và lấy seat map.

## Service

**Lời thuyết trình:**

Mỗi Controller gọi Service tương ứng. `AuthService` tìm tài khoản trong
`FanRepository` hoặc `StaffRepository`, kiểm tra thông tin đăng nhập và giữ
`currentUser` cho phiên Console hiện tại. Sau khi đăng nhập, role trong
`UserResponseDto` quyết định menu nào được mở.

`FanService` xử lý đăng ký và các truy vấn liên quan đến Fan. `BrowseService`
đọc trận đấu từ `MatchRepository` và tình trạng ghế từ `MatchSeatRepository`.
Business logic nằm ở Service để Controller chỉ làm nhiệm vụ điều phối.

## DTO

**Lời thuyết trình:**

Nhóm DTO tách dữ liệu nhập và xuất khỏi entity. `LoginRequestDto` chỉ mang email
và password. `RegisterFanRequestDto` mang dữ liệu đăng ký.
`ProfileUpdateRequestDto` chỉ chứa những trường được phép sửa.
`UserResponseDto` không trả password. Các DTO `MatchResponseDto`,
`MatchSeatResponseDto` và `TicketResponseDto` mang dữ liệu an toàn ra View.

DTO không có business logic và không đọc CSV. Service chuyển dữ liệu từ request
DTO sang model, sau đó chuyển model thành response DTO.

## Model và quan hệ kế thừa

**Lời thuyết trình:**

`BaseEntity` cung cấp id chung. `User` kế thừa `BaseEntity`, còn `Fan` và `Staff`
kế thừa `User`. Nhờ vậy các thuộc tính tài khoản chỉ định nghĩa một lần. `Match`
và `MatchSeat` cũng kế thừa `BaseEntity`.

`Match` chứa thông tin trận và trạng thái mở bán. `MatchSeat` chứa `matchId`,
`seatId`, trạng thái và `version`. Trường `version` được sử dụng cho optimistic
locking ở phần simulation.

## Luồng minh họa

**Lời thuyết trình:**

Ví dụ khi Guest tìm trận, `MainView` gọi `BrowseController.searchMatches`.
Controller chuyển lời gọi cho `BrowseService`. Service gọi `MatchRepository`,
nhận danh sách `Match`, chuyển thành `MatchResponseDto` và trả về View để in ra
Console. Luồng login cũng tương tự nhưng đi qua `AuthController`, `AuthService`
và repository của Fan hoặc Staff.

## Ý chốt của trang

Trang này chứng minh chương trình có một điểm vào rõ ràng, menu được điều hướng
theo role và View không phụ thuộc trực tiếp vào tầng lưu trữ.

## Mối quan hệ UML trên trang 1

### Cách đọc ký hiệu

- `A --> B`: association một chiều hoặc quan hệ sử dụng. `A` biết và gọi `B`,
  nhưng `B` không cần biết ngược lại `A`.
- `A --|> B`: generalization/inheritance. `A` là class con của `B` và kế thừa
  thuộc tính, phương thức phù hợp.

### Từng relationship và lý do

- `Main --> MainView`: quan hệ dependency sử dụng. `Main` tạo hoặc nhận
  `MainView` rồi gọi `start`; `MainView` không cần biết class nào đã khởi động nó.
- `MainView --> AuthController`: association một chiều vì View giữ Controller để
  login, logout và profile, còn Controller không phụ thuộc giao diện Console.
- `MainView --> FanController`: association một chiều để menu gọi đăng ký và
  chức năng Fan.
- `MainView --> BrowseController`: association một chiều để menu Guest tìm, lọc
  và xem trận mà không truy cập Repository trực tiếp.
- `MainView --> BookingView`: association điều hướng. Main menu mở màn hình
  booking; BookingView không sở hữu vòng đời MainView nên không dùng composition.
- `AuthController --> AuthService`: association một chiều vì Controller delegate
  nghiệp vụ xác thực cho Service.
- `FanController --> FanService`: association một chiều vì Controller chỉ nhận
  request và trả response, còn quy tắc Fan nằm ở Service.
- `BrowseController --> BrowseService`: association một chiều để tách thao tác
  Console khỏi logic truy vấn trận và ghế.
- `AuthService --> FanRepository`: association sử dụng vì Service cần tìm Fan
  theo email khi login.
- `AuthService --> StaffRepository`: association sử dụng vì cùng một form login
  phải nhận diện được cả tài khoản Staff.
- `FanService --> FanRepository`: association sử dụng để kiểm tra email và lưu
  Fan khi đăng ký.
- `FanService --> BookingRepository`: association sử dụng để lấy lịch sử booking
  thuộc Fan.
- `FanService --> TicketRepository`: association sử dụng để lấy ticket của Fan.
- `BrowseService --> MatchRepository`: association sử dụng để tìm, lọc và xem
  chi tiết Match.
- `BrowseService --> MatchSeatRepository`: association sử dụng để dựng seat map
  theo trạng thái ghế của từng trận.
- `Fan --|> User`: inheritance vì Fan là một loại User, dùng chung profile,
  email, password hash, role và status, nhưng có thêm quy tắc mua vé.
- `Staff --|> User`: inheritance vì Staff cũng là User nhưng được phân biệt bằng
  role vận hành.
- `User --|> BaseEntity`: inheritance để User dùng id chung của entity.
- `Match --|> BaseEntity`: inheritance để Match có identity và dùng được CRUD
  generic của Repository.
- `MatchSeat --|> BaseEntity`: inheritance vì mỗi bản ghi tồn kho ghế theo trận
  cần id riêng ngoài cặp `matchId` và `seatId`.

### Vì sao DTO không nối relationship?

DTO xuất hiện trong chữ ký method, nhưng sơ đồ cố ý không nối từng DTO vào
Controller và Service. Nếu nối tất cả sẽ tạo quá nhiều dây mà không thêm ý nghĩa
về ownership. Dependency DTO đã được thể hiện qua kiểu tham số và kiểu trả về.
