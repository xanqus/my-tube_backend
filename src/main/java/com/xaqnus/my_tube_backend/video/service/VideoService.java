package com.xaqnus.my_tube_backend.video.service;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;
    public List<VideoItem> getVideos(Integer userId) {
        User user = userRepository.findById(Long.valueOf(userId)).get();
        List<Video> videos = videoRepository.findAllByUser(user);
        List<VideoItem> itemList = new ArrayList<>();
        for(int i = 0; i < videos.size(); i++) {
            VideoItem item = VideoItem.builder()
                    .videoId(videos.get(i).getId())
                    .videoName(videos.get(i).getVideoName())
                    .videoUrl(videos.get(i).getVideoUrl())
                    .thumbnailUrl(videos.get(i).getThumbnailUrl())
                    .isTemp(videos.get(i).getIsTemp())
                    .isPublic(videos.get(i).getIsPublic())
                    .userId((int)videos.get(i).getUser().getId())
                    .views(videos.get(i).getViews())
                    .regDate(videos.get(i).getRegDate())
                    .updatedDate(videos.get(i).getUpdatedDate())
                    .build();
            itemList.add(item);
        }

        return itemList;
    }
}
