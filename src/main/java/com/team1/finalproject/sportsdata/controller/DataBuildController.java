package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.service.CategoryBuildService;
import com.team1.finalproject.sportsdata.service.PeriodicBuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@Controller
@RequiredArgsConstructor
public class DataBuildController {
    private final CategoryBuildService categoryBuildService;
    private final PeriodicBuildService periodicBuildService;
    @PostMapping("/build/data/initial")
    public void buildData() throws ParseException {
        categoryBuildService.setCategory();
    }
    @PostMapping("/build/data/annual")
    public void buildAnnualData() throws ParseException {
        log.info("info log = {}", periodicBuildService.setSeason());
        log.info("info log = {}", periodicBuildService.setTeam());
        log.info("info log = {}", periodicBuildService.setPlayer());
        log.info("info log = {}", periodicBuildService.setGameByDate());
    }
}
