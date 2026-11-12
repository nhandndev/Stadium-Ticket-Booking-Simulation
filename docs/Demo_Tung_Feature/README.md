# Danh mục demo từng tính năng

Bản ghi ngày 12/11/2026, chạy bằng code hiện tại. Bộ này có 88 file tính năng và 139 flow ghi Console thực tế.

- [Chuẩn bị và tình trạng CSV hiện tại](00_CHUAN_BI.md).
- [Thứ tự thực hiện từ đầu đến cuối](01_THU_TU_DEMO.md).
- [Bản đồ menu và cách đọc kết quả](02_BAN_DO_MENU.md).

Mỗi file gồm mục đích, luồng Controller/Service/Repository, điều kiện, từng phím nhập và Console sau từng lần Enter. Các trường hợp huỷ hoặc bị chặn nằm trong file tính năng tương ứng.

Các flow dùng CORE_DIR nhỏ để ID ổn định. Đọc file chuẩn bị và thực hiện theo thứ tự toàn cục. Booking mặc định dùng SYNCHRONIZED để chống double booking.

## 01 Guest

- [Search Matches](01_Guest/01_Search_Matches.md) — Flow 001, 008.
- [View Match Details](01_Guest/02_View_Match_Details.md) — Flow 002, 006.
- [View Seat Availability](01_Guest/03_View_Seat_Availability.md) — Flow 003, 007.
- [Filter Matches and List Schedule](01_Guest/04_Filter_Matches_and_List_Schedule.md) — Flow 004, 005.

## 02 Authentication User

- [Register Fan Account](02_Authentication_User/01_Register_Fan_Account.md) — Flow 009, 010.
- [Login As Fan](02_Authentication_User/02_Login_As_Fan.md) — Flow 011, 015, 016, 017, 019.
- [Login As Staff](02_Authentication_User/03_Login_As_Staff.md) — Flow 018, 020, 080, 099.
- [View Profile](02_Authentication_User/04_View_Profile.md) — Flow 012, 127, 129, 131.
- [Update Profile](02_Authentication_User/05_Update_Profile.md) — Flow 013, 128, 130, 132.
- [Logout](02_Authentication_User/06_Logout.md) — Flow 014.
- [CSV Profile Persistence](02_Authentication_User/07_CSV_Profile_Persistence.md) — Flow 138.

## 03 Admin Venues

- [Create Stadium](03_Admin_Venues/01_Create_Stadium.md) — Flow 021, 048.
- [View Stadium List](03_Admin_Venues/02_View_Stadium_List.md) — Flow 022.
- [View Stadium Details](03_Admin_Venues/03_View_Stadium_Details.md) — Flow 023.
- [Update Stadium](03_Admin_Venues/04_Update_Stadium.md) — Flow 024.
- [Delete Stadium](03_Admin_Venues/05_Delete_Stadium.md) — Flow 049, 050.
- [Create Section](03_Admin_Venues/06_Create_Section.md) — Flow 025, 046.
- [View Sections by Stadium](03_Admin_Venues/07_View_Sections_by_Stadium.md) — Flow 026.
- [View Section Details](03_Admin_Venues/08_View_Section_Details.md) — Flow 027.
- [Update Section](03_Admin_Venues/09_Update_Section.md) — Flow 028.
- [Delete Section](03_Admin_Venues/10_Delete_Section.md) — Flow 047.
- [Create Seat](03_Admin_Venues/11_Create_Seat.md) — Flow 029, 030, 031, 032, 033, 034, 035, 036, 043.
- [View Seats by Section](03_Admin_Venues/12_View_Seats_by_Section.md) — Flow 037.
- [View Seat Details](03_Admin_Venues/13_View_Seat_Details.md) — Flow 038.
- [Update Seat](03_Admin_Venues/14_Update_Seat.md) — Flow 039, 040.
- [Activate Seat](03_Admin_Venues/15_Activate_Seat.md) — Flow 042.
- [Deactivate Seat](03_Admin_Venues/16_Deactivate_Seat.md) — Flow 041.
- [Delete Seat](03_Admin_Venues/17_Delete_Seat.md) — Flow 044, 045.

## 04 Admin Matches Prices

- [Create Match](04_Admin_Matches_Prices/01_Create_Match.md) — Flow 051, 055.
- [View Match List](04_Admin_Matches_Prices/02_View_Match_List.md) — Flow 052.
- [View Match Details](04_Admin_Matches_Prices/03_View_Match_Details.md) — Flow 053.
- [Update Match](04_Admin_Matches_Prices/04_Update_Match.md) — Flow 054.
- [Delete Match](04_Admin_Matches_Prices/05_Delete_Match.md) — Flow 056.
- [Create Ticket Price](04_Admin_Matches_Prices/06_Create_Ticket_Price.md) — Flow 057, 062.
- [View Ticket Prices by Match](04_Admin_Matches_Prices/07_View_Ticket_Prices_by_Match.md) — Flow 058.
- [View Ticket Price Details](04_Admin_Matches_Prices/08_View_Ticket_Price_Details.md) — Flow 059.
- [Update Ticket Price](04_Admin_Matches_Prices/09_Update_Ticket_Price.md) — Flow 060.
- [Delete Ticket Price](04_Admin_Matches_Prices/10_Delete_Ticket_Price.md) — Flow 061.
- [Open Ticket Sales](04_Admin_Matches_Prices/11_Open_Ticket_Sales.md) — Flow 064, 067.
- [Close Ticket Sales](04_Admin_Matches_Prices/12_Close_Ticket_Sales.md) — Flow 065.
- [View Ticket Sales Status](04_Admin_Matches_Prices/13_View_Ticket_Sales_Status.md) — Flow 063, 066.

