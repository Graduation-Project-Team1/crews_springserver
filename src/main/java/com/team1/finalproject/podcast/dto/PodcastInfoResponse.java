package com.team1.finalproject.podcast.dto;

import com.team1.finalproject.podcast.entity.Podcast;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastInfoResponse {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime madeAt;
    private String duration;
    private String path;
    private String teamName;

    @Builder
    public PodcastInfoResponse(Podcast podcast) {
        this.id = podcast.getId();
        this.title = podcast.getTitle();
        this.text = podcast.getText();
        this.madeAt = podcast.getMadeAt();
        this.duration = podcast.getDuration();
        this.path = "/pod/"+podcast.getPath();
        this.teamName = podcast.getTeam().getName();
    }
}
