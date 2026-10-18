package app;

import match.controller.BrowseController;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.service.BrowseService;
import match.view.SeatMapView;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path dataFolder = args.length > 0 ? Path.of(args[0]) : Path.of("data");
        MatchRepository matchRepository = new MatchRepository(dataFolder.resolve("matches.csv"));
        MatchSeatRepository matchSeatRepository = new MatchSeatRepository(
                dataFolder.resolve("match_seats.csv"));
        BrowseService browseService = new BrowseService(matchRepository, matchSeatRepository);
        BrowseController browseController = new BrowseController(browseService);
        try (Scanner scanner = new Scanner(System.in)) {
            MainView mainView = new MainView(scanner);
            SeatMapView seatMapView = new SeatMapView();
            MainController mainController = new MainController(mainView, seatMapView,
                    browseController);
            mainController.start();
        }
    }
}
