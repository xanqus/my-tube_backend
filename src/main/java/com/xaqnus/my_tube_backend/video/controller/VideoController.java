package com.xaqnus.my_tube_backend.video.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.xaqnus.my_tube_backend.fileSystem.service.FileSystemService;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final FileSystemService fileSystemService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @GetMapping("")
    public List<VideoItem> getVideos(@RequestParam("userId") Integer userId) {
        return videoService.getVideos(userId);
    }

    @GetMapping("{videoId}")
    public VideoItem getVideo(@PathVariable("videoId") Long videoId) {
        return videoService.getVideo(videoId);
    }

    @PostMapping("")
    public List<VideoItem> uploadVideos(@RequestParam("files") List<MultipartFile> files, @RequestParam("userId") Integer userId) throws IOException, JCodecException {
        System.out.println("id: "+ userId);
        System.out.println("files: " + files);

//        String root = "C:\\uploadFiles";
//        fileSystemService.createFolder(root);

        videoService.uploadFiles(files, userId);

        return videoService.getVideos(userId);
    }

    @PatchMapping("/{videoId}")
    public void updateVideo(@PathVariable("videoId") int videoId, @RequestBody Video video) {
        videoService.updateVideo(videoId, video);
    }

    @PostMapping("/awstest")
    public List<String> upload(@RequestParam("video") List<MultipartFile> files) throws IOException, RuntimeException {
        List<String> strArr = new ArrayList<>();
        files.stream()
                .forEach(file -> {
                    String s3FileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

                    ObjectMetadata objMeta = new ObjectMetadata();

                    try {
                        objMeta.setContentLength(file.getInputStream().available());
                        amazonS3.putObject(bucket, s3FileName, file.getInputStream(), objMeta);
                        strArr.add(amazonS3.getUrl(bucket, s3FileName).toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        return strArr;

    }
}
