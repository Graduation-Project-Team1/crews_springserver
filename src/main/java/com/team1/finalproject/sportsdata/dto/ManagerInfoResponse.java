package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerInfoResponse {

    private Long id;
    private String name;
    private Timestamp dateOfBirth;
    private String nation;
    private Long teamId;

    public ManagerInfoResponse(Manager manager){
        this.id = manager.getId();
        this.name = manager.getName();
        this.dateOfBirth = manager.getDateOfBirth();
        this.nation = manager.getNationality();
        this.teamId = manager.getTeam().getId();
    }

}
