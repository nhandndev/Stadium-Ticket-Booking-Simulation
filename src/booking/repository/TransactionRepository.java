package booking.repository;

import booking.model.BookingTransaction;
import booking.enums.TransactionStatus;
import booking.enums.SyncMechanism;
import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionRepository extends CsvRepository<BookingTransaction> {
    public TransactionRepository(Path filePath) {
        super(filePath);
    }

    @Override
    public BookingTransaction parseLine(String line) {
        String[] parts = line.split(",", -1);
        // Accept booking records copied from the earlier 12-column layout.
        if (parts.length == 12 && parts[1].isEmpty()) {
            List<String> columns = new ArrayList<>(Arrays.asList(parts));
            columns.remove(1);
            parts = columns.toArray(new String[0]);
        }
        if (parts.length != 11) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 11 transaction columns");
        }
        long id = Long.parseLong(parts[0]);
        long bookingId = Long.parseLong(parts[1]);
        long fanId = Long.parseLong(parts[2]);
        long matchId = Long.parseLong(parts[3]);
        if (id <= 0 || bookingId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Transaction needs a valid booking");
        }
        List<Long> seatIds = new ArrayList<>();
        for (String seatText : parts[4].split(";", -1)) {
            seatIds.add(Long.parseLong(seatText));
        }
        SyncMechanism mechanism = SyncMechanism.valueOf(parts[5]);
        TransactionStatus status = TransactionStatus.valueOf(parts[6]);
        try {
            LocalDateTime createdAt = LocalDateTime.parse(parts[7]);
            LocalDateTime completedAt = LocalDateTime.parse(parts[8]);
            long storedDuration = Long.parseLong(parts[9]);
            String failureReason = URLDecoder.decode(parts[10], StandardCharsets.UTF_8);
            BookingTransaction transaction = new BookingTransaction(id, fanId, matchId,
                    seatIds, mechanism, createdAt);
            transaction.attachBooking(bookingId);
            if (status == TransactionStatus.SUCCESS) {
                if (!failureReason.isEmpty()) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            "Successful transaction has failure reason");
                }
                transaction.markSuccessful(completedAt);
            } else if (status == TransactionStatus.FAILED) {
                transaction.markFailed(failureReason, completedAt);
            } else {
                transaction.markConflict(failureReason, completedAt);
            }
            if (storedDuration != transaction.getDurationMillis()) {
                throw new AppException(ErrorCode.CSV_ERROR, "Transaction duration mismatch");
            }
            return transaction;
        } catch (DateTimeParseException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid transaction timestamp");
        }
    }

    @Override
    public String formatLine(BookingTransaction entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || entity.getStatus() == null || entity.getCompletedAt() == null
                || entity.getBookingId() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Only completed booking transactions can be saved");
        }
        String reason = entity.getFailureReason();
        if ((entity.getStatus() == TransactionStatus.SUCCESS && reason != null)
                || (entity.getStatus() != TransactionStatus.SUCCESS
                && (reason == null || reason.trim().isEmpty()))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid failure reason");
        }
        List<String> seatTexts = new ArrayList<>();
        for (Long seatId : entity.getSeatIds()) {
            seatTexts.add(seatId.toString());
        }
        return entity.getId() + ","
                + entity.getBookingId() + ","
                + entity.getFanId() + "," + entity.getMatchId() + ","
                + String.join(";", seatTexts) + "," + entity.getMechanism().name()
                + "," + entity.getStatus().name() + "," + entity.getCreatedAt()
                + "," + entity.getCompletedAt() + "," + entity.getDurationMillis()
                + "," + (reason == null ? ""
                : URLEncoder.encode(reason, StandardCharsets.UTF_8));
    }
}
