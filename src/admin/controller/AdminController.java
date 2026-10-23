package admin.controller;

import admin.service.AdminService;
import admin.view.AdminManagementView;
import auth.controller.AuthController;
import common.exception.AppException;
import common.exception.ErrorCode;

import java.time.format.DateTimeParseException;

import match.controller.MatchController;
import match.dto.MatchResponseDto;
import match.dto.TicketPriceRequestDto;
import match.model.TicketPrice;
import stadium.controller.StadiumController;
import stadium.dto.StadiumRequestDto;
import stadium.dto.StadiumResponseDto;
import stadium.model.Seat;
import stadium.model.Section;
import user.controller.AccountAdminController;
import user.dto.UserResponseDto;
import user.enums.UserRole;
import user.enums.UserStatus;


public class AdminController {
    private final AdminService adminService;
    private final AdminManagementView adminManagementView;
    private final StadiumController stadiumController;
    private final MatchController matchController;
    private final AccountAdminController accountAdminController;
    private final AuthController authController;

    public AdminController(AdminService adminService, AdminManagementView adminManagementView,
                           StadiumController stadiumController,
                           MatchController matchController,
                           AccountAdminController accountAdminController,
                           AuthController authController) {
        this.adminService = adminService;
        this.adminManagementView = adminManagementView;
        this.stadiumController = stadiumController;
        this.matchController = matchController;
        this.accountAdminController = accountAdminController;
        this.authController = authController;
    }

