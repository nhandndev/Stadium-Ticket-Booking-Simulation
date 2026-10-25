package match.repository;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
        for (TicketPrice price : findByMatchId(matchId)) {
            if (price.getSectionId().equals(sectionId)) {
                return price;
            }
        }
        return null;
    }

    public List<TicketPrice> findByMatchId(Long matchId) {
        List<TicketPrice> result = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
                if (!line.trim().isEmpty()) {
                    TicketPrice p = parseLine(line);
                    if (matchId == null || p.getMatchId().equals(matchId)) {
                        result.add(p);
                    }
                }
            }
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot read " + filePath);
        }
        return result;
    }

    public TicketPrice save(TicketPrice price) {
        if (price == null || price.getMatchId() == null || price.getSectionId() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid price");
        }
        List<TicketPrice> all = findByMatchId(null);
        for (TicketPrice p : all) {
            if (p.getMatchId().equals(price.getMatchId()) && p.getSectionId().equals(price.getSectionId())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Price already exists");
            }
        }
        all.add(price);
        try {
            List<String> lines = new ArrayList<>();
            for (TicketPrice p : all) lines.add(formatLine(p));
            if (filePath.getParent() != null) Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
        }
        return price;
    }

    public TicketPrice update(TicketPrice price) {
        if (price == null || price.getMatchId() == null || price.getSectionId() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid price");
        }
        List<TicketPrice> all = findByMatchId(null);
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            TicketPrice p = all.get(i);
            if (p.getMatchId().equals(price.getMatchId()) && p.getSectionId().equals(price.getSectionId())) {
                all.set(i, price);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AppException(ErrorCode.NOT_FOUND, "Price does not exist");
        }
        try {
            List<String> lines = new ArrayList<>();
            for (TicketPrice p : all) lines.add(formatLine(p));
            if (filePath.getParent() != null) Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
        }
        return price;
    }

    public boolean delete(Long matchId, Long sectionId) {
        if (matchId == null || sectionId == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ID");
        }
        List<TicketPrice> all = findByMatchId(null);
        boolean removed = all.removeIf(p -> p.getMatchId().equals(matchId) && p.getSectionId().equals(sectionId));
        if (removed) {
            try {
                List<String> lines = new ArrayList<>();
                for (TicketPrice p : all) lines.add(formatLine(p));
                if (filePath.getParent() != null) Files.createDirectories(filePath.getParent());
                Files.write(filePath, lines, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new AppException(ErrorCode.CSV_ERROR, "Cannot write " + filePath);
            }
        }
        return removed;
    }

    public TicketPrice parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 3 price columns");
        }
        try {
            return new TicketPrice(Long.parseLong(parts[0].trim()),
                    Long.parseLong(parts[1].trim()),
                    new BigDecimal(parts[2].trim()));
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid number in price line: " + line);
        }
    }

    public String formatLine(TicketPrice entity) {
        if (entity == null || entity.getMatchId() == null || entity.getSectionId() == null
                || entity.getAmount() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid price entity");
        }
        return entity.getMatchId() + "," + entity.getSectionId() + "," + entity.getAmount();
    }
}
