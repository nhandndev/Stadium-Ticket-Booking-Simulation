package admin.view;

import admin.dto.AccountRequestDto;
import admin.dto.AccountResponseDto;
import admin.model.AuditLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import match.dto.MatchRequestDto;
import match.dto.MatchResponseDto;
import match.model.TicketPrice;
import stadium.dto.StadiumRequestDto;
import stadium.dto.StadiumResponseDto;
import stadium.model.Seat;
import stadium.model.Section;
import user.enums.UserRole;
import user.enums.UserStatus;


public class AdminManagementView {
    private final Scanner scanner;

    public AdminManagementView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showAdminMenu() {
        return menu("ADMINISTRATOR", "1 Stadium/Section/Seat | 2 Match/Pricing/Sales\n"
                + "3 Fan accounts | 4 Staff accounts | 5 Audit logs\n"
                + "6 Generate sample CSV | 7 Clear sample CSV | 0 Back");
    }

    public int showStadiumMenu() {
        return menu("STADIUM / SECTION / SEAT",
                "1 Create stadium | 2 List stadiums | 3 Stadium details | 4 Update stadium\n"
                        + "5 Delete stadium | 6 Create section | 7 List sections | 8 Section details\n"
                        + "9 Update section | 10 Delete section | 11 Create seat | 12 List seats\n"
                        + "13 Seat details | 14 Update seat | 15 Delete seat\n"
                        + "16 Activate seat | 17 Deactivate seat | 0 Back");
    }

    public int showMatchMenu() {
        return menu("MATCH / PRICE / SALES",
                "1 Create match | 2 List matches | 3 Match details | 4 Update match\n"
                        + "5 Delete match | 6 Create price | 7 List prices | 8 Price details\n"
                        + "9 Update price | 10 Delete price | 11 Open sales | 12 Close sales\n"
                        + "13 Sales status | 0 Back");
    }

    public int showFanManagementMenu() {
        return menu("FAN ACCOUNTS", "1 Create | 2 List | 3 Details | 4 Search\n"
                + "5 Filter status | 6 Update | 7 Activate | 8 Deactivate | 9 Delete | 0 Back");
    }

    public int showStaffManagementMenu() {
        return menu("STAFF ACCOUNTS", "1 Create | 2 List | 3 Details | 4 Search\n"
                + "5 Filter role/status | 6 Update | 7 Activate | 8 Deactivate\n"
                + "9 Assign role | 10 Change role | 11 Revoke role | 0 Back");
    }

    private int menu(String title, String options) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.println(options);
        return Integer.parseInt(ask("Choose: "));
    }

    public StadiumRequestDto readStadiumRequest() {
        return new StadiumRequestDto(ask("Name: "), ask("Address: "));
    }

    public MatchRequestDto readMatchRequest() {
        Long stadiumId = readId("Stadium ID: ");
        String home = ask("Home team: ");
        String away = ask("Away team: ");
        LocalDateTime time = LocalDateTime.parse(ask("Start yyyy-MM-ddTHH:mm: "));
        return new MatchRequestDto(stadiumId, home, away, time);
    }

    public AccountRequestDto readAccount(UserRole role, boolean create) {
        String name = ask("Full name: ");
        String email = ask("Email: ");
        String password = ask(create ? "Password (min 8): " : "New password (blank = keep): ");
        String phone = ask("Phone: ");
        return new AccountRequestDto(name, email, password, phone, role);
    }

    public UserRole readRole(boolean allowBlank) {
        String input = ask(allowBlank
                ? "Role (blank = all; SELLER/GATE_STAFF/ADMIN/SIMULATOR_OPERATOR/UNASSIGNED): "
                : "Role (SELLER/GATE_STAFF/ADMIN/SIMULATOR_OPERATOR): ");
        return allowBlank && input.isEmpty() ? null : UserRole.valueOf(input.toUpperCase());
    }

    public UserStatus readStatus(boolean allowBlank) {
        String input = ask(allowBlank
                ? "Status (ACTIVE/INACTIVE, blank = all): "
                : "Status (ACTIVE/INACTIVE): ");
        return allowBlank && input.isEmpty() ? null : UserStatus.valueOf(input.toUpperCase());
    }

    public Long readId(String prompt) {
        return Long.parseLong(ask(prompt));
    }

    public int readInt(String prompt) {
        return Integer.parseInt(ask(prompt));
    }

    public BigDecimal readMoney(String prompt) {
        return new BigDecimal(ask(prompt));
    }

    public String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean confirm(String word) {
        return ask("Type " + word + " to confirm: ").equals(word);
    }

    public void printStadium(StadiumResponseDto stadium) {
        System.out.println(stadium.getId() + " | " + stadium.getName()
                + " | " + stadium.getAddress());
    }

    public void printSection(Section section) {
        System.out.println(section.getId() + " | Stadium " + section.getStadiumId()
                + " | " + section.getName());
    }

    public void printSeat(Seat seat) {
        System.out.println(seat.getId() + " | Section " + seat.getSectionId()
                + " | " + seat.getRowLabel() + seat.getSeatNumber()
                + " | " + (seat.isActive() ? "ACTIVE" : "INACTIVE"));
    }

    public void printMatch(MatchResponseDto match) {
        System.out.println(match.getId() + " | Stadium " + match.getStadiumId()
                + " | " + match.getHomeTeam() + " vs " + match.getAwayTeam()
                + " | " + match.getStartTime() + " | " + match.getSaleStatus());
    }

    public void printPrice(TicketPrice price) {
        System.out.println("Match " + price.getMatchId() + " | Section "
                + price.getSectionId() + " | " + price.getAmount());
    }

    public void printAccount(AccountResponseDto account) {
        System.out.println(account.getId() + " | " + account.getFullName()
                + " | " + account.getEmail() + " | " + account.getRole()
                + " | " + account.getStatus());
    }

    public void printAccounts(List<AccountResponseDto> accounts) {
        if (accounts.isEmpty()) {
            displayMessage("No accounts found.");
        }
        for (AccountResponseDto account : accounts) {
            printAccount(account);
        }
    }

    public void printAuditLogs(List<AuditLog> logs) {
        if (logs.isEmpty()) {
            displayMessage("No audit records.");
        }
        for (AuditLog log : logs) {
            System.out.println(log.getCreatedAt() + " | actor " + log.getActorId()
                    + " | " + log.getAction() + " | " + log.getTargetType()
                    + " #" + log.getTargetId() + " | " + log.getResult());
        }
    }

    public String readText(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine().trim();
    }

    public Long readLong(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() ? null : Long.parseLong(line);
    }

    public BigDecimal readAmount(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() ? null : new BigDecimal(line);
    }

    public void displayResult(Object result) {
        if (result != null) {
            System.out.println(result);
        }
    }

    public AccountRequestDto readAccountRequest() {
        return this.readAccount(UserRole.FAN, true);
    }

    public void showAuditLogs(List<AuditLog> logs) {
        this.printAuditLogs(logs);
    }

    public int showDataGenerationMenu() {
        return this.showAdminMenu();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}

