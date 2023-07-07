package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sports {
    @Id
    private Long id;
    @Column
    private String name;

    @Builder
    public Sports(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
