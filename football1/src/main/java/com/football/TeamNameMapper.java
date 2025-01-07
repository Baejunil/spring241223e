package com.football;
import java.util.HashMap;
import java.util.Map;

public class TeamNameMapper {
    private static final Map<String, String> teamNameMapping = new HashMap<>();

    static {
        // 한국어 팀 이름 매핑
    	teamNameMapping.put("Arsenal FC", "아스날 FC");
    	teamNameMapping.put("Aston Villa FC", "애스턴 빌라 FC");
    	teamNameMapping.put("AFC Bournemouth", "AFC 본머스");
    	teamNameMapping.put("Brentford FC", "브렌트포드 FC");
    	teamNameMapping.put("Brighton & Hove Albion FC", "브라이튼 앤 호브 앨비언 FC");
    	teamNameMapping.put("Chelsea FC", "첼시 FC");
    	teamNameMapping.put("Crystal Palace FC", "크리스털 팰리스 FC");
    	teamNameMapping.put("Everton FC", "에버튼 FC");
    	teamNameMapping.put("Fulham FC", "풀럼 FC");
    	teamNameMapping.put("Southampton FC", "사우스햄튼 FC");
    	teamNameMapping.put("Leicester City FC", "레스터 시티 FC");
    	teamNameMapping.put("Liverpool FC", "리버풀 FC");
    	teamNameMapping.put("Manchester City FC", "맨체스터 시티 FC");
    	teamNameMapping.put("Manchester United FC", "맨체스터 유나이티드 FC");
    	teamNameMapping.put("Newcastle United FC", "뉴캐슬 유나이티드 FC");
    	teamNameMapping.put("Nottingham Forest FC", "노팅엄 포레스트 FC");
    	teamNameMapping.put("Ipswich Town FC", "입스위치 타운 FC");
    	teamNameMapping.put("Tottenham Hotspur FC", "토트넘 홋스퍼 FC");
    	teamNameMapping.put("West Ham United FC", "웨스트햄 유나이티드 FC");
    	teamNameMapping.put("Wolverhampton Wanderers FC", "울버햄튼 원더러스 FC");

    }

    // 팀 이름을 한국어로 변환하는 메서드
    public static String getKoreanTeamName(String englishTeamName) {
        return teamNameMapping.getOrDefault(englishTeamName, englishTeamName); // 기본값은 영어 이름 그대로
    }
}
