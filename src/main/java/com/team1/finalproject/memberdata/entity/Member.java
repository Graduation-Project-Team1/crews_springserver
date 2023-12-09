package com.team1.finalproject.memberdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private String nickName;
    @Column
    private String password;
    @Column(name = "kakao_id")
    private String kakaoId;
    // 카카오 로그인은 사업자 등록시에만 이메일을 필수요소로 받아올 수 있음
    @Column(name = "google_id")
    private String googleId;
    @Column
    private java.sql.Timestamp last_access_date;
    @OneToOne
    @JoinColumn(name = "preferences_id")
    private Preferences preferences;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Builder
    public Member(String email, String nickName, String socialId, Long socialCode) {
        this.email = email;
        this.nickName = nickName;
        if (socialCode == 0L)
            this.kakaoId = socialId;
        else if (socialCode == 1L)
            this.googleId = socialId;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateAccessDate() {
        this.last_access_date = Timestamp.valueOf(LocalDateTime.now());
    }
}
