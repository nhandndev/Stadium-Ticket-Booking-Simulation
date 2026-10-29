package ticket.repository;

import booking.model.Booking;
import booking.repository.BookingRepository;
import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import ticket.model.Ticket;
import ticket.enums.TicketStatus;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketRepository extends CsvRepository<Ticket> {
    private final BookingRepository bookingRepository;

    public TicketRepository(Path filePath, BookingRepository bookingRepository) {
        super(filePath);
        if (bookingRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "BookingRepository is required");
        }
        this.bookingRepository = bookingRepository;
    }

    public List<Ticket> findByBookingId(Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking ID must be positive");
        }
        return findByCondition(ticket -> ticket.getBookingId().equals(bookingId));
    }

    public Ticket findByCode(String ticketCode) {
        if (!isValidCode(ticketCode)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ticket code");
        }
        List<Ticket> matches = findByCondition(
                ticket -> ticket.getTicketCode().equals(ticketCode));
        if (matches.size() > 1) {
            throw new AppException(ErrorCode.CSV_ERROR, "Duplicate ticket code");
        }
        return matches.isEmpty() ? null : matches.get(0);
    }

    public List<Ticket> findByFanId(Long fanId) {
        if (fanId == null || fanId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan ID must be positive");
        }
        Set<Long> bookingIds = new HashSet<>();
        for (Booking booking : bookingRepository.findByFanId(fanId)) {
            bookingIds.add(booking.getId());
        }
        return findByCondition(ticket -> bookingIds.contains(ticket.getBookingId()));
    }

    @Override
    public Ticket parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 7) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 7 ticket columns");
        }
        long id = Long.parseLong(parts[0]);
        long bookingId = Long.parseLong(parts[1]);
        long matchId = Long.parseLong(parts[2]);
        long seatId = Long.parseLong(parts[3]);
        if (id <= 0 || bookingId <= 0 || matchId <= 0 || seatId <= 0
                || !isValidCode(parts[4])) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid ticket data");
        }
        TicketStatus status = TicketStatus.valueOf(parts[5]);
        String checkedInText = parts[6];
        if (status == TicketStatus.CHECKED_IN) {
            if (checkedInText.isEmpty()) {
                throw new AppException(ErrorCode.CSV_ERROR, "Missing check-in time");
            }
            LocalDateTime checkedInAt = LocalDateTime.parse(checkedInText);
            Ticket ticket = new Ticket(id, bookingId, matchId, seatId,
                    parts[4], TicketStatus.PENDING);
            ticket.issue();
            ticket.checkIn(checkedInAt);
            return ticket;
        }
        if (!checkedInText.isEmpty()) {
            throw new AppException(ErrorCode.CSV_ERROR, "Unexpected check-in time");
        }
        return new Ticket(id, bookingId, matchId, seatId, parts[4], status);
    }

    @Override
    public String formatLine(Ticket entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || !isValidCode(entity.getTicketCode())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ticket CSV data");
        }
        LocalDateTime checkedInAt = entity.getCheckedInAt();
        if ((entity.getStatus() == TicketStatus.CHECKED_IN) != (checkedInAt != null)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Ticket check-in state is inconsistent");
        }
        return entity.getId() + "," + entity.getBookingId() + ","
                + entity.getMatchId() + "," + entity.getSeatId() + ","
                + entity.getTicketCode() + "," + entity.getStatus().name() + ","
                + (checkedInAt == null ? "" : checkedInAt.toString());
    }

    private boolean isValidCode(String code) {
        return code != null && code.matches("[A-Za-z0-9_-]+");
    }
}
