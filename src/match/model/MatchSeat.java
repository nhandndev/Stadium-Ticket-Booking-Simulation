package match.model;

import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.enums.SeatStatus;

public class MatchSeat extends BaseEntity {
    private Long matchId;
    private Long seatId;
    private SeatStatus status;
    private int version;
    private Long holdBookingId;

    public MatchSeat(Long id, Long matchId, Long seatId, SeatStatus status, int version) {
        this(id, matchId, seatId, status, version, null);
    }

    public MatchSeat(Long id, Long matchId, Long seatId, SeatStatus status, int version,
                     Long holdBookingId) {
        super(id);
        if (holdBookingId != null && (holdBookingId <= 0 || status != SeatStatus.LOCKED)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid seat hold owner");
        }
        this.matchId = matchId;
        this.seatId = seatId;
        this.status = status;
        this.version = version;
        this.holdBookingId = holdBookingId;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }

    public void lock() {
        if (!this.isAvailable()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat is not available");
        }
        this.status = SeatStatus.LOCKED;
        this.version++;
    }

    public void hold(Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking ID is required for a seat hold");
        }
        this.lock();
        this.holdBookingId = bookingId;
    }

    public void markBooked() {
        if (this.status != SeatStatus.LOCKED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat must be locked first");
        }
        this.status = SeatStatus.BOOKED;
        this.holdBookingId = null;
        this.version++;
    }

    public void release() {
        if (this.status == SeatStatus.AVAILABLE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat is already available");
        }
        this.status = SeatStatus.AVAILABLE;
        this.holdBookingId = null;
        this.version++;
    }

    public Long getMatchId() {
        return this.matchId;
    }

    public Long getSeatId() {
        return this.seatId;
    }

    public SeatStatus getStatus() {
        return this.status;
    }

    public int getVersion() {
        return this.version;
    }
    public Long getHoldBookingId() { return this.holdBookingId; }
}
