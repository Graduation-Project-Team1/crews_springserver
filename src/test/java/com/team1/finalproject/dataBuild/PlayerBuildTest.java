package com.team1.finalproject.dataBuild;

import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.entity.soccer.Defender;
import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import com.team1.finalproject.sportsdata.entity.soccer.SoccerPlayer;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import com.team1.finalproject.sportsdata.repository.soccer.DefenderRepository;
import com.team1.finalproject.sportsdata.repository.soccer.ForwardRepository;
import com.team1.finalproject.sportsdata.repository.soccer.SoccerPlayerRepository;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Transactional
public class PlayerBuildTest {
    @Autowired
    private DataParseBuilder dataParseBuilder;
    @Autowired
    private SoccerPlayerRepository soccerPlayerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ForwardRepository forwardRepository;
    @Autowired
    private DefenderRepository defenderRepository;

    Team team;
    Player player;

    String code;

    @BeforeEach
    public void beforeEach() {
        code = dataParseBuilder.availableSeasonCode();
        team = teamRepository.findById(6908L).orElseThrow();
        player = playerRepository.findById(160843L).orElseThrow();
    }

    @Test
    public void buildSoccerPlayer() {
        player = new Player(2L, "name", dataParseBuilder.toTimeStamp(200006),
                20, 170L, 10L, "Korea", "FW", team, code, null);
        SoccerPlayer soccerPlayer = SoccerPlayer.builder()
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
                .appearances(1L).build();
        soccerPlayerRepository.save(soccerPlayer);
        SoccerPlayer soccerPlayer1 = soccerPlayerRepository.findById(2L).orElseThrow();
        System.out.println("soccerPlayer1 = " + soccerPlayer1);

    }

    @Test
    public void buildForward() throws ParseException {
        List<SeasonTeam> seasonTeams = player.getTeam().getSeasonTeams();
        Season season = seasonTeams.stream()
                .filter(seasonTeam -> seasonTeam.getSeason().getName().contains("2023"))
                .findFirst().orElseThrow()
                .getSeason();
        Long playerId = player.getId();
        System.out.println("playerId = " + playerId);
        Long seasonId = season.getId();
        System.out.println("seasonId = " + seasonId);
        Long leagueId = season.getCategory().getLeagueId();
        System.out.println("leagueId = " + leagueId);
        String url = "https://sofascores.p.rapidapi.com/v1/players/statistics/result?seasons_id="+seasonId+"&player_id="+playerId+"&unique_tournament_id="+leagueId+"&player_stat_type=overall";
        JSONObject data = dataParseBuilder.getJSONObject(url);

        JSONObject jsonObject = (JSONObject) data.get("statistics");
        String position = player.getPosition();
        System.out.println("position = " + position);
        if(Objects.equals(position, "D")) {
            playerRepository.delete(player);
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
                    .code(player.getCode())
                    .yellowCards((Long) jsonObject.get("yellowCards"))
                    .redCards((Long) jsonObject.get("redCards"))
                    .appearances((Long) jsonObject.get("appearances"))
                    .aerialDuelsWon((Long) jsonObject.get("aerialDuelsWon")).build();
            defenderRepository.save(defender);
            System.out.println("jsonObject = " + (Long) jsonObject.get("goals"));
            defenderRepository.save(defender);
        }
        Defender defender = defenderRepository.findById(160843L).orElseThrow();
        String name = defender.getName();
        System.out.println("name = " + name);
    }

    @Test
    public void setManager() throws ParseException {
        Long id = 6908L;
        Team team = teamRepository.findById(id).orElseThrow();
        String url = "https://sofascores.p.rapidapi.com/v1/teams/data?team_id=" + id;
        JSONObject jsonObject = dataParseBuilder.getJSONObject(url);
        Long managerId = (Long) ((JSONObject) jsonObject.get("manager")).get("id");
        System.out.println("managerId = " + managerId);
        String url_manager = "https://sofascores.p.rapidapi.com/v1/managers/data?manager_id=" + managerId;
        JSONObject jsonObject1 = dataParseBuilder.getJSONObject(url_manager);
        String name = (String) jsonObject1.get("name");
        Timestamp dateOfBirth = dataParseBuilder.toTimeStamp((Long) jsonObject1.get("dateOfBirthTimestamp"));
        String nationality = (String) jsonObject1.get("nationality");
        Manager manager = new Manager(managerId, name, dateOfBirth, nationality, team);
        System.out.println(manager.getNationality());
        System.out.println("dateOfBirth = " + dateOfBirth);
    }
}
