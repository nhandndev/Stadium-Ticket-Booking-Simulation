package common.csv;

import common.entity.BaseEntity;
import common.exception.AppException;
import common.exception.ErrorCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public abstract class CsvRepository<T extends BaseEntity> {
    private final Path filePath;
    public CsvRepository(Path filePath) {
        if(filePath == null) {
            throw new AppException(ErrorCode.INVALID_INPUT,"File Path is required");
        }
        this.filePath = filePath.toAbsolutePath().normalize();
    }
    public List<T> findAll() {
        this.ensureFile();
        try {
            List<String> lines = Files.readAllLines(this.filePath, StandardCharsets.UTF_8);
            List<T> entities = new ArrayList<>();
            Set<Long> ids = new HashSet<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.trim().isEmpty()) continue;
                try {
                    T entity = this.parseLine(line);
                    if(entity.getId() == null || entity.getId() <= 0 || !ids.add(entity.getId())) {
                        throw new AppException(ErrorCode.CSV_ERROR, "Invalid or duplicate ID");
                    }
                    entities.add(entity);
                } catch (AppException | IllegalArgumentException e){
                    throw new AppException(ErrorCode.CSV_ERROR, this.filePath.getFileName()+" line "+(i+1)+": "+e.getMessage());
                }
            }
            return entities;
        }catch(IOException e) {
            throw  new AppException(ErrorCode.CSV_ERROR,"Cannot read CSV file");
        }
    }
    public T findById(Long id) {
        if(id == null || id <= 0) throw new AppException(ErrorCode.INVALID_INPUT,"Invalid ID , ID Must be greater than 0");
        for(T entity : this.findAll()) {
            if(entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
    public List<T> findByCondition(Predicate<T> condition) {
        if(condition == null){
            throw new AppException(ErrorCode.INVALID_INPUT,"Condition is required");
        }
        List<T> result = new ArrayList<>();
        for(T entity : this.findAll()) {
            if(condition.test(entity)) {
                result.add(entity);
            }
        }
        return result;
    }
    public T save(T entity) {
        if (entity == null) throw new AppException(ErrorCode.INVALID_INPUT, "Entity is required");

        List<T> entities = this.findAll();
        Long originalId = entity.getId();
        if (originalId == null) {
            long maxId = 0;
            for (T current : entities) {
                maxId = Math.max(maxId, current.getId());
            }
            if (maxId == Long.MAX_VALUE) {
                throw new AppException(ErrorCode.INVALID_INPUT, "ID limit reached");
            }
            entity.setId(maxId + 1);
        } else {
            if (originalId <= 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "ID must be positive");
            }
            for (T current : entities) {
                if (current.getId().equals(originalId)) {
                    throw new AppException(ErrorCode.INVALID_INPUT, "Duplicate ID");
                }
            }
        }
        entities.add(entity);
        try {
            this.writeAll(entities);
        } catch (AppException e) {
            entity.setId(originalId);
            throw e;
        }
        return entity;
    }
    public T update(T entity) {
        if(entity == null) throw new AppException(ErrorCode.INVALID_INPUT, "Entity is required");
        if(entity.getId() == null || entity.getId() <= 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Invalid ID");
        }
        List<T> entities = this.findAll();
        for(int i = 0 ; i< entities.size() ; i++) {
            if(entities.get(i).getId().equals(entity.getId())) {
                entities.set(i, entity);
                this.writeAll(entities);
                return entity;
            }
        }
        throw new AppException(ErrorCode.NOT_FOUND, "Entity not found");
    }
    public boolean delete(Long id) {
        if(id == null || id <= 0){
            throw new AppException(ErrorCode.INVALID_INPUT,"Invalid ID");
        }
        List<T> entities = this.findAll();
        for(int i = 0 ; i< entities.size() ; i++) {
            if(entities.get(i).getId().equals(id)) {
                entities.remove(i);
                this.writeAll(entities);
                return true;
            }
        }
        return false;
    }
    private void ensureFile(){
        try{
            Files.createDirectories(this.filePath.getParent());
            if(!Files.exists(this.filePath)){
                Files.createFile(this.filePath);
            }
        }catch (IOException e){
            throw new AppException(ErrorCode.CSV_ERROR,"Cannot create file");
        }
    }
    private void writeAll(List<T> entities) {
        List<String> lines = new ArrayList<>();
        for (T entity : entities) {
            lines.add(this.formatLine(entity));
        }
        this.ensureFile();
        Path temporaryFile = null;
        try {
            temporaryFile = Files.createTempFile(this.filePath.getParent(), "csv-", ".tmp");
            Files.write(temporaryFile, lines, StandardCharsets.UTF_8);
            try {
                Files.move(temporaryFile, this.filePath,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporaryFile, this.filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new AppException(ErrorCode.CSV_ERROR,
                    "Cannot write " + this.filePath + ": " + e.getMessage());
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException e) {
                }
            }
        }
    }
    public abstract T parseLine(String line);
    public abstract String formatLine(T entity);
}
