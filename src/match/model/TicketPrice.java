package match.model;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;

public class TicketPrice {
    private Long matchId;
    private Long sectionId;
    private BigDecimal amount;

    public TicketPrice(Long matchId, Long sectionId, BigDecimal amount) {
        this.matchId = matchId;
        this.sectionId = sectionId;
        this.updatePrice(amount);
    }

    public void updatePrice(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Price must be positive");
        }
        this.amount = amount;
    }

    public Long getMatchId() {
        return this.matchId;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
