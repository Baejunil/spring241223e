package com.football;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@SpringBootTest
public class FootballMatchRepositoryTest {

    @Autowired
    private FootballMatchRepository footballMatchRepository;

    @Test
    public void testSaveFootballMatch() {
        // 테스트 데이터 생성
        String homeTeam = "Team A";
        String awayTeam = "Team B";
        LocalDateTime matchDate = LocalDateTime.now();

        // 새 경기 생성
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, matchDate);
        
        // 데이터베이스에 경기 저장
        FootballMatch savedMatch = footballMatchRepository.save(match);

        // 저장된 데이터 검증
        assertThat(savedMatch).isNotNull(); // 객체가 null이 아님을 확인
        assertThat(savedMatch.getId()).isNotNull(); // ID가 null이 아님을 확인
        assertThat(savedMatch.getId()).isGreaterThan(0); // ID가 양수인지 확인
        assertThat(savedMatch.getHomeTeam()).isEqualTo(homeTeam); // 홈 팀 확인
        assertThat(savedMatch.getAwayTeam()).isEqualTo(awayTeam); // 어웨이 팀 확인
        assertThat(savedMatch.getMatchDate()).isEqualTo(matchDate); // 경기 날짜 확인
    }
}
