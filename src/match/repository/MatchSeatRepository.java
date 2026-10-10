package match.repository;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        requirePositive(matchId);
        List<MatchSeat> result = new ArrayList<>();
        for (MatchSeat seat : readAll()) {
            if (seat.getMatchId().equals(matchId)) {
                result.add(seat);
            }
        }
        return result;
    }

    public MatchSeat find(Long matchId, Long seatId) {
        requirePositive(matchId);
        requirePositive(seatId);
        for (MatchSeat seat : readAll()) {
            if (seat.getMatchId().equals(matchId) && seat.getSeatId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public MatchSeat save(MatchSeat seat) {
        if (seat == null || seat.getMatchId() == null || seat.getSeatId() == null
                || seat.getMatchId() <= 0 || seat.getSeatId() <= 0
                || seat.getStatus() == null || seat.getVersion() < 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid match seat");
        }
        List<MatchSeat> seats = readAll();
        long maxId = 0;
        for (MatchSeat current : seats) {
            maxId = Math.max(maxId, current.getId());
            if (current.getMatchId().equals(seat.getMatchId())
                    && current.getSeatId().equals(seat.getSeatId())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat already exists for match");
            }
            if (seat.getId() != null && current.getId().equals(seat.getId())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "ID already exists");
            }
        }
        Long originalId = seat.getId();
        if (originalId == null) {
            if (maxId == Long.MAX_VALUE) {
                throw new AppException(ErrorCode.INVALID_INPUT, "ID limit reached");
            }
            seat.setId(maxId + 1);
        } else {
            requirePositive(originalId);
        }
        seats.add(seat);
        try {
            writeAll(seats);
        } catch (AppException e) {
            seat.setId(originalId);
            throw e;
        }
        return seat;
    }

    public MatchSeat parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 5 match-seat columns");
        }
        long id = Long.parseLong(parts[0]);
        long matchId = Long.parseLong(parts[1]);
        long seatId = Long.parseLong(parts[2]);
        SeatStatus status = SeatStatus.valueOf(parts[3]);
        int version = Integer.parseInt(parts[4]);
        if (id <= 0 || matchId <= 0 || seatId <= 0 || version < 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid match-seat data");
        }
        return new MatchSeat(id, matchId, seatId, status, version);
    }

    public String formatLine(MatchSeat entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || entity.getMatchId() == null || entity.getMatchId() <= 0
                || entity.getSeatId() == null || entity.getSeatId() <= 0
                || entity.getStatus() == null || entity.getVersion() < 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid match-seat CSV data");
        }
        return entity.getId() + "," + entity.getMatchId() + "," + entity.getSeatId()
                + "," + entity.getStatus() + "," + entity.getVersion();
    }

    private void requirePositive(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "ID must be positive");
        }
    }

    private List<MatchSeat> readAll() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            List<MatchSeat> result = new ArrayList<>();
            Set<Long> ids = new HashSet<>();
            Set<String> pairs = new HashSet<>();
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().isEmpty()) {
                    continue;
                }
                try {
                    MatchSeat seat = parseLine(lines.get(i));
                    String pair = seat.getMatchId() + ":" + seat.getSeatId();
                    if (!ids.add(seat.getId()) || !pairs.add(pair)) {
                        throw new AppException(ErrorCode.CSV_ERROR, "Duplicate ID or match/seat");
                    }
                    result.add(seat);
                } catch (AppException | IllegalArgumentException e) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            filePath.getFileName() + " line " + (i + 1) + ": " + e.getMessage());
                }
            }
            return result;
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot read " + filePath);
        }
    }

    private void writeAll(List<MatchSeat> seats) {
        List<String> lines = new ArrayList<>();
        for (MatchSeat seat : seats) {
            lines.add(formatLine(seat));
        }
        Path temporaryFile = null;
        try {
            temporaryFile = Files.createTempFile(filePath.getParent(), "match-seats-", ".tmp");
            Files.write(temporaryFile, lines, StandardCharsets.UTF_8);
            try {
                Files.move(temporaryFile, filePath,
                        StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporaryFile, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException e) {
                    // Do not hide the original write error.
                }
            }
        }
    }
}
