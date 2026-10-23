package admin.data;

import common.exception.AppException;
import common.exception.ErrorCode;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import user.model.Fan;
import user.model.Staff;
import user.enums.UserRole;
import user.enums.UserStatus;
import user.repository.FanRepository;
import user.repository.StaffRepository;


public class DataGenerator {
    private static final String[] FILES = {
            "stadiums.csv", "sections.csv", "seats.csv", "fans.csv", "staff.csv",
            "matches.csv", "ticket_prices.csv", "match_seats.csv"
    };
    private static final String MARKER = ".stadium-generated.marker";
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
        StadiumRepository repository = new StadiumRepository(path("stadiums.csv"));
        List<String> rows = new ArrayList<>();
        rows.add(repository.formatLine(new Stadium(1L, "Simulation Stadium", "Generated Data")));
        writeNew("stadiums.csv", rows);
    }

    public void generateSections() {
        requireExisting("stadiums.csv");
        SectionRepository repository = new SectionRepository(path("sections.csv"));
        List<String> rows = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            rows.add(repository.formatLine(new Section(i, 1L, "Section " + i)));
        }
        writeNew("sections.csv", rows);
    }

    public void generateSeats(int recordCount) {
        requireExisting("sections.csv");
        if (recordCount < 1 || recordCount > 100000) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat count must be 1..100000");
        }
        SeatRepository repository = new SeatRepository(path("seats.csv"));
        List<String> rows = new ArrayList<>(recordCount);
        for (long i = 1; i <= recordCount; i++) {
            long sectionId = ((i - 1) % 10) + 1;
            int seatNumber = (int) (((i - 1) / 10) + 1);
            rows.add(repository.formatLine(new Seat(i, sectionId, "A", seatNumber, true)));
        }
        writeNew("seats.csv", rows);
    }

    public void generateFans() {
        FanRepository repository = new FanRepository(path("fans.csv"));
        List<String> rows = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            Fan fan = new Fan(i, "Generated Fan " + i,
                    "fan" + i + "@example.invalid", randomHash(), "000" + i,
                    UserStatus.INACTIVE);
            rows.add(repository.formatLine(fan));
        }
        writeNew("fans.csv", rows);
    }

    public void generateStaff() {
        StaffRepository repository = new StaffRepository(path("staff.csv"));
        UserRole[] roles = {UserRole.SELLER, UserRole.GATE_STAFF, UserRole.ADMIN,
                UserRole.SIMULATOR_OPERATOR, UserRole.SELLER};
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < roles.length; i++) {
            long id = i + 1L;
            Staff staff = new Staff(id, "Generated Staff " + id,
                    "staff" + id + "@example.invalid", randomHash(), "001" + id,
                    roles[i], UserStatus.INACTIVE);
            rows.add(repository.formatLine(staff));
        }
        writeNew("staff.csv", rows);
    }

    public void generateMatches() {
        requireExisting("stadiums.csv");
        requireExisting("sections.csv");
        requireExisting("seats.csv");
        MatchRepository matchRepository = new MatchRepository(path("matches.csv"));
        MatchSeatRepository inventoryRepository = new MatchSeatRepository(path("match_seats.csv"));
        TicketPriceRepository priceRepository = new TicketPriceRepository(path("ticket_prices.csv"));
        List<Seat> seats = new SeatRepository(path("seats.csv")).findAll();

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

        writeNew("ticket_prices.csv", priceRows);
        writeNew("match_seats.csv", inventoryRows);
        writeNew("matches.csv", matchRows);
    }

    public void clearGeneratedData() {
        if (!Files.isRegularFile(path(MARKER))) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Refusing to clear a directory not owned by DataGenerator");
        }
        for (String file : FILES) {
            try {
                Files.deleteIfExists(path(file));
            } catch (IOException e) {
                throw new AppException(ErrorCode.CSV_ERROR,
                        "Cannot delete generated file " + file);
            }
        }
        try {
            Files.deleteIfExists(path(MARKER));
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot remove dataset marker");
        }
    }

    private Path path(String name) {
        return outputDirectory.resolve(name);
    }

    private void requireExisting(String name) {
        if (!Files.isRegularFile(path(name))) {
            throw new AppException(ErrorCode.INVALID_INPUT, name + " must be generated first");
        }
    }

    private String randomHash() {
        return "pass_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private void writeNew(String name, List<String> rows) {
        Path target = path(name);
        if (Files.exists(target)) {
            throw new AppException(ErrorCode.INVALID_INPUT, name + " already exists");
        }
        Path temporary = null;
        try {
            Files.createDirectories(outputDirectory);
            if (!Files.exists(path(MARKER))) {
                for (String managed : FILES) {
                    if (Files.exists(path(managed))) {
                        throw new AppException(ErrorCode.INVALID_INPUT,
                                "Directory contains existing CSV; choose a fresh output folder");
                    }
                }
                Files.createFile(path(MARKER));
            }
            temporary = Files.createTempFile(outputDirectory, "generated-", ".tmp");
            Files.write(temporary, rows, StandardCharsets.UTF_8);
            try {
                Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE);
            } catch (
                    AtomicMoveNotSupportedException e) {
                Files.move(temporary, target);
            }
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR,
                    "Cannot write generated file " + name + ": " + e.getMessage());
        } finally {
            if (temporary != null) {
                try {
                    Files.deleteIfExists(temporary);
                } catch (IOException ignored) {
                    // Cleanup failure must not hide the main write error.
                }
            }
        }
    }
}

