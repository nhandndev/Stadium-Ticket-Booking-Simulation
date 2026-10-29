package refund.model;

import refund.enums.RefundStatus;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.time.LocalDateTime;

public class RefundRequest extends BaseEntity {
    private Long bookingId;
    private Long fanId;
    private String reason;
    private RefundStatus status;
    private LocalDateTime requestedAt;
    private Long reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewNote;

    public RefundRequest(Long id, Long bookingId, Long fanId,
                         String reason, LocalDateTime requestedAt) {
        super(id);
        if (bookingId == null || bookingId <= 0 || fanId == null || fanId <= 0
                || reason == null || reason.trim().isEmpty() || requestedAt == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid refund request");
        }
        this.bookingId = bookingId;
        this.fanId = fanId;
        this.reason = reason;
        this.requestedAt = requestedAt;
        this.status = RefundStatus.REQUESTED;
    }

    public void approve(Long adminId, String note, LocalDateTime time) {
        review(adminId, note, time, RefundStatus.APPROVED);
    }

    public void reject(Long adminId, String note, LocalDateTime time) {
        review(adminId, note, time, RefundStatus.REJECTED);
    }

    private void review(Long adminId, String note, LocalDateTime time,
                        RefundStatus decision) {
        if (status != RefundStatus.REQUESTED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Refund request already reviewed");
        }
        if (adminId == null || adminId <= 0 || note == null || note.trim().isEmpty()
                || time == null || time.isBefore(requestedAt)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid refund review");
        }
        reviewedBy = adminId;
        reviewedAt = time;
        reviewNote = note;
        status = decision;
    }

    public Long getBookingId() { return bookingId; }
    public Long getFanId() { return fanId; }
    public String getReason() { return reason; }
    public RefundStatus getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public Long getReviewedBy() { return reviewedBy; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public String getReviewNote() { return reviewNote; }
}
