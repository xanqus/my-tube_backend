package com.xaqnus.my_tube_backend.subscribe.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribedChannelListDto {

    private Long id;

    private List<Long> subscribedChannelIdList = new ArrayList<>();

    public void addSubscribedChannelId(Long subscribedChannelId) {
        subscribedChannelIdList.add(subscribedChannelId);
    }
}
