package gate.view;

import java.util.Scanner;
import ticket.dto.TicketResponseDto;

public class GateView {
    private final Scanner scanner;

    public GateView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showGateMenu() {
        System.out.println();
        System.out.println("=== GATE STAFF ===");
        System.out.println("1. Validate ticket");
        System.out.println("2. Check in ticket");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String readTicketCode() {
        System.out.print("Ticket code (Enter or 0 = Back): ");
        String code = scanner.nextLine().trim();
        if (code.isEmpty() || code.equals("0")) return null;
        return code;
    }

    public Long readMatchId() {
        System.out.print("Match ID (0 = Back): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        long id = Long.parseLong(input);
        if (id <= 0) {
            throw new IllegalArgumentException("Match ID must be positive");
        }
        return id;
    }

    public boolean confirmCheckIn() {
        while (true) {
            System.out.print("Confirm check-in (y/n, Enter = No): ");
            String answer = scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return false;
            if (answer.equalsIgnoreCase("y")) return true;
            displayMessage("Please enter y or n.");
        }
    }

    public void displayTicket(TicketResponseDto ticket) {
        System.out.println("Ticket #" + ticket.getTicketId()
                + " | Code " + ticket.getTicketCode()
                + " | " + ticket.getMatchName() + " (Match #" + ticket.getMatchId() + ")"
                + " | " + ticket.getSeatLabel()
                + " | Status " + ticket.getStatus()
                + (ticket.getCheckedInAt() == null ? "" : " | Checked in at " + ticket.getCheckedInAt()));
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
