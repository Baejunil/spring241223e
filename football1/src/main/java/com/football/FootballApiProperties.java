package com.football;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "football.api")
public class FootballApiProperties {
    
    private String url;
    private String token; // key를 token으로 변경

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() { // getKey()를 getToken()으로 변경
        return token;
    }

    public void setToken(String token) { // setKey()를 setToken()으로 변경
        this.token = token;
    }
}
