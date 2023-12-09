package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String nickName;
    private String password;
    private String chkPassword;
    private String kakaoId;
    private String googleId;
    private Long socialCode;

    /**
     * CREWS 자체 로그인
     * @param email CREWS 유저 이메일
     * @param nickName CREWS 유저 이름
     * @param password CREWS 유저 비밀번호
     * @param chkPassword CREWS 유저 비밀번호 확인
     */
    @Builder
    public SignUpRequest(String email, String nickName, String password, String chkPassword) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.chkPassword = chkPassword;
    }


    /**
     * 소셜 로그인
     * @param email 계정 이메일
     * @param nickName 계정 이름
     * @param socialId 플랫폼별 계정 고유 ID
     * @param  socialCode 대상 플랫폼(0L: 카카오, 1L: 구글)
     */
    @Builder
    public SignUpRequest(String email, String nickName, String socialId, Long socialCode){
        this.email = email;
        this.nickName = nickName;
        this.kakaoId = socialId;
        if (socialCode == 0L)
            this.kakaoId = socialId;
        else if (socialCode == 1L)
            this.googleId = socialId;
        this.socialCode = socialCode;
    }

}
