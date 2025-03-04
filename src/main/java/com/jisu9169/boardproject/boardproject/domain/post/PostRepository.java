package com.jisu9169.boardproject.boardproject.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
