package com.football;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FootballMatchService {

    private static final Logger logger = LoggerFactory.getLogger(FootballMatchService.class);
    private final FootballApiProperties footballApiProperties;  // FootballApiProperties 주입
    private final FootballMatchRepository footballMatchRepository;

    // 생성자에서 FootballApiProperties와 FootballMatchRepository 주입받기
    public FootballMatchService(FootballApiProperties footballApiProperties, FootballMatchRepository footballMatchRepository) {
        this.footballApiProperties = footballApiProperties;
        this.footballMatchRepository = footballMatchRepository;
    }

    public List<FootballMatch> getMatchesSortedByDate() {
        try {
            return footballMatchRepository.findAllByOrderByMatchDateAsc();  // 날짜 순으로 경기를 정렬하여 반환
        } catch (Exception e) {
            logger.error("Error fetching matches sorted by date from database", e);
            throw new RuntimeException("Failed to fetch sorted matches from database", e);
        }
    }

    // 외부 API에서 경기를 가져와서 저장하는 메서드
    // 외부 API에서 경기를 가져와서 저장하는 메서드
    public void fetchAndSaveMatches() {
        try {
            JSONArray matches = fetchExternalMatches();  // 외부 API에서 경기를 가져옴
            logger.info("Fetched {} matches from API", matches.length());

            Set<FootballMatch> uniqueMatches = new HashSet<>();  // 중복된 경기를 방지하기 위해 Set 사용

            // API에서 받은 경기 데이터를 처리
            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);
                FootballMatch footballMatch = createFootballMatchFromJson(match);
                uniqueMatches.add(footballMatch);  // Set에 추가
            }

            // 중복되지 않은 경기를 데이터베이스에 저장
            for (FootballMatch match : uniqueMatches) {
                if (!footballMatchRepository.existsByHomeTeamAndAwayTeamAndMatchDate(match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate())) {
                    footballMatchRepository.save(match);
                    logger.info("Saved match: {} vs {} on {}", match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate());
                } else {
                    logger.info("Skipping duplicate match: {} vs {} on {}", match.getHomeTeam(), match.getAwayTeam(), match.getMatchDate());
                }
            }

        } catch (Exception e) {
            logger.error("Error fetching and saving matches", e);
            throw new RuntimeException("Failed to fetch and save matches", e);
        }
    }
    @Autowired
    private FootballLeagueService footballLeagueService;

    // 리그 순위를 출력하는 메서드
    public void printLeagueStandings() {
        try {
            JSONArray standings = footballLeagueService.fetchLeagueStandings("PL"); // Premier League의 ID 사용
            for (int i = 0; i < standings.length(); i++) {
                JSONObject team = standings.getJSONObject(i);
                logger.info("Team: {}, Points: {}", team.getString("team"), team.getInt("points"));
            }
        } catch (Exception e) {
            logger.error("Error fetching league standings", e);
        }
    }


    // 외부 API에서 경기를 가져오는 메서드
    public JSONArray fetchExternalMatches() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", footballApiProperties.getToken());  // token을 FootballApiProperties로 가져옴

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String apiUrl = footballApiProperties.getUrl();  // url을 FootballApiProperties로 가져옴
        logger.info("Calling API URL: {}", apiUrl);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        logger.info("API 호출 응답 상태 코드: {}", response.getStatusCode());

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getJSONArray("matches");  // 응답에서 "matches" 배열을 추출
        } else {
            logger.error("Failed to fetch matches: HTTP {}", response.getStatusCode());
            throw new RuntimeException("Failed to fetch matches: " + response.getStatusCode());
        }
    }

    // JSON 객체에서 FootballMatch 객체 생성
    private FootballMatch createFootballMatchFromJson(JSONObject match) {
        // 경기 세부 정보 추출
        String homeTeam = match.getJSONObject("homeTeam").optString("name", "정보 없음");
        String awayTeam = match.getJSONObject("awayTeam").optString("name", "정보 없음");
        String utcDateStr = match.getString("utcDate");
        String status = match.getString("status");
        JSONObject scoreObj = match.getJSONObject("score").optJSONObject("fullTime");
        Integer homeScore = (scoreObj != null) ? scoreObj.optInt("homeTeam", 0) : 0;
        Integer awayScore = (scoreObj != null) ? scoreObj.optInt("awayTeam", 0) : 0;
        String matchday = match.optString("matchday", "정보 없음");
        LocalDateTime matchDate = convertToKST(utcDateStr);

        // 팀 이름을 한국어로 변환
        homeTeam = TeamNameMapper.getKoreanTeamName(homeTeam);
        awayTeam = TeamNameMapper.getKoreanTeamName(awayTeam);

        return new FootballMatch(homeTeam, awayTeam, matchDate, matchday, status, homeScore, awayScore);
    }

    // UTC 시간을 KST(한국 표준시)로 변환하는 메서드
    public LocalDateTime convertToKST(String utcDateStr) {
        try {
            ZonedDateTime utcDate = ZonedDateTime.parse(utcDateStr);
            ZonedDateTime kstDate = utcDate.withZoneSameInstant(ZoneOffset.of("+09:00"));
            return kstDate.toLocalDateTime();
        } catch (Exception e) {
            logger.error("Error converting UTC to KST", e);
            throw new RuntimeException("Failed to convert UTC to KST", e);
        }
    }
}
