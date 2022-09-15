package com.xaqnus.my_tube_backend.video.service;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    public void uploadFiles(List<MultipartFile> files, String root, Integer userId) throws JCodecException, IOException {

        User user = userRepository.findById(Long.valueOf(userId)).get();

        List<Map<String, String>> fileList = new ArrayList<>();
        for(int i = 0; i < files.size(); i++) {

            String originalFilename = files.get(i).getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String changedFileName = UUID.randomUUID().toString() + ext;
            String thumbnailFileName = changedFileName.substring(0, changedFileName.lastIndexOf(".")) + ".png";

            Map<String, String> map = new HashMap<>();
            map.put("originFile", originalFilename);
            map.put("changeFile", changedFileName);
            map.put("thumbnail", thumbnailFileName);
            fileList.add(map);

            Video video = Video.builder()
                    .user(user)
                    .videoUrl("http://localhost:8287/uploadFiles/" + changedFileName)
                    .title(title)
                    .filename(originalFilename)
                    .thumbnailUrl("http://localhost:8287/uploadFiles/" + thumbnailFileName)
                    .build();

            videoRepository.save(video);
        }


        try {
            for(int i = 0; i < files.size(); i++) {
                String filepath = root + "\\" + fileList.get(i).get("changeFile");
                String imageFilepath = root+ "\\" + fileList.get(i).get("thumbnail");

                File uploadFile = new File(filepath);
                files.get(i).transferTo(uploadFile);
                Picture picture = FrameGrab.getFrameFromFile(uploadFile, 0);

                BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
                ImageIO.write(bufferedImage, "png", new File(imageFilepath));

            }
            System.out.println("파일 업로드 성공!");



        } catch (IllegalStateException | IOException e) {
            System.out.println("파일 업로드 실패");
            // 만약 업로드 실패하면 파일 삭제
            for(int i = 0; i < files.size(); i++) {
                new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
            }

            e.printStackTrace();
        }
    }

    public void updateVideo(int id, Video video) {
        Optional<Video> opVideo = videoRepository.findById(Long.valueOf(id));
        if(opVideo.isPresent()) {
            Video videoToUpdate = opVideo.get();
            videoToUpdate.setTitle(video.getTitle());
            videoToUpdate.setDescription(video.getDescription());
            if(video.getIsPublic() != null ){
                videoToUpdate.setIsPublic(video.getIsPublic());
            }
            videoRepository.save(videoToUpdate);
        }
    }
}
