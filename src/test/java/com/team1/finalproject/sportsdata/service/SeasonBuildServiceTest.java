package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.entity.Season;
import com.team1.finalproject.sportsdata.entity.SeasonTeam;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.*;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class SeasonBuildServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private SeasonTeamRepository seasonTeamRepository;
    @Autowired
    private SeasonBuildService seasonBuildService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DataParseBuilder dataParseBuilder;

    @Test
    void setSeason() {
    }

    @Test
    void setTeam() throws ParseException {
        Category category = new Category(1L, "Football", 291L, "South Korea", 3284L, 410L, "K League 1");
        category = categoryRepository.save(category);
        Season season = new Season(48379L, "K-League 1 2023", category);
        season = seasonRepository.save(season);
        Long uniqueId = season.getCategory().getUniqueId();
        Long seasonId = season.getId();
        System.out.println("seasonId = " + seasonId);
        System.out.println("uniqueId = " + uniqueId);
        String url = "https://sofasport.p.rapidapi.com/v1/seasons/teams-statistics/result" +
                "?seasons_statistics_type=overall&unique_tournament_id=" + uniqueId +
                "&seasons_id=" + seasonId;
        JSONArray resultArray = dataParseBuilder.getResponse(url);
        System.out.println("resultArray = " + resultArray);
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
        List<Team> all = teamRepository.findAll();
        System.out.println("all = " + all.size());
        Team team = all.get(0);

        /*Long teamId = team.getId();
        String url1 = "https://sofasport.p.rapidapi.com/v1/teams/players?team_id=" + teamId;
        JSONArray resultArray1 = dataParseBuilder.getResponse(url1);
        if(resultArray1!=null){
            for (Object object : resultArray1) {
                JSONObject temp = (JSONObject) ((JSONObject) object).get("player");
                if (temp.get("dateOfBirthTimestamp") != null && temp.get("shirtNumber")!=null && temp.get("height")!=null&&temp.get("shirtNumber")!=null) {
                    String nation = (String) ((JSONObject) temp.get("country")).get("name");
                    Timestamp dateOfBirth = dataParseBuilder.toTimeStamp((Long) temp.get("dateOfBirthTimestamp"));
                    String playerName;
                    if (nation.equals("South Korea"))
                        playerName = dataParseBuilder.nameTrimmer((String) temp.get("name"));
                    else
                        playerName = (String) temp.get("name");
                    Player player = new Player((Long) temp.get("id"), playerName, dateOfBirth, dataParseBuilder.calculateAge(dateOfBirth),
                            (Long) temp.get("height"), (Long) temp.get("shirtNumber"), nation, (String) temp.get("position"), team);
                    // check if player's name exists
                    if (!playerRepository.existsByName(playerName) && !player.containsNull())
                        playerRepository.save(player);
                }
            }
        }

        System.out.println("playerRepository = " + playerRepository.findAll().size());*/
    }

    @Test
    void setPlayer() {
    }

    @Test
    void setGameByDate() {
    }
}