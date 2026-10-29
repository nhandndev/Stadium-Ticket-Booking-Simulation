package booking.dto;

import booking.enums.BookingStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookingResponseDto {
    private Long bookingId;
    private Long fanId;
    private Long matchId;
    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private String fanName;
    private String matchName;
    private List<String> seatLabels;

    public BookingResponseDto(Long bookingId, Long fanId, Long matchId,
                              List<Long> seatIds, BigDecimal totalAmount,
                              BookingStatus status, String fanName, String matchName,
                              List<String> seatLabels) {
        this.bookingId = bookingId;
        this.fanId = fanId;
        this.matchId = matchId;
        this.seatIds = seatIds == null ? null : new ArrayList<>(seatIds);
        this.totalAmount = totalAmount;
        this.status = status;
        this.fanName = fanName;
        this.matchName = matchName;
        this.seatLabels = new ArrayList<>(seatLabels);
    }

    public Long getBookingId() { return bookingId; }
    public Long getFanId() { return fanId; }
    public Long getMatchId() { return matchId; }
    public List<Long> getSeatIds() {
        return seatIds == null ? null : new ArrayList<>(seatIds);
    }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }
    public String getFanName() { return fanName; }
    public String getMatchName() { return matchName; }
    public List<String> getSeatLabels() { return new ArrayList<>(seatLabels); }
}
