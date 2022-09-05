package com.xaqnus.my_tube_backend.video.dto;

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
    private String videoName;
    private Boolean isTemp;
    private Boolean isPublic;
    private Integer views;
    private Integer userId;
    private LocalDateTime regDate;
    private LocalDateTime updatedDate;
}
