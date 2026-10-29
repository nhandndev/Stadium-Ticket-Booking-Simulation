package booking.model;

import booking.enums.BookingStatus;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Booking extends BaseEntity {
    private Long fanId;
    private Long matchId;
    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private BookingStatus status;

    public Booking(Long id, Long fanId, Long matchId, List<Long> seatIds,
                   BigDecimal totalAmount, BookingStatus status) {
        super(id);
        if (fanId == null || fanId <= 0 || matchId == null || matchId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan and match IDs must be positive");
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
        validateTicketLimit();
    }

    public void validateTicketLimit() {
        if (seatIds.isEmpty() || seatIds.size() > 4) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Choose 1 to 4 seats");
        }
        for (Long seatId : seatIds) {
            if (seatId == null || seatId <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat ID must be positive");
            }
        }
        if (new HashSet<>(seatIds).size() != seatIds.size()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Duplicate seat ID");
        }
    }

    public void confirm() {
        if (status != BookingStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Only pending booking can be confirmed");
        }
        status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == BookingStatus.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking already cancelled");
        }
        status = BookingStatus.CANCELLED;
    }

    public Long getFanId() { return fanId; }
    public Long getMatchId() { return matchId; }
    public List<Long> getSeatIds() { return new ArrayList<>(seatIds); }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }
}
