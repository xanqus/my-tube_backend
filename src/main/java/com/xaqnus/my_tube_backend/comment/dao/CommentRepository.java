package com.xaqnus.my_tube_backend.comment.dao;

import com.xaqnus.my_tube_backend.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByVideoId(Long videoId);
}
