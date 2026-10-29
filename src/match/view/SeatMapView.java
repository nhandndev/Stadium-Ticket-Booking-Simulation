package match.view;

import match.dto.MatchSeatResponseDto;
import java.util.List;

public class SeatMapView {
    public SeatMapView() {
    }

    public void displaySeatMap(List<MatchSeatResponseDto> seats) {
        if (seats == null || seats.isEmpty()) {
            System.out.println("\n[!] No seats have been prepared for this match.");
            return;
        }
        System.out.println("\n=== SEAT AVAILABILITY ===");
        System.out.printf("%-8s | %-8s | %-24s | %-12s | %-12s%n",
                "SEAT ID", "LABEL", "SECTION", "PRICE (VND)", "STATUS");
        for (MatchSeatResponseDto seat : seats) {
            System.out.printf("%-8d | %-8s | %-24s | %-12s | %-12s%n",
                    seat.getSeatId(), seat.getSeatLabel(), seat.getSectionName(),
                    seat.getPrice() == null ? "Not set" : seat.getPrice().toPlainString(),
                    seat.isActive() ? seat.getStatus() : "INACTIVE");
        }
    }
}
