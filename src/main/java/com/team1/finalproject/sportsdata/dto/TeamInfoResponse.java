package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfoResponse {
    private Long teamId;

    private String teamName;

    public TeamInfoResponse(Team team) {
        this.teamId = team.getId();
        this.teamName = team.getName();
    }
}
