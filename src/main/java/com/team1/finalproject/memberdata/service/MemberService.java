package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.MemberDataResponse;
import com.team1.finalproject.memberdata.dto.SetPreferencesRequest;
import com.team1.finalproject.memberdata.dto.UpdatePreferencesRequest;
import com.team1.finalproject.memberdata.entity.Member;
import org.springframework.security.core.token.Token;

public interface MemberService {
    String signin();

    String kakaoLogin();

    String appleLogin();

    String googleLogin();

    Boolean chkduplicateNickname(SetPreferencesRequest dto);

    String chkMemberToken(Token token);

    void setMemberPreferences(Member member, SetPreferencesRequest dto);
    void updateMemberPreference(Member member, UpdatePreferencesRequest dto);

    MemberDataResponse viewMemberData(Long memberId);

    void resignMember(Long memberId);
}
