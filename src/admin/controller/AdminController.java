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
        requireAdmin();
        return this.adminService.getSystemSummary();
    }

    public List<AuditLog> getAuditLogs() {
        requireAdmin();
        return this.adminService.getAuditLogs();
    }

    public void runAdminMenu() {
        while (true) {
            requireAdmin();
            try {
                switch (adminManagementView.showAdminMenu()) {
                    case 0: return;
                    case 1: runStadiumMenu(); break;
                    case 2: runMatchMenu(); break;
                    case 3: runAccountMenu(false); break;
                    case 4: runAccountMenu(true); break;
                    case 5: adminManagementView.showAuditLogs(getAuditLogs()); break;
                    case 6: adminManagementView.showSystemSummary(getSystemSummary()); break;
                    case 7: runRefundReviewMenu(); break;
                    default: adminManagementView.displayResult("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runStadiumMenu() {
        while (true) {
            requireAdmin();
            try {
                int choice = adminManagementView.showStadiumMenu();
                if (choice == 0) return;
                if (choice < 1 || choice > 17) {
                    adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                if (choice == 1) {
                    stadium.dto.StadiumRequestDto request = adminManagementView.readStadiumRequest();
                    if (request != null) adminManagementView.displayResult(stadiumController.createStadium(request));
                    continue;
                }
                if (choice == 2) {
                    adminManagementView.displayResult(stadiumController.getStadiums());
                    continue;
                }
                if (choice <= 7) adminManagementView.displayResult(stadiumController.getStadiums());
                else displaySections(choice >= 13);
                Long id = adminManagementView.readLong(
                        choice <= 7 ? "Stadium ID (0 = Back): " :
                        choice <= 12 ? "Section ID (0 = Back): " : "Seat ID (0 = Back): ");
                if (id == null) continue;
                switch (choice) {
                    case 3: adminManagementView.displayResult(stadiumController.getStadiumDetails(id)); break;
                    case 4:
                        stadium.dto.StadiumRequestDto request = adminManagementView.readStadiumRequest();
                        if (request != null) adminManagementView.displayResult(stadiumController.updateStadium(id, request));
                        break;
                    case 5:
                        if (adminManagementView.confirm("Delete stadium #" + id))
                            adminManagementView.displayResult(stadiumController.deleteStadium(id));
                        break;
                    case 6:
                    case 9:
                        String name = adminManagementView.readText("Section name (Enter = Cancel): ");
                        if (!name.isEmpty()) adminManagementView.displayResult(choice == 6
                                ? stadiumController.createSection(id, name) : stadiumController.updateSection(id, name));
                        break;
                    case 7: adminManagementView.displayResult(stadiumController.getSections(id)); break;
                    case 8: adminManagementView.displayResult(stadiumController.getSectionDetails(id)); break;
                    case 10:
                        if (adminManagementView.confirm("Delete section #" + id))
                            adminManagementView.displayResult(stadiumController.deleteSection(id));
                        break;
                    case 11:
                    case 14:
                        String row = adminManagementView.readText("Row label (Enter = Cancel): ");
                        if (row.isEmpty()) break;
                        int number = adminManagementView.readInt("Seat number: ");
                        adminManagementView.displayResult(choice == 11 ? stadiumController.createSeat(id, row, number)
                                : stadiumController.updateSeat(id, row, number));
                        break;
                    case 12: adminManagementView.displayResult(stadiumController.buildSeatMap(id)); break;
                    case 13: adminManagementView.displayResult(stadiumController.getSeatDetails(id)); break;
                    case 15:
                        if (adminManagementView.confirm("Delete seat #" + id))
                            adminManagementView.displayResult(stadiumController.deleteSeat(id));
                        break;
                    case 16: adminManagementView.displayResult(stadiumController.activateSeat(id)); break;
                    case 17:
                        if (adminManagementView.confirm("Deactivate seat #" + id))
                            adminManagementView.displayResult(stadiumController.deactivateSeat(id));
                        break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void displaySections(boolean includeSeats) {
        for (stadium.dto.StadiumResponseDto stadium : stadiumController.getStadiums()) {
            adminManagementView.displayResult(stadium);
            for (stadium.model.Section section : stadiumController.getSections(stadium.getId())) {
                adminManagementView.displayResult(section);
                if (includeSeats) adminManagementView.displayResult(stadiumController.buildSeatMap(section.getId()));
            }
        }
    }

    private void runMatchMenu() {
        while (true) {
            requireAdmin();
            try {
                int choice = adminManagementView.showMatchMenu();
                if (choice == 0) return;
                if (choice < 1 || choice > 13) {
                    adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                if (choice == 1 || choice == 4) {
                    adminManagementView.displayResult(stadiumController.getStadiums());
                }
                if (choice == 1) {
                    match.dto.MatchRequestDto request = adminManagementView.readMatchRequest();
                    if (request != null) adminManagementView.displayResult(matchController.createMatch(request));
                    continue;
                }
                adminManagementView.displayResult(matchController.getMatches());
                if (choice == 2) continue;
                Long id = adminManagementView.readLong("Match ID (0 = Back): ");
                if (id == null) continue;
                Long sectionId = null;
                if (choice == 6 || choice == 8 || choice == 9 || choice == 10) {
                    adminManagementView.displayResult(stadiumController.getSections(matchController.getMatchDetails(id).getStadiumId()));
                    sectionId = adminManagementView.readLong("Section ID (0 = Back): ");
                    if (sectionId == null) continue;
                }
                switch (choice) {
                    case 3: adminManagementView.displayResult(matchController.getMatchDetails(id)); break;
                    case 4:
                        match.dto.MatchRequestDto request = adminManagementView.readMatchRequest();
                        if (request != null) adminManagementView.displayResult(matchController.updateMatch(id, request));
                        break;
                    case 5:
                        if (adminManagementView.confirm("Delete match #" + id))
                            adminManagementView.displayResult(matchController.deleteMatch(id));
                        break;
                    case 6:
                    case 9:
                        java.math.BigDecimal amount = adminManagementView.readAmount("Price in VND (0 = Cancel): ");
                        if (amount != null) adminManagementView.displayResult(choice == 6
                                ? matchController.createTicketPrice(new TicketPriceRequestDto(id, sectionId, amount))
                                : matchController.updateTicketPrice(id, sectionId, amount));
                        break;
                    case 7: adminManagementView.displayResult(matchController.getTicketPrices(id)); break;
                    case 8: adminManagementView.displayResult(matchController.getTicketPriceDetails(id, sectionId)); break;
                    case 10:
                        if (adminManagementView.confirm("Delete price for match #" + id + ", section #" + sectionId))
                            adminManagementView.displayResult(matchController.deleteTicketPrice(id, sectionId));
                        break;
                    case 11: adminManagementView.displayResult(matchController.openTicketSales(id)); break;
                    case 12:
                        if (adminManagementView.confirm("Close sales for match #" + id))
                            adminManagementView.displayResult(matchController.closeTicketSales(id));
                        break;
                    case 13: adminManagementView.displayResult(matchController.getTicketSalesStatus(id)); break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                adminManagementView.displayResult("Please enter a valid number.");
            } catch (java.time.format.DateTimeParseException e) {
                adminManagementView.displayResult("Use yyyy-MM-ddTHH:mm, for example 2030-11-15T19:00.");
            } catch (AppException | IllegalArgumentException e) {
                adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runAccountMenu(boolean staff) {
        while (true) {
            requireAdmin();
            try {
                int choice = staff ? adminManagementView.showStaffManagementMenu() : adminManagementView.showFanManagementMenu();
                if (choice == 0) return;
                if (choice < 1 || choice > (staff ? 11 : 9)) {
                    adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                if (choice == 1) {
                    admin.dto.AccountRequestDto request = adminManagementView.readAccountRequest(staff ? null : UserRole.FAN, true);
                    if (request != null) adminManagementView.displayResult(staff
                            ? accountAdminController.createStaffAccount(request) : accountAdminController.createFanAccount(request));
                    continue;
                }
                if (choice == 4) {
                    String keyword = adminManagementView.readText("Keyword (Enter = All): ");
                    adminManagementView.displayResult(staff ? accountAdminController.searchStaff(keyword)
                            : accountAdminController.searchFans(keyword));
                    continue;
                }
                if (choice == 5) {
                    UserRole role = staff ? adminManagementView.readRole(true) : null;
                    UserStatus status = adminManagementView.readStatus();
                    adminManagementView.displayResult(staff ? accountAdminController.filterStaff(role, status)
                            : accountAdminController.filterFans(status));
                    continue;
                }
                adminManagementView.displayResult(staff ? accountAdminController.getStaffs() : accountAdminController.getFans());
                if (choice == 2) continue;
                Long id = adminManagementView.readLong(staff ? "Staff ID (0 = Back): " : "Fan ID (0 = Back): ");
                if (id == null) continue;
                switch (choice) {
                    case 3: adminManagementView.displayResult(staff ? accountAdminController.getStaffDetails(id)
                            : accountAdminController.getFanDetails(id)); break;
                    case 6:
                        admin.dto.AccountRequestDto request = adminManagementView.readAccountRequest(staff ? null : UserRole.FAN, false);
                        if (request != null) adminManagementView.displayResult(staff
                                ? accountAdminController.updateStaff(id, request) : accountAdminController.updateFan(id, request));
                        break;
                    case 7: adminManagementView.displayResult(staff ? accountAdminController.activateStaff(id)
                            : accountAdminController.activateFan(id)); break;
                    case 8:
                        if (adminManagementView.confirm("Deactivate account #" + id))
                            adminManagementView.displayResult(staff ? accountAdminController.deactivateStaff(id)
                                    : accountAdminController.deactivateFan(id));
                        break;
                    case 9:
                        if (!staff) {
                            if (adminManagementView.confirm("Delete fan #" + id))
                                adminManagementView.displayResult(accountAdminController.deleteFan(id));
                            break;
                        }
                    case 10:
                        UserRole role = adminManagementView.readRole(false);
                        if (role != null) adminManagementView.displayResult(choice == 9
                                ? accountAdminController.assignRole(id, role) : accountAdminController.changeRole(id, role));
                        break;
                    case 11:
                        if (adminManagementView.confirm("Revoke role from staff #" + id))
                            adminManagementView.displayResult(accountAdminController.revokeRole(id));
                        break;
                    default: break;
                }
            } catch (NumberFormatException e) {
                adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private void runRefundReviewMenu() {
        while (true) {
            UserResponseDto admin = requireAdmin();
            try {
                List<refund.model.RefundRequest> pending = refundController.getPendingRefunds();
                adminManagementView.displayResult(pending);
                if (pending.isEmpty()) return;
                int choice = adminManagementView.showRefundReviewMenu();
                if (choice == 0) return;
                if (choice != 1) {
                    adminManagementView.displayResult("Invalid choice.");
                    continue;
                }
                refund.dto.RefundReviewRequestDto request = adminManagementView.readRefundReviewRequest(admin.getId());
                if (request == null) continue;
                RefundResponseDto result = refundController.reviewRefund(request);
                auditLogRepository.append(new AuditLog(null, admin.getId(), "REVIEW_REFUND", "REFUND",
                        result.getRequestId(), result.getStatus().name(), LocalDateTime.now()));
                adminManagementView.displayResult(result);
            } catch (NumberFormatException e) {
                adminManagementView.displayResult("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                adminManagementView.displayResult(e.getMessage());
            }
        }
    }

    private UserResponseDto requireAdmin() {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || user.getRole() != UserRole.ADMIN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Admin login required");
        }
        return user;
    }
}
