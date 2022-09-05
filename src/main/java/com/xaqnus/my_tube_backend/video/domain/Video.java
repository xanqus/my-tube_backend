package com.xaqnus.my_tube_backend.video.domain;

import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255)
    private String videoUrl;

    @Column(length= 255)
    private String thumbnailUrl;

    @Column(length = 50)
    private String videoName;

    private Boolean isTemp;

    private Boolean isPublic;

    private Integer views;

    @ManyToOne
    private User user;

    private LocalDateTime regDate;

    private LocalDateTime updatedDate;
}
