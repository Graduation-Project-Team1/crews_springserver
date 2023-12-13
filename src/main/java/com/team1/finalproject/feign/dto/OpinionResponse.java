package com.team1.finalproject.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OpinionResponse {
    @JsonProperty("positive")
    private Long positive;
    @JsonProperty("negative")
    private Long negative;
    @JsonProperty("p_keywords")
    private List<String> pKeywords;
    @JsonProperty("n_keywords")
    private List<String> nKeywords;
}
