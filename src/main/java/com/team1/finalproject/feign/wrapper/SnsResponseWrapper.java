package com.team1.finalproject.feign.wrapper;

import com.team1.finalproject.feign.dto.SnsResponse;
import lombok.Data;

import java.util.List;

@Data
public class SnsResponseWrapper {

    private List<SnsResponse> sns;
}
