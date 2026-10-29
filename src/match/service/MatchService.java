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
import booking.model.Booking;
import booking.repository.BookingRepository;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;
import stadium.repository.StadiumRepository;
import match.model.MatchSeat;
import match.enums.SeatStatus;
import java.time.LocalDateTime;

public class MatchService {
    private final MatchRepository matchRepository;
    private final TicketPriceRepository ticketPriceRepository;
    private final MatchSeatRepository matchSeatRepository;
    private final BookingRepository bookingRepository;
    private final StadiumRepository stadiumRepository;
    private final SectionRepository sectionRepository;
    private final SeatRepository seatRepository;

    public MatchService(MatchRepository matchRepository,
                        TicketPriceRepository ticketPriceRepository,
                        MatchSeatRepository matchSeatRepository, BookingRepository bookingRepository,
                        StadiumRepository stadiumRepository, SectionRepository sectionRepository,
                        SeatRepository seatRepository) {
        this.matchRepository = matchRepository;
        this.ticketPriceRepository = ticketPriceRepository;
        this.matchSeatRepository = matchSeatRepository;
        this.bookingRepository = bookingRepository;
        this.stadiumRepository = stadiumRepository;
        this.sectionRepository = sectionRepository;
        this.seatRepository = seatRepository;
    }

    public MatchResponseDto createMatch(MatchRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match request is required");
        }
        validateMatchRequest(request);
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
        validateMatchRequest(request);
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        requireNoBookingHistory(matchId);
        if (match.getSaleStatus() == SaleStatus.OPEN
                || !matchSeatRepository.findByMatchId(matchId).isEmpty()
                || !ticketPriceRepository.findByMatchId(matchId).isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Only a closed match without inventory or prices can be changed");
        }
        Match replacement = new Match(matchId, request.getStadiumId(), request.getHomeTeam(),
                request.getAwayTeam(), request.getStartTime(), match.getSaleStatus());
        Match updated = this.matchRepository.update(replacement);
        return new MatchResponseDto(updated.getId(), updated.getStadiumId(), updated.getHomeTeam(),
                updated.getAwayTeam(), updated.getStartTime(), updated.getSaleStatus());
    }

    public boolean deleteMatch(Long matchId) {
        getMatchDetails(matchId);
        requireNoBookingHistory(matchId);
        if (getTicketSalesStatus(matchId) == SaleStatus.OPEN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Close sales before deleting the match");
        }
        matchSeatRepository.deleteByMatchId(matchId);
        for (TicketPrice price : ticketPriceRepository.findByMatchId(matchId)) {
            ticketPriceRepository.delete(matchId, price.getSectionId());
        }
        return this.matchRepository.delete(matchId);
    }

    public TicketPrice createTicketPrice(TicketPriceRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Price request is required");
        }
        MatchResponseDto match = getMatchDetails(request.getMatchId());
        Section section = sectionRepository.findById(request.getSectionId());
        if (section == null || !section.getStadiumId().equals(match.getStadiumId())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Section must belong to the match stadium");
        }
        if (match.getSaleStatus() == SaleStatus.OPEN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Close sales before changing prices");
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
        if (getTicketSalesStatus(matchId) == SaleStatus.OPEN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Close sales before changing prices");
        }
        TicketPrice price = this.ticketPriceRepository.findPrice(matchId, sectionId);
        if (price == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket price not found");
        }
        price.updatePrice(amount);
        return this.ticketPriceRepository.update(price);
    }

    public boolean deleteTicketPrice(Long matchId, Long sectionId) {
        if (getTicketSalesStatus(matchId) == SaleStatus.OPEN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Close sales before deleting prices");
        }
        return this.ticketPriceRepository.delete(matchId, sectionId);
    }

    public MatchResponseDto openTicketSales(Long matchId) {
        Match match = this.matchRepository.findById(matchId);
        if (match == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Match not found");
        }
        if (match.getStartTime() == null || !match.getStartTime().isAfter(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match has already started");
        }
        List<Seat> activeSeats = new ArrayList<>();
        for (Section section : sectionRepository.findByStadiumId(match.getStadiumId())) {
            for (Seat seat : seatRepository.findBySectionId(section.getId())) {
                if (seat.isActive()) {
                    if (ticketPriceRepository.findPrice(matchId, section.getId()) == null) {
                        throw new AppException(ErrorCode.INVALID_INPUT,
                                "Set a price for every section with active seats before opening sales");
                    }
                    activeSeats.add(seat);
                }
            }
        }
        if (activeSeats.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "No active seats for this stadium");
        }
        for (Seat seat : activeSeats) {
            if (matchSeatRepository.find(matchId, seat.getId()) == null) {
                matchSeatRepository.save(new MatchSeat(null, matchId, seat.getId(), SeatStatus.AVAILABLE, 0));
            }
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

    private void requireNoBookingHistory(Long matchId) {
        for (Booking booking : bookingRepository.findAll()) {
            if (booking.getMatchId().equals(matchId)) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Match has booking history and cannot be changed or deleted");
            }
        }
    }

    private void validateMatchRequest(MatchRequestDto request) {
        if (request == null || stadiumRepository.findById(request.getStadiumId()) == null
                || request.getStartTime() == null || !request.getStartTime().isAfter(LocalDateTime.now())
                || request.getHomeTeam() == null || request.getHomeTeam().trim().isEmpty()
                || request.getAwayTeam() == null || request.getAwayTeam().trim().isEmpty()
                || request.getHomeTeam().trim().equalsIgnoreCase(request.getAwayTeam().trim())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Valid stadium, distinct teams and future kick-off are required");
        }
        for (String team : new String[]{request.getHomeTeam(), request.getAwayTeam()}) {
            if (team.contains(",") || team.contains("\n") || team.contains("\r")) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Team name must fit one CSV column");
            }
        }
    }
}
