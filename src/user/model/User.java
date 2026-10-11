package user.model;

import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;

public class User extends BaseEntity {
    private String fullName;
    private final String email;
    private final String passwordHash;
    private String phone;
    private UserRole role;
    private UserStatus status;

    public User(Long id, String fullName, String email, String passwordHash,
            String phone, UserRole role, UserStatus status) {
        super(id);
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public void updateProfile(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public void changeStatus(UserStatus status) {
        if (status == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Status is required");
        }
        this.status = status;
    }

    public void changeRole(UserRole role) {
        if (!(this instanceof Staff) || role == null || role == UserRole.FAN) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid staff role");
        }
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }
}
