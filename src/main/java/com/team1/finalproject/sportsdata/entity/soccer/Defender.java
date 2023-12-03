
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
public class Defender extends SoccerPlayer {
    @Column
    private Long tackles;
    @Column
    private Long interceptions;
    @Column
    private Long clearances;
    @Column
    private Long blockedShots;
    @Column
    private Long totalContest;
    @Column
    private Long totalDuelsWon;
    @Column
    private Long aerialDuelsWon;

    public Defender(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                    String nation, String position, Team team, String code,
                    Long yellowCards, Long redCards, Long appearances,
                    Long tackles, Long interceptions, Long clearances,
                    Long blockedShots, Long totalContest, Long totalDuelsWon, Long aerialDuelsWon) {
        // 부모 클래스의 생성자 호출
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code, yellowCards, redCards, appearances);
        this.tackles = tackles;
        this.interceptions = interceptions;
        this.clearances = clearances;
        this.blockedShots = blockedShots;
        this.totalContest = totalContest;
        this.totalDuelsWon = totalDuelsWon;
        this.aerialDuelsWon = aerialDuelsWon;
    }

}

