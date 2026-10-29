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

    public TicketResponseDto(Long ticketId, Long bookingId, Long matchId,
                             Long seatId, String ticketCode, TicketStatus status,
                             LocalDateTime checkedInAt, String matchName, String seatLabel) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.matchId = matchId;
        this.seatId = seatId;
        this.ticketCode = ticketCode;
        this.status = status;
        this.checkedInAt = checkedInAt;
        this.matchName = matchName;
        this.seatLabel = seatLabel;
    }

    public Long getTicketId() { return ticketId; }
    public Long getBookingId() { return bookingId; }
    public Long getMatchId() { return matchId; }
    public Long getSeatId() { return seatId; }
    public String getTicketCode() { return ticketCode; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCheckedInAt() { return checkedInAt; }
    public String getMatchName() { return matchName; }
    public String getSeatLabel() { return seatLabel; }
}
