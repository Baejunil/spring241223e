package com.football;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballApiService {

    @Value("${football.api.url}")
    private String apiUrl;

    @Value("${football.api.key}")
    private String apiKey;

    public JSONArray fetchMatchesFromAPI() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getJSONArray("matches");
        } else {
            throw new RuntimeException("Failed to fetch matches: " + response.getStatusCode());
        }
    }
}
