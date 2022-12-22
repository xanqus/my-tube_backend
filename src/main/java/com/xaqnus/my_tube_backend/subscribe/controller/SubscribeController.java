package com.xaqnus.my_tube_backend.subscribe.controller;


import com.xaqnus.my_tube_backend.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/{channelId}/{subscribedChannelId}")
    public void subscribe(@PathVariable Long channelId, @PathVariable Long subscribedChannelId) {

        if(channelId == subscribedChannelId) {
            return;
        }
        subscribeService.subscribe(channelId, subscribedChannelId);

    }


}
