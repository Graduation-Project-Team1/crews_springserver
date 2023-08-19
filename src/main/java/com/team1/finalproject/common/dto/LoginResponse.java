package com.team1.finalproject.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private Boolean success;
    private String message;
    private String token;

    @Builder
    public LoginResponse(Boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }
}
