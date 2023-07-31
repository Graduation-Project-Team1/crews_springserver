package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.memberdata.dto.MemberDataResponse;
import com.team1.finalproject.memberdata.dto.SetPreferencesRequest;
import com.team1.finalproject.memberdata.dto.UpdatePreferencesRequest;
import com.team1.finalproject.memberdata.entity.Member;
import com.team1.finalproject.memberdata.entity.Preferences;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.repository.PreferencesRepository;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.PlayerRepository;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PreferencesRepository preferencesRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    @Override
    public String signin() {
        return null;
    }

    @Override
    public String kakaoLogin() {
        return null;
    }

    @Override
    public String appleLogin() {
        return null;
    }

    @Override
    public String googleLogin() {
        return null;
    }

    @Override
    public Boolean chkduplicateNickname(SetPreferencesRequest dto) {
        return preferencesRepository.existsByNickname(dto.getNickname());
    }

    @Override
    public String chkMemberToken(Token token) {
        return null;
    }

    @Override
    public void setMemberPreferences(Member member, SetPreferencesRequest dto) {
        Team team = teamRepository.findById(dto.getTeamId()).get();
        Player player = playerRepository.findById(dto.getPlayerId()).get();
        Preferences preferences = new Preferences(member, dto.getNickname(), team, player);
        member.setPreferences(preferences);
        preferencesRepository.save(preferences);
        memberRepository.save(member);
    }

    @Override
    public void updateMemberPreference(Member member, UpdatePreferencesRequest dto) {
        Preferences preferences = member.getPreferences();
        String nickname = dto.getNickname();
        if(!teamRepository.existsById(dto.getTeamId())&&!playerRepository.existsById(dto.getPlayerId()))
            return; //업데이트 실패 로그 출력
        Team team = teamRepository.findById(dto.getTeamId()).get();
        Player player = playerRepository.findById(dto.getPlayerId()).get();
        preferences.updatePreferences(nickname, team, player);
    }

    @Override
    public MemberDataResponse viewMemberData(Long id) {
        Member member = memberRepository.findById(id).get();
        Preferences preferences = member.getPreferences();
        return new MemberDataResponse(member.getEmail(), preferences.getNickname(),
                preferences.getTeam().getId(), preferences.getPlayer().getId());
    }

    @Override
    public void resignMember(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            Member member = memberRepository.findById(memberId).get();
            Preferences preferences = member.getPreferences();
            preferencesRepository.delete(preferences);
            memberRepository.delete(member);
        }
        else {
            //오류 메시지 출력
        }
    }
}
