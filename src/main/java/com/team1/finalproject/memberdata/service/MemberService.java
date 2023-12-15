package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.*;

public interface MemberService {
    LogInResponse signUp(SignUpRequest dto);

    void logIn(LoginRequest dto);

    Boolean chkDuplicateNickname(SetPreferencesRequest dto);

    UpdatePasswordResponse updateMemberPassword(UpdatePasswordRequest dto, Long memberId);

    LogInResponse setMemberPreferences(SetPreferencesRequest dto, Long memberId) throws ClassNotFoundException;

    PreferencesResponse updateMemberPreferences(UpdatePreferencesRequest dto, Long memberId) throws ClassNotFoundException;

    MemberDataResponse viewMemberData(Long memberId) throws ClassNotFoundException;

    MemberDeletionResponse deleteMember(Long memberId);
    GetPreferencesResponse chkMemberPreference(Long memberId);
}
