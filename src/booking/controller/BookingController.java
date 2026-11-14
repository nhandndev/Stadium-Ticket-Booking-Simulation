package booking.controller;

import auth.controller.AuthController;
import booking.dto.BookingRequestDto;
import booking.dto.BookingResponseDto;
import booking.service.BookingService;
import booking.view.BookingView;
import seller.view.SellerView;
import booking.enums.BookingStatus;
import match.controller.BrowseController;
import match.view.SeatMapView;
import refund.controller.RefundController;
import ticket.controller.TicketController;
import user.controller.FanController;
import common.exception.AppException;
import common.exception.ErrorCode;
import payment.dto.PaymentRequestDto;
import payment.dto.PaymentResponseDto;
import ticket.dto.TicketResponseDto;
import user.dto.UserResponseDto;
import user.enums.UserRole;
import user.enums.UserStatus;
import java.util.List;
import java.time.LocalDateTime;
import match.dto.MatchResponseDto;
import match.dto.MatchSeatResponseDto;
import match.enums.SaleStatus;
import payment.enums.PaymentStatus;
import refund.dto.RefundRequestDto;
import user.dto.RegisterFanRequestDto;

public class BookingController {
    private BookingService bookingService;
    private AuthController authController;
    private final BookingView bookingView;
    private final SellerView sellerView;
    private final SeatMapView seatMapView;
    private final BrowseController browseController;
    private final FanController fanController;
    private final RefundController refundController;
    private final TicketController ticketController;

    public BookingController(BookingService bookingService, AuthController authController,
                               BookingView bookingView, SellerView sellerView,
                             SeatMapView seatMapView,
                             BrowseController browseController, FanController fanController,
                             RefundController refundController,
                             TicketController ticketController) {
        if (bookingService == null || authController == null || bookingView == null
                || sellerView == null || seatMapView == null || browseController == null
                || fanController == null
                || refundController == null || ticketController == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Booking Controller dependencies are required");
        }
        this.bookingService = bookingService;
        this.authController = authController;
        this.bookingView = bookingView;
        this.sellerView = sellerView;
        this.seatMapView = seatMapView;
        this.browseController = browseController;
        this.fanController = fanController;
        this.refundController = refundController;
        this.ticketController = ticketController;
    }

    public void runFanBookingMenu() {
        while (true) {
            UserResponseDto fan = this.requireRole(UserRole.FAN);
            try {
                int group = this.bookingView.showBookingMenu();
                if (group == 0) return;
                if (group == 4) this.runRefundMenu(fan.getId());
                else if (group >= 1 && group <= 3) this.runFanBookingActions(group);
                else this.bookingView.displayMessage("Invalid choice.");
            } catch (NumberFormatException e) {
                this.bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.bookingView.displayMessage(e.getMessage());
            }
        }
    }

