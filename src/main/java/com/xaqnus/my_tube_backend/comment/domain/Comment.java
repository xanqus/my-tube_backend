package com.xaqnus.my_tube_backend.comment.domain;

import com.xaqnus.my_tube_backend.user.domain.User;
import com.xaqnus.my_tube_backend.video.domain.Video;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@Entity
@Builder
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long likes;

    @ManyToOne
    private Video video;

    @ManyToOne
    private User user;
}