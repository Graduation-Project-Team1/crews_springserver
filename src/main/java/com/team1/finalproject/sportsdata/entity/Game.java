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
    @Column
    private String isStarted;
    @Column(name = "round_num")
    private Long roundNum;
    @Column(name = "date_time")
    private Timestamp dateTime;
    @ManyToOne
    @JoinColumn
    private Tournament tournament;
    @OneToOne(mappedBy = "game")
    private GameTeam gameTeam;

    @Builder
    public Game(Long id, String isStarted, Long roundNum, Timestamp dateTime, Tournament tournament) {
        this.id=id;
        this.isStarted=isStarted;
        this.roundNum=roundNum;
        this.dateTime=dateTime;
        this.tournament=tournament;
    }

}
