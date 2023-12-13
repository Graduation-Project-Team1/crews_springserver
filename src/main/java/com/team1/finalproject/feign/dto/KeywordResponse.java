package com.team1.finalproject.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeywordResponse {
    @JsonProperty("rank")
    private Long rank;
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("buzz")
    private Long buzz;
    @JsonProperty("comment")
    private String comment;
}
