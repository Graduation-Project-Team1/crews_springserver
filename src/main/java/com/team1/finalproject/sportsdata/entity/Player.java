package com.team1.finalproject.sportsdata.entity;

import com.team1.finalproject.common.entity.Nation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private int age;
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    @Column
    private Long height;
    @Column(name = "shirt_number")
    private Long shirtNumber;
    @Column
    private String position;
    @ManyToOne
    @JoinColumn(name = "nationality")
    private Nation nation;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public Player(Long id, String name, Timestamp dateOfBirth, Long height, Long shirtNumber,
                  Nation nation, String position, Team team){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.shirtNumber = shirtNumber;
        this.nation = nation;
        this.position = position;
        this.team = team;
        this.age = calculateAge(dateOfBirth);
    }
    public int calculateAge(Timestamp timestamp){
        Date birthDate = new Date(timestamp.getTime());
        Date currentDate = new Date();
        java.util.Calendar birthCalendar = java.util.Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        java.util.Calendar currentCalendar = java.util.Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int age = currentCalendar.get(java.util.Calendar.YEAR) - birthCalendar.get(java.util.Calendar.YEAR);

        // 생일이 지났는지 체크
        if (currentCalendar.get(java.util.Calendar.MONTH) < birthCalendar.get(java.util.Calendar.MONTH)) {
            age--;
        } else if (currentCalendar.get(java.util.Calendar.MONTH) == birthCalendar.get(java.util.Calendar.MONTH)
                && currentCalendar.get(java.util.Calendar.DAY_OF_MONTH) < birthCalendar.get(java.util.Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }
    public boolean containsNull(){
        return id == null || name == null || dateOfBirth == null || height == null || shirtNumber == null || nation == null || position == null;
    }
}
