package com.team1.finalproject.config.jwt;

import com.team1.finalproject.config.UserDetailsImpl;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static com.team1.finalproject.config.jwt.JwtProperties.*;


@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            MemberRepository memberRepository,
            JwtTokenUtils jwtTokenUtils) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String jwtHeader = getHeader(request, response, chain);
        if (jwtHeader == null) {
            return;
        }
        log.info("인증이나 권한이 필요한 주소 요청이 됨");
        String jwtToken = getJwtToken(jwtHeader);
        String email = jwtTokenUtils.extractUserEmail(jwtToken);
        checkUser(email);
        chain.doFilter(request, response);
    }

    public static String getHeader(HttpServletRequest request, HttpServletResponse response,
                                   FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(HEADER_STRING);
        if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return null;
        }
        return jwtHeader;
    }

    public static String getJwtToken(String jwtHeader) {
        return jwtHeader.replace(TOKEN_PREFIX, "");
    }

    public void checkUser(String email) {
        if (email != null) {
            Member user = memberRepository.findByEmail(email).orElseThrow(
                    () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
            );
//            user.setVisitedTime(LocalDateTime.now());
//            userRepository.save(user);
            UserDetails userDetails = new UserDetailsImpl(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
