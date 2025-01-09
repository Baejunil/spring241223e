import React, { useState, useEffect } from "react";
import axios from "axios";
import teamNameMapping from "./teamNameMapping";
import playerNameMapping from "./playerNameMapping"; // 선수 매핑 추가

const Scorers = () => {
  const [scorers, setScorers] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/v4/competitions/PL/scorers", {
        headers: { "X-Auth-Token": "79135d8c253c4950a98fdbe31c622c0d" },
      })
      .then((response) => {
        const mappedScorers = response.data.scorers.map((scorer) => ({
          playerName: playerNameMapping[scorer.player.name] || scorer.player.name, // 선수 이름 매핑
          teamName: teamNameMapping[scorer.team.name] || scorer.team.name,
          goals: scorer.goals,
        }));

        setScorers(mappedScorers);
        setError(null);
      })
      .catch((error) => {
        console.error("득점 순위 데이터 가져오기 오류:", error);
        setError("득점 순위 데이터를 가져오는 데 오류가 발생했습니다.");
      });
  }, []);

  return (
    <div>
      <h2>프리미어리그 득점 순위</h2>
      {error && <p className="error">{error}</p>}
      <table className="scorers-table">
        <thead>
          <tr>
            <th>선수</th>
            <th>팀</th>
            <th>득점</th>
          </tr>
        </thead>
        <tbody>
          {scorers.map((scorer, index) => (
            <tr key={index}>
              <td>{scorer.playerName}</td>
              <td>{scorer.teamName}</td>
              <td>{scorer.goals}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Scorers;
