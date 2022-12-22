package com.xaqnus.my_tube_backend.subscribe.service;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.channel.service.ChannelService;
import com.xaqnus.my_tube_backend.subscribe.dao.SubscribeRepository;
import com.xaqnus.my_tube_backend.subscribe.domain.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    private final ChannelService channelService;

    @Transactional
    public void subscribe(Long channelId, Long subscribedChannelId) {

        Channel channel = channelService.getChannelByChannelId(channelId);
        Channel subscribedChannel = channelService.getChannelByChannelId(subscribedChannelId);
        Optional<Subscribe> subscribeBefore = subscribeRepository.findByChannelAndSubscribedChannel(channel, subscribedChannel);
        if(subscribeBefore.isPresent()) {
            subscribeRepository.deleteByChannelAndSubscribedChannel(channel, subscribedChannel);
        } else {
            Subscribe subscribe = Subscribe.builder()
                    .channel(channel)
                    .subscribedChannel(subscribedChannel)
                    .build();

            subscribeRepository.save(subscribe);
        }

    }
}
