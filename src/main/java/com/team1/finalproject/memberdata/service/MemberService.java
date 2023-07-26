package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.SetUserPreferencesRequestDto;
import com.team1.finalproject.memberdata.dto.ViewUserDataRequestDto;
import com.team1.finalproject.memberdata.entity.Member;
import org.springframework.security.core.token.Token;

public interface MemberService {
    String signin();

    String kakaoLogin();

    String appleLogin();

    String googleLogin();

    Boolean chkduplicateNickname(SetUserPreferencesRequestDto dto);

    String chkUserToken(Token token);

    void setUserPreferences(Member member, SetUserPreferencesRequestDto dto);

    String viewUserData(ViewUserDataRequestDto dto);
}
