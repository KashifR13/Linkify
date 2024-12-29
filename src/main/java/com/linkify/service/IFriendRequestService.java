package com.linkify.service;

import com.linkify.model.FriendRequest;
import com.linkify.model.User;

import java.util.List;

public interface IFriendRequestService {
    void sendFriendRequest(User sender, User receiver);
    void acceptFriendRequest(Long requestId);
    List<FriendRequest> getPendingRequests(User user);
    List<User> getFriendsList(User user);
}