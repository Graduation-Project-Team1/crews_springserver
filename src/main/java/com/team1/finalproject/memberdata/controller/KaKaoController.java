package com.team1.finalproject.memberdata.controller;

import com.team1.finalproject.memberdata.service.KaKaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/kakao")
public class KaKaoController {


    @Autowired
    KaKaoService ks;

    @GetMapping("/do")
    public String kakaoLogin()
    {
        log.info("kakao login request occurred");
        return "/kakaoCI/login.html";
    }

    @GetMapping("/getCI")
    public String getCI(@RequestParam String code, Model model) throws IOException {
        System.out.println("code = " + code);
        String access_token = ks.getToken(code);
        Map<String, Object> userInfo = ks.getUserInfo(access_token);
        model.addAttribute("code", code);
        model.addAttribute("access_token", access_token);
        model.addAttribute("userInfo", userInfo);

        return "index.html";
    }

}
