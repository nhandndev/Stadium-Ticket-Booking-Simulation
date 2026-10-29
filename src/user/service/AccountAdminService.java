package user.service;

import admin.dto.AccountRequestDto;
import admin.dto.AccountResponseDto;
import common.exception.AppException;
import common.exception.ErrorCode;
import booking.repository.BookingRepository;
import java.util.ArrayList;
import java.util.List;
import user.enums.UserRole;
import user.enums.UserStatus;
import user.model.Fan;
import user.model.Staff;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class AccountAdminService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;
    private final BookingRepository bookingRepository;

    public AccountAdminService(FanRepository fanRepository,
                               StaffRepository staffRepository, BookingRepository bookingRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
        this.bookingRepository = bookingRepository;
    }

    public AccountResponseDto createFanAccount(AccountRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Account request is required");
        }
        validateAccountRequest(request, null, null);
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Password is required");
        }
        Fan fan = new Fan(null, request.getFullName(), request.getEmail(),
                request.getPassword(), request.getPhone(), UserStatus.ACTIVE);
        Fan saved = this.fanRepository.save(fan);
        return new AccountResponseDto(saved.getId(), saved.getFullName(), saved.getEmail(),
                saved.getPhone(), saved.getRole(), saved.getStatus());
    }

    public List<AccountResponseDto> getFans() {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Fan fan : this.fanRepository.findAll()) {
            result.add(new AccountResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                    fan.getPhone(), fan.getRole(), fan.getStatus()));
        }
        return result;
    }

    public AccountResponseDto getFanDetails(Long fanId) {
        Fan fan = this.fanRepository.findById(fanId);
        if (fan == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Fan not found");
        }
        return new AccountResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                fan.getPhone(), fan.getRole(), fan.getStatus());
    }

    public List<AccountResponseDto> searchFans(String keyword) {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Fan fan : this.fanRepository.search(keyword)) {
            result.add(new AccountResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                    fan.getPhone(), fan.getRole(), fan.getStatus()));
        }
        return result;
    }

    public List<AccountResponseDto> filterFans(UserStatus status) {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Fan fan : this.fanRepository.findAll()) {
            if (status == null || fan.getStatus() == status) {
                result.add(new AccountResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                        fan.getPhone(), fan.getRole(), fan.getStatus()));
            }
        }
        return result;
    }

    public AccountResponseDto updateFan(Long fanId, AccountRequestDto request) {
        validateAccountRequest(request, fanId, null);
        Fan fan = this.fanRepository.findById(fanId);
        if (fan == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Fan not found");
        }
        String password = (request.getPassword() != null && !request.getPassword().isEmpty())
                ? request.getPassword() : fan.getPasswordHash();
        Fan replacement = new Fan(fanId, request.getFullName(), request.getEmail(),
                password, request.getPhone(), fan.getStatus());
        Fan updated = this.fanRepository.update(replacement);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto activateFan(Long fanId) {
        Fan fan = this.fanRepository.findById(fanId);
        if (fan == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Fan not found");
        }
        fan.changeStatus(UserStatus.ACTIVE);
        Fan updated = this.fanRepository.update(fan);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto deactivateFan(Long fanId) {
        Fan fan = this.fanRepository.findById(fanId);
        if (fan == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Fan not found");
        }
        fan.changeStatus(UserStatus.INACTIVE);
        Fan updated = this.fanRepository.update(fan);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public boolean deleteFan(Long fanId) {
        if (!bookingRepository.findByFanId(fanId).isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan has booking history. Deactivate the account instead.");
        }
        return this.fanRepository.delete(fanId);
    }

    public AccountResponseDto createStaffAccount(AccountRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Account request is required");
        }
        validateAccountRequest(request, null, null);
        if (request.getRole() == null || request.getRole() == UserRole.FAN
                || request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "A staff role and password are required");
        }
        Staff staff = new Staff(null, request.getFullName(), request.getEmail(),
                request.getPassword(), request.getPhone(), request.getRole(), UserStatus.ACTIVE);
        Staff saved = this.staffRepository.save(staff);
        return new AccountResponseDto(saved.getId(), saved.getFullName(), saved.getEmail(),
                saved.getPhone(), saved.getRole(), saved.getStatus());
    }

    public List<AccountResponseDto> getStaffs() {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Staff staff : this.staffRepository.findAll()) {
            result.add(new AccountResponseDto(staff.getId(), staff.getFullName(), staff.getEmail(),
                    staff.getPhone(), staff.getRole(), staff.getStatus()));
        }
        return result;
    }

    public AccountResponseDto getStaffDetails(Long staffId) {
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        return new AccountResponseDto(staff.getId(), staff.getFullName(), staff.getEmail(),
                staff.getPhone(), staff.getRole(), staff.getStatus());
    }

    public List<AccountResponseDto> searchStaff(String keyword) {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Staff staff : this.staffRepository.search(keyword)) {
            result.add(new AccountResponseDto(staff.getId(), staff.getFullName(), staff.getEmail(),
                    staff.getPhone(), staff.getRole(), staff.getStatus()));
        }
        return result;
    }

    public List<AccountResponseDto> filterStaff(UserRole role, UserStatus status) {
        List<AccountResponseDto> result = new ArrayList<>();
        for (Staff staff : this.staffRepository.findAll()) {
            if ((role == null || staff.getRole() == role)
                    && (status == null || staff.getStatus() == status)) {
                result.add(new AccountResponseDto(staff.getId(), staff.getFullName(), staff.getEmail(),
                        staff.getPhone(), staff.getRole(), staff.getStatus()));
            }
        }
        return result;
    }

    public AccountResponseDto updateStaff(Long staffId, AccountRequestDto request) {
        validateAccountRequest(request, null, staffId);
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        String password = (request.getPassword() != null && !request.getPassword().isEmpty())
                ? request.getPassword() : staff.getPasswordHash();
        Staff replacement = new Staff(staffId, request.getFullName(), request.getEmail(),
                password, request.getPhone(), staff.getRole(), staff.getStatus());
        Staff updated = this.staffRepository.update(replacement);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto activateStaff(Long staffId) {
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.changeStatus(UserStatus.ACTIVE);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto deactivateStaff(Long staffId) {
        requireAnotherAdmin(staffId);
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.changeStatus(UserStatus.INACTIVE);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto assignRole(Long staffId, UserRole role) {
        if (role == null || role == UserRole.FAN || role == UserRole.UNASSIGNED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Choose an assigned staff role");
        }
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        if (staff.getRole() != UserRole.UNASSIGNED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Use Change role for staff who already have a role");
        }
        staff.assignRole(role);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto changeRole(Long staffId, UserRole role) {
        if (role == null || role == UserRole.FAN || role == UserRole.UNASSIGNED) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Choose an assigned staff role");
        }
        if (role != UserRole.ADMIN) requireAnotherAdmin(staffId);
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.assignRole(role);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto revokeRole(Long staffId) {
        requireAnotherAdmin(staffId);
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.assignRole(UserRole.UNASSIGNED);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    private void requireAnotherAdmin(Long staffId) {
        Staff staff = staffRepository.findById(staffId);
        if (staff == null || staff.getRole() != UserRole.ADMIN || staff.getStatus() != UserStatus.ACTIVE) return;
        for (Staff other : staffRepository.findAll()) {
            if (!other.getId().equals(staffId) && other.getRole() == UserRole.ADMIN
                    && other.getStatus() == UserStatus.ACTIVE) return;
        }
        throw new AppException(ErrorCode.INVALID_INPUT, "Cannot remove the last active Admin");
    }

    private void validateAccountRequest(AccountRequestDto request, Long fanId, Long staffId) {
        if (request == null) throw new AppException(ErrorCode.INVALID_INPUT, "Account request is required");
        for (String value : new String[]{request.getFullName(), request.getEmail(), request.getPhone()}) {
            if (value == null || value.trim().isEmpty() || value.contains(",")
                    || value.contains("\n") || value.contains("\r")) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Name, email and phone must fit one nonempty CSV column");
            }
        }
        String password = request.getPassword();
        if (password != null && !password.isEmpty() && (password.length() < 8
                || password.contains(",") || password.contains("\n") || password.contains("\r"))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Password must contain at least 8 characters and fit one CSV column");
        }
        String email = request.getEmail().trim();
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid email");
        }
        Fan fan = fanRepository.findByEmail(email);
        Staff staff = staffRepository.findByEmail(email);
        if ((fan != null && !fan.getId().equals(fanId)) || (staff != null && !staff.getId().equals(staffId))) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email already exists");
        }
    }
}
