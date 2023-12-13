package com.team1.finalproject.feign.wrapper;

import com.team1.finalproject.feign.dto.KeywordResponse;
import lombok.Data;

import java.util.List;

@Data
public class KeywordResponseWrapper {

    private List<KeywordResponse> keywords;
}
