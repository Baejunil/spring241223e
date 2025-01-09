import React, { useState, useEffect } from "react";
import axios from "axios";
import "./PostBoard.css";

const PostBoard = () => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState(""); // 검색어 상태 추가

  // 게시글 목록 가져오기
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/posts")
      .then((response) => {
        setPosts(response.data);
      })
      .catch((error) => {
        setError("게시글을 가져오는 데 문제가 발생했습니다.");
        console.error(error);
      });
  }, []);

  // 게시글 추가
  const handleAddPost = (postContent) => {
    if (!postContent) return;

    axios
      .post("http://localhost:8080/api/posts", { content: postContent })
      .then((response) => {
        setPosts([...posts, response.data]);
      })
      .catch((error) => {
        console.error("게시글 추가 중 오류:", error);
      });
  };

  // 게시글 삭제
  const deletePost = (id) => {
    axios
      .delete(`http://localhost:8080/api/posts/${id}`)
      .then(() => {
        setPosts(posts.filter((post) => post.id !== id)); // 삭제된 게시글을 제외한 새로운 배열로 업데이트
      })
      .catch((error) => {
        console.error("Error deleting post:", error);
      });
  };

  // 검색 처리
  const handleSearch = async () => {
    if (searchTerm === "") {
      // 검색어가 비어있으면 모든 게시글을 표시
      try {
        const response = await axios.get("http://localhost:8080/api/posts");
        setPosts(response.data);
      } catch (error) {
        console.error(error);
      }
    } else {
      // 검색어에 맞는 게시글 필터링
      try {
        const response = await axios.get(`http://localhost:8080/api/posts/search?query=${searchTerm}`);
        setPosts(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <div className="post-board">
      <h2>자유게시판</h2>
      {error && <p className="error">{error}</p>}

      {/* 게시글 입력 */}
      <textarea
        className="post-input"
        placeholder="여기에 댓글을 남겨주세요."
        id="postInput"
      ></textarea>
      <button
        onClick={() => {
          const postContent = document.getElementById("postInput").value;
          if (postContent) {
            handleAddPost(postContent);
            document.getElementById("postInput").value = ""; // 입력창 초기화
          }
        }}
        className="post-btn"
      >
        게시글 추가
      </button>

      {/* 검색 기능 */}
      <div className="search-container">
        <input
          type="text"
          placeholder="검색어 입력"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={handleSearch}>검색</button>
      </div>

      {/* 게시글 목록 */}
      <div className="posts">
        {posts.length === 0 ? (
          <p>게시글이 없습니다.</p>
        ) : (
          posts.map((post) => (
            <div key={post.id} className="post">
              <p>{post.content}</p>
              <small>{post.date}</small>
              <button
                className="delete-btn"
                onClick={() => deletePost(post.id)}
              >
                삭제
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default PostBoard;
