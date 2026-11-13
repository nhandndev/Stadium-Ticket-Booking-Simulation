package admin.view;

import admin.dto.AccountRequestDto;
import admin.dto.AccountResponseDto;
import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import match.dto.MatchRequestDto;
import stadium.dto.StadiumRequestDto;
import user.enums.UserRole;
import user.enums.UserStatus;
import refund.dto.RefundReviewRequestDto;
import refund.dto.RefundResponseDto;
import refund.model.RefundRequest;

public class AdminManagementView {
    private final Scanner scanner;

    public AdminManagementView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showAdminMenu() {
        System.out.println("\n=== ADMIN > SYSTEM OPERATIONS ===");
        System.out.println("1. Venues and seating\n2. Matches, pricing and sales");
        System.out.println("3. Accounts and roles\n4. Refund reviews\n5. Reports and audit logs");
        System.out.println("0. Back to dashboard");
        return this.readInt("Choose: ");
    }

    public int showStadiumMenu() {
        System.out.println("\n=== ADMIN > VENUES AND SEATING ===");
        System.out.println("1. Stadiums\n2. Sections\n3. Seats\n0. Back to system operations");
        return this.readInt("Choose: ");
    }

    public int showVenueActionsMenu(int group) {
        switch (group) {
            case 1:
                System.out.println("\n=== ADMIN > VENUES > STADIUMS ===");
                System.out.println("1. Create stadium\n2. List stadiums\n3. Stadium details\n4. Update stadium\n5. Delete stadium");
                break;
            case 2:
                System.out.println("\n=== ADMIN > VENUES > SECTIONS ===");
                System.out.println("1. Create section\n2. List sections by stadium\n3. Section details\n4. Update section\n5. Delete section");
                break;
            case 3:
                System.out.println("\n=== ADMIN > VENUES > SEATS ===");
                System.out.println("1. Create seat\n2. List seats by section\n3. Seat details\n4. Update seat\n5. Delete seat\n6. Activate seat\n7. Deactivate seat");
                break;
            default: throw new IllegalArgumentException("Invalid venue group.");
        }
        System.out.println("0. Back to venues and seating");
        return this.readInt("Choose: ");
    }

    public int showMatchMenu() {
        System.out.println("\n=== ADMIN > MATCHES, PRICING AND SALES ===");
        System.out.println("1. Matches\n2. Ticket pricing\n3. Ticket sales\n0. Back to system operations");
        return this.readInt("Choose: ");
    }

    public int showMatchActionsMenu(int group) {
        switch (group) {
            case 1:
                System.out.println("\n=== ADMIN > MATCHES > MATCH MANAGEMENT ===");
                System.out.println("1. Create match\n2. List matches\n3. Match details\n4. Update match\n5. Delete match");
                break;
            case 2:
                System.out.println("\n=== ADMIN > MATCHES > TICKET PRICING ===");
                System.out.println("1. Create price\n2. List prices by match\n3. Price details\n4. Update price\n5. Delete price");
                break;
            case 3:
                System.out.println("\n=== ADMIN > MATCHES > TICKET SALES ===");
                System.out.println("1. Open ticket sales\n2. Close ticket sales\n3. View sales status");
                break;
            default: throw new IllegalArgumentException("Invalid match group.");
        }
        System.out.println("0. Back to matches, pricing and sales");
        return this.readInt("Choose: ");
    }

    public int showAccountsMenu() {
        System.out.println("\n=== ADMIN > ACCOUNTS AND ROLES ===");
        System.out.println("1. Fan accounts\n2. Staff accounts\n0. Back to system operations");
        return this.readInt("Choose: ");
    }

    public int showReportsMenu() {
        System.out.println("\n=== ADMIN > REPORTS AND AUDIT LOGS ===");
        System.out.println("1. System summary\n2. Audit logs\n0. Back to system operations");
        return this.readInt("Choose: ");
    }

    public int showFanManagementMenu() {
        System.out.println("\n=== ADMIN > ACCOUNTS > FANS ===");
        System.out.println("1. Create\n2. List\n3. Details\n4. Search\n5. Filter status");
        System.out.println("6. Update\n7. Activate\n8. Deactivate\n9. Delete\n0. Back to accounts and roles");
        return this.readInt("Choose: ");
    }

