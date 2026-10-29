package payment.dto;

import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;
import java.math.BigDecimal;

public class PaymentResponseDto {
    private Long paymentId;
    private Long bookingId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;

    public PaymentResponseDto(Long paymentId, Long bookingId, BigDecimal amount,
                              PaymentMethod method, PaymentStatus status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public Long getPaymentId() { return paymentId; }
    public Long getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentMethod getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
}
