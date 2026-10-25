package admin.controller;

import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import admin.service.AdminService;
import admin.view.AdminManagementView;
import common.exception.AppException;
import java.util.List;
import match.controller.MatchController;
import match.dto.TicketPriceRequestDto;
import stadium.controller.StadiumController;
import user.controller.AccountAdminController;
import user.enums.UserRole;
import user.enums.UserStatus;

public class AdminController {
    private final AdminService adminService;
    private final AdminManagementView adminManagementView;
    private final StadiumController stadiumController;
    private final MatchController matchController;
    private final AccountAdminController accountAdminController;

    public AdminController(AdminService adminService, AdminManagementView adminManagementView,
                           StadiumController stadiumController,
                           MatchController matchController,
                           AccountAdminController accountAdminController) {
        this.adminService = adminService;
        this.adminManagementView = adminManagementView;
        this.stadiumController = stadiumController;
        this.matchController = matchController;
        this.accountAdminController = accountAdminController;
    }

    public void generateCsvDataset(int recordCount) {
        this.adminService.generateCsvDataset(recordCount);
    }

    public SystemSummaryDto getSystemSummary() {
        return this.adminService.getSystemSummary();
    }

    public List<AuditLog> getAuditLogs() {
        return this.adminService.getAuditLogs();
    }

    public void runAdminMenu() {
        while (true) {
            try {
                switch (this.adminManagementView.showAdminMenu()) {
                    case 1:
                        runStadiumMenu();
                        break;
                    case 2:
                        runMatchMenu();
                        break;
                    case 3:
                        runFanManagementMenu();
                        break;
                    case 4:
                        runStaffManagementMenu();
                        break;
                    case 5:
                        this.adminManagementView.showAuditLogs(this.getAuditLogs());
                        break;
                    case 6:
                        int count = this.adminManagementView.readInt("Seat count (1..100000): ");
                        this.generateCsvDataset(count);
                        this.adminManagementView.displayResult("Sample dataset generated.");
                        break;
                    case 7:
                        this.adminManagementView.showSystemSummary(this.getSystemSummary());
                        break;
                    case 0:
                        return;
                    default:
                        this.adminManagementView.displayResult("Invalid choice.");
                }
            } catch (AppException e) {
                this.adminManagementView.displayResult(e.getMessage());
            } catch (Exception e) {
                this.adminManagementView.displayResult("Error: " + e.getMessage());
            }
        }
    }

    private void runStadiumMenu() {
        while (true) {
            try {
                int choice = this.adminManagementView.showStadiumMenu();
                if (choice == 0) return;
                stadiumAction(choice);
            } catch (AppException e) {
                this.adminManagementView.displayResult(e.getMessage());
            } catch (Exception e) {
                this.adminManagementView.displayResult("Invalid input.");
            }
        }
    }

    private void runMatchMenu() {
        while (true) {
            try {
                int choice = this.adminManagementView.showMatchMenu();
                if (choice == 0) return;
                matchAction(choice);
            } catch (AppException e) {
                this.adminManagementView.displayResult(e.getMessage());
            } catch (Exception e) {
                this.adminManagementView.displayResult("Invalid input.");
            }
        }
    }

    private void runFanManagementMenu() {
        while (true) {
            try {
                int choice = this.adminManagementView.showFanManagementMenu();
                if (choice == 0) return;
                fanAction(choice);
            } catch (AppException e) {
                this.adminManagementView.displayResult(e.getMessage());
            } catch (Exception e) {
                this.adminManagementView.displayResult("Invalid input.");
            }
        }
    }

    private void runStaffManagementMenu() {
        while (true) {
            try {
                int choice = this.adminManagementView.showStaffManagementMenu();
                if (choice == 0) return;
                staffAction(choice);
            } catch (AppException e) {
                this.adminManagementView.displayResult(e.getMessage());
            } catch (Exception e) {
                this.adminManagementView.displayResult("Invalid input.");
            }
        }
    }

