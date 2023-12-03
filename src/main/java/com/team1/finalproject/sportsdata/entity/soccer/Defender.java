
package com.team1.finalproject.sportsdata.entity.soccer;

import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Defender extends SoccerPlayer {
    private Long goals;
    private Long assists;

    public Defender(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                    String nation, String position, Team team, String code,
                    Long fouls, Long yellowCards, Long redCards, Long appearances,
                    Long goals, Long assists) {
        // 부모 클래스의 생성자 호출
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code, fouls, yellowCards, redCards, appearances);
        this.goals = goals;
        this.assists = assists;
    }

}

