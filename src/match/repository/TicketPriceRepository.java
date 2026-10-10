package match.repository;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import match.model.TicketPrice;

public class TicketPriceRepository {
    private final Path filePath;

    public TicketPriceRepository(Path filePath) {
        if (filePath == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "File path is required");
        }
        this.filePath = filePath.toAbsolutePath().normalize();
    }

    public TicketPrice findPrice(Long matchId, Long sectionId) {
        requirePositive(matchId);
        requirePositive(sectionId);
        for (TicketPrice price : readAll()) {
            if (price.getMatchId().equals(matchId)
                    && price.getSectionId().equals(sectionId)) {
                return price;
            }
        }
        return null;
    }

    public List<TicketPrice> findByMatchId(Long matchId) {
        requirePositive(matchId);
        List<TicketPrice> result = new ArrayList<>();
        for (TicketPrice price : readAll()) {
            if (price.getMatchId().equals(matchId)) {
                result.add(price);
            }
        }
        return result;
    }

    public TicketPrice save(TicketPrice price) {
        formatLine(price);
        List<TicketPrice> prices = readAll();
        for (TicketPrice current : prices) {
            if (current.getMatchId().equals(price.getMatchId())
                    && current.getSectionId().equals(price.getSectionId())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Price already exists");
            }
        }
        prices.add(price);
        writeAll(prices);
        return price;
    }

    public TicketPrice update(TicketPrice price) {
        formatLine(price);
        List<TicketPrice> prices = readAll();
        for (int i = 0; i < prices.size(); i++) {
            TicketPrice current = prices.get(i);
            if (current.getMatchId().equals(price.getMatchId())
                    && current.getSectionId().equals(price.getSectionId())) {
                prices.set(i, price);
                writeAll(prices);
                return price;
            }
        }
        throw new AppException(ErrorCode.NOT_FOUND, "Price does not exist");
    }

    public boolean delete(Long matchId, Long sectionId) {
        requirePositive(matchId);
        requirePositive(sectionId);
        List<TicketPrice> prices = readAll();
        for (int i = 0; i < prices.size(); i++) {
            TicketPrice current = prices.get(i);
            if (current.getMatchId().equals(matchId)
                    && current.getSectionId().equals(sectionId)) {
                prices.remove(i);
                writeAll(prices);
                return true;
            }
        }
        return false;
    }

    public TicketPrice parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 3 price columns");
        }
        long matchId = Long.parseLong(parts[0]);
        long sectionId = Long.parseLong(parts[1]);
        BigDecimal amount = new BigDecimal(parts[2]);
        if (matchId <= 0 || sectionId <= 0 || amount.signum() <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid price data");
        }
        return new TicketPrice(matchId, sectionId, amount);
    }

    public String formatLine(TicketPrice entity) {
        if (entity == null || entity.getMatchId() == null || entity.getMatchId() <= 0
                || entity.getSectionId() == null || entity.getSectionId() <= 0
                || entity.getAmount() == null || entity.getAmount().signum() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ticket price CSV data");
        }
        return entity.getMatchId() + "," + entity.getSectionId() + ","
                + entity.getAmount().toPlainString();
    }

    private void requirePositive(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "ID must be positive");
        }
    }

    private List<TicketPrice> readAll() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            List<TicketPrice> result = new ArrayList<>();
            Set<String> pairs = new HashSet<>();
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().isEmpty()) {
                    continue;
                }
                try {
                    TicketPrice price = parseLine(lines.get(i));
                    String pair = price.getMatchId() + ":" + price.getSectionId();
                    if (!pairs.add(pair)) {
                        throw new AppException(ErrorCode.CSV_ERROR, "Duplicate match/section");
                    }
                    result.add(price);
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

    private void writeAll(List<TicketPrice> prices) {
        List<String> lines = new ArrayList<>();
        for (TicketPrice price : prices) {
            lines.add(formatLine(price));
        }
        Path temporaryFile = null;
        try {
            temporaryFile = Files.createTempFile(filePath.getParent(), "prices-", ".tmp");
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
