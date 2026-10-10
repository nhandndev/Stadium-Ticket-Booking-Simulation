package match.view;

import match.controller.BrowseController;
import match.dto.MatchSeatResponseDto;

import java.util.List;

public class SeatMapView {
    private final BrowseController browseController;
    public SeatMapView(BrowseController browseController) {
        this.browseController = browseController;
    }
    public void displaySeatMap(Long matchId){
        List<MatchSeatResponseDto> seats = browseController.getSeatMap(matchId);
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
