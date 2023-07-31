package com.team1.finalproject.memberdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePreferencesRequest {

    private String nickname;

    private Long teamId;

    private Long playerId;
}
