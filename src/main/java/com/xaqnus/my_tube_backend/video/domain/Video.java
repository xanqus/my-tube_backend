package com.xaqnus.my_tube_backend.video.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Builder.Default
    private Boolean isTemp;

    private Boolean isPublic;

    private Integer views;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;

    private LocalDateTime regDate;

    private LocalDateTime updatedDate;


    public Video(Long videoId, String videoUrl, String thumbnailUrl, String videoName, Boolean isTemp, Boolean isPublic, Integer views, User user, LocalDateTime regDate, LocalDateTime updatedDate) {

    }


}
