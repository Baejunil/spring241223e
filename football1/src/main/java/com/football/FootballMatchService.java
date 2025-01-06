package com.football;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class FootballMatchService {

    private static final Logger logger = LoggerFactory.getLogger(FootballMatchService.class);

    @Value("${football.api.url}")
    private String apiUrl;

    @Value("${football.api.token}")
    private String apiKey;

    private final FootballMatchRepository footballMatchRepository;

    public FootballMatchService(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    /**
     * Fetch matches from external API and save to database
     */
    public void fetchAndSaveMatches() {
        try {
            JSONArray matches = fetchExternalMatches();
            logger.info("Fetched {} matches from API", matches.length());

            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);

                // Extract match details
                String homeTeam = match.getJSONObject("homeTeam").getString("name");
                String awayTeam = match.getJSONObject("awayTeam").getString("name");
                String utcDateStr = match.getString("utcDate");

                // Convert match date to KST
                LocalDateTime matchDate = convertToKST(utcDateStr);

                // Convert team names to Korean
                homeTeam = TeamNameMapper.getKoreanTeamName(homeTeam);
                awayTeam = TeamNameMapper.getKoreanTeamName(awayTeam);

                // Save match to database
                FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam, matchDate);
                footballMatchRepository.save(footballMatch);
                logger.info("Saved match: {} vs {} on {}", homeTeam, awayTeam, matchDate);
            }
        } catch (Exception e) {
            logger.error("Error fetching and saving matches", e);
            throw new RuntimeException("Failed to fetch and save matches", e);
        }
    }

    /**
     * Fetch matches from external API
     * @return JSONArray of matches
     */
    public JSONArray fetchExternalMatches() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        logger.info("Calling API URL: {}", apiUrl);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray matches = jsonResponse.getJSONArray("matches");
            return matches;
        } else {
            logger.error("Failed to fetch matches: HTTP {}", response.getStatusCode());
            throw new RuntimeException("Failed to fetch matches: " + response.getStatusCode());
        }
    }

    /**
     * Convert UTC time to KST (Korean Standard Time)
     */
    public LocalDateTime convertToKST(String utcDateStr) {
        // UTC 시간 문자열을 LocalDateTime으로 파싱
        LocalDateTime utcDate = LocalDateTime.parse(utcDateStr.replace("Z", ""));
        
        // UTC 시간에서 대한민국 시간(KST)으로 변환
        ZonedDateTime utcZoned = utcDate.atZone(ZoneOffset.UTC);
        ZonedDateTime kstZoned = utcZoned.withZoneSameInstant(ZoneOffset.ofHours(9)); // KST (UTC +9)
        
        return kstZoned.toLocalDateTime(); // KST의 LocalDateTime 반환
    }
}
