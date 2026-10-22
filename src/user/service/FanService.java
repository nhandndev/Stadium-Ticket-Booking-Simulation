package user.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;
import user.enums.UserStatus;
import user.model.Fan;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class FanService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;

    public FanService(FanRepository fanRepository, StaffRepository staffRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
    }

    public UserResponseDto register(RegisterFanRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Registration is required");
        }
        String name = this.clean(request.getFullName(), "Full name");
        String email = this.clean(request.getEmail(), "Email").toLowerCase();
        String phone = this.clean(request.getPhone(), "Phone");
        String password = this.clean(request.getPassword(), "Password");

        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid email");
        }
        if (this.fanRepository.findByEmail(email) != null || this.staffRepository.findByEmail(email) != null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email already exists");
        }
        Fan saved = this.fanRepository.save(new Fan(null, name, email, password, phone, UserStatus.ACTIVE));
        return this.toResponse(saved);
    }

    public List<UserResponseDto> searchFans(String keyword) {
        List<UserResponseDto> results = new ArrayList<>();
        for (Fan fan : this.fanRepository.search(keyword)) {
            results.add(this.toResponse(fan));
        }
        return results;
    }

    public List getMyTickets(Long fanId) {
        return Collections.emptyList();
    }

    private String clean(String input, String label) {
        if (input == null || input.trim().isEmpty() || input.contains(",")
                || input.contains("\"") || input.contains("\n") || input.contains("\r")) {
            throw new AppException(ErrorCode.INVALID_INPUT, label + " is invalid");
        }
        return input.trim();
    }

    private UserResponseDto toResponse(Fan fan) {
        return new UserResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                fan.getPhone(), fan.getRole(), fan.getStatus());
    }
}
