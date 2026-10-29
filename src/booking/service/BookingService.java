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
        validateTicketLimit(seatIds);
        Match match = requireOpenMatch(matchId);
        BigDecimal total = BigDecimal.ZERO;
        for (Long seatId : seatIds) {
            Seat seat = requireUsableSeat(match, seatId);
            MatchSeat matchSeat = matchSeatRepository.find(matchId, seatId);
            if (matchSeat == null || !matchSeat.isAvailable()) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat is not available");
            }
            TicketPrice price = ticketPriceRepository.findPrice(matchId,
                    seat.getSectionId());
            if (price == null || price.getAmount() == null
                    || price.getAmount().signum() <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Ticket price is missing or invalid");
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
            requireActiveFan(request.getFanId());
            BigDecimal total = calculateTotal(request.getMatchId(), request.getSeatIds());
            Booking booking = bookingRepository.save(new Booking(null, request.getFanId(),
                    request.getMatchId(), request.getSeatIds(), total, BookingStatus.PENDING));
            return toBookingResponse(booking);
        }
    }

    public BookingResponseDto createBookingForFan(Long sellerId,
                                                  BookingRequestDto request) {
        requirePositive(sellerId, "Seller ID");
        return createBooking(request);
    }

    public PaymentResponseDto makeOnlinePayment(PaymentRequestDto request) {
        return completePayment(request, PaymentMethod.ONLINE);
    }

    public PaymentResponseDto acceptOfflinePayment(Long sellerId,
                                                   PaymentRequestDto request) {
        requirePositive(sellerId, "Seller ID");
        return completePayment(request, PaymentMethod.OFFLINE_CASH);
    }

    private PaymentResponseDto completePayment(PaymentRequestDto request,
                                               PaymentMethod method) {
        if (request == null || request.getAmount() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Payment request is required");
        }
        synchronized (MatchSeatRepository.class) {
            Booking booking = requireBooking(request.getBookingId());
            if (booking.getTotalAmount().compareTo(request.getAmount()) != 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Payment amount mismatch");
            }
            Payment existing = paymentRepository.findByBookingId(booking.getId());
            if (existing != null) {
                if (existing.getMethod() != method) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Booking already uses another payment method");
                }
                if (existing.getStatus() == PaymentStatus.SUCCESS
                        && booking.getStatus() == BookingStatus.CONFIRMED) {
                    if (method == PaymentMethod.ONLINE) issueTickets(booking.getId());
                    return toPaymentResponse(existing);
                }
                if (existing.getStatus() == PaymentStatus.FAILED) {
                    return toPaymentResponse(existing);
                }
                throw new AppException(ErrorCode.CSV_ERROR,
                        "Incomplete checkout requires reconciliation");
            }
            if (booking.getStatus() != BookingStatus.PENDING) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Only pending booking can be paid");
            }
            requireActiveFan(booking.getFanId());
            Match match = requireOpenMatch(booking.getMatchId());
            for (Long seatId : booking.getSeatIds()) {
                requireUsableSeat(match, seatId);
            }
            Payment payment = paymentRepository.save(new Payment(null,
                    booking.getId(), booking.getTotalAmount(), method,
                    PaymentStatus.PENDING));
            if (method == PaymentMethod.ONLINE
                    && !mockPaymentService.processPayment(booking.getId(),
                    booking.getTotalAmount())) {
                payment.markFailed();
                paymentRepository.update(payment);
                booking.cancel();
                bookingRepository.update(booking);
                recordFailedPayment(booking, "Mock payment failed");
                return toPaymentResponse(payment);
            }
            BookingTransaction transaction = strategyFactory
                    .create(SyncMechanism.SYNCHRONIZED)
                    .execute(booking.getFanId(), booking.getMatchId(),
                            booking.getSeatIds());
            if (transaction.getStatus() != TransactionStatus.SUCCESS) {
                payment.markFailed();
                paymentRepository.update(payment);
                booking.cancel();
                bookingRepository.update(booking);
                transaction.attachBooking(booking.getId());
                transactionRepository.save(transaction);
                return toPaymentResponse(payment);
            }
            transaction.attachBooking(booking.getId());
            payment.markSuccess();
            paymentRepository.update(payment);
            booking.confirm();
            bookingRepository.update(booking);
            if (method == PaymentMethod.ONLINE) issueTickets(booking.getId());
            transactionRepository.save(transaction);
            return toPaymentResponse(payment);
        }
    }

    public List<TicketResponseDto> issueTickets(Long bookingId) {
        synchronized (TicketRepository.class) {
            Booking booking = requireBooking(bookingId);
            Payment payment = paymentRepository.findByBookingId(bookingId);
            if (booking.getStatus() != BookingStatus.CONFIRMED || payment == null
                    || payment.getStatus() != PaymentStatus.SUCCESS) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Confirmed paid booking is required before issuing tickets");
            }
            List<Ticket> existing = ticketRepository.findByBookingId(bookingId);
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
                    if (ticketRepository.findByCode(code) != null) {
                        throw new AppException(ErrorCode.CSV_ERROR,
                                "Ticket code already belongs to another ticket");
                    }
                    ticket = new Ticket(null, bookingId, booking.getMatchId(),
                            seatId, code, TicketStatus.PENDING);
                    ticket.issue();
                    ticketRepository.save(ticket);
                } else if (ticket.getStatus() == TicketStatus.PENDING) {
                    ticket.issue();
                    ticketRepository.update(ticket);
                } else if (ticket.getStatus() == TicketStatus.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Cancelled ticket cannot be reissued");
                }
                result.add(toTicketResponse(ticket));
            }
            return result;
        }
    }

    public List<BookingResponseDto> getMyBookings(Long fanId) {
        requirePositive(fanId, "Fan ID");
        List<BookingResponseDto> result = new ArrayList<>();
        for (Booking booking : bookingRepository.findByFanId(fanId)) {
            result.add(toBookingResponse(booking));
        }
        return result;
    }

    public BookingResponseDto getBookingDetails(Long bookingId) {
        return toBookingResponse(requireBooking(bookingId));
    }

    public BookingResponseDto cancelBooking(Long bookingId) {
        synchronized (MatchSeatRepository.class) {
            Booking booking = requireBooking(bookingId);
            if (booking.getStatus() != BookingStatus.PENDING
                    || paymentRepository.findByBookingId(bookingId) != null) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Only an unpaid pending booking can be cancelled directly. Paid bookings need a refund request.");
            }
            booking.cancel();
            return toBookingResponse(bookingRepository.update(booking));
        }
    }

    private Match requireOpenMatch(Long matchId) {
        requirePositive(matchId, "Match ID");
        Match match = matchRepository.findById(matchId);
        if (match == null || match.getSaleStatus() != SaleStatus.OPEN
                || match.getStartTime() == null
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Match is missing, closed or already started");
        }
        return match;
    }

    private Seat requireUsableSeat(Match match, Long seatId) {
        requirePositive(seatId, "Seat ID");
        Seat seat = seatRepository.findById(seatId);
        if (seat == null || !seat.isActive()) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Seat does not exist or is inactive");
        }
        Section section = sectionRepository.findById(seat.getSectionId());
        if (section == null || !section.getStadiumId().equals(match.getStadiumId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Seat does not belong to match stadium");
        }
        return seat;
    }

    private Fan requireActiveFan(Long fanId) {
        requirePositive(fanId, "Fan ID");
        Fan fan = fanRepository.findById(fanId);
        if (fan == null || fan.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Active fan account is required");
        }
        return fan;
    }

    private Booking requireBooking(Long bookingId) {
        requirePositive(bookingId, "Booking ID");
        Booking booking = bookingRepository.findById(bookingId);
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
        transactionRepository.save(transaction);
    }

    private BookingResponseDto toBookingResponse(Booking booking) {
        Fan fan = fanRepository.findById(booking.getFanId());
        Match match = matchRepository.findById(booking.getMatchId());
        List<String> labels = new ArrayList<>();
        for (Long seatId : booking.getSeatIds()) {
            Seat seat = seatRepository.findById(seatId);
            Section section = seat == null ? null : sectionRepository.findById(seat.getSectionId());
            labels.add(seat == null ? "Seat #" + seatId :
                    (section == null ? "" : section.getName() + " ") + seat.getRowLabel() + seat.getSeatNumber());
        }
        return new BookingResponseDto(booking.getId(), booking.getFanId(),
                booking.getMatchId(), booking.getSeatIds(),
                booking.getTotalAmount(), booking.getStatus(),
                fan == null ? "Fan #" + booking.getFanId() : fan.getFullName(),
                match == null ? "Match #" + booking.getMatchId() : match.getHomeTeam() + " vs " + match.getAwayTeam(), labels);
    }

    private PaymentResponseDto toPaymentResponse(Payment payment) {
        return new PaymentResponseDto(payment.getId(), payment.getBookingId(),
                payment.getAmount(), payment.getMethod(), payment.getStatus());
    }

    private TicketResponseDto toTicketResponse(Ticket ticket) {
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
