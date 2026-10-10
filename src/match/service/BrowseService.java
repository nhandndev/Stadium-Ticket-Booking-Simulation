package match.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import match.dto.MatchResponseDto;
import match.dto.MatchSeatResponseDto;
import match.model.Match;
import match.model.MatchSeat;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BrowseService {
    private final MatchRepository matchRepository;
    private final MatchSeatRepository matchSeatRepository;

    public BrowseService(MatchRepository matchRepository, MatchSeatRepository matchSeatRepository) {
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
    }

    public List<MatchResponseDto> searchMatches(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Keyword is required");
        }
        String text = keyword.trim().toLowerCase();
        List<MatchResponseDto> result = new ArrayList<>();
        for (Match match : matchRepository.findAll()) {
            boolean homeMatches = match.getHomeTeam().toLowerCase().contains(text);
            boolean awayMatches = match.getAwayTeam().toLowerCase().contains(text);
            if (homeMatches || awayMatches) {
                result.add(new MatchResponseDto(match.getId(), match.getStadiumId(),
                        match.getHomeTeam(), match.getAwayTeam(), match.getStartTime(),
                        match.getSaleStatus()));
            }
        }
        return result;
    }

    public List<MatchResponseDto> filterMatches(Long stadiumId, LocalDate startDate) {
        if (stadiumId != null && stadiumId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Stadium ID must be positive");
        }
        List<MatchResponseDto> result = new ArrayList<>();

        for (Match match : matchRepository.findAll()) {
            boolean stadiumMatches = true;
            boolean dateMatches = true;
            if (stadiumId != null) {
                stadiumMatches = stadiumId.equals(match.getStadiumId());
            }
            if (startDate != null) {
                dateMatches = startDate.equals(match.getStartTime().toLocalDate());
            }
            if (stadiumMatches && dateMatches) {
                result.add(new MatchResponseDto(match.getId(), match.getStadiumId(),
                        match.getHomeTeam(), match.getAwayTeam(), match.getStartTime(),
                        match.getSaleStatus()));
            }
        }
        return result;
    }

    public MatchResponseDto viewMatchDetails(Long matchId) {
        Match match = matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match does not exist");
        }
        return new MatchResponseDto(match.getId(), match.getStadiumId(),
                match.getHomeTeam(), match.getAwayTeam(), match.getStartTime(),
                match.getSaleStatus());
    }
    public List<MatchSeatResponseDto> getSeatMap(Long matchId) {
        Match match = matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(
                    ErrorCode.NOT_FOUND,
                    "Match does not exist"
            );
        }
        List<MatchSeatResponseDto> result = new ArrayList<>();
        for (MatchSeat seat : matchSeatRepository.findByMatchId(matchId)) {
            result.add(new MatchSeatResponseDto(seat.getId(), seat.getMatchId(),
                    seat.getSeatId(), seat.getStatus(), seat.getVersion()));
        }
        return result;
    }

}
