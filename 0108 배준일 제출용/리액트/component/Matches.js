import React, { useState, useEffect } from "react";
import axios from "axios";
import teamNameMapping from "./teamNameMapping";

const Matches = () => {
  const [matches, setMatches] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/matches/sorted")
      .then((response) => {
        if (response.status === 204) {
          setError("경기 일정이 없습니다.");
          setMatches([]);
        } else {
          const mappedMatches = response.data.map((match) => ({
            ...match,
            homeTeam: teamNameMapping[match.homeTeam] || match.homeTeam,
            awayTeam: teamNameMapping[match.awayTeam] || match.awayTeam,
          }));
          setMatches(mappedMatches);
          setError(null);
        }
      })
      .catch((error) => {
        console.error("경기 일정 가져오기 오류:", error);
        setError("경기 데이터를 가져오는 데 오류가 발생했습니다.");
      });
  }, []);

  return (
    <div>
      <h2>프리미어리그 경기 일정</h2>
      {error && <p className="error">{error}</p>}
      <table className="match-table">
        <thead>
          <tr>
            <th>홈팀</th>
            <th>어웨이팀</th>
            <th>경기 시간</th>
            <th>경기 상태</th>
            <th>경기 결과</th>
            <th>경기 일정</th>
          </tr>
        </thead>
        <tbody>
          {matches.map((match, index) => (
            <tr key={index}>
              <td>{match.homeTeam}</td>
              <td>{match.awayTeam}</td>
              <td>{new Date(match.matchDate).toLocaleString("ko-KR")}</td>
              <td>{match.status}</td>
              <td>
                {match.status === "FINISHED"
                  ? `${match.homeScore} - ${match.awayScore}`
                  : "경기 예정"}
              </td>
              <td>{match.matchday || "정보 없음"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Matches;
