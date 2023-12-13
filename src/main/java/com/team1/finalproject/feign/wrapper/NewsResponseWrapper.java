package com.team1.finalproject.feign.wrapper;

import com.team1.finalproject.feign.dto.NewsResponse;
import lombok.Data;

import java.util.List;

@Data
public class NewsResponseWrapper {

    private List<NewsResponse> news;
}
