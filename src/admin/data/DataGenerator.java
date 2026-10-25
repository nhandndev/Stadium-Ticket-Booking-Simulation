package admin.data;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import match.model.Match;
import match.model.MatchSeat;
import match.model.SaleStatus;
import match.model.SeatStatus;
import match.model.TicketPrice;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.repository.TicketPriceRepository;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.model.Stadium;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;
import stadium.repository.StadiumRepository;
import user.enums.UserRole;
import user.enums.UserStatus;
import user.model.Fan;
import user.model.Staff;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class DataGenerator {
    private static final String[] FILES = {
        "stadiums.csv", "sections.csv", "seats.csv", "fans.csv", "staff.csv",
        "matches.csv", "ticket_prices.csv", "match_seats.csv"
    };
    private final Path outputDirectory;

    public DataGenerator(Path outputDirectory) {
        if (outputDirectory == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Output directory is required");
        }
        this.outputDirectory = outputDirectory.toAbsolutePath().normalize();
    }

    public void generateAll(int recordCount) {
        if (recordCount < 1 || recordCount > 100000) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat count must be 1..100000");
        }
        for (String file : FILES) {
            if (Files.exists(outputDirectory.resolve(file))) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Generated dataset already exists; clear explicitly first");
            }
        }
        generateStadiums();
        generateSections();
        generateSeats(recordCount);
        generateFans();
        generateStaff();
        generateMatches();
    }

    public void generateStadiums() {
        StadiumRepository repository = new StadiumRepository(outputDirectory.resolve("stadiums.csv"));
        List<String> rows = new ArrayList<>();
        rows.add(repository.formatLine(new Stadium(1L, "Simulation Stadium", "Generated Data")));
        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("stadiums.csv"), rows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write stadiums.csv");
        }
    }

    public void generateSections() {
        if (!Files.exists(outputDirectory.resolve("stadiums.csv"))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "stadiums.csv must be generated first");
        }
        SectionRepository repository = new SectionRepository(outputDirectory.resolve("sections.csv"));
        List<String> rows = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            rows.add(repository.formatLine(new Section(i, 1L, "Section " + i)));
        }
        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("sections.csv"), rows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write sections.csv");
        }
    }

    public void generateSeats(int recordCount) {
        if (!Files.exists(outputDirectory.resolve("sections.csv"))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "sections.csv must be generated first");
        }
        if (recordCount < 1 || recordCount > 100000) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat count must be 1..100000");
        }
        SeatRepository repository = new SeatRepository(outputDirectory.resolve("seats.csv"));
        List<String> rows = new ArrayList<>(recordCount);
        for (long i = 1; i <= recordCount; i++) {
            long sectionId = ((i - 1) % 10) + 1;
            int seatNumber = (int) (((i - 1) / 10) + 1);
            rows.add(repository.formatLine(new Seat(i, sectionId, "A", seatNumber, true)));
        }
        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("seats.csv"), rows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write seats.csv");
        }
    }

    public void generateFans() {
        FanRepository repository = new FanRepository(outputDirectory.resolve("fans.csv"));
        List<String> rows = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            Fan fan = new Fan(i, "Generated Fan " + i,
                    "fan" + i + "@example.invalid", "password" + i, "000" + i,
                    UserStatus.INACTIVE);
            rows.add(repository.formatLine(fan));
        }
        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("fans.csv"), rows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write fans.csv");
        }
    }

    public void generateStaff() {
        StaffRepository repository = new StaffRepository(outputDirectory.resolve("staff.csv"));
        UserRole[] roles = {UserRole.SELLER, UserRole.GATE_STAFF, UserRole.ADMIN,
                UserRole.SIMULATOR_OPERATOR, UserRole.SELLER};
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            long id = i + 1L;
            Staff staff = new Staff(id, "Generated Staff " + id,
                    "staff" + id + "@example.invalid", "password" + id, "001" + id,
                    roles[i], UserStatus.INACTIVE);
            rows.add(repository.formatLine(staff));
        }
        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("staff.csv"), rows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write staff.csv");
        }
    }

    public void generateMatches() {
        if (!Files.exists(outputDirectory.resolve("stadiums.csv"))
                || !Files.exists(outputDirectory.resolve("sections.csv"))
                || !Files.exists(outputDirectory.resolve("seats.csv"))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Stadium prerequisites must be generated first");
        }
        MatchRepository matchRepository = new MatchRepository(outputDirectory.resolve("matches.csv"));
        MatchSeatRepository inventoryRepository = new MatchSeatRepository(outputDirectory.resolve("match_seats.csv"));
        TicketPriceRepository priceRepository = new TicketPriceRepository(outputDirectory.resolve("ticket_prices.csv"));
        List<Seat> seats = new SeatRepository(outputDirectory.resolve("seats.csv")).findAll();

        List<String> priceRows = new ArrayList<>();
        for (long sectionId = 1; sectionId <= 10; sectionId++) {
            priceRows.add(priceRepository.formatLine(new TicketPrice(1L, sectionId,
                    BigDecimal.valueOf(100000 + sectionId * 10000))));
        }
        List<String> inventoryRows = new ArrayList<>(seats.size());
        for (Seat seat : seats) {
            inventoryRows.add(inventoryRepository.formatLine(new MatchSeat(
                    seat.getId(), 1L, seat.getId(), SeatStatus.AVAILABLE, 0)));
        }
        List<String> matchRows = new ArrayList<>();
        matchRows.add(matchRepository.formatLine(new Match(1L, 1L, "Generated Home",
                "Generated Away", LocalDateTime.now().plusDays(30), SaleStatus.OPEN)));

        try {
            Files.createDirectories(outputDirectory);
            Files.write(outputDirectory.resolve("ticket_prices.csv"), priceRows, StandardCharsets.UTF_8);
            Files.write(outputDirectory.resolve("match_seats.csv"), inventoryRows, StandardCharsets.UTF_8);
            Files.write(outputDirectory.resolve("matches.csv"), matchRows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write match dataset files");
        }
    }

    public void clearGeneratedData() {
        for (String file : FILES) {
            try {
                Files.deleteIfExists(outputDirectory.resolve(file));
            } catch (IOException e) {
                throw new AppException(ErrorCode.CSV_ERROR, "Cannot delete generated file " + file);
            }
        }
    }
}
