package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodicBuildService {

    private final CategoryRepository categoryRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final DataParseBuilder dataParseBuilder;

    public String setSeason() throws ParseException {
        List<Long> leagueIdList = categoryRepository.findAllLeagueId();
        for(Long leagueId : leagueIdList){
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments/seasons?tournament_id="+ leagueId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) object;
                    if (categoryRepository.existsByLeagueId(leagueId)) {
                        Season season = new Season((Long) temp.get("id"), (String) temp.get("name"),
                                categoryRepository.findByLeagueId(leagueId));
                        if (!seasonRepository.existsById(season.getId()))
                            seasonRepository.save(season);
                    }
                }
            }
        }
        return "Season data set complete";
    }

    public String setTeam() throws ParseException {
        List<Season> seasons = seasonRepository.findAll();
        for(Season season : seasons){
            Long uniqueId = season.getCategory().getUniqueId();
            Long seasonId = season.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/seasons/standings?standing_type=total&seasons_id=" + seasonId +
                    "&unique_tournament_id=" + uniqueId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) ((JSONObject) object).get("team");
                    Team team = new Team((Long) temp.get("id"), (String) temp.get("name"));
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
                    if (temp.get("dateOfBirthTimestamp") != null) {
                        String nation = (String) ((JSONObject) temp.get("country")).get("name");
                        Timestamp dateOfBirth = dataParseBuilder.toTimeStamp((Long) temp.get("dateOfBirthTimestamp"));
                        String playerName;
                        if (nation != null && nation.equals("South Korea"))
                            playerName = dataParseBuilder.nameTrimmer((String) temp.get("name"));
                        else
                            playerName = (String) temp.get("name");
                        Player player = new Player((Long) temp.get("id"), playerName, dateOfBirth, dataParseBuilder.calculateAge(dateOfBirth),
                                (int) temp.get("height"), (int) temp.get("shirtNumber"), nation, (String) temp.get("position"), team);
                        // check if player's name exists
                        if (!playerRepository.existsByName(playerName) && !player.containsNull())
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
                        Team teamHome = teamRepository.findByName((String) ((JSONObject) temp.get("homeTeam")).get("name"));
                        Team teamAway = teamRepository.findByName((String) ((JSONObject) temp.get("awayTeam")).get("name"));
                        String isStarted = (String) ((JSONObject) temp.get("status")).get("description");
                        Long roundNum = (Long) ((JSONObject) temp.get("roundInfo")).get("round");
                        Long leagueId = categoryRepository.findByLeagueName((String) ((JSONObject) temp.get("tournament")).get("name")).getLeagueId();
                        Long uniqueId = (Long) ((JSONObject) ((JSONObject) temp.get("tournament")).get("uniqueTournament")).get("id");
                        if (uniqueIdList.contains(uniqueId)) {
                            Game event = new Game((Long) temp.get("id"), isStarted, roundNum,
                                    dataParseBuilder.toTimeStamp((Long) temp.get("startTimestamp")),
                                    categoryRepository.findByLeagueId(leagueId), teamHome, teamAway);
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
}
