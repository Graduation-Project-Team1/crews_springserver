package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.dto.*;
import com.team1.finalproject.memberdata.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public SignUpResponse signUp(@RequestBody SignUpRequest dto) {
        log.info("Sign up request occurred");
        String result = memberService.signUp(dto);
        if (result.contains("duplicate")) {
            log.warn("Sign up failed: Given email is already used.");
            return new SignUpResponse(false, "Email already used");
        }
        if (result.contains("password")) {
            log.warn("Sign up failed: Given passwords doesn't matches each other.");
            return new SignUpResponse(false, "Recheck your password");
        }
        log.info("Sign up successful");
        return new SignUpResponse(true, "Sign up successful");
    }

    @GetMapping("/member/{memberId}")
    public MemberDataResponse viewMemberData(@PathVariable Long memberId) {
        log.info("Info request occurred: target=" + memberId);
        MemberDataResponse memberDataResponse = memberService.viewMemberData(memberId);
        if (memberDataResponse.getSuccess().equals("false"))
            log.info("Invalid request: Cannot find requested data");
        else
            log.info("Info request successful");
        return memberDataResponse;
    }

    @DeleteMapping("/member")
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

    @PatchMapping("/member/{id}/password")
    public UpdatePasswordResponse updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest dto) {
        log.info("User Deletion request occurred: target=" + id);
        UpdatePasswordResponse updatePasswordResponse = memberService.updateMemberPassword(dto, id);
        if (updatePasswordResponse.getSuccess().equals("Success"))
            log.info("User password updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return updatePasswordResponse;
    }

    @GetMapping("/member/{id}/preferences")
    public GetPreferencesResponse getMemberPreferences(@PathVariable Long id) {
        log.info("User preferences request occurred: target=" + id);

        return memberService.chkMemberPreference(id);
    }
    @PutMapping("/member/{id}/preferences")
    public PreferencesResponse setPreferences(@PathVariable Long id, @RequestBody SetPreferencesRequest dto) {
        log.info("User preferences setting request occurred: target=" + id);
        PreferencesResponse preferencesResponse = memberService.setMemberPreferences(dto, id);
        if (preferencesResponse.getSuccess().equals("Success"))
            log.info("User preferences updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return preferencesResponse;
    }
    @PatchMapping("/member/{id}/preferences")
    public PreferencesResponse updatePreferences(@PathVariable Long id, @RequestBody UpdatePreferencesRequest dto) {
        log.info("User preferences update request occurred: target=" + id);
        PreferencesResponse PreferencesResponse = memberService.updateMemberPreferences(dto, id);
        if (PreferencesResponse.getSuccess().equals("Success"))
            log.info("User preferences updated successfully: target=" + id);
        else
            log.info("Request Failed");
        return PreferencesResponse;
    }
}
