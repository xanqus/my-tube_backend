package com.xaqnus.my_tube_backend.channel.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChannelDto {

    private Long id;

    private String channelName;

    private String channelProfileImageUrl;

    private String channelBannerImageUrl;

    private Long userId;

    private Boolean activateStatus;

    private LocalDateTime regDate;

    private LocalDateTime updatedDate;
}
