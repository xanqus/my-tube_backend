package com.xaqnus.my_tube_backend.user.domain;

import com.xaqnus.my_tube_backend.video.domain.Video;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Video> videoList;

    public List<String> getRoleList() {
        if(this.roles.length() > 0 ) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


}
