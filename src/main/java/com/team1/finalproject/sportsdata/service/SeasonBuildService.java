package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.entity.soccer.Defender;
import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import com.team1.finalproject.sportsdata.entity.soccer.Goalkeeper;
import com.team1.finalproject.sportsdata.entity.soccer.Midfielder;
import com.team1.finalproject.sportsdata.repository.*;
import com.team1.finalproject.sportsdata.repository.soccer.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeasonBuildService {

    private final CategoryRepository categoryRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final SoccerPlayerRepository soccerPlayerRepository;
    private final ForwardRepository forwardRepository;
    private final MidfielderRepository midfielderRepository;
    private final DefenderRepository defenderRepository;
    private final GoalkeeperRepository goalkeeperRepository;
    private final DataParseBuilder dataParseBuilder;
    String code = LocalDate.now().getYear() +"-"+LocalDate.now().getMonth().toString();
    public String setSeason() throws ParseException{
        List<Long> leagueIdList = categoryRepository.findAllLeagueId();
        System.out.println("leagueIdList = " + leagueIdList);
        for(Long leagueId : leagueIdList){
            String url = "https://sofascores.p.rapidapi.com/v1/unique-tournaments/seasons?unique_tournament_id="+ leagueId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) object;
                    Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow();
                    Season season = new Season((Long) temp.get("id"), (String) temp.get("name"), code, category);
                    if (!seasonRepository.existsById(season.getId())&&((String) temp.get("name")).contains("23"))
                        seasonRepository.save(season);
                }
            }
        }
        return "Season data set complete";
    }

    public String setTeam() throws ParseException {
        List<Season> seasons = seasonRepository.findAll();
        for(Season season : seasons){
            Long uniqueId = season.getCategory().getLeagueId();
            System.out.println("season = " + season.getName() + season.getId() + season.getCategory());
            System.out.println("uniqueId = " + uniqueId);
            Long seasonId = season.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/seasons/teams-statistics/result" +
                    "?seasons_statistics_type=overall&unique_tournament_id=" + uniqueId +
                    "&seasons_id=" + seasonId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) ((JSONObject) object).get("team");
                    Team team = new Team((Long) temp.get("id"), (String) temp.get("name"), code);
                    SeasonTeam seasonTeam = new SeasonTeam(season, team);
                    // check if team, seasonTeam exists. -> save in repository
                    if (!teamRepository.existsById(team.getId()))
                        teamRepository.save(team);
                    if (!seasonTeamRepository.existsBySeasonAndTeam(season, team)) {
                        seasonTeamRepository.save(seasonTeam);
                        // set FK relations
                        team.getSeasonTeams().add(seasonTeam);
                        season.getSeasonTeams().add(seasonTeam);
                    }
                }
            }
        }
        return "Team data set complete";
    }

    public String setPlayer() throws ParseException {
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            Long teamId = team.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/teams/players?team_id=" + teamId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) ((JSONObject) object).get("player");
                    if (temp.get("dateOfBirthTimestamp") != null && temp.get("shirtNumber")!=null
                            && temp.get("height")!=null&&temp.get("shirtNumber")!=null) {
                        String nation = (String) ((JSONObject) temp.get("country")).get("name");
                        Timestamp dateOfBirth = dataParseBuilder.toTimeStamp((Long) temp.get("dateOfBirthTimestamp"));
                        String playerName;
                        if (nation.equals("South Korea"))
                            playerName = dataParseBuilder.nameTrimmer((String) temp.get("name"));
                        else
                            playerName = (String) temp.get("name");
                        Player player = new Player((Long) temp.get("id"), playerName, dateOfBirth, dataParseBuilder.calculateAge(dateOfBirth),
                                (Long) temp.get("height"), (Long) temp.get("shirtNumber"), nation, (String) temp.get("position"), team, code);
                        // check if player's name exists
                        if (!playerRepository.existsByName(code, playerName) && !player.containsNull())
                            playerRepository.save(player);
                    }
                }
            }
        }
        return "Player data set complete";
    }

    public String setGameByDate() throws ParseException {
        List<Long> regionIdList = categoryRepository.findAllRegionId();
        String[] dates = {"2023-06-03", "2023-06-03", "2023-06-06","2023-06-07","2023-06-10","2023-06-11","2023-06-24","2023-06-25"};
        List<String> dateList = new ArrayList<>(List.of(dates));
        Long[] uniqueIds = {410L};
        List<Long> uniqueIdList = new ArrayList<>(List.of(uniqueIds));
        for(Long regionId : regionIdList){
            for (String date : dateList) {
                String url = "https://sofasport.p.rapidapi.com/v1/events/schedule/category?date=" + date + "&category_id=" + regionId;
                JSONArray resultArray = dataParseBuilder.getResponse(url);
                if (resultArray != null) {
                    for (Object object : resultArray) {
                        JSONObject temp = (JSONObject) object;
                        Team teamHome = teamRepository.findByName((String) ((JSONObject) temp.get("homeTeam")).get("name"), code);
                        Team teamAway = teamRepository.findByName((String) ((JSONObject) temp.get("awayTeam")).get("name"), code);
                        String isStarted = (String) ((JSONObject) temp.get("status")).get("description");
                        Long roundNum = (Long) ((JSONObject) temp.get("roundInfo")).get("round");
                        Long leagueId = categoryRepository.findByLeagueName((String) ((JSONObject) temp.get("tournament"))
                                        .get("name")).orElseThrow().getLeagueId();
                        Long uniqueId = (Long) ((JSONObject) ((JSONObject) temp.get("tournament")).get("uniqueTournament")).get("id");
                        if (uniqueIdList.contains(uniqueId)) {
                            Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow();
                            Game event = new Game((Long) temp.get("id"), isStarted, roundNum,
                                    dataParseBuilder.toTimeStamp((Long) temp.get("startTimestamp")), category, teamHome, teamAway);
                            // check if player data is already in repository
                            if (!gameRepository.existsById(event.getId()))
                                gameRepository.save(event);
                        }
                    }
                }
            }
        }
        return "Event data set complete";
    }

    public void setRecord() throws ParseException {

        List<Player> playerList = playerRepository.findAll();
        for (Player player : playerList) {
            List<SeasonTeam> seasonTeams = player.getTeam().getSeasonTeams();
            Season season = seasonTeams.stream()
                    .filter(seasonTeam -> seasonTeam.getSeason().getName().contains("2023"))
                    .findFirst().orElseThrow()
                    .getSeason();
            Long playerId = player.getId();
            Long seasonId = season.getId();
            Long uniqueId = season.getCategory().getUniqueId();
            String url = "https://sofascores.p.rapidapi.com/v1/players/statistics/result?seasons_id="+seasonId+"&player_id="+playerId+"&unique_tournament_id="+uniqueId+"&player_stat_type=overall";
            JSONObject data = dataParseBuilder.getJSONObject(url);

            if (data == null) {
                log.info("Player id "+playerId+" has no data : 낮은 출전 횟수 혹은 K League 1 소속이 아님");
            }
            else {
                JSONObject jsonObject = (JSONObject) data.get("statistics");
                String position = player.getPosition();
                playerRepository.delete(player);
                if(Objects.equals(position, "F")){
                    Forward forward = Forward.builder()
                            .name(player.getName())
                            .id(player.getId())
                            .dateOfBirth(player.getDateOfBirth())
                            .age(player.getAge())
                            .height(player.getHeight())
                            .shirtNumber(player.getShirtNumber())
                            .nation(player.getNation())
                            .position(player.getPosition())
                            .team(player.getTeam())
                            .code(player.getCode()).build();
                    forwardRepository.save(forward);
                } else if (Objects.equals(position, "M")) {
                    Midfielder midfielder = Midfielder.builder()
                            .name(player.getName())
                            .id(player.getId())
                            .dateOfBirth(player.getDateOfBirth())
                            .age(player.getAge())
                            .height(player.getHeight())
                            .shirtNumber(player.getShirtNumber())
                            .nation(player.getNation())
                            .position(player.getPosition())
                            .team(player.getTeam())
                            .code(player.getCode()).build();
                    midfielderRepository.save(midfielder);
                } else if (Objects.equals(position, "D")) {
                    Defender defender = Defender.builder()
                            .name(player.getName())
                            .id(player.getId())
                            .dateOfBirth(player.getDateOfBirth())
                            .age(player.getAge())
                            .height(player.getHeight())
                            .shirtNumber(player.getShirtNumber())
                            .nation(player.getNation())
                            .position(player.getPosition())
                            .team(player.getTeam())
                            .code(player.getCode()).build();
                    defenderRepository.save(defender);
                } else if (Objects.equals(position, "G")) {
                    Goalkeeper goalkeeper = Goalkeeper.builder()
                            .name(player.getName())
                            .id(player.getId())
                            .dateOfBirth(player.getDateOfBirth())
                            .age(player.getAge())
                            .height(player.getHeight())
                            .shirtNumber(player.getShirtNumber())
                            .nation(player.getNation())
                            .position(player.getPosition())
                            .team(player.getTeam())
                            .code(player.getCode()).build();
                    goalkeeperRepository.save(goalkeeper);
                }
            }
        }

        String code = dataParseBuilder.availableSeasonCode();
        Team team = teamRepository.save(new Team(1L,"name", code));
        Player player = new Player(2L, "name", dataParseBuilder.toTimeStamp(20000603),
                20, 170L, 10L, "Korea", "FW", team, code);
        playerRepository.delete(playerRepository.findById(2L).orElseThrow());
        Forward forward = Forward.builder()
                .name(player.getName())
                .id(player.getId())
                .dateOfBirth(player.getDateOfBirth())
                .age(player.getAge())
                .height(player.getHeight())
                .shirtNumber(player.getShirtNumber())
                .nation(player.getNation())
                .position(player.getPosition())
                .team(player.getTeam())
                .code(player.getCode())
                .yellowCards(1L)
                .redCards(1L)
                .appearances(1L)
                .goals(1L)
                .assists(1L).build();
        forwardRepository.save(forward);
    }
}
