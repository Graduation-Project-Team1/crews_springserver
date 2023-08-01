package com.team1.finalproject.config;

import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository userRepository;

    // 기본로그인
    public UserDetails loadUserByUsername(String email) {
        Member user = userRepository.findByEmail(email)/*.orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
        )*/;
        return new UserDetailsImpl(user);
    }

}
