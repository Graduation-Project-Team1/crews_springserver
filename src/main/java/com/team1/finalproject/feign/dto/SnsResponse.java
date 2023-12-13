package com.team1.finalproject.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnsResponse {
    @JsonProperty("sns")
    private String sns;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("rt")
    private Long rt;
    @JsonProperty("heart")
    private Long heart;
    @JsonProperty("body")
    private String body;
    @JsonProperty("date")
    private Integer date;
    @JsonProperty("url")
    private String url;
}
