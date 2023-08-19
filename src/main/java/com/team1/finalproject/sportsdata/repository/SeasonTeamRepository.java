package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Season;
import com.team1.finalproject.sportsdata.entity.SeasonTeam;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long> {
    boolean existsBySeasonAndTeam(Season season, Team team);

    Optional<Team> findTeamById(Long seasonTeamId);
}
