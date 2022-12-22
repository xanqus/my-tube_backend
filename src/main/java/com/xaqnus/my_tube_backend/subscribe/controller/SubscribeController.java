package com.xaqnus.my_tube_backend.subscribe.controller;


import com.xaqnus.my_tube_backend.subscribe.dto.SubscribedChannelListDto;
import com.xaqnus.my_tube_backend.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @GetMapping("/{channelId}")
    public SubscribedChannelListDto getAllSubscribeChannel (@PathVariable Long channelId) {
        return subscribeService.getAllSubscribeByChannelId(channelId);
    }

    @PostMapping("/{channelId}/{subscribedChannelId}")
    public void subscribe(@PathVariable Long channelId, @PathVariable Long subscribedChannelId) {

        if(channelId == subscribedChannelId) {
            return;
        }
        subscribeService.subscribe(channelId, subscribedChannelId);

    }

    @GetMapping("/count/{channelId}")
    public Long getCountOfSubscribers(@PathVariable Long channelId) {
        return subscribeService.getCountOfSubscribers(channelId);
    }


}