    public int showStaffManagementMenu() {
        System.out.println("\n=== ADMIN > ACCOUNTS > STAFF ===");
        System.out.println("1. Create\n2. List\n3. Details\n4. Search\n5. Filter role/status");
        System.out.println("6. Update\n7. Activate\n8. Deactivate\n9. Staff roles\n0. Back to accounts and roles");
        return this.readInt("Choose: ");
    }

    public int showStaffRoleMenu() {
        System.out.println("\n=== ADMIN > ACCOUNTS > STAFF > ROLES ===");
        System.out.println("1. Assign role\n2. Change role\n3. Revoke role\n0. Back to staff accounts");
        return this.readInt("Choose: ");
    }

    public StadiumRequestDto readStadiumRequest() {
        String name = this.readText("Name (Enter = Cancel): ");
        if (name.isEmpty()) return null;
        return new StadiumRequestDto(name, this.readText("Address: "));
    }

    public MatchRequestDto readMatchRequest() {
        Long stadiumId = this.readLong("Stadium ID (0 = Back): ");
        if (stadiumId == null) return null;
        String home = this.readText("Home team: ");
        String away = this.readText("Away team: ");
        LocalDateTime time = LocalDateTime.parse(this.readText("Kick-off yyyy-MM-ddTHH:mm (e.g. 2030-11-15T19:00): "));
        return new MatchRequestDto(stadiumId, home, away, time);
    }

    public AccountRequestDto readAccountRequest(UserRole role, boolean create) {
        String name = this.readText("Full name (Enter = Cancel): ");
        if (name.isEmpty()) return null;
        String email = this.readText("Email: ");
        String password = this.readText(create ? "Password (at least 8 characters): " : "New password (Enter = Keep current): ");
        String phone = this.readText("Phone: ");
        if (role == null && create) {
            role = this.readRole(false);
            if (role == null) return null;
        }
        return new AccountRequestDto(name, email, password, phone, role);
    }

    public UserRole readRole(boolean allowAll) {
        System.out.println("1 Seller | 2 Gate Staff | 3 Administrator | 4 Unassigned");
        int choice = this.readInt(allowAll ? "Role (0 = All): " : "Role (0 = Back): ");
        switch (choice) {
            case 0: return null;
            case 1: return UserRole.SELLER;
            case 2: return UserRole.GATE_STAFF;
            case 3: return UserRole.ADMIN;
            case 4: return UserRole.UNASSIGNED;
            default: throw new IllegalArgumentException("Choose one of the displayed roles.");
        }
    }

    public UserStatus readStatus() {
        System.out.println("1 Active | 2 Inactive | 0 All");
        switch (this.readInt("Status: ")) {
            case 0: return null;
            case 1: return UserStatus.ACTIVE;
            case 2: return UserStatus.INACTIVE;
            default: throw new IllegalArgumentException("Choose one of the displayed statuses.");
        }
    }

    public boolean confirm(String action) {
        while (true) {
            String answer = this.readText(action + "? (y/n, Enter = No): ");
            if (answer.isEmpty() || answer.equalsIgnoreCase("n") || answer.equals("0")) return false;
            if (answer.equalsIgnoreCase("y")) return true;
            this.displayResult("Please enter y or n.");
        }
    }

    public int showRefundReviewMenu() {
        System.out.println("\n=== ADMIN > REFUND REVIEWS ===");
        System.out.println("1. Review a request\n0. Back to system operations");
        return this.readInt("Choose: ");
    }

    public RefundReviewRequestDto readRefundReviewRequest(Long adminId) {
        Long requestId = this.readLong("Refund request ID (0 = Back): ");
        if (requestId == null) return null;
        String decision = this.readText("Approve refund? (y/n): ");
        if (!decision.equalsIgnoreCase("y") && !decision.equalsIgnoreCase("n")) {
            throw new IllegalArgumentException("Enter y or n");
        }
        String note = this.readText("Review note (required, 0 = Cancel): ");
        if (note.equals("0")) return null;
        if (note.isEmpty()) throw new IllegalArgumentException("Review note is required.");
        return new RefundReviewRequestDto(adminId, requestId, decision.equalsIgnoreCase("y"), note);
    }

    public String readText(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine().trim();
    }

    public Long readLong(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        if (line.isEmpty() || line.equals("0")) return null;
        long id = Long.parseLong(line);
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        return id;
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() ? -1 : Integer.parseInt(line);
    }

