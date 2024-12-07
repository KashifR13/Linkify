package com.linkify.service;

import com.linkify.model.FriendRequest;
import com.linkify.model.User;
import com.linkify.repository.FriendRequestRepository;
import com.linkify.service.impl.FriendRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FriendRequestServiceTest {

    private FriendRequestService friendRequestService;
    private FriendRequestRepository friendRequestRepository;

    @BeforeEach
    public void setUp() {
        friendRequestRepository = Mockito.mock(FriendRequestRepository.class);
        friendRequestService = new FriendRequestService(friendRequestRepository);
    }

    @Test
    public void shouldSendFriendRequestSuccessfully() {
        User sender = new User();
        sender.setId(1L);
        User receiver = new User();
        receiver.setId(2L);

        friendRequestService.sendFriendRequest(sender, receiver);

        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class));
    }

    @Test
    public void shouldAcceptFriendRequestSuccessfully() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(1L);

        when(friendRequestRepository.findById(1L)).thenReturn(java.util.Optional.of(friendRequest));

        friendRequestService.acceptFriendRequest(1L);

        assertTrue(friendRequest.isAccepted());
        verify(friendRequestRepository, times(1)).save(friendRequest);
    }

    @Test
    public void shouldRejectFriendRequestSuccessfully() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(1L);

        when(friendRequestRepository.findById(1L)).thenReturn(java.util.Optional.of(friendRequest));

        friendRequestService.rejectFriendRequest(1L);

        verify(friendRequestRepository, times(1)).delete(friendRequest);
    }

    @Test
    public void shouldGetPendingFriendRequestsSuccessfully() {
        User user = new User();
        user.setId(1L);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setReceiver(user);

        when(friendRequestRepository.findByReceiverAndAcceptedFalse(user)).thenReturn(List.of(friendRequest));

        List<FriendRequest> pendingRequests = friendRequestService.getPendingFriendRequests(user);

        assertNotNull(pendingRequests);
        assertEquals(1, pendingRequests.size());
    }

    @Test
    public void shouldGetFriendsListSuccessfully() {
        User user = new User();
        user.setId(1L);
        User friend = new User();
        friend.setId(2L);
        FriendRequest sentRequest = new FriendRequest();
        sentRequest.setSender(user);
        sentRequest.setReceiver(friend);
        sentRequest.setAccepted(true);
        FriendRequest receivedRequest = new FriendRequest();
        receivedRequest.setSender(friend);
        receivedRequest.setReceiver(user);
        receivedRequest.setAccepted(true);

        when(friendRequestRepository.findBySenderAndAcceptedTrue(user)).thenReturn(List.of(sentRequest));
        when(friendRequestRepository.findByReceiverAndAcceptedTrue(user)).thenReturn(List.of(receivedRequest));

        List<User> friends = friendRequestService.getFriendsList(user);

        assertNotNull(friends);
        assertEquals(1, friends.size());
        assertEquals(friend.getId(), friends.get(0).getId());
    }

}