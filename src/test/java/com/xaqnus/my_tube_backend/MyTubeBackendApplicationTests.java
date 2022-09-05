package com.xaqnus.my_tube_backend;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class MyTubeBackendApplicationTests {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void contextLoads() {
        for (int i = 0; i < 5; i++) {
            User user = userRepository.findByUsername("user");
            Video video = Video.builder()
                    .videoName("요리 동영상")
                    .videoUrl("http://localhost:8287/uploadFiles/d0f609cf-d2e1-427a-8e21-a16e21a54184.mp4")
                    .thumbnailUrl("https://blog.kakaocdn.net/dn/m07x9/btqSLGu0ccF/WuCwiJPrNKx9IB3xpER7C1/img.png")
                    .user(user).build();
            videoRepository.save(video);
        }
    }

    @Test
    User getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }

    @Test
    List<Video> getVideos() {
        List<Video> videos = videoRepository.findAllByUser(getUser(1L));
        return videos;
    }

}
