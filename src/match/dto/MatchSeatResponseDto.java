package match.dto;

import match.model.SeatStatus;

public class MatchSeatResponseDto {
    private Long id;
    private Long matchId;
    private Long seatId;
    private SeatStatus status;
    private int version;

    public MatchSeatResponseDto(Long id, Long matchId, Long seatId,
            SeatStatus status, int version) {
        this.id = id;
        this.matchId = matchId;
        this.seatId = seatId;
        this.status = status;
        this.version = version;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
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
