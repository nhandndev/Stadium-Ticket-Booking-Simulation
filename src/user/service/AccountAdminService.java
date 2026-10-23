package user.service;

import admin.dto.AccountRequestDto;
import admin.dto.AccountResponseDto;
import common.exception.AppException;
import common.exception.ErrorCode;

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

    public AccountAdminService(FanRepository fanRepository,
                               StaffRepository staffRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
    }

    public AccountResponseDto createFanAccount(AccountRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Account request is required");
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
        return this.fanRepository.delete(fanId);
    }

    public AccountResponseDto createStaffAccount(AccountRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Account request is required");
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
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        String password = (request.getPassword() != null && !request.getPassword().isEmpty())
                ? request.getPassword() : staff.getPasswordHash();
        UserRole role = request.getRole() != null ? request.getRole() : staff.getRole();
        Staff replacement = new Staff(staffId, request.getFullName(), request.getEmail(),
                password, request.getPhone(), role, staff.getStatus());
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
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.assignRole(role);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }

    public AccountResponseDto changeRole(Long staffId, UserRole role) {
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
        Staff staff = this.staffRepository.findById(staffId);
        if (staff == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Staff not found");
        }
        staff.assignRole(UserRole.UNASSIGNED);
        Staff updated = this.staffRepository.update(staff);
        return new AccountResponseDto(updated.getId(), updated.getFullName(), updated.getEmail(),
                updated.getPhone(), updated.getRole(), updated.getStatus());
    }
}

