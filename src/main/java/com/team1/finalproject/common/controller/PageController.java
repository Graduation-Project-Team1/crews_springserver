package com.team1.finalproject.common.controller;

import com.team1.finalproject.memberdata.dto.MemberDataRequest;
import com.team1.finalproject.memberdata.dto.MemberDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PageController {
    @GetMapping("/main")
    public MemberDataResponse showMainPage(@RequestBody MemberDataRequest dto) {
        return null;
    }

    @GetMapping("/news")
    public MemberDataResponse showNewsPage(@RequestBody MemberDataRequest dto) {
        return null;
    }

    @GetMapping("/podcast")
    public MemberDataResponse showPodcastPage(@RequestBody MemberDataRequest dto) {
        return null;
    }

    @GetMapping("/analysis")
    public MemberDataResponse showAnalysisPage(@RequestBody MemberDataRequest dto) {
        return null;
    }

    @GetMapping("/myteam")
    public MemberDataResponse showMyTeamPage(@RequestBody MemberDataRequest dto) {
        return null;
    }
}
