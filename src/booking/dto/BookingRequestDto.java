package booking.dto;

import java.util.ArrayList;
import java.util.List;

public class BookingRequestDto {
    private Long fanId;
    private Long matchId;
    private List<Long> seatIds;

    public BookingRequestDto(Long fanId, Long matchId, List<Long> seatIds) {
        this.fanId = fanId;
        this.matchId = matchId;
        this.seatIds = seatIds == null ? null : new ArrayList<>(seatIds);
    }

    public Long getFanId() { return this.fanId; }
    public Long getMatchId() { return this.matchId; }
    public List<Long> getSeatIds() {
        return this.seatIds == null ? null : new ArrayList<>(this.seatIds);
    }
}
