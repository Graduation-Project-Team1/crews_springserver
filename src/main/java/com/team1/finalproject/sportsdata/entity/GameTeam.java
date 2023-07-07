package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameTeam {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "team_home_id")
    private Team teamHome;
    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team teamAway;

    @Builder
    public GameTeam(Game game, Team teamHome, Team teamAway) {
        this.game = game;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
    }
}
