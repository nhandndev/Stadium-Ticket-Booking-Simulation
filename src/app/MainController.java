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
                switch (mainView.showMainMenu()) {
                    case 1:
                        runGuestMenu();
                        break;
                    case 2:
                        handleFanLogin();
                        break;
                    case 3:
                        handleStaffLogin();
                        break;
                    case 4:
                        handleFanRegistration();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                mainView.displayMessage("Invalid number.");
            } catch (NoSuchElementException e) {
                authController.logout();
                running = false;
            }
        }
        mainView.displayMessage("Goodbye!");
    }

    public void runGuestMenu() {
        boolean browsing = true;
        while (browsing) {
            try {
                switch (mainView.showGuestMenu()) {
                    case 1:
                        List<MatchResponseDto> searchResults = browseController.searchMatches(mainView.readKeyword());
                        mainView.displayMatches(searchResults);
                        handleMatchListActions(searchResults);
                        break;
                    case 2:
                        List<MatchResponseDto> schedules = browseController.filterMatches(null, null);
                        if (schedules.isEmpty()) {
                            mainView.displayMatches(schedules);
                            break;
                        }
                        mainView.displayStadiumOptions(schedules);
                        List<MatchResponseDto> filterResults = browseController.filterMatches(
                                mainView.readStadiumId(), mainView.readStartDate());
                        mainView.displayMatches(filterResults);
                        handleMatchListActions(filterResults);
                        break;
                    case 3:
                        List<MatchResponseDto> detailMatches = browseController.filterMatches(null, null);
                        mainView.displayMatches(detailMatches);
                        if (detailMatches.isEmpty()) break;
                        Long detailId = mainView.readMatchId();
                        if (detailId != null) mainView.displayMatchDetails(browseController.viewMatchDetails(detailId));
                        break;
                    case 4:
                        List<MatchResponseDto> seatMatches = browseController.filterMatches(null, null);
                        mainView.displayMatches(seatMatches);
                        if (seatMatches.isEmpty()) break;
                        Long seatId = mainView.readMatchId();
                        if (seatId != null) seatMapView.displaySeatMap(browseController.getSeatMap(seatId));
                        break;
                    case 0:
                        browsing = false;
                        break;
                    default:
                        mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                mainView.displayMessage("Invalid number.");
            } catch (DateTimeParseException e) {
                mainView.displayMessage("Enter a valid date in yyyy-MM-dd format.");
            } catch (IllegalArgumentException e) {
                mainView.displayMessage(e.getMessage());
            }
        }
    }

    public void handleMatchListActions(List<MatchResponseDto> matches) {
        if (matches == null || matches.isEmpty()) {
            return;
        }
        boolean active = true;
        while (active) {
            try {
                UserResponseDto user = authController.getCurrentUser();
                boolean canBook = user != null && user.getRole() == UserRole.FAN;
                int action = mainView.showMatchActionMenu(canBook);
                if (action == 1 || action == 2 || action == 3 && canBook) {
                    Long matchId = mainView.readMatchId();
                    if (matchId == null) continue;
                    boolean listed = false;
                    for (MatchResponseDto match : matches) {
                        if (match.getId().equals(matchId)) listed = true;
                    }
                    if (!listed) {
                        mainView.displayMessage("Choose a Match ID from these results.");
                        continue;
                    }
                    if (action == 1) {
                        mainView.displayMatchDetails(browseController.viewMatchDetails(matchId));
                    } else if (action == 2) {
                        seatMapView.displaySeatMap(browseController.getSeatMap(matchId));
                    } else {
                        bookingController.bookMatch(matchId);
                    }
                    continue;
                }
                switch (action) {
                    case 0:
                        active = false;
                        break;
                    default:
                        mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                mainView.displayMessage("Invalid number.");
            } catch (IllegalArgumentException e) {
                mainView.displayMessage(e.getMessage());
            }
        }
    }

    public void handleFanLogin() {
        LoginRequestDto request = mainView.readLoginRequest();
        if (request == null) return;
        UserResponseDto user = authController.login(request);
        if (user.getRole() != UserRole.FAN) {
            authController.logout();
            mainView.displayMessage("Use Login As Staff for this account.");
            return;
        }
        mainView.displayUser(user);
        routeAuthenticatedUser(user);
    }

    public void handleStaffLogin() {
        LoginRequestDto request = mainView.readLoginRequest();
        if (request == null) return;
        UserResponseDto user = authController.login(request);
        if (user.getRole() == UserRole.FAN) {
            authController.logout();
            mainView.displayMessage("Use Login As Fan for this account.");
            return;
        }
        mainView.displayUser(user);
        routeAuthenticatedUser(user);
    }

    public void routeAuthenticatedUser(UserResponseDto user) {
        while ((user = authController.getCurrentUser()) != null) {
            try {
                int choice = menuForRole(user.getRole());
                switch (choice) {
                    case 1:
                        mainView.displayUser(user);
                        break;
                    case 2:
                        switch (user.getRole()) {
                            case FAN:
                                bookingController.runFanBookingMenu();
                                break;
                            case SELLER:
                                bookingController.runSellerMenu();
                                break;
                            case GATE_STAFF:
                                ticketController.runGateMenu();
                                break;
                            case ADMIN:
                                adminController.runAdminMenu();
                                break;
                            case SIMULATOR_OPERATOR:
                                ProfileUpdateRequestDto simProfile = mainView.readProfileUpdateRequest();
                                if (simProfile != null) mainView.displayUser(authController.updateCurrentUserProfile(simProfile));
                                break;
                            default:
                                mainView.displayMessage("Invalid choice.");
                        }
                        break;
                    case 3:
                        if (user.getRole() == UserRole.SIMULATOR_OPERATOR) {
                            mainView.displayMessage("Invalid choice.");
                            break;
                        }
                        ProfileUpdateRequestDto profile = mainView.readProfileUpdateRequest();
                        if (profile != null) mainView.displayUser(authController.updateCurrentUserProfile(profile));
                        break;
                    case 4:
                        if (user.getRole() == UserRole.FAN || user.getRole() == UserRole.SELLER) {
                            runGuestMenu();
                        } else {
                            mainView.displayMessage("Invalid choice.");
                        }
                        break;
                    case 0:
                        authController.logout();
                        mainView.displayMessage("Logged out.");
                        return;
                    default:
                        mainView.displayMessage("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                mainView.displayMessage("Please enter a valid number.");
            } catch (AppException | IllegalArgumentException e) {
                mainView.displayMessage(e.getMessage());
            }
        }
    }

    public int menuForRole(UserRole role) {
        switch (role) {
            case FAN: return mainView.showFanMenu();
            case SELLER: return mainView.showSellerMenu();
            case GATE_STAFF: return mainView.showGateStaffMenu();
            case ADMIN: return mainView.showAdminMenu();
            case SIMULATOR_OPERATOR: return mainView.showSimulatorMenu();
            default:
                authController.logout();
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "No menu is available for this role.");
        }
    }

    public void handleFanRegistration() {
        RegisterFanRequestDto request = mainView.readRegisterFanRequest();
        if (request != null) {
            mainView.displayUser(fanController.register(request));
            mainView.displayMessage("Account created. Select Login As Fan to continue.");
        }
    }
}
