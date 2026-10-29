package seller.view;

import booking.dto.BookingRequestDto;
import booking.dto.BookingResponseDto;
import booking.enums.BookingStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import payment.dto.PaymentRequestDto;
import payment.dto.PaymentResponseDto;
import ticket.dto.TicketResponseDto;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;

public class SellerView {
    private final Scanner scanner;

    public SellerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showSellerMenu() {
        System.out.println();
        System.out.println("=== SELLER ===");
        System.out.println("1. Search Fan");
        System.out.println("2. Create Fan");
        System.out.println("3. View seat map");
        System.out.println("4. Create booking for Fan");
        System.out.println("5. Accept offline payment");
        System.out.println("6. Issue tickets");
        System.out.println("7. Customer bookings");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String readFanKeyword() {
        System.out.print("Customer name/email (Enter = List all; 0 = Back): ");
        String keyword = scanner.nextLine().trim();
        return keyword.equals("0") ? null : keyword;
    }

    public RegisterFanRequestDto readRegisterFanRequest() {
        System.out.print("Full name (Enter = Cancel): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return null;
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password (at least 8 characters): ");
        String password = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        return new RegisterFanRequestDto(name, email, password, phone);
    }

    public BookingRequestDto readBookingRequest(Long fanId, Long matchId) {
        System.out.print("Seat IDs (comma-separated, 1 to 4; 0 = Back): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        String[] parts = input.split(",", -1);
        if (parts.length < 1 || parts.length > 4) {
            throw new IllegalArgumentException("Choose 1 to 4 seats");
        }
        List<Long> seatIds = new ArrayList<>();
        for (String part : parts) {
            long id = Long.parseLong(part.trim());
            if (id <= 0 || seatIds.contains(id)) {
                throw new IllegalArgumentException("Seat IDs must be positive and unique");
            }
            seatIds.add(id);
        }
        return new BookingRequestDto(fanId, matchId, seatIds);
    }

    public PaymentRequestDto readPaymentRequest(BookingResponseDto booking) {
        System.out.println("Cash amount due: " + booking.getTotalAmount().toPlainString() + " VND");
        while (true) {
            System.out.print("Type RECEIVED after collecting cash (Enter = Cancel): ");
            String answer = scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equals("0")) return null;
            if (answer.equalsIgnoreCase("RECEIVED")) {
                return new PaymentRequestDto(booking.getBookingId(), booking.getTotalAmount());
            }
            displayMessage("No payment recorded. Type RECEIVED or press Enter to cancel.");
        }
    }

    public boolean confirmIssueTickets() {
        while (true) {
            System.out.print("Issue tickets now? (y/n, Enter = No): ");
            String answer = scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return false;
            if (answer.equalsIgnoreCase("y")) return true;
            displayMessage("Please enter y or n.");
        }
    }

    public Long readFanId() {
        return readPositiveId("Fan ID: ");
    }

    public Long readMatchId() {
        return readPositiveId("Match ID: ");
    }

    public Long readBookingId() {
        return readPositiveId("Booking ID: ");
    }

    private Long readPositiveId(String label) {
        System.out.print(label + "(0 = Back): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        long id = Long.parseLong(input);
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        return id;
    }

    public void displayFans(List<UserResponseDto> fans) {
        if (fans.isEmpty()) {
            displayMessage("No Fan found.");
        }
        for (UserResponseDto fan : fans) {
            System.out.println("Fan #" + fan.getId() + " | " + fan.getFullName()
                    + " | " + fan.getEmail() + " | " + fan.getStatus());
        }
    }

    public void displayCreatedFan(UserResponseDto fan) {
        System.out.println("Created Fan #" + fan.getId() + " | " + fan.getFullName() + " | " + fan.getEmail());
    }

    public void displayBookingResponse(BookingResponseDto booking) {
        System.out.println("Booking #" + booking.getBookingId()
                + " | Fan #" + booking.getFanId()
                + " " + booking.getFanName() + " | " + booking.getMatchName()
                + " | Seats " + booking.getSeatLabels()
                + " | Total " + booking.getTotalAmount().toPlainString() + " VND"
                + " | Status " + booking.getStatus());
        if (booking.getStatus() == BookingStatus.PENDING) {
            displayMessage("Collect cash before recording offline payment.");
            displayMessage("PENDING bookings do not hold seats.");
        }
    }

    public void displayPaymentResponse(PaymentResponseDto payment) {
        System.out.println("Payment #" + payment.getPaymentId()
                + " | Booking #" + payment.getBookingId() + " | " + payment.getAmount().toPlainString() + " VND"
                + " | Method " + payment.getMethod()
                + " | Status " + payment.getStatus());
    }

    public void displayTickets(List<TicketResponseDto> tickets) {
        if (tickets.isEmpty()) displayMessage("No tickets found.");
        for (TicketResponseDto ticket : tickets) {
            System.out.println("Ticket #" + ticket.getTicketId()
                    + " | Code " + ticket.getTicketCode()
                    + " | " + ticket.getMatchName()
                    + " | " + ticket.getSeatLabel()
                    + " | Status " + ticket.getStatus());
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
