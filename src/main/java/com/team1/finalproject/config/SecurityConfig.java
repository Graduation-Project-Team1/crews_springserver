package com.team1.finalproject.config;

import com.team1.finalproject.config.jwt.JwtAuthenticationFilter;
import com.team1.finalproject.config.jwt.JwtAuthorizationFilter;
import com.team1.finalproject.config.jwt.JwtTokenUtils;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CharacterEncodingFilter filter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(
                http.getSharedObject(AuthenticationConfiguration.class));
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                authenticationManager, jwtTokenUtils);
        jwtAuthenticationFilter.setUsernameParameter("email");

        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/member",
                                "/member/{id}/preferences",
                                "/login", "/login/oauth2/**",
                                "/kakao/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter())
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository, jwtTokenUtils))
                .exceptionHandling((exceptionHandling)->exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addAllowedOrigin("http://localhost:3090");
        config.addExposedHeader("*");
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 api 메소드 요청을 허용하겠다.
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
