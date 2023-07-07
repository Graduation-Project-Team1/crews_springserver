package com.team1.finalproject.sportsdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tournament {
    @Id
    private Long id;
    @Column
    private Long uniqueId;
    @Column
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "category_id")
    private Category category;

    @Builder
    public Tournament(Long id, Long uniqueId, String name, Category category){
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
    }
}
