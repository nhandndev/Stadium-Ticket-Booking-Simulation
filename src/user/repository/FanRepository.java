package user.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.nio.file.Path;
import java.util.List;
import user.model.Fan;
import user.enums.UserRole;
import user.enums.UserStatus;

public class FanRepository extends CsvRepository<Fan> {
    public FanRepository(Path filePath) {
        super(filePath);
    }

    public Fan findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email is required");
        }
        String wanted = email.trim().toLowerCase();
        List<Fan> found = findByCondition(
                fan -> fan.getEmail().toLowerCase().equals(wanted));
        if (found.size() > 1) {
            throw new AppException(ErrorCode.CSV_ERROR, "Duplicate fan email in CSV");
        }
        return found.isEmpty() ? null : found.get(0);
    }

    public List<Fan> search(String keyword) {
        String wanted = keyword == null ? "" : keyword.trim().toLowerCase();
        return findByCondition(fan -> fan.getFullName().toLowerCase().contains(wanted)
                || fan.getEmail().toLowerCase().contains(wanted));
    }

    @Override
    public Fan parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 7 || !valid(parts[1]) || !valid(parts[2])
                || !valid(parts[3]) || !valid(parts[4]) || !parts[5].equals("FAN")) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid fan row");
        }
        long id = Long.parseLong(parts[0]);
        UserStatus status = UserStatus.valueOf(parts[6]);
        if (id <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid fan ID");
        }
        return new Fan(id, parts[1], parts[2], parts[3], parts[4], status);
    }

    @Override
    public String formatLine(Fan entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || !valid(entity.getFullName()) || !valid(entity.getEmail())
                || !valid(entity.getPasswordHash()) || !valid(entity.getPhone())
                || entity.getRole() != UserRole.FAN || entity.getStatus() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid fan CSV data");
        }
        return entity.getId() + "," + entity.getFullName() + "," + entity.getEmail()
                + "," + entity.getPasswordHash() + "," + entity.getPhone()
                + "," + entity.getRole() + "," + entity.getStatus();
    }

    private boolean valid(String value) {
        return value != null && !value.trim().isEmpty() && !value.contains(",")
                && !value.contains("\"") && !value.contains("\n") && !value.contains("\r");
    }
}
