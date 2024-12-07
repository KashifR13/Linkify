package com.linkify.repository;

import com.linkify.model.FriendRequest;
import com.linkify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverAndAcceptedFalse(User receiver);
    List<FriendRequest> findBySenderAndAcceptedTrue(User sender);
    List<FriendRequest> findByReceiverAndAcceptedTrue(User receiver);
}