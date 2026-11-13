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
        System.out.println("1. Check in ticket");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public String readTicketCode() {
        System.out.print("Ticket code (Enter or 0 = Back): ");
        String code = this.scanner.nextLine().trim();
        if (code.isEmpty() || code.equals("0")) return null;
        return code;
    }

    public boolean confirmCheckIn() {
        while (true) {
            System.out.print("Confirm check-in (y/n, Enter = No): ");
            String answer = this.scanner.nextLine().trim();
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return false;
            if (answer.equalsIgnoreCase("y")) return true;
            this.displayMessage("Please enter y or n.");
        }
    }

    public void displayTicket(TicketResponseDto ticket) {
        System.out.println("Customer: " + ticket.getCustomerName() + " | Phone: " + ticket.getCustomerPhone());
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
