package admin.service;

import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import admin.repository.AuditLogRepository;
import booking.repository.BookingRepository;
import ticket.repository.TicketRepository;
import java.util.List;
import match.repository.MatchRepository;
import stadium.repository.StadiumRepository;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class AdminService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;
    private final AuditLogRepository auditLogRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    public AdminService(FanRepository fanRepository,
                        StaffRepository staffRepository, StadiumRepository stadiumRepository,
                        MatchRepository matchRepository, AuditLogRepository auditLogRepository,
                        BookingRepository bookingRepository, TicketRepository ticketRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
        this.stadiumRepository = stadiumRepository;
        this.matchRepository = matchRepository;
        this.auditLogRepository = auditLogRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
    }

    public SystemSummaryDto getSystemSummary() {
        return new SystemSummaryDto(
                this.fanRepository.findAll().size(),
                this.staffRepository.findAll().size(),
                this.stadiumRepository.findAll().size(),
                this.matchRepository.findAll().size(),
                this.bookingRepository.findAll().size(),
                this.ticketRepository.findAll().size()
        );
    }

    public List<AuditLog> getAuditLogs() {
        return this.auditLogRepository.findRecent(30);
    }
}
