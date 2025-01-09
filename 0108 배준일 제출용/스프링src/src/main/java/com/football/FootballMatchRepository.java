package com.football;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

 // matchDate 기준으로 오름차순 정렬된 경기를 가져오는 쿼리 메소드
    List<FootballMatch> findAllByOrderByMatchDateAsc();
    
    
    // 동일한 홈팀, 원정팀, 경기 일자가 이미 존재하는지 확인하는 메서드
    FootballMatch findByHomeTeamAndAwayTeamAndMatchDate(String homeTeam, String awayTeam, LocalDateTime matchDate);

    // 중복된 경기 확인 메서드 (LocalDateTime으로 수정)
    boolean existsByHomeTeamAndAwayTeamAndMatchDate(String homeTeam, String awayTeam, LocalDateTime matchDate);

}
