package com.team1.finalproject.sportsdata.entity;

import com.team1.finalproject.common.entity.Nation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private int age;
    @Column (name = "date_of_birth")
    private Timestamp dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "nationality")
    private Nation nation;
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Manager(Long id, String name, int age, Timestamp dateOfBirth, Nation nation, Team team){
        this.id = id;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.nation = nation;
        this.team = team;
    }
}
