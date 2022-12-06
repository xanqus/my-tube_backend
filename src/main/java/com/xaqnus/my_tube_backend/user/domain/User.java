package com.xaqnus.my_tube_backend.user.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable = false)
    private String username;

    @Column(unique=true, nullable = false)
    private String email;

    private String password;
    private String roles;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime regDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedDate;

    public List<String> getRoleList() {
        if(this.roles.length() > 0 ) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }


}
