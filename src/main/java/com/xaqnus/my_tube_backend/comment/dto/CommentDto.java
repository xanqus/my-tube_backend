package com.xaqnus.my_tube_backend.comment.dto;

import com.xaqnus.my_tube_backend.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String text;

    private Long likes;

    private String channelName;

    private String channelProfileImageUrl;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.likes = comment.getLikes();
        this.channelName = comment.getChannel().getChannelName();
        this.channelProfileImageUrl = comment.getChannel().getChannelProfileImageUrl();
    }
}
