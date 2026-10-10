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
        updatePrice(amount);
    }

    public void updatePrice(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Price must be positive");
        }
        this.amount = amount;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
