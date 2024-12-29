package com.linkify.service.impl;

import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.repository.PostRepository;
import com.linkify.service.IFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService implements IFeedService {

    private final PostRepository postRepository;
    private final FriendRequestService friendRequestService;

    @Autowired
    public FeedService(PostRepository postRepository, FriendRequestService friendRequestService) {
        this.postRepository = postRepository;
        this.friendRequestService = friendRequestService;
    }

    @Override
    public List<Post> getNewsFeed(User user) {
        List<User> friends = friendRequestService.getFriendsList(user);
        return postRepository.findByAuthorInOrderByCreatedAtDesc(friends);
    }

}