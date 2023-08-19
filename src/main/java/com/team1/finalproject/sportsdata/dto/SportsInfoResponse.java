package com.team1.finalproject.sportsdata.dto;

import com.team1.finalproject.sportsdata.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportsInfoResponse {
    private Long sportsId;

    private String sportsName;

    public SportsInfoResponse(Category category) {
        this.sportsId = category.getSportsId();
        this.sportsName = category.getSportsName();
    }

}
