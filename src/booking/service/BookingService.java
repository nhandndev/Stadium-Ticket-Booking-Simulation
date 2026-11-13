package booking.service;

import booking.dto.BookingRequestDto;
import booking.dto.BookingResponseDto;
import booking.model.Booking;
import booking.enums.BookingStatus;
import booking.model.BookingTransaction;
import booking.enums.TransactionStatus;
import booking.repository.BookingRepository;
import booking.repository.TransactionRepository;
import booking.strategy.StrategyFactory;
import booking.enums.SyncMechanism;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.model.Match;
import match.model.MatchSeat;
import match.enums.SaleStatus;
import match.enums.SeatStatus;
import match.model.TicketPrice;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.repository.TicketPriceRepository;
import payment.dto.PaymentRequestDto;
import payment.dto.PaymentResponseDto;
import payment.model.Payment;
import payment.enums.PaymentMethod;
import payment.enums.PaymentStatus;
import payment.repository.PaymentRepository;
import payment.service.MockPaymentService;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;
import ticket.dto.TicketResponseDto;
import ticket.model.Ticket;
import ticket.enums.TicketStatus;
import ticket.repository.TicketRepository;
import user.model.Fan;
import user.enums.UserStatus;
import user.repository.FanRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingService {
    private BookingRepository bookingRepository;
    private TicketRepository ticketRepository;
    private PaymentRepository paymentRepository;
    private TransactionRepository transactionRepository;
    private SeatRepository seatRepository;
    private TicketPriceRepository ticketPriceRepository;
    private StrategyFactory strategyFactory;
    private MockPaymentService mockPaymentService;
    private FanRepository fanRepository;
    private MatchRepository matchRepository;
    private MatchSeatRepository matchSeatRepository;
    private SectionRepository sectionRepository;

    public BookingService(BookingRepository bookingRepository,
                          TicketRepository ticketRepository,
                          PaymentRepository paymentRepository,
                          TransactionRepository transactionRepository,
                          SeatRepository seatRepository,
                          TicketPriceRepository ticketPriceRepository,
                          StrategyFactory strategyFactory,
                          MockPaymentService mockPaymentService,
                          FanRepository fanRepository,
                          MatchRepository matchRepository,
                          MatchSeatRepository matchSeatRepository,
                          SectionRepository sectionRepository) {
        if (bookingRepository == null || ticketRepository == null
                || paymentRepository == null || transactionRepository == null
                || seatRepository == null || ticketPriceRepository == null
                || strategyFactory == null || mockPaymentService == null
                || fanRepository == null || matchRepository == null
                || matchSeatRepository == null || sectionRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Booking dependencies are required");
        }
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
        this.seatRepository = seatRepository;
        this.ticketPriceRepository = ticketPriceRepository;
        this.strategyFactory = strategyFactory;
        this.mockPaymentService = mockPaymentService;
        this.fanRepository = fanRepository;
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
        this.sectionRepository = sectionRepository;
    }

    public void validateTicketLimit(List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty() || seatIds.size() > 4) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Choose 1 to 4 seats");
        }
        Set<Long> unique = new HashSet<>();
        for (Long seatId : seatIds) {
            if (seatId == null || seatId <= 0 || !unique.add(seatId)) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Invalid or duplicate seat ID");
            }
        }
    }

    public BigDecimal calculateTotal(Long matchId, List<Long> seatIds) {
        this.expirePendingBookings();
        this.validateTicketLimit(seatIds);
        Match match = this.requireOpenMatch(matchId);
        BigDecimal total = BigDecimal.ZERO;
        for (Long seatId : seatIds) {
            Seat seat = this.requireUsableSeat(match, seatId);
            MatchSeat matchSeat = this.matchSeatRepository.find(matchId, seatId);
            if (matchSeat == null) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Seat ID " + seatId + " has no inventory for Match #" + matchId
                                + ". Choose a SEAT ID from this match's displayed map.");
            }
            if (!matchSeat.isAvailable()) {
                if (matchSeat.getStatus() == SeatStatus.LOCKED) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Seat ID " + seatId + " is HELD by another unpaid booking for Match #"
                                    + matchId + ". Choose an AVAILABLE seat or pay the existing booking.");
                }
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Seat ID " + seatId + " is " + matchSeat.getStatus()
                                + " for Match #" + matchId + ". Choose an AVAILABLE seat.");
            }
            TicketPrice price = this.ticketPriceRepository.findPrice(matchId,
                    seat.getSectionId());
            if (price == null || price.getAmount() == null
                    || price.getAmount().signum() <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Price is missing or invalid for Seat ID " + seatId
                                + " in Match #" + matchId);
            }
            total = total.add(price.getAmount());
        }
        return total;
    }

    public BookingResponseDto createBooking(BookingRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking request is required");
        }
        synchronized (MatchSeatRepository.class) {
            this.requireActiveFan(request.getFanId());
            return this.savePendingBooking(request);
        }
    }

    public BookingResponseDto createBookingForFan(Long sellerId,
                                                  BookingRequestDto request) {
        this.requirePositive(sellerId, "Seller ID");
        return this.createBooking(request);
    }

    public BookingResponseDto createBookingForCustomer(Long sellerId,
                                                       BookingRequestDto request) {
        this.requirePositive(sellerId, "Seller ID");
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Booking request is required");
        }
        synchronized (MatchSeatRepository.class) {
            if (request.getFanId() != null) this.requireActiveFan(request.getFanId());
            return this.savePendingBooking(request);
        }
    }

    public void validateFanAccount(Long fanId) {
        this.requireActiveFan(fanId);
    }

    private BookingResponseDto savePendingBooking(BookingRequestDto request) {
        BigDecimal total = this.calculateTotal(request.getMatchId(), request.getSeatIds());
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(30);
        Match match = this.requireOpenMatch(request.getMatchId());
        if (match.getStartTime().isBefore(expiresAt)) expiresAt = match.getStartTime();
        Booking booking = this.bookingRepository.save(new Booking(null, request.getFanId(),
                request.getMatchId(), request.getSeatIds(), total, BookingStatus.PENDING,
                createdAt, expiresAt));
        try {
            if (!this.matchSeatRepository.holdSeatsSynchronized(booking.getId(), booking.getMatchId(),
                    booking.getSeatIds())) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "The selected seats are no longer available. Choose available seats again.");
            }
        } catch (AppException e) {
            booking.cancel();
            this.bookingRepository.update(booking);
            this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(), booking.getMatchId(),
                    booking.getSeatIds());
            throw e;
        }
        return this.toBookingResponse(booking);
    }

    public void expirePendingBookings() {
        synchronized (MatchSeatRepository.class) {
            LocalDateTime now = LocalDateTime.now();
            for (Booking booking : this.bookingRepository.findAll()) {
                if (booking.isExpired(now)) {
                    Payment payment = this.paymentRepository.findByBookingId(booking.getId());
                    if (payment != null && payment.getStatus() == PaymentStatus.SUCCESS) continue;
                    booking.expire();
                    this.bookingRepository.update(booking);
                }
                if (booking.getStatus() == BookingStatus.EXPIRED
                        || booking.getStatus() == BookingStatus.CANCELLED) {
                    this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(),
                            booking.getMatchId(), booking.getSeatIds());
                }
            }
        }
    }

    public PaymentResponseDto makeOnlinePayment(PaymentRequestDto request) {
        return this.completePayment(request, PaymentMethod.ONLINE);
    }

    public PaymentResponseDto acceptOfflinePayment(Long sellerId,
                                                   PaymentRequestDto request) {
        this.requirePositive(sellerId, "Seller ID");
        return this.completePayment(request, PaymentMethod.OFFLINE_CASH);
    }

    private PaymentResponseDto completePayment(PaymentRequestDto request,
                                               PaymentMethod method) {
        if (request == null || request.getAmount() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment request is required");
        }
        synchronized (MatchSeatRepository.class) {
            this.expirePendingBookings();
            Booking booking = this.requireBooking(request.getBookingId());
            if (booking.getStatus() == BookingStatus.EXPIRED) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Booking #" + booking.getId() + " has expired. The payment window is at most 30 minutes. Create a new booking.");
            }
            if (booking.getFanId() == null && method != PaymentMethod.OFFLINE_CASH) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Walk-in bookings must be paid in cash at the Seller counter");
            }
            if (booking.getTotalAmount().compareTo(request.getAmount()) != 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Payment amount mismatch");
            }
            Payment existing = this.paymentRepository.findByBookingId(booking.getId());
            if (existing != null) {
                if (existing.getMethod() != method) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Booking already uses another payment method");
                }
                if (existing.getStatus() == PaymentStatus.SUCCESS
                        && booking.getStatus() == BookingStatus.CONFIRMED) {
                    if (method == PaymentMethod.ONLINE) this.issueTickets(booking.getId());
                    return this.toPaymentResponse(existing);
                }
                if (existing.getStatus() == PaymentStatus.FAILED) {
                    return this.toPaymentResponse(existing);
                }
                throw new AppException(ErrorCode.CSV_ERROR,
                        "Incomplete checkout requires reconciliation");
            }
            if (booking.getStatus() != BookingStatus.PENDING) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Only pending booking can be paid");
            }
            if (booking.isExpired(LocalDateTime.now())) {
                booking.expire();
                this.bookingRepository.update(booking);
                this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(), booking.getMatchId(),
                        booking.getSeatIds());
                throw new AppException(ErrorCode.INVALID_INPUT, "Booking has expired; payment was not recorded.");
            }
            if (booking.getFanId() != null) this.requireActiveFan(booking.getFanId());
            Match match = this.requireOpenMatch(booking.getMatchId());
            for (Long seatId : booking.getSeatIds()) {
                this.requireUsableSeat(match, seatId);
            }
            Payment payment = this.paymentRepository.save(new Payment(null,
                    booking.getId(), booking.getTotalAmount(), method,
                    PaymentStatus.PENDING));
            if (method == PaymentMethod.ONLINE
                    && !this.mockPaymentService.processPayment(booking.getId(),
                    booking.getTotalAmount())) {
                payment.markFailed();
                this.paymentRepository.update(payment);
                booking.cancel();
                this.bookingRepository.update(booking);
                this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(), booking.getMatchId(),
                        booking.getSeatIds());
                this.recordFailedPayment(booking, "Mock payment failed");
                return this.toPaymentResponse(payment);
            }
            BookingTransaction transaction = this.strategyFactory
                    .create(SyncMechanism.SYNCHRONIZED)
                    .execute(booking.getId(), booking.getFanId(), booking.getMatchId(),
                            booking.getSeatIds());
            if (transaction.getStatus() != TransactionStatus.SUCCESS) {
                payment.markFailed();
                this.paymentRepository.update(payment);
                booking.cancel();
                this.bookingRepository.update(booking);
                this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(), booking.getMatchId(),
                        booking.getSeatIds());
                transaction.attachBooking(booking.getId());
                this.transactionRepository.save(transaction);
                return this.toPaymentResponse(payment);
            }
            transaction.attachBooking(booking.getId());
            payment.markSuccess();
            this.paymentRepository.update(payment);
            booking.confirm();
            this.bookingRepository.update(booking);
            if (method == PaymentMethod.ONLINE) this.issueTickets(booking.getId());
            this.transactionRepository.save(transaction);
            return this.toPaymentResponse(payment);
        }
    }

    public List<TicketResponseDto> issueTickets(Long bookingId) {
        synchronized (TicketRepository.class) {
            Booking booking = this.requireBooking(bookingId);
            Payment payment = this.paymentRepository.findByBookingId(bookingId);
            if (booking.getStatus() != BookingStatus.CONFIRMED || payment == null
                    || payment.getStatus() != PaymentStatus.SUCCESS) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Confirmed paid booking is required before issuing tickets");
            }
            List<Ticket> existing = this.ticketRepository.findByBookingId(bookingId);
            Set<Long> seenSeats = new HashSet<>();
            for (Ticket ticket : existing) {
                if (!ticket.getMatchId().equals(booking.getMatchId())
                        || !booking.getSeatIds().contains(ticket.getSeatId())
                        || !seenSeats.add(ticket.getSeatId())) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            "Ticket data does not match booking");
                }
            }
            List<TicketResponseDto> result = new ArrayList<>();
            for (Long seatId : booking.getSeatIds()) {
                Ticket ticket = null;
                for (Ticket candidate : existing) {
                    if (candidate.getSeatId().equals(seatId)) ticket = candidate;
                }
                if (ticket == null) {
                    String code = "T-" + bookingId + "-" + seatId;
                    if (this.ticketRepository.findByCode(code) != null) {
                        throw new AppException(ErrorCode.CSV_ERROR,
                                "Ticket code already belongs to another ticket");
                    }
                    ticket = new Ticket(null, bookingId, booking.getMatchId(),
                            seatId, code, TicketStatus.PENDING);
                    ticket.issue();
                    this.ticketRepository.save(ticket);
                } else if (ticket.getStatus() == TicketStatus.PENDING) {
                    ticket.issue();
                    this.ticketRepository.update(ticket);
                } else if (ticket.getStatus() == TicketStatus.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Cancelled ticket cannot be reissued");
                }
                result.add(this.toTicketResponse(ticket));
            }
            return result;
        }
    }

    public List<BookingResponseDto> getMyBookings(Long fanId) {
        this.expirePendingBookings();
        this.requirePositive(fanId, "Fan ID");
        List<BookingResponseDto> result = new ArrayList<>();
        for (Booking booking : this.bookingRepository.findByFanId(fanId)) {
            result.add(this.toBookingResponse(booking));
        }
        return result;
    }

    public List<TicketResponseDto> getTicketsForBooking(Long bookingId) {
        this.requireBooking(bookingId);
        List<TicketResponseDto> tickets = new ArrayList<>();
        for (Ticket ticket : this.ticketRepository.findByBookingId(bookingId)) {
            tickets.add(this.toTicketResponse(ticket));
        }
        return tickets;
    }

    public BookingResponseDto getBookingDetails(Long bookingId) {
        this.expirePendingBookings();
        return this.toBookingResponse(this.requireBooking(bookingId));
    }

    public BookingResponseDto cancelBooking(Long bookingId) {
        synchronized (MatchSeatRepository.class) {
            this.expirePendingBookings();
            Booking booking = this.requireBooking(bookingId);
            if (booking.getStatus() != BookingStatus.PENDING
                    || this.paymentRepository.findByBookingId(bookingId) != null) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Only an unpaid pending booking can be cancelled directly. Paid bookings need a refund request.");
            }
            booking.cancel();
            this.bookingRepository.update(booking);
            this.matchSeatRepository.releaseHeldSeatsSynchronized(booking.getId(), booking.getMatchId(),
                    booking.getSeatIds());
            return this.toBookingResponse(booking);
        }
    }

    private Match requireOpenMatch(Long matchId) {
        this.requirePositive(matchId, "Match ID");
        Match match = this.matchRepository.findById(matchId);
        if (match == null || match.getSaleStatus() != SaleStatus.OPEN
                || match.getStartTime() == null
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Match is missing, closed or already started");
        }
        return match;
    }

    private Seat requireUsableSeat(Match match, Long seatId) {
        this.requirePositive(seatId, "Seat ID");
        Seat seat = this.seatRepository.findById(seatId);
        if (seat == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Seat ID " + seatId + " does not exist. Enter a numeric SEAT ID from the displayed map.");
        }
        if (!seat.isActive()) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Seat ID " + seatId + " is INACTIVE and cannot be sold");
        }
        Section section = this.sectionRepository.findById(seat.getSectionId());
        if (section == null || !section.getStadiumId().equals(match.getStadiumId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Seat ID " + seatId + " belongs to another stadium. Choose a seat from Match #"
                            + match.getId() + "'s displayed map.");
        }
        return seat;
    }

    private Fan requireActiveFan(Long fanId) {
        this.requirePositive(fanId, "Fan ID");
        Fan fan = this.fanRepository.findById(fanId);
        if (fan == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan account #" + fanId + " does not exist");
        }
        if (fan.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan account #" + fanId + " is " + fan.getStatus()
                            + ". Choose an ACTIVE customer account.");
        }
        return fan;
    }

    private Booking requireBooking(Long bookingId) {
        this.requirePositive(bookingId, "Booking ID");
        Booking booking = this.bookingRepository.findById(bookingId);
        if (booking == null) throw new AppException(ErrorCode.NOT_FOUND,
                "Booking not found");
        return booking;
    }

    private void requirePositive(Long id, String label) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    label + " must be positive");
        }
    }

    private void recordFailedPayment(Booking booking, String reason) {
        BookingTransaction transaction = new BookingTransaction(null,
                booking.getFanId(), booking.getMatchId(), booking.getSeatIds(),
                SyncMechanism.SYNCHRONIZED);
        transaction.markFailed(reason, LocalDateTime.now());
        transaction.attachBooking(booking.getId());
        this.transactionRepository.save(transaction);
    }

    private BookingResponseDto toBookingResponse(Booking booking) {
        Fan fan = booking.getFanId() == null ? null : this.fanRepository.findById(booking.getFanId());
        Match match = this.matchRepository.findById(booking.getMatchId());
        List<String> labels = new ArrayList<>();
        for (Long seatId : booking.getSeatIds()) {
            Seat seat = this.seatRepository.findById(seatId);
            Section section = seat == null ? null : this.sectionRepository.findById(seat.getSectionId());
            labels.add(seat == null ? "Seat #" + seatId :
                    (section == null ? "" : section.getName() + " ") + seat.getRowLabel() + seat.getSeatNumber());
        }
        return new BookingResponseDto(booking.getId(), booking.getFanId(),
                booking.getMatchId(), booking.getSeatIds(),
                booking.getTotalAmount(), booking.getStatus(),
                booking.getFanId() == null ? "Walk-in customer"
                        : fan == null ? "Fan #" + booking.getFanId() : fan.getFullName(),
                match == null ? "Match #" + booking.getMatchId() : match.getHomeTeam() + " vs " + match.getAwayTeam(),
                labels, booking.getCreatedAt(), booking.getExpiresAt());
    }

    private PaymentResponseDto toPaymentResponse(Payment payment) {
        return new PaymentResponseDto(payment.getId(), payment.getBookingId(),
                payment.getAmount(), payment.getMethod(), payment.getStatus());
    }

    private TicketResponseDto toTicketResponse(Ticket ticket) {
        Booking booking = this.requireBooking(ticket.getBookingId());
        Fan fan = booking.getFanId() == null ? null : this.fanRepository.findById(booking.getFanId());
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
