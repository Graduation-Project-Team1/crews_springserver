package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.dto.SearchResponse;
import com.team1.finalproject.sportsdata.dto.*;

import java.util.List;

public interface SportsService {

    List<String> getSportsList();

    SportsInfoResponse getSportsInfo(Long sportsId);

    List<LeagueInfoResponse> getLeagueList(String sportsName);

    LeagueInfoResponse getLeagueInfo(Long leagueId);

    List<TeamInfoResponse> getTeamList(Long leagueId);

    TeamInfoResponse getTeamInfo(Long teamId);

    List<PlayerInfoResponse> getPlayerList(Long teamId);

    PlayerInfoResponse getPlayerInfo(Long playerId);

    ManagerInfoResponse getManagerInfo(ManagerInfoRequest dto);

    List<GameInfoResponse> getTeamSchedule(Long teamId);

    PlayerInfoResponse getPlayerRecord(Long playerId);
    List<PlayerInfoResponse> getPlayerRecordByTeam(Long teamId);
    List<PlayerInfoResponse> getPlayerRecordByTeamAndPos(Long teamId, String pos);

    String getTeamRecord();

    ManagerInfoResponse getManagerInfo(Long managerId);

    SearchResponse getSearchResult(String query);
}
