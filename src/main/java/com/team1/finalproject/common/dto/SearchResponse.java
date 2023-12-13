package com.team1.finalproject.common.dto;

import com.team1.finalproject.feign.TestFeignClient;
import com.team1.finalproject.feign.dto.CommunityResponse;
import com.team1.finalproject.feign.dto.NewsResponse;
import com.team1.finalproject.podcast.dto.PodcastInfoResponse;
import com.team1.finalproject.podcast.entity.Podcast;
import com.team1.finalproject.sportsdata.dto.PlayerInfoResponse;
import com.team1.finalproject.sportsdata.dto.TeamInfoResponse;
import com.team1.finalproject.sportsdata.entity.Player;
import com.team1.finalproject.sportsdata.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchResponse {

    private String response;
    private List<TeamInfoResponse> teamInfoResponses;
    private List<PlayerInfoResponse> playerInfoResponses;
    private List<PodcastInfoResponse> podcastInfoResponses;
    private List<CommunityResponse> communityResponses;
    private List<NewsResponse> newsResponses;


    public void addTeam(Team team) {
        if(this.teamInfoResponses==null){
            this.teamInfoResponses = new ArrayList<>();
            this.teamInfoResponses.add(new TeamInfoResponse(team));
        }
        else
            this.teamInfoResponses.add(new TeamInfoResponse(team));
    }

    public void addCommunity(List<CommunityResponse> communityResponses) {
        if(this.communityResponses==null)
            this.communityResponses = communityResponses;
        else
            this.communityResponses.addAll(communityResponses);
    }

    public void addNews(List<NewsResponse> newsResponses) {
        if(this.newsResponses==null)
            this.newsResponses = newsResponses;
        else
            this.newsResponses.addAll(newsResponses);
    }

    public void addPlayer(Player player) {
        if (this.playerInfoResponses == null) {
            this.playerInfoResponses = new ArrayList<>();
            this.playerInfoResponses.add(new PlayerInfoResponse(player));
        }
        else
            this.playerInfoResponses.add(new PlayerInfoResponse(player));
    }

    public void addPodcast(Podcast podcast) {
        if (this.podcastInfoResponses == null) {
            this.podcastInfoResponses = new ArrayList<>();
            this.podcastInfoResponses.add(new PodcastInfoResponse(podcast));
        }
        else
            this.podcastInfoResponses.add(new PodcastInfoResponse(podcast));
    }
}
