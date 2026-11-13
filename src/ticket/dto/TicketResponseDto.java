package ticket.dto;

import ticket.enums.TicketStatus;
import java.time.LocalDateTime;

public class TicketResponseDto {
    private Long ticketId;
    private Long bookingId;
    private Long matchId;
    private Long seatId;
    private String ticketCode;
    private TicketStatus status;
    private LocalDateTime checkedInAt;
    private String matchName;
    private String seatLabel;

    private String customerName;
    private String customerPhone;

    public TicketResponseDto(Long ticketId, Long bookingId, Long matchId,
                             Long seatId, String ticketCode, TicketStatus status,
                             LocalDateTime checkedInAt, String matchName, String seatLabel,
                             String customerName, String customerPhone) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.matchId = matchId;
        this.seatId = seatId;
        this.ticketCode = ticketCode;
        this.status = status;
        this.checkedInAt = checkedInAt;
        this.matchName = matchName;
        this.seatLabel = seatLabel;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public Long getTicketId() { return this.ticketId; }
    public Long getBookingId() { return this.bookingId; }
    public Long getMatchId() { return this.matchId; }
    public Long getSeatId() { return this.seatId; }
    public String getTicketCode() { return this.ticketCode; }
    public TicketStatus getStatus() { return this.status; }
    public LocalDateTime getCheckedInAt() { return this.checkedInAt; }
    public String getMatchName() { return this.matchName; }
    public String getSeatLabel() { return this.seatLabel; }
    public String getCustomerName() { return this.customerName; }
    public String getCustomerPhone() { return this.customerPhone; }
}
