package com.linkify.service.impl;

import com.linkify.model.Comment;
import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.repository.PostRepository;
import com.linkify.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(User author, String content, String mediaUrl) {
        Post post = new Post();
        post.setAuthor(author);
        post.setContent(content);
        post.setMediaUrl(mediaUrl);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getNewsFeed(List<User> friends) {
        return postRepository.findByAuthorInOrderByCreatedAtDesc(friends);
    }

    @Override
    public void likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getLikes().add(user);
        postRepository.save(post);
    }

    @Override
    public void commentOnPost(Long postId, User user, String comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.getComments().add(new Comment(user, comment, LocalDateTime.now()));
        postRepository.save(post);
    }

    @Override
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByAuthorInOrderByCreatedAtDesc(List.of(user));
    }

}