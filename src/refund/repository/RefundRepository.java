package refund.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import refund.model.RefundRequest;
import refund.enums.RefundStatus;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RefundRepository extends CsvRepository<RefundRequest> {
    public RefundRepository(Path filePath) {
        super(filePath);
    }

    public List<RefundRequest> findByFanId(Long fanId) {
        if (fanId == null || fanId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan ID must be positive");
        }
        return findByCondition(request -> request.getFanId().equals(fanId));
    }

    public List<RefundRequest> findPending() {
        return findByCondition(request -> request.getStatus() == RefundStatus.REQUESTED);
    }

    @Override
    public RefundRequest parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 9) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 9 refund columns");
        }
        long id = Long.parseLong(parts[0]);
        long bookingId = Long.parseLong(parts[1]);
        long fanId = Long.parseLong(parts[2]);
        if (id <= 0 || bookingId <= 0 || fanId <= 0) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid refund IDs");
        }
        String reason = URLDecoder.decode(parts[3], StandardCharsets.UTF_8);
        RefundStatus status = RefundStatus.valueOf(parts[4]);
        try {
            LocalDateTime requestedAt = LocalDateTime.parse(parts[5]);
            RefundRequest request = new RefundRequest(id, bookingId, fanId,
                    reason, requestedAt);
            if (status == RefundStatus.REQUESTED) {
                if (!parts[6].isEmpty() || !parts[7].isEmpty() || !parts[8].isEmpty()) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            "Pending refund has review data");
                }
                return request;
            }
            long adminId = Long.parseLong(parts[6]);
            LocalDateTime reviewedAt = LocalDateTime.parse(parts[7]);
            String note = URLDecoder.decode(parts[8], StandardCharsets.UTF_8);
            if (status == RefundStatus.APPROVED) {
                request.approve(adminId, note, reviewedAt);
            } else {
                request.reject(adminId, note, reviewedAt);
            }
            return request;
        } catch (DateTimeParseException e) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid refund timestamp");
        }
    }

    @Override
    public String formatLine(RefundRequest entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Refund ID is required");
        }
        String reviewFields = ",,";
        if (entity.getStatus() == RefundStatus.REQUESTED) {
            if (entity.getReviewedBy() != null || entity.getReviewedAt() != null
                    || entity.getReviewNote() != null) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Pending refund cannot have review data");
            }
        } else {
            if (entity.getReviewedBy() == null || entity.getReviewedAt() == null
                    || entity.getReviewNote() == null) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Reviewed refund must have reviewer, time and note");
            }
            reviewFields = entity.getReviewedBy() + "," + entity.getReviewedAt()
                    + "," + URLEncoder.encode(entity.getReviewNote(), StandardCharsets.UTF_8);
        }
        return entity.getId() + "," + entity.getBookingId() + ","
                + entity.getFanId() + ","
                + URLEncoder.encode(entity.getReason(), StandardCharsets.UTF_8) + ","
                + entity.getStatus().name() + "," + entity.getRequestedAt()
                + "," + reviewFields;
    }
}
