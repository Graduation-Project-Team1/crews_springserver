package com.team1.finalproject.memberdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetPreferencesRequest {

    private Long teamId;

    private Long playerId;
}
