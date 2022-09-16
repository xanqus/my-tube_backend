package com.xaqnus.my_tube_backend.video.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@DynamicInsert
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String videoUrl;

    @Column(length= 255, nullable = false)
    private String thumbnailUrl;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String filename;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "tinyint(1) default 1", nullable = false)
    private Boolean isTemp;

    @Column(columnDefinition = "tinyint(1) default 1", nullable = false)
    private Boolean isPublic;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer views;

    @Column(columnDefinition = "integer default 0", nullable=false)
    private Integer likeCount;

    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime regDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedDate;

}
