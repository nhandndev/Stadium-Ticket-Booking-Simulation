package auth.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import user.dto.LoginRequestDto;
import user.dto.ProfileUpdateRequestDto;
import user.dto.UserResponseDto;
import user.model.Fan;
import user.model.Staff;
import user.model.User;
import user.enums.UserRole;
import user.enums.UserStatus;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class AuthService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;
    private User currentUser;

    public AuthService(FanRepository fanRepository, StaffRepository staffRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
    }

    public UserResponseDto login(LoginRequestDto request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email and password are required");
        }
        this.currentUser = null;
        String email = request.getEmail().trim().toLowerCase();
        Fan fan = this.fanRepository.findByEmail(email);
        Staff staff = this.staffRepository.findByEmail(email);
        User found = fan != null ? fan : staff;
        if (found == null || !request.getPassword().equals(found.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid email or password");
        }
        if (found.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Account is inactive");
        }
        this.currentUser = found;
        return new UserResponseDto(found.getId(), found.getFullName(), found.getEmail(),
                found.getPhone(), found.getRole(), found.getStatus());
    }

    public void logout() {
        this.currentUser = null;
    }

    public UserResponseDto getCurrentUser() {
        if (this.currentUser == null) return null;
        return new UserResponseDto(this.currentUser.getId(), this.currentUser.getFullName(),
                this.currentUser.getEmail(), this.currentUser.getPhone(),
                this.currentUser.getRole(), this.currentUser.getStatus());
    }

    public UserResponseDto updateCurrentUserProfile(ProfileUpdateRequestDto request) {
        if (this.currentUser == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Login required");
        }
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Profile request is required");
        }
        String name = request.getFullName() != null ? request.getFullName().trim() : "";
        String phone = request.getPhone() != null ? request.getPhone().trim() : "";
        if (this.currentUser.getRole() == UserRole.FAN) {
            Fan fan = this.fanRepository.findById(this.currentUser.getId());
            if (fan == null) {
                throw new AppException(ErrorCode.NOT_FOUND, "Account no longer exists");
            }
            fan.updateProfile(name, phone);
            this.fanRepository.update(fan);
            this.currentUser = fan;
        } else {
            Staff staff = this.staffRepository.findById(this.currentUser.getId());
            if (staff == null) {
                throw new AppException(ErrorCode.NOT_FOUND, "Account no longer exists");
            }
            staff.updateProfile(name, phone);
            this.staffRepository.update(staff);
            this.currentUser = staff;
        }
        return new UserResponseDto(this.currentUser.getId(), this.currentUser.getFullName(),
                this.currentUser.getEmail(), this.currentUser.getPhone(),
                this.currentUser.getRole(), this.currentUser.getStatus());
    }
}
