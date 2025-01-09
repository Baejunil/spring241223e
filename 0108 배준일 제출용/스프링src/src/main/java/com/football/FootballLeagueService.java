package com.football;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballLeagueService {

    private final FootballApiProperties footballApiProperties;  // API 속성을 주입받기 위한 변수

    public FootballLeagueService(FootballApiProperties footballApiProperties) {
        this.footballApiProperties = footballApiProperties;
    }

    // 리그 순위를 가져오는 메서드
    public JSONArray fetchLeagueStandings(String leagueId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", footballApiProperties.getToken()); // API 토큰 설정

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String apiUrl = String.format("%s/competitions/%s/standings", footballApiProperties.getUrl(), leagueId); // 리그 순위 API URL

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getJSONArray("standings"); // 응답에서 "standings" 배열을 추출
        } else {
            throw new RuntimeException("Failed to fetch league standings: " + response.getStatusCode());
        }
    }
}
