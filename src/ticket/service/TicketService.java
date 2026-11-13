package ticket.service;

import booking.model.Booking;
import user.model.Fan;
import user.repository.FanRepository;
import booking.enums.BookingStatus;
import booking.repository.BookingRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import ticket.dto.TicketResponseDto;
import ticket.model.Ticket;
import ticket.enums.TicketStatus;
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

    private final FanRepository fanRepository;

    public TicketService(TicketRepository ticketRepository,
                         BookingRepository bookingRepository, MatchRepository matchRepository,
                         SeatRepository seatRepository, SectionRepository sectionRepository,
                         FanRepository fanRepository) {
        if (ticketRepository == null || bookingRepository == null || matchRepository == null
                || seatRepository == null || sectionRepository == null || fanRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket, Booking, Match, Seat, Section and Fan repositories are required");
        }
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
        this.sectionRepository = sectionRepository;
        this.fanRepository = fanRepository;
    }

    public TicketResponseDto validateTicket(String ticketCode) {
        Ticket ticket = this.ticketRepository.findByCode(ticketCode);
        if (ticket == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
        }
        this.requireUsable(ticket);
        return this.toResponse(ticket);
    }

    public TicketResponseDto getTicketDetails(Long ticketId) {
        Ticket ticket = this.ticketRepository.findById(ticketId);
        if (ticket == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
        }
        return this.toResponse(ticket);
    }

    public TicketResponseDto checkInTicket(String ticketCode) {
        synchronized (TicketRepository.class) {
            Ticket ticket = this.ticketRepository.findByCode(ticketCode);
            if (ticket == null) {
                throw new AppException(ErrorCode.NOT_FOUND, "Ticket not found");
            }
            this.requireUsable(ticket);
            ticket.checkIn(LocalDateTime.now());
            this.ticketRepository.update(ticket);
            return this.toResponse(ticket);
        }
    }

    private void requireUsable(Ticket ticket) {
        if (ticket.getStatus() != TicketStatus.ISSUED
                || this.matchRepository.findById(ticket.getMatchId()) == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket is not issued, its match is missing, or it was already used");
        }
        Booking booking = this.bookingRepository.findById(ticket.getBookingId());
        if (booking == null || booking.getStatus() != BookingStatus.CONFIRMED
                || !booking.getMatchId().equals(ticket.getMatchId())
                || !booking.getSeatIds().contains(ticket.getSeatId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Ticket has no valid confirmed booking");
        }
    }

    private TicketResponseDto toResponse(Ticket ticket) {
        Booking booking = this.bookingRepository.findById(ticket.getBookingId());
        Fan fan = booking == null || booking.getFanId() == null ? null
                : this.fanRepository.findById(booking.getFanId());
        Match match = this.matchRepository.findById(ticket.getMatchId());
        Seat seat = this.seatRepository.findById(ticket.getSeatId());
        Section section = seat == null ? null : this.sectionRepository.findById(seat.getSectionId());
        return new TicketResponseDto(ticket.getId(), ticket.getBookingId(),
                ticket.getMatchId(), ticket.getSeatId(), ticket.getTicketCode(),
                ticket.getStatus(), ticket.getCheckedInAt(),
                match == null ? "Match #" + ticket.getMatchId() : match.getHomeTeam() + " vs " + match.getAwayTeam(),
                seat == null ? "Seat #" + ticket.getSeatId() :
                        (section == null ? "" : section.getName() + " ") + seat.getRowLabel() + seat.getSeatNumber(),
                fan == null ? "None" : fan.getFullName(),
                fan == null ? "None" : fan.getPhone());
    }
}
