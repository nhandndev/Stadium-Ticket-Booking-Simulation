package admin.view;

import admin.dto.AccountRequestDto;
import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import match.dto.MatchRequestDto;
import stadium.dto.StadiumRequestDto;
import user.enums.UserRole;

public class AdminManagementView {
    private final Scanner scanner;

    public AdminManagementView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showAdminMenu() {
        System.out.println("\n=== ADMINISTRATOR ===");
        System.out.println("1 Stadium/Section/Seat | 2 Match/Pricing/Sales");
        System.out.println("3 Fan accounts | 4 Staff accounts | 5 Audit logs");
        System.out.println("6 Generate sample CSV | 7 System summary | 0 Back");
        return this.readInt("Choose: ");
    }

    public int showStadiumMenu() {
        System.out.println("\n=== STADIUM / SECTION / SEAT ===");
        System.out.println("1 Create stadium | 2 List stadiums | 3 Stadium details | 4 Update stadium");
        System.out.println("5 Delete stadium | 6 Create section | 7 List sections | 8 Section details");
        System.out.println("9 Update section | 10 Delete section | 11 Create seat | 12 List seats");
        System.out.println("13 Seat details | 14 Update seat | 15 Delete seat");
        System.out.println("16 Activate seat | 17 Deactivate seat | 0 Back");
        return this.readInt("Choose: ");
    }

    public int showMatchMenu() {
        System.out.println("\n=== MATCH / PRICE / SALES ===");
        System.out.println("1 Create match | 2 List matches | 3 Match details | 4 Update match");
        System.out.println("5 Delete match | 6 Create price | 7 List prices | 8 Price details");
        System.out.println("9 Update price | 10 Delete price | 11 Open sales | 12 Close sales");
        System.out.println("13 Sales status | 0 Back");
        return this.readInt("Choose: ");
    }

    public int showFanManagementMenu() {
        System.out.println("\n=== FAN ACCOUNTS ===");
        System.out.println("1 Create | 2 List | 3 Details | 4 Search");
        System.out.println("5 Filter status | 6 Update | 7 Activate | 8 Deactivate | 9 Delete | 0 Back");
        return this.readInt("Choose: ");
    }

    public int showStaffManagementMenu() {
        System.out.println("\n=== STAFF ACCOUNTS ===");
        System.out.println("1 Create | 2 List | 3 Details | 4 Search");
        System.out.println("5 Filter role/status | 6 Update | 7 Activate | 8 Deactivate");
        System.out.println("9 Assign role | 10 Change role | 11 Revoke role | 0 Back");
        return this.readInt("Choose: ");
    }

    public int showDataGenerationMenu() {
        System.out.println("\n=== DATA GENERATION ===");
        System.out.println("1 Generate sample dataset | 0 Back");
        return this.readInt("Choose: ");
    }

    public StadiumRequestDto readStadiumRequest() {
        return new StadiumRequestDto(this.readText("Name: "), this.readText("Address: "));
    }

    public MatchRequestDto readMatchRequest() {
        Long stadiumId = this.readLong("Stadium ID: ");
        String home = this.readText("Home team: ");
        String away = this.readText("Away team: ");
        LocalDateTime time = LocalDateTime.parse(this.readText("Start yyyy-MM-ddTHH:mm: "));
        return new MatchRequestDto(stadiumId, home, away, time);
    }

    public AccountRequestDto readAccountRequest() {
        String name = this.readText("Full name: ");
        String email = this.readText("Email: ");
        String password = this.readText("Password: ");
        String phone = this.readText("Phone: ");
        String roleStr = this.readText("Role (FAN/SELLER/GATE_STAFF/ADMIN/SIMULATOR_OPERATOR): ");
        UserRole role = roleStr.isEmpty() ? UserRole.FAN : UserRole.valueOf(roleStr.toUpperCase());
        return new AccountRequestDto(name, email, password, phone, role);
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

    public int readInt(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() ? 0 : Integer.parseInt(line);
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

    public void showSystemSummary(SystemSummaryDto summary) {
        if (summary == null) return;
        System.out.println("=== SYSTEM SUMMARY ===");
        System.out.println("Fans: " + summary.getFanCount()
                + " | Staff: " + summary.getStaffCount()
                + " | Total Accounts: " + summary.getTotalAccounts());
        System.out.println("Stadiums: " + summary.getStadiumCount()
                + " | Matches: " + summary.getMatchCount()
                + " | Bookings: " + summary.getBookingCount()
                + " | Tickets: " + summary.getTicketCount());
    }

    public void showAuditLogs(List<AuditLog> logs) {
        if (logs == null || logs.isEmpty()) {
            System.out.println("No audit logs found.");
            return;
        }
        for (AuditLog log : logs) {
            System.out.println(log.getCreatedAt() + " | Actor " + log.getActorId()
                    + " | " + log.getAction() + " | " + log.getTargetType() + " #"
                    + log.getTargetId() + " -> " + log.getResult());
        }
    }
}
