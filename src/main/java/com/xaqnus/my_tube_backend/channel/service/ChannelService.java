package com.xaqnus.my_tube_backend.channel.service;

import com.xaqnus.my_tube_backend.channel.dao.ChannelRepository;
import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.channel.dto.ChannelDto;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    private final UserService userService;

    public ChannelDto getChannelDtoByChannelId(Long channelId) {
        ChannelDto channelDto = new ChannelDto(getChannelByChannelId(channelId));
        return channelDto;
    }

    public Channel getChannelByChannelId(Long channelId) {
        return channelRepository.findById(channelId).orElseThrow();
    }
    public void create(User user) {
        Channel channel = Channel.builder()
                .channelName(user.getUsername())
                .channelBannerImageUrl("")
                .channelProfileImageUrl("https://cdn-icons-png.flaticon.com/512/1144/1144760.png")
                .user(user)
                .build();
        channelRepository.save(channel);
    }

    public List<ChannelDto> getChannel(String username) {

        User user = userService.getUserByUsername(username);
        List<Channel> channels = channelRepository.findAllByUser(user);
        return channels.stream()
                .map(channel -> {
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
                }).toList();


    }
}
