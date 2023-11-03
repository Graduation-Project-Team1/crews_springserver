package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.service.MemberService;
import com.team1.finalproject.sportsdata.dto.*;
import com.team1.finalproject.sportsdata.entity.*;
import com.team1.finalproject.sportsdata.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SportsServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private SeasonTeamRepository seasonTeamRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private DataParseBuilder dataParseBuilder;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SportsService sportsService;
    @Autowired
    private DataMemoryRepository dataMemoryRepository;
    Category category;
    Category category1;
    Category category2;
    Season season;
    Team team;
    Team team2;
    SeasonTeam seasonTeam;
    Player player;
    Manager manager;

    @BeforeEach
    public void beforeEach() {
        category = categoryRepository.save(new Category(1L, "Football",
                1L, "England", 1L, 1L, "EPL"));
        category1 = categoryRepository.save(new Category(1L, "Football",
                2L, "Spain", 2L, 2L, "LaLiga"));
        category2 = categoryRepository.save(new Category(3L, "Baseball",
                3L, "Korea", 3L, 3L, "KBO"));
        season = seasonRepository.save(new Season(1L, "23-24", category));
        team = teamRepository.save(new Team(1L,"team1"));
        team2 = teamRepository.save(new Team(2L,"team2"));
        seasonTeam = seasonTeamRepository.save(new SeasonTeam(season, team));
        season.getSeasonTeams().add(seasonTeam);
        seasonTeam = seasonTeamRepository.save(new SeasonTeam(season, team2));
        season.getSeasonTeams().add(seasonTeam);
        season = seasonRepository.save(season);
        player = playerRepository.save(new Player(1L, "player1", dataParseBuilder.toTimeStamp(200006),
                1,175L, 1L, "England", "GK", team));
        manager = managerRepository.save(new Manager(1L, "manager1", 40,
                dataParseBuilder.toTimeStamp(200006),"Korea", team));
    }

    @Test
    void getSportsList() {
        List<String> sportsList = sportsService.getSportsList();
        assertThat(sportsList.get(1)).isEqualTo("Football");
        assertThat(sportsList.get(0)).isEqualTo("Baseball");
    }

    @Test
    void getSportsInfo() {
        SportsInfoResponse sportsInfo = sportsService.getSportsInfo(new SportsInfoRequest(1L));
        assertThat(sportsInfo.getSportsName()).isEqualTo("Football");
    }

    @Test
    void getLeagueList() {
        List<LeagueInfoResponse> leagueList = sportsService.getLeagueList(new LeagueListRequest("Football"));
        assertThat(leagueList.get(0).getLeagueName()).isEqualTo("EPL");
        assertThat(leagueList.get(1).getLeagueName()).isEqualTo("LaLiga");
    }

    @Test
    void getLeagueInfo() {
        LeagueInfoResponse leagueInfo = sportsService.getLeagueInfo(new LeagueInfoRequest(1L));
        assertThat(leagueInfo.getLeagueName()).isEqualTo("EPL");
        assertThat(leagueInfo.getRegionName()).isEqualTo("England");
    }

    @Test
    void getTeamList() {
        List<TeamInfoResponse> teamList = sportsService.getTeamList(new TeamListRequest(1L));
        assertThat(teamList.get(0)).isEqualTo(new TeamInfoResponse(1L, "team1"));
        assertThat(teamList.get(1)).isEqualTo(new TeamInfoResponse(2L, "team2"));
    }

    @Test
    void getTeamInfo() {
        TeamInfoResponse teamInfo1 = sportsService.getTeamInfo(new TeamInfoRequest(1L));
        assertThat(teamInfo1.getTeamName()).isEqualTo("team1");
        TeamInfoResponse teamInfo2 = sportsService.getTeamInfo(new TeamInfoRequest(2L));
        assertThat(teamInfo2.getTeamName()).isEqualTo("team2");
    }

    @Test
    void getPlayerList() {
        List<PlayerInfoResponse> playerList = sportsService.getPlayerList(new PlayerListRequest(1L));
        assertThat(playerList.get(0)).isEqualTo(new PlayerInfoResponse(player));
    }

    @Test
    void getPlayerInfo() {
        PlayerInfoResponse playerInfo = sportsService.getPlayerInfo(new PlayerInfoRequest(1L));
        assertThat(playerInfo).isEqualTo(new PlayerInfoResponse(player));
        System.out.println(playerInfo.getName());
    }

    @Test
    void getManagerInfo() {
        ManagerInfoResponse managerInfo = sportsService.getManagerInfo(new ManagerInfoRequest(1L));
        assertThat(managerInfo).isEqualTo(new ManagerInfoResponse(manager));
    }
    @Test
    void 레포지토리점검() {
        boolean b = dataMemoryRepository.containsRegion(1L);
        System.out.println("b = " + b);
    }
}