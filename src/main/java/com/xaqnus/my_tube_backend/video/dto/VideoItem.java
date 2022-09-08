package com.xaqnus.my_tube_backend.video.dto;

import com.xaqnus.my_tube_backend.video.domain.Video;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class VideoItem {
    private long videoId;
    private String videoUrl;

    private String thumbnailUrl;
    private String title;

    private String filename;

    private String description;

    private Boolean isTemp;
    private Boolean isPublic;
    private Integer views;

    private Integer likeCount;
    private Integer userId;
    private LocalDateTime regDate;
    private LocalDateTime updatedDate;

    public VideoItem(Video video) {
        videoId = video.getId();
        videoUrl = video.getVideoUrl();
        title = video.getTitle();
        filename = video.getFilename();
        thumbnailUrl = video.getThumbnailUrl();
        description = video.getDescription();
        isTemp = video.getIsTemp();
        isPublic = video.getIsPublic();
        views = video.getViews();
        likeCount = video.getLikeCount();
        userId = (int) video.getUser().getId();
        regDate = video.getRegDate();
        updatedDate = video.getUpdatedDate();
    }
}
