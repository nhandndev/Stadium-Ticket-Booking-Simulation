package auth.controller;

import auth.service.AuthService;
import user.dto.LoginRequestDto;
import user.dto.ProfileUpdateRequestDto;
import user.dto.UserResponseDto;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public UserResponseDto login(LoginRequestDto request) {
        return this.authService.login(request);
    }

    public void logout() {
        this.authService.logout();
    }

    public UserResponseDto getCurrentUser() {
        return this.authService.getCurrentUser();
    }

    public UserResponseDto updateCurrentUserProfile(ProfileUpdateRequestDto request) {
        return this.authService.updateCurrentUserProfile(request);
    }
}
