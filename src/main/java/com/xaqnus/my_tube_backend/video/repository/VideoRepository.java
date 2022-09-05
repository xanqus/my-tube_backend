package com.xaqnus.my_tube_backend.video.repository;

import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findAllByUser(User user);
}
