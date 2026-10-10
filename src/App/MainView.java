package app;

import common.exception.AppException;
import match.controller.BrowseController;
import match.dto.MatchResponseDto;
import match.view.SeatMapView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner;
    private final BrowseController browseController;
    public MainView(Scanner scanner , BrowseController browseController) {
        this.scanner = scanner;
        this.browseController = browseController;
    }

    public void start() {
        int choice;
        do {
            showMenu();
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            handleChoice(choice);
        } while (choice != 0);

        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println();
        System.out.println("STADIUM TICKET BOOKING SIMULATION");
        System.out.println("1. Browse As Guest");
        System.out.println("2. Login As Fan");
        System.out.println("3. Login As Staff");
        System.out.println("4. Register Fan Account");
        System.out.println("0. Exit");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                showGuestMenu();
                break;
            case 2:
                System.out.println("Fan login is not implemented yet.");
                break;
            case 3:
                System.out.println("Staff login is not implemented yet.");
                break;
            case 4:
                System.out.println("Fan registration is not implemented yet.");
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public void showGuestMenu() {
        SeatMapView seatMapView = new SeatMapView(browseController);
        boolean browse = true;
        while (browse) {
            System.out.println();
            System.out.println("=== GUEST ===");
            System.out.println("1. Search matches");
            System.out.println("2. Filter matches / list all");
            System.out.println("3. View match details");
            System.out.println("4. View seat availability");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            try{
                switch (choice) {
                    case "1":
                        System.out.print("Team keyword: ");
                        String keyword = scanner.nextLine();
                        List<MatchResponseDto> searched = browseController.searchMatches(keyword);
                        if(searched.isEmpty()){
                            System.out.println("No matches found.");
                        }
                        else {
                            for (MatchResponseDto match : searched) {
                                System.out.println(match.getId() + " | " + match.getHomeTeam()
                                        + " vs " + match.getAwayTeam() + " | " + match.getStartTime());
                            }
                        }
                        break;

                    case "2":
                        System.out.print("Stadium ID (blank = all): ");
                        String stadiumInput = scanner.nextLine().trim();
                        Long stadiumId = stadiumInput.isEmpty() ? null : Long.parseLong(stadiumInput);
                        System.out.print("Date yyyy-MM-dd (blank = all): ");
                        String dateInput = scanner.nextLine().trim();
                        LocalDate date = dateInput.isEmpty() ? null : LocalDate.parse(dateInput);
                        List<MatchResponseDto> filtered = browseController.filterMatches(stadiumId, date);
                        if (filtered.isEmpty()) {
                            System.out.println("No matches found.");
                        }else {
                            for (MatchResponseDto match : filtered) {
                                System.out.println(match.getId() + " | " + match.getHomeTeam()
                                        + " vs " + match.getAwayTeam() + " | " + match.getStartTime());
                            }
                        }
                        break;

                    case "3":
                        System.out.print("Match ID: ");
                        Long detailsId = Long.parseLong(scanner.nextLine().trim());
                        MatchResponseDto details = browseController.viewMatchDetails(detailsId);
                        System.out.println(details.getHomeTeam() + " vs " + details.getAwayTeam());
                        System.out.println("Stadium ID: " + details.getStadiumId());
                        System.out.println("Kick-off: " + details.getStartTime());
                        System.out.println("Sales: " + details.getSaleStatus());
                        break;
                    case "4":
                        System.out.print("Match ID: ");
                        Long seatMatchId = Long.parseLong(scanner.nextLine().trim());
                        seatMapView.displaySeatMap(seatMatchId);
                        break;
                    case "0":
                        browse = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
            catch (AppException e) {
                System.out.println(e.getMessage());
            }catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }catch (Exception e) {
                System.out.println("Error issue IDK Right Now");
            }
        }

    }
}
