package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.soccer.Goalkeeper;
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
public class GoalkeeperRecordResponse extends SoccerPlayerRecordResponse {
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
    private Long saves;
    private Long goalsConceded; // 실점
    private Long cleanSheet;

    public GoalkeeperRecordResponse(Goalkeeper goalkeeper) {
        this.id = goalkeeper.getId();
        this.name = goalkeeper.getName();
        this.age = goalkeeper.getAge();
        this.dateOfBirth = goalkeeper.getDateOfBirth();
        this.height = goalkeeper.getHeight();
        this.shirtNumber = goalkeeper.getShirtNumber();
        this.position = goalkeeper.getPosition();
        this.nation = goalkeeper.getNation();
        this.teamName = goalkeeper.getTeam().getName();
        this.yellowCards = goalkeeper.getYellowCards();
        this.redCards = goalkeeper.getRedCards();
        this.appearances = goalkeeper.getAppearances();
        this.saves = goalkeeper.getSaves();
        this.goalsConceded = goalkeeper.getGoalsConceded();
        this.cleanSheet = goalkeeper.getCleanSheet();
    }

}