    private void stadiumAction(int choice) {
        switch (choice) {
            case 1:
                this.adminManagementView.displayResult(this.stadiumController.createStadium(this.adminManagementView.readStadiumRequest()));
                break;
            case 2:
                this.adminManagementView.displayResult(this.stadiumController.getStadiums());
                break;
            case 3:
                this.adminManagementView.displayResult(this.stadiumController.getStadiumDetails(this.adminManagementView.readLong("Stadium ID: ")));
                break;
            case 4:
                this.adminManagementView.displayResult(this.stadiumController.updateStadium(this.adminManagementView.readLong("Stadium ID: "), this.adminManagementView.readStadiumRequest()));
                break;
            case 5:
                this.adminManagementView.displayResult(this.stadiumController.deleteStadium(this.adminManagementView.readLong("Stadium ID: ")));
                break;
            case 6:
                this.adminManagementView.displayResult(this.stadiumController.createSection(this.adminManagementView.readLong("Stadium ID: "), this.adminManagementView.readText("Section name: ")));
                break;
            case 7:
                this.adminManagementView.displayResult(this.stadiumController.getSections(this.adminManagementView.readLong("Stadium ID: ")));
                break;
            case 8:
                this.adminManagementView.displayResult(this.stadiumController.getSectionDetails(this.adminManagementView.readLong("Section ID: ")));
                break;
            case 9:
                this.adminManagementView.displayResult(this.stadiumController.updateSection(this.adminManagementView.readLong("Section ID: "), this.adminManagementView.readText("New name: ")));
                break;
            case 10:
                this.adminManagementView.displayResult(this.stadiumController.deleteSection(this.adminManagementView.readLong("Section ID: ")));
                break;
            case 11:
                this.adminManagementView.displayResult(this.stadiumController.createSeat(this.adminManagementView.readLong("Section ID: "), this.adminManagementView.readText("Row label: "), this.adminManagementView.readInt("Seat number: ")));
                break;
            case 12:
                this.adminManagementView.displayResult(this.stadiumController.buildSeatMap(this.adminManagementView.readLong("Section ID: ")));
                break;
            case 13:
                this.adminManagementView.displayResult(this.stadiumController.getSeatDetails(this.adminManagementView.readLong("Seat ID: ")));
                break;
            case 14:
                this.adminManagementView.displayResult(this.stadiumController.updateSeat(this.adminManagementView.readLong("Seat ID: "), this.adminManagementView.readText("New row label: "), this.adminManagementView.readInt("New seat number: ")));
                break;
            case 15:
                this.adminManagementView.displayResult(this.stadiumController.deleteSeat(this.adminManagementView.readLong("Seat ID: ")));
                break;
            case 16:
                this.adminManagementView.displayResult(this.stadiumController.activateSeat(this.adminManagementView.readLong("Seat ID: ")));
                break;
            case 17:
                this.adminManagementView.displayResult(this.stadiumController.deactivateSeat(this.adminManagementView.readLong("Seat ID: ")));
                break;
            default:
                this.adminManagementView.displayResult("Invalid choice.");
        }
    }

    private void matchAction(int choice) {
        switch (choice) {
            case 1:
                this.adminManagementView.displayResult(this.matchController.createMatch(this.adminManagementView.readMatchRequest()));
                break;
            case 2:
                this.adminManagementView.displayResult(this.matchController.getMatches());
                break;
            case 3:
                this.adminManagementView.displayResult(this.matchController.getMatchDetails(this.adminManagementView.readLong("Match ID: ")));
                break;
            case 4:
                this.adminManagementView.displayResult(this.matchController.updateMatch(this.adminManagementView.readLong("Match ID: "), this.adminManagementView.readMatchRequest()));
                break;
            case 5:
                this.adminManagementView.displayResult(this.matchController.deleteMatch(this.adminManagementView.readLong("Match ID: ")));
                break;
            case 6:
                this.adminManagementView.displayResult(this.matchController.createTicketPrice(new TicketPriceRequestDto(this.adminManagementView.readLong("Match ID: "), this.adminManagementView.readLong("Section ID: "), this.adminManagementView.readAmount("Price: "))));
                break;
            case 7:
                this.adminManagementView.displayResult(this.matchController.getTicketPrices(this.adminManagementView.readLong("Match ID: ")));
                break;
            case 8:
                this.adminManagementView.displayResult(this.matchController.getTicketPriceDetails(this.adminManagementView.readLong("Match ID: "), this.adminManagementView.readLong("Section ID: ")));
                break;
            case 9:
                this.adminManagementView.displayResult(this.matchController.updateTicketPrice(this.adminManagementView.readLong("Match ID: "), this.adminManagementView.readLong("Section ID: "), this.adminManagementView.readAmount("New price: ")));
                break;
            case 10:
                this.adminManagementView.displayResult(this.matchController.deleteTicketPrice(this.adminManagementView.readLong("Match ID: "), this.adminManagementView.readLong("Section ID: ")));
                break;
            case 11:
                this.adminManagementView.displayResult(this.matchController.openTicketSales(this.adminManagementView.readLong("Match ID: ")));
                break;
            case 12:
                this.adminManagementView.displayResult(this.matchController.closeTicketSales(this.adminManagementView.readLong("Match ID: ")));
                break;
            case 13:
                this.adminManagementView.displayResult(this.matchController.getTicketSalesStatus(this.adminManagementView.readLong("Match ID: ")));
                break;
            default:
                this.adminManagementView.displayResult("Invalid choice.");
        }
    }

