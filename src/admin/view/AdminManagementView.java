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
        System.out.println("6 System summary | 0 Back");
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
        return line.isEmpty() ? -1 : Integer.parseInt(line);
    }

    public BigDecimal readAmount(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() ? null : new BigDecimal(line);
    }

    public void displayResult(Object result) {
        if (result == null) {
            System.out.println("No result found.");
        } else if (result instanceof List) {
            List<?> list = (List<?>) result;
            if (list.isEmpty()) {
                System.out.println("List is empty.");
            } else {
                for (Object item : list) {
                    System.out.println(formatItem(item));
                }
            }
        } else {
            System.out.println(formatItem(result));
        }
    }

    private String formatItem(Object item) {
        if (item == null) return "null";
        if (item instanceof stadium.dto.StadiumResponseDto) {
            stadium.dto.StadiumResponseDto s = (stadium.dto.StadiumResponseDto) item;
            return s.getId() + " | " + s.getName() + " | " + s.getAddress();
        }
        if (item instanceof stadium.model.Section) {
            stadium.model.Section sec = (stadium.model.Section) item;
            return sec.getId() + " | " + sec.getName() + " (Stadium ID: " + sec.getStadiumId() + ")";
        }
        if (item instanceof stadium.model.Seat) {
            stadium.model.Seat seat = (stadium.model.Seat) item;
            return seat.getId() + " | Row: " + seat.getRowLabel() + ", Seat: " + seat.getSeatNumber() + " | Section: " + seat.getSectionId() + " | Active: " + seat.isActive();
        }
        if (item instanceof match.dto.MatchResponseDto) {
            match.dto.MatchResponseDto m = (match.dto.MatchResponseDto) item;
            return m.getId() + " | " + m.getHomeTeam() + " vs " + m.getAwayTeam() + " | Stadium: " + m.getStadiumId() + " | Time: " + m.getStartTime() + " | Sales: " + m.getSaleStatus();
        }
        if (item instanceof match.model.TicketPrice) {
            match.model.TicketPrice p = (match.model.TicketPrice) item;
            return "Match: " + p.getMatchId() + " | Section: " + p.getSectionId() + " | Price: " + p.getAmount() + " VND";
        }
        if (item instanceof user.dto.UserResponseDto) {
            user.dto.UserResponseDto u = (user.dto.UserResponseDto) item;
            return u.getId() + " | " + u.getFullName() + " | " + u.getEmail() + " | Role: " + u.getRole() + " | Status: " + u.getStatus();
        }
        if (item instanceof admin.dto.AccountResponseDto) {
            admin.dto.AccountResponseDto a = (admin.dto.AccountResponseDto) item;
            return a.getId() + " | " + a.getFullName() + " | " + a.getEmail() + " | Role: " + a.getRole() + " | Status: " + a.getStatus();
        }
        if (item instanceof admin.model.AuditLog) {
            admin.model.AuditLog log = (admin.model.AuditLog) item;
            return log.getId() + " | Actor #" + log.getActorId() + " | " + log.getAction() + " | Target: " + log.getTargetType() + " #" + log.getTargetId() + " | Result: " + log.getResult() + " | " + log.getCreatedAt();
        }
        return item.toString();
    }

    public void showSystemSummary(SystemSummaryDto summary) {
        System.out.println("\n=== SYSTEM SUMMARY ===");
        System.out.println("Total fans: " + summary.getFanCount());
        System.out.println("Total staff: " + summary.getStaffCount());
        System.out.println("Total stadiums: " + summary.getStadiumCount());
        System.out.println("Total matches: " + summary.getMatchCount());
    }

    public void showAuditLogs(List<AuditLog> logs) {
        System.out.println("\n=== AUDIT LOGS ===");
        if (logs.isEmpty()) {
            System.out.println("No audit logs available.");
            return;
        }
        for (AuditLog log : logs) {
            System.out.println(formatItem(log));
        }
    }
}
