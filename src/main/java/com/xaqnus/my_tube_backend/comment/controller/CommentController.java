package com.xaqnus.my_tube_backend.comment.controller;

import com.xaqnus.my_tube_backend.comment.domain.Comment;
import com.xaqnus.my_tube_backend.comment.dto.CommentDto;
import com.xaqnus.my_tube_backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{videoId}")
    public List<CommentDto> getComments(@PathVariable Long videoId) {
        return commentService.getComments(videoId);
    }

    @PostMapping("/{videoId}")
    public void createComment(@PathVariable Long videoId, @RequestBody Comment comment) {
        commentService.create(videoId, comment.getText());
    }
}
