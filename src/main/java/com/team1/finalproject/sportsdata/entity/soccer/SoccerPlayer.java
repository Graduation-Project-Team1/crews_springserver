package com.team1.finalproject.sportsdata.entity.soccer;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity(name = "soccer_player")
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerPlayer extends Player {
    @Column
    private Long yellowCards;
    @Column
    private Long redCards;
    @Column
    private Long appearances;

    //@Builder(builderMethodName = "soccerPlayerBuilder")
    public SoccerPlayer(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                        String nation, String position, Team team, String code,
                        Long yellowCards, Long redCards, Long appearances) {
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code);
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.appearances = appearances;
    }
}
