package com.xaqnus.my_tube_backend.video.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.xaqnus.my_tube_backend.channel.dao.ChannelRepository;
import com.xaqnus.my_tube_backend.channel.domain.Channel;
import com.xaqnus.my_tube_backend.channel.service.ChannelService;
import com.xaqnus.my_tube_backend.user.dao.UserRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final ChannelService channelService;

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    private final ChannelRepository channelRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    String root = "C:\\uploadFiles";
    public List<VideoDto> getVideos(Long channelId) {
        Channel channel = channelRepository.findById(channelId).get();
        List<Video> videos = videoRepository.findAllByChannel(channel);
        List<VideoDto> collect = videos.stream()
                .map(video -> {
                    VideoDto item = new VideoDto(video);
                    return item;
                })
                .collect(Collectors.toList());

        return collect;
    }

    public void uploadFiles(List<MultipartFile> files, Long channelId) throws JCodecException, IOException {

        Channel channel = channelRepository.findById(channelId).get();


        AtomicInteger index = new AtomicInteger();
        try {
            files.stream()
                    .forEach(file -> {
                        String originalFilename = files.get(index.intValue()).getOriginalFilename();
                        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                        String changedFileName = UUID.randomUUID().toString() + ext;
                        String thumbnailFileName = changedFileName.substring(0, changedFileName.lastIndexOf(".")) + ".png";

                        ObjectMetadata objMeta = new ObjectMetadata();

                        try {
                            objMeta.setContentLength(file.getInputStream().available());
                            amazonS3.putObject(bucket, changedFileName, file.getInputStream(), objMeta);

                            String filepath = root + "\\" + changedFileName;
                            String imageFilepath = root+ "\\" + thumbnailFileName;

                            File uploadFile = new File(filepath);
                            files.get(index.intValue()).transferTo(uploadFile);
                            Picture picture = FrameGrab.getFrameFromFile(uploadFile, 0);

                            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

                            ImageIO.write(bufferedImage, "png", new File(imageFilepath));
                            amazonS3.putObject(bucket, thumbnailFileName, new File(imageFilepath));
                            Video video = Video.builder()
                                    .channel(channel)
                                    .videoUrl(amazonS3.getUrl(bucket, changedFileName).toString())
                                    .title(title)
                                    .filename(originalFilename)
                                    .thumbnailUrl(amazonS3.getUrl(bucket, thumbnailFileName).toString())
                                    .build();

                            videoRepository.save(video);

                            new File(root + "\\" + changedFileName).delete();
                            new File(root + "\\" + thumbnailFileName).delete();

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

    public List<VideoDto> getVideosByChannelId(Long channelId) {
        Channel channel = channelService.getChannelByChannelId(channelId);

        List<Video> videos = videoRepository.findByChannelOrderByRegDateDesc(channel);
        return videos.stream()
                .map(video -> {
                    VideoDto videoDto = new VideoDto(video);
                    return videoDto;
                }).toList();
    }
}
