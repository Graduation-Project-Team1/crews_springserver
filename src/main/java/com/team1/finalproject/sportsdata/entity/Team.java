package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<SeasonTeam> seasonTeams = new ArrayList<>();

    @Builder
    public Team(Long id, String name, String code){
        this.id = id;
        this.name = name;
        this.code = code;
    }
}
