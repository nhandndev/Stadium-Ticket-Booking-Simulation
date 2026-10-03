package stadium.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import stadium.model.Seat;

import java.nio.file.Path;
import java.util.List;

public class seatRepository extends CsvRepository<Seat> {
    public seatRepository(Path filePath) {
        super(filePath);
    }

    public List<Seat> getSeatsBySectionId(Long sectionId) {
        if (sectionId == null || sectionId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Section ID is null");
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
public String formatLine(Seat seat) {
    if (seat == null || seat.getId() == null || seat.getId() <= 0
            || seat.getSectionId() == null || seat.getSectionId() <= 0
            || seat.getSeatNumber() <= 0 || !isValidText(seat.getRowLabel())) {
        throw new AppException(ErrorCode.INVALID_INPUT, "Invalid seat CSV data");

    }
    return seat.getId() + "," + seat.getSectionId() + "," + seat.getRowLabel()
            + "," + seat.getSeatNumber() + "," + seat.isActive();
}
    private boolean isValidText (String value){
        return value != null && !value.trim().isEmpty()
                && !value.contains(",") && !value.contains("\"")
                && !value.contains("\n") && !value.contains("\r");
    }
}

