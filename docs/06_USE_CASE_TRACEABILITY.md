# Full Use Case, Code and Feature Traceability

## Scope Review Results

- Page 00 contains 75 unique Use Case nodes. Shared goals have one node with associations from each participating actor.
- The demo set has 87 feature guides with 148 historical numbered flows, plus current integrated-counter walkthroughs.
- The 97 source class/enum contracts match 192 expanded class boxes and five Mermaid files.
- Seller has five menu groups. Ticket sales contains create-new and pay-existing workflows with cash receipt and automatic ticket issue.
- Thirty-minute holds, expiry checks and hold rollback belong to booking,
  payment and seat-availability workflows.
- Match selection and Book this match belong to browsing and Create booking.

## Coverage by Actor

| Actor/domain | Implemented capabilities | Code entry points |
| --- | --- | --- |
| Guest | Search, filter/list schedules, Match details, seat availability, registration and login entry | MainController, BrowseController, FanController, AuthController |
| User | Login, profile, profile update and logout | MainController, AuthController |
| Fan | List/search/filter Matches, details and seat availability/prices, Create booking, immediate/later online payment, booking/ticket history and details, cancellation/refund | MainController, BrowseController, BookingController, FanController, TicketController, RefundController |
| Seller | Search/create Fan, Create booking for Fan, Pay for an existing booking, seat map/prices and customer history | BookingController.runSellerMenu / runTicketSalesMenu |
| Gate Staff | Check-in ticket, including validation and preview | TicketController |
| Administrator — venues | Stadium/Section/Seat CRUD and Seat activation/deactivation | AdminController, StadiumController |
| Administrator — Match/pricing | Match list/details and CRUD, price CRUD, Open/Close sales and sales status | AdminController, MatchController |
| Administrator — accounts | Fan/Staff lists, details, search, filtering, creation, updates and status, Fan deletion, role viewing/assignment/change/revocation | AdminController, AccountAdminController |
| Administrator — review/monitoring | Refund review, summary and audit logs | AdminController, RefundController |
| Internal payment component | Mock online payment result within Make online payment | BookingService, MockPaymentService |

Back, Exit, input validation and CSV persistence are shared workflow behavior,
verified by the System and CSV Profile Persistence guides. Walk-in purchase is a
branch of counter Create booking, identified by booking/ticket codes.
Booking-to-Fan multiplicity is 0..1. Seller profile, login and logout belong to
the shared User capabilities.

- [Invalid Menu Input](Feature_Demos/11_System/01_Invalid_Menu_Input.md) covers rejected selections and numeric input.
- [Exit System](Feature_Demos/11_System/02_Exit_System.md) covers ending the main application loop.
- [CSV Profile Persistence](Feature_Demos/02_Authentication_User/07_CSV_Profile_Persistence.md) covers persisted account changes across runs.

Actor access was reviewed against the current Console entry points. Guest and
Fan can search by team keyword and filter by Stadium/date. Seller lists and
selects Matches inside its counter flows. Administrator lists and views Matches
through Match Management. Authenticated Administrator and Seller menus currently
have no keyword-search or Stadium/date-filter entry. Gate Staff enters Ticket Code
for validation, preview and confirmation inside check-in. All authenticated roles inherit User profile/update/logout.

Page 00 associates Guest, Fan, Seller and Administrator with one View Match List
node and one View Match Details node. Search Fan is shared by Seller and
Administrator. View seat map and prices is shared by Guest, Fan and Seller.
Seller search/filter associations were removed to match its menu.

## Each Use Case Mapped to Actors, Menus, Code and a Demo

Each business goal is represented once on page 00. Former duplicate IDs UC-41,
UC-72, UC-79 and UC-81 are consolidated into UC-03, UC-09, UC-12 and UC-04
respectively. Existing surviving IDs remain stable. The shared Match list uses UC-40.
UC-18 was an internal mock-payment step and is covered by UC-08. UC-13 ticket
issue is now a step of UC-14 and UC-15. IDs are stable identifiers, so the highest
identifier remains UC-82 while the current goal count is 75.

### UC-01 — Search Matches

- Actors — Fan, Guest.
- Console entry — Guest > Search matches, or Fan dashboard > Browse matches > Search matches.

- [Search Matches](Feature_Demos/01_Guest/01_Search_Matches.md).
- Code `MainController.runGuestMenu → BrowseController.searchMatches → BrowseService → MatchRepository`.

