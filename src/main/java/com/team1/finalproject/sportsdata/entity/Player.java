package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    @Column
    private Integer height;
    @Column(name = "shirt_number")
    private Integer shirtNumber;
    @Column
    private String position;
    @Column(name = "nationality")
    private String nation;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @Column
    private String code;

    //@Builder
    public Player(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                  String nation, String position, Team team, String code) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = Math.toIntExact(height);
        this.shirtNumber = Math.toIntExact(shirtNumber);
        this.nation = nation;
        this.position = position;
        this.team = team;
        this.age = age;
        this.code = code;
    }

    public boolean containsNull() {
        return dateOfBirth == null || height == null || shirtNumber == null || nation == null
                || position == null || team == null || age == null;
    }
}
