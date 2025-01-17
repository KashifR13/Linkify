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

    @PostMapping("/{postId}/like")
    public void likePost(Principal principal, @PathVariable Long postId) {
        User user = userService.findByUsername(principal.getName());
        postService.likePost(postId, user);
    }

    @PostMapping("/{postId}/comment")
    public void commentOnPost(Principal principal, @PathVariable Long postId, @RequestBody String comment) {
        User user = userService.findByUsername(principal.getName());
        postService.commentOnPost(postId, user, comment);
    }

    @GetMapping("/user/{username}")
    public List<Post> getPostsByUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return postService.getPostsByUser(user);
    }

}