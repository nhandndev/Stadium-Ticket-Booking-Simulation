package stadium.controller;

import java.util.List;

import stadium.dto.StadiumRequestDto;
import stadium.dto.StadiumResponseDto;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.service.StadiumService;


public class StadiumController {
    private final StadiumService stadiumService;

    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    public StadiumResponseDto createStadium(StadiumRequestDto request) {
        return this.stadiumService.createStadium(request);
    }

    public List<StadiumResponseDto> getStadiums() {
        return this.stadiumService.getStadiums();
    }

    public StadiumResponseDto getStadiumDetails(Long stadiumId) {
        return this.stadiumService.getStadiumDetails(stadiumId);
    }

    public StadiumResponseDto updateStadium(Long stadiumId, StadiumRequestDto request) {
        return this.stadiumService.updateStadium(stadiumId, request);
    }

    public boolean deleteStadium(Long stadiumId) {
        return this.stadiumService.deleteStadium(stadiumId);
    }

    public Section createSection(Long stadiumId, String name) {
        return this.stadiumService.createSection(stadiumId, name);
    }

    public List<Section> getSections(Long stadiumId) {
        return this.stadiumService.getSections(stadiumId);
    }

    public Section getSectionDetails(Long sectionId) {
        return this.stadiumService.getSectionDetails(sectionId);
    }

    public Section updateSection(Long sectionId, String name) {
        return this.stadiumService.updateSection(sectionId, name);
    }

    public boolean deleteSection(Long sectionId) {
        return this.stadiumService.deleteSection(sectionId);
    }

    public Seat createSeat(Long sectionId, String rowLabel, int seatNumber) {
        return this.stadiumService.createSeat(sectionId, rowLabel, seatNumber);
    }

    public List<Seat> buildSeatMap(Long sectionId) {
        return this.stadiumService.buildSeatMap(sectionId);
    }

    public Seat getSeatDetails(Long seatId) {
        return this.stadiumService.getSeatDetails(seatId);
    }

    public Seat updateSeat(Long seatId, String rowLabel, int seatNumber) {
        return this.stadiumService.updateSeat(seatId, rowLabel, seatNumber);
    }

    public boolean deleteSeat(Long seatId) {
        return this.stadiumService.deleteSeat(seatId);
    }

    public Seat activateSeat(Long seatId) {
        return this.stadiumService.activateSeat(seatId);
    }

    public Seat deactivateSeat(Long seatId) {
        return this.stadiumService.deactivateSeat(seatId);
    }
}
