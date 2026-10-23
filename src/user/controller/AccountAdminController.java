package user.controller;

import admin.dto.AccountRequestDto;
import admin.dto.AccountResponseDto;

import java.util.List;

import user.enums.UserRole;
import user.enums.UserStatus;
import user.service.AccountAdminService;


public class AccountAdminController {
    private final AccountAdminService accountAdminService;

    public AccountAdminController(AccountAdminService accountAdminService) {
        this.accountAdminService = accountAdminService;
    }

    public AccountResponseDto createFanAccount(AccountRequestDto request) {
        return this.accountAdminService.createFanAccount(request);
    }

    public List<AccountResponseDto> getFans() {
        return this.accountAdminService.getFans();
    }

    public AccountResponseDto getFanDetails(Long fanId) {
        return this.accountAdminService.getFanDetails(fanId);
    }

    public List<AccountResponseDto> searchFans(String keyword) {
        return this.accountAdminService.searchFans(keyword);
    }

    public List<AccountResponseDto> filterFans(UserStatus status) {
        return this.accountAdminService.filterFans(status);
    }

    public AccountResponseDto updateFan(Long fanId, AccountRequestDto request) {
        return this.accountAdminService.updateFan(fanId, request);
    }

    public AccountResponseDto activateFan(Long fanId) {
        return this.accountAdminService.activateFan(fanId);
    }

    public AccountResponseDto deactivateFan(Long fanId) {
        return this.accountAdminService.deactivateFan(fanId);
    }

    public boolean deleteFan(Long fanId) {
        return this.accountAdminService.deleteFan(fanId);
    }

    public AccountResponseDto createStaffAccount(AccountRequestDto request) {
        return this.accountAdminService.createStaffAccount(request);
    }

    public List<AccountResponseDto> getStaffs() {
        return this.accountAdminService.getStaffs();
    }

    public AccountResponseDto getStaffDetails(Long staffId) {
        return this.accountAdminService.getStaffDetails(staffId);
    }

    public List<AccountResponseDto> searchStaff(String keyword) {
        return this.accountAdminService.searchStaff(keyword);
    }

    public List<AccountResponseDto> filterStaff(UserRole role, UserStatus status) {
        return this.accountAdminService.filterStaff(role, status);
    }

    public AccountResponseDto updateStaff(Long staffId, AccountRequestDto request) {
        return this.accountAdminService.updateStaff(staffId, request);
    }

    public AccountResponseDto activateStaff(Long staffId) {
        return this.accountAdminService.activateStaff(staffId);
    }

    public AccountResponseDto deactivateStaff(Long staffId) {
        return this.accountAdminService.deactivateStaff(staffId);
    }

    public AccountResponseDto assignRole(Long staffId, UserRole role) {
        return this.accountAdminService.assignRole(staffId, role);
    }

    public AccountResponseDto changeRole(Long staffId, UserRole role) {
        return this.accountAdminService.changeRole(staffId, role);
    }

    public AccountResponseDto revokeRole(Long staffId) {
        return this.accountAdminService.revokeRole(staffId);
    }
}

