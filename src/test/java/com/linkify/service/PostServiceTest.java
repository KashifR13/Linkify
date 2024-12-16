package com.linkify.service;

import com.linkify.model.Post;
import com.linkify.model.User;
import com.linkify.repository.PostRepository;
import com.linkify.service.impl.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        postService = new PostService(postRepository);
    }

    @Test
    public void testCreatePost() {
        User author = new User();
        author.setId(1L);
        String content = "Test content";
        String mediaUrl = "http://example.com/media.jpg";

        Post post = new Post();
        post.setAuthor(author);
        post.setContent(content);
        post.setMediaUrl(mediaUrl);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(author, content, mediaUrl);

        assertNotNull(createdPost);
        assertEquals(author, createdPost.getAuthor());
        assertEquals(content, createdPost.getContent());
        assertEquals(mediaUrl, createdPost.getMediaUrl());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testGetNewsFeed() {
        User friend = new User();
        friend.setId(2L);
        Post post = new Post();
        post.setAuthor(friend);

        when(postRepository.findByAuthorInOrderByCreatedAtDesc(anyList())).thenReturn(List.of(post));

        List<Post> newsFeed = postService.getNewsFeed(List.of(friend));

        assertNotNull(newsFeed);
        assertEquals(1, newsFeed.size());
        assertEquals(friend, newsFeed.get(0).getAuthor());
        verify(postRepository, times(1)).findByAuthorInOrderByCreatedAtDesc(anyList());
    }

}