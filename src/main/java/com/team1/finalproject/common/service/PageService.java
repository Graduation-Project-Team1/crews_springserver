package com.team1.finalproject.common.service;

import com.team1.finalproject.common.dto.*;
import com.team1.finalproject.memberdata.service.MemberService;
import com.team1.finalproject.sportsdata.dto.TeamInfoResponse;
import com.team1.finalproject.sportsdata.service.SportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PageService {

    private final MemberService memberService;
    private final SportsService sportsService;

    public MainPageResponse getMainPage(MainPageRequest dto){
        TeamInfoResponse teamInfo = sportsService.getTeamInfo(memberService.viewMemberData(dto.getMemberId()).getTeamId());

        //get podcast, teamId = teamInfo.getTeamId;

        //get news, num = 3

        //get trend, num = 5

        //get pos, neg

        return null;
    }

    public NewsPageResponse getNewsPage(NewsPageRequest dto) {
        return null;
    }

    public PodcastPageResponse getPodcastPage(PodcastPageRequest dto) {
        return null;
    }

    public AnalysisPageResponse getAnalysisPage(AnalysisPageRequest dto) {
        return null;
    }

    public MyTeamPageResponse getMyTeamPage(MainPageRequest dto) {
        return null;
    }

    public SearchResponse searchQuery(String query) {
        return null;
    }
}
