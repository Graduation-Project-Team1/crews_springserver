package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.service.CategoryBuildService;
import com.team1.finalproject.sportsdata.service.SeasonBuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/build")
@RequiredArgsConstructor
public class DataBuildController {
    private final CategoryBuildService categoryBuildService;
    private final SeasonBuildService seasonBuildService;

    @PostMapping("/category")
    @ResponseBody
    public String buildCategoryData() throws ParseException {
        categoryBuildService.setCategory();
        return "Category Build Successful";
    }

    @PostMapping("/season")
    @ResponseBody
    public String buildSeasonData() throws ParseException {
        log.info("info log = {}", seasonBuildService.setSeason());
        log.info("info log = {}", seasonBuildService.setTeam());
        log.info("info log = {}", seasonBuildService.setPlayer());
        log.info("info log = {}", seasonBuildService.setGameByDate());
        return "Seasonal Data Build Successful";
    }
    @PostMapping("/game")
    @ResponseBody
    public void buildGame() throws ParseException {
        log.info("info log = {}", seasonBuildService.setGameByDate());
    }
    @PostMapping("/player")
    @ResponseBody
    public void buildPlayer() throws ParseException {
        seasonBuildService.setPlayerRecord();
    }
    @PostMapping("/manager")
    @ResponseBody
    public void buildManager() throws ParseException {
        seasonBuildService.setManager();
    }

    @PostMapping("/team/record")
    @ResponseBody
    public void buildTeamRecordSeason() throws ParseException {
        seasonBuildService.setTeamRecordForSeason();
    }
}
