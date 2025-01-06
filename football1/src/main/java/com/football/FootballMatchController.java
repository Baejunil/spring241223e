package com.football;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "http://localhost:3000") // CORS 허용
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
    public ResponseEntity<List<FootballMatch>> getAllMatches() {
        List<FootballMatch> matches = footballMatchRepository.findAll(); // 데이터베이스에서 모든 경기 반환
        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 데이터가 없으면 204 반환
        }
        return ResponseEntity.ok(matches);
    }

    // 경기를 추가하는 메서드 (HTTP POST)
    @PostMapping
    public ResponseEntity<FootballMatch> addMatch(@RequestBody FootballMatch match) {
        FootballMatch savedMatch = footballMatchRepository.save(match); // 데이터베이스에 경기 저장
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatch); // 생성된 경기 반환
    }

    // 외부 API에서 경기를 가져오는 메서드 (HTTP GET)
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchMatchesFromApi() {
        try {
            footballMatchService.fetchAndSaveMatches();  // 외부 API에서 경기를 가져와 저장
            return ResponseEntity.ok("경기 데이터를 성공적으로 가져왔습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("경기 데이터를 가져오는 데 오류가 발생했습니다.");
        }
    }
}
