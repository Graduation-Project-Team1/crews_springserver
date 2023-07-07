package com.team1.finalproject.memberdata.entity;

import com.team1.finalproject.common.entity.Nation;
import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)") // 중복이 생기는 지 확인 필요
    private UUID id;
    @Column (unique = true)
    private String email;
    @Column
    private String pwd;
    @Column
    private String name;
    @Column (unique = true)
    private String nickname;
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    @Column
    private int age;
    @ManyToOne
    @JoinColumn(name = "nationality")
    private Nation nation;
    @ManyToOne
    @JoinColumn(name = "favorite_team")
    private Team favoriteTeam;

    @Builder
    public Member(UUID id, String email, String pwd, String name, String nickname,
                  Timestamp dateOfBirth, int age, Nation nation, Team favoriteTeam) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.nation = nation;
        this.favoriteTeam = favoriteTeam;
    }
}
