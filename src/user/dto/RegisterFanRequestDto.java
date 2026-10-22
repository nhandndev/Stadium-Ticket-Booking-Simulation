package user.dto;

public class RegisterFanRequestDto {
    private final String fullName;
    private final String email;
    private final String password;
    private final String phone;

    public RegisterFanRequestDto(String fullName, String email, String password, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }
}
