import React, { useState, useEffect } from "react";
import axios from "axios";
import moment from "moment"; // moment.js 임포트

const MatchSchedule = () => {
  const [matches, setMatches] = useState([]);
  const [error, setError] = useState(null); // 오류 상태 추가

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/matches")
      .then((response) => {
        console.log(response.data); // 서버에서 응답받은 데이터 확인
        if (response.data && Array.isArray(response.data)) {
          setMatches(response.data); // 받은 데이터로 상태 업데이트
        } else {
          setError("올바른 경기 데이터를 받지 못했습니다.");
        }
      })
      .catch((error) => {
        console.error("Error fetching matches:", error);
        setError("경기 데이터를 가져오는 데 오류가 발생했습니다.");
      });
  }, []);

  return (
    <div>
      <h1>축구 경기 일정 (외부 데이터)</h1>
      {error && <p style={{ color: "red" }}>{error}</p>} {/* 오류 메시지 표시 */}

      {matches.length === 0 ? (
        <p>경기 일정이 없습니다.</p>
      ) : (
        <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th>홈팀</th>
              <th>어웨이팀</th>
              <th>경기 시간</th>
            </tr>
          </thead>
          <tbody>
            {matches.map((match, index) => {
              // 날짜 처리: moment.js로 utcDate를 포맷팅
              const formattedDate = match.matchDate
                ? moment(match.matchDate).format("YYYY-MM-DD HH:mm:ss")
                : "Invalid Date"; // 날짜가 유효한 경우 포맷팅, 그렇지 않으면 "Invalid Date"

              return (
                <tr key={index}>
                  <td>{match.homeTeam || "정보 없음"}</td>
                  <td>{match.awayTeam || "정보 없음"}</td>
                  <td>{formattedDate}</td> {/* 포맷팅된 날짜 표시 */}
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default MatchSchedule;
