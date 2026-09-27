# Class Diagram trang 4 - Concurrency Simulation

## Mục đích của trang

**Lời thuyết trình:**

Trang bốn là phần trọng tâm nghiên cứu của đề tài. Mục tiêu là tạo nhiều booking
attempt chạy đồng thời, đo double booking và so sánh bốn cơ chế đồng bộ trên
cùng một kịch bản.

## Cấu hình và điều khiển

**Lời thuyết trình:**

`SimulatorView` nhận match, danh sách ghế mục tiêu, thread count và mechanism,
sau đó tạo `SimulationRequestDto`. `SimulationController` chuyển yêu cầu cho
`SimulationService`. Kết quả cấu hình trả về bằng `SimulationResponseDto`, còn
kết quả sau khi chạy dùng `SimulationResultDto`.

Việc tách hai response này là có chủ ý: cấu hình mới chỉ có id, match, số thread,
mechanism và trạng thái; các metric chỉ tồn tại sau khi simulation hoàn thành.

## SimulationService

**Lời thuyết trình:**

`SimulationService` sở hữu `ExecutorService` để quản lý thread. Service lấy
strategy từ `StrategyFactory`, tạo nhiều `BookingTask` và dùng
`CountDownLatch` để các task bắt đầu gần cùng thời điểm. Sau khi các task kết
thúc, Service tổng hợp transaction, tính metric, lưu `SimulationResult` và trả
kết quả cho `ReportView`.

`shutdown` được cung cấp để đóng thread pool đúng cách khi thoát chương trình.

## Strategy Pattern

**Lời thuyết trình:**

`BookingStrategy` định nghĩa một hàm `execute` chung. Bốn class hiện thực cùng
interface gồm `NoLockStrategy`, `SynchronizedStrategy`, `FileLockStrategy` và
`OptimisticLockStrategy`. `StrategyFactory` nhận `SyncMechanism` và trả về đúng
strategy, nhờ đó `BookingTask` không cần biết chi tiết khóa được cài đặt ra sao.

`NO_LOCK` là baseline không an toàn. `SYNCHRONIZED` khóa trong JVM và dễ hiểu.
`FILE_LOCK` phù hợp với lưu trữ CSV nhưng có thêm chi phí I/O. `OPTIMISTIC` dùng
`version`; nếu version đã thay đổi thì update thất bại hoặc thử lại theo
`maxRetries`.

## BookingTask và dữ liệu kết quả

**Lời thuyết trình:**

Mỗi `BookingTask` mang fan, match, danh sách seat, strategy và start latch. Khi
latch mở, task gọi strategy rồi trả về một `BookingTransaction`. Như vậy worker
thread không đọc input Console và không gọi View.

`SimulationResult` ghi số attempt, success, failed, conflict, double booking,
thời gian chạy. Từ đó hệ thống tính throughput và double booking rate. Kết quả
được lưu bằng `SimulationResultRepository`, sau đó có thể xem, so sánh hoặc
export.

## Cách phát hiện double booking

**Lời thuyết trình:**

Sau khi chạy, hệ thống nhóm các transaction thành công theo cặp match và seat.
Nếu cùng một cặp xuất hiện thành công nhiều hơn một lần thì đó là double booking.
Điều kiện cần bảo vệ là mỗi `matchId + seatId` chỉ có tối đa một booking thành
công hoặc một ticket hợp lệ.

## Ý chốt của trang

Điểm quan trọng nhất là bốn strategy chạy cùng dữ liệu đầu vào và cùng booking
contract. Nhờ vậy sự khác biệt trong kết quả đến từ cơ chế đồng bộ, không phải
do mỗi strategy dùng một nghiệp vụ khác nhau.

## Mối quan hệ UML trên trang 4

### View, Controller và Service

- `SimulatorView --> SimulationController`: association một chiều để gửi cấu
  hình và lệnh chạy simulation.
- `ReportView --> SimulationController`: association một chiều để lấy, so sánh
  và export kết quả qua cùng application boundary.
- `SimulationController --> SimulationService`: association một chiều vì
  Controller delegate workflow và không tự quản lý thread.

### Service tới Repository và Factory

- `SimulationService --> SimulationRepository`: lưu cấu hình và trạng thái của
  từng Simulation.
- `SimulationService --> SimulationResultRepository`: lưu, đọc và export metric.
- `SimulationService --> TransactionRepository`: thu thập từng booking attempt để
  tính kết quả.
- `SimulationService --> StrategyFactory`: yêu cầu đúng strategy theo mechanism,
  tránh `if-else` tạo strategy nằm rải rác trong Service.

Đây là association sử dụng một chiều. Repository và Factory không phụ thuộc
ngược vào `SimulationService`.

### Task và Strategy

- `BookingTask --> BookingStrategy`: association. Mỗi task nhận một strategy và
  gọi contract `execute`; task không cần biết cơ chế khóa cụ thể.
- `BookingStrategy <|.. NoLockStrategy`: realization. `NoLockStrategy` hiện thực
  interface nhưng cố ý không bảo vệ critical section để làm baseline.
- `BookingStrategy <|.. SynchronizedStrategy`: realization. Class hiện thực cùng
  contract bằng khóa `synchronized` trong JVM.
- `BookingStrategy <|.. FileLockStrategy`: realization. Class hiện thực contract
  bằng file lock cho lưu trữ CSV.
- `BookingStrategy <|.. OptimisticLockStrategy`: realization. Class hiện thực
  contract bằng kiểm tra `version` và retry giới hạn.

Ký hiệu nét đứt với tam giác rỗng là realization chứ không phải inheritance
class thông thường, vì `BookingStrategy` là interface.

### Simulation và kết quả

- `Simulation "1" --> "0..*" SimulationResult`: association một-nhiều. Một cấu
  hình có thể chưa chạy nên chưa có result, hoặc có nhiều result khi so sánh các
  mechanism.
- `Simulation "1" --> "0..*" BookingTransaction`: association một-nhiều. Mỗi
  attempt tạo một transaction gắn với simulation; các transaction là dữ liệu
  đầu vào để tổng hợp metric.

Không dùng composition ở hai đường này vì result và transaction cần được lưu
độc lập trong CSV để báo cáo và audit sau khi phiên chạy kết thúc.
