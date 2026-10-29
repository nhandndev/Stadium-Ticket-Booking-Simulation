package refund.dto;

public class RefundRequestDto {
    private Long fanId;
    private Long bookingId;
    private String reason;

    public RefundRequestDto(Long fanId, Long bookingId, String reason) {
        this.fanId = fanId;
        this.bookingId = bookingId;
        this.reason = reason;
    }

    public Long getFanId() { return fanId; }
    public Long getBookingId() { return bookingId; }
    public String getReason() { return reason; }
}
