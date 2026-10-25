package admin.service;

import admin.data.DataGenerator;
import admin.dto.SystemSummaryDto;
import admin.model.AuditLog;
import admin.repository.AuditLogRepository;
import java.util.List;
import match.repository.MatchRepository;
import stadium.repository.StadiumRepository;
import user.repository.FanRepository;
import user.repository.StaffRepository;

public class AdminService {
    private final DataGenerator dataGenerator;
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminService(DataGenerator dataGenerator, FanRepository fanRepository,
                        StaffRepository staffRepository, StadiumRepository stadiumRepository,
                        MatchRepository matchRepository, AuditLogRepository auditLogRepository) {
        this.dataGenerator = dataGenerator;
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
        this.stadiumRepository = stadiumRepository;
        this.matchRepository = matchRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public void generateCsvDataset(int recordCount) {
        this.dataGenerator.generateAll(recordCount);
    }

    public SystemSummaryDto getSystemSummary() {
        return new SystemSummaryDto(
                this.fanRepository.findAll().size(),
                this.staffRepository.findAll().size(),
                this.stadiumRepository.findAll().size(),
                this.matchRepository.findAll().size(),
                0,
                0
        );
    }

    public List<AuditLog> getAuditLogs() {
        return this.auditLogRepository.findRecent(30);
    }
}
