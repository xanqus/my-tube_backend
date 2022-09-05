package com.xaqnus.my_tube_backend.video.repository;

import com.xaqnus.my_tube_backend.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}
