package app;

import auth.controller.AuthController;
import match.dto.MatchResponseDto;
import user.dto.LoginRequestDto;
import user.dto.ProfileUpdateRequestDto;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;
import user.enums.UserRole;

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

    public int showFanMenu() {
        return showAccountMenu("FAN");
    }

    public int showSellerMenu() {
        return showAccountMenu("SELLER");
    }

    public int showGateStaffMenu() {
        return showAccountMenu("GATE STAFF");
    }

    public int showAdminMenu() {
        System.out.println("=== ADMINISTRATOR ===");
        System.out.println("1. View profile");
        System.out.println("2. Update profile");
        System.out.println("3. Manage system");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int showSimulatorMenu() {
        return showAccountMenu("SIMULATOR OPERATOR");
    }

    private int showAccountMenu(String label) {
        System.out.println();
        System.out.println("=== " + label + " ===");
        System.out.println("1. View profile");
        System.out.println("2. Update profile");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public LoginRequestDto readLoginRequest() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new LoginRequestDto(email, password);
    }

    public RegisterFanRequestDto readRegisterFanRequest() {
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        return new RegisterFanRequestDto(fullName, email, password, phone);
    }

    public ProfileUpdateRequestDto readProfileUpdateRequest() {
        System.out.print("New full name: ");
        String fullName = scanner.nextLine();
        System.out.print("New phone: ");
        String phone = scanner.nextLine();
        return new ProfileUpdateRequestDto(fullName, phone);
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

    public void displayUser(UserResponseDto user) {
        System.out.println(user.getFullName() + " | " + user.getEmail());
        System.out.println("Role: " + user.getRole() + " | Status: " + user.getStatus());
        System.out.println("Phone: " + user.getPhone());
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }
}
