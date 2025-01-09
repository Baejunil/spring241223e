package com.football;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "http://localhost:3000") // CORS 허용
public class FootballMatchController {

    private static final Logger logger = LoggerFactory.getLogger(FootballMatchController.class);

    private final FootballMatchRepository footballMatchRepository;
    private final FootballMatchService footballMatchService;

    // 생성자 주입
    public FootballMatchController(FootballMatchRepository footballMatchRepository,
                                    FootballMatchService footballMatchService) {
        this.footballMatchRepository = footballMatchRepository;
        this.footballMatchService = footballMatchService;
    }

    // 모든 경기를 날짜 순으로 가져오는 메서드 (HTTP GET)
    @GetMapping("/sorted")
    public ResponseEntity<List<FootballMatch>> getMatchesSortedByDate() {
        try {
            List<FootballMatch> matches = footballMatchService.getMatchesSortedByDate();
            if (matches.isEmpty()) {
                logger.info("No matches found.");
                return ResponseEntity.noContent().build();  // 경기가 없으면 204 반환
            }
            logger.info("Successfully fetched {} matches.", matches.size());
            return ResponseEntity.ok(matches);  // 경기 데이터를 정상적으로 반환
        } catch (Exception e) {
            logger.error("Error fetching matches sorted by date", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // 서버 오류 발생 시 500 반환
        }
    }

    // 경기를 추가하는 메서드 (HTTP POST)
    @PostMapping
    public ResponseEntity<FootballMatch> addMatch(@RequestBody FootballMatch match) {
        try {
            // 중복 경기 확인 (홈팀, 어웨이팀, 경기 일정으로 중복 확인)
            if (footballMatchRepository.existsByHomeTeamAndAwayTeamAndMatchDate(match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate())) {
                logger.warn("Conflict: Match between {} and {} on {} already exists.",
                        match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(null); // 중복되는 경기가 있으면 409 반환
            }

            // 중복되지 않으면 저장
            FootballMatch savedMatch = footballMatchRepository.save(match);
            logger.info("Match saved: {} vs {} on {}", match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMatch); // 생성된 경기 반환
        } catch (Exception e) {
            logger.error("Error saving match: {}", match, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 오류 발생 시 500 반환
        }
    }

    // 외부 API에서 경기를 가져오는 메서드 (HTTP GET)
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchMatchesFromApi() {
        try {
            footballMatchService.fetchAndSaveMatches();  // 외부 API에서 경기를 가져와 저장
            logger.info("Successfully fetched and saved matches from the external API.");
            return ResponseEntity.ok("경기 데이터를 성공적으로 가져왔습니다.");
        } catch (Exception e) {
            logger.error("Error fetching matches from API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("경기 데이터를 가져오는 데 오류가 발생했습니다."); // 서버 오류 발생 시 500 반환
        }
    }

    // 특정 경기 조회 API (HTTP GET)
    @GetMapping("/{id}")
    public ResponseEntity<FootballMatch> getMatchById(@PathVariable Long id) {
        try {
            return footballMatchRepository.findById(id)
                    .map(match -> {
                        logger.info("Match found: {} vs {} on {}", match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate());
                        return ResponseEntity.ok(match);
                    })
                    .orElseGet(() -> {
                        logger.warn("Match with ID {} not found", id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 경기 없으면 404 반환
                    });
        } catch (Exception e) {
            logger.error("Error fetching match with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 서버 오류 발생 시 500 반환
        }
    }
}
