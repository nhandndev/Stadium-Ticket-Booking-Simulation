# Full Use Case, Code and Feature Traceability

## Scope Review Results

- Page 00 contains 82 Use Case nodes, including repeated capabilities by actor/domain.
- The demo set has 87 feature guides and 148 flows executed with the current code.
- The 97 source class/enum contracts match 192 expanded class boxes and five Mermaid files.
- Seller has the seven approved features.
- Thirty-minute holds, expiry checks and hold rollback belong to booking,
  payment and seat-availability workflows.
- Match selection and Book this match belong to browsing and Create booking.

## Coverage by Actor

| Actor/domain | Implemented capabilities | Code entry points |
| --- | --- | --- |
| Guest | Search, filter/list schedules, Match details, seat availability, registration and login entry | MainController, BrowseController, FanController, AuthController |
| User | Login, profile, profile update and logout | MainController, AuthController |
| Fan | Create booking, immediate/later online payment, booking/ticket history and details, cancellation/refund | BookingController, FanController, TicketController, RefundController |
| Seller | Search Fan, Create Fan, Create booking for Fan, Accept offline payment, Issue ticket, View seat map/prices, View customer booking history | BookingController.runSellerMenu |
| Gate Staff | Validate ticket and Check-in ticket | TicketController |
| Administrator — venues | Stadium/Section/Seat CRUD and Seat activation/deactivation | AdminController, StadiumController |
| Administrator — Match/pricing | Match/price CRUD, Open/Close sales and sales status | AdminController, MatchController |
| Administrator — accounts | Fan/Staff lists, details, search, filtering, creation, updates and status, Fan deletion, role viewing/assignment/change/revocation | AdminController, AccountAdminController |
| Administrator — review/monitoring | Refund review, summary and audit logs | AdminController, RefundController |
| Payment Service | Mock online payment result | BookingService, MockPaymentService |

Back, Exit, input validation and CSV persistence are shared workflow behavior,
verified by the System and CSV Profile Persistence guides. Walk-in purchase is a
branch of counter Create booking, identified by booking/ticket codes.
Booking-to-Fan multiplicity is 0..1. Seller profile, login and logout belong to
the shared User capabilities.

## Each Use Case Mapped to Code and a Demo

### UC-01 — Search Matches

- [Search Matches](Feature_Demos/01_Guest/01_Search_Matches.md).
- Code `MainController.runGuestMenu → BrowseController.searchMatches → BrowseService → MatchRepository`.

### UC-02 — Filter Matches

- [Filter Matches and List Schedule](Feature_Demos/01_Guest/04_Filter_Matches_and_List_Schedule.md).
- Code `MainController → BrowseController.filterMatches → BrowseService → MatchRepository`.

### UC-03 — View match details

