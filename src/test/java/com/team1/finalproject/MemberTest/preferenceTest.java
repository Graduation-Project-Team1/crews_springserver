package com.team1.finalproject.MemberTest;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.memberdata.dto.SetUserPreferencesRequestDto;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.service.MemberService;
import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.CategoryRepository;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class preferenceTest {
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
    DataParseBuilder dataParseBuilder = new DataParseBuilder(null);

    @Test
    public void findAllRegionIdTest(){
        categoryRepository.save(new Category(1L, "",2L, "", 3L, 4L, ""));
        categoryRepository.save(new Category(1L, "",2L, "", 3L, 4L, ""));
        categoryRepository.save(new Category(1L, "",3L, "", 3L, 4L, ""));
        List<Long> allRegionId = categoryRepository.findAllRegionId();
        for (Long aLong : allRegionId) {
            System.out.println("aLong = " + aLong);
        }
        return ;
    }
    @Test
    public void setPreferencesTest() {
        Member member = new Member("asdf");
        Team team = new Team(1L,"name");
        Player player = new Player(2L, "name", dataParseBuilder.toTimeStamp(200006), 20, 170, 10, "Korea", "FW", team);
        SetUserPreferencesRequestDto dto = new SetUserPreferencesRequestDto("nickname", team, player);
        memberService.setUserPreferences(member, dto);

        assertThat(member.getPreferences().getNickname()).isEqualTo("nickname");
        assertThat(member.getPreferences().getTeam()).isEqualTo(team);
        assertThat(member.getPreferences().getPlayer()).isEqualTo(player);

    }

    @Test
    public void duplicateNicknameTest() {
        Member member1 = new Member("asdf");
        member1 = memberRepository.save(member1);
        Team team = new Team(1L,"name");
        team = teamRepository.save(team);
        Player player = new Player(2L, "name", dataParseBuilder.toTimeStamp(200006), 20, 170, 10, "Korea", "FW", team);
        player = playerRepository.save(player);
        SetUserPreferencesRequestDto dto = new SetUserPreferencesRequestDto("nickname", team, player);
        memberService.setUserPreferences(member1, dto);

        assertThat(memberService.chkduplicateNickname(dto)).isEqualTo(true);
    }
}
