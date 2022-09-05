package com.xaqnus.my_tube_backend.video.controller;

import com.xaqnus.my_tube_backend.fileSystem.FileSystemService;
import com.xaqnus.my_tube_backend.video.dto.VideoItem;
import com.xaqnus.my_tube_backend.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final FileSystemService fileSystemService;

    @GetMapping("")
    public List<VideoItem> getVideos(@RequestParam("userId") Integer userId) {
        return videoService.getVideos(userId);
    }


    @PostMapping("")
    public List<VideoItem> uploadVideos(@RequestParam("files") List<MultipartFile> files, @RequestParam("userId") Integer userId) throws UnsupportedEncodingException {
        System.out.println("id: "+ userId);
        System.out.println("files: " + files);


        String root = "C:\\uploadFiles";
        fileSystemService.createFolder(root);

        videoService.uploadFiles(files, root, userId);

        return videoService.getVideos(userId);

    }
}
