package app;

import common.exception.AppException;
import match.controller.BrowseController;
import match.view.SeatMapView;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class MainController {
    private final MainView mainView;
    private final SeatMapView seatMapView;
    private final BrowseController browseController;

    public MainController(MainView mainView, SeatMapView seatMapView,
                          BrowseController browseController) {
        this.mainView = mainView;
        this.seatMapView = seatMapView;
        this.browseController = browseController;
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
            } catch (NumberFormatException e) {
                mainView.displayMessage("Invalid choice.");
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
                        Long stadiumId = mainView.readStadiumId();
                        mainView.displayMatches(browseController.filterMatches(
                                stadiumId, mainView.readStartDate()));
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
                mainView.displayMessage("Invalid input.");
            } catch (DateTimeParseException e) {
                mainView.displayMessage("Invalid date format.");
            }
        }
    }

    public void handleFanLogin() {
        mainView.displayMessage("Fan login is not implemented yet.");
    }

    public void handleStaffLogin() {
        mainView.displayMessage("Staff login is not implemented yet.");
    }

    public void handleFanRegistration() {
        mainView.displayMessage("Fan registration is not implemented yet.");
    }
}
