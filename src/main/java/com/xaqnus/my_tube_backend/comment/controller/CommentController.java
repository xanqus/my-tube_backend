package com.xaqnus.my_tube_backend.comment.controller;

import com.xaqnus.my_tube_backend.comment.domain.Comment;
import com.xaqnus.my_tube_backend.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{videoId}")
    public void createComment(@PathVariable Long videoId, @RequestBody Comment comment) {
        commentService.create(videoId, comment.getText());

    }
}
