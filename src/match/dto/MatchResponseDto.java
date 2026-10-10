package match.dto;

import java.time.LocalDateTime;
import match.model.SaleStatus;

public class MatchResponseDto {
    private Long id;
    private Long stadiumId;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private SaleStatus saleStatus;

    public MatchResponseDto(Long id, Long stadiumId, String homeTeam, String awayTeam,
            LocalDateTime startTime, SaleStatus saleStatus) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
        this.saleStatus = saleStatus;
    }

    public Long getId() {
        return id;
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
