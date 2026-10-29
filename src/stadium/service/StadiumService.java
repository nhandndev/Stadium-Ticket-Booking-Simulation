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
import match.repository.MatchRepository;
import match.repository.MatchSeatRepository;
import match.repository.TicketPriceRepository;
import match.model.Match;
import match.model.MatchSeat;
import match.enums.SeatStatus;

public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final SectionRepository sectionRepository;
    private final SeatRepository seatRepository;
    private final MatchRepository matchRepository;
    private final MatchSeatRepository matchSeatRepository;
    private final TicketPriceRepository ticketPriceRepository;

    public StadiumService(StadiumRepository stadiumRepository,
                          SectionRepository sectionRepository,
                          SeatRepository seatRepository, MatchRepository matchRepository,
                          MatchSeatRepository matchSeatRepository, TicketPriceRepository ticketPriceRepository) {
        this.stadiumRepository = stadiumRepository;
        this.sectionRepository = sectionRepository;
        this.seatRepository = seatRepository;
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
        this.ticketPriceRepository = ticketPriceRepository;
    }

    public StadiumResponseDto createStadium(StadiumRequestDto request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Stadium request is required");
        }
        Stadium stadium = new Stadium(null, requireText(request.getName()), requireText(request.getAddress()));
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
        if (request == null) throw new AppException(ErrorCode.INVALID_INPUT, "Stadium request is required");
        Stadium stadium = this.stadiumRepository.findById(stadiumId);
        if (stadium == null) {
            throw new AppException(ErrorCode.NOT_FOUND, "Stadium not found");
        }
        stadium.updateDetails(requireText(request.getName()), requireText(request.getAddress()));
        Stadium updated = this.stadiumRepository.update(stadium);
        return new StadiumResponseDto(updated.getId(), updated.getName(), updated.getAddress());
    }

    public boolean deleteStadium(Long stadiumId) {
        getStadiumDetails(stadiumId);
        if (!sectionRepository.findByStadiumId(stadiumId).isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Delete the stadium's sections first");
        }
        for (Match match : matchRepository.findAll()) {
            if (match.getStadiumId().equals(stadiumId)) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Stadium is used by a match");
            }
        }
        return this.stadiumRepository.delete(stadiumId);
    }

    public Section createSection(Long stadiumId, String name) {
        getStadiumDetails(stadiumId);
        Section section = new Section(null, stadiumId, requireText(name));
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
        section.rename(requireText(name));
        return this.sectionRepository.update(section);
    }

    public boolean deleteSection(Long sectionId) {
        getSectionDetails(sectionId);
        if (!seatRepository.findBySectionId(sectionId).isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Delete the section's seats first");
        }
        for (Match match : matchRepository.findAll()) {
            if (ticketPriceRepository.findPrice(match.getId(), sectionId) != null) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Section is used by ticket pricing");
            }
        }
        return this.sectionRepository.delete(sectionId);
    }

    public Seat createSeat(Long sectionId, String rowLabel, int seatNumber) {
        getSectionDetails(sectionId);
        String row = requireText(rowLabel);
        validateSeatLocation(sectionId, row, seatNumber, null);
        Seat seat = new Seat(null, sectionId, row, seatNumber, true);
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
        requireNoInventory(seatId);
        String row = requireText(rowLabel);
        validateSeatLocation(seat.getSectionId(), row, seatNumber, seatId);
        seat.updateLocation(row, seatNumber);
        return this.seatRepository.update(seat);
    }

    public boolean deleteSeat(Long seatId) {
        getSeatDetails(seatId);
        requireNoInventory(seatId);
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
        for (Match match : matchRepository.findAll()) {
            MatchSeat inventory = matchSeatRepository.find(match.getId(), seatId);
            if (inventory != null && inventory.getStatus() != SeatStatus.AVAILABLE) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat has a booking and cannot be deactivated");
            }
        }
        seat.deactivate();
        return this.seatRepository.update(seat);
    }

    private void requireNoInventory(Long seatId) {
        for (Match match : matchRepository.findAll()) {
            if (matchSeatRepository.find(match.getId(), seatId) != null) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat is used by match inventory");
            }
        }
    }

    private void validateSeatLocation(Long sectionId, String row, int number, Long exceptId) {
        if (number <= 0) throw new AppException(ErrorCode.INVALID_INPUT, "Seat number must be positive");
        for (Seat seat : seatRepository.findBySectionId(sectionId)) {
            if (!seat.getId().equals(exceptId) && seat.getRowLabel().equalsIgnoreCase(row)
                    && seat.getSeatNumber() == number) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Seat location already exists");
            }
        }
    }

    private String requireText(String value) {
        if (value == null || value.trim().isEmpty() || value.contains(",")
                || value.contains("\n") || value.contains("\r")) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Text is required and must fit one CSV column");
        }
        return value.trim();
    }
}
