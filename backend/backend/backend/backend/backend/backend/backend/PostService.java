package com.bharatconnect.service;

import com.bharatconnect.model.Post;
import com.bharatconnect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public Post updatePost(String id, Post post) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post p = existingPost.get();
            if (post.getContent() != null) p.setContent(post.getContent());
            if (post.getImages() != null) p.setImages(post.getImages());
            return postRepository.save(p);
        }
        return null;
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public void likePost(String postId, String userId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post p = post.get();
            p.setLikes(p.getLikes() + 1);
            postRepository.save(p);
        }
    }

    public void unlikePost(String postId, String userId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post p = post.get();
            p.setLikes(Math.max(0, p.getLikes() - 1));
            postRepository.save(p);
        }
    }

    public void addComment(String postId, String userId, String text) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post p = post.get();
            p.setComments(p.getComments() + 1);
            postRepository.save(p);
        }
    }
}
