package com.xaqnus.my_tube_backend.channel.dao;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByChannelName(String channelName);
}
