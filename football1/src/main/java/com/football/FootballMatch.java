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

    // 기본 생성자
    public FootballMatch() {}

    // 파라미터를 받는 생성자
    public FootballMatch(String homeTeam, String awayTeam, LocalDateTime matchDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate != null ? matchDate : LocalDateTime.now(); // matchDate가 null이면 현재 시간으로 설정
    }

    // getter 및 setter 메서드
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

    // toString 메서드 개선
    @Override
    public String toString() {
        return String.format("FootballMatch{id=%d, homeTeam='%s', awayTeam='%s', matchDate=%s}", 
                              id, homeTeam, awayTeam, matchDate);
    }

    // equals 및 hashCode 메서드 개선
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootballMatch that = (FootballMatch) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // 개선된 hashCode
    }

    // JPA Entity Lifecycle 메서드 - 데이터베이스에 저장되기 전에 matchDate가 null이면 현재 시간을 설정
    @PrePersist
    public void onPrePersist() {
        if (this.matchDate == null) {
            this.matchDate = LocalDateTime.now(); // 경기일자가 없으면 현재 시간으로 설정
        }
    }
}
