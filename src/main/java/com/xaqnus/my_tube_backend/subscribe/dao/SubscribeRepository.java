package com.xaqnus.my_tube_backend.subscribe.dao;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.subscribe.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<Subscribe> findByChannelAndSubscribedChannel(Channel channel, Channel subscribedChannel);

    void deleteByChannelAndSubscribedChannel(Channel channel, Channel subscribedChannel);

    List<Subscribe> findAllByChannel(Channel channel);
}
