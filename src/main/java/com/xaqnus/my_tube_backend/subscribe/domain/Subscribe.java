package com.xaqnus.my_tube_backend.subscribe.domain;

import com.xaqnus.my_tube_backend.channel.domain.Channel;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구독 된 채널
    @ManyToOne
    private Channel subscribedChannel;

    // 구독 한 채널
    @ManyToOne
    private Channel channel;
}
