package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.dto.LeagueInfoResponse;
import com.team1.finalproject.sportsdata.dto.PlayerInfoResponse;
import com.team1.finalproject.sportsdata.dto.TeamInfoResponse;
import com.team1.finalproject.sportsdata.service.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
@Slf4j
public class SportsDataController {
    private final SportsService sportsService;

    @GetMapping("/sports")
    public List<String> getSportsList() {
        return sportsService.getSportsList();
    }

    @GetMapping("/league/list/{sportsName}")
    public List<LeagueInfoResponse> getLeagueList(@PathVariable("sportsName") String sportsName) {
        return sportsService.getLeagueList(sportsName);
    }

    @GetMapping("/league/{leagueId}")
    public LeagueInfoResponse getLeagueInfo(@PathVariable("leagueId") Long leagueId) {
        return sportsService.getLeagueInfo(leagueId);
    }

    @GetMapping("/team/list/{leagueId}")
    public List<TeamInfoResponse> getTeamList(@PathVariable("leagueId") Long leagueId) {
        return sportsService.getTeamList(leagueId);
    }

    @GetMapping("/team/{teamId}")
    public TeamInfoResponse getTeamInfo(@PathVariable("teamId") Long teamId) {
        return sportsService.getTeamInfo(teamId);
    }

    @GetMapping("/player/list{teamId}")
    public List<PlayerInfoResponse> getPlayerList(@PathVariable("teamId") Long teamId) {
        return sportsService.getPlayerList(teamId);
    }

    @GetMapping("/player/{playerId}")
    public PlayerInfoResponse getPlayerInfo(@PathVariable("playerId") Long playerId) {
        return sportsService.getPlayerInfo(playerId);
    }
}
