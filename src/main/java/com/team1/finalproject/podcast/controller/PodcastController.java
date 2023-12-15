package com.team1.finalproject.podcast.controller;


import com.team1.finalproject.podcast.dto.PodcastInfoResponse;
import com.team1.finalproject.podcast.service.PodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PodcastController {

    private final PodcastService podcastService;

    @GetMapping("/podcast")
    public ResponseEntity<?> getRecentPodcast(@RequestParam(required = false) Long teamId,
                                              @RequestParam(required = false) Long podcastId) throws IOException, ClassNotFoundException {
        if(teamId!=null)
            return podcastService.getPodcastByTeamId(teamId);
        else if(podcastId!=null)
            return podcastService.getPodcastById(podcastId);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/podcast/list")
    @ResponseBody
    public List<PodcastInfoResponse> getPodcastList(@RequestParam Long teamId) throws ClassNotFoundException {
        return podcastService.getPodcastInfoList(teamId);
    }
}
