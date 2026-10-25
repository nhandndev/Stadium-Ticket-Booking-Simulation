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

    public UserRole getRole() { return role; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
}
