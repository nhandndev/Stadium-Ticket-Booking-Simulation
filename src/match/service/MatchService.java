package match.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import match.dto.MatchRequestDto;
import match.dto.MatchResponseDto;
import match.dto.TicketPriceRequestDto;
import match.model.Match;
import match.enums.SaleStatus;
import match.model.TicketPrice;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.repository.TicketPriceRepository;

public class MatchService {
    private final MatchRepository matchRepository;
    private final TicketPriceRepository ticketPriceRepository;
    private final MatchSeatRepository matchSeatRepository;

    public MatchService(MatchRepository matchRepository,
                        TicketPriceRepository ticketPriceRepository,
                        MatchSeatRepository matchSeatRepository) {
        this.matchRepository = matchRepository;
        this.ticketPriceRepository = ticketPriceRepository;
        this.matchSeatRepository = matchSeatRepository;
    }

    public MatchResponseDto createMatch(MatchRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match request is required");
        }
        Match match = new Match(null, request.getStadiumId(), request.getHomeTeam(),
                request.getAwayTeam(), request.getStartTime(), SaleStatus.CLOSED);
        Match saved = this.matchRepository.save(match);
        return new MatchResponseDto(saved.getId(), saved.getStadiumId(), saved.getHomeTeam(),
                saved.getAwayTeam(), saved.getStartTime(), saved.getSaleStatus());
    }

    public List<MatchResponseDto> getMatches() {
        List<MatchResponseDto> result = new ArrayList<>();
        for (Match match : this.matchRepository.findAll()) {
            result.add(new MatchResponseDto(match.getId(), match.getStadiumId(), match.getHomeTeam(),
                    match.getAwayTeam(), match.getStartTime(), match.getSaleStatus()));
        }
        return result;
    }

    public MatchResponseDto getMatchDetails(Long matchId) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        return new MatchResponseDto(match.getId(), match.getStadiumId(), match.getHomeTeam(),
                match.getAwayTeam(), match.getStartTime(), match.getSaleStatus());
    }

    public MatchResponseDto updateMatch(Long matchId, MatchRequestDto request) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        Match replacement = new Match(matchId, request.getStadiumId(), request.getHomeTeam(),
                request.getAwayTeam(), request.getStartTime(), match.getSaleStatus());
        Match updated = this.matchRepository.update(replacement);
        return new MatchResponseDto(updated.getId(), updated.getStadiumId(), updated.getHomeTeam(),
                updated.getAwayTeam(), updated.getStartTime(), updated.getSaleStatus());
    }

    public boolean deleteMatch(Long matchId) {
        return this.matchRepository.delete(matchId);
    }

    public TicketPrice createTicketPrice(TicketPriceRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Price request is required");
        }
        TicketPrice price = new TicketPrice(request.getMatchId(), request.getSectionId(), request.getAmount());
        return this.ticketPriceRepository.save(price);
    }

    public List<TicketPrice> getTicketPrices(Long matchId) {
        return this.ticketPriceRepository.findByMatchId(matchId);
    }

    public TicketPrice getTicketPriceDetails(Long matchId, Long sectionId) {
        TicketPrice price = this.ticketPriceRepository.findPrice(matchId, sectionId);
        if (price == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket price not found");
        }
        return price;
    }

    public TicketPrice updateTicketPrice(Long matchId, Long sectionId, BigDecimal amount) {
        TicketPrice price = this.ticketPriceRepository.findPrice(matchId, sectionId);
        if (price == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket price not found");
        }
        price.updatePrice(amount);
        return this.ticketPriceRepository.update(price);
    }

    public boolean deleteTicketPrice(Long matchId, Long sectionId) {
        return this.ticketPriceRepository.delete(matchId, sectionId);
    }

    public MatchResponseDto openTicketSales(Long matchId) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        match.changeSaleStatus(SaleStatus.OPEN);
        Match updated = this.matchRepository.update(match);
        return new MatchResponseDto(updated.getId(), updated.getStadiumId(), updated.getHomeTeam(),
                updated.getAwayTeam(), updated.getStartTime(), updated.getSaleStatus());
    }

    public MatchResponseDto closeTicketSales(Long matchId) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        match.changeSaleStatus(SaleStatus.CLOSED);
        Match updated = this.matchRepository.update(match);
        return new MatchResponseDto(updated.getId(), updated.getStadiumId(), updated.getHomeTeam(),
                updated.getAwayTeam(), updated.getStartTime(), updated.getSaleStatus());
    }

    public SaleStatus getTicketSalesStatus(Long matchId) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        return match.getSaleStatus();
    }
}

