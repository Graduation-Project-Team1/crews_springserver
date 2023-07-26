package com.team1.finalproject.memberdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (unique = true)
    private String email;
    @OneToOne
    @JoinColumn(name = "preferences_id")
    private Preferences preferences;

    @Builder
    public Member(String email) {
        this.email = email;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}
