package user.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import ticket.dto.TicketResponseDto;
import ticket.model.Ticket;
import ticket.repository.TicketRepository;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;
import user.enums.UserStatus;
import user.model.Fan;
import user.repository.FanRepository;
import user.repository.StaffRepository;
import match.model.Match;
import match.repository.MatchRepository;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;

public class FanService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;
    private final TicketRepository ticketRepository;
    private final MatchRepository matchRepository;
    private final SeatRepository seatRepository;
    private final SectionRepository sectionRepository;

    public FanService(FanRepository fanRepository, StaffRepository staffRepository,
                      TicketRepository ticketRepository, MatchRepository matchRepository,
                      SeatRepository seatRepository, SectionRepository sectionRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
        this.seatRepository = seatRepository;
        this.sectionRepository = sectionRepository;
    }

    public UserResponseDto register(RegisterFanRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Registration is required");
        }
        String name = request.getFullName() != null ? request.getFullName().trim() : "";
        String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";
        String phone = request.getPhone() != null ? request.getPhone().trim() : "";
        String password = request.getPassword() != null ? request.getPassword().trim() : "";

        for (String value : new String[]{name, email, password, phone}) {
            if (value.isEmpty() || value.contains(",") || value.contains("\n")
                    || value.contains("\r")) {
                throw new AppException(ErrorCode.INVALID_INPUT, "All fields are required and must fit one CSV column");
            }
        }
        if (password.length() < 8 || !email.contains("@")
                || email.startsWith("@") || email.endsWith("@")) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Use a valid email and a password of at least 8 characters");
        }

        if (this.fanRepository.findByEmail(email) != null || this.staffRepository.findByEmail(email) != null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email already exists");
        }
        Fan saved = this.fanRepository.save(new Fan(null, name, email, password, phone, UserStatus.ACTIVE));
        return new UserResponseDto(saved.getId(), saved.getFullName(), saved.getEmail(),
                saved.getPhone(), saved.getRole(), saved.getStatus());
    }

    public List<UserResponseDto> searchFans(String keyword) {
        List<UserResponseDto> results = new ArrayList<>();
        for (Fan fan : this.fanRepository.search(keyword)) {
            results.add(new UserResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                    fan.getPhone(), fan.getRole(), fan.getStatus()));
        }
        return results;
    }

    public List<TicketResponseDto> getMyTickets(Long fanId) {
        List<TicketResponseDto> results = new ArrayList<>();
        for (Ticket ticket : ticketRepository.findByFanId(fanId)) {
            Match match = matchRepository.findById(ticket.getMatchId());
            Seat seat = seatRepository.findById(ticket.getSeatId());
            Section section = seat == null ? null : sectionRepository.findById(seat.getSectionId());
            results.add(new TicketResponseDto(ticket.getId(), ticket.getBookingId(),
                    ticket.getMatchId(), ticket.getSeatId(), ticket.getTicketCode(),
                    ticket.getStatus(), ticket.getCheckedInAt(),
                    match == null ? "Match #" + ticket.getMatchId() : match.getHomeTeam() + " vs " + match.getAwayTeam(),
                    seat == null ? "Seat #" + ticket.getSeatId() :
                            (section == null ? "" : section.getName() + " ") + seat.getRowLabel() + seat.getSeatNumber()));
        }
        return results;
    }
}