- [View Match Details](Feature_Demos/04_Admin_Matches_Prices/03_View_Match_Details.md).
- Code `MainController → AdminController → MatchController.getMatchDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- [View Match Details](Feature_Demos/01_Guest/02_View_Match_Details.md).
- Code `MainController → BrowseController.viewMatchDetails → BrowseService → MatchRepository`.

### UC-04 — View seat availability

- [View Seat Availability](Feature_Demos/01_Guest/03_View_Seat_Availability.md).
- Code `MainController → BrowseController.getSeatMap → BrowseService → MatchSeatRepository / SeatRepository → SeatMapView`.

### UC-05 — Register

- [Register Fan Account](Feature_Demos/02_Authentication_User/01_Register_Fan_Account.md).
- Code `MainController.handleFanRegistration → FanController.register → FanService.register → FanRepository / StaffRepository`.

### UC-06 — Create booking

- [Create Booking](Feature_Demos/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Code `MainController → BookingController.createBooking → BookingService.createBooking → BookingRepository; Pay Now continues through makeOnlinePayment`.
- [Book This Match from Browsing](Feature_Demos/05_Fan_Booking_Tickets/08_Book_This_Match_from_Browsing.md).
- Code `MainController.handleMatchListActions → BookingController.bookMatch → BookingView`.

### UC-07 — Select seats

- [Create Booking](Feature_Demos/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Code `MainController → BookingController.createBooking → BookingService.createBooking → BookingRepository; Pay Now continues through makeOnlinePayment`.

### UC-08 — Make online payment

- [Pay Pending Booking](Feature_Demos/05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md).
- Code `MainController → BookingController.makeOnlinePayment → BookingService → MockPaymentService / StrategyFactory / SynchronizedStrategy → CSV repositories`.

### UC-09 — View tickets

- [Ticket History](Feature_Demos/05_Fan_Booking_Tickets/06_Ticket_History.md).
- Code `MainController → BookingController → FanController.getMyTickets → FanService.getMyTickets → TicketRepository`.

### UC-10 — View booking history

- [Booking History](Feature_Demos/05_Fan_Booking_Tickets/03_Booking_History.md).
- Code `MainController → BookingController.getMyBookings → BookingService.getMyBookings → BookingRepository`.

### UC-11 — Request refund

- [Request Refund](Feature_Demos/07_Cancellation_Refund/02_Request_Refund.md).
- Code `MainController → BookingController → RefundController.requestRefund → RefundService → RefundRepository`.

### UC-12 — Search Fan

- [Search Fan](Feature_Demos/09_Admin_Accounts_Roles/04_Search_Fan.md).
- Code `MainController → AdminController → AccountAdminController.searchFans → AccountAdminService → FanRepository / StaffRepository`.
- [Search and List Customers](Feature_Demos/06_Seller/01_Search_and_List_Customers.md).
- Code `MainController → BookingController.runSellerMenu → FanController.searchFans → FanService → FanRepository`.

### UC-13 — Issue ticket

- [Issue Tickets](Feature_Demos/06_Seller/07_Issue_Tickets.md).
- Code `MainController → BookingController.issueTickets → BookingService.issueTickets → TicketRepository`.

### UC-14 — Create booking for Fan

- [Create Booking for Customer](Feature_Demos/06_Seller/04_Create_Booking_for_Customer.md).
- Code `MainController → BookingController.createBookingForCustomer → BookingService.createBookingForCustomer → BookingRepository`.

### UC-15 — Accept offline payment

- [Accept Cash Payment](Feature_Demos/06_Seller/06_Accept_Cash_Payment.md).
- Code `MainController → BookingController.acceptOfflinePayment → BookingService.acceptOfflinePayment → PaymentRepository / MatchSeatRepository / BookingRepository`.

### UC-16 — Validate ticket

- [Validate Ticket](Feature_Demos/08_Gate/01_Validate_Ticket.md).
- Code `MainController → TicketController.validateTicket → TicketService.validateTicket → TicketRepository / BookingRepository`.

### UC-17 — Check-in ticket

- [Check In Ticket](Feature_Demos/08_Gate/02_Check_In_Ticket.md).
- Code `MainController → TicketController.checkInTicket → TicketService.checkInTicket → TicketRepository`.

### UC-18 — Process mock online payment

- [Pay Pending Booking](Feature_Demos/05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md).
- Code `MainController → BookingController.makeOnlinePayment → BookingService → MockPaymentService / StrategyFactory / SynchronizedStrategy → CSV repositories`.

### UC-19 — Logout

- [Logout](Feature_Demos/02_Authentication_User/06_Logout.md).
- Code `MainController.routeAuthenticatedUser → AuthController.logout → AuthService.logout`.

### UC-20 — Update Profile Information

- [Update Profile](Feature_Demos/02_Authentication_User/05_Update_Profile.md).
- Code `MainController → MainView.readProfileUpdateRequest → AuthController.updateCurrentUserProfile → AuthService → FanRepository / StaffRepository`.

### UC-21 — Login As Fan / Login As Staff

- [Login As Fan](Feature_Demos/02_Authentication_User/02_Login_As_Fan.md).
- Code `MainController.handleFanLogin → AuthController.login → AuthService.login → FanRepository / StaffRepository`.
- [Login As Staff](Feature_Demos/02_Authentication_User/03_Login_As_Staff.md).
- Code `MainController.handleStaffLogin → AuthController.login → AuthService.login → MainController.routeAuthenticatedUser`.
- [CSV Profile Persistence](Feature_Demos/02_Authentication_User/07_CSV_Profile_Persistence.md).
- Code `MainController → AuthController.login / getCurrentUser → AuthService → FanRepository`.

### UC-22 — Create Stadium

- [Create Stadium](Feature_Demos/03_Admin_Venues/01_Create_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-23 — View Stadium List

- [View Stadium List](Feature_Demos/03_Admin_Venues/02_View_Stadium_List.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getStadiums → StadiumService → Stadium/Section/SeatRepository`.

### UC-24 — View Stadium Details

