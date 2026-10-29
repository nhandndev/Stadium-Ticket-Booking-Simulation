package booking.repository;

import booking.model.Booking;
import booking.enums.BookingStatus;
import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends CsvRepository<Booking> {
    public BookingRepository(Path filePath) {
        super(filePath);
    }

    public List<Booking> findByFanId(Long fanId) {
        if (fanId == null || fanId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan ID must be positive");
        }
        return findByCondition(booking -> booking.getFanId().equals(fanId));
    }

    @Override
    public Booking parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 6) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 6 booking columns");
        }
        long id = Long.parseLong(parts[0]);
        long fanId = Long.parseLong(parts[1]);
        long matchId = Long.parseLong(parts[2]);
        if (id <= 0 || fanId <= 0 || matchId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid booking IDs");
        }
        String[] seatParts = parts[3].split(";", -1);
        List<Long> seatIds = new ArrayList<>();
        for (String seatPart : seatParts) {
            seatIds.add(Long.parseLong(seatPart));
        }
        BigDecimal amount = new BigDecimal(parts[4]);
        BookingStatus status = BookingStatus.valueOf(parts[5]);
        return new Booking(id, fanId, matchId, seatIds, amount, status);
    }

    @Override
    public String formatLine(Booking entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking ID is required");
        }
        entity.validateTicketLimit();
        List<String> seatTexts = new ArrayList<>();
        for (Long seatId : entity.getSeatIds()) {
            seatTexts.add(seatId.toString());
        }
        return entity.getId() + "," + entity.getFanId() + "," + entity.getMatchId()
                + "," + String.join(";", seatTexts) + ","
                + entity.getTotalAmount().toPlainString() + "," + entity.getStatus().name();
    }
}
