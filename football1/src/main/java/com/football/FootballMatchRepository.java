package com.football;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
    
    // 홈 팀으로 경기를 찾는 메서드
    List<FootballMatch> findByHomeTeam(String homeTeam);
    
    // 어웨이 팀으로 경기를 찾는 메서드
    List<FootballMatch> findByAwayTeam(String awayTeam);
    
    // 날짜 범위로 경기를 찾는 메서드
    List<FootballMatch> findByMatchDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    // 동일한 홈팀, 원정팀, 경기 일자가 이미 존재하는지 확인하는 메서드
    FootballMatch findByHomeTeamAndAwayTeamAndMatchDate(String homeTeam, String awayTeam, LocalDateTime matchDate);
}