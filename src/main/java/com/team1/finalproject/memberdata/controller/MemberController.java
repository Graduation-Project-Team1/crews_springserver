package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.dto.SignUpRequest;
import com.team1.finalproject.memberdata.dto.SignUpResponse;
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
    public SignUpResponse signUp(@RequestBody SignUpRequest dto){
        log.info("Sign up request occurred");
        String result = memberService.signin(dto);
        if(result.contains("duplicate")) {
            log.warn("Sign up failed: Given email is already used.");
            return new SignUpResponse(false, "Email already used");
        }
        if(result.contains("password")) {
            log.warn("Sign up failed: Given passwords doesn't matches each other.");
            return new SignUpResponse(false, "Recheck your password");
        }
        log.info("Sign up successful");
        return new SignUpResponse(true, "Sign up successful");
    }
/*
    @GetMapping("/member")
    @DeleteMapping("/member")*/
}
