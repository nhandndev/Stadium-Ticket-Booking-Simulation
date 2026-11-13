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
        System.out.println("\n=== SELLER ===");
        System.out.println("1. Search Fan");
        System.out.println("2. Create Fan");
        System.out.println("3. Ticket sales");
        System.out.println("4. View seat map and prices");
        System.out.println("5. View customer booking history");
        System.out.println("0. Back to dashboard");
        System.out.print("Choose: ");
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public int showCustomerTypeMenu() {
        System.out.println("\n=== WHO IS BUYING AT THE COUNTER? ===");
        System.out.println("1. Existing Fan account - search and select an ACTIVE account");
        System.out.println("2. Walk-in customer - buy directly, without an account");
        System.out.println("0. Back to Ticket sales");
        System.out.print("Choose customer type: ");
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public int showTicketSalesMenu() {
        System.out.println("\n=== SELLER > TICKET SALES ===");
        System.out.println("1. Create booking for Fan / walk-in customer");
        System.out.println("2. Pay for an existing booking");
        System.out.println("0. Back to Seller menu");
        System.out.print("Choose: ");
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public String readFanKeyword() {
        System.out.print("Customer name/email (Enter = List all; 0 = Back): ");
        String keyword = this.scanner.nextLine().trim();
        return keyword.equals("0") ? null : keyword;
    }

    public RegisterFanRequestDto readRegisterFanRequest() {
        System.out.print("Full name (Enter = Cancel): ");
        String name = this.scanner.nextLine().trim();
        if (name.isEmpty()) return null;
        System.out.print("Email: ");
        String email = this.scanner.nextLine();
        System.out.print("Password (at least 8 characters): ");
        String password = this.scanner.nextLine();
        System.out.print("Phone: ");
        String phone = this.scanner.nextLine();
        return new RegisterFanRequestDto(name, email, password, phone);
    }

    public BookingRequestDto readBookingRequest(Long fanId, Long matchId) {
        this.displayMessage("Use numbers from the SEAT ID column, not row labels such as A1.");
        System.out.print("Seat IDs (comma-separated, 1 to 4; 0 = Back): ");
        String input = this.scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        String[] parts = input.split(",", -1);
        if (parts.length < 1 || parts.length > 4) {
            throw new IllegalArgumentException("Select 1 to 4 SEAT IDs; received " + parts.length + " entries");
        }
        List<Long> seatIds = new ArrayList<>();
        for (String part : parts) {
            long id;
            try {
                id = Long.parseLong(part.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid SEAT ID '" + part.trim()
                        + "'. Use whole numbers separated by commas, for example 1,2,3,4.");
            }
            if (id <= 0) {
                throw new IllegalArgumentException("SEAT ID " + id + " must be positive");
            }
            if (seatIds.contains(id)) {
                throw new IllegalArgumentException("SEAT ID " + id + " was entered more than once");
            }
            seatIds.add(id);
        }
        return new BookingRequestDto(fanId, matchId, seatIds);
    }

    public PaymentRequestDto readPaymentRequest(BookingResponseDto booking) {
        System.out.println("Cash amount due: " + booking.getTotalAmount().toPlainString() + " VND");
        while (true) {
            System.out.print("Type RECEIVED after collecting cash (Enter = Cancel): ");
            String answer = this.scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equals("0")) return null;
            if (answer.equalsIgnoreCase("RECEIVED")) {
                return new PaymentRequestDto(booking.getBookingId(), booking.getTotalAmount());
            }
            this.displayMessage("No payment recorded. Type RECEIVED or press Enter to cancel.");
        }
    }

    public Long readFanId() {
        return this.readPositiveId("Fan ID: ");
    }

    public Long readMatchId() {
        return this.readPositiveId("Match ID: ");
    }

    public Long readBookingId() {
        this.displayMessage("Enter the single Booking ID from the counter receipt, for either customer type.");
        return this.readPositiveId("Booking ID: ");
    }

    private Long readPositiveId(String label) {
        System.out.print(label + "(0 = Back): ");
        String input = this.scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        long id;
        try {
            id = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label.trim()
                    + " requires one numeric ID. Comma-separated values are only accepted at the Seat IDs prompt.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        return id;
    }

    public void displayFans(List<UserResponseDto> fans) {
        if (fans.isEmpty()) {
            this.displayMessage("No Fan found.");
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
                + (booking.getFanId() == null ? " | Walk-in customer" : " | Fan #" + booking.getFanId())
                + " " + booking.getFanName() + " | " + booking.getMatchName()
                + " | Seats " + booking.getSeatLabels()
                + " | Total " + booking.getTotalAmount().toPlainString() + " VND"
                + " | Status " + booking.getStatus());
        this.displayMessage("Keep Booking ID " + booking.getBookingId() + " for payment or ticket issuance.");
        if (booking.getStatus() == BookingStatus.PENDING) {
            this.displayMessage("Collect cash before recording offline payment.");
            this.displayMessage("Seats held until: " + booking.getExpiresAt());
        }
    }

    public void displayPaymentResponse(PaymentResponseDto payment) {
        System.out.println("Payment #" + payment.getPaymentId()
                + " | Booking #" + payment.getBookingId() + " | " + payment.getAmount().toPlainString() + " VND"
                + " | Method " + payment.getMethod()
                + " | Status " + payment.getStatus());
    }

    public void displayTickets(List<TicketResponseDto> tickets) {
        if (tickets.isEmpty()) this.displayMessage("No tickets found.");
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
