package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.dto.*;
import com.team1.finalproject.sportsdata.service.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
@Slf4j
public class SportsDataController {
    private final SportsService sportsService;

    @GetMapping("/sports")
    public List<String> getSportsList(){
        List<String> sportsList = sportsService.getSportsList();
        return sportsList;
    }

    @GetMapping("/league")
    public LeagueInfoResponse getLeagueInfo(@ModelAttribute("dto") LeagueInfoRequest dto) {
        LeagueInfoResponse leagueInfo = sportsService.getLeagueInfo(dto);
        return leagueInfo;
    }
    @GetMapping("/league/list")
    public List<LeagueInfoResponse> getLeagueList(@ModelAttribute("dto") LeagueListRequest dto){
        List<LeagueInfoResponse> leagueList = sportsService.getLeagueList(dto);
        return leagueList;
    }

    @GetMapping("/team")
    public TeamInfoResponse getTeamInfo(@ModelAttribute("dto") TeamInfoRequest dto) {
        TeamInfoResponse teamInfo = sportsService.getTeamInfo(dto);
        return teamInfo;
    }
    @GetMapping("/team/list")
    public List<TeamInfoResponse> getTeamList(@ModelAttribute("dto")TeamListRequest dto){
        List<TeamInfoResponse> teamList = sportsService.getTeamList(dto);
        return teamList;
    }

    @GetMapping("/player")
    public PlayerInfoResponse getPlayerInfo(@ModelAttribute("dto")PlayerInfoRequest dto){
        PlayerInfoResponse playerInfo = sportsService.getPlayerInfo(dto);
        return playerInfo;
    }

    @GetMapping("/player/list")
    public List<PlayerInfoResponse> getPlayerList(@ModelAttribute("dto")PlayerListRequest dto){
        List<PlayerInfoResponse> playerList = sportsService.getPlayerList(dto);
        return playerList;
    }

    @GetMapping("/record")
    public ResponseEntity<String> getPlayerRecordTest(@RequestParam Boolean isGoalkeeper) throws IOException {
        if(isGoalkeeper){
            ClassPathResource resource = new ClassPathResource("GoalKeeperStatistics.json");
            String jsonContent = Files.readString(Path.of(resource.getURI()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonContent);
        }
        else {
            ClassPathResource resource = new ClassPathResource("OutPlayerStatistics.json");
            String jsonContent = Files.readString(Path.of(resource.getURI()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonContent);
        }
    }
}
