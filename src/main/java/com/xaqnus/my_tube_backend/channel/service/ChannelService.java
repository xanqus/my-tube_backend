package com.xaqnus.my_tube_backend.channel.service;

import com.xaqnus.my_tube_backend.channel.dao.ChannelRepository;
import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.channel.dto.ChannelDto;
import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    public void create(User user) {
        Channel channel = Channel.builder()
                .channelName(user.getUsername())
                .channelBannerImageUrl("")
                .channelProfileImageUrl("https://cdn-icons-png.flaticon.com/512/1144/1144760.png")
                .user(user)
                .build();
        channelRepository.save(channel);
    }

    public ChannelDto getChannel(String channelName) {
        Optional<Channel> _channel = channelRepository.findByChannelName(channelName);
        Channel channel = _channel.orElseThrow();
        ChannelDto channelDto = ChannelDto.builder()
                .id(channel.getId())
                .channelName(channel.getChannelName())
                .activateStatus(channel.getActivateStatus())
                .channelBannerImageUrl(channel.getChannelBannerImageUrl())
                .channelProfileImageUrl(channel.getChannelProfileImageUrl())
                .userId(channel.getUser().getId())
                .regDate(channel.getRegDate())
                .updatedDate(channel.getUpdatedDate())
                .build();

        return channelDto;
    }
}