    public BigDecimal readAmount(String prompt) {
        System.out.print(prompt);
        String line = this.scanner.nextLine().trim();
        return line.isEmpty() || line.equals("0") ? null : new BigDecimal(line);
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
                    System.out.println(this.formatItem(item));
                }
            }
        } else if (result instanceof Boolean) {
            System.out.println((Boolean) result ? "Operation completed." : "No record was changed.");
        } else {
            System.out.println(this.formatItem(result));
        }
    }

    public String formatItem(Object item) {
        if (item == null) return "null";
        if (item instanceof stadium.dto.StadiumResponseDto) {
            stadium.dto.StadiumResponseDto s = (stadium.dto.StadiumResponseDto) item;
            return s.getId() + " | " + s.getName() + " | " + s.getAddress();
        }
        if (item instanceof stadium.model.Section) {
            stadium.model.Section sec = (stadium.model.Section) item;
            return sec.getId() + " | " + sec.getName() + " | Stadium #" + sec.getStadiumId();
        }
        if (item instanceof stadium.model.Seat) {
            stadium.model.Seat seat = (stadium.model.Seat) item;
            return seat.getId() + " | Row " + seat.getRowLabel() + ", Seat " + seat.getSeatNumber() + " | Section #" + seat.getSectionId() + " | Active: " + seat.isActive();
        }
        if (item instanceof match.dto.MatchResponseDto) {
            match.dto.MatchResponseDto m = (match.dto.MatchResponseDto) item;
            return m.getId() + " | " + m.getHomeTeam() + " vs " + m.getAwayTeam() + " | " + m.getStadiumName() + " | " + m.getStartTime() + " | Sales: " + m.getSaleStatus();
        }
        if (item instanceof match.model.TicketPrice) {
            match.model.TicketPrice p = (match.model.TicketPrice) item;
            return "Match #" + p.getMatchId() + " | Section #" + p.getSectionId() + " | Price: " + p.getAmount() + " VND";
        }
        if (item instanceof user.dto.UserResponseDto) {
            user.dto.UserResponseDto u = (user.dto.UserResponseDto) item;
            return u.getId() + " | " + u.getFullName() + " | " + u.getEmail() + " | Role: " + u.getRole() + " | Status: " + u.getStatus();
        }
        if (item instanceof admin.dto.AccountResponseDto) {
            admin.dto.AccountResponseDto a = (admin.dto.AccountResponseDto) item;
            return a.getId() + " | " + a.getFullName() + " | " + a.getEmail() + " | Role: " + a.getRole() + " | Status: " + a.getStatus();
        }
        if (item instanceof RefundRequest) {
            RefundRequest r = (RefundRequest) item;
            return "Request #" + r.getId() + " | Booking #" + r.getBookingId()
                    + " | Fan #" + r.getFanId() + " | " + r.getReason() + " | " + r.getStatus();
        }
        if (item instanceof RefundResponseDto) {
            RefundResponseDto r = (RefundResponseDto) item;
            return "Request #" + r.getRequestId() + " | Booking #" + r.getBookingId()
                    + " | " + r.getStatus();
        }
        return item.toString();
    }

    public void showSystemSummary(SystemSummaryDto summary) {
        System.out.println("\n=== SYSTEM SUMMARY ===");
        System.out.println("Total fans: " + summary.getFanCount());
        System.out.println("Total staff: " + summary.getStaffCount());
        System.out.println("Total stadiums: " + summary.getStadiumCount());
        System.out.println("Total matches: " + summary.getMatchCount());
        System.out.println("Total bookings: " + summary.getBookingCount());
        System.out.println("Total tickets: " + summary.getTicketCount());
    }

    public void showAuditLogs(List<AuditLog> logs, List<AccountResponseDto> staffAccounts) {
        System.out.println("\n=== AUDIT LOGS ===");
        if (logs.isEmpty()) {
            System.out.println("No audit logs available.");
            return;
        }
        for (AuditLog log : logs) {
            String name = "Unknown staff #" + log.getActorId();
            String role = "UNKNOWN";
            for (AccountResponseDto staff : staffAccounts) {
                if (staff.getId().equals(log.getActorId())) {
                    name = staff.getFullName();
                    role = staff.getRole().name();
                    break;
                }
            }
            System.out.println(log.getId() + " | Name: " + name + " | Role: " + role
                    + " | " + log.getAction() + " | Target: " + log.getTargetType()
                    + " #" + log.getTargetId() + " | Result: " + log.getResult()
                    + " | " + log.getCreatedAt());
        }
    }

}
