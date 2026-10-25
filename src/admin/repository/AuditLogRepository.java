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
        if (p.length != 7) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid audit log row");
        }
        long id = Long.parseLong(p[0]);
        long actorId = Long.parseLong(p[1]);
        long targetId = Long.parseLong(p[4]);
        LocalDateTime createdAt = LocalDateTime.parse(p[6]);
        return new AuditLog(id, actorId, p[2], p[3], targetId, p[5], createdAt);
    }

    @Override
    public String formatLine(AuditLog log) {
        return log.getId() + "," + log.getActorId() + "," + log.getAction()
                + "," + log.getTargetType() + "," + log.getTargetId() + ","
                + log.getResult() + "," + log.getCreatedAt();
    }
}