## 05 Fan Booking Tickets

- [View Seat Map and Prices](05_Fan_Booking_Tickets/01_View_Seat_Map_and_Prices.md) — Flow 068.
- [Create Booking](05_Fan_Booking_Tickets/02_Create_Booking.md) — Flow 069, 073, 078, 079, 140.
- [Booking History](05_Fan_Booking_Tickets/03_Booking_History.md) — Flow 070.
- [Booking Details](05_Fan_Booking_Tickets/04_Booking_Details.md) — Flow 071.
- [Pay Pending Booking](05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md) — Flow 074, 141.
- [Ticket History](05_Fan_Booking_Tickets/06_Ticket_History.md) — Flow 075, 098.
- [Ticket Details](05_Fan_Booking_Tickets/07_Ticket_Details.md) — Flow 076.
- [Book This Match from Browsing](05_Fan_Booking_Tickets/08_Book_This_Match_from_Browsing.md) — Flow 077.

## 06 Seller

- [Search and List Customers](06_Seller/01_Search_and_List_Customers.md) — Flow 081, 082.
- [Create Customer Fan Account](06_Seller/02_Create_Customer_Fan_Account.md) — Flow 083.
- [View Seat Map and Prices](06_Seller/03_View_Seat_Map_and_Prices.md) — Flow 084.
- [Create Booking for Customer](06_Seller/04_Create_Booking_for_Customer.md) — Flow 085.
- [Customer Booking History](06_Seller/05_Customer_Booking_History.md) — Flow 086.
- [Accept Cash Payment](06_Seller/06_Accept_Cash_Payment.md) — Flow 087, 088.
- [Issue Tickets](06_Seller/07_Issue_Tickets.md) — Flow 089, 090.
- [Browse Matches](06_Seller/08_Browse_Matches.md) — Flow 091.

## 07 Cancellation Refund

- [Cancel Unpaid Booking](07_Cancellation_Refund/01_Cancel_Unpaid_Booking.md) — Flow 072.
- [Request Refund](07_Cancellation_Refund/02_Request_Refund.md) — Flow 092, 093, 106.
- [My Refund Requests](07_Cancellation_Refund/03_My_Refund_Requests.md) — Flow 094, 097.
- [Approve Refund](07_Cancellation_Refund/04_Approve_Refund.md) — Flow 095.
- [Reject Refund](07_Cancellation_Refund/05_Reject_Refund.md) — Flow 096.

## 08 Gate

- [Validate Ticket](08_Gate/01_Validate_Ticket.md) — Flow 100, 104.
- [Check In Ticket](08_Gate/02_Check_In_Ticket.md) — Flow 101, 102, 103, 105.

## 09 Admin Accounts Roles

- [Create Fan Account](09_Admin_Accounts_Roles/01_Create_Fan_Account.md) — Flow 107.
- [View Fan List](09_Admin_Accounts_Roles/02_View_Fan_List.md) — Flow 108.
- [View Fan Details](09_Admin_Accounts_Roles/03_View_Fan_Details.md) — Flow 109.
- [Search Fan](09_Admin_Accounts_Roles/04_Search_Fan.md) — Flow 110.
- [Filter Fan Status](09_Admin_Accounts_Roles/05_Filter_Fan_Status.md) — Flow 111.
- [Update Fan Information](09_Admin_Accounts_Roles/06_Update_Fan_Information.md) — Flow 112.
- [Deactivate Fan](09_Admin_Accounts_Roles/07_Deactivate_Fan.md) — Flow 113.
- [Activate Fan](09_Admin_Accounts_Roles/08_Activate_Fan.md) — Flow 114.
- [Delete Fan](09_Admin_Accounts_Roles/09_Delete_Fan.md) — Flow 115.
- [Create Staff Account](09_Admin_Accounts_Roles/10_Create_Staff_Account.md) — Flow 116.
- [View Staff List](09_Admin_Accounts_Roles/11_View_Staff_List.md) — Flow 117.
- [View Staff Details](09_Admin_Accounts_Roles/12_View_Staff_Details.md) — Flow 118.
- [Search Staff](09_Admin_Accounts_Roles/13_Search_Staff.md) — Flow 119.
- [Filter Staff Role and Status](09_Admin_Accounts_Roles/14_Filter_Staff_Role_and_Status.md) — Flow 120.
- [Update Staff Information](09_Admin_Accounts_Roles/15_Update_Staff_Information.md) — Flow 121.
- [Deactivate Staff](09_Admin_Accounts_Roles/16_Deactivate_Staff.md) — Flow 122.
- [Activate Staff](09_Admin_Accounts_Roles/17_Activate_Staff.md) — Flow 123.
- [Change Staff Role](09_Admin_Accounts_Roles/18_Change_Staff_Role.md) — Flow 124.
- [Revoke Staff Role](09_Admin_Accounts_Roles/19_Revoke_Staff_Role.md) — Flow 125.
- [Assign Staff Role](09_Admin_Accounts_Roles/20_Assign_Staff_Role.md) — Flow 126.

## 10 Admin Monitoring

- [View System Summary](10_Admin_Monitoring/01_View_System_Summary.md) — Flow 136.
- [View Audit Log](10_Admin_Monitoring/02_View_Audit_Log.md) — Flow 137.

## 11 System

- [Invalid Menu Input](11_System/01_Invalid_Menu_Input.md) — Flow 139.
- [Exit System](11_System/02_Exit_System.md) — Flow 142.

