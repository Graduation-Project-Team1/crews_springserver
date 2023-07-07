package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.common.repository.NationRepository;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodicBuildService {

    private final CategoryRepository categoryRepository;
    private final TournamentRepository tournamentRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final SeasonTeamRepository seasonTeamRepository;
    private final GameRepository gameRepository;
    private final GameTeamRepository gameTeamRepository;
    private final PlayerRepository playerRepository;
    private final DataParseBuilder dataParseBuilder;
    private final NationRepository nationRepository;

    public String setSeason() throws ParseException {
        List<Tournament> tournaments = tournamentRepository.findAll();
        for(Tournament tournament : tournaments){
            Long tournamentId = tournament.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments/seasons?tournament_id="+tournamentId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    if (tournamentRepository.findById(tournamentId).isPresent()) {
                        Season season = new Season((Long) temp.get("id"), (String) temp.get("name"),
                                tournamentRepository.findById(tournamentId).get());
                        if (!seasonRepository.existsById(season.getId()))
                            seasonRepository.save(season);
                    }
                    count++;
                }
            }
        }
        return "Season data set complete";
    }

    public String setTeam() throws ParseException {
        List<Season> seasons = seasonRepository.findAll();
        for(Season season : seasons){
            Long uniqueId = season.getTournament().getUniqueId();
            Long seasonId = season.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/seasons/standings?standing_type=total&seasons_id=" + seasonId +
                    "&unique_tournament_id=" + uniqueId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) ((JSONObject) resultArray.get(count)).get("team");
                    Team team = new Team((Long) temp.get("id"), (String) temp.get("name"));
                    SeasonTeam seasonTeam = new SeasonTeam(season, team);
                    // check if team, seasonTeam exists. -> save in repository
                    if (!teamRepository.existsById(team.getId()))
                        teamRepository.save(team);
                    if (!seasonTeamRepository.existsBySeasonAndTeam(season, team)){
                        seasonTeamRepository.save(seasonTeam);
                        // set FK relations
                        team.getSeasonTeams().add(seasonTeam);
                        season.getSeasonTeams().add(seasonTeam);
                    }
                    count++;
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
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) ((JSONObject) resultArray.get(count)).get("player");
                    if(temp.get("dateOfBirthTimestamp")!=null){
                        String nation = (String) ((JSONObject)temp.get("country")).get("name");
                        String playerName;
                        if(nation!=null&&nation.equals("South Korea"))
                            playerName = dataParseBuilder.nameTrimmer((String) temp.get("name"));
                        else
                            playerName = (String) temp.get("name");
                        Player player = new Player((Long)temp.get("id"), playerName, dataParseBuilder.toTimeStamp((Long) temp.get("dateOfBirthTimestamp")),
                                (Long) temp.get("height"), (Long)temp.get("shirtNumber"), nationRepository.findByName(nation), (String) temp.get("position"), team);
                        // check if player's name exists
                        if(!playerRepository.existsByName(playerName)&& !player.containsNull())
                            playerRepository.save(player);
                    }
                    count++;
                }
            }
        }
        return "Player data set complete";
    }

    public String setEventByDate() {
        List<Category> categories = categoryRepository.findAll();
        String[] days = new String[]{"2023-06-03", "2023-06-03", "2023-06-06","2023-06-07","2023-06-10","2023-06-11","2023-06-24","2023-06-25"};
        List<String> dates = new ArrayList<>(List.of(days));
        categories.forEach(category -> dates.forEach(date -> {
            Long categoryId = category.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/events/schedule/category?date=" + date + "&category_id=" + categoryId;
            JSONArray resultArray = null;
            try {
                resultArray = dataParseBuilder.getResponse(url);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (resultArray != null) {
                int count = 0;
                while (resultArray.size() > count) {
                    JSONObject temp = (JSONObject) resultArray.get(count);
                    Team teamHome = teamRepository.findByName((String) ((JSONObject) temp.get("homeTeam")).get("name"));
                    Team teamAway = teamRepository.findByName((String) ((JSONObject) temp.get("awayTeam")).get("name"));
                    String status = (String) ((JSONObject) temp.get("status")).get("description");
                    Long roundInfo = (Long) ((JSONObject) temp.get("roundInfo")).get("round");
                    Tournament tournament = tournamentRepository.findByName((String) ((JSONObject) temp.get("tournament")).get("name"));
                    Long unique = (Long) ((JSONObject) ((JSONObject) temp.get("tournament")).get("uniqueTournament")).get("id");
                    if (unique == 410L) {
                        Game event = new Game((Long) temp.get("id"), status, roundInfo, dataParseBuilder.toTimeStamp((Long) temp.get("startTimestamp")), tournament);
                        GameTeam gameTeam = new GameTeam(event, teamHome, teamAway);
                        // check if player data is already in repository
                        if (!gameRepository.existsById(event.getId()))
                            gameRepository.save(event);
                        if (!gameTeamRepository.existsByEvent(event)) {
                            gameTeamRepository.save(gameTeam);
                            teamHome.getGameTeamsHome().add(gameTeam);
                            teamAway.getGameTeamsAway().add(gameTeam);
                        }
                    }
                    count++;
                }
            }
        }));
        return "Event data set complete";
    }
}
