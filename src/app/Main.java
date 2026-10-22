package app;

import auth.controller.AuthController;
import auth.service.AuthService;
import match.controller.BrowseController;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.service.BrowseService;
import match.view.SeatMapView;
import user.controller.FanController;
import user.repository.FanRepository;
import user.repository.StaffRepository;
import user.service.FanService;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path dataFolder = args.length > 0 ? Path.of(args[0]) : Path.of("data");
        MatchRepository matchRepository = new MatchRepository(dataFolder.resolve("matches.csv"));
        MatchSeatRepository matchSeatRepository = new MatchSeatRepository(
                dataFolder.resolve("match_seats.csv"));
        FanRepository fanRepository = new FanRepository(dataFolder.resolve("fans.csv"));
        StaffRepository staffRepository = new StaffRepository(dataFolder.resolve("staff.csv"));

        BrowseController browseController = new BrowseController(
                new BrowseService(matchRepository, matchSeatRepository));
        FanController fanController = new FanController(
                new FanService(fanRepository, staffRepository));
        AuthController authController = new AuthController(
                new AuthService(fanRepository, staffRepository));

        try (Scanner scanner = new Scanner(System.in)) {
            MainView mainView = new MainView(scanner);
            SeatMapView seatMapView = new SeatMapView();
            MainController mainController = new MainController(mainView, seatMapView,
                    browseController, authController, fanController);
            mainController.start();
        }
    }
}
