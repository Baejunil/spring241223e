import React, { useState } from "react";
import Matches from "./Matches";
import Standings from "./Standings";
import Scorers from "./Scorers";
import PostBoard from "./PostBoard";
import "./MatchSchedule.css";
import "./PostBoard.css";
const MatchSchedule = () => {
  const [currentView, setCurrentView] = useState("matches");

  return (
    <div className="container">
      <h1>축구 일정 및 자유게시판</h1>
      <div className="view-buttons">
        <button onClick={() => setCurrentView("matches")}>프리미어리그 보기</button>
        <button onClick={() => setCurrentView("standings")}>순위 보기</button>
        <button onClick={() => setCurrentView("scorers")}>득점 순위</button>
        <button onClick={() => setCurrentView("board")}>게시판 보기</button>
      </div>
      {currentView === "matches" && <Matches />}
      {currentView === "standings" && <Standings />}
      {currentView === "scorers" && <Scorers />}
      {currentView === "board" && <PostBoard />}
    </div>
  );
};

export default MatchSchedule;
