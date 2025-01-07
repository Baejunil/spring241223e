import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MatchSchedule.css";
import PostBoard from "./PostBoard";

const MatchSchedule = () => {
  const [matches, setMatches] = useState([]);  // EPL 경기 일정
  const [standings, setStandings] = useState([]);  // EPl 순위
  const [currentView, setCurrentView] = useState("matches");  // 현재 보고 있는 화면
  const [error, setError] = useState(null);  // 오류 메시지 관리

  // 경기 일정 데이터 가져오기
  useEffect(() => {
    if (currentView === "matches") {
      axios
        .get("http://localhost:8080/api/matches/sorted")
        .then((response) => {
          if (response.status === 204) {
            setError("경기 일정이 없습니다.");
            setMatches([]);  // 경기 일정이 없으면 빈 배열로 설정
          } else if (response.data && Array.isArray(response.data)) {
            setMatches(response.data);  // 경기 일정 데이터 처리
            setError(null);
          } else {
            setError("경기 데이터를 가져오는 데 문제가 발생했습니다.");
          }
        })
        .catch((error) => {
          console.error("경기 일정 가져오기 오류:", error);
          setError("경기 데이터를 가져오는 데 오류가 발생했습니다.");
        });
    }
  }, [currentView]);
  
  axios
  .get("http://localhost:8080/api/matches/sorted")
  .then((response) => {
    if (response.status === 204) {
      setError("경기 일정이 없습니다.");
      setMatches([]);  // 빈 배열 처리
    } else {
      setMatches(response.data);
      setError(null);
    }
  })
  .catch((error) => {
    if (error.response) {
      // 서버가 응답을 반환한 경우 (400 또는 다른 오류)
      console.error("서버 오류 발생:", error.response.data);
      setError(`서버 오류: ${error.response.data.message || "알 수 없는 오류"}`);
    } else if (error.request) {
      // 요청이 보내졌지만 응답이 없는 경우
      console.error("서버 응답 없음:", error.request);
      setError("서버 응답이 없습니다.");
    } else {
      // 오류를 발생시킨 요청 설정 중 문제 발생
      console.error("오류 발생:", error.message);
      setError("오류 발생: " + error.message);
    }
  });


  useEffect(() => {
    if (currentView === "standings") {
      axios
        .get("https://api.football-data.org/v4/competitions/PL/standings", {
          headers: { "X-Auth-Token": "79135d8c253c4950a98fdbe31c622c0d" }
        })
        .then((response) => {
          console.log("순위 데이터 응답:", response.data); // 응답 데이터 확인
          setStandings(response.data.standings);
          setError(null);
        })
        .catch((error) => {
          console.error("네트워크 오류 발생:", error); // 네트워크 오류 로깅
          if (error.response) {
            console.error("서버 응답:", error.response.data); // 서버 오류 메시지
          } else if (error.request) {
            console.error("요청이 완료되지 않았습니다:", error.request); // 요청 문제
          } else {
            console.error("오류 발생:", error.message); // 기타 오류
          }
          setError("순위 데이터를 가져오는 데 오류가 발생했습니다.");
        });
    }
  }, [currentView]);
  


  return (
    <div className="container">
      <h1>축구 일정 및 자유게시판</h1>

      {/* 버튼들 */}
      <div className="view-buttons">
        <button onClick={() => setCurrentView("matches")}>프리미어리그 보기</button>
        <button onClick={() => setCurrentView("standings")}>순위 보기</button>
        <button onClick={() => setCurrentView("board")}>게시판 보기</button>
      </div>

      {/* 경기 일정 */}
      {currentView === "matches" && (
        <div className="match-schedule">
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
                  <td>{match.homeTeam || "정보 없음"}</td>
                  <td>{match.awayTeam || "정보 없음"}</td>
                  <td>{new Date(match.matchDate).toLocaleString("ko-KR") || "정보 없음"}</td>
                  <td>{match.status || "정보 없음"}</td>
                  <td>
                    {match.status === "FINISHED" ? (
                      `${match.homeScore} - ${match.awayScore}`
                    ) : (
                      "경기 예정"
                    )}
                  </td>
                  <td>{match.matchday || "정보 없음"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* 순위 데이터 */}
      {currentView === "standings" && (
        <div className="standings">
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
                <th>득점</th>
              </tr>
            </thead>
            <tbody>
              {standings.map((group, index) => (
                group.table.map((team, teamIndex) => (
                  <tr key={teamIndex}>
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
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* 자유게시판 화면 */}
      {currentView === "board" && <PostBoard />}
    </div>
  );
};

export default MatchSchedule;
