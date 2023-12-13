package com.team1.finalproject.memberdata.service;

import com.team1.finalproject.common.exception.ErrorCode;
import com.team1.finalproject.common.exception.GlobalException;
import com.team1.finalproject.config.UserDetailsImpl;
import com.team1.finalproject.config.jwt.JwtTokenUtils;
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
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public String signUp(SignUpRequest dto) {
        String email = dto.getEmail();
        if (memberRepository.existsByEmail(email))
            return "duplicate email";
        if(!dto.getPassword().equals(dto.getChkPassword())){
            System.out.println(dto.getPassword()+", "+ dto.getChkPassword());
            return "password doesn't matches";}
        // 비밀번호 bcrypt 암호화
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
    public Boolean chkduplicateNickname(SetPreferencesRequest dto) {
        return null;
    }

    @Override
    public UpdatePasswordResponse updateMemberPassword(UpdatePasswordRequest dto, Long memberId) {
        String password = dto.getPassword();
        String newPassword = bCryptPasswordEncoder.encode(dto.getNewPassword());
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
        );

        if (member.getPassword() == password)
            member.setPassword(newPassword);
        else
            return new UpdatePasswordResponse("Failed");
        return new UpdatePasswordResponse("Success");
    }

    @Override
    public LogInResponse setMemberPreferences(SetPreferencesRequest dto, Long memberId) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        Player player = playerRepository.findById(dto.getPlayerId()).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
        );
        if(member.getPreferences()!=null)
            return new LogInResponse(false, "invalid request");
        Preferences preferences = new Preferences(member, team, player);
        member.setPreferences(preferences);
        member.setNickname(dto.getNickname());
        preferencesRepository.save(preferences);
        memberRepository.save(member);
        String jwtToken = jwtTokenUtils.generateJwtToken(new UserDetailsImpl(member));
        return new LogInResponse(true, "complete", memberId, jwtToken);
    }

    @Override
    public PreferencesResponse updateMemberPreferences(UpdatePreferencesRequest dto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다.")
        );
        Preferences preferences = member.getPreferences();
        String nickname = dto.getNickname();
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );
        Player player = playerRepository.findById(dto.getPlayerId()).orElseThrow(
                () -> new GlobalException(ErrorCode.DATA_NOT_FOUND)
        );

        preferences.updatePreferences(team, player);

        return new PreferencesResponse("Success");
    }

    @Override
    public MemberDataResponse viewMemberData(Long id) throws ClassNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(ClassNotFoundException::new);
        if (member.getPreferences() != null)
            return new MemberDataResponse("Success", member);
        return new MemberDataResponse("Failed");
    }

    @Override
    public MemberDeletionResponse deleteMember(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            Member member = memberRepository.findById(memberId).get();
            Preferences preferences = member.getPreferences();
            preferencesRepository.delete(preferences);
            memberRepository.delete(member);
            return new MemberDeletionResponse("Success");
        }
        else {
            return new MemberDeletionResponse("Failed");
        }
    }

    @Override
    public GetPreferencesResponse chkMemberPreference(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new GlobalException(ErrorCode.INVALID_USER)
        );
        Preferences preferences = member.getPreferences();
        if(preferences==null){
            throw new GlobalException(ErrorCode.INVALID_USER);
        }
        return new GetPreferencesResponse(preferences);
    }
}
