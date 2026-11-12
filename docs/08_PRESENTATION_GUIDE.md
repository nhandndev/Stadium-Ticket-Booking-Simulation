# Kịch bản tổng quan dự án

## Giới thiệu

- Em là Đoàn Ngọc Nhân, QE210282. Đề tài của em là Stadium Ticket Booking.
- Dự án dùng Java thuần, giao diện Console và lưu dữ liệu bằng CSV.
- Bài toán là quản lý trận đấu và bán vé theo từng ghế, với các vai trò rõ ràng.

## Bài toán và cách giải quyết

- Administrator chuẩn bị sân, khu vực, ghế, trận đấu, giá và mở bán.
- Guest tìm trận, xem chi tiết và tình trạng ghế trước khi đăng ký tài khoản.
- Fan đăng nhập, chọn tối đa bốn ghế, tạo booking và thanh toán mock.
- Seller hỗ trợ khách tại quầy, tạo booking, xác nhận đã nhận tiền và xuất vé.
- Gate Staff kiểm tra vé và xác nhận check-in một lần.
- Fan có thể huỷ booking chưa trả tiền hoặc gửi yêu cầu hoàn tiền cho booking
  đủ điều kiện. Administrator duyệt yêu cầu và hệ thống trả ghế khi chấp thuận.

## Điểm cần giải thích khi demo

- Booking history là lịch sử đơn đặt, tiền và trạng thái đơn.
- Ticket history là từng vé đã phát hành, thông tin ghế và trạng thái vào cổng.
- Seat mô tả ghế vật lý. MatchSeat lưu tình trạng ghế trong một trận cụ thể.
- PENDING lưu báo giá. Khi thanh toán, hệ thống kiểm tra lại ghế.
- Cơ chế SYNCHRONIZED mặc định bao quanh bước kiểm tra và cập nhật ghế. Hai
  lượt thanh toán cùng ghế trong một JVM sẽ lần lượt vào vùng xử lý này.
- Nếu lượt trước đã mua thành công, lượt sau thấy ghế BOOKED và nhận xung đột.
- Payment/refund là mock, mật khẩu CSV là dữ liệu demo.

## Kiến trúc

- Main khởi tạo các đối tượng và chạy MainController.
- Controller điều phối View để nhập/in Console rồi gọi Service.
- Service kiểm tra nghiệp vụ và gọi Repository để đọc/ghi CSV.
- Model giữ trạng thái. Request/Response DTO chuyển dữ liệu giữa các tầng.

## Thứ tự trình bày sơ đồ

- Trang 00 giải thích actor và các chức năng trong Use Case.
- Trang 01 giải thích Main, tài khoản, đăng nhập và duyệt trận.
- Trang 02 giải thích quản trị sân, trận, giá và tài khoản.
- Trang 03 giải thích booking, payment, ticket, refund và bảo vệ đặt ghế.
- Trang 04 giải thích CsvRepository và các repository cụ thể.
- Trang 05 giải thích quan hệ giữa các model và multiplicity.

## Demo

- Theo [bộ demo hiện tại](Demo_Tung_Feature/README.md).
- Chạy trên bản sao CSV theo thứ tự để ID và trạng thái khớp.
- Mỗi file có mục đích, phím nhập, Console trả về và ví dụ bị chặn.
