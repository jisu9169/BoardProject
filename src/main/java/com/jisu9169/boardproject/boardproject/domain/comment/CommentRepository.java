package com.jisu9169.boardproject.boardproject.domain.comment;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jisu9169.boardproject.boardproject.domain.comment.entity.Comment;
import com.jisu9169.boardproject.boardproject.domain.post.entity.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@BatchSize(size = 100)
	Page<Comment> findByPostAndParent(Post post, Comment parent, Pageable pageable);
}
