package com.football;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.football.FootballMatchService;

@SuppressWarnings("unused")
@Service
public class FootballMatchScheduler {

    private final FootballMatchService footballMatchService;

    // 생성자 주입
    public FootballMatchScheduler(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }

    // 하루에 한 번 API 데이터를 갱신하는 메서드
    @Scheduled(fixedRate = 86400000) // 하루에 한 번 (밀리초 단위)
    public void updateMatches() {
        footballMatchService.fetchAndSaveMatches();  // 외부 API에서 경기 데이터를 가져와 저장
    }
}
