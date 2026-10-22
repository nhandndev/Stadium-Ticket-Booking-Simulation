package user.dto;

import user.enums.UserRole;
import user.enums.UserStatus;

public class UserResponseDto {
    private final Long id;
    private final String fullName;
    private final String email;
    private final String phone;
    private final UserRole role;
    private final UserStatus status;

    public UserResponseDto(Long id, String fullName, String email, String phone,
                           UserRole role, UserStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public UserRole getRole() {
        return this.role;
    }

    public UserStatus getStatus() {
        return this.status;
    }
}
