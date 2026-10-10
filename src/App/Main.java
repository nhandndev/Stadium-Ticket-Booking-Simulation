package app;

import match.controller.BrowseController;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.service.BrowseService;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path demoFolder = Path.of("test");
        MatchRepository matchRepository = new MatchRepository(demoFolder.resolve("matches.csv"));
        MatchSeatRepository matchSeatRepository = new MatchSeatRepository(
                demoFolder.resolve("match_seats.csv"));
        BrowseService browseService = new BrowseService(matchRepository, matchSeatRepository);
        BrowseController browseController = new BrowseController(browseService);
        try (Scanner scanner = new Scanner(System.in)) {
            MainView view = new MainView(scanner, browseController);
            view.start();
        }
    }
}