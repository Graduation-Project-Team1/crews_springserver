package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.dto.SearchResponse;
import com.team1.finalproject.feign.TestFeignClient;
import com.team1.finalproject.feign.dto.QueryResponse;
import com.team1.finalproject.podcast.entity.Podcast;
import com.team1.finalproject.podcast.repository.PodcastRepository;
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
    private final GameRepository gameRepository;
    private final TestFeignClient testFeignClient;
    private final PodcastRepository podcastRepository;
    private final SoccerTeamRepository soccerTeamRepository;

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
        return categories.stream()
                .map(LeagueInfoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public LeagueInfoResponse getLeagueInfo(Long leagueId) throws ClassNotFoundException {
        Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 리그입니다.")
        );
        return new LeagueInfoResponse(category);
    }

    @Override
    public List<TeamInfoResponse> getTeamList(Long leagueId) throws ClassNotFoundException {
        List<TeamInfoResponse> teamInfoResponses = new java.util.ArrayList<>(List.of());
        Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 리그입니다.")
        );
        Season season = seasonRepository.findByCategory(category).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 시즌입니다.")
        );
        List<SeasonTeam> seasonTeams = season.getSeasonTeams();
        for (SeasonTeam seasonTeam : seasonTeams) {
            Team team = seasonTeam.getTeam();
            Manager manager = managerRepository.findByTeam(team).orElseThrow();
            teamInfoResponses.add(new TeamInfoResponse(team, manager));
        }
        return teamInfoResponses;
    }

    @Override
    public TeamInfoResponse getTeamInfo(Long teamId) throws ClassNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 팀입니다.")
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
    public PlayerInfoResponse getPlayerInfo(Long playerId) throws ClassNotFoundException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 선수입니다.")
        );
        return new PlayerInfoResponse(player);
    }

    @Override
    public ManagerInfoResponse getManagerInfo(ManagerInfoRequest dto) throws ClassNotFoundException {
        Long managerId = dto.getManagerId();
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 감독입니다.")
        );
        return new ManagerInfoResponse(manager);
    }

    @Override
    public List<GameInfoResponse> getTeamSchedule(Long teamId) throws ClassNotFoundException {
        List<GameInfoResponse> gameInfoResponses = new ArrayList<>();
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 팀입니다."));

        List<Game> games = gameRepository.findAllByTeam(team);

        for (Game game : games) {
            gameInfoResponses.add(new GameInfoResponse(game));
        }

        return gameInfoResponses;
    }

    @Override
    public PlayerInfoResponse getPlayerRecord(Long playerId) throws ClassNotFoundException {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 선수입니다.")
        );
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
                    Forward forward = forwardRepository.findById(playerId).orElse(null);
                    if(forward==null)
                        return null;
                    return new ForwardRecordResponse(forward);
                }
                case "M" -> {
                    Midfielder midfielder = midfielderRepository.findById(playerId).orElse(null);
                    if(midfielder==null)
                        return null;
                    return new MidfielderRecordResponse(midfielder);
                }
                case "D" -> {
                    Defender defender = defenderRepository.findById(playerId).orElse(null);
                    if(defender==null)
                        return null;
                    return new DefenderRecordResponse(defender);
                }
                case "G" -> {
                    Goalkeeper goalkeeper = goalkeeperRepository.findById(playerId).orElse(null);
                    if(goalkeeper==null)
                        return null;
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
    public List<PlayerInfoResponse> getPlayerRecordByTeam(Long teamId) throws ClassNotFoundException {
        List<PlayerInfoResponse> playerRecordList = new ArrayList<>();
        List<PlayerInfoResponse> playerList = getPlayerList(teamId);
        for (PlayerInfoResponse playerInfoResponse : playerList) {
            Long id = playerInfoResponse.getId();
            PlayerInfoResponse playerRecord = getPlayerRecord(id);
            if(playerRecord!=null)
                playerRecordList.add(playerRecord);
        }
        return playerRecordList;
    }

    @Override
    public List<PlayerInfoResponse> getPlayerRecordByTeamAndPos(Long teamId, String pos) throws ClassNotFoundException {
        List<PlayerInfoResponse> playerRecordList = new ArrayList<>();
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 팀입니다.")
        );
        List<Player> playerList = playerRepository.findByTeamAndPosition(team, pos);
        for (Player player : playerList) {
            Long id = player.getId();
            PlayerInfoResponse playerRecord = getPlayerRecord(id);
            if(playerRecord!=null)
                playerRecordList.add(playerRecord);
        }
        return playerRecordList;
    }

    @Override
    public TeamSeasonRecordResponse getTeamSeasonRecord(Long teamId) throws ClassNotFoundException {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 팀입니다.")
        );
        SoccerTeam soccerTeam = soccerTeamRepository.findByTeam(team).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 축구 구단입니다. 혹은 축구 구단으로 등록되지 않은 팀입니다.")
        );
        return new TeamSeasonRecordResponse(soccerTeam);
    }

    @Override
    public ManagerInfoResponse getManagerInfo(Long managerId) throws ClassNotFoundException {
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new ClassNotFoundException("존재하지 않는 감독입니다.")
        );
        return new ManagerInfoResponse(manager);
    }

    @Override
    public SearchResponse getSearchResult(String query) {
        SearchResponse searchResponse = new SearchResponse();
        QueryResponse response = testFeignClient.getENGLetter(query);
        System.out.println("q = " + response);
        String q;
        if (response.getName() == null)
            q = response.getNames().get(0);
        else
            q = response.getName();

        List<Team> teams = teamRepository.searchAllByNameContainingIgnoreCase(q.trim());
        System.out.println("teams = " + teams);
        if (teams.size() != 0) {
            for (Team team : teams) {
                searchResponse.addTeam(team);
                searchResponse.addNews(testFeignClient.getNews(team.getId()).getNews());
                searchResponse.addCommunity(testFeignClient.getCommunity(team.getId()).getCommunity());
            }
        }

        List<Player> playerList = playerRepository.searchTop5ByNameContainingIgnoreCase(q);
        for (Player player : playerList) {
            searchResponse.addPlayer(player);
        }

        List<Podcast> podcastList = podcastRepository.searchTop5ByTitleContainingAndTextContainingOrderByMadeAtDesc(query, query);
        for (Podcast podcast : podcastList) {
            searchResponse.addPodcast(podcast);
        }

        return searchResponse;
    }

}
