package com.team1.finalproject.memberdata.dto;

import com.team1.finalproject.memberdata.entity.Preferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPreferencesResponse {

    private String nickname;

    private Long teamId;

    private Long playerId;

    public GetPreferencesResponse(Preferences preferences) {
        this.nickname = preferences.getNickname();
        this.teamId = preferences.getPlayer().getId();
        this.playerId = preferences.getTeam().getId();
    }
}
