# Bản đồ menu

## Main và chức năng dùng chung

```text
1. Browse As Guest
2. Login As Fan
3. Login As Staff
4. Register Fan Account
0. Exit System
```

Tài khoản đã đăng nhập có xem/cập nhật profile và logout.
Controller kiểm tra role khi điều phối vào chức năng.

## Guest và browsing của Fan

```text
1. Search matches by team keyword
2. Filter matches / List all schedule
3. View match details
4. View seat availability / Seat map
0. Back
```

Kết quả chưa chọn trận yêu cầu Match ID một lần. Sau khi chọn, menu giữ Match ID
cho xem chi tiết, xem ghế và Book this match của Fan. Choose another match xoá
lựa chọn. Thoát màn hình kết thúc ngữ cảnh chọn trận.

## Fan

```text
1. View profile
2. Tickets and bookings
   1. Book tickets
      1. View seat map and prices
      2. Create booking
   2. My bookings and payments
      1. Booking history
      2. Booking details
      3. Pay pending booking
   3. My tickets
      1. Ticket history
      2. Ticket details
   4. Cancellations and refunds
      1. Request refund
      2. Cancel unpaid pending booking
      3. My refund requests
3. Update profile
4. Browse matches
0. Logout
```

Create booking giữ tối đa bốn ghế trong hạn thanh toán đã lưu. Fan trả online
ngay hoặc chọn trả sau. Seller nhận tiền offline bằng mã booking trong cùng hạn.
Booking EXPIRED vẫn có trong lịch sử và ghế được giải phóng.

## Seller

Dashboard chọn 2. Sales counter để vào đúng bảy feature.

```text
1. Search Fan
2. Create Fan
3. Create booking for Fan / walk-in customer
4. Accept offline payment
5. Issue ticket
6. View seat map and prices
7. View customer booking history
0. Back to dashboard
```

- Mục 3 chọn tài khoản ACTIVE hoặc khách vãng lai, rồi chọn Match/Seat IDs để tạo PENDING.
- Mục 4 chỉ xác nhận đã nhận tiền offline cho booking còn hạn.
- Mục 5 phát hành vé của booking CONFIRMED đã có thanh toán thành công.
- Booking ID phục vụ hai thao tác 4 và 5.
- Ghế HELD là ghế đang được giữ, BOOKED là ghế đã mua thành công.

## Gate Staff

Dashboard chọn 2, sau đó chọn Validate ticket hoặc Check in ticket.
Nhập mã vé và Match ID. Check-in yêu cầu xác nhận, ghi thời điểm và từ chối dùng lại.

## Administrator

```text
1. Venues and seating
   1. Stadiums
   2. Sections
   3. Seats
2. Matches, pricing and sales
   1. Matches
   2. Ticket pricing
   3. Ticket sales
3. Accounts and roles
   1. Fans
   2. Staff
   3. Staff roles
4. Refund reviews
5. Reports and audit logs
   1. System summary
   2. Audit logs
0. Back to dashboard
```

Các menu con chứa thao tác CRUD/quản lý trạng thái theo SRS. Đọc file tính năng
để lấy đúng số phím và Console sau từng lần Enter.
