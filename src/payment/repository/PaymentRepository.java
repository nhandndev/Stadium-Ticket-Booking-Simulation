package payment.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import payment.model.Payment;
import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class PaymentRepository extends CsvRepository<Payment> {
    public PaymentRepository(Path filePath) {
        super(filePath);
    }

    public Payment findByBookingId(Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking ID must be positive");
        }
        List<Payment> payments = findByCondition(
                payment -> payment.getBookingId().equals(bookingId));
        if (payments.size() > 1) {
            throw new AppException(ErrorCode.CSV_ERROR, "Multiple payments for one booking");
        }
        return payments.isEmpty() ? null : payments.get(0);
    }

    @Override
    public Payment parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 5 payment columns");
        }
        long id = Long.parseLong(parts[0]);
        long bookingId = Long.parseLong(parts[1]);
        if (id <= 0 || bookingId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid payment IDs");
        }
        BigDecimal amount = new BigDecimal(parts[2]);
        PaymentMethod method = PaymentMethod.valueOf(parts[3]);
        PaymentStatus status = PaymentStatus.valueOf(parts[4]);
        return new Payment(id, bookingId, amount, method, status);
    }

    @Override
    public String formatLine(Payment entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment ID is required");
        }
        return entity.getId() + "," + entity.getBookingId() + ","
                + entity.getAmount().toPlainString() + ","
                + entity.getMethod().name() + "," + entity.getStatus().name();
    }
}
