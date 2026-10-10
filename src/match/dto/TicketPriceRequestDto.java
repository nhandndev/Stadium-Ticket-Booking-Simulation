package match.dto;

import java.math.BigDecimal;

public class TicketPriceRequestDto {
    private Long matchId;
    private Long sectionId;
    private BigDecimal amount;

    public TicketPriceRequestDto(Long matchId, Long sectionId, BigDecimal amount) {
        this.matchId = matchId;
        this.sectionId = sectionId;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getSectionId() {
        return sectionId;
    }
}
