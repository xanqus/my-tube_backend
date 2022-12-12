package com.xaqnus.my_tube_backend;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.video.dao.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MyTubeBackendApplicationTests {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;




}
