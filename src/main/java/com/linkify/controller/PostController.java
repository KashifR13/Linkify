package com.linkify.controller;

import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.service.IPostService;
import com.linkify.service.IUserService;
import com.linkify.service.impl.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final IPostService postService;
    private final IUserService userService;
    private final FriendRequestService friendRequestService;

    @Autowired
    public PostController(IPostService postService, IUserService userService, FriendRequestService friendRequestService) {
        this.postService = postService;
        this.userService = userService;
        this.friendRequestService = friendRequestService;
    }

    @PostMapping
    public Post createPost(Principal principal, @RequestBody Post postRequest) {
        User author = userService.findByUsername(principal.getName());
        return postService.createPost(author, postRequest.getContent(), postRequest.getMediaUrl());
    }

    @GetMapping("/feed")
    public List<Post> getNewsFeed(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<User> friends = friendRequestService.getFriendsList(user);
        return postService.getNewsFeed(friends);
    }
}