package com.xaqnus.my_tube_backend.video.service;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;
    public List<VideoItem> getVideos(Integer userId) {
        User user = userRepository.findById(Long.valueOf(userId)).get();
        List<Video> videos = videoRepository.findAllByUser(user);
        List<VideoItem> collect = videos.stream()
                .map(video -> {
                    VideoItem item = new VideoItem(video);
                    return item;
                })
                .collect(Collectors.toList());

        return collect;
    }
}
