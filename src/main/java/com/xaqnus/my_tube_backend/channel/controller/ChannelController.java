package com.xaqnus.my_tube_backend.channel.controller;


import com.xaqnus.my_tube_backend.channel.dto.ChannelDto;
import com.xaqnus.my_tube_backend.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/{channelName}")
    public ChannelDto getChannel(@PathVariable String channelName) {
        return channelService.getChannel(channelName);
    }
}
