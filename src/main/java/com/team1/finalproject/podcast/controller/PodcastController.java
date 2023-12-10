package com.team1.finalproject.podcast.controller;


import com.team1.finalproject.podcast.dto.PodcastInfoResponse;
import com.team1.finalproject.podcast.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PodcastController {

    private final PodcastService podcastService;

    @GetMapping("/podcast")
    public ResponseEntity<?> getRecentPodcast(@RequestParam Long teamId) throws IOException {
        return podcastService.getPodcastByTeamId(teamId);
    }

    @GetMapping("/podcast")
    public ResponseEntity<?> getPodcast(@RequestParam Long podcastId) throws IOException {
        return podcastService.getPodcastById(podcastId);
    }

    @GetMapping("/podcast/list")
    public List<PodcastInfoResponse> getPodcastList(@RequestParam Long teamId) {
        return podcastService.getPodcastInfoList(teamId);
    }
}
