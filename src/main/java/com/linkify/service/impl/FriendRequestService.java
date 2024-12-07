package com.linkify.service.impl;

import com.linkify.model.FriendRequest;
import com.linkify.model.User;
import com.linkify.repository.FriendRequestRepository;
import com.linkify.service.IFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FriendRequestService implements IFriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public void sendFriendRequest(User sender, User receiver) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setSentAt(LocalDateTime.now());
        friendRequest.setAccepted(false);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow();
        friendRequest.setAccepted(true);
        friendRequestRepository.save(friendRequest);
    }

    public void rejectFriendRequest(Long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow();
        friendRequestRepository.delete(friendRequest);
    }

    @Override
    public List<FriendRequest> getPendingRequests(User user) {
        return friendRequestRepository.findByReceiverAndAcceptedFalse(user);
    }

    public List<User> getFriendsList(User user) {
        List<FriendRequest> sentRequests = friendRequestRepository.findBySenderAndAcceptedTrue(user);
        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceiverAndAcceptedTrue(user);

        return Stream.concat(
                sentRequests.stream().map(FriendRequest::getReceiver),
                receivedRequests.stream().map(FriendRequest::getSender)
        ).distinct().collect(Collectors.toList());
    }

    public List<FriendRequest> getPendingFriendRequests(User user) {
        return friendRequestRepository.findByReceiverAndAcceptedFalse(user);
    }

}