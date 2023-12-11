package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Game;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("select g from Game g where g.teamHome=:team OR g.teamAway=:team")
    List<Game> findAllByTeam(Team team);
}
