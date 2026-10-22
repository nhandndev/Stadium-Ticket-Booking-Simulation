package user.controller;

import java.util.List;
import user.dto.RegisterFanRequestDto;
import user.dto.UserResponseDto;
import user.service.FanService;

public class FanController {
    private final FanService fanService;

    public FanController(FanService fanService) {
        this.fanService = fanService;
    }

    public UserResponseDto register(RegisterFanRequestDto request) {
        return this.fanService.register(request);
    }

    public List<UserResponseDto> searchFans(String keyword) {
        return this.fanService.searchFans(keyword);
    }

    public List getMyTickets(Long fanId) {
        return this.fanService.getMyTickets(fanId);
    }
}
