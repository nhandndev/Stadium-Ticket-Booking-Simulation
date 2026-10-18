package app;

import match.dto.MatchResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner;

    public MainView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMainMenu() {
        System.out.println();
        System.out.println("STADIUM TICKET BOOKING SIMULATION");
        System.out.println("1. Browse As Guest");
        System.out.println("2. Login As Fan");
        System.out.println("3. Login As Staff");
        System.out.println("4. Register Fan Account");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showGuestMenu() {
        System.out.println();
        System.out.println("=== GUEST ===");
        System.out.println("1. Search matches");
        System.out.println("2. Filter matches / list all");
        System.out.println("3. View match details");
        System.out.println("4. View seat availability");
        System.out.println("0. Back");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String readKeyword() {
        System.out.print("Team keyword: ");
        return scanner.nextLine();
    }

    public Long readStadiumId() {
        System.out.print("Stadium ID (blank = all): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Long.parseLong(input);
    }

    public LocalDate readStartDate() {
        System.out.print("Date yyyy-MM-dd (blank = all): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : LocalDate.parse(input);
    }

    public Long readMatchId() {
        System.out.print("Match ID: ");
        return Long.parseLong(scanner.nextLine().trim());
    }

    public void displayMatches(List<MatchResponseDto> matches) {
        if (matches.isEmpty()) {
            displayMessage("No matches found.");
            return;
        }
        for (MatchResponseDto match : matches) {
            System.out.println(match.getId() + " | " + match.getHomeTeam()
                    + " vs " + match.getAwayTeam() + " | " + match.getStartTime());
        }
    }

    public void displayMatchDetails(MatchResponseDto match) {
        System.out.println(match.getHomeTeam() + " vs " + match.getAwayTeam());
        System.out.println("Stadium ID: " + match.getStadiumId());
        System.out.println("Kick-off: " + match.getStartTime());
        System.out.println("Sales: " + match.getSaleStatus());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
