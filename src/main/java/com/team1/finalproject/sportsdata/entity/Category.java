package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long sportsId;
    @Column
    private String sportsName;
    @Column
    private Long regionId;
    @Column
    private String regionName;
    @Column
    private Long leagueId;
    @Column
    private Long uniqueId;
    @Column
    private String leagueName;
    @Builder

    public Category(Long sportsId, String sportsName, Long regionId, String regionName,
                    Long leagueId, Long uniqueId, String leagueName) {
        this.sportsId = sportsId;
        this.sportsName = sportsName;
        this.regionId = regionId;
        this.regionName = regionName;
        this.leagueId = leagueId;
        this.uniqueId = uniqueId;
        this.leagueName = leagueName;
    }
}
