package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.common.exception.ErrorCode;
import com.team1.finalproject.common.exception.GlobalException;
import com.team1.finalproject.memberdata.dto.*;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PreferencesRepository preferencesRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public String signin(SignUpRequest dto) {
        String email = dto.getEmail();
        if (memberRepository.existsByEmail(email))
            return "duplicate email";
        // 비밀번호 bcrypt 암호화
        if(dto.getPassword()!=dto.getChkPassword())
            return "password doesn't matches";
        String password = bCryptPasswordEncoder.encode(dto.getPassword());
        Member member = new Member(email, password);
        memberRepository.save(member);

        return "complete";
    }

    @Override
    public void logIn(LoginRequest dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findByEmailAndPassword(email, password).orElseThrow(
                () -> new UsernameNotFoundException("유효하지 않은 요청입니다.")
        );
        // 유저 데이터가 담긴 토큰 발급

        // 최근 접속 날짜 업데이트
        member.updateAccessDate();
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
    public void updateMemberPassword(UpdatePasswordRequest dto, String email) {
        String password = dto.getPassword();
        String newPassword = dto.getNewPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
        );

        if (member.getPassword() == password)
            member.setPassword(newPassword);
    }

    @Override
    public void setMemberPreferences(String email, SetPreferencesRequest dto) {
        Team team = teamRepository.findById(dto.getTeamId()).get();
        Player player = playerRepository.findById(dto.getPlayerId()).get();
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Preferences preferences = new Preferences(member, dto.getNickname(), team, player);
        member.setPreferences(preferences);
        preferencesRepository.save(preferences);
        memberRepository.save(member);
    }

    @Override
    public void updateMemberPreference(String email, UpdatePreferencesRequest dto) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
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

    @Override
    public PreferencesResponse chkMemberPreference(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new GlobalException(ErrorCode.INVALID_USER)
        );
        Preferences preferences = member.getPreferences();
        if(preferences==null){
            throw new GlobalException(ErrorCode.INVALID_USER);
        }
        return new PreferencesResponse(preferences);
    }
}
