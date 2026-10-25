package admin.model;

import common.entity.BaseEntity;
import java.time.LocalDateTime;

public class AuditLog extends BaseEntity {
    private final Long actorId;
    private final String action;
    private final String targetType;
    private final Long targetId;
    private final String result;
    private final LocalDateTime createdAt;

    public AuditLog(Long id, Long actorId, String action, String targetType,
                    Long targetId, String result, LocalDateTime createdAt) {
        super(id);
        this.actorId = actorId;
        this.action = action;
        this.targetType = targetType;
        this.targetId = targetId;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getActorId() { return actorId; }
    public String getAction() { return action; }
    public String getTargetType() { return targetType; }
    public Long getTargetId() { return targetId; }
    public String getResult() { return result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
