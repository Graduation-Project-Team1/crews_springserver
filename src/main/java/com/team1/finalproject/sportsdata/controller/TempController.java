package com.team1.finalproject.sportsdata.controller;

import com.team1.finalproject.sportsdata.dto.ForwardRecordResponse;
import com.team1.finalproject.sportsdata.dto.PlayerInfoResponse;
import com.team1.finalproject.sportsdata.entity.soccer.Forward;
import com.team1.finalproject.sportsdata.repository.soccer.ForwardRepository;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class TempController {

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


    @GetMapping("/data/news/{id}")
    public ResponseEntity<String> getMainNews(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("mainNewsApi.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/data/emotion/{id}")
    public ResponseEntity<String> getEmotions(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("TeamEmotion.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/search")
    public ResponseEntity<String> getSearchResult(@RequestParam String query) throws IOException {
        InputStream inputStream = new ClassPathResource("searchResultApi.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/sns/{id}")
    public ResponseEntity<String> getSnsData(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("snsDataApi.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }

    @GetMapping("/totalNews/{id}")
    public ResponseEntity<String> getTotalNews(@PathVariable("id") Long id) throws IOException {
        InputStream inputStream = new ClassPathResource("totalNewsApi.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
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
