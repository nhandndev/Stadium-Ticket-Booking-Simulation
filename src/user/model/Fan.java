package user.model;

public class Fan extends User {
    private static final int MAX_TICKETS_PER_TRANSACTION = 4;

    public Fan(Long id, String fullName, String email, String passwordHash,
            String phone, UserStatus status) {
        super(id, fullName, email, passwordHash, phone, UserRole.FAN, status);
    }

    public boolean canBook(int ticketCount) {
        return ticketCount >= 1 && ticketCount <= MAX_TICKETS_PER_TRANSACTION;
    }
}
