package match.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import match.dto.MatchResponseDto;
import match.dto.MatchSeatResponseDto;
import match.model.Match;
import match.model.MatchSeat;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import stadium.model.Seat;
import stadium.repository.SeatRepository;
import stadium.model.Stadium;
import stadium.model.Section;
import stadium.repository.StadiumRepository;
import stadium.repository.SectionRepository;
import match.model.TicketPrice;
import match.repository.TicketPriceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrowseService {
    private final MatchRepository matchRepository;
    private final MatchSeatRepository matchSeatRepository;
    private final SeatRepository seatRepository;
    private final StadiumRepository stadiumRepository;
    private final SectionRepository sectionRepository;
    private final TicketPriceRepository ticketPriceRepository;

    public BrowseService(MatchRepository matchRepository, MatchSeatRepository matchSeatRepository,
                         SeatRepository seatRepository, StadiumRepository stadiumRepository,
                         SectionRepository sectionRepository, TicketPriceRepository ticketPriceRepository) {
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
        this.seatRepository = seatRepository;
        this.stadiumRepository = stadiumRepository;
        this.sectionRepository = sectionRepository;
        this.ticketPriceRepository = ticketPriceRepository;
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
                result.add(toMatchResponse(match));
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
                result.add(toMatchResponse(match));
            }
        }
        return result;
    }

    public MatchResponseDto viewMatchDetails(Long matchId) {
        Match match = matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match does not exist");
        }
        return toMatchResponse(match);
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
            Seat physicalSeat = seatRepository.findById(seat.getSeatId());
            if (physicalSeat == null) {
                throw new AppException(ErrorCode.CSV_ERROR, "Match inventory references a missing seat");
            }
            Long sectionId = physicalSeat.getSectionId();
            Section section = sectionRepository.findById(sectionId);
            TicketPrice price = ticketPriceRepository.findPrice(matchId, sectionId);
            result.add(new MatchSeatResponseDto(seat.getId(), seat.getMatchId(),
                    seat.getSeatId(), sectionId, seat.getStatus(), seat.getVersion(),
                    section == null ? "Section #" + sectionId : section.getName(),
                    physicalSeat.getRowLabel() + physicalSeat.getSeatNumber(),
                    price == null ? null : price.getAmount(), physicalSeat.isActive()));
        }
        return result;
    }

    private MatchResponseDto toMatchResponse(Match match) {
        Stadium stadium = stadiumRepository.findById(match.getStadiumId());
        return new MatchResponseDto(match.getId(), match.getStadiumId(), match.getHomeTeam(),
                match.getAwayTeam(), match.getStartTime(), match.getSaleStatus(),
                stadium == null ? "Stadium #" + match.getStadiumId() : stadium.getName());
    }
}
