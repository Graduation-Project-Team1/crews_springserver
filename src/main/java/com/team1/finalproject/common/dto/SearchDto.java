package com.team1.finalproject.common.dto;

import com.team1.finalproject.memberdata.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
public class SearchDto {
    private Member user;
    private String search;
    private LocalDate startDate;
    private LocalDate endDate;
    private Pageable pageable;

    @Builder
    public SearchDto(Member user, String search, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        this.user = user;
        this.search = search;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pageable = pageable;
    }
}
