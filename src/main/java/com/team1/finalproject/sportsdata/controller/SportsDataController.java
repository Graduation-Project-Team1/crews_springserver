package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.dto.LeagueInfoResponse;
import com.team1.finalproject.sportsdata.dto.ManagerInfoResponse;
import com.team1.finalproject.sportsdata.dto.PlayerInfoResponse;
import com.team1.finalproject.sportsdata.dto.TeamInfoResponse;
import com.team1.finalproject.sportsdata.service.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/league/list")
    public List<LeagueInfoResponse> getLeagueList(@RequestParam String sportsName) {
        return sportsService.getLeagueList(sportsName);
    }

    @GetMapping("/league")
    public LeagueInfoResponse getLeagueInfo(@RequestParam Long leagueId) {
        return sportsService.getLeagueInfo(leagueId);
    }

    @GetMapping("/team/list")
    public List<TeamInfoResponse> getTeamList(@RequestParam Long leagueId) {
        return sportsService.getTeamList(leagueId);
    }

    @GetMapping("/team")
    public TeamInfoResponse getTeamInfo(@RequestParam Long teamId) {
        return sportsService.getTeamInfo(teamId);
    }

    @GetMapping("/player/list")
    public List<PlayerInfoResponse> getPlayerList(@RequestParam Long teamId) {
        return sportsService.getPlayerList(teamId);
    }

    @GetMapping("/player")
    public PlayerInfoResponse getPlayerInfo(@RequestParam Long playerId) {
        return sportsService.getPlayerInfo(playerId);
    }

    @GetMapping("/manager")
    public ManagerInfoResponse getManagerInfo(@RequestParam Long managerId) {
        return sportsService.getManagerInfo(managerId);
    }

    @GetMapping("/record")
    public PlayerInfoResponse getPlayerRecord(@RequestParam Long playerId) {
        return sportsService.getPlayerRecord(playerId);
    }
}
