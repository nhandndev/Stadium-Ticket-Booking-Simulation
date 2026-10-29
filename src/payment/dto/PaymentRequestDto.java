package payment.dto;

import java.math.BigDecimal;

public class PaymentRequestDto {
    private Long bookingId;
    private BigDecimal amount;

    public PaymentRequestDto(Long bookingId, BigDecimal amount) {
        this.bookingId = bookingId;
        this.amount = amount;
    }

    public Long getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
}
