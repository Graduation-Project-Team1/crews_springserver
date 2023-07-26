package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.SetUserPreferencesRequestDto;
import com.team1.finalproject.memberdata.dto.ViewUserDataRequestDto;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.entity.Preferences;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.repository.PreferencesRepository;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PreferencesRepository preferencesRepository;
    private final PlayerRepository playerRepository;
    @Override
    public String signin() {
        return null;
    }

    @Override
    public String kakaoLogin() {
        return null;
    }

    @Override
    public String appleLogin() {
        return null;
    }

    @Override
    public String googleLogin() {
        return null;
    }

    @Override
    public Boolean chkduplicateNickname(SetUserPreferencesRequestDto dto) {
        return preferencesRepository.existsByNickname(dto.getNickname());
    }

    @Override
    public String chkUserToken(Token token) {
        return null;
    }

    @Override
    public void setUserPreferences(Member member, SetUserPreferencesRequestDto dto) {
        Preferences preferences = new Preferences(member, dto.getNickname(), dto.getTeam(), dto.getPlayer());
        member.setPreferences(preferences);
        preferencesRepository.save(preferences);
        memberRepository.save(member);
    }

    @Override
    public String viewUserData(ViewUserDataRequestDto dto) {
        return null;
    }
}
