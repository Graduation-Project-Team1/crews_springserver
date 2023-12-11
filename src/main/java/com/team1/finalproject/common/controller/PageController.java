package com.team1.finalproject.common.controller;

import com.team1.finalproject.common.dto.*;
import com.team1.finalproject.common.service.PageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;
    @GetMapping("/main")
    public MainPageResponse showMainPage(HttpServletRequest request, @RequestBody MainPageRequest dto) throws ClassNotFoundException {
        log.info(request + ": Main Page requested");
        return pageService.getMainPage(dto);
    }

    @GetMapping("/news")
    public NewsPageResponse showNewsPage(HttpServletRequest request, @RequestBody NewsPageRequest dto) {
        log.info(request + ": News Page requested");
        return pageService.getNewsPage(dto);
    }

    @GetMapping("/podcast")
    public PodcastPageResponse showPodcastPage(HttpServletRequest request, @RequestBody PodcastPageRequest dto) {
        log.info(request + ": Podcast Page requested");
        return pageService.getPodcastPage(dto);
    }

    @GetMapping("/analysis")
    public AnalysisPageResponse showAnalysisPage(HttpServletRequest request, @RequestBody AnalysisPageRequest dto) {
        log.info(request + ": Analysis Page requested");
        return pageService.getAnalysisPage(dto);
    }

    @GetMapping("/myteam")
    public MyTeamPageResponse showMyTeamPage(HttpServletRequest request, @RequestBody MainPageRequest dto) {
        log.info(request + ": My TeamPage requested");
        return pageService.getMyTeamPage(dto);
    }

    @GetMapping("/search")
    public SearchResponse search(HttpServletRequest request,
                                 @RequestParam(name="query") String query) {
        log.info(request + ": Search query requested");
        return pageService.searchQuery(query);
    }
}
