package com.xaqnus.my_tube_backend.video.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoDto;
import com.xaqnus.my_tube_backend.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    String root = "C:\\uploadFiles";
    public List<VideoDto> getVideos(Integer userId) {
        User user = userRepository.findById(Long.valueOf(userId)).get();
        List<Video> videos = videoRepository.findAllByUser(user);
        List<VideoDto> collect = videos.stream()
                .map(video -> {
                    VideoDto item = new VideoDto(video);
                    return item;
                })
                .collect(Collectors.toList());

        return collect;
    }

    public void uploadFiles(List<MultipartFile> files, Integer userId) throws JCodecException, IOException {

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
            map.put("title", title);
            fileList.add(map);
        }


        AtomicInteger index = new AtomicInteger();
        try {
            List<String> strArr = new ArrayList<>();
            files.stream()
                    .forEach(file -> {
                        String s3FileName = fileList.get(index.intValue()).get("changeFile");
                        ObjectMetadata objMeta = new ObjectMetadata();

                        try {
                            objMeta.setContentLength(file.getInputStream().available());
                            amazonS3.putObject(bucket, s3FileName, file.getInputStream(), objMeta);
                            strArr.add(amazonS3.getUrl(bucket, s3FileName).toString());

                            String filepath = root + "\\" + fileList.get(index.intValue()).get("changeFile");
                            String imageFilepath = root+ "\\" + fileList.get(index.intValue()).get("thumbnail");

                            File uploadFile = new File(filepath);
                            files.get(index.intValue()).transferTo(uploadFile);
                            Picture picture = FrameGrab.getFrameFromFile(uploadFile, 0);

                            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

                            ImageIO.write(bufferedImage, "png", new File(imageFilepath));
                            amazonS3.putObject(bucket, fileList.get(index.intValue()).get("thumbnail"), new File(imageFilepath));
                            Video video = Video.builder()
                                    .user(user)
                                    .videoUrl(amazonS3.getUrl(bucket, fileList.get(index.intValue()).get("changeFile")).toString())
                                    .title(fileList.get(index.intValue()).get("title"))
                                    .filename(fileList.get(index.intValue()).get("originFile"))
                                    .thumbnailUrl(amazonS3.getUrl(bucket, fileList.get(index.intValue()).get("thumbnail")).toString())
                                    .build();

                            videoRepository.save(video);

                            new File(root + "\\" + fileList.get(index.intValue()).get("changeFile")).delete();
                            new File(root + "\\" + fileList.get(index.intValue()).get("thumbnail")).delete();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JCodecException e) {
                            throw new RuntimeException(e);
                        }

                        index.getAndIncrement();
                    });


            System.out.println("파일 업로드 성공!");


        } catch (IllegalStateException e) {
            System.out.println("파일 업로드 실패");

            e.printStackTrace();
        }
    }

    public void updateVideo(int videoId, Video video) {
        Optional<Video> opVideo = videoRepository.findById(Long.valueOf(videoId));
        if(opVideo.isPresent()) {
            Video videoToUpdate = opVideo.get();
            videoToUpdate.setTitle(video.getTitle());
            videoToUpdate.setDescription(video.getDescription());
            if(video.getIsPublic() != null ){
                videoToUpdate.setIsPublic(video.getIsPublic());
            }
            if(video.getIsTemp()!=null) {
                videoToUpdate.setIsTemp(video.getIsTemp());
            }
            videoRepository.save(videoToUpdate);
        }
    }

    public VideoDto getVideo(Long videoId) {
        Optional<Video> video =  videoRepository.findById(Long.valueOf(videoId));
        if(video.isPresent()) {
            VideoDto videoItem = new VideoDto(video.get());
            return videoItem;
        }
        return null;
    }
}
