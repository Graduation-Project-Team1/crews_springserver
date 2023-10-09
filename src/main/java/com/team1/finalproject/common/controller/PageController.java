package com.team1.finalproject.common.controller;

import com.team1.finalproject.common.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/main")
    public MainPageResponse showMainPage(@RequestBody MainPageRequest dto) {
        return null;
    }

    @GetMapping("/news")
    public NewsPageResponse showNewsPage(@RequestBody NewsPageRequest dto) {
        return null;
    }

    @GetMapping("/podcast")
    public PodcastPageResponse showPodcastPage(@RequestBody PodcastPageRequest dto) {
        return null;
    }

    @GetMapping("/analysis")
    public AnalysisPageResponse showAnalysisPage(@RequestBody AnalysisPageRequest dto) {
        return null;
    }

    @GetMapping("/myteam")
    public MyTeamPageResponse showMyTeamPage(@RequestBody MainPageRequest dto) {
        return null;
    }

    @GetMapping("/search")
    public MyTeamPageResponse search(@RequestBody MainPageRequest dto) {
        return null;
    }
}
