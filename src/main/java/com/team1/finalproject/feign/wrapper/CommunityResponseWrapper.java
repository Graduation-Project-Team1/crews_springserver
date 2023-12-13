package com.team1.finalproject.feign.wrapper;

import com.team1.finalproject.feign.dto.CommunityResponse;
import lombok.Data;

import java.util.List;

@Data
public class CommunityResponseWrapper {

    private List<CommunityResponse> community;
}
