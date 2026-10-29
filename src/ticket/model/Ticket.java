package ticket.model;

import ticket.enums.TicketStatus;
import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.time.LocalDateTime;

public class Ticket extends BaseEntity {
    private Long bookingId;
    private Long matchId;
    private Long seatId;
    private String ticketCode;
    private TicketStatus status;
    private LocalDateTime checkedInAt;

    public Ticket(Long id, Long bookingId, Long matchId, Long seatId,
                  String ticketCode, TicketStatus status) {
        super(id);
        if (bookingId == null || bookingId <= 0 || matchId == null || matchId <= 0
                || seatId == null || seatId <= 0 || ticketCode == null
                || ticketCode.trim().isEmpty() || status == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ticket data");
        }
        this.bookingId = bookingId;
        this.matchId = matchId;
        this.seatId = seatId;
        this.ticketCode = ticketCode;
        this.status = status;
    }

    public void issue() {
        if (status != TicketStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Only pending ticket can be issued");
        }
        status = TicketStatus.ISSUED;
    }

    public boolean isValidForMatch(Long matchId) {
        return matchId != null && this.matchId.equals(matchId)
                && status == TicketStatus.ISSUED;
    }

    public void checkIn(LocalDateTime time) {
        if (time == null || status != TicketStatus.ISSUED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Ticket cannot be checked in");
        }
        status = TicketStatus.CHECKED_IN;
        checkedInAt = time;
    }

    public void cancel() {
        if (status != TicketStatus.PENDING && status != TicketStatus.ISSUED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Ticket cannot be cancelled");
        }
        status = TicketStatus.CANCELLED;
    }

    public Long getBookingId() { return bookingId; }
    public Long getMatchId() { return matchId; }
    public Long getSeatId() { return seatId; }
    public String getTicketCode() { return ticketCode; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCheckedInAt() { return checkedInAt; }
}
