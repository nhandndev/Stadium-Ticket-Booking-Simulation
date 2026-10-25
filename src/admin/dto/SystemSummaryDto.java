package admin.dto;

public class SystemSummaryDto {
    private final int fanCount;
    private final int staffCount;
    private final int stadiumCount;
    private final int matchCount;
    private final int bookingCount;
    private final int ticketCount;

    public SystemSummaryDto(int fanCount, int staffCount, int stadiumCount,
                            int matchCount, int bookingCount, int ticketCount) {
        this.fanCount = fanCount;
        this.staffCount = staffCount;
        this.stadiumCount = stadiumCount;
        this.matchCount = matchCount;
        this.bookingCount = bookingCount;
        this.ticketCount = ticketCount;
    }

    public int getTotalAccounts() {
        return this.fanCount + this.staffCount;
    }

    public int getFanCount() {
        return this.fanCount;
    }

    public int getStaffCount() {
        return this.staffCount;
    }

    public int getStadiumCount() {
        return this.stadiumCount;
    }

    public int getMatchCount() {
        return this.matchCount;
    }

    public int getBookingCount() {
        return this.bookingCount;
    }

    public int getTicketCount() {
        return this.ticketCount;
    }
}
