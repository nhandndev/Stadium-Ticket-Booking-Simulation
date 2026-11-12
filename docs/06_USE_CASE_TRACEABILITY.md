# Đối chiếu Use Case với tính năng

Ngày đối chiếu 12/11/2026.

- 97 class/enum trong source khớp 190 khung class hiển thị.
- File draw.io chính thức gồm sáu trang 00–05.
- Năm file Mermaid có attributes, constructor và public methods khớp source.
- 82 Use Case node có file demo nghiệp vụ tương ứng.
- 88 file tính năng chứa 139 flow và 1469 lượt nhập/phản hồi đã chạy.
- BookingConcurrencySmoke và SeedDataSmoke dùng fixture/bản sao riêng.
- Booking thanh toán online/cash dùng SYNCHRONIZED mặc định.
- Staff ID 5 chuyển sang UNASSIGNED. Transaction CSV hiện có 11 cột.

## Đối chiếu từng Use Case

- Search Matches → [Search Matches](Demo_Tung_Feature/01_Guest/01_Search_Matches.md).
- Filter Matches → [Filter Matches and List Schedule](Demo_Tung_Feature/01_Guest/04_Filter_Matches_and_List_Schedule.md).
- View match details → [View Match Details](Demo_Tung_Feature/04_Admin_Matches_Prices/03_View_Match_Details.md), [View Match Details](Demo_Tung_Feature/01_Guest/02_View_Match_Details.md).
- View seat availability → [View Seat Availability](Demo_Tung_Feature/01_Guest/03_View_Seat_Availability.md).
- Register → [Register Fan Account](Demo_Tung_Feature/02_Authentication_User/01_Register_Fan_Account.md).
- Create booking → [Create Booking](Demo_Tung_Feature/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Select seats → [Create Booking](Demo_Tung_Feature/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Make online payment → [Pay Pending Booking](Demo_Tung_Feature/05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md).
- View tickets → [Ticket History](Demo_Tung_Feature/05_Fan_Booking_Tickets/06_Ticket_History.md).
- View booking history → [Booking History](Demo_Tung_Feature/05_Fan_Booking_Tickets/03_Booking_History.md).
- Request refund → [Request Refund](Demo_Tung_Feature/07_Cancellation_Refund/02_Request_Refund.md).
- Search Fan → [Search Fan](Demo_Tung_Feature/09_Admin_Accounts_Roles/04_Search_Fan.md), [Search and List Customers](Demo_Tung_Feature/06_Seller/01_Search_and_List_Customers.md).
- issue ticket → [Issue Tickets](Demo_Tung_Feature/06_Seller/07_Issue_Tickets.md).
- Create booking for Fan → [Create Booking for Customer](Demo_Tung_Feature/06_Seller/04_Create_Booking_for_Customer.md).
- Accept offline payment → [Accept Cash Payment](Demo_Tung_Feature/06_Seller/06_Accept_Cash_Payment.md).
- Validate ticket → [Validate Ticket](Demo_Tung_Feature/08_Gate/01_Validate_Ticket.md).
- Check-in ticket → [Check In Ticket](Demo_Tung_Feature/08_Gate/02_Check_In_Ticket.md).
- Process mock online payment → [Pay Pending Booking](Demo_Tung_Feature/05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md).
- Logout → [Logout](Demo_Tung_Feature/02_Authentication_User/06_Logout.md).
- Update Profile Information → [Update Profile](Demo_Tung_Feature/02_Authentication_User/05_Update_Profile.md).
- Login As Fan / Login As Staff → [Login As Fan](Demo_Tung_Feature/02_Authentication_User/02_Login_As_Fan.md), [Login As Staff](Demo_Tung_Feature/02_Authentication_User/03_Login_As_Staff.md).
- Create Stadium → [Create Stadium](Demo_Tung_Feature/03_Admin_Venues/01_Create_Stadium.md).
- View Stadium List → [View Stadium List](Demo_Tung_Feature/03_Admin_Venues/02_View_Stadium_List.md).
- View Stadium Details → [View Stadium Details](Demo_Tung_Feature/03_Admin_Venues/03_View_Stadium_Details.md).
- Update Stadium → [Update Stadium](Demo_Tung_Feature/03_Admin_Venues/04_Update_Stadium.md).
- Delete Stadium → [Delete Stadium](Demo_Tung_Feature/03_Admin_Venues/05_Delete_Stadium.md).
- Create Section → [Create Section](Demo_Tung_Feature/03_Admin_Venues/06_Create_Section.md).
- View Sections by Stadium → [View Sections by Stadium](Demo_Tung_Feature/03_Admin_Venues/07_View_Sections_by_Stadium.md).
- View Section Details → [View Section Details](Demo_Tung_Feature/03_Admin_Venues/08_View_Section_Details.md).
- Update Section → [Update Section](Demo_Tung_Feature/03_Admin_Venues/09_Update_Section.md).
- Delete Section → [Delete Section](Demo_Tung_Feature/03_Admin_Venues/10_Delete_Section.md).
- Create Seat → [Create Seat](Demo_Tung_Feature/03_Admin_Venues/11_Create_Seat.md).
- View Seats by Section → [View Seats by Section](Demo_Tung_Feature/03_Admin_Venues/12_View_Seats_by_Section.md).
- View Seat Details → [View Seat Details](Demo_Tung_Feature/03_Admin_Venues/13_View_Seat_Details.md).
- Update Seat → [Update Seat](Demo_Tung_Feature/03_Admin_Venues/14_Update_Seat.md).
- Delete Seat → [Delete Seat](Demo_Tung_Feature/03_Admin_Venues/17_Delete_Seat.md).
- Activate Seat → [Activate Seat](Demo_Tung_Feature/03_Admin_Venues/15_Activate_Seat.md).
- Deactivate Seat → [Deactivate Seat](Demo_Tung_Feature/03_Admin_Venues/16_Deactivate_Seat.md).
- Create Match → [Create Match](Demo_Tung_Feature/04_Admin_Matches_Prices/01_Create_Match.md).
- View Match List → [View Match List](Demo_Tung_Feature/04_Admin_Matches_Prices/02_View_Match_List.md).
- View Match Details → [View Match Details](Demo_Tung_Feature/04_Admin_Matches_Prices/03_View_Match_Details.md), [View Match Details](Demo_Tung_Feature/01_Guest/02_View_Match_Details.md).
- Update Match → [Update Match](Demo_Tung_Feature/04_Admin_Matches_Prices/04_Update_Match.md).
- Delete Match → [Delete Match](Demo_Tung_Feature/04_Admin_Matches_Prices/05_Delete_Match.md).
- Create Ticket Price for a Match and Section → [Create Ticket Price](Demo_Tung_Feature/04_Admin_Matches_Prices/06_Create_Ticket_Price.md).
- View Ticket Prices by Match → [View Ticket Prices by Match](Demo_Tung_Feature/04_Admin_Matches_Prices/07_View_Ticket_Prices_by_Match.md).
- View Ticket Price Details → [View Ticket Price Details](Demo_Tung_Feature/04_Admin_Matches_Prices/08_View_Ticket_Price_Details.md).
- Update Ticket Price → [Update Ticket Price](Demo_Tung_Feature/04_Admin_Matches_Prices/09_Update_Ticket_Price.md).
- Delete Ticket Price → [Delete Ticket Price](Demo_Tung_Feature/04_Admin_Matches_Prices/10_Delete_Ticket_Price.md).
- Open Ticket Sales → [Open Ticket Sales](Demo_Tung_Feature/04_Admin_Matches_Prices/11_Open_Ticket_Sales.md).
- Close Ticket Sales → [Close Ticket Sales](Demo_Tung_Feature/04_Admin_Matches_Prices/12_Close_Ticket_Sales.md).
- View Ticket Sales Status → [View Ticket Sales Status](Demo_Tung_Feature/04_Admin_Matches_Prices/13_View_Ticket_Sales_Status.md).
- Update Fan Information → [Update Fan Information](Demo_Tung_Feature/09_Admin_Accounts_Roles/06_Update_Fan_Information.md).
- Activate Fan Account → [Activate Fan](Demo_Tung_Feature/09_Admin_Accounts_Roles/08_Activate_Fan.md).
- Deactivate Fan Account → [Deactivate Fan](Demo_Tung_Feature/09_Admin_Accounts_Roles/07_Deactivate_Fan.md).
- Delete Fan Account → [Delete Fan](Demo_Tung_Feature/09_Admin_Accounts_Roles/09_Delete_Fan.md).
- View System Summary → [View System Summary](Demo_Tung_Feature/10_Admin_Monitoring/01_View_System_Summary.md).
- View Audit Log → [View Audit Log](Demo_Tung_Feature/10_Admin_Monitoring/02_View_Audit_Log.md).
- View Fans → [View Fan List](Demo_Tung_Feature/09_Admin_Accounts_Roles/02_View_Fan_List.md).
- View Fan Details → [View Fan Details](Demo_Tung_Feature/09_Admin_Accounts_Roles/03_View_Fan_Details.md).
- Create Staff Account → [Create Staff Account](Demo_Tung_Feature/09_Admin_Accounts_Roles/10_Create_Staff_Account.md).
- Search Staff → [Search Staff](Demo_Tung_Feature/09_Admin_Accounts_Roles/13_Search_Staff.md).
- Update Staff Information → [Update Staff Information](Demo_Tung_Feature/09_Admin_Accounts_Roles/15_Update_Staff_Information.md).
- Activate Staff Account → [Activate Staff](Demo_Tung_Feature/09_Admin_Accounts_Roles/17_Activate_Staff.md).
- Deactivate Staff Account → [Deactivate Staff](Demo_Tung_Feature/09_Admin_Accounts_Roles/16_Deactivate_Staff.md).
- View Staffs → [View Staff List](Demo_Tung_Feature/09_Admin_Accounts_Roles/11_View_Staff_List.md).
- View Staff Details → [View Staff Details](Demo_Tung_Feature/09_Admin_Accounts_Roles/12_View_Staff_Details.md).
- Filter Staff → [Filter Staff Role and Status](Demo_Tung_Feature/09_Admin_Accounts_Roles/14_Filter_Staff_Role_and_Status.md).
- Assign Staff Role → [Assign Staff Role](Demo_Tung_Feature/09_Admin_Accounts_Roles/20_Assign_Staff_Role.md).
- Change Staff Role → [Change Staff Role](Demo_Tung_Feature/09_Admin_Accounts_Roles/18_Change_Staff_Role.md).
- Revoke Staff Role → [Revoke Staff Role](Demo_Tung_Feature/09_Admin_Accounts_Roles/19_Revoke_Staff_Role.md).
- View ticket details → [Ticket Details](Demo_Tung_Feature/05_Fan_Booking_Tickets/07_Ticket_Details.md).
- View Tickets History → [Ticket History](Demo_Tung_Feature/05_Fan_Booking_Tickets/06_Ticket_History.md).
- View Booking Details → [Booking Details](Demo_Tung_Feature/05_Fan_Booking_Tickets/04_Booking_Details.md).
- Create Fan → [Create Fan Account](Demo_Tung_Feature/09_Admin_Accounts_Roles/01_Create_Fan_Account.md), [Create Customer Fan Account](Demo_Tung_Feature/06_Seller/02_Create_Customer_Fan_Account.md).
- Review Refund Request → [Approve Refund](Demo_Tung_Feature/07_Cancellation_Refund/04_Approve_Refund.md), [Reject Refund](Demo_Tung_Feature/07_Cancellation_Refund/05_Reject_Refund.md).
- Cancel unpaid pending booking → [Cancel Unpaid Booking](Demo_Tung_Feature/07_Cancellation_Refund/01_Cancel_Unpaid_Booking.md).
- View my refund requests → [My Refund Requests](Demo_Tung_Feature/07_Cancellation_Refund/03_My_Refund_Requests.md).
- View profile → [View Profile](Demo_Tung_Feature/02_Authentication_User/04_View_Profile.md).
- Search Fan → [Search Fan](Demo_Tung_Feature/09_Admin_Accounts_Roles/04_Search_Fan.md), [Search and List Customers](Demo_Tung_Feature/06_Seller/01_Search_and_List_Customers.md).
- Filter Fans by status → [Filter Fan Status](Demo_Tung_Feature/09_Admin_Accounts_Roles/05_Filter_Fan_Status.md).
- View seat map and prices → [View Seat Map and Prices](Demo_Tung_Feature/06_Seller/03_View_Seat_Map_and_Prices.md), [View Seat Map and Prices](Demo_Tung_Feature/05_Fan_Booking_Tickets/01_View_Seat_Map_and_Prices.md).
- View customer booking history → [Customer Booking History](Demo_Tung_Feature/06_Seller/05_Customer_Booking_History.md).

## Chuẩn bị nộp

- [README tổng](../README.md).
- [Use Case và Class Diagram](ClassDiagram.drawio).
- [Bộ demo](Demo_Tung_Feature/README.md).
- [Kết quả kiểm tra và giới hạn](05_VERIFICATION_AND_DELIVERY.md).

Cần rà trực quan sơ đồ trước khi export. Kết quả kiểm tra source/cấu trúc và
139 flow phản ánh các đường đi đã chạy. Sinh viên cần tự diễn tập và hiểu cơ chế.
