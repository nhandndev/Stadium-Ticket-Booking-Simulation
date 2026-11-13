package booking.model;

import booking.enums.BookingStatus;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Booking extends BaseEntity {
    private Long fanId;
    private Long matchId;
    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Booking(Long id, Long fanId, Long matchId, List<Long> seatIds,
                   BigDecimal totalAmount, BookingStatus status,
                   LocalDateTime createdAt, LocalDateTime expiresAt) {
        super(id);
        if ((fanId != null && fanId <= 0) || matchId == null || matchId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid Fan or Match ID");
        }
        if (seatIds == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seats are required");
        }
        if (totalAmount == null || totalAmount.signum() < 0 || status == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid amount or booking status");
        }
        this.fanId = fanId;
        this.matchId = matchId;
        this.seatIds = new ArrayList<>(seatIds);
        this.totalAmount = totalAmount;
        this.status = status;
        if ((createdAt == null) != (expiresAt == null)
                || (createdAt != null && (!expiresAt.isAfter(createdAt)
                || Duration.between(createdAt, expiresAt).compareTo(Duration.ofMinutes(30)) > 0))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid booking payment deadline");
        }
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.validateTicketLimit();
    }

    public void validateTicketLimit() {
        if (this.seatIds.isEmpty() || this.seatIds.size() > 4) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Choose 1 to 4 seats");
        }
        for (Long seatId : this.seatIds) {
            if (seatId == null || seatId <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat ID must be positive");
            }
        }
        if (new HashSet<>(this.seatIds).size() != this.seatIds.size()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Duplicate seat ID");
        }
    }

    public void confirm() {
        if (this.status != BookingStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Only pending booking can be confirmed");
        }
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == BookingStatus.CANCELLED || this.status == BookingStatus.EXPIRED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking is already cancelled or expired");
        }
        this.status = BookingStatus.CANCELLED;
    }

    public boolean isExpired(LocalDateTime now) {
        if (now == null) throw new AppException(ErrorCode.INVALID_INPUT, "Current time is required");
        return this.status == BookingStatus.PENDING
                && (this.expiresAt == null || !now.isBefore(this.expiresAt));
    }

    public void expire() {
        if (this.status != BookingStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Only a pending booking can expire");
        }
        this.status = BookingStatus.EXPIRED;
    }

    public Long getFanId() { return this.fanId; }
    public Long getMatchId() { return this.matchId; }
    public List<Long> getSeatIds() { return new ArrayList<>(this.seatIds); }
    public BigDecimal getTotalAmount() { return this.totalAmount; }
    public BookingStatus getStatus() { return this.status; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getExpiresAt() { return this.expiresAt; }
}
