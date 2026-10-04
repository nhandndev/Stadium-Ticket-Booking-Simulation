package stadium.repository;

import common.csv.CsvRepository;
import common.exception.AppException;
import common.exception.ErrorCode;
import stadium.model.Stadium;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class StadiumRepository extends CsvRepository<Stadium> {
    public StadiumRepository(Path filePath) {
        super(filePath);
    }
    public List<Stadium> findByName(String name){
        if(name ==null || name.trim().isEmpty()){
            throw  new AppException(ErrorCode.INVALID_INPUT,"Name is required");
        }
        String keyword = name.trim().toLowerCase(Locale.ROOT);
        return findByCondition(stadium ->  stadium.getName().toLowerCase(Locale.ROOT).contains(keyword));
    }
    @Override
    public Stadium parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new AppException(ErrorCode.CSV_ERROR, "Expected 3 stadium columns");
        }
        long id = Long.parseLong(parts[0]);
        if (id <= 0 || !isValidText(parts[1]) || !isValidText(parts[2])) {
            throw new AppException(ErrorCode.CSV_ERROR, "Invalid stadium data");
        }
        return new Stadium(id, parts[1], parts[2]);
    }
    @Override
    public String formatLine(Stadium entity) {
        if (entity == null || entity.getId() == null || entity.getId() <= 0
                || !isValidText(entity.getName()) || !isValidText(entity.getAddress())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid stadium CSV data");
        }
        return entity.getId() + "," + entity.getName() + "," + entity.getAddress();

    }
    private boolean isValidText(String value) {
        return value != null && !value.trim().isEmpty()
                && !value.contains(",") && !value.contains("\"")
                && !value.contains("\n") && !value.contains("\r");
    }
}
