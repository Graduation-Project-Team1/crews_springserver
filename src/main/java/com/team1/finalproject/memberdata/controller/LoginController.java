package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.service.KaKaoService;
import com.team1.finalproject.memberdata.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping(produces = "application/json")
public class LoginController {


    private final KaKaoService ks;

    private final LoginService loginService;

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
    public String getCI(@RequestParam String code, Model model) throws IOException {
        System.out.println("code = " + code);
        String access_token = ks.getKakaoToken(code);
        Map<String, Object> userInfo = ks.getKakaoUserInfo(access_token);
        model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);

        return "index.html";
    }

}
