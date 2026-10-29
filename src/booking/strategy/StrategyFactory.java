package booking.strategy;

import booking.enums.SyncMechanism;
import booking.repository.TransactionRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import match.repository.MatchSeatRepository;

public class StrategyFactory {
    private final MatchSeatRepository matchSeatRepository;
    private final TransactionRepository transactionRepository;

    public StrategyFactory(MatchSeatRepository matchSeatRepository,
                           TransactionRepository transactionRepository) {
        if (matchSeatRepository == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "MatchSeatRepository is required");
        }
        this.matchSeatRepository = matchSeatRepository;
        this.transactionRepository = transactionRepository;
    }

    public BookingStrategy create(SyncMechanism mechanism) {
        if (mechanism == SyncMechanism.SYNCHRONIZED) {
            return new SynchronizedStrategy(this.matchSeatRepository, this.transactionRepository);
        }
        throw new AppException(ErrorCode.INVALID_INPUT,
                "Unsupported synchronization mechanism: " + mechanism);
    }
}
