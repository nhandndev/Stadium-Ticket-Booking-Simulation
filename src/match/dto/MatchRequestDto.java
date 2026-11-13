package match.dto;

import java.time.LocalDateTime;

public class MatchRequestDto {
    private Long stadiumId;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;

    public MatchRequestDto(Long stadiumId, String homeTeam, String awayTeam,
            LocalDateTime startTime) {
        this.stadiumId = stadiumId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
    }

    public Long getStadiumId() {
        return this.stadiumId;
    }

    public String getHomeTeam() {
        return this.homeTeam;
    }

    public String getAwayTeam() {
        return this.awayTeam;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }
}
