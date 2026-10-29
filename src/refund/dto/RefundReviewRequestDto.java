package refund.dto;

public class RefundReviewRequestDto {
    private Long adminId;
    private Long requestId;
    private boolean approve;
    private String note;

    public RefundReviewRequestDto(Long adminId, Long requestId,
                                  boolean approve, String note) {
        this.adminId = adminId;
        this.requestId = requestId;
        this.approve = approve;
        this.note = note;
    }

    public Long getAdminId() { return adminId; }
    public Long getRequestId() { return requestId; }
    public boolean isApprove() { return approve; }
    public String getNote() { return note; }
}
