package match.controller;

import java.math.BigDecimal;
import java.util.List;

import match.dto.MatchRequestDto;
import match.dto.MatchResponseDto;
import match.dto.TicketPriceRequestDto;
import match.model.SaleStatus;
import match.model.TicketPrice;
import match.service.MatchService;


public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    public MatchResponseDto createMatch(MatchRequestDto request) {
        return this.matchService.createMatch(request);
    }

    public List<MatchResponseDto> getMatches() {
        return this.matchService.getMatches();
    }

    public MatchResponseDto getMatchDetails(Long matchId) {
        return this.matchService.getMatchDetails(matchId);
    }

    public MatchResponseDto updateMatch(Long matchId, MatchRequestDto request) {
        return this.matchService.updateMatch(matchId, request);
    }

    public boolean deleteMatch(Long matchId) {
        return this.matchService.deleteMatch(matchId);
    }

    public TicketPrice createTicketPrice(TicketPriceRequestDto request) {
        return this.matchService.createTicketPrice(request);
    }

    public List<TicketPrice> getTicketPrices(Long matchId) {
        return this.matchService.getTicketPrices(matchId);
    }

    public TicketPrice getTicketPriceDetails(Long matchId, Long sectionId) {
        return this.matchService.getTicketPriceDetails(matchId, sectionId);
    }

    public TicketPrice updateTicketPrice(Long matchId, Long sectionId, BigDecimal amount) {
        return this.matchService.updateTicketPrice(matchId, sectionId, amount);
    }

    public boolean deleteTicketPrice(Long matchId, Long sectionId) {
        return this.matchService.deleteTicketPrice(matchId, sectionId);
    }

    public MatchResponseDto openTicketSales(Long matchId) {
        return this.matchService.openTicketSales(matchId);
    }

    public MatchResponseDto closeTicketSales(Long matchId) {
        return this.matchService.closeTicketSales(matchId);
    }

    public SaleStatus getTicketSalesStatus(Long matchId) {
        return this.matchService.getTicketSalesStatus(matchId);
    }
}
