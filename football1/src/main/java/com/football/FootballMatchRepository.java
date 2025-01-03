package com.football;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // List import 추가
import java.time.LocalDateTime; // LocalDateTime import 추가
@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
    // 홈팀을 기준으로 경기를 찾는 메서드
    List<FootballMatch> findByHomeTeam(String homeTeam);

    // 홈팀과 어웨이팀을 기준으로 경기를 찾는 메서드
    List<FootballMatch> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam);

    // 날짜를 기준으로 경기를 찾는 메서드
    List<FootballMatch> findByMatchDateAfter(LocalDateTime date);
}
