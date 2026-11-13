package admin.controller;

import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import admin.service.AdminService;
import admin.view.AdminManagementView;
import common.exception.AppException;
import common.exception.ErrorCode;
import auth.controller.AuthController;
import admin.repository.AuditLogRepository;
import refund.controller.RefundController;
import refund.dto.RefundResponseDto;
import java.time.LocalDateTime;
import user.dto.UserResponseDto;
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
    private final RefundController refundController;
    private final AuthController authController;
    private final AuditLogRepository auditLogRepository;

    public AdminController(AdminService adminService, AdminManagementView adminManagementView,
                           StadiumController stadiumController,
                           MatchController matchController,
                           AccountAdminController accountAdminController,
                           RefundController refundController, AuthController authController,
                           AuditLogRepository auditLogRepository) {
        this.adminService = adminService;
        this.adminManagementView = adminManagementView;
        this.stadiumController = stadiumController;
        this.matchController = matchController;
        this.accountAdminController = accountAdminController;
        this.refundController = refundController;
        this.authController = authController;
        this.auditLogRepository = auditLogRepository;
    }


    public SystemSummaryDto getSystemSummary() {
        this.requireAdmin();
        return this.adminService.getSystemSummary();
    }

    public List<AuditLog> getAuditLogs() {
        this.requireAdmin();
        return this.adminService.getAuditLogs();
    }

    public void runAdminMenu() {
        while (true) {
            this.requireAdmin();
            try {
                switch (this.adminManagementView.showAdminMenu()) {
                    case 0: return;
                    case 1: this.runStadiumMenu(); break;
                    case 2: this.runMatchMenu(); break;
                    case 3: this.runAccountsMenu(); break;
                    case 4: this.runRefundReviewMenu(); break;
                    case 5: this.runReportsMenu(); break;
                    default: this.adminManagementView.displayResult("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runStadiumMenu() {
        while (true) {
            this.requireAdmin();
            try {
                int group = this.adminManagementView.showStadiumMenu();
                if (group == 0) return;
                if (group >= 1 && group <= 3) this.runVenueActions(group);
                else this.adminManagementView.displayResult("Invalid choice.");
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    // Groups: 1 = stadiums, 2 = sections, 3 = seats.
    private void runVenueActions(int group) {
        while (true) {
            this.requireAdmin();
            try {
                int action = this.adminManagementView.showVenueActionsMenu(group);
                if (action == 0) return;
                if (action < 1 || action > (group == 3 ? 7 : 5)) {
                    this.adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                // Cases: 1-5 = stadiums, 6-10 = sections, 11-17 = seats.
                int choice = action + (group - 1) * 5;
                if (choice == 1) {
                    stadium.dto.StadiumRequestDto request = this.adminManagementView.readStadiumRequest();
                    if (request != null) this.adminManagementView.displayResult(this.stadiumController.createStadium(request));
                    continue;
                }
                if (choice == 2) {
                    this.adminManagementView.displayResult(this.stadiumController.getStadiums());
                    continue;
                }
                if (choice <= 7) this.adminManagementView.displayResult(this.stadiumController.getStadiums());
                else this.displaySections(choice >= 13);
                // ID types: cases 1-7 = Stadium, 8-12 = Section, 13-17 = Seat.
                Long id = this.adminManagementView.readLong(
                        choice <= 7 ? "Stadium ID (0 = Back): " :
                        choice <= 12 ? "Section ID (0 = Back): " : "Seat ID (0 = Back): ");
                if (id == null) continue;
                switch (choice) {
                    case 3: this.adminManagementView.displayResult(this.stadiumController.getStadiumDetails(id)); break;
                    case 4:
                        stadium.dto.StadiumRequestDto request = this.adminManagementView.readStadiumRequest();
                        if (request != null) this.adminManagementView.displayResult(this.stadiumController.updateStadium(id, request));
                        break;
                    case 5:
                        if (this.adminManagementView.confirm("Delete stadium #" + id))
                            this.adminManagementView.displayResult(this.stadiumController.deleteStadium(id));
                        break;
                    case 6:
                    case 9:
                        String name = this.adminManagementView.readText("Section name (Enter = Cancel): ");
                        // 6 = create section, 9 = update section.
                        if (!name.isEmpty()) this.adminManagementView.displayResult(choice == 6
                                ? this.stadiumController.createSection(id, name) : this.stadiumController.updateSection(id, name));
                        break;
                    case 7: this.adminManagementView.displayResult(this.stadiumController.getSections(id)); break;
                    case 8: this.adminManagementView.displayResult(this.stadiumController.getSectionDetails(id)); break;
                    case 10:
                        if (this.adminManagementView.confirm("Delete section #" + id))
                            this.adminManagementView.displayResult(this.stadiumController.deleteSection(id));
                        break;
                    case 11:
                    case 14:
                        String row = this.adminManagementView.readText("Row label (Enter = Cancel): ");
                        if (row.isEmpty()) break;
                        int number = this.adminManagementView.readInt("Seat number: ");
                        // 11 = create seat, 14 = update seat.
                        this.adminManagementView.displayResult(choice == 11 ? this.stadiumController.createSeat(id, row, number)
                                : this.stadiumController.updateSeat(id, row, number));
                        break;
                    case 12: this.adminManagementView.displayResult(this.stadiumController.buildSeatMap(id)); break;
                    case 13: this.adminManagementView.displayResult(this.stadiumController.getSeatDetails(id)); break;
                    case 15:
                        if (this.adminManagementView.confirm("Delete seat #" + id))
                            this.adminManagementView.displayResult(this.stadiumController.deleteSeat(id));
                        break;
                    case 16: this.adminManagementView.displayResult(this.stadiumController.activateSeat(id)); break;
                    case 17:
                        if (this.adminManagementView.confirm("Deactivate seat #" + id))
                            this.adminManagementView.displayResult(this.stadiumController.deactivateSeat(id));
                        break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void displaySections(boolean includeSeats) {
        for (stadium.dto.StadiumResponseDto stadium : this.stadiumController.getStadiums()) {
            this.adminManagementView.displayResult(stadium);
            for (stadium.model.Section section : this.stadiumController.getSections(stadium.getId())) {
                this.adminManagementView.displayResult(section);
                if (includeSeats) this.adminManagementView.displayResult(this.stadiumController.buildSeatMap(section.getId()));
            }
        }
    }

    private void runMatchMenu() {
        while (true) {
            this.requireAdmin();
            try {
                int group = this.adminManagementView.showMatchMenu();
                if (group == 0) return;
                if (group >= 1 && group <= 3) this.runMatchActions(group);
                else this.adminManagementView.displayResult("Invalid choice.");
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    // Groups: 1 = matches, 2 = prices, 3 = sales.
    private void runMatchActions(int group) {
        while (true) {
            this.requireAdmin();
            try {
                int action = this.adminManagementView.showMatchActionsMenu(group);
                if (action == 0) return;
                if (action < 1 || action > (group == 3 ? 3 : 5)) {
                    this.adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                // Cases: 1-5 = matches, 6-10 = prices, 11-13 = sales.
                int choice = action + (group - 1) * 5;
                if (choice == 1 || choice == 4) {
                    this.adminManagementView.displayResult(this.stadiumController.getStadiums());
                }
                if (choice == 1) {
                    match.dto.MatchRequestDto request = this.adminManagementView.readMatchRequest();
                    if (request != null) this.adminManagementView.displayResult(this.matchController.createMatch(request));
                    continue;
                }
                this.adminManagementView.displayResult(this.matchController.getMatches());
                if (choice == 2) continue;
                Long id = this.adminManagementView.readLong("Match ID (0 = Back): ");
                if (id == null) continue;
                Long sectionId = null;
                // Price actions needing a Section: 6 = create, 8 = details, 9 = update, 10 = delete.
                if (choice == 6 || choice == 8 || choice == 9 || choice == 10) {
                    this.adminManagementView.displayResult(this.stadiumController.getSections(this.matchController.getMatchDetails(id).getStadiumId()));
                    sectionId = this.adminManagementView.readLong("Section ID (0 = Back): ");
                    if (sectionId == null) continue;
                }
                switch (choice) {
                    case 3: this.adminManagementView.displayResult(this.matchController.getMatchDetails(id)); break;
                    case 4:
                        match.dto.MatchRequestDto request = this.adminManagementView.readMatchRequest();
                        if (request != null) this.adminManagementView.displayResult(this.matchController.updateMatch(id, request));
                        break;
                    case 5:
                        if (this.adminManagementView.confirm("Delete match #" + id))
                            this.adminManagementView.displayResult(this.matchController.deleteMatch(id));
                        break;
                    case 6:
                    case 9:
                        java.math.BigDecimal amount = this.adminManagementView.readAmount("Price in VND (0 = Cancel): ");
                        // 6 = create price, 9 = update price.
                        if (amount != null) this.adminManagementView.displayResult(choice == 6
                                ? this.matchController.createTicketPrice(new TicketPriceRequestDto(id, sectionId, amount))
                                : this.matchController.updateTicketPrice(id, sectionId, amount));
                        break;
                    case 7: this.adminManagementView.displayResult(this.matchController.getTicketPrices(id)); break;
                    case 8: this.adminManagementView.displayResult(this.matchController.getTicketPriceDetails(id, sectionId)); break;
                    case 10:
                        if (this.adminManagementView.confirm("Delete price for match #" + id + ", section #" + sectionId))
                            this.adminManagementView.displayResult(this.matchController.deleteTicketPrice(id, sectionId));
                        break;
                    case 11: this.adminManagementView.displayResult(this.matchController.openTicketSales(id)); break;
                    case 12:
                        if (this.adminManagementView.confirm("Close sales for match #" + id))
                            this.adminManagementView.displayResult(this.matchController.closeTicketSales(id));
                        break;
                    case 13: this.adminManagementView.displayResult(this.matchController.getTicketSalesStatus(id)); break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (java.time.format.DateTimeParseException e) {
                this.adminManagementView.displayResult("Use yyyy-MM-ddTHH:mm, for example 2030-11-15T19:00.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runAccountsMenu() {
        while (true) {
            this.requireAdmin();
            try {
                int choice = this.adminManagementView.showAccountsMenu();
                if (choice == 0) return;
                // 1 = Fan accounts, 2 = Staff accounts.
                if (choice == 1 || choice == 2) this.runAccountMenu(choice == 2);
                else this.adminManagementView.displayResult("Invalid choice.");
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runReportsMenu() {
        while (true) {
            this.requireAdmin();
            try {
                switch (this.adminManagementView.showReportsMenu()) {
                    case 0: return;
                    case 1: this.adminManagementView.showSystemSummary(this.getSystemSummary()); break;
                    case 2: this.adminManagementView.showAuditLogs(this.getAuditLogs(), this.accountAdminController.getStaffs()); break;
                    default: this.adminManagementView.displayResult("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runStaffRoles() {
        while (true) {
            this.requireAdmin();
            try {
                // 1 = assign role, 2 = change role, 3 = revoke role.
                int choice = this.adminManagementView.showStaffRoleMenu();
                if (choice == 0) return;
                if (choice < 1 || choice > 3) {
                    this.adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                this.adminManagementView.displayResult(this.accountAdminController.getStaffs());
                Long id = this.adminManagementView.readLong("Staff ID (0 = Back): ");
                if (id == null) continue;
                if (choice == 3) {
                    if (this.adminManagementView.confirm("Revoke role from staff #" + id))
                        this.adminManagementView.displayResult(this.accountAdminController.revokeRole(id));
                } else {
                    UserRole role = this.adminManagementView.readRole(false);
                    if (role != null) this.adminManagementView.displayResult(choice == 1
                            ? this.accountAdminController.assignRole(id, role) : this.accountAdminController.changeRole(id, role));
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runAccountMenu(boolean staff) {
        while (true) {
            this.requireAdmin();
            try {
                int choice = staff ? this.adminManagementView.showStaffManagementMenu() : this.adminManagementView.showFanManagementMenu();
                if (choice == 0) return;
                if (choice < 1 || choice > 9) {
                    this.adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                // 9 = role management for Staff, account deletion for Fan.
                if (staff && choice == 9) {
                    this.runStaffRoles();
                    continue;
                }
                if (choice == 1) {
                    admin.dto.AccountRequestDto request = this.adminManagementView.readAccountRequest(staff ? null : UserRole.FAN, true);
                    if (request != null) this.adminManagementView.displayResult(staff
                            ? this.accountAdminController.createStaffAccount(request) : this.accountAdminController.createFanAccount(request));
                    continue;
                }
                if (choice == 4) {
                    String keyword = this.adminManagementView.readText("Keyword (Enter = All): ");
                    this.adminManagementView.displayResult(staff ? this.accountAdminController.searchStaff(keyword)
                            : this.accountAdminController.searchFans(keyword));
                    continue;
                }
                if (choice == 5) {
                    UserRole role = staff ? this.adminManagementView.readRole(true) : null;
                    UserStatus status = this.adminManagementView.readStatus();
                    this.adminManagementView.displayResult(staff ? this.accountAdminController.filterStaff(role, status)
                            : this.accountAdminController.filterFans(status));
                    continue;
                }
                this.adminManagementView.displayResult(staff ? this.accountAdminController.getStaffs() : this.accountAdminController.getFans());
                if (choice == 2) continue;
                Long id = this.adminManagementView.readLong(staff ? "Staff ID (0 = Back): " : "Fan ID (0 = Back): ");
                if (id == null) continue;
                switch (choice) {
                    case 3: this.adminManagementView.displayResult(staff ? this.accountAdminController.getStaffDetails(id)
                            : this.accountAdminController.getFanDetails(id)); break;
                    case 6:
                        admin.dto.AccountRequestDto request = this.adminManagementView.readAccountRequest(staff ? null : UserRole.FAN, false);
                        if (request != null) this.adminManagementView.displayResult(staff
                                ? this.accountAdminController.updateStaff(id, request) : this.accountAdminController.updateFan(id, request));
                        break;
                    case 7: this.adminManagementView.displayResult(staff ? this.accountAdminController.activateStaff(id)
                            : this.accountAdminController.activateFan(id)); break;
                    case 8:
                        if (this.adminManagementView.confirm("Deactivate account #" + id))
                            this.adminManagementView.displayResult(staff ? this.accountAdminController.deactivateStaff(id)
                                    : this.accountAdminController.deactivateFan(id));
                        break;
                    case 9:
                        if (this.adminManagementView.confirm("Delete fan #" + id))
                            this.adminManagementView.displayResult(this.accountAdminController.deleteFan(id));
                        break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runRefundReviewMenu() {
        while (true) {
            UserResponseDto admin = this.requireAdmin();
            try {
                List<refund.model.RefundRequest> pending = this.refundController.getPendingRefunds();
                this.adminManagementView.displayResult(pending);
                if (pending.isEmpty()) return;
                int choice = this.adminManagementView.showRefundReviewMenu();
                if (choice == 0) return;
                if (choice != 1) {
                    this.adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                refund.dto.RefundReviewRequestDto request = this.adminManagementView.readRefundReviewRequest(admin.getId());
                if (request == null) continue;
                RefundResponseDto result = this.refundController.reviewRefund(request);
                this.auditLogRepository.append(new AuditLog(null, admin.getId(), "REVIEW_REFUND", "REFUND",
                        result.getRequestId(), result.getStatus().name(), LocalDateTime.now()));
                this.adminManagementView.displayResult(result);
            } catch (NumberFormatException e) {
                this.adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private UserResponseDto requireAdmin() {
        UserResponseDto user = this.authController.getCurrentUser();
        if (user == null || user.getRole() != UserRole.ADMIN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Admin login required");
        }
        return user;
    }
}
