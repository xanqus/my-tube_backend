package com.xaqnus.my_tube_backend.video.controller;

import com.xaqnus.my_tube_backend.fileSystem.service.FileSystemService;
import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.domain.VideoDocument;
import com.xaqnus.my_tube_backend.video.dto.VideoDto;
import com.xaqnus.my_tube_backend.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static com.xaqnus.my_tube_backend.util.Util.getRemoteAddr;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final FileSystemService fileSystemService;

    @GetMapping("")
    public List<VideoDto> getVideos(@RequestParam("channelId") Long channelId) {
        return videoService.getVideos(channelId);
    }

    @GetMapping("/{videoId}")
    public VideoDto getVideo(@PathVariable("videoId") Long videoId, HttpServletRequest request) {
        System.out.println("비디오 조회");
        String ip = getRemoteAddr(request);

        return videoService.getVideo(videoId, "61.98.31.98");
    }

    @GetMapping("/channel/{channelId}")
    public List<VideoDto> getVideosByChannelId(@PathVariable("channelId") Long channelId) {
        return videoService.getVideosByChannelId(channelId);
    }

    @PostMapping("")
    public void uploadVideos(@RequestParam("files") List<MultipartFile> files, @RequestParam("channelId") Long channelId) throws IOException, JCodecException {

        String root = "";
        String osName = System.getProperty("os.name");

        if(osName.contains("Linux")) {
            root = "/uploadFiles";

        } else if(osName.contains("Window")) {
            root = "C:\\uploadFiles";
        }
        fileSystemService.createFolder(root);

        videoService.uploadFiles(files, channelId, root);
    }

    @PatchMapping("/{videoId}")
    public void updateVideo(@PathVariable("videoId") int videoId, @RequestBody Video video) {
        videoService.updateVideo(videoId, video);
    }

    @GetMapping("/search")
    public List<VideoDocument> getVideosSearchedByElasticsearch(@RequestParam("title")String title, @RequestParam("description")String description) {

        return videoService.getVideosByTitleAndDescriptionMatch(title, description);
    }


//    @PostMapping("/awstest")
//    public List<String> upload(@RequestParam("video") List<MultipartFile> files) throws IOException, RuntimeException {
//        List<String> strArr = new ArrayList<>();
//        files.stream()
//                .forEach(file -> {
//                    String s3FileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//                    ObjectMetadata objMeta = new ObjectMetadata();
//
//                    try {
//                        objMeta.setContentLength(file.getInputStream().available());
//                        amazonS3.putObject(bucket, s3FileName, file.getInputStream(), objMeta);
//                        strArr.add(amazonS3.getUrl(bucket, s3FileName).toString());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//        return strArr;
//    }
}
