package user.model;

import common.exception.AppException;
import common.exception.ErrorCode;

public class Staff extends User {
    public Staff(Long id, String fullName, String email, String passwordHash,
            String phone, UserRole role, UserStatus status) {
        super(id, fullName, email, passwordHash, phone, role, status);
        if (role == null || role == UserRole.FAN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Staff role is required");
        }
    }

    public void assignRole(UserRole role) {
        if (role == null || role == UserRole.FAN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid staff role");
        }
        changeRole(role);
    }
}
