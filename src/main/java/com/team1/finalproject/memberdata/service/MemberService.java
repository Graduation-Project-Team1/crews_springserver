package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.*;
import com.team1.finalproject.memberdata.entity.Member;
import org.springframework.security.core.token.Token;

public interface MemberService {
    String signin(SigninRequest dto);

    void logIn(LoginRequest dto);

    String kakaoLogin();

    String appleLogin();

    String googleLogin();

    Boolean chkduplicateNickname(SetPreferencesRequest dto);

    Member chkMemberToken(Token token);
    void updateMemberPassword(UpdatePasswordRequest dto, String email);

    void setMemberPreferences(String email, SetPreferencesRequest dto);
    void updateMemberPreference(String email, UpdatePreferencesRequest dto);

    MemberDataResponse viewMemberData(Long memberId);

    void resignMember(Long memberId);
    PreferencesResponse chkMemberPreference(String email);
}
