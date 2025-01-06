package com.football.model;

import com.football.model.Post;
import com.football.FootballMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // 모든 게시글 조회
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 추가
    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }
}
