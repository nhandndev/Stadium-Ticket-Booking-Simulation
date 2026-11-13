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
            this.requireGate();
            try {
                int choice = this.gateView.showGateMenu();
                switch (choice) {
                    case 1:
                        String code = this.gateView.readTicketCode();
                        if (code == null) break;
                        this.gateView.displayTicket(this.validateTicket(code));
                        if (this.gateView.confirmCheckIn()) {
                            this.gateView.displayTicket(this.checkInTicket(code));
                        } else {
                            this.gateView.displayMessage("Check-in cancelled.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        this.gateView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.gateView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.gateView.displayMessage(e.getMessage());
            }
        }
    }

    public TicketResponseDto validateTicket(String ticketCode) {
        this.requireGate();
        return this.ticketService.validateTicket(ticketCode);
    }

    public TicketResponseDto getTicketDetails(Long ticketId) {
        UserResponseDto user = this.requireSignedIn();
        TicketResponseDto ticket = this.ticketService.getTicketDetails(ticketId);
        if (user.getRole() == UserRole.FAN) {
            Booking booking = this.bookingRepository.findById(ticket.getBookingId());
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

    public TicketResponseDto checkInTicket(String ticketCode) {
        this.requireGate();
        return this.ticketService.checkInTicket(ticketCode);
    }

    private void requireGate() {
        if (this.requireSignedIn().getRole() != UserRole.GATE_STAFF) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Gate Staff role is required");
        }
    }

    private UserResponseDto requireSignedIn() {
        UserResponseDto user = this.authController.getCurrentUser();
        if (user == null || user.getId() == null || user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Active login required");
        }
        return user;
    }
}
