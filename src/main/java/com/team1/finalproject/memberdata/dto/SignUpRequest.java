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
     * 카카오 로그인
     * @param email 카카오 계정 이메일
     * @param nickName 카카오 계정 이름
     * @param kakaoId 카카오 계정 고유 ID
     */
    @Builder
    public SignUpRequest(String email, String nickName, String kakaoId){
        this.email = email;
        this.nickName = nickName;
        this.kakaoId = kakaoId;
    }

}
