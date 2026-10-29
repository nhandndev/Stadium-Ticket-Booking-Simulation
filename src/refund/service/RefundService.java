package refund.service;

import booking.model.Booking;
import booking.enums.BookingStatus;
import booking.repository.BookingRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.model.Match;
import match.model.MatchSeat;
import match.enums.SeatStatus;
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import payment.model.Payment;
import payment.enums.PaymentStatus;
import payment.repository.PaymentRepository;
import refund.dto.RefundRequestDto;
import refund.dto.RefundResponseDto;
import refund.dto.RefundReviewRequestDto;
import refund.model.RefundRequest;
import refund.enums.RefundStatus;
import refund.repository.RefundRepository;
import ticket.model.Ticket;
import ticket.enums.TicketStatus;
import ticket.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class RefundService {
    private RefundRepository refundRepository;
    private BookingRepository bookingRepository;
    private PaymentRepository paymentRepository;
    private TicketRepository ticketRepository;
    private MatchRepository matchRepository;
    private MatchSeatRepository matchSeatRepository;

    public RefundService(RefundRepository refundRepository,
                         BookingRepository bookingRepository,
                         PaymentRepository paymentRepository,
                         TicketRepository ticketRepository,
                         MatchRepository matchRepository,
                         MatchSeatRepository matchSeatRepository) {
        if (refundRepository == null || bookingRepository == null
                || paymentRepository == null || ticketRepository == null
                || matchRepository == null || matchSeatRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Refund dependencies are required");
        }
        this.refundRepository = refundRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
    }

    public RefundResponseDto requestRefund(RefundRequestDto request) {
        if (request == null || request.getFanId() == null
                || request.getFanId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Valid refund request and Fan ID are required");
        }
        synchronized (MatchSeatRepository.class) {
            Booking booking = requireBooking(request.getBookingId());
            if (!booking.getFanId().equals(request.getFanId())) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Fan does not own booking");
            }
            requireEligible(booking);
            for (RefundRequest old : refundRepository.findByFanId(request.getFanId())) {
                if (old.getBookingId().equals(booking.getId())
                        && old.getStatus() != RefundStatus.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Booking already has an active refund request");
                }
            }
            RefundRequest refund = new RefundRequest(null, booking.getId(),
                    booking.getFanId(), request.getReason(), LocalDateTime.now());
            return toResponse(refundRepository.save(refund));
        }
    }

    public List<RefundRequest> getPendingRefunds() {
        return refundRepository.findPending();
    }

    public List<RefundResponseDto> getMyRefunds(Long fanId) {
        List<RefundResponseDto> results = new ArrayList<>();
        for (RefundRequest request : refundRepository.findByFanId(fanId)) {
            results.add(toResponse(request));
        }
        return results;
    }

    public RefundResponseDto reviewRefund(RefundReviewRequestDto request) {
        if (request == null || request.getAdminId() == null
                || request.getAdminId() <= 0 || request.getRequestId() == null
                || request.getRequestId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Valid Admin and refund IDs are required");
        }
        synchronized (MatchSeatRepository.class) {
            synchronized (TicketRepository.class) {
                RefundRequest refund = refundRepository.findById(request.getRequestId());
                if (refund == null) {
                    throw new AppException(ErrorCode.NOT_FOUND,
                            "Refund request not found");
                }
                if (refund.getStatus() != RefundStatus.REQUESTED) {
                    throw new AppException(ErrorCode.INVALID_INPUT,
                            "Refund request already reviewed");
                }
                if (!request.isApprove()) {
                    refund.reject(request.getAdminId(), request.getNote(),
                            LocalDateTime.now());
                    return toResponse(refundRepository.update(refund));
                }
                Booking booking = requireBooking(refund.getBookingId());
                if (!booking.getFanId().equals(refund.getFanId())) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            "Refund owner does not match booking");
                }
                requireEligible(booking);
                List<Ticket> tickets = ticketRepository.findByBookingId(booking.getId());
                for (Ticket ticket : tickets) {
                    if (!ticket.getMatchId().equals(booking.getMatchId())
                            || !booking.getSeatIds().contains(ticket.getSeatId())
                            || ticket.getStatus() == TicketStatus.CHECKED_IN
                            || ticket.getStatus() == TicketStatus.CANCELLED) {
                        throw new AppException(ErrorCode.INVALID_INPUT,
                                "Ticket cannot be refunded");
                    }
                }
                for (Long seatId : booking.getSeatIds()) {
                    MatchSeat seat = matchSeatRepository.find(booking.getMatchId(), seatId);
                    if (seat == null || seat.getStatus() != SeatStatus.BOOKED) {
                        throw new AppException(ErrorCode.CSV_ERROR,
                                "Booked seat state is inconsistent");
                    }
                }
                refund.approve(request.getAdminId(), request.getNote(), LocalDateTime.now());
                for (Ticket ticket : tickets) {
                    ticket.cancel();
                    ticketRepository.update(ticket);
                }
                booking.cancel();
                bookingRepository.update(booking);
                if (!matchSeatRepository.releaseSeatsSynchronized(
                        booking.getMatchId(), booking.getSeatIds())) {
                    throw new AppException(ErrorCode.CSV_ERROR,
                            "Booked seats could not be released");
                }
                return toResponse(refundRepository.update(refund));
            }
        }
    }

    private void requireEligible(Booking booking) {
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Only confirmed booking can request refund");
        }
        Payment payment = paymentRepository.findByBookingId(booking.getId());
        if (payment == null || payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Successful payment is required");
        }
        Match match = matchRepository.findById(booking.getMatchId());
        if (match == null || match.getStartTime() == null
                || !match.getStartTime().isAfter(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Match already started or is missing");
        }
        for (Ticket ticket : ticketRepository.findByBookingId(booking.getId())) {
            if (ticket.getStatus() == TicketStatus.CHECKED_IN
                    || ticket.getStatus() == TicketStatus.CANCELLED) {
                throw new AppException(ErrorCode.INVALID_INPUT,
                        "Used or cancelled ticket cannot be refunded");
            }
        }
    }

    private Booking requireBooking(Long bookingId) {
        if (bookingId == null || bookingId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Booking ID must be positive");
        }
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Booking not found");
        }
        return booking;
    }

    private RefundResponseDto toResponse(RefundRequest refund) {
        return new RefundResponseDto(refund.getId(), refund.getBookingId(),
                refund.getFanId(), refund.getStatus());
    }
}
