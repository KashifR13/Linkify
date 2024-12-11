package com.linkify.repository;

import com.linkify.model.Post;
import com.linkify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorInOrderByCreatedAtDesc(List<User> authors);
}