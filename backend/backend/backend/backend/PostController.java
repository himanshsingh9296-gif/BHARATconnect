package com.bharatconnect.controller;

import com.bharatconnect.model.Post;
import com.bharatconnect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable String id, @RequestParam String userId) {
        postService.likePost(id, userId);
        return ResponseEntity.ok("Post liked!");
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable String id, @RequestParam String userId) {
        postService.unlikePost(id, userId);
        return ResponseEntity.ok("Post unliked!");
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> addComment(@PathVariable String id, @RequestParam String userId, @RequestParam String text) {
        postService.addComment(id, userId, text);
        return ResponseEntity.ok("Comment added!");
    }
}
