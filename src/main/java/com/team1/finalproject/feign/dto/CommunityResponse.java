package com.team1.finalproject.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityResponse {
    @JsonProperty("title")
    private String title;
    @JsonProperty("press")
    private String press;
    @JsonProperty("date")
    private Integer date;
    @JsonProperty("view")
    private Long view;
    @JsonProperty("url")
    private String url;
}
