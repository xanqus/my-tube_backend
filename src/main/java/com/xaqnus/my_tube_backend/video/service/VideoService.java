package com.xaqnus.my_tube_backend.video.service;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
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

    public void uploadFiles(List<MultipartFile> files, String root, Integer userId) {
        User user = userRepository.findById(Long.valueOf(userId)).get();

        List<Map<String, String>> fileList = new ArrayList<>();
        for(int i = 0; i < files.size(); i++) {
            String originalFilename = files.get(i).getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String originalFilenameForSave = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String changedFileName = UUID.randomUUID().toString() + ext;

            Map<String, String> map = new HashMap<>();
            map.put("originFile", originalFilename);
            map.put("changeFile", changedFileName);
            fileList.add(map);

            Video video = Video.builder()
                    .user(user)
                    .videoUrl("http://localhost:8287/uploadFiles/" + changedFileName)
                    .videoName(originalFilenameForSave).build();
            videoRepository.save(video);
        }
        System.out.println(fileList);

        try {
            for(int i = 0; i < files.size(); i++) {
                File uploadFile = new File(root + "\\" + fileList.get(i).get("changeFile"));
                files.get(i).transferTo(uploadFile);
                System.out.println("changeFile: " + fileList.get(i).get("changeFile"));
            }
            System.out.println("다중 파일 업로드 성공!");

        } catch (IllegalStateException | IOException e) {
            System.out.println("다중 파일 업로드 실패 ㅠㅠ");
            // 만약 업로드 실패하면 파일 삭제
            for(int i = 0; i < files.size(); i++) {
                new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
            }

            e.printStackTrace();
        }
    }
}
