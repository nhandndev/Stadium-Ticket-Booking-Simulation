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
        String name = request.getFullName() != null ? request.getFullName().trim() : "";
        String email = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : "";
        String phone = request.getPhone() != null ? request.getPhone().trim() : "";
        String password = request.getPassword() != null ? request.getPassword().trim() : "";

        if (this.fanRepository.findByEmail(email) != null || this.staffRepository.findByEmail(email) != null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email already exists");
        }
        Fan saved = this.fanRepository.save(new Fan(null, name, email, password, phone, UserStatus.ACTIVE));
        return new UserResponseDto(saved.getId(), saved.getFullName(), saved.getEmail(),
                saved.getPhone(), saved.getRole(), saved.getStatus());
    }

    public List<UserResponseDto> searchFans(String keyword) {
        List<UserResponseDto> results = new ArrayList<>();
        for (Fan fan : this.fanRepository.search(keyword)) {
            results.add(new UserResponseDto(fan.getId(), fan.getFullName(), fan.getEmail(),
                    fan.getPhone(), fan.getRole(), fan.getStatus()));
        }
        return results;
    }

    public List getMyTickets(Long fanId) {
        return Collections.emptyList();
    }
}
