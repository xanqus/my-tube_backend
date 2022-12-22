package com.xaqnus.my_tube_backend.channel.dto;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {

    private Long id;

    private String channelName;

    private String channelProfileImageUrl;

    private String channelBannerImageUrl;

    private Long userId;

    private Boolean activateStatus;

    private LocalDateTime regDate;

    private LocalDateTime updatedDate;

    public ChannelDto(Channel channel) {
        this.id = channel.getId();

        this.channelName = channel.getChannelName();

        this.channelProfileImageUrl = channel.getChannelProfileImageUrl();

        this.channelBannerImageUrl = channel.getChannelBannerImageUrl();

        this.userId = channel.getUser().getId();

        this.activateStatus = channel.getActivateStatus();

        this.regDate = channel.getRegDate();

        this.updatedDate = channel.getUpdatedDate();
    }
}
