package match.model;

import common.entity.BaseEntity;
import java.time.LocalDateTime;

public class Match extends BaseEntity {
    private Long stadiumId;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private SaleStatus saleStatus;

    public Match(Long id, Long stadiumId, String homeTeam, String awayTeam,
            LocalDateTime startTime, SaleStatus saleStatus) {
        super(id);
        this.stadiumId = stadiumId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
        this.saleStatus = saleStatus;
    }

    public void reschedule(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void changeSaleStatus(SaleStatus status) {
        this.saleStatus = status;
    }

    public Long getStadiumId() {
        return stadiumId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }
}
