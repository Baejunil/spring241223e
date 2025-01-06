package com.football;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FootballMatchRunner implements CommandLineRunner {

    private final FootballMatchService footballMatchService;

    public FootballMatchRunner(FootballMatchService footballMatchService) {
        this.footballMatchService = footballMatchService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            footballMatchService.fetchAndSaveMatches();
        } catch (Exception e) {
            System.err.println("경기 데이터를 가져오는 데 실패했습니다: " + e.getMessage());
        }
    }}
