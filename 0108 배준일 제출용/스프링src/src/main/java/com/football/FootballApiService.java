package com.football;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballApiService {

    private static final Logger logger = LoggerFactory.getLogger(FootballApiService.class);

    @Value("${football.api.url}")
    private String apiUrl;

    @Value("${football.api.token}")
    private String apiKey;

    public JSONArray fetchMatchesFromAPI(String season, String dateFrom, String dateTo) {
        RestTemplate restTemplate = new RestTemplate();

        // URL 생성 (필터 동적 추가)
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        boolean hasQuery = false;

        if (season != null) {
            urlBuilder.append(hasQuery ? "&" : "?").append("season=").append(season);
            hasQuery = true;
        }
        if (dateFrom != null) {
            urlBuilder.append(hasQuery ? "&" : "?").append("dateFrom=").append(dateFrom);
            hasQuery = true;
        }
        if (dateTo != null) {
            urlBuilder.append(hasQuery ? "&" : "?").append("dateTo=").append(dateTo);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate로 API 요청
        ResponseEntity<String> response;
        try {
            logger.info("Making API request to URL: {}", urlBuilder.toString());
            response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            logger.error("Error occurred while making API request: {}", e.getMessage());
            throw new RuntimeException("Error occurred while making API request: " + e.getMessage());
        }

        // 응답 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                logger.info("Successfully received response from API. Status: {}", response.getStatusCode());
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getJSONArray("matches");
            } catch (Exception e) {
                logger.error("Failed to parse the response JSON: {}", e.getMessage());
                throw new RuntimeException("Failed to parse the response JSON: " + e.getMessage());
            }
        } else {
            logger.error("Failed to fetch matches. Status code: {} - {}", response.getStatusCode(),
                    ((HttpStatus) response.getStatusCode()).getReasonPhrase());
            throw new RuntimeException("Failed to fetch matches. Status code: " + response.getStatusCode() +
                    ", Message: " + ((HttpStatus) response.getStatusCode()).getReasonPhrase());
        }
    }
}
