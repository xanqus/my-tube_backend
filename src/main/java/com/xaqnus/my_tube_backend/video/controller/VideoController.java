package com.xaqnus.my_tube_backend.video.controller;

import com.xaqnus.my_tube_backend.video.domain.Video;
import com.xaqnus.my_tube_backend.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("")
    public List<Video> getVideos(@RequestParam("userId") Integer userId) {
        return videoService.getVideos(userId);
    }


    @PostMapping("")
    public String uploadVideos(@RequestParam("files") List<MultipartFile> files, @RequestParam("userId") Integer userId) throws UnsupportedEncodingException {
        System.out.println("id: "+ userId);
        System.out.println("files: " + files);


        String root = "C:\\uploadFiles";
        File fileCheck = new File(root);
        if(!fileCheck.exists()) fileCheck.mkdirs();
        List<Map<String, String>> fileList = new ArrayList<>();
        for(int i = 0; i < files.size(); i++) {
            String originFile = files.get(i).getOriginalFilename();
            String ext = originFile.substring(originFile.lastIndexOf("."));
            String changeFile = UUID.randomUUID().toString() + ext;

            Map<String, String> map = new HashMap<>();
            map.put("originFile", originFile);
            map.put("changeFile", changeFile);

            fileList.add(map);
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

        return "동영상 업로드됨";

    }
}
