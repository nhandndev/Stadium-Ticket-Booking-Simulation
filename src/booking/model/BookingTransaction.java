package booking.model;

import booking.enums.TransactionStatus;
import booking.enums.SyncMechanism;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookingTransaction extends BaseEntity {
    private Long simulationId;
    private Long bookingId;
    private Long fanId;
    private Long matchId;
    private List<Long> seatIds;
    private SyncMechanism mechanism;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private long durationMillis;
    private String failureReason;

    public BookingTransaction(Long id, Long fanId, Long matchId,
                              List<Long> seatIds, SyncMechanism mechanism) {
        this(id, fanId, matchId, seatIds, mechanism, LocalDateTime.now());
    }

    public BookingTransaction(Long id, Long fanId, Long matchId,
                              List<Long> seatIds, SyncMechanism mechanism,
                              LocalDateTime createdAt) {
        super(id);
        if (fanId == null || fanId <= 0 || matchId == null || matchId <= 0
                || seatIds == null || seatIds.isEmpty() || mechanism == null
                || createdAt == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid booking transaction");
        }
        for (Long seatId : seatIds) {
            if (seatId == null || seatId <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat ID must be positive");
            }
        }
        if (new HashSet<>(seatIds).size() != seatIds.size()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Duplicate transaction seat");
        }
        this.fanId = fanId;
        this.matchId = matchId;
        this.seatIds = new ArrayList<>(seatIds);
        this.mechanism = mechanism;
        this.createdAt = createdAt;
    }

    public void attachBooking(Long bookingId) {
        if (bookingId == null || bookingId <= 0 || this.bookingId != null
                || this.simulationId != null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid booking link");
        }
        this.bookingId = bookingId;
    }

    public void attachSimulation(Long simulationId) {
        if (simulationId == null || simulationId <= 0 || this.simulationId != null
                || this.bookingId != null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid simulation link");
        }
        this.simulationId = simulationId;
    }

    public void markSuccessful(LocalDateTime completedAt) {
        validateCompletion(completedAt);
        this.completedAt = completedAt;
        this.status = TransactionStatus.SUCCESS;
        this.durationMillis = calculateDuration();
    }

    public void markFailed(String reason, LocalDateTime completedAt) {
        markUnsuccessful(TransactionStatus.FAILED, reason, completedAt);
    }

    public void markConflict(String reason, LocalDateTime completedAt) {
        markUnsuccessful(TransactionStatus.CONFLICT, reason, completedAt);
    }

    private void markUnsuccessful(TransactionStatus result, String reason,
                                  LocalDateTime completedAt) {
        validateCompletion(completedAt);
        if (reason == null || reason.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Failure reason is required");
        }
        this.completedAt = completedAt;
        this.failureReason = reason;
        this.status = result;
        this.durationMillis = calculateDuration();
    }

    private void validateCompletion(LocalDateTime time) {
        if (status != null || time == null || time.isBefore(createdAt)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid transaction completion");
        }
    }

    public long calculateDuration() {
        if (completedAt == null) return 0L;
        return Duration.between(createdAt, completedAt).toMillis();
    }

    public Long getSimulationId() { return simulationId; }
    public Long getBookingId() { return bookingId; }
    public Long getFanId() { return fanId; }
    public Long getMatchId() { return matchId; }
    public List<Long> getSeatIds() { return new ArrayList<>(seatIds); }
    public SyncMechanism getMechanism() { return mechanism; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public long getDurationMillis() { return durationMillis; }
    public String getFailureReason() { return failureReason; }
}
