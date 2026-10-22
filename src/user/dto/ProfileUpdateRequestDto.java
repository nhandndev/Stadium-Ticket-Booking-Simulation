package user.dto;

public class ProfileUpdateRequestDto {
    private final String fullName;
    private final String phone;

    public ProfileUpdateRequestDto(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPhone() {
        return this.phone;
    }
}
