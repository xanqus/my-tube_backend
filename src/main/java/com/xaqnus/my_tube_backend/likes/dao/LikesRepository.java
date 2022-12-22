package com.xaqnus.my_tube_backend.likes.dao;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.likes.domain.Likes;
import com.xaqnus.my_tube_backend.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Long countByLikedVideo(Video video);

    Optional<Likes> findByLikedVideoAndChannel(Video video, Channel channel);

    void deleteByLikedVideoAndChannel(Video video, Channel channel);
}
