package com.team1.finalproject.podcast.service;

import com.team1.finalproject.podcast.entity.Podcast;
import com.team1.finalproject.podcast.repository.PodcastRepository;
import com.team1.finalproject.sportsdata.entity.Team;
import com.team1.finalproject.sportsdata.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PodcastServiceTest {
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PodcastService podcastService;

    Podcast podcast1;
    Podcast podcast2;

    Podcast podcast3;
    Podcast podcast4;
    Podcast podcast5;
    Podcast podcast6;
    Podcast podcast7;

    Team team;


    @BeforeEach
    public void beforeEach() {
        team = teamRepository.findById(6908L).orElseThrow();

        podcast1 = podcastRepository.save(new Podcast("title1", "text1", LocalDateTime.of(2023, 12, 1, 12, 30), "300", "path1", team));
        podcast2 = podcastRepository.save(new Podcast("title2", "text2", LocalDateTime.of(2023, 12, 2, 12, 30), "300", "path2", team));
        podcast3 = podcastRepository.save(new Podcast("title3", "text3", LocalDateTime.of(2023, 12, 3, 12, 30), "300", "path3", team));
        podcast4 = podcastRepository.save(new Podcast("title4", "text4", LocalDateTime.of(2023, 12, 4, 12, 30), "300", "path4", team));
        podcast5 = podcastRepository.save(new Podcast("title5", "text5", LocalDateTime.of(2023, 12, 5, 12, 30), "300", "path5", team));
        podcast6 = podcastRepository.save(new Podcast("title6", "text6", LocalDateTime.of(2023, 12, 6, 12, 30), "300", "path6", team));
        podcast7 = podcastRepository.save(new Podcast("title7", "text7", LocalDateTime.of(2023, 12, 7, 12, 30), "300", "path7", team));
    }

    @Test
    void getTopPodcast() {
        List<Podcast> top5ByOrderByMadeAtDesc = podcastRepository.findTop5ByOrderByMadeAtDesc();
        for (Podcast podcast : top5ByOrderByMadeAtDesc) {
            System.out.println("podcast.getId() = " + podcast.getId());
            System.out.println("podcast.getTitle() = " + podcast.getTitle());
        }
        Podcast podcast = podcastRepository.findTopByTeamOrderByMadeAtDesc(team).orElseThrow();
        System.out.println("podcast.getTitle() = " + podcast.getTitle());
    }
}