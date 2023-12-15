package com.team1.finalproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team1.finalproject.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if (authException instanceof UsernameNotFoundException) {
            // 사용자를 찾을 수 없는 경우
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResponse = new ErrorResponse(""+HttpStatus.NOT_FOUND.value(), "User not found");
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        } else {
            // 기타 인증 오류 처리
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse errorResponse = new ErrorResponse(""+HttpStatus.UNAUTHORIZED.value(), "Authentication failed");
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }
}
