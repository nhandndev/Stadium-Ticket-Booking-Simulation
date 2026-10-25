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
        List<Fan> found = this.findByCondition(
                fan -> fan.getEmail().toLowerCase().equals(wanted));
        if (found.size() > 1) {
            throw new AppException(ErrorCode.CSV_ERROR, "Duplicate fan email in CSV");
        }
        return found.isEmpty() ? null : found.get(0);
    }

    public List<Fan> search(String keyword) {
        String wanted = keyword == null ? "" : keyword.trim().toLowerCase();
        return this.findByCondition(fan -> fan.getFullName().toLowerCase().contains(wanted)
                || fan.getEmail().toLowerCase().contains(wanted));
    }

    @Override
    public Fan parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 7) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid fan row");
        }
        long id = Long.parseLong(parts[0]);
        UserStatus status = UserStatus.valueOf(parts[6]);
        return new Fan(id, parts[1], parts[2], parts[3], parts[4], status);
    }

    @Override
    public String formatLine(Fan entity) {
        return entity.getId() + "," + entity.getFullName() + "," + entity.getEmail()
                + "," + entity.getPasswordHash() + "," + entity.getPhone()
                + "," + entity.getRole() + "," + entity.getStatus();
    }
}
