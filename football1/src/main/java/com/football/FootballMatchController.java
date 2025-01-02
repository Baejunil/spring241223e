package com.football;

import com.football.FootballMatch;
import com.football.FootballMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class FootballMatchController {

    @Autowired
    private FootballMatchRepository repository;

    // 모든 경기를 가져오는 메서드 (HTTP GET)
    @GetMapping
    public List<FootballMatch> getAllMatches() {
        return repository.findAll();  // 데이터베이스에서 모든 경기 반환
    }

    // 경기를 추가하는 메서드 (HTTP POST)
    @PostMapping
    public FootballMatch addMatch(@RequestBody FootballMatch match) {
        return repository.save(match);  // 데이터베이스에 경기 저장
    }
    @GetMapping("/api/external-matches")
    public String fetchExternalMatches() {
        JSONArray matches = footballMatchService.fetchMatchesFromAPI();
        return matches.toString(); // JSON 데이터를 React로 반환
    }
}
