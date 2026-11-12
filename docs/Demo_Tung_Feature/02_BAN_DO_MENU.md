# Bản đồ menu và luồng đọc tài liệu

## Main

```text
1. Browse As Guest
2. Login As Fan
3. Login As Staff
4. Register Fan Account
0. Exit System
```

Nhập lựa chọn ở Main trước. Staff đăng nhập xong được chuyển theo role trong CSV.

## Guest

```text
Main 1
  1. Search matches by team keyword
  2. Filter matches / List all schedule
  3. View match details
  4. View seat availability / Seat map
  0. Back
```

Tìm kiếm hoặc lọc xong có menu chọn xem chi tiết và xem ghế. Fan đang đăng nhập còn có Book this match trong menu kết quả.

## Fan

```text
Main 2 → Login
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
        1. My tickets / Ticket history
        2. Ticket details
     4. Cancellations and refunds
        1. Request refund for paid booking
        2. Cancel unpaid pending booking
        3. My refund requests
  3. Update profile
  4. Browse matches
  0. Logout
```

- Booking History hiển thị giao dịch đặt chỗ và tổng tiền. Một booking có thể chứa nhiều ghế.
- Ticket History hiển thị từng vé, mã vé và trạng thái sử dụng.
- Tạo PENDING lưu báo giá. Ghế được kiểm tra và commit khi thanh toán.
- Thanh toán online thành công phát hành vé. Thanh toán mock thất bại được minh hoạ bằng cờ riêng trong Flow 141.

## Seller

```text
Main 3 → Login Seller
  1. View profile
  2. Sales counter
     1. Customers
        1. Search / List customers
        2. Create Fan account
     2. Customer bookings
        1. View seat map and prices
        2. Create booking for customer
        3. Customer booking history
     3. Payments and ticket issue
        1. Accept cash payment
        2. Issue tickets for paid booking
  3. Update profile
  4. Browse matches
  0. Logout
```

- Chọn khách trước khi tạo booking hoặc tìm booking.
- Nhập RECEIVED tại bước thu tiền để ghi nhận cash payment.
- Vé có thể được phát hành sau thanh toán. Gọi Issue Tickets lần nữa lấy lại vé đã có.

## Administrator

```text
Main 3 → Login Administrator
  1. View profile
  2. Manage system operations
     1. Venues and seating
        1. Stadiums
           1 Create / 2 List / 3 Details / 4 Update / 5 Delete
        2. Sections
           1 Create / 2 List by stadium / 3 Details / 4 Update / 5 Delete
        3. Seats
           1 Create / 2 List by section / 3 Details / 4 Update / 5 Delete
           6 Activate / 7 Deactivate
     2. Matches, pricing and sales
        1. Matches
           1 Create / 2 List / 3 Details / 4 Update / 5 Delete
        2. Ticket pricing
           1 Create / 2 List by match / 3 Details / 4 Update / 5 Delete
        3. Ticket sales
           1 Open / 2 Close / 3 Status
     3. Accounts and roles
        1. Fan accounts
           1 Create / 2 List / 3 Details / 4 Search / 5 Filter status
           6 Update / 7 Activate / 8 Deactivate / 9 Delete
        2. Staff accounts
           1 Create / 2 List / 3 Details / 4 Search / 5 Filter role/status
           6 Update / 7 Activate / 8 Deactivate / 9 Staff roles
             1 Assign / 2 Change / 3 Revoke
     4. Refund reviews
        1. Review a request
     5. Reports and audit logs
        1. System summary
        2. Audit logs
  3. Update profile
  0. Logout
```

- Mỗi menu con có 0 để lùi một cấp. Nhập các số 0 đúng từng bước trong transcript.
- Các thao tác xoá, vô hiệu hoá, đóng bán và review có xác nhận theo màn hình thực tế.
- Menu chọn role hiển thị Seller, Gate Staff, Administrator và Unassigned.
- Duyệt refund làm huỷ vé/booking hợp lệ và trả ghế. Audit Log ghi nhận quyết định review.
- Dữ liệu phụ trong các flow Create phục vụ các flow Delete, Activate và Update phía sau.

## Gate Staff

```text
Main 3 → Login Gate Staff
  1. View profile
  2. Validate / Check in ticket
     1. Validate ticket
     2. Check in ticket
     0. Back
  3. Update profile
  0. Logout
```

Nhập mã vé và Match ID. Validate kiểm tra tính hợp lệ. Check-in cần xác nhận và chuyển sang CHECKED_IN; lần sử dụng tiếp theo bị chặn.

## Đường đi trong code

Controller điều phối menu và đọc dữ liệu qua View. Dữ liệu nhập được đóng thành DTO khi cần. Service kiểm tra nghiệp vụ, gọi Repository và trả kết quả. Controller chuyển kết quả cho View in Console.

Mỗi file tính năng ghi tên Controller/Service/Repository cụ thể để đối chiếu với code và Class Diagram.
