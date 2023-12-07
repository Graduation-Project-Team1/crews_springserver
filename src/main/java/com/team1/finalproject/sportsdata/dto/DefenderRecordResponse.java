package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.soccer.Defender;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefenderRecordResponse extends SoccerPlayerRecordResponse{
    private Long id;
    private String name;
    private Integer age;
    private Timestamp dateOfBirth;
    private Integer height;
    private Integer shirtNumber;
    private String position;
    private String nation;
    private String teamName;
    private Long yellowCards;
    private Long redCards;
    private Long appearances;
    private Long tackles;
    private Long interceptions;
    private Long clearances;
    private Long blockedShots;
    private Long totalContest;
    private Long totalDuelsWon;
    private Long aerialDuelsWon;

    public DefenderRecordResponse(Defender defender){
        this.id = defender.getId();
        this.name = defender.getName();
        this.age = defender.getAge();
        this.dateOfBirth = defender.getDateOfBirth();
        this.height = defender.getHeight();
        this.shirtNumber = defender.getShirtNumber();
        this.position = defender.getPosition();
        this.nation = defender.getNation();
        this.teamName = defender.getTeam().getName();
        this.yellowCards = defender.getYellowCards();
        this.redCards = defender.getRedCards();
        this.appearances = defender.getAppearances();
        this.tackles = defender.getTackles();
        this.interceptions = defender.getInterceptions();
        this.clearances = defender.getClearances();
        this.blockedShots = defender.getBlockedShots();
        this.totalContest = defender.getTotalContest();
        this.totalDuelsWon = defender.getTotalDuelsWon();
        this.aerialDuelsWon = defender.getAerialDuelsWon();
    }

}
