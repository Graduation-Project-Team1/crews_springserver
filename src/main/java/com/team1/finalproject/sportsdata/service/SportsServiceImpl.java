package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.exception.ErrorCode;
import com.team1.finalproject.common.exception.GlobalException;
import com.team1.finalproject.sportsdata.dto.*;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.entity.soccer.Defender;
import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import com.team1.finalproject.sportsdata.entity.soccer.Goalkeeper;
import com.team1.finalproject.sportsdata.entity.soccer.Midfielder;
import com.team1.finalproject.sportsdata.repository.*;
import com.team1.finalproject.sportsdata.repository.soccer.DefenderRepository;
import com.team1.finalproject.sportsdata.repository.soccer.ForwardRepository;
import com.team1.finalproject.sportsdata.repository.soccer.GoalkeeperRepository;
import com.team1.finalproject.sportsdata.repository.soccer.MidfielderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SportsServiceImpl implements SportsService {

    private final CategoryRepository categoryRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ForwardRepository forwardRepository;
    private final MidfielderRepository midfielderRepository;
    private final DefenderRepository defenderRepository;
    private final GoalkeeperRepository goalkeeperRepository;
    private final ManagerRepository managerRepository;

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

        Manager manager = managerRepository.findByTeam(team).orElseThrow();
        return new TeamInfoResponse(team, manager);
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
    public PlayerInfoResponse getPlayerRecord(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        Long sportsId = player.getTeam().getSeasonTeams()
                .stream()
                .filter(seasonTeam -> seasonTeam.getSeason().getName().contains("2023"))
                .findFirst().orElseThrow()
                .getSeason()
                .getCategory()
                .getSportsId();
        String position = player.getPosition();
        // in case sports = football
        if (sportsId == 1L) {
            switch (position) {
                case "F" -> {
                    Forward forward = forwardRepository.findById(playerId).orElseThrow();
                    return new ForwardRecordResponse(forward);
                }
                case "M" -> {
                    Midfielder midfielder = midfielderRepository.findById(playerId).orElseThrow();
                    return new MidfielderRecordResponse(midfielder);
                }
                case "D" -> {
                    Defender defender = defenderRepository.findById(playerId).orElseThrow();
                    return new DefenderRecordResponse(defender);
                }
                case "G" -> {
                    Goalkeeper goalkeeper = goalkeeperRepository.findById(playerId).orElseThrow();
                    return new GoalkeeperRecordResponse(goalkeeper);
                }
                default -> {
                    log.warn("Parameter position is not available.");
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public List<PlayerInfoResponse> getPlayerRecordByTeam(Long teamId) {
        List<PlayerInfoResponse> playerRecordList = new ArrayList<>();
        List<PlayerInfoResponse> playerList = getPlayerList(teamId);
        for (PlayerInfoResponse playerInfoResponse : playerList) {
            Long id = playerInfoResponse.getId();
            PlayerInfoResponse playerRecord = getPlayerRecord(id);
            playerRecordList.add(playerRecord);
        }
        return playerRecordList;
    }

    @Override
    public String getTeamRecord() {
        return null;
    }

    @Override
    public ManagerInfoResponse getManagerInfo(Long managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow();
        return new ManagerInfoResponse(manager);
    }

}
