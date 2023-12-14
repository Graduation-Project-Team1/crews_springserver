package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.SoccerTeam;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoccerTeamRepository extends JpaRepository<SoccerTeam, Long> {

    Optional<SoccerTeam> findByTeam(Team team);

    Optional<SoccerTeam> findByTeamId(Long id);

    boolean existsByTeam(Team team);
}
