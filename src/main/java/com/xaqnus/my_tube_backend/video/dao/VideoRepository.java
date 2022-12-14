package com.xaqnus.my_tube_backend.video.dao;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByChannel(Channel channel);

    List<Video> findByChannelOrderByRegDateDesc(Channel channel);
}
