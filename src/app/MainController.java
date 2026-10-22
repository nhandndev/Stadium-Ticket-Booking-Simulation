package app;

import auth.controller.AuthController;
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

    public MainController(MainView mainView, SeatMapView seatMapView,
            BrowseController browseController, AuthController authController,
            FanController fanController) {
        this.mainView = mainView;
        this.seatMapView = seatMapView;
        this.browseController = browseController;
        this.authController = authController;
        this.fanController = fanController;
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
                        this.mainView.displayMatches(this.browseController.searchMatches(this.mainView.readKeyword()));
                        break;
                    case 2:
                        this.mainView.displayMatches(this.browseController.filterMatches(
                                this.mainView.readStadiumId(), this.mainView.readStartDate()));
                        break;
                    case 3:
                        this.mainView.displayMatchDetails(this.browseController.viewMatchDetails(
                                this.mainView.readMatchId()));
                        break;
                    case 4:
                        this.seatMapView.displaySeatMap(this.browseController.getSeatMap(
                                this.mainView.readMatchId()));
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
                this.mainView.displayMessage("Invalid date format.");
            }
        }
    }

    public void handleFanLogin() {
        this.loginForRole(true);
    }

    public void handleStaffLogin() {
        this.loginForRole(false);
    }

    private void loginForRole(boolean fanLogin) {
        UserResponseDto user = this.authController.login(this.mainView.readLoginRequest());
        boolean isFan = user.getRole() == UserRole.FAN;
        if (fanLogin != isFan) {
            this.authController.logout();
            this.mainView.displayMessage("This account belongs to the other login menu.");
            return;
        }
        this.mainView.displayUser(user);
        this.routeAuthenticatedUser(user);
    }

    public void handleFanRegistration() {
        this.mainView.displayUser(this.fanController.register(this.mainView.readRegisterFanRequest()));
    }

    public void routeAuthenticatedUser(UserResponseDto user) {
        while (this.authController.getCurrentUser() != null) {
            try {
                int choice = this.menuForRole(user.getRole());
                switch (choice) {
                    case 1:
                        this.mainView.displayUser(this.authController.getCurrentUser());
                        break;
                    case 2:
                        user = this.authController.updateCurrentUserProfile(
                                this.mainView.readProfileUpdateRequest());
                        this.mainView.displayUser(user);
                        break;
                    case 0:
                        this.authController.logout();
                        this.mainView.displayMessage("Logged out.");
                        break;
                    default:
                        this.mainView.displayMessage("Invalid choice.");
                }
            } catch (AppException e) {
                this.mainView.displayMessage(e.getMessage());
            } catch (NumberFormatException e) {
                this.mainView.displayMessage("Invalid number.");
            }
        }
    }

    public int menuForRole(UserRole role) {
        switch (role) {
            case FAN:
                return this.mainView.showFanMenu();
            case SELLER:
                return this.mainView.showSellerMenu();
            case GATE_STAFF:
                return this.mainView.showGateStaffMenu();
            case ADMIN:
                return this.mainView.showAdminMenu();
            case SIMULATOR_OPERATOR:
                return this.mainView.showSimulatorMenu();
            default:
                throw new IllegalStateException("Unknown role: " + role);
        }
    }
}
