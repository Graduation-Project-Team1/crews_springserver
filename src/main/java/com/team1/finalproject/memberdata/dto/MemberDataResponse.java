package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDataResponse {

    private String email;

    private String nickname;

    private Long teamId;

    private Long playerId;
}
