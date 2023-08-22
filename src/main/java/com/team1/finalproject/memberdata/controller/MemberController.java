package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.dto.SigninRequest;
import com.team1.finalproject.memberdata.repository.MemberRepository;
import com.team1.finalproject.memberdata.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/member")
    public String signIn(@RequestBody SigninRequest dto){
        memberService.signin(dto);
        return "Sign in complete";
    }
/*
    @GetMapping("/member")
    @DeleteMapping("/member")*/
}
