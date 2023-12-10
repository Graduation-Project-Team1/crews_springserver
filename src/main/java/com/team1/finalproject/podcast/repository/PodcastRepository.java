package com.team1.finalproject.podcast.repository;

import com.team1.finalproject.memberdata.entity.Preferences;
import com.team1.finalproject.podcast.entity.Podcast;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    Optional<Podcast> findByTeam(Team team);

    List<Podcast> findTop5ByOrderByMadeAtDesc();

    Optional<Podcast> findTopByTeamOrderByMadeAtDesc(Team team);

    List<Podcast> findAllByTeam(Team team);
}
