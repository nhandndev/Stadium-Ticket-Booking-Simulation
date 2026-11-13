package booking.dto;

import booking.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public BookingResponseDto(Long bookingId, Long fanId, Long matchId,
                              List<Long> seatIds, BigDecimal totalAmount,
                              BookingStatus status, String fanName, String matchName,
                              List<String> seatLabels) {
        this(bookingId, fanId, matchId, seatIds, totalAmount, status,
                fanName, matchName, seatLabels, null, null);
    }

    public BookingResponseDto(Long bookingId, Long fanId, Long matchId,
                              List<Long> seatIds, BigDecimal totalAmount,
                              BookingStatus status, String fanName, String matchName,
                              List<String> seatLabels, LocalDateTime createdAt,
                              LocalDateTime expiresAt) {
        this.bookingId = bookingId;
        this.fanId = fanId;
        this.matchId = matchId;
        this.seatIds = seatIds == null ? null : new ArrayList<>(seatIds);
        this.totalAmount = totalAmount;
        this.status = status;
        this.fanName = fanName;
        this.matchName = matchName;
        this.seatLabels = new ArrayList<>(seatLabels);
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Long getBookingId() { return this.bookingId; }
    public Long getFanId() { return this.fanId; }
    public Long getMatchId() { return this.matchId; }
    public List<Long> getSeatIds() {
        return this.seatIds == null ? null : new ArrayList<>(this.seatIds);
    }
    public BigDecimal getTotalAmount() { return this.totalAmount; }
    public BookingStatus getStatus() { return this.status; }
    public String getFanName() { return this.fanName; }
    public String getMatchName() { return this.matchName; }
    public List<String> getSeatLabels() { return new ArrayList<>(this.seatLabels); }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getExpiresAt() { return this.expiresAt; }
}
