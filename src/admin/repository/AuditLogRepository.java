package admin.repository;

import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuditLogRepository extends CsvRepository<AuditLog> {
    public AuditLogRepository(Path filePath) {
        super(filePath);
    }

    public void append(AuditLog log) {
        save(log);
    }

    public List<AuditLog> findRecent(int limit) {
        if (limit <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Limit must be positive");
        }
        List<AuditLog> logs = new ArrayList<>(findAll());
        logs.sort(Comparator.comparing(AuditLog::getCreatedAt).reversed()
                .thenComparing(AuditLog::getId, Comparator.reverseOrder()));
        return new ArrayList<>(logs.subList(0, Math.min(limit, logs.size())));
    }

    @Override
    public AuditLog parseLine(String line) {
        String[] p = line.split(",", -1);
        if (p.length != 7 || !valid(p[2]) || !valid(p[3]) || !valid(p[5])) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid audit log row");
        }
        long id = Long.parseLong(p[0]);
        long actorId = Long.parseLong(p[1]);
        long targetId = Long.parseLong(p[4]);
        LocalDateTime createdAt = LocalDateTime.parse(p[6]);
        if (id <= 0 || actorId <= 0 || targetId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid audit log IDs");
        }
        return new AuditLog(id, actorId, p[2], p[3], targetId, p[5], createdAt);
    }

    @Override
    public String formatLine(AuditLog log) {
        if (log == null || log.getId() == null || log.getId() <= 0
                || log.getActorId() == null || log.getActorId() <= 0
                || log.getTargetId() == null || log.getTargetId() <= 0
                || !valid(log.getAction()) || !valid(log.getTargetType())
                || !valid(log.getResult()) || log.getCreatedAt() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid audit log");
        }
        return log.getId() + "," + log.getActorId() + "," + log.getAction()
                + "," + log.getTargetType() + "," + log.getTargetId() + ","
                + log.getResult() + "," + log.getCreatedAt();
    }

    private boolean valid(String text) {
        return text != null && !text.trim().isEmpty() && !text.contains(",")
                && !text.contains("\"") && !text.contains("\n") && !text.contains("\r");
    }
}
