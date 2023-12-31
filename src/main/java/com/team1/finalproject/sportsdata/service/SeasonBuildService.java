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
    private final ForwardRepository forwardRepository;
    private final MidfielderRepository midfielderRepository;
    private final DefenderRepository defenderRepository;
    private final GoalkeeperRepository goalkeeperRepository;
    private final ManagerRepository managerRepository;
    private final DataParseBuilder dataParseBuilder;
    private final SoccerTeamRepository soccerTeamRepository;
    String code = LocalDate.now().getYear() + "-" + LocalDate.now().getMonth().toString();

    public String setSeason() throws ParseException {
        List<Long> leagueIdList = categoryRepository.findAllLeagueId();
        for (Long leagueId : leagueIdList) {
            String url = "https://sofascores.p.rapidapi.com/v1/unique-tournaments/seasons?unique_tournament_id=" + leagueId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if (resultArray != null) {
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) object;
                    Category category = categoryRepository.findByLeagueId(leagueId).orElseThrow();
                    Season season = new Season((Long) temp.get("id"), (String) temp.get("name"), code, category);
                    if (!seasonRepository.existsById(season.getId()) && ((String) temp.get("name")).contains("23"))
                        seasonRepository.save(season);
                }
            }
        }
        return "Season data set complete";
    }

    public String setTeam() throws ParseException {
        List<Season> seasons = seasonRepository.findAll();
        for (Season season : seasons) {
            Long uniqueId = season.getCategory().getLeagueId();
            Long seasonId = season.getId();
            String url = "https://sofasport.p.rapidapi.com/v1/seasons/teams-statistics/result" +
                    "?seasons_statistics_type=overall&unique_tournament_id=" + uniqueId +
                    "&seasons_id=" + seasonId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if (resultArray != null) {
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
            if (resultArray != null) {
                for (Object object : resultArray) {
                    JSONObject temp = (JSONObject) ((JSONObject) object).get("player");
                    if (temp.get("dateOfBirthTimestamp") != null && temp.get("shirtNumber") != null
                            && temp.get("height") != null && temp.get("shirtNumber") != null) {
                        String nation = (String) ((JSONObject) temp.get("country")).get("name");
                        Timestamp dateOfBirth = dataParseBuilder.toTimeStamp((Long) temp.get("dateOfBirthTimestamp"));
                        String playerName;
                        if (nation.equals("South Korea"))
                            playerName = dataParseBuilder.nameTrimmer((String) temp.get("name"));
                        else
                            playerName = (String) temp.get("name");
                        Player player = new Player((Long) temp.get("id"), playerName, dateOfBirth, dataParseBuilder.calculateAge(dateOfBirth),
                                (Long) temp.get("height"), (Long) temp.get("shirtNumber"), nation, (String) temp.get("position"), team, code, null);
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
        String[] dates = {"2023-12-02", "2023-12-03", "2023-12-06", "2023-12-9"};
        List<String> dateList = new ArrayList<>(List.of(dates));
        Long[] uniqueIds = {410L};
        List<Long> uniqueIdList = new ArrayList<>(List.of(uniqueIds));
        for (Long regionId : regionIdList) {
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
                        Long uniqueId = (Long) ((JSONObject) ((JSONObject) temp.get("tournament")).get("uniqueTournament")).get("id");
                        if (uniqueIdList.contains(uniqueId)) {
                            Category category = categoryRepository.findByLeagueId(uniqueId).orElseThrow();
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

    public void setPlayerRecord() throws ParseException {

        List<Player> playerList = playerRepository.findAll();
        for (Player player : playerList) {
            List<SeasonTeam> seasonTeams = player.getTeam().getSeasonTeams();
            Season season = seasonTeams.stream()
                    .filter(seasonTeam -> seasonTeam.getSeason().getName().contains("2023"))
                    .findFirst().orElseThrow()
                    .getSeason();
            Long playerId = player.getId();
            Long seasonId = season.getId();
            Long leagueId = season.getCategory().getLeagueId();
            String url = "https://sofascores.p.rapidapi.com/v1/players/statistics/result?seasons_id=" + seasonId + "&player_id=" + playerId + "&unique_tournament_id=" + leagueId + "&player_stat_type=overall";
            JSONObject data = dataParseBuilder.getJSONObject(url);

            if (data == null) {
                log.info("Player id " + playerId + " has no data : 낮은 출전 횟수 혹은 K League 1 소속이 아님");
            } else {
                JSONObject jsonObject = (JSONObject) data.get("statistics");
                String position = player.getPosition();
                if (Objects.equals(position, "F")) {
                    playerRepository.delete(player);
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
                            .yellowCards((Long) jsonObject.get("yellowCards"))
                            .redCards((Long) jsonObject.get("redCards"))
                            .appearances((Long) jsonObject.get("appearances"))
                            .goals((Long) jsonObject.get("goals"))
                            .assists((Long) jsonObject.get("assists"))
                            .totalShots((Long) jsonObject.get("totalShots"))
                            .shotsOnTarget((Long) jsonObject.get("shotsOnTarget"))
                            .penaltyGoals((Long) jsonObject.get("penaltyGoals"))
                            .touches((Long) jsonObject.get("touches"))
                            .successfulDribbles((Long) jsonObject.get("successfulDribbles")).build();
                    forwardRepository.save(forward);
                } else if (Objects.equals(position, "M")) {
                    playerRepository.delete(player);
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
                            .code(player.getCode())
                            .yellowCards((Long) jsonObject.get("yellowCards"))
                            .redCards((Long) jsonObject.get("redCards"))
                            .appearances((Long) jsonObject.get("appearances"))
                            .goals((Long) jsonObject.get("goals"))
                            .assists((Long) jsonObject.get("assists"))
                            .totalPasses((Long) jsonObject.get("totalPasses"))
                            .accuratePasses((Long) jsonObject.get("accuratePasses"))
                            .accurateCrosses((Long) jsonObject.get("accurateCrosses"))
                            .interceptions((Long) jsonObject.get("interceptions"))
                            .touches((Long) jsonObject.get("touches")).build();
                    midfielderRepository.save(midfielder);
                } else if (Objects.equals(position, "D")) {
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
                            .tackles((Long) jsonObject.get("tackles"))
                            .interceptions((Long) jsonObject.get("interceptions"))
                            .clearances((Long) jsonObject.get("clearances"))
                            .blockedShots((Long) jsonObject.get("blockedShots"))
                            .totalContest((Long) jsonObject.get("totalContest"))
                            .totalDuelsWon((Long) jsonObject.get("totalDuelsWon"))
                            .aerialDuelsWon((Long) jsonObject.get("aerialDuelsWon")).build();
                    defenderRepository.save(defender);
                } else if (Objects.equals(position, "G")) {
                    playerRepository.delete(player);
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
                            .code(player.getCode())
                            .yellowCards((Long) jsonObject.get("yellowCards"))
                            .redCards((Long) jsonObject.get("redCards"))
                            .appearances((Long) jsonObject.get("appearances"))
                            .saves((Long) jsonObject.get("saves"))
                            .goalsConceded((Long) jsonObject.get("goalsConceded"))
                            .cleanSheet((Long) jsonObject.get("cleanSheet")).build();
                    goalkeeperRepository.save(goalkeeper);
                }
            }
        }
    }

    public void setManager() throws ParseException {
        List<Team> teamList = teamRepository.findAll();
        for (Team team : teamList) {
            Long id = team.getId();

            String url = "https://sofascores.p.rapidapi.com/v1/teams/data?team_id=" + id;
            JSONObject jsonObject = dataParseBuilder.getJSONObject(url);
            Long managerId = (Long) ((JSONObject) jsonObject.get("manager")).get("id");

            String url_manager = "https://sofascores.p.rapidapi.com/v1/managers/data?manager_id=" + managerId;
            JSONObject jsonObject1 = dataParseBuilder.getJSONObject(url_manager);

            String name = (String) jsonObject1.get("name");
            Timestamp dateOfBirth = null;
            if (jsonObject1.get("dateOfBirthTimestamp") != null)
                dateOfBirth = dataParseBuilder.toTimeStamp((Long) jsonObject1.get("dateOfBirthTimestamp"));
            String nationality = (String) jsonObject1.get("nationality");
            Manager manager = new Manager(managerId, name, dateOfBirth, nationality, team);

            managerRepository.save(manager);
            System.out.println("Team: " + team.getName() + " -> Manager: " + manager.getName());
        }
    }

    public void setTeamRecordForSeason() throws ParseException {
        List<Season> seasonList = seasonRepository.findAll();
        for (Season season : seasonList) {
            Long leagueId = season.getCategory().getLeagueId();
            Long seasonId = season.getId();
            String url = "https://sofascores.p.rapidapi.com/v1/seasons/standings" +
                    "?seasons_id=" + seasonId + "&standing_type=total&unique_tournament_id=" + leagueId;
            JSONArray response = dataParseBuilder.getResponse(url);
            /*
                response.get : 0과 1의 경우를 모두 수행하도록 수정 필요
            * */
            JSONObject jsonObject = (JSONObject) response.get(1);
            JSONArray rows = (JSONArray) jsonObject.get("rows");
            for (Object row : rows) {
                JSONObject rowObject = (JSONObject) row;
                Long teamId = (Long) ((JSONObject) rowObject.get("team")).get("id");
                Long matches = (Long) rowObject.get("matches");
                Long wins = (Long) rowObject.get("wins");
                Long losses = (Long) rowObject.get("losses");
                Long draws = (Long) rowObject.get("draws");
                Long points = (Long) rowObject.get("points");
                String url1 = "https://sofascores.p.rapidapi.com/v1/teams/statistics/result?" +
                        "season_id=" + seasonId + "&unique_tournament_id=" + leagueId + "&team_id=" + teamId;
                JSONObject object = dataParseBuilder.getJSONObject(url1);
                Long goalsScored = (Long) object.get("goalsScored");
                Long goalsConceded = (Long) object.get("goalsConceded");
                Long assists = (Long) object.get("assists");
                Long fouls = (Long) object.get("fouls");
                StringBuilder recentForm = new StringBuilder();
                String url2 = "https://sofascores.p.rapidapi.com/v1/teams/recent-form?team_id=" + teamId;
                JSONObject jsonObject1 = dataParseBuilder.getJSONObject(url2);
                JSONArray events = (JSONArray) jsonObject1.get("events");
                for (Object o : events) {
                    JSONObject object1 = (JSONObject) o;
                    Long winnerCode = (Long) object1.get("winnerCode");
                    // 홈팀 승리일 때
                    if (winnerCode == 1) {
                        Long homeTeamId = (Long) ((JSONObject) object1.get("homeTeam")).get("id");
                        // 대상 팀이 홈팀이면
                        if (homeTeamId.equals(teamId))
                            recentForm.append("승");
                        else
                            recentForm.append("패");
                    }
                    // 어웨이팀 승리일 때
                    else if (winnerCode == 2) {
                        Long awayTeamId = (Long) ((JSONObject) object1.get("awayTeam")).get("id");
                        // 대상 팀이 어웨이팀이면
                        if (awayTeamId.equals(teamId))
                            recentForm.append("승");
                        else
                            recentForm.append("패");
                    }
                    // 무승부
                    else if (winnerCode == 3) {
                        recentForm.append("무");
                    }
                }
                Team team = teamRepository.findById(teamId).orElseThrow();
                SoccerTeam soccerTeam = new SoccerTeam(team, recentForm.toString(), matches, wins, losses, draws, points, goalsScored, goalsConceded, assists, fouls);
                if(!soccerTeamRepository.existsByTeam(team))
                    soccerTeamRepository.save(soccerTeam);
            }
        }
    }

    public void setPlayerPhoto() {
        List<Team> teamList = teamRepository.findAll();
        for (Team team : teamList) {
            Long teamId = team.getId();
            if(teamId==6908L||teamId==7652L){
                List<Player> playerList = playerRepository.findAllByTeamId(teamId);
                for (Player player : playerList) {
                    Long playerId = player.getId();
                    String url = "https://sofascores.p.rapidapi.com/v1/players/photo?player_id="+ playerId;
                    String s = dataParseBuilder.getImage(url, playerId);
                    System.out.println("playerId = " + playerId + s);
                }
            }
        }
    }

    public void initializePhoto() {
        List<Team> teamList = teamRepository.findAll();
        for (Team team : teamList) {
            Long teamId = team.getId();
            team.setLogo("https://media.crews.jongmin.xyz/"+ teamId +".png");
            List<Player> playerList = playerRepository.findAllByTeamId(teamId);
            for (Player player : playerList) {
                Long playerId = player.getId();
                player.setPhoto("https://media.crews.jongmin.xyz/players/"+playerId+".png");
            }
            playerRepository.saveAll(playerList);
        }
        teamRepository.saveAll(teamList);
    }
}
