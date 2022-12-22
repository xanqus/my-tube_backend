package com.xaqnus.my_tube_backend.likes.service;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.channel.service.ChannelService;
import com.xaqnus.my_tube_backend.likes.dao.LikesRepository;
import com.xaqnus.my_tube_backend.likes.domain.Likes;
import com.xaqnus.my_tube_backend.video.dao.VideoRepository;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    private final ChannelService channelService;

    private final VideoService videoService;

    private final VideoRepository videoRepository;

    @Transactional
    public void addLikes(Long videoId, Long channelId) {
        Video video = videoService.getVideoByVideoId(videoId);

        Channel channel = channelService.getChannelByChannelId(channelId);

        Optional<Likes> likesOptional = likesRepository.findByLikedVideoAndChannel(video, channel);
        if(likesOptional.isPresent()) {
            video.setLikeCount(video.getLikeCount() -1);
            likesRepository.deleteByLikedVideoAndChannel(video, channel);
        } else {

            Likes likes = Likes.builder()
                    .likedVideo(video)
                    .channel(channel)
                    .build();
            video.setLikeCount(video.getLikeCount() + 1);
            likesRepository.save(likes);

        }
        videoRepository.save(video);

    }

    public Long getLikeCountByVideoId(Long videoId) {
        Video video = videoService.getVideoByVideoId(videoId);
        return likesRepository.countByLikedVideo(video);
    }

    public Boolean haveClikcedLikeBefore(Long videoId, Long channelId) {
        Video video = videoService.getVideoByVideoId(videoId);

        Channel channel = channelService.getChannelByChannelId(channelId);

        Optional<Likes> likesOptional = likesRepository.findByLikedVideoAndChannel(video, channel);

        if(likesOptional.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
