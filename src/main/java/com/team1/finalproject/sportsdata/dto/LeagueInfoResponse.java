package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueInfoResponse {

    private String regionName;

    private Long leagueId;

    private String leagueName;

    public LeagueInfoResponse(Category category) {
        this.regionName = category.getRegionName();
        this.leagueId = category.getLeagueId();
        this.leagueName = category.getLeagueName();
    }
}
