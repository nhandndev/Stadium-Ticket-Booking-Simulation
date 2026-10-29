package payment.model;

import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;

public class Payment extends BaseEntity {
    private Long bookingId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(Long id, Long bookingId, BigDecimal amount,
                   PaymentMethod method, PaymentStatus status) {
        super(id);
        if (bookingId == null || bookingId <= 0 || amount == null
                || amount.signum() < 0 || method == null || status == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid payment data");
        }
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public void markSuccess() {
        if (status != PaymentStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment already completed");
        }
        status = PaymentStatus.SUCCESS;
    }

    public void markFailed() {
        if (status != PaymentStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment already completed");
        }
        status = PaymentStatus.FAILED;
    }

    public Long getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentMethod getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
}
