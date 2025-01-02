package com.football;



import com.football.FootballMatch;
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
        FootballMatch savedMatch = footballMatchRepository.save(match);

        // 저장된 경기 확인
        assertThat(savedMatch).isNotNull();
        assertThat(savedMatch.getId()).isGreaterThan(0);
        assertThat(savedMatch.getTeamA()).isEqualTo("Team A");
    }
}
