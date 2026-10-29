package ticket.service;

import booking.model.Booking;
import booking.enums.BookingStatus;
import booking.repository.BookingRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import ticket.dto.TicketResponseDto;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import java.time.LocalDateTime;
import match.model.Match;
import match.repository.MatchRepository;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;

public class TicketService {
    private TicketRepository ticketRepository;
    private BookingRepository bookingRepository;
    private final MatchRepository matchRepository;
    private final SeatRepository seatRepository;
    private final SectionRepository sectionRepository;

    public TicketService(TicketRepository ticketRepository,
                         BookingRepository bookingRepository, MatchRepository matchRepository,
                         SeatRepository seatRepository, SectionRepository sectionRepository) {
        if (ticketRepository == null || bookingRepository == null || matchRepository == null
                || seatRepository == null || sectionRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket, Booking, Match, Seat and Section repositories are required");
        }
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
        this.sectionRepository = sectionRepository;
    }

    public TicketResponseDto validateTicket(String ticketCode, Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match ID must be positive");
        }
        Ticket ticket = ticketRepository.findByCode(ticketCode);
        if (ticket == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
        }
        requireUsable(ticket, matchId);
        return toResponse(ticket);
    }

    public TicketResponseDto getTicketDetails(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        if (ticket == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
        }
        return toResponse(ticket);
    }

    public TicketResponseDto checkInTicket(String ticketCode, Long matchId) {
        if (matchId == null || matchId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Match ID must be positive");
        }
        synchronized (TicketRepository.class) {
            Ticket ticket = ticketRepository.findByCode(ticketCode);
            if (ticket == null) {
                throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
            }
            requireUsable(ticket, matchId);
            ticket.checkIn(LocalDateTime.now());
            ticketRepository.update(ticket);
            return toResponse(ticket);
        }
    }

    private void requireUsable(Ticket ticket, Long matchId) {
        if (!ticket.isValidForMatch(matchId)) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket is not issued for this match or was already used");
        }
        Booking booking = bookingRepository.findById(ticket.getBookingId());
        if (booking == null || booking.getStatus() != BookingStatus.CONFIRMED
                || !booking.getMatchId().equals(ticket.getMatchId())
                || !booking.getSeatIds().contains(ticket.getSeatId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket has no valid confirmed booking");
        }
    }

    private TicketResponseDto toResponse(Ticket ticket) {
        Match match = matchRepository.findById(ticket.getMatchId());
        Seat seat = seatRepository.findById(ticket.getSeatId());
        Section section = seat == null ? null : sectionRepository.findById(seat.getSectionId());
        return new TicketResponseDto(ticket.getId(), ticket.getBookingId(),
                ticket.getMatchId(), ticket.getSeatId(), ticket.getTicketCode(),
                ticket.getStatus(), ticket.getCheckedInAt(),
                match == null ? "Match #" + ticket.getMatchId() : match.getHomeTeam() + " vs " + match.getAwayTeam(),
                seat == null ? "Seat #" + ticket.getSeatId() :
                        (section == null ? "" : section.getName() + " ") + seat.getRowLabel() + seat.getSeatNumber());
    }
}
