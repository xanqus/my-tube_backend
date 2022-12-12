package com.xaqnus.my_tube_backend.video.domain;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = "my-tube___video_type_2___v1")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VideoDocument {

    @Id
    private Long id;

    @Field(value = "video_url")
    private String videoUrl;

    @Field(value = "thumbnail_url")
    private String thumbnailUrl;

    private String title;

    private String filename;

    private String description;

    @Field(value = "is_temp")
    private Boolean isTemp;

    @Field(value = "is_public")
    private Boolean isPublic;

    private Integer views;

    @Field(value = "like_count")
    private Integer likeCount;

    @Field(value = "channel_id")
    private Long channelId;

    @Field(value = "channel_name")
    private String channelName;

    @Field(value = "channel_profile_image_url")
    private String channelProfileImageUrl;

    @Field(value = "reg_date")
    private LocalDateTime regDate;

    @Field(value = "updated_date")
    private LocalDateTime updatedDate;

    public static VideoDocument from(Video video) {
        return VideoDocument.builder()
                .id(video.getId())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .title(video.getTitle())
                .filename(video.getFilename())
                .description(video.getDescription())
                .isTemp(video.getIsTemp())
                .isPublic(video.getIsPublic())
                .views(video.getViews())
                .likeCount(video.getLikeCount())
                .channelId(video.getChannel().getId())
                .channelName(video.getChannel().getChannelName())
                .channelProfileImageUrl(video.getChannel().getChannelProfileImageUrl())
                .regDate(video.getRegDate())
                .updatedDate(video.getUpdatedDate())
                .build();
    }
}
