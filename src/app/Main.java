package app;

import admin.controller.AdminController;
import admin.repository.AuditLogRepository;
import admin.service.AdminService;
import admin.view.AdminManagementView;
import auth.controller.AuthController;
import auth.service.AuthService;
import java.nio.file.Path;
import java.util.Scanner;
import match.controller.BrowseController;
import match.controller.MatchController;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.repository.TicketPriceRepository;
import match.service.BrowseService;
import match.service.MatchService;
import match.view.SeatMapView;
import stadium.controller.StadiumController;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;
import stadium.repository.StadiumRepository;
import stadium.service.StadiumService;
import user.controller.AccountAdminController;
import user.controller.FanController;
import user.repository.FanRepository;
import user.repository.StaffRepository;
import user.service.AccountAdminService;
import user.service.FanService;

public class Main {
    public static void main(String[] args) {
        Path dataFolder = args.length > 0 ? Path.of(args[0]) : Path.of("data");
        MatchRepository matchRepository = new MatchRepository(dataFolder.resolve("matches.csv"));
        MatchSeatRepository matchSeatRepository = new MatchSeatRepository(
                dataFolder.resolve("match_seats.csv"));
        FanRepository fanRepository = new FanRepository(dataFolder.resolve("fans.csv"));
        StaffRepository staffRepository = new StaffRepository(dataFolder.resolve("staff.csv"));
        StadiumRepository stadiumRepository = new StadiumRepository(dataFolder.resolve("stadiums.csv"));
        SectionRepository sectionRepository = new SectionRepository(dataFolder.resolve("sections.csv"));
        SeatRepository seatRepository = new SeatRepository(dataFolder.resolve("seats.csv"));
        TicketPriceRepository ticketPriceRepository = new TicketPriceRepository(
                dataFolder.resolve("ticket_prices.csv"));
        AuditLogRepository auditLogRepository = new AuditLogRepository(
                dataFolder.resolve("audit_logs.csv"));

        BrowseController browseController = new BrowseController(
                new BrowseService(matchRepository, matchSeatRepository));
        FanController fanController = new FanController(
                new FanService(fanRepository, staffRepository));
        AuthController authController = new AuthController(
                new AuthService(fanRepository, staffRepository));

        StadiumController stadiumController = new StadiumController(
                new StadiumService(stadiumRepository, sectionRepository, seatRepository));
        MatchController matchController = new MatchController(
                new MatchService(matchRepository, ticketPriceRepository, matchSeatRepository));
        AccountAdminController accountAdminController = new AccountAdminController(
                new AccountAdminService(fanRepository, staffRepository));
        AdminService adminService = new AdminService(
                fanRepository, staffRepository, stadiumRepository,
                matchRepository, auditLogRepository);

        try (Scanner scanner = new Scanner(System.in)) {
            MainView mainView = new MainView(scanner);
            SeatMapView seatMapView = new SeatMapView();
            AdminController adminController = new AdminController(adminService,
                    new AdminManagementView(scanner), stadiumController, matchController,
                    accountAdminController);
            MainController mainController = new MainController(mainView, seatMapView,
                    browseController, authController, fanController, adminController);
            mainController.start();
        }
    }
}
