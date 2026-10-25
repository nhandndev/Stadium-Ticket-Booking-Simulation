package stadium.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import stadium.model.Section;

import java.nio.file.Path;
import java.util.List;

public class SectionRepository extends CsvRepository<Section> {
    public SectionRepository(Path filePath) {
        super(filePath);
    }
    public List<Section> findByStadiumId(Long  stadiumId) {
        if(stadiumId == null || stadiumId <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT,"Stadium Id must be positive or not null");
        }
        return findByCondition(section -> section.getStadiumId().equals(stadiumId));
    }
    @Override
    public Section parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 3 section columns");
        }
        long id = Long.parseLong(parts[0]);
        long stadiumId = Long.parseLong(parts[1]);
        return new Section(id, stadiumId, parts[2]);
    }

    @Override
    public String formatLine(Section entity) {
        return entity.getId() + "," + entity.getStadiumId() + "," + entity.getName();
    }
}
