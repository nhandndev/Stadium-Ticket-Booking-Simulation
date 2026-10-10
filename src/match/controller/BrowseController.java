package match.controller;

import match.dto.MatchResponseDto;
import match.dto.MatchSeatResponseDto;
import match.service.BrowseService;

import java.time.LocalDate;
import java.util.List;

public class BrowseController {
    private final BrowseService browseService;

    public BrowseController(BrowseService browseService) {
        this.browseService = browseService;
    }

    public List<MatchResponseDto> searchMatches(String keyword) {
        return browseService.searchMatches(keyword);
    }

    public List<MatchResponseDto> filterMatches(Long stadiumId, LocalDate startDate) {
        return browseService.filterMatches(stadiumId, startDate);
    }

    public MatchResponseDto viewMatchDetails(Long matchId) {
        return browseService.viewMatchDetails(matchId);
    }

    public List<MatchSeatResponseDto> getSeatMap(Long matchId) {
        return browseService.getSeatMap(matchId);
    }

}

