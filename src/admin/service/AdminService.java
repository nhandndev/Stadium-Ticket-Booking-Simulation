package admin.service;

import admin.data.DataGenerator;
import admin.model.AuditLog;
import admin.repository.AuditLogRepository;

import java.util.List;


public class AdminService {
    private final DataGenerator dataGenerator;
    private final AuditLogRepository auditLogRepository;

    public AdminService(DataGenerator dataGenerator, AuditLogRepository auditLogRepository) {
        this.dataGenerator = dataGenerator;
        this.auditLogRepository = auditLogRepository;
    }

    public void generateCsvDataset(int recordCount) {
        dataGenerator.generateAll(recordCount);
    }

    public void clearGeneratedData() {
        dataGenerator.clearGeneratedData();
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findRecent(30);
    }
}

