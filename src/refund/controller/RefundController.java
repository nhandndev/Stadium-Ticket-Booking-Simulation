package refund.controller;

import auth.controller.AuthController;
import common.exception.AppException;
import common.exception.ErrorCode;
import refund.dto.RefundRequestDto;
import refund.dto.RefundResponseDto;
import java.util.List;
import refund.model.RefundRequest;
import refund.dto.RefundReviewRequestDto;
import refund.service.RefundService;
import user.dto.UserResponseDto;
import user.enums.UserRole;
import user.enums.UserStatus;

public class RefundController {
    private RefundService refundService;
    private AuthController authController;

    public RefundController(RefundService refundService,
                            AuthController authController) {
        if (refundService == null || authController == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "RefundService and AuthController are required");
        }
        this.refundService = refundService;
        this.authController = authController;
    }

    public RefundResponseDto requestRefund(RefundRequestDto request) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        if (request == null || !fan.getId().equals(request.getFanId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Fan can only request own refund");
        }
        return refundService.requestRefund(request);
    }

    public List<RefundRequest> getPendingRefunds() {
        requireRole(UserRole.ADMIN);
        return refundService.getPendingRefunds();
    }

    public List<RefundResponseDto> getMyRefunds(Long fanId) {
        UserResponseDto fan = requireRole(UserRole.FAN);
        if (!fan.getId().equals(fanId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Fan can only see own refund requests");
        }
        return refundService.getMyRefunds(fanId);
    }

    public RefundResponseDto reviewRefund(RefundReviewRequestDto request) {
        UserResponseDto admin = requireRole(UserRole.ADMIN);
        if (request == null || !admin.getId().equals(request.getAdminId())) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Admin ID does not match current session");
        }
        return refundService.reviewRefund(request);
    }

    private UserResponseDto requireRole(UserRole role) {
        UserResponseDto user = authController.getCurrentUser();
        if (user == null || user.getId() == null
                || user.getStatus() != UserStatus.ACTIVE || user.getRole() != role) {
            throw new AppException(ErrorCode.INVALID_INPUT,
                    "Active " + role + " login is required");
        }
        return user;
    }
}
