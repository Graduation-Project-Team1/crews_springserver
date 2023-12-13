package com.team1.finalproject.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsResponse {
    @JsonProperty("title")
    private String title;
    @JsonProperty("press")
    private String press;
    @JsonProperty("date")
    private Integer date;
    @JsonProperty("url")
    private String url;
}
