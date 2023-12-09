package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDataResponse {

    private String success;

    private String email;

    private Long teamId;

    private Long playerId;

    public MemberDataResponse(String str) {
        this.success = str;
    }
}
