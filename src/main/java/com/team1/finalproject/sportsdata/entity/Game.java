package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {
    @Id
    private Long id;
    @Column(name = "is_started")
    private String isStarted;
    @Column(name = "round_num")
    private Long roundNum;
    @Column(name = "date_time")
    private Timestamp dateTime;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "team_home_id")
    private Team teamHome;
    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team teamAway;

    @Builder

    public Game(Long id, String isStarted, Long roundNum, Timestamp dateTime,
                Category category, Team teamHome, Team teamAway) {
        this.id = id;
        this.isStarted = isStarted;
        this.roundNum = roundNum;
        this.dateTime = dateTime;
        this.category = category;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
    }
}
