package com.xaqnus.my_tube_backend.comment.dao;

import com.xaqnus.my_tube_backend.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
