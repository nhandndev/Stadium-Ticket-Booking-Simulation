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
import java.util.List;

public class TransactionRepository extends CsvRepository<BookingTransaction> {
    public TransactionRepository(Path filePath) {
        super(filePath);
    }

    public List<BookingTransaction> findBySimulationId(Long simulationId) {
        if (simulationId == null || simulationId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Simulation ID must be positive");
        }
        return findByCondition(transaction -> simulationId.equals(
                transaction.getSimulationId()));
    }

    @Override
    public BookingTransaction parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 12) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 12 transaction columns");
        }
        long id = Long.parseLong(parts[0]);
        Long simulationId = parts[1].isEmpty() ? null : Long.parseLong(parts[1]);
        Long bookingId = parts[2].isEmpty() ? null : Long.parseLong(parts[2]);
        long fanId = Long.parseLong(parts[3]);
        long matchId = Long.parseLong(parts[4]);
        if (id <= 0 || (simulationId == null) == (bookingId == null)) {
            throw new AppException(ErrorCode.CSV_ERROR, "Transaction needs one owner");
        }
        List<Long> seatIds = new ArrayList<>();
        for (String seatText : parts[5].split(";", -1)) {
            seatIds.add(Long.parseLong(seatText));
        }
        SyncMechanism mechanism = SyncMechanism.valueOf(parts[6]);
        TransactionStatus status = TransactionStatus.valueOf(parts[7]);
        try {
            LocalDateTime createdAt = LocalDateTime.parse(parts[8]);
            LocalDateTime completedAt = LocalDateTime.parse(parts[9]);
            long storedDuration = Long.parseLong(parts[10]);
            String failureReason = URLDecoder.decode(parts[11], StandardCharsets.UTF_8);
            BookingTransaction transaction = new BookingTransaction(id, fanId, matchId,
                    seatIds, mechanism, createdAt);
            if (simulationId != null) transaction.attachSimulation(simulationId);
            if (bookingId != null) transaction.attachBooking(bookingId);
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
                || (entity.getSimulationId() == null) == (entity.getBookingId() == null)) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Only completed transaction with one owner can be saved");
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
                + (entity.getSimulationId() == null ? "" : entity.getSimulationId()) + ","
                + (entity.getBookingId() == null ? "" : entity.getBookingId()) + ","
                + entity.getFanId() + "," + entity.getMatchId() + ","
                + String.join(";", seatTexts) + "," + entity.getMechanism().name()
                + "," + entity.getStatus().name() + "," + entity.getCreatedAt()
                + "," + entity.getCompletedAt() + "," + entity.getDurationMillis()
                + "," + (reason == null ? ""
                : URLEncoder.encode(reason, StandardCharsets.UTF_8));
    }
}
