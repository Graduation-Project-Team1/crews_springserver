package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.config.jwt.JwtTokenUtils;
import com.team1.finalproject.memberdata.service.KaKaoService;
import com.team1.finalproject.memberdata.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping(produces = "application/json")
public class LoginController {


    private final KaKaoService ks;

    private final LoginService loginService;

    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/login/oauth2/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.googleLogin(code, registrationId);
    }

    @GetMapping("/kakao/do")
    public String kakaoLogin()
    {
        log.info("kakao login request occurred");
        return "kakaoCI/login";
    }

    @GetMapping("/kakao/getCI")
    @ResponseBody
    public String getCI(@RequestParam String code) throws IOException {
        String access_token = ks.getKakaoToken(code);
        if(access_token==null)
            System.out.println("Error signing up with Kakao.");
        return ks.signUpAsKakao(ks.getKakaoUserInfo(access_token));
    }

}
