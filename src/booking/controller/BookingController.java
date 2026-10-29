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
                int choice = bookingView.showBookingMenu();
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
                            bookingView.displayMessage("No bookings are waiting for payment.");
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
                    case 7:
                        runRefundMenu(fan.getId());
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
        bookingView.displayMessage("Seats are not held until payment succeeds.");
        payBooking(booking);
    }

    private void payBooking(BookingResponseDto booking) {
        PaymentRequestDto payment = bookingView.readPaymentRequest(booking);
        if (payment == null) {
            bookingView.displayMessage("Not paid. This booking remains pending; use Pay pending booking later.");
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
                int choice = sellerView.showSellerMenu();
                switch (choice) {
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
                    case 4:
                        UserResponseDto customer = choice == 4 ? selectFan() : null;
                        if (choice == 4 && customer == null) break;
                        List<MatchResponseDto> matches = browseController.filterMatches(null, null);
                        bookingView.displayMatches(matches);
                        if (matches.isEmpty()) break;
                        Long matchId = sellerView.readMatchId();
                        if (matchId == null) break;
                        MatchResponseDto match = browseController.viewMatchDetails(matchId);
                        bookingView.displayMatchDetails(match);
                        List<MatchSeatResponseDto> seats = browseController.getSeatMap(matchId);
                        seatMapView.displaySeatMap(seats);
                        if (choice == 3) break;
                        if (match.getSaleStatus() != SaleStatus.OPEN
                                || !match.getStartTime().isAfter(LocalDateTime.now())) {
                            sellerView.displayMessage("Ticket sales are closed for this match.");
                            break;
                        }
                        boolean available = false;
                        for (MatchSeatResponseDto seat : seats) {
                            if (seat.isAvailable()) available = true;
                        }
                        if (!available) {
                            sellerView.displayMessage("No seats are available for booking.");
                            break;
                        }
                        BookingRequestDto request = sellerView.readBookingRequest(customer.getId(), matchId);
                        if (request != null) sellerView.displayBookingResponse(createBookingForFan(seller.getId(), request));
                        else sellerView.displayMessage("Booking cancelled.");
                        break;
                    case 5:
                    case 6:
                    case 7:
                        UserResponseDto fan = selectFan();
                        if (fan == null) break;
                        List<BookingResponseDto> bookings = getBookingsForFan(fan.getId());
                        if (choice == 5) bookings.removeIf(b -> b.getStatus() != BookingStatus.PENDING);
                        if (choice == 6) bookings.removeIf(b -> b.getStatus() != BookingStatus.CONFIRMED);
                        if (bookings.isEmpty()) {
                            sellerView.displayMessage("No bookings match this action for this customer.");
                            break;
                        }
                        bookingView.displayBookings(bookings);
                        if (choice == 7) break;
                        Long bookingId = sellerView.readBookingId();
                        if (bookingId == null) break;
                        BookingResponseDto booking = getBookingDetails(bookingId);
                        if (!booking.getFanId().equals(fan.getId())) {
                            sellerView.displayMessage("This booking belongs to another customer.");
                            break;
                        }
                        bookingView.displayBookingResponse(booking);
                        if (choice == 6) {
                            sellerView.displayTickets(issueTickets(bookingId));
                            break;
                        }
                        if (booking.getStatus() != BookingStatus.PENDING) {
                            sellerView.displayMessage("Choose a booking waiting for payment.");
                            break;
                        }
                        PaymentRequestDto payment = sellerView.readPaymentRequest(booking);
                        if (payment == null) {
                            sellerView.displayMessage("No payment recorded.");
                            break;
                        }
                        PaymentResponseDto result = acceptOfflinePayment(seller.getId(), payment);
                        sellerView.displayPaymentResponse(result);
                        if (result.getStatus() == PaymentStatus.SUCCESS && sellerView.confirmIssueTickets()) {
                            sellerView.displayTickets(issueTickets(bookingId));
                        }
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

    private UserResponseDto selectFan() {
        String keyword = sellerView.readFanKeyword();
        if (keyword == null) return null;
        List<UserResponseDto> fans = fanController.searchFans(keyword);
        sellerView.displayFans(fans);
        if (fans.isEmpty()) {
            sellerView.displayMessage("Use Create Fan to add a new customer.");
            return null;
        }
        Long fanId = sellerView.readFanId();
        if (fanId == null) return null;
        for (UserResponseDto fan : fans) {
            if (fan.getId().equals(fanId)) {
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
