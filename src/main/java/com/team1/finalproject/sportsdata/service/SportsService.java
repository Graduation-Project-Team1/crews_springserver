package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.sportsdata.dto.*;

import java.util.List;

public interface SportsService {

    List<String> getSportsList();

    SportsInfoResponse getSportsInfo(SportsInfoRequest dto);

    List<LeagueInfoResponse> getLeagueList(LeagueListRequest dto);

    LeagueInfoResponse getLeagueInfo(LeagueInfoRequest dto);

    List<TeamInfoResponse> getTeamList(TeamListRequest dto);

    TeamInfoResponse getTeamInfo(TeamInfoRequest dto);

    List<PlayerInfoResponse> getPlayerList(PlayerListRequest dto);

    PlayerInfoResponse getPlayerInfo(PlayerInfoRequest dto);

    ManagerInfoResponse getManagerInfo(ManagerInfoRequest dto);

    String getTeamSchedule();

    String getTeamRecord();

    String getPlayerRecord();

    String getManagerRecord();
}