    public void runAdminMenu() {
        requireAdmin();
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
                        this.adminManagementView.printAuditLogs(adminService.getAuditLogs());
                        break;
                    case 6:
                        int count = this.adminManagementView.readInt("Seat count (1..100000): ");
                        if (this.adminManagementView.confirm("GENERATE")) {
                            adminService.generateCsvDataset(count);
                            this.adminManagementView.displayMessage("Sample dataset generated.");
                        }
                        break;
                    case 7:
                        if (this.adminManagementView.confirm("DELETE SAMPLE DATA")) {
                            adminService.clearGeneratedData();
                            this.adminManagementView.displayMessage("Sample dataset cleared.");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        this.adminManagementView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                this.adminManagementView.displayMessage(e.getMessage());
            } catch (DateTimeParseException e) {
                this.adminManagementView.displayMessage("Use yyyy-MM-ddTHH:mm for date and time.");
            } catch (IllegalArgumentException e) {
                this.adminManagementView.displayMessage("Invalid number, role or status.");
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
                this.adminManagementView.displayMessage(e.getMessage());
            } catch (IllegalArgumentException e) {
                this.adminManagementView.displayMessage("Invalid input.");
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
                this.adminManagementView.displayMessage(e.getMessage());
            } catch (DateTimeParseException e) {
                this.adminManagementView.displayMessage("Use yyyy-MM-ddTHH:mm for date and time.");
            } catch (IllegalArgumentException e) {
                this.adminManagementView.displayMessage("Invalid input.");
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
                this.adminManagementView.displayMessage(e.getMessage());
            } catch (IllegalArgumentException e) {
                this.adminManagementView.displayMessage("Invalid input.");
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
                this.adminManagementView.displayMessage(e.getMessage());
            } catch (IllegalArgumentException e) {
                this.adminManagementView.displayMessage("Invalid input.");
            }
        }
    }

    private void requireAdmin() {
        UserResponseDto current = authController.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMIN || current.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Admin login required");
        }
    }

    private void stadiumAction(int choice) {
        Long id;
        switch (choice) {
            case 1:
                this.adminManagementView.printStadium(stadiumController.createStadium(new StadiumRequestDto(
                        this.adminManagementView.ask("Name: "), this.adminManagementView.ask("Address: "))));
                break;
            case 2:
                for (StadiumResponseDto stadium : stadiumController.getStadiums()) {
                    this.adminManagementView.printStadium(stadium);
                }
                break;
            case 3:
                this.adminManagementView.printStadium(stadiumController.getStadiumDetails(this.adminManagementView.readId("Stadium ID: ")));
                break;
            case 4:
                id = this.adminManagementView.readId("Stadium ID: ");
                this.adminManagementView.printStadium(stadiumController.updateStadium(id,
                        new StadiumRequestDto(this.adminManagementView.ask("Name: "), this.adminManagementView.ask("Address: "))));
                break;
            case 5:
                id = this.adminManagementView.readId("Stadium ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: " + stadiumController.deleteStadium(id));
                }
                break;
            case 6:
                this.adminManagementView.printSection(stadiumController.createSection(
                        this.adminManagementView.readId("Stadium ID: "), this.adminManagementView.ask("Section name: ")));
                break;
            case 7:
                for (Section section : stadiumController.getSections(this.adminManagementView.readId("Stadium ID: "))) {
                    this.adminManagementView.printSection(section);
                }
                break;
            case 8:
                this.adminManagementView.printSection(stadiumController.getSectionDetails(this.adminManagementView.readId("Section ID: ")));
                break;
            case 9:
                id = this.adminManagementView.readId("Section ID: ");
                this.adminManagementView.printSection(stadiumController.updateSection(id, this.adminManagementView.ask("Section name: ")));
                break;
            case 10:
                id = this.adminManagementView.readId("Section ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: " + stadiumController.deleteSection(id));
                }
                break;
            case 11:
                this.adminManagementView.printSeat(stadiumController.createSeat(this.adminManagementView.readId("Section ID: "),
                        this.adminManagementView.ask("Row: "), this.adminManagementView.readInt("Seat number: ")));
                break;
            case 12:
                for (Seat seat : stadiumController.buildSeatMap(this.adminManagementView.readId("Section ID: "))) {
                    this.adminManagementView.printSeat(seat);
                }
                break;
            case 13:
                this.adminManagementView.printSeat(stadiumController.getSeatDetails(this.adminManagementView.readId("Seat ID: ")));
                break;
            case 14:
                id = this.adminManagementView.readId("Seat ID: ");
                this.adminManagementView.printSeat(stadiumController.updateSeat(id, this.adminManagementView.ask("Row: "),
                        this.adminManagementView.readInt("Seat number: ")));
                break;
            case 15:
                id = this.adminManagementView.readId("Seat ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: " + stadiumController.deleteSeat(id));
                }
                break;
            case 16:
                this.adminManagementView.printSeat(stadiumController.activateSeat(this.adminManagementView.readId("Seat ID: ")));
                break;
            case 17:
                this.adminManagementView.printSeat(stadiumController.deactivateSeat(this.adminManagementView.readId("Seat ID: ")));
                break;
            default:
                this.adminManagementView.displayMessage("Invalid choice.");
        }
    }

    private void matchAction(int choice) {
        Long matchId;
        Long sectionId;
        switch (choice) {
            case 1:
                this.adminManagementView.printMatch(matchController.createMatch(this.adminManagementView.readMatchRequest()));
                break;
            case 2:
                for (MatchResponseDto match : matchController.getMatches()) {
                    this.adminManagementView.printMatch(match);
                }
                break;
            case 3:
                this.adminManagementView.printMatch(matchController.getMatchDetails(this.adminManagementView.readId("Match ID: ")));
                break;
            case 4:
                matchId = this.adminManagementView.readId("Match ID: ");
                this.adminManagementView.printMatch(matchController.updateMatch(matchId, this.adminManagementView.readMatchRequest()));
                break;
            case 5:
                matchId = this.adminManagementView.readId("Match ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: " + matchController.deleteMatch(matchId));
                }
                break;
            case 6:
                this.adminManagementView.printPrice(matchController.createTicketPrice(new TicketPriceRequestDto(
                        this.adminManagementView.readId("Match ID: "), this.adminManagementView.readId("Section ID: "),
                        this.adminManagementView.readMoney("Amount: "))));
                break;
            case 7:
                for (TicketPrice price : matchController.getTicketPrices(this.adminManagementView.readId("Match ID: "))) {
                    this.adminManagementView.printPrice(price);
                }
                break;
            case 8:
                this.adminManagementView.printPrice(matchController.getTicketPriceDetails(this.adminManagementView.readId("Match ID: "),
                        this.adminManagementView.readId("Section ID: ")));
                break;
            case 9:
                matchId = this.adminManagementView.readId("Match ID: ");
                sectionId = this.adminManagementView.readId("Section ID: ");
                this.adminManagementView.printPrice(matchController.updateTicketPrice(matchId, sectionId,
                        this.adminManagementView.readMoney("Amount: ")));
                break;
            case 10:
                matchId = this.adminManagementView.readId("Match ID: ");
                sectionId = this.adminManagementView.readId("Section ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: "
                            + matchController.deleteTicketPrice(matchId, sectionId));
                }
                break;
            case 11:
                this.adminManagementView.printMatch(matchController.openTicketSales(this.adminManagementView.readId("Match ID: ")));
                break;
            case 12:
                this.adminManagementView.printMatch(matchController.closeTicketSales(this.adminManagementView.readId("Match ID: ")));
                break;
            case 13:
                this.adminManagementView.displayMessage("Sales: "
                        + matchController.getTicketSalesStatus(this.adminManagementView.readId("Match ID: ")));
                break;
            default:
                this.adminManagementView.displayMessage("Invalid choice.");
        }
    }

    private void fanAction(int choice) {
        Long id;
        switch (choice) {
            case 1:
                this.adminManagementView.printAccount(accountAdminController.createFanAccount(this.adminManagementView.readAccount(UserRole.FAN, true)));
                break;
            case 2:
                this.adminManagementView.printAccounts(accountAdminController.getFans());
                break;
            case 3:
                this.adminManagementView.printAccount(accountAdminController.getFanDetails(this.adminManagementView.readId("Fan ID: ")));
                break;
            case 4:
                this.adminManagementView.printAccounts(accountAdminController.searchFans(this.adminManagementView.ask("Keyword: ")));
                break;
            case 5:
                this.adminManagementView.printAccounts(accountAdminController.filterFans(this.adminManagementView.readStatus(true)));
                break;
            case 6:
                id = this.adminManagementView.readId("Fan ID: ");
                this.adminManagementView.printAccount(accountAdminController.updateFan(id, this.adminManagementView.readAccount(UserRole.FAN, false)));
                break;
            case 7:
                this.adminManagementView.printAccount(accountAdminController.activateFan(this.adminManagementView.readId("Fan ID: ")));
                break;
            case 8:
                this.adminManagementView.printAccount(accountAdminController.deactivateFan(this.adminManagementView.readId("Fan ID: ")));
                break;
            case 9:
                id = this.adminManagementView.readId("Fan ID: ");
                if (this.adminManagementView.confirm("DELETE")) {
                    this.adminManagementView.displayMessage("Deleted: " + accountAdminController.deleteFan(id));
                }
                break;
            default:
                this.adminManagementView.displayMessage("Invalid choice.");
        }
    }

    private void staffAction(int choice) {
        Long id;
        switch (choice) {
            case 1:
                this.adminManagementView.printAccount(accountAdminController.createStaffAccount(
                        this.adminManagementView.readAccount(this.adminManagementView.readRole(false), true)));
                break;
            case 2:
                this.adminManagementView.printAccounts(accountAdminController.getStaffs());
                break;
            case 3:
                this.adminManagementView.printAccount(accountAdminController.getStaffDetails(this.adminManagementView.readId("Staff ID: ")));
                break;
            case 4:
                this.adminManagementView.printAccounts(accountAdminController.searchStaff(this.adminManagementView.ask("Keyword: ")));
                break;
            case 5:
                this.adminManagementView.printAccounts(accountAdminController.filterStaff(this.adminManagementView.readRole(true), this.adminManagementView.readStatus(true)));
                break;
            case 6:
                id = this.adminManagementView.readId("Staff ID: ");
                this.adminManagementView.printAccount(accountAdminController.updateStaff(id, this.adminManagementView.readAccount(null, false)));
                break;
            case 7:
                this.adminManagementView.printAccount(accountAdminController.activateStaff(this.adminManagementView.readId("Staff ID: ")));
                break;
            case 8:
                this.adminManagementView.printAccount(accountAdminController.deactivateStaff(this.adminManagementView.readId("Staff ID: ")));
                break;
            case 9:
                id = this.adminManagementView.readId("Staff ID: ");
                this.adminManagementView.printAccount(accountAdminController.assignRole(id, this.adminManagementView.readRole(false)));
                break;
            case 10:
                id = this.adminManagementView.readId("Staff ID: ");
                this.adminManagementView.printAccount(accountAdminController.changeRole(id, this.adminManagementView.readRole(false)));
                break;
            case 11:
                id = this.adminManagementView.readId("Staff ID: ");
                if (this.adminManagementView.confirm("REVOKE")) {
                    this.adminManagementView.printAccount(accountAdminController.revokeRole(id));
                }
                break;
            default:
                this.adminManagementView.displayMessage("Invalid choice.");
        }
    }
}


