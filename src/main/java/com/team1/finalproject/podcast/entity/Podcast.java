package com.team1.finalproject.podcast.entity;

import com.team1.finalproject.memberdata.entity.Preferences;
import com.team1.finalproject.sportsdata.entity.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String text;
    @Column
    private LocalDateTime madeAt;
    @Column
    private String duration;
    @Column
    private String path;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Podcast(String title, String text, LocalDateTime madeAt, String duration, String path, Team team) {
        this.title = title;
        this.text = text;
        this.madeAt = madeAt;
        this.duration = duration;
        this.path = path;
        this.team = team;
    }
}
