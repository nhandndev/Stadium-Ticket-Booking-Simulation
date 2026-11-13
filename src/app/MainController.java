package app;

import auth.controller.AuthController;
import admin.controller.AdminController;
import booking.controller.BookingController;
import ticket.controller.TicketController;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.controller.BrowseController;
import match.dto.MatchResponseDto;
import match.view.SeatMapView;
import user.controller.FanController;
import user.dto.UserResponseDto;
import user.enums.UserRole;
import user.dto.LoginRequestDto;
import user.dto.RegisterFanRequestDto;
import user.dto.ProfileUpdateRequestDto;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

public class MainController {
    private final MainView mainView;
    private final SeatMapView seatMapView;
    private final BrowseController browseController;
    private final AuthController authController;
    private final FanController fanController;
    private final AdminController adminController;
    private final BookingController bookingController;
    private final TicketController ticketController;

    public MainController(MainView mainView, SeatMapView seatMapView,
                          BrowseController browseController, AuthController authController,
                          FanController fanController,
                          AdminController adminController, BookingController bookingController,
                          TicketController ticketController) {
        this.mainView = mainView;
        this.seatMapView = seatMapView;
        this.browseController = browseController;
        this.authController = authController;
        this.fanController = fanController;
        this.adminController = adminController;
        this.bookingController = bookingController;
        this.ticketController = ticketController;
    }
    public void start() {
        boolean running = true;
        while (running) {
            try {
                switch (this.mainView.showMainMenu()) {
                    case 1:
                        this.runGuestMenu();
                        break;
                    case 2:
                        this.handleFanLogin();
                        break;
                    case 3:
                        this.handleStaffLogin();
                        break;
                    case 4:
                        this.handleFanRegistration();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        this.mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                this.mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                this.mainView.displayMessage("Invalid number.");
            } catch (NoSuchElementException e) {
                this.authController.logout();
                running = false;
            }
        }
        this.mainView.displayMessage("Goodbye!");
    }

    public void runGuestMenu() {
        boolean browsing = true;
        while (browsing) {
            try {
                switch (this.mainView.showGuestMenu()) {
                    case 1:
                        List<MatchResponseDto> searchResults = this.browseController.searchMatches(this.mainView.readKeyword());
                        this.mainView.displayMatches(searchResults);
                        this.handleMatchListActions(searchResults);
                        break;
                    case 2:
                        List<MatchResponseDto> schedules = this.browseController.filterMatches(null, null);
                        if (schedules.isEmpty()) {
                            this.mainView.displayMatches(schedules);
                            break;
                        }
                        this.mainView.displayStadiumOptions(schedules);
                        List<MatchResponseDto> filterResults = this.browseController.filterMatches(
                                this.mainView.readStadiumId(), this.mainView.readStartDate());
                        this.mainView.displayMatches(filterResults);
                        this.handleMatchListActions(filterResults);
                        break;
                    case 3:
                        List<MatchResponseDto> detailMatches = this.browseController.filterMatches(null, null);
                        this.mainView.displayMatches(detailMatches);
                        if (detailMatches.isEmpty()) break;
                        Long detailId = this.mainView.readMatchId();
                        if (detailId != null) {
                            this.mainView.displayMatchDetails(this.browseController.viewMatchDetails(detailId));
                            this.handleMatchActions(detailMatches, detailId);
                        }
                        break;
                    case 4:
                        List<MatchResponseDto> seatMatches = this.browseController.filterMatches(null, null);
                        this.mainView.displayMatches(seatMatches);
                        if (seatMatches.isEmpty()) break;
                        Long seatId = this.mainView.readMatchId();
                        if (seatId != null) {
                            this.seatMapView.displaySeatMap(this.browseController.getSeatMap(seatId));
                            this.handleMatchActions(seatMatches, seatId);
                        }
                        break;
                    case 0:
                        browsing = false;
                        break;
                    default:
                        this.mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                this.mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                this.mainView.displayMessage("Invalid number.");
            } catch (DateTimeParseException e) {
                this.mainView.displayMessage("Enter a valid date in yyyy-MM-dd format.");
            } catch (IllegalArgumentException e) {
                this.mainView.displayMessage(e.getMessage());
            }
        }
    }

    public void handleMatchListActions(List<MatchResponseDto> matches) {
        this.handleMatchActions(matches, null);
    }

    private void handleMatchActions(List<MatchResponseDto> matches, Long selectedMatchId) {
        if (matches == null || matches.isEmpty()) {
            return;
        }
        boolean active = true;
        while (active) {
            try {
                UserResponseDto user = this.authController.getCurrentUser();
                boolean canBook = user != null && user.getRole() == UserRole.FAN;
                // Actions: 1 = details, 2 = seat map, 3 = booking, 4 = change match.
                int action = this.mainView.showMatchActionMenu(canBook, selectedMatchId);
                if (action == 4 && selectedMatchId != null) {
                    selectedMatchId = null;
                    this.mainView.displayMatches(matches);
                    continue;
                }
                if (action == 1 || action == 2 || (action == 3 && canBook)) {
                    Long matchId = selectedMatchId;
                    if (matchId == null) matchId = this.mainView.readMatchId();
                    if (matchId == null) continue;
                    boolean listed = false;
                    for (MatchResponseDto match : matches) {
                        if (match.getId().equals(matchId)) listed = true;
                    }
                    if (!listed) {
                        this.mainView.displayMessage("Choose a Match ID from these results.");
                        continue;
                    }
                    selectedMatchId = matchId;
                    if (action == 1) {
                        this.mainView.displayMatchDetails(this.browseController.viewMatchDetails(matchId));
                    } else if (action == 2) {
                        this.seatMapView.displaySeatMap(this.browseController.getSeatMap(matchId));
                    } else {
                        this.bookingController.bookMatch(matchId);
                    }
                    continue;
                }
                switch (action) {
                    case 0:
                        active = false;
                        break;
                    default:
                        this.mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                this.mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                this.mainView.displayMessage("Invalid number.");
            } catch (IllegalArgumentException e) {
                this.mainView.displayMessage(e.getMessage());
            }
        }
    }

    public void handleFanLogin() {
        LoginRequestDto request = this.mainView.readLoginRequest();
        if (request == null) return;
        UserResponseDto user = this.authController.login(request);
        if (user.getRole() != UserRole.FAN) {
            this.authController.logout();
            this.mainView.displayMessage("Use Login As Staff for this account.");
            return;
        }
        this.mainView.displayUser(user);
        this.routeAuthenticatedUser(user);
    }

    public void handleStaffLogin() {
        LoginRequestDto request = this.mainView.readLoginRequest();
        if (request == null) return;
        UserResponseDto user = this.authController.login(request);
        if (user.getRole() == UserRole.FAN) {
            this.authController.logout();
            this.mainView.displayMessage("Use Login As Fan for this account.");
            return;
        }
        this.mainView.displayUser(user);
        this.routeAuthenticatedUser(user);
    }

    public void routeAuthenticatedUser(UserResponseDto user) {
        while ((user = this.authController.getCurrentUser()) != null) {
            try {
                int choice = this.menuForRole(user.getRole());
                switch (choice) {
                    case 1:
                        this.mainView.displayUser(user);
                        break;
                    case 2:
                        switch (user.getRole()) {
                            case FAN:
                                this.bookingController.runFanBookingMenu();
                                break;
                            case SELLER:
                                this.bookingController.runSellerMenu();
                                break;
                            case GATE_STAFF:
                                this.ticketController.runGateMenu();
                                break;
                            case ADMIN:
                                this.adminController.runAdminMenu();
                                break;
                            default:
                                this.mainView.displayMessage("Invalid choice.");
                        }
                        break;
                    case 3:
                        ProfileUpdateRequestDto profile = this.mainView.readProfileUpdateRequest();
                        if (profile != null) this.mainView.displayUser(this.authController.updateCurrentUserProfile(profile));
                        break;
                    case 4:
                        if (user.getRole() == UserRole.FAN) {
                            this.runGuestMenu();
                        } else {
                            this.mainView.displayMessage("Invalid choice.");
                        }
                        break;
                    case 0:
                        this.authController.logout();
                        this.mainView.displayMessage("Logged out.");
                        return;
                    default:
                        this.mainView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                this.mainView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                this.mainView.displayMessage(e.getMessage());
            }
        }
    }

    public int menuForRole(UserRole role) {
        switch (role) {
            case FAN: return this.mainView.showFanMenu();
            case SELLER: return this.mainView.showSellerMenu();
            case GATE_STAFF: return this.mainView.showGateStaffMenu();
            case ADMIN: return this.mainView.showAdminMenu();
            default:
                this.authController.logout();
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "No menu is available for this role.");
        }
    }

    public void handleFanRegistration() {
        RegisterFanRequestDto request = this.mainView.readRegisterFanRequest();
        if (request != null) {
            this.mainView.displayUser(this.fanController.register(request));
            this.mainView.displayMessage("Account created. Select Login As Fan to continue.");
        }
    }
}
