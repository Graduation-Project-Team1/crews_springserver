package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.common.dto.SearchResponse;
import com.team1.finalproject.feign.*;
import com.team1.finalproject.feign.dto.*;
import com.team1.finalproject.feign.wrapper.*;
import com.team1.finalproject.sportsdata.service.SportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TempController {

    private final TestFeignClient testFeignClient;
    private final SportsService sportsService;
    @GetMapping("/data/record/team/{id}")
    public ResponseEntity<String> getTeamRecord(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("TeamStatistics.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/data/record/coach/{id}")
    public ResponseEntity<String> getCoachRecord(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("CoachStatistics.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }


    @GetMapping("/news/{teamId}")
    public List<NewsResponse> getMainNews(@PathVariable("teamId") Long teamId) {
        NewsResponseWrapper newsResponseWrapper = testFeignClient.getNews(teamId);
        return newsResponseWrapper.getNews();
    }

    @GetMapping("/news/{teamId}/pos")
    public List<NewsResponse> getPositiveNews(@PathVariable("teamId") Long teamId) {
        NewsResponseWrapper newsResponseWrapper = testFeignClient.getPositiveNews(teamId);
        return newsResponseWrapper.getNews();
    }

    @GetMapping("/news/{teamId}/neg")
    public List<NewsResponse> getNegativeNews(@PathVariable("teamId") Long teamId) {
        NewsResponseWrapper newsResponseWrapper = testFeignClient.getNegativeNews(teamId);
        return newsResponseWrapper.getNews();
    }

    @GetMapping("/sns/{teamId}")
    public List<SnsResponse> getSnsData(@PathVariable("teamId") Long teamId) {
        SnsResponseWrapper snsResponseWrapper = testFeignClient.getSNS(teamId);
        return  snsResponseWrapper.getSns();
    }

    @GetMapping("/community/{teamId}")
    public List<CommunityResponse> getCommunity(@PathVariable("teamId") Long teamId) {
        CommunityResponseWrapper communityResponseWrapper = testFeignClient.getCommunity(teamId);
        return communityResponseWrapper.getCommunity();
    }

    @GetMapping("/community/{teamId}/pos")
    public List<CommunityResponse> getPositiveCommunity(@PathVariable("teamId") Long teamId) {
        CommunityResponseWrapper communityResponseWrapper = testFeignClient.getCommunityPositive(teamId);
        return communityResponseWrapper.getCommunity();
    }

    @GetMapping("/community/{teamId}/neg")
    public List<CommunityResponse> getNegativeCommunity(@PathVariable("teamId") Long teamId) {
        CommunityResponseWrapper communityResponseWrapper = testFeignClient.getCommunityNegative(teamId);
        return communityResponseWrapper.getCommunity();
    }

    @GetMapping("/community/{teamId}/keyword")
    public List<KeywordResponse> getKeyword(@PathVariable("teamId") Long teamId) {
        KeywordResponseWrapper keywordResponseWrapper = testFeignClient.getKeyword(teamId);
        return keywordResponseWrapper.getKeywords();
    }

    @GetMapping("/community/{teamId}/emotion")
    public OpinionResponse getEmotions(@PathVariable("teamId") Long teamId) {
        OpinionResponseWrapper opinionResponseWrapper = testFeignClient.getOpinion(teamId);
        return opinionResponseWrapper.getOpinion();
    }

    @GetMapping("/search")
    public SearchResponse getSearchResult(@RequestParam String query) {
        return sportsService.getSearchResult(query);
    }


    @GetMapping("/data/trend/{id}")
    public ResponseEntity<String> getTrend(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("trendApi.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/data/trend")
    public ResponseEntity<String> getTeamTrend(@RequestParam Long teamId) throws IOException {
        InputStream inputStream = new ClassPathResource("TeamTrend.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/onboard")
    public ResponseEntity<String> getOnboardData(@RequestParam Long userId) throws IOException {
        InputStream inputStream = new ClassPathResource("userDataStatic.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    /*@GetMapping("/podcast")
    public ResponseEntity<String> getPodcast() throws IOException {
        InputStream inputStream = new ClassPathResource("PodcastListData.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }*/
}
