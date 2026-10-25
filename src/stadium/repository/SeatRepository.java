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
        return new Seat(id, sectionId, parts[2], seatNumber, Boolean.parseBoolean(parts[4]));
    }

    @Override
    public String formatLine(Seat entity) {
        return entity.getId() + "," + entity.getSectionId() + "," + entity.getRowLabel()
                + "," + entity.getSeatNumber() + "," + entity.isActive();
    }
}
