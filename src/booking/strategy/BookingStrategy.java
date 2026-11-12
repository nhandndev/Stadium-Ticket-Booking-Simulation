package booking.strategy;

import booking.model.BookingTransaction;
import java.util.List;

public interface BookingStrategy {
    BookingTransaction execute(Long fanId, Long matchId, List<Long> seatIds);
    BookingTransaction execute(Long bookingId, Long fanId, Long matchId, List<Long> seatIds);
}
