package match.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import match.model.Match;
import match.model.SaleStatus;

public class MatchRepository extends CsvRepository<Match> {
    public MatchRepository(Path filePath) {
        super(filePath);
    }

    public List<Match> findUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        return findByCondition(match -> !match.getStartTime().isBefore(now));
    }

    @Override
    public Match parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 6) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 6 match columns");
        }
        try {
            long id = Long.parseLong(parts[0]);
            long stadiumId = Long.parseLong(parts[1]);
            LocalDateTime startTime = LocalDateTime.parse(parts[4]);
            SaleStatus status = SaleStatus.valueOf(parts[5]);
            if (id <= 0 || stadiumId <= 0 || !validText(parts[2]) || !validText(parts[3])) {
                throw new AppException(ErrorCode.CSV_ERROR, "Invalid match data");
            }
            return new Match(id, stadiumId, parts[2], parts[3], startTime, status);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid match number, time or status");
        }
    }

    @Override
    public String formatLine(Match entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || entity.getStadiumId() == null || entity.getStadiumId() <= 0
                || !validText(entity.getHomeTeam()) || !validText(entity.getAwayTeam())
                || entity.getStartTime() == null || entity.getSaleStatus() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid match CSV data");
        }
        return entity.getId() + "," + entity.getStadiumId() + "," + entity.getHomeTeam()
                + "," + entity.getAwayTeam() + "," + entity.getStartTime()
                + "," + entity.getSaleStatus();
    }

    private boolean validText(String value) {
        return value != null && !value.trim().isEmpty() && !value.contains(",")
                && !value.contains("\"") && !value.contains("\n") && !value.contains("\r");
    }
}
