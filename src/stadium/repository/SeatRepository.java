package stadium.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import stadium.model.Seat;

import java.nio.file.Path;
import java.util.List;

public class SeatRepository extends CsvRepository<Seat> {
    public SeatRepository(Path filePath) {
        super(filePath);
    }

    public List<Seat> findBySectionId(Long sectionId) {
        if (sectionId == null || sectionId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Section ID must be positive");
        }
        return findByCondition(seat -> seat.getSectionId().equals(sectionId));
    }

    @Override
    public Seat parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 5 seat columns");
        }
        long id = Long.parseLong(parts[0]);
        long sectionId = Long.parseLong(parts[1]);
        int seatNumber = Integer.parseInt(parts[3]);
        if (id <= 0 || sectionId <= 0 || seatNumber <= 0 || !isValidText(parts[2])
                || !(parts[4].equals("true") || parts[4].equals("false"))) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid seat data");
        }
        return new Seat(id, sectionId, parts[2], seatNumber,
                Boolean.parseBoolean(parts[4]));
    }

    @Override
    public String formatLine(Seat entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || entity.getSectionId() == null || entity.getSectionId() <= 0
                || entity.getSeatNumber() <= 0 || !isValidText(entity.getRowLabel())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid seat CSV data");
        }
        return entity.getId() + "," + entity.getSectionId() + "," + entity.getRowLabel()
                + "," + entity.getSeatNumber() + "," + entity.isActive();
    }

    private boolean isValidText(String value) {
        return value != null && !value.trim().isEmpty()
                && !value.contains(",") && !value.contains("\"")
                && !value.contains("\n") && !value.contains("\r");
    }
}
