package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import com.team1.finalproject.sportsdata.entity.soccer.SoccerPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoccerPlayerRecordResponse extends PlayerInfoResponse{

    private Long id;
    private String name;
    private Integer age;
    private Timestamp dateOfBirth;
    private Integer height;
    private Integer shirtNumber;
    private String position;
    private String nation;
    private String photo;
    private Long yellowCards;
    private Long redCards;
    private Long appearances;

    public SoccerPlayerRecordResponse(SoccerPlayer soccerPlayer){
        this.id = soccerPlayer.getId();
        this.name = soccerPlayer.getName();
        this.age = soccerPlayer.getAge();
        this.dateOfBirth = soccerPlayer.getDateOfBirth();
        this.height = soccerPlayer.getHeight();
        this.shirtNumber = soccerPlayer.getShirtNumber();
        this.position = soccerPlayer.getPosition();
        this.nation = soccerPlayer.getNation();
        this.photo = soccerPlayer.getPhoto();
        this.yellowCards = soccerPlayer.getYellowCards();
        this.redCards = soccerPlayer.getRedCards();
        this.appearances = soccerPlayer.getAppearances();
    }

}
