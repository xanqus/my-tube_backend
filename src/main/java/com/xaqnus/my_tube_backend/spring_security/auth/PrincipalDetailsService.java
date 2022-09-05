package com.xaqnus.my_tube_backend.spring_security.auth;

import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import com.xaqnus.my_tube_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


// http://localhost:8287/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("login요청");
        System.out.println("email: " + email);
        User userEntity = userRepository.findByEmail(email);
        System.out.println("loadUserByEamil: " + userEntity);
        return new PrincipalDetails(userEntity);
    }
}
