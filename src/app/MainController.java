package app;

import auth.controller.AuthController;
import admin.controller.AdminController;
import common.exception.AppException;
import match.controller.BrowseController;
import match.view.SeatMapView;
import user.controller.FanController;
import user.dto.UserResponseDto;
import user.enums.UserRole;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class MainController {
    private final MainView mainView;
    private final SeatMapView seatMapView;
    private final BrowseController browseController;
    private final AuthController authController;
    private final FanController fanController;
    private final AdminController adminController;

    public MainController(MainView mainView, SeatMapView seatMapView,
                          BrowseController browseController, AuthController authController,
                          FanController fanController,
                          AdminController adminController) {
        this.mainView = mainView;
        this.seatMapView = seatMapView;
        this.browseController = browseController;
        this.authController = authController;
        this.fanController = fanController;
        this.adminController = adminController;
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
                        mainView.displayMatches(browseController.searchMatches(mainView.readKeyword()));
                        break;
                    case 2:
                        mainView.displayMatches(browseController.filterMatches(
                                mainView.readStadiumId(), mainView.readStartDate()));
                        break;
                    case 3:
                        mainView.displayMatchDetails(browseController.viewMatchDetails(
                                mainView.readMatchId()));
                        break;
                    case 4:
                        seatMapView.displaySeatMap(browseController.getSeatMap(
                                mainView.readMatchId()));
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
                mainView.displayMessage("Invalid date format.");
            }
        }
    }

    public void handleFanLogin() {
        loginForRole(true);
    }

    public void handleStaffLogin() {
        loginForRole(false);
    }

    private void loginForRole(boolean fanLogin) {
        UserResponseDto user = authController.login(mainView.readLoginRequest());
        boolean isFan = user.getRole() == UserRole.FAN;
        if (fanLogin != isFan) {
            authController.logout();
            mainView.displayMessage("This account belongs to the other login menu.");
            return;
        }
        mainView.displayUser(user);
        routeAuthenticatedUser(user);
    }

    public void handleFanRegistration() {
        mainView.displayUser(fanController.register(mainView.readRegisterFanRequest()));
    }

    public void routeAuthenticatedUser(UserResponseDto user) {
        while (authController.getCurrentUser() != null) {
            try {
                int choice = menuForRole(user.getRole());
                switch (choice) {
                    case 1:
                        mainView.displayUser(authController.getCurrentUser());
                        break;
                    case 2:
                        user = authController.updateCurrentUserProfile(
                                mainView.readProfileUpdateRequest());
                        mainView.displayUser(user);
                        break;
                    case 3:
                        if (user.getRole() == UserRole.ADMIN) {
                            adminController.runAdminMenu();
                        } else {
                            mainView.displayMessage("Invalid choice.");
                        }
                        break;
                    case 0:
                        authController.logout();
                        mainView.displayMessage("Logged out.");
                        break;
                    default:
                        mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                mainView.displayMessage("Invalid number.");
            }
        }
    }

    private int menuForRole(UserRole role) {
        switch (role) {
            case FAN:
                return mainView.showFanMenu();
            case SELLER:
                return mainView.showSellerMenu();
            case GATE_STAFF:
                return mainView.showGateStaffMenu();
            case ADMIN:
                return mainView.showAdminMenu();
            case SIMULATOR_OPERATOR:
                return mainView.showSimulatorMenu();
            default:
                throw new IllegalStateException("Unknown role: " + role);
        }
    }
}
