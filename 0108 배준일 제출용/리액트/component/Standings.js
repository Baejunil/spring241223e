import React, { useState, useEffect } from "react";
import axios from "axios";
import teamNameMapping from "./teamNameMapping";

const Standings = () => {
  const [standings, setStandings] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("/v4/competitions/PL/standings", {
        headers: { "X-Auth-Token": "79135d8c253c4950a98fdbe31c622c0d" },
      })
      .then((response) => {
        const mappedStandings = response.data.standings.map((group) => ({
          ...group,
          table: group.table.map((team) => ({
            ...team,
            team: {
              ...team.team,
              name: teamNameMapping[team.team.name] || team.team.name,
            },
          })),
        }));
        setStandings(mappedStandings);
        setError(null);
      })
      .catch((error) => {
        console.error("순위 데이터 가져오기 오류:", error);
        setError("순위 데이터를 가져오는 데 오류가 발생했습니다.");
      });
  }, []);

  return (
    <div>
      <h2>프리미어리그 순위</h2>
      {error && <p className="error">{error}</p>}
      <table className="standings-table">
        <thead>
          <tr>
            <th>순위</th>
            <th>팀</th>
            <th>경기</th>
            <th>승</th>
            <th>무</th>
            <th>패</th>
            <th>득점</th>
            <th>실점</th>
            <th>골차</th>
            <th>승점</th>
          </tr>
        </thead>
        <tbody>
          {standings.map((group) =>
            group.table.map((team, index) => (
              <tr key={index}>
                <td>{team.position}</td>
                <td>{team.team.name}</td>
                <td>{team.playedGames}</td>
                <td>{team.won}</td>
                <td>{team.draw}</td>
                <td>{team.lost}</td>
                <td>{team.goalsFor}</td>
                <td>{team.goalsAgainst}</td>
                <td>{team.goalDifference}</td>
                <td>{team.points}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Standings;
