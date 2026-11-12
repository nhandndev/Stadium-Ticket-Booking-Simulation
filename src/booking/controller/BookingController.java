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
            UserResponseDto fan = requireRole(UserRole.FAN);
            try {
                int group = bookingView.showBookingMenu();
                if (group == 0) return;
                if (group == 4) runRefundMenu(fan.getId());
                else if (group >= 1 && group <= 3) runFanBookingActions(group);
                else bookingView.displayMessage("Invalid choice.");
            } catch (NumberFormatException e) {
                bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                bookingView.displayMessage(e.getMessage());
            }
        }
    }

    private void runFanBookingActions(int group) {
        while (true) {
            UserResponseDto fan = requireRole(UserRole.FAN);
            try {
                int action = bookingView.showBookingActionsMenu(group);
                if (action == 0) return;
                if (action < 1 || action > (group == 2 ? 3 : 2)) {
                    bookingView.displayMessage("Invalid choice.");
                    continue;
                }
                int choice = action;
                if (group == 2) choice = action == 1 ? 4 : action == 2 ? 8 : 3;
                if (group == 3) choice = action + 4;
                switch (choice) {
                    case 1:
                    case 2:
                        List<MatchResponseDto> matches = browseController.filterMatches(null, null);
                        bookingView.displayMatches(matches);
                        if (matches.isEmpty()) break;
                        Long matchId = bookingView.readMatchId();
                        if (matchId == null) break;
                        if (choice == 2) {
                            bookMatch(matchId);
                        } else {
                            bookingView.displayMatchDetails(browseController.viewMatchDetails(matchId));
                            seatMapView.displaySeatMap(browseController.getSeatMap(matchId));
                        }
                        break;
                    case 3:
                        List<BookingResponseDto> pending = getMyBookings(fan.getId());
                        pending.removeIf(booking -> booking.getStatus() != BookingStatus.PENDING);
                        if (pending.isEmpty()) {
                            bookingView.displayMessage("No active bookings are waiting for payment. Unpaid bookings expire after at most 30 minutes; check booking history.");
                            break;
                        }
                        bookingView.displayBookings(pending);
                        Long bookingId = bookingView.readBookingId();
                        if (bookingId == null) break;
                        BookingResponseDto booking = getBookingDetails(bookingId);
                        if (booking.getStatus() != BookingStatus.PENDING) {
                            bookingView.displayMessage("Choose a booking waiting for payment.");
                            break;
                        }
                        bookingView.displayBookingResponse(booking);
                        payBooking(booking);
                        break;
                    case 4:
                        bookingView.displayBookings(getMyBookings(fan.getId()));
                        break;
                    case 5:
                        bookingView.displayTickets(fanController.getMyTickets(fan.getId()));
                        break;
                    case 6:
                        List<TicketResponseDto> tickets = fanController.getMyTickets(fan.getId());
                        bookingView.displayTickets(tickets);
                        if (tickets.isEmpty()) break;
                        Long ticketId = bookingView.readTicketId();
                        if (ticketId != null) {
                            bookingView.displayTicketDetails(ticketController.getTicketDetails(ticketId));
                        }
                        break;
                    case 8:
                        List<BookingResponseDto> bookings = getMyBookings(fan.getId());
                        bookingView.displayBookings(bookings);
                        if (bookings.isEmpty()) break;
                        Long detailId = bookingView.readBookingId();
                        if (detailId != null) bookingView.displayBookingResponse(getBookingDetails(detailId));
                        break;
                    case 0:
                        return;
                    default:
                        bookingView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                bookingView.displayMessage(e.getMessage());
            }
        }
    }

    public void bookMatch(Long matchId) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        MatchResponseDto match = browseController.viewMatchDetails(matchId);
        bookingView.displayMatchDetails(match);
        if (match.getSaleStatus() != SaleStatus.OPEN
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            bookingView.displayMessage("Ticket sales are closed for this match.");
            return;
        }
        List<MatchSeatResponseDto> seats = browseController.getSeatMap(matchId);
        seatMapView.displaySeatMap(seats);
        boolean available = false;
        for (MatchSeatResponseDto seat : seats) {
            if (seat.isAvailable()) available = true;
        }
        if (!available) {
            bookingView.displayMessage("No seats are available for booking.");
            return;
        }
        BookingRequestDto request = bookingView.readBookingRequest(fan.getId(), matchId);
        if (request == null) {
            bookingView.displayMessage("Booking cancelled.");
            return;
        }
        BookingResponseDto booking = createBooking(request);
        bookingView.displayBookingResponse(booking);
        payBooking(booking);
    }

    private void payBooking(BookingResponseDto booking) {
        PaymentRequestDto payment = bookingView.readPaymentRequest(booking);
        if (payment == null) {
            bookingView.displayMessage("Not paid. Pay before " + booking.getExpiresAt() + " online or give the Booking ID to a Seller for offline payment.");
            return;
        }
        PaymentResponseDto result = makeOnlinePayment(payment);
        bookingView.displayPaymentResponse(result);
        if (result.getStatus() == PaymentStatus.SUCCESS) {
            List<TicketResponseDto> tickets = fanController.getMyTickets(booking.getFanId());
            tickets.removeIf(ticket -> !ticket.getBookingId().equals(booking.getBookingId()));
            bookingView.displayTickets(tickets);
        }
    }

    private void runRefundMenu(Long fanId) {
        while (true) {
            requireRole(UserRole.FAN);
            try {
                int choice = bookingView.showRefundMenu();
                if (choice == 0) return;
                if (choice == 3) {
                    bookingView.displayRefunds(refundController.getMyRefunds(fanId));
                    continue;
                }
                if (choice != 1 && choice != 2) {
                    bookingView.displayMessage("Invalid choice.");
                    continue;
                }
                List<BookingResponseDto> bookings = getMyBookings(fanId);
                BookingStatus required = choice == 1 ? BookingStatus.CONFIRMED : BookingStatus.PENDING;
                bookings.removeIf(booking -> booking.getStatus() != required);
                if (bookings.isEmpty()) {
                    bookingView.displayMessage(choice == 1 ? "No paid bookings available for a refund request."
                            : "No unpaid bookings available to cancel.");
                    continue;
                }
                bookingView.displayBookings(bookings);
                Long bookingId = bookingView.readBookingId();
                if (bookingId == null) continue;
                BookingResponseDto booking = getBookingDetails(bookingId);
                if (booking.getStatus() != required) {
                    bookingView.displayMessage("Choose a booking from the displayed list.");
                    continue;
                }
                bookingView.displayBookingResponse(booking);
                if (choice == 1) {
                    RefundRequestDto request = bookingView.readRefundRequest(fanId, bookingId);
                    if (request != null) bookingView.displayRefundResponse(refundController.requestRefund(request));
                    else bookingView.displayMessage("Refund request cancelled.");
                } else if (bookingView.confirmCancellation()) {
                    bookingView.displayBookingResponse(cancelBooking(bookingId));
                } else {
                    bookingView.displayMessage("Booking was not cancelled.");
                }
            } catch (NumberFormatException e) {
                bookingView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                bookingView.displayMessage(e.getMessage());
            }
        }
    }

    public void runSellerMenu() {
        while (true) {
            UserResponseDto seller = requireRole(UserRole.SELLER);
            try {
                switch (sellerView.showSellerMenu()) {
                    case 1:
                        String keyword = sellerView.readFanKeyword();
                        if (keyword != null) sellerView.displayFans(fanController.searchFans(keyword));
                        break;
                    case 2:
                        RegisterFanRequestDto registration = sellerView.readRegisterFanRequest();
                        if (registration != null) sellerView.displayCreatedFan(fanController.register(registration));
                        else sellerView.displayMessage("Account creation cancelled.");
                        break;
                    case 3:
                        sellTicketsAtCounter(seller.getId());
                        break;
                    case 4:
                        BookingResponseDto unpaid = selectSellerBooking();
                        if (unpaid != null) collectOfflinePayment(seller.getId(), unpaid);
                        break;
                    case 5:
                        BookingResponseDto paid = selectSellerBooking();
                        if (paid != null) sellerView.displayTickets(issueTickets(paid.getBookingId()));
                        break;
                    case 6:
                        List<MatchResponseDto> matches = browseController.filterMatches(null, null);
                        bookingView.displayMatches(matches);
                        if (matches.isEmpty()) break;
                        Long matchId = sellerView.readMatchId();
                        if (matchId == null) break;
                        bookingView.displayMatchDetails(browseController.viewMatchDetails(matchId));
                        seatMapView.displaySeatMap(browseController.getSeatMap(matchId));
                        break;
                    case 7:
                        UserResponseDto fan = selectFan();
                        if (fan != null) bookingView.displayBookings(getBookingsForFan(fan.getId()));
                        break;
                    case 0:
                        return;
                    default:
                        sellerView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                sellerView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                sellerView.displayMessage(e.getMessage());
            }
        }
    }

    private void sellTicketsAtCounter(Long sellerId) {
        int customerType = sellerView.showCustomerTypeMenu();
        if (customerType == 0) return;
        UserResponseDto customer = null;
        switch (customerType) {
            case 1:
                customer = selectFan();
                if (customer == null) return;
                break;
            case 2:
                break;
            default:
                sellerView.displayMessage("Choose an existing account or walk-in customer. Use Create Fan to register an account.");
                return;
        }
        List<MatchResponseDto> matches = browseController.filterMatches(null, null);
        bookingView.displayMatches(matches);
        if (matches.isEmpty()) return;
        Long matchId = sellerView.readMatchId();
        if (matchId == null) return;
        MatchResponseDto match = browseController.viewMatchDetails(matchId);
        bookingView.displayMatchDetails(match);
        if (match.getSaleStatus() != SaleStatus.OPEN
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            sellerView.displayMessage("Ticket sales are closed for this match.");
            return;
        }
        List<MatchSeatResponseDto> seats = browseController.getSeatMap(matchId);
        seatMapView.displaySeatMap(seats);
        boolean available = false;
        for (MatchSeatResponseDto seat : seats) {
            if (seat.isAvailable()) available = true;
        }
        if (!available) {
            sellerView.displayMessage("No seats are AVAILABLE for this match.");
            return;
        }
        Long fanId = customer == null ? null : customer.getId();
        BookingRequestDto request = sellerView.readBookingRequest(fanId, matchId);
        if (request == null) {
            sellerView.displayMessage("Booking cancelled.");
            return;
        }
        BookingResponseDto booking = createBookingForCustomer(sellerId, request);
        sellerView.displayBookingResponse(booking);
        sellerView.displayMessage("Use 4. Accept offline payment before the deadline, then 5. Issue ticket.");
    }

    private BookingResponseDto selectSellerBooking() {
        Long bookingId = sellerView.readBookingId();
        if (bookingId == null) return null;
        BookingResponseDto booking = getBookingDetails(bookingId);
        if (booking.getFanId() != null) bookingService.validateFanAccount(booking.getFanId());
        sellerView.displayBookingResponse(booking);
        return booking;
    }

    private void collectOfflinePayment(Long sellerId, BookingResponseDto booking) {
        if (booking.getFanId() != null) bookingService.validateFanAccount(booking.getFanId());
        if (booking.getStatus() == BookingStatus.EXPIRED) {
            sellerView.displayMessage("Booking has expired. Offline payment is no longer allowed. Create a new booking.");
            return;
        }
        if (booking.getStatus() != BookingStatus.PENDING) {
            sellerView.displayMessage("Offline payment requires a PENDING booking within its payment deadline.");
            return;
        }
        PaymentRequestDto payment = sellerView.readPaymentRequest(booking);
        if (payment == null) {
            sellerView.displayMessage("No payment recorded. Keep Booking ID " + booking.getBookingId()
                    + " to pay before " + booking.getExpiresAt() + ".");
            return;
        }
        PaymentResponseDto result = acceptOfflinePayment(sellerId, payment);
        sellerView.displayPaymentResponse(result);
        if (result.getStatus() == PaymentStatus.SUCCESS) {
            sellerView.displayMessage("Offline payment recorded. Use 5. Issue ticket to issue the paid tickets.");
        } else {
            sellerView.displayMessage("Cash sale failed because the selected seats could not be committed. "
                    + "No tickets were issued. Return any cash collected and check current seat availability.");
        }
    }

    private UserResponseDto selectFan() {
        String keyword = sellerView.readFanKeyword();
        if (keyword == null) return null;
        List<UserResponseDto> fans = fanController.searchFans(keyword);
        sellerView.displayFans(fans);
        if (fans.isEmpty()) {
            sellerView.displayMessage("No matching Fan account. Choose Walk-in customer or register a Fan account.");
            return null;
        }
        Long fanId = sellerView.readFanId();
        if (fanId == null) return null;
        for (UserResponseDto fan : fans) {
            if (fan.getId().equals(fanId)) {
                bookingService.validateFanAccount(fan.getId());
                sellerView.displayMessage("Customer: " + fan.getFullName() + " | " + fan.getEmail());
                return fan;
            }
        }
        sellerView.displayMessage("Choose a Fan ID from these results.");
        return null;
    }

    public List<BookingResponseDto> getBookingsForFan(Long fanId) {
        requireRole(UserRole.SELLER);
        return bookingService.getMyBookings(fanId);
    }

    public BookingResponseDto createBooking(BookingRequestDto request) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        if (request == null || !fan.getId().equals(request.getFanId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan can only create own booking");
        }
        return bookingService.createBooking(request);
    }

    public BookingResponseDto createBookingForFan(Long sellerId,
                                                   BookingRequestDto request) {
        UserResponseDto seller = requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return bookingService.createBookingForFan(sellerId, request);
    }

    public BookingResponseDto createBookingForCustomer(Long sellerId,
                                                        BookingRequestDto request) {
        UserResponseDto seller = requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return bookingService.createBookingForCustomer(sellerId, request);
    }

    public PaymentResponseDto makeOnlinePayment(PaymentRequestDto request) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment request is required");
        }
        BookingResponseDto booking = bookingService.getBookingDetails(request.getBookingId());
        if (!fan.getId().equals(booking.getFanId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan cannot pay another fan's booking");
        }
        return bookingService.makeOnlinePayment(request);
    }

    public PaymentResponseDto acceptOfflinePayment(Long sellerId,
                                                    PaymentRequestDto request) {
        UserResponseDto seller = requireRole(UserRole.SELLER);
        if (!seller.getId().equals(sellerId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Wrong seller ID");
        }
        return bookingService.acceptOfflinePayment(sellerId, request);
    }

    public List<TicketResponseDto> issueTickets(Long bookingId) {
        requireRole(UserRole.SELLER);
        return bookingService.issueTickets(bookingId);
    }

    public List<BookingResponseDto> getMyBookings(Long fanId) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        if (!fan.getId().equals(fanId)) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan can only see own bookings");
        }
        return bookingService.getMyBookings(fanId);
    }

    public BookingResponseDto getBookingDetails(Long bookingId) {
        UserResponseDto user = requireSignedIn();
        BookingResponseDto booking = bookingService.getBookingDetails(bookingId);
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
        requireRole(UserRole.FAN);
        getBookingDetails(bookingId);
        return bookingService.cancelBooking(bookingId);
    }

    private UserResponseDto requireRole(UserRole role) {
        UserResponseDto user = requireSignedIn();
        if (user.getRole() != role) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Role " + role + " is required");
        }
        return user;
    }

    private UserResponseDto requireSignedIn() {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || user.getId() == null || user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Active login required");
        }
        return user;
    }
}
