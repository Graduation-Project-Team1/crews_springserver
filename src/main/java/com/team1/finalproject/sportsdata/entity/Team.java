package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String logo;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<SeasonTeam> seasonTeams = new ArrayList<>();

    public Team(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Team(Long id, String name, String code, List<SeasonTeam> seasonTeams) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.seasonTeams = seasonTeams;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
