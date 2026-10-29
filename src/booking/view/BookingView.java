package booking.view;

import booking.dto.BookingRequestDto;
import booking.dto.BookingResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import payment.dto.PaymentRequestDto;
import payment.dto.PaymentResponseDto;
import refund.dto.RefundRequestDto;
import refund.dto.RefundResponseDto;
import ticket.dto.TicketResponseDto;
import match.dto.MatchResponseDto;

public class BookingView {
    private final Scanner scanner;

    public BookingView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showBookingMenu() {
        System.out.println();
        System.out.println("=== FAN BOOKING ===");
        System.out.println("1. View seat map");
        System.out.println("2. Create booking");
        System.out.println("3. Pay pending booking");
        System.out.println("4. My bookings");
        System.out.println("5. My tickets / Ticket history");
        System.out.println("6. Ticket details");
        System.out.println("7. Cancellation / Refund");
        System.out.println("8. Booking details");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showRefundMenu() {
        System.out.println("1. Request refund for paid booking");
        System.out.println("2. Cancel unpaid pending booking");
        System.out.println("3. My refund requests | 0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
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
            long seatId = Long.parseLong(part.trim());
            if (seatId <= 0 || seatIds.contains(seatId)) {
                throw new IllegalArgumentException("Seat IDs must be positive and unique");
            }
            seatIds.add(seatId);
        }
        return new BookingRequestDto(fanId, matchId, seatIds);
    }

    public PaymentRequestDto readPaymentRequest(BookingResponseDto booking) {
        System.out.println("\n=== SIMULATED ONLINE PAYMENT ===");
        System.out.println("Amount due: " + booking.getTotalAmount().toPlainString() + " VND");
        while (true) {
            System.out.print("Pay now? (y/n, Enter = No): ");
            String answer = scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return null;
            if (answer.equalsIgnoreCase("y")) {
                return new PaymentRequestDto(booking.getBookingId(), booking.getTotalAmount());
            }
            displayMessage("Please enter y or n.");
        }
    }

    public RefundRequestDto readRefundRequest(Long fanId, Long bookingId) {
        System.out.print("Reason (Enter = Cancel): ");
        String reason = scanner.nextLine().trim();
        return reason.isEmpty() ? null : new RefundRequestDto(fanId, bookingId, reason);
    }

    public boolean confirmCancellation() {
        while (true) {
            System.out.print("Cancel this unpaid booking? (y/n, Enter = No): ");
            String answer = scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return false;
            if (answer.equalsIgnoreCase("y")) return true;
            displayMessage("Please enter y or n.");
        }
    }

    public Long readMatchId() {
        return readPositiveId("Match ID: ");
    }

    public Long readBookingId() {
        return readPositiveId("Booking ID: ");
    }

    public Long readTicketId() {
        return readPositiveId("Ticket ID: ");
    }

    private Long readPositiveId(String prompt) {
        System.out.print(prompt + "(0 = Back): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        long id = Long.parseLong(input);
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        return id;
    }

    public void displayBookingResponse(BookingResponseDto response) {
        System.out.println("\n=== BOOKING #" + response.getBookingId() + " ===");
        System.out.println("Customer: " + response.getFanName() + " (Fan #" + response.getFanId() + ")");
        System.out.println("Match: " + response.getMatchName() + " (#" + response.getMatchId() + ")");
        System.out.println("Seats: " + response.getSeatLabels() + " | IDs: " + response.getSeatIds());
        System.out.println("Total: " + response.getTotalAmount().toPlainString() + " VND | Status: " + response.getStatus());
    }

    public void displayMatches(List<MatchResponseDto> matches) {
        System.out.println("\n=== MATCHES ===");
        if (matches.isEmpty()) displayMessage("No matches available.");
        for (MatchResponseDto match : matches) {
            System.out.println(match.getId() + " | " + match.getHomeTeam() + " vs " + match.getAwayTeam()
                    + " | " + match.getStadiumName() + " | " + match.getStartTime()
                    + " | Sales: " + match.getSaleStatus());
        }
    }

    public void displayMatchDetails(MatchResponseDto match) {
        System.out.println("\nMatch #" + match.getId() + " | " + match.getHomeTeam()
                + " vs " + match.getAwayTeam() + " | " + match.getStadiumName()
                + " | " + match.getStartTime() + " | Sales: " + match.getSaleStatus());
    }

    public void displayBookings(List<BookingResponseDto> bookings) {
        if (bookings.isEmpty()) {
            displayMessage("No bookings yet.");
        }
        for (BookingResponseDto booking : bookings) {
            System.out.println("Booking #" + booking.getBookingId() + " | " + booking.getMatchName()
                    + " | " + booking.getSeatLabels() + " | " + booking.getTotalAmount().toPlainString()
                    + " VND | " + booking.getStatus());
        }
    }

    public void displayPaymentResponse(PaymentResponseDto response) {
        System.out.println("Payment #" + response.getPaymentId()
                + " | Booking #" + response.getBookingId()
                + " | Amount " + response.getAmount().toPlainString() + " VND"
                + " | Method " + response.getMethod()
                + " | Status " + response.getStatus());
        if (response.getStatus() == payment.enums.PaymentStatus.FAILED) {
            displayMessage("Payment failed. This booking is cancelled; check availability and create a new booking.");
        }
    }

    public void displayTickets(List<TicketResponseDto> tickets) {
        if (tickets.isEmpty()) {
            displayMessage("No tickets yet.");
        }
        for (TicketResponseDto ticket : tickets) {
            System.out.println("Ticket #" + ticket.getTicketId()
                    + " | Booking #" + ticket.getBookingId()
                    + " | " + ticket.getMatchName()
                    + " | " + ticket.getSeatLabel()
                    + " | Code " + ticket.getTicketCode()
                    + " | Status " + ticket.getStatus());
        }
    }

    public void displayTicketDetails(TicketResponseDto ticket) {
        System.out.println("Ticket #" + ticket.getTicketId()
                + " | Booking #" + ticket.getBookingId()
                + " | " + ticket.getMatchName()
                + " | " + ticket.getSeatLabel());
        System.out.println("Code: " + ticket.getTicketCode()
                + " | Status: " + ticket.getStatus());
        System.out.println("Checked in at: "
                + (ticket.getCheckedInAt() == null ? "Not checked in"
                        : ticket.getCheckedInAt()));
    }

    public void displayRefundResponse(RefundResponseDto response) {
        System.out.println("Refund request #" + response.getRequestId()
                + " | Booking #" + response.getBookingId()
                + " | Status " + response.getStatus());
        if (response.getStatus() == refund.enums.RefundStatus.REQUESTED) {
            displayMessage("Admin review is required. Refunds are simulated; no real money is transferred.");
        }
    }

    public void displayRefunds(List<RefundResponseDto> refunds) {
        if (refunds.isEmpty()) displayMessage("No refund requests yet.");
        for (RefundResponseDto refund : refunds) displayRefundResponse(refund);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
