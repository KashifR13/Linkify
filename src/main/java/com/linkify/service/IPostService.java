package com.linkify.service;

import com.linkify.model.Post;
import com.linkify.model.User;

import java.util.List;

public interface IPostService {
    Post createPost(User author, String content, String mediaUrl);
    List<Post> getNewsFeed(List<User> friends);
}