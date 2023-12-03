package com.team1.finalproject.sportsdata.controller;

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

    @GetMapping("/record")
    public ResponseEntity<String> getPlayerRecordTest(@RequestParam String pos) throws IOException {
        switch (pos) {
            case "fw" -> {
                InputStream inputStream = new ClassPathResource("AttackerStatistics.json").getInputStream();
                byte[] arr = inputStream.readAllBytes();
                String jsonContent = new String(arr);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonContent);
            }
            case "mf" -> {
                InputStream inputStream = new ClassPathResource("MidfielderStatistics.json").getInputStream();
                byte[] arr = inputStream.readAllBytes();
                String jsonContent = new String(arr);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonContent);
            }
            case "df" -> {
                InputStream inputStream = new ClassPathResource("DefenderStatistics.json").getInputStream();
                byte[] arr = inputStream.readAllBytes();
                String jsonContent = new String(arr);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonContent);
            }
            case "gk" -> {
                InputStream inputStream = new ClassPathResource("GoalKeeperStatistics.json").getInputStream();
                byte[] arr = inputStream.readAllBytes();
                String jsonContent = new String(arr);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonContent);
            }
            default -> {
                log.warn("Parameter pos is not available.");
                return ResponseEntity
                        .notFound()
                        .build();
            }
        }
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

    @GetMapping("/podcast")
    public ResponseEntity<String> getPodcast() throws IOException {
        InputStream inputStream = new ClassPathResource("PodcastListData.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String jsonContent = new String(arr);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonContent);
    }
}
