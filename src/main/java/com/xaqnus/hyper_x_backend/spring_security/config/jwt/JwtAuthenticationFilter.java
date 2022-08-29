package com.xaqnus.hyper_x_backend.spring_security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaqnus.hyper_x_backend.spring_security.auth.PrincipalDetails;
import com.xaqnus.hyper_x_backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username, password를 전송하면(post) UsernamePasswordAuthenticationFilter가 동작을함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/v1/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");
        try {
            ObjectMapper om = new ObjectMapper();
            System.out.println("입력정보");
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨.
            // DB에 있는 username과 password가 일치한다
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // => 로그인이 되었다는 뜻
            PrincipalDetails principalDetails= (PrincipalDetails) authentication.getPrincipal();
            System.out.println("=================");
            System.out.println("로그인 완료됨: " + principalDetails.getUser().getUsername()); // 로그인 정상적으로 되었다는뜻
            // authentication 객체가 session 영역에 저장됨.
            // 리턴의 이유는 권한 관리를 security가 대신 해주기때문에 편하려고 하는거임
            // 굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 session 넣어준다.


            return authentication;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfultAuthentication 실행됨: 인증 완료");

        PrincipalDetails principalDetails= (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("login token")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader("Authorization", JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