- [View Stadium Details](Feature_Demos/03_Admin_Venues/03_View_Stadium_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getStadiumDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-25 — Update Stadium

- [Update Stadium](Feature_Demos/03_Admin_Venues/04_Update_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-26 — Delete Stadium

- [Delete Stadium](Feature_Demos/03_Admin_Venues/05_Delete_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-27 — Create Section

- [Create Section](Feature_Demos/03_Admin_Venues/06_Create_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-28 — View Sections by Stadium

- [View Sections by Stadium](Feature_Demos/03_Admin_Venues/07_View_Sections_by_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSections → StadiumService → Stadium/Section/SeatRepository`.

### UC-29 — View Section Details

- [View Section Details](Feature_Demos/03_Admin_Venues/08_View_Section_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSectionDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-30 — Update Section

- [Update Section](Feature_Demos/03_Admin_Venues/09_Update_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-31 — Delete Section

- [Delete Section](Feature_Demos/03_Admin_Venues/10_Delete_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-32 — Create Seat

- [Create Seat](Feature_Demos/03_Admin_Venues/11_Create_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-33 — View Seats by Section

- [View Seats by Section](Feature_Demos/03_Admin_Venues/12_View_Seats_by_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.buildSeatMap → StadiumService → Stadium/Section/SeatRepository`.

### UC-34 — View Seat Details

- [View Seat Details](Feature_Demos/03_Admin_Venues/13_View_Seat_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSeatDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-35 — Update Seat

- [Update Seat](Feature_Demos/03_Admin_Venues/14_Update_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-36 — Delete Seat

- [Delete Seat](Feature_Demos/03_Admin_Venues/17_Delete_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-37 — Activate Seat

- [Activate Seat](Feature_Demos/03_Admin_Venues/15_Activate_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.activateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-38 — Deactivate Seat

- [Deactivate Seat](Feature_Demos/03_Admin_Venues/16_Deactivate_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deactivateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-39 — Create Match

- [Create Match](Feature_Demos/04_Admin_Matches_Prices/01_Create_Match.md).
- Code `MainController → AdminController → MatchController.createMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-40 — View Match List

- [View Match List](Feature_Demos/04_Admin_Matches_Prices/02_View_Match_List.md).
- Code `MainController → AdminController → MatchController.getMatches → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-41 — View Match Details

- [View Match Details](Feature_Demos/04_Admin_Matches_Prices/03_View_Match_Details.md).
- Code `MainController → AdminController → MatchController.getMatchDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- [View Match Details](Feature_Demos/01_Guest/02_View_Match_Details.md).
- Code `MainController → BrowseController.viewMatchDetails → BrowseService → MatchRepository`.

### UC-42 — Update Match

- [Update Match](Feature_Demos/04_Admin_Matches_Prices/04_Update_Match.md).
- Code `MainController → AdminController → MatchController.updateMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-43 — Delete Match

- [Delete Match](Feature_Demos/04_Admin_Matches_Prices/05_Delete_Match.md).
- Code `MainController → AdminController → MatchController.deleteMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-44 — Create Ticket Price for a Match and Section

- [Create Ticket Price](Feature_Demos/04_Admin_Matches_Prices/06_Create_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.createTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-45 — View Ticket Prices by Match

- [View Ticket Prices by Match](Feature_Demos/04_Admin_Matches_Prices/07_View_Ticket_Prices_by_Match.md).
- Code `MainController → AdminController → MatchController.getTicketPrices → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-46 — View Ticket Price Details

- [View Ticket Price Details](Feature_Demos/04_Admin_Matches_Prices/08_View_Ticket_Price_Details.md).
- Code `MainController → AdminController → MatchController.getTicketPriceDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-47 — Update Ticket Price

- [Update Ticket Price](Feature_Demos/04_Admin_Matches_Prices/09_Update_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.updateTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-48 — Delete Ticket Price

- [Delete Ticket Price](Feature_Demos/04_Admin_Matches_Prices/10_Delete_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.deleteTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-49 — Open Ticket Sales

- [Open Ticket Sales](Feature_Demos/04_Admin_Matches_Prices/11_Open_Ticket_Sales.md).
- Code `MainController → AdminController → MatchController.openTicketSales → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-50 — Close Ticket Sales

- [Close Ticket Sales](Feature_Demos/04_Admin_Matches_Prices/12_Close_Ticket_Sales.md).
- Code `MainController → AdminController → MatchController.closeTicketSales → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-51 — View Ticket Sales Status

- [View Ticket Sales Status](Feature_Demos/04_Admin_Matches_Prices/13_View_Ticket_Sales_Status.md).
- Code `MainController → AdminController → MatchController.getTicketSalesStatus → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-52 — Update Fan Information

- [Update Fan Information](Feature_Demos/09_Admin_Accounts_Roles/06_Update_Fan_Information.md).
- Code `MainController → AdminController → AccountAdminController.updateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-53 — Activate Fan Account

- [Activate Fan](Feature_Demos/09_Admin_Accounts_Roles/08_Activate_Fan.md).
- Code `MainController → AdminController → AccountAdminController.activateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-54 — Deactivate Fan Account

- [Deactivate Fan](Feature_Demos/09_Admin_Accounts_Roles/07_Deactivate_Fan.md).
- Code `MainController → AdminController → AccountAdminController.deactivateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-55 — Delete Fan Account

- [Delete Fan](Feature_Demos/09_Admin_Accounts_Roles/09_Delete_Fan.md).
- Code `MainController → AdminController → AccountAdminController.deleteFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-56 — View System Summary

- [View System Summary](Feature_Demos/10_Admin_Monitoring/01_View_System_Summary.md).
- Code `MainController → AdminController.getSystemSummary → AdminService → domain repositories`.

### UC-57 — View Audit Log

- [View Audit Log](Feature_Demos/10_Admin_Monitoring/02_View_Audit_Log.md).
- Code `MainController → AdminController.getAuditLogs → AdminService → AuditLogRepository`.

### UC-58 — View Fans

- [View Fan List](Feature_Demos/09_Admin_Accounts_Roles/02_View_Fan_List.md).
- Code `MainController → AdminController → AccountAdminController.getFans → AccountAdminService → FanRepository / StaffRepository`.

### UC-59 — View Fan Details

- [View Fan Details](Feature_Demos/09_Admin_Accounts_Roles/03_View_Fan_Details.md).
- Code `MainController → AdminController → AccountAdminController.getFanDetails → AccountAdminService → FanRepository / StaffRepository`.

### UC-60 — Create Staff Account

- [Create Staff Account](Feature_Demos/09_Admin_Accounts_Roles/10_Create_Staff_Account.md).
- Code `MainController → AdminController → AccountAdminController.createStaffAccount → AccountAdminService → FanRepository / StaffRepository`.

### UC-61 — Search Staff

- [Search Staff](Feature_Demos/09_Admin_Accounts_Roles/13_Search_Staff.md).
- Code `MainController → AdminController → AccountAdminController.searchStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-62 — Update Staff Information

- [Update Staff Information](Feature_Demos/09_Admin_Accounts_Roles/15_Update_Staff_Information.md).
- Code `MainController → AdminController → AccountAdminController.updateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-63 — Activate Staff Account

- [Activate Staff](Feature_Demos/09_Admin_Accounts_Roles/17_Activate_Staff.md).
- Code `MainController → AdminController → AccountAdminController.activateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-64 — Deactivate Staff Account

- [Deactivate Staff](Feature_Demos/09_Admin_Accounts_Roles/16_Deactivate_Staff.md).
- Code `MainController → AdminController → AccountAdminController.deactivateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-65 — View Staffs

- [View Staff List](Feature_Demos/09_Admin_Accounts_Roles/11_View_Staff_List.md).
- Code `MainController → AdminController → AccountAdminController.getStaffs → AccountAdminService → FanRepository / StaffRepository`.

### UC-66 — View Staff Details

- [View Staff Details](Feature_Demos/09_Admin_Accounts_Roles/12_View_Staff_Details.md).
- Code `MainController → AdminController → AccountAdminController.getStaffDetails → AccountAdminService → FanRepository / StaffRepository`.

### UC-67 — Filter Staff

- [Filter Staff Role and Status](Feature_Demos/09_Admin_Accounts_Roles/14_Filter_Staff_Role_and_Status.md).
- Code `MainController → AdminController → AccountAdminController.filterStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-68 — Assign Staff Role

- [Assign Staff Role](Feature_Demos/09_Admin_Accounts_Roles/20_Assign_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.assignRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-69 — Change Staff Role

- [Change Staff Role](Feature_Demos/09_Admin_Accounts_Roles/18_Change_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.changeRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-70 — Revoke Staff Role

- [Revoke Staff Role](Feature_Demos/09_Admin_Accounts_Roles/19_Revoke_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.revokeRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-71 — View ticket details

- [Ticket Details](Feature_Demos/05_Fan_Booking_Tickets/07_Ticket_Details.md).
- Code `MainController → BookingController → TicketController.getTicketDetails → TicketService → TicketRepository`.

### UC-72 — View Tickets History

- [Ticket History](Feature_Demos/05_Fan_Booking_Tickets/06_Ticket_History.md).
- Code `MainController → BookingController → FanController.getMyTickets → FanService.getMyTickets → TicketRepository`.

### UC-73 — View Booking Details

- [Booking Details](Feature_Demos/05_Fan_Booking_Tickets/04_Booking_Details.md).
- Code `MainController → BookingController.getBookingDetails → BookingService → BookingRepository`.

### UC-74 — Create Fan

- [Create Fan Account](Feature_Demos/09_Admin_Accounts_Roles/01_Create_Fan_Account.md).
- Code `MainController → AdminController → AccountAdminController.createFanAccount → AccountAdminService → FanRepository / StaffRepository`.
- [Create Customer Fan Account](Feature_Demos/06_Seller/02_Create_Customer_Fan_Account.md).
- Code `MainController → BookingController → SellerView.readRegisterFanRequest → FanController.register → FanService → FanRepository`.

### UC-75 — Review Refund Request

- [Approve Refund](Feature_Demos/07_Cancellation_Refund/04_Approve_Refund.md).
- Code `MainController → AdminController → RefundController.reviewRefund → RefundService → Ticket/Booking/MatchSeat/RefundRepository; AuditLogRepository.append`.
- [Reject Refund](Feature_Demos/07_Cancellation_Refund/05_Reject_Refund.md).
- Code `MainController → AdminController → RefundController.reviewRefund → RefundService → RefundRepository; AuditLogRepository.append`.

### UC-76 — Cancel unpaid pending booking

- [Cancel Unpaid Booking](Feature_Demos/07_Cancellation_Refund/01_Cancel_Unpaid_Booking.md).
- Code `MainController → BookingController.cancelBooking → BookingService.cancelBooking → BookingRepository`.

### UC-77 — View my refund requests

- [My Refund Requests](Feature_Demos/07_Cancellation_Refund/03_My_Refund_Requests.md).
- Code `MainController → BookingController → RefundController.getMyRefunds → RefundService → RefundRepository`.

### UC-78 — View profile

- [View Profile](Feature_Demos/02_Authentication_User/04_View_Profile.md).
- Code `MainController → AuthController.getCurrentUser → AuthService.getCurrentUser → MainView.displayUser`.

### UC-79 — Search Fan

- [Search Fan](Feature_Demos/09_Admin_Accounts_Roles/04_Search_Fan.md).
- Code `MainController → AdminController → AccountAdminController.searchFans → AccountAdminService → FanRepository / StaffRepository`.
- [Search and List Customers](Feature_Demos/06_Seller/01_Search_and_List_Customers.md).
- Code `MainController → BookingController.runSellerMenu → FanController.searchFans → FanService → FanRepository`.

### UC-80 — Filter Fans by status

- [Filter Fan Status](Feature_Demos/09_Admin_Accounts_Roles/05_Filter_Fan_Status.md).
- Code `MainController → AdminController → AccountAdminController.filterFans → AccountAdminService → FanRepository / StaffRepository`.

### UC-81 — View seat map and prices

- [View Seat Map and Prices](Feature_Demos/06_Seller/03_View_Seat_Map_and_Prices.md).
- Code `MainController → BookingController → BrowseController.getSeatMap → BrowseService → SeatMapView`.
- [View Seat Map and Prices](Feature_Demos/05_Fan_Booking_Tickets/01_View_Seat_Map_and_Prices.md).
- Code `MainController → BookingController → BrowseController.getSeatMap → BrowseService → SeatMapView`.

### UC-82 — View customer booking history

- [Customer Booking History](Feature_Demos/06_Seller/05_Customer_Booking_History.md).
- Code `MainController → BookingController.getBookingsForFan → BookingService.getMyBookings → BookingRepository`.

## Business Rules Represented in the Workflows

- PENDING stores createdAt/expiresAt and owns its holds through holdBookingId.
- The deadline is at most 30 minutes, ending earlier at kick-off.
- Fan pays online immediately or later. Seller accepts offline payment for the
  same booking within that deadline.
- Accept offline payment records cash receipt. Issue ticket is a separate action.
- EXPIRED blocks both payment channels and releases that booking's owned holds.
- Cancellation, failed payment, idempotency, ownership, invalid Seat IDs and
  INACTIVE account checks have corresponding conditions and verification flows.
- Booking history, ticket history, refund and check-in retain their separate meanings.

## Using This Document in a Presentation

Open the [Use Case and Class Diagram](ClassDiagram.drawio), find the actor's
feature and follow its demo link above. Use the [setup guide](Feature_Demos/00_SETUP.md)
and [complete order](Feature_Demos/01_DEMO_ORDER.md) so data state and Console IDs match.
