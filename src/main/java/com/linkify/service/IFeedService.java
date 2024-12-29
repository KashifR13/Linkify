package com.linkify.service;

import com.linkify.model.Post;
import com.linkify.model.User;

import java.util.List;

public interface IFeedService {
    List<Post> getNewsFeed(User user);
}