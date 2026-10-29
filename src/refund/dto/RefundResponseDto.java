package refund.dto;

import refund.enums.RefundStatus;

public class RefundResponseDto {
    private Long requestId;
    private Long bookingId;
    private Long fanId;
    private RefundStatus status;

    public RefundResponseDto(Long requestId, Long bookingId, Long fanId,
                             RefundStatus status) {
        this.requestId = requestId;
        this.bookingId = bookingId;
        this.fanId = fanId;
        this.status = status;
    }

    public Long getRequestId() { return requestId; }
    public Long getBookingId() { return bookingId; }
    public Long getFanId() { return fanId; }
    public RefundStatus getStatus() { return status; }
}
