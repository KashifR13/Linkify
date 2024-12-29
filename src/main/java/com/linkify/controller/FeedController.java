package com.linkify.controller;

import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.service.impl.FeedService;
import com.linkify.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final UserService userService;

    @Autowired
    public FeedController(FeedService feedService, UserService userService) {
        this.feedService = feedService;
        this.userService = userService;
    }

    @GetMapping
    public List<Post> getNewsFeed(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return feedService.getNewsFeed(user);
    }

}