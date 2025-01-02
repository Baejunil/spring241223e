package com.football;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // List import 추가
import java.time.LocalDateTime; // LocalDateTime import 추가
@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
    // 필요한 추가 쿼리 메서드 작성 가능
    // 예: teamA가 "Team A"인 경기 찾기
    List<FootballMatch> findByTeamA(String teamA);
 // 예시: 팀 A와 팀 B를 기준으로 경기를 찾는 쿼리 메서드
    List<FootballMatch> findByTeamAAndTeamB(String teamA, String teamB);

    // 예시: 날짜를 기준으로 경기를 찾는 쿼리 메서드
    List<FootballMatch> findByMatchDateAfter(LocalDateTime date);

}