### UC-02 — Filter Matches

- Actors — Fan, Guest.
- Console entry — Guest > Filter matches / List all schedule, or Fan dashboard > Browse matches > Filter matches.

- [Filter Matches and List Schedule](Feature_Demos/01_Guest/04_Filter_Matches_and_List_Schedule.md).
- Code `MainController → BrowseController.filterMatches → BrowseService → MatchRepository`.

### UC-03 — View Match Details

- Actors — Administrator, Fan, Guest, Seller.
- Console entry — Guest/Fan > Match details or selected-Match actions. Seller > Create booking or View seat map and prices. Administrator > Matches > Match details.

- [View Match Details](Feature_Demos/04_Admin_Matches_Prices/03_View_Match_Details.md).
- Code `MainController → AdminController → MatchController.getMatchDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.
- [View Match Details](Feature_Demos/01_Guest/02_View_Match_Details.md).
- Code `MainController → BrowseController.viewMatchDetails → BrowseService → MatchRepository`.

### UC-04 — View seat map and prices

- Actors — Fan, Guest, Seller.
- Console entry — Guest/Fan > Seat availability or selected-Match actions. Fan > Tickets and bookings > Book tickets > View seat map and prices. Seller > 4. View seat map and prices or Ticket sales > Create booking.

- Shared actors are Guest, Fan and Seller. Availability and prices are displayed together by the current seat-map flow.
- [Fan Seat Map and Prices](Feature_Demos/05_Fan_Booking_Tickets/01_View_Seat_Map_and_Prices.md).
- [Seller Seat Map and Prices](Feature_Demos/06_Seller/03_View_Seat_Map_and_Prices.md).
- [View Seat Availability](Feature_Demos/01_Guest/03_View_Seat_Availability.md).
- Code `MainController → BrowseController.getSeatMap → BrowseService → MatchSeatRepository / SeatRepository → SeatMapView`.

### UC-05 — Register

- Actors — Guest.
- Console entry — Main menu > 4. Register Fan Account.

- [Register Fan Account](Feature_Demos/02_Authentication_User/01_Register_Fan_Account.md).
- Code `MainController.handleFanRegistration → FanController.register → FanService.register → FanRepository / StaffRepository`.

### UC-06 — Create booking

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > Book tickets > Create booking, or Browse matches > Book this match.

- [Create Booking](Feature_Demos/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Code `MainController → BookingController.createBooking → BookingService.createBooking → BookingRepository; Pay Now continues through makeOnlinePayment`.
- [Book This Match from Browsing](Feature_Demos/05_Fan_Booking_Tickets/08_Book_This_Match_from_Browsing.md).
- Code `MainController.handleMatchListActions → BookingController.bookMatch → BookingView`.

### UC-07 — Select seats

- Actors — Fan, Seller.
- Console entry — Fan Create booking or Seller Create booking for Fan > Seat IDs prompt after Match selection.

- [Create Booking](Feature_Demos/05_Fan_Booking_Tickets/02_Create_Booking.md).
- Code `MainController → BookingController.createBooking → BookingService.createBooking → BookingRepository; Pay Now continues through makeOnlinePayment`.

- [Counter Seat Selection](Feature_Demos/06_Seller/04_Create_Booking_for_Customer.md).
- Code `BookingController.sellTicketsAtCounter → SellerView.readBookingRequest → BookingController.createBookingForCustomer`.

### UC-08 — Make online payment

- Actors — Fan.
- Console entry — Fan Create booking > Pay now, or Tickets and bookings > My bookings and payments > Pay pending booking.

- [Pay Pending Booking](Feature_Demos/05_Fan_Booking_Tickets/05_Pay_Pending_Booking.md).
- Code `MainController → BookingController.makeOnlinePayment → BookingService → MockPaymentService / StrategyFactory / SynchronizedStrategy → CSV repositories`.

MockPaymentService is an internal collaborator of this flow. Online checkout issues
tickets on success. The same goal covers Pay Now and later payment before expiry.

### UC-09 — View Ticket History

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > My tickets > My tickets / Ticket history.

- [Ticket History](Feature_Demos/05_Fan_Booking_Tickets/06_Ticket_History.md).
- Code `MainController → BookingController → FanController.getMyTickets → FanService.getMyTickets → TicketRepository`.

### UC-10 — View booking history

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > My bookings and payments > Booking history.

- [Booking History](Feature_Demos/05_Fan_Booking_Tickets/03_Booking_History.md).
- Code `MainController → BookingController.getMyBookings → BookingService.getMyBookings → BookingRepository`.

### UC-11 — Request refund

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > Cancellations and refunds > Request refund.

- [Request Refund](Feature_Demos/07_Cancellation_Refund/02_Request_Refund.md).
- Code `MainController → BookingController → RefundController.requestRefund → RefundService → RefundRepository`.

### UC-12 — Search Fan

- Actors — Administrator, Seller.
- Console entry — Seller > Search Fan or customer selection. Administrator > Accounts and roles > Fan accounts > Search.

- [Search Fan](Feature_Demos/09_Admin_Accounts_Roles/04_Search_Fan.md).
- Code `MainController → AdminController → AccountAdminController.searchFans → AccountAdminService → FanRepository / StaffRepository`.
- [Search and List Customers](Feature_Demos/06_Seller/01_Search_and_List_Customers.md).
- Code `MainController → BookingController.runSellerMenu → FanController.searchFans → FanService → FanRepository`.

### UC-14 — Create booking for Fan

- Actors — Seller.
- Console entry — Seller > 3. Ticket sales > 1. Create booking for Fan / walk-in customer.

- [Create Booking for Customer](Feature_Demos/06_Seller/04_Create_Booking_for_Customer.md).
- Code `MainController → BookingController.createBookingForCustomer → BookingService.createBookingForCustomer → BookingRepository`.

- Automatic issue uses `BookingController.collectOfflinePayment → acceptOfflinePayment → issueTickets`. The public business methods are retained.
- [Automatic Ticket Issue and Retry](Feature_Demos/06_Seller/07_Issue_Tickets.md).

### UC-15 — Pay for an existing booking

- Actors — Seller.
- Console entry — Seller > 3. Ticket sales > 2. Pay for an existing booking > Booking ID > cash confirmation and automatic ticket issue.

- [Accept Cash Payment](Feature_Demos/06_Seller/06_Accept_Cash_Payment.md).
- Code `MainController → BookingController.acceptOfflinePayment → BookingService.acceptOfflinePayment → PaymentRepository / MatchSeatRepository / BookingRepository`.

- Automatic issue uses `BookingController.collectOfflinePayment → acceptOfflinePayment → issueTickets`. The public business methods are retained.
- [Automatic Ticket Issue and Retry](Feature_Demos/06_Seller/07_Issue_Tickets.md).

UC-16 is retired into UC-17. Validation is the preview step inside Check-in ticket.

### UC-17 — Check-in ticket

- Actors — Gate Staff.
- Console entry — Gate Staff dashboard > 2 > 1. Check in ticket > Ticket Code > preview > confirmation.

- [Check In Ticket](Feature_Demos/08_Gate/02_Check_In_Ticket.md).
- Code `MainController → TicketController.checkInTicket → TicketService.checkInTicket → TicketRepository`.

### UC-19 — Logout

- Actors — User through inheritance by Fan, Seller, Gate Staff and Administrator.
- Console entry — Any authenticated dashboard > 0. Logout.

- [Logout](Feature_Demos/02_Authentication_User/06_Logout.md).
- Code `MainController.routeAuthenticatedUser → AuthController.logout → AuthService.logout`.

### UC-20 — Update Profile Information

- Actors — User through inheritance by Fan, Seller, Gate Staff and Administrator.
- Console entry — Any authenticated dashboard > 3. Update profile.

- [Update Profile](Feature_Demos/02_Authentication_User/05_Update_Profile.md).
- Code `MainController → MainView.readProfileUpdateRequest → AuthController.updateCurrentUserProfile → AuthService → FanRepository / StaffRepository`.

### UC-21 — Login As Fan / Login As Staff

- Actors — Guest.
- Console entry — Main menu > 2. Login As Fan or 3. Login As Staff.

- [Login As Fan](Feature_Demos/02_Authentication_User/02_Login_As_Fan.md).
- Code `MainController.handleFanLogin → AuthController.login → AuthService.login → FanRepository / StaffRepository`.
- [Login As Staff](Feature_Demos/02_Authentication_User/03_Login_As_Staff.md).
- Code `MainController.handleStaffLogin → AuthController.login → AuthService.login → MainController.routeAuthenticatedUser`.
- [CSV Profile Persistence](Feature_Demos/02_Authentication_User/07_CSV_Profile_Persistence.md).
- Code `MainController → AuthController.login / getCurrentUser → AuthService → FanRepository`.

### UC-22 — Create Stadium

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Stadiums > Create.

- [Create Stadium](Feature_Demos/03_Admin_Venues/01_Create_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-23 — View Stadium List

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Stadiums > List.

- [View Stadium List](Feature_Demos/03_Admin_Venues/02_View_Stadium_List.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getStadiums → StadiumService → Stadium/Section/SeatRepository`.

