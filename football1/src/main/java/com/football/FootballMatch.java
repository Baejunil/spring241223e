package com.football;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FootballMatch {

    @Id
    @GeneratedValue
    private Long id;
    private String teamA;
    private String teamB;
    private LocalDateTime matchDate;

    // 기본 생성자
    public FootballMatch() {
    }

    // 매개변수가 있는 생성자
    public FootballMatch(String teamA, String teamB, LocalDateTime matchDate) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchDate = matchDate;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "FootballMatch{" +
                "id=" + id +
                ", teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                ", matchDate=" + matchDate +
                '}';
    }
}
