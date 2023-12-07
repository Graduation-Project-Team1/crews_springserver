package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.soccer.Midfielder;
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
public class MidfielderRecordResponse extends SoccerPlayerRecordResponse{
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
    private Long goals;
    private Long assists;
    private Long totalPasses;
    private Long accuratePasses;
    private Long accurateCrosses;
    private Long interceptions;
    private Long touches;

    public MidfielderRecordResponse(Midfielder midfielder){
        this.id = midfielder.getId();
        this.name = midfielder.getName();
        this.age = midfielder.getAge();
        this.dateOfBirth = midfielder.getDateOfBirth();
        this.height = midfielder.getHeight();
        this.shirtNumber = midfielder.getShirtNumber();
        this.position = midfielder.getPosition();
        this.nation = midfielder.getNation();
        this.teamName = midfielder.getTeam().getName();
        this.yellowCards = midfielder.getYellowCards();
        this.redCards = midfielder.getRedCards();
        this.appearances = midfielder.getAppearances();
        this.goals = midfielder.getGoals();
        this.assists = midfielder.getAssists();
        this.totalPasses = midfielder.getTotalPasses();
        this.accuratePasses = midfielder.getAccuratePasses();
        this.accurateCrosses = midfielder.getAccurateCrosses();
        this.interceptions = midfielder.getInterceptions();
        this.touches = midfielder.getTouches();
    }

}
