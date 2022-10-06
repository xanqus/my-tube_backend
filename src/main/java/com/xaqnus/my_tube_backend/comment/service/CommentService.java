package com.xaqnus.my_tube_backend.comment.service;

import com.xaqnus.my_tube_backend.comment.dao.CommentRepository;
import com.xaqnus.my_tube_backend.comment.domain.Comment;
import com.xaqnus.my_tube_backend.comment.dto.CommentDto;
import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    private final UserRepository userRepository;
    public void create(Long videoId, String text) {
        Optional<Video> opVideo = videoRepository.findById(videoId);

        User user = userRepository.findById(opVideo.get().getUser().getId()).get();
        if (opVideo.isPresent()) {
            Comment comment = Comment.builder()
                    .video(opVideo.get())
                    .user(user)
                    .text(text)
                    .build();
            commentRepository.save(comment);
        }

    }

    public List<CommentDto> getComments(Long videoId) {
        List<Comment> commentList = commentRepository.findAllByVideoId(videoId);
        List<CommentDto> commentDtoList = commentList.stream().map(comment -> {
                    CommentDto commentDto = new CommentDto(comment);
                    return commentDto;
                })
                .collect(Collectors.toList());
        return commentDtoList;
    }
}
