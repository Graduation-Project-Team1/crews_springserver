
package com.team1.finalproject.sportsdata.entity.soccer;

import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Forward extends SoccerPlayer {
    @Column
    private Long goals;
    @Column
    private Long assists;
    @Column
    private Long totalShots;
    @Column
    private Long shotsOnTarget;
    @Column
    private Long penaltyGoals;
    @Column
    private Long touches;
    @Column
    private Long successfulDribbles;

    public Forward(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                   String nation, String position, Team team, String code,
                   Long yellowCards, Long redCards, Long appearances,
                   Long goals, Long assists, Long totalShots, Long shotsOnTarget,
                   Long penaltyGoals, Long touches, Long successfulDribbles) {
        // 부모 클래스의 생성자 호출
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code, yellowCards, redCards, appearances);
        this.goals = goals;
        this.assists = assists;
        this.totalShots = totalShots;
        this.shotsOnTarget = shotsOnTarget;
        this.penaltyGoals = penaltyGoals;
        this.touches = touches;
        this.successfulDribbles = successfulDribbles;
    }

}

