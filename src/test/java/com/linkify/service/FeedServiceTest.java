package com.linkify.service;

import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.repository.PostRepository;
import com.linkify.service.impl.FeedService;
import com.linkify.service.impl.FriendRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedServiceTest {

    private FeedService feedService;
    private PostRepository postRepository;
    private FriendRequestService friendRequestService;

    @BeforeEach
    public void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        friendRequestService = Mockito.mock(FriendRequestService.class);
        feedService = new FeedService(postRepository, friendRequestService);
    }

    @Test
    public void shouldReturnNewsFeed() {
        User user = new User();
        User friend = new User();
        Post post = new Post();
        post.setAuthor(friend);

        when(friendRequestService.getFriendsList(user)).thenReturn(List.of(friend));
        when(postRepository.findByAuthorInOrderByCreatedAtDesc(List.of(friend))).thenReturn(List.of(post));

        List<Post> feed = feedService.getNewsFeed(user);

        assertNotNull(feed);
        assertEquals(1, feed.size());
        assertEquals(friend, feed.get(0).getAuthor());
    }

}