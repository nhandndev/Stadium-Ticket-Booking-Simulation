package user.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.nio.file.Path;
import java.util.List;
import user.model.Staff;
import user.enums.UserRole;
import user.enums.UserStatus;

public class StaffRepository extends CsvRepository<Staff> {
    public StaffRepository(Path filePath) {
        super(filePath);
    }

    public Staff findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email is required");
        }
        String wanted = email.trim().toLowerCase();
        List<Staff> found = findByCondition(
                staff -> staff.getEmail().toLowerCase().equals(wanted));
        if (found.size() > 1) {
            throw new AppException(ErrorCode.CSV_ERROR, "Duplicate staff email in CSV");
        }
        return found.isEmpty() ? null : found.get(0);
    }

    public List<Staff> search(String keyword) {
        String wanted = keyword == null ? "" : keyword.trim().toLowerCase();
        return findByCondition(staff -> staff.getFullName().toLowerCase().contains(wanted)
                || staff.getEmail().toLowerCase().contains(wanted));
    }

    @Override
    public Staff parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 7 || !valid(parts[1]) || !valid(parts[2])
                || !valid(parts[3]) || !valid(parts[4])) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid staff row");
        }
        long id = Long.parseLong(parts[0]);
        UserRole role = UserRole.valueOf(parts[5]);
        UserStatus status = UserStatus.valueOf(parts[6]);
        if (id <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid staff ID");
        }
        return new Staff(id, parts[1], parts[2], parts[3], parts[4], role, status);
    }

    @Override
    public String formatLine(Staff entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || !valid(entity.getFullName()) || !valid(entity.getEmail())
                || !valid(entity.getPasswordHash()) || !valid(entity.getPhone())
                || entity.getRole() == null || entity.getRole() == UserRole.FAN
                || entity.getStatus() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid staff CSV data");
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
