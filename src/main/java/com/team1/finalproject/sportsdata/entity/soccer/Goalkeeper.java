
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
public class Goalkeeper extends SoccerPlayer {
    private Long saves;
    private Long goalsConceded; // 실점
    private Long cleanSheet;
    public Goalkeeper(Long id, String name, Timestamp dateOfBirth, int age, Long height, Long shirtNumber,
                      String nation, String position, Team team, String code,
                      Long yellowCards, Long redCards, Long appearances,
                      Long saves, Long goalsConceded, Long cleanSheet) {
        // 부모 클래스의 생성자 호출
        super(id, name, dateOfBirth, age, height, shirtNumber, nation, position, team, code, yellowCards, redCards, appearances);
        this.saves = saves;
        this.goalsConceded = goalsConceded;
        this.cleanSheet = cleanSheet;
    }

}

