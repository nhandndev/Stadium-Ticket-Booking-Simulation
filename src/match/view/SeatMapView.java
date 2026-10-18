package match.view;

import match.dto.MatchSeatResponseDto;

import java.util.List;

public class SeatMapView {
    public SeatMapView() {
    }

    public void displaySeatMap(List<MatchSeatResponseDto> seats) {
        if (seats.isEmpty()) {
            System.out.println("No seats have been prepared for this match.");
            return;
        }
        System.out.println("Seat ID | Status | Version");
        for (MatchSeatResponseDto seat : seats) {
            System.out.println(seat.getSeatId() + " | " + seat.getStatus()
                    + " | " + seat.getVersion());
        }
    }
}
