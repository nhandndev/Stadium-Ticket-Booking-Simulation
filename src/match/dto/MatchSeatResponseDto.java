package match.dto;

import match.enums.SeatStatus;
import java.math.BigDecimal;

public class MatchSeatResponseDto {
    private Long id;
    private Long matchId;
    private Long seatId;
    private Long sectionId;
    private SeatStatus status;
    private int version;
    private String sectionName;
    private String seatLabel;
    private BigDecimal price;
    private boolean active;

    public MatchSeatResponseDto(Long id, Long matchId, Long seatId, Long sectionId,
            SeatStatus status, int version, String sectionName, String seatLabel,
            BigDecimal price, boolean active) {
        this.id = id;
        this.matchId = matchId;
        this.seatId = seatId;
        this.sectionId = sectionId;
        this.status = status;
        this.version = version;
        this.sectionName = sectionName;
        this.seatLabel = seatLabel;
        this.price = price;
        this.active = active;
    }

    public boolean isAvailable() {
        return active && status == SeatStatus.AVAILABLE;
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

    public Long getSectionId() {
        return sectionId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public int getVersion() {
        return version;
    }

    public String getSectionName() { return sectionName; }
    public String getSeatLabel() { return seatLabel; }
    public BigDecimal getPrice() { return price; }
    public boolean isActive() { return active; }
}
