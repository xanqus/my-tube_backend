package com.xaqnus.my_tube_backend.video.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideosResponseDto {
    private List<VideoDto> items;
}
