package app;

import admin.controller.AdminController;
import admin.repository.AuditLogRepository;
import admin.service.AdminService;
import admin.view.AdminManagementView;
import auth.controller.AuthController;
import auth.service.AuthService;
import booking.controller.BookingController;
import booking.repository.BookingRepository;
import booking.repository.TransactionRepository;
import booking.service.BookingService;
import booking.strategy.StrategyFactory;
import booking.view.BookingView;
import gate.view.GateView;
import payment.repository.PaymentRepository;
import payment.service.MockPaymentService;
import refund.controller.RefundController;
import refund.repository.RefundRepository;
import refund.service.RefundService;
import seller.view.SellerView;
import ticket.controller.TicketController;
import ticket.repository.TicketRepository;
import ticket.service.TicketService;
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
        BookingRepository bookingRepository = new BookingRepository(dataFolder.resolve("bookings.csv"));
        PaymentRepository paymentRepository = new PaymentRepository(dataFolder.resolve("payments.csv"));
        TicketRepository ticketRepository = new TicketRepository(dataFolder.resolve("tickets.csv"), bookingRepository);
        TransactionRepository transactionRepository = new TransactionRepository(dataFolder.resolve("transactions.csv"));
        RefundRepository refundRepository = new RefundRepository(dataFolder.resolve("refunds.csv"));

        BrowseController browseController = new BrowseController(
                new BrowseService(matchRepository, matchSeatRepository, seatRepository,
                        stadiumRepository, sectionRepository, ticketPriceRepository));
        AuthController authController = new AuthController(
                new AuthService(fanRepository, staffRepository));
        FanController fanController = new FanController(
                new FanService(fanRepository, staffRepository, ticketRepository,
                        matchRepository, seatRepository, sectionRepository), authController);
        boolean paymentSuccess = args.length < 2 || !args[1].equals("--payment-fails");
        BookingService bookingService = new BookingService(bookingRepository, ticketRepository,
                paymentRepository, transactionRepository, seatRepository, ticketPriceRepository,
                new StrategyFactory(matchSeatRepository, transactionRepository),
                new MockPaymentService(paymentSuccess), fanRepository, matchRepository,
                matchSeatRepository, sectionRepository);
        TicketService ticketService = new TicketService(ticketRepository, bookingRepository,
                matchRepository, seatRepository, sectionRepository);
        RefundController refundController = new RefundController(new RefundService(
                refundRepository, bookingRepository, paymentRepository, ticketRepository,
                matchRepository, matchSeatRepository), authController);

        StadiumController stadiumController = new StadiumController(
                new StadiumService(stadiumRepository, sectionRepository, seatRepository,
                        matchRepository, matchSeatRepository, ticketPriceRepository));
        MatchController matchController = new MatchController(
                new MatchService(matchRepository, ticketPriceRepository, matchSeatRepository,
                        bookingRepository, stadiumRepository, sectionRepository, seatRepository));
        AccountAdminController accountAdminController = new AccountAdminController(
                new AccountAdminService(fanRepository, staffRepository, bookingRepository));
        AdminService adminService = new AdminService(
                fanRepository, staffRepository, stadiumRepository,
                matchRepository, auditLogRepository, bookingRepository, ticketRepository);

        try (Scanner scanner = new Scanner(System.in)) {
            MainView mainView = new MainView(scanner);
            SeatMapView seatMapView = new SeatMapView();
            TicketController ticketController = new TicketController(ticketService, authController,
                    bookingRepository, new GateView(scanner));
            BookingController bookingController = new BookingController(bookingService, authController,
                    new BookingView(scanner), new SellerView(scanner), seatMapView, browseController,
                    fanController, refundController, ticketController);
            AdminController adminController = new AdminController(adminService,
                    new AdminManagementView(scanner), stadiumController, matchController,
                    accountAdminController, refundController, authController, auditLogRepository);
            MainController mainController = new MainController(mainView, seatMapView,
                    browseController, authController, fanController, adminController,
                    bookingController, ticketController);
            mainController.start();
        }
    }
}
