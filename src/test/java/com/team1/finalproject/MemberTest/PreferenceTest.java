package com.team1.finalproject.MemberTest;

import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.memberdata.dto.*;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.service.MemberService;
import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.CategoryRepository;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PreferenceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DataParseBuilder dataParseBuilder;
    String code = dataParseBuilder.availableSeasonCode();
    Member member;
    Team team;
    Player player;
    SetPreferencesRequest dto;
    @BeforeEach
    public void beforeEach() {
        member = memberRepository.save(new Member("asdf", "1234"));
        team = teamRepository.save(new Team(1L,"name", code));
        player = playerRepository.save(new Player(2L, "name", dataParseBuilder.toTimeStamp(200006),
                20, 170L, 10L, "Korea", "FW", team, code, null));
        dto = new SetPreferencesRequest("sd", 1L, 2L);
        memberService.setMemberPreferences(dto, member.getId());
    }
    @Test
    public void findAllRegionIdTest(){
        categoryRepository.save(new Category(1L, "",2L, "", 3L, 4L, ""));
        categoryRepository.save(new Category(1L, "",2L, "", 3L, 4L, ""));
        categoryRepository.save(new Category(1L, "",3L, "", 3L, 4L, ""));
        List<Long> allRegionId = categoryRepository.findAllRegionId();
        for (Long aLong : allRegionId) {
            System.out.println("aLong = " + aLong);
        }
    }
    @Test
    public void setPreferencesTest() {
        assertThat(member.getPreferences().getTeam().getId()).isEqualTo(1L);
        assertThat(member.getPreferences().getPlayer().getId()).isEqualTo(2L);
    }

    @Test
    public void updatePreferenceTest() {
        String newNickname = "new nickname";
        Team newTeam = teamRepository.save(new Team(2L,"name2", code));
        Player newPlayer = playerRepository.save(new Player(3L, "name2", dataParseBuilder.toTimeStamp(200006),
                22, 175L, 10L, "Korea", "FW", team, code, null));
        UpdatePreferencesRequest dto = new UpdatePreferencesRequest(newNickname, newTeam.getId(), newPlayer.getId());
        memberService.updateMemberPreferences(dto,member.getId());
        assertThat(member.getPreferences().getTeam()).isEqualTo(newTeam);
        assertThat(member.getPreferences().getPlayer()).isEqualTo(newPlayer);
    }

    @Test
    public void duplicateNicknameTest() {
        assertThat(memberService.chkDuplicateNickname(dto)).isEqualTo(true);
    }

    @Test
    public void viewUserDataTest() throws ClassNotFoundException {
        System.out.println("memberRepository = " + memberRepository.findAll().get(0).getId());
        MemberDataResponse memberDataResponseDto = memberService.viewMemberData(member.getId());
        System.out.println("memberDataResponseDto = " + memberDataResponseDto);
    }

    @Test
    public void resignMemberTest() {
        memberService.deleteMember(member.getId());
        assertThat(memberRepository.findById(member.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void updateMemberPasswordTest() {
        UpdatePasswordRequest dto = new UpdatePasswordRequest("1234", "4321");
        memberService.updateMemberPassword(dto, member.getId());

        assertThat(member.getPassword()).isEqualTo("4321");
    }

    /*@Test
    public void memberSignInTest() {
        String msg = memberService.signin(new SigninRequest("eemmaaiill", "paaassword"));
        assertThat(msg).isEqualTo("Sign in complete");
        memberService.setMemberPreferences("eemmaaiill", dto);
        assertThat(memberRepository.findById(2L).orElseThrow().getEmail()).isEqualTo("eemmaaiill");
    }*/

    /*@Test
    public void memberLogInTest() {
        memberService.logIn(new LoginRequest("email", "password"));
    }*/
}
