package com.linkify.controller;

import com.linkify.model.FriendRequest;
import com.linkify.model.User;
import com.linkify.service.FriendRequestService;
import com.linkify.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    private final UserService userService;

    public FriendRequestController(FriendRequestService friendRequestService, UserService userService) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
    }

    @PostMapping("/send/{username}")
    public void sendFriendRequest(Principal principal, @PathVariable String username) {
        User sender = userService.findByUsername(principal.getName());
        User receiver = userService.findByUsername(username);
        friendRequestService.sendFriendRequest(sender, receiver);
    }

    @PostMapping("/accept/{requestId}")
    public void acceptFriendRequest(@PathVariable Long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
    }

    @GetMapping("/pending")
    public List<FriendRequest> getPendingRequests(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return friendRequestService.getPendingRequests(user);
    }

}