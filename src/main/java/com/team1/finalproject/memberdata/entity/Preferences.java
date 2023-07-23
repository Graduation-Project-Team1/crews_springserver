package com.team1.finalproject.memberdata.entity;

import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preferences {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(mappedBy = "preferences")
    private Member member;
    @Column
    private String nickname;
    @ManyToOne
    @JoinColumn(name = "favorite_team")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "favorite_player")
    private Player player;


}
