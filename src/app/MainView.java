package app;

import match.dto.MatchResponseDto;
import user.dto.LoginRequestDto;
import user.dto.RegisterFanRequestDto;
import user.dto.ProfileUpdateRequestDto;
import user.dto.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class MainView {
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MainView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMainMenu() {
        System.out.println("\nSTADIUM TICKET BOOKING SIMULATION");
        System.out.println("1. Browse As Guest (Search matches & View seat availability)");
        System.out.println("2. Login As Fan");
        System.out.println("3. Login As Staff (Admin, Seller, Gate, Sim)");
        System.out.println("4. Register Fan Account");
        System.out.println("0. Exit System");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showGuestMenu() {
        System.out.println("\n=== MATCH BROWSING ===");
        System.out.println("1. Search matches by team keyword");
        System.out.println("2. Filter matches / List all schedule");
        System.out.println("3. View match details (by Match ID)");
        System.out.println("4. View seat availability / Seat map (by Match ID)");
        System.out.println("0. Back");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showMatchActionMenu(boolean canBook) {
        System.out.println("\n--- Choose action for matches ---");
        System.out.println("1. View match details (enter Match ID)");
        System.out.println("2. View seat availability / Seat map (enter Match ID)");
        if (canBook) System.out.println("3. Book this match");
        System.out.println("0. Back to match browsing");
        System.out.print("Choose action: ");
        String line = scanner.nextLine().trim();
        return line.isEmpty() ? 0 : Integer.parseInt(line);
    }

    public int showFanMenu() {
        System.out.println("\n=== FAN DASHBOARD ===");
        System.out.println("1. View profile");
        System.out.println("2. Booking / Payment / Tickets / Refund");
        System.out.println("3. Update profile");
        System.out.println("4. Browse matches");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showSellerMenu() {
        System.out.println("\n=== SELLER OPERATIONS ===");
        System.out.println("1. View profile");
        System.out.println("2. Sales counter");
        System.out.println("3. Update profile");
        System.out.println("4. Browse matches");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showGateStaffMenu() {
        System.out.println("\n=== GATE STAFF OPERATIONS ===");
        System.out.println("1. View profile");
        System.out.println("2. Validate / Check in ticket");
        System.out.println("3. Update profile");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showAdminMenu() {
        System.out.println("\n=== ADMINISTRATOR ===");
        System.out.println("1. View profile");
        System.out.println("2. Manage system operations");
        System.out.println("3. Update profile");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showSimulatorMenu() {
        System.out.println("\n=== SIMULATOR OPERATOR ===");
        System.out.println("1. View profile");
        System.out.println("2. Update profile");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public LoginRequestDto readLoginRequest() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Email (Enter = Cancel): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) return null;
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        return new LoginRequestDto(email, password);
    }

    public RegisterFanRequestDto readRegisterFanRequest() {
        System.out.println("\n=== REGISTER FAN ACCOUNT ===");
        System.out.print("Full name (Enter = Cancel): ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) return null;
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password (at least 8 characters): ");
        String password = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        return new RegisterFanRequestDto(fullName, email, password, phone);
    }

    public String readKeyword() {
        System.out.print("Enter Team keyword (e.g. Vietnam, Thailand): ");
        return scanner.nextLine().trim();
    }

    public ProfileUpdateRequestDto readProfileUpdateRequest() {
        System.out.print("New full name (Enter = Cancel): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return null;
        System.out.print("New phone: ");
        return new ProfileUpdateRequestDto(name, scanner.nextLine().trim());
    }

    public Long readStadiumId() {
        System.out.print("Stadium ID (or press Enter for all): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Long.parseLong(input);
    }

    public LocalDate readStartDate() {
        System.out.print("Match Date yyyy-MM-dd (e.g. 2026-11-15, or press Enter for all): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : LocalDate.parse(input);
    }

    public Long readMatchId() {
        System.out.print("Match ID (0 = Back): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty() || input.equals("0")) return null;
        long id = Long.parseLong(input);
        if (id <= 0) throw new IllegalArgumentException("Match ID must be positive.");
        return id;
    }

    public void displayStadiumOptions(List<MatchResponseDto> matches) {
        System.out.println("\n=== STADIUM FILTER OPTIONS ===");
        Set<Long> shown = new HashSet<>();
        for (MatchResponseDto match : matches) {
            if (shown.add(match.getStadiumId())) {
                System.out.println(match.getStadiumId() + " | " + match.getStadiumName());
            }
        }
    }

    public void displayMatches(List<MatchResponseDto> matches) {
        if (matches == null || matches.isEmpty()) {
            displayMessage("\n[!] No matches found matching your criteria.");
            return;
        }
        System.out.println("\n=== MATCH SCHEDULE ===");
        System.out.printf("%-4s | %-28s | %-24s | %-16s | %-10s%n", "ID", "MATCH", "STADIUM (ID)", "KICK-OFF", "SALES");
        for (MatchResponseDto match : matches) {
            String matchTitle = match.getHomeTeam() + " vs " + match.getAwayTeam();
            String timeStr = formatDateTime(match.getStartTime());
            System.out.printf("%-4d | %-28s | %-24s | %-16s | %-10s%n",
                    match.getId(), matchTitle, match.getStadiumName() + " (" + match.getStadiumId() + ")",
                    timeStr, match.getSaleStatus());
        }
    }

    public void displayMatchDetails(MatchResponseDto match) {
        if (match == null) {
            displayMessage("\n[!] Match details not found.");
            return;
        }
        System.out.println("\n=== MATCH DETAILS #" + match.getId() + " ===");
        System.out.println("Match:      " + match.getHomeTeam() + " vs " + match.getAwayTeam());
        System.out.println("Stadium:    " + match.getStadiumName() + " [ID: " + match.getStadiumId() + "]");
        System.out.println("Kick-off:   " + formatDateTime(match.getStartTime()));
        System.out.println("Sales Gate: [ " + match.getSaleStatus() + " ]");
    }

    public void displayUser(UserResponseDto user) {
        if (user == null) return;
        System.out.println("\n=== USER PROFILE ===");
        System.out.println("Name:   " + user.getFullName());
        System.out.println("Email:  " + user.getEmail());
        System.out.println("Phone:  " + user.getPhone());
        System.out.println("Role:   " + user.getRole() + " | Status: " + user.getStatus());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    private String formatDateTime(LocalDateTime dt) {
        if (dt == null) return "N/A";
        return dt.format(dateFormatter);
    }
}
