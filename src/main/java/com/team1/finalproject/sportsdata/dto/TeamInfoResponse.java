package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Manager;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfoResponse {

    private Long teamId;

    private String teamName;

    private Long managerId;

    private String managerName;

    /** 팀 정보 반환
     *
     * @param team 팀 엔티티
     * @param manager 소속 감독 엔티티
     */

    public TeamInfoResponse(Team team, Manager manager){
        this.teamId = team.getId();
        this.teamName = team.getName();
        this.managerId = manager.getId();
        this.managerName = manager.getName();
    }

    public TeamInfoResponse(Team team){
        this.teamId = team.getId();
        this.teamName = team.getName();
    }
}
