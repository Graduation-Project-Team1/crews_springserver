
package com.team1.finalproject.sportsdata.entity.soccer;

import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Midfielder extends SoccerPlayer {
    @Column
    private Long goals;
    @Column
    private Long assists;
    @Column
    private Long totalPasses;
    @Column
    private Long accuratePasses;
    @Column
    private Long accurateCrosses;
    @Column
    private Long interceptions;
    @Column
    private Long touches;

    public Midfielder(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                      String nation, String position, Team team, String code, String photo,
                      Long yellowCards, Long redCards, Long appearances,
                      Long goals, Long assists, Long totalPasses, Long accuratePasses,
                      Long accurateCrosses, Long interceptions, Long touches) {
        // 부모 클래스의 생성자 호출
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code, photo, yellowCards, redCards, appearances);
        this.goals = goals;
        this.assists = assists;
        this.totalPasses = totalPasses;
        this.accuratePasses = accuratePasses;
        this.accurateCrosses = accurateCrosses;
        this.interceptions = interceptions;
        this.touches = touches;
    }

}

