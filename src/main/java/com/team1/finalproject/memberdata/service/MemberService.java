package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.*;

public interface MemberService {
    String signUp(SignUpRequest dto);

    void logIn(LoginRequest dto);

    Boolean chkduplicateNickname(SetPreferencesRequest dto);

    UpdatePasswordResponse updateMemberPassword(UpdatePasswordRequest dto, Long memberId);

    PreferencesResponse setMemberPreferences(SetPreferencesRequest dto, Long memberId);

    PreferencesResponse updateMemberPreferences(UpdatePreferencesRequest dto, Long memberId);

    MemberDataResponse viewMemberData(Long memberId);

    MemberDeletionResponse deleteMember(Long memberId);
    GetPreferencesResponse chkMemberPreference(Long memberId);
}
