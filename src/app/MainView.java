package app;

import match.dto.MatchResponseDto;
import user.dto.LoginRequestDto;
import user.dto.ProfileUpdateRequestDto;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;

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
        return Integer.parseInt(this.scanner.nextLine().trim());
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
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public int showFanMenu() {
        return this.showAccountMenu("FAN");
    }

    public int showSellerMenu() {
        return this.showAccountMenu("SELLER");
    }

    public int showGateStaffMenu() {
        return this.showAccountMenu("GATE STAFF");
    }

    public int showAdminMenu() {
        return this.showAccountMenu("ADMINISTRATOR");
    }

    public int showSimulatorMenu() {
        return this.showAccountMenu("SIMULATOR OPERATOR");
    }

    private int showAccountMenu(String label) {
        System.out.println();
        System.out.println("=== " + label + " ===");
        System.out.println("1. View profile");
        System.out.println("2. Update profile");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
        return Integer.parseInt(this.scanner.nextLine().trim());
    }

    public LoginRequestDto readLoginRequest() {
        System.out.print("Email: ");
        String email = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();
        return new LoginRequestDto(email, password);
    }

    public RegisterFanRequestDto readRegisterFanRequest() {
        System.out.print("Full name: ");
        String fullName = this.scanner.nextLine();
        System.out.print("Email: ");
        String email = this.scanner.nextLine();
        System.out.print("Password: ");
        String password = this.scanner.nextLine();
        System.out.print("Phone: ");
        String phone = this.scanner.nextLine();
        return new RegisterFanRequestDto(fullName, email, password, phone);
    }

    public ProfileUpdateRequestDto readProfileUpdateRequest() {
        System.out.print("New full name: ");
        String fullName = this.scanner.nextLine();
        System.out.print("New phone: ");
        String phone = this.scanner.nextLine();
        return new ProfileUpdateRequestDto(fullName, phone);
    }

    public String readKeyword() {
        System.out.print("Team keyword: ");
        return this.scanner.nextLine();
    }

    public Long readStadiumId() {
        System.out.print("Stadium ID (blank = all): ");
        String input = this.scanner.nextLine().trim();
        return input.isEmpty() ? null : Long.parseLong(input);
    }

    public LocalDate readStartDate() {
        System.out.print("Date yyyy-MM-dd (blank = all): ");
        String input = this.scanner.nextLine().trim();
        return input.isEmpty() ? null : LocalDate.parse(input);
    }

    public Long readMatchId() {
        System.out.print("Match ID: ");
        return Long.parseLong(this.scanner.nextLine().trim());
    }

    public void displayMatches(List<MatchResponseDto> matches) {
        if (matches.isEmpty()) {
            this.displayMessage("No matches found.");
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

    public void displayUser(UserResponseDto user) {
        System.out.println(user.getFullName() + " | " + user.getEmail());
        System.out.println("Role: " + user.getRole() + " | Status: " + user.getStatus());
        System.out.println("Phone: " + user.getPhone());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
