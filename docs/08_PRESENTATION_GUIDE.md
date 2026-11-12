# Kịch bản thuyết trình dự án

## Bài toán của nhóm

- Nhóm xây dựng hệ thống đặt vé sân vận động bằng Java Console, lưu CSV.
- Administrator chuẩn bị sân, khu vực, ghế, trận, giá và trạng thái mở bán.
- Guest tìm trận, xem thông tin và đăng ký tài khoản.
- Fan chọn tối đa bốn ghế rồi tạo booking.
- Seller hỗ trợ khách tại quầy, nhận tiền offline và xuất vé.
- Gate Staff xác thực và check-in vé. Administrator xử lý refund đủ điều kiện.

## Giải thích luồng booking

- Khi tạo PENDING, hệ thống lưu tổng tiền, createdAt và expiresAt.
- Mỗi ghế được giữ có trạng thái LOCKED và holdBookingId chỉ đúng booking sở hữu.
- Thời hạn tối đa 30 phút, kết thúc sớm nếu trận bắt đầu trước đó.
- Fan trả online ngay hoặc trả sau. Fan cũng có thể đưa Booking ID cho Seller
  nhận thanh toán offline trong cùng hạn.
- Hết hạn thì booking thành EXPIRED, từ chối thanh toán và trả ghế đang giữ.
- Huỷ booking chưa trả hoặc thanh toán thất bại cũng trả ghế của booking đó.
- SYNCHRONIZED bảo vệ bước kiểm tra và thay đổi trạng thái trong một JVM.

## Bảy feature Seller

1. Search Fan dùng tìm khách theo tên/email hoặc xem danh sách.
2. Create Fan dùng đăng ký tài khoản khi khách muốn có tài khoản.
3. Create booking for Fan dùng chọn khách ACTIVE, trận và ghế. Nhánh vãng lai
   mua trực tiếp tại quầy và giữ mã booking/mã vé.
4. Accept offline payment dùng nhận tiền cho booking còn hạn. Ví dụ Fan chọn
   trả sau và đưa Booking ID cho Seller.
5. Issue ticket dùng xuất vé sau khi booking đã CONFIRMED và trả tiền thành công.
6. View seat map and prices dùng xem mã ghế, giá và trạng thái.
7. View customer booking history dùng xem các đơn của tài khoản Fan đã chọn.

Tạo booking, nhận tiền offline và xuất vé là ba thao tác riêng. Tài khoản
INACTIVE bị chặn trước khi chọn trận. Nhập Seat IDs 1,2,3,4 là chọn bốn mã ghế
trong cột SEAT ID. Lỗi phải chỉ ra ghế sai và nguyên nhân.

## Phân biệt các bảng và quan hệ

- Seat là ghế vật lý. MatchSeat là ghế của một trận cụ thể.
- Booking history là lịch sử đơn/tổng tiền/trạng thái. Ticket history là từng
  vé đã phát hành và trạng thái sử dụng.
- Booking tới Fan có multiplicity 0..1 vì có nhánh khách vãng lai.
- MatchSeat.holdBookingId tham chiếu booking đang giữ ghế.
- Controller điều phối View và Service. Service gọi Repository để đọc/ghi CSV.
- Request/Response DTO chuyển dữ liệu giữa các tầng.
- Mũi tên class thể hiện quan hệ cấu trúc. Luồng thao tác được giải thích riêng.

## Đọc sơ đồ theo trang

- Trang 00 giới thiệu actor và chức năng. [Đối chiếu đầy đủ](06_USE_CASE_TRACEABILITY.md)
  có đường dẫn từ từng Use Case tới code và demo.
- Trang 01 giới thiệu Main, authentication, browsing và dịch vụ dọn booking hết hạn.
- Trang 02 giới thiệu quản trị, bao gồm kiểm tra hold đã hết hạn trước khi tắt ghế.
- Trang 03 giới thiệu booking, payment, ticket, refund, deadline và chủ sở hữu hold.
- Trang 04 giới thiệu CSV repository, phiên bản dữ liệu cũ/mới và entity chung.
- Trang 05 giới thiệu quan hệ model và multiplicity.

## Demo

Theo [chuẩn bị](Demo_Tung_Feature/00_CHUAN_BI.md) và
[thứ tự thao tác](Demo_Tung_Feature/01_THU_TU_DEMO.md). Dùng đúng bản sao đã ghi
trong từng flow để giữ ID và trạng thái khớp Console.
