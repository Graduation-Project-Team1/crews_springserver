package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfoResponse {
    private Long id;
    private String name;
    private Integer age;
    private Timestamp dateOfBirth;
    private Integer height;
    private Integer shirtNumber;
    private String position;
    private String nation;


    public PlayerInfoResponse(Player player){
        this.id = player.getId();
        this.name = player.getName();
        this.age = player.getAge();
        this.dateOfBirth = player.getDateOfBirth();
        this.height = player.getHeight();
        this.shirtNumber = player.getShirtNumber();
        this.position = player.getPosition();
        this.nation = player.getNation();
    }

}
