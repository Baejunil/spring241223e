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
        // 새 경기 생성
        FootballMatch match = new FootballMatch("Team A", "Team B", LocalDateTime.now());
        
        // 데이터베이스에 경기 저장
        FootballMatch savedMatch = footballMatchRepository.save(match);

        // 저장된 경기 확인
        assertThat(savedMatch).isNotNull();
        assertThat(savedMatch.getId()).isGreaterThan(0);
        assertThat(savedMatch.getHomeTeam()).isEqualTo("Team A");  // 수정된 부분
        assertThat(savedMatch.getAwayTeam()).isEqualTo("Team B");  // 수정된 부분
    }
}
