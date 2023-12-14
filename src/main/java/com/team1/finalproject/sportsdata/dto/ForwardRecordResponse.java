package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForwardRecordResponse extends SoccerPlayerRecordResponse{

    private Long id;
    private String name;
    private Integer age;
    private Timestamp dateOfBirth;
    private Integer height;
    private Integer shirtNumber;
    private String position;
    private String nation;
    private String teamName;
    private String photo;
    private Long yellowCards;
    private Long redCards;
    private Long appearances;
    private Long goals;
    private Long assists;
    private Long totalShots;
    private Long shotsOnTarget;
    private Long penaltyGoals;
    private Long touches;
    private Long successfulDribbles;

    public ForwardRecordResponse(Forward forward){
        this.id = forward.getId();
        this.name = forward.getName();
        this.age = forward.getAge();
        this.dateOfBirth = forward.getDateOfBirth();
        this.height = forward.getHeight();
        this.shirtNumber = forward.getShirtNumber();
        this.position = forward.getPosition();
        this.nation = forward.getNation();
        this.teamName = forward.getTeam().getName();
        this.photo = forward.getPhoto();
        this.yellowCards = forward.getYellowCards();
        this.redCards = forward.getRedCards();
        this.appearances = forward.getAppearances();
        this.goals = forward.getGoals();
        this.assists = forward.getAssists();
        this.totalShots = forward.getTotalShots();
        this.shotsOnTarget = forward.getShotsOnTarget();
        this.penaltyGoals = forward.getPenaltyGoals();
        this.touches = forward.getTouches();
        this.successfulDribbles = forward.getSuccessfulDribbles();
    }

}
