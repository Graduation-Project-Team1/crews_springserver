package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {
    private Boolean success;
    private String message;
    private Long memberId;
    private String token;

    @Builder
    public LogInResponse(Boolean success, String message, Long memberId) {
        this.success = success;
        this.message = message;
        this.memberId = memberId;
    }
}