### UC-24 — View Stadium Details

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Stadiums > Details.

- [View Stadium Details](Feature_Demos/03_Admin_Venues/03_View_Stadium_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getStadiumDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-25 — Update Stadium

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Stadiums > Update.

- [Update Stadium](Feature_Demos/03_Admin_Venues/04_Update_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-26 — Delete Stadium

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Stadiums > Delete.

- [Delete Stadium](Feature_Demos/03_Admin_Venues/05_Delete_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteStadium → StadiumService → Stadium/Section/SeatRepository`.

### UC-27 — Create Section

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Sections > Create.

- [Create Section](Feature_Demos/03_Admin_Venues/06_Create_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-28 — View Sections by Stadium

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Sections > List by Stadium.

- [View Sections by Stadium](Feature_Demos/03_Admin_Venues/07_View_Sections_by_Stadium.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSections → StadiumService → Stadium/Section/SeatRepository`.

### UC-29 — View Section Details

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Sections > Details.

- [View Section Details](Feature_Demos/03_Admin_Venues/08_View_Section_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSectionDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-30 — Update Section

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Sections > Update.

- [Update Section](Feature_Demos/03_Admin_Venues/09_Update_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-31 — Delete Section

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Sections > Delete.

- [Delete Section](Feature_Demos/03_Admin_Venues/10_Delete_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteSection → StadiumService → Stadium/Section/SeatRepository`.

### UC-32 — Create Seat

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Create.

- [Create Seat](Feature_Demos/03_Admin_Venues/11_Create_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.createSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-33 — View Seats by Section

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > List by Section.

- [View Seats by Section](Feature_Demos/03_Admin_Venues/12_View_Seats_by_Section.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.buildSeatMap → StadiumService → Stadium/Section/SeatRepository`.

### UC-34 — View Seat Details

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Details.

- [View Seat Details](Feature_Demos/03_Admin_Venues/13_View_Seat_Details.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.getSeatDetails → StadiumService → Stadium/Section/SeatRepository`.

### UC-35 — Update Seat

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Update.

- [Update Seat](Feature_Demos/03_Admin_Venues/14_Update_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.updateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-36 — Delete Seat

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Delete.

- [Delete Seat](Feature_Demos/03_Admin_Venues/17_Delete_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deleteSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-37 — Activate Seat

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Activate.

- [Activate Seat](Feature_Demos/03_Admin_Venues/15_Activate_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.activateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-38 — Deactivate Seat

- Actors — Administrator.
- Console entry — Administrator > Venues and seating > Seats > Deactivate.

- [Deactivate Seat](Feature_Demos/03_Admin_Venues/16_Deactivate_Seat.md).
- Code `MainController → AdminController.runAdminMenu → StadiumController.deactivateSeat → StadiumService → Stadium/Section/SeatRepository`.

### UC-39 — Create Match

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Matches > Create.

- [Create Match](Feature_Demos/04_Admin_Matches_Prices/01_Create_Match.md).
- Code `MainController → AdminController → MatchController.createMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-40 — View Match List

- Actors — Administrator, Fan, Guest, Seller.
- Console entry — Guest/Fan > Filter matches with empty criteria. Seller > Match selection during booking/map viewing. Administrator > Matches > List matches.

- Guest and Fan list schedules through MainController.runGuestMenu and BrowseController.filterMatches(null, null).
- Seller lists Matches through BookingController.runSellerMenu / sellTicketsAtCounter and BrowseController.filterMatches(null, null).
- [Filter Matches and List Schedule](Feature_Demos/01_Guest/04_Filter_Matches_and_List_Schedule.md).
- [View Seat Map and Prices at the Counter](Feature_Demos/06_Seller/03_View_Seat_Map_and_Prices.md).
- [View Match List](Feature_Demos/04_Admin_Matches_Prices/02_View_Match_List.md).
- Code `MainController → AdminController → MatchController.getMatches → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-42 — Update Match

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Matches > Update.

- [Update Match](Feature_Demos/04_Admin_Matches_Prices/04_Update_Match.md).
- Code `MainController → AdminController → MatchController.updateMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-43 — Delete Match

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Matches > Delete.

- [Delete Match](Feature_Demos/04_Admin_Matches_Prices/05_Delete_Match.md).
- Code `MainController → AdminController → MatchController.deleteMatch → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-44 — Create Ticket Price for a Match and Section

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket pricing > Create price.

- [Create Ticket Price](Feature_Demos/04_Admin_Matches_Prices/06_Create_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.createTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-45 — View Ticket Prices by Match

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket pricing > List prices by Match.

- [View Ticket Prices by Match](Feature_Demos/04_Admin_Matches_Prices/07_View_Ticket_Prices_by_Match.md).
- Code `MainController → AdminController → MatchController.getTicketPrices → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-46 — View Ticket Price Details

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket pricing > Price details.

- [View Ticket Price Details](Feature_Demos/04_Admin_Matches_Prices/08_View_Ticket_Price_Details.md).
- Code `MainController → AdminController → MatchController.getTicketPriceDetails → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-47 — Update Ticket Price

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket pricing > Update price.

- [Update Ticket Price](Feature_Demos/04_Admin_Matches_Prices/09_Update_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.updateTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-48 — Delete Ticket Price

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket pricing > Delete price.

- [Delete Ticket Price](Feature_Demos/04_Admin_Matches_Prices/10_Delete_Ticket_Price.md).
- Code `MainController → AdminController → MatchController.deleteTicketPrice → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-49 — Open Ticket Sales

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket sales > Open.

- [Open Ticket Sales](Feature_Demos/04_Admin_Matches_Prices/11_Open_Ticket_Sales.md).
- Code `MainController → AdminController → MatchController.openTicketSales → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-50 — Close Ticket Sales

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket sales > Close.

- [Close Ticket Sales](Feature_Demos/04_Admin_Matches_Prices/12_Close_Ticket_Sales.md).
- Code `MainController → AdminController → MatchController.closeTicketSales → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-51 — View Ticket Sales Status

- Actors — Administrator.
- Console entry — Administrator > Matches, pricing and sales > Ticket sales > View status.

- [View Ticket Sales Status](Feature_Demos/04_Admin_Matches_Prices/13_View_Ticket_Sales_Status.md).
- Code `MainController → AdminController → MatchController.getTicketSalesStatus → MatchService → MatchRepository / TicketPriceRepository / MatchSeatRepository`.

### UC-52 — Update Fan Information

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > Update.

- [Update Fan Information](Feature_Demos/09_Admin_Accounts_Roles/06_Update_Fan_Information.md).
- Code `MainController → AdminController → AccountAdminController.updateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-53 — Activate Fan Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > Activate.

- [Activate Fan](Feature_Demos/09_Admin_Accounts_Roles/08_Activate_Fan.md).
- Code `MainController → AdminController → AccountAdminController.activateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-54 — Deactivate Fan Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > Deactivate.

- [Deactivate Fan](Feature_Demos/09_Admin_Accounts_Roles/07_Deactivate_Fan.md).
- Code `MainController → AdminController → AccountAdminController.deactivateFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-55 — Delete Fan Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > Delete.

- [Delete Fan](Feature_Demos/09_Admin_Accounts_Roles/09_Delete_Fan.md).
- Code `MainController → AdminController → AccountAdminController.deleteFan → AccountAdminService → FanRepository / StaffRepository`.

### UC-56 — View System Summary

- Actors — Administrator.
- Console entry — Administrator dashboard > 2 > Reports and audit logs > System summary.

- [View System Summary](Feature_Demos/10_Admin_Monitoring/01_View_System_Summary.md).
- Code `MainController → AdminController.getSystemSummary → AdminService → domain repositories`.

### UC-57 — View Audit Log

- Actors — Administrator.
- Console entry — Administrator dashboard > 2 > Reports and audit logs > Audit logs.

- [View Audit Log](Feature_Demos/10_Admin_Monitoring/02_View_Audit_Log.md).
- Code `MainController → AdminController.getAuditLogs → AdminService → AuditLogRepository`.

### UC-58 — View Fans

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > List.

- [View Fan List](Feature_Demos/09_Admin_Accounts_Roles/02_View_Fan_List.md).
- Code `MainController → AdminController → AccountAdminController.getFans → AccountAdminService → FanRepository / StaffRepository`.

### UC-59 — View Fan Details

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > Details.

- [View Fan Details](Feature_Demos/09_Admin_Accounts_Roles/03_View_Fan_Details.md).
- Code `MainController → AdminController → AccountAdminController.getFanDetails → AccountAdminService → FanRepository / StaffRepository`.

### UC-60 — Create Staff Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Create.

- [Create Staff Account](Feature_Demos/09_Admin_Accounts_Roles/10_Create_Staff_Account.md).
- Code `MainController → AdminController → AccountAdminController.createStaffAccount → AccountAdminService → FanRepository / StaffRepository`.

### UC-61 — Search Staff

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Search.

- [Search Staff](Feature_Demos/09_Admin_Accounts_Roles/13_Search_Staff.md).
- Code `MainController → AdminController → AccountAdminController.searchStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-62 — Update Staff Information

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Update.

- [Update Staff Information](Feature_Demos/09_Admin_Accounts_Roles/15_Update_Staff_Information.md).
- Code `MainController → AdminController → AccountAdminController.updateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-63 — Activate Staff Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Activate.

- [Activate Staff](Feature_Demos/09_Admin_Accounts_Roles/17_Activate_Staff.md).
- Code `MainController → AdminController → AccountAdminController.activateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-64 — Deactivate Staff Account

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Deactivate.

- [Deactivate Staff](Feature_Demos/09_Admin_Accounts_Roles/16_Deactivate_Staff.md).
- Code `MainController → AdminController → AccountAdminController.deactivateStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-65 — View Staffs

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > List.

- [View Staff List](Feature_Demos/09_Admin_Accounts_Roles/11_View_Staff_List.md).
- Code `MainController → AdminController → AccountAdminController.getStaffs → AccountAdminService → FanRepository / StaffRepository`.

### UC-66 — View Staff Details

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Details.

- [View Staff Details](Feature_Demos/09_Admin_Accounts_Roles/12_View_Staff_Details.md).
- Code `MainController → AdminController → AccountAdminController.getStaffDetails → AccountAdminService → FanRepository / StaffRepository`.

### UC-67 — Filter Staff

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > Filter role/status.

- [Filter Staff Role and Status](Feature_Demos/09_Admin_Accounts_Roles/14_Filter_Staff_Role_and_Status.md).
- Code `MainController → AdminController → AccountAdminController.filterStaff → AccountAdminService → FanRepository / StaffRepository`.

### UC-68 — Assign Staff Role

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > 9. Staff roles > Assign role.

- [Assign Staff Role](Feature_Demos/09_Admin_Accounts_Roles/20_Assign_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.assignRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-69 — Change Staff Role

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > 9. Staff roles > Change role.

- [Change Staff Role](Feature_Demos/09_Admin_Accounts_Roles/18_Change_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.changeRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-70 — Revoke Staff Role

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Staff accounts > 9. Staff roles > Revoke role.

- [Revoke Staff Role](Feature_Demos/09_Admin_Accounts_Roles/19_Revoke_Staff_Role.md).
- Code `MainController → AdminController → AccountAdminController.revokeRole → AccountAdminService → FanRepository / StaffRepository`.

### UC-71 — View ticket details

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > My tickets > Ticket details.

- [Ticket Details](Feature_Demos/05_Fan_Booking_Tickets/07_Ticket_Details.md).
- Code `MainController → BookingController → TicketController.getTicketDetails → TicketService → TicketRepository`.

### UC-73 — View Booking Details

- Actors — Fan, Seller.
- Console entry — Fan > My bookings and payments > Booking details. Seller enters Booking ID within Ticket sales > Pay for an existing booking.

- [Booking Details](Feature_Demos/05_Fan_Booking_Tickets/04_Booking_Details.md).
- Code `MainController → BookingController.getBookingDetails → BookingService → BookingRepository`.

- [Seller Booking Details before Cash Payment](Feature_Demos/06_Seller/06_Accept_Cash_Payment.md).
- Code `BookingController.selectSellerBooking → getBookingDetails → SellerView.displayBookingResponse`.

### UC-74 — Create Fan

- Actors — Administrator, Seller.
- Console entry — Seller > 2. Create Fan. Administrator > Accounts and roles > Fan accounts > Create.

- [Create Fan Account](Feature_Demos/09_Admin_Accounts_Roles/01_Create_Fan_Account.md).
- Code `MainController → AdminController → AccountAdminController.createFanAccount → AccountAdminService → FanRepository / StaffRepository`.
- [Create Customer Fan Account](Feature_Demos/06_Seller/02_Create_Customer_Fan_Account.md).
- Code `MainController → BookingController → SellerView.readRegisterFanRequest → FanController.register → FanService → FanRepository`.

### UC-75 — Review Refund Request

- Actors — Administrator.
- Console entry — Administrator dashboard > 2 > Refund reviews > Review a request > decision and note.

- [Approve Refund](Feature_Demos/07_Cancellation_Refund/04_Approve_Refund.md).
- Code `MainController → AdminController → RefundController.reviewRefund → RefundService → Ticket/Booking/MatchSeat/RefundRepository; AuditLogRepository.append`.
- [Reject Refund](Feature_Demos/07_Cancellation_Refund/05_Reject_Refund.md).
- Code `MainController → AdminController → RefundController.reviewRefund → RefundService → RefundRepository; AuditLogRepository.append`.

### UC-76 — Cancel unpaid pending booking

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > Cancellations and refunds > Cancel unpaid pending booking.

- [Cancel Unpaid Booking](Feature_Demos/07_Cancellation_Refund/01_Cancel_Unpaid_Booking.md).
- Code `MainController → BookingController.cancelBooking → BookingService.cancelBooking → BookingRepository`.

### UC-77 — View my refund requests

- Actors — Fan.
- Console entry — Fan > Tickets and bookings > Cancellations and refunds > My refund requests.

- [My Refund Requests](Feature_Demos/07_Cancellation_Refund/03_My_Refund_Requests.md).
- Code `MainController → BookingController → RefundController.getMyRefunds → RefundService → RefundRepository`.

### UC-78 — View profile

- Actors — User through inheritance by Fan, Seller, Gate Staff and Administrator.
- Console entry — Any authenticated dashboard > 1. View profile.

- [View Profile](Feature_Demos/02_Authentication_User/04_View_Profile.md).
- Code `MainController → AuthController.getCurrentUser → AuthService.getCurrentUser → MainView.displayUser`.

### UC-80 — Filter Fans by status

- Actors — Administrator.
- Console entry — Administrator > Accounts and roles > Fan accounts > 5. Filter status.

- [Filter Fan Status](Feature_Demos/09_Admin_Accounts_Roles/05_Filter_Fan_Status.md).
- Code `MainController → AdminController → AccountAdminController.filterFans → AccountAdminService → FanRepository / StaffRepository`.

### UC-82 — View customer booking history

- Actors — Seller.
- Console entry — Seller > 5. View customer booking history > select an ACTIVE Fan.

- [Customer Booking History](Feature_Demos/06_Seller/05_Customer_Booking_History.md).
- Code `MainController → BookingController.getBookingsForFan → BookingService.getMyBookings → BookingRepository`.

## Business Rules Represented in the Workflows

- PENDING stores createdAt/expiresAt and owns its holds through holdBookingId.
- The deadline is at most 30 minutes, ending earlier at kick-off.
- Fan pays online immediately or later. Seller accepts offline payment for the
  same booking within that deadline.
- Successful cash checkout records receipt and automatically issues tickets in the same workflow. Deferring receipt preserves the pending booking deadline.
- EXPIRED blocks both payment channels and releases that booking's owned holds.
- Cancellation, failed payment, idempotency, ownership, invalid Seat IDs and
  INACTIVE account checks have corresponding conditions and verification flows.
- Booking history, ticket history, refund and check-in retain their separate meanings.

## Using This Document in a Presentation

Open the [Use Case and Class Diagram](ClassDiagram.drawio), find the actor's
feature and follow its demo link above. Use the [setup guide](Feature_Demos/00_SETUP.md)
and [complete order](Feature_Demos/01_DEMO_ORDER.md) so data state and Console IDs match.
