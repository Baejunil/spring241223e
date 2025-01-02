import React, { useState, useEffect } from "react";
import axios from "axios";

const MatchSchedule = () => {
  const [matches, setMatches] = useState([]);

  useEffect(() => {
    // Spring Boot의 외부 API 엔드포인트 호출
    axios
      .get("http://localhost:8080/api/external-matches")
      .then((response) => {
        const externalMatches = JSON.parse(response.data); // JSON 데이터를 파싱
        setMatches(externalMatches);
      })
      .catch((error) => {
        console.error("Error fetching matches from external API:", error);
      });
  }, []);

  return (
    <div>
      <h1>축구 경기 일정 (외부 데이터)</h1>
      <ul>
        {matches.map((match, index) => (
          <li key={index}>
            {match.homeTeam.name} vs {match.awayTeam.name} - {match.utcDate}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MatchSchedule;
