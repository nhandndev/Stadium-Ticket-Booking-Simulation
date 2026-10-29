package payment.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;

public class MockPaymentService {
    private boolean successMode;

    public MockPaymentService(boolean successMode) {
        this.successMode = successMode;
    }

    public boolean processPayment(Long bookingId, BigDecimal amount) {
        if (bookingId == null || bookingId <= 0 || amount == null
                || amount.signum() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Valid booking ID and positive amount are required");
        }
        return successMode;
    }
}
