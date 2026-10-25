package stadium.service;

import common.exception.AppException;
import common.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import stadium.dto.StadiumRequestDto;
import stadium.dto.StadiumResponseDto;
import stadium.model.Seat;
import stadium.model.Section;
import stadium.model.Stadium;
import stadium.repository.SeatRepository;
import stadium.repository.SectionRepository;
import stadium.repository.StadiumRepository;

public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final SectionRepository sectionRepository;
    private final SeatRepository seatRepository;

    public StadiumService(StadiumRepository stadiumRepository,
                          SectionRepository sectionRepository,
                          SeatRepository seatRepository) {
        this.stadiumRepository = stadiumRepository;
        this.sectionRepository = sectionRepository;
        this.seatRepository = seatRepository;
    }

    public StadiumResponseDto createStadium(StadiumRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Stadium request is required");
        }
        Stadium stadium = new Stadium(null, request.getName(), request.getAddress());
        Stadium saved = this.stadiumRepository.save(stadium);
        return new StadiumResponseDto(saved.getId(), saved.getName(), saved.getAddress());
    }

    public List<StadiumResponseDto> getStadiums() {
        List<StadiumResponseDto> result = new ArrayList<>();
        for (Stadium stadium : this.stadiumRepository.findAll()) {
            result.add(new StadiumResponseDto(stadium.getId(), stadium.getName(), stadium.getAddress()));
        }
        return result;
    }

    public StadiumResponseDto getStadiumDetails(Long stadiumId) {
        Stadium stadium = this.stadiumRepository.findById(stadiumId);
        if (stadium == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Stadium not found");
        }
        return new StadiumResponseDto(stadium.getId(), stadium.getName(), stadium.getAddress());
    }

    public StadiumResponseDto updateStadium(Long stadiumId, StadiumRequestDto request) {
        Stadium stadium = this.stadiumRepository.findById(stadiumId);
        if (stadium == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Stadium not found");
        }
        stadium.updateDetails(request.getName(), request.getAddress());
        Stadium updated = this.stadiumRepository.update(stadium);
        return new StadiumResponseDto(updated.getId(), updated.getName(), updated.getAddress());
    }

    public boolean deleteStadium(Long stadiumId) {
        return this.stadiumRepository.delete(stadiumId);
    }

    public Section createSection(Long stadiumId, String name) {
        Section section = new Section(null, stadiumId, name);
        return this.sectionRepository.save(section);
    }

    public List<Section> getSections(Long stadiumId) {
        return this.sectionRepository.findByStadiumId(stadiumId);
    }

    public Section getSectionDetails(Long sectionId) {
        Section section = this.sectionRepository.findById(sectionId);
        if (section == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Section not found");
        }
        return section;
    }

    public Section updateSection(Long sectionId, String name) {
        Section section = this.sectionRepository.findById(sectionId);
        if (section == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Section not found");
        }
        section.rename(name);
        return this.sectionRepository.update(section);
    }

    public boolean deleteSection(Long sectionId) {
        return this.sectionRepository.delete(sectionId);
    }

    public Seat createSeat(Long sectionId, String rowLabel, int seatNumber) {
        Seat seat = new Seat(null, sectionId, rowLabel, seatNumber, true);
        return this.seatRepository.save(seat);
    }

    public List<Seat> buildSeatMap(Long sectionId) {
        return this.seatRepository.findBySectionId(sectionId);
    }

    public Seat getSeatDetails(Long seatId) {
        Seat seat = this.seatRepository.findById(seatId);
        if (seat == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Seat not found");
        }
        return seat;
    }

    public Seat updateSeat(Long seatId, String rowLabel, int seatNumber) {
        Seat seat = this.seatRepository.findById(seatId);
        if (seat == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Seat not found");
        }
        seat.updateLocation(rowLabel, seatNumber);
        return this.seatRepository.update(seat);
    }

    public boolean deleteSeat(Long seatId) {
        return this.seatRepository.delete(seatId);
    }

    public Seat activateSeat(Long seatId) {
        Seat seat = this.seatRepository.findById(seatId);
        if (seat == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Seat not found");
        }
        seat.activate();
        return this.seatRepository.update(seat);
    }

    public Seat deactivateSeat(Long seatId) {
        Seat seat = this.seatRepository.findById(seatId);
        if (seat == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Seat not found");
        }
        seat.deactivate();
        return this.seatRepository.update(seat);
    }
}

