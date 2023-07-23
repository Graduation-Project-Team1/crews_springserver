package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.SetUserPreferencesRequestDto;
import com.team1.finalproject.memberdata.dto.ViewUserDataRequestDto;
import org.springframework.security.core.token.Token;

public interface MemberService {
    String signin();

    String kakaoLogin();

    String appleLogin();

    String googleLogin();

    String chkduplicateNickname(String nickname);

    String chkUserToken(Token token);

    String setUserPreferences(SetUserPreferencesRequestDto dto);

    String viewUserData(ViewUserDataRequestDto dto);
}
