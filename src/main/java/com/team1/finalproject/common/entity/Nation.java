package com.team1.finalproject.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nation {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    @Builder
    public Nation(String name){
        this.name = name;
    }
}
