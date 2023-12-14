package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.SoccerTeam;
import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamSeasonRecordResponse {

    private Long teamId;
    private String teamName;
    private String recentForm;
    private Long matches;
    private Long wins;
    private Long losses;
    private Long draws;
    private Long points;
    private Long goalsScored;
    private Long goalsConceded;
    private Long goalDiff;
    private Long assists;
    private Long fouls;

    public TeamSeasonRecordResponse(SoccerTeam soccerTeam) {
        Team team = soccerTeam.getTeam();
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.recentForm = soccerTeam.getRecentForm();
        this.matches = soccerTeam.getMatches();
        this.wins = soccerTeam.getWins();
        this.losses = soccerTeam.getLosses();
        this.draws = soccerTeam.getDraws();
        this.points = soccerTeam.getPoints();
        this.goalsScored = soccerTeam.getGoalsScored();
        this.goalsConceded = soccerTeam.getGoalsConceded();
        this.goalDiff = soccerTeam.getGoalDiff();
        this.assists = soccerTeam.getAssists();
        this.fouls = soccerTeam.getFouls();
    }
}
