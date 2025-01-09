package com.football;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "football_match")
public class FootballMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @Column(name = "matchday")
    private String matchday;

    @Column(name = "status")
    private String status;

    // 기본 생성자
    public FootballMatch() {}

    // 파라미터를 받는 생성자
    public FootballMatch(String homeTeam, String awayTeam, LocalDateTime matchDate, String matchday, String status, Integer homeScore, Integer awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate != null ? matchDate : LocalDateTime.now(); // matchDate가 null이면 현재 시간으로 설정
        this.matchday = matchday;
        this.status = status;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("FootballMatch{id=%d, homeTeam='%s', awayTeam='%s', matchDate=%s, homeScore=%d, awayScore=%d, matchday='%s', status='%s'}", 
                              id, homeTeam, awayTeam, matchDate, homeScore, awayScore, matchday, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootballMatch that = (FootballMatch) o;
        return Objects.equals(homeTeam, that.homeTeam) &&
               Objects.equals(awayTeam, that.awayTeam) &&
               Objects.equals(matchDate, that.matchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, matchDate);
    }

    @PrePersist
    public void onPrePersist() {
        if (this.matchDate == null) {
            this.matchDate = LocalDateTime.now(); // 경기일자가 없으면 현재 시간으로 설정
        }
    }
}
