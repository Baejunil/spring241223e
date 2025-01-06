package com.football;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FootballMatchScheduler {

    private final FootballMatchService footballMatchService;
    private static final Logger logger = LoggerFactory.getLogger(FootballMatchScheduler.class);

    // 생성자 주입
    public FootballMatchScheduler(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }

    // 하루에 한 번 API 데이터를 갱신하는 메서드
    @Scheduled(fixedRate = 86400000) // 하루에 한 번 (밀리초 단위)
    public void updateMatches() {
        try {
            footballMatchService.fetchAndSaveMatches();  // 외부 API에서 경기 데이터를 가져와 저장
            logger.info("경기 데이터를 성공적으로 가져와 갱신했습니다.");
        } catch (Exception e) {
            logger.error("경기 데이터를 갱신하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
}
