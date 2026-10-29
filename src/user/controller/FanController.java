package user.controller;

import auth.controller.AuthController;
import common.exception.AppException;
import common.exception.ErrorCode;
import java.util.List;
import ticket.dto.TicketResponseDto;
import user.enums.UserRole;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;
import user.service.FanService;

public class FanController {
    private final FanService fanService;
    private final AuthController authController;

    public FanController(FanService fanService, AuthController authController) {
        this.fanService = fanService;
        this.authController = authController;
    }

    public UserResponseDto register(RegisterFanRequestDto request) {
        return this.fanService.register(request);
    }

    public List<UserResponseDto> searchFans(String keyword) {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || (user.getRole() != UserRole.SELLER && user.getRole() != UserRole.ADMIN)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Seller or Admin login required");
        }
        return this.fanService.searchFans(keyword);
    }

    public List<TicketResponseDto> getMyTickets(Long fanId) {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || user.getRole() != UserRole.FAN || !user.getId().equals(fanId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan can only see own tickets");
        }
        return this.fanService.getMyTickets(fanId);
    }
}
