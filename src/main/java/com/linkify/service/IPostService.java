package com.linkify.service;

import com.linkify.model.Post;
import com.linkify.model.User;

import java.util.List;

public interface IPostService {
    Post createPost(User user, String content, String mediaUrl);
    List<Post> getNewsFeed(List<User> friends);
    void likePost(Long postId, User user);
    void commentOnPost(Long postId, User user, String comment);
    List<Post> getPostsByUser(User user);
}