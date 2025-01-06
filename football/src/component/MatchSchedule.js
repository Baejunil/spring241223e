import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MatchSchedule.css";

const MatchSchedule = () => {
  const [matches, setMatches] = useState([]); // 경기 데이터
  const [posts, setPosts] = useState([]); // 게시글 데이터
  const [currentView, setCurrentView] = useState("matches"); // 현재 화면 상태
  const [error, setError] = useState(null); // 오류 상태

  // 경기 일정 데이터 가져오기
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/matches")
      .then((response) => {
        if (response.data && Array.isArray(response.data)) {
          setMatches(response.data);
        } else {
          setError("경기 데이터를 가져오는 데 문제가 발생했습니다.");
        }
      })
      .catch((error) => {
        setError("경기 데이터를 가져오는 데 오류가 발생했습니다.");
      });
  }, []);

  // 게시글 목록 가져오기
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/posts")
      .then((response) => {
        setPosts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching posts:", error);
      });
  }, []);

  // 게시글 추가 API 호출
  const addPost = (postContent) => {
    axios
      .post("http://localhost:8080/api/posts", { content: postContent })
      .then((response) => {
        setPosts([...posts, response.data]);
      })
      .catch((error) => {
        console.error("Error adding post:", error);
      });
  };

  return (
    <div className="container">
      <h1>축구 일정 및 자유게시판</h1>

      {/* 버튼들 */}
      <div className="view-buttons">
        <button onClick={() => setCurrentView("matches")}>챔피언스리그 보기</button>
        <button onClick={() => setCurrentView("board")}>게시판 보기</button>
      </div>

      {/* 경기 일정 화면 */}
      {currentView === "matches" && (
        <div className="match-schedule">
          <h2>챔피언스리그 경기 일정</h2>
          {error && <p className="error">{error}</p>} {/* 오류 표시 */}
          <table className="match-table">
            <thead>
              <tr>
                <th>홈팀</th>
                <th>어웨이팀</th>
                <th>경기 시간</th>
              </tr>
            </thead>
            <tbody>
              {matches.map((match, index) => (
                <tr key={index}>
                  <td>{match.homeTeam || "정보 없음"}</td>
                  <td>{match.awayTeam || "정보 없음"}</td>
                  <td>{match.matchDate || "정보 없음"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* 자유게시판 화면 */}
      {currentView === "board" && (
        <div className="post-board">
          <h2>자유게시판</h2>
          <textarea
            className="post-input"
            placeholder="여기에 댓글을 남겨주세요."
            id="postInput"
          ></textarea>
          <button
            onClick={() => {
              const postContent = document.getElementById("postInput").value;
              if (postContent) {
                addPost(postContent);
                document.getElementById("postInput").value = ""; // 입력창 비우기
              }
            }}
          >
            게시글 추가
          </button>

          {/* 게시글 목록 */}
          <div className="posts">
            {posts.length === 0 ? (
              <p>게시글이 없습니다.</p>
            ) : (
              posts.map((post, index) => (
                <div key={index} className="post">
                  <p>{post.content}</p>
                  <small>{post.date}</small>
                </div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default MatchSchedule;
