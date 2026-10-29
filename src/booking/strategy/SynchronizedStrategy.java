package booking.strategy;

import booking.enums.SyncMechanism;
import booking.model.BookingTransaction;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.repository.MatchSeatRepository;
import java.time.LocalDateTime;
import java.util.List;

import booking.repository.TransactionRepository;

public class SynchronizedStrategy implements BookingStrategy {
    private final MatchSeatRepository matchSeatRepository;
    private final TransactionRepository transactionRepository;

    public SynchronizedStrategy(MatchSeatRepository matchSeatRepository,
                                TransactionRepository transactionRepository) {
        if (matchSeatRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "MatchSeatRepository is required");
        }
        this.matchSeatRepository = matchSeatRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public BookingTransaction execute(Long fanId, Long matchId, List<Long> seatIds) {
        BookingTransaction transaction = new BookingTransaction(null, fanId, matchId,
                seatIds, SyncMechanism.SYNCHRONIZED);
        boolean booked = matchSeatRepository.bookSeatsSynchronized(matchId, seatIds);
        LocalDateTime completedAt = LocalDateTime.now();
        if (completedAt.isBefore(transaction.getCreatedAt())) {
            completedAt = transaction.getCreatedAt();
        }
        if (booked) {
            transaction.markSuccessful(completedAt);
        } else {
            transaction.markConflict("Seat not available", completedAt);
        }
        return transaction;
    }
}
