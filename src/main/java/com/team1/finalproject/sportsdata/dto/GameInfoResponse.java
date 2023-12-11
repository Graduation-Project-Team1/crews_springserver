package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoResponse {
    private Long id;
    private String isStarted;
    private Long roundNum;
    private Timestamp dateTime;
    private String categoryName;
    private String teamHomeName;
    private Long teamHomeId;
    private String teamAwayName;
    private Long teamAwayId;

    @Builder
    public GameInfoResponse(Game game) {
        this.id = game.getId();
        this.isStarted = game.getIsStarted();
        this.roundNum = game.getRoundNum();
        this.dateTime = game.getDateTime();
        this.categoryName = game.getCategory().getLeagueName();
        this.teamHomeName = game.getTeamHome().getName();
        this.teamHomeId = game.getTeamHome().getId();
        this.teamAwayName = game.getTeamAway().getName();
        this.teamAwayId = game.getTeamAway().getId();
    }
}