    private void fanAction(int choice) {
        switch (choice) {
            case 1:
                this.adminManagementView.displayResult(this.accountAdminController.createFanAccount(this.adminManagementView.readAccountRequest()));
                break;
            case 2:
                this.adminManagementView.displayResult(this.accountAdminController.getFans());
                break;
            case 3:
                this.adminManagementView.displayResult(this.accountAdminController.getFanDetails(this.adminManagementView.readLong("Fan ID: ")));
                break;
            case 4:
                this.adminManagementView.displayResult(this.accountAdminController.searchFans(this.adminManagementView.readText("Keyword: ")));
                break;
            case 5:
                String s = this.adminManagementView.readText("Status (ACTIVE/INACTIVE, blank = all): ");
                this.adminManagementView.displayResult(this.accountAdminController.filterFans(s.isEmpty() ? null : UserStatus.valueOf(s.toUpperCase())));
                break;
            case 6:
                this.adminManagementView.displayResult(this.accountAdminController.updateFan(this.adminManagementView.readLong("Fan ID: "), this.adminManagementView.readAccountRequest()));
                break;
            case 7:
                this.adminManagementView.displayResult(this.accountAdminController.activateFan(this.adminManagementView.readLong("Fan ID: ")));
                break;
            case 8:
                this.adminManagementView.displayResult(this.accountAdminController.deactivateFan(this.adminManagementView.readLong("Fan ID: ")));
                break;
            case 9:
                this.adminManagementView.displayResult(this.accountAdminController.deleteFan(this.adminManagementView.readLong("Fan ID: ")));
                break;
            default:
                this.adminManagementView.displayResult("Invalid choice.");
        }
    }

    private void staffAction(int choice) {
        switch (choice) {
            case 1:
                this.adminManagementView.displayResult(this.accountAdminController.createStaffAccount(this.adminManagementView.readAccountRequest()));
                break;
            case 2:
                this.adminManagementView.displayResult(this.accountAdminController.getStaffs());
                break;
            case 3:
                this.adminManagementView.displayResult(this.accountAdminController.getStaffDetails(this.adminManagementView.readLong("Staff ID: ")));
                break;
            case 4:
                this.adminManagementView.displayResult(this.accountAdminController.searchStaff(this.adminManagementView.readText("Keyword: ")));
                break;
            case 5:
                String r = this.adminManagementView.readText("Role (blank = all): ");
                String st = this.adminManagementView.readText("Status (blank = all): ");
                this.adminManagementView.displayResult(this.accountAdminController.filterStaff(
                        r.isEmpty() ? null : UserRole.valueOf(r.toUpperCase()),
                        st.isEmpty() ? null : UserStatus.valueOf(st.toUpperCase())));
                break;
            case 6:
                this.adminManagementView.displayResult(this.accountAdminController.updateStaff(this.adminManagementView.readLong("Staff ID: "), this.adminManagementView.readAccountRequest()));
                break;
            case 7:
                this.adminManagementView.displayResult(this.accountAdminController.activateStaff(this.adminManagementView.readLong("Staff ID: ")));
                break;
            case 8:
                this.adminManagementView.displayResult(this.accountAdminController.deactivateStaff(this.adminManagementView.readLong("Staff ID: ")));
                break;
            case 9:
                this.adminManagementView.displayResult(this.accountAdminController.assignRole(this.adminManagementView.readLong("Staff ID: "), UserRole.valueOf(this.adminManagementView.readText("Role: ").toUpperCase())));
                break;
            case 10:
                this.adminManagementView.displayResult(this.accountAdminController.changeRole(this.adminManagementView.readLong("Staff ID: "), UserRole.valueOf(this.adminManagementView.readText("Role: ").toUpperCase())));
                break;
            case 11:
                this.adminManagementView.displayResult(this.accountAdminController.revokeRole(this.adminManagementView.readLong("Staff ID: ")));
                break;
            default:
                this.adminManagementView.displayResult("Invalid choice.");
        }
    }
}
