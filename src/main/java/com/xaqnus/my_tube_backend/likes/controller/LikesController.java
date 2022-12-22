package com.xaqnus.my_tube_backend.likes.controller;

import com.xaqnus.my_tube_backend.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/{videoId}")
    public Long getLikeCountByVideoId(@PathVariable Long videoId) {
        return likesService.getLikeCountByVideoId(videoId);
    }

    @PostMapping("/{videoId}/{channelId}")
    public void addLikesToVideo(@PathVariable Long videoId, @PathVariable Long channelId) {
        likesService.addLikes(videoId, channelId);
    }

    @GetMapping("/clickedLikeBefore/{videoId}/{channelId}")
    public Boolean haveClickedLikeBefore(@PathVariable Long videoId, @PathVariable Long channelId) {
        return likesService.haveClikcedLikeBefore(videoId, channelId);
    }


}
