package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerTeam {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;
    @Column
    private Long matches;
    @Column
    private Long wins;
    @Column
    private Long losses;
    @Column
    private Long draws;
    @Column
    private Long points;
    @Column
    private Long goalsScored;
    @Column
    private Long goalsConceded;
    @Column
    private Long goalDiff;
    @Column
    private Long assists;
    @Column
    private Long fouls;

    public SoccerTeam(Team team, Long matches, Long wins, Long losses, Long draws, Long points,
                      Long goalsScored, Long goalsConceded, Long assists, Long fouls) {
        this.team = team;
        this.matches = matches;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.points = points;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.goalDiff = goalsScored - goalsConceded;
        this.assists = assists;
        this.fouls = fouls;
    }
}
