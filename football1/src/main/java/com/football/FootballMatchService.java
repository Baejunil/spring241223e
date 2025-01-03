package com.football;

import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballMatchService {

    // application.properties에서 API URL 값을 주입
    @Value("${football.api.url}")
    private String apiUrl;

    // application.properties에서 API 키 값을 주입
    @Value("${football.api.key}")
    private String apiKey;

    private final FootballMatchRepository footballMatchRepository;

    public FootballMatchService(FootballMatchRepository footballMatchRepository) {
        this.footballMatchRepository = footballMatchRepository;
    }

    public void fetchAndSaveMatches() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray matches = jsonResponse.getJSONArray("matches");

            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);
                FootballMatch footballMatch = new FootballMatch(
                    match.getJSONObject("homeTeam").getString("name"),
                    match.getJSONObject("awayTeam").getString("name"),
                    LocalDateTime.parse(match.getString("utcDate"))
                );
                footballMatchRepository.save(footballMatch);
            }
        } else {
            throw new RuntimeException("Failed to fetch matches: " + response.getStatusCode());
        }
    }

	public JSONArray fetchExternalMatches() {
		// TODO Auto-generated method stub
		return null;
	}
}