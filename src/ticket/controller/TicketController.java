package ticket.controller;

import auth.controller.AuthController;
import booking.model.Booking;
import booking.repository.BookingRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import ticket.dto.TicketResponseDto;
import ticket.service.TicketService;
import gate.view.GateView;
import user.dto.UserResponseDto;
import user.enums.UserRole;
import user.enums.UserStatus;

public class TicketController {
    private TicketService ticketService;
    private AuthController authController;
    private BookingRepository bookingRepository;
    private final GateView gateView;

    public TicketController(TicketService ticketService, AuthController authController,
                            BookingRepository bookingRepository, GateView gateView) {
        if (ticketService == null || authController == null
                || bookingRepository == null || gateView == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket dependencies are required");
        }
        this.ticketService = ticketService;
        this.authController = authController;
        this.bookingRepository = bookingRepository;
        this.gateView = gateView;
    }

    public void runGateMenu() {
        while (true) {
            requireGate();
            try {
                int choice = gateView.showGateMenu();
                switch (choice) {
                    case 1:
                    case 2:
                        String code = gateView.readTicketCode();
                        if (code == null) break;
                        Long matchId = gateView.readMatchId();
                        if (matchId == null) break;
                        gateView.displayTicket(validateTicket(code, matchId));
                        if (choice == 1) break;
                        if (gateView.confirmCheckIn()) {
                            gateView.displayTicket(checkInTicket(code, matchId));
                        } else {
                            gateView.displayMessage("Check-in cancelled.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        gateView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                gateView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                gateView.displayMessage(e.getMessage());
            }
        }
    }

    public TicketResponseDto validateTicket(String ticketCode, Long matchId) {
        requireGate();
        return ticketService.validateTicket(ticketCode, matchId);
    }

    public TicketResponseDto getTicketDetails(Long ticketId) {
        UserResponseDto user = requireSignedIn();
        TicketResponseDto ticket = ticketService.getTicketDetails(ticketId);
        if (user.getRole() == UserRole.FAN) {
            Booking booking = bookingRepository.findById(ticket.getBookingId());
            if (booking == null || !user.getId().equals(booking.getFanId())) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Fan cannot see another fan's ticket");
            }
        } else if (user.getRole() != UserRole.SELLER
                && user.getRole() != UserRole.ADMIN) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Role cannot view ticket details");
        }
        return ticket;
    }

    public TicketResponseDto checkInTicket(String ticketCode, Long matchId) {
        requireGate();
        return ticketService.checkInTicket(ticketCode, matchId);
    }

    private void requireGate() {
        if (requireSignedIn().getRole() != UserRole.GATE_STAFF) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Gate Staff role is required");
        }
    }

    private UserResponseDto requireSignedIn() {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || user.getId() == null || user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Active login required");
        }
        return user;
    }
}
