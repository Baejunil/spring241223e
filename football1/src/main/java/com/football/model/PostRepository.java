package com.football.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 문자열을 포함하는 게시글을 검색
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:query%")
    List<Post> findByContentContaining(String query);
}
