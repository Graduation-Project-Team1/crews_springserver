package com.team1.finalproject.memberdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String kakaoId;
    // 카카오 로그인은 사업자 등록시에만 이메일을 필수요소로 받아올 수 있음
    @Column
    private java.sql.Timestamp last_access_date;
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
