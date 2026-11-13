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
        return this.active && this.status == SeatStatus.AVAILABLE;
    }

    public Long getId() {
        return this.id;
    }

    public Long getMatchId() {
        return this.matchId;
    }

    public Long getSeatId() {
        return this.seatId;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public SeatStatus getStatus() {
        return this.status;
    }

    public int getVersion() {
        return this.version;
    }

    public String getSectionName() { return this.sectionName; }
    public String getSeatLabel() { return this.seatLabel; }
    public BigDecimal getPrice() { return this.price; }
    public boolean isActive() { return this.active; }
}
