package com.football;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class FootballMatchController {

    private final FootballMatchRepository footballMatchRepository;
    private final FootballMatchService footballMatchService;

    // 생성자 주입
    public FootballMatchController(FootballMatchRepository footballMatchRepository, 
                                    FootballMatchService footballMatchService) {
        this.footballMatchRepository = footballMatchRepository;
        this.footballMatchService = footballMatchService;
    }

    // 모든 경기를 가져오는 메서드 (HTTP GET)
    @GetMapping
    public List<FootballMatch> getAllMatches() {
        return footballMatchRepository.findAll(); // 데이터베이스에서 모든 경기 반환
    }

    // 경기를 추가하는 메서드 (HTTP POST)
    @PostMapping
    public FootballMatch addMatch(@RequestBody FootballMatch match) {
        return footballMatchRepository.save(match); // 데이터베이스에 경기 저장
    }

    // 추가적인 메서드가 필요하다면 여기서 처리
    // 예시: 외부 API에서 데이터를 가져오는 메서드
    @GetMapping("/fetch")
    public void fetchMatchesFromApi() {
        footballMatchService.fetchAndSaveMatches();  // 외부 API에서 경기를 가져와 저장
    }
}
