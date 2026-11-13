package admin.dto;

import user.enums.UserRole;

public class AccountRequestDto {
    private final String fullName;
    private final String email;
    private final String password;
    private final String phone;
    private final UserRole role;

    public AccountRequestDto(String fullName, String email, String password,
                             String phone, UserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public UserRole getRole() { return this.role; }
    public String getFullName() { return this.fullName; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public String getPhone() { return this.phone; }
}
