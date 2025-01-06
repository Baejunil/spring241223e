package com.football;
import java.util.HashMap;
import java.util.Map;

public class TeamNameMapper {
    private static final Map<String, String> teamNameMapping = new HashMap<>();

    static {
        // 한국어 팀 이름 매핑
        teamNameMapping.put("BSC Young Boys", "BSC 영 보이스");
        teamNameMapping.put("Aston Villa FC", "애스턴 빌라 FC");
        teamNameMapping.put("Juventus FC", "유벤투스 FC");
        teamNameMapping.put("PSV", "PSV 에인트호번");
        teamNameMapping.put("Sporting Clube de Portugal", "스포르팅 CP");
        teamNameMapping.put("Lille OSC", "릴 OSC");
        teamNameMapping.put("FC Bayern München", "바이에른 뮌헨");
        teamNameMapping.put("GNK Dinamo Zagreb", "디나모 자그레브");
        teamNameMapping.put("Real Madrid CF", "레알 마드리드 CF");
        teamNameMapping.put("AC Milan", "AC 밀란");
        teamNameMapping.put("Liverpool FC", "리버풀 FC");
        teamNameMapping.put("Bologna FC 1909", "볼로냐 FC 1909");
        teamNameMapping.put("AC Sparta Praha", "AC 스파르타 프라하");
        teamNameMapping.put("FC Red Bull Salzburg", "레드불 잘츠부르크");
        teamNameMapping.put("Manchester City FC", "맨체스터 시티 FC");
        teamNameMapping.put("FC Internazionale Milano", "인터밀란");
        teamNameMapping.put("Paris Saint-Germain FC", "파리 생제르맹 FC");
        teamNameMapping.put("Club Brugge KV", "클럽 브루지");
        teamNameMapping.put("Borussia Dortmund", "보루시아 도르트문트");
        teamNameMapping.put("Celtic FC", "셀틱 FC");
        teamNameMapping.put("ŠK Slovan Bratislava", "ŠK 슬로반 브라티슬라바");
        teamNameMapping.put("Feyenoord Rotterdam", "페예노르트 로테르담");
        teamNameMapping.put("FK Crvena Zvezda", "FK 레드 스타");
        teamNameMapping.put("AS Monaco FC", "AS 모나코 FC");
        teamNameMapping.put("Stade Brestois 29", "스타드 브레스트");
        teamNameMapping.put("SK Sturm Graz", "SK 슈투름 그라츠");
        teamNameMapping.put("RB Leipzig", "RB 라이프치히");
        teamNameMapping.put("Bayer 04 Leverkusen", "바이언 04 레버쿠젠");
        teamNameMapping.put("FC Barcelona", "FC 바르셀로나");
        teamNameMapping.put("Girona FC", "히로나 FC");
        teamNameMapping.put("Atalanta BC", "아탈란타 BC");
    }

    // 팀 이름을 한국어로 변환하는 메서드
    public static String getKoreanTeamName(String englishTeamName) {
        return teamNameMapping.getOrDefault(englishTeamName, englishTeamName); // 기본값은 영어 이름 그대로
    }
}
