package match.repository;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import match.model.MatchSeat;
import match.model.SeatStatus;

public class MatchSeatRepository {
    private final Path filePath;

    public MatchSeatRepository(Path filePath) {
        if (filePath == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "File path is required");
        }
        this.filePath = filePath.toAbsolutePath().normalize();
    }

    public List<MatchSeat> findByMatchId(Long matchId) {
        List<MatchSeat> result = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
                if (!line.trim().isEmpty()) {
                    MatchSeat seat = parseLine(line);
                    if (matchId == null || seat.getMatchId().equals(matchId)) {
                        result.add(seat);
                    }
                }
            }
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot read " + filePath);
        }
        return result;
    }

    public MatchSeat find(Long matchId, Long seatId) {
        if (matchId == null || seatId == null) {
            return null;
        }
        for (MatchSeat seat : findByMatchId(matchId)) {
            if (seat.getSeatId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public MatchSeat save(MatchSeat seat) {
        if (seat == null || seat.getMatchId() == null || seat.getSeatId() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid match seat");
        }
        List<MatchSeat> seats = findByMatchId(null);
        long maxId = 0;
        for (MatchSeat current : seats) {
            maxId = Math.max(maxId, current.getId());
            if (current.getMatchId().equals(seat.getMatchId())
                    && current.getSeatId().equals(seat.getSeatId())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat already exists for match");
            }
        }
        if (seat.getId() == null) {
            seat.setId(maxId + 1);
        }
        seats.add(seat);
        try {
            List<String> lines = new ArrayList<>();
            for (MatchSeat s : seats) lines.add(formatLine(s));
            if (filePath.getParent() != null) Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
        }
        return seat;
    }

    public boolean deleteByMatchId(Long matchId) {
        if (matchId == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match ID is required");
        }
        List<MatchSeat> seats = findByMatchId(null);
        boolean removed = seats.removeIf(seat -> seat.getMatchId().equals(matchId));
        if (removed) {
            try {
                List<String> lines = new ArrayList<>();
                for (MatchSeat s : seats) lines.add(formatLine(s));
                if (filePath.getParent() != null) Files.createDirectories(filePath.getParent());
                Files.write(filePath, lines, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
            }
        }
        return removed;
    }

    public MatchSeat parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 5 match-seat columns");
        }
        try {
            long id = Long.parseLong(parts[0].trim());
            long matchId = Long.parseLong(parts[1].trim());
            long seatId = Long.parseLong(parts[2].trim());
            SeatStatus status = SeatStatus.valueOf(parts[3].trim());
            int version = Integer.parseInt(parts[4].trim());
            return new MatchSeat(id, matchId, seatId, status, version);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid match-seat data: " + line);
        }
    }

    public String formatLine(MatchSeat entity) {
        if (entity == null || entity.getId() == null || entity.getMatchId() == null
                || entity.getSeatId() == null || entity.getStatus() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid match-seat CSV data");
        }
        return entity.getId() + "," + entity.getMatchId() + "," + entity.getSeatId()
                + "," + entity.getStatus() + "," + entity.getVersion();
    }
}
