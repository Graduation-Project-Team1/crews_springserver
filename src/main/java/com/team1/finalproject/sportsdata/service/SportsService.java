package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.dto.SearchResponse;
import com.team1.finalproject.sportsdata.dto.*;

import java.util.List;

public interface SportsService {

    List<String> getSportsList();

    SportsInfoResponse getSportsInfo(Long sportsId);

    List<LeagueInfoResponse> getLeagueList(String sportsName);

    LeagueInfoResponse getLeagueInfo(Long leagueId) throws ClassNotFoundException;

    List<TeamInfoResponse> getTeamList(Long leagueId) throws ClassNotFoundException;

    TeamInfoResponse getTeamInfo(Long teamId) throws ClassNotFoundException;

    List<PlayerInfoResponse> getPlayerList(Long teamId);

    PlayerInfoResponse getPlayerInfo(Long playerId) throws ClassNotFoundException;

    ManagerInfoResponse getManagerInfo(ManagerInfoRequest dto) throws ClassNotFoundException;

    List<GameInfoResponse> getTeamSchedule(Long teamId) throws ClassNotFoundException;

    PlayerInfoResponse getPlayerRecord(Long playerId) throws ClassNotFoundException;
    List<PlayerInfoResponse> getPlayerRecordByTeam(Long teamId) throws ClassNotFoundException;
    List<PlayerInfoResponse> getPlayerRecordByTeamAndPos(Long teamId, String pos) throws ClassNotFoundException;

    TeamSeasonRecordResponse getTeamSeasonRecord(Long teamId) throws ClassNotFoundException;

    ManagerInfoResponse getManagerInfo(Long managerId) throws ClassNotFoundException;

    SearchResponse getSearchResult(String query);
}
