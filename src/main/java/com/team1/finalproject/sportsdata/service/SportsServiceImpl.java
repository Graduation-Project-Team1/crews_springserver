package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.exception.ErrorCode;
import com.team1.finalproject.common.exception.GlobalException;
import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.sportsdata.dto.*;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SportsServiceImpl implements SportsService {

    private final CategoryRepository categoryRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ManagerRepository managerRepository;
    private final DataParseBuilder dataParseBuilder;

    @Override
    public List<String> getSportsList() {
        return categoryRepository.findAllSportsName();
    }

    @Override
    public SportsInfoResponse getSportsInfo(Long sportsId) {
        String sportsName = categoryRepository.findSportsNameBySportsId(sportsId);
        return new SportsInfoResponse(sportsId, sportsName);
    }

    @Override
    public List<LeagueInfoResponse> getLeagueList(String sportsName) {
        List<Category> categories = categoryRepository.findBySportsName(sportsName);
        List<LeagueInfoResponse> leagueInfoResponses = categories.stream()
                .map(LeagueInfoResponse::new)
                .collect(Collectors.toList());
        return leagueInfoResponses;
    }

    @Override
    public LeagueInfoResponse getLeagueInfo(Long leagueId) {
        Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        return new LeagueInfoResponse(category);
    }

    @Override
    public List<TeamInfoResponse> getTeamList(Long leagueId) {
        List<TeamInfoResponse> teamInfoResponses = new java.util.ArrayList<>(List.of());
        Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        Season season = seasonRepository.findByCategory(category).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        List<SeasonTeam> seasonTeams = season.getSeasonTeams();
        for (SeasonTeam seasonTeam : seasonTeams) {
            Team team = seasonTeam.getTeam();
            teamInfoResponses.add(new TeamInfoResponse(team));
        }
        return teamInfoResponses;
    }

    @Override
    public TeamInfoResponse getTeamInfo(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        return new TeamInfoResponse(team);
    }

    @Override
    public List<PlayerInfoResponse> getPlayerList(Long teamId) {
        List<Player> players = playerRepository.findAllByTeamId(teamId);
        return players.stream()
                .map(PlayerInfoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerInfoResponse getPlayerInfo(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        return new PlayerInfoResponse(player);
    }

    @Override
    public ManagerInfoResponse getManagerInfo(ManagerInfoRequest dto) {
        Long managerId = dto.getManagerId();
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        return new ManagerInfoResponse(manager);
    }

    @Override
    public String getTeamSchedule() {
        return null;
    }

    @Override
    public String getTeamRecord() {
        return null;
    }

    @Override
    public String getPlayerRecord() {
        return null;
    }

    @Override
    public String getManagerRecord() {
        return null;
    }
}
