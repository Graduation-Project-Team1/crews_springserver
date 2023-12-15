package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.dto.*;
import com.team1.finalproject.memberdata.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public LogInResponse signUp(@RequestBody SignUpRequest dto) {
        log.info("Sign up request occurred");
        return memberService.signUp(dto);
    }

    @GetMapping("/{memberId}")
    public MemberDataResponse viewMemberData(@PathVariable Long memberId) throws ClassNotFoundException {
        log.info("Info request occurred: target=" + memberId);
        MemberDataResponse memberDataResponse = memberService.viewMemberData(memberId);
        if (memberDataResponse.getState().equals("false"))
            log.info("Invalid request: Cannot find requested data");
        else
            log.info("Info request successful");
        return memberDataResponse;
    }

    @DeleteMapping("")
    public MemberDeletionResponse deleteMember(@RequestBody MemberDeletionRequest dto) {
        Long memberId = dto.getMemberId();
        log.info("User Deletion request occurred: target=" + memberId);
        MemberDeletionResponse memberDeletionResponse = memberService.deleteMember(memberId);
        if (memberDeletionResponse.getSuccess().equals("Success"))
            log.info("User deleted successfully: target=" + memberId);
        else
            log.info("Request Failed");
        return memberDeletionResponse;
    }

    @PatchMapping("/{id}/password")
    public UpdatePasswordResponse updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest dto) {
        log.info("User Deletion request occurred: target=" + id);
        UpdatePasswordResponse updatePasswordResponse = memberService.updateMemberPassword(dto, id);
        if (updatePasswordResponse.getSuccess().equals("Success"))
            log.info("User password updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return updatePasswordResponse;
    }

    @GetMapping("/{id}/preferences")
    public GetPreferencesResponse getMemberPreferences(@PathVariable Long id) {
        log.info("User preferences request occurred: target=" + id);
        return memberService.chkMemberPreference(id);
    }

    @PutMapping("/{id}/preferences")
    public LogInResponse setPreferences(@PathVariable Long id, @RequestBody SetPreferencesRequest dto) throws ClassNotFoundException {
        log.info("User preferences setting request occurred: target=" + id);
        LogInResponse logInResponse = memberService.setMemberPreferences(dto, id);
        if (logInResponse.getSuccess().equals(true))
            log.info("User preferences updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return logInResponse;
    }

    @PatchMapping("/{id}/preferences")
    public PreferencesResponse updatePreferences(@PathVariable Long id, @RequestBody UpdatePreferencesRequest dto) throws ClassNotFoundException {
        log.info("User preferences update request occurred: target=" + id);
        PreferencesResponse PreferencesResponse = memberService.updateMemberPreferences(dto, id);
        if (PreferencesResponse.getSuccess().equals("Success"))
            log.info("User preferences updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return PreferencesResponse;
    }
}
