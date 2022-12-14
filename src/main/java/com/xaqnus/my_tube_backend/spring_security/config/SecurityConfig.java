package com.xaqnus.my_tube_backend.spring_security.config;

import com.xaqnus.my_tube_backend.spring_security.config.jwt.JwtAuthenticationFilter;
import com.xaqnus.my_tube_backend.spring_security.config.jwt.JwtAuthorizationFilter;
import com.xaqnus.my_tube_backend.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final UserRepository userRepository;


  @Bean

    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return
                http
                //.addFilterBefore(new MyFilter3(), UsernamePasswordAuthenticationFilter.class) //Security Config에 Filter거는 방법
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                        //.addFilter(corsFilter)// @CrossOrigin 어노테이션을 컨트롤러에 걸면 인증이 없을때 작동함, 인증이 있을때는 시큐리티 필터에 cors설정을 걸어줘야함
                        .formLogin().disable()
                        .httpBasic().disable()
                        .apply(new MyCustomDsl())
                        .and()
                        //권한설정
                        .authorizeRequests(authorize -> authorize

//                                        .antMatchers(HttpMethod.POST,"api/v1/join")
//                                        .anonymous()
//                                        .antMatchers(HttpMethod.POST,"api/v1/join")
//                                        .permitAll()
//                                .antMatchers(HttpMethod.POST)
//                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers(HttpMethod.POST, "/api/v1/video/**")
                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                        .antMatchers(HttpMethod.POST, "/api/v1/comment/**")
                                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                        .antMatchers(HttpMethod.POST, "/api/v1/subscribe/**")
                                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers(HttpMethod.PATCH)
                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers(HttpMethod.PUT)
                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

//                                .antMatchers(HttpMethod.POST,"/api/v1/video/**")
//                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                                .antMatchers(HttpMethod.POST,"/api/v1/comment/**")
//                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                                .antMatchers(HttpMethod.POST,"/api/v1/subscribe/**")
//                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers("/api/v1/user/**")
                                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers("/api/v1/manager/**")
                                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                                .antMatchers("/api/v1/admin/**")
                                .access("hasRole('ROLE_ADMIN')")
                                .anyRequest().permitAll()

                        )

                .build();
    }
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http

                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }


}
