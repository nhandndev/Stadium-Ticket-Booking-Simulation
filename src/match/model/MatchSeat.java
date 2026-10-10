package match.model;

import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;

public class MatchSeat extends BaseEntity {
    private Long matchId;
    private Long seatId;
    private SeatStatus status;
    private int version;

    public MatchSeat(Long id, Long matchId, Long seatId, SeatStatus status, int version) {
        super(id);
        this.matchId = matchId;
        this.seatId = seatId;
        this.status = status;
        this.version = version;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public void lock() {
        if (!isAvailable()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat is not available");
        }
        status = SeatStatus.LOCKED;
        version++;
    }

    public void markBooked() {
        if (status != SeatStatus.LOCKED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat must be locked first");
        }
        status = SeatStatus.BOOKED;
        version++;
    }

    public void release() {
        if (status == SeatStatus.AVAILABLE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seat is already available");
        }
        status = SeatStatus.AVAILABLE;
        version++;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public int getVersion() {
        return version;
    }
}
