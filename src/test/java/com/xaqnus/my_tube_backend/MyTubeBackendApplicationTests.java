package com.xaqnus.my_tube_backend;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import com.xaqnus.my_tube_backend.video.domain.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class MyTubeBackendApplicationTests {

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		User user = userRepository.findByUsername("user");
		Video video = new Video();
		video.setVideoName("요리 동영상");
		video.setVideoUrl("http://localhost:8287/uploadFiles/d0f609cf-d2e1-427a-8e21-a16e21a54184.mp4");
		video.setIsTemp(true);
		video.setIsPublic(true);
		video.setViews(0);
		video.setRegDate(LocalDateTime.now());
		video.setUpdatedDate(LocalDateTime.now());
		video.setUser(user);
		videoRepository.save(video);
	}

}