    // Groups: 1 = Book tickets, 2 = My bookings and payments, 3 = My tickets.
    private void runFanBookingActions(int group) {
        while (true) {
            UserResponseDto fan = this.requireRole(UserRole.FAN);
            try {
                int action = this.bookingView.showBookingActionsMenu(group);
                if (action == 0) return;
                if (action < 1 || action > (group == 2 ? 3 : 2)) {
                    this.bookingView.displayMessage("Invalid choice.");
                    continue;
                }
                int choice = action;
                // Cases: 4 = booking history, 8 = booking details, 3 = payment.
                if (group == 2) choice = action == 1 ? 4 : action == 2 ? 8 : 3;
                // Cases: 5 = ticket history, 6 = ticket details.
                if (group == 3) choice = action + 4;
                switch (choice) {
                    case 1: // Seat map and prices.
                    case 2: // Create booking.
                        List<MatchResponseDto> matches = this.browseController.filterMatches(null, null);
                        this.bookingView.displayMatches(matches);
                        if (matches.isEmpty()) break;
                        Long matchId = this.bookingView.readMatchId();
                        if (matchId == null) break;
                        if (choice == 2) {
                            this.bookMatch(matchId);
                        } else {
                            this.bookingView.displayMatchDetails(this.browseController.viewMatchDetails(matchId));
                            this.seatMapView.displaySeatMap(this.browseController.getSeatMap(matchId));
                        }
                        break;
                    case 3: // Pay pending booking.
                        List<BookingResponseDto> pending = this.getMyBookings(fan.getId());
                        pending.removeIf(booking -> booking.getStatus() != BookingStatus.PENDING);
                        if (pending.isEmpty()) {
                            this.bookingView.displayMessage("No active bookings are waiting for payment. Unpaid bookings expire after at most 30 minutes; check booking history.");
                            break;
                        }
                        this.bookingView.displayBookings(pending);
                        Long bookingId = this.bookingView.readBookingId();
                        if (bookingId == null) break;
                        BookingResponseDto booking = this.getBookingDetails(bookingId);
                        if (booking.getStatus() != BookingStatus.PENDING) {
                            this.bookingView.displayMessage("Choose a booking waiting for payment.");
                            break;
                        }
                        this.bookingView.displayBookingResponse(booking);
                        this.payBooking(booking);
                        break;
                    case 4: // Booking history.
                        this.bookingView.displayBookings(this.getMyBookings(fan.getId()));
                        break;
                    case 5: // Ticket history.
                        this.bookingView.displayTickets(this.fanController.getMyTickets(fan.getId()));
                        break;
                    case 6: // Ticket details.
                        List<TicketResponseDto> tickets = this.fanController.getMyTickets(fan.getId());
                        this.bookingView.displayTickets(tickets);
                        if (tickets.isEmpty()) break;
                        Long ticketId = this.bookingView.readTicketId();
                        if (ticketId != null) {
                            this.bookingView.displayTicketDetails(this.ticketController.getTicketDetails(ticketId));
                        }
                        break;
                    case 8: // Booking details.
                        List<BookingResponseDto> bookings = this.getMyBookings(fan.getId());
                        this.bookingView.displayBookings(bookings);
                        if (bookings.isEmpty()) break;
                        Long detailId = this.bookingView.readBookingId();
                        if (detailId != null) {
                            this.bookingView.displayBookingResponse(this.getBookingDetails(detailId));
                            this.bookingView.displayTickets(this.getTicketsForBooking(detailId));
                        }
                        break;
                    case 0:
                        return;
                    default:
                        this.bookingView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.bookingView.displayMessage(e.getMessage());
            }
        }
    }

    public void bookMatch(Long matchId) {
        UserResponseDto fan = this.requireRole(UserRole.FAN);
        MatchResponseDto match = this.browseController.viewMatchDetails(matchId);
        this.bookingView.displayMatchDetails(match);
        if (match.getSaleStatus() != SaleStatus.OPEN
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            this.bookingView.displayMessage("Ticket sales are closed for this match.");
            return;
        }
        List<MatchSeatResponseDto> seats = this.browseController.getSeatMap(matchId);
        this.seatMapView.displaySeatMap(seats);
        boolean available = false;
        for (MatchSeatResponseDto seat : seats) {
            if (seat.isAvailable()) available = true;
        }
        if (!available) {
            this.bookingView.displayMessage("No seats are available for booking.");
            return;
        }
        BookingRequestDto request = this.bookingView.readBookingRequest(fan.getId(), matchId);
        if (request == null) {
            this.bookingView.displayMessage("Booking cancelled.");
            return;
        }
        //Make Booking and hold seat
        BookingResponseDto booking = this.createBooking(request);
        //show booking have just made
        this.bookingView.displayBookingResponse(booking);
        //ask method pay booking
        this.payBooking(booking);
    }

    private void payBooking(BookingResponseDto booking) {
        PaymentRequestDto payment = this.bookingView.readPaymentRequest(booking);
        if (payment == null) {
            this.bookingView.displayMessage("Booking ID: " + booking.getBookingId());
            this.bookingView.displayMessage("Not paid. Pay before " + booking.getExpiresAt() + " online or give the Booking ID to a Seller for offline payment.");
            return;
        }
        PaymentResponseDto result = this.makeOnlinePayment(payment);
        this.bookingView.displayPaymentResponse(result);
        if (result.getStatus() == PaymentStatus.SUCCESS) {
            List<TicketResponseDto> tickets = this.fanController.getMyTickets(booking.getFanId());
            tickets.removeIf(ticket -> !ticket.getBookingId().equals(booking.getBookingId()));
            this.bookingView.displayTickets(tickets);
        }
    }

    private void runRefundMenu(Long fanId) {
        while (true) {
            this.requireRole(UserRole.FAN);
            try {
                int choice = this.bookingView.showRefundMenu();
                if (choice == 0) return;
                if (choice == 3) {
                    this.bookingView.displayRefunds(this.refundController.getMyRefunds(fanId));
                    continue;
                }
                if (choice != 1 && choice != 2) {
                    this.bookingView.displayMessage("Invalid choice.");
                    continue;
                }
                List<BookingResponseDto> bookings = this.getMyBookings(fanId);
                // 1 = request refund, 2 = cancel unpaid booking.
                BookingStatus required = choice == 1 ? BookingStatus.CONFIRMED : BookingStatus.PENDING;
                bookings.removeIf(booking -> booking.getStatus() != required);
                if (bookings.isEmpty()) {
                    this.bookingView.displayMessage(choice == 1 ? "No paid bookings available for a refund request."
                            : "No unpaid bookings available to cancel.");
                    continue;
                }
                this.bookingView.displayBookings(bookings);
                Long bookingId = this.bookingView.readBookingId();
                if (bookingId == null) continue;
                BookingResponseDto booking = this.getBookingDetails(bookingId);
                if (booking.getStatus() != required) {
                    this.bookingView.displayMessage("Choose a booking from the displayed list.");
                    continue;
                }
                this.bookingView.displayBookingResponse(booking);
                if (choice == 1) {
                    RefundRequestDto request = this.bookingView.readRefundRequest(fanId, bookingId);
                    if (request != null) this.bookingView.displayRefundResponse(this.refundController.requestRefund(request));
                    else this.bookingView.displayMessage("Refund request cancelled.");
                } else if (this.bookingView.confirmCancellation()) {
                    this.bookingView.displayBookingResponse(this.cancelBooking(bookingId));
                } else {
                    this.bookingView.displayMessage("Booking was not cancelled.");
                }
            } catch (NumberFormatException e) {
                this.bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.bookingView.displayMessage(e.getMessage());
            }
        }
    }

    public void runSellerMenu() {
        while (true) {
            UserResponseDto seller = this.requireRole(UserRole.SELLER);
            try {
                switch (this.sellerView.showSellerMenu()) {
                    case 1:
                        String keyword = this.sellerView.readFanKeyword();
                        if (keyword != null) this.sellerView.displayFans(this.fanController.searchFans(keyword));
                        break;
                    case 2:
                        RegisterFanRequestDto registration = this.sellerView.readRegisterFanRequest();
                        if (registration != null) this.sellerView.displayCreatedFan(this.fanController.register(registration));
                        else this.sellerView.displayMessage("Account creation cancelled.");
                        break;
                    case 3:
                        this.runTicketSalesMenu(seller.getId());
                        break;
                    case 4:
                        List<MatchResponseDto> matches = this.browseController.filterMatches(null, null);
                        this.bookingView.displayMatches(matches);
                        if (matches.isEmpty()) break;
                        Long matchId = this.sellerView.readMatchId();
                        if (matchId == null) break;
                        this.bookingView.displayMatchDetails(this.browseController.viewMatchDetails(matchId));
                        this.seatMapView.displaySeatMap(this.browseController.getSeatMap(matchId));
                        break;
                    case 5:
                        UserResponseDto fan = this.selectFan();
                        if (fan != null) this.bookingView.displayBookings(this.getBookingsForFan(fan.getId()));
                        break;
                    case 0:
                        return;
                    default:
                        this.sellerView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.sellerView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.sellerView.displayMessage(e.getMessage());
            }
        }
    }

    private void runTicketSalesMenu(Long sellerId) {
        while (true) {
            this.requireRole(UserRole.SELLER);
            try {
                switch (this.sellerView.showTicketSalesMenu()) {
                    case 1:
                        this.sellTicketsAtCounter(sellerId);
                        break;
                    case 2:
                        BookingResponseDto booking = this.selectSellerBooking();
                        if (booking != null) this.collectOfflinePayment(sellerId, booking);
                        break;
                    case 0:
                        return;
                    default:
                        this.sellerView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.sellerView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.sellerView.displayMessage(e.getMessage());
            }
        }
    }

    private void sellTicketsAtCounter(Long sellerId) {
        int customerType = this.sellerView.showCustomerTypeMenu();
        if (customerType == 0) return;
        UserResponseDto customer = null;
        switch (customerType) {
            case 1:
                customer = this.selectFan();
                if (customer == null) return;
                break;
            case 2:
                break;
            default:
                this.sellerView.displayMessage("Choose an existing account or walk-in customer. Use Create Fan to register an account.");
                return;
        }
        List<MatchResponseDto> matches = this.browseController.filterMatches(null, null);
        this.bookingView.displayMatches(matches);
        if (matches.isEmpty()) return;
        Long matchId = this.sellerView.readMatchId();
        if (matchId == null) return;
        MatchResponseDto match = this.browseController.viewMatchDetails(matchId);
        this.bookingView.displayMatchDetails(match);
        if (match.getSaleStatus() != SaleStatus.OPEN
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            this.sellerView.displayMessage("Ticket sales are closed for this match.");
            return;
        }
        List<MatchSeatResponseDto> seats = this.browseController.getSeatMap(matchId);
        this.seatMapView.displaySeatMap(seats);
        boolean available = false;
        for (MatchSeatResponseDto seat : seats) {
            if (seat.isAvailable()) available = true;
        }
        if (!available) {
            this.sellerView.displayMessage("No seats are AVAILABLE for this match.");
            return;
        }
        Long fanId = customer == null ? null : customer.getId();
        BookingRequestDto request = this.sellerView.readBookingRequest(fanId, matchId);
        if (request == null) {
            this.sellerView.displayMessage("Booking cancelled.");
            return;
        }
        BookingResponseDto booking = this.createBookingForCustomer(sellerId, request);
        this.sellerView.displayBookingResponse(booking);
        this.collectOfflinePayment(sellerId, booking);
    }

    private BookingResponseDto selectSellerBooking() {
        Long bookingId = this.sellerView.readBookingId();
        if (bookingId == null) return null;
        BookingResponseDto booking = this.getBookingDetails(bookingId);
        if (booking.getFanId() != null) this.bookingService.validateFanAccount(booking.getFanId());
        this.sellerView.displayBookingResponse(booking);
        this.sellerView.displayTickets(this.getTicketsForBooking(bookingId));
        return booking;
    }

    private void collectOfflinePayment(Long sellerId, BookingResponseDto booking) {
        if (booking.getFanId() != null) this.bookingService.validateFanAccount(booking.getFanId());
        if (booking.getStatus() == BookingStatus.EXPIRED) {
            this.sellerView.displayMessage("Booking has expired. Offline payment is no longer allowed. Create a new booking.");
            return;
        }
        if (booking.getStatus() != BookingStatus.PENDING
                && booking.getStatus() != BookingStatus.CONFIRMED) {
            this.sellerView.displayMessage("Offline payment requires a PENDING booking within its payment deadline.");
            return;
        }
        PaymentRequestDto payment;
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            this.sellerView.displayMessage("Booking is already confirmed. Checking its existing cash payment.");
            payment = new PaymentRequestDto(booking.getBookingId(), booking.getTotalAmount());
        } else {
            payment = this.sellerView.readPaymentRequest(booking);
        }
        if (payment == null) {
            this.sellerView.displayMessage("No payment recorded. Keep Booking ID " + booking.getBookingId()
                    + " to pay before " + booking.getExpiresAt() + ".");
            return;
        }
        PaymentResponseDto result = this.acceptOfflinePayment(sellerId, payment);
        this.sellerView.displayPaymentResponse(result);
        if (result.getStatus() == PaymentStatus.SUCCESS) {
            this.sellerView.displayTickets(this.issueTickets(booking.getBookingId()));
        } else {
            this.sellerView.displayMessage("Cash sale failed because the selected seats could not be committed. "
                    + "No tickets were issued. Return any cash collected and check current seat availability.");
        }
    }

    private UserResponseDto selectFan() {
        String keyword = this.sellerView.readFanKeyword();
        if (keyword == null) return null;
        List<UserResponseDto> fans = this.fanController.searchFans(keyword);
        this.sellerView.displayFans(fans);
        if (fans.isEmpty()) {
            this.sellerView.displayMessage("No matching Fan account. Choose Walk-in customer or register a Fan account.");
            return null;
        }
        Long fanId = this.sellerView.readFanId();
        if (fanId == null) return null;
        for (UserResponseDto fan : fans) {
            if (fan.getId().equals(fanId)) {
                this.bookingService.validateFanAccount(fan.getId());
                this.sellerView.displayMessage("Customer: " + fan.getFullName() + " | " + fan.getEmail());
                return fan;
            }
        }
        this.sellerView.displayMessage("Choose a Fan ID from these results.");
        return null;
    }

    public List<BookingResponseDto> getBookingsForFan(Long fanId) {
        this.requireRole(UserRole.SELLER);
        return this.bookingService.getMyBookings(fanId);
    }

    public BookingResponseDto createBooking(BookingRequestDto request) {
        UserResponseDto fan = this.requireRole(UserRole.FAN);
        if (request == null || !fan.getId().equals(request.getFanId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan can only create own booking");
        }
        return this.bookingService.createBooking(request);
    }

    public BookingResponseDto createBookingForFan(Long sellerId,
                                                   BookingRequestDto request) {
        UserResponseDto seller = this.requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return this.bookingService.createBookingForFan(sellerId, request);
    }

    public BookingResponseDto createBookingForCustomer(Long sellerId,
                                                        BookingRequestDto request) {
        UserResponseDto seller = this.requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return this.bookingService.createBookingForCustomer(sellerId, request);
    }

    public PaymentResponseDto makeOnlinePayment(PaymentRequestDto request) {
        UserResponseDto fan = this.requireRole(UserRole.FAN);
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment request is required");
        }
        BookingResponseDto booking = this.bookingService.getBookingDetails(request.getBookingId());
        if (!fan.getId().equals(booking.getFanId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan cannot pay another fan's booking");
        }
        return this.bookingService.makeOnlinePayment(request);
    }

    public PaymentResponseDto acceptOfflinePayment(Long sellerId,
                                                    PaymentRequestDto request) {
        UserResponseDto seller = this.requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return this.bookingService.acceptOfflinePayment(sellerId, request);
    }

    public List<TicketResponseDto> issueTickets(Long bookingId) {
        this.requireRole(UserRole.SELLER);
        return this.bookingService.issueTickets(bookingId);
    }

    public List<BookingResponseDto> getMyBookings(Long fanId) {
        UserResponseDto fan = this.requireRole(UserRole.FAN);
        if (!fan.getId().equals(fanId)) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan can only see own bookings");
        }
        return this.bookingService.getMyBookings(fanId);
    }

    public List<TicketResponseDto> getTicketsForBooking(Long bookingId) {
        this.getBookingDetails(bookingId);
        return this.bookingService.getTicketsForBooking(bookingId);
    }

    public BookingResponseDto getBookingDetails(Long bookingId) {
        UserResponseDto user = this.requireSignedIn();
        BookingResponseDto booking = this.bookingService.getBookingDetails(bookingId);
        if (user.getRole() == UserRole.FAN) {
            if (!user.getId().equals(booking.getFanId())) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Fan cannot see another fan's booking");
            }
        } else if (user.getRole() != UserRole.SELLER
                && user.getRole() != UserRole.ADMIN) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Role cannot view booking details");
        }
        return booking;
    }

    public BookingResponseDto cancelBooking(Long bookingId) {
        this.requireRole(UserRole.FAN);
        this.getBookingDetails(bookingId);
        return this.bookingService.cancelBooking(bookingId);
    }

    private UserResponseDto requireRole(UserRole role) {
        UserResponseDto user = this.requireSignedIn();
        if (user.getRole() != role) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Role " + role + " is required");
        }
        return user;
    }

    private UserResponseDto requireSignedIn() {
        UserResponseDto user = this.authController.getCurrentUser();
        if (user == null || user.getId() == null || user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Active login required");
        }
        return user;
    }
}
