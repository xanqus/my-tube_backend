package com.xaqnus.my_tube_backend.user.controller;

import com.xaqnus.my_tube_backend.channel.service.ChannelService;
import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    private final ChannelService channelService;

    @PostMapping("/api/v1/join")
    public String join(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        //channelService.create(user);

        return "회원가입 완료";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("session")
    public String getSession() {
        String contextHolder = SecurityContextHolder.getContext().getAuthentication().toString();
        System.out.println("contextHolder");
        System.out.println(contextHolder);
        return "session";
    }
}
