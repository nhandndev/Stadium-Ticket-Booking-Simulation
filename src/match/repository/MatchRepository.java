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
        long id = Long.parseLong(parts[0]);
        long stadiumId = Long.parseLong(parts[1]);
        LocalDateTime startTime = LocalDateTime.parse(parts[4]);
        SaleStatus status = SaleStatus.valueOf(parts[5]);
        return new Match(id, stadiumId, parts[2], parts[3], startTime, status);
    }

    @Override
    public String formatLine(Match entity) {
        return entity.getId() + "," + entity.getStadiumId() + "," + entity.getHomeTeam()
                + "," + entity.getAwayTeam() + "," + entity.getStartTime()
                + "," + entity.getSaleStatus();
    }
}
