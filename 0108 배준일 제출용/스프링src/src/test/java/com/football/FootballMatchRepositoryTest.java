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
        String homeTeam = "팀A";  // 홈팀
        String awayTeam = "팀B";  // 어웨이팀
        LocalDateTime matchDate = LocalDateTime.now();  // 현재 시간
        String matchday = "1";  // 임의의 matchday 설정
        String status = "예정";  // 임의의 경기 상태 설정
        Integer homeScore = 0;  // 홈팀 점수
        Integer awayScore = 0;  // 어웨이팀 점수

        // 새 경기 생성 (FootballMatch의 생성자에 맞게 수정)
        FootballMatch match = new FootballMatch(homeTeam, awayTeam, matchDate, matchday, status, homeScore, awayScore);

        // 데이터베이스에 경기 저장
        FootballMatch savedMatch = footballMatchRepository.save(match);

        // 저장된 데이터 검증
        assertThat(savedMatch).isNotNull(); // 객체가 null이 아님을 확인
        assertThat(savedMatch.getId()).isNotNull(); // ID가 null이 아님을 확인
        assertThat(savedMatch.getId()).isGreaterThan(0); // ID가 양수인지 확인
        assertThat(savedMatch.getHomeTeam()).isEqualTo(homeTeam); // 홈팀 확인
        assertThat(savedMatch.getAwayTeam()).isEqualTo(awayTeam); // 어웨이팀 확인
        assertThat(savedMatch.getMatchDate()).isEqualTo(matchDate); // 경기 날짜 확인
        assertThat(savedMatch.getMatchday()).isEqualTo(matchday); // 경기 일자 확인
        assertThat(savedMatch.getStatus()).isEqualTo(status); // 경기 상태 확인
        assertThat(savedMatch.getHomeScore()).isEqualTo(homeScore); // 홈팀 점수 확인
        assertThat(savedMatch.getAwayScore()).isEqualTo(awayScore); // 어웨이팀 점수 확인
    }
}
