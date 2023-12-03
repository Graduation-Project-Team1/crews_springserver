package com.team1.finalproject.dataBuild;

import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.soccer.SoccerPlayer;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import com.team1.finalproject.sportsdata.repository.soccer.SoccerPlayerRepository;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    Team team;
    Player player;

    String code;

    @BeforeEach
    public void beforeEach() {
        code = dataParseBuilder.availableSeasonCode();
        team = teamRepository.save(new Team(1L,"name", code));
    }

    @Test
    public void buildSoccerPlayer() {
        player = new Player(2L, "name", dataParseBuilder.toTimeStamp(200006),
                20, 170L, 10L, "Korea", "FW", team, code);
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
}
